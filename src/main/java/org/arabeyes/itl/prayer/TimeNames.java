package org.arabeyes.itl.prayer;

import java.util.Locale;

public class TimeNames {
	
	private static TimeNames instance;
	
//	private Properties res = new Properties();
	private String[] timeNames = {"Fajr", "Sunrise", "Zohor", "Asr", "Maghrib", "Isha"};
	private Locale locale;
	private int count = 6;
	
	public TimeNames(Locale locale) {
		/*try {
			this.res.load(getClass().getClassLoader().getResourceAsStream("props.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		this.locale = locale;
		
//		count = 5;
		/*Enumeration<String> keys = res.getKeys();
		while(keys.hasMoreElements()) {
			keys.nextElement();
			count++;
		}*/
	}
	
	public Locale getLocale() {
		return locale;
	}

	/**
	 * 
	 * @param type one of prayer time constants in {@link PrayerTimes}, including {@link PrayerTimes#SUNRISE}
	 * @return
	 */
	public String get(int type) {
		if (type >= 6 || type < 0)
			throw new IllegalArgumentException("Invalid time type: " + type);
//		return res.getProperty("time." + type);
		return timeNames[type];
	}

	public String getImsak() {
		return "Imsak"; //res.getProperty("time.imsak");
	}
	
	public static TimeNames getInstance(Locale locale) {
		if (instance == null || !instance.locale.equals(locale))
			instance = new TimeNames(locale);
		return instance;
	}

	public int size() {
		return count;
	}
}
