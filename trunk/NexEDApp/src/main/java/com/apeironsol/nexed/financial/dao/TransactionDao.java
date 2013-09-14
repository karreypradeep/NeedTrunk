package com.apeironsol.nexed.financial.dao;

import java.util.Collection;

import com.apeironsol.nexed.core.model.Transaction;
import com.apeironsol.framework.BaseDao;

public interface TransactionDao extends BaseDao<Transaction> {

	/**
	 * Find all transactions by student id.
	 * 
	 * @param studentId
	 *            student id.
	 * @return collection of all transactions by student id.
	 */
	public Collection<Transaction> findTransactionsByStudentId(Long studentId);

}
