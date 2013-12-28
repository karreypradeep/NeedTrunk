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

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.service.SequenceGeneratorService;
import com.apeironsol.need.procurement.dao.PurchaseOrderDao;
import com.apeironsol.need.procurement.model.PurchaseOrder;
import com.apeironsol.need.procurement.model.PurchaseOrderStatus;
import com.apeironsol.need.util.constants.PurchaseOrderStatusConstant;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;

/**
 * Data access interface for Student Attendance entity implementation.
 * 
 * @author Pradeep
 * 
 */
@Service("purchaseOrderService")
@Transactional(rollbackFor = Exception.class)
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

	@Resource
	private PurchaseOrderDao			purchaseOrderDao;

	@Resource
	SequenceGeneratorService			sequenceGeneratorService;

	@Resource
	private PurchaseOrderStatusService	purchaseOrderStatusService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<PurchaseOrder> findPurchaseOrdersByBranchId(final Long branchId) throws BusinessException {
		return this.purchaseOrderDao.findPurchaseOrdersByBranchId(branchId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PurchaseOrder findPurchaseOrderById(final Long id) throws BusinessException {
		return this.purchaseOrderDao.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PurchaseOrder savePurchaseOrder(final PurchaseOrder purchaseOrder) throws BusinessException, InvalidArgumentException {
		PurchaseOrder result = purchaseOrder;
		if (purchaseOrder.getPurchaseOrderNumber() == null || purchaseOrder.getPurchaseOrderNumber().isEmpty()) {
			purchaseOrder.setPurchaseOrderNumber(this.sequenceGeneratorService.getNextPurchaseOrderNumber());
			purchaseOrder.setPurchaseOrderCurrentStatusConstant(PurchaseOrderStatusConstant.NEW);
			result = this.purchaseOrderDao.persist(purchaseOrder);
			PurchaseOrderStatus purchaseOrderStatus = new PurchaseOrderStatus();
			purchaseOrderStatus.setRemarks(result.getDescription());
			purchaseOrderStatus.setPurchaseOrderStatusConstant(PurchaseOrderStatusConstant.NEW);
			purchaseOrderStatus.setPurchaseOrder(result);
			this.purchaseOrderStatusService.savePurchaseOrderStatus(purchaseOrderStatus);
		} else {
			result = this.purchaseOrderDao.persist(purchaseOrder);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removePurchaseOrder(final PurchaseOrder purchaseOrder) throws BusinessException {
		this.purchaseOrderDao.remove(purchaseOrder);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<PurchaseOrder> findAllPurchaseOrders() throws BusinessException {
		return this.purchaseOrderDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<PurchaseOrder> findPurchaseOrdersByBranchIdAndAcademicYear(final Long branchId, final AcademicYear academicYear) throws BusinessException {
		return this.purchaseOrderDao.findPurchaseOrdersByBranchIdAndAcademicYear(branchId, academicYear);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PurchaseOrder updatePurchaseOrderStatus(final PurchaseOrder purchaseOrder, final PurchaseOrderStatusConstant status, final String remarks)
			throws BusinessException, InvalidArgumentException {
		PurchaseOrder result = purchaseOrder;
		purchaseOrder.setPurchaseOrderCurrentStatusConstant(status);
		result = this.purchaseOrderDao.persist(purchaseOrder);
		PurchaseOrderStatus purchaseOrderStatus = new PurchaseOrderStatus();
		purchaseOrderStatus.setRemarks(remarks);
		purchaseOrderStatus.setPurchaseOrderStatusConstant(status);
		purchaseOrderStatus.setPurchaseOrder(result);
		this.purchaseOrderStatusService.savePurchaseOrderStatus(purchaseOrderStatus);
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<PurchaseOrder> findPurchaseOrdersByBranchIdAndPurchaseOrderStatus(final Long branchId,
			final EnumSet<PurchaseOrderStatusConstant> statusConstants) throws BusinessException {
		return this.purchaseOrderDao.findPurchaseOrdersByBranchIdAndPurchaseOrderStatus(branchId, statusConstants);
	}

}
