package com.daewooenc.pips.admin.web.domain.dto.facility;

import java.util.Date;

/**
 * T_FACLT_BIZCO_BAS
 * 시설물 업체 기본
 * 시설 업체 정보 테이블 맵핑 DAO
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
public class FacilityBizco extends FacilityBizcoCaddr {
    private Date crDt;
    private String crerId;
    private String bizcoNm;
    private String conCont;
    private int facltBizcoId;
    private String facltBizcoTpNm;
    private String twbsNm;
    private String repNm;
    private String editerId;
    private Date editDt;
    private String delYn;
    private String mgmTpCd;
    private String cont;
    private String startCrDt;
    private String endCrDt;

    private String houscplxCd;
    private String houscplxNm;
    private String encKey;

    @Override
    public Date getCrDt() {
        return crDt;
    }

    @Override
    public void setCrDt(Date crDt) {
        this.crDt = crDt;
    }

    @Override
    public String getCrerId() {
        return crerId;
    }

    @Override
    public void setCrerId(String crerId) {
        this.crerId = crerId;
    }

    public String getBizcoNm() {
        return bizcoNm;
    }

    public void setBizcoNm(String bizcoNm) {
        this.bizcoNm = bizcoNm;
    }

    public String getConCont() {
        return conCont;
    }

    public void setConCont(String conCont) {
        this.conCont = conCont;
    }

    public int getFacltBizcoId() {
        return facltBizcoId;
    }

    public void setFacltBizcoId(int facltBizcoId) {
        this.facltBizcoId = facltBizcoId;
    }

    public String getFacltBizcoTpNm() {
        return facltBizcoTpNm;
    }

    public void setFacltBizcoTpNm(String facltBizcoTpNm) {
        this.facltBizcoTpNm = facltBizcoTpNm;
    }

    public String getTwbsNm() {
        return twbsNm;
    }

    public void setTwbsNm(String twbsNm) {
        this.twbsNm = twbsNm;
    }

    public String getRepNm() {
        return repNm;
    }

    public void setRepNm(String repNm) {
        this.repNm = repNm;
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
    public Date getEditDt() {
        return editDt;
    }

    @Override
    public void setEditDt(Date editDt) {
        this.editDt = editDt;
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
    public String getMgmTpCd() {
        return mgmTpCd;
    }

    @Override
    public void setMgmTpCd(String mgmTpCd) {
        this.mgmTpCd = mgmTpCd;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public String getStartCrDt() {
        return startCrDt;
    }

    public void setStartCrDt(String startCrDt) {
        this.startCrDt = startCrDt;
    }

    public String getEndCrDt() {
        return endCrDt;
    }

    public void setEndCrDt(String endCrDt) {
        this.endCrDt = endCrDt;
    }

    public String getHouscplxCd() {
        return houscplxCd;
    }

    public void setHouscplxCd(String houscplxCd) {
        this.houscplxCd = houscplxCd;
    }

    public String getHouscplxNm() {
        return houscplxNm;
    }

    public void setHouscplxNm(String houscplxNm) {
        this.houscplxNm = houscplxNm;
    }

    @Override
    public String getEncKey() {
        return encKey;
    }

    @Override
    public void setEncKey(String encKey) {
        this.encKey = encKey;
    }

    @Override
    public String toString() {
        return "FacilityBizco{" +
                "crDt=" + crDt +
                ", crerId='" + crerId + '\'' +
                ", bizcoNm='" + bizcoNm + '\'' +
                ", conCont='" + conCont + '\'' +
                ", facltBizcoId=" + facltBizcoId +
                ", facltBizcoTpNm='" + facltBizcoTpNm + '\'' +
                ", twbsNm='" + twbsNm + '\'' +
                ", repNm='" + repNm + '\'' +
                ", editerId='" + editerId + '\'' +
                ", editDt=" + editDt +
                ", delYn='" + delYn + '\'' +
                ", mgmTpCd='" + mgmTpCd + '\'' +
                ", cont='" + cont + '\'' +
                ", startCrDt='" + startCrDt + '\'' +
                ", endCrDt='" + endCrDt + '\'' +
                ", houscplxCd='" + houscplxCd + '\'' +
                ", houscplxNm='" + houscplxNm + '\'' +
                ", encKey='" + encKey + '\'' +
                '}';
    }
}