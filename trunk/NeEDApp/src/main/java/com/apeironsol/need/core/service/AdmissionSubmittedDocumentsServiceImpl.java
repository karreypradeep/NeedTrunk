/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.dao.AdmissionSubmittedDocumentsDao;
import com.apeironsol.need.core.model.AdmissionSubmittedDocuments;

/**
 * Service layer implementation for StudentSection DAO implementation.
 * 
 * @author Pradeep
 * 
 */
@Service("admissionSubmittedDocumentsService")
@Transactional(rollbackFor = Exception.class)
public class AdmissionSubmittedDocumentsServiceImpl implements AdmissionSubmittedDocumentsService {

	@Resource
	AdmissionSubmittedDocumentsDao	admissionSubmittedDocumentsDao;

	@Override
	public AdmissionSubmittedDocuments saveAdmissionSubmittedDocuments(final AdmissionSubmittedDocuments admissionSubmittedDocuments) {
		return this.admissionSubmittedDocumentsDao.persist(admissionSubmittedDocuments);
	}

	@Override
	public void removeAdmissionSubmittedDocuments(final AdmissionSubmittedDocuments admissionSubmittedDocuments) {
		this.admissionSubmittedDocumentsDao.remove(admissionSubmittedDocuments);

	}

}
