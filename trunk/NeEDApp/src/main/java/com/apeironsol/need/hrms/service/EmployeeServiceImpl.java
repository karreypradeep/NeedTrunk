/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 *
 */
package com.apeironsol.need.hrms.service;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.service.SequenceGeneratorService;
import com.apeironsol.need.hrms.dao.EmployeeDao;
import com.apeironsol.need.hrms.dao.EmployeeDesignationDao;
import com.apeironsol.need.hrms.dataobject.EmployeeDO;
import com.apeironsol.need.hrms.model.Employee;
import com.apeironsol.need.hrms.model.EmployeeDesignation;
import com.apeironsol.need.util.constants.EmploymentCurrentStatusConstant;
import com.apeironsol.need.util.searchcriteria.EmployeeSearchCriteria;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Service implementation interface for student.
 *
 * @author pradeep
 *
 */
@Service("employeeService")
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	@Resource
	private EmployeeDao					employeeDao;

	@Resource
	private EmployeeDesignationDao 		employeeDesignationDao;

	@Resource
	private EmployeeDesignationService	employeeDesignationService;

	@Resource
	private SequenceGeneratorService	sequenceGeneratorService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Employee saveEmployee(final Employee employee) {
		return this.employeeDao.persist(employee);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeEmployee(final Employee employee) {
		this.employeeDesignationService.removeEmployeeDesignationByEmployeeID(employee.getId());
		this.employeeDao.remove(employee);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Employee findEmployeeById(final Long id) {
		return this.employeeDao.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Employee> findAllEmployees() {
		return this.employeeDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Employee> findAllEmployeesByBranchId(final Long branchId) {
		return this.employeeDao.findAllEmployeesByBranchId(branchId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveNewEmployeeWizard(final Employee employee, final EmployeeDesignation employeeDesignation) {

		String employeeNumber = this.sequenceGeneratorService.getNextEmployeeNumber(employee.getBranch().getCode());
		employee.setEmployeeNumber(employeeNumber);
		Employee newEmployee = this.employeeDao.persist(employee);
		employeeDesignation.setEmployee(newEmployee);
		this.employeeDesignationService.createNewEmployeeDesignation(employeeDesignation);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Employee> findEmployeesByBranchIdAndStatus(final Long branchId, final EmploymentCurrentStatusConstant status) throws BusinessException {
		return this.employeeDao.findEmployeesByBranchIdAndStatus(branchId, status);
	}

	@Override
	public Collection<Employee> findAllEmployeesByBranchIdAndAcademicYear(final Long branchId, final AcademicYear academicyear) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee findEmployeeByUsername(final String username) {
		return this.employeeDao.findEmployeeByUsername(username);
	}

	@Override
	public Collection<EmployeeDO> findEmployeesBySearchCriteria(final EmployeeSearchCriteria employeeSearchCriteria) throws BusinessException {

		Collection<EmployeeDO> employeeDOs = new ArrayList<EmployeeDO>();
		Collection<Employee> searchResults = this.employeeDao.findEmployeesBySearchCriteria(employeeSearchCriteria);

		if (searchResults != null && !searchResults.isEmpty()) {

			for (Employee employee : searchResults) {

				EmployeeDO employeeDO = this.buildEmployeeDO(employee);

				employeeDOs.add(employeeDO);

			}

		}

		return employeeDOs;
	}

	private EmployeeDO buildEmployeeDO(final Employee employee) {

		EmployeeDesignation employeeCurrentDesignation = this.employeeDesignationDao.findCurrentEmployeeDesignationByEmployeeID(employee.getId());

		EmployeeDesignation employeeFirstDesignation = this.employeeDesignationDao.findFirstEmployeeDesignationByEmployeeID(employee.getId());

		EmployeeDO employeeDO = new EmployeeDO();

		employeeDO.setEmployee(employee);

		employeeDO.setEmployeeCurrentDesignation(employeeCurrentDesignation);

		employeeDO.setJoiningDate(employeeFirstDesignation.getEndDate());

		return employeeDO;
	}

	@Override
	public EmployeeDO findEmployeeDetailsById(final Long id) throws BusinessException {
		return this.buildEmployeeDO(this.findEmployeeById(id));
	}

}
