package com.daewooenc.pips.admin.web.controller.maintenanceFee;

import com.daewooenc.pips.admin.core.domain.common.SessionUser;
import com.daewooenc.pips.admin.core.util.SessionUtil;
import com.daewooenc.pips.admin.web.domain.dto.maintenanceFee.MaintenanceFee;
import com.daewooenc.pips.admin.web.service.maintenanceFee.MaintenanceFeeService;
import com.daewooenc.pips.admin.web.util.XSSUtil;
import org.apache.commons.lang3.StringUtils;
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
 * 단지 관리비 관리 관련 Controller
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
@Controller
@RequestMapping("/cm/maintenance/fee")
public class MaintenanceFeeController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String thisUrl = "cm/maintenance/fee";

    @Autowired
    private XSSUtil xssUtil;

    @Autowired
    private MaintenanceFeeService maintenanceFeeservice;

    /**
     * 단지 관리 > 단지 관리비 관리 > 단지 관리비 목록(최초 접근시)
     * @param maintenanceFee
     * @param request
     * @return
     */
    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    public String maintenanceFeeList(MaintenanceFee maintenanceFee, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
        String groupName = StringUtils.defaultIfEmpty(paramGroupName, "");
        String houscplxCd = xssUtil.replaceAll(StringUtils.defaultString(maintenanceFee.getHouscplxCd()));

        if("COMPLEX_ADMIN".equals(groupName)) {
            houscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        }

        model.addAttribute("houscplxCd", maintenanceFee.getHouscplxCd());
        model.addAttribute("houscplxNm", maintenanceFee.getHouscplxNm());
        model.addAttribute("userId", session.getUserId());

        return thisUrl + "/list";
    }

    /**
     * 단지 관리 > 단지 관리비 관리 > 단지 관리비 목록
     * @param maintenanceFee
     * @param request
     * @return
     */
    @RequestMapping(value = "search", method = {RequestMethod.GET, RequestMethod.POST})
    public String maintenanceFeeSearch(MaintenanceFee maintenanceFee, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
        String groupName = StringUtils.defaultIfEmpty(paramGroupName, "");

        String houscplxCd = maintenanceFee.getHouscplxCd();
        String houscplxNm = maintenanceFee.getHouscplxNm();
        String dongNo = maintenanceFee.getDongNo();
        String hoseNo = maintenanceFee.getHoseNo();
        String hsholdId = "";

        if("COMPLEX_ADMIN".equals(groupName)) {
            houscplxCd = session.getHouscplxCd();
            houscplxNm = session.getHouscplxNm();
        }

        if(houscplxCd != null && !"all".equals(houscplxCd)){
            hsholdId = houscplxCd;
            if(dongNo != null && !"all".equals(dongNo)){
                hsholdId = houscplxCd + "." + dongNo;
                if(hoseNo != null && !"all".equals(hoseNo)){
                    hsholdId = houscplxCd + "." + dongNo + "." + hoseNo;
                }
            }
        }

        maintenanceFee.setHsholdId(hsholdId);

        if("MULTI_COMPLEX_ADMIN".equals(groupName)){
            maintenanceFee.setUserId(session.getUserId());
            model.addAttribute("maintenanceFeeList", maintenanceFeeservice.getMaintenanceFeeListMulti(maintenanceFee));
        }else{
            model.addAttribute("maintenanceFeeList", maintenanceFeeservice.getMaintenanceFeeList(maintenanceFee));
        }

        model.addAttribute("yr", maintenanceFee.getYr());
        model.addAttribute("mm", maintenanceFee.getMm());
        model.addAttribute("houscplxCd", houscplxCd);
        model.addAttribute("houscplxNm", houscplxNm);
        model.addAttribute("dongNo", StringUtils.defaultIfEmpty(maintenanceFee.getDongNo(),"all"));
        model.addAttribute("hoseNo", StringUtils.defaultIfEmpty(maintenanceFee.getHoseNo(),"all"));
        model.addAttribute("userId", session.getUserId());
        model.addAttribute("searchingYn", "Y");

        return thisUrl + "/list";
    }

    /**
     * 단지 관리 > 단지 관리비 관리 > 단지 관리비 목록 > 단지 관리비 상세
     * @param maintenanceFee
     * @param request
     * @return
     */
    @RequestMapping(value = "view", method = {RequestMethod.GET, RequestMethod.POST})
    public String maintenanceFeeView(MaintenanceFee maintenanceFee, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        maintenanceFee.setYr(request.getParameter("yr__"));
        maintenanceFee.setMm(request.getParameter("mm__"));

        model.addAttribute("maintenanceFeeDetail", maintenanceFeeservice.getMaintenanceFeeDetail(maintenanceFee));

        return thisUrl + "/view";
    }

    /**
     * 단지 관리 > 단지 관리비 관리 > 단지 관리비 목록 > 단지 관리비 등록
     * @param maintenanceFee
     * @param request
     * @return
     */
    @RequestMapping(value = "add", method = {RequestMethod.GET, RequestMethod.POST})
    public String maintenanceFeeAdd(MaintenanceFee maintenanceFee, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
        String groupName = StringUtils.defaultIfEmpty(paramGroupName, "");

        if("COMPLEX_ADMIN".equals(groupName)) {
            model.addAttribute("houscplxCd", session.getHouscplxCd());
            model.addAttribute("houscplxNm", session.getHouscplxNm());
        }

        model.addAttribute("userId", session.getUserId());

        return thisUrl + "/add";
    }

    /**
     * 단지 관리 > 단지 관리비 관리 > 단지 관리비 목록 > 단지 관리비 등록 > 등록하기
     * @param maintenanceFee
     * @param request
     * @return
     */
    @RequestMapping(value = "insertAction", method = {RequestMethod.GET, RequestMethod.POST})
    public String insertAction(MaintenanceFee maintenanceFee, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        String houscplxCd = request.getParameter("houscplxCd");

        Map<String, Object> maintenanceFeeMap;

        maintenanceFee.setCrerId(session.getUserId());

        String csv = request.getParameter("csvData");
        char c = (char) 26;
        String[] line = csv.split(String.valueOf(c));


        int lineLength = line.length;
        int lineCnt = (int)Math.ceil(lineLength*1.0 / 1000);

        for(int j = 0; j < lineCnt; j++) {

            List<Map<String, Object>> maintenanceFeeList = new ArrayList<Map<String, Object>>();

            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("crerId", session.getUserId());

            for (int i = j*1000; i < (j+1)*1000; i++) {

                if(i == lineLength){
                    break;
                }

                maintenanceFeeMap = new HashMap<String, Object>();

                String[] data = line[i].split(",");
                String dong = String.format("%04d", Integer.parseInt(data[1]));
                String hose = String.format("%04d", Integer.parseInt(data[2]));

                maintenanceFeeMap.put("hsholdId", houscplxCd + "." + dong + "." + hose);
                maintenanceFeeMap.put("yr", data[67].substring(0, 4));
                maintenanceFeeMap.put("mm", data[67].substring(4));
                maintenanceFeeMap.put("genMgmCstQty", data[4]);
                maintenanceFeeMap.put("cleanCstQty", data[5]);
                maintenanceFeeMap.put("dfCstQty", data[6]);
                maintenanceFeeMap.put("elevCstQty", data[7]);
                maintenanceFeeMap.put("repairMtCstQty", data[8]);
                maintenanceFeeMap.put("longRepCstQty", data[9]);
                maintenanceFeeMap.put("cemcCstQty", data[10]);
                maintenanceFeeMap.put("susCstQty", data[11]);
                maintenanceFeeMap.put("expCstQty", data[12]);
                maintenanceFeeMap.put("repMtCstQty", data[13]);
                maintenanceFeeMap.put("builPreCstQty", data[14]);
                maintenanceFeeMap.put("conMgmCstQty", data[15]);
                maintenanceFeeMap.put("jobSupCstQty", data[16]);
                maintenanceFeeMap.put("hsholdElctCstQty", data[17]);
                maintenanceFeeMap.put("commElctCstQty", data[18]);
                maintenanceFeeMap.put("elevElctCstQty", data[19]);
                maintenanceFeeMap.put("tvCstQty", data[20]);
                maintenanceFeeMap.put("hsholdWtrsplCstQty", data[22]);
                maintenanceFeeMap.put("commWtrsplCstQty", data[23]);
                maintenanceFeeMap.put("hsholdHeatCstQty", data[25]);
                maintenanceFeeMap.put("basicHeatCstQty", data[26]);
                maintenanceFeeMap.put("commHeatCstQty", data[27]);
                maintenanceFeeMap.put("hsholdHotwtrCstQty", data[28]);
                maintenanceFeeMap.put("wastCommisionCstQty", data[31]);
                maintenanceFeeMap.put("eleccarElctCstQty", data[33]);
                maintenanceFeeMap.put("hsholdAccCardCstQty", data[34]);
                maintenanceFeeMap.put("beforeMgmcstQty", data[39]);
                maintenanceFeeMap.put("afterMgmcstQty", data[40]);
                maintenanceFeeMap.put("elctUseQty", data[41]);
                maintenanceFeeMap.put("hotwtrUseQty", data[42]);
                maintenanceFeeMap.put("wtrsplUseRate", data[43]);
                maintenanceFeeMap.put("heatUseQty", data[44]);
                maintenanceFeeMap.put("gasUseRate", data[45]);
                maintenanceFeeMap.put("currentMgmCstQty", data[46]);
                maintenanceFeeMap.put("unpaidMgmCstQty", data[47]);
                maintenanceFeeMap.put("unpaidArrMgmCstQty", data[48]);
                maintenanceFeeMap.put("overdueMgmCstQty", data[49]);
                maintenanceFeeMap.put("beforeElctQty", data[50]);
                maintenanceFeeMap.put("currentElctQty", data[51]);
                maintenanceFeeMap.put("beforeHotwtrQty", data[52]);
                maintenanceFeeMap.put("currentHotwtrQty", data[53]);
                maintenanceFeeMap.put("beforeWtrsplQty", data[54]);
                maintenanceFeeMap.put("currentWtrsplQty", data[55]);
                maintenanceFeeMap.put("beforeHeatQty", data[56]);
                maintenanceFeeMap.put("currentHeatQty", data[57]);
                maintenanceFeeMap.put("beforeGasQty", data[58]);
                maintenanceFeeMap.put("currentGasQty", data[59]);
                maintenanceFeeMap.put("sumMgmCstQty", data[65]);
                maintenanceFeeMap.put("agencyMgmCstQty", data[66]);
                maintenanceFeeMap.put("currentAfterUnpaidCstQty", data[68]);
                maintenanceFeeMap.put("elctDiscountCstQty", data[71]);
                maintenanceFeeMap.put("wtrsplDiscountCstQty", data[72]);

                maintenanceFeeList.add(maintenanceFeeMap);
            }
            paramMap.put("maintenanceFeeList", maintenanceFeeList);
            maintenanceFeeservice.deleteMaintenanceFee(paramMap);
            maintenanceFeeservice.insertMaintenanceFee(paramMap);
        }


        return "redirect:/" + thisUrl + "/list";
    }


}