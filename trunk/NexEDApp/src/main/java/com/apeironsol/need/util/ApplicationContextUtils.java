/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Class for storing application context.
 * 
 * @author Pradeep
 */
@Component
public class ApplicationContextUtils implements ApplicationContextAware {
	private static ApplicationContext	applicationContext;

	@Override
	public void setApplicationContext(final ApplicationContext appContext) throws BeansException {
		setApplicationContextParam(appContext);
	}

	private static void setApplicationContextParam(final ApplicationContext appContext) throws BeansException {
		applicationContext = appContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
}
