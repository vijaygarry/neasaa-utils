package com.neasaa.excel;

/**
 * @author Vijay Garothaya
 * @version 1.0 Dec 26, 2013
 */
public enum ExcelType {
	EXCEL_XLS, EXCEL_XLSX;
	
	private static String EXCEL_FORMAT_XLS = "xls";
	private static String EXCEL_FORMAT_XLSX = "xlsx";
	
	public static ExcelType getExcelType(String aExcelFileName) {
		int dotIndex = aExcelFileName.lastIndexOf( '.' );
		String fileFormat = aExcelFileName.substring( dotIndex + 1 );
		if ( EXCEL_FORMAT_XLS.equalsIgnoreCase( fileFormat ) ) {
			return EXCEL_XLS;
		}
		else if ( EXCEL_FORMAT_XLSX.equalsIgnoreCase( fileFormat ) ) {
			return EXCEL_XLSX;
		}
		else {
			throw new RuntimeException( "Invalid file name extension. Only ."
					+ EXCEL_FORMAT_XLS + " and ." + EXCEL_FORMAT_XLSX + " supported." );
		}
	}
}
