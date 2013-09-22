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

import com.apeironsol.need.core.model.Student;
import com.apeironsol.need.core.model.StudentAcademicYearFeeSummary;
import com.apeironsol.need.core.model.StudentSection;

/**
 * Student financial data object for academic year.
 * 
 * @author Pradeep
 */
public class StudentFinancialAcademicYearDO implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long				serialVersionUID	= 5544306216046576694L;

	/**
	 * Name of branch.
	 */
	private StudentSection					studentSection;

	private double							fee;

	private double							totalFeePaymentAmount;

	private double							totalFeeDueAmount;

	private double							totalFeeDeductedAmount;

	private double							totalFeeRefundAmount;

	private double							totalFeePaymentPendingAmount;

	private double							totalFeeDeductionRequestAmount;

	private double							totalFeeRefundRequestAmount;

	private double							totalFeeRefundPendingAmount;

	private String							uniqueId;

	private StudentAcademicYearFeeSummary	studentAcademicYearFeeSummary;

	/**
	 * Collection of student financial data objects.
	 */
	private Collection<StudentFinancialDO>	studentFinancialDOs;

	public double getFee() {
		return this.fee;
	}

	public void setFee(final double fee) {
		this.fee = fee;
	}

	public double getTotalFeePaymentAmount() {
		return this.totalFeePaymentAmount;
	}

	public void setTotalFeePaymentAmount(final double feePaid) {
		this.totalFeePaymentAmount = feePaid;
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
		return this.totalFeePaymentAmount - this.totalFeeRefundAmount;
	}

	public double getNetFee() {
		return this.fee - this.totalFeeDeductedAmount;
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
	public Collection<StudentFinancialDO> getStudentFinancialDOs() {
		return this.studentFinancialDOs;
	}

	/**
	 * @param studentFinancialDOs
	 *            the studentFinancialDOs to set
	 */
	public void setStudentFinancialDOs(final Collection<StudentFinancialDO> studentFinancialDOs) {
		this.studentFinancialDOs = studentFinancialDOs;
	}

	/**
	 * @param studentFinancialDO
	 *            the studentFinancialDO to add to collection.
	 */
	public void addStudentFinancialDO(final StudentFinancialDO studentFinancialDO) {
		if (this.studentFinancialDOs == null) {
			this.studentFinancialDOs = new ArrayList<StudentFinancialDO>();
		}
		this.studentFinancialDOs.add(studentFinancialDO);
	}

	/**
	 * Calculate student fee for academic year.
	 */
	public void calculateStudentFeeForAcademicYear() {
		if (this.studentAcademicYearFeeSummary != null) {
			this.fee = this.studentAcademicYearFeeSummary.getTotalFeePayable();
			this.totalFeePaymentAmount = this.studentAcademicYearFeeSummary.getTotalFeePaid();
			this.totalFeeDueAmount = this.fee - this.totalFeePaymentAmount;
			this.totalFeeDeductedAmount = this.studentAcademicYearFeeSummary.getTotalFeeWaived();
			this.totalFeeRefundAmount = this.studentAcademicYearFeeSummary.getTotalFeeRefunded();
			this.totalFeePaymentPendingAmount = this.studentAcademicYearFeeSummary.getTotalFeePaymentPendingAmount();
			this.totalFeeDeductionRequestAmount = this.studentAcademicYearFeeSummary.getTotalFeeWaivedRequestAmount();
			this.totalFeeRefundRequestAmount = this.studentAcademicYearFeeSummary.getTotalFeeRefundRequestAmount();
			this.totalFeeRefundPendingAmount = this.studentAcademicYearFeeSummary.getTotalFeeRefundPendingAmount();
		} else if (this.getStudentFinancialDOs() != null && !this.getStudentFinancialDOs().isEmpty()) {
			for (StudentFinancialDO studentFinancialDO : this.getStudentFinancialDOs()) {
				this.fee += studentFinancialDO.getFee();
				this.totalFeePaymentAmount += studentFinancialDO.getTotalFeePaymentAmount();
				this.totalFeeDueAmount += studentFinancialDO.getTotalFeeDueAmount();
				this.totalFeeDeductedAmount += studentFinancialDO.getTotalFeeDeductedAmount();
				this.totalFeeRefundAmount += studentFinancialDO.getTotalFeeRefundAmount();
				this.totalFeePaymentPendingAmount += studentFinancialDO.getTotalFeePaymentPendingAmount();
				this.totalFeeDeductionRequestAmount += studentFinancialDO.getTotalFeeDeductionRequestAmount();
				this.totalFeeRefundRequestAmount += studentFinancialDO.getTotalFeeRefundRequestAmount();
				this.totalFeeRefundPendingAmount += studentFinancialDO.getTotalFeeRefundPendingAmount();
			}
		}
	}

	/**
	 * @return the studentSection
	 */
	public StudentSection getStudentSection() {
		return this.studentSection;
	}

	/**
	 * @param studentSection
	 *            the studentSection to set
	 */
	public void setStudentSection(final StudentSection studentSection) {
		this.studentSection = studentSection;
	}

	public Student getStudent() {
		return this.studentSection != null ? this.studentSection.getStudentAcademicYear().getStudent() : null;
	}

	/**
	 * @return the uniqueId
	 */
	public String getUniqueId() {
		if (this.uniqueId == null) {
			this.uniqueId = this.studentSection.getId() + "";
		}
		return this.uniqueId;
	}

	/**
	 * @param uniqueId
	 *            the uniqueId to set
	 */
	public void setUniqueId(final String uniqueId) {
		this.uniqueId = uniqueId;
	}

	/**
	 * @return the studentAcademicYearFeeSummary
	 */
	public StudentAcademicYearFeeSummary getStudentAcademicYearFeeSummary() {
		return this.studentAcademicYearFeeSummary;
	}

	/**
	 * @param studentAcademicYearFeeSummary
	 *            the studentAcademicYearFeeSummary to set
	 */
	public void setStudentAcademicYearFeeSummary(final StudentAcademicYearFeeSummary studentAcademicYearFeeSummary) {
		this.studentAcademicYearFeeSummary = studentAcademicYearFeeSummary;
	}

}
