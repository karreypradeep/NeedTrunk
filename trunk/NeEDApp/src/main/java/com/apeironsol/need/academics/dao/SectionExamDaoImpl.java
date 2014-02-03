package com.apeironsol.need.academics.dao;

import java.util.Collection;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.framework.BaseDaoImpl;
import com.apeironsol.need.academics.model.SectionExam;
import com.apeironsol.need.core.model.Section;

@Repository
public class SectionExamDaoImpl extends BaseDaoImpl<SectionExam> implements SectionExamDao {

	@Override
	public Collection<SectionExam> findSectionExamsByBranchId(final Long branchId) {
		final TypedQuery<SectionExam> query = getEntityManager().createQuery("select s from SectionExam s where s.exam.branch.id = :branchId",
				SectionExam.class);
		query.setParameter("branchId", branchId);
		return query.getResultList();
	}

	@Override
	public Collection<SectionExam> findSectionExamsBySectionId(final Long sectionId) {
		final TypedQuery<SectionExam> query = getEntityManager().createQuery("select s from SectionExam s where s.section.id = :sectionId", SectionExam.class);
		query.setParameter("sectionId", sectionId);
		return query.getResultList();
	}

	@Override
	public Collection<SectionExam> findSectionExamsByKlassIdAndExamId(final Long klassId, final Long examId) {
		final TypedQuery<SectionExam> query = getEntityManager().createQuery(
				"select s from SectionExam s where s.section.klass.id = :klassId and s.exam.id = :examId", SectionExam.class);
		query.setParameter("klassId", klassId);
		query.setParameter("examId", examId);
		return query.getResultList();
	}

	@Override
	public Collection<Section> findSectionsByKlassIdAndExamId(final Long klassId, final Long examId) {
		final TypedQuery<Section> query = getEntityManager().createQuery(
				"select s.section from SectionExam s where s.section.klass.id = :klassId and s.exam.id = :examId", Section.class);
		query.setParameter("klassId", klassId);
		query.setParameter("examId", examId);
		return query.getResultList();
	}

	@Override
	public Collection<SectionExam> findSectionExamsByBranchIdAndExamId(final Long branchId, final Long examId) {
		final TypedQuery<SectionExam> query = getEntityManager().createQuery(
				"select s from SectionExam s where s.exam.branch.id = :branchId and s.exam.id = :examId", SectionExam.class);
		query.setParameter("branchId", branchId);
		query.setParameter("examId", examId);
		return query.getResultList();
	}

	@Override
	public Collection<SectionExam> findSectionExamsBySectionIdAndExamTypeId(final Long sectionId, final Long examTypeId) {
		final TypedQuery<SectionExam> query = getEntityManager().createQuery(
				"select s from SectionExam s where s.section.id = :sectionId and s.exam.buildingBlock.id =:examTypeId", SectionExam.class);
		query.setParameter("sectionId", sectionId);
		query.setParameter("examTypeId", examTypeId);
		return query.getResultList();
	}

	@Override
	public SectionExam findSectionExamsBySectionIdAndExamId(final Long sectionId, final Long examId) {
		try {
			final TypedQuery<SectionExam> query = getEntityManager().createQuery(
					"select s from SectionExam s where s.section.id = :sectionId and s.exam.id = :examId", SectionExam.class);
			query.setParameter("sectionId", sectionId);
			query.setParameter("examId", examId);
			return query.getSingleResult();

		} catch (final NoResultException e) {
			return null;
		}
	}

	@Override
	public Collection<SectionExam> findSectionExamsByKlassId(final Long klassId, final Long academicYearId) {
		final TypedQuery<SectionExam> query = getEntityManager().createQuery(
				"select s from SectionExam s where s.section.klass.id = :klassId and s.section.academicYear.id = :academicYearId", SectionExam.class);
		query.setParameter("klassId", klassId);
		query.setParameter("academicYearId", academicYearId);
		return query.getResultList();
	}

	@Override
	public Collection<SectionExam> findSectionExamsByExamIdsandAcademicYearId(final Collection<Long> examIDs, final Long academicYearId) {
		final TypedQuery<SectionExam> query = getEntityManager().createQuery(
				"select s from SectionExam s where s.exam.id in :examIDs and s.section.academicYear.id = :academicYearId", SectionExam.class);
		query.setParameter("examIDs", examIDs);
		query.setParameter("academicYearId", academicYearId);
		return query.getResultList();
	}

	@Override
	public Collection<SectionExam> findSectionExamsByAcademicYearId(final Long academicYearId) {
		final TypedQuery<SectionExam> query = getEntityManager().createQuery("select s from SectionExam s where  s.section.academicYear.id = :academicYearId",
				SectionExam.class);
		query.setParameter("academicYearId", academicYearId);
		return query.getResultList();
	}

	@Override
	public Collection<SectionExam> findSectionExamsBySectionIdsAndExamId(final Collection<Long> sectionIds, final Long examId) {
		final TypedQuery<SectionExam> query = getEntityManager().createQuery(
				"select s from SectionExam s where s.section.id in :sectionIds and s.exam.id = :examId", SectionExam.class);
		query.setParameter("sectionIds", sectionIds);
		query.setParameter("examId", examId);
		return query.getResultList();

	}
}
