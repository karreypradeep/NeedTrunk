/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.procurement.dao;

import java.util.Collection;

import com.apeironsol.need.procurement.model.Supplier;
import com.apeironsol.framework.BaseDao;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for supplier entity implementation.
 * 
 * @author Pradeep
 * 
 */
public interface SupplierDao extends BaseDao<Supplier> {

	/**
	 * Find suppliers by branch Id.
	 * 
	 * @param branchId
	 *            branch Id.
	 * @return collection of suppliers by branch Id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<Supplier> findSuppliersByBranchId(Long branchId) throws BusinessException;

}
