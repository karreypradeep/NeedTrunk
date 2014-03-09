/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.analysis.revenue.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.analysis.revenue.dataobject.RevenueAcademicYearDO;
import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.core.model.Klass;
import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.core.model.StudentAcademicYearFeeSummary;
import com.apeironsol.need.core.model.StudentSection;
import com.apeironsol.need.core.service.KlassService;
import com.apeironsol.need.core.service.SectionService;
import com.apeironsol.need.core.service.StudentAcademicYearFeeSummaryService;
import com.apeironsol.need.core.service.StudentService;
import com.apeironsol.need.util.constants.FeeCollectedAnalysisTypeConstant;
import com.apeironsol.need.util.constants.StudentSectionStatusConstant;
import com.apeironsol.need.util.searchcriteria.RevenueAnalysisSearchCriteria;

/**
 * Service implementation interface for student.
 * 
 * @author pradeep
 * 
 */
@Service("revenueAnalysisService")
@Transactional(rollbackFor = Exception.class)
public class RevenueAnalysisServiceImpl implements RevenueAnalysisService {

	@Resource
	private StudentAcademicYearFeeSummaryService	studentAcademicYearFeeSummaryService;

	@Resource
	private KlassService							klassService;

	@Resource
	private SectionService							sectionService;

	@Resource
	private StudentService							studentService;

	@Override
	public RevenueAcademicYearDO getRevenueGeneratedForAcademicYearBySearchCriteria(final RevenueAnalysisSearchCriteria revenueAnalysisSearchCriteria) {
		final Collection<StudentAcademicYearFeeSummary> studentAcademicYearFeeSummaries = this.studentAcademicYearFeeSummaryService
				.findStudentAcademicYearFeeSummaryByAcademicYearId(revenueAnalysisSearchCriteria.getAcademicYear().getId());
		final RevenueAcademicYearDO revenueAcademicYearDO = new RevenueAcademicYearDO();
		revenueAcademicYearDO.setStudentAcademicYearFeeSummaries(studentAcademicYearFeeSummaries);
		revenueAcademicYearDO.setBranch(revenueAnalysisSearchCriteria.getBranch());
		if (FeeCollectedAnalysisTypeConstant.BY_COURSE.equals(revenueAnalysisSearchCriteria.getRevenueAnalysisType())) {
			revenueAcademicYearDO.setStudentAcademicYearIdsByKlass(this.getStudentAcademicYearsByKlass(revenueAnalysisSearchCriteria.getAcademicYear(),
					revenueAnalysisSearchCriteria.getBranch()));
		}
		return revenueAcademicYearDO;
	}

	private Map<Klass, Collection<Long>> getStudentAcademicYearsByKlass(final AcademicYear academicYear, final Branch branch) {
		final Map<Klass, Collection<Long>> studentAcademicYearsByKlass = new HashMap<Klass, Collection<Long>>();
		final List<Klass> klassses = this.klassService.findKlassesByBranchId(branch.getId());
		for (final Klass klass : klassses) {
			final Collection<Long> studentAcademicYears = new ArrayList<Long>();
			final Collection<Section> sections = this.sectionService.findAllSectionsByKlassIdAndAcademicYearId(klass.getId(), academicYear.getId());
			final Collection<Long> sectionIds = new ArrayList<Long>();
			for (final Section section : sections) {
				sectionIds.add(section.getId());
			}
			if ((sectionIds != null) && (sectionIds.size() > 0)) {
				final Collection<StudentSection> studentSections = this.studentService.findAllStudentSectionsBySectionIds(sectionIds);
				for (final StudentSection studentSection : studentSections) {
					if (!StudentSectionStatusConstant.TRANSFERED.equals(studentSection.getStudentSectionStatus())) {
						if (!studentAcademicYears.contains(studentSection.getStudentAcademicYear().getId())) {
							studentAcademicYears.add(studentSection.getStudentAcademicYear().getId());
						}
					}
				}
			}
			studentAcademicYearsByKlass.put(klass, studentAcademicYears);
		}
		return studentAcademicYearsByKlass;
	}
}
