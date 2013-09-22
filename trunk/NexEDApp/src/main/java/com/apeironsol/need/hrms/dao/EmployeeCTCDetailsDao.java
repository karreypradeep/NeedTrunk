/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.hrms.dao;

import java.util.Collection;

import com.apeironsol.need.hrms.model.EmployeeCTCDetails;
import com.apeironsol.framework.BaseDao;

/**
 * Data access interface for employee salary entity.
 * 
 * @author Sunny
 * 
 */
public interface EmployeeCTCDetailsDao extends BaseDao<EmployeeCTCDetails> {

	/**
	 * Remove all employee ctc by employee id.
	 * 
	 * @param employeeId
	 *            employee id.
	 */
	void removeAllEmployeeCTCsByEmployeeCTCID(final Long employeeCTCId);

	/**
	 * Find all employee ctc details by employee ctc id.
	 * 
	 * @param employeeCTCId
	 *            employee id.
	 * @return collection of all employee ctc by employee id.
	 */
	Collection<EmployeeCTCDetails> findAllEmployeeCTCDetailsByEmployeeCTCID(final Long employeeCTCId);

}
