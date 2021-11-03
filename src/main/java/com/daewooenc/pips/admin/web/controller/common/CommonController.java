package com.daewooenc.pips.admin.web.controller.common;

import com.daewooenc.pips.admin.core.domain.common.SessionUser;
import com.daewooenc.pips.admin.core.util.SessionUtil;
import com.daewooenc.pips.admin.core.util.message.MessageUtil;
import com.daewooenc.pips.admin.web.common.UserType;
import com.daewooenc.pips.admin.web.domain.dto.housingcplx.HousingCplx;
import com.daewooenc.pips.admin.web.domain.dto.user.PipsUser;
import com.daewooenc.pips.admin.web.domain.vo.excel.ExcelVo;
import com.daewooenc.pips.admin.web.service.household.HouseholdService;
import com.daewooenc.pips.admin.web.service.housingcplx.HousingCplxService;
import com.daewooenc.pips.admin.web.util.CommonUtil;
import com.daewooenc.pips.admin.web.util.JsonUtil;
import com.daewooenc.pips.admin.web.util.XSSUtil;
import com.daewooenc.pips.admin.web.util.excel.ExportExcelUtil;
import org.apache.commons.lang3.StringEscapeUtils;
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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-10-07      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-10-07
 **/
@Controller
@RequestMapping("/cm/common")
public class CommonController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String thisUrl = "cm/common";

    @Autowired
    private HousingCplxService housingCplxService;

    @Autowired
    private HouseholdService householdService;

    @Autowired
    private XSSUtil xssUtil;

    /**
     * 시스템 및 단지 관리자용 검색영역에서 단지선택 버큰 클릭시 단지목록을 조회
     * Ajax 방식
     * @param housingCplx
     * @param request
     * @return
     */
    @RequestMapping(value = "housingcplx/list", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/text; charset=utf8")
    @ResponseBody
    public String getHousingCplxList(HousingCplx housingCplx, HttpServletRequest request) {
        String result = "";

        List<HousingCplx> nHousingCplxList = new ArrayList();
        HousingCplx hcp = new HousingCplx();
        hcp.setHouscplxNm("전체");
        nHousingCplxList.add(hcp);

        List<HousingCplx> housingCplxList = housingCplxService.getHouscplxMetaList(housingCplx);
        for (int i = 0; i < housingCplxList.size(); i++) {
            HousingCplx hc = housingCplxList.get(i);
            nHousingCplxList.add(hc);
        }

        String housingCplxJsonArray = JsonUtil.toJsonNotZero(nHousingCplxList);

        result = housingCplxJsonArray;

        return result;
    }

    /**
     * 멀티 단지 관리자용 검색영역에서 단지선택 버큰 클릭시 단지목록을 조회
     * Ajax 방식
     * @param housingCplx
     * @param request
     * @return
     */
    @RequestMapping(value = "housingcplx/multiList", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/text; charset=utf8")
    @ResponseBody
    public String getHousingCplxMultiList(HousingCplx housingCplx, HttpServletRequest request) {
        String result = "";
        String userId = request.getParameter("userId");

        List<HousingCplx> nHousingCplxList = new ArrayList();
        HousingCplx hcp = new HousingCplx();

        if(!"Y".equals(request.getParameter("noAll"))) {
            hcp.setHouscplxNm("전체");
            nHousingCplxList.add(hcp);
            housingCplx.setUserId(userId);
        }

        List<HousingCplx> housingCplxList = housingCplxService.getHouscplxMetaMultiList(housingCplx);
        for (int i = 0; i < housingCplxList.size(); i++) {
            HousingCplx hc = housingCplxList.get(i);
            nHousingCplxList.add(hc);
        }

        String housingCplxJsonArray = JsonUtil.toJsonNotZero(nHousingCplxList);

        result = housingCplxJsonArray;

        return result;
    }

    /**
     * 시스템 및 단지 관리자용 검색영역에서 단지선택 버큰 클릭시 단지목록을 조회(전체 제거)
     * Ajax 방식
     * @param housingCplx
     * @param request
     * @return
     */
    @RequestMapping(value = "housingcplx/listNotAll", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/text; charset=utf8")
    @ResponseBody
    public String getHousingCplxListNotAll(HousingCplx housingCplx, HttpServletRequest request) {
        //단지상태가 활성화인 것만 조회
        housingCplx.setDelYn("N");

        String result = "";

        List<HousingCplx> nHousingCplxList = new ArrayList();

        List<HousingCplx> housingCplxList = housingCplxService.getHouscplxMetaList(housingCplx);
        for (int i = 0; i < housingCplxList.size(); i++) {
            HousingCplx hc = housingCplxList.get(i);
            nHousingCplxList.add(hc);
        }

        String housingCplxJsonArray = JsonUtil.toJsonNotZero(nHousingCplxList);

        result = housingCplxJsonArray;

        return result;
    }

    /**
     * 시스템 및 단지 관리자용 검색영역에서 단지선택 버큰 클릭시 단지목록을 조회(수정)
     * Ajax 방식
     * @param housingCplx
     * @param request
     * @return
     */
    @RequestMapping(value = "housingcplx/danjiSelectModify", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/text; charset=utf8")
    @ResponseBody
    public String getHousingCplxDanjiSelectModify(HousingCplx housingCplx, HttpServletRequest request) {

        housingCplx.setLnkSvcId(request.getParameter("blltNo"));
        housingCplx.setbanrLnkCls(request.getParameter("banrLnkCls"));

        String result = "";

        List<HousingCplx> nHousingCplxList = new ArrayList();

        List<HousingCplx> housingCplxList = housingCplxService.getHouscplxMetaListSelectModify(housingCplx);
        for (int i = 0; i < housingCplxList.size(); i++) {
            HousingCplx hc = housingCplxList.get(i);
            nHousingCplxList.add(hc);
        }

        String housingCplxJsonArray = JsonUtil.toJsonNotZero(nHousingCplxList);

        result = housingCplxJsonArray;

        return result;
    }

    /**
     * 시스템 관리자용 서비스 공지사항 단지선택 버큰 클릭시 단지목록을 조회(수정)
     * Ajax 방식
     * @param housingCplx
     * @param request
     * @return
     */
    @RequestMapping(value = "housingcplx/danjiSelectModifyNoti", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/text; charset=utf8")
    @ResponseBody
    public String getHousingCplxDanjiSelectModifyNoti(HousingCplx housingCplx, HttpServletRequest request) {

        String blltNo = request.getParameter("blltNo");

        String result = "";

        List<HousingCplx> nHousingCplxList = new ArrayList();

        List<HousingCplx> housingCplxList = housingCplxService.getHouscplxMetaListSelectModifyNoti(blltNo);
        for (int i = 0; i < housingCplxList.size(); i++) {
            HousingCplx hc = housingCplxList.get(i);
            nHousingCplxList.add(hc);
        }

        String housingCplxJsonArray = JsonUtil.toJsonNotZero(nHousingCplxList);

        result = housingCplxJsonArray;

        return result;
    }

    /**
     * 시스템 및 단지 관리자용 검색영역에서 동, 호 선택시 기본정보를 조회
     * Ajax 방식
     * @param pipsUser
     * @param request
     * @return
     */
    @RequestMapping(value = "household/list", method = {RequestMethod.GET, RequestMethod.POST},produces = "application/text; charset=utf8")
    @ResponseBody
    public String getHouseholdList(PipsUser pipsUser, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
        String groupName = StringUtils.defaultIfEmpty(paramGroupName, "");

        JSONObject result = new JSONObject();
        JSONArray params = new JSONArray();
        JSONObject param = new JSONObject();

        if (UserType.COMPLEX.getGroupName().equals(groupName)) {
            String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
            String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));

            String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

            if (StringUtils.isEmpty(houscplxCd)) {
                param.put("id", "houscplxCd");
                param.put("msg", MessageUtil.getMessage("NotEmpty.houscplxCd.houscplxCd"));
                params.put(param);

                result.put("status", false);
                result.put("params", params);

                return result.toString();
            } else {
                pipsUser.setHouscplxCd(houscplxCd);
            }
        }

        List<PipsUser> householdList = householdService.getHouseholdMetaList(pipsUser);

        String householdJsonArray = JsonUtil.toJsonNotZero(householdList);

        return householdJsonArray;
    }

    /**
     * 시스템 및 단지 관리자용 각 목록페이지에서 엑셀 다운로드
     * Ajax 방식
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "excel/download", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String excelDownload(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject result = new JSONObject();
        JSONArray params = new JSONArray();
        JSONObject param = new JSONObject();

        String paramTitle = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("title")));
        String paramTableHeader = xssUtil.replaceTag(StringUtils.defaultString(request.getParameter("tableHeader")));
        String paramTableData = xssUtil.replaceTag(StringUtils.defaultString(request.getParameter("tableData")));

        String title = StringUtils.defaultIfEmpty(paramTitle, "");
        String tableHeader = StringUtils.defaultIfEmpty(paramTableHeader, "");
        String tableData = StringUtils.defaultIfEmpty(paramTableData, "");
        tableData = StringEscapeUtils.unescapeHtml4(tableData.replaceAll("& lt;","<").replaceAll("& gt;",">"));

        if (StringUtils.isEmpty(title)) {
            param.put("id", "title");
            param.put("msg", MessageUtil.getMessage("NotEmpty.title.title"));
            params.put(param);
        }

        if (StringUtils.isEmpty(tableHeader)) {
            param.put("id", "tableHeader");
            param.put("msg", MessageUtil.getMessage("NotEmpty.tableHeader.tableHeader"));
            params.put(param);
        }

        if (StringUtils.isEmpty(tableData)) {
            param.put("id", "tableData");
            param.put("msg", MessageUtil.getMessage("NotEmpty.tableData.tableData"));
            params.put(param);
        }

        if (params.length() > 0) {
            result.put("status", false);
            result.put("params", params);

            return result.toString();
        }

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            Date date = new Date();

            String currentDate = simpleDateFormat.format(date);
            String fileName =  currentDate + "_" + title + ".xlsx";

            JSONArray tableHeaderArray = new JSONArray(tableHeader);
            JSONArray tableDataArray = new JSONArray(tableData);

            List<String> tableHeaderList = new ArrayList<String>();
            for(int i = 0; i < tableHeaderArray.length(); i++){
                tableHeaderList.add(tableHeaderArray.getString(i));
            }

            String[] tableHeaders = tableHeaderList.toArray(new String[tableHeaderList.size()]);

            JSONArray dataArray = new JSONArray();

            for (int i=0; i<tableDataArray.length(); i++) {
                JSONObject currentData = tableDataArray.getJSONObject(i);

                JSONObject jsonObject = new JSONObject();

                for (int j=0; j<tableHeaderList.size(); j++) {
                    jsonObject.put(tableHeaderList.get(j), currentData.get(tableHeaderList.get(j)));
                }

                dataArray.put(jsonObject);
            }

            ArrayList<JSONObject> dataList = new ArrayList<JSONObject>();
            JSONArray jsonArray = dataArray;

            if (jsonArray != null) {
                for (int i=0; i<jsonArray.length(); i++){
                    dataList.add(jsonArray.getJSONObject(i));
                }
            }

            CommonUtil commonUtil = new CommonUtil();

            String header = commonUtil.getBrowserHeader(request);
            fileName = commonUtil.getDownloadFileName(header, fileName);

            ExportExcelUtil exportExcelUtil = new ExportExcelUtil();

            ExcelVo excelVo = exportExcelUtil.makeExportData(dataList, fileName, title, title, tableHeaders, tableHeaders);

            fileName = xssUtil.replaceAll(fileName);
            excelVo.setFileName(fileName);
            exportExcelUtil.exportExcel(excelVo, response);

            result.put("status", true);
        } catch (Exception e) {
            logger.error("excel/download Exception: " + e.getCause());
            result.put("status", false);
        }

        return result.toString();
    }

    /**
     * 엑셀 다운로드 샘플
     * Ajax 방식
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "excel/sample", method = {RequestMethod.GET, RequestMethod.POST})
    public String excelSample(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();

        String currentDate = simpleDateFormat.format(date);
        String title = "세대별 장치목록";
        String fileName =  currentDate + "_" + title + ".xlsx";
        String tableHeader = "['동', '호', '번호']";
        String tableData = "[{'동': '1002','호': '101','번호': '010-1234-5678'},{'동': '1003','호': '102','번호': '010-1234-1234'},{'동': '1004','호': '103','번호': '010-1234-7410'},{'동': '1005','호': '104','번호': '010-1234-8520'},{'동': '1006','호': '105','번호': '010-1234-9630'}]";

        JSONArray tableHeaderArray = new JSONArray(tableHeader);
        JSONArray tableDataArray = new JSONArray(tableData);

        List<String> tableHeaderList = new ArrayList<String>();
        for(int i = 0; i < tableHeaderArray.length(); i++){
            tableHeaderList.add(tableHeaderArray.getString(i));
        }

        String[] tableHeaders = tableHeaderList.toArray(new String[tableHeaderList.size()]);

        JSONArray dataArray = new JSONArray();

        for (int i=0; i<tableDataArray.length(); i++) {
            JSONObject currentData = tableDataArray.getJSONObject(i);

            JSONObject jsonObject = new JSONObject();

            for (int j=0; j<tableHeaderList.size(); j++) {
                jsonObject.put(tableHeaderList.get(j), currentData.get(tableHeaderList.get(j)));
            }

            dataArray.put(jsonObject);
        }

        ArrayList<JSONObject> dataList = new ArrayList<JSONObject>();
        JSONArray jsonArray = dataArray;

        if (jsonArray != null) {
            for (int i=0; i<jsonArray.length(); i++){
                dataList.add(jsonArray.getJSONObject(i));
            }
        }

        CommonUtil commonUtil = new CommonUtil();

        String header = commonUtil.getBrowserHeader(request);
        fileName = commonUtil.getDownloadFileName(header, fileName);

        ExportExcelUtil exportExcelUtil = new ExportExcelUtil();

        ExcelVo excelVo = exportExcelUtil.makeExportData(dataList, fileName, title, title, tableHeaders, tableHeaders);

        fileName = xssUtil.replaceAll(fileName);
        excelVo.setFileName(fileName);
        exportExcelUtil.exportExcel(excelVo, response);

        return "cm/housingcplx/info/list";
    }
}