/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.procurement.dao;

import java.util.Collection;
import java.util.EnumSet;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.procurement.model.PurchaseOrder;
import com.apeironsol.need.util.constants.PurchaseOrderStatusConstant;
import com.apeironsol.framework.BaseDao;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for BranchAssert entity implementation.
 * 
 * @author Pradeep
 * 
 */
public interface PurchaseOrderDao extends BaseDao<PurchaseOrder> {

	/**
	 * Find purchase orders by purchase order Id.
	 * 
	 * @param branchId
	 *            branch Id.
	 * @return collection of purchase orders by purchase order Id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<PurchaseOrder> findPurchaseOrdersByBranchId(Long branchId) throws BusinessException;

	/**
	 * Find purchase orders by purchase order Id and building block id and
	 * assert date.
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
	 * Find purchase order by purchase order number.
	 * 
	 * @param purchaseOrderNumber
	 *            purchase order number.
	 * @return purchase order by purchase order number.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	PurchaseOrder findPurchaseOrderByPurchaseOrderNumber(String purchaseOrderNumber) throws BusinessException;

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
