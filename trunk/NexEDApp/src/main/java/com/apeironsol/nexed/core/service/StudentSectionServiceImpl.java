/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.nexed.core.service;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.nexed.core.dao.StudentSectionDao;
import com.apeironsol.nexed.core.model.Section;
import com.apeironsol.nexed.core.model.StudentSection;
import com.apeironsol.nexed.util.constants.StudentSectionStatusConstant;

/**
 * Service layer implementation for StudentSection DAO implementation.
 * 
 * @author Pradeep
 * 
 */
@Service("studentSectionService")
@Transactional
public class StudentSectionServiceImpl implements StudentSectionService {

	@Resource
	StudentSectionDao	studentSectionDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StudentSection findStudentSectionByStudentAcademicYearIdAndStatus(final Long studentAcademicYearId, final StudentSectionStatusConstant status) {
		return this.studentSectionDao.findStudentSectionByStudentAcademicYearIdAndStatus(studentAcademicYearId, status);
	}

	@Override
	public StudentSection findLatestStudentSectionByStudentAcademicYearId(final Long studentAcademicYearId) {
		return this.studentSectionDao.findLatestStudentSectionByStudentAcademicYearId(studentAcademicYearId);
	}

	@Override
	public StudentSection findStudentSectionByStudentIdAndSectionId(final Long studentId, final Long sectionId) {
		return this.studentSectionDao.findStudentSectionByStudentIdAndSectionId(studentId, sectionId);
	}

	@Override
	public Collection<StudentSection> findStudentSectionsByStudentAcademicYearId(final Long studentAcademicYearId) {
		return this.studentSectionDao.findStudentSectionByStudendAcademicYearId(studentAcademicYearId);
	}

	@Override
	public Collection<StudentSection> findStudentStudentSectionStatusAndSection(final StudentSectionStatusConstant studentSectionStatus, final Section section) {
		final Collection<Long> sectionIds = new ArrayList<Long>();
		sectionIds.add(section.getId());
		return this.studentSectionDao.findStudentSectionsBySectionIdsAndStatus(sectionIds, studentSectionStatus);
	}

}
