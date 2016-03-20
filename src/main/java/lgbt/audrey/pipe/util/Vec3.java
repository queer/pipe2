package lgbt.audrey.pipe.util;

/**
 * A simple 3-dimensional vector
 *
 * @author c
 * @since 5/11/15
 */
@SuppressWarnings({"unused", "StaticVariableOfConcreteClass"})
public class Vec3 implements Cloneable {
    /**
     * x-, y-, and z-coordinates of this vector
     */
    private double x;
    private double y;
    private double z;

    /**
     * Vector with values of zero
     */
    private static final Vec3 ZERO_VEC = new Vec3(0, 0, 0);

    /**
     * Vector with values of one
     */
    private static final Vec3 UNIT_VEC = new Vec3(1, 1, 1);

    /**
     * Stereotypical up vector
     */
    private static final Vec3 UP_VEC = new Vec3(0, 1, 0);

    /**
     * Creates a new vector with the supplied coordinates
     *
     * @param x The x-coordinate
     * @param y The y-coordinate
     * @param z The z-coordinate
     */
    public Vec3(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Adds another vector to this one
     *
     * @param v The vector to add
     * @return Itself
     */
    public Vec3 add(final Vec3 v) {
        x += v.x;
        y += v.y;
        z += v.z;
        return this;
    }

    /**
     * Subtracts another vector from this one
     *
     * @param v The vector to subtract
     * @return Itself
     */
    public Vec3 sub(final Vec3 v) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
        return this;
    }

    /**
     * Multiplies this vector by another one
     *
     * @param v The vector to multiply by
     * @return Itself
     */
    public Vec3 mul(final Vec3 v) {
        x *= v.x;
        y *= v.y;
        z *= v.z;
        return this;
    }

    /**
     * Divides this vector by another one
     *
     * @param v The vector to divide by
     * @return Itself
     */
    public Vec3 div(final Vec3 v) {
        x /= v.x;
        y /= v.y;
        z /= v.z;
        return this;
    }

    /**
     * Returns the distance from this vector to the supplied vector
     *
     * @param v The vector to get the distance to
     * @return The distance from this vector to the supplied vector
     */
    public double dist(final Vec3 v) {
        return Math.sqrt(x * v.x + y * v.y + z * v.z);
    }

    /**
     * Returns the dot product of this vector with the supplied vector
     *
     * @param v The vector to do the dot product with
     * @return The dot product
     */
    public double dot(final Vec3 v) {
        return x * v.x + y * v.y + z * v.z;
    }

    /**
     * Returns the length of this vector
     *
     * @return The length of this vector from the origin
     */
    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    /**
     * Normalizes this vector
     *
     * @return Itself
     */
    public Vec3 normalize() {
        final double l = length();
        x = x / l;
        y = y / l;
        z = z / l;
        return this;
    }

    /**
     * Returns the cross product of this vector with the supplied vector
     *
     * @param v The vector to do the cross product with
     * @return The cross product
     */
    public Vec3 cross(final Vec3 v) {
        final double tx = y * v.z - z * v.y;
        final double ty = z * v.x - x * v.z;
        final double tz = x * v.y - y * v.x;
        x = tx;
        y = ty;
        z = tz;
        return this;
    }

    /**
     * Adds the supplied amount to the x-coordinate
     *
     * @param x The amount to add
     * @return Itself
     */
    public Vec3 addX(final double x) {
        this.x += x;
        return this;
    }

    /**
     * Adds the supplied amount to the y-coordinate
     *
     * @param y The amount to add
     * @return Itself
     */
    public Vec3 addY(final double y) {
        this.y += y;
        return this;
    }

    /**
     * Adds the supplied amount to the z-coordinate
     *
     * @param z The amount to add
     * @return Itself
     */
    public Vec3 addZ(final double z) {
        this.z += z;
        return this;
    }

    /**
     * Sets the x-, y-, and z-coordinates of ths vector to those of the supplied
     * vector.
     *
     * @param v The vector to "clone"
     * @return Itself
     */
    public Vec3 set(final Vec3 v) {
        x = v.x;
        y = v.y;
        z = v.z;
        return this;
    }

    /**
     * The x-coordinate of this vector
     *
     * @return The x-coordinate
     */
    public double x() {
        return x;
    }

    /**
     * The y-coordinate of this vector
     *
     * @return The y-coordinate
     */
    public double y() {
        return y;
    }

    /**
     * The z-coordinate of this vector
     *
     * @return The z-coordinate
     */
    public double z() {
        return z;
    }

    /**
     * Sets the x-coordinate of this vector
     *
     * @param x The new x-coordinate
     */
    public void x(final double x) {
        this.x = x;
    }

    /**
     * Sets the y-coordinate of this vector
     *
     * @param y The new y-coordinate
     */
    public void y(final double y) {
        this.y = y;
    }

    /**
     * Sets the z-coordinate of this vector
     *
     * @param z The new z-coordinate
     */
    public void z(final double z) {
        this.z = z;
    }

    /**
     * Returns a String representation of this vector
     *
     * @return A String representation of this vector
     */
    @Override
    public String toString() {
        return '(' + x + ", " + y + ", " + z + ')';
    }

    /**
     * Returns the zero vector
     *
     * @return The zero vector
     */
    public static Vec3 zero() {
        return ZERO_VEC;
    }

    /**
     * Returns the unit vector
     *
     * @return The unit vector
     */
    public static Vec3 unit() {
        return UNIT_VEC;
    }

    /**
     * Returns the up vector
     *
     * @return The stereotypical up vector
     */
    public static Vec3 up() {
        return UP_VEC;
    }
}
