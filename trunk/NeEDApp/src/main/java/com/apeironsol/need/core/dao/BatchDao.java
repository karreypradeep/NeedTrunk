/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.dao;

import java.util.Collection;

import com.apeironsol.need.core.model.Batch;
import com.apeironsol.framework.BaseDao;

/**
 * Data access interface for batch entity.
 * 
 * @author Pradeep
 * 
 */
public interface BatchDao extends BaseDao<Batch> {

	/**
	 * Retrieves all batches of supplied branch id.
	 * 
	 * @param branchId
	 *            branch id.
	 * @return all batches of supplied branch id.
	 */
	Collection<Batch> findBatchesByBranchId(Long branchId);

	/**
	 * Retrieves all active batches of supplied branch id.
	 * 
	 * @param branchId
	 * @return
	 */
	Collection<Batch> findActiveBatchesByBranchId(Long branchId);

}
