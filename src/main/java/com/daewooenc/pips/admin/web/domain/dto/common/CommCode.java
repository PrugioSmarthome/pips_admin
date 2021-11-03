package com.daewooenc.pips.admin.web.domain.dto.common;

import java.util.Date;

/**
 * T_COMM_CD_BAS
 * 공통 코드 기본
 * 스마트홈 플랫폼 공통 그룹 코드 관리 테이블
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
public class CommCode {
    private String commCdGrpNm;
    private String sysId;
    private String lenQty;
    private String refCd1;
    private String refCd2;
    private String refCd3;
    private String rem;
    private String useYn;
    private String sysUseYn;
    private String inptMenuId;
    private Date crDt;
    private Date editDt;
    private String crerId;
    private String editerId;
    private String commCdGrpCd;

    public String getCommCdGrpNm() {
        return commCdGrpNm;
    }

    public void setCommCdGrpNm(String commCdGrpNm) {
        this.commCdGrpNm = commCdGrpNm;
    }

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    public String getLenQty() {
        return lenQty;
    }

    public void setLenQty(String lenQty) {
        this.lenQty = lenQty;
    }

    public String getRefCd1() {
        return refCd1;
    }

    public void setRefCd1(String refCd1) {
        this.refCd1 = refCd1;
    }

    public String getRefCd2() {
        return refCd2;
    }

    public void setRefCd2(String refCd2) {
        this.refCd2 = refCd2;
    }

    public String getRefCd3() {
        return refCd3;
    }

    public void setRefCd3(String refCd3) {
        this.refCd3 = refCd3;
    }

    public String getRem() {
        return rem;
    }

    public void setRem(String rem) {
        this.rem = rem;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getSysUseYn() {
        return sysUseYn;
    }

    public void setSysUseYn(String sysUseYn) {
        this.sysUseYn = sysUseYn;
    }

    public String getInptMenuId() {
        return inptMenuId;
    }

    public void setInptMenuId(String inptMenuId) {
        this.inptMenuId = inptMenuId;
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

    public String getCommCdGrpCd() {
        return commCdGrpCd;
    }

    public void setCommCdGrpCd(String commCdGrpCd) {
        this.commCdGrpCd = commCdGrpCd;
    }

    @Override
    public String toString() {
        return "CommCode{" +
                "commCdGrpNm='" + commCdGrpNm + '\'' +
                ", sysId='" + sysId + '\'' +
                ", lenQty='" + lenQty + '\'' +
                ", refCd1='" + refCd1 + '\'' +
                ", refCd2='" + refCd2 + '\'' +
                ", refCd3='" + refCd3 + '\'' +
                ", rem='" + rem + '\'' +
                ", useYn='" + useYn + '\'' +
                ", sysUseYn='" + sysUseYn + '\'' +
                ", inptMenuId='" + inptMenuId + '\'' +
                ", crDt=" + crDt +
                ", editDt=" + editDt +
                ", crerId='" + crerId + '\'' +
                ", editerId='" + editerId + '\'' +
                ", commCdGrpCd='" + commCdGrpCd + '\'' +
                '}';
    }
}