package com.daewooenc.pips.admin.web.domain.dto.household;

import java.util.Date;

/**
 * T_HSHOLD_USER_ITEM
 * 세대 사용자 내역
 * 세대와 사용자 맵핑 및 승인정보 테이블 맵핑 DAO
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
public class HouseholdUserItem {
    private Date crDt;
    private Date editDt;
    private String crerId;
    private String editerId;
    private String apprStsCd;
    private String fmlyTpCd;
    private String userId;
    private Date apprDemDt;
    private Date apprDt;
    private Date hsholdCompoDt;
    private String delYn;
    private String hsholdId;

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

    public String getApprStsCd() {
        return apprStsCd;
    }

    public void setApprStsCd(String apprStsCd) {
        this.apprStsCd = apprStsCd;
    }

    public String getFmlyTpCd() {
        return fmlyTpCd;
    }

    public void setFmlyTpCd(String fmlyTpCd) {
        this.fmlyTpCd = fmlyTpCd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getApprDemDt() {
        return apprDemDt;
    }

    public void setApprDemDt(Date apprDemDt) {
        this.apprDemDt = apprDemDt;
    }

    public Date getApprDt() {
        return apprDt;
    }

    public void setApprDt(Date apprDt) {
        this.apprDt = apprDt;
    }

    public Date getHsholdCompoDt() {
        return hsholdCompoDt;
    }

    public void setHsholdCompoDt(Date hsholdCompoDt) {
        this.hsholdCompoDt = hsholdCompoDt;
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

    @Override
    public String toString() {
        return "HouseholdUserItem{" +
                "crDt=" + crDt +
                ", editDt=" + editDt +
                ", crerId='" + crerId + '\'' +
                ", editerId='" + editerId + '\'' +
                ", apprStsCd='" + apprStsCd + '\'' +
                ", fmlyTpCd='" + fmlyTpCd + '\'' +
                ", userId='" + userId + '\'' +
                ", apprDemDt=" + apprDemDt +
                ", apprDt=" + apprDt +
                ", hsholdCompoDt=" + hsholdCompoDt +
                ", delYn='" + delYn + '\'' +
                ", hsholdId='" + hsholdId + '\'' +
                '}';
    }
}
