/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.dao.AcademicYearDao;
import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.core.portal.messages.BusinessMessages;
import com.apeironsol.need.transportation.model.PickUpPointFee;
import com.apeironsol.need.transportation.service.PickUpPointFeeService;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;
import com.apeironsol.framework.exception.SystemException;

/**
 * Service implementation interface for calendar year.
 * This service act as controller for calendar year details.
 * 
 * @author Pradeep
 * 
 */
@Service("academicYearService")
@Transactional(rollbackFor = Exception.class)
public class AcademicYearServiceImpl implements AcademicYearService {

	@Resource
	private AcademicYearDao			academicYearDao;

	@Resource
	private PickUpPointFeeService	pickUpPointFeeService;

	@Resource
	private KlassService			klassService;

	@Resource
	private SectionService			sectionService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AcademicYear saveAcademicYear(final AcademicYear academicYear) throws BusinessException, InvalidArgumentException {
		academicYear.validate();
		this.validateAcademicYearForOverLapping(academicYear);
		return this.academicYearDao.persist(academicYear);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AcademicYear findAcademicYearById(final Long id) throws BusinessException {
		return this.academicYearDao.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeAcademicYear(final AcademicYear academicYear) throws BusinessException {

		// if section exits for academic year.
		Collection<Section> sections = this.sectionService.findAllSectionsByAcademicYearId(academicYear.getId());
		if (sections != null && !sections.isEmpty()) {

			throw new BusinessException(BusinessMessages.getResourceBundleName(), BusinessMessages.MSG_CANNOT_DELETE_ACADEMIC_YEAR_SECTIONS_ASSOCIATED, null);
		}

		// if pickup points fees associated with academic year.

		Collection<PickUpPointFee> pickUpPointFees = this.pickUpPointFeeService.findPickUpPointFeesByAcademicYearId(academicYear.getId());
		if (pickUpPointFees != null && !pickUpPointFees.isEmpty()) {

			throw new BusinessException(BusinessMessages.getResourceBundleName(),
					BusinessMessages.MSG_CANNOT_DELETE_ACADEMIC_YEAR_PICKPUP_POINTS_FEES_ASSOCIATED, null);
		}

		this.academicYearDao.remove(academicYear);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<AcademicYear> findAllAcademicYears() throws BusinessException {
		return this.academicYearDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<AcademicYear> findAcademicYearsByBranchId(final Long branchId) throws BusinessException {
		return this.academicYearDao.findAcademicYearsByBranchId(branchId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<AcademicYear> findActiveAcademicYearsByBranchIdAndAdmissionsOpen(final Long branchId) throws BusinessException {
		return this.academicYearDao.findActiveAcademicYearsByBranchIdAndAdmissionsOpen(branchId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AcademicYear findAcademicYearByBranchIdAndDate(final Long branchId, final Date date) {
		return this.academicYearDao.findAcademicYearByBranchIdAndDate(branchId, date);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AcademicYear deactivateAcademicYear(final AcademicYear academicYear) {

		academicYear.setActive(false);

		return this.academicYearDao.persist(academicYear);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AcademicYear activateAcademicYear(final AcademicYear academicYear) {

		this.validateAcademicYear(academicYear);

		academicYear.setActive(true);

		return this.academicYearDao.persist(academicYear);

	}

	/**
	 * Validates supplied academic year.
	 * 
	 * @param academicYear
	 *            academic year to be validated.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	private void validateAcademicYear(final AcademicYear academicYear) throws BusinessException {

		// Validate branch has classes
		AcademicYear pAcademicYear = this.academicYearDao.findById(academicYear.getId());
		Long branchId = pAcademicYear.getBranch().getId();
		this.klassService.validateKlasssForAcademicYear(branchId, pAcademicYear);

		// TODO Pradeep validate if all mandatory branch fees are created for
		// academic year.
	}

	/**
	 * Validate academic year for overlapping with other academic year of a
	 * branch.
	 * 
	 * @param academicYear
	 *            academic year.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	private void validateAcademicYearForOverLapping(final AcademicYear academicYear) throws BusinessException {
		Collection<AcademicYear> academicYears = this.findOverLappingAcademicYearForBranchIdAndAcademicYear(academicYear.getBranch().getId(), academicYear);
		if (!academicYears.isEmpty()) {
			if (academicYear.getId() == null) {
				AcademicYear overLappingAcademicYear = academicYears.iterator().next();
				throw new BusinessException(BusinessMessages.getResourceBundleName(), BusinessMessages.MSG_ACADEMIC_YEAR_IS_OVERLAPPING, new Object[] {
						new SimpleDateFormat("dd/MM/yyyy").format(overLappingAcademicYear.getStartDate()),
						new SimpleDateFormat("dd/MM/yyyy").format(overLappingAcademicYear.getEndDate()) });

			} else {
				for (AcademicYear overLappingAcademicYear : academicYears) {
					if (!academicYear.getId().equals(overLappingAcademicYear.getId())) {
						throw new BusinessException(BusinessMessages.getResourceBundleName(), BusinessMessages.MSG_ACADEMIC_YEAR_IS_OVERLAPPING, new Object[] {
								new SimpleDateFormat("dd/MM/yyyy").format(overLappingAcademicYear.getStartDate()),
								new SimpleDateFormat("dd/MM/yyyy").format(overLappingAcademicYear.getEndDate()) });
					}
				}
			}

		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<AcademicYear> findAcademicYearsByBranchIdAndActiveStatus(final Long branchId, final boolean active) {
		return this.academicYearDao.findAcademicYearsByBranchIdAndActiveStatus(branchId, active);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<AcademicYear> findOverLappingAcademicYearForBranchIdAndAcademicYear(final Long branchId, final AcademicYear academicYear) {
		return this.academicYearDao.findOverLappingAcademicYearForBranchIdAndAcademicYear(branchId, academicYear);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AcademicYear findLatestAcademicYear(final Long branchId) throws BusinessException {
		return this.academicYearDao.findLatestAcademicYear(branchId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<AcademicYear> findAcademicYearsForBatchId(final Long batchId, final Long branchId) throws BusinessException, SystemException {
		return this.academicYearDao.findAcademicYearsForBatchId(batchId, branchId);
	}

}
