package com.neasaa.codegenerator.jdbc;

import java.util.ArrayList;
import java.util.Arrays;

import com.neasaa.codegenerator.CodeGeneratorConstants;
import com.neasaa.codegenerator.java.JavaClassDef;
import com.neasaa.codegenerator.java.JavaFieldDef;
import com.neasaa.codegenerator.java.JavaMethodDef;
import com.neasaa.util.FileUtils;
import com.neasaa.util.StringUtils;
import com.neasaa.util.config.BaseConfig;


/**
 * @author Vijay Garothaya
 * @version 1.0 Dec 5, 2018
 */
public class RowMapperGenerator extends AbstractJavaClassGenerator {
	
//	private String className;
//	private String packageName;
//	private String entityName;
//	private TableDefinition tableDefinition;
	
	public static String getRowMapperClassName (String aEntityClassName) {
		return aEntityClassName + "RowMapper";
	}
	
	public static void generateRowMapperClassForTable (String aEntityClassName, TableDefinition aTableDefinition) throws Exception {
		
		String srcMainJavaPath = BaseConfig.getProperty("java.generated.file.base.dir");
		String packageName = BaseConfig.getProperty("java.generated.file.dao.package");
		String entityClassPackageName = BaseConfig.getProperty(CodeGeneratorConstants.ENTITY_CLASS_PACKAGE_CONFIG_NAME);
		packageName = packageName + ".pg";
		JavaClassDef classDef = new JavaClassDef();
		String rowMapperClassName = getRowMapperClassName(aEntityClassName);
		classDef.setClassName( rowMapperClassName );
		classDef.setInterfaces(new ArrayList<>(Arrays.asList("RowMapper<" + aEntityClassName + ">")));
		classDef.setHeader(getCopyrightHeaderForClass());
		String javaClassFile = srcMainJavaPath + getClassFileName(packageName, rowMapperClassName);
		classDef.setPackageName( packageName );
		classDef.addImportClass( "java.sql.ResultSet" );
		classDef.addImportClass( "java.sql.SQLException" );
		//classDef.addImportClass( "java.util.List" );
		
		//classDef.addImportClass( "org.slf4j.Logger" );
		//classDef.addImportClass( "org.slf4j.LoggerFactory" );
		classDef.addImportClass( "org.springframework.jdbc.core.RowMapper" );
		classDef.addImportClass( entityClassPackageName + "." + aEntityClassName);
		
		classDef.addMethod(getmapRowMethod (aEntityClassName, aTableDefinition));
		FileUtils.createEmptyFile(javaClassFile, false);
		System.out.println("Creating RowMapper java class " + javaClassFile);
		FileUtils.writeStringToFile(javaClassFile, classDef.generateJavaClass(), false);
	}
	
	
	
	private static JavaMethodDef getmapRowMethod (String aEntityClassName, TableDefinition aTableDefinition) throws Exception {
		JavaMethodDef method = new JavaMethodDef("mapRow", "ResultSet aRs, int aRowNum");
		method.setReturnType(aEntityClassName);
		method.addMethodException("SQLException");
		method.addAnnotation("@Override");
		
		StringBuilder sb = new StringBuilder();
		String entityVarName = StringUtils.lowerFirstChar (aEntityClassName); 
		sb.append("\t\t" +aEntityClassName).append( " " ).append( entityVarName ) .append( " = new " ).append( aEntityClassName ).append( "();\n" );
		for(ColumnDefinition colDef :aTableDefinition.getColumnDefinitions()){
			JavaFieldDef javaFieldDef = TableToJavaHelper.getJavaFieldDefFromCol( aTableDefinition, colDef );
			String setterMethodName = javaFieldDef.getSetterMethodName();
			sb.append("\t\t" + entityVarName).append( "." ).append( setterMethodName ) .append( "(" ).append( SqlStatementHelper.getResultSetMethod( "aRs", colDef.getDataType(), colDef.getColumnName() ) ).append( ");\n" );	
		}
		sb.append("\t\treturn " + entityVarName).append( ";" );
		method.addMethodImplementation(sb.toString());
		return method;
	}
}