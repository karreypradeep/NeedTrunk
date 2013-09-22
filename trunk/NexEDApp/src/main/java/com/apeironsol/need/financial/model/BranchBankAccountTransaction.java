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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.apeironsol.need.util.constants.BankAccountTransactionTypeConstant;
import com.apeironsol.framework.BaseEntity;

/**
 * Entity class for academic year expenses.
 * 
 * @author Pradeep
 */
@Entity
@Table(name = "BRANCH_BANK_ACCOUNT_TXN")
public class BranchBankAccountTransaction extends BaseEntity implements Serializable {

	/**
	 * Universal serial version id for this class
	 */
	private static final long					serialVersionUID	= -7472693677756078576L;

	@NotNull(message = "model.transaction_end_date_mandatory")
	@Column(name = "TXN_DATE", nullable = false)
	private Date								transactionDate;

	@Column(name = "DESCRIPTION", length = 200, nullable = false)
	private String								description;

	@Column(name = "AMOUNT")
	private Double								amount;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BRANCH_BANK_ACCOUNT_ID", nullable = false)
	private BranchBankAccount					branchBankAccount;

	@NotNull(message = "model.transaction_mandatory")
	@Column(name = "TRANSACTION_TYPE", nullable = false)
	@Enumerated(EnumType.STRING)
	private BankAccountTransactionTypeConstant	transactionType;

	@NotNull(message = "model.transactionId_mandatory")
	@Column(name = "TRANSACTION_NR", nullable = false, unique = true)
	private String								transactionNr;

	@Column(name = "EXT_TRANSACTION_NR", unique = true)
	private String								externalTransactionNr;

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
	 * @return the transactionDate
	 */
	public Date getTransactionDate() {
		return this.transactionDate;
	}

	/**
	 * @param transactionDate
	 *            the transactionDate to set
	 */
	public void setTransactionDate(final Date transactionDate) {
		this.transactionDate = transactionDate;
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
	 * @return the transactionType
	 */
	public BankAccountTransactionTypeConstant getTransactionType() {
		return this.transactionType;
	}

	/**
	 * @param transactionType
	 *            the transactionType to set
	 */
	public void setTransactionType(final BankAccountTransactionTypeConstant transactionType) {
		this.transactionType = transactionType;
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
	 * @return the externalTransactionNr
	 */
	public String getExternalTransactionNr() {
		return this.externalTransactionNr;
	}

	/**
	 * @param externalTransactionNr
	 *            the externalTransactionNr to set
	 */
	public void setExternalTransactionNr(final String externalTransactionNr) {
		this.externalTransactionNr = externalTransactionNr;
	}

}
