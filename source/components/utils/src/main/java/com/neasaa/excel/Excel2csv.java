package com.neasaa.excel;

import java.util.List;

import org.apache.poi.ss.usermodel.Row;

import com.neasaa.util.FileUtils;


/**
 * @author Vijay Garothaya
 * @version 1.0 Jan 30, 2014
 */
public class Excel2csv {
	
	public static final String DEFAULT_DELIMITER = ","; 
	private static final String EMPTY_STRING = "";
	public static void main ( String[] args ) throws  Exception {
		workbook2csv(args[0], args[1]);
	}
	
	public static void workbook2csv(String aExcelFilepath, String aDestinationFolder) throws Exception {
		System.out.println("Excel File:" + aExcelFilepath);
		System.out.println("Destination Folder:" + aDestinationFolder);
		ExcelWorkBook workBook = new ExcelWorkBook( aExcelFilepath );
		workbook2csv( workBook, aDestinationFolder );
	}

	public static void workbook2csv(ExcelWorkBook aExcelWorkBook, String aDestinationFolder) throws Exception  {
		List<String> sheetNames = aExcelWorkBook.getExcelSheetNames();
		System.out.println("Sheets found in excel:" + sheetNames);
		for(String sheetName : sheetNames) {
			System.out.println("Processing Sheet:" + sheetName);
			sheet2csv(aExcelWorkBook.getExcelSheetByName( sheetName ), aDestinationFolder + "/" + sheetName + ".csv", DEFAULT_DELIMITER);
		}
	}
	
	public static void sheet2csv(ExcelSheet aExcelSheet, String aCsvFilename, String aDelimiter
			) throws Exception {
		String delimiter = aDelimiter;
		if(aDelimiter == null || aDelimiter.isEmpty()) {
			delimiter = DEFAULT_DELIMITER;
		}
			
		System.out.println("Converting sheet :" + aExcelSheet.toString());
		StringBuilder sb = new StringBuilder();
		int lastRowNum = aExcelSheet.getLastRowNum();
		for(int rowCounter =0; rowCounter <= lastRowNum; rowCounter ++) {
			System.out.println("Processing row :" + rowCounter);
			if(rowCounter!= 0) {
				sb.append( "\n");
			}
			Row fileDetailRow = aExcelSheet.getRow( rowCounter );
			sb.append( getRowData(fileDetailRow, delimiter));
		}
		FileUtils.writeStringToFile( aCsvFilename, sb.toString(), false);
	}
	
	public static String getRowData (Row aExcelRow, String aDelimiter) {
		
		if(aExcelRow == null) {
			return EMPTY_STRING;
		}
		StringBuilder sb = new StringBuilder();
		for(int columnCounter = 0; columnCounter < aExcelRow.getLastCellNum(); columnCounter ++) {
			String cellValue = ExcelSheet.getCellValue( aExcelRow, columnCounter );
			if(cellValue == null ) {
				cellValue = EMPTY_STRING;
			}
			System.out.println("Processing cell :" + columnCounter);
			if(columnCounter != 0) {
				sb.append( aDelimiter );
			}
			sb.append( cellValue );
		}
		return sb.toString();
	}
	
}
