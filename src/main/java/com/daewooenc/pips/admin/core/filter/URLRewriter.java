package com.daewooenc.pips.admin.core.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * URLRewriter Filter
 */
public class URLRewriter extends UrlRewriteFilter {

	/** logger. */
	private static final Logger logger = LoggerFactory.getLogger(URLRewriter.class);

	/** 국제화 지원 URI prefix. */
	private static final List<String> globalSupportUriList = Arrays.asList("/images/");
	
	@Override
	public void init(FilterConfig config) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
		HttpServletRequest request = (HttpServletRequest) req;
		String requestURI = request.getRequestURI();
		StringBuffer sb = new StringBuffer();
		
		for(String globalSupportUri : globalSupportUriList){
			if(requestURI.startsWith(globalSupportUri)){
				sb.setLength(0);
				String toReplace = sb.append(globalSupportUri).append(request.getLocale().toString()).append("/").toString();
				String newURI = requestURI.replace(globalSupportUri, toReplace);

				logger.debug("################################ requestURI := {}", globalSupportUri);
				logger.debug("################################ newURI := {}", newURI);
				req.getRequestDispatcher(newURI).forward(req, res);
			}
		}

		chain.doFilter(req, res);
	}

	@Override
	public void destroy() {
	}

}
