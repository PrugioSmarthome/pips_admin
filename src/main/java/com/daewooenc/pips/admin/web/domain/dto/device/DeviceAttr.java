package com.daewooenc.pips.admin.web.domain.dto.device;

import java.util.Date;

/**
 * T_DEVICE_ATTR_BAS
 * 장치 속성 기본
 * 장치별 메타 정보 관리 테이블 맵핑 DAO
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
public class DeviceAttr {
    private String deviceAttrCd;
    private Date crDt;
    private Date editDt;
    private String crerId;
    private String editerId;
    private String delYn;
    private String rem;

    public String getDeviceAttrCd() {
        return deviceAttrCd;
    }

    public void setDeviceAttrCd(String deviceAttrCd) {
        this.deviceAttrCd = deviceAttrCd;
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

    public String getRem() {
        return rem;
    }

    public void setRem(String rem) {
        this.rem = rem;
    }

    @Override
    public String toString() {
        return "DeviceAttr{" +
                "deviceAttrCd='" + deviceAttrCd + '\'' +
                ", crDt=" + crDt +
                ", editDt=" + editDt +
                ", crerId='" + crerId + '\'' +
                ", editerId='" + editerId + '\'' +
                ", delYn='" + delYn + '\'' +
                ", rem='" + rem + '\'' +
                '}';
    }
}