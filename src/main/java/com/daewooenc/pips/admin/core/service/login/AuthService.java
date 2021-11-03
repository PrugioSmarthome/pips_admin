package com.daewooenc.pips.admin.core.service.login;

import com.daewooenc.pips.admin.core.domain.authorization.User;
import com.daewooenc.pips.admin.core.domain.authorization.UserGroupAuth;
import com.daewooenc.pips.admin.core.service.authorization.UserGroupAuthService;
import com.daewooenc.pips.admin.core.util.SessionUtil;
import com.daewooenc.pips.admin.core.util.crypto.Sha256Cipher;
import com.daewooenc.pips.admin.core.dao.configuration.LoginHistoryMapper;
import com.daewooenc.pips.admin.core.dao.login.AuthMapper;
import com.daewooenc.pips.admin.core.domain.Consts;
import com.daewooenc.pips.admin.core.domain.common.SessionUser;
import com.daewooenc.pips.admin.core.domain.configuration.LoginHistory;
import com.daewooenc.pips.admin.web.util.XSSUtil;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 로그인 처리 Service.
 */
@Service
public class AuthService {

	/** 로그 출력. */
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/** AuthMapper Autowired. */
	@Autowired
	private AuthMapper authMapper;

	/** LoginHistoryMapper Autowired. */
	@Autowired
	private LoginHistoryMapper loginhistoryMapper;

	@Autowired
	private UserGroupAuthService userGroupAuthService;

	@Autowired
	private XSSUtil xssUtil;

	/** Ehcache Autowired. */
	@Autowired
	private Ehcache ehcache;

	/**
	 * 로그인 처리 프로세스.
	 *
	 * @param text_id 사용자ID
	 * @param text_nm 비밀번호
	 * @param request HttpServletRequest
	 * @return String
	 */
	public String login(String text_id, String text_nm, HttpServletRequest request, String encKey){
		String resultMsg = "";

		logger.error("login");

		if ("".equals(StringUtils.defaultString(text_id))) {
			resultMsg = "ID_INPUT_NULL";
			return resultMsg;
		}

		if ("".equals(StringUtils.defaultString(text_nm))) {
			resultMsg = "PASSWD_INPUT_NULL";
			return resultMsg;
		}

		// 로그인 실패 횟수 초과로 계정이 잠겨있는지 검사
		if (isAccountLock(text_id)) {
			logger.info("==>> account lock!!! : {}", text_id);
			resultMsg = "LOCK_ACCOUNT";

			return resultMsg;
		}

		String remoteAddress = request.getRemoteAddr();
		//login check
		SessionUser sessionUser = getSessionUser(text_id, text_nm, remoteAddress, encKey);

		if (sessionUser != null) { //로그인 성공
			logger.info("==>> login success!!! : {}", text_id);

			//session create
			HttpSession session = request.getSession(true);
			session.removeAttribute(Consts.SessionAttr.USER);
			session.setAttribute(Consts.SessionAttr.USER, sessionUser);

			if (isInitAccount(text_id)) {
				logger.info("==> user init account!!! : {}", text_id);

				resultMsg = "INIT_ACCOUNT";
			} else {

				resultMsg = "GO_MAIN";
			}

		} else { //로그인 실패
			logger.info("==>> login fail!!! : " + text_id);

			if (authMapper.getUserCount(text_id) > 0) {
				if (isOverLoginFailCount(text_id)) {
					logger.info("==>> over login fail count!!! : {}", text_id);

					setAccountLock(text_id);

					return "OVER_LOGIN_FAIL_COUNT";
				}
			}

			resultMsg = "LOGIN_FAIL";
		}

		return resultMsg;
	}

	/**
	 * 로그인 검증 및 세션정보 생성.
	 *
	 * @param userId 사용자ID
	 * @param password 비밀번호
	 * @param loginGatewayIp 로그인IP
	 * @return SessionUser
	 */
	private SessionUser getSessionUser(String userId, String password, String loginGatewayIp, String encKey) {
		SessionUser sUser = new SessionUser();
		sUser.setUserId(userId);
		sUser.setPassword(new Sha256Cipher(password).encrypt());
		sUser.setEncKey(encKey);
		SessionUser sessionUser = authMapper.login(sUser);

		if (sessionUser != null) {
			/**
			 * DB에서 처리 가능하나, 여러 종류의 DBMS와의 일관성을 위해서 프로그램에서 처리
			 * 프로젝트 성격에 맞도록 SQL을 수정해서 사용 가능
			 */
			sessionUser.setLoginTimeRecently();

			//set client ip
			sessionUser.setLoginGatewayIp(loginGatewayIp);

			logger.debug("{}", sessionUser);

			//로그인 시간 저장(비밀번호 변경주기를 위한...)
			authMapper.updateLastLoginDateTime(sessionUser);

			//로그인 이력처리
			//loginhistoryMapper.insert(setLoginHistory(sessionUser));
		} else {
			authMapper.updateLoginFailCount(userId);
		}

		return sessionUser;
	}

	/**
	 * 로그아웃 처리.
	 *
	 * @param sessionUser 세션정보
	 */
	public void logout(SessionUser sessionUser) {
//		authMapper.insertLogoutHistory(user);
	}

	/**
	 * 로그인 이력 저장.
	 *
	 * @param sessionUser 세션정보
	 * @return LoginHistory
	 */
	private LoginHistory setLoginHistory(SessionUser sessionUser) {
		LoginHistory loginhistory = new LoginHistory();

		loginhistory.setUserId(sessionUser.getUserId());

		Map<String, String> mapLoginDate = authMapper.getLoginDate(sessionUser.getUserId());

		if (mapLoginDate == null) {
			loginhistory.setLoginDate("");
			loginhistory.setLoginTime("");
		} else {
			loginhistory.setLoginDate(mapLoginDate.get("LOGIN_DATE") == null ? "" : mapLoginDate.get("LOGIN_DATE"));
			loginhistory.setLoginTime(mapLoginDate.get("LOGIN_TIME") == null ? "" : mapLoginDate.get("LOGIN_TIME"));

			if (loginhistory.getLoginDate().indexOf("|") > -1) {
				loginhistory.setLoginDate(loginhistory.getLoginDate().substring(loginhistory.getLoginDate().indexOf("|")));
			}
			if (loginhistory.getLoginTime().indexOf("|") > -1) {
				loginhistory.setLoginTime(loginhistory.getLoginTime().substring(loginhistory.getLoginTime().indexOf("|")));
			}
		}

		loginhistory.setLoginGatewayIp(sessionUser.getLoginGatewayIp());

		return loginhistory;
	}

	/** 로그인 실패 제한 횟수. */
	private @Value("${login.fail.limit}") String limit;

	/**
	 * 로그인 실패 횟수 초과 여부 확인.
	 *
	 * @param userId 사용자ID
	 * @return boolean
	 */
	private boolean isOverLoginFailCount(String userId) {
		limit = xssUtil.replaceAll(StringUtils.defaultString(limit));
		return authMapper.getLoginFailCount(userId) >= Integer.parseInt(limit);
	}

	/**
	 * 사용자 계정 잠금 설정.
	 *
	 * @param userId 사용자ID
	 */
	private void setAccountLock(String userId) {
		authMapper.setAccountLock(userId);
	}

	/**
	 * 사용자 잠김 여부 확인.
	 *
	 * @param userId 사용자ID
	 * @return boolean
	 */
	private boolean isAccountLock(String userId) {
		String accountLock = authMapper.getAccountLock(userId);

		return "Y".equals(accountLock);
	}

	/**
	 * 사용자 최초접속여부 확인.
	 *
	 * @param userId 사용자ID
	 * @return boolean
	 */
	private boolean isInitAccount(String userId) {
		String initAccount = authMapper.checkInitAccount(userId);

		return "Y".equals(initAccount);
	}

	/**
	 * IP 허용 영역 확인.
	 *
	 * @param sessionUser 세션정보
	 * @param remoteAddress 원격주소
	 * @return boolean
	 */
	private boolean isPassIP_Bandwidth(SessionUser sessionUser, String remoteAddress) {
		String[] ipBandwidth = sessionUser.getIpBandwidth().split("\\.");

		logger.debug("ip bandwidth : {}", sessionUser.getIpBandwidth());
		logger.debug("remoteAddress : {}", remoteAddress);
		logger.debug("ip bandwidth : {}", ipBandwidth.length);

		// localhost 접속이면 건너뜀
		if (remoteAddress.equals("localhost") || remoteAddress.equals("0:0:0:0:0:0:0:1")) {
			return true;
		}else if (remoteAddress.indexOf(".") < 0) {		// ip에 '.'이 있는지 확인
			return false;
		}

		String[] ip = remoteAddress.split("\\.");
		logger.debug("ip : {}", ip.length);
		// '.'이 세개인지 확인
		if (ip.length > 4) {
			return false;
		}

		boolean result = true;
		for(int i = 0; i < ipBandwidth.length; i++) {
			logger.debug("ipBandwidth[{}] : {}", i, ipBandwidth[i]);

			if (!"*".equals(ipBandwidth[i])) {
				logger.debug("ipBandwidth[i] : !*", i);

				if (!ip[i].equals(ipBandwidth[i])) {
					logger.debug("ip not equal ipBandwidth");

					result = false;
					break;
				}
			}
		}

		return result;
	}

	public boolean updateMyInfo(User user) {
		return authMapper.updateMyInfo(user) > 0;
	}
}
