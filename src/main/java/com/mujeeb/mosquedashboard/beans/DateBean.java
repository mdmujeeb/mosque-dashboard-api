package com.mujeeb.mosquedashboard.beans;

import java.io.Serializable;

public class DateBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String date;
	private String month;
	private String year;
	
	public DateBean() {};
	
	public DateBean(String date, String month, String year) {
		this.date = date;
		this.month = month;
		this.year = year;
	}
	
	@Override
	public String toString() {
		return "[date: " + date + ", month: " + month + ", year: " + year + "]"; 
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
}
