package com.daewooenc.pips.admin.web.domain.dto.user;

import com.daewooenc.pips.admin.web.domain.dto.household.Household;
import java.util.Date;

/**
 * T_USER_BAS
 * 사용자 기본
 * 사용자(일반 사용자, 입주민) 정보 관리 테이블 맵핑 DAO
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
public class PipsUser extends Household {
    private String userId;
    private Date crDt;
    private Date editDt;
    private String userNm;
    private String emailNm;
    private String userTpCd;
    private String apprStsCd;
    private String crerId;
    private String editerId;
    private String delYn;
    private String pwd;
    private String mphoneNo;
    private String locaOffrAgrmYn;
    private String pslinfoCnthousAgrmYn;
    private String svcPlcyCfYn;
    private String pushAgrmYn;
    private String loginYn;
    private int loginPvnsCnt;
    private String houscplxCd;
    private String snsUserId;
    private String oprsNm;
    private String oprsVer;
    private String mphoneMdl;
    private String pushTknCd;
    private String certfCmpltCd;
    private String cncnHouscplxCd;
    private String useYn;
    private String masterYn;

    // User Cert Token Info
    private String certfTpCd;
    private String accessTknCd;
    private String refreshTknCd;
    private String exprtnTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public Date getCrDt() {
        return crDt;
    }

    @Override
    public void setCrDt(Date crDt) {
        this.crDt = crDt;
    }

    @Override
    public Date getEditDt() {
        return editDt;
    }

    @Override
    public void setEditDt(Date editDt) {
        this.editDt = editDt;
    }

    public String getUserNm() {
        return userNm;
    }

    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }

    public String getEmailNm() {
        return emailNm;
    }

    public void setEmailNm(String emailNm) {
        this.emailNm = emailNm;
    }

    public String getUserTpCd() {
        return userTpCd;
    }

    public void setUserTpCd(String userTpCd) {
        this.userTpCd = userTpCd;
    }

    public String getApprStsCd() { return apprStsCd; }

    public void setApprStsCd(String apprStsCd) { this.apprStsCd = apprStsCd; }

    @Override
    public String getCrerId() {
        return crerId;
    }

    @Override
    public void setCrerId(String crerId) {
        this.crerId = crerId;
    }

    @Override
    public String getEditerId() {
        return editerId;
    }

    @Override
    public void setEditerId(String editerId) {
        this.editerId = editerId;
    }

    @Override
    public String getDelYn() {
        return delYn;
    }

    @Override
    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getMphoneNo() {
        return mphoneNo;
    }

    public void setMphoneNo(String mphoneNo) {
        this.mphoneNo = mphoneNo;
    }

    public String getLocaOffrAgrmYn() {
        return locaOffrAgrmYn;
    }

    public void setLocaOffrAgrmYn(String locaOffrAgrmYn) {
        this.locaOffrAgrmYn = locaOffrAgrmYn;
    }

    public String getPslinfoCnthousAgrmYn() {
        return pslinfoCnthousAgrmYn;
    }

    public void setPslinfoCnthousAgrmYn(String pslinfoCnthousAgrmYn) {
        this.pslinfoCnthousAgrmYn = pslinfoCnthousAgrmYn;
    }

    public String getSvcPlcyCfYn() {
        return svcPlcyCfYn;
    }

    public void setSvcPlcyCfYn(String svcPlcyCfYn) {
        this.svcPlcyCfYn = svcPlcyCfYn;
    }

    public String getPushAgrmYn() {
        return pushAgrmYn;
    }

    public void setPushAgrmYn(String pushAgrmYn) {
        this.pushAgrmYn = pushAgrmYn;
    }

    public String getLoginYn() {
        return loginYn;
    }

    public void setLoginYn(String loginYn) {
        this.loginYn = loginYn;
    }

    public int getLoginPvnsCnt() {
        return loginPvnsCnt;
    }

    public void setLoginPvnsCnt(int loginPvnsCnt) {
        this.loginPvnsCnt = loginPvnsCnt;
    }

    @Override
    public String getHouscplxCd() {
        return houscplxCd;
    }

    @Override
    public void setHouscplxCd(String houscplxCd) {
        this.houscplxCd = houscplxCd;
    }

    public String getSnsUserId() {
        return snsUserId;
    }

    public void setSnsUserId(String snsUserId) {
        this.snsUserId = snsUserId;
    }

    public String getOprsNm() {
        return oprsNm;
    }

    public void setOprsNm(String oprsNm) {
        this.oprsNm = oprsNm;
    }

    public String getOprsVer() {
        return oprsVer;
    }

    public void setOprsVer(String oprsVer) {
        this.oprsVer = oprsVer;
    }

    public String getMphoneMdl() {
        return mphoneMdl;
    }

    public void setMphoneMdl(String mphoneMdl) {
        this.mphoneMdl = mphoneMdl;
    }

    public String getPushTknCd() {
        return pushTknCd;
    }

    public void setPushTknCd(String pushTknCd) {
        this.pushTknCd = pushTknCd;
    }

    public String getCertfCmpltCd() {
        return certfCmpltCd;
    }

    public void setCertfCmpltCd(String certfCmpltCd) {
        this.certfCmpltCd = certfCmpltCd;
    }

    public String getCncnHouscplxCd() {
        return cncnHouscplxCd;
    }

    public void setCncnHouscplxCd(String cncnHouscplxCd) {
        this.cncnHouscplxCd = cncnHouscplxCd;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getMasterYn() { return masterYn; }

    public void setMasterYn(String masterYn) { this.masterYn = masterYn; }

    public String getCertfTpCd() {
        return certfTpCd;
    }

    public void setCertfTpCd(String certfTpCd) {
        this.certfTpCd = certfTpCd;
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

    @Override
    public String toString() {
        return "PipsUser{" +
                "userId='" + userId + '\'' +
                ", crDt=" + crDt +
                ", editDt=" + editDt +
                ", userNm='" + userNm + '\'' +
                ", emailNm='" + emailNm + '\'' +
                ", userTpCd='" + userTpCd + '\'' +
                ", crerId='" + crerId + '\'' +
                ", editerId='" + editerId + '\'' +
                ", delYn='" + delYn + '\'' +
                ", pwd='" + pwd + '\'' +
                ", mphoneNo='" + mphoneNo + '\'' +
                ", locaOffrAgrmYn='" + locaOffrAgrmYn + '\'' +
                ", pslinfoCnthousAgrmYn='" + pslinfoCnthousAgrmYn + '\'' +
                ", svcPlcyCfYn='" + svcPlcyCfYn + '\'' +
                ", pushAgrmYn='" + pushAgrmYn + '\'' +
                ", loginYn='" + loginYn + '\'' +
                ", loginPvnsCnt=" + loginPvnsCnt +
                ", houscplxCd='" + houscplxCd + '\'' +
                ", snsUserId='" + snsUserId + '\'' +
                ", oprsNm='" + oprsNm + '\'' +
                ", oprsVer='" + oprsVer + '\'' +
                ", mphoneMdl='" + mphoneMdl + '\'' +
                ", pushTknCd='" + pushTknCd + '\'' +
                ", certfCmpltCd='" + certfCmpltCd + '\'' +
                ", cncnHouscplxCd='" + cncnHouscplxCd + '\'' +
                ", useYn='" + useYn + '\'' +
                ", masterYn='" + masterYn + '\'' +
                ", certfTpCd='" + certfTpCd + '\'' +
                ", accessTknCd='" + accessTknCd + '\'' +
                ", refreshTknCd='" + refreshTknCd + '\'' +
                ", exprtnTime='" + exprtnTime + '\'' +
                '}';
    }
}