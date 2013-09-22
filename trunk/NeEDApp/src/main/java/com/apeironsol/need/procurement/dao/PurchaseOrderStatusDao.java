/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.procurement.dao;

import java.util.Collection;

import com.apeironsol.need.procurement.model.PurchaseOrderStatus;
import com.apeironsol.framework.BaseDao;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for purchase order status entity implementation.
 * 
 * @author Pradeep
 * 
 */
public interface PurchaseOrderStatusDao extends BaseDao<PurchaseOrderStatus> {

	/**
	 * Find purchase order status by purchase order Id.
	 * 
	 * @param purchaseOrderId
	 *            purchase order Id.
	 * @return collection of purchase order status by purchase order Id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<PurchaseOrderStatus> findPurchaseOrderStatusByPurchaseOrderId(Long purchaseOrderId)
			throws BusinessException;

}
