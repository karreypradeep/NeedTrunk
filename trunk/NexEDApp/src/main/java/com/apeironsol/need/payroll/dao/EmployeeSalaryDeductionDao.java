/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.payroll.dao;

import java.util.Collection;

import com.apeironsol.need.payroll.model.EmployeeSalaryDeduction;
import com.apeironsol.framework.BaseDao;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for employee salary entity.
 * 
 * @author Sunny
 * 
 */
public interface EmployeeSalaryDeductionDao extends BaseDao<EmployeeSalaryDeduction> {

	/**
	 * Retrieve employee salary deductions by employee salary.
	 * 
	 * @param employeeSalaryId
	 *            employee salary id.
	 * @return collection employee salary deductions by employee salary.
	 * @throws BusinessException
	 */
	Collection<EmployeeSalaryDeduction> findEmployeeSalaryDeductionsByEmployeeSalary(final Long employeeSalaryId) throws BusinessException;

}
