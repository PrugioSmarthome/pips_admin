package com.daewooenc.pips.admin.web.util.mongo;

import com.daewooenc.pips.admin.web.domain.vo.mongodb.FailureOccurrenceConditionVo;
import com.daewooenc.pips.admin.web.domain.vo.mongodb.VisitCarHistoryConditionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("FailureOccurrenceMongo")
public class FailureOccurrenceMongo {

    private String dbName = "";

    @Autowired
    private MongoTemplate mongodb;

    //리스트형태 데이터 전부 가져오기
    public long getList(String homenetId, String houscplxCd, String outcomDateStart, String outcomDateEnd) {

        dbName = homenetId + "." + houscplxCd + ".cmd";

        Query query = new Query();
        Criteria criteria = new Criteria();

        String[] ctl_sts_cd_list = {"FAIL","TIME_OUT","D_NACK"};
        Criteria[] criteria_arr = new Criteria[ctl_sts_cd_list.length];

        for(int i = 0;i < ctl_sts_cd_list.length;i++){
            String ctl_sts_cd = ctl_sts_cd_list[i];
            criteria_arr[i] = Criteria.where("ctl_sts_cd").regex(ctl_sts_cd);
        }
        query.addCriteria(criteria.orOperator(criteria_arr));

        if(!"".equals(outcomDateStart) && !"".equals(outcomDateEnd) ){
            query.addCriteria(Criteria.where("outcom_dt").gte(outcomDateStart+"000000").lte(outcomDateEnd+"235959"));
        }else if(!"".equals(outcomDateStart)){
            query.addCriteria(Criteria.where("outcom_dt").gte(outcomDateStart+"000000"));
        }else if(!"".equals(outcomDateEnd)){
            query.addCriteria(Criteria.where("outcom_dt").lte(outcomDateEnd+"235959"));
        }

        return mongodb.count(query, FailureOccurrenceConditionVo.class, dbName);
    }

    public List<FailureOccurrenceConditionVo> getView(String homenetId, String houscplxCd, String outcomDateStart, String outcomDateEnd, String dongNo, String hoseNo){

        dbName = homenetId + "." + houscplxCd + ".cmd";

        Query query = new Query();
        Criteria criteria = new Criteria();

        String[] ctl_sts_cd_list = {"FAIL","TIME_OUT","D_NACK"};
        Criteria[] criteria_arr = new Criteria[ctl_sts_cd_list.length];

        for(int i = 0;i < ctl_sts_cd_list.length;i++){
            String ctl_sts_cd = ctl_sts_cd_list[i];
            criteria_arr[i] = Criteria.where("ctl_sts_cd").regex(ctl_sts_cd);
        }
        query.addCriteria(criteria.orOperator(criteria_arr));

        if(!"".equals(dongNo) && !"".equals(hoseNo)){
            String dongHoseNo = houscplxCd + "." + dongNo + "." + hoseNo;
            query.addCriteria(Criteria.where("wpad_id").regex(dongHoseNo));
        }else if(!"".equals(dongNo)){
            String dong = houscplxCd + "." + dongNo;
            query.addCriteria(Criteria.where("wpad_id").regex(dong));
        }


        if(!"".equals(outcomDateStart) && !"".equals(outcomDateEnd) ){
            query.addCriteria(Criteria.where("outcom_dt").gte(outcomDateStart+"000000").lte(outcomDateEnd+"235959"));
        }else if(!"".equals(outcomDateStart)){
            query.addCriteria(Criteria.where("outcom_dt").gte(outcomDateStart+"000000"));
        }else if(!"".equals(outcomDateEnd)){
            query.addCriteria(Criteria.where("outcom_dt").lte(outcomDateEnd+"235959"));
        }

        query.fields().include("wpad_id");
        query.fields().include("ctl_sts_cd");
        query.fields().include("ctl_tp_cd");
        query.fields().include("dem_dt");
        query.fields().include("ctl_outcom_cont.addition_message");

        query.with(new Sort(Sort.Direction.DESC, "dem_dt"));

        return mongodb.find(query, FailureOccurrenceConditionVo.class, dbName);
    }

}