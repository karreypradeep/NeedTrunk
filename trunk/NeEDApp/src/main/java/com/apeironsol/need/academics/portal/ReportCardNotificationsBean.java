/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.academics.portal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.portal.AbstractTabbedBean;
import com.apeironsol.need.notifications.model.BatchLog;
import com.apeironsol.need.notifications.model.BatchLogMessage;
import com.apeironsol.need.notifications.producer.util.BatchLogBuilder;
import com.apeironsol.need.notifications.service.BatchLogMessageService;
import com.apeironsol.need.notifications.service.BatchLogService;
import com.apeironsol.need.notifications.service.NotificationService;
import com.apeironsol.need.util.DateUtil;
import com.apeironsol.need.util.constants.BatchLogMessageStatusConstant;
import com.apeironsol.need.util.constants.BatchStatusConstant;
import com.apeironsol.need.util.constants.NotificationLevelConstant;
import com.apeironsol.need.util.constants.NotificationSubTypeConstant;
import com.apeironsol.need.util.constants.NotificationTypeConstant;

/**
 * Managed bean for klass notifications.
 * 
 * @author Pradeep
 */
@Named
@Scope(value = "session")
public class ReportCardNotificationsBean extends AbstractTabbedBean {

	/**
	 * Unique serial version id for this class
	 */
	private static final long			serialVersionUID				= -393945274413948139L;

	/**
	 * Batch logs send for the klass for entire academic year.
	 */
	private Collection<BatchLog>		batchReportCardBatchLogs		= new ArrayList<BatchLog>();

	/**
	 * Batch log messages for the klass for selected batch log.
	 */
	private Collection<BatchLogMessage>	batchReportCardBatchLogMessages	= new ArrayList<BatchLogMessage>();

	/**
	 * Batch log service.
	 */
	@Resource
	private BatchLogService				batchLogService;

	/**
	 * Batch log message service.
	 */
	@Resource
	private BatchLogMessageService		batchLogMessageService;

	/**
	 * Batch log.
	 */
	private BatchLog					batchLog;

	/**
	 * Scheduled batch log.
	 */
	private BatchLog					scheduledBatchLog;

	/**
	 * Variable to decide what has to be displayed.
	 */
	private ViewAction					viewActionString				= ViewAction.VIEW_BATCH_LOGS;

	/**
	 * Variable to hole batch log message error message.
	 */
	private String						batchLogMessageErrorMessage;

	/**
	 * Variable to hold batch log message sent.
	 */
	private String						batchLogMessageSentMessage;

	/**
	 * Indicator to specify if batch logs has to be fetched form DB.
	 */
	private boolean						loadBatchLogsFromDB				= false;

	/**
	 * Indicator to specify if batch logs has to be fetched form DB.
	 */
	private boolean						loadBatchLogMessagesFromDB		= false;

	/**
	 * Boolean to indicate if current scheduled batch has finished.
	 */
	private boolean						batchFinished					= true;

	/**
	 * Number of elements processed.
	 */
	private long						elementsProcessed;

	@Resource
	private NotificationService			notificationService;

	@Resource
	private ReportCardBean				reportCardBean;

	/**
	 * Enum class used for deciding what has to be displayed on screen.
	 * 
	 * @author pradeep
	 * 
	 */
	public enum ViewAction {
		VIEW_BATCH_LOGS, VIEW_BATCH_LOG_MESSAGES, VIEW_SEND_NOTIFICATION;
	}

	/**
	 * Default constructor.
	 */
	public ReportCardNotificationsBean() {
	}

	/**
	 * On tab change event.
	 */
	@Override
	public void onTabChange() {
		this.viewActionString = ViewAction.VIEW_BATCH_LOGS;
		this.loadBatchLogsFromDB = true;
	}

	/**
	 * Adds a message to faces context.
	 * 
	 * @param message
	 *            message to display.
	 */
	private void addMessage(final FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	/**
	 * Send notification to JMS queue.
	 * 
	 * @return
	 */
	public String sendNotification() {
		try {
			this.scheduledBatchLog = new BatchLogBuilder().branch(this.sessionBean.getCurrentBranch())
					.notificationLevelId(this.sessionBean.getCurrentBranch().getId()).notificationTypeConstant(NotificationTypeConstant.SMS_NOTIFICATION)
					.notificationLevelConstant(NotificationLevelConstant.BRANCH).reportCard(this.reportCardBean.getReportCard())
					.notificationSubTypeConstant(NotificationSubTypeConstant.REPORT_CARD_NOTIFICATION).attendanceDate(DateUtil.getSystemDate()).build();
			this.scheduledBatchLog = this.notificationService.sendReportCardNotificationForStudent(this.scheduledBatchLog);
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
			this.addMessage(message);
		}
		this.setViewBatchLogs();
		this.loadBatchLogsFromDB = true;
		this.loadBatchLogsByBranchLevelAndReportCardId();
		this.batchFinished = false;
		this.elementsProcessed = 0;
		return null;
	}

	/**
	 * Load batch logs from database.
	 */
	public void loadBatchLogsByBranchLevelAndReportCardId() {
		if (this.loadBatchLogsFromDB) {
			this.setBatchReportCardBatchLogs(this.batchLogService.findBatchLogsForReportCardByNotificationLevelAndNotificationLevelId(this.sessionBean
					.getCurrentBranch().getId(), NotificationLevelConstant.BRANCH, this.sessionBean.getCurrentBranch().getId(), this.reportCardBean
					.getReportCard().getId()));
			this.loadBatchLogsFromDB = false;
		}
	}

	/**
	 * Fetch batch log messages for batch log from database.
	 */
	public void loadBatchLogMessagesByBatchLog() {
		if (this.loadBatchLogMessagesFromDB) {
			this.setBatchReportCardBatchLogMessages(this.batchLogMessageService.findBatchLogMessagesByBatchLogId(this.getBatchLog().getId()));
			this.loadBatchLogMessagesFromDB = false;
		}
	}

	/**
	 * @return the viewActionString
	 */
	public ViewAction getViewActionString() {
		return this.viewActionString;
	}

	/**
	 * @param viewActionString
	 *            the viewActionString to set
	 */
	public void setViewActionString(final ViewAction viewActionString) {
		this.viewActionString = viewActionString;
	}

	public String setViewBatchLogs() {
		this.viewActionString = ViewAction.VIEW_BATCH_LOGS;
		return null;
	}

	public String setViewBatchLogMessages() {
		this.viewActionString = ViewAction.VIEW_BATCH_LOG_MESSAGES;
		this.clearBatchLogMesagesCollection();
		return null;
	}

	public void clearBatchLogMesagesCollection() {
		if (this.batchReportCardBatchLogs != null && !this.batchReportCardBatchLogs.isEmpty()) {
			this.batchReportCardBatchLogs.clear();
		}
	}

	public String setViewSendNotification() {
		this.viewActionString = ViewAction.VIEW_SEND_NOTIFICATION;
		return null;
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

	/**
	 * @return the loadBatchLogsFromDB
	 */
	public boolean isLoadBatchLogsFromDB() {
		return this.loadBatchLogsFromDB;
	}

	/**
	 * @param loadBatchLogsFromDB
	 *            the loadBatchLogsFromDB to set
	 */
	public void setLoadBatchLogsFromDB(final boolean loadBatchLogsFromDB) {
		this.loadBatchLogsFromDB = loadBatchLogsFromDB;
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
	 * Checks if the current batch has finished.
	 */
	public void checkBatchStopped() {
		this.batchFinished = true;
		if (this.scheduledBatchLog != null) {
			this.scheduledBatchLog = this.batchLogService.findBatchLogById(this.scheduledBatchLog.getId());
			if (BatchStatusConstant.CREATED.equals(this.scheduledBatchLog.getBatchStatusConstant())
					|| BatchStatusConstant.DISTRIBUTED.equals(this.scheduledBatchLog.getBatchStatusConstant())) {
				this.batchFinished = false;
			} else {
				this.batchLog = this.scheduledBatchLog;
				this.scheduledBatchLog = null;
				this.loadBatchLogsFromDB = true;
				this.loadBatchLogsByBranchLevelAndReportCardId();
			}
		}
	}

	/**
	 * Listener for Poll which checks for every few seconds to check if batch
	 * has finished.
	 */
	public void pollListener() {
		this.checkBatchStopped();
	}

	/**
	 * Returns true if batch has finished executing.
	 * 
	 * @return
	 */
	public boolean isBatchFinished() {
		return this.batchFinished;
	}

	/**
	 * Returns progress bar value indicating percentage of batch finished.
	 * 
	 * @return
	 */
	public Integer getProgressBarValue() {
		int progress = 0;
		if (this.scheduledBatchLog != null) {
			this.scheduledBatchLog = this.batchLogService.findBatchLogById(this.scheduledBatchLog.getId());
			if (BatchStatusConstant.FINISHED.equals(this.scheduledBatchLog.getBatchStatusConstant())) {
				progress = 100;
				this.batchFinished = true;
			} else {
				long totalElements = this.scheduledBatchLog.getNrElements();
				Long processedElements = this.batchLogMessageService.findNumberOfBatchLogMessagesByBatchLogIdAndStatus(this.scheduledBatchLog.getId(),
						EnumSet.allOf(BatchLogMessageStatusConstant.class));
				this.elementsProcessed = processedElements != null ? processedElements : 0;
				progress = totalElements > 0 ? Long.valueOf(this.elementsProcessed * 100 / totalElements).intValue() : 100;
			}

		}
		if (progress == 100) {
			this.batchFinished = true;
		}
		return progress == 0 ? 1 : progress;

	}

	/**
	 * Interval at which pool should check for status of scheduled batch.
	 * 
	 * @return
	 */
	public int getBatchPollInterval() {
		int interval = 15;
		if (this.scheduledBatchLog != null) {
			interval = 10;
		}
		return interval;
	}

	/**
	 * @return the scheduledBatchLog
	 */
	public BatchLog getScheduledBatchLog() {
		return this.scheduledBatchLog;
	}

	/**
	 * @param scheduledBatchLog
	 *            the scheduledBatchLog to set
	 */
	public void setScheduledBatchLog(final BatchLog scheduledBatchLog) {
		this.scheduledBatchLog = scheduledBatchLog;
	}

	/**
	 * @return the elementsProcessed
	 */
	public long getElementsProcessed() {
		return this.elementsProcessed;
	}

	/**
	 * @param elementsProcessed
	 *            the elementsProcessed to set
	 */
	public void setElementsProcessed(final long elementsProcessed) {
		this.elementsProcessed = elementsProcessed;
	}

	/**
	 * @return the batchReportCardBatchLogs
	 */
	public Collection<BatchLog> getBatchReportCardBatchLogs() {
		return this.batchReportCardBatchLogs;
	}

	/**
	 * @return the batchReportCardBatchLogMessages
	 */
	public Collection<BatchLogMessage> getBatchReportCardBatchLogMessages() {
		return this.batchReportCardBatchLogMessages;
	}

	/**
	 * @param batchReportCardBatchLogMessages
	 *            the batchReportCardBatchLogMessages to set
	 */
	public void setBatchReportCardBatchLogMessages(final Collection<BatchLogMessage> batchReportCardBatchLogMessages) {
		this.batchReportCardBatchLogMessages = batchReportCardBatchLogMessages;
	}

	/**
	 * @param batchReportCardBatchLogs
	 *            the batchReportCardBatchLogs to set
	 */
	public void setBatchReportCardBatchLogs(final Collection<BatchLog> batchReportCardBatchLogs) {
		this.batchReportCardBatchLogs = batchReportCardBatchLogs;
	}

}
