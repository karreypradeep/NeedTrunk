/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.procurement.service;

import java.util.Collection;

import com.apeironsol.need.procurement.model.Supplier;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;

/**
 * Service layer interface for supplier DAO implementation.
 * 
 * @author Pradeep
 * 
 */
public interface SupplierService {

	/**
	 * Find suppliers by branch Id.
	 * 
	 * @param branchId
	 *            branch Id.
	 * @return collection of suppliers by supplier Id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<Supplier> findSuppliersByBranchId(Long branchId) throws BusinessException;

	/**
	 * Retrieve supplier by object id.
	 * 
	 * @param id
	 *            id of supplier.
	 * @return supplier by object id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Supplier findSupplierById(Long id) throws BusinessException;

	/**
	 * Saves supplied supplier.
	 * 
	 * @param supplier
	 *            supplier.
	 * @return supplier saved.
	 * @throws BusinessException
	 *             In case of exception.
	 * @throws InvalidArgumentException
	 *             In case supplied parameter is not valid.
	 */
	Supplier saveSupplier(Supplier supplier) throws BusinessException, InvalidArgumentException;

	/**
	 * Removes supplier.
	 * 
	 * @param supplier
	 *            supplier to be removed.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void removeSupplier(Supplier supplier) throws BusinessException;

	/**
	 * Retrieves all suppliers.
	 * 
	 * @return collection of all suppliers.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<Supplier> findAllSuppliers() throws BusinessException;

}
