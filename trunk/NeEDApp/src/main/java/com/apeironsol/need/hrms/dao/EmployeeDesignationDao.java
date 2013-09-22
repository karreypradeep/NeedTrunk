/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 *
 */
package com.apeironsol.need.hrms.dao;

import java.util.Collection;

import com.apeironsol.need.hrms.model.EmployeeDesignation;
import com.apeironsol.framework.BaseDao;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for employee designation entity.
 *
 * @author Sunny
 *
 */
public interface EmployeeDesignationDao extends BaseDao<EmployeeDesignation> {

	/**
	 * Retrieve all employee designations held by employee using employee id.
	 *
	 * @param employeeId
	 *            employee id.
	 * @return collection of all employee designations held by employee using
	 *         employee id.
	 */
	Collection<EmployeeDesignation> findAllEmployeeDesignationsByEmployeeID(final Long employeeId);

	/**
	 * Remove all employee designations by employee id.
	 *
	 * @param employeeId
	 *            employee id.
	 */
	void removeEmployeeDesignationByEmployeeID(final Long employeeId);

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
	 *
	 * @param employeeId
	 * @return
	 * @throws BusinessException
	 */
	EmployeeDesignation findFirstEmployeeDesignationByEmployeeID(Long employeeId) throws BusinessException;

}
