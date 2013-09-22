package com.apeironsol.need.core.dao;

import java.util.Collection;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.core.model.StudentStatusHistory;
import com.apeironsol.framework.BaseDaoImpl;

@Repository("studentStatusHistoryDao")
public class StudentStatusHistoryDaoImpl extends BaseDaoImpl<StudentStatusHistory> implements StudentStatusHistoryDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentStatusHistory> findStudentStatusHistoryByStudentId(final Long studentId) {

		TypedQuery<StudentStatusHistory> query = this.getEntityManager().createQuery(
				"select s from StudentStatusHistory s where s.student.id = :studentId ORDER BY s.actionTakenTime DESC",
				StudentStatusHistory.class);
		query.setParameter("studentId", studentId);
		return query.getResultList();

	}

}
