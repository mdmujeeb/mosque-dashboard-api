package com.mujeeb.mosquedashboard.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class ParserUtil {
	
	public static SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
	private static Logger logger = LogManager.getLogger(ParserUtil.class);
	
	public static Long addDurationToTime(Long startTime, String duration) {
		
		if(duration == null || duration.isEmpty()) {
			return new Date().getTime();
		}
		
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(startTime);
		
		String strNumber = duration.substring(0, duration.length()-1);
		int number = 0;
		try {
			number = Integer.parseInt(strNumber);
		}catch(Exception ex) {
			logger.error(ex);
		}
		
		duration = duration.trim();
		char indicator = duration.charAt(duration.length()-1);
		
		switch(indicator) {
			case 'y' : calendar.add(Calendar.YEAR, number); break;
			case 'Y' : calendar.add(Calendar.YEAR, number); break;
			case 'M' : calendar.add(Calendar.MONTH, number); break;
			case 'd' : calendar.add(Calendar.DATE, number); break;
			case 'D' : calendar.add(Calendar.DATE, number); break;
			case 'h' : calendar.add(Calendar.HOUR, number); break;
			case 'H' : calendar.add(Calendar.HOUR, number); break;
			case 'm' : calendar.add(Calendar.MINUTE, number); break;
			case 's' : calendar.add(Calendar.SECOND, number); break;
			case 'S' : calendar.add(Calendar.SECOND, number); break;
		}
		
		return calendar.getTimeInMillis();
	}
	
	public static Date parseDate(String when) {
		GregorianCalendar calendar = new GregorianCalendar();
		
		when = when.replaceAll("-", "/");
		String parts[] = when.split("/");
		if(parts.length < 2) {
			return null;
		}
		int date = calendar.get(Calendar.DATE);
		int month = calendar.get(Calendar.MONTH);
		int year = calendar.get(Calendar.YEAR);
		
		try {
			date = Integer.parseInt(parts[0]);
		} catch(Exception ex) {
			logger.error(ex);
		}
		
		try {
			month = Integer.parseInt(parts[1]);
		}catch(Exception ex) {
			logger.error(ex);
		}
		
		if(parts.length >= 3) {
			try {
				year = Integer.parseInt(parts[2]);
			}catch(Exception ex) {
				logger.error(ex);
			}
		}
		
		calendar.set(Calendar.DATE, date);
		calendar.set(Calendar.MONTH, month-1);
		calendar.set(Calendar.YEAR, year);
		return calendar.getTime();
	}
	
	public static Date getTommorowsDate() {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.add(Calendar.DATE, 1);
		return calendar.getTime();
	}
	
	public static Long setTimeToDate(Date date, String time) {
		String[] parts = time.split(":");
		if(parts == null || parts.length < 2) {
			return null;
		}
		
		int hour = 0;
		int minute = 0;
		int second = 0;
		
		try {
			hour = Integer.parseInt(parts[0]);
		} catch(Exception ex) {
			logger.error(ex);
		}
		
		try {
			minute = Integer.parseInt(parts[1]);
		}catch(Exception ex) {
			logger.error(ex);
		}
		
		if(parts.length >= 3) {
			try {
				second = Integer.parseInt(parts[2]);
			}catch(Exception ex) {
				logger.error(ex);
			}
		}
		
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		return calendar.getTimeInMillis();
	}
	
	public static String getFormattedDateTime(Long dateTime) {
		
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(dateTime);
//		calendar.add(Calendar.HOUR_OF_DAY, 1);
		
		return DATE_TIME_FORMAT.format(calendar.getTime());
	}
	
	public static Date getCurrentTime() {
		
		GregorianCalendar calendar = new GregorianCalendar();
//		calendar.add(Calendar.HOUR_OF_DAY, 1);
		
		return calendar.getTime();
	}
	
	public static String readTextFromFile(String filePath) {
		try {
			InputStream stream = new FileInputStream(filePath);
			int length = stream.available();
			byte[] buffer = new byte[length];
			stream.read(buffer);
			stream.close();
			return new String(buffer);
		}catch(Exception ex) {
			logger.error(ex);
			return null;
		}
	}
}
