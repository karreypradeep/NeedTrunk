package com.apeironsol.need.core.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.apeironsol.need.util.constants.StudentSectionStatusConstant;
import com.apeironsol.framework.BaseEntity;

@Entity
@Table(name = "STUDENT_SECTION", uniqueConstraints = { @UniqueConstraint(columnNames = { "STUDENT_ACADEMIC_YEAR_ID", "SECTION_ID" }, name = "UQ_STUDENT_SECTION") })
public class StudentSection extends BaseEntity {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long				serialVersionUID	= -4879846476642525886L;

	@NotNull(message = "model.section_mandatory")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SECTION_ID", nullable = false)
	private Section							section;

	@NotNull(message = "model.student_academic_year_mandatory")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STUDENT_ACADEMIC_YEAR_ID", nullable = false)
	private StudentAcademicYear				studentAcademicYear;

	@Basic
	@Column(name = "STUDENT_SECTION_STATUS", nullable = false)
	@Enumerated(EnumType.STRING)
	private StudentSectionStatusConstant	studentSectionStatus;

	@Column(name = "SEQUENCE_NR", nullable = false)
	private Integer							sequenceNr;

	@OneToOne(optional = true)
	@JoinColumn(name = "PREVS_STU_SEC_ID")
	private StudentSection					previousStudentSection;

	@Temporal(TemporalType.DATE)
	@Column(name = "ACTION_DATE", nullable = false)
	private Date							actionDate;

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

	public Section getSection() {
		return this.section;
	}

	public void setSection(final Section section) {
		this.section = section;
	}

	public StudentSectionStatusConstant getStudentSectionStatus() {
		return this.studentSectionStatus;
	}

	public void setStudentSectionStatus(final StudentSectionStatusConstant studentClassSectionStatus) {
		this.studentSectionStatus = studentClassSectionStatus;
	}

	public StudentAcademicYear getStudentAcademicYear() {
		return this.studentAcademicYear;
	}

	public void setStudentAcademicYear(final StudentAcademicYear studentAcademicYear) {
		this.studentAcademicYear = studentAcademicYear;
	}

	/**
	 * @return the previousStudentSection
	 */
	public StudentSection getPreviousStudentSection() {
		return this.previousStudentSection;
	}

	/**
	 * @param previousStudentSection
	 *            the previousStudentSection to set
	 */
	public void setPreviousStudentSection(final StudentSection previousStudentSection) {
		this.previousStudentSection = previousStudentSection;
	}

	/**
	 * @return the actionDate
	 */
	public Date getActionDate() {
		return this.actionDate;
	}

	/**
	 * @param actionDate
	 *            the actionDate to set
	 */
	public void setActionDate(final Date actionDate) {
		this.actionDate = actionDate;
	}

}
