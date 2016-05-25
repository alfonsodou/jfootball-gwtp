/**
 * 
 */
package org.javahispano.jfootball.framework.common.fsm;

import org.javahispano.jfootball.framework.entity.EntityType;

/**
 * @author alfonso
 *
 */
public class StateMachine<T> {
	private EntityType owner;
	private State<EntityType> currentState;
	private State<EntityType> previousState;
	private State<EntityType> globalState;
}
