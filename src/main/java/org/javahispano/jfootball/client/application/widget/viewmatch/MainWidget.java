/**
 * 
 */
package org.javahispano.jfootball.client.application.widget.viewmatch;

import com.akjava.gwt.three.client.js.THREE;
import com.akjava.gwt.three.client.js.renderers.WebGLRenderer;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author adou
 *
 */
public class MainWidget extends Composite {

	private static MainWidgetUiBinder uiBinder = GWT.create(MainWidgetUiBinder.class);

	interface MainWidgetUiBinder extends UiBinder<Widget, MainWidget> {
	}

	private WebGLRenderer renderer;
	private int width = 500, height = 500;
	private FocusPanel focusPanel;
	private MyAnimation myAnimation = new MyAnimation();

	@UiField
	VerticalPanel main;

	public MainWidget() {
		initWidget(uiBinder.createAndBindUi(this));

		renderer = THREE.WebGLRenderer();
		if (renderer != null) {
			renderer.gwtSetType("webgl");
			renderer.setSize(width, height);
			HTMLPanel div = new HTMLPanel("");
			div.getElement().appendChild(renderer.getDomElement());
			focusPanel = new FocusPanel();
			focusPanel.add(div);
		}

		getMain().add(focusPanel);
	}

	public VerticalPanel getMain() {
		return main;
	}

	public void stop() {
		myAnimation.stop();
	}

	public void startDemo() {
		stop();
		myAnimation.start(renderer, width, height, focusPanel);
	}

}
