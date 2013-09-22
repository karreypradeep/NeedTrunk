package com.apeironsol.need.util.portal;

import java.util.Date;

public class BranchIncomeTreeNode {

	private String	name;
	private double	incomeAmount;
	private Date	incomeDate;
	private Long	branchIcomeId;

	public BranchIncomeTreeNode(final String name, final double incomeAmount, final Date incomeDate) {
		super();
		this.name = name;
		this.incomeAmount = incomeAmount;
		this.incomeDate = incomeDate;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @param incomeAmount
	 *            the incomeAmount to set
	 */
	public void setIncomeAmount(final double incomeAmount) {
		this.incomeAmount = incomeAmount;
	}

	/**
	 * @param incomeDate
	 *            the incomeDate to set
	 */
	public void setIncomeDate(final Date incomeDate) {
		this.incomeDate = incomeDate;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return the incomeAmount
	 */
	public double getIncomeAmount() {
		return this.incomeAmount;
	}

	/**
	 * @return the incomeDate
	 */
	public Date getIncomeDate() {
		return this.incomeDate;
	}

	/**
	 * @return the branchIcomeId
	 */
	public Long getBranchIcomeId() {
		return this.branchIcomeId;
	}

	/**
	 * @param branchIcomeId
	 *            the branchIcomeId to set
	 */
	public void setBranchIcomeId(final Long branchIcomeId) {
		this.branchIcomeId = branchIcomeId;
	}

}
