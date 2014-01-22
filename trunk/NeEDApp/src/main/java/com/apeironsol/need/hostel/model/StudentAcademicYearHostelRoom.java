/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.hostel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.apeironsol.framework.BaseEntity;
import com.apeironsol.need.core.model.StudentAcademicYear;

/**
 * Entity class for Employee
 * 
 * @author Pradeep
 */
@Entity
@Table(name = "STU_ACDE_YEAR_HOSTEL_ROOM")
public class StudentAcademicYearHostelRoom extends BaseEntity {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long	serialVersionUID	= 1388451419017780068L;

	@NotNull(message = "model.studentAcademicYear_id_mandatory")
	@ManyToOne
	@JoinColumn(name = "STUDENT_ACADEMIC_YEAR_ID", nullable = false)
	private StudentAcademicYear	studentAcademicYear;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_DATE", nullable = false)
	private Date				startDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_DATE")
	private Date				endDate;

	@NotNull(message = "model.hostelRoom_id_mandatory")
	@ManyToOne
	@JoinColumn(name = "HOSTEL_ROOM_ID", nullable = false)
	private HostelRoom			hostelRoom;

	@Column(name = "BED_NUMBER")
	private Integer				bedNumber;

	@Column(name = "REASON_FOR_LEAVING", length = 200, nullable = false)
	private String				reasonForLeaving;

	/**
	 * @return the reasonForLeaving
	 */
	public String getReasonForLeaving() {
		return this.reasonForLeaving;
	}

	/**
	 * @param reasonForLeaving
	 *            the reasonForLeaving to set
	 */
	public void setReasonForLeaving(final String reasonForLeaving) {
		this.reasonForLeaving = reasonForLeaving;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return this.startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return this.endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the studentAcademicYear
	 */
	public StudentAcademicYear getStudentAcademicYear() {
		return this.studentAcademicYear;
	}

	/**
	 * @param studentAcademicYear
	 *            the studentAcademicYear to set
	 */
	public void setStudentAcademicYear(final StudentAcademicYear studentAcademicYear) {
		this.studentAcademicYear = studentAcademicYear;
	}

	/**
	 * @return the hostelRoom
	 */
	public HostelRoom getHostelRoom() {
		return this.hostelRoom;
	}

	/**
	 * @param hostelRoom
	 *            the hostelRoom to set
	 */
	public void setHostelRoom(final HostelRoom hostelRoom) {
		this.hostelRoom = hostelRoom;
	}

	/**
	 * @return the bedNumber
	 */
	public Integer getBedNumber() {
		return this.bedNumber;
	}

	/**
	 * @param bedNumber
	 *            the bedNumber to set
	 */
	public void setBedNumber(final Integer bedNumber) {
		this.bedNumber = bedNumber;
	}

}
