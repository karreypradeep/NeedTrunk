package com.apeironsol.need.notifications.consumers.worker.util;

import java.io.Serializable;

import com.apeironsol.need.util.constants.BatchLogMessageStatusConstant;

public class NotificationMessage implements Serializable {

	/**
	 * 
	 */
	private static final long				serialVersionUID	= -8534346817047111872L;

	private String							message;

	private BatchLogMessageStatusConstant	batchLogMessageStatus;

	private String							errorMessage;

	private String							sentAddress;

	/**
	 * @return the message
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(final String message) {
		this.message = message;
	}

	/**
	 * @return the batchLogMessageStatus
	 */
	public BatchLogMessageStatusConstant getBatchLogMessageStatus() {
		return this.batchLogMessageStatus;
	}

	/**
	 * @param batchLogMessageStatus
	 *            the batchLogMessageStatus to set
	 */
	public void setBatchLogMessageStatus(final BatchLogMessageStatusConstant batchLogMessageStatus) {
		this.batchLogMessageStatus = batchLogMessageStatus;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return this.errorMessage;
	}

	/**
	 * @param errorMessage
	 *            the errorMessage to set
	 */
	public void setErrorMessage(final String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the sentAddress
	 */
	public String getSentAddress() {
		return sentAddress;
	}

	/**
	 * @param sentAddress the sentAddress to set
	 */
	public void setSentAddress(String sentAddress) {
		this.sentAddress = sentAddress;
	}

}
