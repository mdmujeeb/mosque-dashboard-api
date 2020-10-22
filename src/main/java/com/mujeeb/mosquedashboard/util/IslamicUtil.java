package com.mujeeb.mosquedashboard.util;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import com.google.code.geocoder.model.LatLng;
import com.mujeeb.mosquedashboard.beans.DateBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.arabeyes.itl.hijri.HijriCalc;
import org.arabeyes.itl.hijri.SimpleHijriDate;
import org.arabeyes.itl.prayer.Method;
import org.arabeyes.itl.prayer.PrayerTimeCalc;
import org.arabeyes.itl.prayer.PrayerTimes;
import org.arabeyes.itl.prayer.TimeNames;
import org.arabeyes.itl.prayer.astro.Location;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


public class IslamicUtil {
	
	private static Map<String,Method> calculationMethodCountryMapping = new HashMap<String, Method>();
	private static final Method DEFAULT_CALCULATION_METHOD = Method.KARACHI_HANAF;
	private static Logger logger = LogManager.getLogger(IslamicUtil.class);
	
	static {
		calculationMethodCountryMapping.put(
				"Indonesia, Iraq, Jordan, Lebanon, Malaysia, Singapore, Syria, Africa"
				, Method.EGYPT_SURVEY);
		calculationMethodCountryMapping.put("Iran, Kuwait, Europe", Method.KARACHI_SHAF);
		calculationMethodCountryMapping.put("Afghanistan, Bangladesh, India", Method.KARACHI_HANAF);
		calculationMethodCountryMapping.put("Canada, UK, United States", Method.NORTH_AMERICA);
		calculationMethodCountryMapping.put("", Method.MUSLIM_LEAGUE);
		calculationMethodCountryMapping.put("Saudi Arabia", Method.UMM_ALQURRA);
		calculationMethodCountryMapping.put("Bahrain, Oman, Qatar, United Arab Emirates", Method.FIXED_ISHAA);
	}
	
	public static void main(String[] args) {
//		logger.debug(findQibla(12.9667, 77.5667)); // Bangalore
//		logger.debug(findQibla(24.4667, 54.3667)); // Abu Dhabi
//		logger.debug(findQibla(51.5072, 0.1275));  // London
//		logger.debug(findQibla("Bangalore")); // Bangalore
//		logger.debug(findQibla("Abu Dhabi")); // Abu Dhabi
//		logger.debug(findQibla("London"));  // London
//		logger.debug(getHijriDate());
//		logger.debug(getPrayerTimes("bangalore"));
//		System.out.println(getPrayerTimes());
//		System.out.println(checkMagribPassed(new Date()));
		System.out.println(getHijriDate(-1));
	}
	
	public static QiblaFinderResult findQibla(String strLocation) {
		final Geocoder geocoder = new Geocoder();
		GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(strLocation)
									.setLanguage("en").getGeocoderRequest();
		try {
			GeocoderResult response = geocoder.geocode(geocoderRequest).getResults().get(0);
			LatLng location = response.getGeometry().getLocation();
			double latitude = location.getLat().doubleValue();
			double longitude = location.getLng().doubleValue();
			
			String address = response.getFormattedAddress();
			logger.debug(getCountryName(address));
			return new QiblaFinderResult(address
											, latitude
											, longitude
											, calculateQibla(latitude, longitude));
			
		} catch (IOException e) {
			logger.error(e);
			return null;
		}
	}
	
	public static QiblaFinderResult findQibla(double latitude, double longitude) {
		final Geocoder geocoder = new Geocoder();
		GeocoderRequest geocoderRequest = new GeocoderRequestBuilder()
									.setLocation(new LatLng(new BigDecimal(latitude), new BigDecimal(longitude)))
									.setLanguage("en").getGeocoderRequest();
		try {
			GeocoderResult response = geocoder.geocode(geocoderRequest).getResults().get(0);
			LatLng location = response.getGeometry().getLocation();
			latitude = location.getLat().doubleValue();
			longitude = location.getLng().doubleValue();
			
			return new QiblaFinderResult(response.getFormattedAddress()
											, latitude
											, longitude
											, calculateQibla(latitude, longitude));
			
		} catch (IOException e) {
			logger.error(e);
			return null;
		}
	}
	
	public static String getHijriDateString() {
		/* Convert current date to Hijri calendar. */
		SimpleHijriDate hdate = HijriCalc.toHijri(new Date());
		
		/* Prepare full Hijri date string */
		Locale locale = Locale.getDefault();
		return new StringBuilder(hdate.getDayOfWeekName(locale)).append(", ")
					.append(hdate.getDayOfMonth()).append(" ")
					.append(hdate.getMonthName(locale)).append(" ")
					.append(hdate.getYear()).append(" ")
					.append(hdate.getEraName(locale)).toString();
	}
	
	public static DateBean getHijriDate(int adjustment) {
		Date date = new Date();
		date = addDayToDate(date, adjustment);

		/* Convert current date to Hijri calendar. */
		SimpleHijriDate hdate = HijriCalc.toHijri(date);
		
		/* Prepare full Hijri date string */
		Locale locale = Locale.getDefault();
		return new DateBean("" + hdate.getDayOfMonth(), "" + hdate.getMonthName(locale), "" + hdate.getYear());
	}
	
	
	/*public static PrayerTimesResult getPrayerTimes(double latitude, double longitude) {
		String location = getFormattedLocation(latitude, longitude);
		return getPrayerTimes(location);
	}*/
	
	public static boolean isPostMagrib() {
		Map<String,String> hijriNamazTimes = IslamicUtil.getPrayerTimes();
		String[] magribTime = hijriNamazTimes.get("Maghrib").split(":");
	   	int hour = -1;
	   	int minute = -1;
	   	
	   	try {
	   		hour = Integer.parseInt(magribTime[0]) + 12;
	   	}catch(Exception ex) { /*Do Nothing*/ }
	   	
	   	try {
	   		minute = Integer.parseInt(magribTime[1]);
	   	}catch(Exception ex) { /*Do Nothing*/ }

	   	Calendar cal = new GregorianCalendar();
	   	int currentHours = cal.get(Calendar.HOUR_OF_DAY);
	   	int currentMinutes = cal.get(Calendar.MINUTE);
	   	if(currentHours > hour || (currentHours == hour && currentMinutes >= minute)) {
	   		return true;
	   	} else {
	   		return false;
	   	}
	}

	private static Date addDayToDate(Date date, int dayCount) {
		//Get current date minus 1 day
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, dayCount);
		return calendar.getTime();
	}

	public static Map<String,String> getPrayerTimes() {
		
		double latitude = 13.0681631;
		double longitude = 77.6394163;
		String country = "India";
		double gmtDifference = +5.5;
		
		Method method = getCalculationMethod(country);

		/* Instantiate the calculator. */
		PrayerTimeCalc calculator = new PrayerTimeCalc(new Location(latitude, longitude, gmtDifference, 0), method);
		
		/* Calculate prayer times for today. */
		Date date = new Date();
		PrayerTimes prayerTimes = calculator.getPrayerTimes(date);
		
		/* Print it (using default locale). */
		TimeNames names = TimeNames.getInstance(Locale.getDefault());
		
		Map<String,String> times = new HashMap<String,String>();
		for (int i=0; i < names.size(); ++i) {
			
			if(names.get(i).equalsIgnoreCase("Sunrise")) {
				times.put("Ishraq", getFormattedTime(prayerTimes.get(i).getHour(), prayerTimes.get(i).getMinute(), 20)); // Ishraq
			} else if(names.get(i).equalsIgnoreCase("Maghrib")) {
				times.put("Maghrib", getFormattedTime(prayerTimes.get(i).getHour(), prayerTimes.get(i).getMinute(), 5));
				times.put("Iftar", getFormattedTime(prayerTimes.get(i).getHour(), prayerTimes.get(i).getMinute()));
			} else {
				times.put(names.get(i), getFormattedTime(prayerTimes.get(i).getHour(), prayerTimes.get(i).getMinute()));
			}
		}
		
		return times;
	}
	
	private static long calculateQibla(double latitude, double longitude) {
		if (Double.isNaN(latitude-0.0) || Double.isNaN(longitude-0.0)) {
			logger.debug("Non-numeric entry/entries");
			return -1;
		}
		if ((latitude-0.0)>(90.0-0.0) || (latitude-0.0)<(-90.0-0.0)) {
			logger.debug("Latitude must be between -90 and 90 degrees");
			return -1;
		}
		if ((longitude-0.0)>(180.0-0.0) || (longitude-0.0)<(-180.0-0.0)) {
			logger.debug("Longitude must be between -180 and 180 degrees");
			return -1;
		}
		if (Math.abs(latitude-21.4)<Math.abs(0.0-0.01) && Math.abs(longitude-39.8)<Math.abs(0.0-0.01)) return 0;	//Mecca
		double phiK = 21.4*Math.PI/180.0;
		double lambdaK = 39.8*Math.PI/180.0;
		double phi = latitude*Math.PI/180.0;
		double lambda = longitude*Math.PI/180.0;
		double psi = 180.0/Math.PI*Math.atan2(Math.sin(lambdaK-lambda)
								,Math.cos(phi)*Math.tan(phiK)-Math.sin(phi)*Math.cos(lambdaK-lambda));
		long qibla = Math.round(psi);
		if(qibla < 0) {
			return 360 + qibla;
		} else {
			return qibla;
		}
	}
	
	private static String getFormattedTime(int hour, int minute) {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
//		return new SimpleDateFormat("hh:mm a").format(calendar.getTime());
		return new SimpleDateFormat("h:mm").format(calendar.getTime());
	}
	
	private static String getFormattedTime(int hour, int minute, int minuteOffset) {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.add(Calendar.MINUTE, minuteOffset);
//		return new SimpleDateFormat("hh:mm a").format(calendar.getTime());
		return new SimpleDateFormat("h:mm").format(calendar.getTime());
	}

	private static Method getCalculationMethod(String city) {
		if(city == null || city.trim().isEmpty()) {
			return DEFAULT_CALCULATION_METHOD;
		} else {
			city = city.trim();
			for(String countries : calculationMethodCountryMapping.keySet()) {
				if(countries.contains(city)) {
					return calculationMethodCountryMapping.get(countries);
				}
			}
			return DEFAULT_CALCULATION_METHOD;
		}
	}
	
	/*private static String getFormattedLocation(double latitude, double longitude) {
		final Geocoder geocoder = new Geocoder();
		GeocoderRequest geocoderRequest = new GeocoderRequestBuilder()
									.setLocation(new LatLng(new BigDecimal(latitude), new BigDecimal(longitude)))
									.setLanguage("en").getGeocoderRequest();
		try {
			GeocoderResult response = geocoder.geocode(geocoderRequest).getResults().get(0);
			return response.getFormattedAddress();
			
		} catch (IOException e) {
			logger.error(e);
			return null;
		}
	}*/

	private static String getCountryName(String address) {
		if(address == null || address.trim().isEmpty()) {
			return null;
		}
		
		String[] parts = address.split(",");
		parts = parts[parts.length-1].trim().split("-");
		return parts[parts.length-1].trim();
	}
}
