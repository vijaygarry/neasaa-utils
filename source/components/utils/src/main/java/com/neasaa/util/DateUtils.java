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
	
	public static Date parseDateInUTCTimeZone (String aStrValue, String aDateFormat) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(aDateFormat);
		sdf.setTimeZone(TimeZone.getTimeZone(UTC_TIME_ZONE));
		return sdf.parse(aStrValue);
	}
	
	public static Date parseDateInLocalTimeZone (String aStrValue, String aDateFormat) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(aDateFormat);
		return sdf.parse(aStrValue);
	}
	
	public static String formatDateInUTCTimeZone (Date aDateValue, String aDateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(aDateFormat);
		sdf.setTimeZone(TimeZone.getTimeZone(UTC_TIME_ZONE));
		return sdf.format(aDateValue);
	}
	
	public static String formatDateInLocalTimeZone (Date aDateValue, String aDateFormat) {
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
	
	public static Date truncateTimeInUTCTimezone (Date dateValue) {
		Calendar cal = getUtcCalendarInstance();
		cal.setTime(dateValue);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	public static Date truncateTimeInLocalTimezone (Date dateValue) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateValue);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	public static Date addDays (Date dateValue, int numberOfDays) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateValue);
		cal.add(Calendar.DATE, numberOfDays); // Adding days
		return cal.getTime();
	}
	
	public static Date addMonths (Date dateValue, int numberOfMonths) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateValue);
		cal.add(Calendar.MONTH, numberOfMonths); // Adding months
		return cal.getTime();
	}
	
	public static Date addYears (Date dateValue, int numberOfYears) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateValue);
		cal.add(Calendar.YEAR, numberOfYears); // Adding years
		return cal.getTime();
	}
}
