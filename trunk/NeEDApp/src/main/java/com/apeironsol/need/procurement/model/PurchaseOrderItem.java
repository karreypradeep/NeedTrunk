/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.procurement.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.apeironsol.framework.BaseEntity;


/**
 * Entity class for purchase order items.
 * 
 * @author Pradeep
 */
@Entity
@Table(name = "PURCHASE_ORDER_ITEM")
public class PurchaseOrderItem extends BaseEntity implements Serializable {

	/**
	 * Universal serial version id for this class
	 */
	private static final long	serialVersionUID	= -143049941591246004L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PURCHASE_ORDER_ID", nullable = false)
	private PurchaseOrder		purchaseOrder;

	@Column(name = "NAME", length = 50, nullable = false)
	private String				name;

	@Column(name = "PRICE_PER_ITEM", nullable = false)
	private Double				pricePerItem;

	@Column(name = "QUANTITY", nullable = false)
	private Integer				quantity;

	/**
	 * @return the purchaseOrder
	 */
	public PurchaseOrder getPurchaseOrder() {
		return this.purchaseOrder;
	}

	/**
	 * @param purchaseOrder
	 *            the purchaseOrder to set
	 */
	public void setPurchaseOrder(final PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
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
	 * @return the pricePerItem
	 */
	public Double getPricePerItem() {
		return this.pricePerItem;
	}

	/**
	 * @param pricePerItem
	 *            the pricePerItem to set
	 */
	public void setPricePerItem(final Double pricePerItem) {
		this.pricePerItem = pricePerItem;
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
	 * @return the toatlCost
	 */
	public Double getToatlCost() {
		Double toatlCost = null;
		if (this.quantity != null && this.pricePerItem != null) {
			toatlCost = this.quantity * this.pricePerItem;
		}
		return toatlCost;
	}

}
