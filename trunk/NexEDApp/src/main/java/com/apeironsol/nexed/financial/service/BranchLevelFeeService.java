/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.nexed.financial.service;

import java.util.Collection;

import com.apeironsol.nexed.financial.model.BranchLevelFee;
import com.apeironsol.nexed.financial.model.BranchLevelFeeCatalog;
import com.apeironsol.nexed.util.constants.FeeTypeConstant;
import com.apeironsol.nexed.util.dataobject.AcademicYearBranchLevelFeeDO;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;
import com.apeironsol.framework.exception.SystemException;

/**
 * Service interface for branch level fee service.
 * 
 * @author Pradeep
 * 
 */
public interface BranchLevelFeeService {

	BranchLevelFee saveBranchLevelFee(BranchLevelFee branchLevelFee) throws BusinessException, SystemException;

	void removeBranchLevelFee(BranchLevelFee branchLevelFee) throws BusinessException, SystemException;

	Collection<BranchLevelFee> findBranchLevelFeesByBranchId(Long id) throws BusinessException, SystemException;

	Collection<BranchLevelFeeCatalog> findAllBranchLevelFeeCatalogsByBranchLevelFeeId(Long id) throws BusinessException, SystemException;

	Collection<AcademicYearBranchLevelFeeDO> findAllAcademicYearBranchLevelFeesByBranchId(Long branchId) throws BusinessException, SystemException;

	Collection<BranchLevelFee> findBranchLevelFeeByBranchIdAndAcademicYearIdAndBuildingBlockId(Long branchId, Long academicYearId, Long buildingBlockId);

	BranchLevelFee updateBranchLevelFee(BranchLevelFee branchLevelFee);

	Collection<BranchLevelFee> findBranchLevelFeeByBranchIdAndAcademicYearId(Long branchId, Long academicYearId);

	void applyBranchLevelFeeToExistingActiveStudents(final BranchLevelFee blassLevelFee) throws BusinessException, InvalidArgumentException;

	Collection<BranchLevelFee> findBranchLevelFeeByBranchIdAndAcademicYearIdAndFeeType(Long branchId, Long academicYearId, FeeTypeConstant feeType);

}
