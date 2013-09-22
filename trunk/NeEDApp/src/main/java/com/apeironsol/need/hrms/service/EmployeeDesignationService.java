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

import com.apeironsol.need.hrms.model.EmployeeDesignation;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Service layer interface for employee designation DAO implementation.
 *
 * @author Sunny
 *
 */
public interface EmployeeDesignationService {

	/**
	 * Save employee designation.
	 *
	 * @param employeeDesignation
	 *            employee designation.
	 * @return saved employee designation.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	EmployeeDesignation createNewEmployeeDesignation(EmployeeDesignation employeeDesignation) throws BusinessException;

	/**
	 * Removes employee designation.
	 *
	 * @param employeeDesignation
	 *            employee designation to be removed.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void removeEmployeeDesignation(EmployeeDesignation employeeDesignation) throws BusinessException;

	/**
	 * Find employee designation by id.
	 *
	 * @param id
	 *            employee designation id.
	 * @return employee designation with supplied id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	EmployeeDesignation findEmployeeDesignationById(Long id) throws BusinessException;

	/**
	 * Find all employee designations of all employees.
	 *
	 * @return collection of all employee designations.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<EmployeeDesignation> findAllEmployeeDesignations() throws BusinessException;

	/**
	 * Find all employee designations of a employee.
	 *
	 * @param employeeId
	 *            employee id.
	 * @return collection of all employee designations of employee.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<EmployeeDesignation> findAllEmployeeDesignationsByEmployeeID(final Long employeeId)
			throws BusinessException;

	/**
	 * Remove all employee designations of a employee with supplied id.
	 *
	 * @param employeeId
	 *            employee id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void removeEmployeeDesignationByEmployeeID(final Long employeeId) throws BusinessException;

	/**
	 * Find employee designation by employee id.
	 *
	 * @param id
	 *            employee id.
	 * @return employee designation with supplied employee id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	EmployeeDesignation findCurrentEmployeeDesignationByEmployeeID(final Long employeeId) throws BusinessException;

	/**
	 * Close employee current designation;
	 * @param closeDate
	 * @return
	 * @throws BusinessException
	 */
	EmployeeDesignation closeEmployeeCurrentDesignation(final String employeeNumber, Date closeDate) throws BusinessException;

}
