package com.daewooenc.pips.admin.web.domain.dto.housingcplx;

import java.util.Date;

/**
 * T_HOUSCPLX_CADDR_BAS
 * 단지 연락처 기본
 * 관리 사무소, 경비실등의 단지 연락처 관리 테이블 맵핑 DAO
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
public class HousingCplxCaddr extends HousingCplxCaddrGdnc {
    private String houscplxCd;
    private Date crDt;
    private Date editDt;
    private String rem;
    private String caddrTpCd;
    private String crerId;
    private String editerId;
    private String delYn;
    private String workpTpCd;
    private int ordNo;
    private String caddrCont;
    private String cntctStime;
    private String cntctEtime;

    private String encKey;

    @Override
    public String getHouscplxCd() {
        return houscplxCd;
    }

    @Override
    public void setHouscplxCd(String houscplxCd) {
        this.houscplxCd = houscplxCd;
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

    public String getRem() {
        return rem;
    }

    public void setRem(String rem) {
        this.rem = rem;
    }

    public String getCaddrTpCd() {
        return caddrTpCd;
    }

    public void setCaddrTpCd(String caddrTpCd) {
        this.caddrTpCd = caddrTpCd;
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
    public String getDelYn() {
        return delYn;
    }

    @Override
    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }

    @Override
    public String getWorkpTpCd() {
        return workpTpCd;
    }

    @Override
    public void setWorkpTpCd(String workpTpCd) {
        this.workpTpCd = workpTpCd;
    }

    public int getOrdNo() {
        return ordNo;
    }

    public void setOrdNo(int ordNo) {
        this.ordNo = ordNo;
    }

    public String getCaddrCont() {
        return caddrCont;
    }

    public void setCaddrCont(String caddrCont) {
        this.caddrCont = caddrCont;
    }

    public String getCntctStime() {
        return cntctStime;
    }

    public void setCntctStime(String cntctStime) {
        this.cntctStime = cntctStime;
    }

    public String getCntctEtime() {
        return cntctEtime;
    }

    public void setCntctEtime(String cntctEtime) {
        this.cntctEtime = cntctEtime;
    }

    public String getEncKey() {
        return encKey;
    }

    public void setEncKey(String encKey) {
        this.encKey = encKey;
    }

    @Override
    public String toString() {
        return "HousingCplxCaddr{" +
                "houscplxCd='" + houscplxCd + '\'' +
                ", crDt=" + crDt +
                ", editDt=" + editDt +
                ", rem='" + rem + '\'' +
                ", caddrTpCd='" + caddrTpCd + '\'' +
                ", crerId='" + crerId + '\'' +
                ", editerId='" + editerId + '\'' +
                ", delYn='" + delYn + '\'' +
                ", workpTpCd='" + workpTpCd + '\'' +
                ", ordNo=" + ordNo +
                ", caddrCont='" + caddrCont + '\'' +
                ", cntctStime='" + cntctStime + '\'' +
                ", cntctEtime='" + cntctEtime + '\'' +
                ", encKey='" + encKey + '\'' +
                '}';
    }
}
