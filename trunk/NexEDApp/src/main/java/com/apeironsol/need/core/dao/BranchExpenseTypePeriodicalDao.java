/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.dao;

import java.util.Collection;
import java.util.Date;

import com.apeironsol.need.core.model.BranchAssembly;
import com.apeironsol.need.core.model.BranchExpenseTypePeriodical;
import com.apeironsol.framework.BaseDao;

/**
 * Data access interface for branch expense implementation.
 * 
 * @author Pradeep
 * 
 */
public interface BranchExpenseTypePeriodicalDao extends BaseDao<BranchExpenseTypePeriodical> {

	/**
	 * Retrieves the collection of branch expenses defined for branch for
	 * assemble
	 * id.
	 * 
	 * @param branchAssembly
	 *            the branch assemble.
	 * @return the collection of branch expenses defined for branch for assemble
	 *         id.
	 */
	Collection<BranchExpenseTypePeriodical> findBranchExpenseTypePeriodicalsByBranchAssembly(
			final BranchAssembly branchAssembly);

	/**
	 * Retrieves the branch expense based on branch assembly and end date.
	 * 
	 * @param branchAssembly
	 *            the branch assembly.
	 * @param endDate
	 *            the end date.
	 * @return the branch expense based on branch assembly and end date.
	 */
	BranchExpenseTypePeriodical findBranchExpenseByBranchAssemblyAndEndDate(final BranchAssembly branchAssembly,
			final Date endDate);

	/**
	 * Get current periodical for the expense type branch assembly.
	 * 
	 * @param branchAssembly
	 *            the branch assembly.
	 * @return current periodical periodical for branch assembly.
	 */
	BranchExpenseTypePeriodical findCurrentBranchExpenseTypePeriodicalByBranchAssembly(BranchAssembly branchAssembly);

	/**
	 * Find latest branch expense type periodical by branch assembly.
	 * 
	 * @param branchAssembly
	 *            branch assembly.
	 * @return latest branch expense type periodical by branch assembly.
	 */
	BranchExpenseTypePeriodical findLatestBranchExpenseTypePeriodicalByBranchAssembly(BranchAssembly branchAssembly);
}
