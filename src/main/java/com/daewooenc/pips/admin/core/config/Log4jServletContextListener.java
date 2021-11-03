package com.daewooenc.pips.admin.core.config;

import org.apache.logging.log4j.web.Log4jWebSupport;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Log4j servlet context Listener
 */
public class Log4jServletContextListener implements ServletContextListener {
	private org.apache.logging.log4j.web.Log4jServletContextListener listener = new org.apache.logging.log4j.web.Log4jServletContextListener();

	@Override
	public void contextInitialized(ServletContextEvent event) {
		String loggerPath = "WEB-INF/classes/log4j2.xml";
		event.getServletContext().setInitParameter(Log4jWebSupport.LOG4J_CONFIG_LOCATION, loggerPath);
		listener.contextInitialized(event);
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		listener.contextDestroyed(event);
	}

}
