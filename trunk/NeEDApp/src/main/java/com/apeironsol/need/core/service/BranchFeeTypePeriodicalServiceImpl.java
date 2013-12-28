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

import org.apache.log4j.Logger;
import org.joda.time.DateMidnight;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.dao.BranchFeeTypePeriodicalDao;
import com.apeironsol.need.core.model.BranchAssembly;
import com.apeironsol.need.core.model.BranchFeeTypePeriodical;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;

/**
 * Service implementation interface for Branch fee type periodical.
 * 
 * @author Pradeep
 * 
 */
@Service("branchFeeTypePeriodicalService")
@Transactional(rollbackFor = Exception.class)
public class BranchFeeTypePeriodicalServiceImpl implements BranchFeeTypePeriodicalService {

	private static final Logger	log	= Logger.getLogger(BranchFeeTypePeriodicalServiceImpl.class);

	@Resource
	BranchFeeTypePeriodicalDao	branchFeeTypePeriodicalDao;

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public BranchFeeTypePeriodical saveBranchFeeTypePeriodical(final BranchFeeTypePeriodical branchFeeTypePeriodical)
			throws BusinessException, InvalidArgumentException {

		branchFeeTypePeriodical.validate();

		BranchFeeTypePeriodical highestPeriodical = branchFeeTypePeriodicalDao
				.findLatestBranchFeeTypePeriodicalByBranchAssembly(branchFeeTypePeriodical.getBranchAssembly());

		if (highestPeriodical != null) {

			if (highestPeriodical.equals(branchFeeTypePeriodical)) {
				return branchFeeTypePeriodicalDao.persist(branchFeeTypePeriodical);
			}

			Date newPeriodicalStartDate = branchFeeTypePeriodical.getStartDate();
			if (highestPeriodical.getStartDate().compareTo(newPeriodicalStartDate) != -1) {
				throw new BusinessException(
						"This periodical start date is overlaping with the existing periodicals start date.");
			}

			// End highest periodical
			if (highestPeriodical.getEndDate() == null) {
				DateMidnight currentPeriodicalEndDate = new DateMidnight(branchFeeTypePeriodical.getStartDate()
						.getTime());
				currentPeriodicalEndDate = currentPeriodicalEndDate.minusDays(1);
				highestPeriodical.setEndDate(new Date(currentPeriodicalEndDate.getMillis()));
				// highestPeriodical is persistence object and update on this
				// object
				// are automatically flushed.
			}
		}
		branchFeeTypePeriodical.setEndDate(null);
		return branchFeeTypePeriodicalDao.persist(branchFeeTypePeriodical);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeBranchFeePeriodical(final BranchFeeTypePeriodical branchFeeTypePeriodical)
			throws BusinessException {

		if (branchFeeTypePeriodical.getEndDate() != null
				&& branchFeeTypePeriodical.getEndDate().compareTo(new Date()) == -1) {
			log.info("This periodical cannot be removed , because of the end date of the periodical is in past date");
			throw new BusinessException(
					"This periodical cannot be removed , because of the end date of the periodical is in past date");
		}

		BranchAssembly branchAssembly = branchFeeTypePeriodical.getBranchAssembly();

		BranchFeeTypePeriodical highestPeriodical = branchFeeTypePeriodicalDao
				.findLatestBranchFeeTypePeriodicalByBranchAssembly(branchAssembly);

		if (!branchFeeTypePeriodical.equals(highestPeriodical)) {
			log.info("This periodical cannot be removed untill all other higher periodicals are removed.");
			throw new BusinessException(
					"This periodical cannot be removed untill all other higher periodicals are removed.");
		} else {

			DateMidnight previousPeriodicalEndDate = new DateMidnight(branchFeeTypePeriodical.getStartDate().getTime());
			previousPeriodicalEndDate = previousPeriodicalEndDate.minusDays(1);
			BranchFeeTypePeriodical previousPeriodical = branchFeeTypePeriodicalDao
					.findBranchFeeByBranchAssemblyAndEndDate(branchFeeTypePeriodical.getBranchAssembly(), new Date(
							previousPeriodicalEndDate.getMillis()));

			if (previousPeriodical != null) {
				previousPeriodical.setEndDate(null);
				// previousPeriodical is a persistence object and updates on
				// this
				// object are automatically flushed.
			}

			branchFeeTypePeriodicalDao.remove(branchFeeTypePeriodical);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BranchFeeTypePeriodical> findBranchFeeTypeByBranchAssembly(final BranchAssembly branchAssembly)
			throws BusinessException {
		return branchFeeTypePeriodicalDao.findBranchFeeTypePeriodicalsByBranchAssembly(branchAssembly);
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public BranchFeeTypePeriodical findLatestBranchFeePeriodical(final BranchAssembly branchAssembly) {
		return branchFeeTypePeriodicalDao.findLatestBranchFeeTypePeriodicalByBranchAssembly(branchAssembly);
	}
}
