package com.mujeeb.mosquedashboard.beans.request;

public class QRCodeUpdateRequestBean extends BaseRequestBean {

	private static final long serialVersionUID = 1L;

	private String qrCode;
	private String enable;
	
	public QRCodeUpdateRequestBean() {}

	public QRCodeUpdateRequestBean(String qrCode, String enable) {
		this.qrCode = qrCode;
		this.enable = enable;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}
}
