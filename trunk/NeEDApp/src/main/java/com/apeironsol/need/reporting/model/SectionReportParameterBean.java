/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.reporting.model;

import java.util.Map;

import com.apeironsol.need.reporting.util.JReportParameter;
import com.apeironsol.need.reporting.util.JReportUsingParameter;
import com.apeironsol.need.reporting.util.JReportUtils;
import com.apeironsol.need.util.constants.SectionReportsConstant;

/**
 * 
 * @author pradeep
 * 
 */
public class SectionReportParameterBean implements JReportUsingParameter {

	String						reportFileName;

	SectionReportsConstant		sectionReportsConstant;

	@JReportParameter(name = "schoolName")
	private String				schoolName;

	@JReportParameter(name = "className")
	private String				className;

	@JReportParameter(name = "sectioName")
	private String				sectioName;

	@JReportParameter(name = "academicYear")
	private String				academicYear;

	private static final String	SECTION_FINANCIAL_REPORT_ACADEMIC_YEAR	= "/reports/SectionFeeReport.jasper";

	private static final String	SECTION_ATTENDANCE_REPORT_ACADEMIC_YEAR	= "/reports/SectionAttendanceReport.jasper";

	@Override
	public Map<String, Object> getReportParameters() {
		return JReportUtils.getJReportParameters(this.getClass(), this);
	}

	@Override
	public String getJRXMLFilePath() {
		return "student_admission_report_parameter.jrxml";
	}

	@Override
	public String getJasperFilePath() {
		String result = null;
		if (SectionReportsConstant.ATTENDANCE_REPORT.equals(this.sectionReportsConstant)) {
			result = SECTION_ATTENDANCE_REPORT_ACADEMIC_YEAR;
		} else {
			result = SECTION_FINANCIAL_REPORT_ACADEMIC_YEAR;
		}
		return result;
	}

	@Override
	public String getReportOutPutFileName() {
		return this.reportFileName == null ? "outPutFile" : this.reportFileName;
	}

	@Override
	public void setReportOutPutFileName(final String reportFileName) {
		this.reportFileName = reportFileName;
	}

	public SectionReportParameterBean() {

	}

	/**
	 * @return the sectionReportsConstant
	 */
	public SectionReportsConstant getSectionReportsConstant() {
		return this.sectionReportsConstant;
	}

	/**
	 * @param sectionReportsConstant
	 *            the sectionReportsConstant to set
	 */
	public void setSectionReportsConstant(final SectionReportsConstant sectionReportsConstant) {
		this.sectionReportsConstant = sectionReportsConstant;
	}

	/**
	 * @return the schoolName
	 */
	public String getSchoolName() {
		return this.schoolName;
	}

	/**
	 * @param schoolName
	 *            the schoolName to set
	 */
	public void setSchoolName(final String schoolName) {
		this.schoolName = schoolName;
	}

	/**
	 * @return the className
	 */
	public String getClassName() {
		return this.className;
	}

	/**
	 * @param className
	 *            the className to set
	 */
	public void setClassName(final String className) {
		this.className = className;
	}

	/**
	 * @return the sectioName
	 */
	public String getSectioName() {
		return this.sectioName;
	}

	/**
	 * @param sectioName
	 *            the sectioName to set
	 */
	public void setSectioName(final String sectioName) {
		this.sectioName = sectioName;
	}

	/**
	 * @return the academicYear
	 */
	public String getAcademicYear() {
		return this.academicYear;
	}

	/**
	 * @param academicYear
	 *            the academicYear to set
	 */
	public void setAcademicYear(final String academicYear) {
		this.academicYear = academicYear;
	}
}
