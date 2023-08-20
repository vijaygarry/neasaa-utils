package com.neasaa.codegenerator.jdbc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neasaa.codegenerator.CodeGeneratorConstants;
import com.neasaa.codegenerator.java.JavaClassDef;
import com.neasaa.codegenerator.java.JavaFieldDef;
import com.neasaa.util.FileUtils;
import com.neasaa.util.config.BaseConfig;

/**
 * @author Vijay Garothaya
 * @version 1.0 Dec 5, 2018
 */
public class EntityClassGenerator extends AbstractJavaClassGenerator {
	
	public static void generateEntityClassForTable (TableDefinition aTableDefinition) throws Exception {
		String srcMainJavaPath = BaseConfig.getProperty("java.generated.file.base.dir");
		String packageName = BaseConfig.getProperty(CodeGeneratorConstants.ENTITY_CLASS_PACKAGE_CONFIG_NAME);
		
		String className = DbHelper.getClassNameFromTableName (aTableDefinition.getTableName());
		String javaClassFile = srcMainJavaPath + getClassFileName(packageName, className);
		FileUtils.createEmptyFile(javaClassFile, false);
		List<JavaFieldDef> fields = new ArrayList<>();
		Map<String, JavaFieldDef> columnNameToFieldMap = new HashMap<>();
		
		
		JavaFieldDef serialVerUIDField = JavaFieldDef.getSerialVerUIDFieldDef();
		fields.add(serialVerUIDField);
		
		for(ColumnDefinition colDef: aTableDefinition.getColumnDefinitions()) {
			JavaFieldDef javaFieldDef = TableToJavaHelper.getJavaFieldDefFromCol( aTableDefinition, colDef );
			fields.add(javaFieldDef);	
			columnNameToFieldMap.put(colDef.getColumnName(), javaFieldDef);
		}

		JavaClassDef classDef = new JavaClassDef();
		classDef.setHeader(getCopyrightHeaderForClass());
		classDef.setPackageName(packageName);
		//addSlf4jImports (classDef);
		addUtilDateImport (classDef);
		
		classDef.setClassName(className);
		
		classDef.setParentClass(CodeGeneratorConstants.ABSTRACT_ENTITY_CLASS_NAME);
		
		classDef.setFields(fields);
		System.out.println("Creating Entity java class " + javaClassFile);
		FileUtils.writeStringToFile(javaClassFile, classDef.generateJavaClass(), false);
		
	}
}
