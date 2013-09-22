/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.financial.dao;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.financial.model.BranchLevelFeeCatalog;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * Data access interface for class fee
 * 
 * @author Pradeep
 * 
 */
@Repository
public class BranchLevelFeeCatalogDaoImpl extends BaseDaoImpl<BranchLevelFeeCatalog> implements BranchLevelFeeCatalogDao {

	@Override
	public void removeBranchLevelFeeCatalogsByBranchLevelFeeId(final Long branchLevelFeeId) {
		Query query = this.getEntityManager().createQuery("delete from BranchLevelFeeCatalog kfp where kfp.branchLevelFee.id = :branchLevelFeeId");
		query.setParameter("branchLevelFeeId", branchLevelFeeId);
		query.executeUpdate();

	}

	@Override
	public Collection<BranchLevelFeeCatalog> findBranchFeePaymentsByBranchLevelFeeId(final Long branchLevelFeeId) {
		TypedQuery<BranchLevelFeeCatalog> query = this.getEntityManager().createQuery(
				"select kfp from BranchLevelFeeCatalog kfp where kfp.branchLevelFee.id= :branchLevelFeeId)", BranchLevelFeeCatalog.class);
		query.setParameter("branchLevelFeeId", branchLevelFeeId);
		return query.getResultList();
	}

	@Override
	public Collection<BranchLevelFeeCatalog> findBranchFeePaymentsByKlassFeeIdAndDueDate(final Long branchLevelFeeId, final Date dueDate) {

		TypedQuery<BranchLevelFeeCatalog> query = this.getEntityManager().createQuery(
				"select kfp from BranchLevelFeeCatalog kfp where kfp.branchLevelFee.id= :branchLevelFeeId and kfp.dueDate <= :dueDate)",
				BranchLevelFeeCatalog.class);
		query.setParameter("branchLevelFeeId", branchLevelFeeId);
		query.setParameter("dueDate", dueDate);
		return query.getResultList();
	}

}
