package com.neasaa.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {
	
	/**
	 * UTC time zone constant.
	 */
	public static final String UTC_TIME_ZONE = "UTC";
	
	public static Date parseDate (String aStrValue, String aDateFormat) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(aDateFormat);
		return sdf.parse(aStrValue);
	}
	
	public static String formatDate (Date aDateValue, String aDateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(aDateFormat);
		return sdf.format(aDateValue);
	}
	
	/**
	 * Returns the Sql Timestamp object for specified java.util.Date object.
	 * 
	 * @param aDate
	 * @return sql.Timestamp object for specified date.
	 */
	public static Timestamp dateToSqlTimestamp (Date aDate) {
		if(aDate == null) {
			return null;
		}
		return new Timestamp (aDate.getTime());
	}
	
	/**
	 * Returns a new Calendar instance whose time zone is set to UTC.
	 *
	 * @return a new Calendar instance whose time zone is set to UTC.
	 */
	public static Calendar getUtcCalendarInstance () {
		return Calendar.getInstance( TimeZone.getTimeZone( UTC_TIME_ZONE ) );
	}
}
