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
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.academics.model.Exam;
import com.apeironsol.need.academics.model.SectionExam;
import com.apeironsol.need.core.portal.AbstractNotificationBean;
import com.apeironsol.need.notifications.model.BatchLog;
import com.apeironsol.need.notifications.model.BatchLogMessage;
import com.apeironsol.need.notifications.producer.util.BatchLogBuilder;
import com.apeironsol.need.notifications.service.BatchLogMessageService;
import com.apeironsol.need.notifications.service.BatchLogService;
import com.apeironsol.need.util.DateUtil;
import com.apeironsol.need.util.comparator.BatchLogComparator;
import com.apeironsol.need.util.constants.BatchLogMessageStatusConstant;
import com.apeironsol.need.util.constants.BatchStatusConstant;
import com.apeironsol.need.util.constants.NotificationLevelConstant;
import com.apeironsol.need.util.constants.NotificationSentForConstant;
import com.apeironsol.need.util.constants.NotificationSubTypeConstant;
import com.apeironsol.need.util.portal.ViewUtil;

/**
 * Managed bean for klass notifications.
 * 
 * @author Pradeep
 */
@Named
@Scope(value = "session")
public class BranchStudentsNotificationsBean extends AbstractNotificationBean {

	/**
	 * Unique serial version id for this class
	 */
	private static final long			serialVersionUID			= 6835124226503498607L;

	/**
	 * Batch logs send for the klass for entire academic year.
	 */
	private Collection<BatchLog>		klassBatchLogs				= new ArrayList<BatchLog>();

	/**
	 * Batch log messages for the klass for selected batch log.
	 */
	private Collection<BatchLogMessage>	klassBatchLogMessages		= new ArrayList<BatchLogMessage>();

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
	private boolean						loadBatchLogsFromDB			= false;

	/**
	 * Indicator to specify if batch logs has to be fetched form DB.
	 */
	private boolean						loadBatchLogMessagesFromDB	= false;

	/**
	 * Boolean to indicate if current scheduled batch has finished.
	 */
	private boolean						batchFinished				= true;

	/**
	 * Number of elements processed.
	 */
	private long						elementsProcessed;

	/**
	 * Default constructor.
	 */
	public BranchStudentsNotificationsBean() {
	}

	/**
	 * @param batchFinished
	 *            the batchFinished to set
	 */
	public void setBatchFinished(final boolean batchFinished) {
		this.batchFinished = batchFinished;
	}

	/**
	 * On tab change event.
	 */
	@Override
	public void onTabChange() {
		this.setViewActionString(ViewAction.VIEW_BATCH_LOGS);
		this.loadBatchLogsFromDB = true;
		this.loadBranchNotification();
		this.getBranchNotificationByNotificationType();
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
		if (this.getNotificationTypeConstant() == null) {
			ViewUtil.addMessage("Please select notification type.", FacesMessage.SEVERITY_ERROR);
			return null;
		} else if (this.getNotificationSubTypeConstant() == null) {
			ViewUtil.addMessage("Please select notifications sub type.", FacesMessage.SEVERITY_ERROR);
			return null;
		} else {
			try {
				if (this.getNotificationSubTypeConstant().isMessageRequired()
						&& ((this.getNotificationText() == null) || this.getNotificationText().trim().isEmpty())) {
					ViewUtil.addMessage("Message required for this notification type.", FacesMessage.SEVERITY_ERROR);
					return null;
				}
				this.scheduledBatchLog = new BatchLogBuilder().branch(this.sessionBean.getCurrentBranch())
						.notificationLevelId(this.sessionBean.getCurrentBranch().getId()).notificationTypeConstant(this.getNotificationTypeConstant())
						.notificationSendForAcademicYear(this.getAcademicYearForNotification()).notificationLevelConstant(NotificationLevelConstant.BRANCH)
						.notificationSubTypeConstant(this.getNotificationSubTypeConstant()).messageToBeSent(this.getNotificationText())
						.exam(this.getSelectedExamForNotification()).attendanceDate(DateUtil.getSystemDate())
						.notificationSendForAcademicYear(this.getAcademicYearForNotification()).notificationSentFor(NotificationSentForConstant.STUDENTS)
						.smsProvider(this.sessionBean.getCurrentBranchRule().getSmsProvider()).build();

				ViewUtil.addMessage("Notifications are sent for processing.", FacesMessage.SEVERITY_INFO);
				this.setViewBatchLogs();
				this.loadBatchLogsFromDB = true;
				this.batchFinished = false;
				this.elementsProcessed = 0;

				this.scheduledBatchLog = this.notificationService.sendNotificationForStudent(this.getAcademicYearForNotification().getId(),
						this.scheduledBatchLog);

			} catch (final Exception e) {
				final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
				this.addMessage(message);
			}
			this.loadBatchLogsByKlassLevelAndKlassId();
		}
		return null;
	}

	/**
	 * Load batch logs from database.
	 */
	public void loadBatchLogsByKlassLevelAndKlassId() {
		if (this.loadBatchLogsFromDB) {
			this.setKlassBatchLogs(this.batchLogService.findBatchLogsByNotificationLevelAndNotificationLevelId(this.sessionBean.getCurrentBranch().getId(),
					NotificationLevelConstant.BRANCH, this.sessionBean.getCurrentBranch().getId(), NotificationSubTypeConstant.getBrachNotifications(),
					NotificationSentForConstant.STUDENTS));
			Collections.sort((List<BatchLog>) this.getKlassBatchLogs(), new BatchLogComparator(BatchLogComparator.Order.ID));
			this.loadBatchLogsFromDB = false;
		}
	}

	/**
	 * Fetch batch log messages for batch log from database.
	 */
	public void loadBatchLogMessagesByBatchLog() {
		if (this.loadBatchLogMessagesFromDB) {
			this.setKlassBatchLogMessages(this.batchLogMessageService.findBatchLogMessagesByBatchLogId(this.getBatchLog().getId()));
			this.loadBatchLogMessagesFromDB = false;
		}
	}

	/**
	 * @return the klassBatchLogs
	 */
	public Collection<BatchLog> getKlassBatchLogs() {
		return this.klassBatchLogs;
	}

	/**
	 * @param klassBatchLogs
	 *            the klassBatchLogs to set
	 */
	public void setKlassBatchLogs(final Collection<BatchLog> klassBatchLogs) {
		this.klassBatchLogs = klassBatchLogs;
	}

	/**
	 * @return the klassBatchLogMessages
	 */
	public Collection<BatchLogMessage> getKlassBatchLogMessages() {
		return this.klassBatchLogMessages;
	}

	/**
	 * @param klassBatchLogs
	 *            the klassBatchLogs to set
	 */
	public void setKlassBatchLogMessages(final Collection<BatchLogMessage> batchLogMessages) {
		this.klassBatchLogMessages = batchLogMessages;
	}

	public String setViewBatchLogs() {
		this.setViewActionString(ViewAction.VIEW_BATCH_LOGS);
		return null;
	}

	public String setViewBatchLogMessages() {
		this.setViewActionString(ViewAction.VIEW_BATCH_LOG_MESSAGES);
		this.clearBatchLogMesagesCollection();
		return null;
	}

	public void clearBatchLogMesagesCollection() {
		if ((this.klassBatchLogMessages != null) && !this.klassBatchLogMessages.isEmpty()) {
			this.klassBatchLogMessages.clear();
		}
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
		if ((this.scheduledBatchLog != null) && ((this.scheduledBatchLog.getId()) != null)) {
			this.scheduledBatchLog = this.batchLogService.findBatchLogById(this.scheduledBatchLog.getId());
			if (BatchStatusConstant.CREATED.equals(this.scheduledBatchLog.getBatchStatusConstant())
					|| BatchStatusConstant.DISTRIBUTED.equals(this.scheduledBatchLog.getBatchStatusConstant())) {
				this.batchFinished = false;
			} else {
				this.batchLog = this.scheduledBatchLog;
				this.scheduledBatchLog = null;
				this.loadBatchLogsFromDB = true;
				this.loadBatchLogsByKlassLevelAndKlassId();
			}
		} else if ((this.scheduledBatchLog != null)
				&& ((this.scheduledBatchLog.getId() == null) && (BatchStatusConstant.CREATED.equals(this.scheduledBatchLog.getBatchStatusConstant()) || BatchStatusConstant.DISTRIBUTED
						.equals(this.scheduledBatchLog.getBatchStatusConstant())))) {
			this.batchFinished = false;
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
				final long totalElements = this.scheduledBatchLog.getNrElements();
				final Long processedElements = this.batchLogMessageService.findNumberOfBatchLogMessagesByBatchLogIdAndStatus(this.scheduledBatchLog.getId(),
						EnumSet.allOf(BatchLogMessageStatusConstant.class));
				this.elementsProcessed = processedElements != null ? processedElements : 0;
				progress = totalElements > 0 ? Long.valueOf((this.elementsProcessed * 100) / totalElements).intValue() : 100;
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
		return 45;
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

	@Override
	public void loadExamsForNotifications() {
		this.setSelectedExamForNotification(null);
		if (this.getExamsForNotification() != null) {
			this.getExamsForNotification().clear();
		}
		if (this.getAcademicYearForNotification() != null) {
			final Collection<SectionExam> sectionExams = this.sectionExamService
					.findSectionExamsByAcademicYearId(this.getAcademicYearForNotification().getId());
			final Map<Long, Exam> examsMap = new HashMap<Long, Exam>();
			for (final SectionExam sectionExam : sectionExams) {
				if (examsMap.get(sectionExam.getExam().getId()) == null) {
					if (NotificationSubTypeConstant.EXAM_ABSENT_NOTIFICATION.equals(this.getNotificationSubTypeConstant())
							|| NotificationSubTypeConstant.EXAM_RESULT_NOTIFICATION.equals(this.getNotificationSubTypeConstant())) {
						if (sectionExam.getEndDate().before(DateUtil.getSystemDate())) {
							examsMap.put(sectionExam.getExam().getId(), sectionExam.getExam());
						}
					} else if (NotificationSubTypeConstant.EXAM_SCHEDULE_NOTIFICATION.equals(this.getNotificationSubTypeConstant())) {
						if (!sectionExam.getStartDate().before(DateUtil.getSystemDate())) {
							examsMap.put(sectionExam.getExam().getId(), sectionExam.getExam());
						}
					}
				}
			}
			this.setExamsForNotification(examsMap.values());
		}
	}

}
