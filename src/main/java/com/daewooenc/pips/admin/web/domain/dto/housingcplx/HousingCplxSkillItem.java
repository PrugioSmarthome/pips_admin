package com.daewooenc.pips.admin.web.domain.dto.housingcplx;

import java.util.Date;

/**
 * T_HOUSCPLX_SKIL_ITEM
 * 단지 기능 내역
 * 단지에서 제공하는 주차, 택배 등의 기능 리스트 관리 테이블 맵핑 DAO
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
public class HousingCplxSkillItem {
    private Date crDt;
    private String houscplxCd;
    private String crerId;
    private String refCont;
    private String skilGrpCd;
    private String skilCd;

    public Date getCrDt() {
        return crDt;
    }

    public void setCrDt(Date crDt) {
        this.crDt = crDt;
    }

    public String getHouscplxCd() {
        return houscplxCd;
    }

    public void setHouscplxCd(String houscplxCd) {
        this.houscplxCd = houscplxCd;
    }

    public String getCrerId() {
        return crerId;
    }

    public void setCrerId(String crerId) {
        this.crerId = crerId;
    }

    public String getRefCont() {
        return refCont;
    }

    public void setRefCont(String refCont) {
        this.refCont = refCont;
    }

    public String getSkilGrpCd() {
        return skilGrpCd;
    }

    public void setSkilGrpCd(String skilGrpCd) {
        this.skilGrpCd = skilGrpCd;
    }

    public String getSkilCd() {
        return skilCd;
    }

    public void setSkilCd(String skilCd) {
        this.skilCd = skilCd;
    }

    @Override
    public String toString() {
        return "HousingCplxSkillItem{" +
                "crDt=" + crDt +
                ", houscplxCd='" + houscplxCd + '\'' +
                ", crerId='" + crerId + '\'' +
                ", refCont='" + refCont + '\'' +
                ", skilGrpCd='" + skilGrpCd + '\'' +
                ", skilCd='" + skilCd + '\'' +
                '}';
    }
}
