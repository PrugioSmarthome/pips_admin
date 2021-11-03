package com.daewooenc.pips.admin.web.domain.dto.household;

import java.util.Date;

/**
 * T_HSHOLD_MGMCST_HST
 * 세대 관리비 이력
 * 세대별 관리비 정보 관리 테이블 맵핑 DAO
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-07-30      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-07-30
 **/
public class HouseholdMgmExpHist {
    private Date crDt;
    private String crerId;
    private String mgmcstQty;
    private String hsholdId;
    private String yr;
    private String mm;

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

    public String getMgmcstQty() {
        return mgmcstQty;
    }

    public void setMgmcstQty(String mgmcstQty) {
        this.mgmcstQty = mgmcstQty;
    }

    public String getHsholdId() {
        return hsholdId;
    }

    public void setHsholdId(String hsholdId) {
        this.hsholdId = hsholdId;
    }

    public String getYr() {
        return yr;
    }

    public void setYr(String yr) {
        this.yr = yr;
    }

    public String getMm() {
        return mm;
    }

    public void setMm(String mm) {
        this.mm = mm;
    }

    @Override
    public String toString() {
        return "HouseholdMgmExpHist{" +
                "crDt=" + crDt +
                ", crerId='" + crerId + '\'' +
                ", mgmcstQty='" + mgmcstQty + '\'' +
                ", hsholdId='" + hsholdId + '\'' +
                ", yr='" + yr + '\'' +
                ", mm='" + mm + '\'' +
                '}';
    }
}
