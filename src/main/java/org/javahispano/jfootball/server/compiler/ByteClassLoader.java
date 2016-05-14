/**
 * 
 */
package org.javahispano.jfootball.server.compiler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author alfonso
 *
 */
class ByteClassLoader extends ClassLoader {
	private Map<String, byte[]> classMap;

	public ByteClassLoader() {
		super();
		this.classMap = new HashMap<String, byte[]>();
	}
	public ByteClassLoader(Map<String, byte[]> classMap) {
		super();
		this.classMap = classMap;
	}

	protected Class<?> findClass(String name) throws ClassNotFoundException {
		byte[] bytes = classMap.get(name);
		if (bytes == null) {
			return super.findClass(name);
		} else {
			return defineClass(name, bytes, 0, bytes.length);
		}
	}
}
