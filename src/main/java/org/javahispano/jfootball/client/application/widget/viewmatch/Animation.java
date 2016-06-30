/**
 * 
 */
package org.javahispano.jfootball.client.application.widget.viewmatch;

import com.google.gwt.canvas.client.Canvas;
import com.shc.webgl4j.client.WebGL10;
import com.shc.webgl4j.client.WebGLContext;

/**
 * @author adou
 *
 */
/**
 * The abstract class that represents an example. Contains the context, the canvas, and a simple animation loop.
 *
 * @author Sri Harsha Chilakapati
 */
public abstract class Animation
{
    private WebGLContext context;
    private Canvas       canvas;

    public Animation(Canvas canvas)
    {
        this(canvas, WebGL10.createContext(canvas));
    }

    public Animation(Canvas canvas, WebGLContext context)
    {
        this.canvas = canvas;
        this.setContext(context);
    }

    /**
     * Initializes the WebGL resources, and also the example.
     */
    public abstract void init();

    /**
     * Called every frame to draw the example onto the canvas.
     */
    public abstract void render();

	public WebGLContext getContext() {
		return context;
	}

	public void setContext(WebGLContext context) {
		this.context = context;
	}

	/**
	 * @return the canvas
	 */
	public Canvas getCanvas() {
		return canvas;
	}

	/**
	 * @param canvas the canvas to set
	 */
	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}
	
	
}
