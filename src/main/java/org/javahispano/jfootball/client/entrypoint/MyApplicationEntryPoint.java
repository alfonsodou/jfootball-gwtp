/**
 * 
 */
package org.javahispano.jfootball.client.entrypoint;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.gwtplatform.mvp.client.ApplicationController;

/**
 * @author alfonso
 *
 */
public class MyApplicationEntryPoint implements EntryPoint {
    private static final ApplicationController CONTROLLER =
            GWT.create(ApplicationController.class);
    
    @Override
    public void onModuleLoad() {
        CONTROLLER.init();
    }

}
