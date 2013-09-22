/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.apeironsol.framework.BaseEntity;

/**
 * Entity class for branch department.
 * 
 * @author Pradeep
 */
@Entity
@Table(name = "BRANCH_RESERVATION_CATEGORY")
public class BranchReservationCategory extends BaseEntity implements Serializable {

	/**
	 * Universal serial version number.
	 */
	private static final long	serialVersionUID	= -7612814040836719567L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCH_ASSEMBLE_ID", nullable = false)
	private BranchAssembly		branchAssembly;

	@NotNull(message = "Start date is mandatory.")
	@Column(name = "START_DATE", nullable = false)
	private Date				startDate;

	@Column(name = "END_DATE")
	private Date				endDate;

	public BranchAssembly getBranchAssembly() {
		return this.branchAssembly;
	}

	public void setBranchAssembly(final BranchAssembly branchAssembly) {
		this.branchAssembly = branchAssembly;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public void validate() {

	}
}
