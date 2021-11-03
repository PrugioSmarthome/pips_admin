package com.daewooenc.pips.admin.web.controller.system.failureOccurrence;

import com.daewooenc.pips.admin.core.domain.common.SessionUser;
import com.daewooenc.pips.admin.core.util.SessionUtil;
import com.daewooenc.pips.admin.web.common.UserType;
import com.daewooenc.pips.admin.web.domain.dto.housingcplx.HousingCplx;
import com.daewooenc.pips.admin.web.domain.vo.mongodb.FailureOccurrenceConditionVo;
import com.daewooenc.pips.admin.web.domain.vo.mongodb.VisitCarHistoryConditionVo;
import com.daewooenc.pips.admin.web.service.homenet.HomenetService;
import com.daewooenc.pips.admin.web.service.housingcplx.HousingCplxService;
import com.daewooenc.pips.admin.web.util.XSSUtil;
import com.daewooenc.pips.admin.web.util.mongo.FailureOccurrenceMongo;
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
@RequestMapping("/cm/system/failureOccurrence")
public class FailureOccurrenceController {
    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    private String thisUrl = "cm/system/failureOccurrence";

    @Autowired
    private XSSUtil xssUtil;

    @Autowired
    private FailureOccurrenceMongo failureOccurrenceMongo;

    @Autowired
    private HomenetService homenetService;

    @Autowired
    private HousingCplxService housingCplxService;

/**
     *
     * @param
     * @param model
     * @param request
     * @return
     */

    @RequestMapping(value = "list", method = {RequestMethod.GET,RequestMethod.POST})
    public String search(Model model, HttpServletRequest request) throws Exception {

        Map<String, Object> failureOccurrenceMap;
        List<Map<String, Object>> failureOccurrenceList = new ArrayList<Map<String, Object>>();

        List<HousingCplx> houscplxList = housingCplxService.selectFailureOccurrence();

        String outcomDateStart = StringUtils.defaultIfEmpty(request.getParameter("outcomDateStart"), "");
        String outcomDateEnd = StringUtils.defaultIfEmpty(request.getParameter("outcomDateEnd"), "");


        for(int i=0; i<houscplxList.size(); i++) {

            failureOccurrenceMap = new HashMap<String, Object>();

            String homenetNm = homenetService.getHomenetNm(houscplxList.get(i).getHmnetId());

            long resultList = failureOccurrenceMongo.getList(houscplxList.get(i).getHmnetId(), houscplxList.get(i).getHouscplxCd(), outcomDateStart, outcomDateEnd);

            failureOccurrenceMap.put("houscplxCd", houscplxList.get(i).getHouscplxCd());
            failureOccurrenceMap.put("houscplxNm", houscplxList.get(i).getHouscplxNm());
            failureOccurrenceMap.put("homenetNm", homenetNm);
            failureOccurrenceMap.put("resultList", resultList);

            failureOccurrenceList.add(failureOccurrenceMap);
        }

        model.addAttribute("failureOccurrenceList", failureOccurrenceList);
        model.addAttribute("outcomDateStart", outcomDateStart);
        model.addAttribute("outcomDateEnd", outcomDateEnd);

        return thisUrl + "/list";
    }

}