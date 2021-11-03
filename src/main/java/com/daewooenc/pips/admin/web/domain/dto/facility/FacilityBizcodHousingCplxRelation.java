package com.daewooenc.pips.admin.web.domain.dto.facility;

import java.util.Date;

/**
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-10-30      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-10-30
 **/
public class FacilityBizcodHousingCplxRelation {
    private int facltBizcoId;
    private String houscplxCd;
    private String crerId;
    private Date crDt;

    public int getFacltBizcoId() {
        return facltBizcoId;
    }

    public void setFacltBizcoId(int facltBizcoId) {
        this.facltBizcoId = facltBizcoId;
    }

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

    @Override
    public String toString() {
        return "FacilityBizcodHousingCplxRelation{" +
                "facltBizcoId=" + facltBizcoId +
                ", houscplxCd='" + houscplxCd + '\'' +
                ", crerId='" + crerId + '\'' +
                ", crDt=" + crDt +
                '}';
    }
}