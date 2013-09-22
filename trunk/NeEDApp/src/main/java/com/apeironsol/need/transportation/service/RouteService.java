/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.transportation.service;

import java.util.Collection;
import java.util.List;

import com.apeironsol.need.transportation.model.Route;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Service Interface for Route. This service act as controller for
 * Route details.
 * 
 * @author Sandeep
 * 
 */
public interface RouteService {

	/**
	 * Retrieves the route by id.
	 * 
	 * @param id
	 *            the id of route to be retrieved.
	 * @return
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Route findRouteById(Long id) throws BusinessException;

	/**
	 * Retrieves all routes available.
	 * 
	 * @return all routes available.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<Route> findAllRoutes() throws BusinessException;

	/**
	 * Saves the route.
	 * 
	 * @return the saved route.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Route saveRoute(Route route) throws BusinessException;

	/**
	 * Deletes the route by supplied id.
	 * 
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void deleteRoute(final Long id) throws BusinessException;

	/**
	 * Retrieve route by route number.
	 * 
	 * @param routeNumber
	 *            route number.
	 * @return route by route number.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Route findRouteByRouteNumber(final String routeNumber) throws BusinessException;

	/**
	 * Retrieve route by route name.
	 * 
	 * @param routeName
	 *            route name.
	 * @return route by route name.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Route findRouteByRouteName(final String routeName) throws BusinessException;

	/**
	 * Retrieves all Routes for branch.
	 * 
	 * @return all Routes for branch.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	List<Route> findRoutesByBranchId(final Long branchId) throws BusinessException;

	Route findRouteDetailsByRouteId(final Long id) throws BusinessException;

	Collection<Route> findAllRoutesByPickUpPointId(final Long id);
}
