package com.apeironsol.need.notifications.producer.util;

import java.util.Date;

import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.need.academics.model.Exam;
import com.apeironsol.need.academics.model.ReportCard;
import com.apeironsol.need.academics.model.SectionExam;
import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.core.model.SMSProvider;
import com.apeironsol.need.notifications.model.BatchLog;
import com.apeironsol.need.util.DateUtil;
import com.apeironsol.need.util.constants.BatchStatusConstant;
import com.apeironsol.need.util.constants.NotificationLevelConstant;
import com.apeironsol.need.util.constants.NotificationSentForConstant;
import com.apeironsol.need.util.constants.NotificationSubTypeConstant;
import com.apeironsol.need.util.constants.NotificationTypeConstant;

public class BatchLogBuilder {

	BatchLog							batchLog;

	private Long						nrElements;
	private NotificationTypeConstant	notificationTypeConstant;
	private NotificationSubTypeConstant	notificationSubTypeConstant;
	private NotificationSentForConstant	notificationSentFor;
	private NotificationLevelConstant	notificationLevelConstant;
	private Branch						branch;
	private Long						notificationLevelId;
	private String						studentFeeTransactionNr;
	private Date						attendanceDate;
	private SectionExam					sectionExam;
	private ReportCard					reportCard;
	private String						messageToBeSent;
	private Exam						exam;
	private SMSProvider					smsProvider;
	private AcademicYear				notificationSendForAcademicYear;

	public BatchLogBuilder messageToBeSent(final String messageToBeSent) {
		this.messageToBeSent = messageToBeSent;
		return this;
	}

	public BatchLogBuilder studentFeeTransactionNr(final String studentFeeTransactionNr) {
		this.studentFeeTransactionNr = studentFeeTransactionNr;
		return this;
	}

	public BatchLogBuilder notificationTypeConstant(final NotificationTypeConstant notificationTypeConstant) {
		this.notificationTypeConstant = notificationTypeConstant;
		return this;
	}

	public BatchLogBuilder notificationSubTypeConstant(final NotificationSubTypeConstant notificationSubTypeConstant) {
		this.notificationSubTypeConstant = notificationSubTypeConstant;
		return this;
	}

	public BatchLogBuilder notificationLevelConstant(final NotificationLevelConstant notificationLevelConstant) {
		this.notificationLevelConstant = notificationLevelConstant;
		return this;
	}

	public BatchLogBuilder branch(final Branch branch) {
		this.branch = branch;
		return this;
	}

	public BatchLogBuilder nrElements(final Long nrElements) {
		this.nrElements = nrElements;
		return this;
	}

	public BatchLogBuilder notificationLevelId(final Long notificationLevelId) {
		this.notificationLevelId = notificationLevelId;
		return this;
	}

	public BatchLogBuilder attendanceDate(final Date attendanceDate) {
		this.attendanceDate = attendanceDate;
		return this;
	}

	public BatchLogBuilder sectionExam(final SectionExam sectionExam) {
		this.sectionExam = sectionExam;
		return this;
	}

	public BatchLogBuilder reportCard(final ReportCard reportCard) {
		this.reportCard = reportCard;
		return this;
	}

	public BatchLogBuilder exam(final Exam exam) {
		this.exam = exam;
		return this;
	}

	public BatchLogBuilder smsProvider(final SMSProvider smsProvider) {
		this.smsProvider = smsProvider;
		return this;
	}

	public BatchLogBuilder notificationSentFor(final NotificationSentForConstant notificationSentFor) {
		this.notificationSentFor = notificationSentFor;
		return this;
	}

	public BatchLogBuilder notificationSendForAcademicYear(final AcademicYear academicYear) {
		this.notificationSendForAcademicYear = academicYear;
		return this;
	}

	public BatchLog build() {
		return this.getBatchLog(this);
	}

	private BatchLog getBatchLog(final BatchLogBuilder builder) {
		if (builder.branch == null) {
			throw new ApplicationException("Branch for BatchLog cannot be null");
		}
		final BatchLog batchLog = new BatchLog();
		batchLog.setStudentFeeTransactionNr(builder.studentFeeTransactionNr);
		batchLog.setNotificationTypeConstant(builder.notificationTypeConstant);
		batchLog.setNotificationSubTypeConstant(builder.notificationSubTypeConstant);
		batchLog.setNotificationLevelConstant(builder.notificationLevelConstant);
		batchLog.setBranch(builder.branch);
		batchLog.setNotificationLevelId(builder.notificationLevelId);
		batchLog.setAttendanceDate(builder.attendanceDate);
		batchLog.setSectionExam(builder.sectionExam);
		batchLog.setMessage(builder.messageToBeSent);
		batchLog.setReportCard(this.reportCard);
		batchLog.setNotificationSentFor(this.notificationSentFor);
		batchLog.setNotificationSendForAcademicYear(this.notificationSendForAcademicYear);
		batchLog.setExam(this.exam);

		if (this.nrElements != null) {
			batchLog.setNrElements(builder.nrElements);
			batchLog.setBatchStatusConstant(builder.nrElements > 0 ? BatchStatusConstant.CREATED : BatchStatusConstant.FINISHED);
			batchLog.setCompletedIndicator(builder.nrElements > 0 ? false : true);
		}
		if (this.smsProvider != null) {
			batchLog.setSmsProvider(this.smsProvider);
		} else {
			batchLog.setMessage("SMS Provider is not selected for the branch.");
			batchLog.setBatchStatusConstant(BatchStatusConstant.CANCELLED);
		}
		batchLog.setExecutionStartDate(DateUtil.getSystemDate());
		return batchLog;
	}
}
