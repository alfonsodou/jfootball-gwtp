package org.javahispano.jfootball.client.application.home;

import javax.inject.Inject;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.html.Paragraph;
import org.parallax3d.parallax.platforms.gwt.GwtRenderingContext;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class HomeView extends ViewWithUiHandlers<HomeUiHandlers> implements
		HomePresenter.MyView {
	@UiField
	Paragraph result;
	@UiField
	Button send;
	@UiField
	FlowPanel flowPanel;
	@UiField
	SimpleLayoutPanel content;

	interface Binder extends UiBinder<Widget, HomeView> {
	}

	public interface PanelReady {
		void onRenderingReady(GwtRenderingContext rendering);
	}

	public GwtRenderingContext rendering;

	public PanelReady renderingReady;
	
	@Inject
	HomeView(Binder uiBinder) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public Paragraph getResult() {
		return result;
	}

	@Override
	public Button getSend() {
		return send;
	}

	@UiHandler("send")
	void onSendClicked(ClickEvent event) {
		result.clear();
		result.setText("");
		processCompile();
	}

	public void processCompile() {
		getUiHandlers().compile();
	}

	@Override
	public FlowPanel getFlowPanel() {
		return flowPanel;
	}

	@Override
	public SimpleLayoutPanel getRenderingPanel() {
		return content;
	}

	public void addGwtReadyListener(PanelReady gwtReady) {
		this.renderingReady = gwtReady;
	}

	@Override
	public GwtRenderingContext getRendering() {
		return rendering;
	}

	@Override
	public PanelReady getRenderingReady() {
		return renderingReady;
	}

	@Override
	public void setRendering(GwtRenderingContext rendering) {
		this.rendering = rendering;
	}

}
