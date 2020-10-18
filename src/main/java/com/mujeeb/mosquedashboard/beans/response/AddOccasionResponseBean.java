package com.mujeeb.mosquedashboard.beans.response;

import com.mujeeb.mosquedashboard.util.Errors;

import java.io.Serializable;

public class AddOccasionResponseBean extends BaseResponseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	protected String id = "0";

	public AddOccasionResponseBean() {}

	public AddOccasionResponseBean(int errorCode) {
		this.resultCode = errorCode;
		this.description = Errors.getErrorDescription(errorCode);
	}

	public AddOccasionResponseBean(int resultCode, String description, String id) {
		this.resultCode = resultCode;
		this.description = description;
		this.id = id;
	}
	
	public String getId() { return id; }
	public void setId(String id) { this.id = id; }
}
