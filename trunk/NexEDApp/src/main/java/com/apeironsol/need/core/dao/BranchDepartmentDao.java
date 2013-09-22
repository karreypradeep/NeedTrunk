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
import com.apeironsol.need.core.model.BranchDepartment;
import com.apeironsol.framework.BaseDao;

/**
 * Data access interface for branch department implementation.
 * 
 * @author Pradeep
 * 
 */
public interface BranchDepartmentDao extends BaseDao<BranchDepartment> {

	/**
	 * Retrieves the collection of branch departments defined for branch for
	 * assemble id.
	 * 
	 * @param branchAssembly
	 *            the branch assemble.
	 * @return the collection of branch departments defined for branch for
	 *         assemble id.
	 */
	Collection<BranchDepartment> findBranchDepartmentsByBranchAssembly(BranchAssembly branchAssembly);

	/**
	 * Retrieves the previous branch department periodical for supplied branch
	 * department.
	 * 
	 * @param branchFee
	 *            the branch department.
	 * @return the previous branch department periodical for supplied branch
	 *         department.
	 */
	BranchDepartment findPreviousBranchDepartmentPeriodical(final BranchDepartment branchDepartment);

	/**
	 * Retrieves the branch department based on branch assembly and date.
	 * 
	 * @param branchAssembly
	 *            the branch assembly.
	 * @param referenceDate
	 *            the reference date.
	 * @return the the branch department based on branch assembly and date.
	 */
	BranchDepartment findBranchDepartmentByBranchAssemblyAndDate(final BranchAssembly branchAssembly,
			final Date referenceDate);

	/**
	 * Retrieves the branch departments by branch assembly.
	 * 
	 * @param branchAssembly
	 *            branch assembly.
	 * @return latest branch department.
	 */
	BranchDepartment findLatestBranchDepartment(final BranchAssembly branchAssembly);

	/**
	 * Removes all branch departments for the given branch assembly.
	 * 
	 * @param branchAssembly
	 *            branch assembly.
	 */
	void removeBranchDepartmentByBranchAssembly(final BranchAssembly branchAssembly);

	/**
	 * Retrieves the branch department based on branch assembly and start date.
	 * 
	 * @param branchAssembly
	 *            the branch assembly.
	 * @param referenceDate
	 *            the start date.
	 * @return the branch department based on branch assembly and start date.
	 */
	BranchDepartment findBranchDepartmentByBranchAssemblyAndStartDate(final BranchAssembly branchAssembly,
			final Date referenceDate);

	/**
	 * Retrieves the branch department based on branch assembly and end date.
	 * 
	 * @param branchAssembly
	 *            the branch assembly.
	 * @param referenceDate
	 *            the end date.
	 * @return the branch department based on branch assembly and end date.
	 */
	BranchDepartment findBranchDepartmentByBranchAssemblyAndEndDate(final BranchAssembly branchAssembly,
			final Date referenceDate);
}
