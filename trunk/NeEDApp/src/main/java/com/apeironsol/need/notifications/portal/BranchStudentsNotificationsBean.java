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
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.academics.model.Exam;
import com.apeironsol.need.academics.model.SectionExam;
import com.apeironsol.need.academics.service.SectionExamService;
import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.portal.AbstractTabbedBean;
import com.apeironsol.need.core.service.AttendanceService;
import com.apeironsol.need.notifications.model.BatchLog;
import com.apeironsol.need.notifications.model.BatchLogMessage;
import com.apeironsol.need.notifications.model.BranchNotification;
import com.apeironsol.need.notifications.producer.util.BatchLogBuilder;
import com.apeironsol.need.notifications.service.BatchLogMessageService;
import com.apeironsol.need.notifications.service.BatchLogService;
import com.apeironsol.need.notifications.service.BranchNotificationService;
import com.apeironsol.need.notifications.service.NotificationService;
import com.apeironsol.need.util.DateUtil;
import com.apeironsol.need.util.constants.BatchLogMessageStatusConstant;
import com.apeironsol.need.util.constants.BatchStatusConstant;
import com.apeironsol.need.util.constants.NotificationLevelConstant;
import com.apeironsol.need.util.constants.NotificationSubTypeConstant;
import com.apeironsol.need.util.constants.NotificationTypeConstant;
import com.apeironsol.need.util.portal.ViewUtil;

/**
 * Managed bean for klass notifications.
 * 
 * @author Pradeep
 */
@Named
@Scope(value = "session")
public class BranchStudentsNotificationsBean extends AbstractTabbedBean {

	/**
	 * Unique serial version id for this class
	 */
	private static final long						serialVersionUID					= 6835124226503498607L;

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
	 * Batch logs send for the klass for entire academic year.
	 */
	private Collection<BatchLog>					klassBatchLogs						= new ArrayList<BatchLog>();

	/**
	 * Batch log messages for the klass for selected batch log.
	 */
	private Collection<BatchLogMessage>				klassBatchLogMessages				= new ArrayList<BatchLogMessage>();

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
	private ViewAction								viewActionString					= ViewAction.VIEW_BATCH_LOGS;

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
	private boolean									loadBatchLogsFromDB					= false;

	/**
	 * Indicator to specify if batch logs has to be fetched form DB.
	 */
	private boolean									loadBatchLogMessagesFromDB			= false;

	/**
	 * Boolean to indicate if current scheduled batch has finished.
	 */
	private boolean									batchFinished						= true;

	/**
	 * Number of elements processed.
	 */
	private long									elementsProcessed;

	@Resource
	private NotificationService						notificationService;

	private AcademicYear							academicYearForNotification;

	@Resource
	private BranchNotificationService				branchNotificationService;

	private Collection<BranchNotification>			branchNotifications;

	private Collection<NotificationSubTypeConstant>	notificationSubTypeAvailable;

	@Resource
	private AttendanceService						attendanceService;

	@Resource
	private SectionExamService						sectionExamService;

	private boolean									disaplyExamDropdownForNotification	= false;

	private Collection<Exam>						examsForNotification;

	private Exam									selectedExamForNotification;

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
	public BranchStudentsNotificationsBean() {
	}

	/**
	 * On tab change event.
	 */
	@Override
	public void onTabChange() {
		this.viewActionString = ViewAction.VIEW_BATCH_LOGS;
		this.loadBatchLogsFromDB = true;
		loadBranchNotification();
		getBranchNotificationByNotificationType();
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
				if (this.notificationSubTypeConstant.isMessageRequired() && ((this.notificationText == null) || this.notificationText.trim().isEmpty())) {
					ViewUtil.addMessage("Message required for this notification type.", FacesMessage.SEVERITY_ERROR);
					return null;
				}
				this.scheduledBatchLog = new BatchLogBuilder().branch(this.sessionBean.getCurrentBranch())
						.notificationLevelId(this.sessionBean.getCurrentBranch().getId()).notificationTypeConstant(this.notificationTypeConstant)
						.notificationLevelConstant(NotificationLevelConstant.BRANCH).notificationSubTypeConstant(this.notificationSubTypeConstant)
						.messageToBeSent(this.notificationText).exam(this.selectedExamForNotification).attendanceDate(DateUtil.getSystemDate()).build();

				this.scheduledBatchLog = this.notificationService.sendNotificationForStudent(this.academicYearForNotification.getId(), this.scheduledBatchLog);
			} catch (final Exception e) {
				final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
				addMessage(message);
			}
			setViewBatchLogs();
			this.loadBatchLogsFromDB = true;
			loadBatchLogsByKlassLevelAndKlassId();
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
	public void loadBatchLogsByKlassLevelAndKlassId() {
		if (this.loadBatchLogsFromDB) {
			setKlassBatchLogs(this.batchLogService.findBatchLogsByNotificationLevelAndNotificationLevelId(this.sessionBean.getCurrentBranch().getId(),
					NotificationLevelConstant.BRANCH, this.sessionBean.getCurrentBranch().getId()));
			this.loadBatchLogsFromDB = false;
		}
	}

	/**
	 * Fetch batch log messages for batch log from database.
	 */
	public void loadBatchLogMessagesByBatchLog() {
		if (this.loadBatchLogMessagesFromDB) {
			setKlassBatchLogMessages(this.batchLogMessageService.findBatchLogMessagesByBatchLogId(getBatchLog().getId()));
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
		clearBatchLogMesagesCollection();
		return null;
	}

	public void clearBatchLogMesagesCollection() {
		if ((this.klassBatchLogMessages != null) && !this.klassBatchLogMessages.isEmpty()) {
			this.klassBatchLogMessages.clear();
		}
	}

	public String setViewSendNotification() {
		this.academicYearForNotification = null;
		this.notificationTypeConstant = null;
		this.notificationSubTypeConstant = null;
		this.notificationText = null;
		this.viewActionString = ViewAction.VIEW_SEND_NOTIFICATION;
		this.selectedExamForNotification = null;
		this.disaplyExamDropdownForNotification = false;
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
				loadBatchLogsByKlassLevelAndKlassId();
			}
		}
	}

	/**
	 * Listener for Poll which checks for every few seconds to check if batch
	 * has finished.
	 */
	public void pollListener() {
		checkBatchStopped();
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
		if ((notificationText == null) || notificationText.trim().isEmpty()) {
			this.notificationText = null;
		} else {
			this.notificationText = notificationText;
		}
	}

	/**
	 * @return the academicYearForNotification
	 */
	public AcademicYear getAcademicYearForNotification() {
		return this.academicYearForNotification;
	}

	/**
	 * @param academicYearForNotification
	 *            the academicYearForNotification to set
	 */
	public void setAcademicYearForNotification(final AcademicYear academicYearForNotification) {
		this.academicYearForNotification = academicYearForNotification;
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

	public String handleAcademicYearChange() {
		this.notificationTypeConstant = null;
		handleNotificationTypeChange();
		return null;
	}

	public String handleNotificationTypeChange() {
		getBranchNotificationByNotificationType();
		this.notificationSubTypeConstant = null;
		handleNotificationSubTypeChange();
		return null;
	}

	public void getBranchNotificationByNotificationType() {
		this.notificationSubTypeAvailable = new ArrayList<NotificationSubTypeConstant>();
		for (final BranchNotification branchNotification : this.branchNotifications) {
			if (!branchNotification.getNotificationSubType().isImplicitMessage()) {
				if (NotificationTypeConstant.EMAIL_NOTIFICATION.equals(this.notificationTypeConstant)
						&& ((null != branchNotification.getEmailIndicator()) && branchNotification.getEmailIndicator())) {
					this.notificationSubTypeAvailable.add(branchNotification.getNotificationSubType());
				} else if (NotificationTypeConstant.SMS_NOTIFICATION.equals(this.notificationTypeConstant) && (null != branchNotification.getSmsIndicator())
						&& branchNotification.getSmsIndicator()) {
					this.notificationSubTypeAvailable.add(branchNotification.getNotificationSubType());
				} else if ((this.notificationTypeConstant == null)
						&& (((null != branchNotification.getSmsIndicator()) && branchNotification.getSmsIndicator()) || ((null != branchNotification
								.getEmailIndicator()) && branchNotification.getEmailIndicator()))) {
					this.notificationSubTypeAvailable.add(branchNotification.getNotificationSubType());
				}
			}
		}
	}

	public void loadBranchNotification() {
		setBranchNotifications(this.branchNotificationService.findBranchNotificationsByBranchId(this.sessionBean.getCurrentBranch().getId()));
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
		if (NotificationSubTypeConstant.EXAM_ABSENT_NOTIFICATION.equals(this.notificationSubTypeConstant)
				|| NotificationSubTypeConstant.EXAM_RESULT_NOTIFICATION.equals(this.notificationSubTypeConstant)
				|| NotificationSubTypeConstant.EXAM_SCHEDULE_NOTIFICATION.equals(this.notificationSubTypeConstant)) {
			this.disaplyExamDropdownForNotification = true;
			loadExamsForNotifications();
		} else {
			this.disaplyExamDropdownForNotification = false;
		}
		return null;
	}

	/**
	 * @return the disaplyExamDropdownForNotification
	 */
	public boolean isDisaplyExamDropdownForNotification() {
		return this.disaplyExamDropdownForNotification;
	}

	/**
	 * @param disaplyExamDropdownForNotification
	 *            the disaplyExamDropdownForNotification to set
	 */
	public void setDisaplyExamDropdownForNotification(final boolean disaplyExamDropdownForNotification) {
		this.disaplyExamDropdownForNotification = disaplyExamDropdownForNotification;
	}

	public void loadExamsForNotifications() {
		if (this.academicYearForNotification != null) {
			final Collection<SectionExam> sectionExams = this.sectionExamService.findSectionExamsByAcademicYearId(this.academicYearForNotification.getId());
			final Map<Long, Exam> examsMap = new HashMap<Long, Exam>();
			for (final SectionExam sectionExam : sectionExams) {
				if (examsMap.get(sectionExam.getExam().getId()) == null) {
					examsMap.put(sectionExam.getExam().getId(), sectionExam.getExam());
				}
			}
			this.examsForNotification = examsMap.values();
		}
	}

	/**
	 * @return the examsForNotification
	 */
	public Collection<Exam> getExamsForNotification() {
		return this.examsForNotification;
	}

	/**
	 * @param examsForNotification
	 *            the examsForNotification to set
	 */
	public void setExamsForNotification(final Collection<Exam> examsForNotification) {
		this.examsForNotification = examsForNotification;
	}

	/**
	 * @return the selectedExamForNotification
	 */
	public Exam getSelectedExamForNotification() {
		return this.selectedExamForNotification;
	}

	/**
	 * @param selectedExamForNotification
	 *            the selectedExamForNotification to set
	 */
	public void setSelectedExamForNotification(final Exam selectedExamForNotification) {
		this.selectedExamForNotification = selectedExamForNotification;
	}

}
