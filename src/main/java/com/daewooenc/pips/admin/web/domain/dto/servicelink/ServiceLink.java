package com.daewooenc.pips.admin.web.domain.dto.servicelink;

import java.util.Date;

/**
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
public class ServiceLink {
    private Date crDt;
    private Date editDt;
    private String crerId;
    private String editerId;
    private String delYn;
    private int lnkSvcId;
    private String lnkSvcTpCd;
    private String lnkSvcGrpTpCd;
    private String lnkSvcNm;
    private String perchrgNm;
    private String offcPhoneNo;
    private String emailNm;
    private String orgnlFileNm;
    private String fileNm;
    private String fileUrlCont;
    private String useYn;
    private String filePathCont;
    private String commCdNm;

    private String startCrDt;
    private String endCrDt;

    private int lnkOrdNo;

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

    public int getLnkSvcId() {
        return lnkSvcId;
    }

    public void setLnkSvcId(int lnkSvcId) {
        this.lnkSvcId = lnkSvcId;
    }

    public String getLnkSvcTpCd() {
        return lnkSvcTpCd;
    }

    public void setLnkSvcTpCd(String lnkSvcTpCd) {
        this.lnkSvcTpCd = lnkSvcTpCd;
    }

    public String getLnkSvcGrpTpCd() {
        return lnkSvcGrpTpCd;
    }

    public void setLnkSvcGrpTpCd(String lnkSvcGrpTpCd) {
        this.lnkSvcGrpTpCd = lnkSvcGrpTpCd;
    }

    public String getLnkSvcNm() {
        return lnkSvcNm;
    }

    public void setLnkSvcNm(String lnkSvcNm) {
        this.lnkSvcNm = lnkSvcNm;
    }

    public String getPerchrgNm() {
        return perchrgNm;
    }

    public void setPerchrgNm(String perchrgNm) {
        this.perchrgNm = perchrgNm;
    }

    public String getOffcPhoneNo() {
        return offcPhoneNo;
    }

    public void setOffcPhoneNo(String offcPhoneNo) {
        this.offcPhoneNo = offcPhoneNo;
    }

    public String getEmailNm() {
        return emailNm;
    }

    public void setEmailNm(String emailNm) {
        this.emailNm = emailNm;
    }

    public String getOrgnlFileNm() {
        return orgnlFileNm;
    }

    public void setOrgnlFileNm(String orgnlFileNm) {
        this.orgnlFileNm = orgnlFileNm;
    }

    public String getFileNm() {
        return fileNm;
    }

    public void setFileNm(String fileNm) {
        this.fileNm = fileNm;
    }

    public String getFileUrlCont() {
        return fileUrlCont;
    }

    public void setFileUrlCont(String fileUrlCont) {
        this.fileUrlCont = fileUrlCont;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getFilePathCont() {
        return filePathCont;
    }

    public void setFilePathCont(String filePathCont) {
        this.filePathCont = filePathCont;
    }

    public String getCommCdNm() {
        return commCdNm;
    }

    public void setCommCdNm(String commCdNm) {
        this.commCdNm = commCdNm;
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

    public int getLnkOrdNo() { return lnkOrdNo; }

    public void setLnkOrdNo(int lnkOrdNo) { this.lnkOrdNo = lnkOrdNo; }

    @Override
    public String toString() {
        return "ServiceLink{" +
                "crDt=" + crDt +
                ", editDt=" + editDt +
                ", crerId='" + crerId + '\'' +
                ", editerId='" + editerId + '\'' +
                ", delYn='" + delYn + '\'' +
                ", lnkSvcId=" + lnkSvcId +
                ", lnkSvcTpCd='" + lnkSvcTpCd + '\'' +
                ", lnkSvcGrpTpCd='" + lnkSvcGrpTpCd + '\'' +
                ", lnkSvcNm='" + lnkSvcNm + '\'' +
                ", perchrgNm='" + perchrgNm + '\'' +
                ", offcPhoneNo='" + offcPhoneNo + '\'' +
                ", emailNm='" + emailNm + '\'' +
                ", orgnlFileNm='" + orgnlFileNm + '\'' +
                ", fileNm='" + fileNm + '\'' +
                ", fileUrlCont='" + fileUrlCont + '\'' +
                ", useYn='" + useYn + '\'' +
                ", filePathCont='" + filePathCont + '\'' +
                ", commCdNm='" + commCdNm + '\'' +
                ", startCrDt='" + startCrDt + '\'' +
                ", endCrDt='" + endCrDt + '\'' +
                '}';
    }
}