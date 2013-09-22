/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.notifications.portal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.academics.model.SectionExam;
import com.apeironsol.need.academics.service.SectionExamService;
import com.apeironsol.need.core.portal.AbstractTabbedBean;
import com.apeironsol.need.core.portal.SectionBean;
import com.apeironsol.need.notifications.model.BatchLog;
import com.apeironsol.need.notifications.model.BatchLogMessage;
import com.apeironsol.need.notifications.model.BranchNotification;
import com.apeironsol.need.notifications.producer.util.BatchLogBuilder;
import com.apeironsol.need.notifications.service.BatchLogMessageService;
import com.apeironsol.need.notifications.service.BatchLogService;
import com.apeironsol.need.notifications.service.BranchNotificationService;
import com.apeironsol.need.notifications.service.NotificationService;
import com.apeironsol.need.util.constants.BatchLogMessageStatusConstant;
import com.apeironsol.need.util.constants.BatchStatusConstant;
import com.apeironsol.need.util.constants.NotificationLevelConstant;
import com.apeironsol.need.util.constants.NotificationSubTypeConstant;
import com.apeironsol.need.util.constants.NotificationTypeConstant;
import com.apeironsol.need.util.portal.ViewUtil;

/**
 * Managed bean for section notifications.
 * 
 * @author Pradeep
 */
@Named
@Scope(value = "session")
public class SectionNotificationsBean extends AbstractTabbedBean {

	/**
	 * Unique serial version id for this class
	 */
	private static final long						serialVersionUID			= 514816731312124801L;

	/**
	 * Notification sub type.
	 */
	private NotificationSubTypeConstant				notificationSubTypeConstant;

	private NotificationTypeConstant				notificationTypeConstant;

	/**
	 * Notification sub type.
	 */
	private String									notificationText;

	/**
	 * Batch logs send for the section for entire academic year.
	 */
	private Collection<BatchLog>					sectionBatchLogs			= new ArrayList<BatchLog>();

	/**
	 * Batch log messages for the section for selected batch log.
	 */
	private Collection<BatchLogMessage>				sectionBatchLogMessages		= new ArrayList<BatchLogMessage>();

	/**
	 * Section bean.
	 */
	@Resource
	private SectionBean								sectionBean;

	/**
	 * Batch log service.
	 */
	@Resource
	private BatchLogService							batchLogService;

	/**
	 * Batch log message service.
	 */
	@Resource
	private BatchLogMessageService					batchLogMessageService;

	/**
	 * Batch log.
	 */
	private BatchLog								batchLog;

	/**
	 * Scheduled batch log.
	 */
	private BatchLog								scheduledBatchLog;

	/**
	 * Variable to decide what has to be displayed.
	 */
	private ViewAction								viewActionString			= ViewAction.VIEW_BATCH_LOGS;

	/**
	 * Variable to hole batch log message error message.
	 */
	private String									batchLogMessageErrorMessage;

	/**
	 * Variable to hold batch log message sent.
	 */
	private String									batchLogMessageSentMessage;

	/**
	 * Indicator to specify if batch logs has to be fetched form DB.
	 */
	private boolean									loadBatchLogsFromDB			= false;

	/**
	 * Indicator to specify if batch logs has to be fetched form DB.
	 */
	private boolean									loadBatchLogMessagesFromDB	= false;

	/**
	 * Boolean to indicate if current scheduled batch has finished.
	 */
	private boolean									batchFinished				= true;

	/**
	 * Number of elements processed.
	 */
	private long									elementsProcessed;

	@Resource
	private NotificationService						notificationService;

	@Resource
	private BranchNotificationService				branchNotificationService;

	private Collection<BranchNotification>			branchNotifications;

	private Collection<NotificationSubTypeConstant>	notificationSubTypeAvailable;

	/**
	 * Section Exam service.
	 */
	@Resource
	private SectionExamService						sectionExamService;

	private Collection<SectionExam>					sectionExams;

	/**
	 * Boolean to indicate if current scheduled batch has finished.
	 */
	private boolean									renderSectionExamIndicator	= false;

	private SectionExam								selectedSectionExam;

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
	public SectionNotificationsBean() {
	}

	/**
	 * On tab change event.
	 */
	@Override
	public void onTabChange() {
		this.viewActionString = ViewAction.VIEW_BATCH_LOGS;
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
		if (this.notificationTypeConstant == null) {
			ViewUtil.addMessage("Please select notification type.", FacesMessage.SEVERITY_ERROR);
			return null;
		} else if (this.notificationSubTypeConstant == null) {
			ViewUtil.addMessage("Please select notifications sub type.", FacesMessage.SEVERITY_ERROR);
			return null;
		} else {
			try {
				if (this.notificationSubTypeConstant.isMessageRequired() && (this.notificationText == null || this.notificationText.trim().isEmpty())) {
					ViewUtil.addMessage("Message required for this notification type.", FacesMessage.SEVERITY_ERROR);
					return null;
				}
				this.scheduledBatchLog = new BatchLogBuilder().branch(this.sessionBean.getCurrentBranch())
						.notificationLevelId(this.sectionBean.getSection().getId()).notificationTypeConstant(this.notificationTypeConstant)
						.notificationLevelConstant(NotificationLevelConstant.SECTION).notificationSubTypeConstant(this.notificationSubTypeConstant)
						.messageToBeSent(this.notificationText).build();

				this.scheduledBatchLog = this.notificationService.sendNotificationForStudent(this.sectionBean.getSection(), this.scheduledBatchLog);
			} catch (Exception e) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
				this.addMessage(message);
			}
			this.setViewBatchLogs();
			this.loadBatchLogsFromDB = true;
			this.loadBatchLogsBySectionLevelAndSectionId();
			this.batchFinished = false;
			this.elementsProcessed = 0;
		}
		return null;
	}

	/**
	 * @return the notificationSubTypeConstant
	 */
	public NotificationSubTypeConstant getNotificationSubTypeConstant() {
		return this.notificationSubTypeConstant;
	}

	/**
	 * @param notificationSubTypeConstant
	 *            the notificationSubTypeConstant to set
	 */
	public void setNotificationSubTypeConstant(final NotificationSubTypeConstant notificationSubTypeConstant) {
		this.notificationSubTypeConstant = notificationSubTypeConstant;
	}

	/**
	 * Load batch logs from database.
	 */
	public void loadBatchLogsBySectionLevelAndSectionId() {
		if (this.loadBatchLogsFromDB) {
			this.setSectionBatchLogs(this.batchLogService.findBatchLogsByNotificationLevelAndNotificationLevelId(this.sessionBean.getCurrentBranch().getId(),
					NotificationLevelConstant.SECTION, this.sectionBean.getSection().getId()));
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
		if (this.sectionBatchLogMessages != null && !this.sectionBatchLogMessages.isEmpty()) {
			this.sectionBatchLogMessages.clear();
		}
	}

	public String setViewSendNotification() {
		this.notificationTypeConstant = null;
		this.notificationSubTypeConstant = null;
		this.notificationText = null;
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
				this.loadBatchLogsBySectionLevelAndSectionId();
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
			} else {
				long totalElements = this.scheduledBatchLog.getNrElements();
				Long processedElements = this.batchLogMessageService.findNumberOfBatchLogMessagesByBatchLogIdAndStatus(this.scheduledBatchLog.getId(),
						EnumSet.allOf(BatchLogMessageStatusConstant.class));
				this.elementsProcessed = processedElements != null ? processedElements : 0;
				progress = Long.valueOf(this.elementsProcessed * 100 / totalElements).intValue();
			}

		}
		return progress == 0 ? 1 : progress;

	}

	/**
	 * Interval at which pool should check for status of scheduled batch.
	 * 
	 * @return
	 */
	public int getBatchPollInterval() {
		int interval = 0;
		if (this.scheduledBatchLog != null) {
			interval = 5;
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
	 * @return the notificationText
	 */
	public String getNotificationText() {
		return this.notificationText;
	}

	/**
	 * @param notificationText
	 *            the notificationText to set
	 */
	public void setNotificationText(final String notificationText) {
		if (notificationText == null || notificationText.trim().isEmpty()) {
			this.notificationText = null;
		} else {
			this.notificationText = notificationText;
		}
	}

	/**
	 * @return the notificationTypeConstant
	 */
	public NotificationTypeConstant getNotificationTypeConstant() {
		return this.notificationTypeConstant;
	}

	/**
	 * @param notificationTypeConstant
	 *            the notificationTypeConstant to set
	 */
	public void setNotificationTypeConstant(final NotificationTypeConstant notificationTypeConstant) {
		this.notificationTypeConstant = notificationTypeConstant;
	}

	public String handleNotificationTypeChange() {
		this.getBranchNotificationByNotificationType();
		this.notificationSubTypeConstant = null;
		this.handleNotificationSubTypeChange();
		return null;
	}

	public void getBranchNotificationByNotificationType() {
		this.notificationSubTypeAvailable = new ArrayList<NotificationSubTypeConstant>();
		for (BranchNotification branchNotification : this.branchNotifications) {
			if (!branchNotification.getNotificationSubType().isImplicitMessage()) {
				if (NotificationTypeConstant.EMAIL_NOTIFICATION.equals(this.notificationTypeConstant) && branchNotification.getEmailIndicator()) {
					this.notificationSubTypeAvailable.add(branchNotification.getNotificationSubType());
				} else if (NotificationTypeConstant.SMS_NOTIFICATION.equals(this.notificationTypeConstant) && branchNotification.getSmsIndicator()) {
					this.notificationSubTypeAvailable.add(branchNotification.getNotificationSubType());
				} else if (this.notificationTypeConstant == null && (branchNotification.getSmsIndicator() || branchNotification.getEmailIndicator())) {
					this.notificationSubTypeAvailable.add(branchNotification.getNotificationSubType());
				}
			}
		}
	}

	public void loadBranchNotification() {
		this.setBranchNotifications(this.branchNotificationService.findBranchNotificationsByBranchId(this.sessionBean.getCurrentBranch().getId()));
	}

	/**
	 * @return the branchNotifications
	 */
	public Collection<BranchNotification> getBranchNotifications() {
		return this.branchNotifications;
	}

	/**
	 * @param branchNotifications
	 *            the branchNotifications to set
	 */
	public void setBranchNotifications(final Collection<BranchNotification> branchNotifications) {
		this.branchNotifications = branchNotifications;
	}

	/**
	 * @return the notificationSubTypeAvailable
	 */
	public Collection<NotificationSubTypeConstant> getNotificationSubTypeAvailable() {
		return this.notificationSubTypeAvailable;
	}

	/**
	 * @param notificationSubTypeAvailable
	 *            the notificationSubTypeAvailable to set
	 */
	public void setNotificationSubTypeAvailable(final Collection<NotificationSubTypeConstant> notificationSubTypeAvailable) {
		this.notificationSubTypeAvailable = notificationSubTypeAvailable;
	}

	/**
	 * Event listener for Notification sub type.
	 * 
	 * @return
	 */
	public String handleNotificationSubTypeChange() {
		this.selectedSectionExam = null;
		if (this.getSectionExams() != null) {
			this.getSectionExams().clear();
		}
		if (NotificationSubTypeConstant.EXAM_ABSENT_NOTIFICATION.equals(this.notificationSubTypeConstant)
				|| NotificationSubTypeConstant.EXAM_SCHEDULE_NOTIFICATION.equals(this.notificationSubTypeConstant)) {
			this.setSectionExams(this.sectionExamService.findSectionExamsBySectionId(this.sectionBean.getSection().getId()));
			this.renderSectionExamIndicator = true;
		} else {
			this.renderSectionExamIndicator = false;
		}
		return null;
	}

	/**
	 * @return the sectionExams
	 */
	public Collection<SectionExam> getSectionExams() {
		return this.sectionExams;
	}

	/**
	 * @param sectionExams
	 *            the sectionExams to set
	 */
	public void setSectionExams(final Collection<SectionExam> sectionExams) {
		this.sectionExams = sectionExams;
	}

	/**
	 * @return the renderSectionExamIndicator
	 */
	public boolean isRenderSectionExamIndicator() {
		return this.renderSectionExamIndicator;
	}

	/**
	 * @param renderSectionExamIndicator
	 *            the renderSectionExamIndicator to set
	 */
	public void setRenderSectionExamIndicator(final boolean renderSectionExamIndicator) {
		this.renderSectionExamIndicator = renderSectionExamIndicator;
	}

	/**
	 * @return the selectedsectionExam
	 */
	public SectionExam getSelectedSectionExam() {
		return this.selectedSectionExam;
	}

	/**
	 * @param selectedsectionExam
	 *            the selectedsectionExam to set
	 */
	public void setSelectedSectionExam(final SectionExam selectedSectionExam) {
		this.selectedSectionExam = selectedSectionExam;
	}

}
