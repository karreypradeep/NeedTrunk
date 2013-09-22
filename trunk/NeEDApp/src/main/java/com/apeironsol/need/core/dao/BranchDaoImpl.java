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

import com.apeironsol.need.core.model.Branch;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * Data access interface for branch entity implementation.
 * 
 * @author Pradeep
 */
@Repository("branchDao")
public class BranchDaoImpl extends BaseDaoImpl<Branch> implements BranchDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Branch findBranchByName(final String branchName) {
		TypedQuery<Branch> query = this.getEntityManager().createQuery("select b from Branch b where b.name = :name",
				Branch.class);
		query.setParameter("name", branchName);
		return query.getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Branch findBranchByCode(final String branchCode) {
		TypedQuery<Branch> query = this.getEntityManager().createQuery("select b from Branch b where b.code = :code",
				Branch.class);
		query.setParameter("code", branchCode);
		return query.getSingleResult();

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Branch> findAllBranchsByActiveState(final Boolean activeState) {
		TypedQuery<Branch> query = this.getEntityManager().createQuery(
				"select b from Branch b where b.active = :active", Branch.class);
		query.setParameter("active", activeState);
		return query.getResultList();

	}

}
