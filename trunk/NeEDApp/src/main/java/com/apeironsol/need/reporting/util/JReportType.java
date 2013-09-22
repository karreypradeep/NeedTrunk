/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.reporting.util;

/**
 * Report types supported by apeironsol.
 * 
 * @author pradeep
 * 
 */
public enum JReportType {
	PDF_REPORT(".pdf"), EXCEL_REPORT(".xlsx"), DOC_REPORT(".docx");

	private String	extension;

	private JReportType(final String extension) {
		this.extension = extension;
	}

	/**
	 * @return the extension
	 */
	public String getExtension() {
		return this.extension;
	}

}
