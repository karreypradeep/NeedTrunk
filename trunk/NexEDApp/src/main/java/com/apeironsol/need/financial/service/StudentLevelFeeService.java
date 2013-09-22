/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.financial.service;

import java.util.Collection;

import com.apeironsol.need.financial.model.StudentLevelFee;
import com.apeironsol.need.financial.model.StudentLevelFeeCatalog;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.SystemException;

/**
 * Service interface for branch level fee service.
 * 
 * @author Pradeep
 * 
 */
public interface StudentLevelFeeService {

	StudentLevelFee saveStudentLevelFee(StudentLevelFee studentLevelFee) throws BusinessException, SystemException;

	void removeStudentLevelFee(StudentLevelFee studentLevelFee) throws BusinessException, SystemException;

	Collection<StudentLevelFee> findStudentLevelFeesByStudentAcademicYearId(Long studentAcademicYearId) throws BusinessException, SystemException;

	Collection<StudentLevelFeeCatalog> findStudentLevelFeeCatalogsByStudentLevelFeeId(Long studentLevelFeeId);

}
