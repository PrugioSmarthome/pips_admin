package com.daewooenc.pips.admin.web.domain.vo.mongoTest;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;

@Document
public class hshold_mgmcst {

    private String hshold_id;
    private String ymd;
    private String yr;
    private String mm;
    private HashMap<String, Object> mgmcst_info;

    public String getHsholdId() { return hshold_id; }

    public void setHsholdId(String hshold_id) { this.hshold_id = hshold_id; }

    public String getYmd() { return ymd; }

    public void setYmd(String ymd) { this.ymd = ymd; }

    public String getYr() { return yr; }

    public void setYr(String yr) { this.yr = yr; }

    public String getMm() { return mm; }

    public void setMm(String mm) { this.mm = mm; }

    public HashMap<String, Object> getMgmcstInfo() { return mgmcst_info; }

    public void setMgmcstInfo(HashMap<String, Object> mgmcst_info) { this.mgmcst_info = mgmcst_info; }

}