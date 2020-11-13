package com.mujeeb.mosquedashboard.beans.request;

public class EnquiryRequestBean extends BaseRequestBean {

	private static final long serialVersionUID = 1L;

	private String name;
	private String phone;
	private String email;
	private String enquiry;
	
	public EnquiryRequestBean() {}

	public EnquiryRequestBean(String name, String phone, String email, String enquiry) {
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.enquiry = enquiry;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEnquiry() {
		return enquiry;
	}

	public void setEnquiry(String enquiry) {
		this.enquiry = enquiry;
	}
}
