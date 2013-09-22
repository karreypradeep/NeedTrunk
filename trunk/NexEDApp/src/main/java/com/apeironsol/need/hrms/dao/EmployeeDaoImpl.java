/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 *
 */
package com.apeironsol.need.hrms.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.hrms.model.Employee;
import com.apeironsol.need.hrms.model.EmployeeDesignation;
import com.apeironsol.need.util.constants.EmploymentCurrentStatusConstant;
import com.apeironsol.need.util.searchcriteria.EmployeeSearchCriteria;
import com.apeironsol.framework.BaseDaoImpl;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for employee entity implementation.
 *
 * @author Sunny
 *
 */
@Repository("employeeDao")
public class EmployeeDaoImpl extends BaseDaoImpl<Employee> implements EmployeeDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Employee> findAllEmployeesByBranchId(final Long branchId) {
		TypedQuery<Employee> query = this.getEntityManager().createQuery("select e from Employee e where e.branch.id = :branchId", Employee.class);
		query.setParameter("branchId", branchId);
		return query.getResultList();
	}

	@Override
	public Collection<Employee> findEmployeesByBranchIdAndStatus(final Long branchId, final EmploymentCurrentStatusConstant status) {
		TypedQuery<Employee> query = this.getEntityManager().createQuery(
				"select e from Employee e where e.branch.id = :branchId and e.currentStatus = :currentStatus", Employee.class);
		query.setParameter("branchId", branchId);
		query.setParameter("currentStatus", status);
		return query.getResultList();
	}

	@Override
	public Collection<Employee> findAllEmployeesByBranchIdAndAcademicYear(final Long branchId, final AcademicYear academicyear) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee findAllEmployeesByEmployeeNumber(final String employeeNumber) {

		try {
			TypedQuery<Employee> query = this.getEntityManager().createQuery("select e from Employee e where e.employeeNumber = :employeeNumber",
					Employee.class);
			query.setParameter("employeeNumber", employeeNumber);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Employee findEmployeeByUsername(final String username) {
		try {
			TypedQuery<Employee> query = this.getEntityManager().createQuery("select e from Employee e where e.userAccount.username = :username",
					Employee.class);
			query.setParameter("username", username);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Employee findEmployeeByEmployeeNumber(final String employeeNumber) {
		try {
			TypedQuery<Employee> query = this.getEntityManager().createQuery("select e from Employee e where e.employeeNumber = :employeeNumber",
					Employee.class);
			query.setParameter("employeeNumber", employeeNumber);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Collection<Employee> findEmployeesBySearchCriteria(final EmployeeSearchCriteria employeeSearchCriteria) throws BusinessException {
		StringBuilder queryString = new StringBuilder("select ed from EmployeeDesignation ed ");
		boolean whereClasuseAdded = false;

		if (employeeSearchCriteria.getBranch() != null) {
			if (!whereClasuseAdded) {
				queryString = queryString.append(" where ");
			} else {
				queryString = queryString.append(" and ");
			}
			queryString = queryString.append(" ed.employee.branch = :branch ");
			whereClasuseAdded = true;
		}

		if (employeeSearchCriteria.getName() != null && !employeeSearchCriteria.getName().trim().isEmpty()) {
			if (!whereClasuseAdded) {
				queryString = queryString.append(" where ");
			} else {
				queryString = queryString.append(" and ");
			}
			queryString = queryString.append(" ( ");
			queryString = queryString.append(" ed.employee.firstName like '%" + employeeSearchCriteria.getName() + "%'");
			queryString = queryString.append(" or ed.employee.lastName like '%" + employeeSearchCriteria.getName() + "%'");
			queryString = queryString.append(" or ed.employee.middleName like '%" + employeeSearchCriteria.getName() + "%'");
			queryString = queryString.append(" ) ");
			whereClasuseAdded = true;
		}

		if (employeeSearchCriteria.getEmployeeNumber() != null && !employeeSearchCriteria.getEmployeeNumber().trim().isEmpty()) {
			if (!whereClasuseAdded) {
				queryString = queryString.append(" where ");
			} else {
				queryString = queryString.append(" and ");
			}
			queryString = queryString.append(" ( ");
			queryString = queryString.append(" ed.employee.employeeNumber like '%" + employeeSearchCriteria.getEmployeeNumber() + "%'");
			queryString = queryString.append(" ) ");
			whereClasuseAdded = true;
		}

		if (employeeSearchCriteria.getDateOfBirth() != null) {
			if (!whereClasuseAdded) {
				queryString = queryString.append(" where ");
			} else {
				queryString = queryString.append(" and ");
			}
			queryString = queryString.append(" ed.employee.dateOfBirth = :dateOfBirth ");
			whereClasuseAdded = true;
		}

		if (employeeSearchCriteria.getDepartmentBuildingBlock() != null) {
			if (!whereClasuseAdded) {
				queryString = queryString.append(" where ");
			} else {
				queryString = queryString.append(" and ");
			}
			queryString = queryString.append(" ed.department = :department ");
			whereClasuseAdded = true;
		}

		if (employeeSearchCriteria.getDesignationBuildingBlock() != null) {
			if (!whereClasuseAdded) {
				queryString = queryString.append(" where ");
			} else {
				queryString = queryString.append(" and ");
			}
			queryString = queryString.append(" ed.designation = :designation ");
			whereClasuseAdded = true;
		}

		if (employeeSearchCriteria.getEmploymentType() != null) {
			if (!whereClasuseAdded) {
				queryString = queryString.append(" where ");
			} else {
				queryString = queryString.append(" and ");
			}
			queryString = queryString.append(" ed.employmentType = :employmentType ");
			whereClasuseAdded = true;
		}

		TypedQuery<EmployeeDesignation> query = this.getEntityManager().createQuery(queryString.toString(), EmployeeDesignation.class);

		if (employeeSearchCriteria.getBranch() != null) {
			query.setParameter("branch", employeeSearchCriteria.getBranch());
		}

		if (employeeSearchCriteria.getDateOfBirth() != null) {
			query.setParameter("dateOfBirth", employeeSearchCriteria.getDateOfBirth());
		}

		if (employeeSearchCriteria.getDepartmentBuildingBlock() != null) {
			query.setParameter("department", employeeSearchCriteria.getDepartmentBuildingBlock());
		}

		if (employeeSearchCriteria.getDesignationBuildingBlock() != null) {
			query.setParameter("designation", employeeSearchCriteria.getDesignationBuildingBlock());
		}

		if (employeeSearchCriteria.getEmploymentType() != null) {
			query.setParameter("employmentType", employeeSearchCriteria.getEmploymentType());
		}
		Collection<EmployeeDesignation> employeeDesignations = query.getResultList();

		Map<String, Employee> employeesMap = new HashMap<String, Employee>();

		if (employeeDesignations != null && !employeeDesignations.isEmpty()) {
			for (EmployeeDesignation employeeDesignation : employeeDesignations) {
				if (employeesMap.get(employeeDesignation.getEmployee().getEmployeeNumber()) == null) {
					employeesMap.put(employeeDesignation.getEmployee().getEmployeeNumber(), employeeDesignation.getEmployee());
				}
			}
		}
		Collection<Employee> employees = new ArrayList<Employee>();
		for (Employee employee : employeesMap.values()) {
			employees.add(employee);
		}
		return employees;
	}

}
