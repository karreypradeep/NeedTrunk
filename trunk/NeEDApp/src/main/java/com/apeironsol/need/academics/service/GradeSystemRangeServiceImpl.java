/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.academics.service;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.academics.dao.GradeSystemRangeDao;
import com.apeironsol.need.academics.model.GradeSystemRange;
import com.apeironsol.need.util.NonNull;

/**
 * Service implementation interface for student.
 * 
 * @author pradeep
 * 
 */
@Service("gradeSystemRangeService")
@Transactional(rollbackFor = Exception.class)
public class GradeSystemRangeServiceImpl implements GradeSystemRangeService {

	@Resource
	private GradeSystemRangeDao	gradeSystemRangeDao;

	@Override
	public Collection<GradeSystemRange> findAllGradingSystemRangesByGradeSystem(@NonNull final Long gradeSystem) {
		return this.gradeSystemRangeDao.findAllGradingSystemRangesByGradeSystem(gradeSystem);
	}

}
