/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.notifications.producer.util;

import java.io.Serializable;
import java.util.Collection;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.apeironsol.framework.NeEDJMSObject;
import com.apeironsol.need.core.model.BranchRule;
import com.apeironsol.need.core.model.Student;
import com.apeironsol.need.core.model.StudentAcademicYear;
import com.apeironsol.need.core.model.StudentRegistration;
import com.apeironsol.need.core.service.BranchRuleService;
import com.apeironsol.need.notifications.model.BatchLog;
import com.apeironsol.need.notifications.service.BatchLogService;
import com.apeironsol.need.util.constants.BatchStatusConstant;
import com.apeironsol.need.util.portal.ViewUtil;

/**
 * Class for sending email notification for student pending fee.
 * 
 * @author Pradeep
 */
public class NotificationProducerUtil implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 3445522634213583375L;

	/**
	 * Logger for logging.
	 */
	private static final Log	Logger				= LogFactory.getLog(NotificationProducerUtil.class);

	private Destination			destination;

	private ConnectionFactory	jmsConnectionFactory;

	private BatchLogService		batchLogService;

	private BranchRuleService	branchRuleService;

	/** The message priority. */
	private static final short	LOW_PRIORITY		= 3;

	/**
	 * Create batch log for the messages to be sent.
	 * 
	 * @param batchSize
	 *            batch size.
	 * @param branchId
	 *            branch id.
	 * @return batch log created.
	 */
	public BatchLog createBatchLog(final BatchLog batchLog) {
		BatchLog result = batchLog;

		if (result.getSmsProvider() == null) {
			final BranchRule branchRule = this.getBranchRuleService().findBranchRuleByBranchId(result.getBranch().getId());
			if (branchRule != null) {
				result.setSmsProvider(branchRule.getSmsProvider());
			} else {
				result.setMessage("SMS Provider is not selected for the branch.");
				result.setBatchStatusConstant(BatchStatusConstant.CANCELLED);
			}
		}
		result = this.getBatchLogService().saveBatchLogInNewTransaction(batchLog);
		return result;
	}

	/**
	 * 
	 * @param studentAcademicYear
	 * @param branchId
	 * @param batchLog
	 */
	public synchronized void sendNotificationAsBatch(final Collection<StudentAcademicYear> studentAcademicYears, final BatchLog batchLog) {
		Connection queueConn = null;
		Session session = null;
		try {
			// create a queue connection
			queueConn = this.getJmsConnectionFactory().createConnection();
			// create a queue session
			session = queueConn.createSession(true, Session.AUTO_ACKNOWLEDGE);
			// create a queue sender
			final MessageProducer producer = session.createProducer(this.getDestination());
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			ObjectMessage message = null;
			int seqNr = 1;
			for (final StudentAcademicYear studentAcademicYear : studentAcademicYears) {
				message = this.createNotificationMessage(studentAcademicYear, null, batchLog, Long.valueOf(seqNr), session);
				// send the message
				producer.send(message);
				seqNr++;
			}
			// Send this message as last message to update batch log.
			message = this.createLastMessage(batchLog, session);
			// send the message
			producer.send(message);
			this.updateBatchLogToStatus(batchLog, BatchStatusConstant.DISTRIBUTED, Long.valueOf(studentAcademicYears.size()));
			session.commit();
		} catch (final Exception e) {
			this.updateBatchLogToStatus(batchLog, BatchStatusConstant.SENDING_FAILED, Long.valueOf(studentAcademicYears.size()));
			if (session != null) {
				try {
					session.rollback();
				} catch (final JMSException e1) {
					Logger.error(e.getCause());
				}
			}
			Logger.error(e.getCause());
		} finally {
			// close the queue connection
			if (queueConn != null) {
				try {
					queueConn.close();
				} catch (final JMSException e) {
					Logger.error(e.getCause());
				}
			}
		}
	}

	/**
	 * 
	 * @param studentAcademicYear
	 * @param branchId
	 * @param batchLog
	 */
	public synchronized void sendNotificationAsBatch(final BatchLog batchLog, final Collection<Student> students) {
		Connection queueConn = null;
		Session session = null;
		try {
			// create a queue connection
			queueConn = this.getJmsConnectionFactory().createConnection();
			// create a queue session
			session = queueConn.createSession(true, Session.AUTO_ACKNOWLEDGE);
			// create a queue sender
			final MessageProducer producer = session.createProducer(this.getDestination());
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			ObjectMessage message = null;
			int seqNr = 1;
			for (final Student student : students) {
				message = this.createNotificationMessage(null, student, batchLog, Long.valueOf(seqNr), session);
				// send the message
				producer.send(message);
				seqNr++;
			}
			// Send this message as last message to update batch log.
			message = this.createLastMessage(batchLog, session);
			// send the message
			producer.send(message);
			this.updateBatchLogToStatus(batchLog, BatchStatusConstant.DISTRIBUTED, Long.valueOf(students.size()));
			session.commit();
		} catch (final Exception e) {
			this.updateBatchLogToStatus(batchLog, BatchStatusConstant.SENDING_FAILED, Long.valueOf(students.size()));
			if (session != null) {
				try {
					session.rollback();
				} catch (final JMSException e1) {
					Logger.error(e.getCause());
				}
			}
			Logger.error(e.getCause());
		} finally {
			// close the queue connection
			if (queueConn != null) {
				try {
					queueConn.close();
				} catch (final JMSException e) {
					Logger.error(e.getCause());
				}
			}
		}
	}

	/**
	 * 
	 * @param studentAcademicYear
	 * @param branchId
	 * @param batchLog
	 */
	public synchronized void sendNotificationJMS(final StudentAcademicYear studentAcademicYear, final BatchLog batchLog) {
		Connection queueConn = null;
		Session session = null;
		try {
			// create a queue connection
			queueConn = this.getJmsConnectionFactory().createConnection();
			// create a queue session
			session = queueConn.createSession(true, Session.AUTO_ACKNOWLEDGE);
			// create a queue producer
			final MessageProducer producer = session.createProducer(this.getDestination());
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);

			ObjectMessage message = this.createNotificationMessage(studentAcademicYear, null, batchLog, Long.valueOf(1), session);
			// send the message
			producer.send(message);

			// Send this message as last message to update batch log.
			message = this.createLastMessage(batchLog, session);
			// send the message
			producer.send(message);
			this.updateBatchLogToStatus(batchLog, BatchStatusConstant.DISTRIBUTED, Long.valueOf(1));
			session.commit();
		} catch (final Exception e) {
			this.updateBatchLogToStatus(batchLog, BatchStatusConstant.SENDING_FAILED, Long.valueOf(1));
			if (session != null) {
				try {
					session.rollback();
				} catch (final JMSException e1) {
					Logger.error(e.getCause());
				}
			}
			Logger.error(e.getCause());
		} finally {
			// close the queue connection
			if (queueConn != null) {
				try {
					queueConn.close();
				} catch (final JMSException e) {
					Logger.error(e.getCause());
				}
			}
		}
	}

	/**
	 * 
	 * @param studentAcademicYear
	 * @param branchId
	 * @param batchLog
	 */
	public synchronized void sendNotificationJMS(final Student student, final BatchLog batchLog) {
		Connection queueConn = null;
		Session session = null;
		try {
			// create a queue connection
			queueConn = this.getJmsConnectionFactory().createConnection();
			// create a queue session
			session = queueConn.createSession(true, Session.AUTO_ACKNOWLEDGE);
			// create a queue producer
			final MessageProducer producer = session.createProducer(this.getDestination());
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);

			ObjectMessage message = this.createNotificationMessage(null, student, batchLog, Long.valueOf(1), session);
			// send the message
			producer.send(message);

			// Send this message as last message to update batch log.
			message = this.createLastMessage(batchLog, session);
			// send the message
			producer.send(message);
			this.updateBatchLogToStatus(batchLog, BatchStatusConstant.DISTRIBUTED, Long.valueOf(1));
			session.commit();
		} catch (final Exception e) {
			this.updateBatchLogToStatus(batchLog, BatchStatusConstant.SENDING_FAILED, Long.valueOf(1));
			if (session != null) {
				try {
					session.rollback();
				} catch (final JMSException e1) {
					Logger.error(e.getCause());
				}
			}
			Logger.error(e.getCause());
		} finally {
			// close the queue connection
			if (queueConn != null) {
				try {
					queueConn.close();
				} catch (final JMSException e) {
					Logger.error(e.getCause());
				}
			}
		}
	}

	/**
	 * 
	 * @param studentAcademicYear
	 * @param branchId
	 * @param batchLog
	 */
	public synchronized void sendNotificationJMS(final StudentRegistration studentRegistration, final BatchLog batchLog) {
		Connection queueConn = null;
		Session session = null;
		try {
			// create a queue connection
			queueConn = this.getJmsConnectionFactory().createConnection();
			// create a queue session
			session = queueConn.createSession(true, Session.AUTO_ACKNOWLEDGE);
			// create a queue producer
			final MessageProducer producer = session.createProducer(this.getDestination());
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);

			ObjectMessage message = this.createNotificationMessage(studentRegistration, batchLog, Long.valueOf(1), session);
			// send the message
			producer.send(message);

			// Send this message as last message to update batch log.
			message = this.createLastMessage(batchLog, session);
			// send the message
			producer.send(message);
			this.updateBatchLogToStatus(batchLog, BatchStatusConstant.DISTRIBUTED, Long.valueOf(1));
			session.commit();
		} catch (final Exception e) {
			this.updateBatchLogToStatus(batchLog, BatchStatusConstant.SENDING_FAILED, Long.valueOf(1));
			if (session != null) {
				try {
					session.rollback();
				} catch (final JMSException e1) {
					Logger.error(e.getCause());
				}
			}
			Logger.error(e.getCause());
		} finally {
			// close the queue connection
			if (queueConn != null) {
				try {
					queueConn.close();
				} catch (final JMSException e) {
					Logger.error(e.getCause());
				}
			}
		}
	}

	/**
	 * 
	 * @param studentAcademicYear
	 * @param branchId
	 * @param batchLog
	 */
	public synchronized void sendNotificationAsBatchForStudentRegistration(final Collection<StudentRegistration> studentRegistrations, final BatchLog batchLog) {
		Connection queueConn = null;
		Session session = null;
		try {
			// create a queue connection
			queueConn = this.getJmsConnectionFactory().createConnection();
			// create a queue session
			session = queueConn.createSession(true, Session.AUTO_ACKNOWLEDGE);
			// create a queue sender
			final MessageProducer producer = session.createProducer(this.getDestination());
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			ObjectMessage message = null;
			int seqNr = 1;
			for (final StudentRegistration studentRegistration : studentRegistrations) {
				message = this.createNotificationMessage(studentRegistration, batchLog, Long.valueOf(seqNr), session);
				// send the message
				producer.send(message);
				seqNr++;
			}
			// Send this message as last message to update batch log.
			message = this.createLastMessage(batchLog, session);
			// send the message
			producer.send(message);
			this.updateBatchLogToStatus(batchLog, BatchStatusConstant.DISTRIBUTED, Long.valueOf(studentRegistrations.size()));
			session.commit();
		} catch (final Exception e) {
			this.updateBatchLogToStatus(batchLog, BatchStatusConstant.SENDING_FAILED, Long.valueOf(studentRegistrations.size()));
			if (session != null) {
				try {
					session.rollback();
				} catch (final JMSException e1) {
					Logger.error(e.getCause());
				}
			}
			Logger.error(e.getCause());
		} finally {
			// close the queue connection
			if (queueConn != null) {
				try {
					queueConn.close();
				} catch (final JMSException e) {
					Logger.error(e.getCause());
				}
			}
		}
	}

	/**
	 * Updates supplied batch log to status supplied.
	 * 
	 * @param batchLog
	 *            batch log to update.
	 * @param batchStatusConstant
	 *            status to which batch log has to be updated.
	 */
	private void updateBatchLogToStatus(final BatchLog batchLog, final BatchStatusConstant batchStatusConstant, final Long batchSize) {
		final BatchLog batchLogEntity = this.getBatchLogService().findBatchLogById(batchLog.getId());
		batchLogEntity.setBatchStatusConstant(batchStatusConstant);
		if (batchSize != null) {
			batchLogEntity.setNrElements(batchSize);
		}
		this.getBatchLogService().saveBatchLog(batchLogEntity);

	}

	/**
	 * Creates last message which is for updating batch log.
	 * 
	 * @param batchLog
	 *            batch log.
	 * @param session
	 *            session.
	 * @return
	 * @throws JMSException
	 */
	private ObjectMessage createLastMessage(final BatchLog batchLog, final Session session) throws JMSException {
		// Send this message as last message to update batch log.
		final NeEDJMSObject jmsObject = new NeEDJMSObject(batchLog);
		jmsObject.setLastMessage(true);
		jmsObject.setUserName(ViewUtil.getPrincipal());
		final ObjectMessage message = session.createObjectMessage(jmsObject);
		message.setJMSPriority(LOW_PRIORITY);
		return message;
	}

	/**
	 * Creates object message of JMSObject for student.
	 * 
	 * @param studentAcademicYear
	 *            studentAcademicYear.
	 * @param batchLog
	 *            batchLog.
	 * @param sequenceNr
	 *            sequenceNr.
	 * @param session
	 *            session.
	 * @return
	 * @throws JMSException
	 */
	private ObjectMessage createNotificationMessage(final StudentAcademicYear studentAcademicYear, final Student student, final BatchLog batchLog,
			final Long sequenceNr, final Session session) throws JMSException {
		// Send this message as last message to update batch log.
		final NeEDJMSObject jmsObject = new NeEDJMSObject(batchLog);
		if (studentAcademicYear != null) {
			jmsObject.setStudentAcademicYear(studentAcademicYear);
		}
		if (student != null) {
			jmsObject.setStudent(student);
		}
		jmsObject.setSmsProvider(batchLog.getSmsProvider());
		jmsObject.setSequenceNr(Long.valueOf(1));
		jmsObject.setUserName(ViewUtil.getPrincipal());
		final ObjectMessage message = session.createObjectMessage(jmsObject);
		message.setJMSPriority(Message.DEFAULT_PRIORITY);
		return message;
	}

	/**
	 * Creates object message of JMSObject for student.
	 * 
	 * @param studentAcademicYear
	 *            studentAcademicYear.
	 * @param batchLog
	 *            batchLog.
	 * @param sequenceNr
	 *            sequenceNr.
	 * @param session
	 *            session.
	 * @return
	 * @throws JMSException
	 */
	private ObjectMessage createNotificationMessage(final StudentRegistration studentRegistration, final BatchLog batchLog, final Long sequenceNr,
			final Session session) throws JMSException {
		// Send this message as last message to update batch log.
		final NeEDJMSObject jmsObject = new NeEDJMSObject(batchLog);
		if (studentRegistration != null) {
			jmsObject.setStudentRegistration(studentRegistration);
		}

		jmsObject.setSmsProvider(batchLog.getSmsProvider());
		jmsObject.setSequenceNr(Long.valueOf(1));
		jmsObject.setUserName(ViewUtil.getPrincipal());
		final ObjectMessage message = session.createObjectMessage(jmsObject);
		message.setJMSPriority(Message.DEFAULT_PRIORITY);
		return message;
	}

	/**
	 * @return the destination
	 */
	public Destination getDestination() {
		return this.destination;
	}

	/**
	 * @param destination
	 *            the destination to set
	 */
	public void setDestination(final Destination destination) {
		this.destination = destination;
	}

	/**
	 * @return the jmsConnectionFactory
	 */
	public ConnectionFactory getJmsConnectionFactory() {
		return this.jmsConnectionFactory;
	}

	/**
	 * @param jmsConnectionFactory
	 *            the jmsConnectionFactory to set
	 */
	public void setJmsConnectionFactory(final ConnectionFactory jmsConnectionFactory) {
		this.jmsConnectionFactory = jmsConnectionFactory;
	}

	/**
	 * @return the batchLogService
	 */
	public BatchLogService getBatchLogService() {
		return this.batchLogService;
	}

	/**
	 * @param batchLogService
	 *            the batchLogService to set
	 */
	public void setBatchLogService(final BatchLogService batchLogService) {
		this.batchLogService = batchLogService;
	}

	/**
	 * 
	 * @param studentAcademicYear
	 * @param branchId
	 * @param batchLog
	 */
	public void sendNotificationJMS(final BatchLog batchLog) {
		Connection queueConn = null;
		Session session = null;
		try {
			// create a queue connection
			queueConn = this.getJmsConnectionFactory().createConnection();
			// create a queue session
			session = queueConn.createSession(true, Session.AUTO_ACKNOWLEDGE);
			// create a queue producer
			final MessageProducer producer = session.createProducer(this.getDestination());
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);

			ObjectMessage message = this.createNotificationMessage(null, null, batchLog, Long.valueOf(1), session);
			// send the message
			producer.send(message);

			// Send this message as last message to update batch log.
			message = this.createLastMessage(batchLog, session);
			// send the message
			producer.send(message);
			this.updateBatchLogToStatus(batchLog, BatchStatusConstant.DISTRIBUTED, Long.valueOf(1));
			session.commit();
		} catch (final Exception e) {
			this.updateBatchLogToStatus(batchLog, BatchStatusConstant.SENDING_FAILED, Long.valueOf(1));
			if (session != null) {
				try {
					session.rollback();
				} catch (final JMSException e1) {
					Logger.error(e.getCause());
				}
			}
			Logger.error(e.getCause());
		} finally {
			// close the queue connection
			if (queueConn != null) {
				try {
					queueConn.close();
				} catch (final JMSException e) {
					Logger.error(e.getCause());
				}
			}
		}
	}

	/**
	 * @return the branchRuleService
	 */
	public BranchRuleService getBranchRuleService() {
		return this.branchRuleService;
	}

	/**
	 * @param branchRuleService
	 *            the branchRuleService to set
	 */
	public void setBranchRuleService(final BranchRuleService branchRuleService) {
		this.branchRuleService = branchRuleService;
	}

}