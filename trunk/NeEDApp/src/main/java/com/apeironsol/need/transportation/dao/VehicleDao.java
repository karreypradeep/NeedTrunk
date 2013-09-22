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

import com.apeironsol.need.transportation.model.Vehicle;
import com.apeironsol.need.transportation.model.VehicleRoute;
import com.apeironsol.framework.BaseDao;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for pickup point entity.
 * 
 * @author sandeep
 * 
 */
public interface VehicleDao extends BaseDao<Vehicle> {

	/**
	 * Retrieves vehicle by vehicle number.
	 * 
	 * @return vehicle by vehicle number.
	 * @throws BusinessException
	 *             in case of exception.
	 */
	Vehicle findVehicleByVehicleNumber(final String vehicleNumber);

	/**
	 * Retrieves all Vehicles for branch.
	 * 
	 * @return all Vehicles for branch.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	List<Vehicle> findVehiclesByBranchId(final Long branchId);

	Collection<Vehicle> findAllVehiclesByRouteId(final Long id);

	Collection<VehicleRoute> findAllVehicleRoutesByRouteId(final Long id);

}
