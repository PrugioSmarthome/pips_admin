package com.daewooenc.pips.admin.web.domain.dto.facility;

import java.util.Date;

/**
 * T_FACLT_BIZCO_CADDR_BAS
 * 시설물 업체 연락처 기본
 * 시설 업체 연락처 정보 테이블 맵핑 DAO
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-09-18      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-09-18
 **/
public class FacilityBizcoCaddr {
    private Date crDt;
    private String crerId;
    private String mphoneNo;
    private String faxNo;
    private int facltBizcoCaddrId;
    private String grdNm;
    private String perchrgNm;
    private String offcPhoneNo;
    private String editerId;
    private Date editDt;
    private String delYn;
    private String mgmTpCd;

    private String encKey;

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

    public String getMphoneNo() {
        return mphoneNo;
    }

    public void setMphoneNo(String mphoneNo) {
        this.mphoneNo = mphoneNo;
    }

    public String getFaxNo() {
        return faxNo;
    }

    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }

    public int getFacltBizcoCaddrId() {
        return facltBizcoCaddrId;
    }

    public void setFacltBizcoCaddrId(int facltBizcoCaddrId) {
        this.facltBizcoCaddrId = facltBizcoCaddrId;
    }

    public String getGrdNm() {
        return grdNm;
    }

    public void setGrdNm(String grdNm) {
        this.grdNm = grdNm;
    }

    public String getPerchrgNm() {
        return perchrgNm;
    }

    public void setPerchrgNm(String perchrgNm) {
        this.perchrgNm = perchrgNm;
    }

    public String getOffcPhoneNo() {
        return offcPhoneNo;
    }

    public void setOffcPhoneNo(String offcPhoneNo) {
        this.offcPhoneNo = offcPhoneNo;
    }

    public String getEditerId() {
        return editerId;
    }

    public void setEditerId(String editerId) {
        this.editerId = editerId;
    }

    public Date getEditDt() {
        return editDt;
    }

    public void setEditDt(Date editDt) {
        this.editDt = editDt;
    }

    public String getDelYn() {
        return delYn;
    }

    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }

    public String getMgmTpCd() {
        return mgmTpCd;
    }

    public void setMgmTpCd(String mgmTpCd) {
        this.mgmTpCd = mgmTpCd;
    }

    public String getEncKey() {
        return encKey;
    }

    public void setEncKey(String encKey) {
        this.encKey = encKey;
    }

    @Override
    public String toString() {
        return "FacilityBizcoCaddr{" +
                "crDt=" + crDt +
                ", crerId='" + crerId + '\'' +
                ", mphoneNo='" + mphoneNo + '\'' +
                ", faxNo='" + faxNo + '\'' +
                ", facltBizcoCaddrId=" + facltBizcoCaddrId +
                ", grdNm='" + grdNm + '\'' +
                ", perchrgNm='" + perchrgNm + '\'' +
                ", offcPhoneNo='" + offcPhoneNo + '\'' +
                ", editerId='" + editerId + '\'' +
                ", editDt=" + editDt +
                ", delYn='" + delYn + '\'' +
                ", mgmTpCd='" + mgmTpCd + '\'' +
                ", encKey='" + encKey + '\'' +
                '}';
    }
}