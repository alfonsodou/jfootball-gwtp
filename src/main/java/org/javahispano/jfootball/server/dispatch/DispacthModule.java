/**
 * 
 */
package org.javahispano.jfootball.server.dispatch;

import com.gwtplatform.dispatch.rpc.server.guice.HandlerModule;

/**
 * @author alfonso
 *
 */
public class DispacthModule extends HandlerModule {

	@Override
	protected void configureHandlers() {
		install(new com.gwtplatform.dispatch.rpc.server.guice.DispatchModule());

	}

}
