package com.daewooenc.pips.admin.web.domain.dto.wpad;

import java.util.Date;

/**
 * T_WPAD_BAS
 * 월패드 기본
 * 월패드 정보 관리 테이블 맵핑 DAO
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-08-20      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-08-20
 **/
public class Wpad {
    private Date crDt;
    private Date editDt;
    private String crerId;
    private String editerId;
    private String delYn;
    private char mdlNm;
    private char serlNo;
    private char mnfcoNm;
    private String wpadId;
    private String hsholdId;

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

    public char getMdlNm() {
        return mdlNm;
    }

    public void setMdlNm(char mdlNm) {
        this.mdlNm = mdlNm;
    }

    public char getSerlNo() {
        return serlNo;
    }

    public void setSerlNo(char serlNo) {
        this.serlNo = serlNo;
    }

    public char getMnfcoNm() {
        return mnfcoNm;
    }

    public void setMnfcoNm(char mnfcoNm) {
        this.mnfcoNm = mnfcoNm;
    }

    public String getWpadId() {
        return wpadId;
    }

    public void setWpadId(String wpadId) {
        this.wpadId = wpadId;
    }

    public String getHsholdId() {
        return hsholdId;
    }

    public void setHsholdId(String hsholdId) {
        this.hsholdId = hsholdId;
    }

    @Override
    public String toString() {
        return "Wpad{" +
                "crDt=" + crDt +
                ", editDt=" + editDt +
                ", crerId='" + crerId + '\'' +
                ", editerId='" + editerId + '\'' +
                ", delYn='" + delYn + '\'' +
                ", mdlNm=" + mdlNm +
                ", serlNo=" + serlNo +
                ", mnfcoNm=" + mnfcoNm +
                ", wpadId='" + wpadId + '\'' +
                ", hsholdId='" + hsholdId + '\'' +
                '}';
    }
}