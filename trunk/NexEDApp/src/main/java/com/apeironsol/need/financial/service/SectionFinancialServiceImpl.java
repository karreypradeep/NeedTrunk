/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 * 
 */
package com.apeironsol.need.financial.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.core.model.StudentSection;
import com.apeironsol.need.core.service.SectionService;
import com.apeironsol.need.core.service.StudentService;
import com.apeironsol.need.util.constants.StudentSectionStatusConstant;
import com.apeironsol.need.util.constants.StudentStatusConstant;
import com.apeironsol.need.util.dataobject.SectionFinancialDO;
import com.apeironsol.need.util.dataobject.StudentFinancialAcademicYearDO;

/**
 * Service implementation for section financial details.
 * 
 * @author pradeep
 * 
 */
@Service("sectionFinancialService")
@Transactional
public class SectionFinancialServiceImpl implements SectionFinancialService {

	@Resource
	StudentFinancialService	studentFinancialService;

	@Resource
	StudentService			studentService;

	@Resource
	SectionService			sectionService;

	@Override
	public SectionFinancialDO getSectionFeeFinancialDetailsBySectionIdAndAcademicYearId(final Long sectionId, final Long academicYearId,
			final StudentStatusConstant studentStatus) {
		return this.getSectionFeeFinancialDetailsBySectionIdAndAcademicYearIdForDueDate(sectionId, academicYearId, null, studentStatus);
	}

	@Override
	public Collection<SectionFinancialDO> getSectionFeeFinancialDetailsBySectionIdsAndAcademicYearId(final Collection<Long> sectionIds,
			final Long academicYearId, final StudentStatusConstant studentStatus) {
		return this.getSectionFeeFinancialDetailsBySectionIdAndAcademicYearIdForDueDate(sectionIds, academicYearId, null, studentStatus);
	}

	@Override
	public SectionFinancialDO getSectionFeeFinancialDetailsBySectionIdAndAcademicYearIdForDueDate(final Long sectionId, final Long academicYearId,
			final Date dueDate, final StudentStatusConstant studentStatus) {
		return this.getSectionFeeFinancialDetailsBySectionIdAndAcademicYearIdForDueDate(sectionId, academicYearId, dueDate, studentStatus, true);
	}

	@Override
	public Collection<SectionFinancialDO> getSectionFeeFinancialDetailsBySectionIdAndAcademicYearIdForDueDate(final Collection<Long> sectionIds,
			final Long academicYearId, final Date dueDate, final StudentStatusConstant studentStatus) {
		final Collection<StudentSection> allStudentSections = this.studentService.findAllStudentSectionsBySectionIds(sectionIds);
		final Collection<StudentSection> studentSections = new ArrayList<StudentSection>();
		final Map<Long, StudentSection> mapStudentAcademicYearIdBYStudentSection = new HashMap<Long, StudentSection>();
		final Set<StudentSectionStatusConstant> studentSectionStatus = new HashSet<StudentSectionStatusConstant>();
		if (studentStatus != null && StudentSectionStatusConstant.getStudentSectionStatusForStudentStatus(studentStatus) != null) {
			studentSectionStatus.add(StudentSectionStatusConstant.getStudentSectionStatusForStudentStatus(studentStatus));
		} else {
			studentSectionStatus.add(StudentSectionStatusConstant.ACTIVE);
			studentSectionStatus.add(StudentSectionStatusConstant.DROPOUT);
			studentSectionStatus.add(StudentSectionStatusConstant.ACCEPT_FOR_DROPOUT);
			studentSectionStatus.add(StudentSectionStatusConstant.PROMOTED);
			studentSectionStatus.add(StudentSectionStatusConstant.COMPLETED);
		}

		for (final StudentSection studentSection : allStudentSections) {
			if (studentSectionStatus.contains(studentSection.getStudentSectionStatus())) {
				studentSections.add(studentSection);
				mapStudentAcademicYearIdBYStudentSection.put(studentSection.getStudentAcademicYear().getId(), studentSection);
			}
		}

		final Collection<StudentFinancialAcademicYearDO> studentFinancialAcademicYearDOs = this.studentFinancialService
				.getStudnetFeeFinancialAcademicYearDetailsByAcademicYearIdForDueDate(studentSections, dueDate);
		final Map<Long, SectionFinancialDO> mapSectionFinancialDOs = new HashMap<Long, SectionFinancialDO>();
		StudentSection studentSection = null;
		SectionFinancialDO sectionFinancialDO = null;
		for (final StudentFinancialAcademicYearDO studentFinancialAcademicYearDO : studentFinancialAcademicYearDOs) {
			if (mapStudentAcademicYearIdBYStudentSection
					.get(studentFinancialAcademicYearDO.getStudentAcademicYearFeeSummary().getStudentAcademicYear().getId()) != null) {
				studentSection = mapStudentAcademicYearIdBYStudentSection.get(studentFinancialAcademicYearDO.getStudentAcademicYearFeeSummary()
						.getStudentAcademicYear().getId());
				if (mapSectionFinancialDOs.get(studentSection.getSection().getId()) == null) {
					sectionFinancialDO = new SectionFinancialDO();
					sectionFinancialDO.setName(studentSection.getSection().getName());
					sectionFinancialDO.setSection(studentSection.getSection());
					mapSectionFinancialDOs.put(studentSection.getSection().getId(), sectionFinancialDO);
				} else {
					sectionFinancialDO = mapSectionFinancialDOs.get(studentSection.getSection().getId());
				}
				sectionFinancialDO.addStudentFinancialAcademicYearDO(studentFinancialAcademicYearDO);
			}
		}

		for (final Map.Entry<Long, SectionFinancialDO> entry : mapSectionFinancialDOs.entrySet()) {
			entry.getValue().calculateSectionFee(true);
		}
		return mapSectionFinancialDOs.values();
	}

	@Override
	public SectionFinancialDO getSectionFeeFinancialDetailsBySectionIdAndAcademicYearIdForDueDate(final Long sectionId, final Long academicYearId,
			final Date dueDate, final StudentStatusConstant studentStatus, final boolean clearStudentFinancialDOsIndicator) {
		final Collection<StudentSection> allStudentSections = this.studentService.findAllStudentSectionsBySectionId(sectionId);
		final SectionFinancialDO sectionFinancialDO = new SectionFinancialDO();
		final Collection<StudentSection> studentSections = new ArrayList<StudentSection>();
		final Set<StudentSectionStatusConstant> studentSectionStatus = new HashSet<StudentSectionStatusConstant>();
		if (studentStatus != null && StudentSectionStatusConstant.getStudentSectionStatusForStudentStatus(studentStatus) != null) {
			studentSectionStatus.add(StudentSectionStatusConstant.getStudentSectionStatusForStudentStatus(studentStatus));
		} else {
			studentSectionStatus.add(StudentSectionStatusConstant.ACTIVE);
			studentSectionStatus.add(StudentSectionStatusConstant.DROPOUT);
			studentSectionStatus.add(StudentSectionStatusConstant.ACCEPT_FOR_DROPOUT);
			studentSectionStatus.add(StudentSectionStatusConstant.PROMOTED);
			studentSectionStatus.add(StudentSectionStatusConstant.COMPLETED);
		}
		for (final StudentSection studentSection : allStudentSections) {
			if (studentSectionStatus.contains(studentSection.getStudentSectionStatus())) {
				studentSections.add(studentSection);
			}
		}
		sectionFinancialDO.setStudentFinancialAcademicYearDOs(this.studentFinancialService.getStudnetFeeFinancialAcademicYearDetailsByAcademicYearIdForDueDate(
				studentSections, dueDate));
		final Section section = this.sectionService.findSectionById(sectionId);
		sectionFinancialDO.setName(section.getName());
		sectionFinancialDO.calculateSectionFee(clearStudentFinancialDOsIndicator);
		sectionFinancialDO.setSection(section);
		return sectionFinancialDO;
	}

}
