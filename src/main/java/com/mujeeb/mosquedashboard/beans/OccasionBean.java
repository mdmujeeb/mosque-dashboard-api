package com.mujeeb.mosquedashboard.beans;

import java.io.Serializable;
import java.util.Date;

public class OccasionBean implements Serializable {
	
	private static final long serialVersionUID = -6427145784371855347L;
	
	private String id;
	private Date	dtDate;
	private String  date;
	private String  description;

	public OccasionBean() {}
	
	public OccasionBean(String id, Date dtDate, String date, String description) {
		this.id = id;
		this.dtDate = dtDate;
		this.date = date;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDtDate() {
		return dtDate;
	}

	public void setDtDate(Date dtDate) {
		this.dtDate = dtDate;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
