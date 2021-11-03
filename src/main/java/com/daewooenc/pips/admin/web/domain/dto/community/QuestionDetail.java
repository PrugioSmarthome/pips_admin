package com.daewooenc.pips.admin.web.domain.dto.community;

import java.util.Date;

/**
 * T_QST_INVST_DTL
 * 설문 조사 상세
 * 단지별 설문 조사상세  내용 관리 테이블 맵핑 DAO
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
public class QuestionDetail extends QuestionHousingCplx {
    private int qstItmNo;
    private int qstNo;
    private String qstItmQuestCont;
    private String qstItmAnsrCont;
    private String delYn;
    private String crerId;
    private String editerId;
    private Date crDt;
    private Date editDt;
    private int qstItmAnsrCnt;

    public int getQstItmNo() {
        return qstItmNo;
    }

    public void setQstItmNo(int qstItmNo) {
        this.qstItmNo = qstItmNo;
    }

    public int getQstNo() {
        return qstNo;
    }

    public void setQstNo(int qstNo) {
        this.qstNo = qstNo;
    }

    public String getQstItmQuestCont() {
        return qstItmQuestCont;
    }

    public void setQstItmQuestCont(String qstItmQuestCont) {
        this.qstItmQuestCont = qstItmQuestCont;
    }

    public String getQstItmAnsrCont() {
        return qstItmAnsrCont;
    }

    public void setQstItmAnsrCont(String qstItmAnsrCont) {
        this.qstItmAnsrCont = qstItmAnsrCont;
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

    public int getQstItmAnsrCnt() {
        return qstItmAnsrCnt;
    }

    public void setQstItmAnsrCnt(int qstItmAnsrCnt) {
        this.qstItmAnsrCnt = qstItmAnsrCnt;
    }

    @Override
    public String toString() {
        return "QuestionDetail{" +
                "qstItmNo=" + qstItmNo +
                ", qstNo=" + qstNo +
                ", qstItmQuestCont='" + qstItmQuestCont + '\'' +
                ", qstItmAnsrCont='" + qstItmAnsrCont + '\'' +
                ", delYn='" + delYn + '\'' +
                ", crerId='" + crerId + '\'' +
                ", editerId='" + editerId + '\'' +
                ", crDt=" + crDt +
                ", editDt=" + editDt +
                ", qstItmAnsrCnt=" + qstItmAnsrCnt +
                '}';
    }
}
