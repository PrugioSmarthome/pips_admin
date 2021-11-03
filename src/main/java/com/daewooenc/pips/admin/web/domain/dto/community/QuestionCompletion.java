package com.daewooenc.pips.admin.web.domain.dto.community;

import java.util.Date;

/**
 * T_QST_CMPLT_HST
 * 설문 완료 이력
 * 세대별 설문 완료 이력 테이블 맵핑 DAO
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
public class QuestionCompletion extends QuestionHousingCplx {
    private int qstNo;
    private String hsholdId;
    private int qstItmNo;
    private String qstEtcAnsr;
    private String crerId;
    private String editerId;
    private Date crDt;
    private Date editDt;

    private int qstCmpltHstNo;

    public int getQstNo() {
        return qstNo;
    }

    public void setQstNo(int qstNo) {
        this.qstNo = qstNo;
    }

    public String getHsholdId() {
        return hsholdId;
    }

    public void setHsholdId(String hsholdId) {
        this.hsholdId = hsholdId;
    }

    public int getQstItmNo() {
        return qstItmNo;
    }

    public void setQstItmNo(int qstItmNo) {
        this.qstItmNo = qstItmNo;
    }

    public String getQstEtcAnsr() {
        return qstEtcAnsr;
    }

    public void setQstEtcAnsr(String qstEtcAnsr) {
        this.qstEtcAnsr = qstEtcAnsr;
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

    public int getQstCmpltHstNo() {
        return qstCmpltHstNo;
    }

    public void setQstCmpltHstNo(int qstCmpltHstNo) {
        this.qstCmpltHstNo = qstCmpltHstNo;
    }

    @Override
    public String toString() {
        return "QuestionCompletion{" +
                "qstNo=" + qstNo +
                ", hsholdId='" + hsholdId + '\'' +
                ", qstItmNo=" + qstItmNo +
                ", qstEtcAnsr='" + qstEtcAnsr + '\'' +
                ", crerId='" + crerId + '\'' +
                ", editerId='" + editerId + '\'' +
                ", crDt=" + crDt +
                ", editDt=" + editDt +
                ", qstCmpltHstNo=" + qstCmpltHstNo +
                '}';
    }
}
