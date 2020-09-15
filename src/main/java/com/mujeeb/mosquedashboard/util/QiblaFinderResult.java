package com.mujeeb.mosquedashboard.util;

public class QiblaFinderResult {
	
	private String address;
	private double latitude;
	private double longitude;
	private long qibla;
	
	public QiblaFinderResult() {}
	
	public QiblaFinderResult(String address, double latitude, double longitude, long qibla) {
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
		this.qibla = qibla;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Location: ").append(address).append("\n")
				.append("GPS Coordinates: ").append(latitude).append(",").append(longitude).append("\n")
				.append("Qibla Direction: ").append(qibla).append(" Degrees clockwise from North.");
		return builder.toString();
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public long getQibla() {
		return qibla;
	}
	public void setQibla(long qibla) {
		this.qibla = qibla;
	}
}
