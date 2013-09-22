package com.apeironsol.need.util.portal;

import java.util.Date;

public class BranchLiabilityTreeNode {

	private String	name;
	private double	totalAmount;
	private Date	date;
	private Long	branchLiabilityId;

	public BranchLiabilityTreeNode(final String name, final double totalAmount, final Date date) {
		super();
		this.name = name;
		this.totalAmount = totalAmount;
		this.date = date;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @param assertAmount
	 *            the assertAmount to set
	 */
	public void setTotalAmount(final double totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @param expenseDate
	 *            the expenseDate to set
	 */
	public void setDate(final Date date) {
		this.date = date;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return the assertAmount
	 */
	public double getTotalAmount() {
		return this.totalAmount;
	}

	/**
	 * @return the expenseDate
	 */
	public Date getDate() {
		return this.date;
	}

	/**
	 * @return the branchAssertId
	 */
	public Long getBranchLiabilityId() {
		return this.branchLiabilityId;
	}

	/**
	 * @param branchAssertId
	 *            the branchAssertId to set
	 */
	public void setBranchLiabilityId(final Long branchLiabilityId) {
		this.branchLiabilityId = branchLiabilityId;
	}

}
