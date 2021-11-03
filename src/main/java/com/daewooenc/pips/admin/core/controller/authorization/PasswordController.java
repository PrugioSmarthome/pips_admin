package com.daewooenc.pips.admin.core.controller.authorization;

import com.daewooenc.pips.admin.core.domain.Consts;
import com.daewooenc.pips.admin.core.domain.authorization.AuthPassword;
import com.daewooenc.pips.admin.core.domain.authorization.User;
import com.daewooenc.pips.admin.core.domain.common.SessionUser;
import com.daewooenc.pips.admin.core.service.authorization.UserGroupAuthService;
import com.daewooenc.pips.admin.core.service.authorization.UserService;
import com.daewooenc.pips.admin.core.util.crypto.Sha256Cipher;
import com.daewooenc.pips.admin.core.util.message.MessageUtil;
import com.daewooenc.pips.admin.core.domain.authorization.ChangePassword;
import com.daewooenc.pips.admin.core.service.authorization.PasswordService;
import com.daewooenc.pips.admin.core.util.SessionUtil;
import com.daewooenc.pips.admin.web.common.UserType;
import com.daewooenc.pips.admin.web.common.WebConsts;
import com.daewooenc.pips.admin.web.domain.dto.housingcplx.HousingCplx;
import com.daewooenc.pips.admin.web.domain.vo.common.HttpResult;
import com.daewooenc.pips.admin.web.service.housingcplx.HousingCplxService;
import com.daewooenc.pips.admin.web.util.HTTPClientUtil;
import com.daewooenc.pips.admin.web.util.JsonUtil;
import com.daewooenc.pips.admin.web.util.SmsUtil;
import com.daewooenc.pips.admin.web.util.XSSUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.BooleanUtils;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 비밀번호 관련 Controller
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
@RequestMapping(value = "/cm/authorization/passwd")
public class PasswordController {

	/** 로그 출력. */
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private @Value("${pips.serviceServer.auth}") String pipsServiceServerAuth;
	private @Value("${pips.serviceServer.url}") String pipsServiceServerUrl;
	private @Value("${pips.serviceServer.path.sms}") String pipsServiceServerSmsPath;

	@Autowired
	private PasswordService passwordService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserGroupAuthService userGroupAuthService;

	@Autowired
	private HousingCplxService housingCplxService;

	@Autowired
	private XSSUtil xssUtil;

	/** 기본 경로. */
	private String thisUrl = "cm/authorization/passwd";

	/**
	 * 로그인 > 비밀번호 변경
	 * @param mode 팝업확인
	 * @param model Model
	 * @return String
	 */
	@RequestMapping(value = "changePassword", method = {RequestMethod.GET, RequestMethod.POST})
	public String changePassword(@RequestParam(required=false) String mode, Model model) {

		return thisUrl + "/changePassword";
	}

	/**
	 * 로그인 > 비밀번호 변경
	 * 비밀번호 변경 처리.
	 * @param changePassword 비밀번호
	 * @param result 파라미터 검증 결과
	 * @param model Model
	 * @param request HttpServletRequest
	 * @return String
	 */
	@RequestMapping(value = "changePasswordAction", method = RequestMethod.POST)
	public String changePasswordAction(@Valid ChangePassword changePassword,
									 BindingResult result,
									 Model model,
									 HttpServletRequest request) {
		String resultUrl = "redirect:/cm/login";
		SessionUser sessionUser =  SessionUtil.getSessionUser(request);

		String paramCurrentPassword = xssUtil.replaceAll(StringUtils.defaultString(changePassword.getCurrentPassword()));
		String paramChangePasswordNew = xssUtil.replaceAll(StringUtils.defaultString(changePassword.getNewPassword()));
		String paramChangePasswordNewRe = xssUtil.replaceAll(StringUtils.defaultString(changePassword.getNewPasswordRe()));
		String paramTelNo = xssUtil.replaceAll(StringUtils.defaultString(changePassword.getTelNo()));
		String paramUserId = xssUtil.replaceAll(StringUtils.defaultString(sessionUser.getUserId()));

		String currentPassword = StringUtils.defaultIfEmpty(paramCurrentPassword, "");
		String changePasswordNew = StringUtils.defaultIfEmpty(paramChangePasswordNew, "");
		String changePasswordNewRe = StringUtils.defaultIfEmpty(paramChangePasswordNewRe, "");
		String telNo = StringUtils.defaultIfEmpty(paramTelNo, "");
		String userId = StringUtils.defaultIfEmpty(paramUserId, "");

		// 현재 비밀번호 미입력
		if (StringUtils.isEmpty(currentPassword)) {
			result.addError(new FieldError("currentPassword", "currentPassword", MessageUtil.getMessage("NotEmpty.changePassword.currentPassword")));
		}

		User userInfo = userService.getUser(userId);

		String prevCurrentPassword = userInfo.getPassword();
		String userInputCurrentPassword = new Sha256Cipher(currentPassword).encrypt();

		// 입력한 현재 비밀번호 맞지 않을 경우
		if (!userInputCurrentPassword.equals(prevCurrentPassword)) {
			result.addError(new FieldError("newPassword", "newPassword", MessageUtil.getMessage("NotEqual.changePassword.currentPassword")));
		}

		// 새 비밀번호 미입력
		if (StringUtils.isEmpty(changePasswordNew)) {
			result.addError(new FieldError("newPassword", "newPassword", MessageUtil.getMessage("NotEmpty.changePassword.newPassword")));
		}

		// 새 비밀번호 확인 미입력
		if (StringUtils.isEmpty(changePasswordNewRe)) {
			result.addError(new FieldError("newPasswordRe", "newPasswordRe", MessageUtil.getMessage("NotEmpty.changePassword.newPasswordRe")));
		}

		if (!"".equals(StringUtils.defaultString(changePasswordNew))) {
			//비밀번호와 비밀번호 확인 일치 확인
			if (!changePasswordNew.equals(changePasswordNewRe)) {
				result.addError(new FieldError("newPassword", "newPassword", MessageUtil.getMessage("NotEqual.changePassword.password_passwordRe")));
			}
		}

		// 휴대폰 번호 미입력
		if (StringUtils.isEmpty(telNo)) {
			result.addError(new FieldError("telNo", "telNo", MessageUtil.getMessage("NotEmpty.telNo.telNo")));
		}

		logger.info("result.hasErrors()=>{}",result.getFieldError());

		//set session userId
		changePassword.setUserId(paramUserId);

		//서버 상에서 파라미터 검증 (JSR303)
		if (result.hasErrors()) {
			resultUrl = thisUrl + "/changePassword";

			logger.debug("result.hasErrors()=>{}", result.getFieldError());

			model.addAttribute("changePassword", changePassword);
			model.addAttribute("resultMsg", MessageUtil.getMessage("msg.input.error"));
			model.addAttribute("mode", changePassword.getMode());

			return resultUrl;
		}

		changePassword.setUserId(paramUserId);
		changePassword.setCurrentPassword(currentPassword);
		changePassword.setNewPassword(changePasswordNew);
		changePassword.setNewPasswordRe(changePasswordNewRe);

		if (!passwordService.updatePassword(changePassword)) {
			result.addError(new FieldError("currentPassword", "currentPassword", MessageUtil.getMessage("NotEqual.changePassword.currentPassword")));

			if (result.hasErrors()) {
				resultUrl = thisUrl + "/changePassword";

				logger.debug("result.hasErrors()=>{}", result.getFieldError());

				model.addAttribute("changePassword", null);
				model.addAttribute("resultMsg", MessageUtil.getMessage("NotEqual.changePassword.currentPassword"));
				model.addAttribute("mode", changePassword.getMode());

				return resultUrl;
			}
		} else {
			SessionUtil.removeAttribute(request, Consts.SessionAttr.USER);
		}

		return resultUrl;
	}

	/**
	 * 로그인 > 비밀번호 재설정_ 휴대폰 본인확인
	 * 비밀번호 재설정 (휴대폰 인증)
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "authPassword", method = {RequestMethod.GET, RequestMethod.POST})
	public String authPassword(HousingCplx housingCplx, Model model, HttpServletRequest request) {
		User user = new User();

		model.addAttribute("modalList", housingCplxService.getHouscplxMetaList(housingCplx));
		model.addAttribute("userGroupList",userService.getGroupList(user));
		request.setAttribute("isAuth", "N");
		return thisUrl + "/authPassword";
	}

	/**
	 * 로그인 > 비밀번호 재설정_ 휴대폰 본인확인
	 * 휴대폰 인증번호 전송
	 * @return
	 */
	@RequestMapping(value = "getAuthAction", method = RequestMethod.POST, produces = "application/json; charset=utf8")
	@ResponseBody
	public String getAuthAction(@Valid AuthPassword authPassword, HttpServletRequest request) {
		String paramGroupName = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("userGroupName")));
		String groupName = StringUtils.defaultIfEmpty(paramGroupName, "");

		JSONObject result = new JSONObject();
		JSONArray params = new JSONArray();
		JSONObject param = new JSONObject();

		if(StringUtils.isEmpty(authPassword.getUserId())) {
			param.put("id", "userId");
			param.put("msg", MessageUtil.getMessage("notEmpty.userId.userId"));
			params.put(param);
		}

		if (UserType.COMPLEX.getGroupName().equals(groupName)) {
			if(StringUtils.isEmpty(authPassword.getHouscplxCd())) {
				param.put("id", "houscplxCd");
				param.put("msg", MessageUtil.getMessage("notEmpty.houscplxCd.houscplxCd"));
				params.put(param);
			}
		}

		if(StringUtils.isEmpty(authPassword.getTelNo())) {
			param.put("id", "telNo");
			param.put("msg", MessageUtil.getMessage("notEmpty.telNo.telNo"));
			params.put(param);
		}

		if (params.length() > 0) {
			result.put("status", false);
			result.put("params", params);

			return result.toString();
		}

		User userInfo = null;

		String paramUserId = xssUtil.replaceAll(StringUtils.defaultString(authPassword.getUserId()));
		String paramTelNo = xssUtil.replaceAll(StringUtils.defaultString(authPassword.getTelNo()));
		String userId = StringUtils.defaultIfEmpty(paramUserId, "");
		String telNo = StringUtils.defaultIfEmpty(paramTelNo, "");

		if (UserType.COMPLEX.getGroupName().equals(groupName)) {
			String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(authPassword.getHouscplxCd()));
			String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, "");

			userInfo = userService.getUserForAuth(userId, houscplxCd, telNo);
		} else if (UserType.SYSTEM.getGroupName().equals(groupName) || UserType.SUB_SYSTEM.getGroupName().equals(groupName)) {
			userInfo = userService.getUserForSysAdminAuth(userId, telNo);
		}

		if (userInfo == null) {
			if (UserType.COMPLEX.getGroupName().equals(groupName)) {
				param.put("id", "houscplxCd");
				param.put("msg", MessageUtil.getMessage("Invalid.houscplxCd.houdcplxCd"));
				params.put(param);

				result.put("params", params);
			}
			result.put("status", false);

			return result.toString();
		}

		try {
			String authCode = String.format("%06d", new SecureRandom().nextInt(1000000));
			authPassword.setAuthCode(authCode);

			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("rnumber", telNo);
			paramMap.put("content", "[대우건설 푸르지오 스마트홈 관리자 플랫폼] 본인확인 인증번호[" + authCode + "]를 화면에 입력해주세요");

			String paramData = JsonUtil.toJson(paramMap);

			HTTPClientUtil httpClientUtil = new HTTPClientUtil();

			String requestUrl = pipsServiceServerUrl + pipsServiceServerSmsPath;

			HttpResult httpResult = httpClientUtil.sendData(requestUrl, paramData, pipsServiceServerAuth);

			if (httpResult.getStatus().equals(String.valueOf(HttpStatus.OK.value()))) {
				logger.debug("sendSMS API Result: OK");
				if (passwordService.updateAuthForReset(authPassword)) {
					AuthPassword updatedAuthPassword = passwordService.selectUserAuth(userId);

					param.put("expireDt", updatedAuthPassword.getAuthExpireDt());
					param.put("msg", "Y");
					params.put(param);

					result.put("status", true);
					result.put("params", params);
				} else {
					param.put("id", "authCode");
					param.put("msg", MessageUtil.getMessage("DataError.authCode.authCode"));
					params.put(param);

					result.put("status", false);
					result.put("params", params);
				}
			} else {
				logger.debug("sendSMS API Result: BAD_REQUEST");
				param.put("id", "authCode");
				param.put("msg", MessageUtil.getMessage("ServerError.authCode.authCode"));
				params.put(param);

				result.put("status", false);
				result.put("params", params);
			}
		} catch (Exception e) {
			logger.debug("Exception getAuthAction : " + e.getCause());

			param.put("id", "authCode");
			param.put("msg", MessageUtil.getMessage("DataError.authCode.authCode"));
			params.put(param);

			result.put("status", false);
			result.put("params", params);
		}

		return result.toString();
	}

	/**
	 * 로그인 > 비밀번호 재설정_ 휴대폰 본인확인
	 * 휴대폰 인증번호 확인
	 * @return
	 */
	@RequestMapping(value = "verifyAuthAction", method = RequestMethod.POST, produces = "application/json; charset=utf8")
	@ResponseBody
	public String verifyAuthAction(@Valid AuthPassword authPassword, HttpServletRequest request) {
		String paramGroupName = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("userGroupName")));
		String groupName = StringUtils.defaultIfEmpty(paramGroupName, "");
		String paramUserId = xssUtil.replaceAll(StringUtils.defaultString(authPassword.getUserId()));
		String paramHouscplxCd = xssUtil.replaceAll(StringUtils.defaultString(authPassword.getHouscplxCd()));
		String paramTelNo = xssUtil.replaceAll(StringUtils.defaultString(authPassword.getTelNo()));
		String paramAuthCode = xssUtil.replaceAll(StringUtils.defaultString(authPassword.getAuthCode()));

		JSONObject result = new JSONObject();
		JSONArray params = new JSONArray();
		JSONObject param = new JSONObject();

		String userId = StringUtils.defaultIfEmpty(paramUserId, "");
		String houscplxCd = StringUtils.defaultIfEmpty(paramHouscplxCd, "");
		String telNo = StringUtils.defaultIfEmpty(paramTelNo, "");
		String authCode = StringUtils.defaultIfEmpty(paramAuthCode, "");

		if(StringUtils.isEmpty(userId)) {
			param.put("id", "userId");
			param.put("msg", MessageUtil.getMessage("notEmpty.userId.userId"));
			params.put(param);
		}

		if (UserType.COMPLEX.getGroupName().equals(groupName)) {
			if(StringUtils.isEmpty(houscplxCd)) {
				param.put("id", "houscplxCd");
				param.put("msg", MessageUtil.getMessage("NotEmpty.houscplxCd.houscplxCd"));
				params.put(param);
			}
		}

		if(StringUtils.isEmpty(telNo)) {
			param.put("id", "telNo");
			param.put("msg", MessageUtil.getMessage("NotEmpty.telNo.telNo"));
			params.put(param);
		}

		if (StringUtils.isEmpty(authCode)) {
			param.put("id", "authCode");
			param.put("msg", MessageUtil.getMessage("NotEmpty.authCode.authCode"));
			params.put(param);
		}

		if (params.length() > 0) {
			result.put("status", false);
			result.put("params", params);

			return result.toString();
		}

		User user = null;

		if (UserType.COMPLEX.getGroupName().equals(groupName)) {
			user = userService.getUserForAuthVerify(userId, houscplxCd, telNo, authCode);
		} else if (UserType.SYSTEM.getGroupName().equals(groupName) || UserType.SUB_SYSTEM.getGroupName().equals(groupName)) {
			user = userService.getUserForSysAdminAuthVerify(userId, telNo, authCode);
		}

		if (user.getUserId() != null) {
			authPassword.setUserId(userId);
			authPassword.setHouscplxCd(houscplxCd);
			authPassword.setTelNo(telNo);
			authPassword.setAuthCode(authCode);

			if (passwordService.updateAuthCompleteForReset(authPassword)) {
				result.put("status", true);
			} else {
				param.put("id", "authCode");
				param.put("msg", MessageUtil.getMessage("DataError.authCode.authCode"));
				params.put(param);

				result.put("status", false);
				result.put("params", params);
			}
		} else {
			param.put("id", "authCode");
			param.put("msg", MessageUtil.getMessage("DataError.authCode.authCode"));
			params.put(param);

			result.put("status", false);
			result.put("params", params);
		}

		return result.toString();
	}

	/**
	 * 로그인 > 비밀번호 재설정_ 휴대폰 본인확인
	 * 비밀번호 재설정 (휴대폰 인증) 처리
	 * @param authPassword
	 * @param result
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "authPasswordAction", method = RequestMethod.POST)
	public String authPasswordAction(@Valid AuthPassword authPassword,
									 BindingResult result,
									 Model model,
									 HttpServletRequest request) {
		String resultUrl = thisUrl + "/authPassword";
		String paramGroupId = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("userGroupId")));
		String paramUserId = xssUtil.replaceAll(StringUtils.defaultString(authPassword.getUserId()));

		String groupId = StringUtils.defaultIfEmpty(paramGroupId, "");
		String userId = StringUtils.defaultIfEmpty(paramUserId, "");

		if(StringUtils.isEmpty(userId)) {
			result.addError(new FieldError("userId", "userId", MessageUtil.getMessage("NotEmpty.userId.userId")));
		}

		if (userService.getUser(userId).getUserId() != null) {
			User userInfo = userService.getUser(userId);

			String isAuth = StringUtils.defaultIfEmpty(userInfo.getIsAuth(), "N");

			if (isAuth.equals("N")) {
				result.addError(new FieldError("authCode", "authCode", MessageUtil.getMessage("NotIdentify.authCode.authCode")));

				logger.debug("result.hasErrors()=>{}", result.getFieldError());

				model.addAttribute("authPassword", authPassword);
				model.addAttribute("resultMsg", MessageUtil.getMessage("msg.input.error"));
			} else {
				String paramGroupName = xssUtil.replaceAll(StringUtils.defaultString(request.getParameter("userGroupName")));
				String groupName = StringUtils.defaultIfEmpty(paramGroupName, "");

				if (UserType.COMPLEX.getGroupName().equals(groupName)) {
					if(StringUtils.isEmpty(authPassword.getHouscplxCd())) {
						result.addError(new FieldError("houscplxCd", "houscplxCd", MessageUtil.getMessage("NotEmpty.houscplxCd.houscplxCd")));
					}

					String prevUserHouscplxCd = userInfo.getHouscplxCd();

					if (!authPassword.getHouscplxCd().equals(prevUserHouscplxCd)) {
						result.addError(new FieldError("houscplxCd", "houscplxCd", MessageUtil.getMessage("Invalid.houscplxCd.houdcplxCd")));
					}
				}

				if(StringUtils.isEmpty(authPassword.getTelNo())) {
					result.addError(new FieldError("telNo", "telNo", MessageUtil.getMessage("NotEmpty.telNo.telNo")));
				}

				String prevTelNo = StringUtils.defaultIfEmpty(userInfo.getTelNo(), "");

				if (!prevTelNo.equals(authPassword.getTelNo()) || StringUtils.isEmpty(authPassword.getTelNo())) {
					result.addError(new FieldError("telNo", "telNo", MessageUtil.getMessage("Invalid.telNo.telNo")));
				}
			}
		}

		if (result.hasErrors()) {
			logger.debug("result.hasErrors()=>{}", result.getFieldError());

			model.addAttribute("authPassword", authPassword);
			model.addAttribute("resultMsg", MessageUtil.getMessage("msg.input.error"));

			return resultUrl;
		} else {
			request.setAttribute("isAuth", "Y");
			request.setAttribute("userId", authPassword.getUserId());

			resultUrl = thisUrl + "/resetPassword";
		}

		return resultUrl;
	}

	/**
	 * 로그인 > 비밀번호 재설정_ 비밀번호 재설정
	 * 비밀번호 재설정
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "resetPassword", method = {RequestMethod.GET, RequestMethod.POST})
	public String resetPassword(Model model, HttpServletRequest request) {
		String resultUrl = thisUrl + "/resetPassword";
		String paramIsAuth = xssUtil.replaceAll(StringUtils.defaultString((String) request.getAttribute("isAuth")));
		String paramUserId = xssUtil.replaceAll(StringUtils.defaultString((String) request.getAttribute("userId")));

		String isAuth = StringUtils.defaultIfEmpty(paramIsAuth, "N");
		String userId = StringUtils.defaultIfEmpty(paramUserId, "");

		if (!isAuth.equals("Y") || StringUtils.isEmpty(userId)) {
			System.out.println("Not Authorized");
			resultUrl = "redirect:/cm/authorization/passwd/authPassword";
		} else {
			request.setAttribute("userId", userId);
		}

		return resultUrl;
	}

	/**
	 * 로그인 > 비밀번호 재설정_ 비밀번호 재설정
	 * 비밀번호 재설정 처리
	 * @param changePassword
	 * @param result
	 * @param model
	 * @param request
	 * @return
	 */
    @RequestMapping(value = "resetPasswordAction", method = RequestMethod.POST)
    public String resetPasswordAction(@Valid ChangePassword changePassword,
                                      BindingResult result,
                                      Model model,
                                      HttpServletRequest request) {
        String resultUrl = thisUrl + "/login";

		String paramChangePasswordNew = xssUtil.replaceAll(StringUtils.defaultString(changePassword.getNewPassword()));
		String paramChangePasswordNewRe = xssUtil.replaceAll(StringUtils.defaultString(changePassword.getNewPasswordRe()));
		String changePasswordNew = StringUtils.defaultIfEmpty(paramChangePasswordNew, "");
		String changePasswordNewRe = StringUtils.defaultIfEmpty(paramChangePasswordNewRe, "");

		// 새 비밀번호 미입력
		if (StringUtils.isEmpty(paramChangePasswordNew)) {
			result.addError(new FieldError("newPassword", "newPassword", MessageUtil.getMessage("NotEmpty.changePassword.newPassword")));
		}

		// 새 비밀번호 확인 미입력
		if (StringUtils.isEmpty(paramChangePasswordNewRe)) {
			result.addError(new FieldError("newPasswordRe", "newPasswordRe", MessageUtil.getMessage("NotEmpty.changePassword.newPasswordRe")));
		}

		if (!"".equals(StringUtils.defaultString(paramChangePasswordNew))) {
			//비밀번호와 비밀번호 확인 일치 확인
			if (!paramChangePasswordNew.equals(paramChangePasswordNewRe)) {
				result.addError(new FieldError("newPassword", "newPassword", MessageUtil.getMessage("NotEqual.changePassword.password_passwordRe")));
			}
		}

		if (result.hasErrors()) {
			resultUrl = thisUrl + "/resetPassword";

			logger.debug("result.hasErrors()=>{}", result.getFieldError());

			model.addAttribute("changePassword", changePassword);
			model.addAttribute("resultMsg", MessageUtil.getMessage("msg.input.error"));

			return resultUrl;
		}

		changePassword.setNewPassword(changePasswordNew);
		changePassword.setNewPasswordRe(changePasswordNewRe);
		if (!passwordService.updatePasswordForReset(changePassword)) {
			if (result.hasErrors()) {
				resultUrl = thisUrl + "/resetPassword";

				logger.debug("result.hasErrors()=>{}", result.getFieldError());

				model.addAttribute("changePassword", changePassword);
				model.addAttribute("resultMsg", MessageUtil.getMessage("msg.input.error"));

				return resultUrl;
			}
		}

		model.addAttribute("changePassword", null);
		model.addAttribute("resultMsg", MessageUtil.getMessage("msg.passwd.changePasswordOK"));
		model.addAttribute("returnMsg", "CHANGE_SUCCESS");

        return "redirect:/cm/login";
    }
}