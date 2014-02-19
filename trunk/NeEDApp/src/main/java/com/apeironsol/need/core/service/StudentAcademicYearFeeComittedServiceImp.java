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

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.dao.StudentAcademicYearFeeComittedDao;
import com.apeironsol.need.core.model.StudentAcademicYearFeeComitted;

/**
 * Service implementation for student academic year.
 * 
 * @author Pradeep
 * 
 */
@Service("studentAcademicYearFeeComittedService")
@Transactional(rollbackFor = Exception.class)
public class StudentAcademicYearFeeComittedServiceImp implements StudentAcademicYearFeeComittedService {

	@Resource
	StudentAcademicYearFeeComittedDao	studentAcademicYearFeeComittedDao;

	@Override
	public StudentAcademicYearFeeComitted findStudentAcademicYearFeeComittedById(final Long id) {
		return this.studentAcademicYearFeeComittedDao.findById(id);
	}

	@Override
	public StudentAcademicYearFeeComitted saveStudentAcademicYearFeeComitted(final StudentAcademicYearFeeComitted studentAcademicYearFeeComitted) {
		return this.studentAcademicYearFeeComittedDao.persist(studentAcademicYearFeeComitted);
	}

	@Override
	public Collection<StudentAcademicYearFeeComitted> saveStudentAcademicYearFeeComitted(
			final Collection<StudentAcademicYearFeeComitted> studentAcademicYearFeeComitted) {
		final Collection<StudentAcademicYearFeeComitted> result = new ArrayList<StudentAcademicYearFeeComitted>();
		for (final StudentAcademicYearFeeComitted stuAcademicYearFeeComitted : studentAcademicYearFeeComitted) {
			result.add(this.saveStudentAcademicYearFeeComitted(stuAcademicYearFeeComitted));
		}
		return result;
	}

	@Override
	public StudentAcademicYearFeeComitted findStudentAcademicYearFeeComittedByStudentIdAndAcademicYearId(final Long studentId, final Long academicYearId) {
		return this.studentAcademicYearFeeComittedDao.findStudentAcademicYearFeeComittedByStudentIdAndAcademicYearId(studentId, academicYearId);
	}

	@Override
	public Collection<StudentAcademicYearFeeComitted> findStudentAcademicYearFeeComittedByStudentId(final Long studentId) {
		return this.studentAcademicYearFeeComittedDao.findStudentAcademicYearFeeComittedByStudentId(studentId);
	}

	@Override
	public Collection<StudentAcademicYearFeeComitted> findStudentAcademicYearFeeComittedByStudentIds(final Long studentIds) {
		return this.studentAcademicYearFeeComittedDao.findStudentAcademicYearFeeComittedByStudentIds(studentIds);
	}

}
