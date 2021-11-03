package com.daewooenc.pips.admin.web.domain.dto.common;

import java.util.Date;

/**
 * T_COMM_CD_DTL
 * 공통 코드 상세
 * 스마트홈 플랫폼 공통 코드 관리 테이블
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
public class CommCodeDetail {
    private String commCdNm;
    private String ref1Cd;
    private String ref2Cd;
    private String ref3Cd;
    private String ref4Cd;
    private String basvalYn;
    private String rem;
    private int arrayOrdNo;
    private String useYn;
    private String sysUseYn;
    private String inputMenuId;
    private String crerId;
    private Date crDt;
    private String editerId;
    private Date editDt;
    private String commCd;
    private String commCdGrpCd;

    public String getCommCdNm() {
        return commCdNm;
    }

    public void setCommCdNm(String commCdNm) {
        this.commCdNm = commCdNm;
    }

    public String getRef1Cd() {
        return ref1Cd;
    }

    public void setRef1Cd(String ref1Cd) {
        this.ref1Cd = ref1Cd;
    }

    public String getRef2Cd() {
        return ref2Cd;
    }

    public void setRef2Cd(String ref2Cd) {
        this.ref2Cd = ref2Cd;
    }

    public String getRef3Cd() {
        return ref3Cd;
    }

    public void setRef3Cd(String ref3Cd) {
        this.ref3Cd = ref3Cd;
    }

    public String getRef4Cd() {
        return ref4Cd;
    }

    public void setRef4Cd(String ref4Cd) {
        this.ref4Cd = ref4Cd;
    }

    public String getBasvalYn() {
        return basvalYn;
    }

    public void setBasvalYn(String basvalYn) {
        this.basvalYn = basvalYn;
    }

    public String getRem() {
        return rem;
    }

    public void setRem(String rem) {
        this.rem = rem;
    }

    public int getArrayOrdNo() {
        return arrayOrdNo;
    }

    public void setArrayOrdNo(int arrayOrdNo) {
        this.arrayOrdNo = arrayOrdNo;
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

    public String getInputMenuId() {
        return inputMenuId;
    }

    public void setInputMenuId(String inputMenuId) {
        this.inputMenuId = inputMenuId;
    }

    public String getCrerId() {
        return crerId;
    }

    public void setCrerId(String crerId) {
        this.crerId = crerId;
    }

    public Date getCrDt() {
        return crDt;
    }

    public void setCrDt(Date crDt) {
        this.crDt = crDt;
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

    public String getCommCd() {
        return commCd;
    }

    public void setCommCd(String commCd) {
        this.commCd = commCd;
    }

    public String getCommCdGrpCd() {
        return commCdGrpCd;
    }

    public void setCommCdGrpCd(String commCdGrpCd) {
        this.commCdGrpCd = commCdGrpCd;
    }

    @Override
    public String toString() {
        return "CommCodeDetail{" +
                "commCdNm='" + commCdNm + '\'' +
                ", ref1Cd='" + ref1Cd + '\'' +
                ", ref2Cd='" + ref2Cd + '\'' +
                ", ref3Cd='" + ref3Cd + '\'' +
                ", ref4Cd='" + ref4Cd + '\'' +
                ", basvalYn='" + basvalYn + '\'' +
                ", rem='" + rem + '\'' +
                ", arrayOrdNo=" + arrayOrdNo +
                ", useYn='" + useYn + '\'' +
                ", sysUseYn='" + sysUseYn + '\'' +
                ", inputMenuId='" + inputMenuId + '\'' +
                ", crerId='" + crerId + '\'' +
                ", crDt=" + crDt +
                ", editerId='" + editerId + '\'' +
                ", editDt=" + editDt +
                ", commCd='" + commCd + '\'' +
                ", commCdGrpCd='" + commCdGrpCd + '\'' +
                '}';
    }
}