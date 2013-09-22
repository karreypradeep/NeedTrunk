/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.procurement.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.apeironsol.need.core.model.Address;
import com.apeironsol.need.core.model.Branch;
import com.apeironsol.framework.BaseEntity;

/**
 * Entity class for purchase order.
 * 
 * @author Pradeep
 */
@Entity
@Table(name = "SUPPLIER", uniqueConstraints = { @UniqueConstraint(columnNames = { "NAME", "BRANCH_ID" }, name = "UQ_BRANCH_ID_AND_NAME_FOR_SUPPLIER") })
public class Supplier extends BaseEntity implements Serializable {

	/**
	 * Universal serial version id for this class
	 */
	private static final long	serialVersionUID	= -4055737041536254005L;

	@Column(name = "NAME", length = 50, nullable = false)
	private String				name;

	@OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "ADDRESS_ID", nullable = false)
	private Address				address;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BRANCH_ID", nullable = false)
	private Branch				branch;

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
	 * @return the address
	 */
	public Address getAddress() {
		return this.address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(final Address address) {
		this.address = address;
	}

}
