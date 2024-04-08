package com.neasaa.excel;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellCopyPolicy;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;

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
	
	public void copyRows(int sourceStartRowNum, int sourceEndRowNum, int destinationStartRowNum) {
		if(this.excelSheet instanceof XSSFSheet) {
			XSSFSheet sheet = (XSSFSheet)this.excelSheet;
			sheet.copyRows(sourceStartRowNum, sourceEndRowNum, destinationStartRowNum, new CellCopyPolicy());
		} else {
			throw new RuntimeException("copyRows function is supported only for XSSFSheet. Current sheet is of type: " + this.excelSheet.getClass().getName());
		}
	}
	
	public void copyRow(int sourceRowNum, int destinationRowNum) {
		copyRows(sourceRowNum, sourceRowNum, destinationRowNum);
	}
	
	public void insertCopyRow(int sourceRowNum, int destinationRowNum) {
		insertRowAt(destinationRowNum);
		copyRows(sourceRowNum, sourceRowNum, destinationRowNum);
	}
	
	public void insertRowAt (int rowIndex) {
		Row newRow = getRow(rowIndex);
		
		// If the row exist in destination, push down all rows by 1 else create a new row
		if (newRow != null) {
			  this.excelSheet.shiftRows(rowIndex, this.excelSheet.getLastRowNum(), 1);
		}
		newRow = this.excelSheet.createRow(rowIndex);
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
	
	/**
	 * This method won't delete the row from excel instead will delete the value of
	 * the cells in row. 
	 * @see     #deleteRow(int)
	 * 
	 * @param aExcelRow
	 */
	public void removeRow(Row aExcelRow) {
		this.excelSheet.removeRow(aExcelRow);
	}
	
	/**
	 * This method will delete the row from excel sheet.
	 * 
	 * @param rowIndex
	 */
	public void deleteRow(int rowIndex) {
		int lastRowNum = this.excelSheet.getLastRowNum();
		if (rowIndex >= 0 && rowIndex < lastRowNum) {
			this.excelSheet.shiftRows(rowIndex + 1, lastRowNum, -1);
		}
		if (rowIndex == lastRowNum) {
			Row removingRow = this.excelSheet.getRow(rowIndex);
			if (removingRow != null) {
				this.excelSheet.removeRow(removingRow);
			}
		}
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
	
	public void mergeCells(int startRow, int endRow, int startCol, int endCol) {
		excelSheet.addMergedRegion(new CellRangeAddress(startRow, endRow, startCol, endCol));	
	}
	
	public void mergeCellsInRow(int rowNum, int startCol, int endCol) {
		excelSheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, startCol, endCol));	
	}
	
	public void mergeCellsInColumn(int startRow, int endRow, int colNum) {
		excelSheet.addMergedRegion(new CellRangeAddress(startRow, endRow, colNum, colNum));	
	}
}
