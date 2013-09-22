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
import com.apeironsol.need.financial.model.BranchExpense;
import com.apeironsol.need.util.searchcriteria.BranchExpenseSearchCriteria;
import com.apeironsol.framework.BaseDaoImpl;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for Student Attendance entity implementation.
 * 
 * @author Pradeep
 * 
 */
@Repository("branchExpenseDao")
public class BranchExpenseDaoImpl extends BaseDaoImpl<BranchExpense> implements BranchExpenseDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BranchExpense> findBranchExpensesByBranchId(final Long branchId) {
		TypedQuery<BranchExpense> query = this.getEntityManager().createQuery("select be from BranchExpense be where be.branch.id = :branchId",
				BranchExpense.class);
		query.setParameter("branchId", branchId);
		return query.getResultList();
	}

	@Override
	public Collection<BranchExpense> findBranchExpensesByBranchIdBuildingBLockIdAndAcademicYear(final Long branchId, final Long buildingBlockId,
			final AcademicYear academicYear) throws BusinessException {
		TypedQuery<BranchExpense> query = this
				.getEntityManager()
				.createQuery(
						"select be from BranchExpense be where be.branch.id = :branchId and be.expenseBuildingBlock.id = :buildingBlockId and (be.expenseDate >= :startDate and be.expenseDate <= :endDate)",
						BranchExpense.class);
		query.setParameter("branchId", branchId);
		query.setParameter("buildingBlockId", buildingBlockId);
		query.setParameter("startDate", academicYear.getStartDate());
		query.setParameter("endDate", academicYear.getEndDate());
		return query.getResultList();
	}

	@Override
	public Collection<BranchExpense> findBranchExpensesByBranchIdAndAcademicYear(final Long branchId, final AcademicYear academicYear) throws BusinessException {
		TypedQuery<BranchExpense> query = this.getEntityManager().createQuery(
				"select be from BranchExpense be where be.branch.id = :branchId and (be.expenseDate >= :startDate and be.expenseDate <= :endDate)",
				BranchExpense.class);
		query.setParameter("branchId", branchId);
		query.setParameter("startDate", academicYear.getStartDate());
		query.setParameter("endDate", academicYear.getEndDate());
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BranchExpense> findBranchExpensesBySearchCriteria(final BranchExpenseSearchCriteria branchExpenseSearchCriteria) throws BusinessException {
		CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<BranchExpense> query = builder.createQuery(BranchExpense.class);
		Root<BranchExpense> branchExpense = query.from(BranchExpense.class);
		query.select(branchExpense);

		List<Predicate> predicateList = new ArrayList<Predicate>();
		Predicate assertNamePredicate, fromDatePredicate, toDatePredicate, buildingBlocksPrdicate, brachPredicate;
		if (branchExpenseSearchCriteria.getExpenseName() != null && !branchExpenseSearchCriteria.getExpenseName().isEmpty()) {
			assertNamePredicate = builder.like(builder.upper(branchExpense.<String> get("name")), "%"
					+ branchExpenseSearchCriteria.getExpenseName().toUpperCase() + "%");
			predicateList.add(assertNamePredicate);
		}

		if (branchExpenseSearchCriteria.getExpenseTypeBuildingBlocks() != null && !branchExpenseSearchCriteria.getExpenseTypeBuildingBlocks().isEmpty()) {
			buildingBlocksPrdicate = branchExpense.get("expenseBuildingBlock").in(branchExpenseSearchCriteria.getExpenseTypeBuildingBlocks());
			predicateList.add(buildingBlocksPrdicate);
		}

		if (branchExpenseSearchCriteria.getFromDate() != null) {
			fromDatePredicate = builder.greaterThanOrEqualTo(branchExpense.<Date> get("expenseDate"), branchExpenseSearchCriteria.getFromDate());
			predicateList.add(fromDatePredicate);
		}

		if (branchExpenseSearchCriteria.getToDate() != null) {
			toDatePredicate = builder.lessThanOrEqualTo(branchExpense.<Date> get("expenseDate"), branchExpenseSearchCriteria.getToDate());
			predicateList.add(toDatePredicate);
		}

		if (branchExpenseSearchCriteria.getBranch() != null) {
			brachPredicate = builder.equal(branchExpense.<Branch> get("branch"), branchExpenseSearchCriteria.getBranch());
			predicateList.add(brachPredicate);
		}

		Predicate[] predicates = new Predicate[predicateList.size()];
		predicateList.toArray(predicates);
		query.where(predicates);

		return this.getEntityManager().createQuery(query).getResultList();
	}

	@Override
	public Collection<BranchExpense> findBranchExpensesByBranchIdBetweenDates(final Long branchId, final Date fromDate, final Date toDate)
			throws BusinessException {
		TypedQuery<BranchExpense> query = this.getEntityManager().createQuery(
				"select be from BranchExpense be where be.branch.id = :branchId  and  be.expenseDate >= :startDate and be.expenseDate <= :endDate ",
				BranchExpense.class);
		query.setParameter("branchId", branchId);
		query.setParameter("startDate", fromDate);
		query.setParameter("endDate", toDate);
		return query.getResultList();
	}

}
