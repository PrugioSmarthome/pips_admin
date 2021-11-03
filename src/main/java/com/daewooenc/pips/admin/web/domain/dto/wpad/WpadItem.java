package com.daewooenc.pips.admin.web.domain.dto.wpad;

import java.util.Date;

/**
 * T_WPAD_ATTR_ITEM
 * 월패드 속성 내역
 * 방범등의 월패트 기능 관리 테이블 맵핑 DAO
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
public class WpadItem {
    private Date crDt;
    private Date editDt;
    private String crerId;
    private String editerId;
    private String delYn;
    private String wpadId;
    private String attrCont;
    private String deviceAttrCd;
    private String varCont;

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

    public String getWpadId() {
        return wpadId;
    }

    public void setWpadId(String wpadId) {
        this.wpadId = wpadId;
    }

    public String getAttrCont() {
        return attrCont;
    }

    public void setAttrCont(String attrCont) {
        this.attrCont = attrCont;
    }

    public String getDeviceAttrCd() {
        return deviceAttrCd;
    }

    public void setDeviceAttrCd(String deviceAttrCd) {
        this.deviceAttrCd = deviceAttrCd;
    }

    public String getVarCont() {
        return varCont;
    }

    public void setVarCont(String varCont) {
        this.varCont = varCont;
    }

    @Override
    public String toString() {
        return "WpadItem{" +
                "crDt=" + crDt +
                ", editDt=" + editDt +
                ", crerId='" + crerId + '\'' +
                ", editerId='" + editerId + '\'' +
                ", delYn='" + delYn + '\'' +
                ", wpadId='" + wpadId + '\'' +
                ", attrCont='" + attrCont + '\'' +
                ", deviceAttrCd='" + deviceAttrCd + '\'' +
                ", varCont='" + varCont + '\'' +
                '}';
    }
}