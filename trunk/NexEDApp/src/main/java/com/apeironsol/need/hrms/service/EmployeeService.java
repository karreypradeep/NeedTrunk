/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 *
 */
package com.apeironsol.need.hrms.service;

import java.util.Collection;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.hrms.dataobject.EmployeeDO;
import com.apeironsol.need.hrms.model.Employee;
import com.apeironsol.need.hrms.model.EmployeeDesignation;
import com.apeironsol.need.util.constants.EmploymentCurrentStatusConstant;
import com.apeironsol.need.util.searchcriteria.EmployeeSearchCriteria;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Service interface for employee.
 *
 * @author Sunny
 *
 */
public interface EmployeeService {

	/**
	 * Save employee.
	 *
	 * @param employee
	 *            employee to be saved.
	 * @return persisted employee.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Employee saveEmployee(Employee employee) throws BusinessException;

	/**
	 * Delete employee.
	 *
	 * @param employee
	 *            employee to be deleted.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void removeEmployee(Employee employee) throws BusinessException;

	/**
	 * Find employee by Id.
	 *
	 * @param id
	 *            employee Id.
	 * @return employee with supplied Id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Employee findEmployeeById(Long id) throws BusinessException;

	/**
	 * Find all employees.
	 *
	 * @return collections of all employees
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<Employee> findAllEmployees() throws BusinessException;

	/**
	 * Find all employees by branch id.
	 *
	 * @param branchId
	 *            id of branch.
	 * @return collections of all employees in a branch.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<Employee> findAllEmployeesByBranchId(final Long branchId) throws BusinessException;

	/**
	 * Saves employee along with designations and salary.
	 *
	 * @param employee
	 *            employee.
	 * @param employeeDesignation
	 *            employee designation.
	 * @param employeeSalary
	 *            employee salary.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void saveNewEmployeeWizard(final Employee employee, final EmployeeDesignation employeeDesignation) throws BusinessException;

	/**
	 * Find employees by branch id and status.
	 *
	 * @param branchId
	 *            id of branch.
	 * @param status
	 *            status.
	 * @return collections of active employees in a branch with status as
	 *         supplied.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<Employee> findEmployeesByBranchIdAndStatus(final Long branchId, final EmploymentCurrentStatusConstant status) throws BusinessException;

	/**
	 * Find all employees by branch id who are/were active in academic year.
	 *
	 * @param branchId
	 *            id of branch.
	 * @param academicyear
	 *            academic year.
	 * @return collections of all employees in a branch.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<Employee> findAllEmployeesByBranchIdAndAcademicYear(final Long branchId, final AcademicYear academicyear) throws BusinessException;

	/**
	 * Find employees by user name.
	 *
	 * @param username
	 *            user name of employee.
	 * @return employee with supplied user name.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Employee findEmployeeByUsername(String username);

	/**
	 * Find all employees that match the search criteria.
	 *
	 * @param employeeSearchCriteria
	 *            employeeSearchCriteria.
	 * @return collection all employees that match the search criteria.
	 * @throws BusinessException
	 *             In case of Exception.
	 */
	Collection<EmployeeDO> findEmployeesBySearchCriteria(final EmployeeSearchCriteria employeeSearchCriteria) throws BusinessException;

	/**
	 * Find employee details by Id.
	 *
	 * @param id
	 *            employee Id.
	 * @return EmployeeDO with supplied Id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	EmployeeDO findEmployeeDetailsById(Long id) throws BusinessException;

}
