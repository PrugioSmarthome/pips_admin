package com.daewooenc.pips.admin.web.domain.dto.housingcplx;

import java.util.Date;

/**
 * T_HOUSCPLX_CADDR_GDNC_BAS
 * 단지 연락처 안내 기본
 * 단지 전체 연락처에 대한 Comment 관리 테이블 맵핑 DAO
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
public class HousingCplxCaddrGdnc {
    private Date crDt;
    private Date editDt;
    private String cont;
    private String crerId;
    private String editerId;
    private String delYn;
    private String houscplxCd;
    private String workpTpCd;

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

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
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

    public String getWorkpTpCd() {
        return workpTpCd;
    }

    public void setWorkpTpCd(String workpTpCd) {
        this.workpTpCd = workpTpCd;
    }

    @Override
    public String toString() {
        return "HousingCplxCaddrGdnc{" +
                "crDt=" + crDt +
                ", editDt=" + editDt +
                ", cont='" + cont + '\'' +
                ", crerId='" + crerId + '\'' +
                ", editerId='" + editerId + '\'' +
                ", delYn='" + delYn + '\'' +
                ", houscplxCd='" + houscplxCd + '\'' +
                ", workpTpCd='" + workpTpCd + '\'' +
                '}';
    }
}
