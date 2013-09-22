/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import java.util.List;

import com.apeironsol.need.core.model.BranchRule;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Service Interface for Branch. This service act as controller for
 * Branch.
 * 
 * @author Pradeep
 * 
 */
public interface BranchRuleService {

	/**
	 * Persists branch Rule into database.
	 * 
	 * @param branchRule
	 *            branch rule.
	 * @return
	 * @throws BusinessException
	 *             In case of exception.
	 */
	BranchRule saveBranchRule(BranchRule branchRule) throws BusinessException;

	/**
	 * Retrieves all branch rules.
	 * 
	 * @return all branch rules.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	List<BranchRule> findAllBranchRules() throws BusinessException;

	/**
	 * Retrieves branch rule with given id.
	 * 
	 * @param id
	 *            branch rule id.
	 * @return branch rule with given id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	BranchRule findBranchRuleById(Long id) throws BusinessException;

	/**
	 * Removes branch rule with d.
	 * 
	 * @param id
	 *            branch rule id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void removeBranchRule(Long id) throws BusinessException;

	/**
	 * Returns branch rule by branch id.
	 * 
	 * @param branchId
	 *            branch id.
	 * @return branch rule by branch id.
	 */
	BranchRule findBranchRuleByBranchId(final Long branchId);

	/**
	 * Removes branch rule by branch id.
	 * 
	 * @param id
	 *            branch id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void removeBranchRuleByBranchId(final Long branchId) throws BusinessException;

}
