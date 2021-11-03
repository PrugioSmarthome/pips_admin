package com.daewooenc.pips.admin.core.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * CrossScriptingFilter 필터 정의.
 */
public class CrossScriptingFilter extends DefaultFilterImpl {

	/**
	 * 로그 출력.
	 */
	private static final Logger logger = LoggerFactory.getLogger(CrossScriptingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
    	logger.debug("In doFilter CrossScriptingFilter  ...............");
        chain.doFilter(new RequestWrapper((HttpServletRequest) request), response);
        logger.debug("Out doFilter CrossScriptingFilter ...............");
    }

}