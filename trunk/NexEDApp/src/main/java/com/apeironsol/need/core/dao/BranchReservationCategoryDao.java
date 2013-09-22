/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.dao;

import java.sql.Date;
import java.util.Collection;

import com.apeironsol.need.core.model.BranchAssembly;
import com.apeironsol.need.core.model.BranchReservationCategory;
import com.apeironsol.framework.BaseDao;

/**
 * Data access interface for branch reservation category implementation.
 * 
 * @author Pradeep
 * 
 */
public interface BranchReservationCategoryDao extends BaseDao<BranchReservationCategory> {

	/**
	 * Retrieves the collection of branch reservation categories defined for
	 * branch for assemble id.
	 * 
	 * @param branchAssembly
	 *            the branch assemble.
	 * @return the collection of branch reservation categories defined for
	 *         branch for assemble id.
	 */
	Collection<BranchReservationCategory> findBranchReservationCategoriesByBranchAssembly(BranchAssembly branchAssembly);

	/**
	 * Retrieves the previous branch reservation category periodical for
	 * supplied branch reservation category.
	 * 
	 * @param branchFee
	 *            the branch reservation category.
	 * @return the previous branch reservation category periodical for supplied
	 *         branch reservation category.
	 */
	BranchReservationCategory findPreviousBranchReservationCategoryPeriodical(
			final BranchReservationCategory branchReservationCategory);

	/**
	 * Retrieves the branch reservation category based on branch assembly and
	 * date.
	 * 
	 * @param branchAssembly
	 *            the branch assembly.
	 * @param referenceDate
	 *            the reference date.
	 * @return the branch reservation category based on branch assembly and
	 *         date.
	 */
	BranchReservationCategory findBranchReservationCategoryByBranchAssemblyAndDate(final BranchAssembly branchAssembly,
			final Date referenceDate);

	/**
	 * Retrieves the branch reservation category by id.
	 * 
	 * @param branchAssembly
	 *            branch assembly.
	 * @return the latest branch reservation category.
	 */
	BranchReservationCategory findLatestBranchReservationCategory(final BranchAssembly branchAssembly);

	/**
	 * Removes all branch reservation category for the given branch assembly.
	 * 
	 * @param branchAssembly
	 *            branch assembly.
	 */
	void removeBranchReservationCategoryByBranchAssembly(final BranchAssembly branchAssembly);
}
