/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.dao;

import java.util.Collection;

import com.apeironsol.framework.BaseDao;
import com.apeironsol.need.core.model.Section;

/**
 * Data access interface for course entity.
 * 
 * @author Pradeep
 * 
 */
public interface SectionDao extends BaseDao<Section> {

	/**
	 * Find all sections by class id.
	 * 
	 * @param classId
	 *            class id.
	 * @return collection of all sections by class id.
	 */
	Collection<Section> findAllSectionsByKlassId(Long classId);

	/**
	 * Find all sections by branch id.
	 * 
	 * @param branchId
	 *            branch id.
	 * @return collection of all sections by branch id.
	 */
	Collection<Section> findAllSectionsByBranchId(Long branchId);

	/**
	 * Find all sections by academic year id.
	 * 
	 * @param academicYearId
	 *            academic year id.
	 * @return
	 */
	Collection<Section> findAllSectionsByAcademicYearId(Long academicYearId);

	/**
	 * Find all active sections by class id and academic year id.
	 * 
	 * @param klassId
	 *            class id.
	 * @param academicYearId
	 *            academic year id.
	 * @return collection of all active sections by class id and academic year
	 *         id.
	 */
	Collection<Section> findActiveSectionsByKlassIdAndAcademicYearId(Long klassId, Long academicYearId);

	/**
	 * Find all active sections by class ids and academic year id.
	 * 
	 * @param klassIds
	 *            class ids.
	 * @param academicYearId
	 *            academic year id.
	 * @return collection of all active sections by class id and academic year
	 *         id.
	 */
	Collection<Section> findActiveSectionsByKlassIdsAndAcademicYearId(Collection<Long> klassIds, Long academicYearId);

	/**
	 * Find all active sections by class id.
	 * 
	 * @param klassId
	 *            class id.
	 * @return collection of all active sections by class id.
	 */
	Collection<Section> findActiveSectionsByKlassId(Long klassId);

	/**
	 * Find all sections by academic year id and status.
	 * 
	 * @param academicYearId
	 *            academic year id.
	 * @param status
	 *            status.
	 * @return collection of all sections by academic year id and status.
	 */
	Collection<Section> findAllSectionsByAcademicYearIdAndStatus(Long academicYearId, final boolean status);

	/**
	 * Find all sections by class id and academic year id.
	 * 
	 * @param klassId
	 *            class id.
	 * @param academicYearId
	 *            academic year id.
	 * @return collection of all active sections by class id and academic year
	 *         id.
	 */
	Collection<Section> findAllSectionsByKlassIdAndAcademicYearId(Long klassId, Long academicYearId);

	Collection<Section> findAllSectionsByKlassIdsAndAcademicYearId(final Collection<Long> klassIds, final Long academicYearId);
}
