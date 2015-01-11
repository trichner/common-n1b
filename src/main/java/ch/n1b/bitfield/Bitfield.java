package ch.n1b.bitfield;

import ch.n1b.vector.Vec2D;

/**
 * Created on 04.01.2015.
 *
 * @author Thomas
 */
public class Bitfield {
    private static final int SIZE = 16;
    private static final int EIGHT = 8;
    private Bitfield8x8[][] fields = new Bitfield8x8[SIZE][];

    private Vec2D offset = Vec2D.zero;

    public boolean isSet(int x, int y){
        boolean isSet = false;
        if(inRange(x,y)){
            Bitfield8x8[] field = fields[x/ EIGHT];
            if(field!=null){
                Bitfield8x8 subfield = field[y/ EIGHT];
                if(subfield!=null){
                    isSet = subfield.isSet(x% EIGHT,y% EIGHT);
                }
            }
        }
        return isSet;
    }

    private boolean inRange(int x, int y){
        return x>=0 && y>=0 && x<SIZE*8 && y<SIZE*8;
    }

    public Bitfield set(int x, int y) {
        return set(x, y, true);
    }

    public Bitfield set(int x, int y,boolean bit){
        if(inRange(x,y)) {
            if (fields[x/ EIGHT] == null) {
                fields[x/ EIGHT] = new Bitfield8x8[SIZE];
            }
            Bitfield8x8[] field = fields[x/ EIGHT];
            if (field[y / EIGHT] == null) {
                field[y / EIGHT] = new Bitfield8x8();
            }
            if(bit){
                field[y / EIGHT].set(x % EIGHT, y % EIGHT);
            }else {
                field[y / EIGHT].erase(x % EIGHT, y % EIGHT);
            }
        }
        return this;
    }

    public Bitfield erase(int x, int y){
        return set(x, y, false);
    }

    public boolean reduce(){
        if(!isEmpty()) {
            int offsetX = offset.X;
            int offsetY = offset.Y;
            while (isEmptyLeft()) {
                shiftLeft();
                offsetX++;
            }
            while (isEmptyDown()) {
                shiftDown();
                offsetY++;
            }
            offset = new Vec2D(offsetX, offsetY);
            return true;
        }else {
            return false;
        }
    }


    public Bitfield4x4 cut4x4(){
        //---- find first
        int offsetX = 0;
        for(;offsetX<SIZE*8;offsetX++){
            if(isSet(offsetX,0)){
                break;
            }
        }

        //---- grow area
        Bitfield8x8 field8x8 = new Bitfield8x8();
        grow(field8x8,4,0,offsetX);
        Bitfield4x4 cutout = field8x8.reduce();

        Vec2D offset = cutout.getOffset().add(new Vec2D(offsetX,0)).add(this.offset);
        cutout.setOffset(offset);

        return cutout;
    }

    private void grow(Bitfield8x8 field,int x, int y,int offsetX){
        if(!isSet(offsetX+x,y)){
            return;
        }else if(!in8x8Range(x,y)){
            // WARNING! We are out of range! Doors can not be bigger than 4x4!
            return;
        }

        field.set(x,y);
        erase(offsetX+x,y);
        grow(field,x+1,y,offsetX);
        grow(field,x,y+1,offsetX);
        grow(field,x-1,y,offsetX);
    }

    private boolean in8x8Range(int x, int y){
        return x>=0 && y>=0 && x<8 && y<8;
    }

    private boolean isEmptyLeft(){
        if(fields[0] != null) {
            for (Bitfield8x8 field : fields[0]) {
                if (field != null && !field.leftEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isEmptyDown(){
        for (Bitfield8x8[] field : fields) {
            if(field!=null){
                Bitfield8x8 subfield = field[0];
                if (subfield != null && !subfield.downEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private Bitfield shiftLeft(){
        for (int y = 0; y < SIZE; y++) {
            if(fields[0][y]!=null){
                fields[0][y].shiftLeft();
            }
            for (int x = 1; x < SIZE; x++) {
                if(fields[x]!=null && fields[x][y]!=null){
                    long overflow = fields[x][y].shiftLeftOverflow();
                    if(overflow!=0){
                        Bitfield8x8 subfield = fields[x-1][y];
                        if(subfield != null){
                            subfield.or(overflow);
                        }
                    }
                }
            }
        }
        return this;
    }

    private Bitfield shiftDown(){
        for (Bitfield8x8[] field : fields) {
            if(field!=null) {
                if (field[0] != null) {
                    field[0].shiftDown();
                }
                for (int y = 1; y < field.length; y++) {
                    if (field[y] != null) {
                        long overflow = field[y].shiftDownOverflow();
                        if (overflow != 0) {
                            Bitfield8x8 subfield = field[y];
                            if (subfield != null) {
                                subfield.or(overflow);
                            }
                        }
                    }
                }
            }
        }
        return this;
    }

    public boolean isEmpty(){
        for (Bitfield8x8[] xfields : fields) {
            if(xfields!=null) {
                for (Bitfield8x8 yfields : xfields) {
                    if (yfields!=null && (!yfields.isEmpty())){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("---- Bitfield --------------------------------------------\n");
        for (int y = SIZE*8-1; y >0; y--) {
            for (int x = 0; x < SIZE*8; x++) {
                builder.append(isSet(x,y) ? "X" : ".");
            }
            builder.append('\n');
        }
        builder.append("----------------------------------------------------------");
        return builder.toString();
    }
}
