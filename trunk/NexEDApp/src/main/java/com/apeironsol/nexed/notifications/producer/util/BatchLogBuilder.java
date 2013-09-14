package com.apeironsol.nexed.notifications.producer.util;

import java.util.Date;

import com.apeironsol.nexed.academics.model.SectionExam;
import com.apeironsol.nexed.core.model.Branch;
import com.apeironsol.nexed.notifications.model.BatchLog;
import com.apeironsol.nexed.util.DateUtil;
import com.apeironsol.nexed.util.constants.BatchStatusConstant;
import com.apeironsol.nexed.util.constants.NotificationLevelConstant;
import com.apeironsol.nexed.util.constants.NotificationSubTypeConstant;
import com.apeironsol.nexed.util.constants.NotificationTypeConstant;

public class BatchLogBuilder {

	BatchLog							batchLog;

	private Long						nrElements;
	private NotificationTypeConstant	notificationTypeConstant;
	private NotificationSubTypeConstant	notificationSubTypeConstant;
	private NotificationLevelConstant	notificationLevelConstant;
	private Branch						branch;
	private Long						notificationLevelId;
	private String						studentFeeTransactionNr;
	private Date						attendanceDate;
	private SectionExam					sectionExam;
	private String						messageToBeSent;

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

	public BatchLog build() {
		return this.getBatchLog(this);
	}

	private BatchLog getBatchLog(final BatchLogBuilder builder) {
		BatchLog batchLog = new BatchLog();
		batchLog.setStudentFeeTransactionNr(builder.studentFeeTransactionNr);
		batchLog.setNotificationTypeConstant(builder.notificationTypeConstant);
		batchLog.setNotificationSubTypeConstant(builder.notificationSubTypeConstant);
		batchLog.setNotificationLevelConstant(builder.notificationLevelConstant);
		batchLog.setBranch(builder.branch);
		batchLog.setNotificationLevelId(builder.notificationLevelId);
		batchLog.setAttendanceDate(builder.attendanceDate);
		batchLog.setSectionExam(builder.sectionExam);
		batchLog.setMessage(builder.messageToBeSent);
		if (this.nrElements != null) {
			batchLog.setNrElements(builder.nrElements);
			batchLog.setBatchStatusConstant(builder.nrElements > 0 ? BatchStatusConstant.CREATED : BatchStatusConstant.FINISHED);
			batchLog.setCompletedIndicator(builder.nrElements > 0 ? false : true);
		}
		batchLog.setExecutionStartDate(DateUtil.getSystemDate());
		return batchLog;
	}

}
