/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.transportation.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity class for VehicleRoute.
 * 
 * @author Sandeep Goud.
 */
@Entity
@Table(name = "VEHICLE_ROUTE")
public class VehicleRoute implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long	serialVersionUID	= 1344316776605123037L;

	@EmbeddedId
	private VehicleRoutePK		vehicleRoutePK;

	/**
	 * @return the vehicleRoutePK
	 */
	public VehicleRoutePK getVehicleRoutePK() {
		return this.vehicleRoutePK;
	}

	/**
	 * @param vehicleRoutePK
	 *            the vehicleRoutePK to set
	 */
	public void setVehicleRoutePK(final VehicleRoutePK vehicleRoutePK) {
		this.vehicleRoutePK = vehicleRoutePK;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.vehicleRoutePK == null ? 0 : this.vehicleRoutePK.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		VehicleRoute other = (VehicleRoute) obj;
		if (this.vehicleRoutePK == null) {
			if (other.vehicleRoutePK != null) {
				return false;
			}
		} else if (!this.vehicleRoutePK.equals(other.vehicleRoutePK)) {
			return false;
		}
		return true;
	}

}
