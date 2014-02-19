package com.apeironsol.need.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.apeironsol.framework.BaseEntity;

@Entity
@Table(name = "STUDENT_ACADEMIC_YEAR_FEE_COMITTED")
public class StudentAcademicYearFeeComitted extends BaseEntity {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long	serialVersionUID	= 8940404312945273613L;

	@NotNull(message = "model.student_academic_year_mandatory")
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ACADEMIC_YEAR_ID", nullable = false)
	private AcademicYear		academicYear;

	@NotNull(message = "model.student")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STUDENT_ID", nullable = false)
	private Student				student;

	@Column(name = "FEE_COMITTED", nullable = false)
	private Double				feeComitted;

	/**
	 * @return the feeComitted
	 */
	public Double getFeeComitted() {
		return this.feeComitted;
	}

	/**
	 * @param feeComitted
	 *            the feeComitted to set
	 */
	public void setFeeComitted(final Double feeComitted) {
		this.feeComitted = feeComitted;
	}

	/**
	 * @return the academicYear
	 */
	public AcademicYear getAcademicYear() {
		return this.academicYear;
	}

	/**
	 * @param academicYear
	 *            the academicYear to set
	 */
	public void setAcademicYear(final AcademicYear academicYear) {
		this.academicYear = academicYear;
	}

	/**
	 * @return the student
	 */
	public Student getStudent() {
		return this.student;
	}

	/**
	 * @param student
	 *            the student to set
	 */
	public void setStudent(final Student student) {
		this.student = student;
	}

}
