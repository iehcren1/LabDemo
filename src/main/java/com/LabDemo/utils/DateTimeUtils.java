package com.LabDemo.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

	/**
	 * 判斷輸入的日期格式是否正確
	 * @param dateStr
	 * @return
	 */
	public static boolean isLegalDate(String dateStr, String datePattern) {
		int legalLen = 10;
		if (dateStr == null || dateStr.length() != legalLen) {
			return false;
		}
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(datePattern);
		
		try {
			LocalDate localDate = LocalDate.parse(dateStr, dtf);
			return dateStr.equals(localDate.format(dtf));
		} catch (Exception e) {
			return false;
		}
		
	}
}
