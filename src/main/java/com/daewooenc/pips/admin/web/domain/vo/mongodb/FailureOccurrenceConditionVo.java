package com.daewooenc.pips.admin.web.domain.vo.mongodb;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class FailureOccurrenceConditionVo {

    private String ctl_outcom_cont;
    private String ctl_sts_cd;
    private String dem_dt;
    private String wpad_id;
    private String ctl_tp_cd;

    public String getCtlOutcomCont() { return ctl_outcom_cont; }

    public void setCtlOutcomCont(String ctl_outcom_cont) { this.ctl_outcom_cont = ctl_outcom_cont; }

    public String getCtlStsCd() { return ctl_sts_cd; }

    public void setCtlStsCd(String ctl_sts_cd) { this.ctl_sts_cd = ctl_sts_cd; }

    public String getDemDt() { return dem_dt; }

    public void setDemDt(String dem_dt) { this.dem_dt = dem_dt; }

    public String getWpadId() { return wpad_id; }

    public void setWpadId(String wpad_id) { this.wpad_id = wpad_id; }

    public String getCtlTpCd() { return ctl_tp_cd; }

    public void setCtlTpCd(String ctl_tp_cd) { this.ctl_tp_cd = ctl_tp_cd; }

}