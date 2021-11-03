package com.daewooenc.pips.admin.core.domain.configuration;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 로그인 이력.
 */
@XStreamAlias("loginHistory")
public class LoginHistory {

	/** The userGroupName. */
	private String userGroupName;

	/** The userId. */
	private String userId;

	/** The userName. */
	private String userName;

	/** The loginDate. */
	private String loginDate;

	/** The loginTime. */
	private String loginTime;

	/** The loginGatewayIp. */
	private String loginGatewayIp;

	/** The logoutDate. */
	private String logoutDate;

	/** The logoutTime. */
	private String logoutTime;

	/** The logoutStatus. */
	private String logoutStatus;

	/** The remark. */
	private String remark;


	/**
	 * Gets the userGroupName.
	 *
	 * @return the userGroupName
	 */
	public String getUserGroupName() {
		return userGroupName;
	}

	/**
	 * Sets the userGroupName.
	 *
	 * @param userGroupName the new userGroupName
	 */
	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}

	/**
	 * Gets the userId.
	 *
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Sets the userId.
	 *
	 * @param userId the new userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Gets the loginDate.
	 *
	 * @return the loginDate
	 */
	public String getLoginDate() {
		return loginDate;
	}

	/**
	 * Sets the loginDate.
	 *
	 * @param loginDate the new loginDate
	 */
	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}

	/**
	 * Gets the loginTime.
	 *
	 * @return the loginTime
	 */
	public String getLoginTime() {
		return loginTime;
	}

	/**
	 * Sets the loginTime.
	 *
	 * @param loginTime the new loginTime
	 */
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	/**
	 * Gets the loginGatewayIp.
	 *
	 * @return the loginGatewayIp
	 */
	public String getLoginGatewayIp() {
		return loginGatewayIp;
	}

	/**
	 * Sets the loginGatewayIp.
	 *
	 * @param loginGatewayIp the new loginGatewayIp
	 */
	public void setLoginGatewayIp(String loginGatewayIp) {
		this.loginGatewayIp = loginGatewayIp;
	}

	/**
	 * Gets the logoutDate.
	 *
	 * @return the logoutDate
	 */
	public String getLogoutDate() {
		return logoutDate;
	}

	/**
	 * Sets the logoutDate.
	 *
	 * @param logoutDate the new logoutDate
	 */
	public void setLogoutDate(String logoutDate) {
		this.logoutDate = logoutDate;
	}

	/**
	 * Gets the logoutTime.
	 *
	 * @return the logoutTime
	 */
	public String getLogoutTime() {
		return logoutTime;
	}

	/**
	 * Sets the logoutTime.
	 *
	 * @param logoutTime the new logoutTime
	 */
	public void setLogoutTime(String logoutTime) {
		this.logoutTime = logoutTime;
	}

	/**
	 * Gets the logoutStatus.
	 *
	 * @return the logoutStatus
	 */
	public String getLogoutStatus() {
		return logoutStatus;
	}

	/**
	 * Sets the logoutStatus.
	 *
	 * @param logoutStatus the new logoutStatus
	 */
	public void setLogoutStatus(String logoutStatus) {
		this.logoutStatus = logoutStatus;
	}

	/**
	 * Gets the remark.
	 *
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * Sets the remark.
	 *
	 * @param remark the new remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * Gets the userName.
	 *
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the userName.
	 *
	 * @param userName the new userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
