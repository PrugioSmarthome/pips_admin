package com.daewooenc.pips.admin.web.domain.dto.manufacturerByDevice;

import java.util.Date;

/**
 * T_HOUSCPLX_DEVICE_MF_RLT
 * 장치별 제조사 관리 기본
 * 장치별 제조사 관리 테이블 맵핑 DAO
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2021-03-23      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2021-03-23
 **/
public class ManufacturerByDevice {
    private String houscplxCd;
    private String houscplxCdDB;
    private String houscplxNm;
    private String deviceTpCd;
    private String deviceTpCdDB;
    private String deviceTpCdOrgn;
    private String deviceMfCd;
    private String deviceMfCdDB;
    private String commCd;
    private String commCdNm;
    private String crerId;
    private String editerId;
    private Date crDt;
    private Date editDt;


    public String getHouscplxCd() {
        return houscplxCd;
    }

    public void setHouscplxCd(String houscplxCd) {
        this.houscplxCd = houscplxCd;
    }

    public String getHouscplxCdDB() {
        return houscplxCdDB;
    }

    public void setHouscplxCdDB(String houscplxCdDB) {
        this.houscplxCdDB = houscplxCdDB;
    }

    public String getHouscplxNm() { return houscplxNm; }

    public void setHouscplxNm(String houscplxNm) { this.houscplxNm = houscplxNm; }

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

    public String getDeviceTpCd() {
        return deviceTpCd;
    }

    public void setDeviceTpCd(String deviceTpCd) {
        this.deviceTpCd = deviceTpCd;
    }

    public String getDeviceTpCdDB() {
        return deviceTpCdDB;
    }

    public void setDeviceTpCdDB(String deviceTpCdDB) {
        this.deviceTpCdDB = deviceTpCdDB;
    }

    public String getDeviceTpCdOrgn() { return deviceTpCdOrgn; }

    public void setDeviceTpCdOrgn(String deviceTpCdOrgn) { this.deviceTpCdOrgn = deviceTpCdOrgn; }

    public String getDeviceMfCd() {
        return deviceMfCd;
    }

    public void setDeviceMfCd(String deviceMfCd) {
        this.deviceMfCd = deviceMfCd;
    }

    public String getDeviceMfCdDB() {
        return deviceMfCdDB;
    }

    public void setDeviceMfCdDB(String deviceMfCdDB) {
        this.deviceMfCdDB = deviceMfCdDB;
    }

    public String getCommCd() { return commCd; }

    public void setCommCd(String commCd) { this.commCd = commCd; }

    public String getCommCdNm() { return commCdNm; }

    public void setCommCdNm(String commCdNm) { this.commCdNm = commCdNm; }

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

    @Override
    public String toString() {
        return "Device{" +
                ", crDt=" + crDt +
                ", editDt=" + editDt +
                ", deviceTpCd='" + deviceTpCd + '\'' +
                ", deviceTpCdOrgn='" + deviceTpCdOrgn + '\'' +
                ", deviceMfCd='" + deviceMfCd + '\'' +
                ", houscplxCd='" + houscplxCd + '\'' +
                ", commCd='" + commCd + '\'' +
                ", commCdNm='" + commCdNm + '\'' +
                ", crerId='" + crerId + '\'' +
                ", editerId='" + editerId + '\'' +
                '}';
    }
}