package com.daewooenc.pips.admin.core.domain.authorization;

import com.daewooenc.pips.admin.core.util.crypto.Sha256Cipher;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

/**
 * 사용자 Domain.
 *
 */
@XStreamAlias("user")
public class User {

	/** 사용자 ID. */
	@NotEmpty
	@Length(min=4,max=20)
	private String userId;

	/** 비밀번호. */
	private String password;

	/** 비밀번호 확인. */
	private String passwordRe;

	/** 사용자 명. */
	@NotEmpty
	private String userName;

	/** 사용자 그룹 ID. */
	@NotEmpty
	private String userGroupId;

	/** 사용자 그룹 명. */
	private String userGroupName;

	/** 사용자 부서 명. */
	private String deptName;

	/** 사용자 전화번호. */
	private String telNo;

	/** 사용자 Email. */
	@Email
	private String webMail;

	/** 직원번호. */
	private String empNo;

	/** IP 대역. */
	@NotEmpty
	private String ipBandwidth;

	/** 로그인 실패 횟수. */
	private int    loginFailCount = 0;

	/** 비밀번호 만료 일자. */
	@NotEmpty
	private String passwordDueDate;

	/** 비밀번호 변경 기간. */
	@NotEmpty
	private String passwordChangePeriod = "30";

	/** 최종 로그인 일자. */
	private String lastLoginDate;

	/** 최종 로그인 시간. */
	private String lastLoginTime;

	/** 계좌 잠김 여부. */
	private String accountLock;

	/** 로그인 IP. */
	private String loginGatewayIp;

	/** 변경 사용자 그룹. */
	private String userGroupIdC;


	/** 비밀번호. */
	private String password1;

	/** 비밀번호 확인.*/
	private String password2;

	/** 사용자 그룹 level.  */
	private String userGroupLevel;

	private String initAccount;

	private String houscplxCd;

	private String authCode;

	private Date authExpireDt;

	private String isAuth;

	private String encKey;

	private String description;

	private String houscplxNm;

	private String crerId;

	private Date crDt;

	private String startCrDt;

	private String endCrDt;

	private int cnt;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordRe() {
		return passwordRe;
	}

	public void setPasswordRe(String passwordRe) {
		this.passwordRe = passwordRe;
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

	public String getWebMail() {
		return webMail;
	}

	public void setWebMail(String webMail) {
		this.webMail = webMail;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getIpBandwidth() {
		return ipBandwidth;
	}

	public void setIpBandwidth(String ipBandwidth) {
		this.ipBandwidth = ipBandwidth;
	}

	public int getLoginFailCount() {
		return loginFailCount;
	}

	public void setLoginFailCount(int loginFailCount) {
		this.loginFailCount = loginFailCount;
	}

	public String getPasswordDueDate() {
		return passwordDueDate;
	}

	public void setPasswordDueDate(String passwordDueDate) {
		this.passwordDueDate = passwordDueDate;
	}

	public String getPasswordChangePeriod() {
		return passwordChangePeriod;
	}

	public void setPasswordChangePeriod(String passwordChangePeriod) {
		this.passwordChangePeriod = passwordChangePeriod;
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

	public String getLoginGatewayIp() {
		return loginGatewayIp;
	}

	public void setLoginGatewayIp(String loginGatewayIp) {
		this.loginGatewayIp = loginGatewayIp;
	}

	public String getAccountLock() {
		return accountLock;
	}

	public void setAccountLock(String accountLock) {
		this.accountLock = accountLock;
	}

	public String getUserGroupIdC() {
		return userGroupIdC;
	}

	public void setUserGroupIdC(String userGroupIdC) {
		this.userGroupIdC = userGroupIdC;
	}

	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getEncKey() {
		return encKey;
	}

	public void setEncKey(String encKey) {
		this.encKey = encKey;
	}

	public String getUserGroupLevel() {
		return userGroupLevel;
	}

	public void setUserGroupLevel(String userGroupLevel) {
		this.userGroupLevel = userGroupLevel;
	}

	public String getInitAccount() {
		return initAccount;
	}

	public void setInitAccount(String initAccount) {
		this.initAccount = initAccount;
	}

	public String getHouscplxCd() {
		return houscplxCd;
	}

	public void setHouscplxCd(String houscplxCd) {
		this.houscplxCd = houscplxCd;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public Date getAuthExpireDt() {
		return authExpireDt;
	}

	public void setAuthExpireDt(Date authExpireDt) {
		this.authExpireDt = authExpireDt;
	}

	public String getIsAuth() {
		return isAuth;
	}

	public void setIsAuth(String isAuth) {
		this.isAuth = isAuth;
	}

	public boolean checkPreviousPassword(ChangePassword changePassword){
		boolean ret = true;

		String encPassword = new Sha256Cipher(changePassword.getNewPassword()).encrypt();
		
		if (StringUtils.defaultString(getPassword1()).equals(encPassword) ||
		   StringUtils.defaultString(getPassword2()).equals(encPassword)
		 ) {
			ret = false;
		}

		return ret;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHouscplxNm() {
		return houscplxNm;
	}

	public void setHouscplxNm(String houscplxNm) {
		this.houscplxNm = houscplxNm;
	}

	public String getCrerId() {
		return crerId;
	}

	public void setCrerId(String crerId) {
		this.crerId = crerId;
	}

	public Date getCrDt() {
		return crDt;
	}

	public void setCrDt(Date crDt) {
		this.crDt = crDt;
	}

	public String getStartCrDt() {
		return startCrDt;
	}

	public void setStartCrDt(String startCrDt) {
		this.startCrDt = startCrDt;
	}

	public String getEndCrDt() {
		return endCrDt;
	}

	public void setEndCrDt(String endCrDt) {
		this.endCrDt = endCrDt;
	}

	public int getCnt() { return cnt; }

	public void setCnt(int cnt) { this.cnt = cnt; }

	@Override
	public String toString() {
		return "User{" +
				"userId='" + userId + '\'' +
				", password='" + password + '\'' +
				", passwordRe='" + passwordRe + '\'' +
				", userName='" + userName + '\'' +
				", userGroupId='" + userGroupId + '\'' +
				", userGroupName='" + userGroupName + '\'' +
				", deptName='" + deptName + '\'' +
				", telNo='" + telNo + '\'' +
				", webMail='" + webMail + '\'' +
				", empNo='" + empNo + '\'' +
				", ipBandwidth='" + ipBandwidth + '\'' +
				", loginFailCount=" + loginFailCount +
				", passwordDueDate='" + passwordDueDate + '\'' +
				", passwordChangePeriod='" + passwordChangePeriod + '\'' +
				", lastLoginDate='" + lastLoginDate + '\'' +
				", lastLoginTime='" + lastLoginTime + '\'' +
				", accountLock='" + accountLock + '\'' +
				", loginGatewayIp='" + loginGatewayIp + '\'' +
				", userGroupIdC='" + userGroupIdC + '\'' +
				", password1='" + password1 + '\'' +
				", password2='" + password2 + '\'' +
				", userGroupLevel='" + userGroupLevel + '\'' +
				", initAccount='" + initAccount + '\'' +
				", houscplxCd='" + houscplxCd + '\'' +
				", authCode='" + authCode + '\'' +
				", authExpireDt=" + authExpireDt +
				", isAuth='" + isAuth + '\'' +
				", encKey='" + encKey + '\'' +
				", description='" + description + '\'' +
				", houscplxNm='" + houscplxNm + '\'' +
				'}';
	}


}
