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
import com.apeironsol.need.core.model.BranchFeeTypePeriodical;
import com.apeironsol.framework.BaseDao;

/**
 * Data access interface for branch Fee implementation.
 * 
 * @author Pradeep
 * 
 */
public interface BranchFeeTypePeriodicalDao extends BaseDao<BranchFeeTypePeriodical> {

	/**
	 * Retrieves the collection of branch fees defined for branch for assemble
	 * id.
	 * 
	 * @param branchAssembly
	 *            the branch assemble.
	 * @return the collection of branch fees defined for branch for assemble id.
	 */
	Collection<BranchFeeTypePeriodical> findBranchFeeTypePeriodicalsByBranchAssembly(final BranchAssembly branchAssembly);

	/**
	 * Retrieves the branch fee based on branch assembly and end date.
	 * 
	 * @param branchAssembly
	 *            the branch assembly.
	 * @param endDate
	 *            the end date.
	 * @return the branch fee based on branch assembly and end date.
	 */
	BranchFeeTypePeriodical findBranchFeeByBranchAssemblyAndEndDate(final BranchAssembly branchAssembly,
			final Date endDate);

	/**
	 * Get current periodical for the fee type branch assembly.
	 * 
	 * @param branchAssembly
	 *            the branch assembly.
	 * @return current periodical periodical for branch assembly.
	 */
	BranchFeeTypePeriodical findActiveBranchFeeTypePeriodicalByBranchAssembly(BranchAssembly branchAssembly);

	/**
	 * Find latest branch fee type periodical by branch assembly.
	 * 
	 * @param branchAssembly
	 *            branch assembly.
	 * @return latest branch fee type periodical by branch assembly.
	 */
	BranchFeeTypePeriodical findLatestBranchFeeTypePeriodicalByBranchAssembly(BranchAssembly branchAssembly);
}
