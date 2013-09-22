/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.notifications.service;

import java.util.Collection;
import java.util.EnumSet;

import com.apeironsol.need.notifications.model.BatchLogMessage;
import com.apeironsol.need.util.constants.BatchLogMessageStatusConstant;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;

/**
 * Service interface for batch log message.
 * 
 * @author Pradeep
 */
public interface BatchLogMessageService {
	/**
	 * Find batch log message by Id.
	 * 
	 * @param id
	 *            batch log message Id.
	 * @return batch log message with supplied id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	BatchLogMessage findBatchLogMessageById(Long id) throws BusinessException;

	/**
	 * Save batch log message.
	 * 
	 * @param batchLogMessage
	 *            batch log message to be saved.
	 * @return persisted batch log message.
	 * @throws BusinessException
	 *             In case of exception.
	 * @throws InvalidArgumentException
	 */
	BatchLogMessage saveBatchLogMessage(BatchLogMessage batchLogMessage) throws BusinessException,
			InvalidArgumentException;

	/**
	 * Remove batch log message.
	 * 
	 * @param batchLogMessage
	 *            batch log message to be removed.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void removeBatchLogMessage(BatchLogMessage batchLogMessage) throws BusinessException;

	/**
	 * Find all batch log messages
	 * 
	 * @return collection of all batch log messages.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<BatchLogMessage> findAllBatchLogMessages() throws BusinessException;

	/**
	 * Find all batch log messages for batch log.
	 * 
	 * @param branchLogId
	 *            branchLogId.
	 * @return collection of all batch log messages for batch log.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<BatchLogMessage> findBatchLogMessagesByBatchLogId(final Long batchLogId) throws BusinessException;

	/**
	 * Find number of batch log messages processed with status as supplied for
	 * batch log.
	 * 
	 * @param branchLogId
	 *            branchLogId.
	 * @param statusConstants
	 *            statusConstants.
	 * @return number of batch log messages processed with status as supplied
	 *         for batch log.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Long findNumberOfBatchLogMessagesByBatchLogIdAndStatus(final Long batchLogId,
			final EnumSet<BatchLogMessageStatusConstant> statusConstants) throws BusinessException;

	/**
	 * Find all batch log messages for batch log and section id.
	 * 
	 * @param branchLogId
	 *            branchLogId.
	 * @param sectionId
	 *            section id
	 * @return collection of all batch log messages for batch log.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<BatchLogMessage> findBatchLogMessagesByBatchLogIdAndSectionId(final Long batchLogId, final Long sectionId)
			throws BusinessException;

	/**
	 * Find all batch log messages for student academic year.
	 * 
	 * @param studentAcademicYearId
	 *            studentAcademicYearId.
	 * @return collection of all batch log messages for student academic year.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<BatchLogMessage> findBatchLogMessagesByStudentAcademicYearId(final Long studentAcademicYearId)
			throws BusinessException;

	/**
	 * Find batch log message for batch log id and student academic year id.
	 * 
	 * @param branchLogId
	 *            branchLogId.
	 * @param studentAcademicYearId
	 *            studentAcademicYearId.
	 * @return batch log message for batch log id and student academic year id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	BatchLogMessage findBatchLogMessageByBatchLogIdAndStudentAcademicYearId(final Long batchLogId,
			final Long studentAcademicYearId) throws BusinessException;

	/**
	 * Save batch log message in new transaction.
	 * 
	 * @param batchLogMessage
	 * @return
	 * @throws BusinessException
	 * @throws InvalidArgumentException
	 */
	BatchLogMessage saveBatchLogMessageInNewTransaction(final BatchLogMessage batchLogMessage)
			throws BusinessException, InvalidArgumentException;
}
