/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.financial.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.financial.model.BranchInvestment;
import com.apeironsol.need.util.searchcriteria.BranchInvestmentSearchCriteria;
import com.apeironsol.framework.BaseDaoImpl;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for Student Attendance entity implementation.
 * 
 * @author Pradeep
 * 
 */
@Repository("branchInvestmentDao")
public class BranchInvestmentDaoImpl extends BaseDaoImpl<BranchInvestment> implements BranchInvestmentDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BranchInvestment> findBranchInvestmentsByBranchId(final Long branchId) {
		final TypedQuery<BranchInvestment> query = getEntityManager().createQuery(
				"select be from BranchInvestment be where be.branch.id = :branchId", BranchInvestment.class);
		query.setParameter("branchId", branchId);
		return query.getResultList();
	}

	@Override
	public Collection<BranchInvestment> findBranchInvestmentsByBranchIdBuildingBLockIdAndAcademicYear(
			final Long branchId, final Long buildingBlockId, final AcademicYear academicYear) throws BusinessException {
		final TypedQuery<BranchInvestment> query = getEntityManager()
				.createQuery(
						"select be from BranchInvestment be where be.branch.id = :branchId and be.investmentBuildingBlock.id = :buildingBlockId and (be.investmentDate >= :startDate and be.investmentDate <= :endDate)",
						BranchInvestment.class);
		query.setParameter("branchId", branchId);
		query.setParameter("buildingBlockId", buildingBlockId);
		query.setParameter("startDate", academicYear.getStartDate());
		query.setParameter("endDate", academicYear.getEndDate());
		return query.getResultList();
	}

	@Override
	public Collection<BranchInvestment> findBranchInvestmentsByBranchIdAndAcademicYear(final Long branchId,
			final AcademicYear academicYear) throws BusinessException {
		final TypedQuery<BranchInvestment> query = getEntityManager()
				.createQuery(
						"select be from BranchInvestment be where be.branch.id = :branchId and (be.investmentDate >= :startDate and be.investmentDate <= :endDate)",
						BranchInvestment.class);
		query.setParameter("branchId", branchId);
		query.setParameter("startDate", academicYear.getStartDate());
		query.setParameter("endDate", academicYear.getEndDate());
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BranchInvestment> findBranchInvestmentBySearchCriteria(
			final BranchInvestmentSearchCriteria branchInvestmentSearchCriteria) throws BusinessException {
		final CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<BranchInvestment> query = builder.createQuery(BranchInvestment.class);
		final Root<BranchInvestment> branchInvestment = query.from(BranchInvestment.class);
		query.select(branchInvestment);

		final List<Predicate> predicateList = new ArrayList<Predicate>();
		Predicate assertNamePredicate, fromDatePredicate, toDatePredicate, buildingBlocksPrdicate;
		if (branchInvestmentSearchCriteria.getInvestmentName() != null
				&& !branchInvestmentSearchCriteria.getInvestmentName().isEmpty()) {
			assertNamePredicate = builder.like(builder.upper(branchInvestment.<String> get("name")), "%"
					+ branchInvestmentSearchCriteria.getInvestmentName().toUpperCase() + "%");
			predicateList.add(assertNamePredicate);
		}

		if (branchInvestmentSearchCriteria.getInvestmentTypeBuildingBlocks() != null
				&& !branchInvestmentSearchCriteria.getInvestmentTypeBuildingBlocks().isEmpty()) {
			buildingBlocksPrdicate = branchInvestment.get("investmentBuildingBlock").in(
					branchInvestmentSearchCriteria.getInvestmentTypeBuildingBlocks());
			predicateList.add(buildingBlocksPrdicate);
		}

		if (branchInvestmentSearchCriteria.getFromDate() != null) {
			fromDatePredicate = builder.greaterThanOrEqualTo(branchInvestment.<Date> get("investmentDate"),
					branchInvestmentSearchCriteria.getFromDate());
			predicateList.add(fromDatePredicate);
		}

		if (branchInvestmentSearchCriteria.getToDate() != null) {
			toDatePredicate = builder.lessThanOrEqualTo(branchInvestment.<Date> get("investmentDate"),
					branchInvestmentSearchCriteria.getToDate());
			predicateList.add(toDatePredicate);
		}

		if (branchInvestmentSearchCriteria.getBranch() != null) {
			fromDatePredicate = builder.equal(branchInvestment.<Branch> get("branch"),
					branchInvestmentSearchCriteria.getBranch());
			predicateList.add(fromDatePredicate);
		}

		final Predicate[] predicates = new Predicate[predicateList.size()];
		predicateList.toArray(predicates);
		query.where(predicates);

		return getEntityManager().createQuery(query).getResultList();
	}

}
