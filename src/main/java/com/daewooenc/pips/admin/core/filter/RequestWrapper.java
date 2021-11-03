package com.daewooenc.pips.admin.core.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * XSS 대비를 위한 HttpServletRequestWrapper 구현체.
 */
public final class RequestWrapper extends HttpServletRequestWrapper {
	/**
	 * 로그출력.
	 */
	private static final Logger logger = LoggerFactory.getLogger(RequestWrapper.class);
	
	/**
	 * HttpServletRequest 생성자.
	 * 
	 * @param servletRequest HttpServletRequest
	 */
	public RequestWrapper(HttpServletRequest servletRequest) {
		super(servletRequest);
	}

	@Override
	public String[] getParameterValues(String parameter) {
		logger.debug("In getParameterValues .. parameter .......");
		String[] values = super.getParameterValues(parameter);
		if (values == null) {
			return null;
		}
		int count = values.length;
		String[] encodedValues = new String[count];
		for (int i = 0; i < count; i++) {
			encodedValues[i] = cleanXSS(values[i]);
		}
		return encodedValues;
	}

	@Override
	public String getParameter(String parameter) {
		logger.debug("In getParameter .. parameter .......");
		String value = super.getParameter(parameter);
		if (value == null) {
			return null;
		}
		logger.debug("In getParameter RequestWrapper ........ value .......");
		return cleanXSS(value);
	}

	@Override
	public String getHeader(String name) {
		logger.debug("In getHeader .. parameter .......");
		String value = super.getHeader(name);
		if (value == null)
			return null;
		logger.debug("In getHeader RequestWrapper ........... value ....");
		return cleanXSS(value);
	}

	/**
	 * 
	 * XSS 대비를 위해 파라미터에서 변수 변조. 
	 *
	 * @param param 파라미터변수
	 * @return String
	 */
	private String cleanXSS(String param) {
		// You'll need to remove the spaces from the html entities below
		
		String value = param;
		
		logger.debug("In cleanXSS RequestWrapper ...............{}", value);

		StringBuilder ret = new StringBuilder();

		do {
			ret.setLength(0);
			ret.append(value);
			
			value = ret.toString()
					.replaceAll("eval\\((.*)\\)", "")
					.replaceAll("[\\\"\\'][\\s]*javascript:(.*)[\\\"\\']", "\"\"")
					.replaceAll("(?i)<script.*?>.*?<script.*?>", "")
					.replaceAll("(?i)<script.*?>.*?</script.*?>", "")
					.replaceAll("(?i)<.*?javascript:.*?>.*?</.*?>", "")
					.replaceAll("(?i)<style.*?>.*?<style.*?>", "")
					.replaceAll("(?i)<style.*?>.*?</style.*?>", "")
					.replaceAll("(?i)<.*?stylesheet:.*?>.*?</.*?>", "")
					.replaceAll("(?i)<.*?\\s+on.*?>.*?</.*?>", "")
					.replaceAll("<(\"[^\"]*\"|'[^']*'|[^'\">])*>", "");
		}while(ret.toString().length() != value.length());

		logger.debug("Out cleanXSS RequestWrapper ........ value ....... {}", value);

		return value;
	}
}