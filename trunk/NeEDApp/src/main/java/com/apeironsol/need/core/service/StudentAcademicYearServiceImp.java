/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.dao.StudentAcademicYearDao;
import com.apeironsol.need.core.model.StudentAcademicYear;

/**
 * Service implementation for student academic year.
 * 
 * @author pradeep
 * 
 */
@Service("studentAcademicYearService")
@Transactional(rollbackFor = Exception.class)
public class StudentAcademicYearServiceImp implements StudentAcademicYearService {

	@Resource
	StudentAcademicYearDao	studentAcademicYearDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StudentAcademicYear findStudentAcademicYearById(final Long id) {
		return this.studentAcademicYearDao.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StudentAcademicYear findStudentAcademicYearByStudentIdAndAcademicYearId(final Long studentId, final Long academicYearId) {
		return this.studentAcademicYearDao.findStudentAcademicYearByStudentIdAndAcademicYearId(studentId, academicYearId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentAcademicYear> findStudentAcademicYearsByStudentId(final Long studentId) {

		return this.studentAcademicYearDao.findStudentAcademicYearByStudentId(studentId);

	}

	@Override
	public StudentAcademicYear findStudentCurrentOrMostRecentAcademicYearByStudentId(final Long studentId) {
		return this.studentAcademicYearDao.findStudentCurrentOrMostRecentAcademicYearByStudentId(studentId);
	}

	@Override
	public StudentAcademicYear saveStudentAcademicYear(final StudentAcademicYear studentAcademicYear) {
		return this.studentAcademicYearDao.persist(studentAcademicYear);
	}

	@Override
	public void removeStudentAcademicYear(final StudentAcademicYear studentAcademicYear) {
		this.studentAcademicYearDao.remove(studentAcademicYear);
	}

	@Override
	public Collection<StudentAcademicYear> findStudentAcademicYearByStudentId(final Long studentId) {
		return this.studentAcademicYearDao.findStudentAcademicYearByStudentId(studentId);
	}

	@Override
	public void removeStudentAcademicYearByStudendIdAndAcademicYearId(final Long studentId, final Long academicYearId) {
		this.studentAcademicYearDao.removeStudentAcademicYearByStudendIdAndAcademicYearId(studentId, academicYearId);
	}
}
