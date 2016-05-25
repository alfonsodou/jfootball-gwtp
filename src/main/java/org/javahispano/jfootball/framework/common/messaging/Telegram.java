/**
 * 
 */
package org.javahispano.jfootball.framework.common.messaging;

/**
 * @author alfonso
 *
 */
public class Telegram {
	private int sender;
	private int receiver;
	private int msg;
	private double dispatchTime;

	public Telegram() {
		this.sender = -1;
		this.receiver = -1;
		this.msg = -1;
		this.dispatchTime = -1;
	}
	
	public Telegram(int sender, int receiver, int msg, double dispatchTime) {
		this.sender = sender;
		this.receiver = receiver;
		this.msg = msg;
		this.dispatchTime = dispatchTime;
	}

	/**
	 * @return the sender
	 */
	public int getSender() {
		return sender;
	}

	/**
	 * @param sender the sender to set
	 */
	public void setSender(int sender) {
		this.sender = sender;
	}

	/**
	 * @return the receiver
	 */
	public int getReceiver() {
		return receiver;
	}

	/**
	 * @param receiver the receiver to set
	 */
	public void setReceiver(int receiver) {
		this.receiver = receiver;
	}

	/**
	 * @return the msg
	 */
	public int getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(int msg) {
		this.msg = msg;
	}

	/**
	 * @return the dispatchTime
	 */
	public double getDispatchTime() {
		return dispatchTime;
	}

	/**
	 * @param dispatchTime the dispatchTime to set
	 */
	public void setDispatchTime(double dispatchTime) {
		this.dispatchTime = dispatchTime;
	}
	
	
}
