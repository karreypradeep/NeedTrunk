/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.inventory.dao;

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
import com.apeironsol.need.inventory.model.BranchAssert;
import com.apeironsol.need.util.searchcriteria.BranchAssertSearchCriteria;
import com.apeironsol.framework.BaseDaoImpl;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for Student Attendance entity implementation.
 * 
 * @author Pradeep
 * 
 */
@Repository("branchAssertDao")
public class BranchAssertDaoImpl extends BaseDaoImpl<BranchAssert> implements BranchAssertDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BranchAssert> findBranchAssertsByBranchId(final Long branchId) {
		TypedQuery<BranchAssert> query = getEntityManager().createQuery(
				"select ba from BranchAssert ba where ba.branch.id = :branchId", BranchAssert.class);
		query.setParameter("branchId", branchId);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BranchAssert> findBranchAssertsByBranchIdBuildingBLockIdAndAcademicYear(final Long branchId,
			final Long buildingBlockId, final AcademicYear academicYear) throws BusinessException {
		TypedQuery<BranchAssert> query = getEntityManager()
				.createQuery(
						"select ba from BranchAssert ba where ba.branch.id = :branchId and ba.assertTypeBuildingBlock.id = :buildingBlockId and (ba.date >= :startDate and ba.date <= :endDate)",
						BranchAssert.class);
		query.setParameter("branchId", branchId);
		query.setParameter("buildingBlockId", buildingBlockId);
		query.setParameter("startDate", academicYear.getStartDate());
		query.setParameter("endDate", academicYear.getEndDate());
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BranchAssert> findBranchAssertsByBranchIdAndAcademicYear(final Long branchId,
			final AcademicYear academicYear) throws BusinessException {
		TypedQuery<BranchAssert> query = getEntityManager()
				.createQuery(
						"select ba from BranchAssert ba where ba.branch.id = :branchId and (ba.date >= :startDate and ba.date <= :endDate)",
						BranchAssert.class);
		query.setParameter("branchId", branchId);
		query.setParameter("startDate", academicYear.getStartDate());
		query.setParameter("endDate", academicYear.getEndDate());
		return query.getResultList();
	}

	@Override
	public Collection<BranchAssert> findBranchAssertsBySearchCriteria(
			final BranchAssertSearchCriteria branchAssertSearchCriteria) throws BusinessException {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<BranchAssert> query = builder.createQuery(BranchAssert.class);
		Root<BranchAssert> branchAssert = query.from(BranchAssert.class);
		query.select(branchAssert);

		List<Predicate> predicateList = new ArrayList<Predicate>();
		Predicate assertNamePredicate, fromDatePredicate, toDatePredicate, buildingBlocksPrdicate;
		if (branchAssertSearchCriteria.getAssertName() != null && !branchAssertSearchCriteria.getAssertName().isEmpty()) {
			assertNamePredicate = builder.like(builder.upper(branchAssert.<String> get("name")), "%"
					+ branchAssertSearchCriteria.getAssertName().toUpperCase() + "%");
			predicateList.add(assertNamePredicate);
		}

		if (branchAssertSearchCriteria.getAssertTypeBuildingBlocks() != null
				&& !branchAssertSearchCriteria.getAssertTypeBuildingBlocks().isEmpty()) {
			buildingBlocksPrdicate = branchAssert.get("assertTypeBuildingBlock").in(
					branchAssertSearchCriteria.getAssertTypeBuildingBlocks());
			predicateList.add(buildingBlocksPrdicate);
		}

		if (branchAssertSearchCriteria.getFromDate() != null) {
			fromDatePredicate = builder.greaterThanOrEqualTo(branchAssert.<Date> get("date"),
					branchAssertSearchCriteria.getFromDate());
			predicateList.add(fromDatePredicate);
		}

		if (branchAssertSearchCriteria.getToDate() != null) {
			toDatePredicate = builder.lessThanOrEqualTo(branchAssert.<Date> get("date"),
					branchAssertSearchCriteria.getToDate());
			predicateList.add(toDatePredicate);
		}

		if (branchAssertSearchCriteria.getBranch() != null) {
			fromDatePredicate = builder.equal(branchAssert.<Branch> get("branch"),
					branchAssertSearchCriteria.getBranch());
			predicateList.add(fromDatePredicate);
		}

		Predicate[] predicates = new Predicate[predicateList.size()];
		predicateList.toArray(predicates);
		query.where(predicates);

		return getEntityManager().createQuery(query).getResultList();
	}
}
