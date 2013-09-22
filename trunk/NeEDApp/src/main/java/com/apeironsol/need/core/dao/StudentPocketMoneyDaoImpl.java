/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.dao;

import java.util.Collection;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.financial.model.StudentPocketMoneyTransaction;
import com.apeironsol.framework.BaseDaoImpl;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for StudentPocketMoneyTransaction entity
 * implementation.
 * 
 * @author Pradeep
 * 
 */
@Repository("studentPocketMoneyTransactionDao")
public class StudentPocketMoneyDaoImpl extends BaseDaoImpl<StudentPocketMoneyTransaction> implements
		StudentPocketMoneyDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentPocketMoneyTransaction> findStudentPocketMoneyTransactionByStudentSectionId(
			final Long studentSectionId) throws BusinessException {
		TypedQuery<StudentPocketMoneyTransaction> query = getEntityManager().createQuery(
				"select spmt from StudentPocketMoneyTransaction spmt where spmt.studentSection.id = :studentSectionId",
				StudentPocketMoneyTransaction.class);
		query.setParameter("studentSectionId", studentSectionId);
		return query.getResultList();
	}

}
