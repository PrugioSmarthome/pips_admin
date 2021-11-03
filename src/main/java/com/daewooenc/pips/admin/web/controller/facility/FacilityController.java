package com.daewooenc.pips.admin.web.controller.facility;

import com.daewooenc.pips.admin.core.domain.common.SessionUser;
import com.daewooenc.pips.admin.core.util.SessionUtil;
import com.daewooenc.pips.admin.core.util.message.MessageUtil;
import com.daewooenc.pips.admin.web.domain.dto.facility.FacilityBizco;
import com.daewooenc.pips.admin.web.domain.dto.facility.FacilityBizcoCaddr;
import com.daewooenc.pips.admin.web.domain.dto.housingcplx.HousingCplx;
import com.daewooenc.pips.admin.web.service.facility.FacilityService;
import com.daewooenc.pips.admin.web.util.JsonUtil;
import com.daewooenc.pips.admin.web.util.XSSUtil;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 시설업체관리 관련 Controller
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-09-18      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-09-18
 **/
@Controller
@RequestMapping("/cm/system/facility")
public class FacilityController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String thisUrl = "cm/system/facility";

    @Autowired
    private FacilityService facilityService;

    @Autowired
    private XSSUtil xssUtil;

    private @Value("${pips.encrypt.key}") String pipsEncryptKey;

    @RequestMapping(value = "excel/list", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/text; charset=utf8")
    @ResponseBody
    public String facilityInfoExcelList(FacilityBizco facilityBizco, HttpServletRequest request) {
        String paramDelYn = xssUtil.replaceAll(StringUtils.defaultString(facilityBizco.getDelYn()));
        String delYn = StringUtils.defaultIfEmpty(paramDelYn, "N");
        String result = "";

        if (StringUtils.isEmpty(delYn)) {
            JSONObject jsonResult = new JSONObject();
            JSONArray params = new JSONArray();
            JSONObject param = new JSONObject();

            param.put("id", "delYn");
            param.put("msg", MessageUtil.getMessage("NotEmpty.delYn.delYn"));
            params.put(param);

            jsonResult.put("status", false);
            jsonResult.put("params", params);

            return jsonResult.toString();
        } else {
            pipsEncryptKey = xssUtil.replaceAll(StringUtils.defaultString(pipsEncryptKey));

            facilityBizco.setEncKey(pipsEncryptKey);
            List<FacilityBizco> facilityBizcoExcelList = facilityService.getFacilityInfoExcelList(facilityBizco);
            String facilityBizcoExcelJsonArray = JsonUtil.toJsonNotZero(facilityBizcoExcelList);

            logger.debug("Excel download facilityBizcoExcelJsonArray : " + facilityBizcoExcelJsonArray);

            result = facilityBizcoExcelJsonArray;
        }

        return result;
    }

    /**
     * 단지 관리 > 단지정보 관리 > 시설업체 등록
     * 시스템 및 단지 관리자용 시설업체 등록
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "add", method = {RequestMethod.GET, RequestMethod.POST})
    public String facilityInfoAdd(Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        model.addAttribute("houscplxCd", houscplxCd);

        return thisUrl + "/add";
    }

    /**
     * 단지 관리 > 단지정보 관리 > 시설업체 등록처리
     * 시스템 및 단지 관리자용 시설업체 등록처리
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "addFacilityInfoAction", method = {RequestMethod.GET, RequestMethod.POST})
    public String addFacilityInfoAction(Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));

        String adminId = StringUtils.defaultIfEmpty(paramSessionUserId, "");
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        JSONArray facilityInfoJsonArray = new JSONArray(request.getParameter("facilityInfoList"));
        int length = facilityInfoJsonArray.length();

        if (length > 0) {
            for(int i=0; i<length; i++) {
                List<FacilityBizcoCaddr> addressList = new ArrayList<>();

                JSONObject facilityInfoJsonObject = facilityInfoJsonArray.getJSONObject(i);
                JSONArray facilityAddressInfoArray = facilityInfoJsonObject.getJSONArray("addressList");

                FacilityBizco facilityBizco = new FacilityBizco();

                String facltBizcotpNm = xssUtil.replaceAll(StringUtils.defaultString(facilityInfoJsonObject.getString("fa")));
                String twbsNm = xssUtil.replaceAll(StringUtils.defaultString(facilityInfoJsonObject.getString("tw")));
                String bizcoNm = xssUtil.replaceAll(StringUtils.defaultString(facilityInfoJsonObject.getString("biz")));
                String conCont = xssUtil.replaceAll(StringUtils.defaultString(facilityInfoJsonObject.getString("con")));

                facilityBizco.setFacltBizcoTpNm(StringUtils.defaultIfEmpty(facltBizcotpNm, ""));
                facilityBizco.setTwbsNm(StringUtils.defaultIfEmpty(twbsNm, ""));
                facilityBizco.setBizcoNm(StringUtils.defaultIfEmpty(bizcoNm, ""));
                facilityBizco.setConCont(StringUtils.defaultIfEmpty(conCont, ""));
                facilityBizco.setMgmTpCd("COMPLEX_MGMT");
                facilityBizco.setCrerId(adminId);

                for (int j=0; j<facilityAddressInfoArray.length(); j++) {
                    JSONObject facilityAddressJsonObject = facilityAddressInfoArray.getJSONObject(j);

                    String perchrgNm = xssUtil.replaceAll(StringUtils.defaultString(facilityAddressJsonObject.getString("per")));
                    String mphoneNo = xssUtil.replaceAll(StringUtils.defaultString(facilityAddressJsonObject.getString("mp")));
                    String offcPhoneNo = xssUtil.replaceAll(StringUtils.defaultString(facilityAddressJsonObject.getString("offc")));
                    String faxNo = xssUtil.replaceAll(StringUtils.defaultString(facilityAddressJsonObject.getString("fax")));

                    FacilityBizcoCaddr facilityBizcoCaddr = new FacilityBizcoCaddr();
                    facilityBizcoCaddr.setPerchrgNm(StringUtils.defaultIfEmpty(perchrgNm, ""));
                    facilityBizcoCaddr.setMphoneNo(StringUtils.defaultIfEmpty(mphoneNo, ""));
                    facilityBizcoCaddr.setOffcPhoneNo(StringUtils.defaultIfEmpty(offcPhoneNo, ""));
                    facilityBizcoCaddr.setFaxNo(StringUtils.defaultIfEmpty(faxNo, ""));
                    facilityBizcoCaddr.setMgmTpCd("COMPLEX_MGMT");
                    facilityBizcoCaddr.setCrerId(adminId);

                    addressList.add(facilityBizcoCaddr);
                }

                facilityService.insertFacilityInfo(facilityBizco, addressList, houscplxCd);
            }
        }

        model.addAttribute("houscplxCd", houscplxCd);

        return "redirect:/" + thisUrl + "/facility/list";
    }

    /**
     * 단지 관리 > 단지정보 관리 > 시설업체 상세
     * 시스템 및 단지 관리자용 시설업체 상세 조회
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    public String facilityInfoList(@RequestParam(value="houscplxCdGoList", required=false) String houscplxCdGoList, @RequestParam(value="houscplxCdGoListNm", required=false) String houscplxCdGoListNm, FacilityBizco facilityBizco, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        if(!"".equals(houscplxCdGoList) && houscplxCdGoList != null){
            facilityBizco.setHouscplxCd(houscplxCdGoList);
            model.addAttribute("houscplxCdGoListNm", houscplxCdGoListNm);
            model.addAttribute("houscplxCdGoList", houscplxCdGoList);
        }

        String paramHouscplxNm = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxNm")));
        String paramBizcoNm = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("bizcoNm")));
        String paramStartCrDt = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("startCrDt")));
        String paramEndCrDt = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("endCrDt")));
        String paramSearchCheck = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("search_check")));

        String houscplxNm = StringUtils.defaultIfEmpty(paramHouscplxNm,"");
        String bizcoNm = StringUtils.defaultIfEmpty(paramBizcoNm,"");
        String startCrDt = StringUtils.defaultIfEmpty(paramStartCrDt,"");
        String endCrDt = StringUtils.defaultIfEmpty(paramEndCrDt,"");
        String search_check = StringUtils.defaultIfEmpty(paramSearchCheck,"N");

        model.addAttribute("facilityInfoList", facilityService.getFacilityInfoList(facilityBizco));
        model.addAttribute("houscplxCd", houscplxCd);
        model.addAttribute("houscplxNm", houscplxNm);
        model.addAttribute("bizcoNm", bizcoNm);
        model.addAttribute("startCrDt", startCrDt);
        model.addAttribute("endCrDt", endCrDt);
        model.addAttribute("search_check", search_check);

        return thisUrl + "/list";
    }

    /**
     * 단지 관리 > 단지정보 관리 > 시설업체 상세 연락처
     * 시스템 및 단지 관리자용 시설업체 상세 연락처 조회
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "detailView", method = {RequestMethod.GET, RequestMethod.POST})
    public String facilityInfoDetailView(Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramFacltBizcoId = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("facltBizcoId")));
        String facltBizcoId = StringUtils.defaultIfEmpty(paramFacltBizcoId, "");

        Integer parseFacltBizcoId = null;

        if (StringUtils.isNotEmpty(facltBizcoId)) {
            pipsEncryptKey = xssUtil.replaceAll(StringUtils.defaultString(pipsEncryptKey));

            parseFacltBizcoId = Integer.parseInt(facltBizcoId);

            FacilityBizco facilityBizco = new FacilityBizco();
            facilityBizco.setFacltBizcoId(parseFacltBizcoId);
            facilityBizco.setEncKey(pipsEncryptKey);

            model.addAttribute("facltBizcoId", facltBizcoId);
            model.addAttribute("facilityInfoCaddrList", facilityService.getFacilityInfoCaddrList(facilityBizco));
        } else if (StringUtils.isEmpty(facltBizcoId)) {
            return thisUrl + "/list";
        }
        model.addAttribute("houscplxCdGoList", StringUtils.defaultIfEmpty(request.getParameter("houscplxCdGoList"), ""));
        model.addAttribute("houscplxCdGoListNm", StringUtils.defaultIfEmpty(request.getParameter("houscplxCdGoListNm"), ""));

        return thisUrl + "/detailView";
    }

    /**
     * 단지 관리 > 단지정보 관리 > 시설업체 수정
     * 시스템 및 단지 관리자용 시설업체 수정
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "edit", method = {RequestMethod.GET, RequestMethod.POST})
    public String facilityInfoEdit(Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        model.addAttribute("houscplxCd", houscplxCd);

        return thisUrl + "/edit";
    }

    /**
     * 단지 관리 > 단지정보 관리 > 시설업체 수정
     * 시스템 및 단지 관리자용 시설업체 수정
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "detailEdit", method = {RequestMethod.GET, RequestMethod.POST})
    public String facilityInfoDetailEdit(Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        String paramFacltBizcoId = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("facltBizcoId")));
        String facltBizcoId = StringUtils.defaultIfEmpty(paramFacltBizcoId, "");

        Integer parseFacltBizcoId = null;

        if (StringUtils.isNotEmpty(facltBizcoId)) {
            pipsEncryptKey = xssUtil.replaceAll(StringUtils.defaultString(pipsEncryptKey));

            parseFacltBizcoId = Integer.parseInt(facltBizcoId);

            FacilityBizco facilityBizco = new FacilityBizco();
            facilityBizco.setFacltBizcoId(parseFacltBizcoId);
            facilityBizco.setEncKey(pipsEncryptKey);

            model.addAttribute("facltBizcoId", facltBizcoId);
            model.addAttribute("houscplxCd", houscplxCd);
            model.addAttribute("facilityInfoCaddrList", facilityService.getFacilityInfoCaddrList(facilityBizco));
        } else if (StringUtils.isEmpty(facltBizcoId)) {
            return thisUrl + "/list";
        }

        model.addAttribute("houscplxCdGoList", StringUtils.defaultIfEmpty(request.getParameter("houscplxCdGoList"), ""));
        model.addAttribute("houscplxCdGoListNm", StringUtils.defaultIfEmpty(request.getParameter("houscplxCdGoListNm"), ""));

        return thisUrl + "/detailEdit";
    }

    /**
     * 단지 관리 > 단지정보 관리 > 시설업체 수정처리
     * 시스템 및 단지 관리자용 시설업체 수정처리
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "editFacilityInfoAction", method = RequestMethod.POST)
    public String editFacilityInfoAction(Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));

        String adminId = StringUtils.defaultIfEmpty(paramSessionUserId, "");
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        String paramFacltBizcoId = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("facltBizcoId")));
        String paramFacltBizcoTpNm = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("facltBizcoTpNm")));
        String paramTwbsNm = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("twbsNm")));
        String paramBizcoNm = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("bizcoNm")));
        String paramConCont = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("conCont")));

        String facltBizcoId = StringUtils.defaultIfEmpty(paramFacltBizcoId, "");
        String facltBizcoTpNm = StringUtils.defaultIfEmpty(paramFacltBizcoTpNm, "");
        String twbsNm = StringUtils.defaultIfEmpty(paramTwbsNm, "");
        String bizcoNm = StringUtils.defaultIfEmpty(paramBizcoNm, "");
        String conCont = StringUtils.defaultIfEmpty(paramConCont, "");

        try {
            FacilityBizco facilityBizco = new FacilityBizco();

            Integer parseFacltBizcoId = Integer.parseInt(facltBizcoId);

            facilityBizco.setFacltBizcoId(parseFacltBizcoId);
            facilityBizco.setFacltBizcoTpNm(facltBizcoTpNm);
            facilityBizco.setTwbsNm(twbsNm);
            facilityBizco.setBizcoNm(bizcoNm);
            facilityBizco.setConCont(conCont);
            facilityBizco.setEditerId(adminId);

            facilityService.updateFacilityInfo(facilityBizco);
        } catch (Exception e) {
            logger.debug("Exception editFacilityInfoAction: " + e.getCause());
        }

        model.addAttribute("houscplxCd", houscplxCd);

        return "redirect:/" + thisUrl + "/list";
    }

    @RequestMapping(value = "deleteFacilityInfoAction", method = RequestMethod.POST)
    public String deleteFacilityInfoAction(Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));

        String adminId = StringUtils.defaultIfEmpty(paramSessionUserId, "");
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        String paramFacltBizcoId = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("facltBizcoId")));
        String facltBizcoId = StringUtils.defaultIfEmpty(paramFacltBizcoId, "");

        try {
            Integer parseFacltBizcoId = Integer.parseInt(facltBizcoId);

            FacilityBizco facilityBizco = new FacilityBizco();
            facilityBizco.setFacltBizcoId(parseFacltBizcoId);
            facilityBizco.setHouscplxCd(houscplxCd);
            facilityBizco.setEditerId(adminId);

            facilityService.deleteFacilityInfo(facilityBizco);
        } catch (Exception e) {
            logger.debug("Exception deleteFacilityInfoAction: " + e.getCause());
        }

        model.addAttribute("houscplxCd", houscplxCd);
        model.addAttribute("facltBizcoId", facltBizcoId);

        return "redirect:/" + thisUrl + "/list";
    }

    /**
     * 단지 관리 > 단지정보 관리 > 시설업체 연락처 정보 수정처리
     * 시스템 및 단지 관리자용 시설업체 연락처 정보 수정처리
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "editFacilityInfoCaddrAction", method = RequestMethod.POST)
    public String editFacilityInfoCaddrAction(Model model, HttpServletRequest request, RedirectAttributes redirect) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));

        String adminId = StringUtils.defaultIfEmpty(paramSessionUserId, "");
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        String paramFacltBizcoId = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("facltBizcoId")));
        String facltBizcoId = StringUtils.defaultIfEmpty(paramFacltBizcoId, "");

        try {
            Integer parseFacltBizcoId = Integer.parseInt(facltBizcoId);

            String facilityInfoCaddrList = xssUtil.replaceTag(StringUtils.defaultString(request.getParameter("facilityInfoCaddrList")));
            JSONArray facilityInfoCaddrJsonArray = new JSONArray(facilityInfoCaddrList);
            int length = facilityInfoCaddrJsonArray.length();

            if (length > 0) {
                FacilityBizco facilityBizco = new FacilityBizco();
                facilityBizco.setFacltBizcoId(parseFacltBizcoId);
                facilityBizco.setHouscplxCd(houscplxCd);
                facilityBizco.setEditerId(adminId);

                List<FacilityBizcoCaddr> addressList = new ArrayList<>();

                for (int j=0; j<length; j++) {
                    JSONObject facilityAddressJsonObject = facilityInfoCaddrJsonArray.getJSONObject(j);
                    FacilityBizcoCaddr facilityBizcoCaddr = new FacilityBizcoCaddr();

                    String facltBizcoCaddrId = StringUtils.defaultIfEmpty(facilityAddressJsonObject.getString("facltBizcoCaddrId"), "");

                    Integer parsedFacltBizcoCaddrId = null;

                    if (StringUtils.isNotEmpty(facltBizcoCaddrId)) {
                        parsedFacltBizcoCaddrId = Integer.parseInt(facltBizcoCaddrId);
                        facilityBizcoCaddr.setFacltBizcoCaddrId(parsedFacltBizcoCaddrId);
                    }

                    String perchrgNm = xssUtil.replaceAll(StringUtils.defaultString(facilityAddressJsonObject.getString("per")));
                    String mphoneNo = xssUtil.replaceAll(StringUtils.defaultString(facilityAddressJsonObject.getString("mp")));
                    String offcPhoneNo = xssUtil.replaceAll(StringUtils.defaultString(facilityAddressJsonObject.getString("offc")));
                    String faxNo = xssUtil.replaceAll(StringUtils.defaultString(facilityAddressJsonObject.getString("fax")));

                    facilityBizcoCaddr.setPerchrgNm(StringUtils.defaultIfEmpty(perchrgNm, ""));
                    facilityBizcoCaddr.setMphoneNo(StringUtils.defaultIfEmpty(mphoneNo, ""));
                    facilityBizcoCaddr.setOffcPhoneNo(StringUtils.defaultIfEmpty(offcPhoneNo, ""));
                    facilityBizcoCaddr.setFaxNo(StringUtils.defaultIfEmpty(faxNo, ""));
                    facilityBizcoCaddr.setMgmTpCd("COMPLEX_MGMT");
                    facilityBizcoCaddr.setCrerId(adminId);
                    facilityBizcoCaddr.setEditerId(adminId);

                    addressList.add(facilityBizcoCaddr);
                }

                facilityService.insertFacilityInfoCaddrInfo(facilityBizco, addressList);
            }
        } catch (Exception e) {
            logger.debug("Exception editFacilityInfoCaddrAction: " + e.getCause());
        }
        model.addAttribute("houscplxCd",houscplxCd);
        model.addAttribute("facltBizcoId",facltBizcoId);
        redirect.addAttribute("houscplxCdGoList", request.getParameter("houscplxCdGoList"));
        redirect.addAttribute("houscplxCdGoListNm", request.getParameter("houscplxCdGoListNm"));
        return "redirect:/" + thisUrl + "/list";
    }

    @RequestMapping(value = "deleteFacilityInfoCaddrAction", method = RequestMethod.POST)
    public String deleteFacilityInfoCaddrAction(Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));

        String adminId = StringUtils.defaultIfEmpty(paramSessionUserId, "");
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        String paramFacltBizcoId = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("facltBizcoId")));
        String paramIsAll = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("isAll")));
        String facltBizcoId = StringUtils.defaultIfEmpty(paramFacltBizcoId, "");
        String isAll = StringUtils.defaultIfEmpty(paramIsAll, "");

        try {
            Integer parseFacltBizcoId = Integer.parseInt(facltBizcoId);

            FacilityBizco facilityBizco = new FacilityBizco();
            facilityBizco.setFacltBizcoId(parseFacltBizcoId);
            facilityBizco.setEditerId(adminId);

            model.addAttribute("houscplxCd", houscplxCd);

            if (isAll.equals("Y")) {
                facilityService.deleteFacilityInfoCaddrInfoAll(facilityBizco);

                return "redirect:/" + thisUrl + "/list";
            } else if (isAll.equals("N")) {
                String paramFacltBizcoCaddrId = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("facltBizcoCaddrId")));
                String facltBizcoCaddrId = StringUtils.defaultIfEmpty(paramFacltBizcoCaddrId, "");
                Integer parseFacltBizcoCaddrId = Integer.parseInt(facltBizcoCaddrId);

                if (StringUtils.isNotEmpty(facltBizcoCaddrId)) {
                    facilityService.deleteFacilityInfoCaddrInfoOne(parseFacltBizcoCaddrId, parseFacltBizcoId, adminId);
                }
            }
        } catch (Exception e) {
            logger.debug("Exception deleteFacilityInfoCaddrAction: " + e.getCause());
        }

        model.addAttribute("facltBizcoId", facltBizcoId);

        return "redirect:/" + thisUrl + "/detailEdit";
    }
}