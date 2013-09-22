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

import com.apeironsol.need.core.model.SectionTeacher;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * Data access interface for Student Attendance entity implementation.
 * 
 * @author Pradeep
 * 
 */
@Repository("sectionTeacherDao")
public class SectionTeacherDaoImpl extends BaseDaoImpl<SectionTeacher> implements SectionTeacherDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<SectionTeacher> findSectionTeachersByScetionId(final Long sectionId) {
		TypedQuery<SectionTeacher> query = this.getEntityManager().createQuery(
				"select st from SectionTeacher st where st.section.id = :sectionId", SectionTeacher.class);
		query.setParameter("sectionId", sectionId);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SectionTeacher findSectionTeacherByScetionIdAndEmployeeId(final Long sectionId, final Long employeeId) {
		TypedQuery<SectionTeacher> query = this.getEntityManager().createQuery(
				"select st from SectionTeacher st where st.section.id = :sectionId and"
						+ " sa.employee.id = :employeeId", SectionTeacher.class);
		query.setParameter("sectionId", sectionId);
		query.setParameter("employeeId", employeeId);
		return query.getSingleResult();
	}
}
