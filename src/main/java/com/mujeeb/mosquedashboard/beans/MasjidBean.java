package com.mujeeb.mosquedashboard.beans;

import java.io.Serializable;

public class MasjidBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String Id;
	private String name;
	private String password;
	private String latitude;
	private String longitude;
	
	public MasjidBean() {};
	
	public MasjidBean(String Id, String name, String password, String latitude, String longitude) {
		this.Id = Id;
		this.name = name;
		this.password = password;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	@Override
	public String toString() {
		return "[Id: " + Id + ", name: " + name + ", latitude: " + latitude + ", longitude: " + longitude + "]"; 
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
