/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.payroll.service;

import java.util.Collection;
import java.util.Date;

import com.apeironsol.need.hrms.dataobject.EmployeeSalaryDO;
import com.apeironsol.need.payroll.model.EmployeeSalary;
import com.apeironsol.need.util.constants.SalaryTypeConstant;
import com.apeironsol.need.util.searchcriteria.EmployeeSalarySearchCriteria;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Service layer interface for employee salary DAO implementation.
 * 
 * @author Sunny
 * 
 */
public interface EmployeeSalaryService {

	/**
	 * Find all employee salarys of a employee.
	 * 
	 * @param employeeId
	 *            employee id.
	 * @return collection of all employee salarys of employee.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	EmployeeSalaryDO getEmployeeSalaryDetailsForMonth(final Long employeeId, final SalaryTypeConstant salaryTypeConstant, final Date salaryMonth)
			throws BusinessException;

	/**
	 * Find all employee salarys of a employee.
	 * 
	 * @param employeeId
	 *            employee id.
	 * @return collection of all employee salarys of employee.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	EmployeeSalaryDO saveEmployeeSalary(final EmployeeSalaryDO employeeSalaryDO) throws BusinessException;

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

	public EmployeeSalaryDO getEmployeeSalaryDetailsById(final Long employeeSalaryId) throws BusinessException;

	void removeEmployeeSalary(final EmployeeSalary employeeSalary) throws BusinessException;
}
