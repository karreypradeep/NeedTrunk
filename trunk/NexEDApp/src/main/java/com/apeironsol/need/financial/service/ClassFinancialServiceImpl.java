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

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.core.service.KlassService;
import com.apeironsol.need.core.service.SectionService;
import com.apeironsol.need.util.constants.StudentStatusConstant;
import com.apeironsol.need.util.dataobject.ClassFinancialDO;

/**
 * Service implementation for class financial details.
 * 
 * @author pradeep
 * 
 */
@Service("classFinancialService")
@Transactional
public class ClassFinancialServiceImpl implements ClassFinancialService {

	@Resource
	SectionFinancialService	sectionFinancialService;

	@Resource
	SectionService			sectionService;

	@Resource
	KlassService			klassService;

	@Override
	public ClassFinancialDO getClassFeeFinancialDetailsByClassIdAndAcademicYearId(final Long classId, final Collection<Long> sectionIds,
			final Long academicYearId, final StudentStatusConstant studentStatus) {
		return this.getClassFeeFinancialDetailsByClassIdAndAcademicYearIdForDueDate(classId, sectionIds, academicYearId, null, studentStatus);
	}

	@Override
	public Collection<ClassFinancialDO> getClassFeeFinancialDetailsByClassIdsAndAcademicYearId(final Collection<Long> classIds,
			final Collection<Long> sectionIds, final Long academicYearId, final StudentStatusConstant studentStatus) {
		return this.getClassFeeFinancialDetailsByClassIdsAndAcademicYearIdForDueDate(classIds, sectionIds, academicYearId, null, studentStatus);
	}

	@Override
	public ClassFinancialDO getClassFeeFinancialDetailsByClassIdAndAcademicYearIdForDueDate(final Long classId, final Collection<Long> sectionIds,
			final Long academicYearId, final Date dueDate, final StudentStatusConstant studentStatus) {
		final Collection<Section> sections = this.sectionService.findActiveSectionsByKlassIdAndAcademicYearId(classId, academicYearId);
		ClassFinancialDO classFinancialDO = null;
		final Collection<Long> newSectionIds = new ArrayList<Long>();
		if (sectionIds == null || sectionIds.isEmpty()) {
			for (final Section section : sections) {
				newSectionIds.add(section.getId());
			}
		} else {
			newSectionIds.addAll(sectionIds);
		}
		if (newSectionIds != null && !newSectionIds.isEmpty()) {
			classFinancialDO = new ClassFinancialDO();
			classFinancialDO.setSectionFinancialDOs(this.sectionFinancialService.getSectionFeeFinancialDetailsBySectionIdAndAcademicYearIdForDueDate(
					newSectionIds, academicYearId, dueDate, studentStatus));
			classFinancialDO.setName(this.klassService.findKlassById(classId).getName());
			classFinancialDO.calculateClassFee();
		}
		return classFinancialDO;
	}

	@Override
	public Collection<ClassFinancialDO> getClassFeeFinancialDetailsByClassIdsAndAcademicYearIdForDueDate(final Collection<Long> classIds,
			final Collection<Long> sectionIds, final Long academicYearId, final Date dueDate, final StudentStatusConstant studentStatus) {
		final Collection<ClassFinancialDO> classFinancialDOs = new ArrayList<ClassFinancialDO>();
		for (final Long classId : classIds) {
			ClassFinancialDO classFinancialDO = this.getClassFeeFinancialDetailsByClassIdAndAcademicYearIdForDueDate(classId, sectionIds, academicYearId,
					dueDate, studentStatus);
			if (classFinancialDO != null) {
				classFinancialDOs.add(classFinancialDO);
			}
		}
		return classFinancialDOs;
	}

}
