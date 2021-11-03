package com.daewooenc.pips.admin.web.controller.system.visitCar;

import com.daewooenc.pips.admin.core.domain.common.SessionUser;
import com.daewooenc.pips.admin.core.util.SessionUtil;
import com.daewooenc.pips.admin.web.common.UserType;
import com.daewooenc.pips.admin.web.domain.vo.mongodb.VisitCarHistoryConditionVo;
import com.daewooenc.pips.admin.web.service.homenet.HomenetService;
import com.daewooenc.pips.admin.web.util.XSSUtil;
import com.daewooenc.pips.admin.web.util.mongo.VisitCarHistoryMongo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
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
 * 시스템 관리의 서비스 공지사항 관련 Controller
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-10-25      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-10-25
 **/
@Controller
@RequestMapping("/cm/visit/car")
public class VisitCarController {
    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    private String thisUrl = "cm/visit/car";

    @Autowired
    private HomenetService homenetService;

    @Autowired
    private XSSUtil xssUtil;

    @Autowired
    private VisitCarHistoryMongo visitCarHistoryMongo;

    /**
     *
     * @param
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "list", method = {RequestMethod.GET,RequestMethod.POST})
    public String list(Model model, HttpServletRequest request) throws Exception {

        SessionUser session = SessionUtil.getSessionUser(request);
        String paramGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
        String groupName = StringUtils.defaultIfEmpty(paramGroupName, "");

        if (UserType.COMPLEX.getGroupName().equals(groupName)) {
            model.addAttribute("COMPLEX_ADMIN_YN", "Y");
        }


        model.addAttribute("userId", StringUtils.defaultIfEmpty(session.getUserId(), ""));
        model.addAttribute("houscplxCd", StringUtils.defaultIfEmpty(session.getHouscplxCd(), ""));

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
    public String search(Model model, HttpServletRequest request) throws Exception {

        SessionUser session = SessionUtil.getSessionUser(request);

        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, "");
        String reqHouscplxCd = request.getParameter("houscplxCd");

        String paramHouscplxNm = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxNm()));
        String houscplxNm = StringUtils.defaultIfEmpty(paramHouscplxNm, "");
        String reqHouscplxNm = request.getParameter("houscplxNm_");

        String paramGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
        String groupName = StringUtils.defaultIfEmpty(paramGroupName, "");

        if (UserType.COMPLEX.getGroupName().equals(groupName)) {
            model.addAttribute("COMPLEX_ADMIN_YN", "N");
            reqHouscplxNm = houscplxNm;
        }

        if(!"".equals(reqHouscplxCd) || reqHouscplxCd != null){
            houscplxCd = reqHouscplxCd;
        }

        String homenetId = homenetService.getHomenetId(houscplxCd);

        VisitCarHistoryConditionVo vo = new VisitCarHistoryConditionVo();

        String dongNo = StringUtils.defaultIfEmpty(request.getParameter("dongNo"), "");
        if("all".equals(dongNo)){
            dongNo = "";
        }
        String hoseNo = StringUtils.defaultIfEmpty(request.getParameter("hoseNo"), "");
        if("all".equals(hoseNo)){
            hoseNo = "";
        }
        String visitDateStart = StringUtils.defaultIfEmpty(request.getParameter("visitDateStart"), "");

        String visitDateEnd = StringUtils.defaultIfEmpty(request.getParameter("visitDateEnd"), "");

        List<VisitCarHistoryConditionVo> resultList = visitCarHistoryMongo.getList(vo, homenetId, houscplxCd, visitDateStart, visitDateEnd, dongNo, hoseNo);

        List<Map<String, String>> list = null;

        Map<String, String> map = null;

//        JSONObject jsonObject = new JSONObject();

        ArrayList arrayList = new ArrayList();

        for(int i=0; i<resultList.size(); i++){
            JSONObject jsonObject = new JSONObject(resultList.get(i).getCtlDemCont());
            jsonObject.put("wpad_id", resultList.get(i).getWpadId() );
            arrayList.add(jsonObject);
        }

        list = new ArrayList<Map<String, String>>();
        for(int i = 0; i < arrayList.size(); i++) {
            map = new HashMap<String, String>();
            JSONObject jsonObject = new JSONObject(arrayList.get(i).toString());

            String visit_date = (String) jsonObject.get("visit_date");
            String year = visit_date.substring(0, 4);
            String month = visit_date.substring(4, 6);
            String day = visit_date.substring(6);
            visit_date = year + "." + month + "." + day;

            String wpad_id = (String) jsonObject.get("wpad_id");
            String dong = wpad_id.substring(7, 11);
            String hose = wpad_id.substring(12);

            String car_num = (String) jsonObject.get("car_num");
            String visit_starttime = (String) jsonObject.get("visit_starttime");

            map.put("visit_date", visit_date);
            map.put("wpad_id", wpad_id);
            map.put("dong", dong);
            map.put("hose", hose);
            map.put("car_num", car_num);
            map.put("visit_starttime", visit_starttime);
            map.put("houscplxNm", reqHouscplxNm);
            list.add(map);
        }

        model.addAttribute("searchingYn", "Y");
        model.addAttribute("resultList", list);
        model.addAttribute("userId", session.getUserId());
        model.addAttribute("houscplxCd", houscplxCd);
        model.addAttribute("houscplxNm", reqHouscplxNm);
        model.addAttribute("dongNo", StringUtils.defaultIfEmpty(dongNo,"all"));
        model.addAttribute("hoseNo", StringUtils.defaultIfEmpty(hoseNo,"all"));
        model.addAttribute("visitDateStart", visitDateStart);
        model.addAttribute("visitDateEnd", visitDateEnd);

        return thisUrl + "/list";
    }

}