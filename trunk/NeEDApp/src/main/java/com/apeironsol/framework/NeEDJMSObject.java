/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.framework;

import java.io.Serializable;

import com.apeironsol.need.core.model.SMSProvider;
import com.apeironsol.need.core.model.StudentAcademicYear;

public class NeEDJMSObject implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -5208354291419801716L;

	/**
	 * Student to whom notification has to be sent.
	 */
	private StudentAcademicYear	studentAcademicYear;

	/** The batch id. */
	private final Long			batchId;

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
	 * @param batchId
	 */
	public NeEDJMSObject(final Long batchId) {
		this.batchId = batchId;
	}

	/**
	 * Returns the batch id.
	 * 
	 * @return the batch id.
	 */
	public Long getBatchId() {
		return this.batchId;
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

}
