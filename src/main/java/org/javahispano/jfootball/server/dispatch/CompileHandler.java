/**
 * 
 */
package org.javahispano.jfootball.server.dispatch;


import javax.inject.Inject;

import org.javahispano.jfootball.shared.dispatch.compile.CompileAction;
import org.javahispano.jfootball.shared.dispatch.compile.CompileResult;

import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

/**
 * @author alfonso
 *
 */
public class CompileHandler extends AbstractActionHandler<CompileAction, CompileResult> {
	@Inject
	CompileHandler() {
		super(CompileAction.class);
	}

	@Override
	public CompileResult execute(CompileAction arg0, ExecutionContext arg1)
			throws ActionException {
		return null;
	}

	@Override
	public void undo(CompileAction arg0, CompileResult arg1,
			ExecutionContext arg2) throws ActionException {
	}

}
