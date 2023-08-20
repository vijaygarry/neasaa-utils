package com.neasaa.csv.parser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class AbstractCSVParser {
	
	private final String csvFilePath;
	private List<String> columnNames;
	private boolean skipHeaderRecord = false;
	private char delimiter = ',';
	
	private CSVParser parser = null;
	
	public AbstractCSVParser(String aCsvFilePath) {
		super();
		this.csvFilePath = aCsvFilePath;
	}

	public void setColumnNames(List<String> aColumnNames) {
		this.columnNames = aColumnNames;
	}
	
	public void setSkipHeaderRecord(boolean aSkipHeaderRecord) {
		this.skipHeaderRecord = aSkipHeaderRecord;
	}
	
	public void setDelimiter(char aDelimiter) {
		this.delimiter = aDelimiter;
	}
	
	public void initParser () throws IOException {
		String[] colNameArray = null;
		if(this.columnNames != null) {
			colNameArray = new String[this.columnNames.size()];
			for(int i=0; i< this.columnNames.size(); i++) {
				colNameArray[i] = this.columnNames.get(i).toUpperCase();
			}
		}
		
		CSVFormat format = CSVFormat.DEFAULT.builder()
				.setSkipHeaderRecord(skipHeaderRecord)
				.setDelimiter(delimiter)
				.setHeader(colNameArray)
				.build();
		
		this.parser = CSVParser.parse(new File(this.csvFilePath), Charset.defaultCharset(), format);
	}
	
	public String getCsvFilePath() {
		return this.csvFilePath;
	}
	
	public long getCurrentLineNumber () {
		return this.parser.getCurrentLineNumber();
	}
	
	public boolean hasNext () {
		return this.parser.iterator().hasNext();
	}
	
	public CSVRecord next () {
		return this.parser.iterator().next();
	}

	public void close () {
		try {
			this.parser.close();
		} catch (IOException ex) {
		}
	}

}
