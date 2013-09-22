/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.dao;

import java.util.Collection;

import com.apeironsol.need.financial.model.StudentPocketMoneyTransaction;
import com.apeironsol.framework.BaseDao;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for StudentPocketMoneyTransaction entity
 * implementation.
 * 
 * @author Pradeep
 * 
 */
public interface StudentPocketMoneyDao extends BaseDao<StudentPocketMoneyTransaction> {

	/**
	 * Find student pocket money transactions by student section Id.
	 * 
	 * @param studentSectionId
	 *            student section Id.
	 * @return collection of student pocket money transactions by student
	 *         section Id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<StudentPocketMoneyTransaction> findStudentPocketMoneyTransactionByStudentSectionId(Long studentSectionId)
			throws BusinessException;

}
