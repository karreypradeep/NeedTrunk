/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.notifications.consumers.sms;

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

import com.apeironsol.framework.NeEDJMSObject;
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
	private static final Log		Logger	= LogFactory.getLog(SMSDeadLetterConsumer.class);

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
			NeEDJMSObject jmsObject = (NeEDJMSObject) ((ObjectMessage) message).getObject();
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
	private void processBatchMesssage(final NeEDJMSObject jmsObject, final BatchLog batchLog) {
		NotificationMessage notificationMessage = null;
		SMSWorker smsWorker = SMSWorkerFactory.getSMSWorker(batchLog.getNotificationSubTypeConstant());
		try {
			notificationMessage = smsWorker.sendSMS(null, jmsObject.getStudentAcademicYear(), batchLog);
		} catch (Throwable exception) {
			if (notificationMessage == null) {
				notificationMessage = this.createNotificationMessage(smsWorker.getMessage(jmsObject.getStudentAcademicYear(), batchLog), exception.getMessage()
						.concat(Arrays.toString(exception.getStackTrace())), BatchLogMessageStatusConstant.FAILED);
			}
			Logger.error(exception);
		} finally {
			if (notificationMessage == null) {
				notificationMessage = this.createNotificationMessage("Null pointer", "Null pointer", BatchLogMessageStatusConstant.FAILED);
			}
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
		BatchLogMessage batchLogMessage = new BatchLogMessage();
		batchLogMessage.setBatchLog(batchLog);
		batchLogMessage.setSendTo(jmsObject.getStudentAcademicYear().getStudent().getAddress().getContactNumber());
		batchLogMessage.setStudentAcademicYear(jmsObject.getStudentAcademicYear());
		batchLogMessage.setAuditUsername(jmsObject.getUserName());
		batchLogMessage.setMessageSent(notificationMessage.getMessage());
		batchLogMessage.setMessageSentTime(DateUtil.getSystemDate());
		batchLogMessage.setBatchLogMessageStatusConstant(notificationMessage.getBatchLogMessageStatus());
		batchLogMessage.setErrorMessage(notificationMessage.getErrorMessage());
		batchLogMessage.setMessageSentTime(DateUtil.getSystemDate());
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
