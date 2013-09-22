/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 * 
 */
package com.apeironsol.need.procurement.portal;

import java.io.Serializable;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.portal.AbstractTabbedBean;
import com.apeironsol.need.procurement.model.PurchaseOrderStatus;

/**
 * JSF managed for PurchaseOrderStatus.
 * 
 * @author Pradeep
 */
@Named
@Scope(value = "session")
public class PurchaseOrderStatusBean extends AbstractTabbedBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long	serialVersionUID	= 3555712130831423495L;

	/**
	 * Purchase order.
	 */
	private PurchaseOrderStatus	purchaseOrderStatus;

	@Override
	public void onTabChange() {
		this.setViewOrNewAction(false);
	}

	/**
	 * @return the purchaseOrderStatus
	 */
	public PurchaseOrderStatus getPurchaseOrderStatus() {
		return this.purchaseOrderStatus;
	}

	/**
	 * @param purchaseOrderStatus
	 *            the purchaseOrderStatus to set
	 */
	public void setPurchaseOrderStatus(final PurchaseOrderStatus purchaseOrderStatus) {
		this.purchaseOrderStatus = purchaseOrderStatus;
	}

	public String viewPurchaseOrderStatus() {
		this.setViewOrNewAction(true);
		return null;
	}

	/**
	 * Removes academic year Income from database.
	 */
	public String calcelAction() {
		this.setViewOrNewAction(false);
		return null;
	}

}
