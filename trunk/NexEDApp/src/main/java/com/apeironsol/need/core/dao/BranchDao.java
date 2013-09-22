/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.dao;

import java.util.Collection;

import com.apeironsol.need.core.model.Branch;
import com.apeironsol.framework.BaseDao;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for branch entity.
 * 
 * @author Pradeep
 */
public interface BranchDao extends BaseDao<Branch> {

	/**
	 * Returns branch by name.
	 * 
	 * @param branchName
	 *            branch name.
	 * @return branch by name.
	 */
	Branch findBranchByName(String branchName);

	/**
	 * Returns branch by code.
	 * 
	 * @param branchCode
	 *            branch code.
	 * @return branch by code.
	 */
	Branch findBranchByCode(String branchCode);

	/**
	 * Retrieves all branches by supplied active state from database.
	 * 
	 * @return all branches by supplied active state from database.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<Branch> findAllBranchsByActiveState(final Boolean activeState) throws BusinessException;
}
