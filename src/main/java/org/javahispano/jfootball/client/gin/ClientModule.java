package org.javahispano.jfootball.client.gin;

import org.javahispano.jfootball.client.application.ApplicationModule;
import org.javahispano.jfootball.client.dispatch.rpc.AppRpcDispatchHooks;
import org.javahispano.jfootball.client.dispatch.rpc.RpcInterceptorRegistry;
import org.javahispano.jfootball.client.place.NameTokens;
import org.javahispano.jfootball.client.resources.ResourceLoader;

import com.gwtplatform.dispatch.rpc.client.gin.RpcDispatchAsyncModule;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;

public class ClientModule extends AbstractPresenterModule {
	@Override
	protected void configure() {
		install(new DefaultModule.Builder().defaultPlace(NameTokens.HOME).errorPlace(NameTokens.HOME)
				.unauthorizedPlace(NameTokens.HOME).build());

		install(new RpcDispatchAsyncModule.Builder().dispatchHooks(AppRpcDispatchHooks.class)
				.interceptorRegistry(RpcInterceptorRegistry.class).build());

		install(new ApplicationModule());

		bind(ResourceLoader.class).asEagerSingleton();
	}
}
