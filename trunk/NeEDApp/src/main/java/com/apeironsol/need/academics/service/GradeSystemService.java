/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.academics.service;

import java.util.Collection;

import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.need.academics.model.GradeSystem;
import com.apeironsol.need.util.NonNull;

/**
 * Service interface for GradeSystem.
 * 
 * @author Sunny
 * 
 */
public interface GradeSystemService {

	/**
	 * Save GradeSystem.
	 * 
	 * @param GradeSystem
	 *            gradeSystem to be saved.
	 * @return persisted GradeSystem.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	GradeSystem saveGradeSystem(GradeSystem gradeSystem) throws BusinessException;

	/**
	 * Delete GradeSystem.
	 * 
	 * @param GradeSystem
	 *            gradeSystem to be deleted.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void removeGradeSystem(GradeSystem gradeSystem) throws BusinessException;

	/**
	 * Find GradeSystem by Id.
	 * 
	 * @param id
	 *            GradeSystem Id.
	 * @return GradeSystem with supplied Id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	GradeSystem findGradeSystemById(Long id) throws BusinessException;

	/**
	 * Find all GradeSystems.
	 * 
	 * @return collections of all GradeSystems
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<GradeSystem> findAllGradeSystems(Long branchId) throws BusinessException;

	/**
	 * Remove all employee ctc by employee id.
	 * 
	 * @param employeeId
	 *            employee id.
	 */
	GradeSystem findDefaultGradeSystemForBranch(@NonNull final Long branchId);
}
