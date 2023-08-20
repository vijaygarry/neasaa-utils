
package com.neasaa.excel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Include following dependent jars poi-3.9-20121203.jar,
 * poi-ooxml-3.9-20121203.jar, poi-ooxml-schemas-3.9-20121203.jar,
 * xmlbeans-2.3.0.jar, dom4j-1.6.1.jar
 * 
 * @author vgarothaya
 * @version 1.0 Dec 28, 2012
 */
public class ExcelWorkBook {

	private Workbook excelWorkBook;
	
	/**
	 * File name is set only if excel workbook created using file name.
	 * excelFileName will be null if workbook created with inputStream.
	 */
	private String excelFileName;


	public ExcelWorkBook ( String aExcelFileName ) throws IOException {
		openExcelFile( aExcelFileName );
	}


	/**
	 * @param aFileInputStream
	 *        Input stream from excel file
	 * @param aExcelType
	 *        Excel type like XLS, XLSX
	 * @throws IOException
	 */
	public ExcelWorkBook ( InputStream aFileInputStream, ExcelType aExcelType )
			throws IOException {
		openExcelFile( aFileInputStream, aExcelType );
	}


	public static ExcelWorkBook createNewFile ( String aExcelFileName )
			throws IOException {
		return new ExcelWorkBook( aExcelFileName );
	}

	private void openExcelFile ( String aExcelFileName ) throws IOException {
		
		this.excelFileName = aExcelFileName;
		if ( aExcelFileName == null || aExcelFileName.isEmpty() ) {
			throw new RuntimeException( "Invalid file name" );
		}
		
		FileInputStream fileInputStream = new FileInputStream( aExcelFileName );
		ExcelType excelType = ExcelType.getExcelType( aExcelFileName );
		openExcelFile(fileInputStream, excelType);
		
	}


	private void openExcelFile ( InputStream aFileInputStream, ExcelType aExcelType )
			throws IOException {
		
		switch (aExcelType) {
		case EXCEL_XLS:
			this.excelWorkBook = new HSSFWorkbook( aFileInputStream );
			break;
		case EXCEL_XLSX:
			this.excelWorkBook = new XSSFWorkbook( aFileInputStream );
			break;
		default:
			throw new RuntimeException( "Excel type <" + aExcelType + "> not supported.");
		}
		
	}


	public ExcelSheet getExcelSheetByName(String aSheetName) {
		Sheet sheet = this.excelWorkBook.getSheet(aSheetName);
		if (sheet == null) {
			return null;
		}
		return new ExcelSheet(sheet);
	}


	public ExcelSheet getExcelSheetByIndex ( int aSheetIndex ) {
		return new ExcelSheet( this.excelWorkBook.getSheetAt( aSheetIndex ) );
	}

	public List<String> getExcelSheetNames () {
		int numberOfSheets  = this.excelWorkBook.getNumberOfSheets();
		List<String> sheetNames = new ArrayList<String>();
		for(int i = 0; i < numberOfSheets; i++) {
			sheetNames.add( this.excelWorkBook.getSheetName(  i ) );
		}
		return sheetNames;
	}
	
	/**
	 * Save the current worksheet to a file. And reopen the workbook to update
	 * the reference. Note: Discard all the reference of sheet/cell/rows after
	 * saving. And get the sheet/row/cell again for processing.
	 * 
	 * @throws IOException
	 */
	public void save() throws IOException {
		if (this.excelFileName == null) {
			throw new RuntimeException("Excel file name is not set, so excel can not be saved.");
		}
		FileOutputStream fileOut = new FileOutputStream(this.excelFileName);
		this.excelWorkBook.write(fileOut);
		fileOut.close();
		// Reopen the file after saving to refresh the content.
		openExcelFile(this.excelFileName);
	}


//	public static void main ( String[] args ) throws IOException {
//
//		String excelFileName = args[ 0 ];
//		ExcelWorkBook excel = new ExcelWorkBook( excelFileName );
//		ExcelSheet allEmailSheet = excel.getExcelSheetByName( "All Email Ids" );
//
//		for ( int rowNum = 0;; rowNum++ ) {
//			Row excelRow = allEmailSheet.getRow( rowNum );
//			String name = ExcelSheet.getCellValue( excelRow, 1 );
//			String email = ExcelSheet.getCellValue( excelRow, 2 );
//			if ( name == null || name.isEmpty() ) {
//				break;
//			}
//			System.out.println( "Name:" + name + "  Email:" + email );
//		}
//	}
	
//	public static void main ( String[] args ) throws IOException {
//
//		ExcelWorkBook excel = new ExcelWorkBook( "/Users/vijay/work/Product/Neasaa/Projects/navyfcu/source/NeasaaTests/NEASAA-INF/ExcelReportTemplate.xlsx" );
//		ExcelSheet allEmailSheet = excel.getExcelSheetByName( "AccountCreation" );
//
//		for ( int rowNum = 1;rowNum <= 13; rowNum++ ) {
//			if(rowNum %2 ==0) {
//				allEmailSheet.updateCellValue(rowNum, 2, "PASS");
//			}
//		}
//		
//		excel.save();
//		excel = new ExcelWorkBook( "/Users/vijay/work/Product/Neasaa/Projects/navyfcu/source/NeasaaTests/NEASAA-INF/ExcelReportTemplate.xlsx" );
//		allEmailSheet = excel.getExcelSheetByName( "AccountCreation" );
//		
//		for ( int rowNum = 1;rowNum <= 13; rowNum++ ) {
//			if(rowNum %2 == 1) {
//				allEmailSheet.updateCellValue(rowNum, 2, "FAIL");
//			}
//		}
//		excel.save();
//		
//	}
}
