package com.daewooenc.pips.admin.core.controller.login;

import com.daewooenc.pips.admin.core.domain.Consts;
import com.daewooenc.pips.admin.core.domain.authorization.User;
import com.daewooenc.pips.admin.core.domain.common.SessionUser;
import com.daewooenc.pips.admin.core.service.authorization.UserGroupAuthService;
import com.daewooenc.pips.admin.core.service.login.AuthService;
import com.daewooenc.pips.admin.core.util.SessionUtil;
import com.daewooenc.pips.admin.web.util.XSSUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 사용자 로그인 및 인증 확인 Controller
 *
 * @author : ntels
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *
 * </pre>
 * @since : 2019-07-01
 **/
@Controller
@RequestMapping(value = "/cm")
public class AuthController {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	private @Value("${pips.encrypt.key}") String pipsEncryptKey;

	private final String thisUrl = "cm";

	@SuppressWarnings("unused")
	@Autowired
	private ServletContext servletContext;

	@Autowired
	private AuthService authService;

	@Autowired
	private UserGroupAuthService userGroupAuthService;

	@Autowired
	private XSSUtil xssUtil;

	/**
	 * 로그인 화면. 
	 * 
	 * @param model
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "login", method = {RequestMethod.GET, RequestMethod.POST})
	public String login(Model model, HttpServletRequest request) {
		String resultUrl = thisUrl + "/login";

		logger.debug("start Login Page");
		
		HttpSession session = request.getSession(false);

		if (session == null)
			return resultUrl;

		session.invalidate();

		return resultUrl;
	}

	/**
	 *
	 * 로그인 프로세스.
	 *
	 * @param text_id
	 * @param text_nm
	 * @param request
	 * @return Object
	 */
	@RequestMapping(value = "loginAction", method = RequestMethod.POST)
	public @ResponseBody Object loginAction(@RequestParam(required = false) String text_id,
											@RequestParam(required = false) String text_nm,
							 			    HttpServletRequest request) {
		String paramUserId = xssUtil.replaceAll(StringUtils.defaultString(text_id));
		String paramPassword = xssUtil.replaceAll(StringUtils.defaultString(text_nm));

		String userId = StringUtils.defaultIfEmpty(paramUserId, "");
		String password = StringUtils.defaultIfEmpty(paramPassword, "");
		pipsEncryptKey = xssUtil.replaceAll(StringUtils.defaultString(pipsEncryptKey));

		String resultMsg = authService.login(userId, password, request, pipsEncryptKey);

		return resultMsg;
	}

	/**
	 * 로그아웃 프로세스.
	 *
	 * @param model Model
	 * @param request HttpServletRequest
	 * @return String
	 */
	@RequestMapping(value = "logoutAction", method = RequestMethod.POST)
	public String logoutAction(Model model, HttpServletRequest request) {
		String resultUrl = "redirect:/cm/login";

		SessionUser sessionUser = SessionUtil.getSessionUser(request);
		SessionUtil.removeAttribute(request, Consts.SessionAttr.USER);

		if (sessionUser != null) {
			authService.logout(sessionUser);
			logger.info("==>> logout userId : {}", sessionUser.getUserId());
		}

		return resultUrl;
	}

	@RequestMapping(value = "updateMyInfo", method = {RequestMethod.GET})
	@ResponseBody
	public String updateMyInfo(User user, Model model, HttpServletRequest request, BindingResult result) {
		pipsEncryptKey = xssUtil.replaceAll(StringUtils.defaultString(pipsEncryptKey));

		user.setEncKey(pipsEncryptKey);
		Boolean isExist = authService.updateMyInfo(user);

		if (isExist == true) {
			SessionUser sessionUser = SessionUtil.getSessionUser(request);
			String paramUserName = xssUtil.replaceAll(StringUtils.defaultString(user.getUserName()));
			String paramDeptName = xssUtil.replaceAll(StringUtils.defaultString(user.getDeptName()));
			String paramTelNo = xssUtil.replaceAll(StringUtils.defaultString(user.getTelNo()));

			String userName = StringUtils.defaultIfEmpty(paramUserName, "");
			String deptName = StringUtils.defaultIfEmpty(paramDeptName, "");
			String telNo = StringUtils.defaultIfEmpty(paramTelNo, "");

			if (userName != null && !"".equals(userName)) {
				sessionUser.setUserName(userName);
			}
			if (deptName != null && !"".equals(deptName)) {
				sessionUser.setDeptName(deptName);
			}
			sessionUser.setTelNo(telNo);
			SessionUtil.setSessionUser(request, sessionUser);
		}

		return isExist.toString();
	}

	/**
	 * 약관 및 정책 화면, 랜딩 페이지.
	 *
	 * @param model
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "policy", method = {RequestMethod.GET, RequestMethod.POST})
	public String policy(Model model, HttpServletRequest request) {
		String resultUrl = thisUrl + "/policy";

		logger.info("start policy Page");
		logger.debug("start policy Page");

		return resultUrl;
	}

	@RequestMapping(value = "privacy", method = {RequestMethod.GET, RequestMethod.POST})
	public String privacy(Model model, HttpServletRequest request) {
		String resultUrl = thisUrl + "/privacy";

		logger.info("start privacy Page");
		logger.debug("start privacy Page");

		return resultUrl;
	}

	@RequestMapping(value = "locationPolicy", method = {RequestMethod.GET, RequestMethod.POST})
	public String locationPolicy(Model model, HttpServletRequest request) {
		String resultUrl = thisUrl + "/locationPolicy";

		logger.info("start locationPolicy Page");
		logger.debug("start locationPolicy Page");

		return resultUrl;
	}

	@RequestMapping(value = "faq", method = {RequestMethod.GET, RequestMethod.POST})
	public String faq(Model model, HttpServletRequest request) {
		String resultUrl = thisUrl + "/faq";

		logger.info("start faq Page");
		logger.debug("start faq Page");

		return resultUrl;
	}

	@RequestMapping(value = "landing", method = {RequestMethod.GET, RequestMethod.POST})
	public String landing(Model model, HttpServletRequest request) {
		String resultUrl = thisUrl + "/landing";

		logger.info("start landing Page");
		logger.debug("start landing Page");

		return resultUrl;
	}
}
