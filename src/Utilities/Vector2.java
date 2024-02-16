package Utilities;

import java.util.Objects;

/**
 * Represents a 2D vector with integer x and y coordinates.
 * 
 * <p>
 * This class encapsulates operations and properties of a 2-dimensional vector
 * that can be used in various mathematical or graphical scenarios.
 * </p>
 *
 * <p>
 * Common operations such as addition, subtraction, and magnitude calculation
 * are provided. It also provides a set of predefined vectors like {@link #zero}
 * and {@link #up} for convenience.
 * </p>
 * 
 * <p>
 * Example:
 * 
 * <pre>
* {@code
 * Vector2 a = new Vector2(1, 2);
 * Vector2 b = new Vector2(2, 3);
 * Vector2 result = a.plus(b); // Returns a new Vector2(3, 5)
 * }
* </pre>
 * </p>
 * 
 * @author Hunter
 * @since 1.0
 */
public class Vector2 {

    /**
     * A static Vector2 that contains the value: (x: 1, y: 1)
     */
    public static final Vector2 one = new Vector2(1, 1);

    /**
     * A static Vector2 that contains the value: (x: 0, y: 0)
     */
    public static final Vector2 zero = new Vector2(0, 0);

    /**
     * A static Vector2 that contains the value: (x: 0, y: 1)
     */
    public static final Vector2 up = new Vector2(0, 1);

    /**
     * A static Vector2 that contains the value: (x: 0, y: -1)
     */
    public static final Vector2 down = new Vector2(0, -1);

    /**
     * A static Vector2 that contains the value: (x: -1, y: 0)
     */
    public static final Vector2 left = new Vector2(-1, 0);

    /**
     * A static Vector2 that contains the value: (x: 1, y: 0)
     */
    public static final Vector2 right = new Vector2(1, 0);

    public float x;
    public float y;

    /**
     * Default constructor. Initializes the vector with both x and y set to 0.
     */
    public Vector2() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * Parameterized constructor. Initializes the vector with the provided x and y
     * values.
     *
     * @param x The x-coordinate of the vector.
     * @param y The y-coordinate of the vector.
     */
    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Adds the provided vector's coordinates to this vector's coordinates.
     * <p>
     * This method performs element-wise addition between this vector and the
     * provided vector.
     * The result is a new {@code Vector2} instance, without modifying the original
     * vectors.
     * </p>
     * 
     * @param vector2 The vector to be added to this vector. Must not be
     *                {@code null}.
     * @return A new {@code Vector2} instance with the result of the addition.
     * @throws IllegalArgumentException if {@code vector2} is null.
     */
    public Vector2 plus(Vector2 vector2) {
        if (vector2 == null) {
            throw new IllegalArgumentException("Provided vector must not be null");
        }
        return new Vector2(x + vector2.x, y + vector2.y);
    }

    /**
     * Subtracts the provided vector's coordinates from this vector's coordinates.
     * 
     * @param vector2 The vector whose coordinates are to be subtracted from this
     *                vector. Must not be {@code null}.
     * @return A new {@code Vector2} instance with the result of the subtraction.
     * @throws IllegalArgumentException if {@code vector2} is null.
     */
    public Vector2 minus(Vector2 vector2) {
        if (vector2 == null) {
            throw new IllegalArgumentException("Provided vector must not be null");
        }
        return new Vector2(x - vector2.x, y - vector2.y);
    }

    /**
     * Multiplies this vector's coordinates by the provided scalar value.
     * <p>
     * This operation scales the vector's magnitude by the scalar, changing its
     * length
     * but not its direction.
     * </p>
     * 
     * @param scalar The scalar value by which this vector's coordinates are to be
     *               multiplied.
     * @return A new {@code Vector2} instance with the result of the multiplication.
     */
    public Vector2 mult(float scalar) {
        return new Vector2(x * scalar, y * scalar);
    }

    /**
     * Multiplies this vector's coordinates by the provided value.
     * 
     * @param vector2 The vector value by which this vector's coordinates are to be
     *                multiplied. (x1 * x2) and (y1 * y2)
     * @return A new {@code Vector2} instance with the result of the multiplication.
     */
    public Vector2 mult(Vector2 vector2) {
        return new Vector2(x * vector2.x, y * vector2.y);
    }

    /**
     * Divides this vector's coordinates by the provided scalar value.
     * 
     * @param scalar The scalar value by which this vector's coordinates are to be
     *               divided. Must not be zero.
     * @return A new {@code Vector2} instance with the result of the division.
     * @throws IllegalArgumentException if {@code scalar} is zero.
     */
    public Vector2 div(float scalar) {
        if (scalar == 0) {
            throw new IllegalArgumentException("Scalar value must not be zero");
        }
        return new Vector2(x / scalar, y / scalar);
    }

    /**
     * Divides this vector's coordinates by the provided value.
     * 
     * @param vector2 The vector value by which this vector's coordinates are to be
     *                divided. (x1 / x2) and (y1 / y2)
     * @return A new {@code Vector2} instance with the result of the division.
     */
    public Vector2 div(Vector2 vector2) {
        if (vector2.x == 0 || vector2.y == 0) {
            throw new IllegalArgumentException("Scalar value must not be zero");
        }
        return new Vector2(x / vector2.x, y / vector2.y);
    }

    /**
     * Adds two vectors together and returns the resulting vector.
     * <p>
     * This static method performs a vector addition by taking the x and y
     * coordinates
     * from each input vector, adding them together, and then returning a new
     * {@code Vector2}
     * instance with the resultant coordinates.
     * </p>
     * 
     * @param vector1 The first vector. Must not be {@code null}.
     * @param vector2 The second vector. Must not be {@code null}.
     * @return A new {@code Vector2} instance that represents the sum of the two
     *         input vectors.
     * @throws IllegalArgumentException if either {@code vector1} or {@code vector2}
     *                                  is null.
     */
    public static Vector2 add(Vector2 vector1, Vector2 vector2) {
        if (vector1 == null || vector2 == null) {
            throw new IllegalArgumentException("Vectors must not be null");
        }
        return vector1.plus(vector2);
    }

    /**
     * Subtracts the second vector from the first vector and returns the resulting
     * vector.
     * 
     * @param vector1 The vector from which {@code vector2} is subtracted. Must not
     *                be {@code null}.
     * @param vector2 The vector to be subtracted. Must not be {@code null}.
     * @return A new {@code Vector2} instance that represents the difference of the
     *         two input vectors.
     * @throws IllegalArgumentException if either {@code vector1} or {@code vector2}
     *                                  is null.
     */
    public static Vector2 subtract(Vector2 vector1, Vector2 vector2) {
        if (vector1 == null || vector2 == null) {
            throw new IllegalArgumentException("Vectors must not be null");
        }
        return vector1.minus(vector2);
    }

    /**
     * Multiplies a vector by a scalar value and returns the resulting vector.
     * 
     * @param vector The vector to be multiplied. Must not be {@code null}.
     * @param scalar The scalar value by which the vector is multiplied.
     * @return A new {@code Vector2} instance that represents the product of the
     *         vector and the scalar.
     * @throws IllegalArgumentException if {@code vector} is null.
     */
    public static Vector2 multiply(Vector2 vector, float scalar) {
        if (vector == null) {
            throw new IllegalArgumentException("Vector must not be null");
        }
        return vector.mult(scalar);
    }

    /**
     * Multiplies a vector by a vector value and returns the resulting vector.
     * 
     * @param vector  The vector to be multiplied. Must not be {@code null}.
     * @param vector2 The vector value by which the vector is multiplied.
     * @return A new {@code Vector2} instance that represents the product of the
     *         vectors.
     * @throws IllegalArgumentException if {@code vector} is null.
     */
    public static Vector2 multiply(Vector2 vector, Vector2 vector2) {
        if (vector == null) {
            throw new IllegalArgumentException("Vector must not be null");
        }
        return vector.mult(vector2);
    }

    /**
     * Divides a vector by a scalar value and returns the resulting vector.
     * 
     * @param vector The vector to be divided. Must not be {@code null}.
     * @param scalar The scalar value by which the vector is divided. Must not be
     *               zero.
     * @return A new {@code Vector2} instance that represents the quotient of the
     *         vector and the scalar.
     * @throws IllegalArgumentException if {@code vector} is null or {@code scalar}
     *                                  is zero.
     */
    public static Vector2 divide(Vector2 vector, float scalar) {
        if (vector == null) {
            throw new IllegalArgumentException("Vector must not be null");
        }
        if (scalar == 0) {
            throw new IllegalArgumentException("Scalar value must not be zero");
        }
        return vector.div(scalar);
    }

    /**
     * Divides a vector by a vector value and returns the resulting vector.
     * 
     * @param vector The vector to be divided. Must not be {@code null}.
     * @param scalar The vector value by which the vector is divided. Must not be
     *               zero.
     * @return A new {@code Vector2} instance that represents the quotient of the
     *         vectors.
     * @throws IllegalArgumentException if {@code vector} is null or {@code scalar}
     *                                  is zero.
     */
    public static Vector2 divide(Vector2 vector, Vector2 vector2) {
        if (vector == null) {
            throw new IllegalArgumentException("Vector must not be null");
        }
        if (vector2.x == 0 || vector2.y == 0) {
            throw new IllegalArgumentException("Scalar value must not be zero");
        }
        return vector.div(vector2);
    }

    /**
     * Computes the magnitude (length) of the vector.
     * 
     * <p>
     * The magnitude is the Euclidean distance from this vector to the origin
     * (0, 0). It provides a measure of the "length" or "size" of the vector,
     * irrespective of its direction. The magnitude is always non-negative and
     * gives the straight-line distance between the point represented by this vector
     * and the origin.
     * </p>
     * 
     * <p>
     * This implementation leverages the {@link #distance(Vector2, Vector2)}
     * method by calculating the distance between the vector and the origin,
     * represented by {@code Vector2.zero}.
     * </p>
     *
     * @return The magnitude of the vector.
     */
    public double magnitude() {
        return distance(Vector2.zero, this);
    }

    /**
     * Calculates the Euclidean distance between two points represented by
     * {@code Vector2} objects.
     * The distance is computed using the formula:
     * distance = sqrt((end.x - start.x)^2 + (end.y - start.y)^2).
     * 
     * @param start The starting point represented as a Vector2 object. Must not be
     *              null.
     * @param end   The ending point represented as a Vector2 object. Must not be
     *              null.
     * @return The Euclidean distance between the two points.
     * @throws IllegalArgumentException if either {@code start} or {@code end} is
     *                                  null.
     */
    public static double distance(Vector2 start, Vector2 end) {

        if (start == null || end == null) {
            throw new IllegalArgumentException("Vectors must not be null");
        }

        var x = Math.pow((end.x - start.x), 2);
        var y = Math.pow((end.y - start.y), 2);

        return Math.sqrt(x + y);
    }

    /**
     * Normalizes the vector.
     * If the vector's magnitude is 0 (i.e., it's a zero vector), it remains
     * unchanged.
     * Otherwise, the vector is scaled such that its magnitude becomes 1.
     *
     * @return The normalized vector.
     */
    public Vector2 normalize() {
        double magnitude = Math.sqrt(x * x + y * y);

        // Check if the vector is a zero vector.
        if (magnitude == 0) {
            return this;
        }

        float newX = (float) (x / magnitude);
        float newY = (float) (y / magnitude);

        return new Vector2(newX, newY);
    }

    /**
     * Binarizes the components of the vector.
     * <p>
     * This method converts each component of the vector to its corresponding sign:
     * - 1 if the component is positive
     * - -1 if the component is negative
     * - 0 if the component is zero
     * </p>
     * 
     * @return A new {@code Vector2} object where each component is set to its
     *         corresponding sign.
     *         If both components of the original vector are zero, the method
     *         returns a zero vector.
     */
    public Vector2 binarize() {

        if (x == 0 && y == 0) {
            return Vector2.zero;
        }

        return new Vector2(Math.signum(x), Math.signum(y));
    }

    /**
     * Truncates the x and y coordinates of the vector.
     * This method essentially removes any fractional part of the coordinates,
     * leaving only the whole number. For example, a coordinate value of 5.7
     * would be truncated to 5.
     *
     * @return A new Vector2 instance with truncated x and y coordinates.
     */
    public Vector2 truncate() {
        return new Vector2((float) ((int) x), (float) ((int) y));
    }

    /**
     * Rounds the x and y coordinates of the vector to the nearest whole number.
     * Uses standard mathematical rounding, where values with a fractional part
     * of 0.5 or greater are rounded up and values with a fractional part less
     * than 0.5 are rounded down.
     *
     * @return A new Vector2 instance with rounded x and y coordinates.
     */
    public Vector2 round() {
        return new Vector2(Math.round(x), Math.round(y));
    }

    // Linear Interpolation (LERP) static method
    public static Vector2 lerp(Vector2 start, Vector2 end, float t) {
        return start.mult(1 - t).plus(end.mult(t));
    }

    /**
     * Returns a hash code value for this Vector2 object.
     * This implementation is based on the x and y coordinates.
     *
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Compares this vector to the specified object for equality.
     * The result is true if and only if the argument is not null
     * and is a Vector2 object that represents the same (x, y) coordinate as this
     * object.
     *
     * @param obj The object to compare with.
     * @return True if the given object represents a Vector2 equivalent to this
     *         vector, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Vector2 vector2 = (Vector2) obj;
        return x == vector2.x && y == vector2.y;
    }

    /**
     * Returns a string representation of this Vector2 object.
     * The string is in the format "(x, y)".
     *
     * @return A string representation of this vector in the format "(x, y)".
     */
    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

}