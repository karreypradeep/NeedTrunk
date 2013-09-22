/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.payroll.dao;

import java.util.Collection;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.payroll.model.EmployeeSalaryDeduction;
import com.apeironsol.framework.BaseDaoImpl;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for employee salary entity implementation.
 * 
 * @author Sunny
 * 
 */
@Repository("employeeSalaryDeductionDao")
public class EmployeeSalaryDeductionDaoImpl extends BaseDaoImpl<EmployeeSalaryDeduction> implements EmployeeSalaryDeductionDao {

	@Override
	public Collection<EmployeeSalaryDeduction> findEmployeeSalaryDeductionsByEmployeeSalary(final Long employeeSalaryId) throws BusinessException {
		TypedQuery<EmployeeSalaryDeduction> query = this.getEntityManager().createQuery(
				"select esd from EmployeeSalaryDeduction esd where esd.employeeSalary.id = :employeeSalaryId", EmployeeSalaryDeduction.class);
		query.setParameter("employeeSalaryId", employeeSalaryId);
		return query.getResultList();
	}

}
