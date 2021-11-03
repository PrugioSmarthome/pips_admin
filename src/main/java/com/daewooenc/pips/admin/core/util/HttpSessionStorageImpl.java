package com.daewooenc.pips.admin.core.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * SessionUtil.
*/
public class HttpSessionStorageImpl implements SessionStorageProvider {

	/**
	 * Session 속성을 구한다.
	 *
	 * @param	request : request
	 * @param   key : key
	 * @return	Object
	*/
	public Object getAttribute(HttpServletRequest request, String key) {
//		LOG.debug("key: " + key);
		return request.getSession().getAttribute(key);
	}

	/**
	 * Session 속성을 구한다.
	 *
	 * @param	sessionId sessionId
	 * @param   key key
	 * @return	Object
	*/
	@Override
	public Object getAttribute(String sessionId, String key) {
//		LOG.debug("key: " + key);
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getHeader(key);
	}


	/**
	 * Session 속성을 설정한다.
	 *
	 * @param	request : request
	 * @param	key : key
	 * @param	value : value
	*/
	public void setAttribute(HttpServletRequest request, String key, Object value) {
//		LOG.debug("key: {}, value : {}", key, value.toString());
		request.getSession().setAttribute(key, value);
	}

	/**
	 * Session 속성을 삭제한다.
	 *
	 * @param	request : request
	 * @param	key : key
	*/
	public void removeAttribute(HttpServletRequest request, String key) {
//		LOG.debug("key: " + key);
		request.getSession().removeAttribute(key);
	}
}
