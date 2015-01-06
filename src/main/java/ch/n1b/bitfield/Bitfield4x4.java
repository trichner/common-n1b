package ch.n1b.bitfield;

import ch.n1b.vector.Vec2D;

/**
 * Created on 04.01.2015.
 *
 * @author Thomas
 */
public class Bitfield4x4 {

    public static final int SIZE = 4;
    private short field = 0;

    private Vec2D offset = Vec2D.zero;

    public Bitfield4x4(short field) {
        this.field = field;
    }

    public Bitfield4x4(short field, Vec2D offset) {
        this(field);
        this.offset = offset;
    }

    public Bitfield4x4() {}

    public Vec2D getOffset() {
        return offset;
    }

    public void setOffset(Vec2D offset) {
        this.offset = offset;
    }

    public boolean isSet(int x,int y){
        return Bitfield4Utils.isSet(x, y, field);
    }

    public Bitfield4x4 set(int x, int y){
        field |= Bitfield4Utils.xyToBit(x, y);
        return this;
    }

    public Bitfield4x4 shiftLeft(){
        field = Bitfield4Utils.shiftLeft(field);
        return  this;
    }

    public Bitfield4x4 shiftUp(){
        field = Bitfield4Utils.shiftUp(field);
        return  this;
    }

    public Bitfield4x4 shiftRight(){
        field = Bitfield4Utils.shiftRight(field);
        return  this;
    }

    public Bitfield4x4 shiftDown(){
        field = Bitfield4Utils.shiftDown(field);
        return  this;
    }

    public Bitfield4x4 align(){
        if(field!=0) {
            while (Bitfield4Utils.isSet(field,Bitfield4Utils.BORDER_D)) {
                shiftDown();
            }
            while (Bitfield4Utils.isSet(field,Bitfield4Utils.BORDER_L)) {
                shiftLeft();
            }
        }
        return  this;
    }

    public Bitfield4x4 flipHorizontally(){
        field = Bitfield4Utils.flipX(field);
        return this;
    }

    @Override
    public String toString() {
        return Bitfield4Utils.toString(field);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bitfield4x4 that = (Bitfield4x4) o;

        if (field != that.field) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return field;
    }
}
