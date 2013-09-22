package com.apeironsol.need.academics.dao;

import java.util.Collection;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.academics.model.Exam;
import com.apeironsol.framework.BaseDaoImpl;

@Repository
public class ExamDaoImpl extends BaseDaoImpl<Exam> implements ExamDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Exam> findExamsByBranchId(final Long branchId) {
		TypedQuery<Exam> query = this.getEntityManager().createQuery("select e from Exam e join e.branch br where br.id = :id", Exam.class);
		query.setParameter("id", branchId);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Exam> findExamsByBranchIdAndExamTypeBuildingBlockId(final Long branchId, final Long examTypeBuildingBlockId) {
		TypedQuery<Exam> query = this.getEntityManager().createQuery(
				"select e from Exam e where e.branch.id = :id and e.buildingBlock.id =:examTypeBuildingBlockId", Exam.class);
		query.setParameter("id", branchId);
		query.setParameter("examTypeBuildingBlockId", examTypeBuildingBlockId);
		return query.getResultList();
	}

}
