package com.neasaa.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class StringUtils {

	public static final String WHITESPACE_CHARS = " \t\r\n\f";
	public static final String NEW_LINE = "\n";
	private static final String TRUE_VALUE = "true";
	private static final String YES_VALUE = "yes";
	private static final String FALSE_VALUE = "false";
	private static final String NO_VALUE = "no";

	// --------------------------------------------------------------------------
	public static boolean isWhiteSpaceChar(char aChar) {
		if (WHITESPACE_CHARS.indexOf(aChar) == -1) {
			return false;
		} else {
			return true;
		}
	}

	// --------------------------------------------------------------------------
	public static String replace(String aSourceString, String aStringToReplace, String aReplacement) {

		if (aSourceString == null || aSourceString.isEmpty()) {
			return aSourceString;
		}
		String result = aSourceString;
		result = result.replace(aStringToReplace, aReplacement);
		return result;
	}

	// --------------------------------------------------------------------------
	public static String getStringWithChar(char aChar, int aLength) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < aLength; i++) {
			sb.append(aChar);
		}
		return sb.toString();
	}

	// --------------------------------------------------------------------------
	public static String ltrim(String aInputString) {
		int i = 0;
		while (i < aInputString.length() && Character.isWhitespace(aInputString.charAt(i))) {
			i++;
		}
		return aInputString.substring(i);
	}

	// --------------------------------------------------------------------------
	public static String rtrim(String aInputString) {
		int i = aInputString.length() - 1;
		while (i >= 0 && Character.isWhitespace(aInputString.charAt(i))) {
			i--;
		}
		return aInputString.substring(0, i + 1);
	}

	// -------------------------------------------------------------------------
	/**
	 * Utility method to check if String is empty.
	 * 
	 * @param aString
	 *            Input String
	 * @return {@code true} if string is not null and length is greater then 0
	 *         otherwise returns false.
	 */
	public static boolean isEmpty(String aString) {

		if (aString == null || aString.trim().length() == 0) {
			return true;
		}
		return false;
	}

	// --------------------------------------------------------------------------
	/**
	 * Right pad (append) the specified character to source string to make
	 * string of specified length.
	 * 
	 * @param aSrc
	 *            Original string
	 * @param aBuffChar
	 *            char to append
	 * @param aLength
	 *            final length of string
	 * @return String with character appended to make the string of specified
	 *         length
	 */
	public static String rightPad(String aSrc, char aBuffChar, int aLength) {
		StringBuilder sb = new StringBuilder(aSrc);
		for (int i = aSrc.length(); i < aLength; i++) {
			sb.append(aBuffChar);
		}
		return sb.toString();
	}

	// --------------------------------------------------------------------------
	public static String leftPad(String aSrc, char aBuffChar, int aLength) {
		StringBuilder sb = new StringBuilder(aSrc);
		for (int i = aSrc.length(); i < aLength; i++) {
			sb.insert(0, aBuffChar);
		}
		return sb.toString();
	}

	// --------------------------------------------------------------------------
	public static String centerPad(String aSrc, char aBuffChar, int aLength) {
		if (aSrc == null) {
			return aSrc;
		}
		int lengthToAdd = aLength - aSrc.length();
		if (lengthToAdd < 0) {
			return aSrc;
		}

		int righPadLength = lengthToAdd / 2;
		int leftPadLength = lengthToAdd - righPadLength;

		StringBuilder padBuffer = new StringBuilder();
		for (int i = 0; i < righPadLength; i++) {
			padBuffer.append(aBuffChar);
		}
		if (righPadLength == leftPadLength) {
			return padBuffer.toString() + aSrc + padBuffer.toString();
		} else {
			return padBuffer.toString() + aSrc + padBuffer.append(aBuffChar).toString();
		}
	}

	public static String stackTraceToString(Throwable aTh) {
		StringWriter sw = new StringWriter();
		aTh.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}

	// --------------------------------------------------------------------------
	/**
	 * This method parses given string to int.
	 * 
	 * @param aStringValue
	 *            String to be converted.
	 * 
	 * @return int int representation of string
	 **/
	public static int stringToInt(String aStringValue) {

		return Integer.parseInt(aStringValue);
	}

	// --------------------------------------------------------------------------
	/**
	 * This method parses given string to long.
	 * 
	 * @param aStringValue
	 *            String to be converted.
	 * 
	 * @return long long representation of string
	 **/
	public static long stringToLong(String aStringValue) {

		return Long.parseLong(aStringValue);
	}

	// --------------------------------------------------------------------------
	/**
	 * Parse the input string to boolean value. Yes, true is consider as boolean
	 * true. No, false is consider as boolean false.
	 * 
	 * @param aValue
	 * @return
	 */
	public static boolean parseBooleanValue(String aValue) {
		if (aValue == null || aValue.length() == 0) {
			throw new RuntimeException("Invalid value <" + aValue + "> to parse to boolean");
		}
		if (TRUE_VALUE.equalsIgnoreCase(aValue) || YES_VALUE.equalsIgnoreCase(aValue)) {
			return true;
		}

		if (FALSE_VALUE.equalsIgnoreCase(aValue) || NO_VALUE.equalsIgnoreCase(aValue)) {
			return false;
		}

		return Boolean.parseBoolean(aValue);
	}
	
	
	public static String capitalize (String aStringValue) {
		if (isEmpty(aStringValue)) {
			return aStringValue;
		}

		char firstChar = aStringValue.charAt(0);
		char updatedFirstChar;
		updatedFirstChar = Character.toUpperCase(firstChar);
		if (firstChar == updatedFirstChar) {
			return aStringValue;
		}
		char[] chars = aStringValue.toCharArray();
		chars[0] = updatedFirstChar;
		return new String(chars, 0, chars.length);
	}
	
	public static String lowerFirstChar (String aStringValue) {
		if (isEmpty(aStringValue)) {
			return aStringValue;
		}

		char firstChar = aStringValue.charAt(0);
		char updatedFirstChar;
		updatedFirstChar = Character.toLowerCase( firstChar);
		if (firstChar == updatedFirstChar) {
			return aStringValue;
		}
		char[] chars = aStringValue.toCharArray();
		chars[0] = updatedFirstChar;
		return new String(chars, 0, chars.length);
	}
	
	/**
	 * Split the input string by comma, trim the values and create a list from values.
	 * 
	 * @param aInput
	 * @return - Returns List with parse values. Return null if input is null or empty.
	 */
	public static List<String> parseStringToList(String aInput) {
		if(aInput == null || aInput.isEmpty()) {
			return null;
		}
		String[] array = aInput.split(",");
		
		List<String> list = new ArrayList<String>(array.length);
		for (String value : array) {
			if(!value.trim().isEmpty()) {
				list.add(value.trim());
			}
		}
		return list;

	}
	
	/**
	 * Return String with comma separated values from input list.
	 * If input list if null or empty, returns null.
	 * 
	 * @param aInput
	 * @return - Returns List with parse values. Return null if input is null or empty.
	 */
	public static String convertListToCSVString(List<String> aInput) {
		if(aInput == null || aInput.isEmpty()) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (String value : aInput) {
			if(first) {
				first = false;
			} else {
				sb.append(",");
			}
			sb.append(value);
		}
		return sb.toString();
	}
	// --------------------------------------------------------------------------
}
// ----------------------------------------------------------------------------
