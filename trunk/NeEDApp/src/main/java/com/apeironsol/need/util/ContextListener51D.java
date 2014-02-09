/**
 * 
 */
package com.apeironsol.need.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import fiftyone.mobile.detection.binary.Reader;

/**
 * @author pradeep
 * 
 */
public class ContextListener51D implements ServletContextListener {

	@Override
	public void contextInitialized(final ServletContextEvent sce) {
		try {
			sce.getServletContext().setAttribute("51Degrees.mobi", Reader.create());
		} catch (final Exception ex) {
			sce.getServletContext().setAttribute("51Degrees.mobi", null);
		}
	}

	@Override
	public void contextDestroyed(final ServletContextEvent sce) {
		sce.getServletContext().removeAttribute("51Degrees.mobi");
	}
}