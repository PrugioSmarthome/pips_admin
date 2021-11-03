package com.daewooenc.pips.admin.web.domain.dto.housingcplx;

import java.util.Date;

/**
 * T_HOUSCPLX_ENRG_UNT_BAS
 * 단지 에너지 단위 기본
 * 단지의 사용되는 에너지 단위 테이블 맵핑 DAO
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
public class HousingCplxEnergyUnit {
    private String houscplxCd;
    private Date crDt;
    private Date editDt;
    private String crerId;
    private String editerId;
    private String delYn;
    private String enrgTpCd;
    private String enrgUntCd;
    private String enrgMaxQty;

    public String getHouscplxCd() {
        return houscplxCd;
    }

    public void setHouscplxCd(String houscplxCd) {
        this.houscplxCd = houscplxCd;
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

    public String getEnrgTpCd() {
        return enrgTpCd;
    }

    public void setEnrgTpCd(String enrgTpCd) {
        this.enrgTpCd = enrgTpCd;
    }

    public String getEnrgUntCd() {
        return enrgUntCd;
    }

    public void setEnrgUntCd(String enrgUntCd) {
        this.enrgUntCd = enrgUntCd;
    }

    public String getEnrgMaxQty() {
        return enrgMaxQty;
    }

    public void setEnrgMaxQty(String enrgMaxQty) {
        this.enrgMaxQty = enrgMaxQty;
    }

    @Override
    public String toString() {
        return "HousingCplxEnergyUnit{" +
                "houscplxCd='" + houscplxCd + '\'' +
                ", crDt=" + crDt +
                ", editDt=" + editDt +
                ", crerId='" + crerId + '\'' +
                ", editerId='" + editerId + '\'' +
                ", delYn='" + delYn + '\'' +
                ", enrgTpCd='" + enrgTpCd + '\'' +
                ", enrgUntCd='" + enrgUntCd + '\'' +
                ", enrgMaxQty='" + enrgMaxQty + '\'' +
                '}';
    }
}
