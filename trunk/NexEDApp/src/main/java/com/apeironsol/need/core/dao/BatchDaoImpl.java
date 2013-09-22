/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.dao;

import java.util.Collection;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.core.model.Batch;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * Data access interface for batch implementation entity.
 * 
 * @author Pradeep
 * 
 */
@Repository("batchDao")
public class BatchDaoImpl extends BaseDaoImpl<Batch> implements BatchDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Batch> findBatchesByBranchId(final Long branchId) {
		TypedQuery<Batch> query = this.getEntityManager().createQuery(
				"select b from Batch b join b.branch br where br.id = :id", Batch.class);
		query.setParameter("id", branchId);
		return query.getResultList();
	}

	@Override
	public Collection<Batch> findActiveBatchesByBranchId(final Long branchId) {
		TypedQuery<Batch> query = this.getEntityManager().createQuery(
				"select b from Batch b join b.branch br where br.id = :id and br.active = :active", Batch.class);
		query.setParameter("id", branchId);
		query.setParameter("active", true);
		return query.getResultList();

	}

}
