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
import javax.validation.constraints.NotNull;

import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.core.model.BuildingBlock;
import com.apeironsol.need.util.constants.BranchAccountTypeConstant;
import com.apeironsol.need.util.constants.PaymentMethodConstant;
import com.apeironsol.framework.BaseEntity;

/**
 * Entity class for academic year expenses.
 * 
 * @author Pradeep
 */
@Entity
@Table(name = "BRANCH_EXPENSE")
public class BranchExpense extends BaseEntity implements Serializable {

	/**
	 * Universal serial version id for this class
	 */
	private static final long			serialVersionUID	= 1789235692992278161L;

	@Column(name = "NAME", length = 50, nullable = false)
	private String						name;

	@NotNull(message = "model.expense_end_date_mandatory")
	@Column(name = "EXPENSE_DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date						expenseDate;

	@Column(name = "DESCRIPTION", length = 200, nullable = false)
	private String						description;

	@Column(name = "TRANSACTION_NR", length = 50, nullable = false)
	private String						transactionNr;

	@Column(name = "AMOUNT")
	private Double						amount;

	@Column(name = "ACCOUNT_TYPE")
	@Enumerated(EnumType.STRING)
	private BranchAccountTypeConstant	branchAccountType;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "EXPENSE_TYPE_BUILD_BLK_ID", nullable = false)
	private BuildingBlock				expenseBuildingBlock;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCH_ID", nullable = false)
	private Branch						branch;

	@Basic
	@Column(name = "PAYMENT_METHOD")
	@Enumerated(EnumType.STRING)
	private PaymentMethodConstant		paymentMethod;

	@Column(name = "CHEQUE_NR")
	private String						chequeNumber;

	@ManyToOne
	@JoinColumn(name = "BRANCH_BANK_ACCOUNT_ID")
	private BranchBankAccount			branchBankAccount;

	@ManyToOne
	@JoinColumn(name = "BRANCH_CREDIT_ACCOUNT_ID")
	private BranchCreditAccount			branchCreditAccount;

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
	 * @return the expenseBuildingBlock
	 */
	public BuildingBlock getExpenseBuildingBlock() {
		return this.expenseBuildingBlock;
	}

	/**
	 * @param expenseBuildingBlock
	 *            the expenseType to set
	 */
	public void setExpenseBuildingBlock(final BuildingBlock expenseBuildingBlock) {
		this.expenseBuildingBlock = expenseBuildingBlock;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 * @return the expenseDate
	 */
	public Date getExpenseDate() {
		return this.expenseDate;
	}

	/**
	 * @param expenseDate
	 *            the expenseDate to set
	 */
	public void setExpenseDate(final Date expenseDate) {
		this.expenseDate = expenseDate;
	}

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
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		this.name = name;
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
