package com.daewooenc.pips.admin.web.domain.dto.household;

import java.util.Date;

/**
 * T_HSHOLD_APPR_HST
 * 세대 승인 이력
 * 세대 사용자에 대한 승인 이력 테이블 맵핑 DAO
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
public class HouseholdApprHist {
    private Date crDt;
    private String crerId;
    private String apprStsCd;
    private Date apprDt;
    private int hsholdApprId;
    private String hsholdId;
    private String userId;

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

    public String getApprStsCd() {
        return apprStsCd;
    }

    public void setApprStsCd(String apprStsCd) {
        this.apprStsCd = apprStsCd;
    }

    public Date getApprDt() {
        return apprDt;
    }

    public void setApprDt(Date apprDt) {
        this.apprDt = apprDt;
    }

    public int getHsholdApprId() {
        return hsholdApprId;
    }

    public void setHsholdApprId(int hsholdApprId) {
        this.hsholdApprId = hsholdApprId;
    }

    public String getHsholdId() {
        return hsholdId;
    }

    public void setHsholdId(String hsholdId) {
        this.hsholdId = hsholdId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "HouseholdApprHist{" +
                "crDt=" + crDt +
                ", crerId='" + crerId + '\'' +
                ", apprStsCd='" + apprStsCd + '\'' +
                ", apprDt=" + apprDt +
                ", hsholdApprId=" + hsholdApprId +
                ", hsholdId='" + hsholdId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
