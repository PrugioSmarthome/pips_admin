package com.daewooenc.pips.admin.web.domain.dto.housingcplx;

import java.util.Date;

/**
 * T_HOUSCPLX_LNK_SVC_RLT
 * 단지 연계 서비스 관계
 * 연계 APP 정보 관리 테이블
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-10-01      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-10-01
 **/
public class HousingCplxServiceLink {
    private Date crDt;
    private String crerId;
    private int lnkSvcId;
    private String houscplxCd;

    public Date getCrDt() {
        return crDt;
    }

    public void setCrDt(Date crDt) {
        this.crDt = crDt;
    }

    public String getCrerId() {
        return crerId;
    }

    public void setCrerId(String crerId) {
        this.crerId = crerId;
    }

    public int getLnkSvcId() {
        return lnkSvcId;
    }

    public void setLnkSvcId(int lnkSvcId) {
        this.lnkSvcId = lnkSvcId;
    }

    public String getHouscplxCd() {
        return houscplxCd;
    }

    public void setHouscplxCd(String houscplxCd) {
        this.houscplxCd = houscplxCd;
    }

    @Override
    public String toString() {
        return "HousingCplxServiceLink{" +
                "crDt=" + crDt +
                ", crerId='" + crerId + '\'' +
                ", lnkSvcId=" + lnkSvcId +
                ", houscplxCd='" + houscplxCd + '\'' +
                '}';
    }
}