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

	public ByteClassLoader(ClassLoader classLoader) {
		super(classLoader);
		this.classMap = new HashMap<String, byte[]>();
	}
	public ByteClassLoader(Map<String, byte[]> classMap) {
		super();
		this.classMap = classMap;
	}
	
	@Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {       
        byte[] mbc = classMap.get(name);       
        if (mbc==null){           
            mbc = classMap.get(name.replace(".","/"));           
            if (mbc==null){               
                return super.findClass(name);           
            }       
        }       
        return defineClass(name, mbc, 0, mbc.length);   
    }
 
    public void addClass(String name, byte[] mbc) {       
        classMap.put(name, mbc);   
    }
}

