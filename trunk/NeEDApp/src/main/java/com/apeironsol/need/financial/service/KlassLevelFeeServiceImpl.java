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

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.dao.KlassDao;
import com.apeironsol.need.core.model.BuildingBlock;
import com.apeironsol.need.core.model.Klass;
import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.core.model.StudentSection;
import com.apeironsol.need.core.service.BuildingBlockService;
import com.apeironsol.need.core.service.SectionService;
import com.apeironsol.need.core.service.StudentService;
import com.apeironsol.need.financial.dao.KlassLevelFeeCatalogDao;
import com.apeironsol.need.financial.dao.KlassLevelFeeDao;
import com.apeironsol.need.financial.dao.StudentFeeDao;
import com.apeironsol.need.financial.model.KlassLevelFee;
import com.apeironsol.need.financial.model.KlassLevelFeeCatalog;
import com.apeironsol.need.financial.model.StudentFee;
import com.apeironsol.need.util.constants.BuildingBlockConstant;
import com.apeironsol.need.util.constants.FeeClassificationLevelConstant;
import com.apeironsol.need.util.constants.FeeTypeConstant;
import com.apeironsol.need.util.constants.ResidenceConstant;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;

/**
 * Service implementation interface for Branch fee type periodical.
 * 
 * @author Pradeep
 * 
 */
@Service
@Transactional
public class KlassLevelFeeServiceImpl implements KlassLevelFeeService {

	@Resource
	KlassDao				klassDao;

	@Resource
	KlassLevelFeeDao		klassLevelFeeDao;

	@Resource
	KlassLevelFeeCatalogDao	klassLevelFeeCatalogDao;

	@Resource
	BuildingBlockService	buildingBlockService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public KlassLevelFee saveKlassFee(final KlassLevelFee klassLevelFee) throws BusinessException, InvalidArgumentException {
		// TODO Pradeep , check if the fee is associated with any transactions
		// if so throw business exception.

		klassLevelFee.validate();

		KlassLevelFee klassFeeLocal = this.klassLevelFeeDao.findKlassFeeByKlassidAndBunildingBlockIdAndAcademicYearId(klassLevelFee.getKlass().getId(),
				klassLevelFee.getBuildingBlock().getId(), klassLevelFee.getAcademicYear().getId());

		if (klassFeeLocal != null && (klassLevelFee.getId() == null || klassLevelFee.getId() == 0)) {
			throw new BusinessException(klassFeeLocal.getBuildingBlock().getName() + " for class " + klassFeeLocal.getKlass().getName()
					+ " is already defined for the academic year " + klassFeeLocal.getAcademicYear().getDisplayLabel());
		}

		if (klassLevelFee.getId() != null) {
			this.klassLevelFeeCatalogDao.removeKlassFeePaymentsByKlassFeeId(klassLevelFee);
		}
		return this.klassLevelFeeDao.persist(klassLevelFee);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeKlassFee(final KlassLevelFee klassLevelFee) throws BusinessException {

		// TODO Pradeep , check if the fee is associated with any transactions
		// if so throw business exception.

		this.klassLevelFeeDao.remove(klassLevelFee);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<KlassLevelFee> findKlassFeesByKlassIdAndBuildingBlockId(final Long klassId, final Long buildingBlockId) {
		return this.klassLevelFeeDao.findKlassFeesByKlassIdAndBuildingBlockId(klassId, buildingBlockId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public KlassLevelFee findLatestKlassFeeByKlassIdAndBuildingBlockId(final Long klassId, final Long buildingBlockId) {
		return this.klassLevelFeeDao.findKlassFeeByKlassIdAndBuildingBlockIdAndCurrentAcademicYear(klassId, buildingBlockId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<KlassLevelFee> findAllKlassFeesByKlassIdAndCurrentAcademicYear(final Long klassId) throws BusinessException {

		Klass klass = this.klassDao.findById(klassId);

		Collection<BuildingBlock> feeTypes = this.buildingBlockService.findBuildingBlocksbyBranchIdAndBuildingBlockType(klass.getBranch().getId(),
				BuildingBlockConstant.FEE_TYPE);

		Collection<KlassLevelFee> klassFeePeriodicals = new ArrayList<KlassLevelFee>();
		for (BuildingBlock feeType : feeTypes) {
			KlassLevelFee klassLevelFee = this.klassLevelFeeDao.findKlassFeeByKlassIdAndBuildingBlockIdAndCurrentAcademicYear(klassId, feeType.getId());
			if (klassLevelFee != null) {
				klassFeePeriodicals.add(klassLevelFee);
			}
		}

		return klassFeePeriodicals;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<KlassLevelFeeCatalog> findAllKlassFeePaymentByKlassFeeId(final Long klassFeeId) {
		return this.klassLevelFeeDao.findAllKlassFeePaymentByKlassFeeId(klassFeeId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<KlassLevelFee> findAllKlassFeesByKlassIdAndAcademicYearId(final Long klassId, final Long academicYearId) throws BusinessException {

		Klass klass = this.klassDao.findById(klassId);

		Collection<BuildingBlock> feeTypes = this.buildingBlockService.findBuildingBlocksbyBranchIdAndBuildingBlockType(klass.getBranch().getId(),
				BuildingBlockConstant.FEE_TYPE);

		Collection<KlassLevelFee> klassFeePeriodicals = new ArrayList<KlassLevelFee>();
		for (BuildingBlock feeType : feeTypes) {

			KlassLevelFee klassLevelFee = this.klassLevelFeeDao.findKlassFeeByKlassidAndBunildingBlockIdAndAcademicYearId(klassId, feeType.getId(),
					academicYearId);
			if (klassLevelFee != null) {
				klassFeePeriodicals.add(klassLevelFee);
			}
		}

		return klassFeePeriodicals;

	}

	@Resource
	SectionService	sectionService;

	@Resource
	StudentService	studentService;

	@Resource
	StudentFeeDao	studentFeeDao;

	@Override
	public void applyKlassFeeToExistingActiveStudents(final KlassLevelFee klassLevelFee) throws BusinessException, InvalidArgumentException {
		Collection<Section> activeSections = this.sectionService.findActiveSectionsByKlassIdAndAcademicYearId(klassLevelFee.getKlass().getId(), klassLevelFee
				.getAcademicYear().getId());
		for (Section section : activeSections) {
			Collection<StudentSection> studentSections = this.studentService.findActiveStudentSectionsBySectionId(section.getId());
			for (StudentSection studentSection : studentSections) {
				if (FeeTypeConstant.HOSTEL_FEE.equals(klassLevelFee.getBuildingBlock().getFeeType())
						&& studentSection.getStudentAcademicYear().getStudent().getResidence().equals(ResidenceConstant.DAY_SCHOOLER)) {
					// don't create hostel fee.
					continue;
				}
				if (this.studentFeeDao
						.findStudentFeeByStudentAcadmicYearIdAndKlassFeeId(studentSection.getStudentAcademicYear().getId(), klassLevelFee.getId()) == null) {
					StudentFee studentFee = new StudentFee();
					studentFee.setFeeClassificationLevel(FeeClassificationLevelConstant.KLASS_LEVEL);
					studentFee.setKlassFee(klassLevelFee);
					studentFee.setStudentAcademicYear(studentSection.getStudentAcademicYear());
					studentFee = this.studentFeeDao.persist(studentFee);
				}
			}
		}

	}

}
