/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.academics.dao;

import java.util.Collection;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.framework.BaseDaoImpl;
import com.apeironsol.need.academics.model.ReportCardExam;
import com.apeironsol.need.util.NonNull;

/**
 * Data access interface for employee salary entity implementation.
 * 
 * @author Pradeep
 * 
 */
@Repository("reportCardExamDao")
public class ReportCardExamDaoImpl extends BaseDaoImpl<ReportCardExam> implements ReportCardExamDao {

	@Override
	public Collection<ReportCardExam> findAllReportCardExamsByReportCard(@NonNull final Long reportCardId) {
		TypedQuery<ReportCardExam> query = this.getEntityManager().createQuery("select gs from ReportCardExam gs where gs.reportCard.id  = :reportCardId",
				ReportCardExam.class);
		query.setParameter("reportCardId", reportCardId);
		return query.getResultList();
	}

	@Override
	public Collection<ReportCardExam> findAllReportCardExamsByExams(@NonNull final Collection<Long> examIds) {
		TypedQuery<ReportCardExam> query = this.getEntityManager().createQuery("select gs from ReportCardExam gs where gs.exam.id  in :examIds",
				ReportCardExam.class);
		query.setParameter("examIds", examIds);
		return query.getResultList();
	}

}
