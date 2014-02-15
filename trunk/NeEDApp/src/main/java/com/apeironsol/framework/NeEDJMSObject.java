/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.framework;

import java.io.Serializable;

import com.apeironsol.need.core.model.SMSProvider;
import com.apeironsol.need.core.model.Student;
import com.apeironsol.need.core.model.StudentAcademicYear;
import com.apeironsol.need.core.model.StudentRegistration;
import com.apeironsol.need.notifications.model.BatchLog;

public class NeEDJMSObject implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -5208354291419801716L;

	/**
	 * Student to whom notification has to be sent.
	 */
	private StudentAcademicYear	studentAcademicYear;

	/**
	 * Student to whom notification has to be sent.
	 */
	private Student				student;

	/** The batch id. */
	private Long				sequenceNr;

	/** The batch id. */
	private boolean				isLastMessage;

	/**
	 * User name of user logged into application.
	 */
	private String				userName;

	/**
	 * User name of user logged into application.
	 */
	private SMSProvider			smsProvider;

	/**
	 * User name of user logged into application.
	 */
	private final BatchLog		batchLog;

	/**
	 * Student to whom notification has to be sent.
	 */
	private StudentRegistration	studentRegistration;

	/**
	 * @param batchId
	 */
	public NeEDJMSObject(final BatchLog batchLog) {
		this.batchLog = batchLog;
	}

	/**
	 * Returns the studentAcademicYear.
	 * 
	 * @return the studentAcademicYear
	 */
	public StudentAcademicYear getStudentAcademicYear() {
		return this.studentAcademicYear;
	}

	/**
	 * Sets the studentAcademicYear.
	 * 
	 * @param student
	 *            the student to set
	 */
	public void setStudentAcademicYear(final StudentAcademicYear studentAcademicYear) {
		this.studentAcademicYear = studentAcademicYear;
	}

	/**
	 * @return the sequenceNr
	 */
	public Long getSequenceNr() {
		return this.sequenceNr;
	}

	/**
	 * @param sequenceNr
	 *            the sequenceNr to set
	 */
	public void setSequenceNr(final Long sequenceNr) {
		this.sequenceNr = sequenceNr;
	}

	/**
	 * @return the isLastMessage
	 */
	public boolean isLastMessage() {
		return this.isLastMessage;
	}

	/**
	 * @param isLastMessage
	 *            the isLastMessage to set
	 */
	public void setLastMessage(final boolean isLastMessage) {
		this.isLastMessage = isLastMessage;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return this.userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(final String userName) {
		this.userName = userName;
	}

	/**
	 * @return the smsProvider
	 */
	public SMSProvider getSmsProvider() {
		return this.smsProvider;
	}

	/**
	 * @param smsProvider
	 *            the smsProvider to set
	 */
	public void setSmsProvider(final SMSProvider smsProvider) {
		this.smsProvider = smsProvider;
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

	/**
	 * @return the batchLog
	 */
	public BatchLog getBatchLog() {
		return this.batchLog;
	}

	/**
	 * @return the studentRegistration
	 */
	public StudentRegistration getStudentRegistration() {
		return this.studentRegistration;
	}

	/**
	 * @param studentRegistration
	 *            the studentRegistration to set
	 */
	public void setStudentRegistration(final StudentRegistration studentRegistration) {
		this.studentRegistration = studentRegistration;
	}

}
