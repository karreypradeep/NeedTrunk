package com.apeironsol.need.core.dao;

import java.util.Collection;
import java.util.Map;

import com.apeironsol.need.core.model.StudentSection;
import com.apeironsol.need.util.constants.StudentSectionStatusConstant;
import com.apeironsol.need.util.searchcriteria.StudentSearchCriteria;
import com.apeironsol.framework.BaseDao;
import com.apeironsol.framework.exception.BusinessException;

public interface StudentSectionDao extends BaseDao<StudentSection> {

	/**
	 * Find student section by student academic year id.
	 * 
	 * @param studentId
	 *            student id.
	 */
	Collection<StudentSection> findStudentSectionByStudendAcademicYearId(final Long studentAcademicYearId);

	/**
	 * Remove student section by student id with state assigned.
	 * 
	 * @param studentId
	 *            student id.
	 */
	void removeStudentSectionByStudendAcademicYearId(final Long studentAcademicYearId);

	/**
	 * Find student sections by section id.
	 * 
	 * @param sectionId
	 *            section id.
	 * @return collection of student sections by section id.
	 */
	Collection<StudentSection> findStudentSectionsBySectionId(Long sectionId);

	/**
	 * Find student sections by section id with active state.
	 * 
	 * @param sectionId
	 *            section id.
	 * @return collection of student sections by section id with active state.
	 */
	Collection<StudentSection> findStudentSectionsBySectionIdAndStateActive(Long sectionId);

	/**
	 * Find student section by student academic year id and assigned state.
	 * 
	 * @param studentAcademicYearId
	 *            student academic year id.
	 * @return student section by student academic year id and assigned state.
	 */
	StudentSection findStudentSectionByStudentAcademicYearIdAndSectionId(Long studentAcademicYearId, Long sectionId);

	/**
	 * Retrieve student sections for all the sections id'd and with status as
	 * passed.
	 * 
	 * @param sectionIds
	 *            list of section ids.
	 * @param status
	 *            StudentSectionStatusConstant
	 * @return collection of student sections for all the sections id'd and with
	 *         status as passed.
	 */
	Collection<StudentSection> findStudentSectionsBySectionIdsAndStatus(final Collection<Long> sectionIds, final StudentSectionStatusConstant status);

	/**
	 * Return number of all active students by section.
	 * 
	 * @param sectionId
	 *            section id.
	 * @return number of all active students by section.
	 * @throws BusinessException
	 *             In case of Exception.
	 */
	int findNumberOfActiveStudentsBySectionId(Long sectionId) throws BusinessException;

	/**
	 * Find number of active students by section id's.
	 * 
	 * @param sectionIds
	 *            collection of section id's.
	 * @return number of active students by section id's.
	 */
	Map<Long, Integer> findNumberOfActiveStudentsBySectionIds(final Collection<Long> sectionIds);

	/**
	 * Find all student sections that match the search criteria.
	 * 
	 * @param studentSearchCriteria
	 *            studentSearchCriteria.
	 * @return collection of all student sections that match the search
	 *         criteria.
	 * @throws BusinessException
	 *             In case of Exception.
	 */
	Collection<StudentSection> findStudentSectionsBySearchCriteria(final StudentSearchCriteria studentSearchCriteria) throws BusinessException;

	/**
	 * {@inheritDoc}
	 */
	StudentSection findStudentSectionByStudentAcademicYearIdAndStatus(final Long studentAcademicYearId, final StudentSectionStatusConstant status);

	/**
	 * Find student section by student id and active state.
	 * 
	 * @param studentId
	 *            student id.
	 * @return student section by student id and active state.
	 */
	StudentSection findStudentSectionByStudentAcademicYearIdAndActiveStatus(Long studentAcademicYearId);

	/**
	 * Find latest student section by student academic year.
	 * 
	 * @param studentAcademicYearId
	 *            student academic year id.
	 * @return
	 */
	StudentSection findLatestStudentSectionByStudentAcademicYearId(final Long studentAcademicYearId);

	/**
	 * Find student section by student and section.
	 * 
	 * @param studentId
	 *            student id.
	 * @param sectionId
	 *            sectionId.
	 * @return
	 */
	StudentSection findStudentSectionByStudentIdAndSectionId(final Long studentId, final Long sectionId);

	Collection<StudentSection> findAllStudentSectionsBySectionIds(final Collection<Long> sectionIds);
}
