package com.daewooenc.pips.admin.web.domain.dto.household;

import java.util.Date;

/**
 * T_HSHOLD_ENRG_OBJTV_SET_BAS
 * 세대 에너지 목표 설정 기본
 * 세대별 에너지 목표량 정보 관리 테이블 맵핑 DAO
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
public class HouseholdEnergyObjSet {
    private Date crDt;
    private String crerId;
    private String delYn;
    private String hsholdId;
    private Date editDt;
    private String editerId;
    private int objtvQty;
    private String enrgTpCd;

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

    public String getDelYn() {
        return delYn;
    }

    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }

    public String getHsholdId() {
        return hsholdId;
    }

    public void setHsholdId(String hsholdId) {
        this.hsholdId = hsholdId;
    }

    public Date getEditDt() {
        return editDt;
    }

    public void setEditDt(Date editDt) {
        this.editDt = editDt;
    }

    public String getEditerId() {
        return editerId;
    }

    public void setEditerId(String editerId) {
        this.editerId = editerId;
    }

    public int getObjtvQty() {
        return objtvQty;
    }

    public void setObjtvQty(int objtvQty) {
        this.objtvQty = objtvQty;
    }

    public String getEnrgTpCd() {
        return enrgTpCd;
    }

    public void setEnrgTpCd(String enrgTpCd) {
        this.enrgTpCd = enrgTpCd;
    }

    @Override
    public String toString() {
        return "HouseholdEnergyObjSet{" +
                "crDt=" + crDt +
                ", crerId='" + crerId + '\'' +
                ", delYn='" + delYn + '\'' +
                ", hsholdId='" + hsholdId + '\'' +
                ", editDt=" + editDt +
                ", editerId='" + editerId + '\'' +
                ", objtvQty=" + objtvQty +
                ", enrgTpCd='" + enrgTpCd + '\'' +
                '}';
    }
}
