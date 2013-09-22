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

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.procurement.model.PurchaseOrder;
import com.apeironsol.need.util.constants.PurchaseOrderStatusConstant;
import com.apeironsol.framework.BaseDaoImpl;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for Student Attendance entity implementation.
 * 
 * @author Pradeep
 * 
 */
@Repository("purchaseOrderDao")
public class PurchaseOrderDaoImpl extends BaseDaoImpl<PurchaseOrder> implements PurchaseOrderDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<PurchaseOrder> findPurchaseOrdersByBranchId(final Long branchId) throws BusinessException {
		TypedQuery<PurchaseOrder> query = getEntityManager().createQuery(
				"select po from PurchaseOrder po where po.branch.id = :branchId", PurchaseOrder.class);
		query.setParameter("branchId", branchId);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<PurchaseOrder> findPurchaseOrdersByBranchIdAndAcademicYear(final Long branchId,
			final AcademicYear academicYear) throws BusinessException {
		TypedQuery<PurchaseOrder> query = getEntityManager()
				.createQuery(
						"select po from PurchaseOrder po where po.branch.id = :branchId and (po.purchaseDate >= :startDate and po.purchaseDate <= :endDate)",
						PurchaseOrder.class);
		query.setParameter("branchId", branchId);
		query.setParameter("startDate", academicYear.getStartDate());
		query.setParameter("endDate", academicYear.getEndDate());
		return query.getResultList();
	}

	@Override
	public PurchaseOrder findPurchaseOrderByPurchaseOrderNumber(final String purchaseOrderNumber)
			throws BusinessException {
		TypedQuery<PurchaseOrder> query = getEntityManager().createQuery(
				"select po from PurchaseOrder po where po.purchaseOrderNumber = :purchaseOrderNumber",
				PurchaseOrder.class);
		query.setParameter("purchaseOrderNumber", purchaseOrderNumber);
		return query.getSingleResult();
	}

	@Override
	public Collection<PurchaseOrder> findPurchaseOrdersByBranchIdAndPurchaseOrderStatus(final Long branchId,
			final EnumSet<PurchaseOrderStatusConstant> statusConstants) throws BusinessException {
		TypedQuery<PurchaseOrder> query = getEntityManager()
				.createQuery(
						"select po from PurchaseOrder po where po.branch.id = :branchId and po.purchaseOrderCurrentStatusConstant in :statusConstants",
						PurchaseOrder.class);
		query.setParameter("branchId", branchId);
		query.setParameter("statusConstants", statusConstants);
		return query.getResultList();
	}

}
