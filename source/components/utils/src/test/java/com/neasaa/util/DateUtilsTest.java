package com.neasaa.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;


public class DateUtilsTest {
	
	@Test
	public void testTruncateTime () {
		//Date in UTC
		Date utcDate = new Date(1693671467000l);
		Date truncatedTime = DateUtils.truncateTimeInUTCTimezone(utcDate);
		System.out.println(truncatedTime + ",  " + DateUtils.formatDateInUTCTimeZone(truncatedTime, "yyyy-MM-dd"));
		assertEquals("2023-09-02", DateUtils.formatDateInUTCTimeZone(truncatedTime, "yyyy-MM-dd"));
		assertEquals("2023-09-01", DateUtils.formatDateInLocalTimeZone(truncatedTime, "yyyy-MM-dd"));
		
		Date localDate = new Date(1693671467000l);
		truncatedTime = DateUtils.truncateTimeInLocalTimezone(localDate);
		System.out.println(truncatedTime + ",  " + DateUtils.formatDateInUTCTimeZone(truncatedTime, "yyyy-MM-dd"));
		assertEquals("2023-09-02", DateUtils.formatDateInLocalTimeZone(truncatedTime, "yyyy-MM-dd"));
		assertEquals("2023-09-02", DateUtils.formatDateInUTCTimeZone(truncatedTime, "yyyy-MM-dd"));
		
	}
}
