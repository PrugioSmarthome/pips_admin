package com.daewooenc.pips.admin.web.domain.vo.mongoTest;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;

@Document
public class MongoTestVo {

    private String hshold_id;
    private String ymd;
    //private String mgmcst_info;
    private HashMap<String, Object> mgmcst_info;

/*    public MongoTestVo(String hshold_id, String ymd, HashMap<String, Object> mgmcst_info){
        this.hshold_id = hshold_id;
        this.ymd = ymd;
        this.mgmcst_info = mgmcst_info;
    }*/

    public String getHsholdId() { return hshold_id; }

    public void setHsholdId(String hshold_id) { this.hshold_id = hshold_id; }

    public String getYmd() { return ymd; }

    public void setYmd(String ymd) { this.ymd = ymd; }

    public HashMap<String, Object> getMgmcstInfo() { return mgmcst_info; }

    public void setMgmcstInfo(HashMap<String, Object> mgmcst_info) { this.mgmcst_info = mgmcst_info; }

}