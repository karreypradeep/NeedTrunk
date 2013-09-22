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

import com.apeironsol.need.procurement.model.PurchaseOrderItem;
import com.apeironsol.framework.BaseDaoImpl;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for Student Attendance entity implementation.
 * 
 * @author Pradeep
 * 
 */
@Repository("purchaseOrderItemDao")
public class PurchaseOrderItemDaoImpl extends BaseDaoImpl<PurchaseOrderItem> implements PurchaseOrderItemDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<PurchaseOrderItem> findPurchaseOrderItemsByPurchaseOrderId(final Long purchaseOrderId)
			throws BusinessException {
		TypedQuery<PurchaseOrderItem> query = getEntityManager().createQuery(
				"select poi from PurchaseOrderItem poi where poi.purchaseOrder.id = :purchaseOrderId",
				PurchaseOrderItem.class);
		query.setParameter("purchaseOrderId", purchaseOrderId);
		return query.getResultList();
	}

}
