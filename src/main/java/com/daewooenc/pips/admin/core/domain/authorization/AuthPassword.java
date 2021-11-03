package com.daewooenc.pips.admin.core.domain.authorization;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

/**
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-09-05      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-09-05
 **/
public class AuthPassword {
    private String userId;
    private String houscplxCd;
    private String telNo;
    private String authCode;
    private Date authExpireDt;
    private String isAuth;
    private String encKey;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHouscplxCd() {
        return houscplxCd;
    }

    public void setHouscplxCd(String houscplxCd) {
        this.houscplxCd = houscplxCd;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
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

    public String getEncKey() {
        return encKey;
    }

    public void setEncKey(String encKey) {
        this.encKey = encKey;
    }
}