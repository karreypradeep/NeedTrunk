/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.procurement.dao;

import java.util.Collection;
import java.util.Date;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.procurement.model.PurchaseInvoice;
import com.apeironsol.framework.BaseDao;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for BranchAssert entity implementation.
 * 
 * @author Pradeep
 * 
 */
public interface PurchaseInvoiceDao extends BaseDao<PurchaseInvoice> {

	/**
	 * Find purchase invoices by purchase invoice Id.
	 * 
	 * @param branchId
	 *            branch Id.
	 * @return collection of purchase invoices by purchase invoice Id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<PurchaseInvoice> findPurchaseInvoicesByBranchId(Long branchId) throws BusinessException;

	/**
	 * Find purchase invoices by purchase invoice Id and building block id and
	 * assert date.
	 * 
	 * @param branchId
	 *            branch Id.
	 * @param academicYear
	 *            academicYear.
	 * @return collection of purchase invoices by purchase invoice Id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<PurchaseInvoice> findPurchaseInvoicesByBranchIdAndAcademicYear(Long branchId, AcademicYear academicYear) throws BusinessException;

	/**
	 * Find purchase order by purchase invoice number.
	 * 
	 * @param purchaseInvoiceNumber
	 *            purchase invoice number.
	 * @return purchase order by purchase invoice number.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	PurchaseInvoice findPurchaseInvoiceByPurchaseInvoiceNumber(String purchaseInvoiceNumber) throws BusinessException;

	/**
	 * Find purchase order by purchase invoice number.
	 * 
	 * @param purchaseInvoiceNumber
	 *            purchase invoice number.
	 * @return purchase order by purchase invoice number.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<PurchaseInvoice> findPurchaseInvoiceByPurchaseOrderNumber(String purchaseOrderNumber) throws BusinessException;

	Collection<PurchaseInvoice> findPurchaseInvoicesByBranchIdAndAcademicYear(Long branchId, Date startDate, Date toDate) throws BusinessException;
}
