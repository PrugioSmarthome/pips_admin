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

public class ExternalApiUseHistory {
    private String id;

    @Field("user_id")
    private String userId;

    @Field("cr_dt")
    private String crDt;

    @Field("svc_tp_cd")
    private String svcTpCd;

    @Field("svc_tp_dtl_cd")
    private String svcTpDtlCd;

    @Field("user_tp_cd")
    private String userTpCd;

    @Field("houscplx_cd")
    private String houscplxCd;

    @Field("houscplx_nm")
    private String houscplxNm;

    @Field("dong_no")
    private String dongNo;

    @Field("hose_no")
    private String hoseNo;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCrDt() {
        return crDt;
    }

    public void setCrDt(String crDt) {
        this.crDt = crDt;
    }

    public String getSvcTpCd() {
        return svcTpCd;
    }

    public void setSvcTpCd(String svcTpCd) {
        this.svcTpCd = svcTpCd;
    }

    public String getSvcTpDtlCd() {
        return svcTpDtlCd;
    }

    public void setSvcTpDtlCd(String svcTpDtlCd) {
        this.svcTpDtlCd = svcTpDtlCd;
    }

    public String getUserTpCd() {
        return userTpCd;
    }

    public void setUserTpCd(String userTpCd) {
        this.userTpCd = userTpCd;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}