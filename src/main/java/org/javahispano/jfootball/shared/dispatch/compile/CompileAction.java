/**
 * 
 */
package org.javahispano.jfootball.shared.dispatch.compile;

import com.gwtplatform.dispatch.rpc.shared.ActionImpl;

/**
 * @author alfonso
 *
 */
public class CompileAction extends ActionImpl<CompileResult> {
	private String code;

	protected CompileAction() {
		this.code = new String();
	}
	
	public CompileAction(String code) {
		this.code = code;
	}
	
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public boolean isSecured() {
		return false;
	}
}
