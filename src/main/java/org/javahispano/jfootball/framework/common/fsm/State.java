/**
 * 
 */
package org.javahispano.jfootball.framework.common.fsm;

import org.javahispano.jfootball.framework.common.messaging.Telegram;

/**
 * @author alfonso
 *
 */
public abstract class State<T> {
	public void Enter(Class<T> clazz) {};
	public void Execute(Class<T> clazz) {};
	public void Exit(Class<T> clazz) {};
	public boolean onMesssage(Class<T> clazz, Telegram telegram) { return false;};
}
