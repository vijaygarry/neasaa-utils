package com.neasaa.codegenerator.jdbc;

import com.neasaa.codegenerator.java.JavaClassDef;
import com.neasaa.util.FileUtils;
import com.neasaa.util.config.BaseConfig;

public class AbstractJavaClassGenerator {

	private static final String JAVA_CLASS_COPYRIGHT_HEADER_FILEPATH_CONFIGNAME = "JAVA.CLASS.COPYRIGHT.HEADER.FILEPATH";
	
	public static String getCopyrightHeaderForClass () throws Exception {
		String filepath = BaseConfig.getOptionalProperty(JAVA_CLASS_COPYRIGHT_HEADER_FILEPATH_CONFIGNAME);
		if(filepath != null) {
			return FileUtils.readFileAsString(filepath);
		}
		return "/** Class Header*/";
	}
	
	
	public static void addSlf4jImports (JavaClassDef aClassDef) {
		aClassDef.addImportClass("org.slf4j.Logger");
		aClassDef.addImportClass("org.slf4j.LoggerFactory");
	}
	
	public static void addUtilDateImport (JavaClassDef aClassDef) {
		aClassDef.addImportClass("java.util.Date");
	}
	
	public static String getClassFileName (String aPackageName, String aClassName) {
		String filePath = aPackageName.replace(".", "/");
		filePath = filePath + "/" + aClassName + ".java";
		return filePath;
	}
	
}
