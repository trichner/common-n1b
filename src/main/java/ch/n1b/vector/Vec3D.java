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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vec3D vec3D = (Vec3D) o;

        if (X != vec3D.X) return false;
        if (Y != vec3D.Y) return false;
        if (Z != vec3D.Z) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = X;
        result = 31 * result + Y;
        result = 31 * result + Z;
        return result;
    }
}
