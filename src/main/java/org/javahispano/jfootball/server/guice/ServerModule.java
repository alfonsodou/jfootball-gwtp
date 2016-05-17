package org.javahispano.jfootball.server.guice;

import javax.inject.Singleton;

import org.javahispano.jfootball.server.dispatch.DispatchModule;

import com.google.inject.AbstractModule;
import com.googlecode.objectify.ObjectifyFilter;

public class ServerModule extends AbstractModule {
    @Override
    protected void configure() {
    	install(new DispatchModule());
    	
    	bind(ObjectifyFilter.class).in(Singleton.class);
    }
}
