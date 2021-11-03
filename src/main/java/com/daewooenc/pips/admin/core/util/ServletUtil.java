package com.daewooenc.pips.admin.core.util;

import org.springframework.web.servlet.FrameworkServlet;

import javax.servlet.jsp.PageContext;
import java.util.Enumeration;

/**
 * Servlet 에 필요한 정보를 추출하기 위한 Class
*/
public class ServletUtil {
	
	/**
	 * web.xml 에 선언된 DispatcherServlet 에 대한 Spring의 Context Attribute Name.
	 */
	private static String servletConextAttributeName;
	
	/**
	 * servletConextAttributeName 추출 메소드.
	 * 
	 * @param pageContext PageContext
	 * @return String servletConextAttributeName
	 */
	public static String getServletConextName(PageContext pageContext){
		
		if(servletConextAttributeName == null){
			Enumeration<String> e = pageContext.getServletContext().getAttributeNames();
			while(e.hasMoreElements()){
				String attrName = e.nextElement();
				if(attrName.startsWith(FrameworkServlet.SERVLET_CONTEXT_PREFIX)){
					servletConextAttributeName = attrName;
					break;
				}
			}
		}
		
		return servletConextAttributeName;
	}	
}
