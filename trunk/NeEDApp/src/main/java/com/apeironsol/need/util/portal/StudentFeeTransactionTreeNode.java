package com.apeironsol.need.util.portal;

import java.util.Date;

public class StudentFeeTransactionTreeNode {

	private String	transactionNr;
	private String	externalTransactionNr;
	private Double	amount;
	private Date	transactionDate;
	private Date	externalTransactionDate;
	private String	name;
	private String	userName;
	private String	admissionNumber;

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
	 * @return the externalTransactionDate
	 */
	public Date getExternalTransactionDate() {
		return this.externalTransactionDate;
	}

	/**
	 * @param externalTransactionDate
	 *            the externalTransactionDate to set
	 */
	public void setExternalTransactionDate(final Date externalTransactionDate) {
		this.externalTransactionDate = externalTransactionDate;
	}

	/**
	 * @return the admissionNumber
	 */
	public String getAdmissionNumber() {
		return this.admissionNumber;
	}

	/**
	 * @param admissionNumber
	 *            the admissionNumber to set
	 */
	public void setAdmissionNumber(final String admissionNumber) {
		this.admissionNumber = admissionNumber;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return this.userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(final String userName) {
		this.userName = userName;
	}

}
