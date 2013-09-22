package com.apeironsol.need.util.portal;

import java.util.Date;

public class BranchSalaryTreeNode {

	private String	name;
	private double	amount;
	private Date	date;
	private Long	salaryId;
	private String	salaryType;
	private Date	salaryMonth;

	public BranchSalaryTreeNode(final String name) {
		setName(name);
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
	 * @return the amount
	 */
	public double getAmount() {
		return this.amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(final double amount) {
		this.amount = amount;
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

	/**
	 * @return the salaryId
	 */
	public Long getSalaryId() {
		return this.salaryId;
	}

	/**
	 * @param salaryId
	 *            the salaryId to set
	 */
	public void setSalaryId(final Long salaryId) {
		this.salaryId = salaryId;
	}

	/**
	 * @return the salaryType
	 */
	public String getSalaryType() {
		return this.salaryType;
	}

	/**
	 * @param salaryType
	 *            the salaryType to set
	 */
	public void setSalaryType(final String salaryType) {
		this.salaryType = salaryType;
	}

	/**
	 * @return the salaryMonth
	 */
	public Date getSalaryMonth() {
		return this.salaryMonth;
	}

	/**
	 * @param salaryMonth
	 *            the salaryMonth to set
	 */
	public void setSalaryMonth(final Date salaryMonth) {
		this.salaryMonth = salaryMonth;
	}

}
