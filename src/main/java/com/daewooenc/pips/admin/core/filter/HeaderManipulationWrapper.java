package com.daewooenc.pips.admin.core.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public final class HeaderManipulationWrapper extends HttpServletRequestWrapper {
	
	/**
	 * logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(HeaderManipulationWrapper.class);
	
	/**
	 * 생성자.
	 *
	 * @param		servletRequest : servletRequest
	 */
	public HeaderManipulationWrapper(HttpServletRequest servletRequest) {
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
			encodedValues[i] = clean(values[i]);
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
		logger.debug("In getParameter HeaderManipulationWrapper ........ value .......");
		return clean(value);
	}

	@Override
	public String getHeader(String name) {
		logger.debug("In getHeader .. parameter .......");
		String value = super.getHeader(name);
		if (value == null) {
			return null;
		}
		logger.debug("In getHeader HeaderManipulationWrapper ........... value ....");
		return clean(value);
	}

	/**
	 * clean.
	 * 
	 * @param param String
	 * @return String
	 */
	private String clean(String param) {
		// You'll need to remove the spaces from the html entities below

		String value = param;
		
		logger.debug("In clean HeaderManipulationWrapper ...............{}", value);

		StringBuilder ret = new StringBuilder();

		do {
			ret.setLength(0);
			ret.append(value);

			value = ret.toString().replace("\n", "");
			value = value.replace("\r", "");
		} while(ret.toString().length() != value.length());

		logger.debug("Out clean RequestWrapper ........ value ....... {}", value);
		return value;
	}
}