package com.daewooenc.pips.admin.core.util.message;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * 메시지 관련 Util
 */
public class MessageUtil {

	/** The ms acc. */
	private static MessageSourceAccessor msAcc;

	/**
	 * Sets the message source accessor.
	 *
	 * @param pMsAcc the new message source accessor
	 */
	public void setMessageSourceAccessor(MessageSourceAccessor pMsAcc){
		if(msAcc == null) {
			msAcc = pMsAcc;
		}
	}

	/**
	 * 키에 해당하는 메세지 반환.
	 *
	 * @param key the key
	 * @return the message
	 */
	public static String getMessage(String key){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();

		String sessionCountry = (String)request.getSession().getAttribute("sessionCountry");
		String sessionLanguage = (String)request.getSession().getAttribute("sessionLanguage");

		Locale sessionLocale;
		if (StringUtils.isBlank(sessionCountry) || StringUtils.isBlank(sessionLanguage)) {
			sessionLocale = new Locale(request.getLocale().getLanguage(), request.getLocale().getCountry());			
		} else {
			sessionLocale = new Locale(sessionLanguage, sessionCountry);
		}

		return msAcc.getMessage(key, sessionLocale);
	}

	/**
	 * 키에 해당하는 메세지 반환 (locale).
	 * 
	 * @param key 키
	 * @param locale Locale
	 * @return String 메세지
	 */
	public static String getMessage(String key, Locale locale){
		return msAcc.getMessage(key, locale);
	}

	/**
	 * 키에 해당하는 메세지 반환 (파라미터 포함).
	 *
	 * @param key the key
	 * @param objs the objs
	 * @return the message
	 */
	public static String getMessage(String key, Object[] objs){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();

		String sessionCountry = (String)request.getSession().getAttribute("sessionCountry");
		String sessionLanguage = (String)request.getSession().getAttribute("sessionLanguage");

		Locale sessionLocale;
		if (StringUtils.isBlank(sessionCountry) || StringUtils.isBlank(sessionLanguage)) {
			sessionLocale = new Locale(request.getLocale().getLanguage(), request.getLocale().getCountry());			
		} else {
			sessionLocale = new Locale(sessionLanguage, sessionCountry);
		}

		return msAcc.getMessage(key, objs, sessionLocale);
	}

	/**
	 * 키에 해당하는 메세지 반환 (파라미터 포함).
	 * 
	 * @param key 키
	 * @param objs 대입문자
	 * @param locale locale
	 * @return String 메세지
	 */
	public static String getMessage(String key, Object[] objs, Locale locale){
		return msAcc.getMessage(key, objs, locale);
	}

}
