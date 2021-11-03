package com.daewooenc.pips.admin.core.util;

import javax.servlet.http.HttpServletRequest;

/**
 * SessionUtil.
*/
public interface SessionStorageProvider {

	/**
	 * Session 속성을 구한다.
	 *
	 * @param	request : request
	 * @param	key : key
	 * @return	Object
	*/
	Object getAttribute(HttpServletRequest request, String key);

	/**
	 * Session 속성을 구한다.
	 *
	 * @param	sessionId : sessionId
	 * @param	key : key
	 * @return	Object
	*/
	Object getAttribute(String sessionId, String key);

	/**
	 * Session 속성을 설정한다.
	 *
	 * @param	request : request
	 * @param	key : key
	 * @param	value : value
	 * @return
	*/
	void setAttribute(HttpServletRequest request, String key, Object value);

	/**
	 * Session 속성을 삭제한다.
	 *
	 * @param	request : request
	 * @param	key : key
	 * @return
	*/
	void removeAttribute(HttpServletRequest request, String key);


}
