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

import com.apeironsol.need.procurement.dao.SupplierDao;
import com.apeironsol.need.procurement.model.Supplier;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;

/**
 * Data access interface for supplier entity implementation.
 * 
 * @author Pradeep
 * 
 */
@Service("supplierService")
@Transactional(rollbackFor = Exception.class)
public class SupplierServiceImpl implements SupplierService {

	@Resource
	private SupplierDao	supplierDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Supplier> findSuppliersByBranchId(final Long branchId) throws BusinessException {
		return this.supplierDao.findSuppliersByBranchId(branchId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Supplier findSupplierById(final Long id) throws BusinessException {
		return this.supplierDao.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Supplier saveSupplier(final Supplier supplier) throws BusinessException, InvalidArgumentException {
		return this.supplierDao.persist(supplier);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeSupplier(final Supplier supplier) throws BusinessException {
		this.supplierDao.remove(supplier);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Supplier> findAllSuppliers() throws BusinessException {
		return this.supplierDao.findAll();
	}

}
