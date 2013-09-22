/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.apeironsol.framework.BaseEntity;

/**
 * Entity class for calendar year
 * 
 * @author Pradeep
 */
@Entity
@Table(name = "BRANCH_ASSEMBLY", uniqueConstraints = { @UniqueConstraint(columnNames = { "BRANCH_ID",
		"BUILDING_BLOCK_ID" }, name = "UQ_BRANCH_ASSEMBLY") })
public class BranchAssembly extends BaseEntity implements Serializable {

	/**
	 * Universal serial version number.
	 */
	private static final long	serialVersionUID	= 5734598690975464449L;

	@Column(name = "BRANCH_ID", nullable = false)
	private Long				branchId;

	@Column(name = "BUILDING_BLOCK_ID", nullable = false)
	private Long				buildingBlockId;

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(final Long branchId) {
		this.branchId = branchId;
	}

	public Long getBuildingBlockId() {
		return buildingBlockId;
	}

	public void setBuildingBlockId(final Long buildingBlockId) {
		this.buildingBlockId = buildingBlockId;
	}

}
