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

import com.apeironsol.need.procurement.model.Supplier;
import com.apeironsol.framework.BaseDaoImpl;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for Student Attendance entity implementation.
 * 
 * @author Pradeep
 * 
 */
@Repository("supplierDao")
public class SupplierDaoImpl extends BaseDaoImpl<Supplier> implements SupplierDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Supplier> findSuppliersByBranchId(final Long branchId) throws BusinessException {
		TypedQuery<Supplier> query = getEntityManager().createQuery(
				"select s from Supplier s where s.branch.id = :branchId", Supplier.class);
		query.setParameter("branchId", branchId);
		return query.getResultList();
	}

}
