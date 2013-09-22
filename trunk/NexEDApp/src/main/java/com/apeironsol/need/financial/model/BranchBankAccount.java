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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.apeironsol.need.core.model.Address;
import com.apeironsol.need.core.model.Branch;
import com.apeironsol.framework.BaseEntity;

/**
 * Entity class for academic year expenses.
 * 
 * @author Pradeep
 */
@Entity
@Table(name = "BRANCH_BANK_ACCOUNT", uniqueConstraints = { @UniqueConstraint(columnNames = { "ACCOUNT_NUMBER", "BRANCH_ID" }) })
public class BranchBankAccount extends BaseEntity implements Serializable {

	/**
	 * Universal serial version id for this class
	 */
	private static final long	serialVersionUID	= 7323490909247862470L;

	@NotNull(message = "model.bank_account_name_mandatory")
	@Column(name = "ACCOUNT_NUMBER", length = 50, nullable = false)
	private String				accountNumber;

	@NotNull(message = "model.expense_end_date_mandatory")
	@Column(name = "ACCOUNT_OPENING_DATE", nullable = false)
	private Date				accountOpeningDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCH_ID", nullable = false)
	private Branch				branch;

	@NotNull(message = "model.bank_name_mandatory")
	@Column(name = "BANK_NAME", length = 50, nullable = false)
	private String				bankName;

	@NotNull(message = "model.bank_branch")
	@Column(name = "BANK_BRANCH", length = 50, nullable = false)
	private String				bankBranch;

	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "BANK_BRANCH_ADDRESS_ID", nullable = false)
	private Address				bankBranchAddress;

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
	 * @return the bankName
	 */
	public String getBankName() {
		return this.bankName;
	}

	/**
	 * @param bankName
	 *            the bankName to set
	 */
	public void setBankName(final String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return the bankBranch
	 */
	public String getBankBranch() {
		return this.bankBranch;
	}

	/**
	 * @param bankBranch
	 *            the bankBranch to set
	 */
	public void setBankBranch(final String bankBranch) {
		this.bankBranch = bankBranch;
	}

	/**
	 * @return the bankBranchAddress
	 */
	public Address getBankBranchAddress() {
		return this.bankBranchAddress;
	}

	/**
	 * @param bankBranchAddress
	 *            the bankBranchAddress to set
	 */
	public void setBankBranchAddress(final Address bankBranchAddress) {
		this.bankBranchAddress = bankBranchAddress;
	}

}
