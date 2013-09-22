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

import com.apeironsol.need.core.dao.AdmissionReservationFeeDao;
import com.apeironsol.need.core.model.AdmissionReservationFee;
import com.apeironsol.need.util.DateUtil;
import com.apeironsol.framework.exception.BusinessException;

/**
 * 
 * @author sunny
 *         Service interface implementation for AdmissionReservationFee.
 * 
 */
@Service("admissionReservationFeeService")
@Transactional
public class AdmissionReservationFeeServiceImpl implements AdmissionReservationFeeService {

	@Resource
	AdmissionReservationFeeDao	admissionReservationFeeDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AdmissionReservationFee findAdmissionReservationFeeById(final Long id) {
		return this.admissionReservationFeeDao.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<AdmissionReservationFee> findAllAdmissionReservationFees() throws BusinessException {
		return this.admissionReservationFeeDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AdmissionReservationFee saveAdmissionReservationFee(final AdmissionReservationFee studentRegistrationFee) throws BusinessException {

		if (studentRegistrationFee != null && studentRegistrationFee.getApplicationFormFee() != null && studentRegistrationFee.getApplicationFormFee() > 0d
				&& studentRegistrationFee.getApplicationFeePaidDate() == null) {
			studentRegistrationFee.setApplicationFeePaidDate(DateUtil.getSystemDate());
		}

		if (studentRegistrationFee != null && studentRegistrationFee.getReservationFee() != null && studentRegistrationFee.getReservationFee() > 0d
				&& studentRegistrationFee.getReservationFeePaidDate() == null) {
			studentRegistrationFee.setReservationFeePaidDate(DateUtil.getSystemDate());
		}

		return this.admissionReservationFeeDao.persist(studentRegistrationFee);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAdmissionReservationFee(final Long id) throws BusinessException {
		this.admissionReservationFeeDao.remove(this.findAdmissionReservationFeeById(id));
	}

	@Override
	public AdmissionReservationFee findAdmissionReservationFeeByStudentID(final Long studentId) {
		return this.admissionReservationFeeDao.findAdmissionReservationFeeByStudentID(studentId);
	}

}
