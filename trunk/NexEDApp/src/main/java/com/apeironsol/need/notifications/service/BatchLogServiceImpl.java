/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.notifications.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.notifications.dao.BatchLogDao;
import com.apeironsol.need.notifications.model.BatchLog;
import com.apeironsol.need.util.constants.NotificationLevelConstant;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;

/**
 * Service class for batch log.
 * 
 * @author Pradeep
 */
@Service("batchLogService")
@Transactional
public class BatchLogServiceImpl implements BatchLogService {

	@Autowired
	private BatchLogDao	batchLogDao;

	@Override
	public BatchLog findBatchLogById(final Long id) throws BusinessException {
		return batchLogDao.findById(id);
	}

	@Override
	public BatchLog saveBatchLog(final BatchLog batchLog) throws BusinessException, InvalidArgumentException {
		return batchLogDao.persist(batchLog);
	}

	@Override
	public void removeBatchLog(final BatchLog batchLog) throws BusinessException {
		batchLogDao.remove(batchLog);
	}

	@Override
	public Collection<BatchLog> findAllBatchLogs() throws BusinessException {
		return batchLogDao.findAll();
	}

	@Override
	public Collection<BatchLog> findBatchLogsByBranchId(final Long branchId) throws BusinessException {
		return batchLogDao.findBatchLogsByBranchId(branchId);
	}

	@Override
	public Collection<BatchLog> findBatchLogsByNotificationLevelAndNotificationLevelId(final Long branchId,
			final NotificationLevelConstant notificationLevelConstant, final Long notificationLevelId) {
		return batchLogDao.findBatchLogsByNotificationLevelAndNotificationLevelId(branchId, notificationLevelConstant,
				notificationLevelId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public BatchLog saveBatchLogInNewTransaction(final BatchLog batchLog) throws BusinessException,
			InvalidArgumentException {
		return batchLogDao.persist(batchLog);
	}

}
