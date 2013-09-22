/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import java.util.Collection;
import java.util.List;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.model.Klass;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.SystemException;

/**
 * Service interface for klass.
 * 
 * @author pradeep
 * 
 */
public interface KlassService {

	/**
	 * Save the class.
	 * 
	 * @param klass
	 *            class.
	 * @return
	 */
	Klass saveKlass(Klass klass);

	/**
	 * Removes class.
	 * 
	 * @param klass
	 *            class.
	 * @throws BusinessException
	 *             In case of exception.
	 * @throws SystemException
	 *             In case of exception.
	 */
	void removeKlass(Klass klass) throws BusinessException, SystemException;

	/**
	 * Find class by id.
	 * 
	 * @param id
	 *            class id.
	 * @return class by id.
	 */
	Klass findKlassById(Long id);

	/**
	 * Retrieves all classes.
	 * 
	 * @return all classes
	 */
	List<Klass> findAllKlasses();

	/**
	 * Retrieves all classes of supplied branch id.
	 * 
	 * @param branchId
	 *            branch id.
	 * @return all classes of supplied branch id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	List<Klass> findKlassesByBranchId(Long branchId) throws BusinessException;

	/**
	 * Activate class.
	 * 
	 * @param klass
	 *            class.
	 * @return class activated.
	 */
	Klass activateKlass(Klass klass);

	/**
	 * De-activate the class.
	 * 
	 * @param klass
	 *            class.
	 * @return
	 */
	Klass deactivateKlass(Klass klass);

	/**
	 * Find active classes by branch id.
	 * 
	 * @param branchId
	 *            branch id.
	 * @return
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<Klass> findActiveKlassesByBranchId(final Long branchId) throws BusinessException;

	/**
	 * Validate class data for activation of academic year.
	 * 
	 * @param branchId
	 * @param academicYear
	 */
	void validateKlasssForAcademicYear(final Long branchId, final AcademicYear academicYear) throws BusinessException;

}
