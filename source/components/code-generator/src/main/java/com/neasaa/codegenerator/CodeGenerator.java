package com.neasaa.codegenerator;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import com.neasaa.codegenerator.jdbc.CodeGeneratorHelper;
import com.neasaa.codegenerator.jdbc.DBMetaDataHelper;
import com.neasaa.codegenerator.jdbc.DbHelper;
import com.neasaa.codegenerator.jdbc.EntityClassGenerator;
import com.neasaa.codegenerator.jdbc.EntityDaoClassGenerator;
import com.neasaa.codegenerator.jdbc.RowMapperGenerator;
import com.neasaa.codegenerator.jdbc.TableDefinition;
import com.neasaa.util.StringUtils;
import com.neasaa.util.config.BaseConfig;

public class CodeGenerator {
	
	public static void main(String[] args) throws Exception {
		
		CodeGenerationCliParameter cliParams = CodeGenerationCliParameter.parseCommandLineParams(args);
		
		String [] configFiles;
		if(cliParams.getApplicationConfigFilename() != null) {
			configFiles = new String[2];
			configFiles[0] =  "metadata.config";
			configFiles[1] =  cliParams.getApplicationConfigFilename();
		} else {
			configFiles = new String[1];
			configFiles[0] =  "metadata.config";
		}
		try {
			System.out.println( "Loading configurtion file " + Arrays.asList(configFiles));
			BaseConfig.initialize(configFiles);
		} catch (Exception ex) {
			System.err.println("Fail to load configuration file. " + ex.getMessage());
			ex.printStackTrace();
			System.exit(1);
		}

		Connection connection = null;
		try {
			connection = DbHelper.getDbConnection();
		} catch (Exception ex) {
			System.err.println("Fail to create db connection");
			ex.printStackTrace();
			System.exit(2);
		}
		
		List<CodeGeneratorMode> modesToRun = cliParams.getModes();
		
		List<String> tableList;
		for (CodeGeneratorMode mode : modesToRun) {
			System.out.println("\n\nProcessing mode : " + mode.name());
			System.out.println(StringUtils.rightPad("", '=', 60));
			switch (mode) {
			case DB_TABLE_LIST:
				//Print table names
				CodeGeneratorHelper.printAllTableDetailsForSchema(connection, "");
				break;
			case DB_TABLE_DETAILS:
				//Print Table details
				tableList = BaseConfig.getListProperty("TABLE.LIST");
				CodeGeneratorHelper.printTableDetails(connection, tableList, null);
				break;
			case TABLE_CREATE_ENTITY_CLASS:
				tableList = BaseConfig.getListProperty("TABLE.LIST");
				createTableEntityCode (connection, tableList, null);
				break;
			case TABLE_CREATE_ROWMAPPER_CLASS:
				tableList = BaseConfig.getListProperty("TABLE.LIST");
				createTableRowMapperCode (connection, tableList, null);
				break;
			case TABLE_CREATE_DAO_CLASS:
				tableList = BaseConfig.getListProperty("TABLE.LIST");
				createTableDaoCode  (connection, tableList, null);
				break;
			default:
				System.err.println ( "Code generation mode " + mode.name() + " not implemented.");
				System.exit(1);
			}
		}
	}
	
	
	private static void createTableEntityCode (Connection aConnection, List<String> aTableNames, String aSchemaName) throws Exception {
		for(String tableName: aTableNames) {
			TableDefinition tableDefinition = DBMetaDataHelper.getTableDefinition (aConnection, tableName, aSchemaName);
			EntityClassGenerator.generateEntityClassForTable(tableDefinition);
		}		
	}
	
	private static void createTableRowMapperCode (Connection aConnection, List<String> aTableNames, String aSchemaName) throws Exception {
		for(String tableName: aTableNames) {
			TableDefinition tableDefinition = DBMetaDataHelper.getTableDefinition (aConnection, tableName, aSchemaName);
			String entityClassName = DbHelper.getClassNameFromTableName (tableDefinition.getTableName());
			RowMapperGenerator.generateRowMapperClassForTable( entityClassName, tableDefinition);
		}
	}
	
	private static void createTableDaoCode (Connection aConnection, List<String> aTableNames, String aSchemaName) throws Exception {
		for(String tableName: aTableNames) {
			TableDefinition tableDefinition = DBMetaDataHelper.getTableDefinition (aConnection, tableName, aSchemaName);
			String entityClassName = DbHelper.getClassNameFromTableName (tableDefinition.getTableName());
			boolean doNotGenerateDaoInterface = BaseConfig.getBooleanProperty(CodeGeneratorConstants.JAVA_DAO_DO_NOT_CREATE_INTERFACE, false);
			if(!doNotGenerateDaoInterface) {
				EntityDaoClassGenerator.generateDaoInterfaceCode(entityClassName);
			}
			EntityDaoClassGenerator.generateDaoImplCode(entityClassName, tableDefinition);
		}
	}
	
}
