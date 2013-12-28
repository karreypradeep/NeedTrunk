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

import com.apeironsol.need.financial.dao.FinancialYearDao;
import com.apeironsol.need.financial.model.FinancialYear;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;

/**
 * Service implementation interface for calendar year.
 * This service act as controller for calendar year details.
 * 
 * @author Pradeep
 * 
 */
@Service("financialYearService")
@Transactional(rollbackFor = Exception.class)
public class FinancialYearServiceImpl implements FinancialYearService {

	@Resource
	FinancialYearDao	financialYearDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FinancialYear saveFinancialYear(final FinancialYear academicYear) throws BusinessException,
			InvalidArgumentException {
		academicYear.validate();
		return financialYearDao.persist(academicYear);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FinancialYear findFinancialYearById(final Long id) throws BusinessException {
		return financialYearDao.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeFinancialYear(final FinancialYear academicYear) throws BusinessException {

		financialYearDao.remove(academicYear);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<FinancialYear> findAllFinancialYears() throws BusinessException {
		return financialYearDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<FinancialYear> findFinancialYearsByBranchId(final Long branchId) throws BusinessException {
		return financialYearDao.findFinancialYearsByBranchId(branchId);
	}

}
