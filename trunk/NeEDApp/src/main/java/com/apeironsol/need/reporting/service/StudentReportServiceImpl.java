/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.reporting.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.core.model.Klass;
import com.apeironsol.need.core.model.Relation;
import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.core.model.Student;
import com.apeironsol.need.core.model.StudentSection;
import com.apeironsol.need.core.service.KlassService;
import com.apeironsol.need.core.service.RelationService;
import com.apeironsol.need.core.service.SectionService;
import com.apeironsol.need.core.service.StudentService;
import com.apeironsol.need.financial.service.SectionFinancialService;
import com.apeironsol.need.reporting.ro.BranchRO;
import com.apeironsol.need.reporting.ro.KlassRO;
import com.apeironsol.need.reporting.ro.SectionRO;
import com.apeironsol.need.reporting.ro.StudentRO;
import com.apeironsol.need.util.comparator.StudentROComparator;
import com.apeironsol.need.util.constants.GenderConstant;
import com.apeironsol.need.util.constants.RelationTypeConstant;
import com.apeironsol.need.util.constants.ResidenceConstant;
import com.apeironsol.need.util.constants.StudentReportNamesConstant;
import com.apeironsol.need.util.dataobject.ClassFinancialDO;
import com.apeironsol.need.util.dataobject.SectionFinancialDO;
import com.apeironsol.need.util.dataobject.StudentFinancialAcademicYearDO;
import com.apeironsol.need.util.searchcriteria.StudentSearchCriteria;

/**
 * Data access interface for supplier entity implementation.
 * 
 * @author sunny
 * 
 */
@Service("studentReportService")
@Transactional
public class StudentReportServiceImpl implements StudentReportService {

	@Resource
	private KlassService			klassService;

	@Resource
	private SectionService			sectionService;

	@Resource
	private SectionFinancialService	sectionFinancialService;

	@Resource
	private StudentService			studentService;

	@Resource
	private RelationService			relationService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BranchRO getStudentsFeeReportForBranch(final Branch branch, final StudentSearchCriteria studentSearchCriteria) {
		final List<Long> sectionIds = this.getSectionIDsBySearchCriteria(branch, studentSearchCriteria);

		final Collection<SectionFinancialDO> sectionFinancialDOs = this.sectionFinancialService
				.getSectionFeeFinancialDetailsBySectionIdAndAcademicYearIdForDueDate(sectionIds, studentSearchCriteria.getAcademicYear().getId(), null,
						studentSearchCriteria.getStudentStatus());

		final Map<ResidenceConstant, String> residenceTypeConstants = new HashMap<ResidenceConstant, String>();
		for (final ResidenceConstant residenceConstant : ResidenceConstant.values()) {
			residenceTypeConstants.put(residenceConstant, residenceConstant.getLabel());
		}

		final Map<GenderConstant, String> genderConstants = new HashMap<GenderConstant, String>();
		for (final GenderConstant gender : GenderConstant.values()) {
			genderConstants.put(gender, gender.getLabel());
		}

		final BranchRO branchRO = new BranchRO();
		branchRO.setBranch(branch);
		final Map<Long, KlassRO> mapKlassROs = new HashMap<Long, KlassRO>();
		for (final SectionFinancialDO sectionFinancialDO : sectionFinancialDOs) {
			KlassRO klassRO = null;
			if (mapKlassROs.get(sectionFinancialDO.getSection().getKlass().getId()) == null) {
				klassRO = new KlassRO();
				final ClassFinancialDO classFinancialDO = new ClassFinancialDO();
				klassRO.setClassFinancialDO(classFinancialDO);
				klassRO.setKlass(sectionFinancialDO.getSection().getKlass());
				branchRO.addKlassRO(klassRO);
				mapKlassROs.put(sectionFinancialDO.getSection().getKlass().getId(), klassRO);
			} else {
				klassRO = mapKlassROs.get(sectionFinancialDO.getSection().getKlass().getId());
			}
			final SectionRO sectionRO = new SectionRO();
			sectionRO.setSection(sectionFinancialDO.getSection());
			if (sectionFinancialDO.getStudentFinancialAcademicYearDOs() != null && !sectionFinancialDO.getStudentFinancialAcademicYearDOs().isEmpty()) {
				for (final StudentFinancialAcademicYearDO studentFinancialAcademicYearDO : sectionFinancialDO.getStudentFinancialAcademicYearDOs()) {
					final StudentRO studentRO = new StudentRO();

					studentRO.setStudentAcademicYear(studentFinancialAcademicYearDO.getStudentSection().getStudentAcademicYear());

					studentRO.setGender(genderConstants.get(studentFinancialAcademicYearDO.getStudentSection().getStudentAcademicYear().getStudent()
							.getGender()));

					studentRO.setStudentFinancialAcademicYearDO(studentFinancialAcademicYearDO);

					studentRO.setResidenceType(residenceTypeConstants.get(studentRO.getStudentAcademicYear().getStudent().getResidence()));

					this.setParentOrGuardianName(studentRO.getStudentAcademicYear().getStudent(), studentRO);

					sectionRO.addStudentRO(studentRO);
				}
				Collections.sort(sectionRO.getStudentROList(), new StudentROComparator());
			}
			sectionFinancialDO.setStudentFinancialAcademicYearDOs(null);
			sectionRO.setSectionFinancialDO(sectionFinancialDO);
			klassRO.getClassFinancialDO().addSectionFinancialDO(sectionFinancialDO);
			klassRO.addSectionRO(sectionRO);
		}

		if (branchRO.getKlassROList() != null) {
			for (final KlassRO klassRO : branchRO.getKlassROList()) {
				if (klassRO.getClassFinancialDO() != null && klassRO.getClassFinancialDO().getSectionFinancialDOs() != null) {
					klassRO.getClassFinancialDO().calculateClassFee();
					klassRO.getClassFinancialDO().setSectionFinancialDOs(null);
				}
			}
		}
		return branchRO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BranchRO getStudentsPersonalDetailsReportForBranch(final Branch branch, final StudentSearchCriteria studentSearchCriteria) {
		final List<Long> sectionIds = this.getSectionIDsBySearchCriteria(branch, studentSearchCriteria);

		final Map<GenderConstant, String> genderConstants = new HashMap<GenderConstant, String>();
		for (final GenderConstant gender : GenderConstant.values()) {
			genderConstants.put(gender, gender.getLabel());
		}

		final Map<ResidenceConstant, String> residenceTypeConstants = new HashMap<ResidenceConstant, String>();
		for (final ResidenceConstant residenceConstant : ResidenceConstant.values()) {
			residenceTypeConstants.put(residenceConstant, residenceConstant.getLabel());
		}

		final Collection<StudentSection> studentSections = this.studentService.findAllStudentSectionsBySectionIds(sectionIds);

		final BranchRO branchRO = new BranchRO();
		branchRO.setBranch(branch);
		final Map<Long, KlassRO> mapKlassROs = new HashMap<Long, KlassRO>();
		final Map<Long, SectionRO> mapSectionROs = new HashMap<Long, SectionRO>();
		for (final StudentSection studentSection : studentSections) {
			KlassRO klassRO = null;
			SectionRO sectionRO = null;
			if (mapKlassROs.get(studentSection.getSection().getKlass().getId()) == null) {
				klassRO = new KlassRO();
				klassRO.setKlass(studentSection.getSection().getKlass());
				branchRO.addKlassRO(klassRO);
				mapKlassROs.put(studentSection.getSection().getKlass().getId(), klassRO);
			} else {
				klassRO = mapKlassROs.get(studentSection.getSection().getKlass().getId());
			}
			if (mapSectionROs.get(studentSection.getSection().getId()) == null) {
				sectionRO = new SectionRO();
				sectionRO.setSection(studentSection.getSection());
				klassRO.addSectionRO(sectionRO);
				mapSectionROs.put(studentSection.getSection().getId(), sectionRO);
			} else {
				sectionRO = mapSectionROs.get(studentSection.getSection().getId());
			}
			final StudentRO studentRO = new StudentRO();
			studentRO.setStudentAcademicYear(studentSection.getStudentAcademicYear());
			studentRO.setGender(genderConstants.get(studentSection.getStudentAcademicYear().getStudent().getGender()));
			studentRO.setResidenceType(residenceTypeConstants.get(studentSection.getStudentAcademicYear().getStudent().getResidence()));
			sectionRO.addStudentRO(studentRO);
		}

		return branchRO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BranchRO getStudentsContactDetailsReportForBranch(final Branch branch, final StudentSearchCriteria studentSearchCriteria) {
		final List<Long> sectionIds = this.getSectionIDsBySearchCriteria(branch, studentSearchCriteria);

		final Map<GenderConstant, String> genderConstants = new HashMap<GenderConstant, String>();
		for (final GenderConstant gender : GenderConstant.values()) {
			genderConstants.put(gender, gender.getLabel());
		}

		final Map<ResidenceConstant, String> residenceTypeConstants = new HashMap<ResidenceConstant, String>();
		for (final ResidenceConstant residenceConstant : ResidenceConstant.values()) {
			residenceTypeConstants.put(residenceConstant, residenceConstant.getLabel());
		}

		final Collection<StudentSection> studentSections = this.studentService.findAllStudentSectionsBySectionIds(sectionIds);

		final BranchRO branchRO = new BranchRO();
		branchRO.setBranch(branch);
		final Map<Long, KlassRO> mapKlassROs = new HashMap<Long, KlassRO>();
		final Map<Long, SectionRO> mapSectionROs = new HashMap<Long, SectionRO>();
		for (final StudentSection studentSection : studentSections) {
			KlassRO klassRO = null;
			SectionRO sectionRO = null;
			if (mapKlassROs.get(studentSection.getSection().getKlass().getId()) == null) {
				klassRO = new KlassRO();
				klassRO.setKlass(studentSection.getSection().getKlass());
				branchRO.addKlassRO(klassRO);
				mapKlassROs.put(studentSection.getSection().getKlass().getId(), klassRO);
			} else {
				klassRO = mapKlassROs.get(studentSection.getSection().getKlass().getId());
			}
			if (mapSectionROs.get(studentSection.getSection().getId()) == null) {
				sectionRO = new SectionRO();
				sectionRO.setSection(studentSection.getSection());
				klassRO.addSectionRO(sectionRO);
				mapSectionROs.put(studentSection.getSection().getId(), sectionRO);
			} else {
				sectionRO = mapSectionROs.get(studentSection.getSection().getId());
			}
			final StudentRO studentRO = new StudentRO();
			studentRO.setStudentAcademicYear(studentSection.getStudentAcademicYear());
			studentRO.setGender(genderConstants.get(studentSection.getStudentAcademicYear().getStudent().getGender()));
			studentRO.setResidenceType(residenceTypeConstants.get(studentSection.getStudentAcademicYear().getStudent().getResidence()));
			this.setParentOrGuardianName(studentSection.getStudentAcademicYear().getStudent(), studentRO);
			sectionRO.addStudentRO(studentRO);
		}

		return branchRO;
	}

	/**
	 * Retrieve relation information for student specified and set into report
	 * object.
	 * TODO Need to optimize code as we are hitting database for each and every
	 * student.
	 * 
	 * @param student
	 *            student.
	 * @param studentRO
	 *            student report object.
	 */
	private void setParentOrGuardianName(final Student student, final StudentRO studentRO) {
		final Collection<Relation> studentRelations = this.relationService.findRelationByStudentId(student.getId());
		String fatherName = null, motherName = null, guardianName = null;
		for (final Relation relation : studentRelations) {
			if (RelationTypeConstant.FATHER.equals(relation.getRelationType())) {
				fatherName = relation.getDisplayName();
			} else if (RelationTypeConstant.MOTHER.equals(relation.getRelationType())) {
				motherName = relation.getDisplayName();
			} else {
				guardianName = relation.getDisplayName();
			}
		}
		studentRO.setParentOrGuardianName(fatherName != null ? fatherName : motherName != null ? motherName : guardianName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BranchRO getStudentsReportForBranch(final StudentReportNamesConstant reportName, final Branch branch,
			final StudentSearchCriteria studentSearchCriteria) {
		BranchRO branchRO = null;
		branchRO = this.getStudentsFeeReportForBranch(branch, studentSearchCriteria);
		/*
		 * if (StudentReportNamesConstant.STUDENT_FEE_SUMMARY.equals(reportName)
		 * || StudentReportNamesConstant.STUDENT_FEE_DETAILS.equals(reportName))
		 * {
		 * branchRO = this.getStudentsFeeReportForBranch(branch,
		 * studentSearchCriteria);
		 * } else if
		 * (StudentReportNamesConstant.STUDENT_PERSONAL_DETAILS.equals(
		 * reportName)) {
		 * branchRO = this.getStudentsPersonalDetailsReportForBranch(branch,
		 * studentSearchCriteria);
		 * } else if
		 * (StudentReportNamesConstant.STUDENT_CONTACT_DETAILS.equals(reportName
		 * )) {
		 * branchRO = this.getStudentsContactDetailsReportForBranch(branch,
		 * studentSearchCriteria);
		 * }
		 */
		return branchRO;
	}

	/**
	 * Retrieve section for search criteria.
	 * 
	 * @param branch
	 *            branch.
	 * @param studentSearchCriteria
	 *            search criteria.
	 * @return
	 */
	private List<Long> getSectionIDsBySearchCriteria(final Branch branch, final StudentSearchCriteria studentSearchCriteria) {
		List<Klass> klassList = new ArrayList<Klass>();

		if (studentSearchCriteria.getKlass() != null) {
			klassList.add(studentSearchCriteria.getKlass());
		} else {
			klassList = (List<Klass>) this.klassService.findActiveKlassesByBranchId(branch.getId());
		}

		final List<Long> klassIds = new ArrayList<Long>();

		for (final Klass klass : klassList) {
			klassIds.add(klass.getId());
		}

		List<Section> sectionList = new ArrayList<Section>();
		if (studentSearchCriteria.getSection() != null) {
			sectionList.add(studentSearchCriteria.getSection());
		} else {
			sectionList = (List<Section>) this.sectionService.findActiveSectionsByKlassIdsAndAcademicYearId(klassIds, studentSearchCriteria.getAcademicYear()
					.getId());
		}
		final List<Long> sectionIds = new ArrayList<Long>();

		for (final Section section : sectionList) {
			sectionIds.add(section.getId());
		}
		return sectionIds;
	}

}
