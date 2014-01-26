/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.notifications.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.apeironsol.framework.BaseEntity;
import com.apeironsol.need.academics.model.ReportCard;
import com.apeironsol.need.academics.model.SectionExam;
import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.util.constants.BatchStatusConstant;
import com.apeironsol.need.util.constants.NotificationLevelConstant;
import com.apeironsol.need.util.constants.NotificationSubTypeConstant;
import com.apeironsol.need.util.constants.NotificationTypeConstant;

@Entity
@Table(name = "BATCH_LOG")
public class BatchLog extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long			serialVersionUID	= 7725885807828808804L;

	/**
	 * The number of elements.
	 */
	@Column(name = "NR_ELEMENTS", nullable = false)
	private Long						nrElements;

	/**
	 * The number of elements processed.
	 */
	@Column(name = "NR_ELEMENTS_PROC_SUCCESS")
	private Long						nrElementsProcessedSuccess;

	/**
	 * The number of elements processed.
	 */
	@Column(name = "NR_ELEMENTS_PROC_ERROR")
	private Long						nrElementsProcessedWithError;

	/**
	 * The number of elements processed.
	 */
	@Column(name = "NR_ELEMENTS_PROC_CANCELED")
	private Long						nrElementsProcessedWithCanceled;

	/**
	 * Batch status.
	 */
	@Basic
	@Column(name = "BATCH_STATUS", length = 50, nullable = false)
	@Enumerated(EnumType.STRING)
	private BatchStatusConstant			batchStatusConstant;

	/**
	 * Batch notification type.
	 */
	@Basic
	@Column(name = "BATCH_NOTIFICATION_TYPE", length = 50, nullable = false)
	@Enumerated(EnumType.STRING)
	private NotificationTypeConstant	notificationTypeConstant;

	/**
	 * Batch notification type.
	 */
	@Basic
	@Column(name = "BATCH_NOTIFICATION_SUB_TYPE", length = 50, nullable = false)
	@Enumerated(EnumType.STRING)
	private NotificationSubTypeConstant	notificationSubTypeConstant;

	/**
	 * Batch notification level.
	 */
	@Basic
	@Column(name = "BATCH_NOTIFICATION_LEVEL", length = 50, nullable = false)
	@Enumerated(EnumType.STRING)
	private NotificationLevelConstant	notificationLevelConstant;

	/**
	 * Indicator to specify if batch is completed.
	 */
	@Column(name = "COMPLETED")
	private Boolean						completedIndicator;

	/**
	 * Batch execution start date and time.
	 */
	@Column(name = "EXECUTION_START_DATE", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date						executionStartDate;

	/**
	 * Batch execution time.
	 */
	@Column(name = "EXECUTION_TIME")
	private Long						executionTime;

	@ManyToOne
	@JoinColumn(name = "BRANCH_ID")
	private Branch						branch;

	@Column(name = "NOTIFICATION_LEVEL_ID", nullable = false)
	private Long						notificationLevelId;

	@Lob
	@Column(name = "MESSAGE")
	private String						message;

	@Column(name = "STUDENT_FEE_TRANS_NR", length = 50)
	private String						studentFeeTransactionNr;

	/**
	 * Attendance Id.
	 */
	@Column(name = "ATTENDANCE_DATE")
	private Date						attendanceDate;

	/**
	 * Attendance Id.
	 */
	@ManyToOne
	@JoinColumn(name = "SECTION_EXAM_ID")
	private SectionExam					sectionExam;

	/**
	 * Attendance Id.
	 */
	@ManyToOne
	@JoinColumn(name = "REPORT_CARD_ID")
	private ReportCard					reportCard;

	/**
	 * Returns the number of elements.
	 * 
	 * @return Long the number of elements.
	 */
	public Long getNrElements() {
		return this.nrElements;
	}

	/**
	 * Sets the number of elements.
	 * 
	 * @param nrElements
	 *            Long the number of elements.
	 */
	public void setNrElements(final Long nrElements) {
		this.nrElements = nrElements;
	}

	/**
	 * Add the number of elements.
	 * 
	 * @param noOfElements
	 *            Long the number of elements to add.
	 */
	public void addNrElements(final long nrElements) {
		this.nrElements += nrElements;
	}

	/**
	 * @return the nrElementsProcessed
	 */
	public Long getNrElementsProcessed() {
		return (this.nrElementsProcessedSuccess != null ? this.nrElementsProcessedSuccess : 0)
				+ (this.nrElementsProcessedWithCanceled != null ? this.nrElementsProcessedWithCanceled : 0)
				+ (this.nrElementsProcessedWithError != null ? this.nrElementsProcessedWithError : 0);
	}

	/**
	 * @return the nrElementsProcessedSuccess
	 */
	public Long getNrElementsProcessedSuccess() {
		return this.nrElementsProcessedSuccess;
	}

	/**
	 * @param nrElementsProcessedSuccess
	 *            the nrElementsProcessedSuccess to set
	 */
	public void setNrElementsProcessedSuccess(final Long nrElementsProcessedSuccess) {
		this.nrElementsProcessedSuccess = nrElementsProcessedSuccess;
	}

	/**
	 * @return the nrElementsProcessedWithError
	 */
	public Long getNrElementsProcessedWithError() {
		return this.nrElementsProcessedWithError;
	}

	/**
	 * @param nrElementsProcessedWithError
	 *            the nrElementsProcessedWithError to set
	 */
	public void setNrElementsProcessedWithError(final Long nrElementsProcessedWithError) {
		this.nrElementsProcessedWithError = nrElementsProcessedWithError;
	}

	/**
	 * @return the batchStatusConstant
	 */
	public BatchStatusConstant getBatchStatusConstant() {
		return this.batchStatusConstant;
	}

	/**
	 * @param batchStatusConstant
	 *            the batchStatusConstant to set
	 */
	public void setBatchStatusConstant(final BatchStatusConstant batchStatusConstant) {
		this.batchStatusConstant = batchStatusConstant;
	}

	/**
	 * @return the executionStartDate
	 */
	public Date getExecutionStartDate() {
		return this.executionStartDate;
	}

	/**
	 * @param executionStartDate
	 *            the executionStartDate to set
	 */
	public void setExecutionStartDate(final Date executionStartDate) {
		this.executionStartDate = executionStartDate;
	}

	/**
	 * @return the executionTime
	 */
	public Long getExecutionTime() {
		return this.executionTime;
	}

	/**
	 * @param executionTime
	 *            the executionTime to set
	 */
	public void setExecutionTime(final Long executionTime) {
		this.executionTime = executionTime;
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
	 * @return the notificationLevelConstant
	 */
	public NotificationLevelConstant getNotificationLevelConstant() {
		return this.notificationLevelConstant;
	}

	/**
	 * @param notificationLevelConstant
	 *            the notificationLevelConstant to set
	 */
	public void setNotificationLevelConstant(final NotificationLevelConstant notificationLevelConstant) {
		this.notificationLevelConstant = notificationLevelConstant;
	}

	/**
	 * @return the completedIndicator
	 */
	public Boolean isCompletedIndicator() {
		return this.completedIndicator;
	}

	/**
	 * @param completedIndicator
	 *            the completedIndicator to set
	 */
	public void setCompletedIndicator(final Boolean completedIndicator) {
		this.completedIndicator = completedIndicator;
	}

	/**
	 * @return the branch
	 */
	public Branch getBranch() {
		return this.branch;
	}

	/**
	 * @param branch
	 *            the branch to set
	 */
	public void setBranch(final Branch branch) {
		this.branch = branch;
	}

	/**
	 * @return the nrElementsProcessedWithCanceled
	 */
	public Long getNrElementsProcessedWithCanceled() {
		return this.nrElementsProcessedWithCanceled;
	}

	/**
	 * @param nrElementsProcessedWithCanceled
	 *            the nrElementsProcessedWithCanceled to set
	 */
	public void setNrElementsProcessedWithCanceled(final Long nrElementsProcessedWithCanceled) {
		this.nrElementsProcessedWithCanceled = nrElementsProcessedWithCanceled;
	}

	/**
	 * @return the notificationLevelId
	 */
	public Long getNotificationLevelId() {
		return this.notificationLevelId;
	}

	/**
	 * @param notificationLevelId
	 *            the notificationLevelId to set
	 */
	public void setNotificationLevelId(final Long notificationLevelId) {
		this.notificationLevelId = notificationLevelId;
	}

	/**
	 * @return the messageSent
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * @param messageSent
	 *            the messageSent to set
	 */
	public void setMessage(final String messageSent) {
		this.message = messageSent;
	}

	/**
	 * @return the studentFeeTransactionNr
	 */
	public String getStudentFeeTransactionNr() {
		return this.studentFeeTransactionNr;
	}

	/**
	 * @param studentFeeTransactionNr
	 *            the studentFeeTransactionNr to set
	 */
	public void setStudentFeeTransactionNr(final String studentFeeTransactionNr) {
		this.studentFeeTransactionNr = studentFeeTransactionNr;
	}

	/**
	 * @return the sectionExam
	 */
	public SectionExam getSectionExam() {
		return this.sectionExam;
	}

	/**
	 * @param sectionExam
	 *            the sectionExam to set
	 */
	public void setSectionExam(final SectionExam sectionExam) {
		this.sectionExam = sectionExam;
	}

	/**
	 * @return the attendanceDate
	 */
	public Date getAttendanceDate() {
		return this.attendanceDate;
	}

	/**
	 * @param attendanceDate
	 *            the attendanceDate to set
	 */
	public void setAttendanceDate(final Date attendanceDate) {
		this.attendanceDate = attendanceDate;
	}

	/**
	 * @return the reportCard
	 */
	public ReportCard getReportCard() {
		return this.reportCard;
	}

	/**
	 * @param reportCard
	 *            the reportCard to set
	 */
	public void setReportCard(final ReportCard reportCard) {
		this.reportCard = reportCard;
	}

	/**
	 * @return the completedIndicator
	 */
	public Boolean getCompletedIndicator() {
		return this.completedIndicator;
	}

}
