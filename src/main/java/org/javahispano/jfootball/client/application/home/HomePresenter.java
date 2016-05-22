package org.javahispano.jfootball.client.application.home;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.html.Paragraph;
import org.javahispano.jfootball.client.application.ApplicationPresenter;
import org.javahispano.jfootball.client.application.widget.gwtcodemirror.client.GWTCodeMirror;
import org.javahispano.jfootball.client.place.NameTokens;
import org.javahispano.jfootball.shared.dispatch.compile.CompileAction;
import org.javahispano.jfootball.shared.dispatch.compile.CompileResult;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

public class HomePresenter extends Presenter<HomePresenter.MyView, HomePresenter.MyProxy> implements HomeUiHandlers {
	interface MyView extends View, HasUiHandlers<HomeUiHandlers> {
		Paragraph getResult();

		Button getSend();
		
		FlowPanel getFlowPanel();
	}

	@ProxyStandard
	@NameToken(NameTokens.HOME)
	interface MyProxy extends ProxyPlace<HomePresenter> {
	}

	private final DispatchAsync dispatcher;
	private GWTCodeMirror gwtCodeMirror;

	@Inject
	HomePresenter(EventBus eventBus, MyView view, MyProxy proxy, DispatchAsync dispatcher) {
		super(eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN);
		this.dispatcher = dispatcher;
		
		getView().setUiHandlers(this);
	}

	@Override
	protected void onReveal() {

		
		Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
			public void execute() {
				showCode();
			}
		});
	}
	
	private void showCode() {
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
		
		gwtCodeMirror = new GWTCodeMirror("text/x-java", "eclipse");
		getView().getFlowPanel().add(gwtCodeMirror);
		gwtCodeMirror.setValue(builder.toString());
	}

	@Override
	public void compile() {
		CompileAction compileAction = new CompileAction(gwtCodeMirror.getValue());
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
