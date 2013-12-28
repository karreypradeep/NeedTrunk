/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

/**
 * Service interface for branch rule.
 * 
 * @author Pradeep
 * 
 */
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.dao.BranchRuleDao;
import com.apeironsol.need.core.model.BranchRule;
import com.apeironsol.framework.exception.BusinessException;

@Service("branchRuleService")
@Transactional(rollbackFor = Exception.class)
public class BranchRuleServiceImpl implements BranchRuleService {

	@Resource
	private BranchRuleDao	branchRuleDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BranchRule saveBranchRule(final BranchRule branchRule) throws BusinessException {
		return this.branchRuleDao.persist(branchRule);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<BranchRule> findAllBranchRules() throws BusinessException {
		return this.branchRuleDao.findAll();

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BranchRule findBranchRuleById(final Long id) throws BusinessException {
		return this.branchRuleDao.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeBranchRule(final Long id) throws BusinessException {
		this.branchRuleDao.remove(this.branchRuleDao.findById(id));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BranchRule findBranchRuleByBranchId(final Long branchId) {
		return this.branchRuleDao.findBranchRuleByBranchId(branchId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeBranchRuleByBranchId(final Long branchId) throws BusinessException {
		this.branchRuleDao.removeBranchRuleByBranchId(branchId);
	}

}
