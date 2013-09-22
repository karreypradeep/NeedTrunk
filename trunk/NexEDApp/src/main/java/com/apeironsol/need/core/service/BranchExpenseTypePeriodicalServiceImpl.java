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

import org.joda.time.DateMidnight;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.dao.BranchExpenseTypePeriodicalDao;
import com.apeironsol.need.core.model.BranchAssembly;
import com.apeironsol.need.core.model.BranchExpenseTypePeriodical;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Service implementation interface for Branch reservation category type. This
 * service act as controller for Branch reservation category type details.
 * 
 * @author Pradeep
 * 
 */
@Service("branchExpenseTypePeriodicalService")
@Transactional
public class BranchExpenseTypePeriodicalServiceImpl implements BranchExpenseTypePeriodicalService {

	@Resource
	BranchExpenseTypePeriodicalDao	branchExpenseTypePeriodicalDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BranchExpenseTypePeriodical saveBranchExpenseTypePeriodical(
			final BranchExpenseTypePeriodical branchExpenseType) throws BusinessException {

		BranchExpenseTypePeriodical currentPeriodical = branchExpenseTypePeriodicalDao
				.findCurrentBranchExpenseTypePeriodicalByBranchAssembly(branchExpenseType.getBranchAssembly());

		if (currentPeriodical != null) {

			if (currentPeriodical.equals(branchExpenseType)) {
				return branchExpenseTypePeriodicalDao.persist(branchExpenseType);
			}

			// End the current periodical.
			Date newPeriodicalStartDate = branchExpenseType.getStartDate();
			if (currentPeriodical.getStartDate().compareTo(newPeriodicalStartDate) != -1) {
				throw new BusinessException(
						"This periodical start date is overlaping with the existing periodicals start date.");
			}

			DateMidnight currentPeriodicalEndDate = new DateMidnight(branchExpenseType.getStartDate().getTime());
			currentPeriodicalEndDate = currentPeriodicalEndDate.minusDays(1);
			currentPeriodical.setEndDate(new Date(currentPeriodicalEndDate.getMillis()));
			// currentPeriodical is persistence object and update on this object
			// are automatically flushed.
		}
		branchExpenseType.setEndDate(null);
		return branchExpenseTypePeriodicalDao.persist(branchExpenseType);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeBranchExpenseTypePeriodical(final BranchExpenseTypePeriodical branchExpenseType)
			throws BusinessException {

		if (branchExpenseType.getEndDate() != null) {
			throw new BusinessException(
					"This expense type periodical cannot be removed until the current periodical is removed.");
		} else {
			branchExpenseTypePeriodicalDao.remove(branchExpenseType);
		}

		DateMidnight previousPeriodicalEndDate = new DateMidnight(branchExpenseType.getStartDate().getTime());
		previousPeriodicalEndDate = previousPeriodicalEndDate.minusDays(1);
		BranchExpenseTypePeriodical previousPeriodical = branchExpenseTypePeriodicalDao
				.findBranchExpenseByBranchAssemblyAndEndDate(branchExpenseType.getBranchAssembly(), new Date(
						previousPeriodicalEndDate.getMillis()));

		if (previousPeriodical != null) {
			previousPeriodical.setEndDate(null);
			// previousPeriodical is a persistence object and updates on this
			// object are automatically flushed.
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BranchExpenseTypePeriodical> findBranchExpenseTypeByBranchAssembly(
			final BranchAssembly branchAssembly) throws BusinessException {
		return branchExpenseTypePeriodicalDao.findBranchExpenseTypePeriodicalsByBranchAssembly(branchAssembly);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BranchExpenseTypePeriodical findLatestBranchExpenseTypePeriodical(final BranchAssembly branchAssembly) {
		return branchExpenseTypePeriodicalDao.findLatestBranchExpenseTypePeriodicalByBranchAssembly(branchAssembly);
	}
}
