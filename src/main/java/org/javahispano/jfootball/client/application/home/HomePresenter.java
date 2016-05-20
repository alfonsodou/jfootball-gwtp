package org.javahispano.jfootball.client.application.home;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.TextArea;
import org.gwtbootstrap3.client.ui.html.Paragraph;
import org.javahispano.jfootball.client.application.ApplicationPresenter;
import org.javahispano.jfootball.client.place.NameTokens;
import org.javahispano.jfootball.shared.dispatch.compile.CompileAction;
import org.javahispano.jfootball.shared.dispatch.compile.CompileResult;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import edu.stanford.bmir.gwtcodemirror.client.GWTCodeMirror;

public class HomePresenter extends Presenter<HomePresenter.MyView, HomePresenter.MyProxy> implements HomeUiHandlers {
	interface MyView extends View, HasUiHandlers<HomeUiHandlers> {
		Paragraph getResult();

		Button getSend();

		TextArea getTextArea();

		GWTCodeMirror getEditor();
	}

	@ProxyStandard
	@NameToken(NameTokens.HOME)
	interface MyProxy extends ProxyPlace<HomePresenter> {
	}

	private final DispatchAsync dispatcher;

	@Inject
	HomePresenter(EventBus eventBus, MyView view, MyProxy proxy, DispatchAsync dispatcher) {
		super(eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN);
		this.dispatcher = dispatcher;

		getView().setUiHandlers(this);

		final StringBuilder builder = new StringBuilder();

		builder.append("package org.javahispano.jfootball;\n\n");
		builder.append("import java.util.Random;\n\n");
		builder.append("public class B {\n");
		builder.append("	public int aleatorio() {\n");
		builder.append("		Random random = new Random();\n");
		builder.append("		return random.nextInt();\n");
		builder.append("	}\n");
		builder.append("}\n\n");
		builder.append("public class Prueba implements org.javahispano.jfootball.server.compiler.Agent {\n");
		builder.append("	public String execute() {\n");
		builder.append("		B b = new B();\n");
		builder.append("		return  \"Hola Mundo!, el número elegido es \" + Integer.toString(b.aleatorio());\n");
		builder.append("	}\n");
		builder.append("}\n");

		getView().getTextArea().setText(builder.toString());

		Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
			public void execute() {
				getView().getEditor().setValue(builder.toString());
			}
		});

	}

	@Override
	protected void onReveal() {
	}

	@Override
	public void compile(String code) {
		CompileAction compileAction = new CompileAction(code);
		callCompileAction(compileAction);
	}

	private void callCompileAction(CompileAction compileAction) {
		dispatcher.execute(compileAction, new AsyncCallback<CompileResult>() {

			@Override
			public void onFailure(Throwable caught) {
				getView().getResult().setText(caught.getMessage());
			}

			@Override
			public void onSuccess(CompileResult result) {
				getView().getResult().setText(result.getOut());
			}

		});
	}
}
