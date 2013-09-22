/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.procurement.service;

import java.util.Collection;

import com.apeironsol.need.procurement.model.PurchaseOrderItem;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;

/**
 * Service layer interface for purchase order item DAO implementation.
 * 
 * @author Pradeep
 * 
 */
public interface PurchaseOrderItemService {

	/**
	 * Find purchase order items by purchase order Id.
	 * 
	 * @param purchaseOrderId
	 *            purchase order id.
	 * @return collection of purchase order items by purchase order Id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<PurchaseOrderItem> findPurchaseOrderItemsByPurchaseOrderId(Long purchaseOrderId)
			throws BusinessException;

	/**
	 * Retrieve purchase order item by object id.
	 * 
	 * @param id
	 *            id of purchase order item.
	 * @return purchase order item by object id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	PurchaseOrderItem findPurchaseOrderItemById(Long id) throws BusinessException;

	/**
	 * Saves supplied purchase order.
	 * 
	 * @param purchaseOrderItem
	 *            purchase order Item.
	 * @return purchase order saved.
	 * @throws BusinessException
	 *             In case of exception.
	 * @throws InvalidArgumentException
	 *             In case supplied parameter is not valid.
	 */
	PurchaseOrderItem savePurchaseOrderItem(PurchaseOrderItem purchaseOrderItem) throws BusinessException,
			InvalidArgumentException;

	/**
	 * Removes purchase order.
	 * 
	 * @param purchaseOrderItem
	 *            purchase order to be removed.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void removePurchaseOrderItem(PurchaseOrderItem purchaseOrderItem) throws BusinessException;

}
