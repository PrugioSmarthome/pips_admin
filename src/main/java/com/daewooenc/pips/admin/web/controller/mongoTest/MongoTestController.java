package com.daewooenc.pips.admin.web.controller.mongoTest;

import com.daewooenc.pips.admin.core.domain.common.SessionUser;
import com.daewooenc.pips.admin.core.util.SessionUtil;
import com.daewooenc.pips.admin.web.domain.dto.maintenanceFee.MaintenanceFee;
import com.daewooenc.pips.admin.web.domain.vo.mongoTest.MongoTestVo;
import com.daewooenc.pips.admin.web.service.mongoTest.MongoTestService;
import com.daewooenc.pips.admin.web.util.JsonUtil;
import com.daewooenc.pips.admin.web.util.XSSUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cm/mongo/test")
public class MongoTestController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String thisUrl = "cm/mongo/test";

    @Autowired
    private XSSUtil xssUtil;

    @Autowired
    private MongoTestService mongoTestService;




    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(Model model, HttpServletRequest request) {

        //String houscplxCd = request.getParameter("houscplxCd");

        String houscplxCd = "000010";

        List<MongoTestVo> resultList = mongoTestService.getList(houscplxCd);

        List<Map<String, String>> list = null;

        Map<String, String> map = null;

        ArrayList arrayList = new ArrayList();

        for(int i=0; i<resultList.size(); i++){
            JSONObject jsonObject = new JSONObject(resultList.get(i).getMgmcstInfo());
            jsonObject.put("hshold_id", resultList.get(i).getHsholdId() );
            jsonObject.put("ymd", resultList.get(i).getYmd() );
            arrayList.add(jsonObject);
        }

        list = new ArrayList<Map<String, String>>();
        for(int i = 0; i < arrayList.size(); i++) {
            map = new HashMap<String, String>();
            JSONObject jsonObject = new JSONObject(arrayList.get(i).toString());

            String hshold_id = (String) jsonObject.get("hshold_id");
            String ymd = (String) jsonObject.get("ymd");
            String service_charge = (String) jsonObject.get("일반 관리비");
            String maintenance_cost = (String) jsonObject.get("청소비");


            map.put("hshold_id", hshold_id);
            map.put("ymd", ymd);
            map.put("service_charge", service_charge);
            map.put("maintenance_cost", maintenance_cost);
            list.add(map);
        }

        model.addAttribute("resultList", list);

        return thisUrl + "/list";
    }

    @RequestMapping(value = "add", method = {RequestMethod.GET, RequestMethod.POST})
    public String add(Model model, HttpServletRequest request) {

        return thisUrl + "/add";
    }

    @RequestMapping(value = "redirect", method = {RequestMethod.GET, RequestMethod.POST})
    public String redirect(RedirectAttributes redirectAttributes, Model model) {

        //redirectAttributes.addAttribute("key","!!!!!!redirect test value!!!!!!!");
        //redirectAttributes.addFlashAttribute("flashKey","!!!!!!flash value!!!!!!!");

        Map<String, String> map = new HashMap<String, String>();
        map.put("json1", "test1");
        map.put("json2", "test2");
        map.put("json3", "test3");
        map.put("json4", "test4");

        String json = JsonUtil.toJson(map);


        HttpPost httpPost = new HttpPost("http://localhost:8999/v1/redirect/test");
        httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
        httpPost.setEntity(new StringEntity(json, "UTF-8"));
        httpPost.addHeader("Connection", "close");

        HttpClient client = HttpClientBuilder.create().build();

        try {
            HttpResponse response = client.execute(httpPost);

        }catch (Exception e){

        }



        model.addAttribute("flashKey", httpPost);
        //model.addAttribute("flashKey", new StringEntity(json, "UTF-8"));
        //model.addAttribute("flashKey", json);

        //return "redirect:http://localhost:8999/v1/redirect/test";
        return thisUrl + "/redirect";
    }

    @RequestMapping(value = "add2", method = {RequestMethod.GET, RequestMethod.POST})
    public String add2(Model model, HttpServletRequest request) {

        return thisUrl + "/add2";
    }

    @RequestMapping(value = "iframe", method = {RequestMethod.GET, RequestMethod.POST})
    public String iframe(Model model, HttpServletRequest request) {

        return thisUrl + "/iframe";
    }

    @RequestMapping(value = "insertAction", method = {RequestMethod.GET, RequestMethod.POST})
    public String insertAction(Model model, HttpServletRequest request) {

        String hsholdId = request.getParameter("hsholdId");
        String ymd = request.getParameter("ymd");
        String serviceCharge = request.getParameter("serviceCharge");
        String maintenanceCost = request.getParameter("maintenanceCost");

        mongoTestService.insert(hsholdId, ymd, serviceCharge, maintenanceCost);

        return "redirect:/" + thisUrl + "/list";
    }


    /**
     * 단지 관리 > 단지 관리비 관리 > 단지 관리비 목록 > 단지 관리비 등록 > 등록하기
     * @param maintenanceFee
     * @param request
     * @return
     */
    @RequestMapping(value = "insertAction2", method = {RequestMethod.GET, RequestMethod.POST})
    public String insertAction2(MaintenanceFee maintenanceFee, Model model, HttpServletRequest request) {

        //String houscplxCd = request.getParameter("houscplxCd");

        String houscplxCd = "000010";

        Map<String, Object> mongoTestMap;

        String csv = request.getParameter("csvData");
        char c = (char) 26;
        String[] line = csv.split(String.valueOf(c));

        int lineLength = line.length;
        int lineCnt = (int)Math.ceil(lineLength*1.0 / 1000);

        for(int j = 0; j < lineCnt; j++) {

            List<Map<String, Object>> mongoTestList = new ArrayList<Map<String, Object>>();

            for (int i = j*1000; i < (j+1)*1000; i++) {
                
                if(i == lineLength){
                    break;
                }

                mongoTestMap = new HashMap<String, Object>();

                String[] data = line[i].split(",");
                String dong = String.format("%04d", Integer.parseInt(data[1]));
                String hose = String.format("%04d", Integer.parseInt(data[2]));

                mongoTestMap.put("hshold_id", houscplxCd + "." + dong + "." + hose);
                mongoTestMap.put("ymd", data[67]);
                mongoTestMap.put("yr", data[67].substring(0, 4));
                mongoTestMap.put("mm", data[67].substring(4));
                mongoTestMap.put("genMgmCstQty", data[4]);
                mongoTestMap.put("cleanCstQty", data[5]);
                mongoTestMap.put("dfCstQty", data[6]);
                mongoTestMap.put("elevCstQty", data[7]);
                mongoTestMap.put("repairMtCstQty", data[8]);
                mongoTestMap.put("longRepCstQty", data[9]);
                mongoTestMap.put("cemcCstQty", data[10]);
                mongoTestMap.put("susCstQty", data[11]);
                mongoTestMap.put("expCstQty", data[12]);
                mongoTestMap.put("repMtCstQty", data[13]);
                mongoTestMap.put("builPreCstQty", data[14]);
                mongoTestMap.put("conMgmCstQty", data[15]);
                mongoTestMap.put("jobSupCstQty", data[16]);
                mongoTestMap.put("hsholdElctCstQty", data[17]);
                mongoTestMap.put("commElctCstQty", data[18]);
                mongoTestMap.put("elevElctCstQty", data[19]);
                mongoTestMap.put("tvCstQty", data[20]);
                mongoTestMap.put("hsholdWtrsplCstQty", data[22]);
                mongoTestMap.put("commWtrsplCstQty", data[23]);
                mongoTestMap.put("hsholdHeatCstQty", data[25]);
                mongoTestMap.put("basicHeatCstQty", data[26]);
                mongoTestMap.put("commHeatCstQty", data[27]);
                mongoTestMap.put("hsholdHotwtrCstQty", data[28]);
                mongoTestMap.put("wastCommisionCstQty", data[31]);
                mongoTestMap.put("eleccarElctCstQty", data[33]);
                mongoTestMap.put("hsholdAccCardCstQty", data[34]);
                mongoTestMap.put("beforeMgmcstQty", data[39]);
                mongoTestMap.put("afterMgmcstQty", data[40]);
                mongoTestMap.put("elctUseQty", data[41]);
                mongoTestMap.put("hotwtrUseQty", data[42]);
                mongoTestMap.put("wtrsplUseRate", data[43]);
                mongoTestMap.put("heatUseQty", data[44]);
                mongoTestMap.put("gasUseRate", data[45]);
                mongoTestMap.put("currentMgmCstQty", data[46]);
                mongoTestMap.put("unpaidMgmCstQty", data[47]);
                mongoTestMap.put("unpaidArrMgmCstQty", data[48]);
                mongoTestMap.put("overdueMgmCstQty", data[49]);
                mongoTestMap.put("beforeElctQty", data[50]);
                mongoTestMap.put("currentElctQty", data[51]);
                mongoTestMap.put("beforeHotwtrQty", data[52]);
                mongoTestMap.put("currentHotwtrQty", data[53]);
                mongoTestMap.put("beforeWtrsplQty", data[54]);
                mongoTestMap.put("currentWtrsplQty", data[55]);
                mongoTestMap.put("beforeHeatQty", data[56]);
                mongoTestMap.put("currentHeatQty", data[57]);
                mongoTestMap.put("beforeGasQty", data[58]);
                mongoTestMap.put("currentGasQty", data[59]);
                mongoTestMap.put("sumMgmCstQty", data[65]);
                mongoTestMap.put("agencyMgmCstQty", data[66]);
                mongoTestMap.put("currentAfterUnpaidCstQty", data[68]);
                mongoTestMap.put("elctDiscountCstQty", data[71]);
                mongoTestMap.put("wtrsplDiscountCstQty", data[72]);

                mongoTestList.add(mongoTestMap);
            }
            mongoTestService.insert2(mongoTestList, houscplxCd);
        }


        return "redirect:/" + thisUrl + "/list";


    }





}