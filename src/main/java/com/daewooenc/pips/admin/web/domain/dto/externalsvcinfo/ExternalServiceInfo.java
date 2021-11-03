package com.daewooenc.pips.admin.web.domain.dto.externalsvcinfo;

/**
 * T_EXTNL_SVC_BAS
 * 외부 서비스 기본
 * 외부 서비스 정보에 대한 테이블 맵핑 DAO
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description <br/>
 * ---------------------------------------------------------- <br/>
 *    2019-09-09       :       yckim        :                  <br/>
 *
 * </pre>
 * @since : 2019-09-09
 **/
public class ExternalServiceInfo {
    private String svcId;
    private String crDt;
    private String editDt;
    private String crerId;
    private String editerId;
    private String delYn;
    private String svcNm;
    private String svcTpCd;
    private String svcGrpTpCd;
    private String userId;
    private String svcKeyCd;
    private String urlCont;
    private String cont;
    private String exprtnYmd;

    public String getSvcId() {
        return svcId;
    }

    public void setSvcId(String svcId) {
        this.svcId = svcId;
    }

    public String getCrDt() {
        return crDt;
    }

    public void setCrDt(String crDt) {
        this.crDt = crDt;
    }

    public String getEditDt() {
        return editDt;
    }

    public void setEditDt(String editDt) {
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

    public String getSvcNm() {
        return svcNm;
    }

    public void setSvcNm(String svcNm) {
        this.svcNm = svcNm;
    }

    public String getSvcTpCd() {
        return svcTpCd;
    }

    public void setSvcTpCd(String svcTpCd) {
        this.svcTpCd = svcTpCd;
    }

    public String getSvcGrpTpCd() {
        return svcGrpTpCd;
    }

    public void setSvcGrpTpCd(String svcGrpTpCd) {
        this.svcGrpTpCd = svcGrpTpCd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSvcKeyCd() {
        return svcKeyCd;
    }

    public void setSvcKeyCd(String svcKeyCd) {
        this.svcKeyCd = svcKeyCd;
    }

    public String getUrlCont() {
        return urlCont;
    }

    public void setUrlCont(String urlCont) {
        this.urlCont = urlCont;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public String getExprtnYmd() {
        return exprtnYmd;
    }

    public void setExprtnYmd(String exprtnYmd) {
        this.exprtnYmd = exprtnYmd;
    }

    @Override
    public String toString() {
        return "ExternalServiceInfo{" +
                "svcId='" + svcId + '\'' +
                ", crDt='" + crDt + '\'' +
                ", editDt='" + editDt + '\'' +
                ", crerId='" + crerId + '\'' +
                ", editerId='" + editerId + '\'' +
                ", delYn='" + delYn + '\'' +
                ", svcNm='" + svcNm + '\'' +
                ", svcTpCd='" + svcTpCd + '\'' +
                ", svcGrpTpCd='" + svcGrpTpCd + '\'' +
                ", userId='" + userId + '\'' +
                ", svcKeyCd='" + svcKeyCd + '\'' +
                ", urlCont='" + urlCont + '\'' +
                ", cont='" + cont + '\'' +
                ", exprtnYmd='" + exprtnYmd + '\'' +
                '}';
    }
}
