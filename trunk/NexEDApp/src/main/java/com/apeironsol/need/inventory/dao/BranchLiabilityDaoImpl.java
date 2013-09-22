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
import com.apeironsol.need.inventory.model.BranchLiability;
import com.apeironsol.need.util.searchcriteria.BranchLiabilitySearchCriteria;
import com.apeironsol.framework.BaseDaoImpl;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for Student Attendance entity implementation.
 * 
 * @author Pradeep
 * 
 */
@Repository("branchLiabilityDao")
public class BranchLiabilityDaoImpl extends BaseDaoImpl<BranchLiability> implements BranchLiabilityDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BranchLiability> findBranchLiabilitiesByBranchId(final Long branchId) {
		TypedQuery<BranchLiability> query = getEntityManager().createQuery(
				"select bl from BranchLiability bl where bl.branch.id = :branchId", BranchLiability.class);
		query.setParameter("branchId", branchId);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BranchLiability> findBranchLiabilitiesByBranchIdBuildingBLockIdAndAcademicYear(
			final Long branchId, final Long buildingBlockId, final AcademicYear academicYear) throws BusinessException {
		TypedQuery<BranchLiability> query = getEntityManager()
				.createQuery(
						"select bl from BranchLiability bl where bl.branch.id = :branchId and bl.liabilityTypeBuildingBlock.id = :buildingBlockId and (bl.date >= :startDate and bl.date <= :endDate)",
						BranchLiability.class);
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
	public Collection<BranchLiability> findBranchLiabilitiesByBranchIdAndAcademicYear(final Long branchId,
			final AcademicYear academicYear) throws BusinessException {
		TypedQuery<BranchLiability> query = getEntityManager()
				.createQuery(
						"select bl from BranchLiability bl where bl.branch.id = :branchId and (bl.date >= :startDate and bl.date <= :endDate)",
						BranchLiability.class);
		query.setParameter("branchId", branchId);
		query.setParameter("startDate", academicYear.getStartDate());
		query.setParameter("endDate", academicYear.getEndDate());
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BranchLiability> findBranchLiabilitiesBySearchCriteria(
			final BranchLiabilitySearchCriteria branchLiabilitySearchCriteria) throws BusinessException {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<BranchLiability> query = builder.createQuery(BranchLiability.class);
		Root<BranchLiability> branchLiability = query.from(BranchLiability.class);
		query.select(branchLiability);

		List<Predicate> predicateList = new ArrayList<Predicate>();
		Predicate assertNamePredicate, fromDatePredicate, toDatePredicate, buildingBlocksPrdicate;
		if (branchLiabilitySearchCriteria.getLiabilityName() != null
				&& !branchLiabilitySearchCriteria.getLiabilityName().isEmpty()) {
			assertNamePredicate = builder.like(builder.upper(branchLiability.<String> get("name")), "%"
					+ branchLiabilitySearchCriteria.getLiabilityName().toUpperCase() + "%");
			predicateList.add(assertNamePredicate);
		}

		if (branchLiabilitySearchCriteria.getLiabilityTypeBuildingBlocks() != null
				&& !branchLiabilitySearchCriteria.getLiabilityTypeBuildingBlocks().isEmpty()) {
			buildingBlocksPrdicate = branchLiability.get("liabilityTypeBuildingBlock").in(
					branchLiabilitySearchCriteria.getLiabilityTypeBuildingBlocks());
			predicateList.add(buildingBlocksPrdicate);
		}

		if (branchLiabilitySearchCriteria.getFromDate() != null) {
			fromDatePredicate = builder.greaterThanOrEqualTo(branchLiability.<Date> get("date"),
					branchLiabilitySearchCriteria.getFromDate());
			predicateList.add(fromDatePredicate);
		}

		if (branchLiabilitySearchCriteria.getToDate() != null) {
			toDatePredicate = builder.lessThanOrEqualTo(branchLiability.<Date> get("date"),
					branchLiabilitySearchCriteria.getToDate());
			predicateList.add(toDatePredicate);
		}

		if (branchLiabilitySearchCriteria.getBranch() != null) {
			fromDatePredicate = builder.equal(branchLiability.<Branch> get("branch"),
					branchLiabilitySearchCriteria.getBranch());
			predicateList.add(fromDatePredicate);
		}

		Predicate[] predicates = new Predicate[predicateList.size()];
		predicateList.toArray(predicates);
		query.where(predicates);

		return getEntityManager().createQuery(query).getResultList();
	}

}
