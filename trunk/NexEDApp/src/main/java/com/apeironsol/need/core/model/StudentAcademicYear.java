package com.apeironsol.need.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.apeironsol.framework.BaseEntity;

@Entity
@Table(name = "STUDENT_ACADEMIC_YEAR", uniqueConstraints = { @UniqueConstraint(columnNames = { "STUDENT_ID", "ACADEMIC_YEAR_ID" }, name = "UQ_STUDENT_ACADEMIC_YEAR") })
public class StudentAcademicYear extends BaseEntity {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long	serialVersionUID	= 4816224669274781582L;

	@NotNull(message = "model.student_mandatory")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STUDENT_ID", nullable = false)
	private Student				student;

	@NotNull(message = "model.academic_year_mandatory")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ACADEMIC_YEAR_ID", nullable = false)
	private AcademicYear		academicYear;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BATCH_ID")
	private Batch				batch;

	@OneToOne(optional = true)
	@JoinColumn(name = "PREVS_STU_ACAD_YEAR_ID")
	private StudentAcademicYear	previousStudentAcademicYear;

	@Column(name = "SEQUENCE_NR", nullable = false)
	private Integer				sequenceNr;

	/**
	 * @return the previousStudentAcademicYear
	 */
	public StudentAcademicYear getPreviousStudentAcademicYear() {
		return this.previousStudentAcademicYear;
	}

	/**
	 * @param previousStudentAcademicYear
	 *            the previousStudentAcademicYear to set
	 */
	public void setPreviousStudentAcademicYear(final StudentAcademicYear previousStudentAcademicYear) {
		this.previousStudentAcademicYear = previousStudentAcademicYear;
	}

	/**
	 * @return the batch
	 */
	public Batch getBatch() {
		return this.batch;
	}

	/**
	 * @param batch
	 *            the batch to set
	 */
	public void setBatch(final Batch batch) {
		this.batch = batch;
	}

	public AcademicYear getAcademicYear() {
		return this.academicYear;
	}

	public void setAcademicYear(final AcademicYear academicYear) {
		this.academicYear = academicYear;
	}

	public Student getStudent() {
		return this.student;
	}

	public void setStudent(final Student student) {
		this.student = student;
	}

	/**
	 * @return the sequenceNr
	 */
	public Integer getSequenceNr() {
		return this.sequenceNr;
	}

	/**
	 * @param sequenceNr
	 *            the sequenceNr to set
	 */
	public void setSequenceNr(final Integer sequenceNr) {
		this.sequenceNr = sequenceNr;
	}

}
