/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import java.util.Collection;

import com.apeironsol.need.core.model.BranchAssembly;
import com.apeironsol.need.core.model.BranchExpenseTypePeriodical;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Service Interface for branch expense. This service act as controller for
 * branch expense.
 * 
 * @author Pradeep
 * 
 */
public interface BranchExpenseTypePeriodicalService {

	/**
	 * This method create or update branch expense type periodical
	 * 
	 * @param branchExpenseTypePeriodical
	 *            branch expense type periodical.
	 * @return the persisted Branch expense type periodical.
	 * @throws BusinessException
	 *             in case of business validation errors.
	 */
	BranchExpenseTypePeriodical saveBranchExpenseTypePeriodical(BranchExpenseTypePeriodical branchExpenseTypePeriodical)
			throws BusinessException;

	/**
	 * Delete the branch expense type periodical.
	 * 
	 * @param branchExpenseTypePeriodical
	 *            branch expense type periodical.
	 * @throws BusinessException
	 *             in case of business validation errors.
	 */
	public void removeBranchExpenseTypePeriodical(final BranchExpenseTypePeriodical branchExpenseTypePeriodical)
			throws BusinessException;

	/**
	 * Retrieves the collection of branch expense type periodicals based on
	 * branch
	 * assembly.
	 * 
	 * @param branchAssembly
	 *            the branch assembly.
	 * @return the collection of branch expense type periodicals for the branch
	 *         assembly.
	 * @throws BusinessException
	 *             in case of business validation errors.
	 */
	Collection<BranchExpenseTypePeriodical> findBranchExpenseTypeByBranchAssembly(final BranchAssembly branchAssembly)
			throws BusinessException;

	/**
	 * Find latest branch expense type periodical by branch assembly.
	 * 
	 * @param branchAssembly
	 *            branch assembly.
	 * @return latest branch expense type periodical by branch assembly.
	 */
	BranchExpenseTypePeriodical findLatestBranchExpenseTypePeriodical(BranchAssembly branchAssembly);
}
