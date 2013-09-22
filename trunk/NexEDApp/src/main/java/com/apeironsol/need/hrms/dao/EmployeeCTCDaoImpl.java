/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.hrms.dao;

import java.util.Collection;
import java.util.Date;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.hrms.model.EmployeeCTC;
import com.apeironsol.framework.BaseDaoImpl;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for employee salary entity implementation.
 * 
 * @author Sunny
 * 
 */
@Repository("employeeCTCDao")
public class EmployeeCTCDaoImpl extends BaseDaoImpl<EmployeeCTC> implements EmployeeCTCDao {

	@Override
	public void removeAllEmployeeCTCsByEmployeeID(final Long employeeId) {
		Query query = this.getEntityManager().createQuery("delete from EmployeeCTC es where es.employee.id = :employeeId");
		query.setParameter("employeeId", employeeId);
		query.executeUpdate();

	}

	@Override
	public Collection<EmployeeCTC> findAllEmployeeCTCsByEmployeeID(final Long employeeId) {
		TypedQuery<EmployeeCTC> query = this.getEntityManager().createQuery("select es from EmployeeCTC es where es.employee.id = :employeeId",
				EmployeeCTC.class);
		query.setParameter("employeeId", employeeId);
		return query.getResultList();
	}

	@Override
	public EmployeeCTC findLatestEmployeeCTCByEmployeeID(final Long employeeId) {
		EmployeeCTC employeeCTC = null;
		try {
			TypedQuery<EmployeeCTC> query = this.getEntityManager().createQuery(
					"select es from EmployeeCTC es where es.employee.id = :employeeId and es.endDate is null", EmployeeCTC.class);
			query.setParameter("employeeId", employeeId);
			employeeCTC = query.getSingleResult();
		} catch (NoResultException nre) {

		}
		return employeeCTC;
	}

	@Override
	public EmployeeCTC findEmployeeCTCByEmployeeIdAndDate(final Long employeeId, final Date salaryDate) throws BusinessException {
		EmployeeCTC employeeCTC = null;
		try {
			TypedQuery<EmployeeCTC> query = this
					.getEntityManager()
					.createQuery(
							"select es from EmployeeCTC es where es.employee.id = :employeeId and ((es.startDate <= :salaryDate and es.endDate is null) or (es.startDate <= :salaryDate and es.endDate >= :salaryDate) )",
							EmployeeCTC.class);
			query.setParameter("employeeId", employeeId);
			query.setParameter("salaryDate", salaryDate);
			employeeCTC = query.getSingleResult();
		} catch (NoResultException nre) {

		}
		return employeeCTC;
	}
}
