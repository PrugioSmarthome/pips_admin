package com.daewooenc.pips.admin.web.domain.dto.energy;

/**
 * @author : yckim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description <br/>
 * ---------------------------------------------------------- <br/>
 *    2019-09-02       :       yckim        :                  <br/>
 *
 * </pre>
 * @since : 2019-09-02
 **/
public class EnergyAlarmTarget {
    private String hsholdId;
    private String houscplxCd;
    private String yr;
    private String mm;
    private int gasRate;
    private int elctRate;
    private int wtrsplRate;
    private int hotwtrRate;
    private int heatRate;
    private String gasSend1;
    private String gasRend2;
    private String elctSend1;
    private String elctSend2;
    private String wtrsplSend1;
    private String wtrsplSend2;
    private String hotwtrSend1;
    private String hotwtrSend2;
    private String heatSend1;
    private String heatSend2;

    public String getHsholdId() {
        return hsholdId;
    }

    public void setHsholdId(String hsholdId) {
        this.hsholdId = hsholdId;
    }

    public String getHouscplxCd() {
        return houscplxCd;
    }

    public void setHouscplxCd(String houscplxCd) {
        this.houscplxCd = houscplxCd;
    }

    public String getYr() {
        return yr;
    }

    public void setYr(String yr) {
        this.yr = yr;
    }

    public String getMm() {
        return mm;
    }

    public void setMm(String mm) {
        this.mm = mm;
    }

    public int getGasRate() {
        return gasRate;
    }

    public void setGasRate(int gasRate) {
        this.gasRate = gasRate;
    }

    public int getElctRate() {
        return elctRate;
    }

    public void setElctRate(int elctRate) {
        this.elctRate = elctRate;
    }

    public int getWtrsplRate() {
        return wtrsplRate;
    }

    public void setWtrsplRate(int wtrsplRate) {
        this.wtrsplRate = wtrsplRate;
    }

    public int getHotwtrRate() {
        return hotwtrRate;
    }

    public void setHotwtrRate(int hotwtrRate) {
        this.hotwtrRate = hotwtrRate;
    }

    public int getHeatRate() {
        return heatRate;
    }

    public void setHeatRate(int heatRate) {
        this.heatRate = heatRate;
    }

    public String getGasSend1() {
        return gasSend1;
    }

    public void setGasSend1(String gasSend1) {
        this.gasSend1 = gasSend1;
    }

    public String getGasRend2() {
        return gasRend2;
    }

    public void setGasRend2(String gasRend2) {
        this.gasRend2 = gasRend2;
    }

    public String getElctSend1() {
        return elctSend1;
    }

    public void setElctSend1(String elctSend1) {
        this.elctSend1 = elctSend1;
    }

    public String getElctSend2() {
        return elctSend2;
    }

    public void setElctSend2(String elctSend2) {
        this.elctSend2 = elctSend2;
    }

    public String getWtrsplSend1() {
        return wtrsplSend1;
    }

    public void setWtrsplSend1(String wtrsplSend1) {
        this.wtrsplSend1 = wtrsplSend1;
    }

    public String getWtrsplSend2() {
        return wtrsplSend2;
    }

    public void setWtrsplSend2(String wtrsplSend2) {
        this.wtrsplSend2 = wtrsplSend2;
    }

    public String getHotwtrSend1() {
        return hotwtrSend1;
    }

    public void setHotwtrSend1(String hotwtrSend1) {
        this.hotwtrSend1 = hotwtrSend1;
    }

    public String getHotwtrSend2() {
        return hotwtrSend2;
    }

    public void setHotwtrSend2(String hotwtrSend2) {
        this.hotwtrSend2 = hotwtrSend2;
    }

    public String getHeatSend1() {
        return heatSend1;
    }

    public void setHeatSend1(String heatSend1) {
        this.heatSend1 = heatSend1;
    }

    public String getHeatSend2() {
        return heatSend2;
    }

    public void setHeatSend2(String heatSend2) {
        this.heatSend2 = heatSend2;
    }
}