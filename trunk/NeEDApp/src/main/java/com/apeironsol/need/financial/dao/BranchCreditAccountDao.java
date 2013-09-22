/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.financial.dao;

import java.util.Collection;

import com.apeironsol.need.financial.model.BranchCreditAccount;
import com.apeironsol.framework.BaseDao;

/**
 * 
 * @author sunny
 *         Data access interface for branch bank account implementation.
 * 
 */
public interface BranchCreditAccountDao extends BaseDao<BranchCreditAccount> {

	/**
	 * Find BranchCreditAccount by branch id.
	 * 
	 * @param id
	 *            branchCreditAccount id.
	 * @return
	 */
	Collection<BranchCreditAccount> findBranchCreditAccountByBranchId(Long branchId);

	/**
	 * Find BranchCreditAccount by branch id.
	 * 
	 * @param id
	 *            branchCreditAccount id.
	 * @return
	 */
	BranchCreditAccount findBranchCreditAccountByBranchIdAndAccountNumber(Long branchId, String accountNumber);

	/**
	 * Find BranchCreditAccount by branch id.
	 * 
	 * @param id
	 *            employeeId id.
	 * @return
	 */
	Collection<BranchCreditAccount> findBranchCreditAccountByEmployeeId(Long employeeId);

}
