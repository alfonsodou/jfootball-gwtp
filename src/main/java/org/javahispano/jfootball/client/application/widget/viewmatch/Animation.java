/**
 * 
 */
package org.javahispano.jfootball.client.application.widget.viewmatch;


import com.akjava.gwt.three.client.js.renderers.WebGLRenderer;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author adou
 *
 */
public interface Animation {
	public void start(WebGLRenderer renderer,int width,int height,FocusPanel panel);
	public void stop();
	public void clearHandlerRegistration();
	public String getName();
	public void startTimer(Timer timer);
	public Widget getControler();
}
