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
		byteClassLoader = new ByteClassLoader(this.getClass().getClassLoader());
		resourceReader = new MyResourceReader();
		resourceStore = new MyResourceStore();
	}

	public void compile(String[] resourceFiles) {
		compilationResult = eclipseJavaCompiler.compile(resourceFiles, resourceReader, resourceStore, byteClassLoader,
				javaCompilerSettings);
	}

	/**
	 * @return the eclipseJavaCompiler
	 */
	public EclipseJavaCompiler getEclipseJavaCompiler() {
		return eclipseJavaCompiler;
	}

	/**
	 * @param eclipseJavaCompiler
	 *            the eclipseJavaCompiler to set
	 */
	public void setEclipseJavaCompiler(EclipseJavaCompiler eclipseJavaCompiler) {
		this.eclipseJavaCompiler = eclipseJavaCompiler;
	}

	/**
	 * @return the javaCompilerSettings
	 */
	public JavaCompilerSettings getJavaCompilerSettings() {
		return javaCompilerSettings;
	}

	/**
	 * @param javaCompilerSettings
	 *            the javaCompilerSettings to set
	 */
	public void setJavaCompilerSettings(JavaCompilerSettings javaCompilerSettings) {
		this.javaCompilerSettings = javaCompilerSettings;
	}

	/**
	 * @return the byteClassLoader
	 */
	public ByteClassLoader getByteClassLoader() {
		return byteClassLoader;
	}

	/**
	 * @param byteClassLoader
	 *            the byteClassLoader to set
	 */
	public void setByteClassLoader(ByteClassLoader byteClassLoader) {
		this.byteClassLoader = byteClassLoader;
	}

	/**
	 * @return the resourceReader
	 */
	public MyResourceReader getResourceReader() {
		return resourceReader;
	}

	/**
	 * @param resourceReader
	 *            the resourceReader to set
	 */
	public void setResourceReader(MyResourceReader resourceReader) {
		this.resourceReader = resourceReader;
	}

	/**
	 * @return the resourceStore
	 */
	public MyResourceStore getResourceStore() {
		return resourceStore;
	}

	/**
	 * @param resourceStore
	 *            the resourceStore to set
	 */
	public void setResourceStore(MyResourceStore resourceStore) {
		this.resourceStore = resourceStore;
	}

	/**
	 * @return the compilationResult
	 */
	public CompilationResult getCompilationResult() {
		return compilationResult;
	}

	/**
	 * @param compilationResult
	 *            the compilationResult to set
	 */
	public void setCompilationResult(CompilationResult compilationResult) {
		this.compilationResult = compilationResult;
	}

	// Ejemplo de llamada
	// compile("public class Test { public static void test() {
	// System.out.println(\"Hello, world.\"); }}",
	// "Test.java");
}