package com.neasaa.csv.parser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.neasaa.util.FileUtils;

public class CSVFileProcessor<T> {
	
	private CSVParser parser = null;
	private CSVRowMapper<T> rowMapper = null;
	
	private CSVFileProcessor (CSVParser parser, CSVRowMapper<T> rowMapper) throws IOException {
		this.parser = parser;
		this.rowMapper = rowMapper;
	}
	
	public static class Builder <T> {
		private String csvFilePath;
		private char delimiter = ',';
		private boolean skipHeaderRecord = true;
		private List<String> columnNames = null;
		private CSVRowMapper<T> rowMapper = null;
		
		public Builder<T> csvFilePath (String csvFilePath) {
			this.csvFilePath = csvFilePath;
			return this;
		}
		
		public Builder<T> delimiter (char delimiter) {
			this.delimiter = delimiter;
			return this;
		}
		
		public Builder<T> skipHeaderRecord (boolean skipHeaderRecord) {
			this.skipHeaderRecord = skipHeaderRecord;
			return this;
		}
		
		public Builder<T> columnNames (List<String> columnNames) {
			this.columnNames = columnNames;
			return this;
		}
		
		public Builder<T> rowMapper (CSVRowMapper<T> rowMapper) {
			this.rowMapper = rowMapper;
			return this;
		}
		
		public CSVFileProcessor<T> build() throws IOException {
			if(csvFilePath == null) {
				throw new RuntimeException("CSV Filepath is not specified");
			}
			if(!FileUtils.isFileExists(csvFilePath)) {
				throw new RuntimeException("CSV Filepath " + csvFilePath + " does not exists");
			}
			if(rowMapper == null) {
				throw new RuntimeException("Row mapper not specified");
			}
			
			String[] colNameArray = null;
			if(this.columnNames != null) {
				colNameArray = new String[this.columnNames.size()];
				for(int i=0; i< this.columnNames.size(); i++) {
					colNameArray[i] = this.columnNames.get(i);
				}
			}
			
			CSVFormat format = CSVFormat.DEFAULT.builder()
					.setSkipHeaderRecord(skipHeaderRecord)
					.setDelimiter(this.delimiter)
					.setHeader(colNameArray)
					.setIgnoreEmptyLines(true)
					.setTrim(true)
					.build();
			
			CSVParser parser = CSVParser.parse(new File(this.csvFilePath), Charset.defaultCharset(), format);
			return new CSVFileProcessor<T>(parser, rowMapper);
		}
	}
	
	
	public List<T> loadRecords () throws IOException {
		List<T> entities = new ArrayList<>();
		while (parser.iterator().hasNext()) {
			T entity = null;
			String recordString = null;
			try {
				CSVRecord record = parser.iterator().next();
				recordString = record.toString();
				entity = rowMapper.mapRow(record, parser.getCurrentLineNumber());
			} catch (Exception e) {
				throw new RuntimeException("Failed to parse " + recordString, e);
			}
			if(entity == null) {
				continue;
			}
			entities.add(entity);
		}
		return entities;
		
	}
	
	public void close () {
		try {
			this.parser.close();
		} catch (IOException ex) {
		}
	}
	
	//skipComments - Skip row if row starts with comment like # or //
	
	
	
	public long getCurrentLineNumber () {
		return this.parser.getCurrentLineNumber();
	}
	
	public boolean hasNext () {
		return this.parser.iterator().hasNext();
	}
	
	public CSVRecord next () {
		return this.parser.iterator().next();
	}
}
