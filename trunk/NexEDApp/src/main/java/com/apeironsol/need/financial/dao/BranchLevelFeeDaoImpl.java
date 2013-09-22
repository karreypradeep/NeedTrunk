/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.financial.dao;

import java.util.Collection;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.financial.model.BranchLevelFee;
import com.apeironsol.need.util.constants.FeeTypeConstant;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * Data access interface for class fee
 * 
 * @author Pradeep
 * 
 */
@Repository
public class BranchLevelFeeDaoImpl extends BaseDaoImpl<BranchLevelFee> implements BranchLevelFeeDao {

	@Override
	public Collection<BranchLevelFee> findAllBranchLevelFeesByBranchId(final Long branchId) {

		final TypedQuery<BranchLevelFee> query = this.getEntityManager().createQuery("select e from BranchLevelFee e where e.branch.id = :branchId ",
				BranchLevelFee.class);
		query.setParameter("branchId", branchId);
		return query.getResultList();
	}

	@Override
	public BranchLevelFee findBranchLevelFeeByBranchIdAndBunildingBlockIdAndAcademicYearId(final Long branchId, final Long buildingBlockId,
			final Long academicYearId) {

		try {

			final TypedQuery<BranchLevelFee> query = this
					.getEntityManager()
					.createQuery(
							"select e from BranchLevelFee e where e.branch.id = :branchId and e.buildingBlock.id = :buildingBlockId and e.academicYear.id = :academicYearId ",
							BranchLevelFee.class);
			query.setParameter("branchId", branchId);
			query.setParameter("buildingBlockId", buildingBlockId);
			query.setParameter("academicYearId", academicYearId);
			return query.getSingleResult();

		} catch (final NoResultException e) {
			return null;
		}

	}

	@Override
	public Collection<BranchLevelFee> findBranchLevelFeeByBranchIdAndAcademicYearId(final Long branchId, final Long academicYearId) {

		final TypedQuery<BranchLevelFee> query = this.getEntityManager().createQuery(
				"select e from BranchLevelFee e where e.branch.id = :branchId and e.academicYear.id = :academicYearId ", BranchLevelFee.class);
		query.setParameter("branchId", branchId);
		query.setParameter("academicYearId", academicYearId);
		return query.getResultList();
	}

	@Override
	public Collection<BranchLevelFee> findBranchLevelFeeByBranchIdAndAcademicYearIdAndBuildingBlockId(final Long branchId, final Long academicYearId,
			final Long buildingBlockId) {

		final TypedQuery<BranchLevelFee> query = this
				.getEntityManager()
				.createQuery(
						"select e from BranchLevelFee e where e.branch.id = :branchId and e.academicYear.id = :academicYearId and e.buildingBlock.id = :buildingBlockId",
						BranchLevelFee.class);
		query.setParameter("branchId", branchId);
		query.setParameter("academicYearId", academicYearId);
		query.setParameter("buildingBlockId", buildingBlockId);
		return query.getResultList();
	}

	@Override
	public Collection<BranchLevelFee> findBranchLevelFeeByBranchIdAndAcademicYearIdAndFeeType(final Long branchId, final Long academicYearId,
			final FeeTypeConstant feeType) {

		final TypedQuery<BranchLevelFee> query = this.getEntityManager().createQuery(
				"select e from BranchLevelFee e where e.branch.id = :branchId and e.academicYear.id = :academicYearId and e.buildingBlock.feeType = :feeType",
				BranchLevelFee.class);
		query.setParameter("branchId", branchId);
		query.setParameter("academicYearId", academicYearId);
		query.setParameter("feeType", feeType);
		return query.getResultList();

	}

}
