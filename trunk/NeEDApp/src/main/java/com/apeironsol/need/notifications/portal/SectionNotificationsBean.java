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
import com.apeironsol.need.core.model.Attendance;
import com.apeironsol.need.core.portal.AbstractNotificationBean;
import com.apeironsol.need.core.portal.SectionBean;
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
import com.apeironsol.need.util.constants.NotificationSubTypeConstant;
import com.apeironsol.need.util.portal.ViewUtil;

/**
 * Managed bean for section notifications.
 * 
 * @author Pradeep
 */
@Named
@Scope(value = "session")
public class SectionNotificationsBean extends AbstractNotificationBean {

	/**
	 * Unique serial version id for this class
	 */
	private static final long			serialVersionUID			= 514816731312124801L;

	/**
	 * Batch logs send for the section for entire academic year.
	 */
	private Collection<BatchLog>		sectionBatchLogs			= new ArrayList<BatchLog>();

	/**
	 * Batch log messages for the section for selected batch log.
	 */
	private Collection<BatchLogMessage>	sectionBatchLogMessages		= new ArrayList<BatchLogMessage>();

	/**
	 * Section bean.
	 */
	@Resource
	private SectionBean					sectionBean;

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
	public SectionNotificationsBean() {
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
				} else if (this.getNotificationSubTypeConstant().equals(NotificationSubTypeConstant.ABSENT_NOTIFICATION)) {
					final Attendance attendance = this.attendanceService.findAttendanceBySectionIdAndAttendanceDateForDailyAttendance(this.sectionBean
							.getSection().getId(), DateUtil.getSystemDate());
					if (attendance == null) {
						ViewUtil.addMessage("Attendance not taken for today. Could not send absent notifications.", FacesMessage.SEVERITY_ERROR);
						return null;
					}
				}
				this.scheduledBatchLog = new BatchLogBuilder().branch(this.sessionBean.getCurrentBranch())
						.notificationLevelId(this.sectionBean.getSection().getId()).notificationTypeConstant(this.getNotificationTypeConstant())
						.notificationLevelConstant(NotificationLevelConstant.SECTION).notificationSubTypeConstant(this.getNotificationSubTypeConstant())
						.messageToBeSent(this.getNotificationText()).exam(this.getSelectedExamForNotification()).attendanceDate(DateUtil.getSystemDate())
						.smsProvider(this.sessionBean.getCurrentBranchRule().getSmsProvider()).build();

				ViewUtil.addMessage("Notifications are sent for processing.", FacesMessage.SEVERITY_INFO);
				this.setViewBatchLogs();
				this.loadBatchLogsFromDB = true;
				this.batchFinished = false;
				this.elementsProcessed = 0;

				this.scheduledBatchLog = this.notificationService.sendNotificationForStudent(this.sectionBean.getSection(), this.scheduledBatchLog);
			} catch (final Exception e) {
				final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
				this.addMessage(message);
			}
			this.loadBatchLogsBySectionLevelAndSectionId();
		}
		return null;
	}

	/**
	 * Load batch logs from database.
	 */
	public void loadBatchLogsBySectionLevelAndSectionId() {
		if (this.loadBatchLogsFromDB) {
			this.setSectionBatchLogs(this.batchLogService.findBatchLogsByNotificationLevelAndNotificationLevelId(this.sessionBean.getCurrentBranch().getId(),
					NotificationLevelConstant.SECTION, this.sectionBean.getSection().getId()));
			Collections.sort((List<BatchLog>) this.getSectionBatchLogs(), new BatchLogComparator(BatchLogComparator.Order.ID));
			this.loadBatchLogsFromDB = false;
		}
	}

	/**
	 * Fetch batch log messages for batch log from database.
	 */
	public void loadBatchLogMessagesByBatchLog() {
		if (this.loadBatchLogMessagesFromDB) {
			this.setSectionBatchLogMessages(this.batchLogMessageService.findBatchLogMessagesByBatchLogId(this.getBatchLog().getId()));
			this.loadBatchLogMessagesFromDB = false;
		}
	}

	/**
	 * @return the sectionBatchLogs
	 */
	public Collection<BatchLog> getSectionBatchLogs() {
		return this.sectionBatchLogs;
	}

	/**
	 * @param sectionBatchLogs
	 *            the sectionBatchLogs to set
	 */
	public void setSectionBatchLogs(final Collection<BatchLog> sectionBatchLogs) {
		this.sectionBatchLogs = sectionBatchLogs;
	}

	/**
	 * @return the sectionBatchLogMessages
	 */
	public Collection<BatchLogMessage> getSectionBatchLogMessages() {
		return this.sectionBatchLogMessages;
	}

	/**
	 * @param sectionBatchLogs
	 *            the sectionBatchLogs to set
	 */
	public void setSectionBatchLogMessages(final Collection<BatchLogMessage> batchLogMessages) {
		this.sectionBatchLogMessages = batchLogMessages;
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
		if ((this.sectionBatchLogMessages != null) && !this.sectionBatchLogMessages.isEmpty()) {
			this.sectionBatchLogMessages.clear();
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
		if ((this.scheduledBatchLog != null) && (this.scheduledBatchLog.getId() != null)) {
			this.scheduledBatchLog = this.batchLogService.findBatchLogById(this.scheduledBatchLog.getId());
			if (BatchStatusConstant.CREATED.equals(this.scheduledBatchLog.getBatchStatusConstant())
					|| BatchStatusConstant.DISTRIBUTED.equals(this.scheduledBatchLog.getBatchStatusConstant())) {
				this.batchFinished = false;
			} else {
				this.batchLog = this.scheduledBatchLog;
				this.scheduledBatchLog = null;
				this.loadBatchLogsFromDB = true;
				this.loadBatchLogsBySectionLevelAndSectionId();
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
				progress = totalElements > 0 ? Long.valueOf((this.elementsProcessed * 100) / totalElements).intValue() : 1;
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
		return 30;
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
		final Collection<SectionExam> sectionExams = this.sectionExamService.findSectionExamsBySectionId(this.sectionBean.getSection().getId());
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

	/**
	 * @param batchFinished
	 *            the batchFinished to set
	 */
	public void setBatchFinished(final boolean batchFinished) {
		this.batchFinished = batchFinished;
	}

}
