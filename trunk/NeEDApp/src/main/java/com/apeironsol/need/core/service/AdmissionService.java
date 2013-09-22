/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import java.util.Collection;

import com.apeironsol.need.core.model.Address;
import com.apeironsol.need.core.model.AdmissionReservationFee;
import com.apeironsol.need.core.model.BuildingBlock;
import com.apeironsol.need.core.model.EducationHistory;
import com.apeironsol.need.core.model.Klass;
import com.apeironsol.need.core.model.MedicalHistory;
import com.apeironsol.need.core.model.Relation;
import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.core.model.Student;
import com.apeironsol.need.core.model.StudentStatusHistory;
import com.apeironsol.need.util.dataobject.AdmissionFeeDO;
import com.apeironsol.need.util.searchcriteria.AdmissionSearchCriteria;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.SystemException;

/**
 * Service interface for Admission.
 * 
 * @author Pradeep
 * 
 */
public interface AdmissionService {

	/**
	 * Submit admission for review.
	 * 
	 * @param student
	 *            student.
	 * @param relations
	 *            relations.
	 * @param primaryAddress
	 *            primary address.
	 * @param educationHistories
	 *            education history.
	 * @param branchId
	 *            branch id.
	 * @return student saved.
	 * @throws BusinessException
	 *             In case of exception.
	 * @throws SystemException
	 *             In case of exception.
	 */
	Student submitAdmission(final Student student, final Collection<Relation> relations, final Address primaryAddress,
			final Collection<EducationHistory> educationHistories, final Long branchId, final AdmissionReservationFee admissionReservationFee,
			final boolean applicationFeeExternalTransaction, final StudentStatusHistory studentStatusHistory) throws BusinessException, SystemException;

	/**
	 * Review admission.
	 * 
	 * @param student
	 *            student.
	 * @param assignedForKlass
	 *            assigned class.
	 * @param assignedForSection
	 *            assigned section.
	 * @param studentStatusHistory
	 *            student status history.
	 * @return
	 * @throws BusinessException
	 *             In case of exception.
	 * @throws SystemException
	 *             In case of exception.
	 */
	Student anotherReviewAdmission(final Student student, final StudentStatusHistory studentStatusHistory) throws BusinessException, SystemException;

	/**
	 * Review admission.
	 * 
	 * @param student
	 *            student.
	 * @param assignedForKlass
	 *            assigned class.
	 * @param assignedForSection
	 *            assigned section.
	 * @param studentStatusHistory
	 *            student status history.
	 * @return
	 * @throws BusinessException
	 *             In case of exception.
	 * @throws SystemException
	 *             In case of exception.
	 */
	Student reviewAdmission(final Student student, final StudentStatusHistory studentStatusHistory) throws BusinessException, SystemException;

	/**
	 * Approve admission.
	 * 
	 * @param student
	 *            student.
	 * @param studentStatusHistory
	 *            student status history.
	 * @return
	 * @throws BusinessException
	 *             In case of exception.
	 * @throws SystemException
	 *             In case of exception.
	 */
	Student acceptAdmission(final Student student, final Klass acceptedForKlass, final AdmissionReservationFee admissionReservationFee,
			final StudentStatusHistory studentStatusHistory) throws BusinessException, SystemException;

	/**
	 * Approve admission.
	 * 
	 * @param student
	 *            student.
	 * @param studentStatusHistory
	 *            student status history.
	 * @return
	 * @throws BusinessException
	 *             In case of exception.
	 * @throws SystemException
	 *             In case of exception.
	 */
	Student admitStudent(final Student student, final Section admitForSection, final MedicalHistory medicalHistory,
			final Collection<BuildingBlock> admissionSubmittedDocuments, Collection<AdmissionFeeDO> admissionFeeDOs, final boolean deductReservationFee,
			final boolean skipApplicatinFee, final boolean skipReservationFee) throws BusinessException, SystemException;

	/**
	 * Reject admission.
	 * 
	 * @param student
	 *            student.
	 * @param studentStatusHistory
	 *            student status history.
	 * @return
	 * @throws BusinessException
	 *             In case of exception.
	 * @throws SystemException
	 *             In case of exception.
	 */
	Student rejectAdmission(final Student student, final StudentStatusHistory studentStatusHistory) throws BusinessException, SystemException;

	/**
	 * Remove admission.
	 * 
	 * @param student
	 *            student.
	 * @throws BusinessException
	 *             In case of exception.
	 * @throws SystemException
	 *             In case of exception.
	 */
	void removeAdmission(final Student student) throws BusinessException, SystemException;

	/**
	 * Roll back admission current status.
	 * 
	 * @param student
	 *            student.
	 * @param studentStatusHistory
	 *            student status history.
	 * @return
	 * @throws BusinessException
	 *             In case of exception.
	 * @throws SystemException
	 *             In case of exception.
	 */
	Student rollbackAdmissionCurrentState(Student student, final StudentStatusHistory studentStatusHistory) throws BusinessException, SystemException;

	/**
	 * Find all admissions that match the search criteria.
	 * 
	 * @param admissionSearchCriteria
	 *            admission search criteria.
	 * @return collection of all admissions that match the search
	 *         criteria.
	 * @throws BusinessException
	 *             In case of Exception.
	 */
	Collection<Student> findAdmissionsBySearchCriteria(final AdmissionSearchCriteria admissionSearchCriteria) throws BusinessException;

	void saveStudentStatusHistory(final StudentStatusHistory studentStatusHistory, final Student result, final String action);
}
