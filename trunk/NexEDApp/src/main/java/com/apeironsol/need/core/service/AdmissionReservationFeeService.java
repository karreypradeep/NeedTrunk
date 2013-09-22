/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 *
 */
package com.apeironsol.need.core.service;

import java.util.Collection;

import com.apeironsol.need.core.model.AdmissionReservationFee;
import com.apeironsol.framework.exception.BusinessException;

/**
 * @author sunny
 *  Service interface for AdmissionReservationFee.
 *
 */
public interface AdmissionReservationFeeService {

	/**
	 * Retrieves the admission reservation fee point by id.
	 *
	 * @param id
	 *            the id of admission reservation fee to be retrieved.
	 * @return
	 */
	AdmissionReservationFee findAdmissionReservationFeeById(Long id);

	/**
	 * Retrieves all admission reservation fees available.
	 *
	 * @return all admission reservation fees available.
	 */
	Collection<AdmissionReservationFee> findAllAdmissionReservationFees() throws BusinessException;

	/**
	 * Saves the admission reservation fee.
	 *
	 * @return the saved admission reservation fee.
	 */
	AdmissionReservationFee saveAdmissionReservationFee(AdmissionReservationFee admissionReservationFee) throws BusinessException;

	/**
	 * Deletes the admission reservation fee.
	 *
	 */
	void deleteAdmissionReservationFee(final Long id) throws BusinessException;

	/**
	 * Find admission reservation fee by student id.
	 *
	 * @param sectionId
	 *            sectionId
	 * @return admission reservation fee by student id.
	 */
	AdmissionReservationFee findAdmissionReservationFeeByStudentID(Long studentId);

}
