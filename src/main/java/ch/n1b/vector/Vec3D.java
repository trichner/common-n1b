package ch.n1b.vector;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas
 * Date: 29.09.13
 * Time: 23:43
 * To change this template use File | Settings | File Templates.
 */
public final class Vec3D {
    public final int X;
    public final int Y;
    public final int Z;

    public Vec3D(int x, int y, int z) {
        X = x;
        Y = y;
        Z = z;
    }

    public final Vec3D add(Vec3D c) {
        return new Vec3D(X + c.X, Y + c.Y, Z + c.Z);
    }

    public final int dot(Vec3D c) {
        return X * c.X + Y * c.Y + Z * c.Z;
    }

    public final Vec3D mult(int factor){
        return new Vec3D(X*factor,Y*factor,Z*factor);
    }
}