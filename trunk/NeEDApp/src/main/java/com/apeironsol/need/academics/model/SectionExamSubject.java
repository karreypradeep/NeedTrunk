package com.apeironsol.need.academics.model;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.apeironsol.need.core.model.SectionSubject;
import com.apeironsol.framework.BaseEntity;

@Entity
@Table(name = "SECTION_EXAM_SUBJECT")
public class SectionExamSubject extends BaseEntity {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long				serialVersionUID	= -7508140517570820988L;

	@NotNull(message = "model.section_exam_mandatory")
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "SECTION_EXAM_ID", nullable = false)
	private SectionExam						sectionExam;

	@NotNull(message = "model.section_subject_mandatory")
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "SECTION_SUBJECT_ID", nullable = false)
	private SectionSubject					sectionSubject;

	@Temporal(TemporalType.DATE)
	@NotNull(message = "model.exam_start_date_mandatory")
	@Column(name = "SCHEDULED_DATE", nullable = false)
	private Date							scheduledDate;

	@Temporal(TemporalType.TIME)
	@NotNull(message = "model.exam_start_date_mandatory")
	@Column(name = "START_TIME", nullable = false)
	private Date							startTime;

	@Temporal(TemporalType.TIME)
	@NotNull(message = "model.exam_start_date_mandatory")
	@Column(name = "END_TIME", nullable = false)
	private Date							endTime;

	@NotNull(message = "model.maxumum_marks_mandatory")
	@Column(name = "MAX_MARKS", nullable = false, length = 4)
	private Double							maximumMarks;

	@NotNull(message = "model.pass_marks_mandatory")
	@Column(name = "PASS_MARKS", nullable = false, length = 4)
	private Double							passMarks;

	@OneToMany(mappedBy = "sectionExamSubject", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.LAZY)
	private Collection<StudentExamSubject>	studentExamSubjects;

	public SectionExam getSectionExam() {
		return this.sectionExam;
	}

	public void setSectionExam(final SectionExam sectionExam) {
		this.sectionExam = sectionExam;
	}

	public SectionSubject getSectionSubject() {
		return this.sectionSubject;
	}

	public void setSectionSubject(final SectionSubject sectionSubject) {
		this.sectionSubject = sectionSubject;
	}

	public Date getScheduledDate() {
		return this.scheduledDate;
	}

	public void setScheduledDate(final Date scheduledDate) {
		this.scheduledDate = scheduledDate;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(final Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(final Date endTime) {
		this.endTime = endTime;
	}

	public Double getMaximumMarks() {
		return this.maximumMarks;
	}

	public void setMaximumMarks(final Double maximumMarks) {
		this.maximumMarks = maximumMarks;
	}

	public Double getPassMarks() {
		return this.passMarks;
	}

	public void setPassMarks(final Double passMarks) {
		this.passMarks = passMarks;
	}

	public Collection<StudentExamSubject> getStudentExamSubjects() {
		return this.studentExamSubjects;
	}

	public void setStudentExamSubjects(final Collection<StudentExamSubject> studentExamSubjects) {
		this.studentExamSubjects = studentExamSubjects;
	}

}
