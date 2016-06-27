/**
 * 
 */
package org.javahispano.jfootball.client.application.widget.viewmatch;

import org.gwtbootstrap3.client.ui.Button;
import org.parallax3d.parallax.Animation;
import org.parallax3d.parallax.Log;
import org.parallax3d.parallax.platforms.gwt.GwtRenderingContext;
import org.parallax3d.parallax.system.AnimationReadyListener;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author alfonso
 *
 */
public class AnimationWidget extends ResizeComposite implements
		AnimationReadyListener {

	private static AnimationWidgetUiBinder uiBinder = GWT
			.create(AnimationWidgetUiBinder.class);

	interface AnimationWidgetUiBinder extends UiBinder<Widget, AnimationWidget> {
	}

	public interface PanelReady {
		void onRenderingReady(GwtRenderingContext rendering);
	}

	/**
	 * Main panel where will be RenderingPanel located
	 */
	@UiField
	SimplePanel content;

	GwtRenderingContext rendering;

	PanelReady renderingReady;

	public AnimationWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	protected void onLoad() {
		if (rendering == null) {
			Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
				@Override
				public void execute() {
					try {

						rendering = new GwtRenderingContext(
								AnimationWidget.this.content);

						rendering
								.addAnimationReadyListener(AnimationWidget.this);

						if (renderingReady != null)
							renderingReady.onRenderingReady(rendering);

					} catch (Throwable e) {
						String msg = "Sorry, your browser doesn't seem to support WebGL";
						Log.error("setRendering: " + msg, e);
						Window.alert(msg);
					}
				}
			});
		} else {
			if (renderingReady != null)
				renderingReady.onRenderingReady(rendering);
		}

		super.onLoad();
	}

	public void addGwtReadyListener(PanelReady gwtReady) {
		this.renderingReady = gwtReady;
	}

	@Override
	public void onAnimationReady(Animation animation) {
		final Button switchAnimation = new Button("Pause");
		final Button switchFullScreen = new Button("Fullscreen");

		switchAnimation.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (rendering.isRun()) {
					rendering.stop();
					switchAnimation.setText("Resume");
				} else {
					rendering.run();
					switchAnimation.setText("Pause");
				}
			}
		});

		switchFullScreen.setEnabled(rendering.supportsDisplayModeChange());
		switchFullScreen.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				rendering.setFullscreen();
			}
		});
	}
	
	public GwtRenderingContext getGwtRenderingContext() {
		return rendering;
	}
}
