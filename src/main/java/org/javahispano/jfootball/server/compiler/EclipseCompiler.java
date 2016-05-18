/**
 * 
 */
package org.javahispano.jfootball.server.compiler;

/**
 * @author adou
 *
 */
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.internal.compiler.ClassFile;
import org.eclipse.jdt.internal.compiler.CompilationResult;
import org.eclipse.jdt.internal.compiler.Compiler;
import org.eclipse.jdt.internal.compiler.DefaultErrorHandlingPolicies;
import org.eclipse.jdt.internal.compiler.ICompilerRequestor;
import org.eclipse.jdt.internal.compiler.IErrorHandlingPolicy;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileReader;
import org.eclipse.jdt.internal.compiler.env.ICompilationUnit;
import org.eclipse.jdt.internal.compiler.env.INameEnvironment;
import org.eclipse.jdt.internal.compiler.env.NameEnvironmentAnswer;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.problem.DefaultProblemFactory;

/**
 * This class copiles a set of java sources using the Eclipse EJC compiler.
 * The nice thing about this approach is that it compiles the sources from
 * memory so no disk access and it works correctly under Java Web Start.
 */
public class EclipseCompiler
{
	/**
	 * The class loader which will be used to load the classes once they
	 * have been compiled
	 */
	private static EclipseClassLoader _classloader = new EclipseClassLoader();

	public EclipseClassLoader getEclipseClassLoader() {
		return _classloader;
	}
	/**
	 * Compiles a set of Java sources using the Eclipse Java compiler.
   	 *
	 * @param sources a map from fully qualified classname to java source
	 * @throws Exception if an error occurs during compilation that would stop
	 *         us loading and using the compiled class (i.e. compiler warnings,
	 *         such as deprecation, are ignored)
	*/
	public void compile(final Map<String,String> sources) throws Exception
	{
	    // a store for any problems that occur douring compilation
	    final Map<String,List<IProblem>> problems = new HashMap<String,List<IProblem>>();

	    // A class representing a file to be compiled.  An instance of this class
	    // is returned by the name environment when one of the classes given in the
	    // sources map is requested.
	    class CompilationUnit implements ICompilationUnit
	    {
	    	String _className;

	    	CompilationUnit(String className) { _className = className; }

	    	public char[] getFileName() { return _className.toCharArray(); }

	    	public char[] getContents() { return sources.get(_className).toCharArray(); }

	    	/**
	    	 * Returns the unqualified name of the class defined by this compilation unit.
	    	 * @return the unqualified name of the class (i.e. no package information)
	    	 */
	    	public char[] getMainTypeName()
	    	{
	    		int dot = _className.lastIndexOf('.');
	    		if (dot > 0) return _className.substring(dot + 1).toCharArray();
	    		return _className.toCharArray();
	    	}

	    	/**
	    	 * Returns the package name for the class defined by this compilation
	    	 * unit.  For example, if this unit defines java.lang.String,
	    	 * ["java".toCharArray(), "lang".toCharArray()] would be returned.
	    	 * @return the package in which this class is defined
	    	 */
	    	public char[][] getPackageName()
	    	{
	    		StringTokenizer izer = new StringTokenizer(_className, ".");
	    		char[][] result = new char[izer.countTokens()-1][];
	    		for (int i = 0; i < result.length; i++)
	    		{
	    			String tok = izer.nextToken();
	    			result[i] = tok.toCharArray();
	    		}
	    		return result;
	    	}

			@Override
			public boolean ignoreOptionalProblems() {
				// TODO Auto-generated method stub
				return false;
			}
		}

	    // A name enviroment is usedto maps class names to eclipse objects.
	    // If the class name is one of those given in the sources map, the
	    // appropriate CompilationUnit is created.  Otherwise, we try to
	    // load the requested class from the standard classloader and return
	    // a ClassFileReader for that class.
	    final INameEnvironment env = new INameEnvironment()
	    {
	    	/**
	    	 * Tries to find the class or source file defined by the given type
	    	 * name.  We construct a string from the compound name (e.g. ["java",
	    	 * "lang", "String"] becomes "java.lang.String") and search using that.
	    	 */
	    	public NameEnvironmentAnswer findType(char[][] compoundTypeName)
	    	{
	    		return findType(flatten(compoundTypeName));
	    	}

	    	/**
	    	 * Tries to find the class or source file defined by the given type
	    	 * name.  We construct a string from the compound name (e.g. "String",
	    	 * ["java", "lang"] becomes "java.lang.String") and search using that.
	    	 */
	    	public NameEnvironmentAnswer findType(char[] typeName, char[][] packageName)
	    	{
	    		String result = flatten(packageName);
	    		if (result.length() > 0) result += ".";
	    		result += new String(typeName);

	    		return findType(result);
	    	}

	    	/**
	    	 * Find the type referenced by the given name.
	    	 */
	    	private NameEnvironmentAnswer findType(String className)
	    	{
	    		try
	    		{
	    			if (sources.containsKey(className))
	    			{
						// if it's one of the sources we were given to compile,
						// return that as a CompilationUnit.
						return new NameEnvironmentAnswer(new CompilationUnit(className), null);
	    			}

					/** otherwise, try and load the class from the normal classloader. **/

	    			//convert the qualified class name into a package
	    			//directory structure and class filename
	    			String resourceName = className.replace('.', '/') + ".class";

	    			//get the resource using the normal classloader
	    			InputStream is = _classloader.getResourceAsStream(resourceName);

	    			if (is != null)
	    			{
	    				//if we found the file then load it's bytes into memory

	    				//create an 8KB buffer to speed up reading the file
	    				//TODO: Is this the most efficient buffer size?
	    				byte[] buf = new byte[8192];

	    				//Create a writer we can use to fill a byte array with the class
	    				//bytes read from the resource
	    				ByteArrayOutputStream baos = new ByteArrayOutputStream(buf.length);

	    				//the count of how many bytes we have read this iteration
	    				int count;
	    				while ((count = is.read(buf, 0, buf.length)) > 0)
	    				{
	    					//read in bytes from the resource and write them into memory
	    					baos.write(buf, 0, count);
	    				}

	    				//make sure we have fully read/storred all the bytes of the class
	    				baos.flush();

	    				//Store the bytes we have read in along with the class name
	    				ClassFileReader classFileReader = new ClassFileReader(baos.toByteArray(), className.toCharArray(), true);

	    				//return the loaded class to the caller
	    				return new NameEnvironmentAnswer(classFileReader, null);
	    			}
	    		}
	    		catch (IOException exc)
	    		{
	    			System.err.println("Compilation error");
	    			exc.printStackTrace();
	    		}
	    		catch (org.eclipse.jdt.internal.compiler.classfmt.ClassFormatException exc)
	    		{
	    			System.err.println("Compilation error");
	    			exc.printStackTrace();
	    		}

	    		//no matter where we looked we couldn't find the class
	    		return null;
	    	}

	    	/**
	    	 * Is the requested name a package? We assume yes if it's not a class.
	    	 */
	    	private boolean isPackage(String name)
	    	{
	    		//if it is the name of a class we are compiling then it can't be a package
	    		if (sources.containsKey(name)) return false;

	    		//if it is a known class then return true
	    		if (Package.getPackage(name) != null) return true;

	    		Class theClass = null;
	    		try
	    		{
	    			//lets assume it is a class and try to load it
	    			theClass = _classloader.loadClass(name);
	    		}
	    		catch(Throwable e)
	    		{
	    			//we don't care about errors at this point
	    		}

	    		//if the var is still null then we couldn't load the class
	    		//so lets assume that the name is a package
	    		return theClass == null;
	    	}

	    	/**
	    	 * Checks whether the given name refers to a package rather than a
	    	 * class.
	    	 */
	    	public boolean isPackage(char[][] parentPackageName, char[] packageName)
	    	{
	    		String result = "";
	    		String sep = "";

	    		if (parentPackageName != null)
	    		{
	    			result = flatten(parentPackageName);
	    			if (result.length()>0) sep = ".";
	    		}

	    		String str = new String(packageName);

	    		if (Character.isUpperCase(str.charAt(0)) && !isPackage(result)) return false;

	    		result += sep;
	    		result += str;

	    		return isPackage(result);
	    	}

	    	public void cleanup() { }

		};

	    // Error handling policy - try the best we can
	    final IErrorHandlingPolicy policy = DefaultErrorHandlingPolicies.proceedWithAllProblems();

	    // A store for all the compilation options (thing of this as a souped up version of the
	    // command line arguments you would normally pass to javac)
	    final Map<String,String> settings = new HashMap<String,String>();

	    // Make sure that we access both the source and line numbers to help with reporting errors
	    settings.put(CompilerOptions.OPTION_LineNumberAttribute, CompilerOptions.GENERATE);
	    settings.put(CompilerOptions.OPTION_SourceFileAttribute, CompilerOptions.GENERATE);

	    // ignore deprecation, unused imports, missing serial version UIDs, and unused local
	    // variables - otherwise almost every Saxon rule would generate warnings...
	    settings.put(CompilerOptions.OPTION_ReportDeprecation, CompilerOptions.IGNORE);
	    settings.put(CompilerOptions.OPTION_ReportUnusedImport, CompilerOptions.IGNORE);
	    settings.put(CompilerOptions.OPTION_ReportMissingSerialVersion, CompilerOptions.IGNORE);
	    settings.put(CompilerOptions.OPTION_ReportUnusedLocal, CompilerOptions.IGNORE);
	    settings.put(CompilerOptions.OPTION_ReportUncheckedTypeOperation, CompilerOptions.IGNORE);

	    // source and target - Runes requires 1.5 so that is what we will specify
	    settings.put(CompilerOptions.OPTION_Source, CompilerOptions.VERSION_1_7);
	    settings.put(CompilerOptions.OPTION_TargetPlatform,CompilerOptions.VERSION_1_7);

	    // CompilerRequestor defines what to do with the result of a compilation.
	    final ICompilerRequestor requestor = new ICompilerRequestor()
		{
	    	public void acceptResult(CompilationResult result)
	    	{
	    		//lets start by assuming everything compiled correctly
	    		boolean errors = false;

	    		if (result.hasProblems())
	    		{
	    			//there were unfortunatelt some problems...

	    			for (IProblem problem : result.getProblems())
	    			{
	    				//for each problem

	    				if (problem.isError())
	    				{
	    					//if the problem is an error (something that will cause
	    					//compilation to fail) -- this means that any compiler
	    					//warnings are completely ignored.

	    					//record we have seen an error
	    					errors = true;

	    					//store the error for later
	    					addProblem(problem);
	    				}
	    			}
	    		}

	    		if (!errors)
	    		{
		    		// if there were no errors then load the compiled classes
		    		//into the classloader so that they can be instantiated later

	    			for (ClassFile classFile : result.getClassFiles())
	    			{
	    				//for each class we compiled....

	    				//get the fully qualified classname
	    				String compoundName = flatten(classFile.getCompoundName());

	    				//get the compiled bytes
	    				byte[] bytes = classFile.getBytes();

	    				//define this class in through the classloader
	    				_classloader.defineClass(compoundName, bytes);
	    			}
	    		}
	    	}

	    	/**
	    	 * Store the problem so that we can come back and look at it later
	    	 * @param problem the problem we want to store
	    	 */
	    	private void addProblem(IProblem problem)
	    	{
	    		//get the name of the file in which the compilation error has occured
	    		String name = new String(problem.getOriginatingFileName());

	    		//Get the problems we already know about for this file
	    		List<IProblem> problemsForName = problems.get(name);

	    		if(problemsForName == null)
	    		{
	    			//if we haven't seen any problems for this file yet
	    			//then create a list to store them in add it to the problems map
	    			problemsForName = new ArrayList<IProblem>();
	    			problems.put(name, problemsForName);
	    		}

	    		//store this problem against the filename
	    		problemsForName.add(problem);
	    	}
		};

	    // Define the list of things to compile
	    ICompilationUnit[] compilationUnits = new ICompilationUnit[sources.size()];

	    int i = 0;
	    for (String name : sources.keySet())
	    {
	    	//for each source create and store a compilation unit
	    	compilationUnits[i++] = new CompilationUnit(name);
	    }

	    // create the compiler
	    Compiler compiler = new Compiler(env, policy, new CompilerOptions(settings), requestor, new DefaultProblemFactory(Locale.getDefault()));

	    // and compile the classes
	    compiler.compile(compilationUnits);

	    if(!problems.isEmpty())
	    {
	    	//if we have seen an error then the compile will have failed so tell
	    	//the calling user that there has been a problem

	    	for (Map.Entry<String,List<IProblem>> prob : problems.entrySet())
	    	{
	    		//for each class in which errors occured

	    		//get the classname
	    		String name = prob.getKey();

	    		//get the problems that occured when compiling the class
	    		List<IProblem> probsForName = prob.getValue();

	    		for (IProblem problem : probsForName)
	    		{
	    			//for each problem...

	    			//display Error or Warning (we only ever display error as the warnings are being ignored
	    			//but I've left this in to remind me of this so if I ever put the warnings back into the
	    			//problem list then the output will be correct
	    			if(problem.isError()) System.err.print("Error: ");
	    			else if(problem.isWarning()) System.err.print("Warning: ");

	    			//display the error/warning message
	    			System.err.println(problem.getMessage()  + " at line " + problem.getSourceLineNumber() + " in " + name);
	    		}

	    		//print the source for this class, to help the user debug.
	    		System.err.println("\nThe offending input was:\n");

	    		//split the source code into seperate lines
	    		String[] lines = sources.get(name).split("\n");

				//create a builder to hold the formatted source code
				StringBuilder formatted = new StringBuilder();

				for (int l = 0 ; l < lines.length ; ++l)
				{
					//for each line output the line number and the line of code
					formatted.append(l+1).append(":\t").append(lines[l]).append("\n");
				}

				//display the source code with line numbers
				System.err.println(formatted);
	    	}

	    	//we saw an error which means compilation will have failed so throw an exception
	    	throw new Exception("There were problems; see error log for details!");
		}
	}

	/**
	 * Convert the embeded char arrays back into a dot seperated string.
	 * @param parts the name as char arrays
	 * @return the flattened dot seperated string
	 */
	private static String flatten(char[][] parts)
	{
		String result = "";
		String sep = "";

		for (char[] part : parts)
		{
			result += sep;
			result += new String(part);
			sep = ".";
		}

		return result;
	}

	/**
	 * A very simple class loader that allows us to hook into the standard class loader being
	 * used and define new classes from byte arrays.
	 * @author Mark A. Greenwood
	 */
	private static final class EclipseClassLoader extends ClassLoader
	{
		private Map<String, byte[]> byteStreams = new HashMap<String, byte[]>();
		/**
		 * Create a new class loader which simply delegates to the class loader of the
		 * current Thread context.
		 */
		public EclipseClassLoader()
		{
			super(Thread.currentThread().getContextClassLoader());
		}

		/**
		 * Define a new class from a byte array.
		 * @param name the name of the class being defined
		 * @param bytes the compiled bytes of the class
		 * @return an instance of the class
		 */
		protected Class defineClass(String name, byte[] bytes)
		{
			//define the class within the current class loader. We have to do it via
			//this simple class loader as defineClass(String, byte[], int, int) is protected
			return defineClass(name, bytes, 0, bytes.length);
		}
		
		@Override
		protected Class<?> loadClass(String name, boolean resolve)
				throws ClassNotFoundException {
			if (name == null) {
				throw new NullPointerException();
			}

			// Since all support classes of loaded class use same class loader
			// must check subclass cache of classes for things like Object

			// Class loaded yet?
			Class<?> c = findLoadedClass(name);
			if (c == null) {
				try {
					c = getParent().loadClass(name);
				} catch (ClassNotFoundException ex) {
					// Load class data from file and save in byte array
					byte data[] = byteStreams.get(name);

					if (data == null)
						throw new ClassNotFoundException(name);

					// Convert byte array to Class
					c = defineClass(name, data, 0, data.length);

					// If failed, throw exception
					if (c == null)
						throw new ClassNotFoundException(name);
				}

			}

			// Resolve class definition if approrpriate
			if (resolve)
				resolveClass(c);

			// Return class just created

			return c;
		}
		
	}

	public Class compile(String name, String source) throws Exception
	{
		//The compiler wants a map of name to source so build a map
		Map<String,String> sources = new HashMap<String,String>();

		//put the source file in the map
		sources.put(name,source);

		//compile the sources
		compile(sources);

		//the class has been compiled so get a handle to it and...
		@SuppressWarnings("unchecked") Class c = Class.forName(name, false, _classloader);

		//return it to the calling method
		return c;
	}

/*	public static void main(String args[]) throws Exception
	{
		EclipseCompiler compiler = new EclipseCompiler();

		String HELLO_WORLD_CLASS_NAME = "HelloWorldTest";
		String HELLO_WORLD_SOURCE = "import org.eclipse.jdt.internal.compiler.Compiler;\n\n" +
			"public class "+HELLO_WORLD_CLASS_NAME+"{\n" +
			"    public static void main(String[] args) {\n" +
			"        System.out.println(\"Hello world\");\n" +
			"    }\n" +
			"}";

		Class compiledClass = compiler.compile(HELLO_WORLD_CLASS_NAME, HELLO_WORLD_SOURCE);

		if (compiledClass==null)
		{
			System.out.println("Unable to compile file");
			return;
		}

		Method m = compiledClass.getMethod("main",String[].class);
		m.invoke(null, new Object[]{null});
	}*/
}

