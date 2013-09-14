/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.nexed.payroll.dao;

import java.util.Collection;

import com.apeironsol.nexed.payroll.model.EmployeeSalaryComponent;
import com.apeironsol.framework.BaseDao;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for employee salary entity.
 * 
 * @author Sunny
 * 
 */
public interface EmployeeSalaryComponentDao extends BaseDao<EmployeeSalaryComponent> {

	/**
	 * Retrieve employee salary components by employee salary.
	 * 
	 * @param employeeSalaryId
	 *            employee salary id.
	 * @return collection employee salary components by employee salary.
	 * @throws BusinessException
	 */
	Collection<EmployeeSalaryComponent> findEmployeeSalaryComponentsByEmployeeSalary(final Long employeeSalaryId) throws BusinessException;

}
