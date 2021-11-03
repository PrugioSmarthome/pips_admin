package com.daewooenc.pips.admin.core.filter;

import javax.servlet.*;
import java.io.IOException;

public class DefaultFilterImpl implements Filter {
	
	/**
	 * filter 설정.
	 */
	protected FilterConfig filterConfig;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

	@Override
    public void destroy() {
//        this.filterConfig = null;
    }

    public FilterConfig getFilterConfig() {
		return filterConfig;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
	}
}