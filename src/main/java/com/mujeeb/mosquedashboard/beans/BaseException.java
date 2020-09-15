package com.mujeeb.mosquedashboard.beans;

import com.mujeeb.mosquedashboard.util.Errors;

public class BaseException extends Throwable {

	private static final long serialVersionUID = -5567432763909183654L;
	
	protected int reasonCode = 1;
	protected String description = "An Unknown error occured while performing requested operation.";
	
	public BaseException() {}
	
	public BaseException(int errorCode) {
		this.reasonCode = errorCode;
		this.description = Errors.getErrorDescription(errorCode);
	}
	
	public BaseException(int errorCode, String description) {
		this.reasonCode = errorCode;
		this.description = description;
	}
	
	public int getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(int reasonCode) {
		this.reasonCode = reasonCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
