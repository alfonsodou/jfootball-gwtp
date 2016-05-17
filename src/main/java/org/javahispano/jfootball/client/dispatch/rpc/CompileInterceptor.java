/**
 * 
 */
package org.javahispano.jfootball.client.dispatch.rpc;

import org.javahispano.jfootball.shared.dispatch.compile.CompileAction;
import org.javahispano.jfootball.shared.dispatch.compile.CompileResult;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtplatform.dispatch.client.interceptor.ExecuteCommand;
import com.gwtplatform.dispatch.rpc.client.interceptor.AbstractRpcInterceptor;
import com.gwtplatform.dispatch.rpc.client.interceptor.UndoCommand;
import com.gwtplatform.dispatch.shared.DispatchRequest;

/**
 * @author adou
 *
 */
public class CompileInterceptor extends AbstractRpcInterceptor<CompileAction, CompileResult> {

	CompileInterceptor() {
		super(CompileAction.class);
	}

	@Override
	public DispatchRequest execute(CompileAction action, AsyncCallback<CompileResult> resultCallback,
			ExecuteCommand<CompileAction, CompileResult> executeCommand) {
		return executeCommand.execute(action, resultCallback);
	}

	@Override
	public DispatchRequest undo(CompileAction action, CompileResult result, AsyncCallback<Void> callback,
			UndoCommand<CompileAction, CompileResult> undoCommand) {
		return undoCommand.undo(action, result, callback);
	}

}
