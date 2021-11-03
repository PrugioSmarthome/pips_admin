package com.daewooenc.pips.admin.core.domain.configuration;


/**
 * 로그인 이력 조회 조건.
 *
 */
public class LoginHistoryCondition extends HistoryCondition {

	/** The menuNo. */
	private String menuNo;

	/** The workType. */
	private String workType;

	/** The userId. */
	private String userId;

	/** The userGroupLevel. */
	private String userGroupLevel;

	/** The userGroupId. */
	private String userGroupId;

	/**
	 * Gets the menuNo.
	 *
	 * @return the menuNo
	 */
	public String getMenuNo() {
		return menuNo;
	}

	/**
	 * Sets the menuNo.
	 *
	 * @param menuNo the new menuNo
	 */
	public void setMenuNo(String menuNo) {
		this.menuNo = menuNo;
	}

	/**
	 * Gets the workType.
	 *
	 * @return the workType
	 */
	public String getWorkType() {
		return workType;
	}

	/**
	 * Sets the workType.
	 *
	 * @param workType the new workType
	 */
	public void setWorkType(String workType) {
		this.workType = workType;
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

	public String getUserGroupLevel() {
		return userGroupLevel;
	}

	public void setUserGroupLevel(String ser_group_level) {
		this.userGroupLevel = ser_group_level;
	}

	public String getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(String userGroupId) {
		this.userGroupId = userGroupId;
	}



}
