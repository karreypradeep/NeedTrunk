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
import com.apeironsol.need.core.model.BranchFeeTypePeriodical;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;

/**
 * Service Interface for branch fee type periodicals.
 * 
 * @author Pradeep
 * 
 */
public interface BranchFeeTypePeriodicalService {

	/**
	 * This method create or update branch fee type periodical
	 * 
	 * @param branchFeeTypePeriodical
	 *            branch fee type periodical.
	 * @return the persisted Branch fee type periodical.
	 * @throws BusinessException
	 *             in case of business validation errors.
	 * @throws InvalidArgumentException
	 */
	BranchFeeTypePeriodical saveBranchFeeTypePeriodical(BranchFeeTypePeriodical branchFeeTypePeriodical)
			throws BusinessException, InvalidArgumentException;

	/**
	 * Delete the branch fee type periodical.
	 * 
	 * @param branchFeeTypePeriodical
	 *            branch fee type periodical.
	 * @throws BusinessException
	 *             in case of business validation errors.
	 */
	public void removeBranchFeePeriodical(final BranchFeeTypePeriodical branchFeeTypePeriodical)
			throws BusinessException;

	/**
	 * Retrieves the collection of branch fee type periodicals based on branch
	 * assembly.
	 * 
	 * @param branchAssembly
	 *            the branch assembly.
	 * @return the collection of branch fee type periodicals for the branch
	 *         assembly.
	 * @throws BusinessException
	 *             in case of business validation errors.
	 */
	Collection<BranchFeeTypePeriodical> findBranchFeeTypeByBranchAssembly(final BranchAssembly branchAssembly)
			throws BusinessException;

	/**
	 * Find latest branch fee type periodical by branch assembly.
	 * 
	 * @param branchAssembly
	 *            branch assembly.
	 * @return latest branch fee type periodical by branch assembly.
	 */
	BranchFeeTypePeriodical findLatestBranchFeePeriodical(BranchAssembly branchAssembly);

}
