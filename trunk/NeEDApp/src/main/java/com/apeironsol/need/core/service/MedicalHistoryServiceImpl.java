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

import com.apeironsol.need.core.dao.MedicalHistoryDao;
import com.apeironsol.need.core.model.MedicalHistory;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Service layer implementation for Medical History DAO implementation.
 * 
 * @author Sunny
 *         MedicalHistory
 */
@Service("medicalHistoryService")
@Transactional(rollbackFor = Exception.class)
public class MedicalHistoryServiceImpl implements MedicalHistoryService {

	@Resource
	MedicalHistoryDao	medicalHistoryDao;

	@Override
	public MedicalHistory findMedicalHistoryById(final Long id) throws BusinessException {
		return medicalHistoryDao.findById(id);
	}

	@Override
	public Collection<MedicalHistory> findAllMedicalHistorys() throws BusinessException {
		return medicalHistoryDao.findAll();
	}

	@Override
	public MedicalHistory saveMedicalHistory(final MedicalHistory medicalHistory) throws BusinessException {
		return medicalHistoryDao.persist(medicalHistory);
	}

	@Override
	public void deleteMedicalHistory(final Long id) throws BusinessException {
		medicalHistoryDao.remove(medicalHistoryDao.findById(id));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MedicalHistory findMedicalHistoryByStudentId(final Long studentId) {
		return medicalHistoryDao.findMedicalHistoryByStudentId(studentId);
	}

}
