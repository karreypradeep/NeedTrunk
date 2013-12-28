/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */

package com.apeironsol.need.core.service;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.dao.KlassDao;
import com.apeironsol.need.core.dao.SubjectDao;
import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.model.Klass;
import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.core.model.Subject;
import com.apeironsol.need.core.portal.messages.BusinessMessages;
import com.apeironsol.need.financial.dao.KlassLevelFeeCatalogDao;
import com.apeironsol.need.financial.dao.KlassLevelFeeDao;
import com.apeironsol.need.financial.model.KlassLevelFee;
import com.apeironsol.need.financial.model.KlassLevelFeeCatalog;
import com.apeironsol.need.financial.service.KlassLevelFeeService;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.SystemException;

/**
 * Service interface implementation for Klass.
 * 
 * @author Pradeep
 * 
 */
@Service("kalssService")
@Transactional(rollbackFor = Exception.class)
public class KlassServiceImpl implements KlassService {

	@Resource
	private KlassDao		klassDao;

	@Resource
	BatchService			batchService;

	@Resource
	KlassLevelFeeService	klassLevelFeeService;

	@Resource
	KlassLevelFeeDao		klassLevelFeeDao;

	@Resource
	SubjectDao				subjectDao;

	@Resource
	KlassLevelFeeCatalogDao	klassLevelFeeCatalogDao;

	@Resource
	SectionService			sectionService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Klass saveKlass(final Klass klass) {
		return this.klassDao.persist(klass);
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void removeKlass(final Klass klass) throws BusinessException, SystemException {

		// Check if section are associated with the class
		Collection<Section> sections = this.sectionService.findAllSectionsByKlassId(klass.getId());

		if (sections != null && !sections.isEmpty()) {

			throw new BusinessException(BusinessMessages.getResourceBundleName(), BusinessMessages.MSG_CANNOT_DELETE_KLASS_SECTIONS_ASSOCIATED, null);
		}

		// Check if fees are associated with the class
		Collection<KlassLevelFee> klassLevelFees = this.klassLevelFeeDao.findKlassFeesByKlassId(klass.getId());

		if (klassLevelFees != null && !klassLevelFees.isEmpty()) {

			for (KlassLevelFee klassLevelFee : klassLevelFees) {

				this.klassLevelFeeDao.remove(klassLevelFee);
			}
		}

		// Check if subjects are associated with the class
		Collection<Subject> subjects = this.subjectDao.findAllSubjectsByKlassId(klass.getId());

		if (subjects != null && !subjects.isEmpty()) {

			throw new BusinessException(BusinessMessages.getResourceBundleName(), BusinessMessages.MSG_CANNOT_DELETE_KLASS_SUBJECTS_ASSOCIATED, null);
		}

		this.klassDao.remove(klass);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Klass findKlassById(final Long id) {

		return this.klassDao.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Klass> findAllKlasses() {
		return this.klassDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Klass> findKlassesByBranchId(final Long branchId) throws BusinessException {
		return this.klassDao.findKlassByBranchId(branchId);
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public Klass activateKlass(final Klass klass) {

		validateKlass(klass);

		klass.setActive(true);

		return this.klassDao.persist(klass);

	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public Klass deactivateKlass(final Klass klass) {

		klass.setActive(false);

		return this.klassDao.persist(klass);

	}

	/**
	 * Validate the class.
	 * 
	 * @param klass
	 *            class to validate.
	 */
	private void validateKlass(final Klass klass) {
		Klass pKlass = this.klassDao.findById(klass.getId());

		Collection<KlassLevelFee> klassLevelFees = this.klassLevelFeeDao.findKlassFeesByKlassId(pKlass.getId());

		if (klassLevelFees == null || klassLevelFees.isEmpty()) {
			throw new BusinessException(BusinessMessages.getResourceBundleName(), BusinessMessages.MSG_KLASS_CANNOT_BE_ACTIVATED_ADD_KLASS_FEES,
					new Object[] { klass.getName() });
		}

		for (KlassLevelFee klassLevelFee : klassLevelFees) {

			Collection<KlassLevelFeeCatalog> klassLevelFeeCatalogs = this.klassLevelFeeCatalogDao.findKlassFeePaymentsByKlassFeeId(klassLevelFee.getId());

			if (klassLevelFeeCatalogs == null || klassLevelFeeCatalogs.isEmpty()) {

				throw new BusinessException(BusinessMessages.getResourceBundleName(),
						BusinessMessages.MSG_KLASS_CANNOT_NOT_BE_ACTIVATED_KLASS_FEES_PAYMENTS_NOT_DEFINED, new Object[] { pKlass.getName(),
								klassLevelFee.getBuildingBlock().getName() });
			}
		}

		// Validate class has subjects defined

		Collection<Subject> subjects = this.subjectDao.findAllSubjectsByKlassId(klass.getId());

		if (subjects == null || subjects.isEmpty()) {
			throw new BusinessException(BusinessMessages.getResourceBundleName(),
					BusinessMessages.MSG_KLASS_CANNOT_NOT_BE_ACTIVATED_KLASS_SUBJECTS_NOT_DEFINED, new Object[] { klass.getName() });
		}

		// Validate class had sections defined.
		Collection<Section> sections = this.sectionService.findActiveSectionsByKlassId(pKlass.getId());

		if (sections == null || sections.isEmpty()) {
			throw new BusinessException(BusinessMessages.getResourceBundleName(), BusinessMessages.MSG_KLASS_CANNOT_NOT_BE_ACTIVATED_SECTIONS_ARE_NOT_DEFINED,
					new Object[] { klass.getName() });
		}

	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void validateKlasssForAcademicYear(final Long branchId, final AcademicYear academicYear) throws BusinessException {
		// Validate branch has classes

		Collection<Klass> klasses = this.klassDao.findActiveKlassByBranchId(branchId);

		if (klasses == null || klasses.isEmpty()) {

			throw new BusinessException(BusinessMessages.getResourceBundleName(), BusinessMessages.MSG_NO_CLASS_DEFINED_FOR_BRANCH_TO_ACTIVATE_ACADEMIC_YEAR,
					new Object[] { academicYear.getDisplayLabel(), academicYear.getBranch().getName() });
		}

		// Validate classes had Fee's

		// TODO Validate if all the Mandatory Fee building blocks are defined
		// for branch.
		boolean sectionDefinedForAcademicYear = false;
		for (Klass klass : klasses) {

			Collection<Section> sections = this.sectionService.findActiveSectionsByKlassIdAndAcademicYearId(klass.getId(), academicYear.getId());
			if (sections != null && !sections.isEmpty()) {
				sectionDefinedForAcademicYear = true;
				// Validate Class Fee
				Collection<KlassLevelFee> klassLevelFees = this.klassLevelFeeDao.findKlassFeesByKlassIdAndAcademicYearId(klass.getId(), academicYear.getId());

				if (klassLevelFees == null || klassLevelFees.isEmpty()) {
					throw new BusinessException(BusinessMessages.getResourceBundleName(),
							BusinessMessages.MSG_ACADEMIC_YEAR_CANNOT_NOT_BE_ACTIVATED_KLASS_FEES_NOT_DEFINED, new Object[] { academicYear.getDisplayLabel(),
									klass.getName() });
				}

				for (KlassLevelFee klassLevelFee : klassLevelFees) {

					Collection<KlassLevelFeeCatalog> klassLevelFeeCatalogs = this.klassLevelFeeCatalogDao.findKlassFeePaymentsByKlassFeeId(klassLevelFee
							.getId());

					if (klassLevelFeeCatalogs == null || klassLevelFeeCatalogs.isEmpty()) {

						throw new BusinessException(BusinessMessages.getResourceBundleName(),
								BusinessMessages.MSG_ACADEMIC_YEAR_CANNOT_NOT_BE_ACTIVATED_KLASS_FEES_PAYMENTS_NOT_DEFINED, new Object[] {
										academicYear.getDisplayLabel(), klassLevelFee.getBuildingBlock().getName() });
					}
				}

				this.sectionService.validateSectionForAcademicYearValidation(academicYear, klass);
			}

		}
		if (!sectionDefinedForAcademicYear) {
			throw new BusinessException(BusinessMessages.getResourceBundleName(), BusinessMessages.MSG_NO_SECTIONS_DEFINED_FOR_ACADEMIC_YEAR,
					new Object[] { academicYear.getDisplayLabel() });
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public Collection<Klass> findActiveKlassesByBranchId(final Long branchId) throws BusinessException {
		return this.klassDao.findActiveKlassByBranchId(branchId);
	}

}
