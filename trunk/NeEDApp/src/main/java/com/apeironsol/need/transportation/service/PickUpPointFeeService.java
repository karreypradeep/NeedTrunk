/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.transportation.service;

import java.util.Collection;

import com.apeironsol.need.transportation.model.PickUpPointFee;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.SystemException;

/**
 * Service Interface for pickup point fee. This service act as controller for
 * pickup point details.
 * 
 * @author Sandeep
 * 
 */
public interface PickUpPointFeeService {

	/**
	 * 
	 * @param pickUpPointFee
	 * @return
	 * @throws BusinessException
	 */
	PickUpPointFee savePickUpPointFee(PickUpPointFee pickUpPointFee) throws BusinessException;

	/**
	 * 
	 * @param pickUpPointFeeId
	 * @throws BusinessException
	 */
	void removePickUpPointFee(final Long pickUpPointFeeId) throws BusinessException;

	/**
	 * 
	 * @param pickUpPointId
	 * @return
	 */
	Collection<PickUpPointFee> findPickUpPointFeesByPickUpPointId(Long pickUpPointId);

	/**
	 * 
	 * @param pickUpPointId
	 * @throws BusinessException
	 */
	void removePickUpPointFeesByPickUpPointId(final Long pickUpPointId) throws BusinessException;

	/**
	 * 
	 * @param pickUpPointFeeId
	 * @return
	 */
	PickUpPointFee findPickUpPointFeeById(final Long pickUpPointFeeId);

	/**
	 * Find pick up point fees by academic year Id.
	 * 
	 * @param academicYearId
	 *            academic year Id.
	 * @return collection of pick up point fees by academic year Id.
	 */
	Collection<PickUpPointFee> findPickUpPointFeesByAcademicYearId(Long academicYearId);

	/**
	 * Find pickup point from pickup point id and academic year id.
	 * 
	 * @param pickUpPointId
	 * @param academicYearId
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	PickUpPointFee findPickUpPointFeeByPickUpPointIdAndAcademicYearId(Long pickUpPointId, Long academicYearId) throws BusinessException, SystemException;
}
