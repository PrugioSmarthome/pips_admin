package com.daewooenc.pips.admin.web.domain.dto.device;

import java.util.Date;

/**
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2020-11-23      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2020-11-23
 **/
public class SystemDevice {
    private String hmnetId;
    private String bizcoCd;
    private String houscplxNm;
    private String houscplxCd;
    private String dongNo;
    private String hoseNo;
    private String wpadId;
    private String crerId;
    private String deviceTpCd;
    private String deviceTpCdNm;
    private String deviceNm;
    private String deviceId;
    private String screenYn;
    private String locaNm;
    private String mnfcoNm;
    private String serlNo;
    private String mdlNm;

    private String lightsYn;
    private int lights;
    private int lightsY;
    private int lightsN;
    private String gaslockYn;
    private int gaslock;
    private int gaslockY;
    private int gaslockN;
    private String airconYn;
    private int aircon;
    private int airconY;
    private int airconN;
    private String heatingYn;
    private int heating;
    private int heatingY;
    private int heatingN;
    private String ventilatorYn;
    private int ventilator;
    private int ventilatorY;
    private int ventilatorN;
    private String smartConsentYn;
    private int smartConsent;
    private int smartConsentY;
    private int smartConsentN;
    private String curtainYn;
    private int curtain;
    private int curtainY;
    private int curtainN;
    private String lightSwitchYn;
    private int lightSwitch;
    private int lightSwitchY;
    private int lightSwitchN;

    public void setLightSwitchYn(String lightSwitchYn) { this.lightSwitchYn = lightSwitchYn; }

    public void setLightSwitch(int lightSwitch) { this.lightSwitch = lightSwitch; }

    public void setLightSwitchY(int lightSwitchY) { this.lightSwitchY = lightSwitchY; }

    public void setLightSwitchN(int lightSwitchN) { this.lightSwitchN = lightSwitchN; }

    public String getLightSwitchYn() { return lightSwitchYn; }

    public int getLightSwitch() { return lightSwitch; }

    public int getLightSwitchY() { return lightSwitchY; }

    public int getLightSwitchN() { return lightSwitchN; }

    public String getHmnetId() {
        return hmnetId;
    }

    public void setHmnetId(String hmnetId) {
        this.hmnetId = hmnetId;
    }

    public String getBizcoCd() {
        return bizcoCd;
    }

    public void setBizcoCd(String bizcoCd) {
        this.bizcoCd = bizcoCd;
    }

    public String getHouscplxNm() {
        return houscplxNm;
    }

    public void setHouscplxNm(String houscplxNm) {
        this.houscplxNm = houscplxNm;
    }

    public String getHouscplxCd() {
        return houscplxCd;
    }

    public void setHouscplxCd(String houscplxCd) {
        this.houscplxCd = houscplxCd;
    }

    public String getDongNo() {
        return dongNo;
    }

    public void setDongNo(String dongNo) {
        this.dongNo = dongNo;
    }

    public String getHoseNo() {
        return hoseNo;
    }

    public void setHoseNo(String hoseNo) {
        this.hoseNo = hoseNo;
    }

    public String getWpadId() { return wpadId; }

    public void setWpadId(String wpadId) { this.wpadId = wpadId; }

    public String getCrerId() {
        return crerId;
    }

    public void setCrerId(String crerId) {
        this.crerId = crerId;
    }

    public String getDeviceTpCd() { return deviceTpCd; }

    public void setDeviceTpCd(String deviceTpCd) { this.deviceTpCd = deviceTpCd; }

    public String getDeviceTpCdNm() { return deviceTpCdNm; }

    public void setDeviceTpCdNm(String deviceTpCdNm) { this.deviceTpCdNm = deviceTpCdNm; }

    public String getDeviceNm() { return deviceNm; }

    public void setDeviceNm(String deviceNm) { this.deviceNm = deviceNm; }

    public String getDeviceId() { return deviceId; }

    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }

    public String getScreenYn() { return screenYn; }

    public void setScreenYn(String screenYn) { this.screenYn = screenYn; }

    public String getLocaNm() { return locaNm; }

    public void setLocaNm(String locaNm) { this.locaNm = locaNm; }

    public String getMnfcoNm() { return mnfcoNm; }

    public void setMnfcoNm(String mnfcoNm) { this.mnfcoNm = mnfcoNm; }

    public String getSerlNo() { return serlNo; }

    public void setSerlNo(String serlNo) { this.serlNo = serlNo; }

    public String getMdlNm() { return mdlNm; }

    public void setMdlNm(String mdlNm) { this.mdlNm = mdlNm; }

    public String getLightsYn() {
        return lightsYn;
    }

    public void setLightsYn(String lightsYn) {
        this.lightsYn = lightsYn;
    }

    public int getLights() {
        return lights;
    }

    public void setLights(int lights) {
        this.lights = lights;
    }

    public int getLightsY() {
        return lightsY;
    }

    public void setLightsY(int lightsY) {
        this.lightsY = lightsY;
    }

    public int getLightsN() {
        return lightsN;
    }

    public void setLightsN(int lightsN) {
        this.lightsN = lightsN;
    }

    public String getGaslockYn() {
        return gaslockYn;
    }

    public void setGaslockYn(String gaslockYn) {
        this.gaslockYn = gaslockYn;
    }

    public int getGaslock() {
        return gaslock;
    }

    public void setGaslock(int gaslock) {
        this.gaslock = gaslock;
    }

    public int getGaslockY() {
        return gaslockY;
    }

    public void setGaslockY(int gaslockY) {
        this.gaslockY = gaslockY;
    }

    public int getGaslockN() {
        return gaslockN;
    }

    public void setGaslockN(int gaslockN) {
        this.gaslockN = gaslockN;
    }

    public String getAirconYn() {
        return airconYn;
    }

    public void setAirconYn(String airconYn) {
        this.airconYn = airconYn;
    }

    public int getAircon() {
        return aircon;
    }

    public void setAircon(int aircon) {
        this.aircon = aircon;
    }

    public int getAirconY() {
        return airconY;
    }

    public void setAirconY(int airconY) {
        this.airconY = airconY;
    }

    public int getAirconN() {
        return airconN;
    }

    public void setAirconN(int airconN) {
        this.airconN = airconN;
    }

    public String getHeatingYn() {
        return heatingYn;
    }

    public void setHeatingYn(String heatingYn) {
        this.heatingYn = heatingYn;
    }

    public int getHeating() {
        return heating;
    }

    public void setHeating(int heating) {
        this.heating = heating;
    }

    public int getHeatingY() {
        return heatingY;
    }

    public void setHeatingY(int heatingY) {
        this.heatingY = heatingY;
    }

    public int getHeatingN() {
        return heatingN;
    }

    public void setHeatingN(int heatingN) {
        this.heatingN = heatingN;
    }

    public String getVentilatorYn() {
        return ventilatorYn;
    }

    public void setVentilatorYn(String ventilatorYn) {
        this.ventilatorYn = ventilatorYn;
    }

    public int getVentilator() {
        return ventilator;
    }

    public void setVentilator(int ventilator) {
        this.ventilator = ventilator;
    }

    public int getVentilatorY() {
        return ventilatorY;
    }

    public void setVentilatorY(int ventilatorY) {
        this.ventilatorY = ventilatorY;
    }

    public int getVentilatorN() {
        return ventilatorN;
    }

    public void setVentilatorN(int ventilatorN) {
        this.ventilatorN = ventilatorN;
    }

    public String getSmartConsentYn() {
        return smartConsentYn;
    }

    public void setSmartConsentYn(String smartConsentYn) {
        this.smartConsentYn = smartConsentYn;
    }

    public int getSmartConsent() { return smartConsent; }

    public void setSmartConsent(int smartConsent) {
        this.smartConsent = smartConsent;
    }

    public int getSmartConsentY() { return smartConsentY; }

    public void setSmartConsentY(int smartConsentY) {
        this.smartConsentY = smartConsentY;
    }

    public int getSmartConsentN() { return smartConsentN; }

    public void setSmartConsentN(int smartConsentN) {
        this.smartConsentN = smartConsentN;
    }

    public String getCurtainYn() {
        return curtainYn;
    }

    public void setCurtainYn(String curtainYn) {
        this.curtainYn = curtainYn;
    }

    public int getCurtain() { return curtain; }

    public void setCurtain(int curtain) { this.curtain = curtain; }

    public int getCurtainY() { return curtainY; }

    public void setCurtainY(int curtainY) { this.curtainY = curtainY; }

    public int getCurtainN() { return curtainN; }

    public void setCurtainN(int curtainN) { this.curtainN = curtainN; }



}