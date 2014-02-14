/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.notifications.consumers.sms;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.mail.MessagingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;

import com.apeironsol.framework.NeEDJMSObject;
import com.apeironsol.need.core.service.BranchRuleService;
import com.apeironsol.need.notifications.consumers.worker.sms.SMSWorker;
import com.apeironsol.need.notifications.consumers.worker.sms.SMSWorkerFactory;
import com.apeironsol.need.notifications.consumers.worker.util.NotificationMessage;
import com.apeironsol.need.notifications.model.BatchLog;
import com.apeironsol.need.notifications.model.BatchLogMessage;
import com.apeironsol.need.notifications.service.BatchLogMessageService;
import com.apeironsol.need.notifications.service.BatchLogService;
import com.apeironsol.need.util.DateUtil;
import com.apeironsol.need.util.constants.BatchLogMessageStatusConstant;

/**
 * Class for sending email notification for student pending fee.
 * 
 * @author Pradeep
 */
@Component
public class SMSDeadLetterConsumer implements SessionAwareMessageListener<Message> {

	/**
	 * Logger for logging.
	 */
	private static final Log		logger	= LogFactory.getLog(SMSDeadLetterConsumer.class);

	@Resource
	private BatchLogMessageService	batchLogMessageService;

	@Resource
	private BranchRuleService		branchRuleService;

	@Autowired
	private BatchLogService			batchLogService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onMessage(final Message message, final Session session) throws JMSException {
		if (message instanceof TextMessage) {
			System.out.println(((TextMessage) message).getText() + " onMessage arg1 " + session.getAcknowledgeMode());
		} else if (message instanceof ObjectMessage) {
			final NeEDJMSObject jmsObject = (NeEDJMSObject) ((ObjectMessage) message).getObject();
			final BatchLog batchLog = this.batchLogService.findBatchLogById(jmsObject.getBatchId());
			BatchLogMessage batchLogMessage = null;
			if (jmsObject.getStudentAcademicYear() != null) {
				batchLogMessage = this.batchLogMessageService.findBatchLogMessageByBatchLogIdAndStudentAcademicYearId(batchLog.getId(), jmsObject
						.getStudentAcademicYear().getId());
			} else if (jmsObject.getStudent() != null) {
				batchLogMessage = this.batchLogMessageService.findBatchLogMessageByBatchLogIdAndStudentId(batchLog.getId(), jmsObject.getStudent().getId());
			}
			if (batchLogMessage == null) {
				this.processBatchMesssage(jmsObject, batchLog);
			}
		}
	}

	/**
	 * Process batch message.
	 * 
	 * @param jmsObject
	 *            apeironsolJMSObject.
	 * @throws MessagingException
	 */
	private void processBatchMesssage(final NeEDJMSObject jmsObject, final BatchLog batchLog) {
		NotificationMessage notificationMessage = null;
		final SMSWorker smsWorker = SMSWorkerFactory.getSMSWorker(batchLog.getNotificationSubTypeConstant());
		try {
			notificationMessage = smsWorker.sendSMS(batchLog.getSmsProvider() != null ? batchLog.getSmsProvider() : jmsObject.getSmsProvider(), jmsObject
					.getStudentAcademicYear(), jmsObject.getStudentAcademicYear() == null ? jmsObject.getStudent() : jmsObject.getStudentAcademicYear()
					.getStudent(), batchLog);
			this.postProcessElement(jmsObject, batchLog, notificationMessage);
		} catch (final Throwable exception) {
			logger.error(exception);
			notificationMessage = this.createNotificationMessage(null, exception.getMessage(), BatchLogMessageStatusConstant.FAILED);
			this.postProcessElement(jmsObject, batchLog, notificationMessage);
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
		final BatchLogMessage batchLogMessage = new BatchLogMessage();
		batchLogMessage.setBatchLog(batchLog);
		if (jmsObject.getStudentAcademicYear() != null) {
			batchLogMessage.setStudentAcademicYear(jmsObject.getStudentAcademicYear());
			batchLogMessage.setSendTo(jmsObject.getStudentAcademicYear().getStudent().getAddress().getContactNumber());
		}
		if (jmsObject.getStudent() != null) {
			batchLogMessage.setStudent(jmsObject.getStudent());
		}
		batchLogMessage.setAuditUsername(jmsObject.getUserName());
		if (notificationMessage.getMessage() != null) {
			batchLogMessage.setMessageSent(notificationMessage.getMessage());
		}
		batchLogMessage.setMessageSentTime(DateUtil.getSystemDate());
		batchLogMessage.setBatchLogMessageStatusConstant(notificationMessage.getBatchLogMessageStatus());
		if (notificationMessage.getErrorMessage() != null) {
			batchLogMessage.setErrorMessage(notificationMessage.getErrorMessage());
		}
		batchLogMessage.setMessageSentTime(DateUtil.getSystemDate());
		this.batchLogMessageService.saveBatchLogMessageInNewTransaction(batchLogMessage);
	}

	private NotificationMessage createNotificationMessage(final String message, final String errorMessage,
			final BatchLogMessageStatusConstant batchLogMessageStatusConstant) {
		final NotificationMessage notificationMessage = new NotificationMessage();
		if (message != null) {
			notificationMessage.setMessage(message);
		}
		notificationMessage.setErrorMessage(errorMessage);
		notificationMessage.setBatchLogMessageStatus(batchLogMessageStatusConstant);
		return notificationMessage;
	}

}
