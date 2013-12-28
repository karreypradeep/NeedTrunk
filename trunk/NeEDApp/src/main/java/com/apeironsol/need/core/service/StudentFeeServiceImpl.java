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

import com.apeironsol.need.financial.dao.StudentFeeDao;
import com.apeironsol.need.financial.model.StudentFee;

/**
 * Service layer implementation for Student Attendance DAO implementation.
 * 
 * @author Pradeep
 *         StudentAttendance
 */
@Service("studentFeeService")
@Transactional(rollbackFor = Exception.class)
public class StudentFeeServiceImpl implements StudentFeeService {

	@Resource
	private StudentFeeDao	studentFeeDAO;

	@Override
	public Collection<StudentFee> findStudentFeesByStudentIdAndAcadmicYearId(final Long studentId, final Long acadmicYearId) {
		return this.studentFeeDAO.findStudentFeesByStudentIdAndAcadmicYearId(studentId, acadmicYearId);
	}

	@Override
	public Collection<StudentFee> findStudentFeesByStudentAcadmicYearId(final Long studentAcadmicYearId) {
		return this.studentFeeDAO.findStudentFeesByStudentAcadmicYearId(studentAcadmicYearId);
	}

	@Override
	public StudentFee findStudentFeeByStudentAcadmicYearIdAndKlassFeeId(final Long studentAcadmicYearId, final Long klassFeeId) {
		return this.studentFeeDAO.findStudentFeeByStudentAcadmicYearIdAndKlassFeeId(studentAcadmicYearId, klassFeeId);
	}

	@Override
	public StudentFee findStudentFeeByStudentAcadmicYearIdAndBranchFeeId(final Long studentAcadmicYearId, final Long branchFeeId) {
		return this.studentFeeDAO.findStudentFeeByStudentAcadmicYearIdAndBranchFeeId(studentAcadmicYearId, branchFeeId);
	}

	@Override
	public StudentFee findStudentFeeByStudentAcadmicYearIdAndStudentFeeId(final Long studentAcadmicYearId, final Long studentFeeId) {
		return this.studentFeeDAO.findStudentFeeByStudentAcadmicYearIdAndStudentFeeId(studentAcadmicYearId, studentFeeId);
	}

	@Override
	public StudentFee findStudentFeeByStudentAcadmicYearIdAndPickupPointFeeId(final Long studentAcadmicYearId, final Long pickupPointFeeId) {
		return this.studentFeeDAO.findStudentFeeByStudentAcadmicYearIdAndPickupPointFeeId(studentAcadmicYearId, pickupPointFeeId);
	}

	@Override
	public Collection<StudentFee> findStudentFeeByAcadmicYearIdAndPickupPointFeeId(final Long acadmicYearId, final Long pickupPointFeeId) {
		return this.studentFeeDAO.findStudentFeeByAcadmicYearIdAndPickupPointFeeId(acadmicYearId, pickupPointFeeId);
	}

	@Override
	public StudentFee saveStudentFee(final StudentFee studentFee) {
		return this.studentFeeDAO.persist(studentFee);
	}

	@Override
	public void removeStudentFee(final StudentFee studentFee) {
		this.studentFeeDAO.remove(studentFee);
	}

}
