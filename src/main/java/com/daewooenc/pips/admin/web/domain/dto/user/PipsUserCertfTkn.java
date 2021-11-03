package com.daewooenc.pips.admin.web.domain.dto.user;

import java.util.Date;

/**
 * T_USER_CERTF_TKN_BAS
 * 사용자 인증 토큰 기본
 * 사용자에 대한 인증 토큰 관리 테이블(Access Token, Refresh Token, Expire Date) 맵핑 DAO
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-08-01      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-08-01
 **/
public class PipsUserCertfTkn {
    private String userId;
    private Date crDt;
    private Date editDt;
    private String crerId;
    private String editerId;
    private String delYn;
    private String accessTknCd;
    private String refreshTknCd;
    private String exprtnTime;
    private String certfTpCd;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getDelYn() {
        return delYn;
    }

    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }

    public String getAccessTknCd() {
        return accessTknCd;
    }

    public void setAccessTknCd(String accessTknCd) {
        this.accessTknCd = accessTknCd;
    }

    public String getRefreshTknCd() {
        return refreshTknCd;
    }

    public void setRefreshTknCd(String refreshTknCd) {
        this.refreshTknCd = refreshTknCd;
    }

    public String getExprtnTime() {
        return exprtnTime;
    }

    public void setExprtnTime(String exprtnTime) {
        this.exprtnTime = exprtnTime;
    }

    public String getCertfTpCd() {
        return certfTpCd;
    }

    public void setCertfTpCd(String certfTpCd) {
        this.certfTpCd = certfTpCd;
    }

    @Override
    public String toString() {
        return "PipsUserCertfTkn{" +
                "userId='" + userId + '\'' +
                ", crDt=" + crDt +
                ", editDt=" + editDt +
                ", crerId='" + crerId + '\'' +
                ", editerId='" + editerId + '\'' +
                ", delYn='" + delYn + '\'' +
                ", accessTknCd='" + accessTknCd + '\'' +
                ", refreshTknCd='" + refreshTknCd + '\'' +
                ", exprtnTime='" + exprtnTime + '\'' +
                ", certfTpCd='" + certfTpCd + '\'' +
                '}';
    }
}