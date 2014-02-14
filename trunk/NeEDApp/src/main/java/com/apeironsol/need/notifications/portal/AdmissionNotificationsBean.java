/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.notifications.portal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.portal.AbstractTabbedBean;
import com.apeironsol.need.core.portal.AdmissionBean;
import com.apeironsol.need.notifications.model.BatchLogMessage;
import com.apeironsol.need.notifications.service.BatchLogMessageService;
import com.apeironsol.need.notifications.service.BatchLogService;
import com.apeironsol.need.util.comparator.BatchLogMessageComparator;

/**
 * Managed bean for section notifications.
 * 
 * @author Pradeep
 */
@Named
@Scope(value = "session")
public class AdmissionNotificationsBean extends AbstractTabbedBean {

	/**
	 * Unique serial version id for this class
	 */
	private static final long			serialVersionUID			= -3916949959837371784L;

	@Resource
	private AdmissionBean				admissionBean;

	/**
	 * Batch log messages for the section for selected batch log.
	 */
	private Collection<BatchLogMessage>	admissionBatchLogMessages	= new ArrayList<BatchLogMessage>();

	/**
	 * Batch log message service.
	 */
	@Resource
	private BatchLogMessageService		batchLogMessageService;

	/**
	 * Batch log service.
	 */
	@Resource
	private BatchLogService				batchLogService;

	/**
	 * Indicator to specify if batch logs has to be fetched form DB.
	 */
	private boolean						loadBatchLogMessagesFromDB	= false;

	/**
	 * Variable to hole batch log message error message.
	 */
	private String						batchLogMessageErrorMessage;

	/**
	 * Variable to hold batch log message sent.
	 */
	private String						batchLogMessageSentMessage;

	/**
	 * Default constructor.
	 */
	public AdmissionNotificationsBean() {
	}

	/**
	 * On tab change event.
	 */
	@Override
	public void onTabChange() {
	}

	/**
	 * Fetch batch log messages for batch log from database.
	 */
	public void loadBatchLogMessagesByStudent() {
		if (this.loadBatchLogMessagesFromDB) {
			this.setAdmissionBatchLogMessages(this.batchLogMessageService.findBatchLogMessagesByStudentId(this.admissionBean.getStudent().getId()));
			Collections.sort((List<BatchLogMessage>) this.getAdmissionBatchLogMessages(), new BatchLogMessageComparator(BatchLogMessageComparator.Order.ID));
			this.loadBatchLogMessagesFromDB = false;
		}
	}

	/**
	 * @return the sectionBatchLogMessages
	 */
	public Collection<BatchLogMessage> getAdmissionBatchLogMessages() {
		return this.admissionBatchLogMessages;
	}

	/**
	 * @param sectionBatchLogs
	 *            the sectionBatchLogs to set
	 */
	public void setAdmissionBatchLogMessages(final Collection<BatchLogMessage> batchLogMessages) {
		this.admissionBatchLogMessages = batchLogMessages;
	}

	/**
	 * @return the loadBatchLogMessagesFromDB
	 */
	public boolean isLoadBatchLogMessagesFromDB() {
		return this.loadBatchLogMessagesFromDB;
	}

	/**
	 * @param loadBatchLogMessagesFromDB
	 *            the loadBatchLogMessagesFromDB to set
	 */
	public void setLoadBatchLogMessagesFromDB(final boolean loadBatchLogMessagesFromDB) {
		this.loadBatchLogMessagesFromDB = loadBatchLogMessagesFromDB;
	}

	/**
	 * @return the batchLogMessageErrorMessage
	 */
	public String getBatchLogMessageErrorMessage() {
		return this.batchLogMessageErrorMessage;
	}

	/**
	 * @param batchLogMessageErrorMessage
	 *            the batchLogMessageErrorMessage to set
	 */
	public void setBatchLogMessageErrorMessage(final String batchLogMessageErrorMessage) {
		this.batchLogMessageErrorMessage = batchLogMessageErrorMessage;
	}

	/**
	 * @return the batchLogMessageSentMessage
	 */
	public String getBatchLogMessageSentMessage() {
		return this.batchLogMessageSentMessage;
	}

	/**
	 * @param batchLogMessageSentMessage
	 *            the batchLogMessageSentMessage to set
	 */
	public void setBatchLogMessageSentMessage(final String batchLogMessageSentMessage) {
		this.batchLogMessageSentMessage = batchLogMessageSentMessage;
	}

}
