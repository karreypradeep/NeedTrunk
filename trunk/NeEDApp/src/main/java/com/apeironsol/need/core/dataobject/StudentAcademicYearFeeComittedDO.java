package com.apeironsol.need.core.dataobject;

import java.io.Serializable;

import com.apeironsol.need.core.model.StudentAcademicYearFeeComitted;

public class StudentAcademicYearFeeComittedDO implements Serializable {

	/**
	 * 
	 */
	private static final long				serialVersionUID	= -7461788484160930368L;

	/**
	 * Universal serial version id for this class.
	 */

	private StudentAcademicYearFeeComitted	studentAcademicYearFeeComitted;

	private Double							maxFeePayable;

	/**
	 * @return the studentAcademicYearFeeComitted
	 */
	public StudentAcademicYearFeeComitted getStudentAcademicYearFeeComitted() {
		return this.studentAcademicYearFeeComitted;
	}

	/**
	 * @param studentAcademicYearFeeComitted
	 *            the studentAcademicYearFeeComitted to set
	 */
	public void setStudentAcademicYearFeeComitted(final StudentAcademicYearFeeComitted studentAcademicYearFeeComitted) {
		this.studentAcademicYearFeeComitted = studentAcademicYearFeeComitted;
	}

	/**
	 * @return the maxFeePayable
	 */
	public Double getMaxFeePayable() {
		return this.maxFeePayable;
	}

	/**
	 * @param maxFeePayable
	 *            the maxFeePayable to set
	 */
	public void setMaxFeePayable(final Double maxFeePayable) {
		this.maxFeePayable = maxFeePayable;
	}
}
