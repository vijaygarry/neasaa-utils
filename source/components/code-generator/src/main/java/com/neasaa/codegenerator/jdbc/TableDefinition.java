package com.neasaa.codegenerator.jdbc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neasaa.util.StringUtils;

public class TableDefinition {

	private final String tableName;
	private final String schemaName = null;
	private final List<ColumnDefinition> columnDefinitions;
	private final Map<String, ColumnDefinition> sourceColumnMap;

	public TableDefinition(String aTableName, List<ColumnDefinition> aColumnDefinitions) throws Exception {
		super();

		if (StringUtils.isEmpty(aTableName)) {
			throw new Exception("Table Name is not defined");
		} else {
			this.tableName = normalizeTableNameToUpper(aTableName);
		}

		this.columnDefinitions = aColumnDefinitions;
		this.sourceColumnMap = new HashMap<>();
		for (ColumnDefinition column : aColumnDefinitions) {
			this.sourceColumnMap.put(column.getColumnName(), column);
		}
	}

	public ColumnDefinition getColumnDefinitionBySouceColumnName(String aSourceColumnName) {
		return this.sourceColumnMap.get(aSourceColumnName);
	}

	/**
	 * Trim and change the case to upper.
	 * 
	 * @param aTableName
	 * @return
	 */
	private String normalizeTableNameToUpper(String aTableName) {
		return aTableName.trim().toUpperCase();
	}

	public String getTableName() {
		return this.tableName;
	}

	public String getSchemaName() {
		return this.schemaName;
	}
	
	public List<ColumnDefinition> getColumnDefinitions() {
		return this.columnDefinitions;
	}

	public Map<String, ColumnDefinition> getSourceColumnMap() {
		return this.sourceColumnMap;
	}

	@Override
	public String toString() {
		return "TableDefinition [tableName=" + this.tableName + ", columnDefinitions=" + this.columnDefinitions + "]";
	}

}
