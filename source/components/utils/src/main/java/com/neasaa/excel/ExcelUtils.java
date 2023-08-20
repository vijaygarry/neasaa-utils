package com.neasaa.excel;

import org.apache.poi.ss.usermodel.Row;

/**
 * @author Vijay Garothaya
 * @version 1.0 Feb 3, 2014
 */
public class ExcelUtils {
	
	/**
	 * If first cell in row is empty or null, then returns true.
	 * 
	 * @param aRow
	 * @return
	 */
	public static boolean rowExists ( Row aRow ) {
		String cellValue = ExcelSheet.getCellValue( aRow, 0 );
		if ( cellValue == null || cellValue.isEmpty() ) {
			return false;
		}
		return true;
	}
}
