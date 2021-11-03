package com.daewooenc.pips.admin.web.domain.dto.community;

import com.daewooenc.pips.admin.web.domain.dto.housingcplx.HousingCplx;

import java.util.Date;

/**
 * T_QST_INVST_BAS
 * 설문 조사 기본
 * 단지별 설문 조사 정보 관리 테이블 맵핑 DAO
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
public class Question extends HousingCplx {
    private int qstNo;
    private String qstTpCd;
    private String qstStsCd;
    private String qstTitle;
    private String qstStDt;
    private String qstEdDt;
    private String crerId;
    private String editerId;
    private Date crDt;
    private Date editDt;
    private String delYn;

    private String qstTpCdNm;
    private String qstStsCdNm;
    private String qstAnsrTitle;
    private String userId;
    private String houscplxCd;
    private int totalMemCnt;
    private int votedMemCnt;

    public int getQstNo() {
        return qstNo;
    }

    public void setQstNo(int qstNo) {
        this.qstNo = qstNo;
    }

    public String getQstTpCd() {
        return qstTpCd;
    }

    public void setQstTpCd(String qstTpCd) {
        this.qstTpCd = qstTpCd;
    }

    public String getQstStsCd() {
        return qstStsCd;
    }

    public void setQstStsCd(String qstStsCd) {
        this.qstStsCd = qstStsCd;
    }

    public String getQstTitle() {
        return qstTitle;
    }

    public void setQstTitle(String qstTitle) {
        this.qstTitle = qstTitle;
    }

    public String getQstStDt() {
        return qstStDt;
    }

    public void setQstStDt(String qstStDt) {
        this.qstStDt = qstStDt;
    }

    public String getQstEdDt() {
        return qstEdDt;
    }

    public void setQstEdDt(String qstEdDt) {
        this.qstEdDt = qstEdDt;
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
    public String getDelYn() {
        return delYn;
    }

    @Override
    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }

    public String getQstTpCdNm() {
        return qstTpCdNm;
    }

    public void setQstTpCdNm(String qstTpCdNm) {
        this.qstTpCdNm = qstTpCdNm;
    }

    public String getQstStsCdNm() {
        return qstStsCdNm;
    }

    public void setQstStsCdNm(String qstStsCdNm) {
        this.qstStsCdNm = qstStsCdNm;
    }

    public String getQstAnsrTitle() {
        return qstAnsrTitle;
    }

    public void setQstAnsrTitle(String qstAnsrTitle) {
        this.qstAnsrTitle = qstAnsrTitle;
    }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public String getHouscplxCd() { return houscplxCd; }

    public void setHouscplxCd(String houscplxCd) { this.houscplxCd = houscplxCd; }

    public int getTotalMemCnt() {
        return totalMemCnt;
    }

    public void setTotalMemCnt(int totalMemCnt) {
        this.totalMemCnt = totalMemCnt;
    }

    public int getVotedMemCnt() {
        return votedMemCnt;
    }

    public void setVotedMemCnt(int votedMemCnt) {
        this.votedMemCnt = votedMemCnt;
    }

    @Override
    public String toString() {
        return "Question{" +
                "qstNo=" + qstNo +
                ", qstTpCd='" + qstTpCd + '\'' +
                ", qstStsCd='" + qstStsCd + '\'' +
                ", qstTitle='" + qstTitle + '\'' +
                ", qstStDt='" + qstStDt + '\'' +
                ", qstEdDt='" + qstEdDt + '\'' +
                ", crerId='" + crerId + '\'' +
                ", editerId='" + editerId + '\'' +
                ", crDt=" + crDt +
                ", editDt=" + editDt +
                ", delYn='" + delYn + '\'' +
                ", qstTpCdNm='" + qstTpCdNm + '\'' +
                ", qstStsCdNm='" + qstStsCdNm + '\'' +
                ", qstAnsrTitle='" + qstAnsrTitle + '\'' +
                ", totalMemCnt=" + totalMemCnt +
                ", votedMemCnt=" + votedMemCnt +
                '}';
    }
}
