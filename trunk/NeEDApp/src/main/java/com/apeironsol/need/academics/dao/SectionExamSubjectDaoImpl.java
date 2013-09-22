package com.apeironsol.need.academics.dao;

import java.util.Collection;
import java.util.Date;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.academics.model.SectionExamSubject;
import com.apeironsol.framework.BaseDaoImpl;

@Repository
public class SectionExamSubjectDaoImpl extends BaseDaoImpl<SectionExamSubject> implements SectionExamSubjectDao {

	@Override
	public SectionExamSubject findSectionExamSubjectBySectionIdAndScheduledDateBetweenStartAndEndTime(final Long sectionId, final Date scheduledDate,
			final Date startTime, final Date endTime) {
		try {
			TypedQuery<SectionExamSubject> query = this.getEntityManager().createQuery(
					"select s from SectionExamSubject s where s.sectionExam.section.id = :sectionId and s.scheduledDate = :scheduledDate "
							+ "and ((s.startTime between :startTime and :endTime) or (s.endTime between :startTime and :endTime)) ", SectionExamSubject.class);
			query.setParameter("sectionId", sectionId);
			query.setParameter("scheduledDate", scheduledDate, TemporalType.DATE);
			query.setParameter("startTime", startTime, TemporalType.TIME);
			query.setParameter("endTime", endTime, TemporalType.TIME);
			query.setMaxResults(1);
			return query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		}

	}

	@Override
	public void removeSectionExamSubjectsBySectionExamId(final Long sectionExamId) {

		Query query = this.getEntityManager().createQuery("delete from SectionExamSubject e where e.sectionExam.id = :sectionExamId");
		query.setParameter("sectionExamId", sectionExamId);
		query.executeUpdate();

	}

	@Override
	public Collection<SectionExamSubject> findSectionExamSubjectsBySectionExamId(final Long sectionExamId) {

		TypedQuery<SectionExamSubject> query = this.getEntityManager().createQuery(
				"select s from SectionExamSubject s where s.sectionExam.id = :sectionExamId", SectionExamSubject.class);
		query.setParameter("sectionExamId", sectionExamId);
		return query.getResultList();
	}

}
