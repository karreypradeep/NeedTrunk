package com.apeironsol.need.util.portal;

import java.util.Date;

public class BranchBalanceSheetTreeNode {

	private String	description;
	private Date	date;
	private double	creditAmount;
	private double	debitAmount;

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
	 * @return the date
	 */
	public Date getDate() {
		return this.date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(final Date date) {
		this.date = date;
	}

	public double getCreditAmount() {
		return this.creditAmount;
	}

	public void setCreditAmount(final double creditAmount) {
		this.creditAmount = creditAmount;
	}

	/**
	 * @return the debitAmount
	 */
	public double getDebitAmount() {
		return this.debitAmount;
	}

	/**
	 * @param debitAmount
	 *            the debitAmount to set
	 */
	public void setDebitAmount(final double debitAmount) {
		this.debitAmount = debitAmount;
	}

}
