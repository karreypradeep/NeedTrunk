/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.transportation.dao;

import java.util.Collection;
import java.util.List;

import com.apeironsol.need.transportation.model.PickUpPoint;
import com.apeironsol.framework.BaseDao;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for pickup point entity.
 * 
 * @author sandeep
 * 
 */
public interface PickUpPointDao extends BaseDao<PickUpPoint> {

	/**
	 * Retrieves all pickup points by name or code.
	 * 
	 * @return
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<PickUpPoint> findPickUpPointsByNameOrCode(final String name, final String code);

	/**
	 * Retrieves all pickup points for branch.
	 * 
	 * @return all pickup points for branch.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	List<PickUpPoint> findPickUpPointsByBranchId(final Long branchId);

	Collection<PickUpPoint> findPickUpPointsByRouteId(final Long id);
	
	List<PickUpPoint> findActivePickUpPointsByBranchId(final Long branchId) throws BusinessException;	

}
