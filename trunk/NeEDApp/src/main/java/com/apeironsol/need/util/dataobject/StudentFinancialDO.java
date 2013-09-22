package com.apeironsol.need.util.dataobject;

import java.io.Serializable;
import java.util.Collection;

import com.apeironsol.need.financial.model.StudentFee;
import com.apeironsol.need.util.constants.FeeClassificationLevelConstant;
import com.apeironsol.need.util.constants.FeePaymentStatusConstant;
import com.apeironsol.need.util.constants.FeeTypeConstant;
import com.apeironsol.need.util.constants.PaymentFrequencyConstant;

public class StudentFinancialDO implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long				serialVersionUID	= 7845926152018723395L;

	private FeeClassificationLevelConstant	feeClassificationLevel;

	private String							feeName;

	private PaymentFrequencyConstant		paymentFrequency;

	private StudentFee						studentFee;

	private FeeTypeConstant					feeType;

	private Double							fee;

	private Double							totalFeePaymentAmount;

	private Double							totalFeeDueAmount;

	private Double							totalFeeDeductedAmount;

	private Double							totalFeeRefundAmount;

	private Double							totalFeePaymentPendingAmount;

	private Double							totalFeeDeductionRequestAmount;

	private Double							totalFeeRefundRequestAmount;

	private Double							totalFeeRefundPendingAmount;

	private FeePaymentStatusConstant		status;

	private Collection<StudentFeeDetailsDO>	studentFeeDetailsDOs;

	public StudentFee getStudentFee() {
		return this.studentFee;
	}

	public void setStudentFee(final StudentFee studentFee) {
		this.studentFee = studentFee;
	}

	public FeeTypeConstant getFeeType() {
		return this.feeType;
	}

	public void setFeeType(final FeeTypeConstant feeType) {
		this.feeType = feeType;
	}

	public Double getFee() {
		return this.fee;
	}

	public void setFee(final Double fee) {
		this.fee = fee;
	}

	public Double getTotalFeePaymentAmount() {
		return this.totalFeePaymentAmount;
	}

	public void setTotalFeePaymentAmount(final Double feePaid) {
		this.totalFeePaymentAmount = feePaid;
	}

	public Double getTotalFeeDueAmount() {
		return this.totalFeeDueAmount;
	}

	public void setTotalFeeDueAmount(final Double feeDue) {
		this.totalFeeDueAmount = feeDue;
	}

	public Collection<StudentFeeDetailsDO> getStudentFeeDetailsDOs() {
		return this.studentFeeDetailsDOs;
	}

	public void setStudentFeeDetailsDOs(final Collection<StudentFeeDetailsDO> studentFeeDetailsDOs) {
		this.studentFeeDetailsDOs = studentFeeDetailsDOs;
	}

	public Double getTotalFeeDeductedAmount() {
		return this.totalFeeDeductedAmount;
	}

	public void setTotalFeeDeductedAmount(final Double feeDeducted) {
		this.totalFeeDeductedAmount = feeDeducted;
	}

	public Double getTotalFeeRefundAmount() {
		return this.totalFeeRefundAmount;
	}

	public void setTotalFeeRefundAmount(final Double feeRefund) {
		this.totalFeeRefundAmount = feeRefund;
	}

	public Double getNetFeePaid() {
		return this.totalFeePaymentAmount - this.totalFeeRefundAmount;
	}

	public Double getNetFee() {
		return this.fee - this.totalFeeDeductedAmount;
	}

	public Double getNetFeeDue() {
		return this.getNetFee() - this.getNetFeePaid();
	}

	/**
	 * @return the status
	 */
	public FeePaymentStatusConstant getStatus() {
		return this.status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(final FeePaymentStatusConstant status) {
		this.status = status;
	}

	public Double getTotalFeePaymentPendingAmount() {
		return this.totalFeePaymentPendingAmount;
	}

	public void setTotalFeePaymentPendingAmount(final Double totalFeePaymentPendingAmount) {
		this.totalFeePaymentPendingAmount = totalFeePaymentPendingAmount;
	}

	public Double getTotalFeeDeductionRequestAmount() {
		return this.totalFeeDeductionRequestAmount;
	}

	public void setTotalFeeDeductionRequestAmount(final Double totalFeeDeductionRequestAmount) {
		this.totalFeeDeductionRequestAmount = totalFeeDeductionRequestAmount;
	}

	public Double getTotalFeeRefundRequestAmount() {
		return this.totalFeeRefundRequestAmount;
	}

	public void setTotalFeeRefundRequestAmount(final Double totalFeeRefundRequestAmount) {
		this.totalFeeRefundRequestAmount = totalFeeRefundRequestAmount;
	}

	public Double getTotalFeeRefundPendingAmount() {
		return this.totalFeeRefundPendingAmount;
	}

	public void setTotalFeeRefundPendingAmount(final Double totalFeeRefundPendingAmount) {
		this.totalFeeRefundPendingAmount = totalFeeRefundPendingAmount;
	}

	public String getFeeName() {
		return this.feeName;
	}

	public void setFeeName(final String feeName) {
		this.feeName = feeName;
	}

	public PaymentFrequencyConstant getPaymentFrequency() {
		return this.paymentFrequency;
	}

	public void setPaymentFrequency(final PaymentFrequencyConstant paymentFrequency) {
		this.paymentFrequency = paymentFrequency;
	}

	public String getUniqueId() {
		return this.getStudentFee().getId().toString();
	}

	public FeeClassificationLevelConstant getFeeClassificationLevel() {
		return this.feeClassificationLevel;
	}

	public void setFeeClassificationLevel(final FeeClassificationLevelConstant feeClassificationLevel) {
		this.feeClassificationLevel = feeClassificationLevel;
	}

}
