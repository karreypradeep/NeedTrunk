/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.hrms.dao;

import java.util.Collection;
import java.util.Date;

import com.apeironsol.need.hrms.model.EmployeeCTC;
import com.apeironsol.framework.BaseDao;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for employee salary entity.
 * 
 * @author Sunny
 * 
 */
public interface EmployeeCTCDao extends BaseDao<EmployeeCTC> {

	/**
	 * Remove all employee ctc by employee id.
	 * 
	 * @param employeeId
	 *            employee id.
	 */
	void removeAllEmployeeCTCsByEmployeeID(final Long employeeId);

	/**
	 * Find all employee ctc by employee id.
	 * 
	 * @param employeeId
	 *            employee id.
	 * @return collection of all employee ctc by employee id.
	 */
	Collection<EmployeeCTC> findAllEmployeeCTCsByEmployeeID(final Long employeeId);

	/**
	 * 
	 * @param employeeId
	 * @return
	 */
	EmployeeCTC findLatestEmployeeCTCByEmployeeID(final Long employeeId);

	/**
	 * Find employee salary by id.
	 * 
	 * @param id
	 *            employee salary id.
	 * @return employee salary with supplied id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	EmployeeCTC findEmployeeCTCByEmployeeIdAndDate(final Long employeeId, final Date salaryDate) throws BusinessException;
}
