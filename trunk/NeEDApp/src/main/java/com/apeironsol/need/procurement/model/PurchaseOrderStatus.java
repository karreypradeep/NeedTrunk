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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.apeironsol.need.util.constants.PurchaseOrderStatusConstant;
import com.apeironsol.framework.BaseEntity;

/**
 * Entity class for purchase order status.
 * 
 * @author Pradeep
 */
@Entity
@Table(name = "PURCHASE_ORDER_STATUS")
public class PurchaseOrderStatus extends BaseEntity implements Serializable {

	/**
	 * Universal serial version id for this class
	 */
	private static final long			serialVersionUID	= 6342699817470669718L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PURCHASE_ORDER_ID", nullable = false)
	private PurchaseOrder				purchaseOrder;

	@Column(name = "REMARKS", length = 200, nullable = false)
	private String						remarks;

	@NotNull(message = "model.purchase_order_status_mandatory")
	@Column(name = "STATUS", nullable = false)
	@Enumerated(EnumType.STRING)
	private PurchaseOrderStatusConstant	purchaseOrderStatusConstant;

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
	 * @return the remarks
	 */
	public String getRemarks() {
		return this.remarks;
	}

	/**
	 * @param remarks
	 *            the remarks to set
	 */
	public void setRemarks(final String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the purchaseOrderStatusConstant
	 */
	public PurchaseOrderStatusConstant getPurchaseOrderStatusConstant() {
		return this.purchaseOrderStatusConstant;
	}

	/**
	 * @param purchaseOrderStatusConstant
	 *            the purchaseOrderStatusConstant to set
	 */
	public void setPurchaseOrderStatusConstant(final PurchaseOrderStatusConstant purchaseOrderStatusConstant) {
		this.purchaseOrderStatusConstant = purchaseOrderStatusConstant;
	}

}
