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

import com.apeironsol.need.core.dao.EducationHistoryDao;
import com.apeironsol.need.core.model.EducationHistory;

/**
 * Service layer implementation for StudentSection DAO implementation.
 * 
 * @author Pradeep
 * 
 */
@Service("educationHistoryService")
@Transactional(rollbackFor = Exception.class)
public class EducationHistoryServiceImpl implements EducationHistoryService {

	@Resource
	EducationHistoryDao	educationHistoryDao;

	@Override
	public Collection<EducationHistory> findEducationHistoriesByStudentId(final Long studentId) {
		return this.educationHistoryDao.findEducationHistoriesByStudentId(studentId);
	}

	@Override
	public EducationHistory saveEducationHistory(final EducationHistory educationHistory) {
		return this.educationHistoryDao.persist(educationHistory);
	}

	@Override
	public void removeEducationHistory(final EducationHistory educationHistory) {
		this.educationHistoryDao.remove(educationHistory);
	}

}
