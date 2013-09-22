/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.reporting.ro;

import java.io.Serializable;

import com.apeironsol.need.core.model.StudentAcademicYear;
import com.apeironsol.need.core.model.StudentSection;
import com.apeironsol.need.util.dataobject.StudentFinancialAcademicYearDO;

/**
 * @author sunny
 *         Data object for student report.
 */
public class StudentRO implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long				serialVersionUID	= 1119138863987142135L;

	private StudentFinancialAcademicYearDO	studentFinancialAcademicYearDO;

	private StudentAcademicYear				studentAcademicYear;

	private String							residenceType;

	private String							gender;

	private String							parentOrGuardianName;

	private StudentSection					studentSection;

	/**
	 * @return the studentAcademicYear
	 */
	public StudentAcademicYear getStudentAcademicYear() {
		return this.studentAcademicYear;
	}

	/**
	 * @param studentAcademicYear
	 *            the studentAcademicYear to set
	 */
	public void setStudentAcademicYear(final StudentAcademicYear studentAcademicYear) {
		this.studentAcademicYear = studentAcademicYear;
	}

	/**
	 * @return the studentFinancialAcademicYearDO
	 */
	public StudentFinancialAcademicYearDO getStudentFinancialAcademicYearDO() {
		return this.studentFinancialAcademicYearDO;
	}

	/**
	 * @param studentFinancialAcademicYearDO
	 *            the studentFinancialAcademicYearDO to set
	 */
	public void setStudentFinancialAcademicYearDO(final StudentFinancialAcademicYearDO studentFinancialAcademicYearDO) {
		this.studentFinancialAcademicYearDO = studentFinancialAcademicYearDO;
	}

	/**
	 * @return the residenceType
	 */
	public String getResidenceType() {
		return this.residenceType;
	}

	/**
	 * @param residenceType
	 *            the residenceType to set
	 */
	public void setResidenceType(final String residenceType) {
		this.residenceType = residenceType;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return this.gender;
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public void setGender(final String gender) {
		this.gender = gender;
	}

	public String getParentOrGuardianName() {
		return this.parentOrGuardianName;
	}

	public void setParentOrGuardianName(final String parentOrGuardianName) {
		this.parentOrGuardianName = parentOrGuardianName;
	}

	public StudentSection getStudentSection() {
		return this.studentSection;
	}

	public void setStudentSection(final StudentSection studentSection) {
		this.studentSection = studentSection;
	}

}
