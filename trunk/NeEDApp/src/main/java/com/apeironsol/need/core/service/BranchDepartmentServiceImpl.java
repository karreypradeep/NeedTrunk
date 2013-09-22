/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import java.util.Collection;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.dao.BranchDepartmentDao;
import com.apeironsol.need.core.model.BranchAssembly;
import com.apeironsol.need.core.model.BranchDepartment;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Service implementation interface for Branch department type. This service act
 * as controller for Branch department type details.
 * 
 * @author Pradeep
 * 
 */
@Service("branchDepartmentService")
@Transactional
public class BranchDepartmentServiceImpl implements BranchDepartmentService {

	@Resource
	BranchDepartmentDao	branchDepartmentDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BranchDepartment saveBranchDepartment(final BranchDepartment branchDepartment) throws BusinessException {
		BranchDepartment previousBranchDepartmentPeriodical = this.findBranchDepartmentByBranchAssemblyAndDate(
				branchDepartment.getBranchAssembly(), branchDepartment.getStartDate());
		if (previousBranchDepartmentPeriodical != null) {
			branchDepartment.setEndDate(previousBranchDepartmentPeriodical.getEndDate());
			previousBranchDepartmentPeriodical.setEndDate(branchDepartment.getStartDate());
			this.branchDepartmentDao.persist(previousBranchDepartmentPeriodical);
		}
		return this.branchDepartmentDao.persist(branchDepartment);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BranchDepartment findBranchDepartmentById(final Long id) throws BusinessException {
		return this.branchDepartmentDao.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeBranchDepartment(final BranchDepartment branchDepartment) throws BusinessException {
		BranchDepartment branchFeePeriodical = null;

		if (branchDepartment.getEndDate() != null) {
			branchFeePeriodical = this.findBranchDepartmentByBranchAssemblyAndStartDate(
					branchDepartment.getBranchAssembly(), branchDepartment.getEndDate());
			if (branchFeePeriodical != null) {
				branchFeePeriodical.setStartDate(branchDepartment.getStartDate());
			}
		} else {
			branchFeePeriodical = this.findBranchDepartmentByBranchAssemblyAndEndDate(
					branchDepartment.getBranchAssembly(), branchDepartment.getStartDate());
			if (branchFeePeriodical != null) {
				branchFeePeriodical.setEndDate(branchDepartment.getEndDate());
			}
		}

		if (branchFeePeriodical != null) {
			this.branchDepartmentDao.persist(branchFeePeriodical);
		}
		this.branchDepartmentDao.remove(this.branchDepartmentDao.findById(branchDepartment.getId()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BranchDepartment> findBranchDepartmentsByBranchAssembly(final BranchAssembly branchAssembly)
			throws BusinessException {
		return this.branchDepartmentDao.findBranchDepartmentsByBranchAssembly(branchAssembly);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BranchDepartment findPreviousBranchDepartmentPeriodical(final BranchDepartment branchDepartment)
			throws BusinessException {
		return this.branchDepartmentDao.findPreviousBranchDepartmentPeriodical(branchDepartment);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BranchDepartment findBranchDepartmentByBranchAssemblyAndDate(final BranchAssembly branchAssembly,
			final Date referenceDate) throws BusinessException {
		return this.branchDepartmentDao.findBranchDepartmentByBranchAssemblyAndDate(branchAssembly, referenceDate);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BranchDepartment findLatestBranchDepartment(final BranchAssembly branchAssembly) throws BusinessException {
		return this.branchDepartmentDao.findLatestBranchDepartment(branchAssembly);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeBranchDepartmentByBranchAssembly(final BranchAssembly branchAssembly) throws BusinessException {
		this.branchDepartmentDao.removeBranchDepartmentByBranchAssembly(branchAssembly);
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public BranchDepartment endBranchDepartment(final BranchDepartment branchExpenseType) throws BusinessException {
		return this.branchDepartmentDao.persist(branchExpenseType);
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public BranchDepartment findBranchDepartmentByBranchAssemblyAndStartDate(final BranchAssembly branchAssembly,
			final Date referenceDate) throws BusinessException {
		return this.branchDepartmentDao.findBranchDepartmentByBranchAssemblyAndStartDate(branchAssembly, referenceDate);
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public BranchDepartment findBranchDepartmentByBranchAssemblyAndEndDate(final BranchAssembly branchAssembly,
			final Date referenceDate) throws BusinessException {
		return this.branchDepartmentDao.findBranchDepartmentByBranchAssemblyAndEndDate(branchAssembly, referenceDate);
	}
}
