package com.daewooenc.pips.admin.core.domain.common;

/**
 *
 * Base 도메인 클래스.
 */
public class ResponseInfo {
	/** 응답코드.*/
	String retCode;

	/** 응답메세지. */
	String retMessage;

	public String getRetCode() {
		return retCode;
	}
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	public String getRetMessage() {
		return retMessage;
	}
	public void setRetMessage(String retMessage) {
		this.retMessage = retMessage;
	}
}
