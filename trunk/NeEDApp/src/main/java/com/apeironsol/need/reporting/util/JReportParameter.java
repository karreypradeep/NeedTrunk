/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.reporting.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author pradeep
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface JReportParameter {

	/** The constant that indicates that no name was set. */
	String	NO_NAME_SET	= "**|\\|0 N@me Set**";

	/**
	 * The name under which the {@link java.lang.reflect.Field} is annotated is
	 * to be used during passing parameters to report.
	 */
	String name() default NO_NAME_SET;

}
