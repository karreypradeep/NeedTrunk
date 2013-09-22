/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.procurement.dao;

import java.util.Collection;

import com.apeironsol.need.procurement.model.PurchaseOrderItem;
import com.apeironsol.framework.BaseDao;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for purchase order item entity implementation.
 * 
 * @author Pradeep
 * 
 */
public interface PurchaseOrderItemDao extends BaseDao<PurchaseOrderItem> {

	/**
	 * Find purchase order itemss by purchase order Id.
	 * 
	 * @param purchaseOrderId
	 *            purchase order Id.
	 * @return collection of purchase order items by purchase order Id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<PurchaseOrderItem> findPurchaseOrderItemsByPurchaseOrderId(Long purchaseOrderId)
			throws BusinessException;

}
