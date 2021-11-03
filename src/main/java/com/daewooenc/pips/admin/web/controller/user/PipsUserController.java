package com.daewooenc.pips.admin.web.controller.user;

import com.daewooenc.pips.admin.core.domain.common.SessionUser;
import com.daewooenc.pips.admin.core.util.SessionUtil;
import com.daewooenc.pips.admin.core.util.message.MessageUtil;
import com.daewooenc.pips.admin.web.common.UserType;
import com.daewooenc.pips.admin.web.domain.dto.community.NoticeItem;
import com.daewooenc.pips.admin.web.domain.dto.housingcplx.HousingCplx;
import com.daewooenc.pips.admin.web.domain.dto.user.PipsUser;
import com.daewooenc.pips.admin.web.domain.vo.common.HttpResult;
import com.daewooenc.pips.admin.web.service.household.HouseholdService;
import com.daewooenc.pips.admin.web.util.XSSUtil;
import com.daewooenc.pips.admin.web.util.HTTPClientUtil;
import com.daewooenc.pips.admin.web.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 푸르지오 스마트홈 플랫폼 앱 사용자 관리 관련 Controller
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-08-14      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-08-14
 **/
@Controller
@RequestMapping("/cm/pips")
public class PipsUserController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String thisUrl = "cm/pips";

    @Autowired
    private XSSUtil xssUtil;

    private @Value("${pips.serviceServer.url}") String serviceServerURL;
    private @Value("${pips.serviceServer.auth}") String serviceServerAuth;

    @Autowired
    private HouseholdService householdService;

    /**
     * (시스템 관리자) 회원정보 > 회원정보 관리 > 회원정보 목록 (최초 접근 시)
     * @param pipsUser
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "user/list", method = {RequestMethod.GET,RequestMethod.POST})
    public String userList(PipsUser pipsUser, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String sessionUserId = StringUtils.defaultIfEmpty(paramSessionUserId, "");
        String paramSessionUserGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
        String sessionuserGroupName = StringUtils.defaultIfEmpty(paramSessionUserGroupName, "");

        String houscplxNm = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getHouscplxNm()));
        String houscplxCd = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getHouscplxCd()));
        String userTpCd = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getUserTpCd()));
        String apprStsCd = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getApprStsCd()));
        String userNm = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getUserNm()));
        String dongNo = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getDongNo()));
        String hoseNo = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getHoseNo()));

        model.addAttribute("houscplxNm", StringUtils.defaultIfEmpty(houscplxNm, ""));
        model.addAttribute("houscplxCd", StringUtils.defaultIfEmpty(houscplxCd, ""));
        model.addAttribute("userTpCd", StringUtils.defaultIfEmpty(userTpCd,"all"));
        model.addAttribute("apprStsCd", StringUtils.defaultIfEmpty(apprStsCd,"all"));
        model.addAttribute("userNm", StringUtils.defaultIfEmpty(userNm, ""));
        model.addAttribute("dongNo", StringUtils.defaultIfEmpty(dongNo,"all"));
        model.addAttribute("hoseNo", StringUtils.defaultIfEmpty(hoseNo,"all"));
        logger.info("****************************  사용자 조회  ****************************");
        logger.info("User Info [ID : "+sessionUserId+", Group : "+sessionuserGroupName+", remoteIP : "+request.getRemoteHost()+", action : User Search]");
        logger.info("***********************************************************************");
        return thisUrl + "/user/list";
    }

    /**
     * (시스템 관리자) 회원정보 > 회원정보 관리 > 회원정보 목록 (검색 버튼 후)
     * @param pipsUser
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "user/search", method = {RequestMethod.GET,RequestMethod.POST})
    public String userSearch(PipsUser pipsUser, Model model, HttpServletRequest request, @RequestParam(value="houscplxCdGoList", required=false) String houscplxCdGoList, @RequestParam(value="houscplxCdGoListNm", required=false) String houscplxCdGoListNm) {
        SessionUser session = SessionUtil.getSessionUser(request);

        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String sessionUserId = StringUtils.defaultIfEmpty(paramSessionUserId, "");
        String paramSessionUserGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
        String sessionuserGroupName = StringUtils.defaultIfEmpty(paramSessionUserGroupName, "");

        if(!"".equals(houscplxCdGoList) && houscplxCdGoList != null){
            pipsUser.setHouscplxCd(houscplxCdGoList);
            model.addAttribute("houscplxCdGoList", houscplxCdGoList);
            model.addAttribute("houscplxCdGoListNm", houscplxCdGoListNm);
        }

        pipsUser.setMasterYn("N");

        model.addAttribute("userList", householdService.getUserList(pipsUser));

        String houscplxNm = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getHouscplxNm()));
        String houscplxCd = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getHouscplxCd()));
        String userTpCd = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getUserTpCd()));
        String apprStsCd = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getApprStsCd()));
        String userNm = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getUserNm()));
        String dongNo = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getDongNo()));
        String hoseNo = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getHoseNo()));

        model.addAttribute("searchingYn", "Y");

        model.addAttribute("houscplxNm", StringUtils.defaultIfEmpty(houscplxNm, ""));
        model.addAttribute("houscplxCd", StringUtils.defaultIfEmpty(houscplxCd, ""));
        model.addAttribute("userTpCd", StringUtils.defaultIfEmpty(userTpCd,"all"));
        model.addAttribute("apprStsCd", StringUtils.defaultIfEmpty(apprStsCd,"all"));
        model.addAttribute("userNm", StringUtils.defaultIfEmpty(userNm, ""));
        model.addAttribute("dongNo", StringUtils.defaultIfEmpty(dongNo,"all"));
        model.addAttribute("hoseNo", StringUtils.defaultIfEmpty(hoseNo,"all"));
        logger.info("****************************  사용자 조회  ****************************");
        logger.info("User Info [ID : "+sessionUserId+", Group : "+sessionuserGroupName+", remoteIP : "+request.getRemoteHost()+", action : User Search]");
        logger.info("***********************************************************************");
        return thisUrl + "/user/list";
    }

    /**
     * 입주민 탈퇴시 가족대표가 선택되었을경우 해당 가족대표가 혼자인지 체크
     * Ajax 방식
     * @param pipsUser
     * @param request
     * @return
     */
    @RequestMapping(value = "user/usercheck", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/text; charset=utf8")
    @ResponseBody
    public String getHousingCplxList(PipsUser pipsUser, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

        pipsUser.setHouscplxCd(houscplxCd);

        int Count = householdService.getUserCount(pipsUser);

        return Integer.toString(Count);
    }

    /**
     * (시스템 관리자) 회원정보 > 회원정보 관리 > 회원정보 상세
     * @param pipsUser
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "user/edit", method = {RequestMethod.GET,RequestMethod.POST})
    public String userDetail(PipsUser pipsUser, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String sessionUserId = StringUtils.defaultIfEmpty(paramSessionUserId, "");
        String paramSessionUserGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
        String sessionuserGroupName = StringUtils.defaultIfEmpty(paramSessionUserGroupName, "");

        String paramHsholdId = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getHsholdId()));
        String paramUserId = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getUserId()));
        String reqParamUserId = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("userId")));

        String hsholdId = StringUtils.defaultIfEmpty(paramHsholdId,"");
        String userId = StringUtils.defaultIfEmpty(paramUserId, reqParamUserId);
        pipsUser.setUserId(userId);

        if(StringUtils.isEmpty(hsholdId)){
            model.addAttribute("userDetail", householdService.getHouseholdUserDetailForNormalUser(pipsUser));
        }else{
            model.addAttribute("userDetail", householdService.getHouseholdUserDetail(pipsUser));
            model.addAttribute("userFamilyList",householdService.getHouseholdUserDetailFamilyList(pipsUser));
        }
        model.addAttribute("hsholdId", hsholdId);
        model.addAttribute("houscplxCdGoList", StringUtils.defaultIfEmpty(request.getParameter("houscplxCdGoList"), ""));
        model.addAttribute("houscplxCdGoListNm", StringUtils.defaultIfEmpty(request.getParameter("houscplxCdGoListNm"), ""));
        logger.info("****************************  사용자 상세 조회  ****************************");
        logger.info("User Info [ID : "+sessionUserId+", Group : "+sessionuserGroupName+", remoteIP : "+request.getRemoteHost()+", action : User Detail Search]");
        logger.info("***********************************************************************");
        return thisUrl + "/user/edit";
    }

    @RequestMapping(value = "user/editUseYn", method = {RequestMethod.GET})
    @ResponseBody
    public String editUseYn(PipsUser pipsUser, Model model, HttpServletRequest request) {

        logger.debug("editUseYn : " + pipsUser.toString());
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String sessionUserId = StringUtils.defaultIfEmpty(paramSessionUserId, "");
        String paramSessionUserGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
        String sessionuserGroupName = StringUtils.defaultIfEmpty(paramSessionUserGroupName, "");

        JSONObject obj = new JSONObject();
        obj.put("user_id", xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getUserId())));
        obj.put("use_yn", xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getUseYn())));

        serviceServerAuth = xssUtil.replaceAll(StringUtils.defaultString(serviceServerAuth));
        serviceServerURL = xssUtil.replaceAll(StringUtils.defaultString(serviceServerURL));

        String sendUrl = serviceServerURL + "/user/use";
        HTTPClientUtil httpClientUtil = new HTTPClientUtil();
        logger.debug("sendData : " + obj.toString());
        HttpResult httpResult = httpClientUtil.sendData(sendUrl, obj.toString(), serviceServerAuth);
        logger.debug("Result : " + httpResult.getStatus());

        logger.info("****************************  사용자 수정  ****************************");
        logger.info("User Info [ID : "+sessionUserId+", Group : "+sessionuserGroupName+", remoteIP : "+request.getRemoteHost()+", action : User Modify]");
        logger.info("***********************************************************************");

        return httpResult.getStatus();
    }

    @RequestMapping(value = "user/editRepresent", method = {RequestMethod.GET})
    @ResponseBody
    public String editRepresent(PipsUser pipsUser, Model model, HttpServletRequest request) {

        SessionUser session = SessionUtil.getSessionUser(request);

        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String sessionUserId = StringUtils.defaultIfEmpty(paramSessionUserId, "");
        String paramSessionUserGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
        String sessionuserGroupName = StringUtils.defaultIfEmpty(paramSessionUserGroupName, "");

        JSONObject obj = new JSONObject();
        obj.put("prev_user_id", xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("beforeUserId"))));
        obj.put("after_user_id", xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("afterUserId"))));

        serviceServerAuth = xssUtil.replaceAll(StringUtils.defaultString(serviceServerAuth));
        serviceServerURL = xssUtil.replaceAll(StringUtils.defaultString(serviceServerURL));

        String sendUrl = serviceServerURL + "/family/represent";
        HTTPClientUtil httpClientUtil = new HTTPClientUtil();
        logger.debug("sendData : " + obj.toString());
        HttpResult httpResult = httpClientUtil.sendDataForPut(sendUrl, obj.toString(), serviceServerAuth);
        logger.debug("Result : " + httpResult.getStatus());
        logger.info("****************************  사용자 수정  ****************************");
        logger.info("User Info [ID : "+sessionUserId+", Group : "+sessionuserGroupName+", remoteIP : "+request.getRemoteHost()+", action : User Modify]");
        logger.info("***********************************************************************");
        return httpResult.getStatus();
    }

    @RequestMapping(value = "user/editUserAction", method = RequestMethod.POST)
    public String editUserAction(PipsUser pipsUser, Model model, BindingResult result, HttpServletRequest request, RedirectAttributes redirect) {
        logger.debug("editUserAction : " + pipsUser.toString());
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String sessionUserId = StringUtils.defaultIfEmpty(paramSessionUserId, "");
        String paramSessionUserGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
        String sessionuserGroupName = StringUtils.defaultIfEmpty(paramSessionUserGroupName, "");

        JSONObject obj = new JSONObject();
        obj.put("user_id", xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getUserId())));
        obj.put("user_nm", xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getUserNm())));
        obj.put("email_nm", xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getEmailNm())));
        obj.put("mphone_no", xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getMphoneNo())));

        serviceServerAuth = xssUtil.replaceAll(StringUtils.defaultString(serviceServerAuth));
        serviceServerURL = xssUtil.replaceAll(StringUtils.defaultString(serviceServerURL));

        String sendUrl = serviceServerURL + "/user/info";
        HTTPClientUtil httpClientUtil = new HTTPClientUtil();
        logger.debug("sendData : " + obj.toString());
        HttpResult httpResult = httpClientUtil.sendDataForPut(sendUrl, obj.toString(), serviceServerAuth);
        logger.debug("Result : " + httpResult.getStatus());

        model.addAttribute("userId", pipsUser.getUserId());
        logger.info("****************************  사용자 수정  ****************************");
        logger.info("User Info [ID : "+sessionUserId+", Group : "+sessionuserGroupName+", remoteIP : "+request.getRemoteHost()+", action : User Modify]");
        logger.info("***********************************************************************");
        redirect.addAttribute("houscplxCdGoList", request.getParameter("houscplxCdGoList"));
        redirect.addAttribute("houscplxCdGoListNm", request.getParameter("houscplxCdGoListNm"));

        return "redirect:/" + thisUrl + "/user/search";
    }

}