/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.nexed.reporting.util;

/**
 * INterface with JReport file name and the out put file name.
 * 
 * @author pradeep
 * 
 */
public interface JReportFile {

	String getJRXMLFilePath();

	String getJasperFilePath();

	String getReportOutPutFileName();

	void setReportOutPutFileName(final String reportFileName);
}
