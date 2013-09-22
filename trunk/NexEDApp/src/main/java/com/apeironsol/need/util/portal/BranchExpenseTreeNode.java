package com.apeironsol.need.util.portal;

import java.util.Date;

public class BranchExpenseTreeNode {

	private String	name;
	private double	expenseAmount;
	private Date	expenseDate;
	private Long	branchExpenseId;

	public BranchExpenseTreeNode(final String name, final double expenseAmount, final Date expenseDate) {
		super();
		this.name = name;
		this.expenseAmount = expenseAmount;
		this.expenseDate = expenseDate;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @param expenseAmount
	 *            the expenseAmount to set
	 */
	public void setExpenseAmount(final double expenseAmount) {
		this.expenseAmount = expenseAmount;
	}

	/**
	 * @param expenseDate
	 *            the expenseDate to set
	 */
	public void setExpenseDate(final Date expenseDate) {
		this.expenseDate = expenseDate;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return the expenseAmount
	 */
	public double getExpenseAmount() {
		return this.expenseAmount;
	}

	/**
	 * @return the expenseDate
	 */
	public Date getExpenseDate() {
		return this.expenseDate;
	}

	/**
	 * @return the branchExpenseId
	 */
	public Long getBranchExpenseId() {
		return this.branchExpenseId;
	}

	/**
	 * @param branchExpenseId
	 *            the branchExpenseId to set
	 */
	public void setBranchExpenseId(final Long branchExpenseId) {
		this.branchExpenseId = branchExpenseId;
	}

}
