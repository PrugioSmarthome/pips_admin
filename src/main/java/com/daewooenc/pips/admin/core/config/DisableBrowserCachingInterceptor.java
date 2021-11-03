package com.daewooenc.pips.admin.core.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Browser cache remove Interceptor
 */
public class DisableBrowserCachingInterceptor extends HandlerInterceptorAdapter {

	/** The log. */
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * preHandle.
	 *
	 * @param		request  HttpServletRequest
	 * @param		response HttpServletResponse
	 * @param		handler Object
	 * @return		boolean boolean
	 * @warning
	 * @exception	Exception
	 * @see
	 */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

    	// 차후 필요할 경우 uri 체크하여 선별적으로 적용 필요
    	/*
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0
        response.setDateHeader("Expires", 0); // Proxies
    	 */
        return true;
    }
}