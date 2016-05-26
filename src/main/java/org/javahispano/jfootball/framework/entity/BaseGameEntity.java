/**
 * 
 */
package org.javahispano.jfootball.framework.entity;

import org.javahispano.jfootball.framework.common.math.Vector2D;

/**
 * @author alfonso
 *
 */
public class BaseGameEntity {
	/*
	 * each entity has a unique ID
	 */
	private int id;
	/*
	 * every entity has a type associated with it
	 */
	private EntityType type;
	/*
	 * its location in the environment
	 */
	private Vector2D pos;
	/*
	 * 
	 */
	private Vector2D scale;
	/*
	 * the magnitude of this object's bounding radius
	 */
	private double boundingRadius;
	/*
	 * this is the next valid ID. Each time a BaseGameEntity is instantiated
  	 * this value is updated
	 */
	private static int  nextValidID;

	public BaseGameEntity(int id) {
		setId(id);
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
		BaseGameEntity.nextValidID = id + 1;
	}

	/**
	 * @return the type
	 */
	public EntityType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(EntityType type) {
		this.type = type;
	}

	/**
	 * @return the pos
	 */
	public Vector2D getPos() {
		return pos;
	}

	/**
	 * @param pos the pos to set
	 */
	public void setPos(Vector2D pos) {
		this.pos = pos;
	}

	/**
	 * @return the scale
	 */
	public Vector2D getScale() {
		return scale;
	}

	/**
	 * @param scale the scale to set
	 */
	public void setScale(Vector2D scale) {
		this.scale = scale;
	}

	/**
	 * @return the boundingRadius
	 */
	public double getBoundingRadius() {
		return boundingRadius;
	}

	/**
	 * @param boundingRadius the boundingRadius to set
	 */
	public void setBoundingRadius(double boundingRadius) {
		this.boundingRadius = boundingRadius;
	}

	/**
	 * @return the nextValidID
	 */
	public static int getNextValidID() {
		return nextValidID;
	}

	/**
	 * @param nextValidID the nextValidID to set
	 */
	public static void setNextValidID(int nextValidID) {
		BaseGameEntity.nextValidID = nextValidID;
	}
	
	
}
