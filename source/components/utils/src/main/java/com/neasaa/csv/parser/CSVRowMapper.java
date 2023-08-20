package com.neasaa.csv.parser;

import java.math.BigDecimal;
import java.util.Iterator;

import org.apache.commons.csv.CSVRecord;

import com.neasaa.util.StringUtils;

public abstract class CSVRowMapper<T> {
	
	private RecordFilter recordFilter;
	
	public CSVRowMapper () {
		this(null);
	}
	
	public CSVRowMapper(RecordFilter recordFilter) {
		this.recordFilter = recordFilter;
	}
	
	public String getCSVColumnValue (CSVRecord aCSVRecord, String aColumnName) {
//		String columnName = aColumnName.toUpperCase();
		if(aCSVRecord.isSet(aColumnName)) {
			return aCSVRecord.get(aColumnName);
		}
		return null;
	}
	
	protected long getLongValue (CSVRecord aCSVRecord, String aColumnName) {
		String strValue = aCSVRecord.get(aColumnName);
		if(StringUtils.isEmpty(strValue)) {
			throw new RuntimeException ("Column value is not specified for " + aColumnName);
		}
		return StringUtils.stringToLong(strValue);
	}
	
	protected BigDecimal getBigDecimalValue (CSVRecord aCSVRecord, String aColumnName) {
		String strValue = aCSVRecord.get(aColumnName);
		if(StringUtils.isEmpty(strValue)) {
			throw new RuntimeException ("Column value is not specified for " + aColumnName);
		}
		return new BigDecimal(strValue);
	}
	
	protected BigDecimal getPriceInBigDecimal (CSVRecord aCSVRecord, String aColumnName) {
		String strValue = aCSVRecord.get(aColumnName);
		if(StringUtils.isEmpty(strValue)) {
			throw new RuntimeException ("Column value is not specified for " + aColumnName);
		}
		if(strValue.startsWith("$")) {
			System.out.println("Original value: " + strValue);
			strValue = strValue.replaceAll("\\$", "");
			System.out.println("Udated value: " + strValue);
		}
		return new BigDecimal(strValue);
	}
	
	protected long getLongValue (CSVRecord aCSVRecord, String aColumnName, long aDefaultValue) {
		String strValue = aCSVRecord.get(aColumnName);
		if(StringUtils.isEmpty(strValue)) {
			return aDefaultValue;
		}
		return StringUtils.stringToLong(strValue);
	}
	
	protected String getStringValueForRow (CSVRecord aCSVRecord) {
		if(aCSVRecord == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		Iterator<String> iterator = aCSVRecord.iterator();
		while(iterator.hasNext()) {
			sb.append(iterator.next());
		}
		return sb.toString();
	}
	
	protected boolean includeInResult (String rowLineContent) {
		if(this.recordFilter == null) {
			return true;
		}
		return this.recordFilter.includeInResult(rowLineContent);
	}
	
	public abstract T mapRow(CSVRecord csvRecord, long rowNum);
	
}
