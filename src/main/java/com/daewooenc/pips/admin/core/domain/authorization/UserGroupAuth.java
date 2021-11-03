package com.daewooenc.pips.admin.core.domain.authorization;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 * 사용자 그룹 권한 Domain.
 */
@XStreamAlias("userGroupAuth")
public class UserGroupAuth extends Menu implements Serializable {

	/** Serializable serialVersionUID. */
	private static final long serialVersionUID = 8021174022201439098L;

	/** 사용자 그룹 ID. */
	private String userGroupId;

	/** 권한 구분. */
	private String authType;

	/** 선택 메뉴 번호. */
	private String selectedMenuNo;

	private int subMenuCnt;

	public String getSelectedMenuNo() {
		return selectedMenuNo;
	}
	public void setSelectedMenuNo(String selectedMenuNo) {
		this.selectedMenuNo = selectedMenuNo;
	}
	public String getUserGroupId() {
		return userGroupId;
	}
	public void setUserGroupId(String userGroupId) {
		this.userGroupId = userGroupId;
	}
	public String getAuthType() {
		return authType;
	}
	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public int getSubMenuCnt() {
		return subMenuCnt;
	}

	public void setSubMenuCnt(int subMenuCnt) {
		this.subMenuCnt = subMenuCnt;
	}
}
