package com.daewooenc.pips.admin.web.domain.dto.maintenanceFee;

/**
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2020-12-10      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2020-12-10
 **/
public class MaintenanceFee {
    private String hsholdId;
    private String yr;
    private String mm;
    private String houscplxCd;
    private String houscplxNm;
    private String dongNo;
    private String hoseNo;
    private String userId;
    private String crerId;

    private String genMgmCstQty;
    private String cleanCstQty;
    private String dfCstQty;
    private String elevCstQty;
    private String repairMtCstQty;
    private String longRepCstQty;
    private String cemcCstQty;
    private String susCstQty;
    private String expCstQty;
    private String repMtCstQty;
    private String builPreCstQty;
    private String conMgmCstQty;
    private String jobSupCstQty;
    private String hsholdElctCstQty;
    private String commElctCstQty;
    private String elevElctCstQty;
    private String tvCstQty;
    private String hsholdWtrsplCstQty;
    private String commWtrsplCstQty;
    private String hsholdHeatCstQty;
    private String basicHeatCstQty;
    private String commHeatCstQty;
    private String hsholdHotwtrCstQty;
    private String wastCommisionCstQty;
    private String eleccarElctCstQty;
    private String hsholdAccCardCstQty;
    private String elctUseQty;
    private String hotwtrUseQty;
    private String wtrsplUseRate;
    private String heatUseQty;
    private String gasUseRate;
    private String currentMgmCstQty;
    private String unpaidMgmCstQty;
    private String unpaidArrMgmCstQty;
    private String overdueMgmCstQty;
    private String beforeElctQty;
    private String currentElctQty;
    private String beforeHotwtrQty;
    private String currentHotwtrQty;
    private String beforeWtrsplQty;
    private String currentWtrsplQty;
    private String beforeHeatQty;
    private String currentHeatQty;
    private String beforeGasQty;
    private String currentGasQty;
    private String sumMgmCstQty;
    private String agencyMgmCstQty;
    private String currentAfterUnpaidCstQty;
    private String elctDiscountCstQty;
    private String wtrsplDiscountCstQty;


    private String beforeMgmcstQty;
    private String afterMgmcstQty;


    public String getCrerId() {
        return crerId;
    }

    public void setCrerId(String crerId) {
        this.crerId = crerId;
    }

    public String getHsholdId() {
        return hsholdId;
    }

    public String getYr() {
        return yr;
    }

    public void setGenMgmCstQty(String genMgmCstQty) {
        this.genMgmCstQty = genMgmCstQty;
    }

    public void setCleanCstQty(String cleanCstQty) {
        this.cleanCstQty = cleanCstQty;
    }

    public void setDfCstQty(String dfCstQty) {
        this.dfCstQty = dfCstQty;
    }

    public void setElevCstQty(String elevCstQty) {
        this.elevCstQty = elevCstQty;
    }

    public void setRepairMtCstQty(String repairMtCstQty) {
        this.repairMtCstQty = repairMtCstQty;
    }

    public void setLongRepCstQty(String longRepCstQty) {
        this.longRepCstQty = longRepCstQty;
    }

    public void setCemcCstQty(String cemcCstQty) {
        this.cemcCstQty = cemcCstQty;
    }

    public void setSusCstQty(String susCstQty) {
        this.susCstQty = susCstQty;
    }

    public void setExpCstQty(String expCstQty) {
        this.expCstQty = expCstQty;
    }

    public void setRepMtCstQty(String repMtCstQty) {
        this.repMtCstQty = repMtCstQty;
    }

    public void setBuilPreCstQty(String builPreCstQty) {
        this.builPreCstQty = builPreCstQty;
    }

    public void setConMgmCstQty(String conMgmCstQty) {
        this.conMgmCstQty = conMgmCstQty;
    }

    public void setJobSupCstQty(String jobSupCstQty) {
        this.jobSupCstQty = jobSupCstQty;
    }

    public void setHsholdElctCstQty(String hsholdElctCstQty) {
        this.hsholdElctCstQty = hsholdElctCstQty;
    }

    public void setCommElctCstQty(String commElctCstQty) {
        this.commElctCstQty = commElctCstQty;
    }

    public void setElevElctCstQty(String elevElctCstQty) {
        this.elevElctCstQty = elevElctCstQty;
    }

    public void setTvCstQty(String tvCstQty) {
        this.tvCstQty = tvCstQty;
    }

    public void setHsholdWtrsplCstQty(String hsholdWtrsplCstQty) {
        this.hsholdWtrsplCstQty = hsholdWtrsplCstQty;
    }

    public void setCommWtrsplCstQty(String commWtrsplCstQty) {
        this.commWtrsplCstQty = commWtrsplCstQty;
    }

    public void setHsholdHeatCstQty(String hsholdHeatCstQty) {
        this.hsholdHeatCstQty = hsholdHeatCstQty;
    }

    public void setBasicHeatCstQty(String basicHeatCstQty) {
        this.basicHeatCstQty = basicHeatCstQty;
    }

    public void setCommHeatCstQty(String commHeatCstQty) {
        this.commHeatCstQty = commHeatCstQty;
    }

    public void setHsholdHotwtrCstQty(String hsholdHotwtrCstQty) {
        this.hsholdHotwtrCstQty = hsholdHotwtrCstQty;
    }

    public void setWastCommisionCstQty(String wastCommisionCstQty) {
        this.wastCommisionCstQty = wastCommisionCstQty;
    }

    public void setEleccarElctCstQty(String eleccarElctCstQty) {
        this.eleccarElctCstQty = eleccarElctCstQty;
    }

    public void setHsholdAccCardCstQty(String hsholdAccCardCstQty) {
        this.hsholdAccCardCstQty = hsholdAccCardCstQty;
    }

    public void setElctUseQty(String elctUseQty) {
        this.elctUseQty = elctUseQty;
    }

    public void setHotwtrUseQty(String hotwtrUseQty) {
        this.hotwtrUseQty = hotwtrUseQty;
    }

    public void setWtrsplUseRate(String wtrsplUseRate) {
        this.wtrsplUseRate = wtrsplUseRate;
    }

    public void setHeatUseQty(String heatUseQty) {
        this.heatUseQty = heatUseQty;
    }

    public void setGasUseRate(String gasUseRate) {
        this.gasUseRate = gasUseRate;
    }

    public void setCurrentMgmCstQty(String currentMgmCstQty) {
        this.currentMgmCstQty = currentMgmCstQty;
    }

    public void setUnpaidMgmCstQty(String unpaidMgmCstQty) {
        this.unpaidMgmCstQty = unpaidMgmCstQty;
    }

    public void setUnpaidArrMgmCstQty(String unpaidArrMgmCstQty) {
        this.unpaidArrMgmCstQty = unpaidArrMgmCstQty;
    }

    public void setOverdueMgmCstQty(String overdueMgmCstQty) {
        this.overdueMgmCstQty = overdueMgmCstQty;
    }

    public void setBeforeElctQty(String beforeElctQty) {
        this.beforeElctQty = beforeElctQty;
    }

    public void setCurrentElctQty(String currentElctQty) {
        this.currentElctQty = currentElctQty;
    }

    public void setBeforeHotwtrQty(String beforeHotwtrQty) {
        this.beforeHotwtrQty = beforeHotwtrQty;
    }

    public void setCurrentHotwtrQty(String currentHotwtrQty) {
        this.currentHotwtrQty = currentHotwtrQty;
    }

    public void setBeforeWtrsplQty(String beforeWtrsplQty) {
        this.beforeWtrsplQty = beforeWtrsplQty;
    }

    public void setCurrentWtrsplQty(String currentWtrsplQty) {
        this.currentWtrsplQty = currentWtrsplQty;
    }

    public void setBeforeHeatQty(String beforeHeatQty) {
        this.beforeHeatQty = beforeHeatQty;
    }

    public void setCurrentHeatQty(String currentHeatQty) {
        this.currentHeatQty = currentHeatQty;
    }

    public void setBeforeGasQty(String beforeGasQty) {
        this.beforeGasQty = beforeGasQty;
    }

    public void setCurrentGasQty(String currentGasQty) {
        this.currentGasQty = currentGasQty;
    }

    public void setSumMgmCstQty(String sumMgmCstQty) {
        this.sumMgmCstQty = sumMgmCstQty;
    }

    public void setAgencyMgmCstQty(String agencyMgmCstQty) {
        this.agencyMgmCstQty = agencyMgmCstQty;
    }

    public void setCurrentAfterUnpaidCstQty(String currentAfterUnpaidCstQty) {
        this.currentAfterUnpaidCstQty = currentAfterUnpaidCstQty;
    }

    public void setElctDiscountCstQty(String elctDiscountCstQty) {
        this.elctDiscountCstQty = elctDiscountCstQty;
    }

    public void setWtrsplDiscountCstQty(String wtrsplDiscountCstQty) {
        this.wtrsplDiscountCstQty = wtrsplDiscountCstQty;
    }

    public String getGenMgmCstQty() {
        return genMgmCstQty;
    }

    public String getCleanCstQty() {
        return cleanCstQty;
    }

    public String getDfCstQty() {
        return dfCstQty;
    }

    public String getElevCstQty() {
        return elevCstQty;
    }

    public String getRepairMtCstQty() {
        return repairMtCstQty;
    }

    public String getLongRepCstQty() {
        return longRepCstQty;
    }

    public String getCemcCstQty() {
        return cemcCstQty;
    }

    public String getSusCstQty() {
        return susCstQty;
    }

    public String getExpCstQty() {
        return expCstQty;
    }

    public String getRepMtCstQty() {
        return repMtCstQty;
    }

    public String getBuilPreCstQty() {
        return builPreCstQty;
    }

    public String getConMgmCstQty() {
        return conMgmCstQty;
    }

    public String getJobSupCstQty() {
        return jobSupCstQty;
    }

    public String getHsholdElctCstQty() {
        return hsholdElctCstQty;
    }

    public String getCommElctCstQty() {
        return commElctCstQty;
    }

    public String getElevElctCstQty() {
        return elevElctCstQty;
    }

    public String getTvCstQty() {
        return tvCstQty;
    }

    public String getHsholdWtrsplCstQty() {
        return hsholdWtrsplCstQty;
    }

    public String getCommWtrsplCstQty() {
        return commWtrsplCstQty;
    }

    public String getHsholdHeatCstQty() {
        return hsholdHeatCstQty;
    }

    public String getBasicHeatCstQty() {
        return basicHeatCstQty;
    }

    public String getCommHeatCstQty() {
        return commHeatCstQty;
    }

    public String getHsholdHotwtrCstQty() {
        return hsholdHotwtrCstQty;
    }

    public String getWastCommisionCstQty() {
        return wastCommisionCstQty;
    }

    public String getEleccarElctCstQty() {
        return eleccarElctCstQty;
    }

    public String getHsholdAccCardCstQty() {
        return hsholdAccCardCstQty;
    }

    public String getElctUseQty() {
        return elctUseQty;
    }

    public String getHotwtrUseQty() {
        return hotwtrUseQty;
    }

    public String getWtrsplUseRate() {
        return wtrsplUseRate;
    }

    public String getHeatUseQty() {
        return heatUseQty;
    }

    public String getGasUseRate() {
        return gasUseRate;
    }

    public String getCurrentMgmCstQty() {
        return currentMgmCstQty;
    }

    public String getUnpaidMgmCstQty() {
        return unpaidMgmCstQty;
    }

    public String getUnpaidArrMgmCstQty() {
        return unpaidArrMgmCstQty;
    }

    public String getOverdueMgmCstQty() {
        return overdueMgmCstQty;
    }

    public String getBeforeElctQty() {
        return beforeElctQty;
    }

    public String getCurrentElctQty() {
        return currentElctQty;
    }

    public String getBeforeHotwtrQty() {
        return beforeHotwtrQty;
    }

    public String getCurrentHotwtrQty() {
        return currentHotwtrQty;
    }

    public String getBeforeWtrsplQty() {
        return beforeWtrsplQty;
    }

    public String getCurrentWtrsplQty() {
        return currentWtrsplQty;
    }

    public String getBeforeHeatQty() {
        return beforeHeatQty;
    }

    public String getCurrentHeatQty() {
        return currentHeatQty;
    }

    public String getBeforeGasQty() {
        return beforeGasQty;
    }

    public String getCurrentGasQty() {
        return currentGasQty;
    }

    public String getSumMgmCstQty() {
        return sumMgmCstQty;
    }

    public String getAgencyMgmCstQty() {
        return agencyMgmCstQty;
    }

    public String getCurrentAfterUnpaidCstQty() {
        return currentAfterUnpaidCstQty;
    }

    public String getElctDiscountCstQty() {
        return elctDiscountCstQty;
    }

    public String getWtrsplDiscountCstQty() {
        return wtrsplDiscountCstQty;
    }

    public String getMm() {
        return mm;
    }

    public String getHouscplxCd() {
        return houscplxCd;
    }

    public void setHsholdId(String hsholdId) {
        this.hsholdId = hsholdId;
    }

    public void setYr(String yr) {
        this.yr = yr;
    }

    public void setMm(String mm) {
        this.mm = mm;
    }

    public void setHouscplxCd(String houscplxCd) {
        this.houscplxCd = houscplxCd;
    }

    public String getHouscplxNm() {
        return houscplxNm;
    }

    public String getDongNo() {
        return dongNo;
    }

    public String getHoseNo() {
        return hoseNo;
    }

    public String getBeforeMgmcstQty() {
        return beforeMgmcstQty;
    }

    public String getAfterMgmcstQty() {
        return afterMgmcstQty;
    }

    public void setHouscplxNm(String houscplxNm) {
        this.houscplxNm = houscplxNm;
    }

    public void setDongNo(String dongNo) {
        this.dongNo = dongNo;
    }

    public void setHoseNo(String hoseNo) {
        this.hoseNo = hoseNo;
    }

    public void setBeforeMgmcstQty(String beforeMgmcstQty) {
        this.beforeMgmcstQty = beforeMgmcstQty;
    }

    public void setAfterMgmcstQty(String afterMgmcstQty) {
        this.afterMgmcstQty = afterMgmcstQty;
    }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }
}