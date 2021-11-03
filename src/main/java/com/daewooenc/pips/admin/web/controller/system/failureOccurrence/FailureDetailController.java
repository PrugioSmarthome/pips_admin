package com.daewooenc.pips.admin.web.controller.system.failureOccurrence;

import com.daewooenc.pips.admin.web.domain.dto.housingcplx.HousingCplx;
import com.daewooenc.pips.admin.web.domain.vo.mongodb.FailureOccurrenceConditionVo;
import com.daewooenc.pips.admin.web.service.homenet.HomenetService;
import com.daewooenc.pips.admin.web.service.housingcplx.HousingCplxService;
import com.daewooenc.pips.admin.web.util.XSSUtil;
import com.daewooenc.pips.admin.web.util.mongo.FailureOccurrenceMongo;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 장애 현황 관련 Controller
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2021-02-08      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2021-02-08
 **/
@Controller
@RequestMapping("/cm/system/failureDetail")
public class FailureDetailController {
    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    private String thisUrl = "cm/system/failureDetail";

    @Autowired
    private XSSUtil xssUtil;

    @Autowired
    private FailureOccurrenceMongo failureOccurrenceMongo;

    @Autowired
    private HomenetService homenetService;

    @Autowired
    private HousingCplxService housingCplxService;



    @RequestMapping(value = "list", method = {RequestMethod.GET,RequestMethod.POST})
    public String list(Model model, HttpServletRequest request) throws Exception{

        return thisUrl + "/list";
    }



/**
     *
     * @param
     * @param model
     * @param request
     * @return
     */

    @RequestMapping(value = "search", method = {RequestMethod.GET,RequestMethod.POST})
    public String search(Model model, HttpServletRequest request) throws Exception{

        Map<String, Object> failureOccurrenceMap;
        List<Map<String, Object>> failureOccurrenceViewList = new ArrayList<Map<String, Object>>();

        String outcomDateStart = StringUtils.defaultIfEmpty(request.getParameter("outcomDateStart"), "");
        String outcomDateEnd = StringUtils.defaultIfEmpty(request.getParameter("outcomDateEnd"), "");

        String dongNo = StringUtils.defaultIfEmpty(request.getParameter("dongNo"), "");
        if("all".equals(dongNo)){
            dongNo = "";
        }
        String hoseNo = StringUtils.defaultIfEmpty(request.getParameter("hoseNo"), "");
        if("all".equals(hoseNo)){
            hoseNo = "";
        }

        String houscplxCd = StringUtils.defaultIfEmpty(request.getParameter("houscplxCd"), "");
        if("all".equals(houscplxCd)){
            houscplxCd = "";
        }

        if(!"".equals(houscplxCd)){

            String homenetId = homenetService.getHomenetId(houscplxCd);
            String homenetNm = homenetService.getHomenetNm(homenetId);
            String houscplxNm = request.getParameter("houscplxNm_");

            List<FailureOccurrenceConditionVo> resultList = failureOccurrenceMongo.getView(homenetId, houscplxCd, outcomDateStart, outcomDateEnd, dongNo, hoseNo);

            ArrayList arrayList = new ArrayList();

            for(int j=0; j<resultList.size(); j++){
                if (resultList.get(j).getCtlOutcomCont() == null || "".equals(resultList.get(j).getCtlOutcomCont()) || !resultList.get(j).getCtlOutcomCont().contains("addition_message")) {
                    resultList.get(j).setCtlOutcomCont("{\"addition_message\":\" \"}");
                }
                JSONObject jsonObject = new JSONObject(resultList.get(j).getCtlOutcomCont().replace("[", "").replace("]", ""));
                jsonObject.put("ctl_sts_cd", resultList.get(j).getCtlStsCd());
                jsonObject.put("wpad_id", resultList.get(j).getWpadId());
                jsonObject.put("dem_dt", resultList.get(j).getDemDt());
                jsonObject.put("ctl_tp_cd", resultList.get(j).getCtlTpCd());

                arrayList.add(jsonObject);
            }

            for(int k = 0; k < arrayList.size(); k++) {
                failureOccurrenceMap = new HashMap<String, Object>();
                JSONObject jsonObject = new JSONObject(arrayList.get(k).toString());

                String addition_message = "";
                if (jsonObject.has("addition_message")) {
                    addition_message = (String) jsonObject.get("addition_message");
                }
                String ctl_sts_cd = (String) jsonObject.get("ctl_sts_cd");
                String wpad_id = (String) jsonObject.get("wpad_id");
                String dong = wpad_id.substring(7, 11);
                String hose = wpad_id.substring(12);

                String dem_dt = (String) jsonObject.get("dem_dt");
                String yr = dem_dt.substring(0, 4);
                String mm = dem_dt.substring(4, 6);
                String dd = dem_dt.substring(6, 8);
                String hh = dem_dt.substring(8, 10);
                String mi = dem_dt.substring(10, 12);
                String ss = dem_dt.substring(12);
                dem_dt = yr + "-" + mm + "-" + dd + "T" + hh + ":" + mi + ":" + ss;

                String ctl_tp_cd = (String) jsonObject.get("ctl_tp_cd");

                failureOccurrenceMap.put("addition_message", addition_message);
                failureOccurrenceMap.put("ctl_sts_cd", ctl_sts_cd);
                failureOccurrenceMap.put("wpad_id", wpad_id);
                failureOccurrenceMap.put("dong", dong);
                failureOccurrenceMap.put("hose", hose);
                failureOccurrenceMap.put("dem_dt", dem_dt);
                failureOccurrenceMap.put("ctl_tp_cd", ctl_tp_cd);
                failureOccurrenceMap.put("homenetNm", homenetNm);
                failureOccurrenceMap.put("houscplxNm", houscplxNm);
                failureOccurrenceViewList.add(failureOccurrenceMap);
            }
        } else {
            List<HousingCplx> houscplxList = housingCplxService.selectFailureOccurrence();

            for (int i = 0; i < houscplxList.size(); i++) {

                String homenetNm = homenetService.getHomenetNm(houscplxList.get(i).getHmnetId());
                String houscplxNm = houscplxList.get(i).getHouscplxNm();

                List<FailureOccurrenceConditionVo> resultList = failureOccurrenceMongo.getView(houscplxList.get(i).getHmnetId(), houscplxList.get(i).getHouscplxCd(), outcomDateStart, outcomDateEnd, dongNo, hoseNo);

                ArrayList arrayList = new ArrayList();

                for (int j = 0; j < resultList.size(); j++) {
                    if (resultList.get(j).getCtlOutcomCont() == null || "".equals(resultList.get(j).getCtlOutcomCont())) {
                        resultList.get(j).setCtlOutcomCont("{\"addition_message\":\" \"}");
                    }
                    JSONObject jsonObject = new JSONObject(resultList.get(j).getCtlOutcomCont().replace("[", "").replace("]", ""));
                    jsonObject.put("ctl_sts_cd", resultList.get(j).getCtlStsCd());
                    jsonObject.put("wpad_id", resultList.get(j).getWpadId());
                    jsonObject.put("dem_dt", resultList.get(j).getDemDt());
                    jsonObject.put("ctl_tp_cd", resultList.get(j).getCtlTpCd());

                    arrayList.add(jsonObject);
                }

                for (int k = 0; k < arrayList.size(); k++) {
                    failureOccurrenceMap = new HashMap<String, Object>();
                    JSONObject jsonObject = new JSONObject(arrayList.get(k).toString());

                    String addition_message = (String) jsonObject.get("addition_message");
                    String ctl_sts_cd = (String) jsonObject.get("ctl_sts_cd");
                    String wpad_id = (String) jsonObject.get("wpad_id");
                    String dong = wpad_id.substring(7, 11);
                    String hose = wpad_id.substring(12);

                    String dem_dt = (String) jsonObject.get("dem_dt");
                    String yr = dem_dt.substring(0, 4);
                    String mm = dem_dt.substring(4, 6);
                    String dd = dem_dt.substring(6, 8);
                    String hh = dem_dt.substring(8, 10);
                    String mi = dem_dt.substring(10, 12);
                    String ss = dem_dt.substring(12);
                    dem_dt = yr + "-" + mm + "-" + dd + "T" + hh + ":" + mi + ":" + ss;

                    String ctl_tp_cd = (String) jsonObject.get("ctl_tp_cd");

                    failureOccurrenceMap.put("addition_message", addition_message);
                    failureOccurrenceMap.put("ctl_sts_cd", ctl_sts_cd);
                    failureOccurrenceMap.put("wpad_id", wpad_id);
                    failureOccurrenceMap.put("dong", dong);
                    failureOccurrenceMap.put("hose", hose);
                    failureOccurrenceMap.put("dem_dt", dem_dt);
                    failureOccurrenceMap.put("ctl_tp_cd", ctl_tp_cd);
                    failureOccurrenceMap.put("homenetNm", homenetNm);
                    failureOccurrenceMap.put("houscplxNm", houscplxNm);
                    failureOccurrenceViewList.add(failureOccurrenceMap);
                }
            }
        }

        model.addAttribute("failureOccurrenceViewList", failureOccurrenceViewList);
        model.addAttribute("outcomDateStart", outcomDateStart);
        model.addAttribute("outcomDateEnd", outcomDateEnd);
        model.addAttribute("dongNo", dongNo);
        model.addAttribute("hoseNo", hoseNo);
        model.addAttribute("houscplxCd", StringUtils.defaultIfEmpty(request.getParameter("houscplxCd"), ""));
        model.addAttribute("houscplxNm", StringUtils.defaultIfEmpty(request.getParameter("houscplxNm_"), ""));

        return thisUrl + "/list";
    }

}