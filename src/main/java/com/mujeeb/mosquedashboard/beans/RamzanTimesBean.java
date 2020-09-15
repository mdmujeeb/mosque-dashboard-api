package com.mujeeb.mosquedashboard.beans;

import java.io.Serializable;

public class RamzanTimesBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String seheriTime;
	private String iftarTime;
	
	public RamzanTimesBean() {};
	
	public RamzanTimesBean(String seheriTime, String iftarTime) {
		this.seheriTime = seheriTime;
		this.iftarTime = iftarTime;
	}
	
	@Override
	public String toString() {
		return "[seheri: " + seheriTime + ", iftar: " + iftarTime + "]"; 
	}

	public String getSeheriTime() {
		return seheriTime;
	}

	public void setSeheriTime(String seheriTime) {
		this.seheriTime = seheriTime;
	}

	public String getIftarTime() {
		return iftarTime;
	}

	public void setIftarTime(String iftarTime) {
		this.iftarTime = iftarTime;
	}
}
