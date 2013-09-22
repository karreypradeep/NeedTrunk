/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.financial.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.model.Klass;
import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.core.model.StudentSection;
import com.apeironsol.need.core.service.KlassService;
import com.apeironsol.need.core.service.SectionService;
import com.apeironsol.need.core.service.StudentService;
import com.apeironsol.need.financial.dao.BranchLevelFeeCatalogDao;
import com.apeironsol.need.financial.dao.BranchLevelFeeDao;
import com.apeironsol.need.financial.dao.StudentFeeDao;
import com.apeironsol.need.financial.model.BranchLevelFee;
import com.apeironsol.need.financial.model.BranchLevelFeeCatalog;
import com.apeironsol.need.financial.model.StudentFee;
import com.apeironsol.need.util.constants.FeeClassificationLevelConstant;
import com.apeironsol.need.util.constants.FeeTypeConstant;
import com.apeironsol.need.util.constants.ResidenceConstant;
import com.apeironsol.need.util.dataobject.AcademicYearBranchLevelFeeDO;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;
import com.apeironsol.framework.exception.SystemException;

/**
 * Service interface for branch level fee service implementation.
 * 
 * @author Pradeep
 * 
 */
@Service
@Transactional
public class BranchLevelFeeServiceImpl implements BranchLevelFeeService {

	@Resource
	private BranchLevelFeeDao			branchLevelFeeDao;

	@Resource
	private BranchLevelFeeCatalogDao	branchLevelFeeCatalogDao;

	@Override
	public BranchLevelFee saveBranchLevelFee(final BranchLevelFee branchLevelFee) throws BusinessException, SystemException {

		// TODO Pradeep , check if the fee is associated with any transactions
		// if so throw business exception.

		branchLevelFee.validate();

		final BranchLevelFee branchLevelFeeLocal = this.branchLevelFeeDao.findBranchLevelFeeByBranchIdAndBunildingBlockIdAndAcademicYearId(branchLevelFee
				.getBranch().getId(), branchLevelFee.getBuildingBlock().getId(), branchLevelFee.getAcademicYear().getId());

		if (branchLevelFeeLocal != null) {
			throw new BusinessException(branchLevelFeeLocal.getBuildingBlock().getName() + " is already defined for the academic year "
					+ branchLevelFeeLocal.getAcademicYear().getDisplayLabel());
		}

		if (branchLevelFee.getId() != null) {

			this.branchLevelFeeCatalogDao.removeBranchLevelFeeCatalogsByBranchLevelFeeId(branchLevelFee.getId());
		}
		return this.branchLevelFeeDao.persist(branchLevelFee);
	}

	@Override
	public BranchLevelFee updateBranchLevelFee(final BranchLevelFee branchLevelFee) {

		// TODO Pradeep , check if the fee is associated with any transactions
		// if so throw business exception.

		branchLevelFee.validate();

		final BranchLevelFee branchLevelFeeLocal = this.branchLevelFeeDao.findById(branchLevelFee.getId());

		if (!branchLevelFeeLocal.getBuildingBlock().equals(branchLevelFee.getBuildingBlock())) {
			throw new BusinessException("Fee type cannot be updated, delete the existing fee and create it as new fee.");
		}

		if (!branchLevelFeeLocal.getAcademicYear().equals(branchLevelFee.getAcademicYear())) {
			throw new BusinessException("Academic Year canonot be update on exisitng branch level fee, delete the exisitng fee and create it as new fee.");
		}

		// Delete existing catalogs if they are new catalogs added. Or else keep
		// them as it is.
		final Collection<BranchLevelFeeCatalog> existingBranchLevelFeeCatalogs = this.branchLevelFeeCatalogDao
				.findBranchFeePaymentsByBranchLevelFeeId(branchLevelFee.getId());
		final Collection<BranchLevelFeeCatalog> deleteBranchLevelFeeCatalogs = new ArrayList<BranchLevelFeeCatalog>();
		for (final BranchLevelFeeCatalog existingBranchLevelFeeCatalog : existingBranchLevelFeeCatalogs) {
			boolean foundCatalog = false;
			for (final BranchLevelFeeCatalog newBranchLevelFeeCatalog : branchLevelFee.getBranchLevelFeeCatalogs()) {
				if (newBranchLevelFeeCatalog.getId() != null && existingBranchLevelFeeCatalog.getId().equals(newBranchLevelFeeCatalog.getId())) {
					foundCatalog = true;
				}
			}
			if (!foundCatalog) {
				deleteBranchLevelFeeCatalogs.add(existingBranchLevelFeeCatalog);
			}
		}

		for (final BranchLevelFeeCatalog deleteBranchLevelFeeCatalog : deleteBranchLevelFeeCatalogs) {
			this.branchLevelFeeCatalogDao.remove(deleteBranchLevelFeeCatalog);
		}
		return this.branchLevelFeeDao.persist(branchLevelFee);

	}

	@Override
	public void removeBranchLevelFee(final BranchLevelFee branchLevelFee) throws BusinessException, SystemException {

		// TODO Pradeep , check if the fee is associated with any transactions
		// if so throw business exception.

		this.branchLevelFeeDao.remove(branchLevelFee);
	}

	@Override
	public Collection<BranchLevelFee> findBranchLevelFeesByBranchId(final Long id) throws BusinessException, SystemException {
		return null;
	}

	@Override
	public Collection<BranchLevelFeeCatalog> findAllBranchLevelFeeCatalogsByBranchLevelFeeId(final Long id) throws BusinessException, SystemException {
		return this.branchLevelFeeCatalogDao.findBranchFeePaymentsByBranchLevelFeeId(id);
	}

	@Override
	public Collection<AcademicYearBranchLevelFeeDO> findAllAcademicYearBranchLevelFeesByBranchId(final Long branchId) throws BusinessException, SystemException {

		final Collection<BranchLevelFee> branchLevelFees = this.branchLevelFeeDao.findAllBranchLevelFeesByBranchId(branchId);

		final Map<AcademicYear, Collection<BranchLevelFee>> academicYearBranchLevelFeeMap = new HashMap<AcademicYear, Collection<BranchLevelFee>>();

		for (final BranchLevelFee branchLevelFee : branchLevelFees) {

			if (academicYearBranchLevelFeeMap.containsKey(branchLevelFee.getAcademicYear())) {

				final Collection<BranchLevelFee> branchLevelFees2 = academicYearBranchLevelFeeMap.get(branchLevelFee.getAcademicYear());
				branchLevelFees2.add(branchLevelFee);

			} else {

				final Collection<BranchLevelFee> branchLevelFees2 = new ArrayList<BranchLevelFee>();
				branchLevelFees2.add(branchLevelFee);

				academicYearBranchLevelFeeMap.put(branchLevelFee.getAcademicYear(), branchLevelFees2);
			}
		}

		final Set<Entry<AcademicYear, Collection<BranchLevelFee>>> entries = academicYearBranchLevelFeeMap.entrySet();

		final Collection<AcademicYearBranchLevelFeeDO> academicYearBranchLevelFeeDOs = new ArrayList<AcademicYearBranchLevelFeeDO>();

		for (final Entry<AcademicYear, Collection<BranchLevelFee>> entry : entries) {

			final AcademicYearBranchLevelFeeDO academicYearBranchLevelFeeDO = new AcademicYearBranchLevelFeeDO();

			academicYearBranchLevelFeeDO.setAcademicYear(entry.getKey());

			academicYearBranchLevelFeeDO.setBranchLevelFees(entry.getValue());

			final Collection<BranchLevelFee> branchLevelFees2 = entry.getValue();

			double total = 0d;
			for (final BranchLevelFee branchLevelFee : branchLevelFees2) {
				total = total + branchLevelFee.getAmount();
			}

			academicYearBranchLevelFeeDO.setTotalBranchLevelFee(total);

			academicYearBranchLevelFeeDOs.add(academicYearBranchLevelFeeDO);
		}

		return academicYearBranchLevelFeeDOs;
	}

	@Override
	public Collection<BranchLevelFee> findBranchLevelFeeByBranchIdAndAcademicYearId(final Long branchId, final Long academicYearId) {

		return this.branchLevelFeeDao.findBranchLevelFeeByBranchIdAndAcademicYearId(branchId, academicYearId);
	}

	@Resource
	SectionService	sectionService;

	@Resource
	KlassService	klassService;

	@Resource
	StudentService	studentService;

	@Resource
	StudentFeeDao	studentFeeDao;

	@Override
	public void applyBranchLevelFeeToExistingActiveStudents(final BranchLevelFee blassLevelFee) throws BusinessException, InvalidArgumentException {
		final Collection<Klass> activelasses = this.klassService.findActiveKlassesByBranchId(blassLevelFee.getBranch().getId());
		for (final Klass activeClass : activelasses) {
			final Collection<Section> activeSections = this.sectionService.findActiveSectionsByKlassIdAndAcademicYearId(activeClass.getId(), blassLevelFee
					.getAcademicYear().getId());
			for (final Section section : activeSections) {
				final Collection<StudentSection> studentSections = this.studentService.findActiveStudentSectionsBySectionId(section.getId());
				for (final StudentSection studentSection : studentSections) {
					if (FeeTypeConstant.HOSTEL_FEE.equals(blassLevelFee.getBuildingBlock().getFeeType())
							&& studentSection.getStudentAcademicYear().getStudent().getResidence().equals(ResidenceConstant.DAY_SCHOOLER)) {
						// don't create hostel fee.
						continue;
					}
					if (this.studentFeeDao.findStudentFeeByStudentAcadmicYearIdAndBranchFeeId(studentSection.getStudentAcademicYear().getId(),
							blassLevelFee.getId()) == null) {
						StudentFee studentFee = new StudentFee();
						studentFee.setFeeClassificationLevel(FeeClassificationLevelConstant.BRANCH_LEVEL);
						studentFee.setBranchLevelFee(blassLevelFee);
						studentFee.setStudentAcademicYear(studentSection.getStudentAcademicYear());
						studentFee = this.studentFeeDao.persist(studentFee);
					}
				}
			}
		}
	}

	@Override
	public Collection<BranchLevelFee> findBranchLevelFeeByBranchIdAndAcademicYearIdAndBuildingBlockId(final Long branchId, final Long academicYearId,
			final Long buildingBlockId) {

		return this.branchLevelFeeDao.findBranchLevelFeeByBranchIdAndAcademicYearIdAndBuildingBlockId(branchId, academicYearId, buildingBlockId);
	}

	@Override
	public Collection<BranchLevelFee> findBranchLevelFeeByBranchIdAndAcademicYearIdAndFeeType(final Long branchId, final Long academicYearId,
			final FeeTypeConstant feeType) {

		return this.branchLevelFeeDao.findBranchLevelFeeByBranchIdAndAcademicYearIdAndFeeType(branchId, academicYearId, feeType);
	}
}
