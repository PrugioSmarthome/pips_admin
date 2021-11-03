package com.daewooenc.pips.admin.web.domain.dto.banner;

import java.util.Date;

/**
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-10-29      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-10-29
 **/
public class Banner {
    private int blltNo;
    private String blltOrdNo;
    private int nonResident;
    private String filePathCont;
    private String fileNm;
    private String uuidCd;
    private String linkUrlCont;
    private String delYn;
    private String crerId;
    private String editerId;
    private Date crDt;
    private Date editDt;
    private String fileUrlCont;
    private String orgnlFileNm;
    private String houscplxNm;
    private String houscplxCd;
    private String nonResidentVal;
    private String cnt;

    public int getBlltNo() {
        return blltNo;
    }

    public void setBlltNo(int blltNo) {
        this.blltNo = blltNo;
    }

    public String getBlltOrdNo() {
        return blltOrdNo;
    }

    public void setBlltOrdNo(String blltOrdNo) { this.blltOrdNo = blltOrdNo; }

    public int getNonResident() {
        return nonResident;
    }

    public void setNonResident(int nonResident) {
        this.nonResident = nonResident;
    }

    public String getFilePathCont() {
        return filePathCont;
    }

    public void setFilePathCont(String filePathCont) {
        this.filePathCont = filePathCont;
    }

    public String getFileNm() {
        return fileNm;
    }

    public void setFileNm(String fileNm) {
        this.fileNm = fileNm;
    }

    public String getUuidCd() {
        return uuidCd;
    }

    public void setUuidCd(String uuidCd) {
        this.uuidCd = uuidCd;
    }

    public String getLinkUrlCont() {
        return linkUrlCont;
    }

    public void setLinkUrlCont(String linkUrlCont) {
        this.linkUrlCont = linkUrlCont;
    }

    public String getDelYn() {
        return delYn;
    }

    public void setDelYn(String delYn) {
        this.delYn = delYn;
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

    public String getFileUrlCont() {
        return fileUrlCont;
    }

    public void setFileUrlCont(String fileUrlCont) {
        this.fileUrlCont = fileUrlCont;
    }

    public String getOrgnlFileNm() {
        return orgnlFileNm;
    }

    public void setOrgnlFileNm(String orgnlFileNm) {
        this.orgnlFileNm = orgnlFileNm;
    }

    public String getHouscplxNm() {
        return houscplxNm;
    }

    public void setHouscplxNm(String houscplxNm) { this.houscplxNm = houscplxNm; }

    public String getHouscplxCd() { return houscplxCd; }

    public void setHouscplxCd(String houscplxCd) { this.houscplxCd = houscplxCd; }

    public String getNonResidentVal() { return nonResidentVal; }

    public void setNonResidentVal(String nonResidentVal) { this.nonResidentVal = nonResidentVal; }

    public String getCnt() { return cnt; }

    public void setCnt(String cnt) { this.cnt = cnt; }

}