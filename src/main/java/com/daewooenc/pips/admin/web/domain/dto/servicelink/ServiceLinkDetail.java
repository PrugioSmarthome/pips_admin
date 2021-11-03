package com.daewooenc.pips.admin.web.domain.dto.servicelink;

import java.util.Date;

/**
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-09-23      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-09-23
 **/
public class ServiceLinkDetail extends ServiceLink {
    private Date crDt;
    private Date editDt;
    private String crerId;
    private String editerId;
    private String delYn;
    private int lnkSvcId;
    private String lnkTpCd;
    private String lnkAttrTpCd;
    private String lnkAttrCont;
    private int lnkSvcDtlId;
    String encKey;
    private String houscplxNm;
    private String houscplxCd;
    private String mainScreenYn;
    private int lnkOrdNo;

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

    @Override
    public int getLnkSvcId() {
        return lnkSvcId;
    }

    @Override
    public void setLnkSvcId(int lnkSvcId) {
        this.lnkSvcId = lnkSvcId;
    }

    public String getLnkTpCd() {
        return lnkTpCd;
    }

    public void setLnkTpCd(String lnkTpCd) {
        this.lnkTpCd = lnkTpCd;
    }

    public String getLnkAttrTpCd() {
        return lnkAttrTpCd;
    }

    public void setLnkAttrTpCd(String lnkAttrTpCd) {
        this.lnkAttrTpCd = lnkAttrTpCd;
    }

    public String getLnkAttrCont() {
        return lnkAttrCont;
    }

    public void setLnkAttrCont(String lnkAttrCont) { this.lnkAttrCont = lnkAttrCont; }

    public int getLnkSvcDtlId() {
        return lnkSvcDtlId;
    }

    public void setLnkSvcDtlId(int lnkSvcDtlId) {
        this.lnkSvcDtlId = lnkSvcDtlId;
    }

    public String getEncKey() {
        return encKey;
    }

    public void setEncKey(String encKey) {
        this.encKey = encKey;
    }

    public String getHouscplxNm() {
        return houscplxNm;
    }

    public void setHouscplxNm(String houscplxNm) { this.houscplxNm = houscplxNm; }

    public String getHouscplxCd() { return houscplxCd; }

    public void setHouscplxCd(String houscplxCd) { this.houscplxCd = houscplxCd; }

    public String getMainScreenYn() { return mainScreenYn; }

    public void setMainScreenYn(String mainScreenYn) { this.mainScreenYn = mainScreenYn; }

    public int getLnkOrdNo() { return lnkOrdNo; }

    public void setLnkOrdNo(int lnkOrdNo) { this.lnkOrdNo = lnkOrdNo; }

    @Override
    public String toString() {
        return "ServiceLinkDetail{" +
                "crDt=" + crDt +
                ", editDt=" + editDt +
                ", crerId='" + crerId + '\'' +
                ", editerId='" + editerId + '\'' +
                ", delYn='" + delYn + '\'' +
                ", lnkSvcId=" + lnkSvcId +
                ", lnkTpCd='" + lnkTpCd + '\'' +
                ", lnkAttrTpCd='" + lnkAttrTpCd + '\'' +
                ", lnkAttrCont='" + lnkAttrCont + '\'' +
                ", lnkSvcDtlId=" + lnkSvcDtlId +
                ", encKey='" + encKey + '\'' +
                '}';
    }
}