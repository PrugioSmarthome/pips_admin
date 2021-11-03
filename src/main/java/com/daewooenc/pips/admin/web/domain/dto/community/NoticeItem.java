package com.daewooenc.pips.admin.web.domain.dto.community;

import com.daewooenc.pips.admin.web.domain.dto.housingcplx.HousingCplx;

import java.util.Date;
import java.util.List;

/**
 * T_NOTI_ITEM
 * 공지 내역
 * 단지  및 서비스 공지 사항 관리 테이블 맵핑 DAO
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
public class NoticeItem extends HousingCplx {
    private Date crDt;
    private Date editDt;
    private String crerId;
    private String editerId;
    private String delYn;

    private int blltNo;
    private String title;
    private String cont;
    private String blltTpCd;
    private int blltGrpNo;
    private String blltTpDtlCd;
    private String blltStsCd;
    private String smsYn;
    private String blltGrpTpCd;
    private String tlrncYn;
    private String hmnetNotiCont;
    private String svcNotiTpCd;

    private String tlrncYnNm;
    private String blltTpDtlCdNm;
    private String blltStsCdNm;
    private String reportDongNo;
    private String reportHoseNo;
    private String reportUserNm;
    private String reportUserId;
    private String userId;
    private String reportMphoneNo;
    private String reportSmsYn;
    private String reportAnsr;
    private List houscplxList;
    private String houscplxCd;
    private String houscplxNm;
    private String hsholdId;
    private String mainPopupYn;
    private String houscplxCdList;

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

    public int getBlltNo() {
        return blltNo;
    }

    public void setBlltNo(int blltNo) {
        this.blltNo = blltNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public String getBlltTpCd() {
        return blltTpCd;
    }

    public void setBlltTpCd(String blltTpCd) {
        this.blltTpCd = blltTpCd;
    }

    public int getBlltGrpNo() {
        return blltGrpNo;
    }

    public void setBlltGrpNo(int blltGrpNo) {
        this.blltGrpNo = blltGrpNo;
    }

    public String getBlltTpDtlCd() {
        return blltTpDtlCd;
    }

    public void setBlltTpDtlCd(String blltTpDtlCd) {
        this.blltTpDtlCd = blltTpDtlCd;
    }

    public String getBlltStsCd() {
        return blltStsCd;
    }

    public void setBlltStsCd(String blltStsCd) {
        this.blltStsCd = blltStsCd;
    }

    public String getSmsYn() {
        return smsYn;
    }

    public void setSmsYn(String smsYn) {
        this.smsYn = smsYn;
    }

    public String getBlltGrpTpCd() {
        return blltGrpTpCd;
    }

    public void setBlltGrpTpCd(String blltGrpTpCd) {
        this.blltGrpTpCd = blltGrpTpCd;
    }

    public String getTlrncYn() {
        return tlrncYn;
    }

    public void setTlrncYn(String tlrncYn) {
        this.tlrncYn = tlrncYn;
    }

    public String getHmnetNotiCont() {
        return hmnetNotiCont;
    }

    public void setHmnetNotiCont(String hmnetNotiCont) {
        this.hmnetNotiCont = hmnetNotiCont;
    }

    public String getTlrncYnNm() {
        return tlrncYnNm;
    }

    public void setTlrncYnNm(String tlrncYnNm) {
        this.tlrncYnNm = tlrncYnNm;
    }

    public String getBlltTpDtlCdNm() {
        return blltTpDtlCdNm;
    }

    public void setBlltTpDtlCdNm(String blltTpDtlCdNm) {
        this.blltTpDtlCdNm = blltTpDtlCdNm;
    }

    public String getBlltStsCdNm() {
        return blltStsCdNm;
    }

    public void setBlltStsCdNm(String blltStsCdNm) {
        this.blltStsCdNm = blltStsCdNm;
    }

    public String getReportDongNo() {
        return reportDongNo;
    }

    public void setReportDongNo(String reportDongNo) {
        this.reportDongNo = reportDongNo;
    }

    public String getReportHoseNo() {
        return reportHoseNo;
    }

    public void setReportHoseNo(String reportHoseNo) {
        this.reportHoseNo = reportHoseNo;
    }

    public String getReportUserNm() {
        return reportUserNm;
    }

    public void setReportUserNm(String reportUserNm) {
        this.reportUserNm = reportUserNm;
    }

    public String getReportUserId() {
        return reportUserId;
    }

    public void setReportUserId(String reportUserId) {
        this.reportUserId = reportUserId;
    }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public String getReportMphoneNo() {
        return reportMphoneNo;
    }

    public void setReportMphoneNo(String reportMphoneNo) {
        this.reportMphoneNo = reportMphoneNo;
    }

    public String getReportSmsYn() {
        return reportSmsYn;
    }

    public void setReportSmsYn(String reportSmsYn) {
        this.reportSmsYn = reportSmsYn;
    }

    public String getReportAnsr() {
        return reportAnsr;
    }

    public void setReportAnsr(String reportAnsr) {
        this.reportAnsr = reportAnsr;
    }

    public String getSvcNotiTpCd() {
        return svcNotiTpCd;
    }

    public void setSvcNotiTpCd(String svcNotiTpCd) {
        this.svcNotiTpCd = svcNotiTpCd;
    }

    public List getHouscplxList() { return houscplxList; }

    public void setHouscplxList(List houscplxList) {
        this.houscplxList = houscplxList;
    }

    public String getHouscplxCd() { return houscplxCd; }

    public void setHouscplxCd(String houscplxCd) { this.houscplxCd = houscplxCd; }

    public String getHouscplxNm() { return houscplxNm; }

    public void setHouscplxNm(String houscplxNm) { this.houscplxNm = houscplxNm; }

    public String getHsholdId() { return hsholdId; }

    public void setHsholdId(String hsholdId) { this.hsholdId = hsholdId; }

    public String getMainPopupYn() { return mainPopupYn; }

    public void setMainPopupYn(String mainPopupYn) { this.mainPopupYn = mainPopupYn; }

    public String getHouscplxCdList() { return houscplxCdList; }

    public void setHouscplxCdList(String houscplxCdList) { this.houscplxCdList = houscplxCdList; }

    @Override
    public String toString() {
        return "NoticeItem{" +
                "crDt=" + crDt +
                ", editDt=" + editDt +
                ", crerId='" + crerId + '\'' +
                ", editerId='" + editerId + '\'' +
                ", delYn='" + delYn + '\'' +
                ", blltNo=" + blltNo +
                ", title='" + title + '\'' +
                ", cont='" + cont + '\'' +
                ", blltTpCd='" + blltTpCd + '\'' +
                ", blltGrpNo=" + blltGrpNo +
                ", blltTpDtlCd='" + blltTpDtlCd + '\'' +
                ", blltStsCd='" + blltStsCd + '\'' +
                ", smsYn='" + smsYn + '\'' +
                ", blltGrpTpCd='" + blltGrpTpCd + '\'' +
                ", tlrncYn='" + tlrncYn + '\'' +
                ", hmnetNotiCont='" + hmnetNotiCont + '\'' +
                ", svcNotiTpCd='" + svcNotiTpCd + '\'' +
                ", tlrncYnNm='" + tlrncYnNm + '\'' +
                ", blltTpDtlCdNm='" + blltTpDtlCdNm + '\'' +
                ", blltStsCdNm='" + blltStsCdNm + '\'' +
                ", reportDongNo='" + reportDongNo + '\'' +
                ", reportHoseNo='" + reportHoseNo + '\'' +
                ", reportUserNm='" + reportUserNm + '\'' +
                ", reportUserId='" + reportUserId + '\'' +
                ", reportMphoneNo='" + reportMphoneNo + '\'' +
                ", reportSmsYn='" + reportSmsYn + '\'' +
                ", reportAnsr='" + reportAnsr + '\'' +
                '}';
    }
}
