/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.dao;

import java.util.Collection;
import java.util.Date;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.framework.BaseDao;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for financial year implementation.
 * 
 * @author Pradeep
 */
public interface AcademicYearDao extends BaseDao<AcademicYear> {

	/**
	 * Find all academic years by branch id.
	 * 
	 * @param branchId
	 *            branch id.
	 * @return collection of all academic years by branch id.
	 */
	Collection<AcademicYear> findAcademicYearsByBranchId(Long branchId);

	/**
	 * Find academic year by branch id and date.
	 * 
	 * @param branchId
	 *            branch id.
	 * @param date
	 *            date.
	 * @return academic year by branch id and date.
	 */
	AcademicYear findAcademicYearByBranchIdAndDate(Long branchId, Date date);

	/**
	 * Find academic years with admissions open for branch id.
	 * 
	 * @param branchId
	 *            branch id.
	 * @return collection of academic years with admissions open for branch id.
	 */
	Collection<AcademicYear> findActiveAcademicYearsByBranchIdAndAdmissionsOpen(Long branchId);

	/**
	 * Find academic years by branch id and status.
	 * 
	 * @param branchId
	 *            branch id.
	 * @param active
	 *            status.
	 * @return collection of academic years by branch id and status.
	 */
	Collection<AcademicYear> findAcademicYearsByBranchIdAndActiveStatus(final Long branchId, final boolean active);

	/**
	 * Return collection of overlapping academic years for branch id and
	 * academic year.
	 * 
	 * @param branchId
	 *            branch id.
	 * @param academicYear
	 *            academic year.
	 * @return collection of overlapping academic years for branch id and
	 *         academic year.
	 */
	Collection<AcademicYear> findOverLappingAcademicYearForBranchIdAndAcademicYear(final Long branchId, final AcademicYear academicYear);

	/**
	 * Find academic year with maximum end date.
	 * 
	 * @param branchId
	 *            branch id.
	 * @param date
	 *            date.
	 * @return academic year by branch and date.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	AcademicYear findLatestAcademicYear(final Long branchId) throws BusinessException;

	/**
	 * Retrieve all academic years for the batch.
	 * 
	 * @param batchId
	 * @return
	 */
	Collection<AcademicYear> findAcademicYearsForBatchId(final Long batchId, Long branchId);

}
