/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.transportation.service;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.SystemException;
import com.apeironsol.need.core.model.StudentAcademicYear;
import com.apeironsol.need.core.model.StudentTransportation;
import com.apeironsol.need.financial.dao.StudentFeeDao;
import com.apeironsol.need.financial.dao.StudentFeeTransactionDetailsDao;
import com.apeironsol.need.financial.model.StudentFee;
import com.apeironsol.need.financial.model.StudentFeeTransactionDetails;
import com.apeironsol.need.transportation.dao.PickUpPointFeeDao;
import com.apeironsol.need.transportation.dao.StudentTransportationDao;
import com.apeironsol.need.transportation.model.PickUpPoint;
import com.apeironsol.need.transportation.model.PickUpPointFee;
import com.apeironsol.need.util.constants.FeeClassificationLevelConstant;
import com.apeironsol.need.util.constants.StudentTransportationStatusConstant;

/**
 * @author Sunny
 * 
 */
@Service("studentTransportationService")
@Transactional(rollbackFor = Exception.class)
public class StudentTransportationServiceImpl implements StudentTransportationService {

	@Resource
	StudentTransportationDao		studentTransportationDao;

	@Resource
	PickUpPointFeeDao				pickUpPointFeeDao;

	@Resource
	StudentFeeDao					studentFeeDao;

	@Resource
	StudentFeeTransactionDetailsDao	studentFeeTransactionDetailsDao;

	@Override
	public StudentTransportation findStudentTransportationById(final Long id) throws BusinessException {
		return this.studentTransportationDao.findById(id);
	}

	@Override
	public Collection<StudentTransportation> findAllStudentTransportations() throws BusinessException {
		return this.studentTransportationDao.findAll();
	}

	@Override
	public StudentTransportation assignStudentTransportation(final StudentTransportation studentTransportation) throws BusinessException {

		if ((studentTransportation != null) && (studentTransportation.getId() == null)) {

			studentTransportation.setStudentTransportationStatus(StudentTransportationStatusConstant.ASSIGN_TRANSPORTATION);

			final PickUpPoint pickUpPoint = studentTransportation.getPickUpPoint();

			final StudentAcademicYear studentAcademicYear = studentTransportation.getStudentAcademicYear();

			final PickUpPointFee pickUpPointFee = this.pickUpPointFeeDao.findPickUpPointFeeByPickUpPointIdAndAcademicYearId(pickUpPoint.getId(),
					studentAcademicYear.getAcademicYear().getId());

			final StudentFee studentFee = new StudentFee();

			studentFee.setPickUpPointFee(pickUpPointFee);

			studentFee.setStudentAcademicYear(studentAcademicYear);

			studentFee.setFeeClassificationLevel(FeeClassificationLevelConstant.TRANSPORTATION_LEVEL);

			this.studentFeeDao.persist(studentFee);

		} else {

			final StudentTransportation studentTransportationLocal = this.studentTransportationDao.findById(studentTransportation.getId());

			if (!studentTransportation.getPickUpPoint().equals(studentTransportationLocal.getPickUpPoint())) {
				throw new BusinessException(
						"It is not allowed to change the pickup point once the transportation is assigned, to the pickup point either unassign it or close it.");
			}
		}

		return this.studentTransportationDao.persist(studentTransportation);
	}

	@Override
	public void unassignStudentTransportation(final Long id) throws BusinessException {

		final StudentTransportation studentTransportation = this.studentTransportationDao.findById(id);

		final StudentAcademicYear studentAcademicYear = studentTransportation.getStudentAcademicYear();

		final PickUpPointFee pickupPointFee = this.pickUpPointFeeDao.findPickUpPointFeeByPickUpPointIdAndAcademicYearId(studentTransportation.getPickUpPoint()
				.getId(), studentAcademicYear.getAcademicYear().getId());

		final StudentFee studentFee = this.studentFeeDao.findStudentFeeByStudentAcadmicYearIdAndPickupPointFeeId(studentAcademicYear.getId(),
				pickupPointFee.getId());

		if (studentFee != null) {
			final Collection<StudentFeeTransactionDetails> studentFeeTransactionDetails = this.studentFeeTransactionDetailsDao
					.findStudentFeeTransactionDetailsByStudentFeeId(studentFee.getId());

			if ((studentFeeTransactionDetails != null) && !studentFeeTransactionDetails.isEmpty()) {
				throw new BusinessException(
						"Unassign transportation is not possiable because payments/deductions/refunds have already made for this transportation.");
			} else {
				this.studentFeeDao.remove(studentFee);
			}
		}

		this.studentTransportationDao.remove(this.findStudentTransportationById(id));
	}

	@Override
	public Collection<StudentTransportation> findAllStudentTransportationsByStudentAcadmicYearId(final Long studentAcademicYearId) throws BusinessException {
		return this.studentTransportationDao.findAllStudentTransportationsByStudentAcadmicYearId(studentAcademicYearId);
	}

	@Override
	public StudentTransportation findAssignedStudentTransportationsByStudentAcadmicYearId(final Long studentAcademicYearId) throws BusinessException,
			SystemException {
		return this.studentTransportationDao.findStudentTransportationsByStudentAcadmicYearIdAndStatus(studentAcademicYearId,
				StudentTransportationStatusConstant.ASSIGN_TRANSPORTATION);
	}

}
