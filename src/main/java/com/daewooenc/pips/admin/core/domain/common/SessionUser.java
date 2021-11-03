package com.daewooenc.pips.admin.core.domain.common;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 * 로그인한 유저의 세션 정보 저장.
 */
@XStreamAlias("sessionUser")
public class SessionUser implements Serializable{

	/** Serializable serialVersionUID.  */
	private static final long serialVersionUID = 902237453571708747L;

	/** 사용자 ID. */
	private String userId;

	/** 사용자 이름. */
	private String userName;

	/** 사용자 그룹 ID. */
	private String userGroupId;

	/** 사용자 그룹명. */
	private String userGroupName;

	/** 사용자 그룹 래벨. */
	private String userGroupLevel;

	/** IP대역. */
	private String ipBandwidth;

	/** Login Gateway IP. */
	private String loginGatewayIp;

	/** 최종 로그인 일자. */
	private String lastLoginDate;

	/** 최종 로그인 시간. */
	private String lastLoginTime;

	/** 로그인 실패 횟수. */
	private Integer loginFailCount;

	/** 대우건설 스마트홈 푸르지오 관리자 단지코드 **/
	private String houscplxCd;

	private String houscplxNm;

	/** 소속 */
	private String deptName;

	/** 전화번호 */
	private String telNo;

	private String encKey;

	private String password;

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserGroupId() {
		return userGroupId;
	}
	public void setUserGroupId(String userGroupId) {
		this.userGroupId = userGroupId;
	}
	public String getUserGroupName() {
		return userGroupName;
	}
	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}
	public String getUserGroupLevel() {
		return userGroupLevel;
	}
	public void setUserGroupLevel(String userGroupLevel) {
		this.userGroupLevel = userGroupLevel;
	}
	public String getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	public String getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public Integer getLoginFailCount() {
		return loginFailCount;
	}
	public void setLoginFailCount(Integer loginFailCount) {
		this.loginFailCount = loginFailCount;
	}
	public String getLoginGatewayIp() {
		return loginGatewayIp;
	}
	public void setLoginGatewayIp(String loginGatewayIp) {
		this.loginGatewayIp = loginGatewayIp;
	}
	public String getIpBandwidth() {
		return ipBandwidth;
	}
	public void setIpBandwidth(String ipBandwidth) {
		this.ipBandwidth = ipBandwidth;
	}

	public String getHouscplxCd() {
		return houscplxCd;
	}

	public void setHouscplxCd(String houscplxCd) {
		this.houscplxCd = houscplxCd;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getEncKey() {
		return encKey;
	}

	public void setEncKey(String encKey) {
		this.encKey = encKey;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHouscplxNm() {
		return houscplxNm;
	}

	public void setHouscplxNm(String houscplxNm) {
		this.houscplxNm = houscplxNm;
	}

	public void setLoginTimeRecently(){
		if (getLastLoginDate() != null && getLastLoginDate().indexOf("|") > -1) {
			setLastLoginDate(getLastLoginDate().substring(getLastLoginDate().indexOf("|")+1));
		}
		if (getLastLoginTime() != null && getLastLoginTime().indexOf("|") > -1) {
			setLastLoginTime(getLastLoginTime().substring(getLastLoginTime().indexOf("|")+1));
		}		
	}
	
	/**
	 * toString Overriding.
	 *
	 * @return String
	 */
	public String toString() {
		return "SessionUser [userId=" + userId + ", userName=" + userName
				+ ", userGroupId=" + userGroupId + ", userGroupName="
				+ userGroupName + ", lastLoginDate=" + lastLoginDate
				+ ", lastLoginTime=" + lastLoginTime + ", ipBandwidth=" + ipBandwidth + ", houscplxCd=" + houscplxCd
				+ ", loginGatewayIp=" + loginGatewayIp + "]";
	}
}
