/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.notifications.consumers.email;

import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.mail.MessagingException;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;

import com.apeironsol.need.notifications.consumers.worker.email.EmailWorker;
import com.apeironsol.need.notifications.consumers.worker.email.EmailWorkerFactory;
import com.apeironsol.need.notifications.consumers.worker.util.NotificationMessage;
import com.apeironsol.need.notifications.model.BatchLog;
import com.apeironsol.need.notifications.model.BatchLogMessage;
import com.apeironsol.need.notifications.service.BatchLogMessageService;
import com.apeironsol.need.notifications.service.BatchLogService;
import com.apeironsol.need.util.DateUtil;
import com.apeironsol.need.util.constants.BatchLogMessageStatusConstant;
import com.apeironsol.need.util.constants.BatchStatusConstant;
import com.apeironsol.framework.NeEDJMSObject;

/**
 * Class for sending email notification for student pending fee.
 * 
 * @author Pradeep
 */
@Component
public class EmailConsumer implements SessionAwareMessageListener<Message> {

	@Resource
	private BatchLogMessageService	batchLogMessageService;

	/**
	 * JMSTemplate for sending messages.
	 */
	@Resource(name = "jmsTemplateEmail")
	JmsTemplate						jmsTemplate;

	/** The message priority. */
	private static final short		HIGH_PRIORITY	= 5;

	@Resource
	private BatchLogService			batchLogService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onMessage(final Message message, final Session session) throws JMSException {
		if (message instanceof TextMessage) {
			System.out.println(((TextMessage) message).getText() + " onMessage arg1 " + session.getAcknowledgeMode());
		} else if (message instanceof ObjectMessage) {
			NeEDJMSObject jmsObject = (NeEDJMSObject) ((ObjectMessage) message).getObject();
			BatchLog batchLog = this.batchLogService.findBatchLogById(jmsObject.getBatchId());
			if (this.isCanceled(batchLog)) {
				this.postProcessElement(jmsObject, batchLog,
						this.createNotificationMessage("Batch Cancelled", "Batch Cancelled", BatchLogMessageStatusConstant.CANCELLED));
			} else {

				if (jmsObject.isLastMessage()) {
					Collection<BatchLogMessage> batchLogMessages = this.batchLogMessageService.findBatchLogMessagesByBatchLogId(batchLog.getId());
					final long totalBatchLogMessages = batchLogMessages != null ? batchLogMessages.size() : 0;
					if (totalBatchLogMessages != batchLog.getNrElements().longValue()) {
						this.resendLastMessage(batchLog);
					} else if (batchLog.getNrElements().longValue() > 0) {
						this.updateBatchLogToFinished(batchLog);
					}
				} else {
					try {
						this.processBatchMesssage(jmsObject, batchLog);
					} catch (Exception e) {
						throw new JMSException(e.getMessage());
					}
				}
			}
		}

	}

	/**
	 * Re send the last message with high priority to update the batch status.
	 * 
	 * @param batchLog
	 *            batch log.
	 */
	private void resendLastMessage(final BatchLog batchLog) {
		NeEDJMSObject newJmsObject = new NeEDJMSObject(batchLog.getId());
		newJmsObject.setLastMessage(true);
		this.jmsTemplate.setPriority(HIGH_PRIORITY);
		this.jmsTemplate.convertAndSend(newJmsObject);
	}

	/**
	 * Process batch message.
	 * 
	 * @param jmsObject
	 *            apeironsolJMSObject.
	 * @throws MessagingException
	 */
	private void processBatchMesssage(final NeEDJMSObject jmsObject, final BatchLog batchLog) throws Exception {
		try {
			if (this.batchLogMessageService.findBatchLogMessageByBatchLogIdAndStudentAcademicYearId(batchLog.getId(), jmsObject.getStudentAcademicYear()
					.getId()) == null) {
				EmailWorker emailWorker = EmailWorkerFactory.getEmailWorker(batchLog.getNotificationSubTypeConstant());
				NotificationMessage notificationMessage = emailWorker.sendMail(jmsObject.getStudentAcademicYear(), batchLog);
				this.postProcessElement(jmsObject, batchLog, notificationMessage);
			}
		} catch (Throwable exception) {
			throw new Exception(exception);
		}
	}

	/**
	 * Takes care of the post-processing. At the end of each message.
	 * 
	 * @param jmsObject
	 * @param messageException
	 * @param batchLog
	 */
	private void postProcessElement(final NeEDJMSObject jmsObject, final BatchLog batchLog, final NotificationMessage notificationMessage) {
		BatchLogMessage batchLogMessage = new BatchLogMessage();
		batchLogMessage.setBatchLog(batchLog);
		batchLogMessage.setSendTo(jmsObject.getStudentAcademicYear().getStudent().getAddress().getEmail());
		batchLogMessage.setStudentAcademicYear(jmsObject.getStudentAcademicYear());
		batchLogMessage.setAuditUsername(jmsObject.getUserName());
		if (batchLog.getMessage() == null || batchLog.getMessage().trim().isEmpty()) {
			batchLogMessage.setMessageSent(notificationMessage.getMessage());
		}
		batchLogMessage.setErrorMessage(notificationMessage.getErrorMessage());
		batchLogMessage.setMessageSentTime(DateUtil.getSystemDate());
		batchLogMessage.setBatchLogMessageStatusConstant(notificationMessage.getBatchLogMessageStatus());
		this.batchLogMessageService.saveBatchLogMessageInNewTransaction(batchLogMessage);
	}

	/**
	 * Returns true when the batch was canceled.
	 * 
	 * @param batchServicesLogVO
	 *            the batch service log record of the batch.
	 * @return true when the batch was canceled.
	 */
	private boolean isCanceled(final BatchLog batchLog) {
		return BatchStatusConstant.CANCELLED.equals(batchLog.getBatchStatusConstant());
	}

	/**
	 * Updates the batch log to finished state. This method is called only when
	 * all messages in the batch are completed with successfully or failed.
	 * 
	 * @param batchLog
	 *            the batch log.
	 */
	private void updateBatchLogToFinished(final BatchLog batchLog) {

		batchLog.setNrElementsProcessedSuccess(this.batchLogMessageService.findNumberOfBatchLogMessagesByBatchLogIdAndStatus(batchLog.getId(),
				EnumSet.of(BatchLogMessageStatusConstant.SUCCESS)));

		batchLog.setNrElementsProcessedWithError(this.batchLogMessageService.findNumberOfBatchLogMessagesByBatchLogIdAndStatus(batchLog.getId(),
				EnumSet.of(BatchLogMessageStatusConstant.FAILED)));

		batchLog.setNrElementsProcessedWithCanceled(this.batchLogMessageService.findNumberOfBatchLogMessagesByBatchLogIdAndStatus(batchLog.getId(),
				EnumSet.of(BatchLogMessageStatusConstant.CANCELLED)));

		batchLog.setBatchStatusConstant(BatchStatusConstant.FINISHED);
		Date currentTime = DateUtil.getSystemDate();
		long executionTime = currentTime.getTime() - batchLog.getExecutionStartDate().getTime();
		batchLog.setExecutionTime(executionTime);
		this.batchLogService.saveBatchLogInNewTransaction(batchLog);
	}

	private NotificationMessage createNotificationMessage(final String message, final String errorMessage,
			final BatchLogMessageStatusConstant batchLogMessageStatusConstant) {
		NotificationMessage notificationMessage = new NotificationMessage();
		notificationMessage.setMessage(message);
		notificationMessage.setErrorMessage(errorMessage);
		notificationMessage.setBatchLogMessageStatus(batchLogMessageStatusConstant);
		return notificationMessage;
	}
}
