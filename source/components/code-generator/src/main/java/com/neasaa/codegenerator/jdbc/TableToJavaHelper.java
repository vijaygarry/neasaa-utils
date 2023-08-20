
package com.neasaa.codegenerator.jdbc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neasaa.codegenerator.java.JavaFieldDef;

/**
 * @author Vijay Garothaya
 * @version 1.0 Dec 5, 2018
 */
public class TableToJavaHelper {
	
	public static List<JavaFieldDef> getJavaFieldsFromColumns ( TableDefinition aTableDefinition ) {
		List<JavaFieldDef> fields = new ArrayList<>();
		Map<String, JavaFieldDef> columnNameToFieldMap = new HashMap<>();
		for ( ColumnDefinition colDef : aTableDefinition.getColumnDefinitions() ) {
			JavaFieldDef javaFieldDef = getJavaFieldDefFromCol( aTableDefinition, colDef );
			fields.add( javaFieldDef );
			columnNameToFieldMap.put( colDef.getColumnName(), javaFieldDef );
		}
		return fields;
	}
	
	public static JavaFieldDef getJavaFieldDefFromCol (TableDefinition aTableDefinition, ColumnDefinition aColDef) {
		return new JavaFieldDef( DbHelper.getJavaDatatypeFromSqlDataType( aColDef.getDataType() ),
				DbHelper.getFieldNameFromColumnName( aTableDefinition.getTableName(), aColDef.getColumnName() ) );
		
	}
}
