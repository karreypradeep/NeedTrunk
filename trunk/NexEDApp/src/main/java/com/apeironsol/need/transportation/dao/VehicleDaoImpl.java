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

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.apeironsol.need.transportation.model.Vehicle;
import com.apeironsol.need.transportation.model.VehicleRoute;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * Data access interface for pickup point entity implementation.
 * 
 * @author sandeep
 */
@Repository("vehicleDao")
public class VehicleDaoImpl extends BaseDaoImpl<Vehicle> implements VehicleDao {

	private static final Logger	log	= Logger.getLogger(VehicleDaoImpl.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vehicle findVehicleByVehicleNumber(final String vehicleNumber) {
		try {
			TypedQuery<Vehicle> query = this.getEntityManager().createQuery(
					"select v from Vehicle v where v.vehicleNumber = :vehicleNumber", Vehicle.class);
			query.setParameter("vehicleNumber", vehicleNumber);
			return query.getSingleResult();
		} catch (NoResultException ex) {
			log.info(ex.getMessage());
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Vehicle> findVehiclesByBranchId(final Long branchId) {
		TypedQuery<Vehicle> query = this.getEntityManager().createQuery(
				"select v from Vehicle v join v.branch b where b.id = :id", Vehicle.class);
		query.setParameter("id", branchId);
		return query.getResultList();
	}

	@Override
	public Collection<Vehicle> findAllVehiclesByRouteId(final Long id) {
		TypedQuery<Vehicle> query = this.getEntityManager().createQuery(
				"select v from Vehicle v join v.routes r where r.id = :id", Vehicle.class);
		query.setParameter("id", id);
		return query.getResultList();
	}

	@Override
	public Collection<VehicleRoute> findAllVehicleRoutesByRouteId(final Long id) {
		TypedQuery<VehicleRoute> query = this.getEntityManager().createQuery(
				"select vr from VehicleRoute vr where vr.vehicleRoutePK.routesId = :routesId", VehicleRoute.class);
		query.setParameter("routesId", id);
		return query.getResultList();
	}

}
