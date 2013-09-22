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

import com.apeironsol.framework.BaseDaoImpl;
import com.apeironsol.need.core.model.Section;

/**
 * Data access interface for course implementation entity.
 * 
 * @author Pradeep
 * 
 */
@Repository("sectionDao")
public class SectionDaoImpl extends BaseDaoImpl<Section> implements SectionDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Section> findAllSectionsByKlassId(final Long classId) {
		TypedQuery<Section> query = this.getEntityManager().createQuery("select s from Section s join s.klass k where k.id = :id", Section.class);
		query.setParameter("id", classId);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Section> findActiveSectionsByKlassId(final Long classId) {
		TypedQuery<Section> query = this.getEntityManager().createQuery("select s from Section s join s.klass k where k.id = :id and s.active = :active",
				Section.class);
		query.setParameter("id", classId);
		query.setParameter("active", true);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Section> findAllSectionsByBranchId(final Long branchId) {
		TypedQuery<Section> query = this.getEntityManager().createQuery("select s from Section s join s.klass k where k.branch.id = :id", Section.class);
		query.setParameter("id", branchId);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Section> findAllSectionsByAcademicYearId(final Long academicYearId) {
		TypedQuery<Section> query = this.getEntityManager().createQuery("select s from Section s join s.academicYear k where k.id = :id", Section.class);
		query.setParameter("id", academicYearId);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Section> findActiveSectionsByKlassIdAndAcademicYearId(final Long klassId, final Long academicYearId) {
		TypedQuery<Section> query = this.getEntityManager().createQuery(
				"select s from Section s where s.klass.id = :klassId and s.academicYear.id = :academicYearId and s.active = :active", Section.class);
		query.setParameter("klassId", klassId);
		query.setParameter("academicYearId", academicYearId);
		query.setParameter("active", true);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Section> findAllSectionsByAcademicYearIdAndStatus(final Long academicYearId, final boolean status) {
		TypedQuery<Section> query = this.getEntityManager().createQuery("select s from Section s where s.academicYear.id = :id and s.active = :status",
				Section.class);
		query.setParameter("id", academicYearId);
		query.setParameter("status", status);
		return query.getResultList();
	}

	@Override
	public Collection<Section> findActiveSectionsByKlassIdsAndAcademicYearId(final Collection<Long> klassIds, final Long academicYearId) {
		TypedQuery<Section> query = this.getEntityManager().createQuery(
				"select s from Section s where s.klass.id in :klassIds and s.academicYear.id = :academicYearId and s.active = :active", Section.class);
		query.setParameter("klassIds", klassIds);
		query.setParameter("academicYearId", academicYearId);
		query.setParameter("active", true);
		return query.getResultList();
	}

	@Override
	public Collection<Section> findAllSectionsByKlassIdAndAcademicYearId(final Long klassId, final Long academicYearId) {
		TypedQuery<Section> query = this.getEntityManager().createQuery(
				"select s from Section s where s.klass.id = :klassId and s.academicYear.id = :academicYearId", Section.class);
		query.setParameter("klassId", klassId);
		query.setParameter("academicYearId", academicYearId);
		return query.getResultList();
	}

	@Override
	public Collection<Section> findAllSectionsByKlassIdsAndAcademicYearId(final Collection<Long> klassIds, final Long academicYearId) {
		TypedQuery<Section> query = this.getEntityManager().createQuery(
				"select s from Section s where s.klass.id in :klassIds and s.academicYear.id = :academicYearId", Section.class);
		query.setParameter("klassIds", klassIds);
		query.setParameter("academicYearId", academicYearId);
		return query.getResultList();
	}
}
