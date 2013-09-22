/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.apeironsol.framework.BaseEntity;

/**
 * Entity class for branch department.
 * 
 * @author Pradeep
 */
@Entity
@Table(name = "BRANCH_EXPENSE_TYPE_PERIODICAL")
public class BranchExpenseTypePeriodical extends BaseEntity implements Serializable {

	/**
	 * Universal serial version number.
	 */
	private static final long	serialVersionUID	= -6920288432513328077L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCH_ASSEMBLE_ID", nullable = false)
	private BranchAssembly		branchAssembly;

	@NotNull(message = "model.start_date_mandatory")
	@Column(name = "START_DATE", nullable = false)
	private Date				startDate;

	@Column(name = "END_DATE")
	private Date				endDate;

	@Min(value = 0, message = "model.expense_cannot_be_lessthen_zero")
	@NotNull(message = "model.maximum_expense_mandatory")
	@Column(name = "MAXIMUM_EXPENSE", nullable = false)
	private Double				maximumExpense;

	public Double getMaximumExpense() {
		return this.maximumExpense;
	}

	public void setMaximumExpense(final Double maximumExpense) {
		this.maximumExpense = maximumExpense;
	}

	public BranchAssembly getBranchAssembly() {
		return this.branchAssembly;
	}

	public void setBranchAssembly(final BranchAssembly branchAssembly) {
		this.branchAssembly = branchAssembly;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public void validate() {

	}

}
