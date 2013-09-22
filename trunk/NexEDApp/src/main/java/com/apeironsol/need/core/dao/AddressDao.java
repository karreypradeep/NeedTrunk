/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.dao;

import com.apeironsol.need.core.model.Address;
import com.apeironsol.need.core.model.Branch;
import com.apeironsol.framework.BaseDao;

/**
 * Data access interface for address entity.
 * 
 * @author Pradeep
 * 
 */
public interface AddressDao extends BaseDao<Address> {

	/**
	 * Returns address by branch.
	 * 
	 * @param branch
	 *            branch object.
	 * @return address by branch.
	 */
	Address findAddressByBranch(Branch branch);

	/**
	 * Find address of relation by relation id.
	 * 
	 * @param id
	 *            relation id.
	 * @return address of relation by relation id.
	 */
	Address findRelationAddressByRelationId(Long id);

	/**
	 * Find address of student by student id.
	 * 
	 * @param studentId
	 * @return address of student by student id.
	 */
	Address findStudentAddressByStudentId(final Long studentId);
}
