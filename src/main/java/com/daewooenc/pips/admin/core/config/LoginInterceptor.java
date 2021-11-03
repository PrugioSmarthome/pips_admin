/*****************************************************************************************
 * 세션을 가지고 있는지 체크하고 없으면 로그인 페이지로 이동시키기 위한 클래스
 *
 * 사용 방법:
 * <pre>
 *    스프링 인터셉터에 등록합니다.
 * </pre>
 *
 ******************************************************************************************/
package com.daewooenc.pips.admin.core.config;

import com.daewooenc.pips.admin.core.domain.Consts;
import com.daewooenc.pips.admin.core.domain.authorization.UserGroupAuth;
import com.daewooenc.pips.admin.core.domain.common.SessionUser;
import com.daewooenc.pips.admin.core.service.authorization.MenuService;
import com.daewooenc.pips.admin.core.service.authorization.UserGroupAuthService;
import com.daewooenc.pips.admin.core.util.SessionUtil;
import com.daewooenc.pips.admin.web.common.UserType;
import com.daewooenc.pips.admin.web.util.XSSUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Login 여부를 판별하기 위한 Interceptor
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

	/** The logger. */
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The redirect page. */
	private String redirectPage;

	/** The list no session. */
	private List<String>  listNoSession;

	private @Value("${stomp.host}") String stompHost;
	private @Value("${stomp.port}") String stompPort;
	private @Value("${stomp.path}") String stompPath;
	private @Value("${stomp.user}") String stompUser;
	private @Value("${stomp.message}") String stompMessage;

	@Autowired
	private UserGroupAuthService userGroupAuthService;

	@Autowired
	private MenuService menuService;

	@Autowired
	private XSSUtil xssUtil;

	/**
	 * Gets the redirect page.
	 *
	 * @return the redirect page
	 */
	public String getRedirectPage() {
		return redirectPage;
	}

	/**
	 * Sets the redirect page.
	 *
	 * @param redirectPage the new redirect page
	 */
	public void setRedirectPage(String redirectPage) {
		this.redirectPage = redirectPage;
	}

	/**
	 * Gets the list no session.
	 *
	 * @return the list no session
	 */
	public List<String> getListNoSession() {
		return listNoSession;
	}

	/**
	 * Sets the list no session.
	 *
	 * @param listNoSession the new list no session
	 */
	public void setListNoSession(List<String> listNoSession) {
		this.listNoSession = listNoSession;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		if (logger.isDebugEnabled()) {
			logger.debug("▶▷▶▷▶ PROCESSING REQUEST getPathInfo		[{}]",request.getPathInfo());
			logger.debug("▶▷▶▷▶ PROCESSING REQUEST getQueryString	[{}]", request.getQueryString());
			logger.debug("▶▷▶▷▶ PROCESSING REQUEST getMethod	[{}]", request.getMethod());
		}
//		logger.info("▶▷▶▷▶ ^PROCESSING REQUEST getPathInfo		[{}]",request.getPathInfo());
//		logger.info("▶▷▶▷▶ ^PROCESSING REQUEST getQueryString	[{}]", request.getQueryString());
//		logger.info("▶▷▶▷▶ ^PROCESSING REQUEST getMethod	[{}]", request.getMethod());

		//listNoSession를 호출하면 무조건 통과
		for(int i=0;i<listNoSession.size();i++) {
			if (listNoSession.get(i).trim().endsWith("/*")) {
				String temp = listNoSession.get(i).trim();
//				logger.info("▶▷▶▷▶ ^temp : [{}]", temp);

				temp=temp.substring(0,temp.length()-1);
				if (request.getPathInfo().startsWith(temp)) {

					logger.info("# listNoSession를 호출하면 무조건 통과 => {} : {}", listNoSession.get(i).trim(), request.getPathInfo());
					return true;
				}
			}else if (listNoSession.get(i).trim().equals(request.getPathInfo())) {
				logger.info("listNoSession를 호출하면 무조건 통과 => {} : {}", listNoSession.get(i).trim(), request.getPathInfo());

				return true;
			}
		}

		try {
 			Object obj = SessionUtil.getSessionUser(request);

			//세션 검사 후 없다면 redirectPage로 이동
			if (obj != null) {
				String userId = ((SessionUser) obj).getUserId();

				if (userId==null || userId.isEmpty()) {
					response.sendRedirect(redirectPage);
					return false;
				} else if (userId != null) {
					if (request.getSession().getAttribute(Consts.SessionAttr.USER_MENU) == null) {
						String userGroupId = ((SessionUser) obj).getUserGroupId();

						List<UserGroupAuth> userMenuList = userGroupAuthService.getUserGroupMenu(userGroupId);

						request.getSession().setAttribute(Consts.SessionAttr.USER_MENU, userMenuList);
					}

					if (request.getSession().getAttribute("stompInfo") == null) {
						String webUrl = "ws://" + stompHost + ":" + stompPort + stompPath;
						webUrl = xssUtil.replaceAll(StringUtils.defaultString(webUrl));
						stompMessage = xssUtil.replaceAll(StringUtils.defaultString(stompMessage));
						stompUser = xssUtil.replaceAll(StringUtils.defaultString(stompUser));

						String webMessage = userId + ":" + stompMessage;
						byte[] webMessageBytes = webMessage.getBytes();

						Base64.Encoder encoder = Base64.getEncoder();
						byte[] encodedWebMessageBytes = encoder.encode(webMessageBytes);

						Map<String, Object> stompInfo = new HashMap<>();

						stompInfo.put("webUrl", webUrl);
						stompInfo.put("webUserId", stompUser);
						stompInfo.put("webMessage", new String(encodedWebMessageBytes));

						request.getSession().setAttribute("stompInfo", stompInfo);
					}

					String userGroupName = ((SessionUser) obj).getUserGroupName();

					if (UserType.COMPLEX.getGroupName().equals(userGroupName)) {
						String userPath = "/cm/pips";
						String systemPath = "/cm/system";
						String homenetPath = "/cm/homenet";

						String baseRequestPath = getBasePath(request.getPathInfo(), 3);

						if (userPath.equals(baseRequestPath) || systemPath.equals(baseRequestPath) | homenetPath.equals(baseRequestPath)) {
							response.sendRedirect(redirectPage);
							return false;
						}
					}

					String upMenuNo = menuService.selectUpMenuNo(getBasePath(request.getPathInfo(), 4)+"/", ((SessionUser) obj).getUserGroupId());
					request.getSession().setAttribute(Consts.SessionAttr.UP_MENU_NO, upMenuNo);

					return true;

//					boolean status = checkUserAuth(userId, request.getPathInfo());
//
//					if (BooleanUtils.isTrue(status)) {
//						return true;
//					} else if (BooleanUtils.isFalse(status)) {
//						response.sendRedirect(redirectPage);
//						return false;
//					}
				}
			} else {
				response.sendRedirect(redirectPage);
				return false;
			}
		} catch (Exception e) {
			logger.error("\n", e);
			response.sendRedirect(redirectPage);
			return false;
		}

		//redirectPage로 들어오면 무조건 통과
		if (redirectPage.equals(request.getPathInfo())) {
			return true;
		}

		return true;
	}

	private boolean checkUserAuth(String userId, String requestPath) {
		List<UserGroupAuth> userGroupAuthList = userGroupAuthService.checkUserAuth(userId);

		if (userGroupAuthList.size() == 0) {
			return false;
		}

		for (int i=0; i<userGroupAuthList.size(); i++) {
			UserGroupAuth userGroupAuth = userGroupAuthList.get(i);
			String viewPath = StringUtils.defaultIfEmpty(userGroupAuth.getViewPath(), "");

			String baseRequestPath = getBasePath(requestPath, 4);
			String baseViewPath = "";

			if (StringUtils.isNotEmpty(viewPath)) {
				baseViewPath = getBasePath(viewPath, 4);
			}

			if (baseRequestPath.equals(baseViewPath)) {
				return true;
			} else if (!baseRequestPath.equals(baseViewPath)) {
				String commonPath = "/cm/common";
				String apiPath = "/cm/api";
				baseRequestPath = getBasePath(requestPath, 3);

				if (commonPath.equals(baseRequestPath) || apiPath.equals(baseRequestPath)) {
					return true;
				}
			}
		}

		return false;
	}

	private String getBasePath(String path, int pathLength) {
		String[] splitPath = path.split("/");
		String basePath = "";

		for (int i=1; i<pathLength; i++) {
			basePath += "/" + splitPath[i];
		}

		return basePath;
	}
}
