package com.mujeeb.mosquedashboard.beans.request;

public class OrderRequestBean extends BaseRequestBean {

	private static final long serialVersionUID = 1L;

	private String name;
	private String phone;
	private String email;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String pinCode;
	private String tv;
	private String wifi;
	private String specialInstructions;
	
	public OrderRequestBean() {}

	public OrderRequestBean(String name, String phone, String email, String address1, String address2, String city, String state, String pinCode, String tv, String wifi, String specialInstructions) {
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.state = state;
		this.pinCode = pinCode;
		this.tv = tv;
		this.wifi = wifi;
		this.specialInstructions = specialInstructions;
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

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getTv() {
		return tv;
	}

	public void setTv(String tv) {
		this.tv = tv;
	}

	public String getWifi() {
		return wifi;
	}

	public void setWifi(String wifi) {
		this.wifi = wifi;
	}

	public String getSpecialInstructions() {
		return specialInstructions;
	}

	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
	}
}
