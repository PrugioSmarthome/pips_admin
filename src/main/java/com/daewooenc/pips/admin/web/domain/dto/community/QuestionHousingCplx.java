package com.daewooenc.pips.admin.web.domain.dto.community;

import java.util.Date;

/**
 * T_HOUSCPLX_APP_INFO_RLT
 * 단지 APP 정보 관계
 * 단지 정보와 APP 정보의 맵핑 테이블 맵핑 DAO
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-08-26      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-08-26
 **/
public class QuestionHousingCplx {
    private String houscplxCd;
    private String crerId;
    private Date crDt;
    private int qstNo;

    public String getHouscplxCd() {
        return houscplxCd;
    }

    public void setHouscplxCd(String houscplxCd) {
        this.houscplxCd = houscplxCd;
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

    public int getQstNo() {
        return qstNo;
    }

    public void setQstNo(int qstNo) {
        this.qstNo = qstNo;
    }

    @Override
    public String toString() {
        return "QuestionHousingCplx{" +
                "houscplxCd='" + houscplxCd + '\'' +
                ", crerId='" + crerId + '\'' +
                ", crDt=" + crDt +
                ", qstNo=" + qstNo +
                '}';
    }
}