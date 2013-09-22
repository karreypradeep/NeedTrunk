/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 *
 */
package com.apeironsol.need.hrms.dao;

import java.util.Collection;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.hrms.model.Employee;
import com.apeironsol.need.util.constants.EmploymentCurrentStatusConstant;
import com.apeironsol.need.util.searchcriteria.EmployeeSearchCriteria;
import com.apeironsol.framework.BaseDao;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for employee entity.
 *
 * @author Sunny
 *
 */
public interface EmployeeDao extends BaseDao<Employee> {

	/**
	 * Find all employees by branch id.
	 *
	 * @param branchId
	 *            branch id.
	 * @return collection of employees by branch id.
	 */
	Collection<Employee> findAllEmployeesByBranchId(Long branchId);

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
	 *
	 * @param employeeNumber
	 * @return
	 */
	Employee findAllEmployeesByEmployeeNumber(String employeeNumber);

	/**
	 *
	 * @param username
	 * @return
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
	Collection<Employee> findEmployeesBySearchCriteria(final EmployeeSearchCriteria employeeSearchCriteria) throws BusinessException;

	/**
	 * Find employee by employee number
	 * @param employeeNumber
	 * @return
	 */
	Employee findEmployeeByEmployeeNumber(String employeeNumber);
}
