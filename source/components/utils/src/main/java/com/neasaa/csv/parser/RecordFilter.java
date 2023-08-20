package com.neasaa.csv.parser;

public interface RecordFilter {
	/**
	 * Return true to include in result
	 * @param rowLineContent
	 * @return
	 */
	boolean includeInResult (String rowLineContent);
	
}
