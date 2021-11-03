package com.daewooenc.pips.admin.web.domain.vo.mongodb;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class VisitCarHistoryConditionVo {

    private String ctl_tp_cd;
    private String ctl_sts_cd;
    private String visit_date;
    private String wpad_id;
    private String ctl_dem_cont;
    private String car_num;
    private String visit_starttime;

    public String getCtlTpCd() { return ctl_tp_cd; }

    public void setCtlTpCd(String ctl_tp_cd) { this.ctl_tp_cd = ctl_tp_cd; }

    public String getCtlStsCd() { return ctl_sts_cd; }

    public void setCtlStsCd(String ctl_sts_cd) { this.ctl_sts_cd = ctl_sts_cd; }

    public String getVisitDate() { return visit_date; }

    public void setVisitDate(String visit_date) { this.visit_date = visit_date; }

    public String getWpadId() { return wpad_id; }

    public void setWpadId(String wpad_id) { this.wpad_id = wpad_id; }

    public String getCtlDemCont() { return ctl_dem_cont; }

    public void setCtlDemCont(String ctl_dem_cont) { this.ctl_dem_cont = ctl_dem_cont; }

    public String getCarNum() { return car_num; }

    public void setCarNum(String car_num) { this.car_num = car_num; }

    public String getVisitStarttime() { return visit_starttime; }

    public void setVisitStarttime(String visit_starttime) { this.visit_starttime = visit_starttime; }
}