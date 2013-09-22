/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.transportation.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.apeironsol.need.core.model.Branch;
import com.apeironsol.framework.BaseEntity;


/**
 * Entity class for Vehicle.
 * 
 * @author Sandeep Goud.
 */
@Entity
@Table(name = "VEHICLE")
public class Vehicle extends BaseEntity implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long	serialVersionUID	= -6389962739207551112L;

	@NotEmpty(message = "model.vehicle_number_mandatory")
	@Column(name = "VEHICLE_NUMBER", nullable = false, length = 20)
	private String				vehicleNumber;

	@NotNull(message = "model.vehicle_capacity_mandatory")
	@Column(name = "CAPACITY", nullable = false)
	private Integer				capacity;

	@NotEmpty(message = "model.vehicle_type_mandatory")
	@Column(name = "TYPE", nullable = false, length = 50)
	private String				vehicleType;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "BRANCH_ID", nullable = false)
	private Branch				branch;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.LAZY)
	private Collection<Route>	routes;

	/**
	 * @return the routes
	 */
	public Collection<Route> getRoutes() {
		return this.routes;
	}

	/**
	 * @param routes
	 *            the routes to set
	 */
	public void setRoutes(final Collection<Route> routes) {
		this.routes = routes;
	}

	/**
	 * Returns vehicle capacity.
	 * 
	 * @return the capacity.
	 */
	public Integer getCapacity() {
		return this.capacity;
	}

	/**
	 * Sets capacity.
	 * 
	 * @param capacity
	 *            the capacity to set.
	 */
	public void setCapacity(final Integer capacity) {
		this.capacity = capacity;
	}

	/**
	 * Returns vehicle number.
	 * 
	 * @return the vehicleNumber.
	 */
	public String getVehicleNumber() {
		return this.vehicleNumber;
	}

	/**
	 * Sets vehicle number.
	 * 
	 * @param vehicleNumber
	 *            the vehicleNumber to set.
	 */
	public void setVehicleNumber(final String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	/**
	 * @return the branch
	 */
	public Branch getBranch() {
		return this.branch;
	}

	/**
	 * @param branch
	 *            the branch to set
	 */
	public void setBranch(final Branch branch) {
		this.branch = branch;
	}

	/**
	 * @return the vehicleType
	 */
	public String getVehicleType() {
		return this.vehicleType;
	}

	/**
	 * @param vehicleType
	 *            the vehicleType to set
	 */
	public void setVehicleType(final String vehicleType) {
		this.vehicleType = vehicleType;
	}

}
