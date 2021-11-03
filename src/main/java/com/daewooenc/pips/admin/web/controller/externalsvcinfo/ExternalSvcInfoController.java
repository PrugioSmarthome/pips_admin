package com.daewooenc.pips.admin.web.controller.externalsvcinfo;

import com.daewooenc.pips.admin.core.domain.common.SessionUser;
import com.daewooenc.pips.admin.core.domain.configuration.CommonCode;
import com.daewooenc.pips.admin.core.service.configuration.CommonCodeService;
import com.daewooenc.pips.admin.core.util.SessionUtil;
import com.daewooenc.pips.admin.core.util.message.MessageUtil;
import com.daewooenc.pips.admin.web.domain.dto.common.CommCodeDetail;
import com.daewooenc.pips.admin.web.domain.dto.externalsvcinfo.ExternalServiceInfo;
import com.daewooenc.pips.admin.web.domain.dto.housingcplx.HousingCplx;
import com.daewooenc.pips.admin.web.domain.dto.servicelink.ServiceLink;
import com.daewooenc.pips.admin.web.domain.dto.user.PipsUser;
import com.daewooenc.pips.admin.web.service.externalsvcinfo.ExternalSvcInfoService;
import com.daewooenc.pips.admin.web.util.JsonUtil;
import com.daewooenc.pips.admin.web.util.XSSUtil;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 외부연계 관리 관련 Controller
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-09-09      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-09-09
 **/
@Controller
@RequestMapping("/cm/system/externalService")
public class ExternalSvcInfoController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String thisUrl = "cm/system/externalService";

    @Autowired
    private ExternalSvcInfoService externalSvcInfoService;

    @Autowired
    private CommonCodeService commonCodeService;

    @Autowired
    private XSSUtil xssUtil;

    /**
     * 시스템 관리 > 외부연계 관리 > 외부연계 관리 목록
     * 시스템 관리자용 외부연계 관리 목록을 조회
     * @param externalServiceInfo
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    public String externalServiceList(ExternalServiceInfo externalServiceInfo, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        model.addAttribute("serviceList", externalSvcInfoService.getExternalServiceList(externalServiceInfo));

        return thisUrl + "/list";
    }

    /**
     * 시스템 관리 > 외부연계 관리 > 외부연계 관리 서비스명 중복체크 조회
     * Ajax 방식
     * @param externalServiceInfo
     * @param request
     * @return
     */
    @RequestMapping(value = "servicename", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/text; charset=utf8")
    @ResponseBody
    public String getServiceListCount(ExternalServiceInfo externalServiceInfo, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        int count = externalSvcInfoService.getServiceListCount(externalServiceInfo);

        return Integer.toString(count);
    }

    /**
     * 시스템 관리 > 외부연계 관리 > 외부연계 관리 상세
     * 시스템 관리자용 외부연계 상세정보 조회
     * @param externalServiceInfo
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "view", method = {RequestMethod.GET, RequestMethod.POST})
    public String view(ExternalServiceInfo externalServiceInfo, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        model.addAttribute("serviceDetailInfo", externalSvcInfoService.getExternalServiceDetail(externalServiceInfo));

        return thisUrl + "/view";
    }

    /**
     * 시스템 관리 > 외부연계 관리 > 외부연계 정보 등록
     * 시스템 관리자용 외부연계 정보 등록, 수정시에 외부연계 Type 목록 조회
     * Ajax 방식
     * @param housingCplx
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "type/list", method = {RequestMethod.GET, RequestMethod.POST},produces = "application/text; charset=utf8")
    @ResponseBody
    public String serviceLinkTypeList(HousingCplx housingCplx, Model model, HttpServletRequest request) {
        List<CommonCode> commonCodeTypeList = commonCodeService.list("SVC_GRP_TP_CD");
        String json = JsonUtil.toJsonNotZero(commonCodeTypeList);

        return json;
    }

    /**
     * 시스템 관리 > 외부연계 관리 > 외부연계 정보 등록
     * 시스템 관리자용 외부연계 정보 등록, 수정시에 외부연계 서비스 목록 조회
     * Ajax 방식
     * @param housingCplx
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "info/list", method = {RequestMethod.GET, RequestMethod.POST},produces = "application/text; charset=utf8")
    @ResponseBody
    public String serviceLinkTypeInfo(HousingCplx housingCplx, Model model, HttpServletRequest request) {
        List<CommonCode> commonCodeTypeList = commonCodeService.list("SVC_TP_CD");
        String json = JsonUtil.toJsonNotZero(commonCodeTypeList);

        return json;
    }

    /**
     * 시스템 관리 > 외부연계 관리 > 외부연계 관리 등록
     * 시스템 관리자용 외부연계 정보 등록
     * @param externalServiceInfo
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "add", method = {RequestMethod.GET, RequestMethod.POST})
    public String add(ExternalServiceInfo externalServiceInfo, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        return thisUrl + "/add";
    }

    /**
     * 시스템 관리 > 외부연계 관리 > 외부연계 관리 정보 등록
     * 시스템 관리자용 외부연계 정보 등록
     * @param externalServiceInfo
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "addExternalServiceInfoAction", method = {RequestMethod.GET, RequestMethod.POST})
    public String addExternalServiceInfoAction(ExternalServiceInfo externalServiceInfo, BindingResult result,
                                               Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        String resultUrl = "redirect:/cm/system/externalService/list";
        String resultMsg = "";

        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String paramSvcGrpTpCd = xssUtil.replaceAll(StringUtils.defaultString(externalServiceInfo.getSvcGrpTpCd()));
        String paramSvcNm = xssUtil.replaceAll(StringUtils.defaultString(externalServiceInfo.getSvcNm()));
        String paramSvcKeyCd = xssUtil.replaceAll(StringUtils.defaultString(externalServiceInfo.getSvcKeyCd()));
        String paramUrlCont = xssUtil.replaceAll(StringUtils.defaultString(externalServiceInfo.getUrlCont()));

        String adminId = StringUtils.defaultIfEmpty(paramSessionUserId, "");
        String svcGrpTpCd = StringUtils.defaultIfEmpty(paramSvcGrpTpCd, "");
        String svcNm = StringUtils.defaultIfEmpty(paramSvcNm, "");
        String svcKeyCd = StringUtils.defaultIfEmpty(paramSvcKeyCd, "");
        String urlCont = StringUtils.defaultIfEmpty(paramUrlCont, "");
        externalServiceInfo.setCrerId(adminId);

        if (StringUtils.isEmpty(svcGrpTpCd)) {
            result.addError(new FieldError("svcGrpTpCd", "svcGrpTpCd", MessageUtil.getMessage("notEmpty.svcGrpTpCd.svcGrpTpCd")));
        }

        if (StringUtils.isEmpty(svcNm)) {
            result.addError(new FieldError("svcNm", "svcNm", MessageUtil.getMessage("notEmpty.svcNm.svcNm")));
        }

        if (StringUtils.isEmpty(svcKeyCd)) {
            result.addError(new FieldError("svcKeyCd", "svcKeyCd", MessageUtil.getMessage("notEmpty.svcKeyCd.svcKeyCd")));
        }

        if (StringUtils.isEmpty(urlCont)) {
            result.addError(new FieldError("urlCont", "urlCont", MessageUtil.getMessage("notEmpty.urlCont.urlCont")));
        }

        if (result.hasErrors()) {
            logger.debug("result.hasErrors()=>{}", result.getFieldError());
            model.addAttribute("resultMsg", resultMsg);

            return resultUrl;
        }

        externalSvcInfoService.insertExternalServiceInfo(externalServiceInfo);

        return resultUrl;
    }

    /**
     * 시스템 관리 > 외부연계 관리 > 외부연계 관리 수정
     * 시스템 관리자용 외부연계 정보 수정
     * @param externalServiceInfo
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "edit", method = {RequestMethod.GET, RequestMethod.POST})
    public String edit(ExternalServiceInfo externalServiceInfo, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        model.addAttribute("serviceDetailInfo", externalSvcInfoService.getExternalServiceDetail(externalServiceInfo));

        return thisUrl + "/edit";
    }

    /**
     * 시스템 관리 > 외부연계 관리 > 외부연계 관리 정보 수정
     * 시스템 관리자용 외부연계 정보 수정
     * @param externalServiceInfo
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "editExternalServiceInfoAction", method = {RequestMethod.GET, RequestMethod.POST})
    public String editExternalServiceInfoAction(ExternalServiceInfo externalServiceInfo, BindingResult result,
                                               Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        String resultUrl = "redirect:/cm/system/externalService/list";
        String resultMsg = "";

        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String paramSvcGrpTpCd = xssUtil.replaceAll(StringUtils.defaultString(externalServiceInfo.getSvcGrpTpCd()));
        String paramSvcNm = xssUtil.replaceAll(StringUtils.defaultString(externalServiceInfo.getSvcNm()));
        String paramSvcId = xssUtil.replaceAll(StringUtils.defaultString(externalServiceInfo.getSvcId()));
        String paramSvcKeyCd = xssUtil.replaceAll(StringUtils.defaultString(externalServiceInfo.getSvcKeyCd()));
        String paramUrlCont = xssUtil.replaceAll(StringUtils.defaultString(externalServiceInfo.getUrlCont()));

        String adminId = StringUtils.defaultString(paramSessionUserId, "");
        String svcGrpTpCd = StringUtils.defaultIfEmpty(paramSvcGrpTpCd, "");
        String svcNm = StringUtils.defaultIfEmpty(paramSvcNm, "");
        String svcId = StringUtils.defaultIfEmpty(paramSvcId, "");
        String svcKeyCd = StringUtils.defaultIfEmpty(paramSvcKeyCd, "");
        String urlCont = StringUtils.defaultIfEmpty(paramUrlCont, "");
        externalServiceInfo.setEditerId(adminId);

        if (StringUtils.isEmpty(svcGrpTpCd)) {
            result.addError(new FieldError("svcGrpTpCd", "svcGrpTpCd", MessageUtil.getMessage("notEmpty.svcGrpTpCd.svcGrpTpCd")));
        }

        if (StringUtils.isEmpty(svcNm)) {
            result.addError(new FieldError("svcNm", "svcNm", MessageUtil.getMessage("notEmpty.svcNm.svcNm")));
        }

        if (StringUtils.isEmpty(svcId)) {
            result.addError(new FieldError("svcId", "svcId", MessageUtil.getMessage("notEmpty.svcId.svcId")));
        }

        if (StringUtils.isEmpty(svcKeyCd)) {
            result.addError(new FieldError("svcKeyCd", "svcKeyCd", MessageUtil.getMessage("notEmpty.svcKeyCd.svcKeyCd")));
        }

        if (StringUtils.isEmpty(urlCont)) {
            result.addError(new FieldError("urlCont", "urlCont", MessageUtil.getMessage("notEmpty.urlCont.urlCont")));
        }

        if (result.hasErrors()) {
            logger.debug("result.hasErrors()=>{}", result.getFieldError());
            model.addAttribute("resultMsg", resultMsg);

            return resultUrl;
        }

        externalSvcInfoService.updateExternalServiceInfo(externalServiceInfo);

        return resultUrl;
    }

    /**
     * 시스템 관리 > 외부연계 관리 > 외부연계 관리 정보 수정
     * 시스템 관리자용 외부연계 정보 수정
     * @param externalServiceInfo
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "deleteExternalServiceInfoAction", method = {RequestMethod.GET, RequestMethod.POST})
    public String deleteExternalServiceInfoAction(ExternalServiceInfo externalServiceInfo, BindingResult result,
                                                  Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        logger.debug("delete!!!!!! : " + externalServiceInfo.getSvcId());

        String resultUrl = "redirect:/cm/system/externalService/list";
        String resultMsg = "";

        String paramSvcId = xssUtil.replaceAll(StringUtils.defaultString(externalServiceInfo.getSvcId()));
        String svcId = StringUtils.defaultIfEmpty(paramSvcId, "");

        if (StringUtils.isEmpty(svcId)) {
            result.addError(new FieldError("svcId", "svcId", MessageUtil.getMessage("notEmpty.svcId.svcId")));
        }

        if (result.hasErrors()) {
            logger.debug("result.hasErrors()=>{}", result.getFieldError());
            model.addAttribute("resultMsg", resultMsg);

            return resultUrl;
        }

        externalServiceInfo.setEditerId(session.getUserId());
        externalSvcInfoService.deleteExternalServiceInfo(externalServiceInfo);

        return resultUrl;
    }
}