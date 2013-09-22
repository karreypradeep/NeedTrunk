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

import com.apeironsol.need.procurement.dao.PurchaseOrderStatusDao;
import com.apeironsol.need.procurement.model.PurchaseOrderStatus;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;

/**
 * Data access interface for Student Attendance entity implementation.
 * 
 * @author Pradeep
 * 
 */
@Service("purchaseOrderStatusService")
@Transactional
public class PurchaseOrderStatusServiceImpl implements PurchaseOrderStatusService {

	@Resource
	private PurchaseOrderStatusDao	purchaseOrderStatusDao;

	@Override
	public Collection<PurchaseOrderStatus> findPurchaseOrderStatusByPurchaseOrderId(final Long purchaseOrderId)
			throws BusinessException {
		return this.purchaseOrderStatusDao.findPurchaseOrderStatusByPurchaseOrderId(purchaseOrderId);
	}

	@Override
	public PurchaseOrderStatus findPurchaseOrderStatusById(final Long id) throws BusinessException {
		return this.purchaseOrderStatusDao.findById(id);
	}

	@Override
	public PurchaseOrderStatus savePurchaseOrderStatus(final PurchaseOrderStatus purchaseOrderStatus)
			throws BusinessException, InvalidArgumentException {
		return this.purchaseOrderStatusDao.persist(purchaseOrderStatus);
	}

	@Override
	public void removePurchaseOrderStatus(final PurchaseOrderStatus purchaseOrderStatus) throws BusinessException {
		this.purchaseOrderStatusDao.remove(purchaseOrderStatus);
	}

}
