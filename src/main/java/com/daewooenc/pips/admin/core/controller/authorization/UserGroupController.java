package com.daewooenc.pips.admin.core.controller.authorization;

import com.daewooenc.pips.admin.core.domain.authorization.User;
import com.daewooenc.pips.admin.core.util.message.MessageUtil;
import com.daewooenc.pips.admin.core.domain.authorization.UserGroup;
import com.daewooenc.pips.admin.core.domain.authorization.UserGroupAuth;
import com.daewooenc.pips.admin.core.domain.common.SessionUser;
import com.daewooenc.pips.admin.core.service.authorization.UserGroupAuthService;
import com.daewooenc.pips.admin.core.service.authorization.UserGroupService;
import com.daewooenc.pips.admin.core.service.authorization.UserService;
import com.daewooenc.pips.admin.core.util.SessionUtil;
import com.daewooenc.pips.admin.web.common.UserType;
import com.daewooenc.pips.admin.web.util.XSSUtil;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Ehcache;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 사용자 그룹 및 권한 관련 Controller
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
@RequestMapping("/cm/system/authorization")
public class UserGroupController {

	/**
	 * 로그 출력.
	 */
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * userGroupService autowired.
	 */
	@Autowired
	private UserGroupService userGroupService;

	/**
	 * userGroupAuthService autowired.
	 */
	@Autowired
	private UserGroupAuthService userGroupAuthService;

	/**
	 * userService autowired.
	 */
	@Autowired
	private UserService userService;

	/**
	 * ehcache autowired.
	 */
	@Autowired
	private Ehcache ehcache;

	@Autowired
	private XSSUtil xssUtil;

	/**
	 * 클래스의 tiles 기본 경로.
	 */
	private String thisUrl = "cm/system/authorization";

	/** 메뉴목록. */
	List<UserGroupAuth> listMenu;

	/**
	 * 인덱스.
	 *
	 * @param model Model
	 * @param request HttpServletRequest
	 * @return String
	 */
	@RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST} )
	public String index(User user, Model model, HttpServletRequest request) {
		String resultUrl = thisUrl + "/list";
		SessionUser session = SessionUtil.getSessionUser(request);

		String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
		String sessionUserId = StringUtils.defaultIfEmpty(paramSessionUserId, "");
		String paramSessionUserGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
		String sessionuserGroupName = StringUtils.defaultIfEmpty(paramSessionUserGroupName, "");

		model.addAttribute("userGroupList",userService.getGroupList(user));
		logger.info("****************************  그룹 조회  ****************************");
		logger.info("User Info [ID : "+sessionUserId+", Group : "+sessionuserGroupName+", remoteIP : "+request.getRemoteHost()+", action : Group Search]");
		logger.info("***********************************************************************");

		return resultUrl;
	}

	@RequestMapping(value = "authMenuList", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	@ResponseBody
	public String getAuthMenuList(HttpServletRequest request) {
		SessionUser session = SessionUtil.getSessionUser(request);

		String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
		String paramSessionUserGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
		String sessionUserId = StringUtils.defaultIfEmpty(paramSessionUserId, "");
		String sessionuserGroupName = StringUtils.defaultIfEmpty(paramSessionUserGroupName, "");

		String paramGroupId = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("userGroupId")));
		String groupId = StringUtils.defaultIfEmpty(paramGroupId, "");

		JSONArray resultArray = new JSONArray();
		JSONArray subMenuResultArray = new JSONArray();
		JSONObject subMenuObject = null;

		if (StringUtils.isNotEmpty(groupId)) {
			List<UserGroupAuth> userUserMenuList = userGroupAuthService.getUserGroupAuth(groupId);

			for (int i=0; i<userUserMenuList.size(); i++) {
				JSONObject upMenuObject = new JSONObject();
				subMenuObject = new JSONObject();
				UserGroupAuth userGroupAuth = userUserMenuList.get(i);

				int menuNo = userGroupAuth.getMenuNo();
				int stepNo = userGroupAuth.getStepNo();
				int upMenuNo = userGroupAuth.getUpMenuNo();
				int displayOrder = userGroupAuth.getDisplayOrder();
				int subMenuCnt = userGroupAuth.getSubMenuCnt();

				String paramMenuName = xssUtil.replaceAll(StringUtils.defaultString(userGroupAuth.getMenuName()));
				String paramViewPath = xssUtil.replaceAll(StringUtils.defaultString(userGroupAuth.getViewPath()));
				String paramAuthType = xssUtil.replaceAll(StringUtils.defaultString(userGroupAuth.getAuthType()));

				String menuName = StringUtils.defaultIfEmpty(paramMenuName, "");
				String viewPath = StringUtils.defaultIfEmpty(paramViewPath, "");
				String authType = StringUtils.defaultIfEmpty(paramAuthType, "");

				if (stepNo == 1) {
					upMenuObject.put("text", menuName);
					upMenuObject.put("menu_name", menuName);
					upMenuObject.put("view_path", viewPath);
					upMenuObject.put("auth_type", authType);
					upMenuObject.put("menu_no", menuNo);
					upMenuObject.put("display_order", displayOrder);
					upMenuObject.put("up_menu_no", upMenuNo);
					upMenuObject.put("sub_menu_cnt", subMenuCnt);

					resultArray.put(upMenuObject);
				} else if (stepNo == 2) {
					subMenuObject.put("text", menuName);
					subMenuObject.put("menu_name", menuName);
					subMenuObject.put("view_path", viewPath);
					subMenuObject.put("auth_type", authType);
					subMenuObject.put("menu_no", menuNo);
					subMenuObject.put("display_order", displayOrder);
					subMenuObject.put("up_menu_no", upMenuNo);
					subMenuObject.put("sub_menu_cnt", subMenuCnt);

					subMenuResultArray.put(subMenuObject);
				}
			}

			for (int j=0; j<resultArray.length(); j++) {
				JSONArray subMenuArray = new JSONArray();
				JSONObject currentUpMenuObject = resultArray.getJSONObject(j);

				int menuNo = currentUpMenuObject.getInt("menu_no");

				for (int k=0; k<subMenuResultArray.length(); k++) {
					JSONObject currentSubMenuObject = subMenuResultArray.getJSONObject(k);
					int upMenuNo = currentSubMenuObject.getInt("up_menu_no");

					if (menuNo == upMenuNo) {
						JSONObject childrenMenuObject = new JSONObject();

						int subMenuNo = currentSubMenuObject.getInt("menu_no");
						int subUpMenuNo = currentSubMenuObject.getInt("up_menu_no");
						int subDisplayOrder = currentSubMenuObject.getInt("display_order");

						String paramSubMenuName = xssUtil.replaceAll(StringUtils.defaultString(currentSubMenuObject.getString("menu_name")));
						String paramSubViewPath = xssUtil.replaceAll(StringUtils.defaultString(currentSubMenuObject.getString("view_path")));
						String paramSubAuthType = xssUtil.replaceAll(StringUtils.defaultString(currentSubMenuObject.getString("auth_type")));

						String subMenuName = StringUtils.defaultIfEmpty(paramSubMenuName, "");
						String subViewPath = StringUtils.defaultIfEmpty(paramSubViewPath, "");
						String subAuthType = StringUtils.defaultIfEmpty(paramSubAuthType, "");

						childrenMenuObject.put("text", subMenuName);
						childrenMenuObject.put("menu_name", subMenuName);
						childrenMenuObject.put("view_path", subViewPath);
						childrenMenuObject.put("auth_type", subAuthType);
						childrenMenuObject.put("menu_no", subMenuNo);
						childrenMenuObject.put("display_order", subDisplayOrder);
						childrenMenuObject.put("up_menu_no", subUpMenuNo);
						subMenuArray.put(childrenMenuObject);
					}
				}
				currentUpMenuObject.put("children", subMenuArray);
			}
		}
		logger.info("****************************  그룹 조회  ****************************");
		logger.info("User Info [ID : "+sessionUserId+", Group : "+sessionuserGroupName+", remoteIP : "+request.getRemoteHost()+", action : Group Search]");
		logger.info("***********************************************************************");
		return resultArray.toString();
	}

	@RequestMapping(value = "addUserGroupAction", method = RequestMethod.POST)
	public String addUserGroupAction(HttpServletRequest request) {
		SessionUser session = SessionUtil.getSessionUser(request);

		String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
		String paramSessionUserGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
		String sessionUserId = StringUtils.defaultIfEmpty(paramSessionUserId, "");
		String sessionuserGroupName = StringUtils.defaultIfEmpty(paramSessionUserGroupName, "");

		String resultUrl = "redirect:/" + thisUrl + "/list";
		String paramGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
		String groupName = StringUtils.defaultIfEmpty(paramGroupName, "");
		String paramGroupId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupId()));
		String groupId = StringUtils.defaultIfEmpty(paramGroupId, "");

		if (UserType.SYSTEM.getGroupName().equals(groupName)) {
			String userGroupName = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("userGroupName")));
			String description = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("userGroupDescription")));
			String userAuthType = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("userAuthType")));

			userGroupAuthService.insertUserGroup(userGroupName, description, userAuthType);
		}
		logger.info("****************************  그룹 생성  ****************************");
		logger.info("User Info [ID : "+sessionUserId+", Group : "+sessionuserGroupName+", remoteIP : "+request.getRemoteHost()+", action : Group Create]");
		logger.info("***********************************************************************");
		return resultUrl;
	}

	@RequestMapping(value = "editAuthMenuAction", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	@ResponseBody
	public String editAuthMenuAction(HttpServletRequest request) {

		SessionUser session = SessionUtil.getSessionUser(request);

		String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
		String paramSessionUserGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
		String sessionUserId = StringUtils.defaultIfEmpty(paramSessionUserId, "");
		String sessionuserGroupName = StringUtils.defaultIfEmpty(paramSessionUserGroupName, "");

		JSONObject result = new JSONObject();

		String paramAuthData = xssUtil.replaceTag(StringUtils.defaultString(request.getParameter("authMenuList")));
		String paramGroupId = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("groupId")));

		String authData = StringUtils.defaultIfEmpty(paramAuthData, "");
		String groupId = StringUtils.defaultIfEmpty(paramGroupId, "");

		if (StringUtils.isNotEmpty(authData)) {
			JSONArray jsonArrayAuthData = new JSONArray(authData);

			Map<String, Object> authMap = new HashMap<>();
			List<UserGroupAuth> userGroupAuthList = new ArrayList<>();

			for (int i=0; i<jsonArrayAuthData.length(); i++) {
				JSONObject parentMenuObject = jsonArrayAuthData.getJSONObject(i);
				String paramParentAuthType = xssUtil.replaceAll(StringUtils.defaultString(parentMenuObject.getString("auth_type")));

				String parentAuthType = StringUtils.defaultIfEmpty(paramParentAuthType, "");
				int parentMenuNo = parentMenuObject.getInt("menu_no");
				int parentUpMenuNo = parentMenuObject.getInt("up_menu_no");
				int parentDisplayOrder = parentMenuObject.getInt("display_order");
				int parentSubMenuCnt = parentMenuObject.getInt("sub_menu_cnt");

				UserGroupAuth userGroupAuth = new UserGroupAuth();
				userGroupAuth.setAuthType(parentAuthType);
				userGroupAuth.setMenuNo(parentMenuNo);
				userGroupAuth.setUpMenuNo(parentUpMenuNo);
				userGroupAuth.setDisplayOrder(parentDisplayOrder);
				userGroupAuth.setUserGroupId(groupId);

				userGroupAuthList.add(userGroupAuth);

				if (parentSubMenuCnt > 0) {
					JSONArray childrenJsonArray = new JSONArray();
					childrenJsonArray = parentMenuObject.getJSONArray("children");

					int childrenLength = childrenJsonArray.length();

					if (childrenLength > 0) {
						for (int j=0; j<childrenLength; j++) {

							JSONObject childMenuObject = childrenJsonArray.getJSONObject(j);

							String paramChildtAuthType = xssUtil.replaceAll(StringUtils.defaultString(childMenuObject.getString("auth_type")));

							String childAuthType = StringUtils.defaultIfEmpty(paramChildtAuthType, "");
							int childMenuNo = childMenuObject.getInt("menu_no");
							int childUpMenuNo = childMenuObject.getInt("up_menu_no");
							int childDisplayOrder = childMenuObject.getInt("display_order");

							if (parentAuthType.equals("R")) {
								if(childAuthType.equals("A") || childAuthType.equals("R")){
									childAuthType = "R";
								}
							} else if (parentAuthType.equals("N")) {
								childAuthType = "N";
							}

							UserGroupAuth childUserGroupAuth = new UserGroupAuth();
							childUserGroupAuth.setAuthType(childAuthType);
							childUserGroupAuth.setMenuNo(childMenuNo);
							childUserGroupAuth.setUpMenuNo(childUpMenuNo);
							childUserGroupAuth.setDisplayOrder(childDisplayOrder);
							childUserGroupAuth.setUserGroupId(groupId);

							userGroupAuthList.add(childUserGroupAuth);
						}
					}
				}
			}

			authMap.put("list", userGroupAuthList);

			if (userGroupAuthService.updateUserGroupAuth(authMap)) {
				result.put("status", true);
				logger.info("****************************  그룹 권한 수정  ****************************");
				logger.info("User Info [ID : "+sessionUserId+", Group : "+sessionuserGroupName+", remoteIP : "+request.getRemoteHost()+", action : Group Policy Modify]");
				logger.info("***********************************************************************");
			} else {
				result.put("status", false);
			}
		} else {
			result.put("status", false);
		}

		return result.toString();
	}

	@RequestMapping(value = "deleteUserGroupAction", method = RequestMethod.POST)
	public String deleteUserGroupAction(HttpServletRequest request) {
		SessionUser session = SessionUtil.getSessionUser(request);

		String paramSessionUserId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserId()));
		String sessionUserId = StringUtils.defaultIfEmpty(paramSessionUserId, "");

		String resultUrl = "redirect:/" + thisUrl + "/list";
		String paramGroupName = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupName()));
		String groupName = StringUtils.defaultIfEmpty(paramGroupName, "");
		String paramGroupId = xssUtil.replaceAll(StringUtils.defaultString(session.getUserGroupId()));
		String groupId = StringUtils.defaultIfEmpty(paramGroupId, "");

		if (UserType.SYSTEM.getGroupName().equals(groupName)) {
			String reqParamGroupId = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("userGroupId")));
			reqParamGroupId = StringUtils.defaultIfEmpty(reqParamGroupId, "");
			boolean result = userGroupAuthService.checkUserGroup(reqParamGroupId);

			if (BooleanUtils.isFalse(result)) {
				userGroupAuthService.deleteUserGroup(reqParamGroupId);
				logger.info("****************************  그룹 삭제 ****************************");
				logger.info("User Info [ID : "+sessionUserId+", Group : "+groupName+"]");
				logger.info("User Info [ID : "+sessionUserId+", remoteIP : "+request.getRemoteHost()+", action : Group Delete]");
				logger.info("***********************************************************************");
			}
		}

		return resultUrl;
	}
}
