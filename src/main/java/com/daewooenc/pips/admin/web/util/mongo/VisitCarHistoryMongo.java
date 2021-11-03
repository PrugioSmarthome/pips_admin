package com.daewooenc.pips.admin.web.util.mongo;

import com.daewooenc.pips.admin.web.domain.vo.mongodb.VisitCarHistoryConditionVo;
import com.mongodb.client.model.Sorts;
import com.mongodb.operation.OrderBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("VisitCarHistoryMongo")
public class VisitCarHistoryMongo {

    private String dbName = "";

    @Autowired
    private MongoTemplate mongodb;

    //리스트형태 데이터 전부 가져오기
    public List<VisitCarHistoryConditionVo> getList(VisitCarHistoryConditionVo vo, String homenetId, String houscplxCd, String visitDateStart, String visitDateEnd, String dongNo, String hoseNo) {

        dbName = homenetId + "." + houscplxCd + ".cmd";

        Query query = new Query();
        query.addCriteria(Criteria.where("ctl_tp_cd").is("VISIT_CAR"));
        query.addCriteria(Criteria.where("ctl_sts_cd").is("SUCCESS"));


        if(!"".equals(visitDateStart) && !"".equals(visitDateEnd) ){
            query.addCriteria(Criteria.where("ctl_dem_cont.visit_date").gte(visitDateStart).lte(visitDateEnd));
        }else if(!"".equals(visitDateStart)){
            query.addCriteria(Criteria.where("ctl_dem_cont.visit_date").gte(visitDateStart));
        }else if(!"".equals(visitDateEnd)){
            query.addCriteria(Criteria.where("ctl_dem_cont.visit_date").lte(visitDateEnd));
        }

        if(!"".equals(dongNo) && !"".equals(hoseNo)){
            String dongHoseNo = houscplxCd + "." + dongNo + "." + hoseNo;
            query.addCriteria(Criteria.where("wpad_id").regex(dongHoseNo));
        }else if(!"".equals(dongNo)){
            String dong = houscplxCd + "." + dongNo;
            query.addCriteria(Criteria.where("wpad_id").regex(dong));
        }

        query.with(new Sort(Sort.Direction.DESC, "ctl_dem_cont.visit_date"));
        query.with(new Sort(Sort.Direction.ASC, "wpad_id"));

        return mongodb.find(query, VisitCarHistoryConditionVo.class, dbName);
    }


}