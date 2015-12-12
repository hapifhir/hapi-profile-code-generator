package org.cdscollaborative.common.utils.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import org.apache.commons.io.IOUtils;

public class ResourceLoadingUtils {
	
	/**
	 * Returns an input stream for the resource specified by the absolute file path.
	 * 
	 * @param absolutePath
	 * @return
	 * @throws FileNotFoundException
	 */
	public static InputStream getInputStreamFromFilePath(String absolutePath) throws FileNotFoundException{
		File file = new File(absolutePath);
		return new FileInputStream(file);
	}
	
	/**
	 * Returns a reader for the resource specified by the absolute file path
	 * 
	 * @param absolutePath
	 * @return
	 * @throws FileNotFoundException
	 */
	public static Reader getReaderFromFilePath(String absolutePath) throws FileNotFoundException {
		File file = new File(absolutePath);
		return new FileReader(file);
	}
	
	/**
	 * Returns an input stream for the resource located in the path relative to the root location
	 * on the class path. For instance, a Maven resource in src/java/resources/conf/myconfigfile.xml
	 * should be given a relative path of 'conf/myconfigfile.xml' if 'src/java/resources' is in
	 * the classpath as it typically is in Maven projects.
	 * <br>
	 * Note:
	 * This class makes use of Class.getClassLoader().getResourceAsStream(path) to get the resource so the path
	 * should not be prefixed by a slash '/' as is required when one uses Class.getResourceAsStream(path).
	 * 
	 * @param relativePath
	 * @return
	 */
	public static InputStream getInputStreamFromClasspath(String absolutePath) {
		return ResourceLoadingUtils.class.getClassLoader().getResourceAsStream(absolutePath);
	}
	
	/**
	 * Returns a reader for the resource located in the path relative to the root location
	 * on the class path. For instance, a Maven resource in src/java/resources/conf/myconfigfile.xml
	 * should be given a relative path of 'conf/myconfigfile.xml' if 'src/java/resources' is in
	 * the classpath as it typically is in Maven projects.
	 * <br>
	 * Note:
	 * This class makes use of Class.getClassLoader().getResourceAsStream(path) to get the resource so the path
	 * should not be prefixed by a slash '/' as is required when one uses Class.getResourceAsStream(path).
	 * 
	 * @param relativePath
	 * @return
	 */
	public static Reader getReaderFromClasspath(String absolutePath) {
		InputStream is = getInputStreamFromClasspath(absolutePath);
		return new InputStreamReader(is);
	}
	
	/**
	 * Returns the canonical path for the resource located in the path relative to the root location
	 * on the class path. For instance, a Maven resource in /../project/src/java/resources/conf/myconfigfile.xml
	 * should be given a relative path of '/conf/myconfigfile.xml' if 'src/java/resources' is in
	 * the classpath as it typically is in Maven projects.
	 * <br>
	 * Note:
	 * This class makes use of Class.getClassLoader().getResource(path) to get the resource so the path
	 * should be prefixed by a slash '/' as is required when one uses Class.getResource(resourceRelativePath).
	 * 
	 * @param relativePath
	 * @return
	 */
	public static String getPathFromResourceClassPath(String resourcePath) {
		try {
			URL url = ResourceLoadingUtils.class.getResource(resourcePath);
			return url.toURI().getPath();
		} catch(Exception e) {
			throw new RuntimeException("Error retrieving canonical path from relative path " + resourcePath, e);
		}
	}
	
	/**
	 * Returns a file handle to the resource on the classpath.
	 * 
	 * @param resourcePath
	 * @return
	 */
	public static File getFileFromResourceClasspath(String resourcePath) {
		try {
			URL url = ResourceLoadingUtils.class.getResource(resourcePath);
			return new File(url.toURI().getPath());
		} catch(Exception e) {
			throw new RuntimeException("Error retrieving canonical path from relative path " + resourcePath, e);
		}
	}
	
	/**
     * Loads a resource from the class path and returns it as a String.
     * 
     * @param absolutePath
     * @return
     */
    public static String getResourceFromClasspath(String absolutePath) {
		InputStream is = ResourceLoadingUtils.class.getClassLoader().getResourceAsStream(absolutePath);
		Reader reader = new InputStreamReader(is);
		String resource = null;
		try {
			resource = IOUtils.toString(reader);
		}catch(Exception e) {
			throw new RuntimeException("Error converting stream to string");
		} finally {
			try{reader.close();} catch(Exception e) {e.printStackTrace();}
		}
	    return resource;
	}
}
