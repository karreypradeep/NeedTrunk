/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 * 
 */
package com.apeironsol.need.financial.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.model.Klass;
import com.apeironsol.need.core.service.BranchService;
import com.apeironsol.need.core.service.KlassService;
import com.apeironsol.need.financial.model.BranchBalanceSheet;
import com.apeironsol.need.util.dataobject.BranchFinancialDO;
import com.apeironsol.need.util.searchcriteria.FeeDueSearchCriteria;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Service implementation for branch financial details.
 * 
 * @author pradeep
 * 
 */
@Service("branchFinancialService")
@Transactional
public class BranchFinancialServiceImpl implements BranchFinancialService {

	@Resource
	KlassService						klassService;

	@Resource
	BranchService						branchService;

	@Resource
	ClassFinancialService				classFinancialService;

	@Resource
	StudentFinancialService				studentFinancialService;

	@Resource
	private BranchBalanceSheetService	branchBalanceSheetService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BranchFinancialDO getBranchFeeFinancialDetailsByAcademicYearId(final Long branchId, final Long academicYearId) {
		return this.getBranchFeeFinancialDetailsByAcademicYearIdForDueDate(branchId, academicYearId, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BranchFinancialDO getBranchFeeFinancialDetailsBySearchCriteria(final FeeDueSearchCriteria feeDueSearchCriteria) {
		final Collection<Long> klassIds = new ArrayList<Long>();
		if (feeDueSearchCriteria.getKlass() == null) {
			for (final Klass klass : this.klassService.findKlassesByBranchId(feeDueSearchCriteria.getBranch().getId())) {
				klassIds.add(klass.getId());
			}
		} else {
			klassIds.add(feeDueSearchCriteria.getKlass().getId());
		}

		Collection<Long> sectionIds = null;
		if (feeDueSearchCriteria.getSection() != null) {
			sectionIds = new ArrayList<Long>();
			sectionIds.add(feeDueSearchCriteria.getSection().getId());
		}

		return this.getBranchFeeFinancialDetailsByKlassIdsAndAcademicYearIdForDueDate(klassIds, sectionIds, feeDueSearchCriteria.getAcademicYear().getId(),
				feeDueSearchCriteria.getDueDate(), feeDueSearchCriteria.getBranch().getName());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BranchFinancialDO getBranchFeeFinancialDetailsByAcademicYearIdForDueDate(final Long branchId, final Long academicYearId, final Date dueDate) {
		final List<Klass> klasses = this.klassService.findKlassesByBranchId(branchId);
		final Collection<Long> klassIds = new ArrayList<Long>();
		for (final Klass klass : klasses) {
			klassIds.add(klass.getId());
		}
		return this.getBranchFeeFinancialDetailsByKlassIdsAndAcademicYearIdForDueDate(klassIds, null, academicYearId, dueDate, this.branchService
				.findBranchById(branchId).getName());
	}

	/**
	 * 
	 * @param klassIds
	 * @param academicYearId
	 * @param dueDate
	 * @return
	 */
	private BranchFinancialDO getBranchFeeFinancialDetailsByKlassIdsAndAcademicYearIdForDueDate(final Collection<Long> klassIds,
			final Collection<Long> sectionIds, final Long academicYearId, final Date dueDate, final String branchName) {
		final BranchFinancialDO branchFinancialDO = new BranchFinancialDO();
		branchFinancialDO.setName(branchName);
		branchFinancialDO.setClassFinancialDOs(this.classFinancialService.getClassFeeFinancialDetailsByClassIdsAndAcademicYearIdForDueDate(klassIds,
				sectionIds, academicYearId, dueDate, null));
		branchFinancialDO.calculateBranchFee();
		return branchFinancialDO;
	}

	@Override
	public void checkIfBranchFinancialTransactionsAreAllowed(final Long branchId, final Date transactionDate) throws BusinessException {
		final BranchBalanceSheet branchBalanceSheet = this.branchBalanceSheetService.findBranchBalanceSheetByBranchIdAndDate(branchId, transactionDate);

		if (branchBalanceSheet != null && branchBalanceSheet.getBalanceSheetClosedIndicator()) {
			throw new BusinessException("Balance sheet for date " + new SimpleDateFormat("MMM/dd/yyyy").format(transactionDate) + " is closed.");
		}
	}

}
