/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.financial.dao;

import java.util.Collection;

import com.apeironsol.need.financial.model.BranchLevelFee;
import com.apeironsol.need.util.constants.FeeTypeConstant;
import com.apeironsol.framework.BaseDao;

/**
 * Data access interface for branch Fee implementation.
 * 
 * @author Pradeep
 * 
 */
public interface BranchLevelFeeDao extends BaseDao<BranchLevelFee> {

	Collection<BranchLevelFee> findAllBranchLevelFeesByBranchId(Long branchId);

	BranchLevelFee findBranchLevelFeeByBranchIdAndBunildingBlockIdAndAcademicYearId(Long branchId, Long buildingBlockId, Long academicYearId);

	Collection<BranchLevelFee> findBranchLevelFeeByBranchIdAndAcademicYearId(Long branchId, Long academicYearId);

	Collection<BranchLevelFee> findBranchLevelFeeByBranchIdAndAcademicYearIdAndBuildingBlockId(Long branchId, Long academicYearId, Long buildingBlockId);

	Collection<BranchLevelFee> findBranchLevelFeeByBranchIdAndAcademicYearIdAndFeeType(final Long branchId, final Long academicYearId,
			final FeeTypeConstant feeType);

}
