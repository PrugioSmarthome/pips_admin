package com.daewooenc.pips.admin.web.service.mongoTest;

import com.daewooenc.pips.admin.web.domain.vo.mongoTest.MongoTestVo;
import com.daewooenc.pips.admin.web.domain.vo.mongoTest.hshold_mgmcst;
import com.mongodb.DBObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.*;

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
@Service("MongoTestService")
public class MongoTestService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String dbName = "";

    @Autowired
    private MongoTemplate mongodb;

    /**
     * 시스템관리자, 단지관리자: 단지 관리비 목록 조회
     * @return
     */
    //리스트형태 데이터 전부 가져오기
    public List<MongoTestVo> getList(String houscplxCd) {

        dbName = "hshold_mgmcst_" + houscplxCd;

        Query query = new Query();
/*        query.addCriteria(Criteria.where("ctl_tp_cd").is("VISIT_CAR"));
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
        query.with(new Sort(Sort.Direction.ASC, "wpad_id"));*/


        return mongodb.find(query, MongoTestVo.class, dbName);
    }

    public void insert(String hsholdId, String ymd, String serviceCharge, String maintenanceCost){

        dbName = "hshold_mgmcst";

        MongoTestVo vo = null;

        HashMap<String, Object> mgmcst_info = new HashMap<String, Object>();
        mgmcst_info.put("관리비", serviceCharge);
        mgmcst_info.put("유지보수비", maintenanceCost);

        //vo = new MongoTestVo(hsholdId, ymd, mgmcst_info);

        mongodb.insert(vo,dbName);
    }

    public void insert2(List<Map<String, Object>> mongoTestList, String houscplxCd){

        List<hshold_mgmcst> hshold_mgmcst = new ArrayList<hshold_mgmcst>();
        hshold_mgmcst vo = null;

        HashMap<String, Object> mgmcst_info = null;

        for (int i=0; i<mongoTestList.size(); i++){
            vo = new hshold_mgmcst();
            mgmcst_info = new HashMap<String, Object>();

            vo.setHsholdId((String) mongoTestList.get(i).get("hshold_id"));
            vo.setYmd((String) mongoTestList.get(i).get("ymd"));
            vo.setYr((String) mongoTestList.get(i).get("yr"));
            vo.setMm((String) mongoTestList.get(i).get("mm"));

            mgmcst_info.put("일반 관리비", (String) mongoTestList.get(i).get("genMgmCstQty"));
            mgmcst_info.put("청소비", (String) mongoTestList.get(i).get("cleanCstQty"));
            mgmcst_info.put("소독비", (String) mongoTestList.get(i).get("dfCstQty"));
            mgmcst_info.put("승강기유지비", (String) mongoTestList.get(i).get("elevCstQty"));
            mgmcst_info.put("수선 유지비", (String) mongoTestList.get(i).get("repairMtCstQty"));
            mgmcst_info.put("장기수선충당금", (String) mongoTestList.get(i).get("longRepCstQty"));
            mgmcst_info.put("선관위운영비", (String) mongoTestList.get(i).get("cemcCstQty"));
            mgmcst_info.put("가수금", (String) mongoTestList.get(i).get("susCstQty"));
            mgmcst_info.put("경비비", (String) mongoTestList.get(i).get("expCstQty"));
            mgmcst_info.put("대표회의 운영비", (String) mongoTestList.get(i).get("repMtCstQty"));
            mgmcst_info.put("건물보험료", (String) mongoTestList.get(i).get("builPreCstQty"));
            mgmcst_info.put("위탁관리수수료", (String) mongoTestList.get(i).get("conMgmCstQty"));
            mgmcst_info.put("일자리지원차감", (String) mongoTestList.get(i).get("jobSupCstQty"));
            mgmcst_info.put("세대 전기료", (String) mongoTestList.get(i).get("hsholdElctCstQty"));
            mgmcst_info.put("공동전기료", (String) mongoTestList.get(i).get("commElctCstQty"));
            mgmcst_info.put("승강기 전기료", (String) mongoTestList.get(i).get("elevElctCstQty"));
            mgmcst_info.put("TV 수신료", (String) mongoTestList.get(i).get("tvCstQty"));
            mgmcst_info.put("세대수도료", (String) mongoTestList.get(i).get("hsholdWtrsplCstQty"));
            mgmcst_info.put("공동수도료", (String) mongoTestList.get(i).get("commWtrsplCstQty"));
            mgmcst_info.put("세대 난방비", (String) mongoTestList.get(i).get("hsholdHeatCstQty"));
            mgmcst_info.put("기본 열요금", (String) mongoTestList.get(i).get("basicHeatCstQty"));
            mgmcst_info.put("공동 열요금", (String) mongoTestList.get(i).get("commHeatCstQty"));
            mgmcst_info.put("세대급탕비", (String) mongoTestList.get(i).get("hsholdHotwtrCstQty"));
            mgmcst_info.put("생활폐기물수수료", (String) mongoTestList.get(i).get("wastCommisionCstQty"));
            mgmcst_info.put("전기차전기료", (String) mongoTestList.get(i).get("eleccarElctCstQty"));
            mgmcst_info.put("세대출입카드", (String) mongoTestList.get(i).get("hsholdAccCardCstQty"));
            mgmcst_info.put("납기전 관리비", (String) mongoTestList.get(i).get("beforeMgmcstQty"));
            mgmcst_info.put("납기후 관리비", (String) mongoTestList.get(i).get("afterMgmcstQty"));
            mgmcst_info.put("전기 사용 량", (String) mongoTestList.get(i).get("elctUseQty"));
            mgmcst_info.put("온수 사용 량", (String) mongoTestList.get(i).get("hotwtrUseQty"));
            mgmcst_info.put("수도 사용 율", (String) mongoTestList.get(i).get("wtrsplUseRate"));
            mgmcst_info.put("난방 사용 량", (String) mongoTestList.get(i).get("heatUseQty"));
            mgmcst_info.put("가스 사용 율", (String) mongoTestList.get(i).get("gasUseRate"));
            mgmcst_info.put("당월부과금액", (String) mongoTestList.get(i).get("currentMgmCstQty"));
            mgmcst_info.put("미납금액", (String) mongoTestList.get(i).get("unpaidMgmCstQty"));
            mgmcst_info.put("미납연체", (String) mongoTestList.get(i).get("unpaidArrMgmCstQty"));
            mgmcst_info.put("납기후 연체료", (String) mongoTestList.get(i).get("overdueMgmCstQty"));
            mgmcst_info.put("전기전월지침", (String) mongoTestList.get(i).get("beforeElctQty"));
            mgmcst_info.put("전기당월지침", (String) mongoTestList.get(i).get("currentElctQty"));
            mgmcst_info.put("온수전월지침", (String) mongoTestList.get(i).get("beforeHotwtrQty"));
            mgmcst_info.put("온수당월지침", (String) mongoTestList.get(i).get("currentHotwtrQty"));
            mgmcst_info.put("수도전월지침", (String) mongoTestList.get(i).get("beforeWtrsplQty"));
            mgmcst_info.put("수도당월지침", (String) mongoTestList.get(i).get("currentWtrsplQty"));
            mgmcst_info.put("난방전월지침", (String) mongoTestList.get(i).get("beforeHeatQty"));
            mgmcst_info.put("난방당월지침", (String) mongoTestList.get(i).get("currentHeatQty"));
            mgmcst_info.put("가스전월지침", (String) mongoTestList.get(i).get("beforeGasQty"));
            mgmcst_info.put("가스당월지침", (String) mongoTestList.get(i).get("currentGasQty"));
            mgmcst_info.put("관리비소계", (String) mongoTestList.get(i).get("sumMgmCstQty"));
            mgmcst_info.put("징수대행", (String) mongoTestList.get(i).get("agencyMgmCstQty"));
            mgmcst_info.put("당월후연체료", (String) mongoTestList.get(i).get("currentAfterUnpaidCstQty"));
            mgmcst_info.put("전기할인요금", (String) mongoTestList.get(i).get("elctDiscountCstQty"));
            mgmcst_info.put("수도할인요금", (String) mongoTestList.get(i).get("wtrsplDiscountCstQty"));
            vo.setMgmcstInfo(mgmcst_info);

            hshold_mgmcst.add(vo);
        }
        mongodb.insert(hshold_mgmcst, "hshold_mgmcst_" + houscplxCd);

        Query query = new Query();
        Update update = new Update();
        update.unset("_class");
        mongodb.updateMulti(query,update, "hshold_mgmcst_" + houscplxCd);



    }


}