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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.apeironsol.need.core.model.Branch;
import com.apeironsol.framework.BaseEntity;


/**
 * Entity class for Route.
 * 
 * @author Sandeep Goud.
 */
@Entity
@Table(name = "ROUTE")
public class Route extends BaseEntity implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long		serialVersionUID	= -4684373300166322479L;

	@NotEmpty(message = "model.route_name_mondatory")
	@Column(name = "NAME", nullable = false, length = 50)
	private String					name;

	@Column(name = "NUMBER", length = 50)
	private String					number;

	@OneToOne
	@JoinColumn(name = "SOURCE_ID")
	private PickUpPoint				source;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.LAZY)
	private Collection<PickUpPoint>	pickUpPoints;

	@ManyToMany(mappedBy = "routes")
	private Collection<Vehicle>		vehicles;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "BRANCH_ID", nullable = false)
	private Branch					branch;

	/**
	 * @return the vehicles
	 */
	public Collection<Vehicle> getVehicles() {
		return this.vehicles;
	}

	/**
	 * @param vehicles
	 *            the vehicles to set
	 */
	public void setVehicles(final Collection<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the source
	 */
	public PickUpPoint getSource() {
		return this.source;
	}

	/**
	 * @param source
	 *            the source to set
	 */
	public void setSource(final PickUpPoint source) {
		this.source = source;
	}

	/**
	 * @return the pickUpPoints
	 */
	public Collection<PickUpPoint> getPickUpPoints() {
		return this.pickUpPoints;
	}

	/**
	 * @param pickUpPoints
	 *            the pickUpPoints to set
	 */
	public void setPickUpPoints(final Collection<PickUpPoint> pickUpPoints) {
		this.pickUpPoints = pickUpPoints;
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
	 * @return the number
	 */
	public String getNumber() {
		return this.number;
	}

	/**
	 * @param number
	 *            the number to set
	 */
	public void setNumber(final String number) {
		this.number = number;
	}

}
