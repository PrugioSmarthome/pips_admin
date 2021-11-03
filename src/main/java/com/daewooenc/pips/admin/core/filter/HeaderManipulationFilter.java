package com.daewooenc.pips.admin.core.filter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class HeaderManipulationFilter extends DefaultFilterImpl {
	
	/**
	 * logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(HeaderManipulationFilter.class);

	/**
	 * Linefeed 예외항목 정의.
	 */
	private static final List<String> EXCEPT_LINE_FEED_URL = Arrays.asList("/MagicInfoWebAuthorClient/LFDSave",
			"/MagicInfoWebAuthorClient");

    @Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
    	LOG.debug("In doFilter HeaderManipulationFilter  ...............");

    	if (request instanceof HttpServletRequest) {
    		HttpServletRequest req = (HttpServletRequest) request;

    		if (EXCEPT_LINE_FEED_URL.contains(StringUtils.defaultString(req.getRequestURI()))) {
    			chain.doFilter(request, response);
    		} else {
    			chain.doFilter(new HeaderManipulationWrapper(req), response);
    		}
    	}
    	LOG.debug("Out doFilter HeaderManipulationFilter ...............");
    }

}