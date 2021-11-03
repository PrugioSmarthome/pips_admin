package com.daewooenc.pips.admin.web.domain.dto.serviceinter;

public class ServiceInterInfo {
    private String serviceNo;
    private String serviceTpGrCd;
    private String serviceTpCd;
    private String serviceNm;
    private String userId;
    private String serviceAuthKey;
    private String urlCont;
    private String cont;

    public String getServiceNo() {
        return serviceNo;
    }

    public void setServiceNo(String serviceNo) {
        this.serviceNo = serviceNo;
    }

    public String getServiceTpGrCd() {
        return serviceTpGrCd;
    }

    public void setServiceTpGrCd(String serviceTpGrCd) {
        this.serviceTpGrCd = serviceTpGrCd;
    }

    public String getServiceTpCd() {
        return serviceTpCd;
    }

    public void setServiceTpCd(String serviceTpCd) {
        this.serviceTpCd = serviceTpCd;
    }

    public String getServiceNm() {
        return serviceNm;
    }

    public void setServiceNm(String serviceNm) {
        this.serviceNm = serviceNm;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getServiceAuthKey() {
        return serviceAuthKey;
    }

    public void setServiceAuthKey(String serviceAuthKey) {
        this.serviceAuthKey = serviceAuthKey;
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
}
