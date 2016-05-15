package org.javahispano.jfootball.server.guice;

import org.javahispano.jfootball.shared.api.ApiPaths;

import com.arcbees.guicyresteasy.GuiceRestEasyFilterDispatcher;
import com.google.inject.servlet.ServletModule;
import com.googlecode.objectify.ObjectifyFilter;
import com.gwtplatform.dispatch.rpc.server.guice.DispatchServiceImpl;
import com.gwtplatform.dispatch.rpc.shared.ActionImpl;

public class DispatchServletModule extends ServletModule {
    @Override
    protected void configureServlets() {
		filter("/*").through(ObjectifyFilter.class);
		filter(ApiPaths.ROOT + "/*").through(
				GuiceRestEasyFilterDispatcher.class);
		
    	serve("/" + ActionImpl.DEFAULT_SERVICE_NAME + "*").with(DispatchServiceImpl.class);
    }
}

