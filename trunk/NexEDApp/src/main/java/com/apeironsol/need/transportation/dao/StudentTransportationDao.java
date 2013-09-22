/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.transportation.dao;

import java.util.Collection;

import com.apeironsol.need.core.model.StudentTransportation;
import com.apeironsol.need.util.constants.StudentTransportationStatusConstant;
import com.apeironsol.framework.BaseDao;
import com.apeironsol.framework.exception.BusinessException;

/**
 * @author Sunny
 * 
 *         Data access interface for student transportation entity.
 * 
 */
public interface StudentTransportationDao extends BaseDao<StudentTransportation> {

	/**
	 * Retrieves all student transportations available.
	 * 
	 * @return all student transportations available.
	 */
	Collection<StudentTransportation> findAllStudentTransportationsByStudentAcadmicYearId(
			final Long studentAcademicYearId) throws BusinessException;

	StudentTransportation findStudentTransportationsByStudentAcadmicYearIdAndStatus(final Long studentAcademicYearId,final StudentTransportationStatusConstant studentTransportationStatus) throws BusinessException;
	
	Collection<StudentTransportation> findStudentTransportationByPickupPointId(final Long pickupPointFeeId);

}
