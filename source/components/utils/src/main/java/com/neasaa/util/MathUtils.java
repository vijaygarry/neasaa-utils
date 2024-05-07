package com.neasaa.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtils {
	
	public static final BigDecimal ZERO_VALUE = new BigDecimal(0);
	public static final BigDecimal MINUS_ONE_VALUE = new BigDecimal(-1);
	public static final BigDecimal VALUE_100 = new BigDecimal(100);
	
	public static BigDecimal calculatePercentage (BigDecimal value, BigDecimal totalValue) {
		BigDecimal bd = value.multiply(VALUE_100).divide(totalValue, 2, RoundingMode.HALF_UP);
		return bd;
	}
	
	public static BigDecimal getXPercentageOf(BigDecimal value, double percentage) {
		return value.multiply(new BigDecimal(percentage)).divide(VALUE_100, 2, RoundingMode.HALF_UP);
	}
}
