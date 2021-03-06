package ch.n1b.vector;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas
 * Date: 29.09.13
 * Time: 23:43
 * To change this template use File | Settings | File Templates.
 */
public final class Vec2D {

    public static final Vec2D zero = new Vec2D(0,0);

    public final int X;
    public final int Y;

    public Vec2D(int x, int y) {
        X = x;
        Y = y;
    }

    public final Vec2D add(Vec2D c) {
        return new Vec2D(X + c.X, Y + c.Y);
    }

    public final int dot(Vec2D c) {
        return X * c.X + Y * c.Y;
    }

    public final Vec2D mult(int m) {
        return new Vec2D(X*m,Y*m);
    }

    public final  int norm1(){
        return Math.abs(X)+Math.abs(Y);
    }

    @Override
    public String toString() {
        return "["+X+"/"+Y+"]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vec2D vec2D = (Vec2D) o;

        if (X != vec2D.X) return false;
        if (Y != vec2D.Y) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = X;
        result = 31 * result + Y;
        return result;
    }
}
