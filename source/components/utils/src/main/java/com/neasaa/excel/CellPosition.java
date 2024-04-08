package com.neasaa.excel;

public class CellPosition {
	private int row;
	private int column;
	
	public CellPosition(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

	public ExcelCell getExcelCell (Object value) {
		return new ExcelCell (row, column, value);
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
}
