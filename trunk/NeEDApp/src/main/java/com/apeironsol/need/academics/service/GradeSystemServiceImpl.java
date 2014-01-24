/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.academics.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.need.academics.dao.GradeSystemDao;
import com.apeironsol.need.academics.dao.GradeSystemRangeDao;
import com.apeironsol.need.academics.model.GradeSystem;
import com.apeironsol.need.academics.model.GradeSystemRange;
import com.apeironsol.need.util.NonNull;
import com.apeironsol.need.util.comparator.GradeSystemRangeComparator;

/**
 * Service implementation interface for student.
 * 
 * @author pradeep
 * 
 */
@Service("gradeSystemService")
@Transactional(rollbackFor = Exception.class)
public class GradeSystemServiceImpl implements GradeSystemService {

	@Resource
	private GradeSystemDao		gradeSystemDao;

	@Resource
	private GradeSystemRangeDao	gradeSystemRangeDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GradeSystem saveGradeSystem(final GradeSystem gradeSystem) {
		this.validate(gradeSystem);
		GradeSystem result = this.gradeSystemDao.persist(gradeSystem);
		for (GradeSystemRange gradeSystemRange : gradeSystem.getGradeSystemRange()) {
			gradeSystemRange.setGradeSystem(result);
			this.gradeSystemRangeDao.persist(gradeSystemRange);
		}
		result = this.findGradeSystemById(result.getId());
		return result;
	}

	private void validate(final GradeSystem gradeSystem) {
		for (GradeSystemRange gradeSystemRange : gradeSystem.getGradeSystemRange()) {
			if (gradeSystemRange.getMaximumRange() == null) {
				throw new ApplicationException("Please enter Maximum percentage.");
			} else if (gradeSystemRange.getDistinction() == null) {
				throw new ApplicationException("Please enter Distinction.");
			}
		}

		if (gradeSystem.getGradeSystemRange() == null || gradeSystem.getGradeSystemRange().size() == 0) {
			throw new BusinessException("Please define percentage ranges for grading system.");
		} else {
			int minValue = 100, maxValue = 0;
			Collection<GradeSystemRange> gradeSystemRanges = gradeSystem.getGradeSystemRange();
			if (gradeSystemRanges != null) {
				Collections.sort((List<GradeSystemRange>) gradeSystemRanges, new GradeSystemRangeComparator(GradeSystemRangeComparator.Order.MIN_PERCENTAGE));
				GradeSystemRange previousGradeSystemRange = null;
				for (GradeSystemRange gradeSystemRange : gradeSystem.getGradeSystemRange()) {
					if (gradeSystemRange.getMinimumRange() >= gradeSystemRange.getMaximumRange()) {
						throw new BusinessException("Minimum percentage " + gradeSystemRange.getMinimumRange() + " should be less than maximum percentage "
								+ gradeSystemRange.getMaximumRange() + ".");
					}
					if (previousGradeSystemRange != null && gradeSystemRange.getMinimumRange() <= previousGradeSystemRange.getMaximumRange()) {
						throw new BusinessException("Ranges cannot overlap. Please check.");
					}
					if (gradeSystemRange.getMinimumRange() < minValue) {
						minValue = gradeSystemRange.getMinimumRange();
					}
					if (gradeSystemRange.getMaximumRange() > maxValue) {
						maxValue = gradeSystemRange.getMaximumRange();
					}
					previousGradeSystemRange = gradeSystemRange;
				}
				if (minValue != 0) {
					throw new BusinessException("Minimum percentage for grading system should be 0.");
				} else if (maxValue != 100) {
					throw new BusinessException("Maximum percentage for grading system should be 100.");
				}
			}
		}
		if (gradeSystem.isDefaultGrade()) {
			GradeSystem defaultGradeSystem = this.findDefaultGradeSystemForBranch(gradeSystem.getBranch().getId());
			if (defaultGradeSystem != null && !defaultGradeSystem.getId().equals(gradeSystem.getId())) {
				throw new BusinessException("A default grading systen " + defaultGradeSystem.getName() + " already exists for the branch.");
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeGradeSystem(final GradeSystem gradeSystem) {
		for (GradeSystemRange gradeSystemRange : gradeSystem.getGradeSystemRange()) {
			this.gradeSystemRangeDao.remove(gradeSystemRange);
		}
		this.gradeSystemDao.remove(gradeSystem);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GradeSystem findGradeSystemById(final Long id) {
		return this.gradeSystemDao.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<GradeSystem> findAllGradeSystems(final Long branchId) {
		return this.gradeSystemDao.findAllGradeSystems(branchId);
	}

	@Override
	public GradeSystem findDefaultGradeSystemForBranch(@NonNull final Long branchId) {
		return this.gradeSystemDao.findDefaultGradeSystemForBranch(branchId);
	}

}
