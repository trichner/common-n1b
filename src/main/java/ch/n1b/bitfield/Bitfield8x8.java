package ch.n1b.bitfield;

import ch.n1b.vector.Vec2D;

/**
 * Created on 04.01.2015.
 *
 * @author Thomas
 */
public class Bitfield8x8 {

    private long field = 0L;

    public Bitfield8x8(long field) {
        this.field = field;
    }

    public Bitfield8x8() {}

    public boolean isSet(int x,int y){
        return Bitfield8Utils.isSet(x, y, field);
    }

    public Bitfield8x8 set(int x, int y){
        field |= Bitfield8Utils.xyToBit(x, y);
        return this;
    }

    public Bitfield8x8 erase(int x, int y){
        field &= ~Bitfield8Utils.xyToBit(x, y);
        return this;
    }

    public Bitfield8x8 shiftLeft(){
        field = Bitfield8Utils.shiftW(field);
        return  this;
    }

    public Bitfield8x8 shiftUp(){
        field = Bitfield8Utils.shiftN(field);
        return  this;
    }

    public Bitfield8x8 shiftRight(){
        field = Bitfield8Utils.shiftO(field);
        return  this;
    }

    public Bitfield8x8 shiftDown(){
        field = Bitfield8Utils.shiftS(field);
        return  this;
    }

    public Bitfield4x4 reduce(){
        //--- align
        int dx = 0, dy = 0;
        while (field!=0 && (field & Bitfield8Utils.BORDER_D) == 0) {
            shiftDown();
            dy++;
        }
        while (field!=0 && (field & Bitfield8Utils.BORDER_L) == 0) {
            shiftLeft();
            dx++;
        }
        //--- map
        short field4 = Bitfield4Utils.map8x8(field);
        return new Bitfield4x4(field4,new Vec2D(dx,dy));
    }

    /**
     * Shifts a (long) as 8x8 Bitfield one to the left
     */
    public long shiftLeftOverflow(){
        long overflow;
        field >>>= 1;
        overflow = field & Bitfield8Utils.BORDER_R;
        field &= ~Bitfield8Utils.BORDER_R;
        return overflow;
    }
    /**
     * Shifts a (long) as 8x8 Bitfield one to the right
     */
    public long shiftRightOverflow(){
        long overflow;
        field <<= 1;
        overflow = field & Bitfield8Utils.BORDER_L;
        field &= ~Bitfield8Utils.BORDER_L;
        return overflow;
    }
    /**
     * Shifts a (long) as 8x8 Bitfield one up
     */
    public long shiftUpOverflow(){
        long overflow = field >>> 56;
        field <<= 8;
        return overflow;
    }
    /**
     * Shifts a (long) as 8x8 Bitfield one down
     */
    public long shiftDownOverflow(){
        long overflow = field << 56;
        field >>>= 8;
        return overflow;
    }

    public boolean leftEmpty(){
        return (field & Bitfield8Utils.BORDER_L) == 0;
    }

    public boolean downEmpty(){
        return (field & Bitfield8Utils.BORDER_D) == 0;
    }

    public Bitfield8x8 and(Bitfield8x8 another){
        return and(another.field);
    }

    public Bitfield8x8 or(Bitfield8x8 another){
        return or(another.field);
    }

    public Bitfield8x8 xor(Bitfield8x8 another){
        return xor(another.field);
    }

    public Bitfield8x8 and(long another){
        field &= another;
        return this;
    }

    public Bitfield8x8 or(long another){
        field |= another;
        return this;
    }

    public Bitfield8x8 xor(long another){
        field ^= another;
        return this;
    }

    public boolean isEmpty(){
        return field==0;
    }

    @Override
    public String toString() {
        return Bitfield8Utils.toString(field);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bitfield8x8 that = (Bitfield8x8) o;

        if (field != that.field) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (field ^ (field >>> 32));
    }
}
