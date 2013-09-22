package com.apeironsol.need.financial.model;

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
 * Entity class for class fee
 * 
 * @author Pradeep
 */

@Entity
@Table(name = "BRANCH_LEVEL_FEE_CATALOG")
public class BranchLevelFeeCatalog extends BaseEntity implements Serializable {

	/**
	 * Universal serial version number.
	 */
	private static final long	serialVersionUID	= 6889673453522054463L;

	@NotNull(message = "model.amount_mandatory")
	@Min(value = 0, message = "model.amount_cannot_be_lessthen_zero")
	@Column(name = "AMOUNT", nullable = false)
	private Double				amount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCH_LEVEL_FEE_ID", nullable = false)
	private BranchLevelFee		branchLevelFee;

	@NotNull(message = "model.due_date_mandatory")
	@Column(name = "DUE_DATE", nullable = false)
	private Date				dueDate;

	/**
	 * @return the dueDate
	 */
	public Date getDueDate() {
		return this.dueDate;
	}

	/**
	 * @param dueDate
	 *            the dueDate to set
	 */
	public void setDueDate(final Date dueDate) {
		this.dueDate = dueDate;
	}

	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(final Double amount) {
		this.amount = amount;
	}

	public BranchLevelFee getBranchLevelFee() {
		return this.branchLevelFee;
	}

	public void setBranchLevelFee(final BranchLevelFee branchLevelFee) {
		this.branchLevelFee = branchLevelFee;
	}

}
