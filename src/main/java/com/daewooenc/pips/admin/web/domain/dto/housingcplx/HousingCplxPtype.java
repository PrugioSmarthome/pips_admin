package com.daewooenc.pips.admin.web.domain.dto.housingcplx;

import java.util.Date;

/**
 * T_HOUSCPLX_PTYPE_BAS
 * 단지 평형 기본
 * 단지내의 평형별 이미지 및 배치도 관리 테이블 맵핑 DAO
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
public class HousingCplxPtype {
    private String houscplxCd;
    private Date crDt;
    private Date editDt;
    private String ptypeNm;
    private String crerId;
    private String editerId;
    private String delYn;
    private String ptypeDimQty;
    private String ptypeTpCd;
    private int ordNo;
    private String stosOrgnlPlnfigNm;
    private String stosReducePlnfigNm;
    private String orgnlPlnfigFilePathCont;
    private String orgnlPlnfigFileUrlCont;
    private String reducePlnfigFilePathCont;
    private String reducePlnfigFileUrlCont;
    private String supDimQty;

    private String prevFileNm;

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

    public String getPtypeNm() {
        return ptypeNm;
    }

    public void setPtypeNm(String ptypeNm) {
        this.ptypeNm = ptypeNm;
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

    public String getPtypeDimQty() {
        return ptypeDimQty;
    }

    public void setPtypeDimQty(String ptypeDimQty) {
        this.ptypeDimQty = ptypeDimQty;
    }

    public String getPtypeTpCd() {
        return ptypeTpCd;
    }

    public void setPtypeTpCd(String ptypeTpCd) {
        this.ptypeTpCd = ptypeTpCd;
    }

    public int getOrdNo() {
        return ordNo;
    }

    public void setOrdNo(int ordNo) {
        this.ordNo = ordNo;
    }

    public String getStosOrgnlPlnfigNm() {
        return stosOrgnlPlnfigNm;
    }

    public void setStosOrgnlPlnfigNm(String stosOrgnlPlnfigNm) {
        this.stosOrgnlPlnfigNm = stosOrgnlPlnfigNm;
    }

    public String getStosReducePlnfigNm() {
        return stosReducePlnfigNm;
    }

    public void setStosReducePlnfigNm(String stosReducePlnfigNm) {
        this.stosReducePlnfigNm = stosReducePlnfigNm;
    }

    public String getOrgnlPlnfigFilePathCont() {
        return orgnlPlnfigFilePathCont;
    }

    public void setOrgnlPlnfigFilePathCont(String orgnlPlnfigFilePathCont) {
        this.orgnlPlnfigFilePathCont = orgnlPlnfigFilePathCont;
    }

    public String getOrgnlPlnfigFileUrlCont() {
        return orgnlPlnfigFileUrlCont;
    }

    public void setOrgnlPlnfigFileUrlCont(String orgnlPlnfigFileUrlCont) {
        this.orgnlPlnfigFileUrlCont = orgnlPlnfigFileUrlCont;
    }

    public String getReducePlnfigFilePathCont() {
        return reducePlnfigFilePathCont;
    }

    public void setReducePlnfigFilePathCont(String reducePlnfigFilePathCont) {
        this.reducePlnfigFilePathCont = reducePlnfigFilePathCont;
    }

    public String getReducePlnfigFileUrlCont() {
        return reducePlnfigFileUrlCont;
    }

    public void setReducePlnfigFileUrlCont(String reducePlnfigFileUrlCont) {
        this.reducePlnfigFileUrlCont = reducePlnfigFileUrlCont;
    }

    public String getSupDimQty() {
        return supDimQty;
    }

    public void setSupDimQty(String supDimQty) {
        this.supDimQty = supDimQty;
    }

    public String getPrevFileNm() {
        return prevFileNm;
    }

    public void setPrevFileNm(String prevFileNm) {
        this.prevFileNm = prevFileNm;
    }

    @Override
    public String toString() {
        return "HousingCplxPtype{" +
                "houscplxCd='" + houscplxCd + '\'' +
                ", crDt=" + crDt +
                ", editDt=" + editDt +
                ", ptypeNm='" + ptypeNm + '\'' +
                ", crerId='" + crerId + '\'' +
                ", editerId='" + editerId + '\'' +
                ", delYn='" + delYn + '\'' +
                ", ptypeDimQty='" + ptypeDimQty + '\'' +
                ", ptypeTpCd='" + ptypeTpCd + '\'' +
                ", ordNo=" + ordNo +
                ", stosOrgnlPlnfigNm='" + stosOrgnlPlnfigNm + '\'' +
                ", stosReducePlnfigNm='" + stosReducePlnfigNm + '\'' +
                ", orgnlPlnfigFilePathCont='" + orgnlPlnfigFilePathCont + '\'' +
                ", orgnlPlnfigFileUrlCont='" + orgnlPlnfigFileUrlCont + '\'' +
                ", reducePlnfigFilePathCont='" + reducePlnfigFilePathCont + '\'' +
                ", reducePlnfigFileUrlCont='" + reducePlnfigFileUrlCont + '\'' +
                ", supDimQty='" + supDimQty + '\'' +
                ", prevFileNm='" + prevFileNm + '\'' +
                '}';
    }
}
