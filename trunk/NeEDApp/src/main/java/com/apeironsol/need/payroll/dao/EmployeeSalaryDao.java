/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.payroll.dao;

import java.util.Collection;
import java.util.Date;

import com.apeironsol.need.payroll.model.EmployeeSalary;
import com.apeironsol.need.util.searchcriteria.EmployeeSalarySearchCriteria;
import com.apeironsol.framework.BaseDao;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for employee salary entity.
 * 
 * @author Sunny
 * 
 */
public interface EmployeeSalaryDao extends BaseDao<EmployeeSalary> {

	Collection<EmployeeSalary> findEmployeeSalariesByEmployeeIdAndMonth(final Long employeeId, final Date date);

	/**
	 * Find all employee salaries that match the search criteria.
	 * 
	 * @param employeeSalarySearchCriteria
	 *            employeeSalarySearchCriteria.
	 * @return collection all employee salaries that match the search criteria.
	 * @throws BusinessException
	 *             In case of Exception.
	 */
	Collection<EmployeeSalary> findEmployeeSalariesBySearchCriteria(final EmployeeSalarySearchCriteria employeeSalarySearchCriteria) throws BusinessException;
}
