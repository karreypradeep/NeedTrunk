/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import java.util.Collection;
import java.util.Date;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;
import com.apeironsol.framework.exception.SystemException;

/**
 * Data access interface for Student Attendance entity implementation.
 * 
 */
public interface AcademicYearService {

	/**
	 * Find academic year by Id.
	 * 
	 * @param id
	 *            academic year Id.
	 * @return academic year with supplied id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	AcademicYear findAcademicYearById(Long id) throws BusinessException;

	/**
	 * Save academic year.
	 * 
	 * @param academicYear
	 *            academic year to be saved.
	 * @return persisted academic year.
	 * @throws BusinessException
	 *             In case of exception.
	 * @throws InvalidArgumentException
	 */
	AcademicYear saveAcademicYear(AcademicYear academicYear) throws BusinessException, InvalidArgumentException;

	/**
	 * Remove academic year.
	 * 
	 * @param academicYear
	 *            academic year to be removed.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void removeAcademicYear(AcademicYear academicYear) throws BusinessException;

	/**
	 * Find all academic years
	 * 
	 * @return collection of all academic years.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<AcademicYear> findAllAcademicYears() throws BusinessException;

	/**
	 * Find all academic years for branch.
	 * 
	 * @param branchId
	 *            branch id.
	 * @return collection of all academic years for branch.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<AcademicYear> findAcademicYearsByBranchId(final Long branchId) throws BusinessException;

	/**
	 * Find academic year by branch and date.
	 * 
	 * @param branchId
	 *            branch id.
	 * @param date
	 *            date.
	 * @return academic year by branch and date.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	AcademicYear findAcademicYearByBranchIdAndDate(final Long branchId, Date date) throws BusinessException;

	/**
	 * Find academic years of a branch for which admission are open.
	 * 
	 * @param branchId
	 *            branch id.
	 * @return collection of academic years of a branch for which admission are
	 *         open.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<AcademicYear> findActiveAcademicYearsByBranchIdAndAdmissionsOpen(Long branchId) throws BusinessException;

	/**
	 * Activate supplied academic year.
	 * 
	 * @param academicYear
	 *            academic year to activate.
	 * @return activated academic year.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	AcademicYear activateAcademicYear(AcademicYear academicYear) throws BusinessException;

	/**
	 * Deactivate supplied academic year.
	 * 
	 * @param academicYear
	 *            academic year to deactivate.
	 * @return deactivated academic year.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	AcademicYear deactivateAcademicYear(AcademicYear academicYear) throws BusinessException;

	/**
	 * Find academic years for branch id and with active status supplied.
	 * 
	 * @param branchId
	 *            branch id.
	 * @param active
	 *            active status.
	 * @return collection of academic years for branch id and with active status
	 *         supplied.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<AcademicYear> findAcademicYearsByBranchIdAndActiveStatus(final Long branchId, final boolean active) throws BusinessException;

	/**
	 * Find overlapping academic year for branch id and academic year.
	 * 
	 * @param branchId
	 *            branch Id.
	 * @param academicYear
	 *            academic year.
	 * @return collection of overlapping academic year for branch id and
	 *         academic year.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<AcademicYear> findOverLappingAcademicYearForBranchIdAndAcademicYear(final Long branchId, final AcademicYear academicYear)
			throws BusinessException;

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
	Collection<AcademicYear> findAcademicYearsForBatchId(final Long batchId, Long branchId) throws BusinessException, SystemException;

}
