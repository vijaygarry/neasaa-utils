package com.neasaa.excel;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class ExcelSheet {
	private Sheet excelSheet;
	
	public ExcelSheet ( Sheet aExcelSheet ) {
		this.excelSheet = aExcelSheet;
	}
	
	
	/**
	 * Row is 0 index i.e. first row is at index 0.
	 * 
	 * @param aExcelSheet
	 * @param aRowNum
	 * @return
	 */
	public Row getRow ( int aRowNum ) {
		return this.excelSheet.getRow( aRowNum );
	}

	public Row createRow ( int aRowNum ) {
		return this.excelSheet.createRow( aRowNum );
	}

	public String getCellValue ( int aRowNum, int aColumnNum ) {
		return getCellValue( getCell ( aRowNum , aColumnNum));
	}
	
	public static String getCellValue ( Row aExcelRow, int aColumnNum ) {
		return getCellValue( getCell ( aExcelRow , aColumnNum));
	}
	
	public Cell getCell(int aRowNum, int aColumnNum) {
		Row row = getRow(aRowNum);
		return getCell (row, aColumnNum);
	}
	
	public static Cell getCell(Row aExcelRow, int aColumnNum) {
		if(aExcelRow == null) {
			return null;
		}
		
		Cell excelCell = aExcelRow.getCell(aColumnNum);
		return excelCell;
	}
	
	/**
	 * Last row number in sheet.
	 * @return
	 */
	public int getLastRowNum () {
		return this.excelSheet.getLastRowNum();
	}

	/**
	 * Last column number for specified row
	 * @return
	 */
	public int getLastCellNum (Row aRow) {
		return aRow.getLastCellNum();
	}
	
	/**
	 * Cell is 0 index. i.e. First cell in row is at index 0.
	 * 
	 * @param aExcelRow
	 * @param ColumnNum
	 * @return
	 */
	public static String getCellValue ( Cell aExcelCell ) {

		if ( aExcelCell == null ) {
			return null;
		}

		if ( aExcelCell.getCellType() == CellType.STRING ) {
			return aExcelCell.getStringCellValue();
		}
		else if ( aExcelCell.getCellType() == CellType.NUMERIC ) {			
			//return String.format("%1", excelCell.getNumericCellValue());
			return aExcelCell.toString();
		}
		else if(aExcelCell.getCellType() == CellType.FORMULA) {
			return aExcelCell.toString();
		}
		else {
			return aExcelCell.getStringCellValue();
		}
	}
	
	public void removeRow(Row aExcelRow) {
		this.excelSheet.removeRow(aExcelRow);
	}
	
	public void updateCellValue (int aRowNum, int aColumnNum, Object aValue) {
		Row row = getRow(aRowNum);
		if(row == null) {
			row = createRow(aRowNum);
		}
		updateCellValue (row, aColumnNum, aValue);
	}
	
	public void updateCellValue (Row aExcelRow, int aColumnNum, Object aValue) {
		if(aExcelRow == null) {
			return;
		}
		Cell cell = aExcelRow.getCell(aColumnNum);
		if(cell == null) {
			cell = aExcelRow.createCell(aColumnNum);
		}
		updateCellValue (cell, aValue);	
	}
	
	public static void updateCellValue ( Cell aExcelCell, Object aValue ) {
		if ( aExcelCell == null ) {
			return;
		}
		if(aValue instanceof Double) {
			aExcelCell.setCellValue((double)aValue);
		} else if(aValue instanceof BigDecimal) {
			aExcelCell.setCellValue(((BigDecimal)aValue).doubleValue());
		} else if(aValue instanceof Integer) {
			aExcelCell.setCellValue((int)aValue);
		} else if(aValue instanceof Date) {
			aExcelCell.setCellValue((Date)aValue);
		} else {
			aExcelCell.setCellValue(String.valueOf(aValue));
		}
	}
	
	public void updateCellValue ( ExcelCell excelCell ) {
		if ( excelCell == null ) {
			return;
		}
		updateCellValue(excelCell.getRowNum(), excelCell.getColumnNum(), excelCell.getValue());
	}
	
}
