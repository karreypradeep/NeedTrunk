/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.procurement.service;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.procurement.dao.PurchaseOrderItemDao;
import com.apeironsol.need.procurement.model.PurchaseOrderItem;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;

/**
 * Data access interface for Student Attendance entity implementation.
 * 
 * @author Pradeep
 * 
 */
@Service("purchaseOrderItemService")
@Transactional(rollbackFor = Exception.class)
public class PurchaseOrderItemServiceImpl implements PurchaseOrderItemService {

	@Resource
	private PurchaseOrderItemDao	purchaseOrderItemDao;

	@Override
	public Collection<PurchaseOrderItem> findPurchaseOrderItemsByPurchaseOrderId(final Long purchaseOrderId)
			throws BusinessException {
		return this.purchaseOrderItemDao.findPurchaseOrderItemsByPurchaseOrderId(purchaseOrderId);
	}

	@Override
	public PurchaseOrderItem findPurchaseOrderItemById(final Long id) throws BusinessException {
		return this.purchaseOrderItemDao.findById(id);
	}

	@Override
	public PurchaseOrderItem savePurchaseOrderItem(final PurchaseOrderItem purchaseOrderItem) throws BusinessException,
			InvalidArgumentException {
		return this.purchaseOrderItemDao.persist(purchaseOrderItem);
	}

	@Override
	public void removePurchaseOrderItem(final PurchaseOrderItem purchaseOrderItem) throws BusinessException {
		this.purchaseOrderItemDao.remove(purchaseOrderItem);
	}

}
