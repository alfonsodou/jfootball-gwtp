package org.javahispano.jfootball.client.application.home;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.html.Paragraph;
import org.javahispano.jfootball.client.application.ApplicationPresenter;
import org.javahispano.jfootball.client.place.NameTokens;
import org.javahispano.jfootball.shared.dispatch.compile.CompileAction;
import org.javahispano.jfootball.shared.dispatch.compile.CompileResult;

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

public class HomePresenter extends Presenter<HomePresenter.MyView, HomePresenter.MyProxy> implements HomeUiHandlers {
	interface MyView extends View, HasUiHandlers<HomeUiHandlers> {
		Paragraph getResult();

		Button getSend();
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
