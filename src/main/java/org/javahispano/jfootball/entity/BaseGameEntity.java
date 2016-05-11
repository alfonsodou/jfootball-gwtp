/**
 * 
 */
package org.javahispano.jfootball.entity;

import org.javahispano.jfootball.common.math.Vector2D;

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

}
