/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.hrms.service;

import java.util.Collection;
import java.util.Date;

import com.apeironsol.need.hrms.dataobject.EmployeeCTCDO;
import com.apeironsol.need.hrms.model.EmployeeCTC;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Service layer interface for employee salary DAO implementation.
 * 
 * @author Sunny
 * 
 */
public interface EmployeeCTCService {

	/**
	 * Save employee salary.
	 * 
	 * @param employeeCTCDO
	 *            employee salary.
	 * @return saved employee salary.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	EmployeeCTCDO saveEmployeeCTC(EmployeeCTCDO employeeCTCDO) throws BusinessException;

	/**
	 * Removes employee salary.
	 * 
	 * @param employeeCTC
	 *            employee salary to be removed.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void removeEmployeeCTC(EmployeeCTC employeeCTC) throws BusinessException;

	/**
	 * Find employee salary by id.
	 * 
	 * @param id
	 *            employee salary id.
	 * @return employee salary with supplied id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	EmployeeCTC findEmployeeCTCById(Long id) throws BusinessException;

	/**
	 * Find all employee salarys of all employees.
	 * 
	 * @return collection of all employee salarys.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<EmployeeCTC> findAllEmployeeCTCs() throws BusinessException;

	/**
	 * Remove all employee salarys of a employee with supplied id.
	 * 
	 * @param employeeId
	 *            employee id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void removeEmployeeCTCByEmployeeID(final Long employeeId) throws BusinessException;

	/**
	 * Find all employee salarys of a employee.
	 * 
	 * @param employeeId
	 *            employee id.
	 * @return collection of all employee salarys of employee.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<EmployeeCTC> findAllEmployeeCTCsByEmployeeID(final Long employeeId) throws BusinessException;

	/**
	 * Find employee salary by id.
	 * 
	 * @param id
	 *            employee salary id.
	 * @return employee salary with supplied id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	EmployeeCTCDO findEmployeeCTCByEmployeeIdAndDate(final Long employeeId, final Date salaryDate) throws BusinessException;

}
