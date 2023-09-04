package com.neasaa.codegenerator.jdbc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neasaa.codegenerator.CodeGeneratorConstants;
import com.neasaa.codegenerator.java.JavaClassDef;
import com.neasaa.codegenerator.java.JavaFieldDef;
import com.neasaa.codegenerator.java.JavaInterfaceDef;
import com.neasaa.codegenerator.java.JavaMethodDef;
import com.neasaa.util.FileUtils;
import com.neasaa.util.StringUtils;
import com.neasaa.util.config.BaseConfig;

/**
 * This class will generate interface with following method: <br>
 * void insertAccountEntity (AccountEntity aAccount);<br>
 * void deleteAccountEntityById (String aAccountId);<br>
 * void updateAccountEntityById (AccountEntity aAccount);<br>
 * AccountEntity fetchAccountEntityById (String aAccountId);
 * 
 * @author Vijay G
 * @version 1.0 Dec 21, 2018
 */
public class EntityDaoClassGenerator extends AbstractJavaClassGenerator {

	
	
	public static void generateDaoInterfaceCode (String aEntityClassName) throws Exception {
		String srcMainJavaPath = BaseConfig.getProperty("java.generated.file.base.dir");
		String packageName = BaseConfig.getProperty(CodeGeneratorConstants.DAO_CLASS_PACKAGE_CONFIG_NAME);
		String entityClassPackageName = BaseConfig.getProperty(CodeGeneratorConstants.ENTITY_CLASS_PACKAGE_CONFIG_NAME);
		
		String className = EntityDaoGeneratorHelper.getDaoInterfaceName (aEntityClassName);
		String javaClassFile = srcMainJavaPath + getClassFileName(packageName, className);
		FileUtils.createEmptyFile(javaClassFile, false);

		JavaInterfaceDef classDef = new JavaInterfaceDef();
		classDef.setHeader(getCopyrightHeaderForClass());
		classDef.setPackageName(packageName);
		//addSlf4jImports(classDef);
		//addUtilDateImport(classDef);
		classDef.addImportClass(entityClassPackageName + "." + aEntityClassName);
		classDef.addImportClass("java.sql.SQLException");
		classDef.setClassName(className);

		addInterfaceMethods(aEntityClassName, classDef);
		System.out.println("Creating Dao Interface java class " + javaClassFile);
		FileUtils.writeStringToFile(javaClassFile, classDef.generateJavaClass(), false);
		
	}
	
	public static void generateDaoImplCode (String aEntityClassName, TableDefinition aTableDefinition) throws Exception {
		
		String srcMainJavaPath = BaseConfig.getProperty("java.generated.file.base.dir");
		String interfacePackageName = BaseConfig.getProperty("java.generated.file.dao.package");
		String classPackageName = interfacePackageName + ".pg";
		String entityClassPackageName = BaseConfig.getProperty(CodeGeneratorConstants.ENTITY_CLASS_PACKAGE_CONFIG_NAME);
		
		String className = EntityDaoGeneratorHelper.getDaoImplName (aEntityClassName);
		String daoInterfaceName = EntityDaoGeneratorHelper.getDaoInterfaceName(aEntityClassName);
		String javaClassFile = srcMainJavaPath + getClassFileName(classPackageName, className);
		FileUtils.createEmptyFile(javaClassFile, false);

		JavaClassDef classDef = new JavaClassDef();
		classDef.setHeader(getCopyrightHeaderForClass());
		classDef.setPackageName(classPackageName);
		classDef.addImportClass(interfacePackageName + "." + daoInterfaceName);
		classDef.addImportClass(entityClassPackageName + "." + aEntityClassName);
		classDef.addImportClass("org.springframework.jdbc.core.PreparedStatementCreator");
		classDef.addImportClass("java.sql.SQLException");
		classDef.addImportClass("java.sql.Connection");
		classDef.addImportClass("java.sql.PreparedStatement");
		
		classDef.setClassName(className);

		classDef.setParentClass(CodeGeneratorConstants.ABSTRACT_DAO_CLASS_NAME);
		classDef.setInterfaces(new ArrayList<>(Arrays.asList(daoInterfaceName)));
		
		Map<String, JavaFieldDef> columnNameToFieldMap = new HashMap<>();
		List<JavaFieldDef> fields = new ArrayList<>();
		List<ColumnDefinition> primarykeyColumnValues = new ArrayList<>();
		List<ColumnDefinition> nonPrimarykeyColumnValues = new ArrayList<>();
		
		for(ColumnDefinition colDef: aTableDefinition.getColumnDefinitions()) {
			JavaFieldDef javaFieldDef = TableToJavaHelper.getJavaFieldDefFromCol( aTableDefinition, colDef );
			fields.add(javaFieldDef);	
			columnNameToFieldMap.put(colDef.getColumnName(), javaFieldDef);
			
			// If this column is part of primary key, then use this for where clause.
			if ( colDef.isPrimaryKey() ) {
				primarykeyColumnValues.add( colDef );
				continue;
			}

			// continue if this column to ignore on target database.
			if ( colDef.isIgnoreColumn() ) {
				continue;
			}

			nonPrimarykeyColumnValues.add( colDef );
		}
				
		addInsertMethod(aEntityClassName, classDef, aTableDefinition, columnNameToFieldMap);
		addDeleteMethodImpl(classDef, aTableDefinition, columnNameToFieldMap, aEntityClassName, primarykeyColumnValues, nonPrimarykeyColumnValues);
		addUpdateMethodImpl(classDef, aTableDefinition, columnNameToFieldMap, aEntityClassName, primarykeyColumnValues, nonPrimarykeyColumnValues);
		addFetchMethodImpl(aEntityClassName, classDef, aTableDefinition, columnNameToFieldMap);
		
		
		System.out.println("Creating Dao Impl java class " + javaClassFile);
		FileUtils.writeStringToFile(javaClassFile, classDef.generateJavaClass(), false);
		
	}
	
	
	public static void addInterfaceMethods (String aEntityClassName, JavaInterfaceDef aInterfaceDef) {
		
		JavaMethodDef method = new JavaMethodDef(getInsertMethodName(aEntityClassName), aEntityClassName + " a" + aEntityClassName);
		method.setAccessIdentifier(JavaMethodDef.DEFAULT_ACCESS_IDENTIFIER);
		method.addMethodException("SQLException");
		method.setAbstractMethod(true);
		method.setReturnType("int");
		
		aInterfaceDef.addMethod(method);
		
		
		method = new JavaMethodDef(getDeleteMethodName(aEntityClassName), aEntityClassName + " a" + aEntityClassName);
		method.addMethodException("SQLException");
		method.setAccessIdentifier(JavaMethodDef.DEFAULT_ACCESS_IDENTIFIER);
		method.setReturnType("int");
		method.setAbstractMethod(true);		
		aInterfaceDef.addMethod(method);
		
		
		method = new JavaMethodDef(getUpdateMethodName(aEntityClassName), aEntityClassName + " a" + aEntityClassName);
		method.addMethodException("SQLException");
		method.setAccessIdentifier(JavaMethodDef.DEFAULT_ACCESS_IDENTIFIER);
		method.setReturnType("int");
		method.setAbstractMethod(true);
		aInterfaceDef.addMethod(method);
		
		
		method = new JavaMethodDef(getSelectMethodName(aEntityClassName), aEntityClassName + " a" + aEntityClassName);
		method.setReturnType(aEntityClassName);
		method.addMethodException("SQLException");
		method.setAccessIdentifier(JavaMethodDef.DEFAULT_ACCESS_IDENTIFIER);
		method.setAbstractMethod(true);
		aInterfaceDef.addMethod(method);

	}
	
	private static String getInsertMethodName (String aEntityClassName) {
		return "insert" + aEntityClassName;
	}
	
	private static String getUpdateMethodName (String aEntityClassName) {
		return "update" + aEntityClassName;
	}
	
	private static String getDeleteMethodName (String aEntityClassName) {
		return "delete" + aEntityClassName;
	}
	
	private static String getSelectMethodName (String aEntityClassName) {
		return "fetch" + aEntityClassName;
	}
	
//	private static String getSelectAllMethodName (String aEntityClassName) {
//		return "fetchAll" + aEntityClassName;
//	}
	
	private static void addInsertMethod (String aEntityClassName, JavaClassDef aJavaClassDef, TableDefinition aTableDefinition, Map<String, JavaFieldDef> aColumnNameToFieldMap) throws Exception {
		addInsertStatementMethod(aJavaClassDef, aTableDefinition, aColumnNameToFieldMap, aEntityClassName);
		
		String classParamName = "a"+ aEntityClassName;
		JavaMethodDef method = new JavaMethodDef(getInsertMethodName(aEntityClassName), aEntityClassName + " " + classParamName);
		method.setAccessIdentifier(JavaMethodDef.PUBLIC_ACCESS_IDENTIFIER);
		method.setReturnType("int");
		method.addMethodException("SQLException");
		method.addAnnotation("@Override");
		
		StringBuilder sb = new StringBuilder();
		sb.append("\t\treturn getJdbcTemplate().update(new PreparedStatementCreator() {\n");
		sb.append("\t\t\t@Override\n");
		sb.append("\t\t\tpublic PreparedStatement createPreparedStatement(Connection aCon) throws SQLException {\n");
		sb.append("\t\t\t\treturn buildInsertStatement(aCon, a" + aEntityClassName + ");\n");
		sb.append("\t\t\t}\n");
		sb.append("\t\t});\n");
		
		method.addMethodImplementation(sb.toString());
		aJavaClassDef.addMethod(method);
	}
	
	public static void addInsertStatementMethod (JavaClassDef aClassDef, TableDefinition aTableDefinition, Map<String, JavaFieldDef> aColumnNameToFieldMap, String aEntityClassName) throws Exception {
		
		String classParamName = "a"+ aEntityClassName;
		JavaMethodDef method = new JavaMethodDef("buildInsertStatement", "Connection aConection, " + aEntityClassName + " " + classParamName);
		method.setAccessIdentifier(JavaMethodDef.PRIVATE_ACCESS_IDENTIFIER);
		
		method.setReturnType("PreparedStatement");
		method.addMethodException("SQLException");
		String sqlString = InsertSqlStatementGenerator.buildInsertSqlStatement (aTableDefinition);
		String sqlStatmentVar = "String sqlStatement = \"" + sqlString + "\";\n";
		StringBuilder sb = new StringBuilder();
		sb.append("\t\t" + sqlStatmentVar).append("\n");
		sb.append("\t\t").append("PreparedStatement prepareStatement = aConection.prepareStatement(sqlStatement);").append("\n");
		sb.append(getSetterStatements(aClassDef, aTableDefinition, aColumnNameToFieldMap, aEntityClassName));
		sb.append("\t\t").append("return prepareStatement;");
		
		method.addMethodImplementation(sb.toString());
				
		aClassDef.addMethod(method);
		
	}
	
	private static void addDeleteMethodImpl (JavaClassDef aClassDef, TableDefinition aTableDefinition, Map<String, JavaFieldDef> aColumnNameToFieldMap, String aEntityClassName, List<ColumnDefinition> aPrimarykeyColumns, List<ColumnDefinition> aNonPrimarykeyColumns) throws Exception {
		
		String classParamName = "a"+ aEntityClassName;
		JavaMethodDef method = new JavaMethodDef(getDeleteMethodName(aEntityClassName), aEntityClassName + " " + classParamName);
		method.setAccessIdentifier(JavaMethodDef.PUBLIC_ACCESS_IDENTIFIER);
		method.setReturnType("int");
		method.addMethodException("SQLException");
		method.addAnnotation("@Override");
		
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("\t\treturn getJdbcTemplate().update(new PreparedStatementCreator() {\n");
		sb.append("\t\t\t@Override\n");
		sb.append("\t\t\tpublic PreparedStatement createPreparedStatement(Connection aConection) throws SQLException {\n");
		String sqlStatmentVar = "String deleteSqlQuery = \"" + buildDeleteQuery(aTableDefinition, aPrimarykeyColumns, aNonPrimarykeyColumns) + "\";\n";
		sb.append("\t\t\t\t" + sqlStatmentVar);
		sb.append("\t\t\t\t" + "PreparedStatement prepareStatement = aConection.prepareStatement(deleteSqlQuery);").append("\n");
		
		int index = 0;
		for ( ColumnDefinition columnDef : aPrimarykeyColumns ) {
			index++;
			JavaFieldDef javaFieldDef = aColumnNameToFieldMap.get(columnDef.getColumnName());
			String getterMethodName = javaFieldDef.getGetterMethodName();
			sb.append("\t\t\t\t").append(SqlStatementHelper.generateSetterStatement(columnDef.getDataType(), String.valueOf(index), classParamName + "." + getterMethodName + "()"));
			sb.append("\n");
		}
		sb.append("\t\t\t\t").append("return prepareStatement;\n");
		sb.append("\t\t\t}\n");
		sb.append("\t\t});\n");
		
		method.addMethodImplementation(sb.toString());
		aClassDef.addMethod(method);
	}
	
	private static String buildDeleteQuery ( TableDefinition aTableDefinition,
			List<ColumnDefinition> aPrimarykeyColumns, List<ColumnDefinition> aNonPrimarykeyColumns ) throws Exception {

		if ( aPrimarykeyColumns.isEmpty() ) {
			throw new Exception( "No primary key define for table " + aTableDefinition.getTableName()
					+ ". Failed to create delete statement.");
		}

		StringBuilder query = new StringBuilder( "DELETE FROM " );

		if ( !StringUtils.isEmpty( aTableDefinition.getSchemaName() ) ) {
			query.append( aTableDefinition.getSchemaName() ).append( "." );
		}
		query.append( aTableDefinition.getTableName() );
		query.append( " WHERE " );

		boolean firstCol = true;
		// Loop thru all primary key and add to where clause.
		for ( ColumnDefinition columnDefinition : aPrimarykeyColumns ) {
			if ( firstCol ) {
				firstCol = false;
			}
			else {
				query.append( " and " );
			}
			query.append( columnDefinition.getColumnName() ).append( " = ?" );
		}

		return query.toString();
	}
	
	private static void addUpdateMethodImpl(JavaClassDef aClassDef, TableDefinition aTableDefinition,
			Map<String, JavaFieldDef> aColumnNameToFieldMap, String aEntityClassName,
			List<ColumnDefinition> aPrimarykeyColumns, List<ColumnDefinition> aNonPrimarykeyColumns) throws Exception {

		addBuildUpdateStatement(aClassDef, aTableDefinition, aColumnNameToFieldMap, aPrimarykeyColumns,
				aNonPrimarykeyColumns, aEntityClassName);

		String classParamName = "a" + aEntityClassName;
		JavaMethodDef method = new JavaMethodDef(getUpdateEntityMethodName(aEntityClassName),
				aEntityClassName + " " + classParamName);
		method.setReturnType("int");
		method.addMethodException("SQLException");

		method.addAnnotation("@Override");
		// method.addAnnotation("@Transactional (transactionManager=
		// \"transactionManager\", propagation = Propagation.REQUIRED, readOnly = false,
		// rollbackFor = Exception.class)");

		StringBuilder sb = new StringBuilder();

		sb.append("\t\treturn getJdbcTemplate().update(new PreparedStatementCreator() {\n");
		sb.append("\t\t\t@Override\n");
		sb.append("\t\t\tpublic PreparedStatement createPreparedStatement(Connection aCon) throws SQLException {\n");
		sb.append("\t\t\t\treturn buildUpdateStatement(aCon, a" + aEntityClassName + ");\n");
		sb.append("\t\t\t}\n");
		sb.append("\t\t});\n");

		method.addMethodImplementation(sb.toString());
		aClassDef.addMethod(method);

	}
	
	private static void addBuildUpdateStatement (JavaClassDef aClassDef, TableDefinition aTableDefinition,Map<String, JavaFieldDef> aColumnNameToFieldMap, 
			List<ColumnDefinition> aPrimarykeyColumns, List<ColumnDefinition> aNonPrimarykeyColumns, String aEntityClassName) throws Exception {
			String classParamName = "a"+ aEntityClassName;
			JavaMethodDef method = new JavaMethodDef("buildUpdateStatement", "Connection aConection, " + aEntityClassName + " " + classParamName);
			method.setReturnType("PreparedStatement");
			method.addMethodException("SQLException");
			String sqlString = buildUpdateQuery (aTableDefinition, aPrimarykeyColumns, aNonPrimarykeyColumns);
			String sqlStatmentVar = "String updateStatement = \"" + sqlString + "\";\n";
			StringBuilder sb = new StringBuilder();
			sb.append("\t\t" + sqlStatmentVar).append("\n");
			sb.append("\t\t").append("PreparedStatement prepareStatement = aConection.prepareStatement(updateStatement);").append("\n");
			
			sb.append(getSetterStatementsForUpdateQuery(aClassDef, aTableDefinition, aColumnNameToFieldMap, aEntityClassName, aPrimarykeyColumns, aNonPrimarykeyColumns));
			sb.append("\t\t").append("return prepareStatement;");
	
			method.addMethodImplementation(sb.toString());
			
			aClassDef.addMethod(method);		
	
	}
	
	private static String buildUpdateQuery ( TableDefinition aTableDefinition,
			List<ColumnDefinition> aPrimarykeyColumns, List<ColumnDefinition> aNonPrimarykeyColumns ) throws Exception {

		if ( aPrimarykeyColumns.isEmpty() ) {
			throw new Exception( "No primary key define for table " + aTableDefinition.getTableName()
					+ ". Failed to create update statement.");
		}

		if ( aNonPrimarykeyColumns.isEmpty() ) {
			throw new Exception(
					"No column in table to update. "
							+ "Either all columns are in ignore list or all columns are primary key");
		}

		StringBuilder query = new StringBuilder( "UPDATE " );

		if ( !StringUtils.isEmpty( aTableDefinition.getSchemaName() ) ) {
			query.append( aTableDefinition.getSchemaName() ).append( "." );
		}
		query.append( aTableDefinition.getTableName() );
		query.append( " SET " );

		boolean firstCol = true;
		for ( ColumnDefinition columnDefinition : aNonPrimarykeyColumns ) {
			
			if ( firstCol ) {
				firstCol = false;
			}
			else {
				query.append( ", " );
			}
			query.append( columnDefinition.getColumnName() ).append( " = ? " );
		}

		// Build where clause now
		query.append( " where " );

		firstCol = true;
		// Loop thru all primary key and add to where clause.
		for ( ColumnDefinition columnDefinition : aPrimarykeyColumns ) {
			if ( firstCol ) {
				firstCol = false;
			}
			else {
				query.append( " and " );
			}
			query.append( columnDefinition.getColumnName() ).append( " = ?" );
		}

		return query.toString();
	}
	
	private static void addFetchMethodImpl (String aEntityClassName, JavaClassDef aJavaClassDef, TableDefinition aTableDefinition, Map<String, JavaFieldDef> aColumnNameToFieldMap) {
		String entityClassParamName = "a"+ aEntityClassName;
		JavaMethodDef method = new JavaMethodDef(getSelectMethodName (aEntityClassName), aEntityClassName + " " + entityClassParamName);
		method.setReturnType(aEntityClassName);
		method.addMethodException("SQLException");
		method.addAnnotation("@Override");
		//method.addAnnotation("@Transactional (transactionManager= \"transactionManager\", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)");
		
		StringBuilder sb = new StringBuilder();
		boolean firstCol = true;
		String selectQueryColNames = " "; 
		for(ColumnDefinition columnDefinition : aTableDefinition.getColumnDefinitions()) {
			if(firstCol) {
				firstCol = false;
			} else {
				selectQueryColNames = selectQueryColNames + ", ";
			}
			selectQueryColNames = selectQueryColNames + columnDefinition.getColumnName() + " ";
		}
		sb.append("\t\tString selectQuery = \"select " + selectQueryColNames + " from " + aTableDefinition.getTableName());
		firstCol = true;
		for(ColumnDefinition columnDefinition : aTableDefinition.getColumnDefinitions()) {
			if(columnDefinition.isPrimaryKey()) {
				if(firstCol) {
					firstCol = false;
					sb.append(" where ");
				} else {
					sb.append( " and ");
				}
				sb.append( columnDefinition.getColumnName() + " = ? " );
			}
		}
		sb.append("\";\n");
		sb.append("\t\treturn getJdbcTemplate().queryForObject(selectQuery, new " + RowMapperGenerator.getRowMapperClassName(aEntityClassName) + "()");
		for(ColumnDefinition columnDefinition : aTableDefinition.getColumnDefinitions()) {
			if(columnDefinition.isPrimaryKey()) {
					sb.append(", ");
				
				JavaFieldDef javaFieldDef = aColumnNameToFieldMap.get(columnDefinition.getColumnName());
				String getterMethodName = javaFieldDef.getGetterMethodName();
				sb.append(entityClassParamName + "." + getterMethodName + "()");
			}
		}
		sb.append(");\n");
		
		method.addMethodImplementation(sb.toString());
		aJavaClassDef.addMethod(method);
		
	}
	
	
	
	private static String getSetterStatementsForUpdateQuery (JavaClassDef aClassDef, TableDefinition aTableDefinition, Map<String, JavaFieldDef> aColumnNameToFieldMap, String aEntityClassName, List<ColumnDefinition> aPrimarykeyColumns, List<ColumnDefinition> aNonPrimarykeyColumns) throws Exception {
		
		String classParamName = "a"+ aEntityClassName;
		int index = 0;
		StringBuilder sb = new StringBuilder();
		for ( ColumnDefinition columnDef : aNonPrimarykeyColumns ) {
			index++;
			JavaFieldDef javaFieldDef = aColumnNameToFieldMap.get(columnDef.getColumnName());
			String getterMethodName = javaFieldDef.getGetterMethodName();
			sb.append("\t\t").append(SqlStatementHelper.generateSetterStatement(columnDef.getDataType(), String.valueOf(index), classParamName + "." + getterMethodName + "()"));
			sb.append("\n");
		}
		
		for ( ColumnDefinition columnDef : aPrimarykeyColumns ) {
			index++;
			JavaFieldDef javaFieldDef = aColumnNameToFieldMap.get(columnDef.getColumnName());
			String getterMethodName = javaFieldDef.getGetterMethodName();
			sb.append("\t\t").append(SqlStatementHelper.generateSetterStatement(columnDef.getDataType(), String.valueOf(index), classParamName + "." + getterMethodName + "()"));
			sb.append("\n");
		}
		return sb.toString();
	}
	
	
	
	private static String getUpdateEntityMethodName (String aEntityClassName) {
		return "update" + aEntityClassName;
	}
	
	private static String getSetterStatements (JavaClassDef aClassDef, TableDefinition aTableDefinition, Map<String, JavaFieldDef> aColumnNameToFieldMap, String aEntityClassName) throws Exception {
		List<ColumnDefinition> columnsToAddInInsertStatement = new ArrayList<>();
		// Loop thru all column and create a primary column list
		for ( ColumnDefinition columnDefinition : aTableDefinition.getColumnDefinitions() ) {
			// continue if this column to ignore on target database.
			if ( columnDefinition.isIgnoreColumn() ) {
				continue;
			}
			if ( columnDefinition.isAutoGenerated()) {
				continue;
			}
			columnsToAddInInsertStatement.add( columnDefinition );
		}
		String classParamName = "a"+ aEntityClassName;
		int index = 0;
		StringBuilder sb = new StringBuilder();
		for ( ColumnDefinition columnDef : columnsToAddInInsertStatement ) {
			index++;
			 JavaFieldDef javaFieldDef = aColumnNameToFieldMap.get(columnDef.getColumnName());
			 String getterMethodName = javaFieldDef.getGetterMethodName();
			sb.append("\t\t").append(SqlStatementHelper.generateSetterStatement(columnDef.getDataType(), String.valueOf(index), classParamName + "." + getterMethodName + "()"));
			sb.append("\n");
		}
		return sb.toString();
	}
}
