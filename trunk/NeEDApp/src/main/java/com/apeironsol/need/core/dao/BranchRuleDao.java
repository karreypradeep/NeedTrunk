/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.dao;

import com.apeironsol.need.core.model.BranchRule;
import com.apeironsol.framework.BaseDao;

/**
 * Data access interface for branch entity.
 * 
 * @author Pradeep
 */
public interface BranchRuleDao extends BaseDao<BranchRule> {

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
	 * @param branchId
	 *            branch id.
	 */
	void removeBranchRuleByBranchId(final Long branchId);

}
