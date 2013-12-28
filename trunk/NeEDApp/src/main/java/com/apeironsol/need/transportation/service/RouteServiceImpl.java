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

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.portal.messages.BusinessMessages;
import com.apeironsol.need.transportation.dao.PickUpPointDao;
import com.apeironsol.need.transportation.dao.RouteDao;
import com.apeironsol.need.transportation.dao.VehicleDao;
import com.apeironsol.need.transportation.model.Route;
import com.apeironsol.need.transportation.model.VehicleRoute;
import com.apeironsol.framework.exception.BusinessException;

/**
 * @author sandeep
 * 
 */
@Service("routeService")
@Transactional(rollbackFor = Exception.class)
public class RouteServiceImpl implements RouteService {


	@Resource
	RouteDao		routeDao;

	@Resource
	VehicleDao		vehicleDao;

	@Resource
	PickUpPointDao	pickUpPointDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Route findRouteById(final Long id) throws BusinessException {
		return this.routeDao.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Route findRouteDetailsByRouteId(final Long id) throws BusinessException {
		Route route = this.routeDao.findById(id);
		route.setPickUpPoints(this.pickUpPointDao.findPickUpPointsByRouteId(id));
		return route;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Route> findAllRoutes() throws BusinessException {
		return this.routeDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Route saveRoute(final Route route) throws BusinessException {
		this.validateRouteForUniqueRouteNumber(route);
		this.validateRouteForUniqueRouteName(route);
		return this.routeDao.persist(route);
	}

	/**
	 * Checks if route with same number already exists in
	 * database.
	 * 
	 * @param route
	 *            route to validate.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	private void validateRouteForUniqueRouteName(final Route route) throws BusinessException {
		Route routeObj = this.findRouteByRouteName(route.getName());
		if (routeObj != null && ((route.getId() == null) || (!route.getId().equals(routeObj.getId())))) {
			throw new BusinessException(BusinessMessages.getResourceBundleName(),
					BusinessMessages.MSG_VEHICLE_NUMER_ALREADY_EXIST_FOR_VEHICLE, new Object[] { route.getName() });
		}
	}

	/**
	 * Checks if route with same number already exists in
	 * database.
	 * 
	 * @param route
	 *            route to validate.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	private void validateRouteForUniqueRouteNumber(final Route route) throws BusinessException {
		Route routeObj = this.findRouteByRouteNumber(route.getNumber());
		if (routeObj != null && ((route.getId() == null) || (!route.getId().equals(routeObj.getId())))) {
			throw new BusinessException(BusinessMessages.getResourceBundleName(),
					BusinessMessages.MSG_VEHICLE_NUMER_ALREADY_EXIST_FOR_VEHICLE, new Object[] { route.getNumber() });
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteRoute(final Long id) throws BusinessException {
		// Check if vehicles are associated with the route.
		Collection<VehicleRoute> vehicleRoutes = this.vehicleDao.findAllVehicleRoutesByRouteId(id);
		if (vehicleRoutes != null && !vehicleRoutes.isEmpty()) {
			throw new BusinessException(BusinessMessages.getResourceBundleName(),
					BusinessMessages.MSG_CANNOT_DELETE_ROUTE_VEHICLES_ASSOCIATED, null);
		}

		this.routeDao.remove(this.routeDao.findById(id));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Route findRouteByRouteNumber(final String routeNumber) throws BusinessException {
		return this.routeDao.findRouteByRouteNumber(routeNumber);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Route findRouteByRouteName(final String routeName) throws BusinessException {
		return this.routeDao.findRouteByRouteName(routeName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Route> findRoutesByBranchId(final Long branchId) throws BusinessException {
		return this.routeDao.findRoutesByBranchId(branchId);
	}

	@Override
	public Collection<Route> findAllRoutesByPickUpPointId(final Long id) {
		return this.routeDao.findAllRoutesByPickUpPointId(id);
	}
}
