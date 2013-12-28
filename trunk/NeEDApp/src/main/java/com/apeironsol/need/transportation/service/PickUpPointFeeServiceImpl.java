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

import com.apeironsol.need.financial.dao.StudentFeeDao;
import com.apeironsol.need.financial.model.StudentFee;
import com.apeironsol.need.transportation.dao.PickUpPointFeeDao;
import com.apeironsol.need.transportation.dao.StudentTransportationDao;
import com.apeironsol.need.transportation.model.PickUpPointFee;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.SystemException;

/**
 * @author Pradeep
 * 
 */
@Service("pickUpPointFeeService")
@Transactional(rollbackFor = Exception.class)
public class PickUpPointFeeServiceImpl implements PickUpPointFeeService {

	@Resource
	PickUpPointFeeDao				pickUpPointFeeDao;

	@Resource
	PickUpPointFeeCatalogService	pickUpPointFeeCatalogService;
	
	@Resource
	StudentTransportationDao studentTransportationDao;
	
	@Resource
	StudentFeeDao studentFeeDao;

	@Override
	public PickUpPointFee savePickUpPointFee(final PickUpPointFee pickUpPointFee) throws BusinessException {
		
		pickUpPointFee.validate();
		
		
		this.checkForDuplicateAcedemicYear(pickUpPointFee);
		
		if (pickUpPointFee.getId() != null) {
			this.pickUpPointFeeCatalogService.removePickUpPointFeeCatalogsByPickUpPointFeeId(pickUpPointFee.getId());
		}
		
		return this.pickUpPointFeeDao.persist(pickUpPointFee);
	}

	private void checkForDuplicateAcedemicYear(final PickUpPointFee pickUpPointFee) throws BusinessException {
		PickUpPointFee pickUpPointFeeDB = this.pickUpPointFeeDao.findPickUpPointFeeByPickUpPointIdAndAcademicYearId(pickUpPointFee.getPickUpPoint().getId(),
					pickUpPointFee.getAcademicYear().getId());
		
		if (pickUpPointFeeDB != null) {
			if (pickUpPointFeeDB.getId() == null || !pickUpPointFeeDB.getId().equals(pickUpPointFee.getId())) {
				throw new BusinessException("PickUpPointFee for academic year " + pickUpPointFee.getAcademicYear().getDisplayLabel() + " already exists.");
			}
		}
	}

	@Override
	public Collection<PickUpPointFee> findPickUpPointFeesByPickUpPointId(final Long pickUpPointId) {
		return this.pickUpPointFeeDao.findPickUpPointFeesByPickUpPointId(pickUpPointId);
	}

	@Override
	public void removePickUpPointFee(final Long pickUpPointFeeId) throws BusinessException {
		
		PickUpPointFee pickUpPointFee = this.pickUpPointFeeDao.findById(pickUpPointFeeId);
		
		Collection<StudentFee> studentFees = this.studentFeeDao.findStudentFeeByAcadmicYearIdAndPickupPointFeeId(pickUpPointFee.getAcademicYear().getId(), pickUpPointFeeId);
		
		if(studentFees != null && !studentFees.isEmpty()) {
			
			throw new BusinessException("Pickup point fee cannot be removed because it is associated with the student fee.");
		}
		
		this.pickUpPointFeeCatalogService.removePickUpPointFeeCatalogsByPickUpPointFeeId(pickUpPointFeeId);
		
		this.pickUpPointFeeDao.remove(this.findPickUpPointFeeById(pickUpPointFeeId));
	}

	private void removePickUpPointFee(final PickUpPointFee pickUpPointFee) throws BusinessException {
		this.pickUpPointFeeDao.remove(pickUpPointFee);
	}

	@Override
	public void removePickUpPointFeesByPickUpPointId(final Long pickUpPointId) throws BusinessException {
		Collection<PickUpPointFee> pickUpPointFees = this.pickUpPointFeeDao.findPickUpPointFeesByPickUpPointId(pickUpPointId);
		for (PickUpPointFee pickUpPointFee : pickUpPointFees) {
			this.removePickUpPointFee(pickUpPointFee);
		}
	}

	@Override
	public PickUpPointFee findPickUpPointFeeById(final Long pickUpPointFeeId) {
		return this.pickUpPointFeeDao.findById(pickUpPointFeeId);
	}

	@Override
	public Collection<PickUpPointFee> findPickUpPointFeesByAcademicYearId(final Long academicYearId) {
		return this.pickUpPointFeeDao.findPickUpPointFeesByAcademicYearId(academicYearId);
	}

	@Override
	public PickUpPointFee findPickUpPointFeeByPickUpPointIdAndAcademicYearId(final Long pickUpPointId, final Long academicYearId) throws BusinessException,
			SystemException {
		return this.pickUpPointFeeDao.findPickUpPointFeeByPickUpPointIdAndAcademicYearId(pickUpPointId, academicYearId);
	}

}
