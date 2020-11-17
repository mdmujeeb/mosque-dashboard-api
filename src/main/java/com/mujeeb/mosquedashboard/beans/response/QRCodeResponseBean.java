package com.mujeeb.mosquedashboard.beans.response;

public class QRCodeResponseBean extends BaseResponseBean {
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String value;
	private String lastRedeemed;
	
	public QRCodeResponseBean() {}

	public QRCodeResponseBean(String id, String value, String lastRedeemed) {
		this.id = id;
		this.value = value;
		this.lastRedeemed = lastRedeemed;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLastRedeemed() {
		return lastRedeemed;
	}

	public void setLastRedeemed(String lastRedeemed) {
		this.lastRedeemed = lastRedeemed;
	}
}
