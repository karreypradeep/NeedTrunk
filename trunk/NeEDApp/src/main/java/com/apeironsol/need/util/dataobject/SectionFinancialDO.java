/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 * 
 */
package com.apeironsol.need.util.dataobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import com.apeironsol.need.core.model.Section;

/**
 * Student financial data object for academic year.
 * 
 * @author Pradeep
 */
public class SectionFinancialDO implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long							serialVersionUID	= 7215495952078359216L;

	/**
	 * Name of branch.
	 */
	private String										name;

	/**
	 * Name of branch.
	 */
	private Section										section;

	/**
	 * Total fee expected for the section.
	 */
	private double										totalFeeExpected;

	/**
	 * Total fee collected for the section.
	 */
	private double										totalFeeCollectedAmount;

	/**
	 * Total fee due for the section.
	 */
	private double										totalFeeDueAmount;

	/**
	 * Total fee waived for the section.
	 */
	private double										totalFeeDeductedAmount;

	/**
	 * Total fee refunded for the section.
	 */
	private double										totalFeeRefundAmount;

	/**
	 * Total fee payment pending for the section.
	 */
	private double										totalFeePaymentPendingAmount;

	/**
	 * Total fee deduction requested for the section.
	 */
	private double										totalFeeDeductionRequestAmount;

	/**
	 * Total fee refund requested for the section.
	 */
	private double										totalFeeRefundRequestAmount;

	/**
	 * Total fee refund pending for the section.
	 */
	private double										totalFeeRefundPendingAmount;

	/**
	 * Collection of student financial data objects.
	 */
	private Collection<StudentFinancialAcademicYearDO>	studentFinancialAcademicYearDOs;

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
	 * @return the studentFinancialDOs
	 */
	public Collection<StudentFinancialAcademicYearDO> getStudentFinancialAcademicYearDOs() {
		return this.studentFinancialAcademicYearDOs;
	}

	/**
	 * @param studentFinancialDOs
	 *            the studentFinancialDOs to set
	 */
	public void setStudentFinancialAcademicYearDOs(final Collection<StudentFinancialAcademicYearDO> studentFinancialAcademicYearDOs) {
		this.studentFinancialAcademicYearDOs = studentFinancialAcademicYearDOs;
	}

	/**
	 * @param studentFinancialAcademicYearDO
	 *            the studentFinancialAcademicYearDO to add to collection.
	 */
	public void addStudentFinancialAcademicYearDO(final StudentFinancialAcademicYearDO studentFinancialAcademicYearDO) {
		if (this.studentFinancialAcademicYearDOs == null) {
			this.studentFinancialAcademicYearDOs = new ArrayList<StudentFinancialAcademicYearDO>();
		}
		this.studentFinancialAcademicYearDOs.add(studentFinancialAcademicYearDO);
	}

	/**
	 * Calculate section fees by iterating for entire students.
	 */
	public void calculateSectionFee(final boolean clearStudentFinancialDOsIndicator) {
		if ((this.getStudentFinancialAcademicYearDOs() != null) && !this.getStudentFinancialAcademicYearDOs().isEmpty()) {
			for (StudentFinancialAcademicYearDO studentFinancialAcademicYearDO : this.getStudentFinancialAcademicYearDOs()) {
				this.totalFeeExpected += studentFinancialAcademicYearDO.getFee();
				this.totalFeeCollectedAmount += studentFinancialAcademicYearDO.getTotalFeePaymentAmount();
				this.totalFeeDueAmount += studentFinancialAcademicYearDO.getTotalFeeDueAmount();
				this.totalFeeDeductedAmount += studentFinancialAcademicYearDO.getTotalFeeDeductedAmount();
				this.totalFeeRefundAmount += studentFinancialAcademicYearDO.getTotalFeeRefundAmount();
				this.totalFeePaymentPendingAmount += studentFinancialAcademicYearDO.getTotalFeePaymentPendingAmount();
				this.totalFeeDeductionRequestAmount += studentFinancialAcademicYearDO.getTotalFeeDeductionRequestAmount();
				this.totalFeeRefundRequestAmount += studentFinancialAcademicYearDO.getTotalFeeRefundRequestAmount();
				this.totalFeeRefundPendingAmount += studentFinancialAcademicYearDO.getTotalFeeRefundPendingAmount();
			}
		}
		if (clearStudentFinancialDOsIndicator) {
			this.clearStudentFinancialDOs();
		}
	}

	/**
	 * 
	 */
	private void clearStudentFinancialDOs() {
		if ((this.getStudentFinancialAcademicYearDOs() != null) && !this.getStudentFinancialAcademicYearDOs().isEmpty()) {
			for (StudentFinancialAcademicYearDO studentFinancialAcademicYearDO : this.getStudentFinancialAcademicYearDOs()) {
				if (studentFinancialAcademicYearDO.getStudentFinancialDOs() != null) {
					for (StudentFinancialDO studentFinancialDO : studentFinancialAcademicYearDO.getStudentFinancialDOs()) {
						// studentFinancialDO.getFee();
						// TODO Pradeep : Comment from Pradeep : why are you
						// iterating
						// and cleaning. you can directly set empty collecting
						// or
						// null.

						studentFinancialDO = null;
					}
				}
				studentFinancialAcademicYearDO = null;
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

	/**
	 * @return the section
	 */
	public Section getSection() {
		return this.section;
	}

	/**
	 * @param section the section to set
	 */
	public void setSection(final Section section) {
		this.section = section;
	}
}
