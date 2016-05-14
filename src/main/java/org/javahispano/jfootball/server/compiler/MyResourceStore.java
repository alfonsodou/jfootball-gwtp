/**
 * 
 */
package org.javahispano.jfootball.server.compiler;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.apache.commons.jci.stores.ResourceStore;

import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsInputChannel;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;

/**
 * @author alfonso
 *
 */
public class MyResourceStore implements ResourceStore {
	private final GcsService gcsService = GcsServiceFactory
			.createGcsService(RetryParams.getDefaultInstance());

	@Override
	public byte[] read(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void write(String arg0, byte[] arg1) {
		// TODO Auto-generated method stub

	}

	private void writeToFile(GcsFilename fileName, byte[] content)
			throws IOException {
		GcsOutputChannel outputChannel = gcsService.createOrReplace(fileName,
				GcsFileOptions.getDefaultInstance());
		outputChannel.write(ByteBuffer.wrap(content));
		outputChannel.close();
	}

	private byte[] readFile(GcsFilename fileName) {
		int fileSize;
		ByteBuffer result = null;

		try {
			fileSize = (int) gcsService.getMetadata(fileName).getLength();
			result = ByteBuffer.allocate(fileSize);

			GcsInputChannel readChannel = gcsService.openReadChannel(fileName,
					0);
			readChannel.read(result);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result.array();
	}
}
