/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.financial.dao;

import java.util.Collection;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.financial.model.FinancialYear;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * Data access interface for calendar year entity implementation.
 * 
 * @author Pradeep
 * 
 */
@Repository("financialYearDao")
public class FinancialYearDaoImpl extends BaseDaoImpl<FinancialYear> implements FinancialYearDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<FinancialYear> findFinancialYearsByBranchId(final Long branchId) {
		TypedQuery<FinancialYear> query = this.getEntityManager().createQuery(
				"select f from FinancialYear f join f.branch br where br.id = :id", FinancialYear.class);
		query.setParameter("id", branchId);
		return query.getResultList();
	}
}
