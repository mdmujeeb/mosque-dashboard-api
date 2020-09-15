package com.mujeeb.mosquedashboard.beans;

import java.io.Serializable;

public class TempreatureBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String tempreature;
	private String color;
	
	public TempreatureBean() {}
	
	public TempreatureBean(String tempreature, String color) {
		this.tempreature = tempreature;
		this.color = color;
	}

	public String getTempreature() {
		return tempreature;
	}

	public void setTempreature(String tempreature) {
		this.tempreature = tempreature;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
