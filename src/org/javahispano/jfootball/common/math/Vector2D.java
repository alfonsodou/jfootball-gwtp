/**
 * 
 */
package org.javahispano.jfootball.common.math;

/**
 * @author alfonso
 *
 */
public class Vector2D {
	public double x;
	public double y;

	Vector2D() {
		this.x = 0.0;
		this.y = 0.0;
	}

	Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/*
	 * sets x and y to zero
	 */
	public void Zero() {
		this.x = 0.0;
		this.y = 0.0;
	}

	/*
	 * returns true if both x and y are zero
	 */
	public boolean isZero() {
		return ((x * x) + (y * y)) < Double.MIN_VALUE;
	}

	/*
	 * returns the length of a 2D vector
	 */
	public double Length() {
		return Math.sqrt((x * x) + (y * y));
	}

	/*
	 * returns the squared length of a 2D vector
	 */
	public double LengthSq() {
		return ((x * x) + (y * y));
	}

	/*
	 * calculates the dot product
	 */
	public double Dot(Vector2D v2d) {
		return ((x * v2d.x) + (y * v2d.y));
	}

	/*
	 * returns true if v2 is clockwise of this vector, false if anticlockwise (Y
	 * axis pointing down, X axis to right)
	 */
	public boolean Sign(Vector2D v2d) {
		if ((y * v2d.x) > (x * v2d.y)) {
			return false;
		} else {
			return true;
		}
	}

	/*
	 * Returns a vector perpendicular to this vector
	 */
	public Vector2D Perp() {
		return new Vector2D(-y, x);
	}

	/*
	 * calculates the euclidean distance between two vectors
	 */
	public double Distance(Vector2D v2d) {
		double ySeparation = v2d.y - y;
		double xSeparation = v2d.x - x;

		return Math.sqrt((ySeparation * ySeparation)
				+ (xSeparation * xSeparation));
	}

	/*
	 * calculates the euclidean distance squared between two vectors
	 */
	public double DistanceSq(Vector2D v2d) {
		double ySeparation = v2d.y - y;
		double xSeparation = v2d.x - x;

		return (ySeparation * ySeparation) + (xSeparation * xSeparation);
	}
}
