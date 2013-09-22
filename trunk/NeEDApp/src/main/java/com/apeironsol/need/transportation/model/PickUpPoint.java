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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.apeironsol.need.core.model.Branch;
import com.apeironsol.framework.BaseEntity;

/**
 * Entity class for PickUpPoint
 * 
 * @author sandeep
 * 
 */
@Entity
@Table(name = "PICK_UP_POINT", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "NAME", "BRANCH_ID" }, name = "UQ_BRANCH_ID_AND_NAME_FOR_PICK_UP_POINT"),
		@UniqueConstraint(columnNames = { "CODE", "BRANCH_ID" }, name = "UQ_BRANCH_ID_AND_CODE_FOR_PICK_UP_POINT") })
public class PickUpPoint extends BaseEntity implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long			serialVersionUID	= -4209409752569044788L;

	@NotNull(message = "model.pickup_point_name_mandatory")
	@Column(name = "NAME", nullable = false, length = 100)
	private String						name;

	@NotNull(message = "model.pickup_point_code_mandatory")
	@Column(name = "CODE", nullable = false)
	private String						code;

	@ManyToMany(mappedBy = "pickUpPoints")
	private Collection<Route>			routes;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "BRANCH_ID", nullable = false)
	private Branch						branch;

	@OneToMany(mappedBy = "pickUpPoint", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Collection<PickUpPointFee>	pickUpPointFees;

	@NotNull(message = "model.active_flag_mandatory")
	@Column(name = "ACTIVE", nullable = false)
	private boolean						active;

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return this.active;
	}

	/**
	 * @param active
	 *            the active to set
	 */
	public void setActive(final boolean active) {
		this.active = active;
	}

	/**
	 * @return the pickUpPointFees
	 */
	public Collection<PickUpPointFee> getPickUpPointFees() {
		return this.pickUpPointFees;
	}

	/**
	 * @param pickUpPointFees
	 *            the pickUpPointFees to set
	 */
	public void setPickUpPointFees(final Collection<PickUpPointFee> pickUpPointFees) {
		this.pickUpPointFees = pickUpPointFees;
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
	 * @return the code
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(final String code) {
		this.code = code;
	}

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

}
