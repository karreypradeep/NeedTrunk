/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.financial.dao;

import java.util.Collection;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.financial.model.BranchCreditAccount;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * 
 * @author sunny
 * 
 *         Data access interface for branch bank account implementation.
 * 
 */
@Repository("branchCreditAccountDao")
public class BranchCreditAccountDaoImpl extends BaseDaoImpl<BranchCreditAccount> implements BranchCreditAccountDao {

	@Override
	public Collection<BranchCreditAccount> findBranchCreditAccountByBranchId(final Long branchId) {
		final TypedQuery<BranchCreditAccount> query = this.getEntityManager().createQuery(
				"select bca from BranchCreditAccount bca where bca.branch.id = :branchId", BranchCreditAccount.class);
		query.setParameter("branchId", branchId);
		return query.getResultList();
	}

	@Override
	public BranchCreditAccount findBranchCreditAccountByBranchIdAndAccountNumber(final Long branchId, final String accountNumber) {
		final TypedQuery<BranchCreditAccount> query = this.getEntityManager().createQuery(
				"select bca from BranchCreditAccount bca where bca.branch.id = :branchId and bca.accountNumber = :accountNumber", BranchCreditAccount.class);
		query.setParameter("branchId", branchId);
		query.setParameter("accountNumber", accountNumber);
		return query.getSingleResult();
	}

	@Override
	public Collection<BranchCreditAccount> findBranchCreditAccountByEmployeeId(final Long employeeId) {
		final TypedQuery<BranchCreditAccount> query = this.getEntityManager().createQuery(
				"select bca from BranchCreditAccount bca where bca.employee.id = :employeeId", BranchCreditAccount.class);
		query.setParameter("employeeId", employeeId);
		return query.getResultList();
	}

}
