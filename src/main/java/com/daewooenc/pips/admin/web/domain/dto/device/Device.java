package com.daewooenc.pips.admin.web.domain.dto.device;

import java.util.Date;

/**
 * T_DEVICE_BAS
 * 장치 기본
 * 세대별 장치 정보 테이블 맵핑 DAO
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-08-20      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-08-20
 **/
public class Device {
    private String deviceId;
    private Date crDt;
    private Date editDt;
    private String deviceTpCd;
    private String mdlNm;
    private String serlNo;
    private String mnfcoNm;
    private String deviceNm;
    private String locaNm;
    private String crerId;
    private String editerId;
    private String delYn;
    private String wpadId;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
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

    public String getDeviceTpCd() {
        return deviceTpCd;
    }

    public void setDeviceTpCd(String deviceTpCd) {
        this.deviceTpCd = deviceTpCd;
    }

    public String getMdlNm() {
        return mdlNm;
    }

    public void setMdlNm(String mdlNm) {
        this.mdlNm = mdlNm;
    }

    public String getSerlNo() {
        return serlNo;
    }

    public void setSerlNo(String serlNo) {
        this.serlNo = serlNo;
    }

    public String getMnfcoNm() {
        return mnfcoNm;
    }

    public void setMnfcoNm(String mnfcoNm) {
        this.mnfcoNm = mnfcoNm;
    }

    public String getDeviceNm() {
        return deviceNm;
    }

    public void setDeviceNm(String deviceNm) {
        this.deviceNm = deviceNm;
    }

    public String getLocaNm() {
        return locaNm;
    }

    public void setLocaNm(String locaNm) {
        this.locaNm = locaNm;
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

    public String getWpadId() {
        return wpadId;
    }

    public void setWpadId(String wpadId) {
        this.wpadId = wpadId;
    }

    @Override
    public String toString() {
        return "Device{" +
                "deviceId='" + deviceId + '\'' +
                ", crDt=" + crDt +
                ", editDt=" + editDt +
                ", deviceTpCd='" + deviceTpCd + '\'' +
                ", mdlNm='" + mdlNm + '\'' +
                ", serlNo='" + serlNo + '\'' +
                ", mnfcoNm='" + mnfcoNm + '\'' +
                ", deviceNm='" + deviceNm + '\'' +
                ", locaNm='" + locaNm + '\'' +
                ", crerId='" + crerId + '\'' +
                ", editerId='" + editerId + '\'' +
                ", delYn='" + delYn + '\'' +
                ", wpadId='" + wpadId + '\'' +
                '}';
    }
}