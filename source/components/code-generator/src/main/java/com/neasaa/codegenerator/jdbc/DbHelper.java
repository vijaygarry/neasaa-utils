package com.neasaa.codegenerator.jdbc;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.JDBCType;

import com.neasaa.util.StringUtils;
import com.neasaa.util.config.BaseConfig;

public class DbHelper {

	// -------------------------------------------------------------------------
	public static Connection getDbConnection() throws Exception {
		DriverManager.registerDriver((Driver) Class.forName(BaseConfig.getProperty("DB.DRIVER")).newInstance());
		return DriverManager.getConnection(BaseConfig.getProperty("DB.URL"), BaseConfig.getProperty("DB.USER"),
				BaseConfig.getProperty("DB.PASSWORD"));
	}
	
	public static String getClassNameFromTableName (String aTableName) {
		String className = BaseConfig.getOptionalProperty(aTableName.toUpperCase() + ".CLASSNAME");
		if (className != null) {
			return className;
		}
		System.out.println("MISSING:" + aTableName.toUpperCase() + ".CLASSNAME=" + StringUtils.capitalize(aTableName.toLowerCase()));
		return StringUtils.capitalize(aTableName.toLowerCase());
	}
	
	
	public static String getFieldNameFromColumnName (String aTableName, String aColumnName) {
		String propName = aTableName.toUpperCase() + "." + aColumnName.toUpperCase() + ".FIELDNAME";
		String fieldName = BaseConfig.getOptionalProperty(propName);
		if (fieldName != null) {
			return fieldName;
		}
		
		System.out.println("MISSING:" + propName + "=" + aColumnName.toLowerCase());
		return getJavaFieldNameFromColumnName(aColumnName);
	}
	
	public static String getJavaDatatypeFromSqlDataType (JDBCType aJDBCType) {
		String propName = "SQL." + aJDBCType.toString() + ".TO.JAVATYPE";
		String javaType = BaseConfig.getOptionalProperty(propName);
//		System.out.println("prop name :" + propName + " Value: " + javaType);
		if (javaType != null) {
			return javaType;
		}
		System.out.println("MISSING:" + propName + "=" + StringUtils.capitalize(aJDBCType.toString().toLowerCase()));
		return StringUtils.capitalize(aJDBCType.toString().toLowerCase());
	}
	
	/**
	 * Convert column name to lower case. Remove '_' character and capitalize next char.
	 * 
	 * @param aDbColumnName
	 */
	public static String getJavaFieldNameFromColumnName (String aDbColumnName) {
		StringBuilder sb = new StringBuilder();
		boolean capitalize = false;
		//Go through all the characters in the sentence.
        for(char character : aDbColumnName.toCharArray()) {

            if(character == '_') {
                capitalize = true;
                continue;
            }
            if(capitalize) {
            	sb.append(Character.toUpperCase(character));
            } else {
            	sb.append(Character.toLowerCase(character));
            }
            capitalize = false;
            
        }
        return sb.toString();
	}


}
