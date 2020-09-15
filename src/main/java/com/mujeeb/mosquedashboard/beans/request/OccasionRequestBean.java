package com.mujeeb.mosquedashboard.beans.request;

public class OccasionRequestBean extends BaseRequestBean {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String date;
	private String description;
	
	public OccasionRequestBean() {}
	
	public OccasionRequestBean(Integer id, String name, String time) {
		this.id = id;
		this.date = name;
		this.description = time;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return date;
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
