package com.apeironsol.need.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.apeironsol.framework.BaseEntity;

@Entity
@Table(name = "STUDENT_ABSENT", uniqueConstraints = { @UniqueConstraint(columnNames = { "STUDENT_ACADEMIC_YEAR_ID", "ATTENDANCE_ID" }, name = "UQ_STUDENT_ATTENDANCE") })
public class StudentAbsent extends BaseEntity {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long	serialVersionUID	= -5104615522198664955L;

	@NotNull(message = "model.student_academic_year")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STUDENT_ACADEMIC_YEAR_ID", nullable = false)
	private StudentAcademicYear	studentAcademicYear;

	@Column(name = "ABSENT_REASON")
	private String				absentReason;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ATTENDANCE_ID", nullable = false)
	private Attendance			attendance;

	public StudentAcademicYear getStudentAcademicYear() {
		return this.studentAcademicYear;
	}

	public void setStudentAcademicYear(final StudentAcademicYear studentAcademicYear) {
		this.studentAcademicYear = studentAcademicYear;
	}

	/**
	 * @return the absentReason
	 */
	public String getAbsentReason() {
		return this.absentReason;
	}

	/**
	 * @param absentReason
	 *            the absentReason to set
	 */
	public void setAbsentReason(final String absentReason) {
		this.absentReason = absentReason;
	}

	/**
	 * @return the attendance
	 */
	public Attendance getAttendance() {
		return this.attendance;
	}

	/**
	 * @param attendance
	 *            the attendance to set
	 */
	public void setAttendance(final Attendance attendance) {
		this.attendance = attendance;
	}

}
