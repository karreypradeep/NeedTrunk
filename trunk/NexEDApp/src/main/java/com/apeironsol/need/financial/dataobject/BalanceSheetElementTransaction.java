package com.apeironsol.need.financial.dataobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author pradeep
 * 
 */
public class BalanceSheetElementTransaction implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long	serialVersionUID	= -6680010256378549019L;

	private Date				transactionDate;

	private double				transactionAmount;

	private String				description;

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
	 * @return the transactionAmount
	 */
	public double getTransactionAmount() {
		return this.transactionAmount;
	}

	/**
	 * @param transactionAmount
	 *            the transactionAmount to set
	 */
	public void setTransactionAmount(final double transactionAmount) {
		this.transactionAmount = transactionAmount;
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

}
