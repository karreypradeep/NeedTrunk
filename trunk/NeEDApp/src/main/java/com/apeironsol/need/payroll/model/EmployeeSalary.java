/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.payroll.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.apeironsol.need.financial.model.BranchBankAccount;
import com.apeironsol.need.financial.model.BranchCreditAccount;
import com.apeironsol.need.hrms.model.Employee;
import com.apeironsol.need.util.constants.BranchAccountTypeConstant;
import com.apeironsol.need.util.constants.PaymentMethodConstant;
import com.apeironsol.need.util.constants.SalaryTypeConstant;
import com.apeironsol.framework.BaseEntity;

/**
 * Entity class for EmployeeSalary
 * 
 * @author Sunny
 * 
 */
@Entity
@Table(name = "EMPLOYEE_SALARY")
public class EmployeeSalary extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long			serialVersionUID	= -1149966776657528263L;

	@NotNull(message = "model.salary_month_mandatory")
	@Column(name = "SALARY_MONTH", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date						salaryMonth;

	@NotNull(message = "model.salary_month_mandatory")
	@Column(name = "PAID_DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date						paidDate;

	@NotNull(message = "model.amount_mandatory")
	@Min(value = 0, message = "model.amount_cannot_be_lessthen_zero")
	@Column(name = "AMOUNT", nullable = false)
	private Double						amount;

	@NotNull(message = "model.employee_id_mandatory")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "EMPLOYEE_ID", nullable = false)
	private Employee					employee;

	@Enumerated(EnumType.STRING)
	@Column(name = "SALARY_TYPE", length = 20)
	private SalaryTypeConstant			salaryType;

	@NotNull(message = "model.transactionId_mandatory")
	@Column(name = "TRANSACTION_NR", nullable = false)
	private String						transactionNr;

	@Basic
	@Column(name = "PAYMENT_METHOD")
	@Enumerated(EnumType.STRING)
	private PaymentMethodConstant		paymentMethod;

	@Column(name = "CHEQUE_NR")
	private String						chequeNumber;

	@ManyToOne
	@JoinColumn(name = "BRANCH_BANK_ACCOUNT_ID")
	private BranchBankAccount			branchBankAccount;

	@Column(name = "ACCOUNT_TYPE")
	@Enumerated(EnumType.STRING)
	private BranchAccountTypeConstant	branchAccountType;

	@ManyToOne
	@JoinColumn(name = "BRANCH_CREDIT_ACCOUNT_ID")
	private BranchCreditAccount			branchCreditAccount;

	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return this.amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(final Double amount) {
		this.amount = amount;
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
	 * @return the salaryMonth
	 */
	public Date getSalaryMonth() {
		return this.salaryMonth;
	}

	/**
	 * @param salaryMonth
	 *            the salaryMonth to set
	 */
	public void setSalaryMonth(final Date salaryMonth) {
		this.salaryMonth = salaryMonth;
	}

	/**
	 * @return the paidDate
	 */
	public Date getPaidDate() {
		return this.paidDate;
	}

	/**
	 * @param paidDate
	 *            the paidDate to set
	 */
	public void setPaidDate(final Date paidDate) {
		this.paidDate = paidDate;
	}

	/**
	 * @return the transactionNr
	 */
	public String getTransactionNr() {
		return this.transactionNr;
	}

	/**
	 * @param transactionNr
	 *            the transactionNr to set
	 */
	public void setTransactionNr(final String transactionNr) {
		this.transactionNr = transactionNr;
	}

	/**
	 * @return the salaryType
	 */
	public SalaryTypeConstant getSalaryType() {
		return this.salaryType;
	}

	/**
	 * @param salaryType
	 *            the salaryType to set
	 */
	public void setSalaryType(final SalaryTypeConstant salaryType) {
		this.salaryType = salaryType;
	}

	/**
	 * @return the paymentMethod
	 */
	public PaymentMethodConstant getPaymentMethod() {
		return this.paymentMethod;
	}

	/**
	 * @param paymentMethod
	 *            the paymentMethod to set
	 */
	public void setPaymentMethod(final PaymentMethodConstant paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * @return the chequeNumber
	 */
	public String getChequeNumber() {
		return this.chequeNumber;
	}

	/**
	 * @param chequeNumber
	 *            the chequeNumber to set
	 */
	public void setChequeNumber(final String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}

	/**
	 * @return the branchBankAccount
	 */
	public BranchBankAccount getBranchBankAccount() {
		return this.branchBankAccount;
	}

	/**
	 * @param branchBankAccount
	 *            the branchBankAccount to set
	 */
	public void setBranchBankAccount(final BranchBankAccount branchBankAccount) {
		this.branchBankAccount = branchBankAccount;
	}

	/**
	 * @return the branchAccountType
	 */
	public BranchAccountTypeConstant getBranchAccountType() {
		return this.branchAccountType;
	}

	/**
	 * @param branchAccountType
	 *            the branchAccountType to set
	 */
	public void setBranchAccountType(final BranchAccountTypeConstant branchAccountType) {
		this.branchAccountType = branchAccountType;
	}

	/**
	 * @return the branchCreditAccount
	 */
	public BranchCreditAccount getBranchCreditAccount() {
		return this.branchCreditAccount;
	}

	/**
	 * @param branchCreditAccount
	 *            the branchCreditAccount to set
	 */
	public void setBranchCreditAccount(final BranchCreditAccount branchCreditAccount) {
		this.branchCreditAccount = branchCreditAccount;
	}

}
