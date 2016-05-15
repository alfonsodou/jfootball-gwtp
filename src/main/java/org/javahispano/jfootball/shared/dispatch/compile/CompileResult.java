/**
 * 
 */
package org.javahispano.jfootball.shared.dispatch.compile;

import com.gwtplatform.dispatch.rpc.shared.Result;

/**
 * @author alfonso
 *
 */
public class CompileResult implements Result {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String out;
	
	protected CompileResult() {
		this.out = new String();
	}

	public CompileResult(String out) {
		this.out = out;
	}

	/**
	 * @return the out
	 */
	public String getOut() {
		return out;
	}

	/**
	 * @param out the out to set
	 */
	public void setOut(String out) {
		this.out = out;
	}
}
