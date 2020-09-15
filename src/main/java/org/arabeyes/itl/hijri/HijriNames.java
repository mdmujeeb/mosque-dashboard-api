package org.arabeyes.itl.hijri;

import java.util.Locale;

public class HijriNames {
	
	private static HijriNames instance;
	
	private String[] hijriNames = {"Muharram", "Safar", "Rabi I", "Rabi II", "Jumada I", "Jumada II", "Rajab", "Shaaban", "Ramadan", "Shawwal", "Thul-Qiaadah", "Thul-Hijja"};
	
//	private Properties props;
	private Locale locale;
	
	public HijriNames(Locale locale) {
//		this.props = new Properties();
		/*try {
			props.load(this.getClass().getClassLoader().getResourceAsStream("props.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		this.locale = locale;
	}
	
	public Locale getLocale() {
		return locale;
	}

	public String getDayName(int dayOfWeek) {
		return ""; //props.getProperty("day.long." + dayOfWeek);
	}
	
	public String getDayShortName(int dayOfWeek) {
		return ""; //props.getProperty("day.short." + dayOfWeek);
	}
	
	public String getMonthName(int month) {
		return hijriNames[month]; //props.getProperty("month.long." + month);
	}
	
	public String getMonthShortName(int month) {
		return hijriNames[month]; //props.getProperty("month.short." + month);
	}
	
	public String getEraName(int era) {
		if (era == SimpleHijriDate.ERA_BH)
			return ""; //props.getProperty("era.bh");
		else if (era == SimpleHijriDate.ERA_AH)
			return ""; //props.getProperty("era.ah");
		else
			throw new IllegalArgumentException("Invalid Hijri era: " + era);
	}
	
	public static HijriNames getInstance(Locale locale) {
		if (instance == null || !instance.locale.equals(locale))
			instance = new HijriNames(locale);
		return instance;
	}
}
