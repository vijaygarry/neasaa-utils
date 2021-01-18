package com.neasaa.util.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.neasaa.util.ClassPathUtils;
import com.neasaa.util.StringUtils;

public class BaseConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(BaseConfig.class);
	
	//-------------------------------------------------------------------------
	private static Properties properties;
	//-------------------------------------------------------------------------
	protected BaseConfig() {
	}
	//-------------------------------------------------------------------------
	public static String getOptionalProperty (
		String 	aKey
	) {
		return properties.getProperty(aKey);
	}
	// -------------------------------------------------------------------------
	public static boolean containsProperty (String aKey) {
		return properties.containsKey(aKey);
	}
	//-------------------------------------------------------------------------
	public static String getProperty (
		String 	aKey
	) {
		
		String value = getOptionalProperty(aKey);
		if(value == null) {
			throw new RuntimeException("Property '" + aKey + "' not found.");
		}
		return value;
	}
	//-------------------------------------------------------------------------
	public static int getIntProperty (
		String 	aKey
	) {
		
		String value = getProperty(aKey);
		return StringUtils.stringToInt(value);
	}
	//-------------------------------------------------------------------------
	public static long getLongProperty (
		String 	aKey
	)  {
		
		String value = getProperty(aKey);
		return StringUtils.stringToLong(value);
	}
	//-------------------------------------------------------------------------
	public static boolean getBooleanProperty (
		String 	aKey
	)  {
		String value = getProperty(aKey);
		return StringUtils.parseBooleanValue(value);
	}

	// -------------------------------------------------------------------------
	public static boolean getBooleanProperty(String aKey, boolean aDefaultValue) {
		String value = getOptionalProperty(aKey);
		if (value == null) {
			return aDefaultValue;
		}
		return StringUtils.parseBooleanValue(value);
	}
	// -------------------------------------------------------------------------
	public static List<String> getListProperty(String aKey) {
		String value = getOptionalProperty(aKey);
		if (value == null) {
			return null;
		}
		return StringUtils.parseStringToList(value);
	}
	
	//-------------------------------------------------------------------------
	/**
	 * Initialize the properties with specified properties.
	 * This is only for testing.
	 * @param aProps
	 */
	public static void initialize (Properties aProps) {
		properties = aProps;
	}
	//-------------------------------------------------------------------------
	/**
	 * Reads internal property file. Then reads the external config file if exists.
	 * Overwrite all internal configuration which exists in external config file.
	 * @throws Exception 
	 */
	public static void initialize (String...aConfigFiles
	) throws Exception {
		
		if (properties == null) {
			synchronized (BaseConfig.class) {
				if (properties == null) {
					for(String configFile : aConfigFiles) {
						Properties fileProperties = getPropertiesFromFile(configFile);
						properties = mergeProperties(properties, fileProperties);
					}
				}
			}
		}
	}
	
	// -------------------------------------------------------------------------
	/**
	 * Read config file from classpath and load in properties object.
	 * @throws TestException 
	 */
	private static Properties getPropertiesFromFile ( String aConfigFileName ) throws Exception {
		Properties localProperties = null;
		
		// load the properties file from the class path
		InputStream inputStream;
		try {
			inputStream = ClassPathUtils.getInputStream( aConfigFileName, BaseConfig.class.getClassLoader() );
		}
		catch ( IOException ex ) {
			if(ex instanceof FileNotFoundException) {
				throw new Exception ( ex.getMessage(), ex);
			}
			throw new Exception ( "Error reading config file ["
					+ aConfigFileName + "]. Error details: " + ex.getMessage());
		}

		ClassPathUtils.printResourcePath( aConfigFileName );
		
		if ( inputStream == null ) {
			throw new RuntimeException( "Null input stream" );
		}

		try {
			localProperties = new Properties();
			localProperties.load( inputStream );
		}
		catch ( IOException e ) {
			System.err.println( "Error Loading property file " );
			throw new RuntimeException( "Error Loading property file" );
			
		}

		try {
			inputStream.close();
		}
		catch ( Exception e ) {
			System.err.println( "Error Closing property file " );
			throw new RuntimeException( "Error Closing property file " );
			
		}
		logger.info ( "Properties loaded successfully from file " + aConfigFileName );
		return localProperties;
	}

	// -------------------------------------------------------------------------
	/**
	 * Create a new properties i.e. do not update any of the existing properties
	 * object passed. Copy all properties from aFirstProperties to new prop
	 * object. Then copy all properties from aSecondProperties to new prop
	 * object. Overwrite the properties if already exists. Properties from
	 * aSecondProperties takes precedence over aFirstProperties
	 */
	public static Properties mergeProperties(Properties aFirstProperties, Properties aSecondProperties) {
		if (aFirstProperties == null && aSecondProperties == null) {
			return null;
		}

		Properties newProperties = new Properties();
		if (aFirstProperties != null) {
			for (String propName : aFirstProperties.stringPropertyNames()) {
				newProperties.setProperty(propName, aFirstProperties.getProperty(propName));
			}
		}
		if (aSecondProperties != null) {
			for (String propName : aSecondProperties.stringPropertyNames()) {
				newProperties.setProperty(propName, aSecondProperties.getProperty(propName));
			}
		}
		return newProperties;
	}
	//-------------------------------------------------------------------------
	public static void printAllConfigurationValues () {
		StringBuilder sb = new StringBuilder();
		sb.append( "\n------------- All configurations Start ------------------\n");
		for(String propertyName : properties.stringPropertyNames() ) {
			sb.append( "'" + propertyName + "' = '" + properties.getProperty( propertyName ) +"'\n");
		}
		sb.append( "----------- All configurations End ----------------");
		logger.info( sb.toString());
	}
}
