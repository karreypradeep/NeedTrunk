/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 * 
 */
package com.apeironsol.need.procurement.portal;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.portal.AbstractTabbedBean;
import com.apeironsol.need.procurement.model.PurchaseOrderItem;
import com.apeironsol.need.procurement.service.PurchaseOrderItemService;

/**
 * JSF managed for financial income.
 * 
 * @author Pradeep
 */
@Named
@Scope(value = "session")
public class PurchaseOrderItemBean extends AbstractTabbedBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long			serialVersionUID	= 3555712130831423495L;

	/**
	 * Purchase order.
	 */
	private PurchaseOrderItem			purchaseOrderItem;

	/**
	 * Purchase order service.
	 */
	@Resource
	private PurchaseOrderItemService	purchaseOrderItemService;

	@Resource
	private PurchaseOrderBean			purchaseOrderBean;

	/**
	 * Total amount of the purchase order.
	 */
	private Double						totalAmountCostForItem;

	@Override
	public void onTabChange() {
		setViewOrNewAction(false);
	}

	/**
	 * @return the purchaseOrderItem
	 */
	public PurchaseOrderItem getPurchaseOrderItem() {
		return this.purchaseOrderItem;
	}

	/**
	 * @param purchaseOrderItem
	 *            the purchaseOrderItem to set
	 */
	public void setPurchaseOrderItem(final PurchaseOrderItem purchaseOrderItem) {
		this.purchaseOrderItem = purchaseOrderItem;
	}

	public void savePurchaseOrderItem() {
		this.purchaseOrderItem = this.purchaseOrderItemService.savePurchaseOrderItem(this.purchaseOrderItem);
		this.purchaseOrderBean.loadAllPurchaseOrderItemsForPurchaseOrder();
		setViewOrNewAction(false);
	}

	public void removePurchaseOrderItem() {
		this.purchaseOrderItemService.removePurchaseOrderItem(this.purchaseOrderItem);
		this.purchaseOrderBean.loadAllPurchaseOrderItemsForPurchaseOrder();
	}

	/**
	 * Create new purchase order.
	 */
	public String newPurchaseOrderItem() {
		this.purchaseOrderItem = new PurchaseOrderItem();
		this.purchaseOrderItem.setPurchaseOrder(this.purchaseOrderBean.getPurchaseOrder());
		setViewOrNewAction(true);
		this.totalAmountCostForItem = null;
		return null;
	}

	/**
	 * @return the totalAmountCostForItem
	 */
	public Double getTotalAmountCostForItem() {
		return this.totalAmountCostForItem;
	}

	/**
	 * @param totalAmountCostForItem
	 *            the totalAmountCostForItem to set
	 */
	public void setTotalAmountCostForItem(final Double totalAmountCostForItem) {
		this.totalAmountCostForItem = totalAmountCostForItem;
	}

	/**
	 * Listener for quantity change.
	 */
	public void onChangeQuantity() {
		calculateTotalCostForItem();
	}

	/**
	 * Listener for price per unit change.
	 */
	public void onChangePricePerUnit() {
		calculateTotalCostForItem();
	}

	/**
	 * Calculates total cost for item. Total cost = quantity * price per unit.
	 */
	public void calculateTotalCostForItem() {
		if (this.purchaseOrderItem != null && this.purchaseOrderItem.getQuantity() != null
				&& this.purchaseOrderItem.getQuantity().intValue() > 0
				&& this.purchaseOrderItem.getPricePerItem() != null && this.purchaseOrderItem.getPricePerItem() > 0.0) {
			this.totalAmountCostForItem = this.purchaseOrderItem.getQuantity()
					* this.purchaseOrderItem.getPricePerItem();
		}
	}

	public String viewPurchaseOrderItem() {
		calculateTotalCostForItem();
		setViewOrNewAction(true);
		return null;
	}

	/**
	 * Removes academic year Income from database.
	 */
	public String calcelAction() {
		setViewOrNewAction(false);
		return null;
	}

	public boolean isPurchaseOrderAllowedToChange() {
		return this.purchaseOrderBean.getPurchaseOrder().isPurchaseOrderStatusNew();
	}

}
