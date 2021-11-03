package com.daewooenc.pips.admin.web.domain.dto.device;

/**
 * 시스템 및 단지관리자의 세대장치 관리 목록을 위한 맵핑 DAO
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-08-19      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-08-19
 **/
public class HouseholdDevice {
    private String hmnetId;
    private String bizcoCd;
    private String houscplxCd;
    private String houscplxNm;
    private String dongNo;
    private String hoseNo;
    private String wpadId;
    private String delYn;
    private String userId;
    private int lights;
    private int gaslock;
    private int aircon;
    private int heating;
    private int ventilator;
    private int smartConsent;
    private int curtain;
    private int lightSwitch;

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

    public String getHouscplxCd() {
        return houscplxCd;
    }

    public void setHouscplxCd(String houscplxCd) {
        this.houscplxCd = houscplxCd;
    }

    public String getHouscplxNm() {
        return houscplxNm;
    }

    public void setHouscplxNm(String houscplxNm) {
        this.houscplxNm = houscplxNm;
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

    public String getWpadId() {
        return wpadId;
    }

    public void setWpadId(String wpadId) {
        this.wpadId = wpadId;
    }

    public String getDelYn() {
        return delYn;
    }

    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }

    public String getUserid() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public int getLights() {
        return lights;
    }

    public void setLights(int lights) {
        this.lights = lights;
    }

    public int getGaslock() {
        return gaslock;
    }

    public void setGaslock(int gaslock) {
        this.gaslock = gaslock;
    }

    public int getAircon() {
        return aircon;
    }

    public void setAircon(int aircon) {
        this.aircon = aircon;
    }

    public int getHeating() {
        return heating;
    }

    public void setHeating(int heating) {
        this.heating = heating;
    }

    public int getVentilator() {
        return ventilator;
    }

    public void setVentilator(int ventilator) {
        this.ventilator = ventilator;
    }

    public int getSmartConsent() {
        return smartConsent;
    }

    public void setSmartConsent(int smartConsent) {
        this.smartConsent = smartConsent;
    }

    public int getCurtain() {
        return curtain;
    }

    public void setCurtain(int curtain) {
        this.curtain = curtain;
    }

    public int getLightSwitch() { return lightSwitch; }

    public void setLightSwitch(int lightSwitch) { this.lightSwitch = lightSwitch; }

    @Override
    public String toString() {
        return "HouseholdDevice{" +
                "hmnetId='" + hmnetId + '\'' +
                ", bizcoCd='" + bizcoCd + '\'' +
                ", houscplxCd='" + houscplxCd + '\'' +
                ", houscplxNm='" + houscplxNm + '\'' +
                ", dongNo='" + dongNo + '\'' +
                ", hoseNo='" + hoseNo + '\'' +
                ", wpadId='" + wpadId + '\'' +
                ", delYn='" + delYn + '\'' +
                ", lights=" + lights +
                ", gaslock=" + gaslock +
                ", aircon=" + aircon +
                ", heating=" + heating +
                ", ventilator=" + ventilator +
                ", smartConsent=" + smartConsent +
                ", curtain=" + curtain +
                ", lightSwitch=" + lightSwitch +
                '}';
    }
}