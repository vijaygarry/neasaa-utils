package com.neasaa.util.table;

public class TableCell {
	private Object value;
	//Center, Left, Right
//	private String alignment;
//	private String formatPattern;
	public TableCell(String value) {
		super();
		this.value = value;
	}
	
	public String getValue() {
		return (String)value;
	}
}
