package com.daewooenc.pips.admin.core.exception;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

/**
 * Exception 발생할 경우 분기 및 처리하기 위한 Resolver .
 */
public class ExceptionResolver extends SimpleMappingExceptionResolver {
	
	/** 
	 * 로그출력.
	 */
	private final transient Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {

		log.error(ex.getMessage(), ex);

		String viewName = determineViewName(ex, request);

		if (viewName == null) {
			return null;
		} else {
			
			String methodType = StringUtils.defaultString(request.getParameter("methodType"));

			// ajax 호출인 경우에만 HTTP status code값을 변경시킨다.
			if (StringUtils.equals(request.getHeader("X-Requested-With"),"XMLHttpRequest") || 
					methodType.toUpperCase(Locale.ENGLISH).equals("AJAX")) {
				Integer statusCode = determineStatusCode(request, viewName);

				if (statusCode != null) {
					applyStatusCodeIfPossible(request, response, statusCode);
				}
			}
			
			request.setAttribute("exceptionMsg", ex.getMessage());

			return getModelAndView(viewName, ex, request);
		}
	}

	@Override	
	public void setExceptionMappings(Properties exceptionMappings){
	}

	@Override
	public void setExceptionAttribute(String exceptionAttribute){
	}

}
