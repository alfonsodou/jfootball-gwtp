package org.javahispano.jfootball.server.compiler;

import org.eclipse.jdt.internal.compiler.tool.EclipseCompiler;

import javax.tools.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.net.URI;

import com.sun.scenario.scenegraph.SGNode;
import com.sun.scenario.animation.Timeline;

/**
 *
 */
public class EclipseMemoryCompiler {

    private SpecialClassLoader xcl;
    private String className;

    public void evaluate(String code) throws CompileError {
        try {
            compileAndLoad(code);
        } catch (CompileError e) {
            throw e;
        } catch (Exception e) {
            throw new CompileError(e);
            
        }
    }

    public SGNode getSNNodeInstance()  {
        if (xcl==null){
            return null;
        }
        try {
            return (SGNode) Class.forName(className, true, xcl).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Timeline getTimeline() {
        return null;
    }
    

    private static Pattern pattern = Pattern.compile("class (\\w[\\w|\\d]*)");
    private static Pattern packagePattern = Pattern.compile("^package (.*);",Pattern.MULTILINE);
    public static String getClassName(String src){
        Matcher matcher = pattern.matcher(src);
        if (matcher.find()&&matcher.groupCount()>0){
            String className = matcher.group(1);
            matcher = packagePattern.matcher(src);
            if (matcher.find() && matcher.groupCount()>0){
                return matcher.group(1)+"."+className;
            }
            return className;
        }
        return null;
    }


    public void compileAndLoad(String src) throws Exception {
        StringWriter result = new StringWriter();
        xcl = new SpecialClassLoader(ClassLoader.getSystemClassLoader());
        className = getClassName(src);
        boolean success = compileMemoryMemory(src,className.replace(".","/") , xcl, result);
        if (!success){
            String errorMsg = result.toString();
            if (errorMsg.trim().length()==0){
                errorMsg = "Unknown error";
            }
            throw new CompileError(errorMsg);
        }
    }

    public static boolean compileMemoryMemory(String src, String name, SpecialClassLoader xcl, StringWriter err) throws CompileError {
        DiagnosticCollector<JavaFileObject> diacol = new DiagnosticCollector<JavaFileObject>();
        try{
            JavaCompiler javac = new EclipseCompiler(); 

            StandardJavaFileManager sjfm = javac.getStandardFileManager(diacol, null, null);
            SpecialJavaFileManager xfm = new SpecialJavaFileManager(sjfm, xcl);
            List<String> optionList = new ArrayList<String>();
            
            JavaCompiler.CompilationTask compile = javac.getTask(err, xfm, diacol, optionList, null,
                    Arrays.asList(new MemorySource(name, src)));
            return compile.call();
        }
        catch (Exception e){
            e.printStackTrace(new PrintWriter(err));
            return false;
        }
    }
}

class MemorySource extends SimpleJavaFileObject {
    private String src;
    public MemorySource(String name, String src) {
        super(URI.create("file:///" + name + ".java"), Kind.SOURCE);
        this.src = src;
    }
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return src;
    }
    public OutputStream openOutputStream() {
        throw new IllegalStateException();
    }
    public InputStream openInputStream() {
        return new ByteArrayInputStream(src.getBytes());
    }
}

class MemoryByteCode extends SimpleJavaFileObject {
    private ByteArrayOutputStream baos;
    public MemoryByteCode(String name) {
        super(URI.create("byte:///" + name + ".class"), Kind.CLASS);
    }
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        throw new IllegalStateException();
    }
    public OutputStream openOutputStream() {
        baos = new ByteArrayOutputStream();
        return baos;
    }
    public InputStream openInputStream() {
        throw new IllegalStateException();
    }
    public byte[] getBytes() {
        return baos.toByteArray();
    }
}

class SpecialJavaFileManager extends ForwardingJavaFileManager<StandardJavaFileManager> {
    private SpecialClassLoader xcl;
    public SpecialJavaFileManager(StandardJavaFileManager sjfm, SpecialClassLoader xcl) {
        super(sjfm);
        this.xcl = xcl;
    }
    public JavaFileObject getJavaFileForOutput(Location location, String name, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
        MemoryByteCode mbc = new MemoryByteCode(name);
        xcl.addClass(name, mbc);
        return mbc;
    }

    public ClassLoader getClassLoader(Location location) {
        return xcl;
    }
}

class SpecialClassLoader extends ClassLoader {
    private Map<String,MemoryByteCode> m;

    public SpecialClassLoader(ClassLoader classLoader) {
        super(classLoader);
        m = new HashMap<String, MemoryByteCode>();

    }
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        MemoryByteCode mbc = m.get(name);
        if (mbc==null){
            mbc = m.get(name.replace(".","/"));
            if (mbc==null){
                return super.findClass(name);
            }
        }
        return defineClass(name, mbc.getBytes(), 0, mbc.getBytes().length);
    }

    public void addClass(String name, MemoryByteCode mbc) {
        m.put(name, mbc);
    }
}