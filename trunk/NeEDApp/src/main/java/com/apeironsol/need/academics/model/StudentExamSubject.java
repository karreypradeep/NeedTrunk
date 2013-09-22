package com.apeironsol.need.academics.model;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.apeironsol.need.core.model.StudentAcademicYear;
import com.apeironsol.need.util.constants.StudentExamSubjectStatusConstant;
import com.apeironsol.framework.BaseEntity;

@Entity
@Table(name = "STUDENT_EXAM_SUBJECT")
public class StudentExamSubject extends BaseEntity {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long					serialVersionUID	= -7508140517570820988L;

	@NotNull(message = "model.section_exam_mandatory")
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "STUDENT_ACADEMIC_YEAR_ID", nullable = false)
	private StudentAcademicYear					studentAcademicYear;

	@NotNull(message = "model.section_exam_mandatory")
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "SECTION_EXAM_SUBJECT_ID", nullable = false)
	private SectionExamSubject					sectionExamSubject;

	@Column(name = "SCORED_MARKS", length = 4)
	private Double								scoredMarks;

	@Basic
	@Column(name = "STUDENT_EXAM_STATUS", nullable = false)
	@Enumerated(EnumType.STRING)
	private StudentExamSubjectStatusConstant	studentExamSubjectStatus;

	public StudentAcademicYear getStudentAcademicYear() {
		return this.studentAcademicYear;
	}

	public void setStudentAcademicYear(final StudentAcademicYear studentAcademicYear) {
		this.studentAcademicYear = studentAcademicYear;
	}

	public SectionExamSubject getSectionExamSubject() {
		return this.sectionExamSubject;
	}

	public void setSectionExamSubject(final SectionExamSubject sectionExamSubject) {
		this.sectionExamSubject = sectionExamSubject;
	}

	public Double getScoredMarks() {
		return this.scoredMarks;
	}

	public void setScoredMarks(final Double scoredMarks) {
		this.scoredMarks = scoredMarks;
	}

	public StudentExamSubjectStatusConstant getStudentExamSubjectStatus() {
		return this.studentExamSubjectStatus;
	}

	public void setStudentExamSubjectStatus(final StudentExamSubjectStatusConstant studentExamSubjectStatus) {
		this.studentExamSubjectStatus = studentExamSubjectStatus;
	}
}
