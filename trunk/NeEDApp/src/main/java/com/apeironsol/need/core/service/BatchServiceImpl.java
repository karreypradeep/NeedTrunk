/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */

package com.apeironsol.need.core.service;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.need.core.dao.BatchDao;
import com.apeironsol.need.core.model.Batch;

/**
 * Service interface implementation for Batch.
 * 
 * @author Pradeep
 * 
 */
@Service("batchService")
@Transactional
public class BatchServiceImpl implements BatchService {

	@Resource
	private BatchDao	batchDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Batch saveBatch(final Batch batch) throws BusinessException {
		return this.batchDao.persist(batch);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeBatch(final Batch batch) throws BusinessException {
		this.batchDao.remove(batch);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Batch findBatchById(final Long id) throws BusinessException {
		return this.batchDao.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Batch> findAllBatches() throws BusinessException {
		return this.batchDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Batch> findBatchesByBranchId(final Long branchId) throws BusinessException {
		return this.batchDao.findBatchesByBranchId(branchId);
	}

	@Override
	public Collection<Batch> findActiveBatchesByBranchId(final Long branchId) throws BusinessException {
		return this.batchDao.findActiveBatchesByBranchId(branchId);
	}
}
