package com.daewooenc.pips.admin.web.domain.dto.cctv;

import java.util.Date;

/**
 * T_CCTV_INFO_BAS
 * CCTV 정보 기본
 * 단지별 CCTV 연동 정보 관리 테이블 맵핑 DAO
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-08-19      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-08-19
 **/
public class CCTV {
    private Date crDt;
    private Date editDt;
    private String crerId;
    private String editerId;
    private String delYn;
    private String houscplxCd;
    private String urlCont;
    private String certfInfoCont;
    private String cctvNm;
    private String locaNm;
    private String cont;

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

    public String getHouscplxCd() {
        return houscplxCd;
    }

    public void setHouscplxCd(String houscplxCd) {
        this.houscplxCd = houscplxCd;
    }

    public String getUrlCont() {
        return urlCont;
    }

    public void setUrlCont(String urlCont) {
        this.urlCont = urlCont;
    }

    public String getCertfInfoCont() {
        return certfInfoCont;
    }

    public void setCertfInfoCont(String certfInfoCont) {
        this.certfInfoCont = certfInfoCont;
    }

    public String getCctvNm() {
        return cctvNm;
    }

    public void setCctvNm(String cctvNm) {
        this.cctvNm = cctvNm;
    }

    public String getLocaNm() {
        return locaNm;
    }

    public void setLocaNm(String locaNm) {
        this.locaNm = locaNm;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    @Override
    public String toString() {
        return "CCTV{" +
                "crDt=" + crDt +
                ", editDt=" + editDt +
                ", crerId='" + crerId + '\'' +
                ", editerId='" + editerId + '\'' +
                ", delYn='" + delYn + '\'' +
                ", houscplxCd='" + houscplxCd + '\'' +
                ", urlCont='" + urlCont + '\'' +
                ", certfInfoCont='" + certfInfoCont + '\'' +
                ", cctvNm='" + cctvNm + '\'' +
                ", locaNm='" + locaNm + '\'' +
                ", cont='" + cont + '\'' +
                '}';
    }
}