/**
 * 
 */
package org.javahispano.jfootball.server.compiler;

/**
 * @author adou
 *
 */

import org.apache.commons.jci.compilers.CompilationResult;
import org.apache.commons.jci.compilers.EclipseJavaCompiler;
import org.apache.commons.jci.compilers.JavaCompilerSettings;

public class MyCompiler {
	private EclipseJavaCompiler eclipseJavaCompiler;
	private JavaCompilerSettings javaCompilerSettings;
	private ByteClassLoader byteClassLoader;
	private MyResourceReader resourceReader;
	private MyResourceStore resourceStore;
	private CompilationResult compilationResult;

	public MyCompiler() {
		eclipseJavaCompiler = new EclipseJavaCompiler();
		javaCompilerSettings = eclipseJavaCompiler.createDefaultSettings();
		byteClassLoader = new ByteClassLoader();
		resourceReader = new MyResourceReader();
		resourceStore = new MyResourceStore();
	}

	public void compile(String[] resourceFiles) {
		compilationResult = eclipseJavaCompiler.compile(resourceFiles,
				resourceReader, resourceStore, byteClassLoader,
				javaCompilerSettings);
	}

	// Ejemplo de llamada
	// compile("public class Test { public static void test() { System.out.println(\"Hello, world.\"); }}",
	// "Test.java");
}