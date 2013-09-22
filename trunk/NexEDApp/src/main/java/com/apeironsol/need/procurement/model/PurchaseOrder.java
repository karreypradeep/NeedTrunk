/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.procurement.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.util.constants.PurchaseOrderStatusConstant;
import com.apeironsol.need.util.constants.PurchaseOrderTypeConstant;
import com.apeironsol.framework.BaseEntity;

/**
 * Entity class for purchase order.
 * 
 * @author Pradeep
 */
@Entity
@Table(name = "PURCHASE_ORDER", uniqueConstraints = { @UniqueConstraint(columnNames = { "PURCHASE_ORDER_NUMBER", "BRANCH_ID" }, name = "UQ_BRANCH_ID_AND_PUC_OR_NO_FOR_PURC_ORDER") })
public class PurchaseOrder extends BaseEntity implements Serializable {

	/**
	 * Universal serial version id for this class
	 */
	private static final long				serialVersionUID	= 3265156205353558178L;

	@Column(name = "PURCHASE_ORDER_NUMBER", length = 50, nullable = false)
	private String							purchaseOrderNumber;

	@Column(name = "DESCRIPTION", length = 100, nullable = false)
	private String							description;

	@NotNull(message = "model.purchase_order_type_mandatory")
	@Column(name = "PURCHASE_ORDER_TYPE", nullable = false)
	@Enumerated(EnumType.STRING)
	private PurchaseOrderTypeConstant		purchaseOrderTypeConstant;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BRANCH_ID", nullable = false)
	private Branch							branch;

	@Column(name = "PURCHASE_ORDER_DATE")
	@Temporal(TemporalType.DATE)
	private Date							purchaseDate;

	@OneToMany(mappedBy = "purchaseOrder", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Collection<PurchaseOrderItem>	purchaseOrderItems;

	@OneToMany(mappedBy = "purchaseOrder", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Collection<PurchaseOrderStatus>	purchaseOrderStatus;

	@NotNull(message = "model.purchase_order_status_mandatory")
	@Column(name = "CURRENT_STATUS", nullable = false)
	@Enumerated(EnumType.STRING)
	private PurchaseOrderStatusConstant		purchaseOrderCurrentStatusConstant;

	@Column(name = "TOTAL_COST", nullable = false)
	private Double							totalCost;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SUPPLIER_ID", nullable = false)
	private Supplier						supplier;

	/**
	 * @return the totalCost
	 */
	public Double getTotalCost() {
		return this.totalCost;
	}

	/**
	 * @param totalCost
	 *            the totalCost to set
	 */
	public void setTotalCost(final Double totalCost) {
		this.totalCost = totalCost;
	}

	/**
	 * @return the purchaseOrderCurrentStatusConstant
	 */
	public PurchaseOrderStatusConstant getPurchaseOrderCurrentStatusConstant() {
		return this.purchaseOrderCurrentStatusConstant;
	}

	/**
	 * @param purchaseOrderCurrentStatusConstant
	 *            the purchaseOrderCurrentStatusConstant to set
	 */
	public void setPurchaseOrderCurrentStatusConstant(final PurchaseOrderStatusConstant purchaseOrderCurrentStatusConstant) {
		this.purchaseOrderCurrentStatusConstant = purchaseOrderCurrentStatusConstant;
	}

	/**
	 * @return the purchaseOrderItems
	 */
	public Collection<PurchaseOrderItem> getPurchaseOrderItems() {
		return this.purchaseOrderItems;
	}

	/**
	 * @param purchaseOrderItems
	 *            the purchaseOrderItems to set
	 */
	public void setPurchaseOrderItems(final Collection<PurchaseOrderItem> purchaseOrderItems) {
		this.purchaseOrderItems = purchaseOrderItems;
	}

	/**
	 * @return the purchaseOrderNumber
	 */
	public String getPurchaseOrderNumber() {
		return this.purchaseOrderNumber;
	}

	/**
	 * @param purchaseOrderNumber
	 *            the purchaseOrderNumber to set
	 */
	public void setPurchaseOrderNumber(final String purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
	}

	/**
	 * @return the purchaseOrderTypeConstant
	 */
	public PurchaseOrderTypeConstant getPurchaseOrderTypeConstant() {
		return this.purchaseOrderTypeConstant;
	}

	/**
	 * @param purchaseOrderTypeConstant
	 *            the purchaseOrderTypeConstant to set
	 */
	public void setPurchaseOrderTypeConstant(final PurchaseOrderTypeConstant purchaseOrderTypeConstant) {
		this.purchaseOrderTypeConstant = purchaseOrderTypeConstant;
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
	 * @return the purchaseDate
	 */
	public Date getPurchaseDate() {
		return this.purchaseDate;
	}

	/**
	 * @param purchaseDate
	 *            the purchaseDate to set
	 */
	public void setPurchaseDate(final Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	/**
	 * @return the purchaseOrderStatus
	 */
	public Collection<PurchaseOrderStatus> getPurchaseOrderStatus() {
		return this.purchaseOrderStatus;
	}

	/**
	 * @param purchaseOrderStatus
	 *            the purchaseOrderStatus to set
	 */
	public void setPurchaseOrderStatus(final Collection<PurchaseOrderStatus> purchaseOrderStatus) {
		this.purchaseOrderStatus = purchaseOrderStatus;
	}

	public boolean isPurchaseOrderStatusNew() {
		return this.purchaseOrderCurrentStatusConstant == null || PurchaseOrderStatusConstant.NEW.equals(this.purchaseOrderCurrentStatusConstant);
	}

	/**
	 * @return the supplier
	 */
	public Supplier getSupplier() {
		return this.supplier;
	}

	/**
	 * @param supplier
	 *            the supplier to set
	 */
	public void setSupplier(final Supplier supplier) {
		this.supplier = supplier;
	}

}
