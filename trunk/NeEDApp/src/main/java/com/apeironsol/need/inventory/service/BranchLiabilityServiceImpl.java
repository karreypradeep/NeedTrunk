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
import com.apeironsol.need.inventory.dao.BranchLiabilityDao;
import com.apeironsol.need.inventory.model.BranchLiability;
import com.apeironsol.need.util.searchcriteria.BranchLiabilitySearchCriteria;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;

/**
 * Data access interface for Student Attendance entity implementation.
 * 
 * @author Pradeep
 * 
 */
@Service("branchLiabilityService")
@Transactional
public class BranchLiabilityServiceImpl implements BranchLiabilityService {

	@Resource
	private BranchLiabilityDao	branchLiabilityDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BranchLiability> findBranchLiabilitiesByBranchId(final Long branchId) throws BusinessException {
		return this.branchLiabilityDao.findBranchLiabilitiesByBranchId(branchId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BranchLiability findBranchLiabilityById(final Long id) throws BusinessException {
		return this.branchLiabilityDao.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BranchLiability saveBranchLiability(final BranchLiability branchLiability) throws BusinessException,
			InvalidArgumentException {
		return this.branchLiabilityDao.persist(branchLiability);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeBranchLiability(final BranchLiability branchLiability) throws BusinessException {
		this.branchLiabilityDao.remove(branchLiability);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BranchLiability> findAllBranchLiabilities() throws BusinessException {
		return this.branchLiabilityDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BranchLiability> findBranchLiabilitiesByBranchIdBuildingBLockIdAndAcademicYear(
			final Long branchId, final Long buildingBlockId, final AcademicYear academicYear) throws BusinessException {
		return this.branchLiabilityDao.findBranchLiabilitiesByBranchIdBuildingBLockIdAndAcademicYear(branchId,
				buildingBlockId, academicYear);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BranchLiability> findBranchLiabilitiesByBranchIdAndAcademicYear(final Long branchId,
			final AcademicYear academicYear) throws BusinessException {
		return this.branchLiabilityDao.findBranchLiabilitiesByBranchIdAndAcademicYear(branchId, academicYear);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BranchLiability> findBranchLiabilitiesBySearchCriteria(
			final BranchLiabilitySearchCriteria branchLiabilitySearchCriteria) throws BusinessException {
		return this.branchLiabilityDao.findBranchLiabilitiesBySearchCriteria(branchLiabilitySearchCriteria);
	}

}
