/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.procurement.service;

import java.util.Collection;
import java.util.Date;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.procurement.model.PurchaseInvoice;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;

/**
 * Service layer interface for purchase invoice DAO implementation.
 * 
 * @author Pradeep
 * 
 */
public interface PurchaseInvoiceService {

	/**
	 * Find purchase invoices by branch Id.
	 * 
	 * @param branchId
	 *            branch Id.
	 * @return collection of purchase invoices by purchase invoice Id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<PurchaseInvoice> findPurchaseInvoicesByBranchId(Long branchId) throws BusinessException;

	/**
	 * Retrieve purchase invoice by object id.
	 * 
	 * @param id
	 *            id of purchase invoice.
	 * @return purchase invoice by object id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	PurchaseInvoice findPurchaseInvoiceById(Long id) throws BusinessException;

	/**
	 * Saves supplied purchase invoice.
	 * 
	 * @param purchaseInvoice
	 *            purchase invoice.
	 * @return purchase invoice saved.
	 * @throws BusinessException
	 *             In case of exception.
	 * @throws InvalidArgumentException
	 *             In case supplied parameter is not valid.
	 */
	PurchaseInvoice savePurchaseInvoice(PurchaseInvoice purchaseInvoice) throws BusinessException, InvalidArgumentException;

	/**
	 * Removes purchase invoice.
	 * 
	 * @param purchaseInvoice
	 *            purchase invoice to be removed.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void removePurchaseInvoice(PurchaseInvoice purchaseInvoice) throws BusinessException;

	/**
	 * Retrieves all purchase invoices.
	 * 
	 * @return collection of all purchase invoices.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<PurchaseInvoice> findAllPurchaseInvoices() throws BusinessException;

	/**
	 * Find purchase invoices by branch Id and academic year.
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
	 * Find purchase order by purchase order number.
	 * 
	 * @param purchaseOrderNumber
	 *            purchase order number.
	 * @return collection purchase invoice by purchase order number.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<PurchaseInvoice> findPurchaseInvoiceByPurchaseOrderNumber(String purchaseOrderNumber) throws BusinessException;

	/**
	 * Find purchase invoices by branch Id and academic year.
	 * 
	 * @param branchId
	 *            branch Id.
	 * @param academicYear
	 *            academicYear.
	 * @return collection of purchase invoices by purchase invoice Id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<PurchaseInvoice> findPurchaseInvoicesByBranchIdAndAcademicYear(Long branchId, Date startDate, Date toDate) throws BusinessException;

}
