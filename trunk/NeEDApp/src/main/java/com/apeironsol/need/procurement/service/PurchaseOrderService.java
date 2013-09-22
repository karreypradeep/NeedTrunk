/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.procurement.service;

import java.util.Collection;
import java.util.EnumSet;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.procurement.model.PurchaseOrder;
import com.apeironsol.need.util.constants.PurchaseOrderStatusConstant;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;

/**
 * Service layer interface for purchase order DAO implementation.
 * 
 * @author Pradeep
 * 
 */
public interface PurchaseOrderService {

	/**
	 * Find purchase orders by branch Id.
	 * 
	 * @param branchId
	 *            branch Id.
	 * @return collection of purchase orders by purchase order Id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<PurchaseOrder> findPurchaseOrdersByBranchId(Long branchId) throws BusinessException;

	/**
	 * Retrieve purchase order by object id.
	 * 
	 * @param id
	 *            id of purchase order.
	 * @return purchase order by object id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	PurchaseOrder findPurchaseOrderById(Long id) throws BusinessException;

	/**
	 * Saves supplied purchase order.
	 * 
	 * @param purchaseOrder
	 *            purchase order.
	 * @return purchase order saved.
	 * @throws BusinessException
	 *             In case of exception.
	 * @throws InvalidArgumentException
	 *             In case supplied parameter is not valid.
	 */
	PurchaseOrder savePurchaseOrder(PurchaseOrder purchaseOrder) throws BusinessException, InvalidArgumentException;

	/**
	 * Removes purchase order.
	 * 
	 * @param purchaseOrder
	 *            purchase order to be removed.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void removePurchaseOrder(PurchaseOrder purchaseOrder) throws BusinessException;

	/**
	 * Retrieves all purchase orders.
	 * 
	 * @return collection of all purchase orders.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<PurchaseOrder> findAllPurchaseOrders() throws BusinessException;

	/**
	 * Find purchase orders by branch Id and academic year.
	 * 
	 * @param branchId
	 *            branch Id.
	 * @param academicYear
	 *            academicYear.
	 * @return collection of purchase orders by purchase order Id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<PurchaseOrder> findPurchaseOrdersByBranchIdAndAcademicYear(Long branchId, AcademicYear academicYear)
			throws BusinessException;

	/**
	 * Updates purchase order to status supplied and also creates a entry for
	 * purchase order status .
	 * 
	 * @param purchaseOrder
	 *            purchase order to be updated.
	 * @param status
	 *            status.
	 * @param remarks
	 *            remarks.
	 * @return updated purchaseOrder.
	 * @throws BusinessException
	 *             In case of exception.
	 * @throws InvalidArgumentException
	 *             In case supplied parameter is not valid.
	 */
	PurchaseOrder updatePurchaseOrderStatus(final PurchaseOrder purchaseOrder,
			final PurchaseOrderStatusConstant status, final String remarks) throws BusinessException,
			InvalidArgumentException;

	/**
	 * Find purchase orders by branch Id.
	 * 
	 * @param branchId
	 *            branch Id.
	 * @param statusConstants
	 *            collection of purchase order status.
	 * @return collection of purchase orders by branch Id and status.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<PurchaseOrder> findPurchaseOrdersByBranchIdAndPurchaseOrderStatus(Long branchId,
			final EnumSet<PurchaseOrderStatusConstant> statusConstants) throws BusinessException;

}
