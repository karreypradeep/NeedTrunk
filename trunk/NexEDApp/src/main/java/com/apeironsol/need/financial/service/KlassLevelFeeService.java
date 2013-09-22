/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.financial.service;

import java.util.Collection;

import com.apeironsol.need.financial.model.KlassLevelFee;
import com.apeironsol.need.financial.model.KlassLevelFeeCatalog;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;

/**
 * Service Interface for branch fee type periodicals.
 * 
 * @author Pradeep
 * 
 */
public interface KlassLevelFeeService {

	/**
	 * Save class fee.
	 * 
	 * @param klassLevelFee
	 *            class fee.
	 * @return
	 * @throws BusinessException
	 *             In case of exception.
	 * @throws InvalidArgumentException
	 *             In case of exception.
	 */
	KlassLevelFee saveKlassFee(KlassLevelFee klassLevelFee) throws BusinessException, InvalidArgumentException;

	/**
	 * Remove class fee.
	 * 
	 * @param klassLevelFee
	 *            class fee.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	public void removeKlassFee(final KlassLevelFee klassLevelFee) throws BusinessException;

	/**
	 * Find class fees by class id and building block id.
	 * 
	 * @param klassId
	 *            class id.
	 * @param buildingBlockId
	 *            building block id.
	 * @return collection of class fees by class id and building block id.
	 */
	Collection<KlassLevelFee> findKlassFeesByKlassIdAndBuildingBlockId(final Long klassId, final Long buildingBlockId);

	/**
	 * Find latest class fee by class id and building block id.
	 * 
	 * @param klassId
	 *            class id.
	 * @param buildingBlockId
	 *            building block id.
	 * @return latest class fee by class id and building block id.
	 */
	KlassLevelFee findLatestKlassFeeByKlassIdAndBuildingBlockId(Long klassId, Long buildingBlockId);

	/**
	 * Find all class fee payments by class fee id.
	 * 
	 * @param klassFeeId
	 *            class fee id.
	 * @return collection of all class fee payments by class fee id.
	 */
	Collection<KlassLevelFeeCatalog> findAllKlassFeePaymentByKlassFeeId(Long klassFeeId);

	/**
	 * Find class fee by class id and building block id and for current academic
	 * year based on current date.
	 * 
	 * @param klassId
	 *            class id.
	 * @param buildingBlockId
	 *            building block id.
	 * @return class fee by class id and building block id and for current
	 *         academic
	 *         year based on current date.
	 */
	Collection<KlassLevelFee> findAllKlassFeesByKlassIdAndCurrentAcademicYear(Long klassId) throws BusinessException;

	/**
	 * Find class fees by class id and academic year id.
	 * 
	 * @param klassId
	 *            class id.
	 * @param academicYearId
	 *            academic year id.
	 * @return collection of class fees by class id and academic year id.
	 */
	Collection<KlassLevelFee> findAllKlassFeesByKlassIdAndAcademicYearId(Long klassId, Long academicYearId) throws BusinessException;

	/**
	 * Save class fee.
	 * 
	 * @param klassLevelFee
	 *            class fee.
	 * @return
	 * @throws BusinessException
	 *             In case of exception.
	 * @throws InvalidArgumentException
	 *             In case of exception.
	 */
	void applyKlassFeeToExistingActiveStudents(KlassLevelFee klassLevelFee) throws BusinessException, InvalidArgumentException;

}
