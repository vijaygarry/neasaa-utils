package com.neasaa.util.table;

public class TableUsage {
	public static void main(String[] args) {
		TableRow header = TableRow.getRow("Header 1", "H2", "Cost", "Effective cost", "Profit/Loss");
		TableRow row1 = TableRow.getRow("Sample Value", "Value", "10.23", "23.45", "90,889");
		TableRow row2 = TableRow.getRow("Sample Value1", "V", "10", "23,245", "89");
		
		TableRow row3 = TableRow.getRow("Sample Value1", "V", "10");
		TableRow row4 = TableRow.getRow("Sample Value1", "V", "", "89.90", "2000");
		TableRow row5 = TableRow.getRow("Sample Value1", "V", "more", "89.90", "2000", "Extra cell");
		
		Table table = new Table(header);
		table.addRow(row1);
		table.addRow(row2);
		table.addRow(row3);
		table.addRow(row4);
		table.addRow(row5);
		
		System.out.println(table.getFormattedText());
	}
}
