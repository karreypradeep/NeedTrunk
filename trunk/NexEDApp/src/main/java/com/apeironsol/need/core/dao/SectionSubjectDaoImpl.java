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

import com.apeironsol.need.core.model.SectionSubject;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * Data access interface for section subject entity.
 * 
 * @author Pradeep
 */
@Repository("sectionSubjectDao")
public class SectionSubjectDaoImpl extends BaseDaoImpl<SectionSubject> implements SectionSubjectDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<SectionSubject> findSectionSubjectsBySectionId(final Long sectionId) {

		TypedQuery<SectionSubject> query = this.getEntityManager().createQuery(
				"select s from SectionSubject s where s.section.id = :sectionId", SectionSubject.class);
		query.setParameter("sectionId", sectionId);
		return query.getResultList();
	}

}
