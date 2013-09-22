/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.payroll.dao;

import java.util.Collection;
import java.util.Date;

import javax.annotation.Resource;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.hrms.dao.EmployeeDao;
import com.apeironsol.need.hrms.model.Employee;
import com.apeironsol.need.payroll.model.EmployeeSalary;
import com.apeironsol.need.util.DateUtil;
import com.apeironsol.need.util.searchcriteria.EmployeeSalarySearchCriteria;
import com.apeironsol.framework.BaseDaoImpl;
import com.apeironsol.framework.exception.BusinessException;

/**
 * Data access interface for employee salary entity implementation.
 * 
 * @author Sunny
 * 
 */
@Repository("employeeSalaryDao")
public class EmployeeSalaryDaoImpl extends BaseDaoImpl<EmployeeSalary> implements EmployeeSalaryDao {

	@Resource
	EmployeeDao	employeeDao;

	@Override
	public Collection<EmployeeSalary> findEmployeeSalariesByEmployeeIdAndMonth(final Long employeeId, final Date salaryMonth) {
		TypedQuery<EmployeeSalary> query = this.getEntityManager().createQuery(
				"select es from EmployeeSalary es where es.employee.id = :employeeId and es.salaryMonth = :salaryMonth", EmployeeSalary.class);
		query.setParameter("employeeId", employeeId);
		query.setParameter("salaryMonth", salaryMonth);
		return query.getResultList();
	}

	@Override
	public Collection<EmployeeSalary> findEmployeeSalariesBySearchCriteria(final EmployeeSalarySearchCriteria employeeSalarySearchCriteria)
			throws BusinessException {
		Collection<Employee> employees = this.employeeDao.findEmployeesBySearchCriteria(employeeSalarySearchCriteria);
		StringBuilder queryString = new StringBuilder("select es from EmployeeSalary es ");
		boolean whereClasuseAdded = false;

		if (employees != null && !employees.isEmpty()) {
			if (!whereClasuseAdded) {
				queryString = queryString.append(" where ");
			} else {
				queryString = queryString.append(" and ");
			}
			queryString = queryString.append(" es.employee in :employees ");
			whereClasuseAdded = true;
		}

		if (employeeSalarySearchCriteria.getFromDate() != null) {
			if (!whereClasuseAdded) {
				queryString = queryString.append(" where ");
			} else {
				queryString = queryString.append(" and ");
			}
			queryString = queryString.append(" es.paidDate >= :fromDate ");
			whereClasuseAdded = true;
		}

		if (employeeSalarySearchCriteria.getToDate() != null) {
			if (!whereClasuseAdded) {
				queryString = queryString.append(" where ");
			} else {
				queryString = queryString.append(" and ");
			}
			queryString = queryString.append(" es.paidDate <= :toDate ");
			whereClasuseAdded = true;
		}

		if (employeeSalarySearchCriteria.getSalaryMonth() != null) {
			if (!whereClasuseAdded) {
				queryString = queryString.append(" where ");
			} else {
				queryString = queryString.append(" and ");
			}
			queryString = queryString.append(" es.salaryMonth = :salaryMonth ");
			whereClasuseAdded = true;
		}

		TypedQuery<EmployeeSalary> query = this.getEntityManager().createQuery(queryString.toString(), EmployeeSalary.class);

		if (employees != null && !employees.isEmpty()) {
			query.setParameter("employees", employees);
		}

		if (employeeSalarySearchCriteria.getFromDate() != null) {
			query.setParameter("fromDate", employeeSalarySearchCriteria.getFromDate());
		}

		if (employeeSalarySearchCriteria.getToDate() != null) {
			query.setParameter("toDate", employeeSalarySearchCriteria.getToDate());
		}

		if (employeeSalarySearchCriteria.getSalaryMonth() != null) {
			query.setParameter("salaryMonth", DateUtil.returnFirstDateOfMonth(employeeSalarySearchCriteria.getToDate()));
		}

		return query.getResultList();
	}

}
