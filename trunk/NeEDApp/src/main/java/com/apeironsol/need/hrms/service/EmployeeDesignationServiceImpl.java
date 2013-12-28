/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 *
 */
package com.apeironsol.need.hrms.service;

import java.util.Collection;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.hrms.dao.EmployeeDao;
import com.apeironsol.need.hrms.dao.EmployeeDesignationDao;
import com.apeironsol.need.hrms.model.Employee;
import com.apeironsol.need.hrms.model.EmployeeDesignation;
import com.apeironsol.framework.exception.BusinessException;

/**
 * @author Sunny
 *
 */
@Service("employeeDesignationService")
@Transactional(rollbackFor = Exception.class)
public class EmployeeDesignationServiceImpl implements EmployeeDesignationService {

	@Resource
	private EmployeeDesignationDao	employeeDesignationDao;

	@Resource
	private EmployeeDao				employeeDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EmployeeDesignation createNewEmployeeDesignation(final EmployeeDesignation employeeDesignation) throws BusinessException {

		EmployeeDesignation employeeCurrentDesignation = this.employeeDesignationDao.findCurrentEmployeeDesignationByEmployeeID(employeeDesignation
				.getEmployee().getId());

		if (employeeCurrentDesignation != null && employeeCurrentDesignation.getEndDate() == null) {

			throw new BusinessException("Employee current designation should end before a new designation is created.");
		}

		return this.employeeDesignationDao.persist(employeeDesignation);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EmployeeDesignation closeEmployeeCurrentDesignation(final String employeeNumber, final Date closeDate) throws BusinessException {

		Employee employee = this.employeeDao.findEmployeeByEmployeeNumber(employeeNumber);

		if (employee == null) {
			throw new BusinessException("Employee not fond with employee number " + employeeNumber);
		}

		EmployeeDesignation employeeCurrentDesignation = this.employeeDesignationDao.findCurrentEmployeeDesignationByEmployeeID(employee.getId());

		if (employeeCurrentDesignation == null) {

			throw new BusinessException("Employee current designation not found.");

		} else if (employeeCurrentDesignation.getEndDate() != null) {

			throw new BusinessException("Employee current designation is already closed");

		} else if(employeeCurrentDesignation.getStartDate().after(closeDate)) {

			throw new BusinessException("Employee designation close date should be aftert the designation start date.");

		} else {

			employeeCurrentDesignation.setEndDate(closeDate);

			return this.employeeDesignationDao.persist(employeeCurrentDesignation);
		}
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeEmployeeDesignation(final EmployeeDesignation employeeDesignation) throws BusinessException {
		this.employeeDesignationDao.remove(employeeDesignation);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EmployeeDesignation findEmployeeDesignationById(final Long id) throws BusinessException {
		return this.employeeDesignationDao.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<EmployeeDesignation> findAllEmployeeDesignations() throws BusinessException {
		return this.employeeDesignationDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<EmployeeDesignation> findAllEmployeeDesignationsByEmployeeID(final Long employeeId) throws BusinessException {
		return this.employeeDesignationDao.findAllEmployeeDesignationsByEmployeeID(employeeId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeEmployeeDesignationByEmployeeID(final Long employeeId) throws BusinessException {
		this.employeeDesignationDao.removeEmployeeDesignationByEmployeeID(employeeId);
	}

	@Override
	public EmployeeDesignation findCurrentEmployeeDesignationByEmployeeID(final Long employeeId) throws BusinessException {
		return this.employeeDesignationDao.findCurrentEmployeeDesignationByEmployeeID(employeeId);
	}

}
