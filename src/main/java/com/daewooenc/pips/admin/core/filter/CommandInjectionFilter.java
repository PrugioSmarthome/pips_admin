package com.daewooenc.pips.admin.core.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class CommandInjectionFilter extends DefaultFilterImpl {
	
	/**
	 * logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(CommandInjectionFilter.class);
	
	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
    	logger.debug("In doFilter CommandInjectionFilter  ...............");
    	if (request instanceof HttpServletRequest) {
    		chain.doFilter(new CommandInjectionWrapper((HttpServletRequest) request), response);
    	}
        logger.debug("Out doFilter CommandInjectionFilter ...............");
    }

}