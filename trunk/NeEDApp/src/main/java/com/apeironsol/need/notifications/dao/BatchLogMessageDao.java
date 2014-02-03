/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.notifications.dao;

import java.util.Collection;
import java.util.EnumSet;

import com.apeironsol.framework.BaseDao;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.need.notifications.model.BatchLogMessage;
import com.apeironsol.need.util.constants.BatchLogMessageStatusConstant;

/**
 * Data access interface for batch log.
 * 
 * @author Pradeep
 */
public interface BatchLogMessageDao extends BaseDao<BatchLogMessage> {

	/**
	 * Retrieve all batch log messages for the supplied batch log id.
	 * 
	 * @param batchLogId
	 *            batch id.
	 * @return collection of batch log messages for the supplied batch log id.
	 */
	Collection<BatchLogMessage> findBatchLogMessagesByBatchLogId(Long batchLogId);

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
	Collection<BatchLogMessage> findBatchLogMessagesByBatchLogIdAndSectionId(final Long batchLogId, final Long sectionId) throws BusinessException;

	/**
	 * Find all batch log messages for student academic year.
	 * 
	 * @param studentSetionId
	 *            studentSetionId.
	 * @return collection of all batch log messages for student academic year.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<BatchLogMessage> findBatchLogMessagesByStudentAcademicYearId(final Long studentAcademicYearId) throws BusinessException;

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
	Long findNumberOfBatchLogMessagesByBatchLogIdAndStatus(final Long batchLogId, final EnumSet<BatchLogMessageStatusConstant> statusConstants)
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
	BatchLogMessage findBatchLogMessageByBatchLogIdAndStudentAcademicYearId(final Long batchLogId, final Long studentAcademicYearId) throws BusinessException;

	/**
	 * Find all batch log messages for batch logs.
	 * 
	 * @param batchLogIds
	 *            branchLogId.
	 * @return collection of all batch log messages for batch log.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<BatchLogMessage> findBatchLogMessagesByBatchLogIds(final Collection<Long> batchLogIds) throws BusinessException;
}
