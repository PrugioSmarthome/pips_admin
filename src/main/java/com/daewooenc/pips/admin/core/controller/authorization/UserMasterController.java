package com.daewooenc.pips.admin.core.controller.authorization;

import com.daewooenc.pips.admin.core.domain.authorization.User;
import com.daewooenc.pips.admin.core.domain.common.SessionUser;
import com.daewooenc.pips.admin.core.service.authorization.UserGroupService;
import com.daewooenc.pips.admin.core.service.authorization.UserService;
import com.daewooenc.pips.admin.core.util.SessionUtil;
import com.daewooenc.pips.admin.core.util.message.MessageUtil;
import com.daewooenc.pips.admin.web.domain.dto.user.PipsUser;
import com.daewooenc.pips.admin.web.service.household.HouseholdService;
import com.daewooenc.pips.admin.web.util.XSSUtil;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 사용자관리 관련 Controller
 *
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-08-05      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-08-05
 **/
@Controller
@RequestMapping(value = "/cm/system/userMaster")
public class UserMasterController {

	/** The logger. */
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private @Value("${pips.encrypt.key}") String pipsEncryptKey;

	/** UserService Autowired. */
	@Autowired
	private UserService userService;

	@Autowired
	private HouseholdService householdService;

	@Autowired
	private XSSUtil xssUtil;

	/** 기본 주소. */
	private String thisUrl = "cm/system/userMaster";

	/**
	 * 사용자 관리 > 마스터 계정 관리 > 마스터 계정 목록
	 * @param pipsUser
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "list", method = {RequestMethod.GET,RequestMethod.POST})
	public String list(PipsUser pipsUser, Model model, HttpServletRequest request) {
		SessionUser session = SessionUtil.getSessionUser(request);

		String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
		String sessionUserId = StringUtils.defaultIfEmpty(paramSessionUserId, "");
		String paramSessionUserGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
		String sessionuserGroupName = StringUtils.defaultIfEmpty(paramSessionUserGroupName, "");

		pipsUser.setMasterYn("Y");

		if(request.getParameter("searchingUserId_") != null || "".equals(request.getParameter("searchingUserId_"))){
			pipsUser.setUserId(request.getParameter("searchingUserId_"));
		}

		model.addAttribute("userList", householdService.getUserList(pipsUser));

		String houscplxNm = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getHouscplxNm()));
		String houscplxCd = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getHouscplxCd()));
		String userTpCd = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getUserTpCd()));
		String userNm = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getUserNm()));
		String dongNo = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getDongNo()));
		String hoseNo = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getHoseNo()));
		String userId = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getUserId()));

		if(!"".equals(pipsUser.getHouscplxCd()) && pipsUser.getHouscplxCd() != null && !"all".equals(pipsUser.getHouscplxCd())){
			model.addAttribute("searchingYn", "Y");
		}

		model.addAttribute("houscplxNm", StringUtils.defaultIfEmpty(houscplxNm, ""));
		model.addAttribute("houscplxCd", StringUtils.defaultIfEmpty(houscplxCd, ""));
		model.addAttribute("userTpCd", StringUtils.defaultIfEmpty(userTpCd,"all"));
		model.addAttribute("userNm", StringUtils.defaultIfEmpty(userNm, ""));
		model.addAttribute("dongNo", StringUtils.defaultIfEmpty(dongNo,"all"));
		model.addAttribute("hoseNo", StringUtils.defaultIfEmpty(hoseNo,"all"));
		model.addAttribute("userId", StringUtils.defaultIfEmpty(userId,""));
		logger.info("****************************  사용자 조회  ****************************");
		logger.info("User Info [ID : "+sessionUserId+", Group : "+sessionuserGroupName+", remoteIP : "+request.getRemoteHost()+", action : User Search]");
		logger.info("***********************************************************************");
		return thisUrl + "/list";
	}
	/**
	 * 사용자 관리 > 마스터 계정 관리 > 마스터 계정 목록 > 마스터 계정 등록(최초 접근시)
	 * @param pipsUser
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "add", method = {RequestMethod.GET,RequestMethod.POST})
	public String add(PipsUser pipsUser, Model model, HttpServletRequest request) {
		SessionUser session = SessionUtil.getSessionUser(request);

		String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
		String sessionUserId = StringUtils.defaultIfEmpty(paramSessionUserId, "");
		String paramSessionUserGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
		String sessionuserGroupName = StringUtils.defaultIfEmpty(paramSessionUserGroupName, "");

		String userTpCd = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getUserTpCd()));
		model.addAttribute("userTpCd", StringUtils.defaultIfEmpty(userTpCd,"all"));

		logger.info("****************************  사용자 조회  ****************************");
		logger.info("User Info [ID : "+sessionUserId+", Group : "+sessionuserGroupName+", remoteIP : "+request.getRemoteHost()+", action : User Search]");
		logger.info("***********************************************************************");
		return thisUrl + "/add";
	}
	/**
	 * 사용자 관리 > 마스터 계정 관리 > 마스터 계정 목록 > 마스터 계정 등록 검색
	 * @param pipsUser
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "search", method = {RequestMethod.GET,RequestMethod.POST})
	public String search(PipsUser pipsUser, Model model, HttpServletRequest request) {
		SessionUser session = SessionUtil.getSessionUser(request);

		String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
		String sessionUserId = StringUtils.defaultIfEmpty(paramSessionUserId, "");
		String paramSessionUserGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
		String sessionuserGroupName = StringUtils.defaultIfEmpty(paramSessionUserGroupName, "");

		if(request.getParameter("searchingUserId_") != null || "".equals(request.getParameter("searchingUserId_"))){
			pipsUser.setUserId(request.getParameter("searchingUserId_"));
		}

		model.addAttribute("userList", householdService.getUserList(pipsUser));

		String houscplxNm = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getHouscplxNm()));
		String houscplxCd = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getHouscplxCd()));
		String userTpCd = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getUserTpCd()));
		String userNm = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getUserNm()));
		String dongNo = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getDongNo()));
		String hoseNo = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getHoseNo()));
		String userId = xssUtil.replaceAll(StringUtils.defaultString(pipsUser.getUserId()));

		if(!"".equals(pipsUser.getHouscplxCd()) && pipsUser.getHouscplxCd() != null && !"all".equals(pipsUser.getHouscplxCd())){
			model.addAttribute("searchingYn", "Y");
		}

		model.addAttribute("houscplxNm", StringUtils.defaultIfEmpty(houscplxNm, ""));
		model.addAttribute("houscplxCd", StringUtils.defaultIfEmpty(houscplxCd, ""));
		model.addAttribute("userTpCd", StringUtils.defaultIfEmpty(userTpCd,"all"));
		model.addAttribute("userNm", StringUtils.defaultIfEmpty(userNm, ""));
		model.addAttribute("dongNo", StringUtils.defaultIfEmpty(dongNo,"all"));
		model.addAttribute("hoseNo", StringUtils.defaultIfEmpty(hoseNo,"all"));
		model.addAttribute("userId", StringUtils.defaultIfEmpty(userId,""));
		logger.info("****************************  사용자 조회  ****************************");
		logger.info("User Info [ID : "+sessionUserId+", Group : "+sessionuserGroupName+", remoteIP : "+request.getRemoteHost()+", action : User Search]");
		logger.info("***********************************************************************");
		return thisUrl + "/add";
	}
	/**
	 * 사용자 관리 > 마스터 계정 관리 > 마스터 계정 목록 > 마스터 계정 등록 insert
	 * @param pipsUser
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "insert", method = {RequestMethod.GET,RequestMethod.POST})
	public String insert(PipsUser pipsUser, Model model, HttpServletRequest request) {
		SessionUser session = SessionUtil.getSessionUser(request);

		String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
		String sessionUserId = StringUtils.defaultIfEmpty(paramSessionUserId, "");
		String paramSessionUserGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
		String sessionuserGroupName = StringUtils.defaultIfEmpty(paramSessionUserGroupName, "");

		String userMasterList = request.getParameter("userMasterList");
 		String[] userList = userMasterList.split(",");

		for(int i=0;i<userList.length;i++){
			int idx = userList[i].indexOf("_");
			String userId = userList[i].substring(0, idx);
			String masterYn = userList[i].substring(idx+1);

			userService.insertUserMaster(userId, masterYn);
		}

		logger.info("****************************  사용자 조회  ****************************");
		logger.info("User Info [ID : "+sessionUserId+", Group : "+sessionuserGroupName+", remoteIP : "+request.getRemoteHost()+", action : User Search]");
		logger.info("***********************************************************************");
		return "redirect:/" + thisUrl + "/list";
	}


	/**
	 * 사용자 관리 > 마스터 계정 관리 > 마스터 계정 목록 > 마스터 계정 상세
	 * @param pipsUser
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "edit", method = {RequestMethod.GET,RequestMethod.POST})
	public String edit(PipsUser pipsUser, Model model, HttpServletRequest request) {
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
		logger.info("****************************  사용자 상세 조회  ****************************");
		logger.info("User Info [ID : "+sessionUserId+", Group : "+sessionuserGroupName+", remoteIP : "+request.getRemoteHost()+", action : User Detail Search]");
		logger.info("***********************************************************************");
		return thisUrl + "/edit";
	}

	/**
	 * 사용자 관리 > 마스터 계정 관리 > 마스터 계정 목록 > 마스터 계정 수정 update
	 * @param pipsUser
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "editUserAction", method = {RequestMethod.GET,RequestMethod.POST})
	public String editUserAction(PipsUser pipsUser, Model model, HttpServletRequest request) {
		SessionUser session = SessionUtil.getSessionUser(request);

		String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
		String sessionUserId = StringUtils.defaultIfEmpty(paramSessionUserId, "");
		String paramSessionUserGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
		String sessionuserGroupName = StringUtils.defaultIfEmpty(paramSessionUserGroupName, "");

		String userId = request.getParameter("userId");
		String masterYn = request.getParameter("masterYn");

		userService.insertUserMaster(userId, masterYn);

		logger.info("****************************  사용자 조회  ****************************");
		logger.info("User Info [ID : "+sessionUserId+", Group : "+sessionuserGroupName+", remoteIP : "+request.getRemoteHost()+", action : User Search]");
		logger.info("***********************************************************************");
		return "redirect:/" + thisUrl + "/list";
	}

}