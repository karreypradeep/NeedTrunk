/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 * 
 */
package com.apeironsol.nexed.util.dataobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Student financial data object for academic year.
 * 
 * @author Pradeep
 */
public class BranchFinancialDO implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long				serialVersionUID	= 7515510026908052698L;

	/**
	 * Name of branch.
	 */
	private String							name;

	/**
	 * Total fee expected for the section.
	 */
	private double							totalFeeExpected;

	/**
	 * Total fee collected for the section.
	 */
	private double							totalFeeCollectedAmount;

	/**
	 * Total fee due for the section.
	 */
	private double							totalFeeDueAmount;

	/**
	 * Total fee waived for the section.
	 */
	private double							totalFeeDeductedAmount;

	/**
	 * Total fee refunded for the section.
	 */
	private double							totalFeeRefundAmount;

	/**
	 * Total fee payment pending for the section.
	 */
	private double							totalFeePaymentPendingAmount;

	/**
	 * Total fee deduction requested for the section.
	 */
	private double							totalFeeDeductionRequestAmount;

	/**
	 * Total fee refund requested for the section.
	 */
	private double							totalFeeRefundRequestAmount;

	/**
	 * Total fee refund pending for the section.
	 */
	private double							totalFeeRefundPendingAmount;

	/**
	 * Collection of student financial data objects.
	 */
	private Collection<ClassFinancialDO>	classFinancialDOs	= new ArrayList<ClassFinancialDO>();

	public double getTotalFeeExpected() {
		return this.totalFeeExpected;
	}

	public void setTotalFeeExpected(final double totalFeeExpected) {
		this.totalFeeExpected = totalFeeExpected;
	}

	public double getTotalFeeCollectedAmount() {
		return this.totalFeeCollectedAmount;
	}

	public void setTotalFeeCollectedAmount(final double feePaid) {
		this.totalFeeCollectedAmount = feePaid;
	}

	public double getTotalFeeDueAmount() {
		return this.totalFeeDueAmount;
	}

	public void setTotalFeeDueAmount(final double feeDue) {
		this.totalFeeDueAmount = feeDue;
	}

	public double getTotalFeeDeductedAmount() {
		return this.totalFeeDeductedAmount;
	}

	public void setTotalFeeDeductedAmount(final double feeDeducted) {
		this.totalFeeDeductedAmount = feeDeducted;
	}

	public double getTotalFeeRefundAmount() {
		return this.totalFeeRefundAmount;
	}

	public void setTotalFeeRefundAmount(final double feeRefund) {
		this.totalFeeRefundAmount = feeRefund;
	}

	public double getNetFeePaid() {
		return this.totalFeeCollectedAmount - this.totalFeeRefundAmount;
	}

	public double getNetFee() {
		return this.totalFeeExpected - this.totalFeeDeductedAmount;
	}

	public double getNetFeeDue() {
		return this.getNetFee() - this.getNetFeePaid();
	}

	public double getTotalFeePaymentPendingAmount() {
		return this.totalFeePaymentPendingAmount;
	}

	public void setTotalFeePaymentPendingAmount(final double totalFeePaymentPendingAmount) {
		this.totalFeePaymentPendingAmount = totalFeePaymentPendingAmount;
	}

	public double getTotalFeeDeductionRequestAmount() {
		return this.totalFeeDeductionRequestAmount;
	}

	public void setTotalFeeDeductionRequestAmount(final double totalFeeDeductionRequestAmount) {
		this.totalFeeDeductionRequestAmount = totalFeeDeductionRequestAmount;
	}

	public double getTotalFeeRefundRequestAmount() {
		return this.totalFeeRefundRequestAmount;
	}

	public void setTotalFeeRefundRequestAmount(final double totalFeeRefundRequestAmount) {
		this.totalFeeRefundRequestAmount = totalFeeRefundRequestAmount;
	}

	public double getTotalFeeRefundPendingAmount() {
		return this.totalFeeRefundPendingAmount;
	}

	public void setTotalFeeRefundPendingAmount(final double totalFeeRefundPendingAmount) {
		this.totalFeeRefundPendingAmount = totalFeeRefundPendingAmount;
	}

	/**
	 * @return the sectionFinancialDOs
	 */
	public Collection<ClassFinancialDO> getClassFinancialDOs() {
		return this.classFinancialDOs;
	}

	/**
	 * @param sectionFinancialDOs
	 *            the sectionFinancialDOs to set
	 */
	public void setClassFinancialDOs(final Collection<ClassFinancialDO> sectionFinancialDOs) {
		this.classFinancialDOs = sectionFinancialDOs;
	}

	/**
	 * @param sectionFinancialDO
	 *            the sectionFinancialDO to add to collection.
	 */
	public void addClassFinancialDO(final ClassFinancialDO sectionFinancialDO) {
		if (this.classFinancialDOs == null) {
			this.classFinancialDOs = new ArrayList<ClassFinancialDO>();
		}
		this.classFinancialDOs.add(sectionFinancialDO);
	}

	public void calculateBranchFee() {
		if (this.getClassFinancialDOs() != null && !this.getClassFinancialDOs().isEmpty()) {
			for (ClassFinancialDO sectionFinancialDO : this.getClassFinancialDOs()) {
				this.totalFeeExpected += sectionFinancialDO.getTotalFeeExpected();
				this.totalFeeCollectedAmount += sectionFinancialDO.getTotalFeeCollectedAmount();
				this.totalFeeDueAmount += sectionFinancialDO.getTotalFeeDueAmount();
				this.totalFeeDeductedAmount += sectionFinancialDO.getTotalFeeDeductedAmount();
				this.totalFeeRefundAmount += sectionFinancialDO.getTotalFeeRefundAmount();
				this.totalFeePaymentPendingAmount += sectionFinancialDO.getTotalFeePaymentPendingAmount();
				this.totalFeeDeductionRequestAmount += sectionFinancialDO.getTotalFeeDeductionRequestAmount();
				this.totalFeeRefundRequestAmount += sectionFinancialDO.getTotalFeeRefundRequestAmount();
				this.totalFeeRefundPendingAmount += sectionFinancialDO.getTotalFeeRefundPendingAmount();
			}
		}
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

}
