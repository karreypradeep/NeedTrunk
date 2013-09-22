/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.transportation.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Embeddable class for VehicleRoute.
 * 
 * @author Sandeep Goud.
 */
@Embeddable
public class VehicleRoutePK implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long	serialVersionUID	= 5630479649538282536L;

	@Basic(optional = false)
	@Column(name = "vehicles_ID")
	private Long				vehiclesId;

	@Basic(optional = false)
	@Column(name = "routes_ID")
	private Long				routesId;

	/**
	 * @return the vehicleId
	 */
	public Long getVehiclesId() {
		return this.vehiclesId;
	}

	/**
	 * @param vehicleId
	 *            the vehicleId to set
	 */
	public void setVehiclesId(final Long vehicleId) {
		this.vehiclesId = vehicleId;
	}

	/**
	 * @return the routeId
	 */
	public Long getRoutesId() {
		return this.routesId;
	}

	/**
	 * @param routeId
	 *            the routeId to set
	 */
	public void setRoutesId(final Long routeId) {
		this.routesId = routeId;
	}

	@Override
	public int hashCode() {
		Long hash = new Long(0);
		hash += this.vehiclesId;
		hash += this.routesId;
		return hash.hashCode();
	}

	@Override
	public boolean equals(final Object object) {
		if (object == null) {
			return false;
		}

		if (object == this) {
			return true;
		}

		if (!(object instanceof VehicleRoutePK)) {
			return false;
		}

		VehicleRoutePK other = (VehicleRoutePK) object;
		if (this.vehiclesId.longValue() != other.vehiclesId.longValue()) {
			return false;
		}
		if (this.routesId.longValue() != other.routesId.longValue()) {
			return false;
		}
		return true;
	}

}
