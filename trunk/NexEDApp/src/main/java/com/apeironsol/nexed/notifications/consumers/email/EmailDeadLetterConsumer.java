/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.nexed.notifications.consumers.email;

import java.util.Arrays;

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

import com.apeironsol.nexed.notifications.consumers.worker.email.EmailWorker;
import com.apeironsol.nexed.notifications.consumers.worker.email.EmailWorkerFactory;
import com.apeironsol.nexed.notifications.consumers.worker.util.NotificationMessage;
import com.apeironsol.nexed.notifications.model.BatchLog;
import com.apeironsol.nexed.notifications.model.BatchLogMessage;
import com.apeironsol.nexed.notifications.service.BatchLogMessageService;
import com.apeironsol.nexed.notifications.service.BatchLogService;
import com.apeironsol.nexed.util.DateUtil;
import com.apeironsol.nexed.util.constants.BatchLogMessageStatusConstant;
import com.apeironsol.framework.NexEDJMSObject;

/**
 * Class for sending email notification for student pending fee.
 * 
 * @author Pradeep
 */
@Component
public class EmailDeadLetterConsumer implements SessionAwareMessageListener<Message> {

	/**
	 * Logger for logging.
	 */
	private static final Log		Logger	= LogFactory.getLog(EmailDeadLetterConsumer.class);

	@Resource
	private BatchLogMessageService	batchLogMessageService;

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
			NexEDJMSObject jmsObject = (NexEDJMSObject) ((ObjectMessage) message).getObject();
			BatchLog batchLog = this.batchLogService.findBatchLogById(jmsObject.getBatchId());
			this.processBatchMesssage(jmsObject, batchLog);
		}
	}

	/**
	 * Process batch message.
	 * 
	 * @param jmsObject
	 *            apeironsolJMSObject.
	 * @throws MessagingException
	 */
	private void processBatchMesssage(final NexEDJMSObject jmsObject, final BatchLog batchLog) {
		if (this.batchLogMessageService.findBatchLogMessageByBatchLogIdAndStudentAcademicYearId(batchLog.getId(), jmsObject.getStudentAcademicYear().getId()) == null) {
			NotificationMessage notificationMessage = null;
			EmailWorker emailWorker = EmailWorkerFactory.getEmailWorker(batchLog.getNotificationSubTypeConstant());
			try {
				notificationMessage = emailWorker.sendMail(jmsObject.getStudentAcademicYear(), batchLog);
			} catch (Throwable exception) {
				if (notificationMessage == null) {
					notificationMessage = this.createNotificationMessage(emailWorker.getMessage(jmsObject.getStudentAcademicYear(), batchLog), exception
							.getMessage().concat(Arrays.toString(exception.getStackTrace())), BatchLogMessageStatusConstant.FAILED);
				}
				Logger.error(exception);
			} finally {
				if (notificationMessage == null) {
					notificationMessage = this.createNotificationMessage(emailWorker.getMessage(jmsObject.getStudentAcademicYear(), batchLog), "Null pointer",
							BatchLogMessageStatusConstant.FAILED);
				}
				this.postProcessElement(jmsObject, batchLog, notificationMessage);
			}
		}
	}

	/**
	 * Takes care of the post-processing. At the end of each message.
	 * 
	 * @param jmsObject
	 * @param messageException
	 * @param batchLog
	 */
	private void postProcessElement(final NexEDJMSObject jmsObject, final BatchLog batchLog, final NotificationMessage notificationMessage) {
		BatchLogMessage batchLogMessage = new BatchLogMessage();
		batchLogMessage.setBatchLog(batchLog);
		batchLogMessage.setSendTo(jmsObject.getStudentAcademicYear().getStudent().getAddress().getEmail());
		batchLogMessage.setStudentAcademicYear(jmsObject.getStudentAcademicYear());
		batchLogMessage.setAuditUsername(jmsObject.getUserName());
		batchLogMessage.setMessageSent(notificationMessage.getMessage());
		batchLogMessage.setMessageSentTime(DateUtil.getSystemDate());
		batchLogMessage.setBatchLogMessageStatusConstant(notificationMessage.getBatchLogMessageStatus());
		batchLogMessage.setErrorMessage(notificationMessage.getErrorMessage());
		this.batchLogMessageService.saveBatchLogMessageInNewTransaction(batchLogMessage);
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
