package com.daewooenc.pips.admin.core.domain.authorization;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 비밀번호 변경 Domain
 */
@XStreamAlias("changePassword")
public class ChangePassword {

	/** 사용자ID. */
	private String userId;

	/** 현재 비밀번호. */

	private String currentPassword;

	/** 신규 비밀번호. */
	@NotEmpty
	private String newPassword;

	/** 신규 비밀번호 확인. */
	@NotEmpty
	private String newPasswordRe;

	private String telNo;

	/** 팝업 구분. */
	private String mode;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewPasswordRe() {
		return newPasswordRe;
	}

	public void setNewPasswordRe(String newPasswordRe) {
		this.newPasswordRe = newPasswordRe;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
}
