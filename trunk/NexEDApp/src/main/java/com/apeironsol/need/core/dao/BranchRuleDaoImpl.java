/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.core.model.BranchRule;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * Data access interface for branch rule entity implementation.
 * 
 * @author Pradeep
 */
@Repository("branchRuleDao")
public class BranchRuleDaoImpl extends BaseDaoImpl<BranchRule> implements BranchRuleDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BranchRule findBranchRuleByBranchId(final Long branchId) {
		try {
			TypedQuery<BranchRule> query = this.getEntityManager().createQuery(
					"select br from BranchRule br join br.branch b where b.id = :id ", BranchRule.class);
			query.setParameter("id", branchId);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeBranchRuleByBranchId(final Long branchId) {
		Query query = this.getEntityManager()
				.createQuery("delete from BranchRule br where br.branch.id = :id");
		query.setParameter("id", branchId);
		query.executeUpdate();
	}
}
