package org.javahispano.jfootball.client.dispatch.rpc;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.gwtplatform.dispatch.rpc.client.RpcDispatchHooks;
import com.gwtplatform.dispatch.rpc.shared.Action;
import com.gwtplatform.dispatch.rpc.shared.Result;

public class AppRpcDispatchHooks implements RpcDispatchHooks {
    private static final Logger logger = Logger.getLogger(AppRpcDispatchHooks.class.getName());

    @Override
    public <R extends Result> void onExecute(Action<R> action, boolean undo) {
        logger.log(Level.INFO, "Executing rpc dispatch " + action.getServiceName()
                + " resource action (undo: " + String.valueOf(undo) + ")");
    }

    @Override
    public <R extends Result> void onSuccess(Action<R> action, R result, boolean undo) {
        logger.log(Level.INFO, "Successfully executed " + action.getServiceName()
                + (" undo: " + String.valueOf(undo) + ")"));
    }

    @Override
    public <R extends Result> void onFailure(Action<R> action, Throwable caught, boolean undo) {
        logger.log(Level.INFO, "Failed to executed " + action.getServiceName() + " "
                + caught.getMessage() + (" undo: " + String.valueOf(undo) + ")"));
    }
}
