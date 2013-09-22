/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.reporting.util;

import java.util.Map;

/**
 * Interface for returning all the parameters used for generating jasper report.
 * 
 * @author pradeep
 * 
 */
public interface JReportUsingParameter extends JReportFile {

	/**
	 * Returns parameters used for generating jasper report.
	 * 
	 * @return parameters used for generating jasper report.
	 */
	Map<String, Object> getReportParameters();
}
