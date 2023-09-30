package com.neasaa.util.table;

import java.util.ArrayList;
import java.util.List;

import com.neasaa.util.StringUtils;

/**
 * Class representing table. This utility class can be used to print the table on console or create CSV file.
 * 
 * @author Vijay Garothaya
 *
 */
public class Table {
	private String title;
	private TableRow header;
	private List<TableRow> rows = new ArrayList<>();
	private int maxNumberOfColumns = 0;
	
	public Table (TableRow header) {
		this(header, null);
	}
	
	public Table (TableRow header, String title) {
		super();
		this.title = title;
		this.header = header;
		this.maxNumberOfColumns = header.getCells().size();
	}
	
	public void addRow(TableRow row) {
		rows.add(row);
		int colCount = row.getCells().size();
		if(colCount > this.maxNumberOfColumns) {
			this.maxNumberOfColumns = colCount;
		}
	}
	
	public String getFormattedText() {
		List<Integer> columnWidth = getColumnWidth();
		int totalTableWidth = 1;
		for (Integer colWidth : columnWidth) {
			totalTableWidth = totalTableWidth + colWidth + 1;
		} 
			
		
		StringBuilder sb = new StringBuilder();
		if(title != null) {
			sb.append("|").append(StringUtils.centerPad(title, ' ', totalTableWidth-2));
			sb.append("|").append(StringUtils.NEW_LINE);
		}
		
		sb.append("|");
		for(int i=0; i< this.maxNumberOfColumns; i++) {
			sb.append(StringUtils.getStringWithChar('-', columnWidth.get(i)));
			//If last column
			if(i == this.maxNumberOfColumns-1) {
				sb.append("|");	
			} else {
				sb.append("-");
			}
		}
		sb.append(StringUtils.NEW_LINE);
		
		addRowText(header, sb, columnWidth);
		sb.append("|");
		for(int i=0; i< this.maxNumberOfColumns; i++) {
			sb.append(StringUtils.getStringWithChar('-', columnWidth.get(i)));
			//If last column
			if(i == this.maxNumberOfColumns-1) {
				sb.append("|");	
			} else {
				sb.append("|");
			}
		}
		sb.append(StringUtils.NEW_LINE);
		
		rows.forEach(r -> {
			addRowText(r, sb, columnWidth);	
		});
		sb.append("|");
		for(int i=0; i< this.maxNumberOfColumns; i++) {
			sb.append(StringUtils.getStringWithChar('-', columnWidth.get(i)));
			//If last column
			if(i == this.maxNumberOfColumns-1) {
				sb.append("|");	
			} else {
				sb.append("-");
			}
		}
		sb.append(StringUtils.NEW_LINE);
		return sb.toString();
	}
	
	private void addRowText (TableRow row, StringBuilder sb, List<Integer> columnWidth) {
		List<TableCell> cells = row.getCells();
		sb.append("|");
		for(int i=0; i< this.maxNumberOfColumns; i++) {
			String cellValue = (cells.size() > i) ? cells.get(i).getValue() : "";
			sb.append(StringUtils.leftPad(cellValue, ' ', columnWidth.get(i))).append("|");
		}
		sb.append(StringUtils.NEW_LINE);		
	}
	
	private List<Integer> getColumnWidth() {
		List<Integer> columnWidths = new ArrayList<>(this.maxNumberOfColumns);
		for(int i=0; i< this.maxNumberOfColumns; i++) {
			columnWidths.add(1);
		}
		for(int i=0; i<header.getCells().size(); i++) {
			TableCell cell = header.getCells().get(i);
			columnWidths.set(i, cell.getValue().length());
		}
		for(int r=0; r < rows.size(); r++) {
			TableRow row = rows.get(r);
			for(int c=0; c<row.getCells().size(); c++) {
				TableCell cell = row.getCells().get(c);
				int width = cell.getValue().length();
				if(width > columnWidths.get(c)) {
					columnWidths.set(c, width);	
				}
			}
		}
		return columnWidths;
	}
}
