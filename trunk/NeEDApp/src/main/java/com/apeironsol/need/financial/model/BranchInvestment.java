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
import javax.validation.constraints.NotNull;

import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.core.model.BuildingBlock;
import com.apeironsol.framework.BaseEntity;

/**
 * Entity class for academic year expenses.
 * 
 * @author Pradeep
 */
@Entity
@Table(name = "BRANCH_INVESTMENT")
public class BranchInvestment extends BaseEntity implements Serializable {

	/**
	 * Universal serial version id for this class
	 */
	private static final long	serialVersionUID	= 2776762130606974323L;

	@Column(name = "NAME", length = 50, nullable = false)
	private String				name;

	@NotNull(message = "model.expense_end_date_mandatory")
	@Column(name = "INVESTMENT_DATE", nullable = false)
	private Date				investmentDate;

	@Column(name = "DESCRIPTION", length = 200)
	private String				description;

	@Column(name = "AMOUNT")
	private Double				amount;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "INVESTMENT_BUILD_BLK_ID", nullable = false)
	private BuildingBlock		investmentBuildingBlock;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCH_ID", nullable = false)
	private Branch				branch;

	@Column(name = "TRANSACTION_NR", length = 50, nullable = false)
	private String					transactionNr;

	/**
	 * @return the transactionNr
	 */
	public String getTransactionNr() {
		return transactionNr;
	}

	/**
	 * @param transactionNr the transactionNr to set
	 */
	public void setTransactionNr(final String transactionNr) {
		this.transactionNr = transactionNr;
	}

	/**
	 * @return the branch
	 */
	public Branch getBranch() {
		return branch;
	}

	/**
	 * @param branch
	 *            the branch to set
	 */
	public void setBranch(final Branch branch) {
		this.branch = branch;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
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
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(final Double amount) {
		this.amount = amount;
	}

	/**
	 * @return the investmentBuildingBlock
	 */
	public BuildingBlock getInvestmentBuildingBlock() {
		return investmentBuildingBlock;
	}

	/**
	 * @param investmentBuildingBlock
	 *            the investmentBuildingBlock to set
	 */
	public void setInvestmentBuildingBlock(final BuildingBlock investmentBuildingBlock) {
		this.investmentBuildingBlock = investmentBuildingBlock;
	}

	/**
	 * @return the investmentDate
	 */
	public Date getInvestmentDate() {
		return investmentDate;
	}

	/**
	 * @param investmentDate
	 *            the investmentDate to set
	 */
	public void setInvestmentDate(final Date investmentDate) {
		this.investmentDate = investmentDate;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

}
