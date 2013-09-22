/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.util.portal;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

/**
 * Class for storing servlet context.
 * 
 * @author Pradeep
 */
@Component
public class ServletContextUtils implements ServletContextAware {
	private static ServletContext	servletContext;

	@Override
	public void setServletContext(final ServletContext servletCtx) {
		setServletContextParam(servletCtx);
	}

	private static void setServletContextParam(final ServletContext servletCtx) throws BeansException {
		servletContext = servletCtx;
	}

	public static ServletContext getServletContext() {
		return servletContext;
	}

	public static String getContextInitParam(final String contextParam) {
		return getServletContext().getInitParameter(contextParam);
	}

}
