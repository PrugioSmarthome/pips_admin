package com.daewooenc.pips.admin.web.domain.dto.sns;

import java.util.Date;

public class CommunicationAuthCode {
    private int authcodeNo;
    private String authCode;
    private String userId;
    private String useChk;
    private String crerId;
    private String editerId;
    private Date crDt;
    private Date editDt;
    private String oAuthType;
    private String sessionId;
    private String snsType;
    private String redirectUri;
    private String state;
    private String company;



    public CommunicationAuthCode() {

    }
    public CommunicationAuthCode(String authCode, String useChk, String userId, String crerId, String oAuthType) {
        this.authCode = authCode;
        this.userId = userId;
        this.useChk = useChk;
        this.crerId = crerId;
        this.oAuthType = oAuthType;
    }
    public int getAuthcodeNo() {
        return authcodeNo;
    }

    public void setAuthcodeNo(int authcodeNo) {
        this.authcodeNo = authcodeNo;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getUseChk() {
        return useChk;
    }

    public void setUseChk(String useChk) {
        this.useChk = useChk;
    }

    public String getCrerId() {
        return crerId;
    }

    public void setCrerId(String crerId) {
        this.crerId = crerId;
    }

    public String getEditerId() {
        return editerId;
    }

    public void setEditerId(String editerId) {
        this.editerId = editerId;
    }

    public Date getCrDt() {
        return crDt;
    }

    public void setCrDt(Date crDt) {
        this.crDt = crDt;
    }

    public Date getEditDt() {
        return editDt;
    }

    public void setEditDt(Date editDt) {
        this.editDt = editDt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getoAuthType() { return oAuthType; }

    public void setoAuthType(String oAuthType) { this.oAuthType = oAuthType; }

    public String getSessionId() { return sessionId; }

    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getSnsType() { return snsType; }

    public void setSnsType(String snsType) { this.snsType = snsType; }

    public String getRedirectUri() { return redirectUri; }

    public void setRedirectUri(String redirectUri) { this.redirectUri = redirectUri; }

    public String getState() { return state; }

    public void setState(String state) { this.state = state; }

    public String getCompany() { return company; }

    public void setCompany(String company) { this.company = company; }

}
