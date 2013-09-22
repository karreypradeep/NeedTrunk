package com.apeironsol.need.core.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.apeironsol.need.util.constants.AttendanceTypeConstant;
import com.apeironsol.framework.BaseEntity;

@Entity
@Table(name = "ATTENDANCE")
public class Attendance extends BaseEntity {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long		serialVersionUID	= -1305096337437915725L;

	@NotNull(message = "model.section")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SECTION_ID", nullable = false)
	private Section					section;

	@Column(name = "ATTENDANCE_DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date					attendanceDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "ATTENDANCE_TYPE", nullable = false)
	private AttendanceTypeConstant	attendanceType;

	@Column(name = "SUBMITTED_BY", nullable = false)
	private String					submittedBy;

	@Column(name = "SUBMITTED_DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date					submittedDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SECTION_SUBJECT_ID")
	private SectionSubject			sectionSubject;

	@Column(name = "LOCKED")
	private Boolean					locked;

	public Section getSection() {
		return this.section;
	}

	public void setSection(final Section section) {
		this.section = section;
	}

	/**
	 * @return the attendanceDate
	 */
	public Date getAttendanceDate() {
		return this.attendanceDate;
	}

	/**
	 * @param attendanceDate
	 *            the attendanceDate to set
	 */
	public void setAttendanceDate(final Date attendanceDate) {
		this.attendanceDate = attendanceDate;
	}

	/**
	 * @return the attendanceType
	 */
	public AttendanceTypeConstant getAttendanceType() {
		return this.attendanceType;
	}

	/**
	 * @param attendanceType
	 *            the attendanceType to set
	 */
	public void setAttendanceType(final AttendanceTypeConstant attendanceType) {
		this.attendanceType = attendanceType;
	}

	/**
	 * @return the submittedBy
	 */
	public String getSubmittedBy() {
		return this.submittedBy;
	}

	/**
	 * @param submittedBy
	 *            the submittedBy to set
	 */
	public void setSubmittedBy(final String submittedBy) {
		this.submittedBy = submittedBy;
	}

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
	 * @return the locked
	 */
	public Boolean getLocked() {
		return this.locked;
	}

	/**
	 * @param locked
	 *            the locked to set
	 */
	public void setLocked(final Boolean locked) {
		this.locked = locked;
	}

	/**
	 * @return the submittedDate
	 */
	public Date getSubmittedDate() {
		return this.submittedDate;
	}

	/**
	 * @param submittedDate
	 *            the submittedDate to set
	 */
	public void setSubmittedDate(final Date submittedDate) {
		this.submittedDate = submittedDate;
	}

}
