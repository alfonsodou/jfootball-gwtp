/**
 * 
 */
package org.javahispano.jfootball.server.dispatch;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.codehaus.janino.SimpleCompiler;
import org.javahispano.jfootball.server.compiler.Agent;
import org.javahispano.jfootball.shared.dispatch.compile.CompileAction;
import org.javahispano.jfootball.shared.dispatch.compile.CompileResult;

import com.google.appengine.api.quota.QuotaService;
import com.google.appengine.api.quota.QuotaServiceFactory;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

/**
 * @author alfonso
 *
 */
public class CompileHandler extends AbstractActionHandler<CompileAction, CompileResult> {
	private final GcsService gcsService = GcsServiceFactory.createGcsService(RetryParams.getDefaultInstance());
	private final Logger logger;

	@Inject
	CompileHandler(Logger logger) {
		super(CompileAction.class);

		this.logger = logger;
	}

	@Override
	public CompileResult execute(CompileAction arg0, ExecutionContext arg1) throws ActionException {
		logger.warning(arg0.getCode());
		GcsFilename filename = new GcsFilename("jfootball-130923.appspot.com", "Prueba.java");

		try {
			writeToFile(filename, arg0.getCode().getBytes());
		} catch (IOException e1) {
			e1.printStackTrace();

			return new CompileResult(e1.getMessage());
		}

		try {
			SimpleCompiler compiler = new SimpleCompiler();
			QuotaService qs = QuotaServiceFactory.getQuotaService();
			long start = qs.getCpuTimeInMegaCycles();
			compiler.setDebuggingInformation(true, true, true);
			compiler.cook(arg0.getCode());
			long end = qs.getCpuTimeInMegaCycles();
			long timeCompile = end - start;
			Class<Agent> clazz = (Class<Agent>) Class.forName("org.javahispano.jfootball.Prueba", true,
					compiler.getClassLoader());
			Agent instance = clazz.newInstance();

			start = qs.getCpuTimeInMegaCycles();
			String result = instance.execute();
			end = qs.getCpuTimeInMegaCycles();
			return new CompileResult(
					result + "\n\n :: Tiempo de compilación en CPU MegaCycles = " + Long.toString(timeCompile)
							+ "\n :: Tiempo de ejecución en CPU MegaCycles = " + Long.toString(end - start));
		} catch (Exception e1) {
			e1.printStackTrace();
			return new CompileResult(e1.getMessage());
		}

	}

	@Override
	public void undo(CompileAction arg0, CompileResult arg1, ExecutionContext arg2) throws ActionException {
	}

	private void writeToFile(GcsFilename fileName, byte[] content) throws IOException {
		GcsOutputChannel outputChannel = gcsService.createOrReplace(fileName, GcsFileOptions.getDefaultInstance());
		outputChannel.write(ByteBuffer.wrap(content));
		outputChannel.close();
	}

}
