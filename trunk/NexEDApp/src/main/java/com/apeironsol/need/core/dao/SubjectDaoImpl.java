/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.core.model.Subject;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * Data access interface for course implementation entity.
 * 
 * @author Pradeep
 * 
 */
@Repository("subjectDao")
public class SubjectDaoImpl extends BaseDaoImpl<Subject> implements SubjectDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Subject> findAllSubjectsByKlassId(final Long classId) {
		TypedQuery<Subject> query = this.getEntityManager().createQuery("select s from Subject s join s.klass k where k.id = :id", Subject.class);
		query.setParameter("id", classId);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Subject> findElectiveSubjectsByKlassId(final Long classId) {
		TypedQuery<Subject> query = this.getEntityManager().createQuery("select s from Subject s join s.klass k where k.id = :id and s.elective = :elective",
				Subject.class);
		query.setParameter("id", classId);
		query.setParameter("elective", true);
		return query.getResultList();
	}

	@Override
	public Collection<Subject> findDistenctSubjectsAmongSections(final Collection<Section> sections) {

		List<Long> ids = new ArrayList<Long>();
		for (Section section : sections) {
			ids.add(section.getId());
		}

		TypedQuery<Subject> query = this.getEntityManager().createQuery("select distinct s.subject from SectionSubject s where s.section.id in :ids",
				Subject.class);
		query.setParameter("ids", ids);
		return query.getResultList();
	}

}
