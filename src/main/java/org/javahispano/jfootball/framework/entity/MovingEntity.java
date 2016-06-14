/**
 * 
 */
package org.javahispano.jfootball.framework.entity;

import org.javahispano.jfootball.framework.common.math.Vector2D;

/**
 * @author alfonso
 *
 */
public class MovingEntity extends BaseGameEntity {
	private Vector2D velocity;

	// a normalized vector pointing in the direction the entity is heading.
	private Vector2D heading;

	// a vector perpendicular to the heading vector
	private Vector2D side;

	private double mass;

	// the maximum speed this entity may travel at.
	private double maxSpeed;

	// the maximum force this entity can produce to power itself
	// (think rockets and thrust)
	private double maxForce;

	// the maximum rate (radians per second)this vehicle can rotate
	private double maxTurnRate;

	public MovingEntity(int id, Vector2D velocity, Vector2D heading,
			double mass, double maxSpeed, double maxForce, double maxTurnRate) {
		super(id);
		
		this.velocity = velocity;
		this.heading = heading;
		this.mass = mass;
		this.maxSpeed = maxSpeed;
		this.maxForce = maxForce;
		this.maxTurnRate = maxTurnRate;
	}

	/**
	 * @return the velocity
	 */
	public Vector2D getVelocity() {
		return velocity;
	}

	/**
	 * @param velocity the velocity to set
	 */
	public void setVelocity(Vector2D velocity) {
		this.velocity = velocity;
	}

	/**
	 * @return the heading
	 */
	public Vector2D getHeading() {
		return heading;
	}

	/**
	 * @param heading the heading to set
	 */
	public void setHeading(Vector2D heading) {
		this.heading = heading;
	}

	/**
	 * @return the side
	 */
	public Vector2D getSide() {
		return side;
	}

	/**
	 * @param side the side to set
	 */
	public void setSide(Vector2D side) {
		this.side = side;
	}

	/**
	 * @return the mass
	 */
	public double getMass() {
		return mass;
	}

	/**
	 * @param mass the mass to set
	 */
	public void setMass(double mass) {
		this.mass = mass;
	}

	/**
	 * @return the maxSpeed
	 */
	public double getMaxSpeed() {
		return maxSpeed;
	}

	/**
	 * @param maxSpeed the maxSpeed to set
	 */
	public void setMaxSpeed(double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	/**
	 * @return the maxForce
	 */
	public double getMaxForce() {
		return maxForce;
	}

	/**
	 * @param maxForce the maxForce to set
	 */
	public void setMaxForce(double maxForce) {
		this.maxForce = maxForce;
	}

	/**
	 * @return the maxTurnRate
	 */
	public double getMaxTurnRate() {
		return maxTurnRate;
	}

	/**
	 * @param maxTurnRate the maxTurnRate to set
	 */
	public void setMaxTurnRate(double maxTurnRate) {
		this.maxTurnRate = maxTurnRate;
	}

	
	
}
