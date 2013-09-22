package com.apeironsol.need.core.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.apeironsol.framework.BaseEntity;


@Entity
@Table(name = "SECTION_TIMETABLE")
public class SectionTimetable extends BaseEntity {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long	serialVersionUID	= -5978402120005556231L;

	@NotNull(message = "model.section_subject")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SECTION_SUBJECT_ID", nullable = false)
	private SectionSubject		sectionSubject;

	@NotNull(message = "model.sectionTeacher")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SECTION_TEACHER_ID", nullable = false)
	private SectionTeacher		sectionTeacher;

	@Temporal(TemporalType.TIME)
	@Column(name = "START_TIME", nullable = false)
	private Date				startTime;

	@Temporal(TemporalType.TIME)
	@Column(name = "END_TIME", nullable = false)
	private Date				endTime;

	@Temporal(TemporalType.DATE)
	@Column(name = "SCHEDULE_DATE", nullable = false)
	private Date				scheduleDate;

	/**
	 * @return the sectionSubject
	 */
	public SectionSubject getSectionSubject() {
		return this.sectionSubject;
	}

	/**
	 * @param sectionSubject
	 *            the sectionSubject to set
	 */
	public void setSectionSubject(final SectionSubject sectionSubject) {
		this.sectionSubject = sectionSubject;
	}

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return this.startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(final Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return this.endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(final Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the sectionTeacher
	 */
	public SectionTeacher getSectionTeacher() {
		return this.sectionTeacher;
	}

	/**
	 * @param sectionTeacher
	 *            the sectionTeacher to set
	 */
	public void setSectionTeacher(final SectionTeacher sectionTeacher) {
		this.sectionTeacher = sectionTeacher;
	}

	/**
	 * @return the scheduleDate
	 */
	public Date getScheduleDate() {
		return this.scheduleDate;
	}

	/**
	 * @param scheduleDate
	 *            the scheduleDate to set
	 */
	public void setScheduleDate(final Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

}
