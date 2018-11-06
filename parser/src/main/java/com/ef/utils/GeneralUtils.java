package com.ef.utils;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class GeneralUtils {
	public static boolean isValidDateFormat(String format, String value) {
	    LocalDateTime date = null;
	    DateTimeFormatter dateFomatter = DateTimeFormatter.ofPattern(format);

	    try {
	    	date = LocalDateTime.parse(value, dateFomatter);
	        String result = date.format(dateFomatter);
	        return result.equals(value);
	    } catch (DateTimeParseException e) {
		    return false;
	    }
	}
	
	public static LocalDateTime changeDateZoneForDB(LocalDateTime date, String dbZone) {
		ZoneId newZone = ZoneId.systemDefault();
		ZoneId oldZone = ZoneId.of(dbZone);
		
		LocalDateTime newDateTime = date.atZone(oldZone)
										.withZoneSameInstant(newZone)
										.toLocalDateTime();
		
		return newDateTime;
	}	
	
	public static boolean isNumeric(String value) {
		return value.matches("\\d+");
	}
	
	public static boolean isValidPath(String value) {
		return  new File(value).exists();
	}
}