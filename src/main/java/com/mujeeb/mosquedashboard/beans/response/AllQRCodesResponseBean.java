package com.mujeeb.mosquedashboard.beans.response;

import java.util.ArrayList;
import java.util.List;

public class AllQRCodesResponseBean extends BaseResponseBean {
	
	private static final long serialVersionUID = 1L;
	
	List<QRCodeResponseBean> qrCodes = new ArrayList<QRCodeResponseBean>();
	
	public AllQRCodesResponseBean() {}

	public AllQRCodesResponseBean(List<QRCodeResponseBean> qrCodes) {
		this.qrCodes = qrCodes;
	}

	public List<QRCodeResponseBean> getQrCodes() {
		return qrCodes;
	}

	public void setQrCodes(List<QRCodeResponseBean> qrCodes) {
		this.qrCodes = qrCodes;
	}
}
