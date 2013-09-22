/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 *
 */
package com.apeironsol.need.hrms.dao;

import java.util.Collection;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.hrms.model.EmployeeDesignation;
import com.apeironsol.framework.BaseDaoImpl;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for employee designation entity implementation.
 *
 * @author Sunny
 *
 */
@Repository("employeeDesignationDao")
public class EmployeeDesignationDaoImpl extends BaseDaoImpl<EmployeeDesignation> implements EmployeeDesignationDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<EmployeeDesignation> findAllEmployeeDesignationsByEmployeeID(final Long employeeId) {
		TypedQuery<EmployeeDesignation> query = this.getEntityManager().createQuery("select ed from EmployeeDesignation ed where ed.employee.id = :employeeId",
				EmployeeDesignation.class);
		query.setParameter("employeeId", employeeId);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeEmployeeDesignationByEmployeeID(final Long employeeId) {
		Query query = this.getEntityManager().createQuery("delete from EmployeeDesignation ed where ed.employee.id = :employeeId");
		query.setParameter("employeeId", employeeId);
		query.executeUpdate();
	}

	@Override
	public EmployeeDesignation findCurrentEmployeeDesignationByEmployeeID(final Long employeeId) throws BusinessException {

		try {

			TypedQuery<EmployeeDesignation> query = this.getEntityManager().createQuery(
					"select ed from EmployeeDesignation ed where ed.employee.id = :employeeId order by ed.startDate desc", EmployeeDesignation.class);

			query.setParameter("employeeId", employeeId);
			return query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		}

	}

	@Override
	public EmployeeDesignation findFirstEmployeeDesignationByEmployeeID(final Long employeeId) throws BusinessException {

		try {

			TypedQuery<EmployeeDesignation> query = this.getEntityManager().createQuery(
					"select ed from EmployeeDesignation ed where ed.employee.id = :employeeId order by ed.startDate asc", EmployeeDesignation.class);

			query.setParameter("employeeId", employeeId);
			return query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		}
	}
}
