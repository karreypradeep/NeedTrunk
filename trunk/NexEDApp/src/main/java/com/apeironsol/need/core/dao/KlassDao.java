/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.dao;

import java.util.Collection;
import java.util.List;

import com.apeironsol.need.core.model.Klass;
import com.apeironsol.framework.BaseDao;

/**
 * Data access interface for grade entity.
 * 
 * @author Pradeep
 * 
 */
public interface KlassDao extends BaseDao<Klass> {

	/**
	 * Retrieves all classes of supplied branch id.
	 * 
	 * @param branchId
	 *            branch id.
	 * @return all classes of supplied branch id.
	 */
	List<Klass> findKlassByBranchId(Long branchId);

	/**
	 * Find all active classes by branch id.
	 * 
	 * @param branchId
	 *            branch id.
	 * @return collection of all active classes by branch id.
	 */
	Collection<Klass> findActiveKlassByBranchId(Long branchId);

}
