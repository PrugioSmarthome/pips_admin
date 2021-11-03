package com.daewooenc.pips.admin.web.domain.dto.mongo;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author : yckim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description <br/>
 * ---------------------------------------------------------- <br/>
 *    2019-09-04       :       yckim        :                  <br/>
 *
 * </pre>
 * @since : 2019-09-04
 **/
public class AppAccessHistory {
    private String id;

    @Field("cr_dt") 
    private String crDt;

    @Field("user_id") 
    private String userId;

    @Field("certf_tp_cd") 
    private String certfTpCd;

    @Field("crer_id") 
    private String crerId;

    @Field("login_yn") 
    private String loginYn;

    @Field("user_tp_cd") 
    private String userTpCd;

    @Field("fmly_tp_cd") 
    private String fmlyTpCd;

    @Field("houscplx_cd") 
    private String houscplxCd;

    @Field("houscplx_nm") 
    private String houscplxNm;

    @Field("dong_no") 
    private String dongNo;

    @Field("hose_no") 
    private String hoseNo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCrDt() {
        return crDt;
    }

    public void setCrDt(String crDt) {
        this.crDt = crDt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCertfTpCd() {
        return certfTpCd;
    }

    public void setCertfTpCd(String certfTpCd) {
        this.certfTpCd = certfTpCd;
    }

    public String getCrerId() {
        return crerId;
    }

    public void setCrerId(String crerId) {
        this.crerId = crerId;
    }

    public String getLoginYn() {
        return loginYn;
    }

    public void setLoginYn(String loginYn) {
        this.loginYn = loginYn;
    }

    public String getUserTpCd() {
        return userTpCd;
    }

    public void setUserTpCd(String userTpCd) {
        this.userTpCd = userTpCd;
    }

    public String getFmlyTpCd() {
        return fmlyTpCd;
    }

    public void setFmlyTpCd(String fmlyTpCd) {
        this.fmlyTpCd = fmlyTpCd;
    }

    public String getHouscplxCd() {
        return houscplxCd;
    }

    public void setHouscplxCd(String houscplxCd) {
        this.houscplxCd = houscplxCd;
    }

    public String getHouscplxNm() {
        return houscplxNm;
    }

    public void setHouscplxNm(String houscplxNm) {
        this.houscplxNm = houscplxNm;
    }

    public String getDongNo() {
        return dongNo;
    }

    public void setDongNo(String dongNo) {
        this.dongNo = dongNo;
    }

    public String getHoseNo() {
        return hoseNo;
    }

    public void setHoseNo(String hoseNo) {
        this.hoseNo = hoseNo;
    }

    @Override
    public String toString() {
        return "AppAccessHistory{" +
                "id='" + id + '\'' +
                ", crDt='" + crDt + '\'' +
                ", userId='" + userId + '\'' +
                ", certfTpCd='" + certfTpCd + '\'' +
                ", crerId='" + crerId + '\'' +
                ", loginYn='" + loginYn + '\'' +
                ", userTpCd='" + userTpCd + '\'' +
                ", fmlyTpCd='" + fmlyTpCd + '\'' +
                ", houscplxCd='" + houscplxCd + '\'' +
                ", houscplxNm='" + houscplxNm + '\'' +
                ", dongNo='" + dongNo + '\'' +
                ", hoseNo='" + hoseNo + '\'' +
                '}';
    }
}