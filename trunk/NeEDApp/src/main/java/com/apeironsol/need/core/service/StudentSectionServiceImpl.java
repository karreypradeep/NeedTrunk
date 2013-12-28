/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.need.core.dao.StudentSectionDao;
import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.core.model.StudentSection;
import com.apeironsol.need.util.constants.StudentSectionStatusConstant;
import com.apeironsol.need.util.searchcriteria.StudentSearchCriteria;

/**
 * Service layer implementation for StudentSection DAO implementation.
 * 
 * @author Pradeep
 * 
 */
@Service("studentSectionService")
@Transactional(rollbackFor = Exception.class)
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

	@Override
	public Collection<StudentSection> findStudentSectionByStudendAcademicYearId(final Long studentAcademicYearId) {
		return this.studentSectionDao.findStudentSectionByStudendAcademicYearId(studentAcademicYearId);
	}

	@Override
	public void removeStudentSectionByStudendAcademicYearId(final Long studentAcademicYearId) {
		this.studentSectionDao.removeStudentSectionByStudendAcademicYearId(studentAcademicYearId);
	}

	@Override
	public Collection<StudentSection> findStudentSectionsBySectionId(final Long sectionId) {
		return this.studentSectionDao.findStudentSectionsBySectionId(sectionId);
	}

	@Override
	public Collection<StudentSection> findStudentSectionsBySectionIdAndStateActive(final Long sectionId) {
		return this.studentSectionDao.findStudentSectionsBySectionIdAndStateActive(sectionId);
	}

	@Override
	public StudentSection findStudentSectionByStudentAcademicYearIdAndSectionId(final Long studentAcademicYearId, final Long sectionId) {
		return this.studentSectionDao.findStudentSectionByStudentAcademicYearIdAndSectionId(studentAcademicYearId, sectionId);
	}

	@Override
	public Collection<StudentSection> findStudentSectionsBySectionIdsAndStatus(final Collection<Long> sectionIds, final StudentSectionStatusConstant status) {
		return this.studentSectionDao.findStudentSectionsBySectionIdsAndStatus(sectionIds, status);
	}

	@Override
	public int findNumberOfActiveStudentsBySectionId(final Long sectionId) throws BusinessException {
		return this.studentSectionDao.findNumberOfActiveStudentsBySectionId(sectionId);
	}

	@Override
	public Map<Long, Integer> findNumberOfActiveStudentsBySectionIds(final Collection<Long> sectionIds) {
		return this.studentSectionDao.findNumberOfActiveStudentsBySectionIds(sectionIds);
	}

	@Override
	public Collection<StudentSection> findStudentSectionsBySearchCriteria(final StudentSearchCriteria studentSearchCriteria) throws BusinessException {
		return this.studentSectionDao.findStudentSectionsBySearchCriteria(studentSearchCriteria);
	}

	@Override
	public StudentSection findStudentSectionByStudentAcademicYearIdAndActiveStatus(final Long studentAcademicYearId) {
		return this.studentSectionDao.findStudentSectionByStudentAcademicYearIdAndActiveStatus(studentAcademicYearId);
	}

	@Override
	public Collection<StudentSection> findAllStudentSectionsBySectionIds(final Collection<Long> sectionIds) {
		return this.studentSectionDao.findAllStudentSectionsBySectionIds(sectionIds);
	}

	@Override
	public StudentSection saveStudentSection(final StudentSection studentSection) {
		return this.studentSectionDao.persist(studentSection);
	}

	@Override
	public void removeStudentSection(final StudentSection studentSection) {
		this.studentSectionDao.remove(studentSection);

	}

}
