package org.javahispano.jfootball.client.application.home;

import javax.inject.Inject;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.TextArea;
import org.gwtbootstrap3.client.ui.html.Paragraph;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import edu.stanford.bmir.gwtcodemirror.client.GWTCodeMirror;

public class HomeView extends ViewWithUiHandlers<HomeUiHandlers> implements HomePresenter.MyView {
	@UiField
	Paragraph result;
	@UiField
	Button send;
	@UiField
	TextArea formCode;
	@UiField
	GWTCodeMirror editor;

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

	public void processCompile() {
		getUiHandlers().compile(formCode.getText());
	}

	@Override
	public TextArea getTextArea() {
		return formCode;
	}

	@Override
	public GWTCodeMirror getEditor() {
		return editor;
	}

}
