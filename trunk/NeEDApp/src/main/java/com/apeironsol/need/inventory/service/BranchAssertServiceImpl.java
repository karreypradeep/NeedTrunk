/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.inventory.service;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.inventory.dao.BranchAssertDao;
import com.apeironsol.need.inventory.model.BranchAssert;
import com.apeironsol.need.util.searchcriteria.BranchAssertSearchCriteria;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;

/**
 * Data access interface for Student Attendance entity implementation.
 * 
 * @author Pradeep
 * 
 */
@Service("branchAssertService")
@Transactional
public class BranchAssertServiceImpl implements BranchAssertService {

	@Resource
	private BranchAssertDao	branchAssertDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BranchAssert> findBranchAssertsByBranchId(final Long branchId) throws BusinessException {
		return this.branchAssertDao.findBranchAssertsByBranchId(branchId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BranchAssert findBranchAssertById(final Long id) throws BusinessException {
		return this.branchAssertDao.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BranchAssert saveBranchAssert(final BranchAssert branchAssert) throws BusinessException,
			InvalidArgumentException {
		return this.branchAssertDao.persist(branchAssert);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeBranchAssert(final BranchAssert branchAssert) throws BusinessException {
		this.branchAssertDao.remove(branchAssert);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BranchAssert> findAllBranchAsserts() throws BusinessException {
		return this.branchAssertDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BranchAssert> findBranchAssertsByBranchIdBuildingBLockIdAndAcademicYear(final Long branchId,
			final Long buildingBlockId, final AcademicYear academicYear) throws BusinessException {
		return this.branchAssertDao.findBranchAssertsByBranchIdBuildingBLockIdAndAcademicYear(branchId,
				buildingBlockId, academicYear);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BranchAssert> findBranchAssertsByBranchIdAndAcademicYear(final Long branchId,
			final AcademicYear academicYear) throws BusinessException {
		return this.branchAssertDao.findBranchAssertsByBranchIdAndAcademicYear(branchId, academicYear);
	}

	@Override
	public Collection<BranchAssert> findBranchAssertsBySearchCriteria(
			final BranchAssertSearchCriteria branchAssertSearchCriteria) throws BusinessException {
		return this.branchAssertDao.findBranchAssertsBySearchCriteria(branchAssertSearchCriteria);
	}

}
