package com.daewooenc.pips.admin.web.domain.dto.device;

/**
 * 시스템 및 단지관리자의 세대장치 관리 상세 목록을 위한 맵핑 DAO
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
public class HouseholdDeviceDetail {
    private String wpadId;
    private String deviceTpCd;
    private String deviceTpCdNm;
    private String deviceNm;
    private String locaNm;
    private String mnfcoNm;
    private String serlNo;
    private String mdlNm;
    private String deviceId;
    private String deviceAttrFunc;
    private String delYn;

    public String getWpadId() {
        return wpadId;
    }

    public void setWpadId(String wpadId) {
        this.wpadId = wpadId;
    }

    public String getDeviceTpCd() {
        return deviceTpCd;
    }

    public void setDeviceTpCd(String deviceTpCd) {
        this.deviceTpCd = deviceTpCd;
    }

    public String getDeviceTpCdNm() {
        return deviceTpCdNm;
    }

    public void setDeviceTpCdNm(String deviceTpCdNm) {
        this.deviceTpCdNm = deviceTpCdNm;
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

    public String getMnfcoNm() {
        return mnfcoNm;
    }

    public void setMnfcoNm(String mnfcoNm) {
        this.mnfcoNm = mnfcoNm;
    }

    public String getSerlNo() {
        return serlNo;
    }

    public void setSerlNo(String serlNo) {
        this.serlNo = serlNo;
    }

    public String getMdlNm() {
        return mdlNm;
    }

    public void setMdlNm(String mdlNm) {
        this.mdlNm = mdlNm;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceAttrFunc() {
        return deviceAttrFunc;
    }

    public void setDeviceAttrFunc(String deviceAttrFunc) {
        this.deviceAttrFunc = deviceAttrFunc;
    }

    public String getDelYn() {
        return delYn;
    }

    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }

    @Override
    public String toString() {
        return "HouseholdDeviceDetail{" +
                "wpadId='" + wpadId + '\'' +
                ", deviceTpCd='" + deviceTpCd + '\'' +
                ", deviceTpCdNm='" + deviceTpCdNm + '\'' +
                ", deviceNm='" + deviceNm + '\'' +
                ", locaNm='" + locaNm + '\'' +
                ", mnfcoNm='" + mnfcoNm + '\'' +
                ", serlNo='" + serlNo + '\'' +
                ", mdlNm='" + mdlNm + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", deviceAttrFunc='" + deviceAttrFunc + '\'' +
                ", delYn=" + delYn + '\'' +
                '}';
    }
}