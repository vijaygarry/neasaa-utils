package com.neasaa.excel;
/**
 * Class representing excel sheet cell.
 * This represents cell with position and value.
 * 
 * @author vijay_garry
 *
 */
public class ExcelCell {
	//0 index
	private final int rowNum;
	//0 index
	private final int columnNum;
	
	//Excel sheet set the value base on data type
	private final Object value;
	
	public ExcelCell(int rowNum, int columnNum, Object value) {
		super();
		this.rowNum = rowNum;
		this.columnNum = columnNum;
		this.value = value;
	}
	
	
	public int getRowNum() {
		return rowNum;
	}
	
	public int getColumnNum() {
		return columnNum;
	}
	
	public Object getValue() {
		return value;
	}
}
