/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.financial.service;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.service.SequenceGeneratorService;
import com.apeironsol.need.financial.dao.BranchInvestmentDao;
import com.apeironsol.need.financial.model.BranchInvestment;
import com.apeironsol.need.util.searchcriteria.BranchInvestmentSearchCriteria;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;

/**
 * Data access interface for Student Attendance entity implementation.
 * 
 * @author Pradeep
 * 
 */
@Service("branchInvestmentService")
@Transactional(rollbackFor = Exception.class)
public class BranchInvestmentServiceImpl implements BranchInvestmentService {

	@Resource
	private BranchInvestmentDao		branchInvestmentDao;

	@Resource
	private BranchFinancialService	branchFinancialService;

	@Resource
	SequenceGeneratorService		sequenceGeneratorService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BranchInvestment> findBranchInvestmentsByBranchId(final Long branchId) throws BusinessException {
		return branchInvestmentDao.findBranchInvestmentsByBranchId(branchId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BranchInvestment findBranchInvestmentById(final Long id) throws BusinessException {
		return branchInvestmentDao.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BranchInvestment saveBranchInvestment(final BranchInvestment branchInvestment) throws BusinessException, InvalidArgumentException {
		branchFinancialService.checkIfBranchFinancialTransactionsAreAllowed(branchInvestment.getBranch().getId(), branchInvestment.getInvestmentDate());
		if (branchInvestment.getId() == null) {
			branchInvestment.setTransactionNr(sequenceGeneratorService.getNextTransactionNumberForInvestment());
		}
		return branchInvestmentDao.persist(branchInvestment);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeBranchInvestment(final BranchInvestment branchInvestment) throws BusinessException {
		branchInvestmentDao.remove(branchInvestment);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BranchInvestment> findAllBranchInvestments() throws BusinessException {
		return branchInvestmentDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BranchInvestment> findBranchInvestmentsByBranchIdBuildingBLockIdAndAcademicYear(final Long branchId, final Long buildingBlockId,
			final AcademicYear academicYear) throws BusinessException {
		return branchInvestmentDao.findBranchInvestmentsByBranchIdBuildingBLockIdAndAcademicYear(branchId, buildingBlockId, academicYear);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BranchInvestment> findBranchInvestmentsByBranchIdAndAcademicYear(final Long branchId, final AcademicYear academicYear)
			throws BusinessException {
		return branchInvestmentDao.findBranchInvestmentsByBranchIdAndAcademicYear(branchId, academicYear);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BranchInvestment> findBranchInvestmentBySearchCriteria(final BranchInvestmentSearchCriteria branchInvestmentSearchCriteria)
			throws BusinessException {
		return branchInvestmentDao.findBranchInvestmentBySearchCriteria(branchInvestmentSearchCriteria);
	}

}
