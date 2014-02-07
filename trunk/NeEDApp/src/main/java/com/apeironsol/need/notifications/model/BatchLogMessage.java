/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.notifications.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.apeironsol.framework.BaseEntity;
import com.apeironsol.need.core.model.Student;
import com.apeironsol.need.core.model.StudentAcademicYear;
import com.apeironsol.need.util.constants.BatchLogMessageStatusConstant;

@Entity
@Table(name = "BATCH_LOG_MESSAGE")
public class BatchLogMessage extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long				serialVersionUID	= 7316866807150964448L;

	@Column(name = "SEND_TO")
	private String							sendTo;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BATCH_ID")
	private BatchLog						batchLog;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STUDENT_ACADEMIC_YEAR_ID")
	private StudentAcademicYear				studentAcademicYear;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STUDENT_ID")
	private Student							student;

	@Lob
	@Column(name = "MESSAGE_SENT")
	private String							messageSent;

	@Lob
	@Column(name = "ERROR_MESSAGE")
	private String							errorMessage;

	@Column(name = "MESSAGE_STATUS")
	@Enumerated(EnumType.STRING)
	private BatchLogMessageStatusConstant	batchLogMessageStatusConstant;

	/**
	 * Batch execution start date and time.
	 */
	@Column(name = "MESSAGE_SENT_TIME", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date							messageSentTime;

	/**
	 * @return the messageSentTime
	 */
	public Date getMessageSentTime() {
		return this.messageSentTime;
	}

	/**
	 * @param messageSentTime
	 *            the messageSentTime to set
	 */
	public void setMessageSentTime(final Date messageSentTime) {
		this.messageSentTime = messageSentTime;
	}

	/**
	 * @return the batchLog
	 */
	public BatchLog getBatchLog() {
		return this.batchLog;
	}

	/**
	 * @param batchLog
	 *            the batchLog to set
	 */
	public void setBatchLog(final BatchLog batchLog) {
		this.batchLog = batchLog;
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
	 * @return the message
	 */
	public String getErrorMessage() {
		return this.errorMessage;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setErrorMessage(final String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the batchLogMessageStatusConstant
	 */
	public BatchLogMessageStatusConstant getBatchLogMessageStatusConstant() {
		return this.batchLogMessageStatusConstant;
	}

	/**
	 * @param batchLogMessageStatusConstant
	 *            the batchLogMessageStatusConstant to set
	 */
	public void setBatchLogMessageStatusConstant(final BatchLogMessageStatusConstant batchLogMessageStatusConstant) {
		this.batchLogMessageStatusConstant = batchLogMessageStatusConstant;
	}

	/**
	 * @return the messageSent
	 */
	public String getMessageSent() {
		return this.messageSent;
	}

	/**
	 * @param messageSent
	 *            the messageSent to set
	 */
	public void setMessageSent(final String messageSent) {
		this.messageSent = messageSent;
	}

	/**
	 * @return the sendTo
	 */
	public String getSendTo() {
		return this.sendTo;
	}

	/**
	 * @param sendTo
	 *            the sendTo to set
	 */
	public void setSendTo(final String sendTo) {
		this.sendTo = sendTo;
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
