package com.mujeeb.mosquedashboard.util;

import java.util.List;

public class PrayerTimesResult {

	private double latitude;
	private double longitude;
	private String country;
	private String city;
	private String timeZone;
	private String calculationMethod;
	private int qiblaDirection;
	private List<String[]> prayerTimes;
	
	public PrayerTimesResult() {}
	
	public PrayerTimesResult(double latitude, double longitude, String city, String country, String timeZone, String calcMethod, int qiblaDirection, List<String[]> prayerTimes) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.city = city;
		this.country = country;
		this.timeZone = timeZone;
		this.calculationMethod = calcMethod;
		this.qiblaDirection = qiblaDirection;
		this.prayerTimes = prayerTimes;
	}
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	public String getCalculationMethod() {
		return calculationMethod;
	}
	public void setCalculationMethod(String calculationMethod) {
		this.calculationMethod = calculationMethod;
	}
	public int getQiblaDirection() {
		return qiblaDirection;
	}
	public void setQiblaDirection(int qiblaDirection) {
		this.qiblaDirection = qiblaDirection;
	}
	public List<String[]> getPrayerTimes() {
		return prayerTimes;
	}
	public void setPrayerTimes(List<String[]> prayerTimes) {
		this.prayerTimes = prayerTimes;
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Your location: ").append(city).append(", ").append(country).append(" (").append(latitude).append(",").append(longitude).append(")").append("\n")
			.append("Your Timezone: ").append(timeZone).append("\n")
			.append("Calculation method for your Country: ").append(calculationMethod).append("\n")
			.append("Qibla direction: ").append(qiblaDirection).append(" Degrees clockwise from North.").append("\n")
			.append("Prayer times:\n");
		for (String[] prayer : prayerTimes) {
			builder.append(prayer[0]).append(": ").append(prayer[1]).append("\n");
		}
		return builder.toString();
	}
}
