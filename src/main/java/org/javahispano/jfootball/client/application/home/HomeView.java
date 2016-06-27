package org.javahispano.jfootball.client.application.home;

import javax.inject.Inject;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.html.Paragraph;
import org.javahispano.jfootball.client.application.widget.viewmatch.MainWidget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.FlowPanel;
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
	Button start;
	@UiField
	MainWidget mainWidget;


	interface Binder extends UiBinder<Widget, HomeView> {
	}
	
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
	
	@UiHandler("start")
	void onStartClicked(ClickEvent event) {
		processStart();
	}

	public void processCompile() {
		getUiHandlers().compile();
	}
	
	public void processStart() {
		getUiHandlers().startAnimation();
	}

	@Override
	public FlowPanel getFlowPanel() {
		return flowPanel;
	}

	@Override
	public MainWidget getMainWidget() {
		return mainWidget;
	}


}
