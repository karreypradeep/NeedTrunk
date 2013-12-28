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
import com.apeironsol.need.transportation.dao.RouteDao;
import com.apeironsol.need.transportation.dao.VehicleDao;
import com.apeironsol.need.transportation.model.Vehicle;
import com.apeironsol.framework.exception.BusinessException;

/**
 * @author sandeep
 * 
 */
@Service("vehicleService")
@Transactional(rollbackFor = Exception.class)
public class VehicleServiceImpl implements VehicleService {

	@Resource
	VehicleDao	vehicleDao;

	@Resource
	RouteDao	routeDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vehicle findVehicleById(final Long id) throws BusinessException {
		return this.vehicleDao.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vehicle findVehicleDetailsByVehicleId(final Long id) throws BusinessException {
		Vehicle vehicle = this.vehicleDao.findById(id);
		vehicle.setRoutes(this.routeDao.findRoutesByVehicleId(id));
		return vehicle;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Vehicle> findAllVehicles() throws BusinessException {
		return this.vehicleDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vehicle saveVehicle(final Vehicle vehicle) throws BusinessException {
		this.validateVehicleForUniqueVehicleNumber(vehicle);
		return this.vehicleDao.persist(vehicle);
	}

	/**
	 * Checks if vehicle with same number already exists in
	 * database.
	 * 
	 * @param vehicle
	 *            vehicle to validate.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	private void validateVehicleForUniqueVehicleNumber(final Vehicle vehicle) throws BusinessException {
		Vehicle vehicleObj = this.findVehicleByVehicleNumber(vehicle.getVehicleNumber());
		if (vehicleObj != null && ((vehicle.getId() == null) || (!vehicle.getId().equals(vehicleObj.getId())))) {
			throw new BusinessException(BusinessMessages.getResourceBundleName(),
					BusinessMessages.MSG_VEHICLE_NUMER_ALREADY_EXIST_FOR_VEHICLE,
					new Object[] { vehicle.getVehicleNumber() });
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteVehicle(final Long id) throws BusinessException {
		this.vehicleDao.remove(this.vehicleDao.findById(id));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vehicle findVehicleByVehicleNumber(final String vehicleNumber) throws BusinessException {
		return this.vehicleDao.findVehicleByVehicleNumber(vehicleNumber);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Vehicle> findVehiclesByBranchId(final Long branchId) throws BusinessException {
		return this.vehicleDao.findVehiclesByBranchId(branchId);
	}

	@Override
	public Collection<Vehicle> findAllVehiclesByRouteId(final Long id) {
		return this.vehicleDao.findAllVehiclesByRouteId(id);
	}

}
