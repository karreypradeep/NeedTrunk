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

import com.apeironsol.need.transportation.model.Vehicle;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Service Interface for Vehicle. This service act as controller for
 * Vehicle details.
 * 
 * @author Sandeep
 * 
 */
public interface VehicleService {

	/**
	 * Retrieves the vehicle by id.
	 * 
	 * @param id
	 *            the id of vehicle to be retrieved.
	 * @return
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Vehicle findVehicleById(Long id) throws BusinessException;

	/**
	 * Retrieves all vehicles available.
	 * 
	 * @return all vehicles available.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<Vehicle> findAllVehicles() throws BusinessException;

	/**
	 * Saves the vehicle.
	 * 
	 * @return the saved vehicle.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Vehicle saveVehicle(Vehicle vehicle) throws BusinessException;

	/**
	 * Deletes the vehicle by supplied id.
	 * 
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void deleteVehicle(final Long id) throws BusinessException;

	/**
	 * Retrieve vehicle by vehicle number.
	 * 
	 * @param vehicleNumber
	 *            vehicle number.
	 * @return vehicle by vehicle number.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Vehicle findVehicleByVehicleNumber(final String vehicleNumber) throws BusinessException;

	/**
	 * Retrieves all Vehicles for branch.
	 * 
	 * @return all Vehicles for branch.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	List<Vehicle> findVehiclesByBranchId(final Long branchId) throws BusinessException;

	Vehicle findVehicleDetailsByVehicleId(final Long id) throws BusinessException;

	Collection<Vehicle> findAllVehiclesByRouteId(final Long id);

}
