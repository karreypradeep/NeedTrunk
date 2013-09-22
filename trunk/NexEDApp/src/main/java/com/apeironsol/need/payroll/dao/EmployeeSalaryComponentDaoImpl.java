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

import com.apeironsol.need.payroll.model.EmployeeSalaryComponent;
import com.apeironsol.framework.BaseDaoImpl;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for employee salary entity implementation.
 * 
 * @author Sunny
 * 
 */
@Repository("employeeSalaryComponentDao")
public class EmployeeSalaryComponentDaoImpl extends BaseDaoImpl<EmployeeSalaryComponent> implements EmployeeSalaryComponentDao {

	@Override
	public Collection<EmployeeSalaryComponent> findEmployeeSalaryComponentsByEmployeeSalary(final Long employeeSalaryId) throws BusinessException {
		TypedQuery<EmployeeSalaryComponent> query = this.getEntityManager().createQuery(
				"select esc from EmployeeSalaryComponent esc where esc.employeeSalary.id = :employeeSalaryId", EmployeeSalaryComponent.class);
		query.setParameter("employeeSalaryId", employeeSalaryId);
		return query.getResultList();
	}

}
