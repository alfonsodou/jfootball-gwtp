/**
 * 
 */
package org.javahispano.jfootball.server.dispatch;

import org.javahispano.jfootball.shared.dispatch.compile.CompileAction;

import com.gwtplatform.dispatch.rpc.server.guice.HandlerModule;

/**
 * @author alfonso
 *
 */
public class DispatchModule extends HandlerModule {

	@Override
	protected void configureHandlers() {
		install(new com.gwtplatform.dispatch.rpc.server.guice.DispatchModule());
		
		bindHandler(CompileAction.class, CompileHandler.class);

	}

}
