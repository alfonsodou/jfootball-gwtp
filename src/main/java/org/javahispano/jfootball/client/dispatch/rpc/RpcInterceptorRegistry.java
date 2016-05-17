package org.javahispano.jfootball.client.dispatch.rpc;

import javax.inject.Inject;

import com.gwtplatform.dispatch.rpc.client.interceptor.DefaultRpcInterceptorRegistry;

public class RpcInterceptorRegistry extends DefaultRpcInterceptorRegistry {
	@Inject
	RpcInterceptorRegistry(CompileInterceptor compileInterceptor) {
		register(compileInterceptor);
	}
}
