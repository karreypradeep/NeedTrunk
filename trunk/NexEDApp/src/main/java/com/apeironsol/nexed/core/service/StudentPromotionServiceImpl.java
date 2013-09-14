/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.nexed.core.service;

/**
 * Service implementation interface for student.
 * 
 * @author Pradeep
 * 
 */
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.nexed.core.dao.StudentAcademicYearDao;
import com.apeironsol.nexed.core.dao.StudentSectionDao;
import com.apeironsol.nexed.core.model.AcademicYear;
import com.apeironsol.nexed.core.model.BuildingBlock;
import com.apeironsol.nexed.core.model.Klass;
import com.apeironsol.nexed.core.model.Section;
import com.apeironsol.nexed.core.model.StudentAcademicYear;
import com.apeironsol.nexed.core.model.StudentSection;
import com.apeironsol.nexed.core.model.StudentStatusHistory;
import com.apeironsol.nexed.financial.dao.StudentFeeDao;
import com.apeironsol.nexed.financial.model.BranchLevelFee;
import com.apeironsol.nexed.financial.model.KlassLevelFee;
import com.apeironsol.nexed.financial.model.StudentFee;
import com.apeironsol.nexed.financial.model.StudentLevelFee;
import com.apeironsol.nexed.financial.model.StudentLevelFeeCatalog;
import com.apeironsol.nexed.financial.service.BranchLevelFeeService;
import com.apeironsol.nexed.financial.service.KlassLevelFeeService;
import com.apeironsol.nexed.financial.service.StudentFinancialService;
import com.apeironsol.nexed.financial.service.StudentLevelFeeService;
import com.apeironsol.nexed.util.DateUtil;
import com.apeironsol.nexed.util.constants.BuildingBlockConstant;
import com.apeironsol.nexed.util.constants.FeeClassificationLevelConstant;
import com.apeironsol.nexed.util.constants.FeeTypeConstant;
import com.apeironsol.nexed.util.constants.PaymentFrequencyConstant;
import com.apeironsol.nexed.util.constants.ResidenceConstant;
import com.apeironsol.nexed.util.constants.StudentSectionStatusConstant;
import com.apeironsol.nexed.util.dataobject.StudentFinancialAcademicYearDO;

@Service("studentPromotionService")
@Transactional
public class StudentPromotionServiceImpl implements StudentPromotionService {

	@Resource
	private StudentAcademicYearDao	studentAcademicYearDao;

	@Resource
	private AdmissionService		admissionService;

	@Resource
	private StudentSectionDao		studentSectionDao;

	@Resource
	StudentFeeDao					studentFeeDao;

	@Resource
	private KlassLevelFeeService	klassLevelFeeService;

	@Resource
	private BranchLevelFeeService	branchLevelFeeService;

	@Resource
	private StudentLevelFeeService	studentLevelFeeService;

	@Resource
	private BuildingBlockService	buildingBlockService;

	@Resource
	private StudentFinancialService	studentFinancialService;

	@Override
	public void promoteStudents(final Collection<StudentFinancialAcademicYearDO> studentFinancialAcademicYearDOs, final Section toSection) {

		StudentAcademicYear studentAcademicYear = null;
		StudentSection studentSection = null;
		for (final StudentFinancialAcademicYearDO studentFinancialAcademicYearDO : studentFinancialAcademicYearDOs) {
			studentSection = studentFinancialAcademicYearDO.getStudentSection();
			studentSection.setStudentSectionStatus(StudentSectionStatusConstant.PROMOTED);
			studentSection = this.studentSectionDao.persist(studentSection);

			studentAcademicYear = new StudentAcademicYear();
			studentAcademicYear.setAcademicYear(toSection.getAcademicYear());
			studentAcademicYear.setStudent(studentFinancialAcademicYearDO.getStudent());
			studentAcademicYear.setBatch(studentSection.getStudentAcademicYear().getBatch());
			studentAcademicYear.setSequenceNr(studentSection.getStudentAcademicYear().getSequenceNr() + 1);
			studentAcademicYear.setPreviousStudentAcademicYear(studentSection.getStudentAcademicYear());
			studentAcademicYear = this.studentAcademicYearDao.persist(studentAcademicYear);

			studentSection = new StudentSection();
			studentSection.setSection(toSection);
			studentSection.setSequenceNr(Integer.valueOf(1));
			studentSection.setStudentAcademicYear(studentAcademicYear);
			studentSection.setStudentSectionStatus(StudentSectionStatusConstant.ACTIVE);
			studentSection.setActionDate(DateUtil.getSystemDate());
			studentSection = this.studentSectionDao.persist(studentSection);

			// Generate student fee
			final Klass studentInKlass = studentSection.getSection().getKlass();

			final AcademicYear academicYear = studentAcademicYear.getAcademicYear();

			final Collection<KlassLevelFee> klassLevelFees = this.klassLevelFeeService.findAllKlassFeesByKlassIdAndAcademicYearId(studentInKlass.getId(),
					academicYear.getId());

			for (final KlassLevelFee klassLevelFee : klassLevelFees) {
				if (FeeTypeConstant.HOSTEL_FEE.equals(klassLevelFee.getBuildingBlock().getFeeType())
						&& studentSection.getStudentAcademicYear().getStudent().getResidence().equals(ResidenceConstant.DAY_SCHOOLER)) {
					continue;
				} else if (klassLevelFee.getBuildingBlock().isNewAdmissionFee()) {
					continue;
				}
				final StudentFee studentFee = new StudentFee();
				studentFee.setKlassFee(klassLevelFee);
				studentFee.setStudentAcademicYear(studentAcademicYear);
				studentFee.setFeeClassificationLevel(FeeClassificationLevelConstant.KLASS_LEVEL);
				this.studentFeeDao.persist(studentFee);
			}

			// Add branch level fee.
			final Collection<BranchLevelFee> branchLevelFees = this.branchLevelFeeService.findBranchLevelFeeByBranchIdAndAcademicYearId(studentAcademicYear
					.getStudent().getBranch().getId(), academicYear.getId());

			for (final BranchLevelFee branchLevelFee : branchLevelFees) {

				if (studentSection.getStudentAcademicYear().getStudent().getResidence().equals(ResidenceConstant.DAY_SCHOOLER)
						&& FeeTypeConstant.HOSTEL_FEE.equals(branchLevelFee.getBuildingBlock().getFeeType())) {
					// don't create hostel fee.
					continue;
				} else if (branchLevelFee.getBuildingBlock().isNewAdmissionFee()) {
					continue;
				}

				StudentFee studentFee = new StudentFee();
				studentFee.setFeeClassificationLevel(FeeClassificationLevelConstant.BRANCH_LEVEL);
				studentFee.setBranchLevelFee(branchLevelFee);
				studentFee.setStudentAcademicYear(studentAcademicYear);
				studentFee = this.studentFeeDao.persist(studentFee);
			}

			final Collection<BuildingBlock> feeTypeBuildingBlocks = this.buildingBlockService.findBuildingBlocksbyBranchIdAndBuildingBlockType(studentSection
					.getSection().getKlass().getBranch().getId(), BuildingBlockConstant.FEE_TYPE);
			BuildingBlock pastDueBuildingBlock = null;
			for (final BuildingBlock buildingBlock : feeTypeBuildingBlocks) {
				if (buildingBlock.getFeeType().equals(FeeTypeConstant.PAST_DUE)) {
					pastDueBuildingBlock = buildingBlock;
				}
			}
			// If student is having past due then create the due as additional
			// fee payment.
			if (studentFinancialAcademicYearDO.getNetFeeDue() > 0 && pastDueBuildingBlock != null) {
				StudentLevelFee studentLevelFee = new StudentLevelFee();
				studentLevelFee.setStudentAcademicYear(studentAcademicYear);
				studentLevelFee.setAmount(studentFinancialAcademicYearDO.getNetFeeDue());
				studentLevelFee.setPaymentFrequency(PaymentFrequencyConstant.ONCE);
				studentLevelFee.setBuildingBlock(pastDueBuildingBlock);

				final Collection<StudentLevelFeeCatalog> catalogs = new ArrayList<StudentLevelFeeCatalog>();
				final StudentLevelFeeCatalog studentLevelFeeCatalog = new StudentLevelFeeCatalog();
				studentLevelFeeCatalog.setAmount(studentFinancialAcademicYearDO.getNetFeeDue());
				studentLevelFeeCatalog.setDueDate(studentAcademicYear.getAcademicYear().getStartDate());
				studentLevelFeeCatalog.setStudentLevelFee(studentLevelFee);
				catalogs.add(studentLevelFeeCatalog);

				studentLevelFee.setStudentLevelFeeCatalogs(catalogs);
				studentLevelFee = this.studentLevelFeeService.saveStudentLevelFee(studentLevelFee);

			}

			if (studentAcademicYear != null) {
				this.studentFinancialService.processStudentAcademicYearFeeSummary(studentAcademicYear.getId());
			}
			final StudentStatusHistory history = new StudentStatusHistory();
			history.setComments("Student Promoted from section " + studentFinancialAcademicYearDO.getStudentSection().getSection().getName() + " to section "
					+ toSection.getName());

			this.admissionService.saveStudentStatusHistory(history, studentSection.getStudentAcademicYear().getStudent(), "Promotion");
		}

	}
}
