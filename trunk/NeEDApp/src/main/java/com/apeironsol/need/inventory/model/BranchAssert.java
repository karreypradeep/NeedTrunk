/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.inventory.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.core.model.BuildingBlock;
import com.apeironsol.framework.BaseEntity;


/**
 * Entity class for branch asserts.
 * 
 * @author Pradeep
 */
@Entity
@Table(name = "BRANCH_ASSERT")
public class BranchAssert extends BaseEntity implements Serializable {

	/**
	 * Universal serial version id for this class
	 */
	private static final long	serialVersionUID	= -4924050006069407388L;

	@Column(name = "NAME", length = 50, nullable = false)
	private String				name;

	@Column(name = "DESCRIPTION", length = 200, nullable = false)
	private String				description;

	@Column(name = "TOTAL_AMOUNT", nullable = false)
	private Double				totalAmount;

	@Column(name = "QUANTITY", nullable = false)
	private Integer				quantity;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ASSERT_TYPE_BUILD_BLK_ID", nullable = false)
	private BuildingBlock		assertTypeBuildingBlock;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCH_ID", nullable = false)
	private Branch				branch;

	@Column(name = "ASSERT_DATE")
	@Temporal(TemporalType.DATE)
	private Date				date;

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
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(final String description) {
		this.description = description;
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
	 * @return the assertTypeBuildingBlock
	 */
	public BuildingBlock getAssertTypeBuildingBlock() {
		return this.assertTypeBuildingBlock;
	}

	/**
	 * @param assertTypeBuildingBlock
	 *            the assertTypeBuildingBlock to set
	 */
	public void setAssertTypeBuildingBlock(final BuildingBlock assertTypeBuildingBlock) {
		this.assertTypeBuildingBlock = assertTypeBuildingBlock;
	}

	/**
	 * @return the totalAmount
	 */
	public Double getTotalAmount() {
		return this.totalAmount;
	}

	/**
	 * @param totalAmount
	 *            the totalAmount to set
	 */
	public void setTotalAmount(final Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return this.quantity;
	}

	/**
	 * @param quantity
	 *            the quantity to set
	 */
	public void setQuantity(final Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return this.date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(final Date date) {
		this.date = date;
	}

}
