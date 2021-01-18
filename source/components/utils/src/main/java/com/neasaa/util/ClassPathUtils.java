package com.neasaa.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassPathUtils {

	private static final Logger logger = LoggerFactory.getLogger(ClassPathUtils.class);
	
	private static final String FOLDER_SEPARATOR = "/";
	private static final String WINDOWS_FOLDER_SEPARATOR = "\\\\";
	// -------------------------------------------------------------------------
	/**
	 * This implementation opens an InputStream for the given class path resource.
	 */
	public static InputStream getInputStream ( String aPath ) throws IOException {
		return getInputStream( aPath, (ClassLoader) null );
	}

	// -------------------------------------------------------------------------
	/**
	 * This implementation opens an InputStream for the given class path resource.
	 */
	public static InputStream getInputStream ( String aPath, Class<?> aClass )
			throws IOException {
		if ( aClass == null ) {
			return getInputStream( aPath, (ClassLoader) null );
		}
		else {
			return getInputStream( aPath, aClass.getClassLoader() );
		}
	}
	// -------------------------------------------------------------------------
	/**
	 * This implementation opens an InputStream for the given class path resource.
	 */
	public static InputStream getInputStream ( String aPath,
			ClassLoader aClassLoader )
			throws IOException {
		
		String myPath = normalizePath( aPath );
		ClassLoader classLoader =
				(aClassLoader != null ? aClassLoader : getDefaultClassLoader());
		logger.debug( "Loading " + myPath + " ..." );
		InputStream is = classLoader.getResourceAsStream( myPath );
		
		if ( is == null ) {
			throw new FileNotFoundException( "File [" + myPath
					+ "] cannot be opened because it does not exist" );
		}
		
		return is;
	}

	// -------------------------------------------------------------------------
	/**
	 * Return the default ClassLoader to use: typically the thread context
	 * ClassLoader, if available; the ClassLoader that loaded the ClassUtils class
	 * will be used as fallback.
	 * <p>
	 * Call this method if you intend to use the thread context ClassLoader in a
	 * scenario where you absolutely need a non-null ClassLoader reference: for
	 * example, for class path resource loading (but not necessarily for
	 * <code>Class.forName</code>, which accepts a <code>null</code> ClassLoader
	 * reference as well).
	 * 
	 * @return the default ClassLoader (never <code>null</code>)
	 * @see java.lang.Thread#getContextClassLoader()
	 */
	public static ClassLoader getDefaultClassLoader () {
		ClassLoader cl = null;
		try {
			cl = Thread.currentThread().getContextClassLoader();
		}
		catch ( Throwable ex ) {
			System.out
					.println( "Cannot access thread context ClassLoader - falling back to system class loader" );
			ex.printStackTrace();
		}
		if ( cl == null ) {
			// No thread context class loader -> use class loader of this class.
			cl = ClassPathUtils.class.getClassLoader();
		}
		return cl;
	}

	
	// -------------------------------------------------------------------------
	/**
	 * Print class location from where specified class get loaded in JVM. If
	 * specified class missing in class path, it prints the classpath. <br>
	 * This method may not return the 100% accurate result depending on class
	 * loader scheme.
	 * 
	 * @param aPath
	 */
	public static void printResourcePath ( String aPath ) {
		printResourcePath( aPath, (ClassLoader) null );

	}


	// -------------------------------------------------------------------------
	/**
	 * Print class location from where specified class get loaded in JVM. If
	 * specified class missing in class path, it prints the classpath. <br>
	 * This method may not return the 100% accurate result depending on class
	 * loader scheme.
	 * 
	 * @param aPath
	 * @param aClassLoader
	 */
	public static void printResourcePath ( String aPath, ClassLoader aClassLoader ) {

		String myPath = normalizePath( aPath );
		ClassLoader classLoader =
				(aClassLoader != null ? aClassLoader : getDefaultClassLoader());
		URL url = classLoader.getResource( myPath );
		if ( url == null ) {
			logger.debug( "Resource " + aPath
					+ " not found in  classpath:"
					+ System.getProperty( "java.class.path" ) );
		}
		else {
			logger.debug( "Class " + aPath + " Found at "
					+ url );
		}
	}
	// -------------------------------------------------------------------------
	/**
	 * 
	 * @param aPath
	 * @return
	 */
	private static String normalizePath ( String aPath ) {

		if ( aPath == null || aPath.isEmpty() ) {
			throw new RuntimeException( "Invalid path [" + aPath + "]" );
		}
		String myPath = aPath;

		 if ( myPath.startsWith( "/" ) ) {
			myPath = myPath.substring( 1 );
		}

		myPath = myPath.replaceAll( WINDOWS_FOLDER_SEPARATOR, FOLDER_SEPARATOR );
		logger.debug( "Normalize path is [" + myPath + "]" );
		return myPath;
	}
	// -------------------------------------------------------------------------
	/**
	 * This implementation returns a URL for the underlying class path resource.
	 * 
	 * @see java.lang.ClassLoader#getResource(String)
	 * @see java.lang.Class#getResource(String)
	 */
	public static URL getURL ( String aPath ) throws IOException {

		return getURL( aPath, (ClassLoader) null );
	}


	// -------------------------------------------------------------------------
	/**
	 * This implementation returns a URL for the underlying class path resource.
	 * 
	 * @see java.lang.ClassLoader#getResource(String)
	 * @see java.lang.Class#getResource(String)
	 */
	public static URL getURL ( String aPath, ClassLoader aClassLoader )
			throws IOException {
		String myPath = normalizePath( aPath );
		ClassLoader classLoader =
				(aClassLoader != null ? aClassLoader : getDefaultClassLoader());
		URL url = classLoader.getResource( myPath );
		
		if ( url == null ) {
			throw new FileNotFoundException( "[" + aPath
					+ "] cannot be resolved to URL because it does not exist" );
		}
		return url;
	}
	// -------------------------------------------------------------------------
	//
	//
	// /**
	// * This implementation returns a File reference for the underlying class
	// path
	// * resource, provided that it refers to a file in the file system.
	// *
	// * @see org.springframework.util.ResourceUtils#getFile(java.net.URL, String)
	// */
	// public File getFile () throws IOException {
	// return ResourceUtils.getFile( getURL(), getDescription() );
	// }
	// -------------------------------------------------------------------------
}
// ---------------------------------------------------------------------------