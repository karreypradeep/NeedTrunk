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

import com.apeironsol.need.transportation.model.Route;
import com.apeironsol.need.transportation.model.RoutePickUpPoint;
import com.apeironsol.framework.BaseDao;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for route entity.
 * 
 * @author sandeep
 * 
 */
public interface RouteDao extends BaseDao<Route> {

	/**
	 * Retrieves Route by route name.
	 * 
	 * @return vehicle by route name.
	 * @throws BusinessException
	 *             in case of exception.
	 */
	Route findRouteByRouteName(final String routeName);

	/**
	 * Retrieves Route by route number.
	 * 
	 * @return vehicle by route number.
	 * @throws BusinessException
	 *             in case of exception.
	 */
	Route findRouteByRouteNumber(final String routeNumber);

	/**
	 * Retrieves all Route for branch.
	 * 
	 * @return all Route for branch.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	List<Route> findRoutesByBranchId(final Long branchId);

	Collection<Route> findRoutesByVehicleId(final Long id);

	Collection<Route> findAllRoutesByPickUpPointId(final Long id);

	Collection<RoutePickUpPoint> findAllRoutePickUpPointsByPickUpPointId(final Long id);

}
