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

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.procurement.model.PurchaseInvoice;
import com.apeironsol.framework.BaseDaoImpl;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for purchase invoice entity implementation.
 * 
 * @author Pradeep
 * 
 */
@Repository("purchaseInvoiceDao")
public class PurchaseInvoiceDaoImpl extends BaseDaoImpl<PurchaseInvoice> implements PurchaseInvoiceDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<PurchaseInvoice> findPurchaseInvoicesByBranchId(final Long branchId) throws BusinessException {
		final TypedQuery<PurchaseInvoice> query = this.getEntityManager().createQuery("select pi from PurchaseInvoice pi where pi.branch.id = :branchId",
				PurchaseInvoice.class);
		query.setParameter("branchId", branchId);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<PurchaseInvoice> findPurchaseInvoicesByBranchIdAndAcademicYear(final Long branchId, final AcademicYear academicYear)
			throws BusinessException {
		final TypedQuery<PurchaseInvoice> query = this.getEntityManager().createQuery(
				"select pi from PurchaseInvoice pi where pi.branch.id = :branchId and (pi.invoiceDate >= :startDate and pi.invoiceDate <= :endDate)",
				PurchaseInvoice.class);
		query.setParameter("branchId", branchId);
		query.setParameter("startDate", academicYear.getStartDate());
		query.setParameter("endDate", academicYear.getEndDate());
		return query.getResultList();
	}

	@Override
	public PurchaseInvoice findPurchaseInvoiceByPurchaseInvoiceNumber(final String purchaseInvoiceNumber) throws BusinessException {
		final TypedQuery<PurchaseInvoice> query = this.getEntityManager().createQuery(
				"select pi from PurchaseInvoice pi where pi.invoiceNumber = :invoiceNumber", PurchaseInvoice.class);
		query.setParameter("invoiceNumber", purchaseInvoiceNumber);
		return query.getSingleResult();
	}

	@Override
	public Collection<PurchaseInvoice> findPurchaseInvoiceByPurchaseOrderNumber(final String purchaseOrderNumber) throws BusinessException {
		final TypedQuery<PurchaseInvoice> query = this.getEntityManager().createQuery(
				"select pi from PurchaseInvoice pi where pi.purchaseOrder.purchaseOrderNumber = :purchaseOrderNumber", PurchaseInvoice.class);
		query.setParameter("purchaseOrderNumber", purchaseOrderNumber);
		return query.getResultList();
	}

	@Override
	public Collection<PurchaseInvoice> findPurchaseInvoicesByBranchIdAndAcademicYear(final Long branchId, final Date startDate, final Date toDate)
			throws BusinessException {
		final TypedQuery<PurchaseInvoice> query = this.getEntityManager().createQuery(
				"select pi from PurchaseInvoice pi where pi.branch.id = :branchId and (pi.invoiceDate >= :startDate and pi.invoiceDate <= :endDate)",
				PurchaseInvoice.class);
		query.setParameter("branchId", branchId);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", toDate);
		return query.getResultList();
	}

}
