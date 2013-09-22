/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import java.sql.Date;
import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.dao.BranchReservationCategoryDao;
import com.apeironsol.need.core.model.BranchAssembly;
import com.apeironsol.need.core.model.BranchReservationCategory;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Service implementation interface for Branch reservation category type. This
 * service act as controller for Branch reservation category type details.
 * 
 * @author Pradeep
 * 
 */
@Service("branchReservationCategoryService")
@Transactional
public class BranchReservationCategoryServiceImpl implements BranchReservationCategoryService {

	@Resource
	BranchReservationCategoryDao	branchReservationCategoryDao;

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public BranchReservationCategory saveBranchReservationCategory(
			final BranchReservationCategory branchReservationCategory) throws BusinessException {
		return this.branchReservationCategoryDao.persist(branchReservationCategory);
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public BranchReservationCategory findBranchReservationCategoryById(final Long id) throws BusinessException {
		return this.branchReservationCategoryDao.findById(id);
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void removeBranchReservationCategory(final BranchReservationCategory branchReservationCategory)
			throws BusinessException {
		this.branchReservationCategoryDao.remove(branchReservationCategory);
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public Collection<BranchReservationCategory> findBranchReservationCategoriesByBranchAssembly(
			final BranchAssembly branchAssembly) throws BusinessException {
		return this.branchReservationCategoryDao.findBranchReservationCategoriesByBranchAssembly(branchAssembly);
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public BranchReservationCategory findPreviousBranchReservationCategoryPeriodical(
			final BranchReservationCategory branchReservationCategory) throws BusinessException {
		return this.branchReservationCategoryDao
				.findPreviousBranchReservationCategoryPeriodical(branchReservationCategory);
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public BranchReservationCategory findBranchReservationCategoryByBranchAssemblyAndDate(
			final BranchAssembly branchAssembly, final Date referenceDate) throws BusinessException {
		return this.branchReservationCategoryDao.findBranchReservationCategoryByBranchAssemblyAndDate(branchAssembly,
				referenceDate);
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public BranchReservationCategory findLatestBranchReservationCategory(final BranchAssembly branchAssembly)
			throws BusinessException {
		return this.branchReservationCategoryDao.findLatestBranchReservationCategory(branchAssembly);
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void removeBranchReservationCategoryByBranchAssembly(final BranchAssembly branchAssembly)
			throws BusinessException {
		this.branchReservationCategoryDao.removeBranchReservationCategoryByBranchAssembly(branchAssembly);
	}
}
