/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.financial.dao;

import java.util.Collection;

import com.apeironsol.need.financial.model.BranchBankAccount;
import com.apeironsol.framework.BaseDao;

/**
 * 
 * @author sunny
 *         Data access interface for branch bank account implementation.
 * 
 */
public interface BranchBankAccountDao extends BaseDao<BranchBankAccount> {
	/**
	 * Find BranchBankAccount by branch id.
	 * 
	 * @param id
	 *            branchBankAccount id.
	 * @return
	 */
	Collection<BranchBankAccount> findBranchBankAccountByBranchId(Long branchId);

	/**
	 * Find BranchBankAccount by branch id.
	 * 
	 * @param id
	 *            branchBankAccount id.
	 * @return
	 */
	BranchBankAccount findBranchBankAccountByBranchIdAndAccountNumber(Long branchId, String accountNumber);

}
