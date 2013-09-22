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
 * Enums for admission status constants.
 * 
 * @author sunny
 */
public enum StudentReportNamesConstant implements Labeled {


	STUDENT_PERSONAL_DETAILS("student_personal_details_report", "StudentDetails.jasper","StudentDetails_StudentPersonal.jasper"),
	STUDENT_CONTACT_DETAILS("student_contact_details_report","StudentDetails.jasper","StudentDetails_StudentContact.jasper"),
	STUDENT_FEE_SUMMARY("student_fee_summary_report", "StudentDetails.jasper","StudentDetails_StudentFeeSummary.jasper"),
	STUDENT_FEE_DETAILS("student_fee_details_report", "StudentDetails.jasper","StudentDetails_StudentFeeDetails.jasper");

	private String	label;
	private String	jasperReportFileName;
	private String  jasperSubReportFileName;

	StudentReportNamesConstant(final String label, final String jasperReportFileName,final String jasperSubReportFileName) {
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
