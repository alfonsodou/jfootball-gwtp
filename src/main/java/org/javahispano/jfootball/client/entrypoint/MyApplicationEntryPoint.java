/**
 * 
 */
package org.javahispano.jfootball.client.entrypoint;

import org.parallax3d.parallax.Parallax;
import org.parallax3d.parallax.Parallax.ParallaxListener;
import org.parallax3d.parallax.platforms.gwt.GwtParallax;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.gwtplatform.mvp.client.ApplicationController;

/**
 * @author alfonso
 *
 */
public class MyApplicationEntryPoint implements EntryPoint, ParallaxListener {
    private static final ApplicationController CONTROLLER =
            GWT.create(ApplicationController.class);
    
    @Override
    public void onModuleLoad() {
    	GwtParallax.init( this );
    	
        CONTROLLER.init();
    }

	@Override
	public void onParallaxApplicationReady(Parallax instance) {
		//getView().getRendering().setAnimation(new MyAnimation());		
	}
}
