package com.apeironsol.need.academics.model;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.util.constants.SectionExamStatusConstant;
import com.apeironsol.framework.BaseEntity;

@Entity
@Table(name = "SECTION_EXAM", uniqueConstraints = { @UniqueConstraint(columnNames = { "SECTION_ID", "EXAM_ID" }, name = "UQ1_SECTION_EXAM") })
public class SectionExam extends BaseEntity {

	/**
	 * Universal serial version id or this class.
	 */
	private static final long				serialVersionUID	= 5669999759765097024L;

	@NotNull(message = "model.section_mandatory")
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "SECTION_ID", nullable = false)
	private Section							section;

	@NotNull(message = "model.section_mandatory")
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "EXAM_ID", nullable = false)
	private Exam							exam;

	@NotNull(message = "model.exam_start_date_mandatory")
	@Column(name = "START_DATE", nullable = false)
	private Date							startDate;

	@NotNull(message = "model.exam_end_date_mandatory")
	@Column(name = "END_DATE", nullable = false)
	private Date							endDate;

	@Basic
	@Column(name = "SECTION_EXAM_STATUS", nullable = false)
	@Enumerated(EnumType.STRING)
	private SectionExamStatusConstant		sectionExamStatus;

	@OneToMany(mappedBy = "sectionExam", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.LAZY)
	private Collection<SectionExamSubject>	sectionExamSubjects;

	public Section getSection() {
		return this.section;
	}

	public void setSection(final Section section) {
		this.section = section;
	}

	public Exam getExam() {
		return this.exam;
	}

	public void setExam(final Exam exam) {
		this.exam = exam;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	public SectionExamStatusConstant getSectionExamStatus() {
		return this.sectionExamStatus;
	}

	public void setSectionExamStatus(final SectionExamStatusConstant sectionExamStatus) {
		this.sectionExamStatus = sectionExamStatus;
	}

	public Collection<SectionExamSubject> getSectionExamSubjects() {
		return this.sectionExamSubjects;
	}

	public void setSectionExamSubjects(final Collection<SectionExamSubject> sectionExamSubjects) {
		this.sectionExamSubjects = sectionExamSubjects;
	}

}
