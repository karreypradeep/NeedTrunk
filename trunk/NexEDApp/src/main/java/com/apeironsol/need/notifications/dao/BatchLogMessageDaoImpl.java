/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.notifications.dao;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.apeironsol.need.notifications.model.BatchLogMessage;
import com.apeironsol.need.util.constants.BatchLogMessageStatusConstant;
import com.apeironsol.framework.BaseDaoImpl;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access class for batch message log.
 * 
 * @author Pradeep
 */
@Repository("batchLogMessageDao")
public class BatchLogMessageDaoImpl extends BaseDaoImpl<BatchLogMessage> implements BatchLogMessageDao {

	/**
	 * Logger.
	 */
	private static final Logger	log	= Logger.getLogger(BatchLogMessageDaoImpl.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BatchLogMessage> findBatchLogMessagesByBatchLogId(final Long batchLogId) {
		TypedQuery<BatchLogMessage> query = this.getEntityManager().createQuery(
				"select blm from BatchLogMessage blm where blm.batchLog.id = :batchLogId", BatchLogMessage.class);
		query.setParameter("batchLogId", batchLogId);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BatchLogMessage> findBatchLogMessagesByBatchLogIdAndSectionId(final Long batchLogId,
			final Long sectionId) throws BusinessException {
		TypedQuery<BatchLogMessage> query = this.getEntityManager().createQuery(
				"select blm from BatchLogMessage blm where blm.batchLog.id = :batchLogId and blm.studentAcademicYear in "
						+ "(select ss.studentAcademicYear.id from StudentSection ss where ss.section.id = :sectionId)",
				BatchLogMessage.class);
		query.setParameter("batchLogId", batchLogId);
		query.setParameter("sectionId", sectionId);
		return query.getResultList();

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<BatchLogMessage> findBatchLogMessagesByStudentAcademicYearId(final Long studentAcademicYearId)
			throws BusinessException {
		TypedQuery<BatchLogMessage> query = this
				.getEntityManager()
				.createQuery(
						"select blm from BatchLogMessage blm where blm.studentAcademicYear.id = :studentAcademicYearId order by blm.messageSentTime DESC",
						BatchLogMessage.class);
		query.setParameter("studentAcademicYearId", studentAcademicYearId);
		return query.getResultList();

	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Long findNumberOfBatchLogMessagesByBatchLogIdAndStatus(final Long batchLogId,
			final EnumSet<BatchLogMessageStatusConstant> statusConstants) throws BusinessException {
		Query query = this
				.getEntityManager()
				.createQuery(
						"select count(blm) from BatchLogMessage blm where blm.batchLog.id = :batchLogId and blm.batchLogMessageStatusConstant in :statusConstants");
		query.setParameter("batchLogId", batchLogId);
		query.setParameter("statusConstants", statusConstants);
		List<Object> obs = query.getResultList();
		long count = (Long) obs.get(0);
		return count;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BatchLogMessage findBatchLogMessageByBatchLogIdAndStudentAcademicYearId(final Long batchLogId,
			final Long studentAcademicYearId) throws BusinessException {
		try {
			TypedQuery<BatchLogMessage> query = this
					.getEntityManager()
					.createQuery(
							"select blm from BatchLogMessage blm where blm.batchLog.id = :batchLogId and blm.studentAcademicYear.id = :studentAcademicYearId",
							BatchLogMessage.class);
			query.setParameter("batchLogId", batchLogId);
			query.setParameter("studentAcademicYearId", studentAcademicYearId);
			return query.getSingleResult();
		} catch (NoResultException e) {
			log.info(e.getMessage());
			return null;
		}
	}

}
