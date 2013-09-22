package com.apeironsol.need.financial.dao;

import java.util.Collection;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.core.model.Transaction;
import com.apeironsol.framework.BaseDaoImpl;

@Repository(value = "transactionDao")
public class TransactionDaoImpl extends BaseDaoImpl<Transaction> implements TransactionDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Transaction> findTransactionsByStudentId(final Long studentId) {
		TypedQuery<Transaction> query = this.getEntityManager().createQuery(
				"select t form Transaction t join  t.student s where s.studentId=:studentId", Transaction.class);
		query.setParameter("studentId", studentId);
		return query.getResultList();
	}
}
