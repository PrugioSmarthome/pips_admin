package com.daewooenc.pips.admin.core.controller.authorization;

import com.daewooenc.pips.admin.core.domain.common.SessionUser;
import com.daewooenc.pips.admin.core.domain.authorization.Menu;
import com.daewooenc.pips.admin.core.service.authorization.MenuService;
import com.daewooenc.pips.admin.core.util.SessionUtil;
import com.daewooenc.pips.admin.web.common.UserType;
import com.daewooenc.pips.admin.web.util.XSSUtil;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 메뉴관리 관련 Controller
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
@RequestMapping("/cm/system/menu")
public class MenuController {

	/** 로그 출력. */
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/** Ehcache Autowired. */
	@Autowired
	private Ehcache ehcache;

	/** MenuService Autowired. */
	@Autowired
	private MenuService menuService;

	@Autowired
	private XSSUtil xssUtil;
	
	/** 기본 경로. */
	private String thisUrl = "cm/system/menu";

	/**
	 * 메뉴관리 메인화면.
	 *
	 * @param model Model
	 * @return String
	 */
	@RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
	public String list(Model model) {
		String resultUrl = thisUrl + "/list";

		return resultUrl;
	}

	/**
	 * 시스템 관리 > 메뉴 관리 > 메뉴 목록
	 * 시스템 관리자용 메뉴 목록을 조회
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getMenuItemList", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/text; charset=utf8")
	@ResponseBody
	public String getMenuItemList(HttpServletRequest request) {
		SessionUser session = SessionUtil.getSessionUser(request);

		List<Menu> menuList = menuService.getListMenu();

		JSONArray resultArray = new JSONArray();
		JSONArray subMenuResultArray = new JSONArray();
		JSONObject subMenuObject = null;

		if (menuList.size() > 0) {
			for (int i=0; i<menuList.size(); i++) {
				JSONObject upMenuObject = new JSONObject();
				subMenuObject = new JSONObject();
				Menu menu = menuList.get(i);

				int menuNo = menu.getMenuNo();
				int stepNo = menu.getStepNo();
				int upMenuNo = menu.getUpMenuNo();
				int displayOrder = menu.getDisplayOrder();

				String paramMenuName = xssUtil.replaceAll(StringUtils.defaultString(menu.getMenuName()));
				String paramViewPath = xssUtil.replaceAll(StringUtils.defaultString(menu.getViewPath()));
				String paramUserGroupIds = xssUtil.replaceAll(StringUtils.defaultString(menu.getUserGroupId()));

				String menuName = StringUtils.defaultIfEmpty(paramMenuName, "");
				String viewPath = StringUtils.defaultIfEmpty(paramViewPath, "");
				String userGroupIds = StringUtils.defaultIfEmpty(paramUserGroupIds, "");
				String menu_target_type = "NONE";

//				boolean isSystem = userGroupIds.contains(UserType.SYSTEM_ADMIN.getGroupId());
//				boolean isComplex = userGroupIds.contains(UserType.COMPLEX_ADMIN.getGroupId());
//
//				if (BooleanUtils.isTrue(isSystem) && BooleanUtils.isTrue(isComplex)) {
//					menu_target_type = "ALL";
//				} else if (BooleanUtils.isTrue(isSystem) && BooleanUtils.isFalse(isComplex)) {
//					menu_target_type = "SYSTEM";
//				} else if (BooleanUtils.isFalse(isSystem) && BooleanUtils.isTrue(isComplex)) {
//					menu_target_type = "COMPLEX";
//				} else if (BooleanUtils.isFalse(isSystem) && BooleanUtils.isTrue(isComplex)) {
//					menu_target_type = "NONE";
//				}

				if (stepNo == 1) {
					upMenuObject.put("text", menuName);
					upMenuObject.put("menu_name", menuName);
					upMenuObject.put("view_path", viewPath);
					upMenuObject.put("menu_no", menuNo);
					upMenuObject.put("display_order", displayOrder);
					upMenuObject.put("up_menu_no", upMenuNo);
					upMenuObject.put("menu_target_type", menu_target_type);
					resultArray.put(upMenuObject);
				} else if (stepNo == 2) {
					subMenuObject.put("text", menuName);
					subMenuObject.put("menu_name", menuName);
					subMenuObject.put("view_path", viewPath);
					subMenuObject.put("menu_no", menuNo);
					subMenuObject.put("display_order", displayOrder);
					subMenuObject.put("up_menu_no", upMenuNo);
					subMenuObject.put("menu_target_type", menu_target_type);
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
						String paramMenuTargetType = xssUtil.replaceAll(StringUtils.defaultString(currentSubMenuObject.getString("menu_target_type")));

						String subMenuName = StringUtils.defaultIfEmpty(paramSubMenuName, "") ;
						String subViewPath = StringUtils.defaultIfEmpty(paramSubViewPath, "");
						String menuTargetType = StringUtils.defaultIfEmpty(paramMenuTargetType, "") ;

						childrenMenuObject.put("text", subMenuName);
						childrenMenuObject.put("menu_name", subMenuName);
						childrenMenuObject.put("view_path", subViewPath);
						childrenMenuObject.put("menu_no", subMenuNo);
						childrenMenuObject.put("display_order", subDisplayOrder);
						childrenMenuObject.put("up_menu_no", subUpMenuNo);
						childrenMenuObject.put("menu_target_type", menuTargetType);
						subMenuArray.put(childrenMenuObject);
					}
				}
				currentUpMenuObject.put("children", subMenuArray);
			}
		}

		return resultArray.toString();
	}

	/**
	 * 시스템 관리 > 메뉴 관리 > 메뉴 등록처리
	 * 시스템 관리자용 메뉴 등록을 처리
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "addMenuItemAction", method = RequestMethod.POST, produces = "application/text; charset=utf8")
    @ResponseBody
    public String addMenuItemAction(HttpServletRequest request) {
	    SessionUser session = SessionUtil.getSessionUser(request);
	    JSONObject result = new JSONObject();
		boolean insertResult = false;

		String paramMenuTargetType = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("menuTargetType")));
		String paramMenuName = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("menuName")));
		String paramViewPath = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("viewPath")));

	    String menuTargetType = StringUtils.defaultIfEmpty(paramMenuTargetType, "");
	    String menuName = StringUtils.defaultIfEmpty(paramMenuName, "");
	    String viewPath = StringUtils.defaultIfEmpty(paramViewPath, "");

	    if (StringUtils.isNotEmpty(menuTargetType) && StringUtils.isNotEmpty(menuName)) {
            int menuLastNo = menuService.getMenuLastNo();

            int menuNo = menuLastNo + 1;
            int displayOrder = menuNo;
            int upMenuNo = 0;
            int stepNo = 1;
            String isFolder = "true";

            Menu menu = new Menu();
            menu.setMenuNo(menuNo);
            menu.setMenuName(menuName);
            menu.setUpMenuNo(upMenuNo);
            menu.setViewPath(viewPath);
            menu.setStepNo(stepNo);
            menu.setDisplayOrder(displayOrder);
            menu.setIsfolder(isFolder);

            if (menuTargetType.equals("ALL") || menuTargetType.equals("SYSTEM") || menuTargetType.equals("COMPLEX")) {
				try {
					menuService.insertMenuInfo(menu, menuTargetType);
					insertResult = true;
				} catch (Exception e) {
					logger.error("addMenuItemAction Exception: " + e.getCause());
				}
			} else if (menuTargetType.equals("NONE")) {
				insertResult = menuService.insert(menu);
			}

			result.put("status", insertResult);
        } else {
	    	result.put("status", insertResult);
		}

        return result.toString();
    }

    @RequestMapping(value = "editMenuItemInfoAction", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	@ResponseBody
    public String editMenuItemInfoAction(HttpServletRequest request) {
		SessionUser session = SessionUtil.getSessionUser(request);
		JSONObject result = new JSONObject();

		String paramMenuNo = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("menuNo")));
		String paramUpMenuNo = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("upMenuNo")));
		String paramMenuName = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("menuName")));
		String paramViewPath = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("viewPath")));
		String paramMenuTargetType = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("menuTargetType")));

		String menuNo = StringUtils.defaultIfEmpty(paramMenuNo, "");
		String upMenuNo = StringUtils.defaultIfEmpty(paramUpMenuNo, "");
		String menuName = StringUtils.defaultIfEmpty(paramMenuName, "");
		String viewPath = StringUtils.defaultIfEmpty(paramViewPath, "");
		String menuTargetType = StringUtils.defaultIfEmpty(paramMenuTargetType, "");

		if (StringUtils.isNotEmpty(menuNo) && StringUtils.isNotEmpty(upMenuNo) && StringUtils.isNotEmpty(menuName)) {
			try {
				Integer parsedMenuNo = Integer.parseInt(menuNo);
				Integer parsedUpMenuNo = Integer.parseInt(upMenuNo);
				Menu menu = new Menu();
				menu.setMenuNo(parsedMenuNo);
				menu.setUpMenuNo(parsedUpMenuNo);
				menu.setMenuName(menuName);
				menu.setViewPath(viewPath);

				if (parsedUpMenuNo == 0) {
					menuService.updateMenuItemInfoForUpMenu(menu, menuTargetType);
				} else if (parsedUpMenuNo != 0) {
					menuService.updateMenuItemInfoForDownMenu(menu, menuTargetType);
				}

				result.put("status", true);
			} catch (Exception e) {
				logger.error("editMenuItemInfoAction Exception: " + e.getCause());
				result.put("status", false);
			}
		} else {
			result.put("status", false);
		}

		return result.toString();
	}

	/**
	 * 시스템 관리 > 메뉴 관리 > 메뉴 순서를 수정
	 * 시스템 관리자용 메뉴 노출 순서를 수정
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "editMenuItemOrderAction", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	@ResponseBody
	public String editMenuItemOrderAction(HttpServletRequest request) {
		SessionUser session = SessionUtil.getSessionUser(request);
		JSONObject result = new JSONObject();
		String menuData = xssUtil.replaceTag(StringUtils.defaultString(request.getParameter("menuItemList")));
		menuData = StringUtils.defaultIfEmpty(menuData, "");

		JSONArray menuDataJsonArray = null;

		if (StringUtils.isNotEmpty(menuData)) {
			menuDataJsonArray = new JSONArray(menuData);
		}

		if (menuDataJsonArray != null) {
			if (menuDataJsonArray.length() > 0) {
				try {
					Map<String, Object> menuMap = new HashMap<>();
					List<Menu> menuList = new ArrayList<>();
					int displayOrder = 1;

					for (int i=0; i<menuDataJsonArray.length(); i++) {
						JSONObject parentMenuObject = menuDataJsonArray.getJSONObject(i);

						String paramParentMenuName = xssUtil.replaceAll(StringUtils.defaultString(parentMenuObject.getString("menu_name")));
						String paramParentViewPath = xssUtil.replaceAll(StringUtils.defaultString(parentMenuObject.getString("view_path")));
						String parentMenuName = StringUtils.defaultIfEmpty(paramParentMenuName, "");
						String parentViewPath = StringUtils.defaultIfEmpty(paramParentViewPath, "");

						String parentIsFolder = "true";
						int parentMenuNo = parentMenuObject.getInt("menu_no");
						int parentUpMenuNo = parentMenuObject.getInt("up_menu_no");

						Menu parentMenu = new Menu();
						parentMenu.setMenuNo(parentMenuNo);
						parentMenu.setMenuName(parentMenuName);
						parentMenu.setUpMenuNo(parentUpMenuNo);
						parentMenu.setViewPath(parentViewPath);
						parentMenu.setStepNo(1);
						parentMenu.setDisplayOrder(displayOrder);
						parentMenu.setIsfolder(parentIsFolder);

						menuList.add(parentMenu);

						JSONArray childrenJsonArray = new JSONArray();
						childrenJsonArray = parentMenuObject.getJSONArray("children");

						int childrenLength = childrenJsonArray.length();

						if (childrenLength > 0) {
							for (int j=0; j<childrenLength; j++) {
								displayOrder++;

								JSONObject childMenuObject = childrenJsonArray.getJSONObject(j);

								String paramChildMenuName = xssUtil.replaceAll(StringUtils.defaultString(childMenuObject.getString("menu_name")));
								String paramChildViewPath = xssUtil.replaceAll(StringUtils.defaultString(childMenuObject.getString("view_path")));
								String childMenuName = StringUtils.defaultIfEmpty(paramChildMenuName, "");
								String childViewPath = StringUtils.defaultIfEmpty(paramChildViewPath, "");

								String childIsFolder = "false";
								int childMenuNo = childMenuObject.getInt("menu_no");

								Menu childrenMenu = new Menu();
								childrenMenu.setMenuNo(childMenuNo);
								childrenMenu.setMenuName(childMenuName);
								childrenMenu.setUpMenuNo(parentMenuNo);
								childrenMenu.setViewPath(childViewPath);
								childrenMenu.setStepNo(2);
								childrenMenu.setDisplayOrder(displayOrder);
								childrenMenu.setIsfolder(childIsFolder);

								menuList.add(childrenMenu);
							}
						}
						displayOrder++;
					}

					menuMap.put("list", menuList);

					if (menuService.updateMenuItemOrderList(menuMap)) {
						result.put("status", true);
					} else {
						result.put("status", false);
						result.put("code", "EDIT_DATA_ERROR");
					}
				} catch (Exception e) {
					logger.error("editMenuItemAction Exception: " + e.getCause());
					result.put("status", false);
					result.put("code", "EDIT_DATA_ERROR");
				}
			} else if (menuDataJsonArray.length() == 0) {
				result.put("status", false);
				result.put("code", "EDIT_DATA_EMPTY");
			}
		} else if (menuDataJsonArray == null) {
			result.put("status", false);
			result.put("code", "EDIT_DATA_EMPTY");
		}

		return result.toString();
	}

	/**
	 * 시스템 관리 > 메뉴 관리 > 메뉴 삭제
	 * 시스템 관리자용 메뉴 삭제를 처리
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "deleteMenuItemAction", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	@ResponseBody
	public String deleteMenuItemAction(HttpServletRequest request) {
		SessionUser session = SessionUtil.getSessionUser(request);
		JSONObject result = new JSONObject();

		String paramMenuNo = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("menuNo")));
		String paramUpMenuNo = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("upMenuNo")));

		String menuNo = StringUtils.defaultIfEmpty(paramMenuNo, "");
		String upMenuNo = StringUtils.defaultIfEmpty(paramUpMenuNo, "");

		if (StringUtils.isNotEmpty(menuNo) && StringUtils.isNotEmpty(upMenuNo)) {
            Integer parseMenuNo = null;
            Integer parseUpMenuNo = null;

            parseMenuNo = Integer.parseInt(menuNo);
            parseUpMenuNo = Integer.parseInt(upMenuNo);

		    try {
                menuService.deleteMenuInfo(parseMenuNo, parseUpMenuNo);
                result.put("status", true);
            } catch (Exception e) {
                logger.error("deleteMenuItemAction Exception: " + e.getCause());
                result.put("status", false);
            }
        }

		return result.toString();
	}
}
