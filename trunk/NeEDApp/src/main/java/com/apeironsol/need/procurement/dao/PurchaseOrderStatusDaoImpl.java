/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.procurement.dao;

import java.util.Collection;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.procurement.model.PurchaseOrderStatus;
import com.apeironsol.framework.BaseDaoImpl;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for Student Attendance entity implementation.
 * 
 * @author Pradeep
 * 
 */
@Repository("purchaseOrderStatusDao")
public class PurchaseOrderStatusDaoImpl extends BaseDaoImpl<PurchaseOrderStatus> implements PurchaseOrderStatusDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<PurchaseOrderStatus> findPurchaseOrderStatusByPurchaseOrderId(final Long purchaseOrderId)
			throws BusinessException {
		TypedQuery<PurchaseOrderStatus> query = getEntityManager().createQuery(
				"select poi from PurchaseOrderStatus poi where poi.purchaseOrder.id = :purchaseOrderId",
				PurchaseOrderStatus.class);
		query.setParameter("purchaseOrderId", purchaseOrderId);
		return query.getResultList();
	}

}
