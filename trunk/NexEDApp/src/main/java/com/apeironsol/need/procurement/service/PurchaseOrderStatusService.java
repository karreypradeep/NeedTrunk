/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.procurement.service;

import java.util.Collection;

import com.apeironsol.need.procurement.model.PurchaseOrderStatus;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;

/**
 * Service layer interface for purchase order status DAO implementation.
 * 
 * @author Pradeep
 * 
 */
public interface PurchaseOrderStatusService {

	/**
	 * Find purchase order statuss by purchase order Id.
	 * 
	 * @param purchaseOrderId
	 *            purchase order id.
	 * @return collection of purchase order statuss by purchase order Id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<PurchaseOrderStatus> findPurchaseOrderStatusByPurchaseOrderId(Long purchaseOrderId)
			throws BusinessException;

	/**
	 * Retrieve purchase order status by object id.
	 * 
	 * @param id
	 *            id of purchase order status.
	 * @return purchase order status by object id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	PurchaseOrderStatus findPurchaseOrderStatusById(Long id) throws BusinessException;

	/**
	 * Saves supplied purchase order.
	 * 
	 * @param purchaseOrderStatus
	 *            purchase order Status.
	 * @return purchase order saved.
	 * @throws BusinessException
	 *             In case of exception.
	 * @throws InvalidArgumentException
	 *             In case supplied parameter is not valid.
	 */
	PurchaseOrderStatus savePurchaseOrderStatus(PurchaseOrderStatus purchaseOrderStatus) throws BusinessException,
			InvalidArgumentException;

	/**
	 * Removes purchase order.
	 * 
	 * @param purchaseOrderStatus
	 *            purchase order to be removed.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void removePurchaseOrderStatus(PurchaseOrderStatus purchaseOrderStatus) throws BusinessException;

}
