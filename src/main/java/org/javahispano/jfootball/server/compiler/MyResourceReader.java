/**
 * 
 */
package org.javahispano.jfootball.server.compiler;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.apache.commons.jci.readers.ResourceReader;

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
public class MyResourceReader implements ResourceReader {
	private final GcsService gcsService = GcsServiceFactory.createGcsService(RetryParams.getDefaultInstance());

	@Override
	public byte[] getBytes(String arg0) {
		GcsFilename filename = new GcsFilename("jfootball-130923.appspot.com", arg0);
		return readFile(filename);
	}

	@Override
	public boolean isAvailable(String arg0) {
		return true;
	}

	private void writeToFile(GcsFilename fileName, byte[] content) throws IOException {
		GcsOutputChannel outputChannel = gcsService.createOrReplace(fileName, GcsFileOptions.getDefaultInstance());
		outputChannel.write(ByteBuffer.wrap(content));
		outputChannel.close();
	}

	private byte[] readFile(GcsFilename fileName) {
		int fileSize;
		ByteBuffer result = null;

		try {
			fileSize = (int) gcsService.getMetadata(fileName).getLength();
			result = ByteBuffer.allocate(fileSize);

			GcsInputChannel readChannel = gcsService.openReadChannel(fileName, 0);
			readChannel.read(result);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result.array();
	}
}
