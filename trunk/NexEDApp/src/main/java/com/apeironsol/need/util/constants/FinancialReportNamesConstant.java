/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.util.constants;

import com.apeironsol.need.util.EnumUtil;
import com.apeironsol.need.util.portal.ViewUtil;

/**
 * Enums for financial report names constants.
 * 
 * @author sunny
 */
public enum FinancialReportNamesConstant implements Labeled{

	BRANCH_EXPENSES("financial_expenses_report", "FinancialDetails.jasper","FinancialDetails_Expenses.jasper");

	private String	label;
	private String	jasperReportFileName;
	private String  jasperSubReportFileName;

	FinancialReportNamesConstant(final String label, final String jasperReportFileName,final String jasperSubReportFileName) {
		this.label = label;
		this.setJasperReportFileName(jasperReportFileName);
		this.setJasperSubReportFileName(jasperSubReportFileName);
	}

	@Override
	public String getLabel() {
		return ViewUtil.getEnumLabel(this.label);
	}

	@Override
	public void setLabel(final String label) {
		this.label = label;
	}

	public static StudentReportNamesConstant[] getValuesSortedByLabels() {
		return EnumUtil.getEnumsSortedByLabels(StudentReportNamesConstant.class).toArray(
				new StudentReportNamesConstant[StudentReportNamesConstant.values().length]);
	}

	public String getValue() {
		return this.label;
	}

	/**
	 * @return the jasperReportFileName
	 */
	public String getJasperReportFileName() {
		return this.jasperReportFileName;
	}

	/**
	 * @param jasperReportFileName
	 *            the jasperReportFileName to set
	 */
	public void setJasperReportFileName(final String jasperReportFileName) {
		this.jasperReportFileName = jasperReportFileName;
	}

	/**
	 * @return the jasperSubReportFileName
	 */
	public String getJasperSubReportFileName() {
		return this.jasperSubReportFileName;
	}

	/**
	 * @param jasperSubReportFileName the jasperSubReportFileName to set
	 */
	public void setJasperSubReportFileName(final String jasperSubReportFileName) {
		this.jasperSubReportFileName = jasperSubReportFileName;
	}

}
