package com.daewooenc.pips.admin.core.controller.authorization;

import com.daewooenc.pips.admin.core.util.message.MessageUtil;
import com.daewooenc.pips.admin.core.domain.authorization.User;
import com.daewooenc.pips.admin.core.domain.common.SessionUser;
import com.daewooenc.pips.admin.core.service.authorization.UserGroupService;
import com.daewooenc.pips.admin.core.service.authorization.UserService;
import com.daewooenc.pips.admin.core.util.SessionUtil;
import com.daewooenc.pips.admin.web.domain.dto.device.HouseholdDevice;
import com.daewooenc.pips.admin.web.domain.dto.housingcplx.HousingCplx;
import com.daewooenc.pips.admin.web.util.JsonUtil;
import com.daewooenc.pips.admin.web.util.XSSUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
@RequestMapping(value = "/cm/system/user")
public class UserController{

	/** 로그 출력. */
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private @Value("${pips.encrypt.key}") String pipsEncryptKey;

	/** UserService Autowired. */
	@Autowired
	private UserService userService;

	/** UserGroupService Autowired. */
	@Autowired
	private UserGroupService userGroupService;

	@Autowired
	private XSSUtil xssUtil;

	/** 기본 주소. */
	private String thisUrl = "cm/system/user";

	/**
	 * 인덱스 확면.
	 *
	 * @param user 사용자
	 * @param model Model
	 * @param request HttpServletRequest
	 * @return String
	 */
	@RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
	public String list(User user, Model model, HttpServletRequest request) {
		SessionUser session = SessionUtil.getSessionUser(request);
		String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
		String paramSessionUserGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
		String sessionUserId = StringUtils.defaultIfEmpty(paramSessionUserId, "");
		String sessionuserGroupName = StringUtils.defaultIfEmpty(paramSessionUserGroupName, "");

		pipsEncryptKey = xssUtil.replaceAll(StringUtils.defaultString(pipsEncryptKey));

		user.setEncKey(pipsEncryptKey);
		model.addAttribute("userList", userService.getUserList(user));

		String paramStartCrDt = xssUtil.replaceAll(StringUtils.defaultString(user.getStartCrDt()));
		String paramEndCrDt = xssUtil.replaceAll(StringUtils.defaultString(user.getEndCrDt()));
		String paramUserName = xssUtil.replaceAll(StringUtils.defaultString(user.getUserName()));
		String paramUserGroupId = xssUtil.replaceAll(StringUtils.defaultString(user.getUserGroupId()));
		String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(user.getHouscplxCd()));
		String paramHouscplxNm = xssUtil.replaceAll(StringUtils.defaultString(user.getHouscplxNm()));

		model.addAttribute("startCrDt", paramStartCrDt);
		model.addAttribute("endCrDt", paramEndCrDt);
		model.addAttribute("userName", paramUserName);
		model.addAttribute("userGroupId", paramUserGroupId);
		model.addAttribute("houscplxCd", paramHouscplxCd);
		model.addAttribute("houscplxNm", paramHouscplxNm);

		logger.info("****************************  사용자 정보 조회  ****************************");
		logger.info("User Info [ID : "+sessionUserId+", Group : "+sessionuserGroupName+", remoteIP : "+request.getRemoteHost()+", action : User Search]");
		logger.info("***********************************************************************");

		return thisUrl + "/list";
	}

	@RequestMapping(value = "view", method = {RequestMethod.GET, RequestMethod.POST})
	public String view(User user, Model model, HttpServletRequest request) {

		SessionUser session = SessionUtil.getSessionUser(request);
		String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
		String paramSessionUserGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
		String sessionUserId = StringUtils.defaultIfEmpty(paramSessionUserId, "");
		String sessionuserGroupName = StringUtils.defaultIfEmpty(paramSessionUserGroupName, "");

		String paramUserId = xssUtil.replaceAll(StringUtils.defaultString(user.getUserId()));
		String reqParamUserId = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("userId")));
		String userId = StringUtils.defaultIfEmpty(paramUserId, reqParamUserId);
		pipsEncryptKey = xssUtil.replaceAll(StringUtils.defaultString(pipsEncryptKey));

	    user.setUserId(userId);
		user.setEncKey(pipsEncryptKey);
		model.addAttribute("userInfo", userService.getUserInfo(user));
		model.addAttribute("session_user", session);

		User multiUser = userService.getUserInfo(user);
		if("MULTI_COMPLEX_ADMIN".equals(multiUser.getUserGroupName())){
			model.addAttribute("multiUserInfo", userService.getMultiDanjiList(userId));
		}

		logger.info("****************************  사용자 상세 조회  ****************************");
		logger.info("User Info [ID : "+sessionUserId+", Group : "+sessionuserGroupName+", remoteIP : "+request.getRemoteHost()+", action : User Detail Search]");
		logger.info("***********************************************************************");

		return thisUrl + "/view";
	}

	@RequestMapping(value = "add", method = {RequestMethod.GET, RequestMethod.POST})
	public String add(User user, Model model, HttpServletRequest request) {
		SessionUser session = SessionUtil.getSessionUser(request);
		String paramGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
		String groupName = StringUtils.defaultIfEmpty(paramGroupName, "");

		model.addAttribute("userGroupList",userService.getGroupList(user));
		model.addAttribute("groupName", groupName);

		return thisUrl + "/add";
	}

	@RequestMapping(value = "existUser", method = {RequestMethod.GET})
	@ResponseBody
	public String existUser(User user, Model model, HttpServletRequest request, BindingResult result) {
		Boolean isExist = userService.isExist(user);
		return isExist.toString();
	}

	@RequestMapping(value = "addUserAction", method = {RequestMethod.GET, RequestMethod.POST})
	public String addUserAction(User user, Model model, HttpServletRequest request, BindingResult result) {
		SessionUser session = SessionUtil.getSessionUser(request);
		String resultUrl = "redirect:/cm/system/user/list";

		String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
		String paramSessionUserGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
		String paramUserId = xssUtil.replaceAll(StringUtils.defaultString(user.getUserId()));
		String paramPassword = xssUtil.replaceAll(StringUtils.defaultString(user.getPassword()));
		String paramUserName = xssUtil.replaceAll(StringUtils.defaultString(user.getUserName()));
		String paramUserGroupId = xssUtil.replaceAll(StringUtils.defaultString(user.getUserGroupId()));

		String sessionUserId = StringUtils.defaultIfEmpty(paramSessionUserId, "");
		String sessionuserGroupName = StringUtils.defaultIfEmpty(paramSessionUserGroupName, "");
		String userId = StringUtils.defaultIfEmpty(paramUserId, "");
		String password = StringUtils.defaultIfEmpty(paramPassword, "");
		String userName = StringUtils.defaultIfEmpty(paramUserName, "");
		String userGroupId = StringUtils.defaultIfEmpty(paramUserGroupId, "");

		if (StringUtils.isEmpty(userId)) {
			result.addError(new FieldError("userId", "userId", MessageUtil.getMessage("notEmpty.user.userId")));
		}

		if (StringUtils.isEmpty(password)) {
			result.addError(new FieldError("password", "password", MessageUtil.getMessage("notEmpty.user.password")));
		}

		if (StringUtils.isEmpty(userName)) {
			result.addError(new FieldError("userName", "userName", MessageUtil.getMessage("notEmpty.user.userName")));
		}

		if (StringUtils.isEmpty(userGroupId)) {
			result.addError(new FieldError("userGroupId", "userGroupId", MessageUtil.getMessage("notEmpty.user.userGroupId")));
		}

		if (result.hasErrors()) {
			logger.debug("result.hasErrors()=>{}", result.getFieldError());

			model.addAttribute("addUserAction", null);
			model.addAttribute("resultMsg", "");

			return resultUrl;
		}
		pipsEncryptKey = xssUtil.replaceAll(StringUtils.defaultString(pipsEncryptKey));

		user.setUserId(userId);
		user.setPassword(password);
		user.setUserName(userName);
		user.setUserGroupId(userGroupId);
		user.setEncKey(pipsEncryptKey);
		user.setCrerId(sessionUserId);

		String multiYn = request.getParameter("multiYn");
		if("N".equals(multiYn)) {
			userService.insertUser(user);
		}else{
			String danjiList = user.getHouscplxCd();
			String[] danji = danjiList.split(",");
			user.setHouscplxCd("");

			userService.insertUser(user);

			for(int i=0;i<danji.length;i++){
				user.setHouscplxCd(danji[i]);
				userService.multiInsertUser(user);
			}
		}

		logger.info("****************************  사용자 생성  ****************************");
		logger.info("User Info [ID : "+sessionUserId+", Group : "+sessionuserGroupName+", remoteIP : "+request.getRemoteHost()+", action : User Create]");
		logger.info("***********************************************************************");
		return resultUrl;
	}

	@RequestMapping(value = "edit", method = {RequestMethod.GET, RequestMethod.POST})
	public String edit(User user, Model model, HttpServletRequest request) {
		SessionUser session = SessionUtil.getSessionUser(request);
		String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
		String paramSessionUserGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
		String sessionUserId = StringUtils.defaultIfEmpty(paramSessionUserId, "");
		String sessionuserGroupName = StringUtils.defaultIfEmpty(paramSessionUserGroupName, "");

		String paramUserId = xssUtil.replaceAll(StringUtils.defaultString(user.getUserId()));
		String reqParamUserId = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("userId")));
		String userId = StringUtils.defaultIfEmpty(paramUserId, reqParamUserId);
		pipsEncryptKey = xssUtil.replaceAll(StringUtils.defaultString(pipsEncryptKey));

		user.setEncKey(pipsEncryptKey);
		model.addAttribute("userInfo", userService.getUserInfo(user));
		model.addAttribute("userGroupList",userService.getGroupList(user));
		model.addAttribute("session_user", session);
		model.addAttribute("multiUserYn", "N");

		User multiUser = userService.getUserInfo(user);
		if("MULTI_COMPLEX_ADMIN".equals(multiUser.getUserGroupName())){
			model.addAttribute("multiUserInfo", userService.getMultiDanjiList(userId));
			model.addAttribute("multiUserYn", "Y");
		}

		logger.info("****************************  사용자 정보 조회  ****************************");
		logger.info("User Info [ID : "+sessionUserId+", Group : "+sessionuserGroupName+", remoteIP : "+request.getRemoteHost()+", action : User Search]");
		logger.info("***********************************************************************");
		return thisUrl + "/edit";
	}

	@RequestMapping(value = "editPassword", method = {RequestMethod.GET})
	@ResponseBody
	public String editPassword(User user, Model model, HttpServletRequest request, BindingResult result) {
		Boolean updateYn = userService.updatePassword(user);
		return updateYn.toString();
	}

	@RequestMapping(value = "editUserAction", method = {RequestMethod.GET, RequestMethod.POST})
	public String editUserAction(User user, Model model, HttpServletRequest request, BindingResult result) {
		SessionUser session = SessionUtil.getSessionUser(request);
		String resultUrl = "redirect:/cm/system/user/view";
		String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
		String paramSessionUserGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
		String paramUserId = xssUtil.replaceAll(StringUtils.defaultString(user.getUserId()));
		String paramUserName = xssUtil.replaceAll(StringUtils.defaultString(user.getUserName()));
		String paramUserGroupId = xssUtil.replaceAll(StringUtils.defaultString(user.getUserGroupId()));

		String sessionUserId = StringUtils.defaultIfEmpty(paramSessionUserId, "");
		String sessionuserGroupName = StringUtils.defaultIfEmpty(paramSessionUserGroupName, "");
		String userId = StringUtils.defaultIfEmpty(paramUserId, "");
		String userName = StringUtils.defaultIfEmpty(paramUserName, "");
		String userGroupId = StringUtils.defaultIfEmpty(paramUserGroupId, "");

		if (StringUtils.isEmpty(userId)) {
			result.addError(new FieldError("userId", "userId", MessageUtil.getMessage("notEmpty.user.userId")));
		}

		if (StringUtils.isEmpty(userName)) {
			result.addError(new FieldError("userName", "userName", MessageUtil.getMessage("notEmpty.user.userName")));
		}

		if (StringUtils.isEmpty(userGroupId)) {
			result.addError(new FieldError("userGroupId", "userGroupId", MessageUtil.getMessage("notEmpty.user.userGroupId")));
		}

		if (result.hasErrors()) {
			logger.debug("result.hasErrors()=>{}", result.getFieldError());

			model.addAttribute("addUserAction", null);
			model.addAttribute("resultMsg", "");

			return resultUrl;
		}
		pipsEncryptKey = xssUtil.replaceAll(StringUtils.defaultString(pipsEncryptKey));

		user.setUserId(userId);
		user.setUserName(userName);
		user.setUserGroupId(userGroupId);
		user.setCrerId(sessionUserId);
		user.setEncKey(pipsEncryptKey);

		if("Y".equals(request.getParameter("multiUserYn"))) {
			String danjiArray = request.getParameter("danjiArray");
			String[] danjiList = danjiArray.split(",");
			user.setHouscplxCd("");

			userService.updateUser(user);

			userService.multiDelete(userId);
			for(int i=0;i<danjiList.length;i++){
				user.setHouscplxCd(danjiList[i]);
				userService.multiInsertUser(user);
			}

		}else{
			userService.updateUser(user);
			userService.multiDelete(userId);
		}

        model.addAttribute("userId", user.getUserId());

		logger.info("****************************  사용자 수정  ****************************");
		logger.info("User Info [ID : "+sessionUserId+", Group : "+sessionuserGroupName+", remoteIP : "+request.getRemoteHost()+", action : User Modify]");
		logger.info("***********************************************************************");

		return resultUrl;
	}

	@RequestMapping(value = "deleteUserAction", method = {RequestMethod.GET, RequestMethod.POST})
	public String deleteUserAction(User user, Model model, HttpServletRequest request, BindingResult result) {
		SessionUser session = SessionUtil.getSessionUser(request);

		String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
		String paramSessionUserGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
		String sessionUserId = StringUtils.defaultIfEmpty(paramSessionUserId, "");
		String sessionuserGroupName = StringUtils.defaultIfEmpty(paramSessionUserGroupName, "");

		String resultUrl = "redirect:/cm/system/user/list";

		String paramUserId = xssUtil.replaceAll(StringUtils.defaultString(user.getUserId()));
		String userId = StringUtils.defaultIfEmpty(paramUserId, "");

		if (StringUtils.isEmpty(userId)) {
			result.addError(new FieldError("userId", "userId", MessageUtil.getMessage("notEmpty.user.userId")));
		}

		userService.deleteUser(user);
		logger.info("****************************  사용자 삭제  ****************************");
		logger.info("User Info [ID : "+sessionUserId+", Group : "+sessionuserGroupName+", remoteIP : "+request.getRemoteHost()+", action : User Delete]");
		logger.info("***********************************************************************");
		return resultUrl;
	}

	/**
	 * 사용자 관리 > 사용자 정보 관리 > 사용자 목록
	 * 멀티 단지 관리자 단지 리스트 조회
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "multiDanjiList", method = RequestMethod.POST)
	public List multiDanjiList(User user, HttpServletRequest request) {

		String userId = request.getParameter("userId");

		List multiDanjiList = userService.getMultiDanjiList(userId);

		return multiDanjiList;
	}

	/**
	 * 사용자 관리 > 사용자 정보 관리 > 사용자 상세 > 사용자 수정
	 * 멀티 단지 관리자 단지 리스트 조회
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "selectMultiDanjiList", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/text; charset=utf8")
	public String selectMultiDanjiList(User user, HttpServletRequest request) {

		String userId = request.getParameter("userId");

		String result = "";

		List<User> nMultiList = new ArrayList();

		List<User> multiList = userService.getSelectMultiDanjiList(userId);
		for (int i = 0; i < multiList.size(); i++) {
			User u = multiList.get(i);
			nMultiList.add(u);
		}

		String userJsonArray = JsonUtil.toJsonNotZero(nMultiList);

		result = userJsonArray;

		return result;
	}

}