/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.financial.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.apeironsol.need.core.model.BuildingBlock;
import com.apeironsol.need.financial.model.KlassLevelFee;
import com.apeironsol.need.financial.model.KlassLevelFeeCatalog;
import com.apeironsol.framework.BaseDao;

/**
 * Data access interface for branch Fee implementation.
 * 
 * @author Pradeep
 * 
 */
public interface KlassLevelFeeDao extends BaseDao<KlassLevelFee> {

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
	 * Find class fees by class id and building block id and end date.
	 * 
	 * @param klassId
	 *            class id.
	 * @param buildingBlockId
	 *            building block id.
	 * @param endDate
	 *            end date.
	 * @return collection of class fees by class id and building block id and
	 *         end date.
	 */
	KlassLevelFee findKlassFeeByKlassIdAndBuildingBlockIdAndEndDate(final Long klassId, final Long buildingBlockId,
			Date endDate);

	/**
	 * Retrieve class fee types from building block for class id.
	 * 
	 * @param klassId
	 *            class id.
	 * @return collection of class fee types from building block for class id.
	 */
	Collection<String> findKlassFeeTypes(Long klassId);

	/**
	 * Find active class fees by class id and building block id.
	 * 
	 * @param klassId
	 *            class id.
	 * @param buildingBlockId
	 *            building block id.
	 * @return collection of active class fees by class id and building block
	 *         id.
	 */
	KlassLevelFee findActiveKlassFeeByKlassIdAndBuildingBlockId(Long klassId, Long buildingBlockId);

	/**
	 * Find class fees between start date and end date with class id.
	 * 
	 * @param klassId
	 *            class id.
	 * @param startDate
	 *            start date.
	 * @param endDate
	 *            end date.
	 * @return collection of class fees between start date and end date with
	 *         class id.
	 */
	List<KlassLevelFee> findKlassFeeByKlassBetweenStartDateAndEndDate(final Long klassId, final Date startDate,
			final Date endDate);

	/**
	 * Retrieve all fee types by class id.
	 * 
	 * @param klassId
	 *            class id.
	 * @return collection of building blocks.
	 */
	Collection<BuildingBlock> findAllFeeTypesByKlassId(Long klassId);

	/**
	 * Find all class fee payments by class fee id.
	 * 
	 * @param klassFeeId
	 *            class fee id.
	 * @return collection of all class fee payments by class fee id.
	 */
	Collection<KlassLevelFeeCatalog> findAllKlassFeePaymentByKlassFeeId(final Long klassFeeId);

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
	KlassLevelFee findKlassFeeByKlassIdAndBuildingBlockIdAndCurrentAcademicYear(Long klassId, Long buildingBlockId);

	/**
	 * Find class fee by class id and building block id and academic year.
	 * 
	 * @param klassId
	 *            class id.
	 * @param buindingBlockId
	 *            building block id.
	 * @param academicYearId
	 *            academic year id.
	 * @return class fee by class id and building block id and academic year.
	 */
	KlassLevelFee findKlassFeeByKlassidAndBunildingBlockIdAndAcademicYearId(Long klassId, Long buindingBlockId,
			Long academicYearId);

	/**
	 * Find class fees by class id and academic year id.
	 * 
	 * @param klassId
	 *            class id.
	 * @param academicYearId
	 *            academic year id.
	 * @return collection of class fees by class id and academic year id.
	 */
	Collection<KlassLevelFee> findKlassFeesByKlassIdAndAcademicYearId(Long klassId, Long academicYearId);

	/**
	 * Find class fees by class id.
	 * 
	 * @param klassId
	 *            class id.
	 * @return collection of class fees by class id.
	 */
	Collection<KlassLevelFee> findKlassFeesByKlassId(Long klassId);
}
