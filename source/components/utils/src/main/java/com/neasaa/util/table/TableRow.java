package com.neasaa.util.table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TableRow {
	private List<TableCell> cells = new ArrayList<>();
	
	public static TableRow getRow (List<String> values) {
		TableRow row = new TableRow();
		values.forEach(s -> {
			row.addCell(new TableCell(s));
		});
		return row;
	}
	
	public static TableRow getRow (String...values) {
		return getRow(Arrays.asList(values));
	}
	
	public TableRow addCell(TableCell cell) {
		cells.add(cell);
		return this;
	}
	
	public void setCells(List<TableCell> cells) {
		this.cells = cells;
	}
	
	public List<TableCell> getCells() {
		return cells;
	}
}
