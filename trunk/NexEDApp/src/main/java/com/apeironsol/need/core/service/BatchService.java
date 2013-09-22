/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import java.util.Collection;

import com.apeironsol.need.core.model.Batch;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Service interface for batch.
 * 
 * @author Pradeep
 * 
 */
public interface BatchService {

	/**
	 * Save the batch.
	 * 
	 * @param batch
	 *            batch.
	 * @return saved batch.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Batch saveBatch(Batch batch) throws BusinessException;

	/**
	 * Removes batch.
	 * 
	 * @param batch
	 *            batch.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void removeBatch(Batch batch) throws BusinessException;

	/**
	 * Find batch by id.
	 * 
	 * @param id
	 *            batch id.
	 * @return batch by id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Batch findBatchById(Long id) throws BusinessException;

	/**
	 * Retrieves all batches.
	 * 
	 * @return all batches
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<Batch> findAllBatches() throws BusinessException;

	/**
	 * Retrieves all batches of supplied branch id.
	 * 
	 * @param branchId
	 *            branch id.
	 * @return all batches of supplied branch id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<Batch> findBatchesByBranchId(Long branchId) throws BusinessException;

	/**
	 * Retrieves all active batches of supplied branch id.
	 * 
	 * @param branchId
	 *            branch id.
	 * @return all batches of supplied branch id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<Batch> findActiveBatchesByBranchId(Long branchId) throws BusinessException;

}
