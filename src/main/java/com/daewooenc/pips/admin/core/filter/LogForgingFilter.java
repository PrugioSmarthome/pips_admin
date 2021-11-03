package com.daewooenc.pips.admin.core.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class LogForgingFilter extends DefaultFilterImpl {
	
	/**
	 * logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(LogForgingFilter.class);

    @Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
    	logger.debug("In doFilter LogForgingFilter  ...............");
    	if (request instanceof HttpServletRequest) {
    		chain.doFilter(new LogForgingWrapper((HttpServletRequest) request), response);
    	}
        logger.debug("Out doFilter LogForgingFilter ...............");
    }

}