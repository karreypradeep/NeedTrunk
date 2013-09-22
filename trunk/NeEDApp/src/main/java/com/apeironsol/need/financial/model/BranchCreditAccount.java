/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.financial.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.hrms.model.Employee;
import com.apeironsol.framework.BaseEntity;

/**
 * Entity class for academic year expenses.
 * 
 * @author Pradeep
 */
@Entity
@Table(name = "BRANCH_CREDIT_ACCOUNT", uniqueConstraints = { @UniqueConstraint(columnNames = { "ACCOUNT_NUMBER", "BRANCH_ID" }) })
public class BranchCreditAccount extends BaseEntity implements Serializable {

	/**
	 * Universal serial version id for this class
	 */
	private static final long	serialVersionUID	= 2140256734650946265L;

	@NotNull(message = "model.bank_account_name_mandatory")
	@Column(name = "ACCOUNT_NUMBER", length = 50, nullable = false)
	private String				accountNumber;

	@NotNull(message = "model.expense_end_date_mandatory")
	@Column(name = "ACCOUNT_OPENING_DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date				accountOpeningDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCH_ID", nullable = false)
	private Branch				branch;

	@NotNull(message = "model.employee_id_mandatory")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "EMPLOYEE_ID", nullable = false)
	private Employee			employee;

	@Column(name = "ACCOUNT_BALANCE", nullable = false)
	private Double				accountBalance;

	/**
	 * @return the branch
	 */
	public Branch getBranch() {
		return this.branch;
	}

	/**
	 * @param branch
	 *            the branch to set
	 */
	public void setBranch(final Branch branch) {
		this.branch = branch;
	}

	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return this.accountNumber;
	}

	/**
	 * @param accountNumber
	 *            the accountNumber to set
	 */
	public void setAccountNumber(final String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * @return the accountOpeningDate
	 */
	public Date getAccountOpeningDate() {
		return this.accountOpeningDate;
	}

	/**
	 * @param accountOpeningDate
	 *            the accountOpeningDate to set
	 */
	public void setAccountOpeningDate(final Date accountOpeningDate) {
		this.accountOpeningDate = accountOpeningDate;
	}

	/**
	 * @return the employee
	 */
	public Employee getEmployee() {
		return this.employee;
	}

	/**
	 * @param employee
	 *            the employee to set
	 */
	public void setEmployee(final Employee employee) {
		this.employee = employee;
	}

	/**
	 * @return the accountBalance
	 */
	public Double getAccountBalance() {
		return this.accountBalance;
	}

	/**
	 * @param accountBalance
	 *            the accountBalance to set
	 */
	public void setAccountBalance(final Double accountBalance) {
		this.accountBalance = accountBalance;
	}

}
