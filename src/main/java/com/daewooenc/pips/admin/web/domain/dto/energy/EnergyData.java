package com.daewooenc.pips.admin.web.domain.dto.energy;

/**
 * @author : yckim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description <br/>
 * ---------------------------------------------------------- <br/>
 *    2019-08-28       :       yckim        :                  <br/>
 *
 * </pre>
 * @since : 2019-08-28
 **/
public class EnergyData {
    private String houscplxCd;
    private String hsholdId;
    private String yr;
    private String mm;
    private String ymd;
    private String enrgUseYmd;
    private String crerId;
    private String crDt;
    private String enrgDimQty;
    private String gasUseQty;
    private String elctUseQty;
    private String wtrsplUseQty;
    private String hotwtrUseQty;
    private String heatUseQty;

    public EnergyData() {
    }

    public EnergyData(String houscplxCd, String hsholdId, String enrgDimQty, String yr, String mm, String ymd, String enrgUseYmd, String gasUseQty, String elctUseQty, String wtrsplUseQty, String hotwtrUseQty, String heatUseQty, String crerId, String crDt) {
        this.houscplxCd = houscplxCd;
        this.hsholdId = hsholdId;
        this.enrgDimQty = enrgDimQty;
        this.yr = yr;
        this.mm = mm;
        this.ymd = ymd;
        this.enrgUseYmd = enrgUseYmd;
        this.gasUseQty = gasUseQty;
        this.elctUseQty = elctUseQty;
        this.wtrsplUseQty = wtrsplUseQty;
        this.hotwtrUseQty = hotwtrUseQty;
        this.heatUseQty = heatUseQty;
        this.crerId = crerId;
        this.crDt = crDt;
    }

    public String getHouscplxCd() {
        return houscplxCd;
    }

    public void setHouscplxCd(String houscplxCd) {
        this.houscplxCd = houscplxCd;
    }

    public String getHsholdId() {
        return hsholdId;
    }

    public void setHsholdId(String hsholdId) {
        this.hsholdId = hsholdId;
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

    public String getYmd() {
        return ymd;
    }

    public void setYmd(String ymd) {
        this.ymd = ymd;
    }

    public String getGasUseQty() {
        return gasUseQty;
    }

    public void setGasUseQty(String gasUseQty) {
        this.gasUseQty = gasUseQty;
    }

    public String getCrerId() {
        return crerId;
    }

    public void setCrerId(String crerId) {
        this.crerId = crerId;
    }

    public String getCrDt() {
        return crDt;
    }

    public void setCrDt(String crDt) {
        this.crDt = crDt;
    }

    public String getElctUseQty() {
        return elctUseQty;
    }

    public void setElctUseQty(String elctUseQty) {
        this.elctUseQty = elctUseQty;
    }

    public String getWtrsplUseQty() {
        return wtrsplUseQty;
    }

    public void setWtrsplUseQty(String wtrsplUseQty) {
        this.wtrsplUseQty = wtrsplUseQty;
    }

    public String getHotwtrUseQty() {
        return hotwtrUseQty;
    }

    public void setHotwtrUseQty(String hotwtrUseQty) {
        this.hotwtrUseQty = hotwtrUseQty;
    }

    public String getHeatUseQty() {
        return heatUseQty;
    }

    public void setHeatUseQty(String heatUseQty) {
        this.heatUseQty = heatUseQty;
    }

    public String getEnrgUseYmd() {
        return enrgUseYmd;
    }

    public void setEnrgUseYmd(String enrgUseYmd) {
        this.enrgUseYmd = enrgUseYmd;
    }

    public String getEnrgDimQty() {
        return enrgDimQty;
    }

    public void setEnrgDimQty(String enrgDimQty) {
        this.enrgDimQty = enrgDimQty;
    }
}