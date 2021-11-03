package com.daewooenc.pips.admin.web.domain.dto.household;

import com.daewooenc.pips.admin.web.domain.dto.housingcplx.HousingCplx;

import java.util.Date;

/**
 * T_HSHOLD_BAS
 * 세대 기본
 * 세대(호)별 기본 정보 테이블 맵핑 DAO
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
public class Household extends HousingCplx {
    private String hoseNo;
    private String houscplxCd;
    private Date crDt;
    private Date editDt;
    private String dongNo;
    private String crerId;
    private String editerId;
    private String delYn;
    private String hsholdId;
    private String ptypeNm;
    private String dimQty;
    private String enrgDimQty;
    private String hmnetHsholdId;
    private String hmnetHsholdTkn;

    private String fmlyTpCd;
    private String apprStsCd;
    private Date apprDemDt;
    private Date apprDt;
    private Date hsholdCompoDt;

    private String userCnt;

    public String getHoseNo() {
        return hoseNo;
    }

    public void setHoseNo(String hoseNo) {
        this.hoseNo = hoseNo;
    }

    @Override
    public String getHouscplxCd() {
        return houscplxCd;
    }

    @Override
    public void setHouscplxCd(String houscplxCd) {
        this.houscplxCd = houscplxCd;
    }

    @Override
    public Date getCrDt() {
        return crDt;
    }

    @Override
    public void setCrDt(Date crDt) {
        this.crDt = crDt;
    }

    @Override
    public Date getEditDt() {
        return editDt;
    }

    @Override
    public void setEditDt(Date editDt) {
        this.editDt = editDt;
    }

    public String getDongNo() {
        return dongNo;
    }

    public void setDongNo(String dongNo) {
        this.dongNo = dongNo;
    }

    @Override
    public String getCrerId() {
        return crerId;
    }

    @Override
    public void setCrerId(String crerId) {
        this.crerId = crerId;
    }

    @Override
    public String getEditerId() {
        return editerId;
    }

    @Override
    public void setEditerId(String editerId) {
        this.editerId = editerId;
    }

    @Override
    public String getDelYn() {
        return delYn;
    }

    @Override
    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }

    public String getHsholdId() {
        return hsholdId;
    }

    public void setHsholdId(String hsholdId) {
        this.hsholdId = hsholdId;
    }

    public String getPtypeNm() {
        return ptypeNm;
    }

    public void setPtypeNm(String ptypeNm) {
        this.ptypeNm = ptypeNm;
    }

    public String getDimQty() {
        return dimQty;
    }

    public void setDimQty(String dimQty) {
        this.dimQty = dimQty;
    }

    public String getEnrgDimQty() {
        return enrgDimQty;
    }

    public void setEnrgDimQty(String enrgDimQty) {
        this.enrgDimQty = enrgDimQty;
    }

    public String getHmnetHsholdId() {
        return hmnetHsholdId;
    }

    public void setHmnetHsholdId(String hmnetHsholdId) {
        this.hmnetHsholdId = hmnetHsholdId;
    }

    public String getHmnetHsholdTkn() {
        return hmnetHsholdTkn;
    }

    public void setHmnetHsholdTkn(String hmnetHsholdTkn) {
        this.hmnetHsholdTkn = hmnetHsholdTkn;
    }

    public String getFmlyTpCd() {
        return fmlyTpCd;
    }

    public void setFmlyTpCd(String fmlyTpCd) {
        this.fmlyTpCd = fmlyTpCd;
    }

    public String getApprStsCd() {
        return apprStsCd;
    }

    public void setApprStsCd(String apprStsCd) {
        this.apprStsCd = apprStsCd;
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

    public String getUserCnt() {
        return userCnt;
    }

    public void setUserCnt(String userCnt) {
        this.userCnt = userCnt;
    }

    @Override
    public String toString() {
        return "Household{" +
                "hoseNo='" + hoseNo + '\'' +
                ", houscplxCd='" + houscplxCd + '\'' +
                ", crDt=" + crDt +
                ", editDt=" + editDt +
                ", dongNo='" + dongNo + '\'' +
                ", crerId='" + crerId + '\'' +
                ", editerId='" + editerId + '\'' +
                ", delYn='" + delYn + '\'' +
                ", hsholdId='" + hsholdId + '\'' +
                ", ptypeNm='" + ptypeNm + '\'' +
                ", dimQty='" + dimQty + '\'' +
                ", enrgDimQty='" + enrgDimQty + '\'' +
                ", hmnetHsholdId='" + hmnetHsholdId + '\'' +
                ", hmnetHsholdTkn='" + hmnetHsholdTkn + '\'' +
                ", fmlyTpCd='" + fmlyTpCd + '\'' +
                ", apprStsCd='" + apprStsCd + '\'' +
                ", apprDemDt=" + apprDemDt +
                ", apprDt=" + apprDt +
                ", hsholdCompoDt=" + hsholdCompoDt +
                ", userCnt='" + userCnt + '\'' +
                '}';
    }
}
