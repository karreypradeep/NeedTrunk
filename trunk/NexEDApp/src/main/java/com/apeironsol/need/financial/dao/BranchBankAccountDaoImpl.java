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

import com.apeironsol.need.financial.model.BranchBankAccount;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * 
 * @author sunny
 * 
 *         Data access interface for branch bank account implementation.
 * 
 */
@Repository("branchBankAccountDao")
public class BranchBankAccountDaoImpl extends BaseDaoImpl<BranchBankAccount> implements BranchBankAccountDao {

	@Override
	public Collection<BranchBankAccount> findBranchBankAccountByBranchId(final Long branchId) {
		TypedQuery<BranchBankAccount> query = this.getEntityManager().createQuery("select bba from BranchBankAccount bba where bba.branch.id = :branchId",
				BranchBankAccount.class);
		query.setParameter("branchId", branchId);
		return query.getResultList();
	}

	@Override
	public BranchBankAccount findBranchBankAccountByBranchIdAndAccountNumber(final Long branchId, final String accountNumber) {
		TypedQuery<BranchBankAccount> query = this.getEntityManager().createQuery(
				"select bba from BranchBankAccount bba where bba.branch.id = :branchId and bba.accountNumber = :accountNumber", BranchBankAccount.class);
		query.setParameter("branchId", branchId);
		query.setParameter("accountNumber", accountNumber);
		return query.getSingleResult();
	}

}
