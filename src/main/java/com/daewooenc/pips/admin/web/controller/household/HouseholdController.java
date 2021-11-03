package com.daewooenc.pips.admin.web.controller.household;

import com.daewooenc.pips.admin.core.domain.common.SessionUser;
import com.daewooenc.pips.admin.core.util.SessionUtil;
import com.daewooenc.pips.admin.core.util.message.MessageUtil;
import com.daewooenc.pips.admin.web.common.UserType;
import com.daewooenc.pips.admin.web.domain.dto.device.HouseholdDeviceDetail;
import com.daewooenc.pips.admin.web.domain.dto.user.PipsUser;
import com.daewooenc.pips.admin.web.domain.vo.common.HttpResult;
import com.daewooenc.pips.admin.web.domain.dto.device.HouseholdDevice;
import com.daewooenc.pips.admin.web.service.household.HouseholdService;
import com.daewooenc.pips.admin.web.util.HTTPClientUtil;
import com.daewooenc.pips.admin.web.util.JsonUtil;
import com.daewooenc.pips.admin.web.util.XSSUtil;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 세대관리 관련 Controller
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-07-30      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-07-30
 **/
@Controller
@RequestMapping("/cm/household")
public class HouseholdController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String thisUrl = "cm/household";

    @Autowired
    private HouseholdService householdService;

    @Autowired
    private XSSUtil xssUtil;

    private @Value("${pips.serviceServer.url}") String serviceServerURL;
    private @Value("${pips.serviceServer.auth}") String serviceServerAuth;

    /**
     * 시스템 및 단지 관리자용 검색영역에서 동, 호 선택시 기본정보를 조회
     * Ajax 방식
     * @param pipsUser
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "getHousehold", method = {RequestMethod.GET, RequestMethod.POST},produces = "application/text; charset=utf8")
    @ResponseBody
    public String getHouseholdForSelectBox(PipsUser pipsUser, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
        String groupName = StringUtils.defaultIfEmpty(paramGroupName, "");
        String paramGroupId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupId()));
        String groupId = StringUtils.defaultIfEmpty(paramGroupId, "");

        JSONObject result = new JSONObject();
        JSONArray params = new JSONArray();
        JSONObject param = new JSONObject();

        if (UserType.COMPLEX.getGroupName().equals(groupName)) {
            String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
            String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
            String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

            if (StringUtils.isEmpty(houscplxCd)) {
                param.put("id", "houscplxCd");
                param.put("msg", MessageUtil.getMessage("notEmpty.houscplxCd.houscplxCd"));
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
     * (시스템 or 단지 관리자) 세대구성 신청목록, session에 있는 groupLevel에 따라 리턴하는 페이지가 다르게 처리
         * 시스템 관리자: 회원정보 > 세대구성 신청정보 > 세대구성 신청목록
     * 단지 관리자: 세대정보 > 세대구성 신청정보 > 세대구성 신청목록
     * @param pipsUser
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "group/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String householdGroupList(PipsUser pipsUser, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);

        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String sessionUserId = StringUtils.defaultIfEmpty(paramSessionUserId, "");

        String paramGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
        String groupName = StringUtils.defaultIfEmpty(paramGroupName, "");
        String paramGroupId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupId()));
        String groupId = StringUtils.defaultIfEmpty(paramGroupId, "");

        if (UserType.COMPLEX.getGroupName().equals(groupName)) {
            String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
            String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
            String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);

            pipsUser.setHouscplxCd(houscplxCd);
            model.addAttribute("householderList", householdService.getHouseholderList(pipsUser));
        } else if(UserType.MULTI_COMPLEX.getGroupName().equals(groupName)) {
            pipsUser.setUserId(session.getUserId());
            model.addAttribute("householderList", householdService.getHouseholderMultiList(pipsUser));
        } else {
            model.addAttribute("householderList", householdService.getHouseholderListForSysAdmin(pipsUser));
        }

        if(pipsUser.getHouscplxCd() != ""){
            model.addAttribute("searchingYn", "Y");
        }

        String houscplxCd = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getHouscplxCd()));
        String houscpxlNm = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getHouscplxNm()));
        String dongNo = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getDongNo()));
        String hoseNo = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getHoseNo()));
        String userNm = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getUserNm()));

        model.addAttribute("userId", session.getUserId());
        model.addAttribute("houscplxCd", StringUtils.defaultIfEmpty(houscplxCd, ""));
        model.addAttribute("houscplxNm", StringUtils.defaultIfEmpty(houscpxlNm, ""));
        model.addAttribute("dongNo", StringUtils.defaultIfEmpty(dongNo,"all"));
        model.addAttribute("hoseNo", StringUtils.defaultIfEmpty(hoseNo,"all"));
        model.addAttribute("userNm", StringUtils.defaultIfEmpty(userNm, ""));
        logger.info("****************************  세대구성 신청 조회  ****************************");
        logger.info("User Info [ID : "+sessionUserId+", Group : "+groupName+", remoteIP : "+request.getRemoteHost()+", action : Household Composition Search]");
        logger.info("***********************************************************************");
        return thisUrl + "/group/list";
    }

    /**
     * 시스템 관리자 가족대표 승인/반려 이력 목록
     * 시스템 관리자: 회원 관리 > 가족대표 승인반려 이력 > 가족대표 승인/반려 이력 목록 (최초 접근 시)
     * @param pipsUser
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "groupRecord/list",method = {RequestMethod.GET, RequestMethod.POST})
    public String householdGroupRecordList(PipsUser pipsUser, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String sessionUserId = StringUtils.defaultIfEmpty(paramSessionUserId, "");
        String paramSessionUserGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
        String sessionuserGroupName = StringUtils.defaultIfEmpty(paramSessionUserGroupName, "");

        String houscplxCd = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getHouscplxCd()));
        String houscplxNm = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getHouscplxNm()));
        String dongNo = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getDongNo()));
        String hoseNo = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getHoseNo()));
        String userNm = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getUserNm()));
        String startCrDt = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getStartCrDt()));
        String endCrDt = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getEndCrDt()));
        String apprStsCd = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getApprStsCd()));

        model.addAttribute("houscplxCd", StringUtils.defaultIfEmpty(houscplxCd, ""));
        model.addAttribute("houscplxNm", StringUtils.defaultIfEmpty(houscplxNm, ""));
        model.addAttribute("dongNo", StringUtils.defaultIfEmpty(dongNo,"all"));
        model.addAttribute("hoseNo", StringUtils.defaultIfEmpty(hoseNo,"all"));
        model.addAttribute("userNm", StringUtils.defaultIfEmpty(userNm, ""));
        model.addAttribute("startCrDt", StringUtils.defaultIfEmpty(startCrDt, ""));
        model.addAttribute("endCrDt", StringUtils.defaultIfEmpty(endCrDt, ""));
        model.addAttribute("apprStsCd", StringUtils.defaultIfEmpty(apprStsCd,"all"));
        logger.info("****************************  가족대표 승인/반려 조회  ****************************");
        logger.info("User Info [ID : "+sessionUserId+", Group : "+sessionuserGroupName+", remoteIP : "+request.getRemoteHost()+", action : Approval/Return Search]");
        logger.info("***********************************************************************");
        return thisUrl + "/groupRecord/list";
    }

    /**
     * 시스템 관리자 가족대표 승인/반려 이력 목록
     * 시스템 관리자: 회원 관리 > 가족대표 승인반려 이력 > 가족대표 승인/반려 이력 목록
     * @param pipsUser
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "groupRecord/search",method = {RequestMethod.GET, RequestMethod.POST})
    public String householdGroupRecordSearch(PipsUser pipsUser, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String sessionUserId = StringUtils.defaultIfEmpty(paramSessionUserId, "");
        String paramSessionUserGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
        String sessionuserGroupName = StringUtils.defaultIfEmpty(paramSessionUserGroupName, "");

        model.addAttribute("groupRecord", householdService.getRepresentativeHistory(pipsUser));

        String houscplxCd = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getHouscplxCd()));
        String houscplxNm = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getHouscplxNm()));
        String dongNo = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getDongNo()));
        String hoseNo = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getHoseNo()));
        String userNm = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getUserNm()));
        String startCrDt = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getStartCrDt()));
        String endCrDt = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getEndCrDt()));
        String apprStsCd = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getApprStsCd()));

        if(pipsUser.getHouscplxCd() != ""){
            model.addAttribute("searchingYn", "Y");
        }

        model.addAttribute("houscplxCd", StringUtils.defaultIfEmpty(houscplxCd, ""));
        model.addAttribute("houscplxNm", StringUtils.defaultIfEmpty(houscplxNm, ""));
        model.addAttribute("dongNo", StringUtils.defaultIfEmpty(dongNo,"all"));
        model.addAttribute("hoseNo", StringUtils.defaultIfEmpty(hoseNo,"all"));
        model.addAttribute("userNm", StringUtils.defaultIfEmpty(userNm, ""));
        model.addAttribute("startCrDt", StringUtils.defaultIfEmpty(startCrDt, ""));
        model.addAttribute("endCrDt", StringUtils.defaultIfEmpty(endCrDt, ""));
        model.addAttribute("apprStsCd", StringUtils.defaultIfEmpty(apprStsCd,"all"));
        logger.info("****************************  가족대표 승인/반려 조회  ****************************");
        logger.info("User Info [ID : "+sessionUserId+", Group : "+sessionuserGroupName+", remoteIP : "+request.getRemoteHost()+", action : Approval/Return Search]");
        logger.info("***********************************************************************");
        return thisUrl + "/groupRecord/list";
    }

    /**
     * 단지 관리 > 세대장치 관리 > 세대장치 목록
     * 시스템 및 단지 관리자용 (최초 접근시) 세대장치 목록을 조회
     * @param householdDevice
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "device/list", method = RequestMethod.GET)
    public String householdDeviceList(HouseholdDevice householdDevice, Model model, HttpServletRequest request) {
        // 여러 테이블과의 관계 및 데이터 로딩시 속도를 고려하여 검색시 데이터를 노출하게 구현.
        String houscplxCd = xssUtil.replaceAll(StringUtils.defaultString(householdDevice.getHouscplxCd()));
        String houscplxNm = xssUtil.replaceAll(StringUtils.defaultString(householdDevice.getHouscplxNm()));
        String dongNo = xssUtil.replaceAll(StringUtils.defaultString(householdDevice.getDongNo()));
        String hoseNo = xssUtil.replaceAll(StringUtils.defaultString(householdDevice.getHoseNo()));
        String delYn = xssUtil.replaceAll(StringUtils.defaultString(householdDevice.getDelYn()));

        SessionUser session = SessionUtil.getSessionUser(request);

        String paramGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
        String groupName = StringUtils.defaultIfEmpty(paramGroupName, "");

        if("COMPLEX_ADMIN".equals(groupName)) {
            houscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        }

        model.addAttribute("userId", StringUtils.defaultIfEmpty(session.getUserId(), ""));
        model.addAttribute("houscplxCd", StringUtils.defaultIfEmpty(houscplxCd, ""));
        model.addAttribute("houscplxNm", StringUtils.defaultIfEmpty(houscplxNm, ""));
        model.addAttribute("dongNo", StringUtils.defaultIfEmpty(dongNo,"all"));
        model.addAttribute("hoseNo", StringUtils.defaultIfEmpty(hoseNo,"all"));
        model.addAttribute("delYn", StringUtils.defaultIfEmpty(delYn, "N"));
        return thisUrl + "/device/list";
    }

    /**
     * 단지 관리 > 세대장치 관리 > 세대장치 목록
     * 시스템 및 단지 관리자용 (검색 버튼 후) 세대장치 목록을 조회
     * @param householdDevice
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "device/search", method = RequestMethod.POST)
    public String householdDeviceSearch(HouseholdDevice householdDevice, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramGroupId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupId()));
        String groupId = StringUtils.defaultIfEmpty(paramGroupId, "");
        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);
        String paramGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
        String groupName = StringUtils.defaultIfEmpty(paramGroupName, "");

        householdDevice.setHouscplxCd(houscplxCd);

        if(householdDevice.getHouscplxCd() != ""){
            model.addAttribute("searchingYn", "Y");
        }

        String houscplxNm = xssUtil.replaceAll(StringUtils.defaultString(householdDevice.getHouscplxNm()));
        String dongNo = xssUtil.replaceAll(StringUtils.defaultString(householdDevice.getDongNo()));
        String hoseNo = xssUtil.replaceAll(StringUtils.defaultString(householdDevice.getHoseNo()));
        String delYn = xssUtil.replaceAll(StringUtils.defaultString(householdDevice.getDelYn()));

        model.addAttribute("userId", StringUtils.defaultIfEmpty(session.getUserId(), ""));

        if("MULTI_COMPLEX_ADMIN".equals(groupName)){
            householdDevice.setUserId(session.getUserId());
            model.addAttribute("householdDeviceList", householdService.getMultiHouseholdDeviceList(householdDevice));
        }else{
            model.addAttribute("householdDeviceList", householdService.getHouseholdDeviceList(householdDevice));
        }

        if("COMPLEX_ADMIN".equals(groupName)){
            houscplxNm = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxNm()));
        }

        model.addAttribute("houscplxCd", StringUtils.defaultIfEmpty(houscplxCd, ""));
        model.addAttribute("houscplxNm", StringUtils.defaultIfEmpty(houscplxNm, ""));
        model.addAttribute("dongNo", StringUtils.defaultIfEmpty(dongNo,"all"));
        model.addAttribute("hoseNo", StringUtils.defaultIfEmpty(hoseNo,"all"));
        model.addAttribute("delYn", StringUtils.defaultIfEmpty(delYn, ""));

        return thisUrl + "/device/list";
    }

    /**
     * 단지 관리 > 세대장치 관리 > 세대장치 목록
     * 시스템 및 단지 관리자용 (동기화 버튼) 홈넷 아이디 조회
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "device/hmnetIdSearch", method = RequestMethod.POST)
    public String hmnetIdSearch(HouseholdDevice householdDevice, HttpServletRequest request) {

        String houscplxCd = request.getParameter("houscplxCd");

        String hmnetId = householdService.getHouseholdHmnetId(houscplxCd);

        return hmnetId;
    }

    /**
     * 단지 관리 > 세대장치 관리 > 세대장치 목록 > 세대장치 관리 상세
     * 시스템 관리자용 세대장치 상세목록을 조회
     * @param householdDeviceDetail
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "device/view", method = RequestMethod.POST)
    public String householdDeviceView(HouseholdDeviceDetail householdDeviceDetail, Model model, HttpServletRequest request) {
        model.addAttribute("householdDeviceDetail", householdService.getHouseholdDeviceDetail(householdDeviceDetail));

        return thisUrl + "/device/view";
    }

    /**
     * (단지 관리자) 세대정보 > 세대구성 신청정보 > 세대구성 신청목록
     * @param pipsUser
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "user/list",method = {RequestMethod.GET, RequestMethod.POST})
    public String userList(PipsUser pipsUser, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String sessionUserId = StringUtils.defaultIfEmpty(paramSessionUserId, "");
        String paramSessionUserGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
        String sessionuserGroupName = StringUtils.defaultIfEmpty(paramSessionUserGroupName, "");
        String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxCd()));
        String reqParamHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxCd")));
        String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, reqParamHouscplxCd);
        String paramHouscplxNm = xssUtil.replaceAll(StringUtils.defaultString(session.getHouscplxNm()));
        String reqParamHouscplxNm = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("houscplxNm")));
        String houscplxNm = StringUtils.defaultIfEmpty(paramHouscplxNm, reqParamHouscplxNm);
        String paramGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
        String groupName = StringUtils.defaultIfEmpty(paramGroupName, "");
        pipsUser.setHouscplxCd(houscplxCd);

        if(pipsUser.getHouscplxCd() != ""){
            model.addAttribute("searchingYn", "Y");
        }

        String dongNo = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getDongNo()));
        String hoseNo = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getHoseNo()));
        String userNm = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getUserNm()));

        if (UserType.COMPLEX.getGroupName().equals(groupName)) {
            model.addAttribute("pipsUserList", householdService.getHouseholdUserList(pipsUser));
        } else if(UserType.MULTI_COMPLEX.getGroupName().equals(groupName)) {
            pipsUser.setUserId(session.getUserId());
            model.addAttribute("pipsUserList", householdService.getMultiHouseholdUserList(pipsUser));
        }

        model.addAttribute("userId", session.getUserId());
        model.addAttribute("userGroupName", groupName);
        model.addAttribute("dongNo", StringUtils.defaultIfEmpty(dongNo,"all"));
        model.addAttribute("hoseNo", StringUtils.defaultIfEmpty(hoseNo,"all"));
        model.addAttribute("userNm", StringUtils.defaultIfEmpty(userNm, ""));
        model.addAttribute("houscplxCd", StringUtils.defaultIfEmpty(houscplxCd, ""));
        model.addAttribute("houscplxNm", StringUtils.defaultIfEmpty(houscplxNm, ""));
        logger.info("****************************  세대구성 신청 정보 조회  ****************************");
        logger.info("User Info [ID : "+sessionUserId+", Group : "+sessionuserGroupName+"]");
        logger.info("User Info [ID : "+sessionUserId+", Group : "+sessionuserGroupName+", remoteIP : "+request.getRemoteHost()+", action : Household Composition Search]");
        logger.info("***********************************************************************");
        return thisUrl + "/user/list";
    }

    @RequestMapping(value = "user/edit",method = {RequestMethod.GET, RequestMethod.POST})
    public String UserDetail(PipsUser pipsUser, Model model, HttpServletRequest request) {
        SessionUser session = SessionUtil.getSessionUser(request);
        String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
        String sessionUserId = StringUtils.defaultIfEmpty(paramSessionUserId, "");
        String paramSessionUserGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
        String sessionuserGroupName = StringUtils.defaultIfEmpty(paramSessionUserGroupName, "");
        model.addAttribute("userDetail", householdService.getHouseholdUserDetail(pipsUser));
        model.addAttribute("familyList",householdService.getHouseholdUserDetailFamilyList(pipsUser));
        logger.info("****************************  사용자 정보 상세 조회  ****************************");
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

        String userId = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getUserId()));
        String useYn = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getUseYn()));

        JSONObject obj = new JSONObject();
        obj.put("user_id", StringUtils.defaultIfEmpty(userId, ""));
        obj.put("use_yn", StringUtils.defaultIfEmpty(useYn, ""));

        serviceServerAuth = xssUtil.replaceAll(StringUtils.defaultString(serviceServerAuth));
        serviceServerURL = xssUtil.replaceAll(StringUtils.defaultString(serviceServerURL));

        String sendUrl = serviceServerURL + "/user/use";
        HTTPClientUtil httpClientUtil = new HTTPClientUtil();
        logger.debug("sendData : " + obj.toString());
        HttpResult httpResult = httpClientUtil.sendData(sendUrl, obj.toString(), serviceServerAuth);
        logger.debug("Result : " + httpResult.getStatus());
        logger.info("****************************  사용자 정보 수정  ****************************");
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
        String beforeUserId = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("beforeUserId")));
        String afterUserId = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("afterUserId")));

        JSONObject obj = new JSONObject();
        obj.put("prev_user_id", StringUtils.defaultIfEmpty(beforeUserId, ""));
        obj.put("after_user_id", StringUtils.defaultIfEmpty(afterUserId, ""));

        serviceServerAuth = xssUtil.replaceAll(StringUtils.defaultString(serviceServerAuth));
        serviceServerURL = xssUtil.replaceAll(StringUtils.defaultString(serviceServerURL));

        String sendUrl = serviceServerURL + "/family/represent";
        HTTPClientUtil httpClientUtil = new HTTPClientUtil();
        logger.debug("sendData : " + obj.toString());
        HttpResult httpResult = httpClientUtil.sendDataForPut(sendUrl, obj.toString(), serviceServerAuth);
        logger.debug("Result : " + httpResult.getStatus());
        logger.info("****************************  사용자 정보 수정  ****************************");
        logger.info("User Info [ID : "+sessionUserId+", Group : "+sessionuserGroupName+", remoteIP : "+request.getRemoteHost()+", action : User Modify]");
        logger.info("***********************************************************************");
        return httpResult.getStatus();
    }

    /**
     * 세대 관리 > 세대정보 관리 > 세대정보 목록 > 입주민탈퇴 가능 여부 체크
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "user/secessionCheck", method = RequestMethod.POST)
    public String secessionCheck(PipsUser pipsUser, HttpServletRequest request) {

        String hsholdId = request.getParameter("hsholdId");

        String flag = "";

        int cnt = householdService.getHouseholdSecessionCheck(hsholdId);
        if(cnt == 0){
            flag = "ok";
        }else{
            flag = "no";
        }

        return flag;
    }

}