package com.daewooenc.pips.admin.web.domain.vo.weather;

public class WeatherItemVo {
    String baseDate;
    String baseTime;
    String fcstDate;
    String fcstTime;
    String fcstValue;
    String obsrValue;
    String category;
    int nx;
    int ny;

    //기상 특보
    String tmFc;
    String areaCode;
    String areaName;
    String warnVar;
    String warnStress;
    String command;
    String startTime;
    String stnId;
    String endTime;
    String allEndTime;
    String cancel;
    String tmSeq;

    public String getBaseDate() {
        return baseDate;
    }

    public void setBaseDate(String baseDate) {
        this.baseDate = baseDate;
    }

    public String getBaseTime() {
        return baseTime;
    }

    public void setBaseTime(String baseTime) {
        this.baseTime = baseTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getNx() {
        return nx;
    }

    public void setNx(int nx) {
        this.nx = nx;
    }

    public int getNy() {
        return ny;
    }

    public void setNy(int ny) {
        this.ny = ny;
    }

    public String getObsrValue() {
        return obsrValue;
    }

    public void setObsrValue(String obsrValue) {
        this.obsrValue = obsrValue;
    }

    public String getFcstDate() {
        return fcstDate;
    }

    public void setFcstDate(String fcstDate) {
        this.fcstDate = fcstDate;
    }

    public String getFcstTime() {
        return fcstTime;
    }

    public void setFcstTime(String fcstTime) {
        this.fcstTime = fcstTime;
    }

    public String getFcstValue() {
        return fcstValue;
    }

    public void setFcstValue(String fcstValue) {
        this.fcstValue = fcstValue;
    }

    public String getTmFc() { return tmFc; }

    public void setTmFc(String tmFc) { this.tmFc = tmFc; }

    public String getAreaCode() { return areaCode; }

    public void setAreaCode(String areaCode) { this.areaCode = areaCode; }

    public String getAreaName() { return areaName; }

    public void setAreaName(String areaName) { this.areaName = areaName; }

    public String getWarnVar() { return warnVar; }

    public void setWarnVar(String warnVar) { this.warnVar = warnVar; }

    public String getWarnStress() { return warnStress; }

    public void setWarnStress(String warnStress) { this.warnStress = warnStress; }

    public String getCommand() { return command; }

    public void setCommand(String command) { this.command = command; }

    public String getStartTime() { return startTime; }

    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getStnId() { return stnId; }

    public void setStnId(String stnId) { this.stnId = stnId; }

    public String getEndTime() { return endTime; }

    public void setEndTime(String endTime) { this.endTime = endTime; }

    public String getAllEndTime() { return allEndTime; }

    public void setAllEndTime(String allEndTime) { this.allEndTime = allEndTime; }

    public String getCancel() { return cancel; }

    public void setCancel(String cancel) { this.cancel = cancel; }

    public String getTmSeq() { return tmSeq; }

    public void setTmSeq(String tmSeq) { this.tmSeq = tmSeq; }
}
