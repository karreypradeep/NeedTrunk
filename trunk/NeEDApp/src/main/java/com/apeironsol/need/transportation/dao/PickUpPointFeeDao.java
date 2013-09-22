/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.transportation.dao;

import java.util.Collection;

import com.apeironsol.need.transportation.model.PickUpPointFee;
import com.apeironsol.framework.BaseDao;

/**
 * Data access interface for pickup point fee entity.
 * 
 * @author sandeep
 * 
 */
public interface PickUpPointFeeDao extends BaseDao<PickUpPointFee> {

	Collection<PickUpPointFee> findPickUpPointFeesByPickUpPointId(Long pickUpPointId);

	Collection<PickUpPointFee> findPickUpPointFeesByAcademicYearId(Long academicYearId);

	PickUpPointFee findPickUpPointFeeByPickUpPointIdAndAcademicYearId(final Long pickUpPointId,
			final Long academicYearId);

}
