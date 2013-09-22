/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.reporting.dataobject;

import java.io.Serializable;

import com.apeironsol.need.reporting.util.JReportType;

/**
 * @author sunny
 * 
 */
public class ReportDO  implements Serializable {

	private static final long	serialVersionUID	= -7438066230785859459L;

	private JReportType				reportType;

	private String				inputJasperFileName;

	private String				outputReportName;

	/**
	 * @return the inputJasperFileName
	 */
	public String getInputJasperFileName() {
		return this.inputJasperFileName;
	}

	/**
	 * @param inputJasperFileName
	 *            the inputJasperFileName to set
	 */
	public void setInputJasperFileName(final String inputJasperFileName) {
		this.inputJasperFileName = inputJasperFileName;
	}

	/**
	 * @return the outputReportName
	 */
	public String getOutputReportName() {
		return this.outputReportName;
	}

	/**
	 * @param outputReportName
	 *            the outputReportName to set
	 */
	public void setOutputReportName(final String outputReportName) {
		this.outputReportName = outputReportName;
	}

	/**
	 * @return the reportType
	 */
	public JReportType getReportType() {
		return this.reportType;
	}

	/**
	 * @param reportType the reportType to set
	 */
	public void setReportType(final JReportType reportType) {
		this.reportType = reportType;
	}

}
