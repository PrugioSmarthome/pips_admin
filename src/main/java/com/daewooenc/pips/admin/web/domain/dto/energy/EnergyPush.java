package com.daewooenc.pips.admin.web.domain.dto.energy;

/**
 * @author : yckim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description <br/>
 * ---------------------------------------------------------- <br/>
 *    2019-09-02       :       yckim        :                  <br/>
 *
 * </pre>
 * @since : 2019-09-02
 **/
public class EnergyPush {
    private String crDt;
    private String crerId;
    private String step1TrnsmYn;
    private String yr;
    private String mm;
    private String houscplxCd;
    private String step2TrnsmYn;
    private String hsholdId;
    private String enrgTpCd;

    public String getCrDt() {
        return crDt;
    }

    public void setCrDt(String crDt) {
        this.crDt = crDt;
    }

    public String getCrerId() {
        return crerId;
    }

    public void setCrerId(String crerId) {
        this.crerId = crerId;
    }

    public String getStep1TrnsmYn() {
        return step1TrnsmYn;
    }

    public void setStep1TrnsmYn(String step1TrnsmYn) {
        this.step1TrnsmYn = step1TrnsmYn;
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

    public String getHouscplxCd() {
        return houscplxCd;
    }

    public void setHouscplxCd(String houscplxCd) {
        this.houscplxCd = houscplxCd;
    }

    public String getStep2TrnsmYn() {
        return step2TrnsmYn;
    }

    public void setStep2TrnsmYn(String step2TrnsmYn) {
        this.step2TrnsmYn = step2TrnsmYn;
    }

    public String getHsholdId() {
        return hsholdId;
    }

    public void setHsholdId(String hsholdId) {
        this.hsholdId = hsholdId;
    }

    public String getEnrgTpCd() {
        return enrgTpCd;
    }

    public void setEnrgTpCd(String enrgTpCd) {
        this.enrgTpCd = enrgTpCd;
    }

    @Override
    public String toString() {
        return "EnergyPush{" +
                "crDt='" + crDt + '\'' +
                ", crerId='" + crerId + '\'' +
                ", step1TrnsmYn='" + step1TrnsmYn + '\'' +
                ", yr='" + yr + '\'' +
                ", mm='" + mm + '\'' +
                ", houscplxCd='" + houscplxCd + '\'' +
                ", step2TrnsmYn='" + step2TrnsmYn + '\'' +
                ", hsholdId='" + hsholdId + '\'' +
                ", enrgTpCd='" + enrgTpCd + '\'' +
                '}';
    }
}