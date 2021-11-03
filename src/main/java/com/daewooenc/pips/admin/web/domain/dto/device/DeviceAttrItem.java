package com.daewooenc.pips.admin.web.domain.dto.device;

import java.util.Date;

/**
 * T_DEVICE_ATTR_ITEM
 * 장치 속성 내역
 * 세대별 장치에 대한 메타 정보 관리 테이블 맵핑 DAO
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
public class DeviceAttrItem {
    private String deviceId;
    private Date crDt;
    private Date editDt;
    private String attrCont;
    private String crerId;
    private String editerId;
    private String delYn;
    private String deviceAttrCd;
    private String wpadId;
    private String varCont;

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

    public String getAttrCont() {
        return attrCont;
    }

    public void setAttrCont(String attrCont) {
        this.attrCont = attrCont;
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

    public String getDeviceAttrCd() {
        return deviceAttrCd;
    }

    public void setDeviceAttrCd(String deviceAttrCd) {
        this.deviceAttrCd = deviceAttrCd;
    }

    public String getWpadId() {
        return wpadId;
    }

    public void setWpadId(String wpadId) {
        this.wpadId = wpadId;
    }

    public String getVarCont() {
        return varCont;
    }

    public void setVarCont(String varCont) {
        this.varCont = varCont;
    }

    @Override
    public String toString() {
        return "DeviceAttrItem{" +
                "deviceId='" + deviceId + '\'' +
                ", crDt=" + crDt +
                ", editDt=" + editDt +
                ", attrCont='" + attrCont + '\'' +
                ", crerId='" + crerId + '\'' +
                ", editerId='" + editerId + '\'' +
                ", delYn='" + delYn + '\'' +
                ", deviceAttrCd='" + deviceAttrCd + '\'' +
                ", wpadId='" + wpadId + '\'' +
                ", varCont='" + varCont + '\'' +
                '}';
    }
}