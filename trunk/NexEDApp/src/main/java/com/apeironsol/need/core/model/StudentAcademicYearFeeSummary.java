package com.apeironsol.need.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.apeironsol.framework.BaseEntity;

@Entity
@Table(name = "STUDENT_ACADEMIC_YEAR_FEE_SUMMARY")
public class StudentAcademicYearFeeSummary extends BaseEntity {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long	serialVersionUID	= 8940404312945273613L;

	@NotNull(message = "model.student_academic_year_mandatory")
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STUDENT_ACADEMIC_YEAR_ID", nullable = false)
	private StudentAcademicYear	studentAcademicYear;

	@Column(name = "TOTAL_FEE_PAYABLE")
	private Double				totalFeePayable;

	@Column(name = "TOTAL_FEE_WAIVED")
	private Double				totalFeeWaived;

	@Column(name = "TOTAL_FEE_REFUNDED")
	private Double				totalFeeRefunded;

	@Column(name = "TOTAL_FEE_PAID")
	private Double				totalFeePaid;

	@Column(name = "TOTAL_FEE_WAIVED_REQUESTED")
	private Double				totalFeeWaivedRequestAmount;

	@Column(name = "TOTAL_FEE_REFUND_REQUESTED")
	private Double				totalFeeRefundRequestAmount;

	@Column(name = "TOTAL_FEE_REFUND_PENDING")
	private Double				totalFeeRefundPendingAmount;

	@Column(name = "TOTAL_FEE_PAYMENT_PENDING")
	private Double				totalFeePaymentPendingAmount;

	/**
	 * @return the studentAcademicYear
	 */
	public StudentAcademicYear getStudentAcademicYear() {
		return this.studentAcademicYear;
	}

	/**
	 * @param studentAcademicYear
	 *            the studentAcademicYear to set
	 */
	public void setStudentAcademicYear(final StudentAcademicYear studentAcademicYear) {
		this.studentAcademicYear = studentAcademicYear;
	}

	/**
	 * @return the totalFeePayable
	 */
	public Double getTotalFeePayable() {
		return this.totalFeePayable;
	}

	/**
	 * @param totalFeePayable
	 *            the totalFeePayable to set
	 */
	public void setTotalFeePayable(final Double totalFeePayable) {
		this.totalFeePayable = totalFeePayable;
	}

	/**
	 * @return the totalFeeWaived
	 */
	public Double getTotalFeeWaived() {
		return this.totalFeeWaived;
	}

	/**
	 * @param totalFeeWaived
	 *            the totalFeeWaived to set
	 */
	public void setTotalFeeWaived(final Double totalFeeWaived) {
		this.totalFeeWaived = totalFeeWaived;
	}

	/**
	 * @return the totalFeeRefunded
	 */
	public Double getTotalFeeRefunded() {
		return this.totalFeeRefunded;
	}

	/**
	 * @param totalFeeRefunded
	 *            the totalFeeRefunded to set
	 */
	public void setTotalFeeRefunded(final Double totalFeeRefunded) {
		this.totalFeeRefunded = totalFeeRefunded;
	}

	/**
	 * @return the totalFeePaid
	 */
	public Double getTotalFeePaid() {
		return this.totalFeePaid;
	}

	/**
	 * @param totalFeePaid
	 *            the totalFeePaid to set
	 */
	public void setTotalFeePaid(final Double totalFeePaid) {
		this.totalFeePaid = totalFeePaid;
	}

	/**
	 * @return the totalFeeWaivedRequestAmount
	 */
	public Double getTotalFeeWaivedRequestAmount() {
		return this.totalFeeWaivedRequestAmount;
	}

	/**
	 * @param totalFeeWaivedRequestAmount
	 *            the totalFeeWaivedRequestAmount to set
	 */
	public void setTotalFeeWaivedRequestAmount(final Double totalFeeWaivedRequestAmount) {
		this.totalFeeWaivedRequestAmount = totalFeeWaivedRequestAmount;
	}

	/**
	 * @return the totalFeeRefundRequestAmount
	 */
	public Double getTotalFeeRefundRequestAmount() {
		return this.totalFeeRefundRequestAmount;
	}

	/**
	 * @param totalFeeRefundRequestAmount
	 *            the totalFeeRefundRequestAmount to set
	 */
	public void setTotalFeeRefundRequestAmount(final Double totalFeeRefundRequestAmount) {
		this.totalFeeRefundRequestAmount = totalFeeRefundRequestAmount;
	}

	/**
	 * @return the totalFeeRefundPendingAmount
	 */
	public Double getTotalFeeRefundPendingAmount() {
		return this.totalFeeRefundPendingAmount;
	}

	/**
	 * @param totalFeeRefundPendingAmount
	 *            the totalFeeRefundPendingAmount to set
	 */
	public void setTotalFeeRefundPendingAmount(final Double totalFeeRefundPendingAmount) {
		this.totalFeeRefundPendingAmount = totalFeeRefundPendingAmount;
	}

	/**
	 * @return the totalFeePaymentPendingAmount
	 */
	public Double getTotalFeePaymentPendingAmount() {
		return this.totalFeePaymentPendingAmount;
	}

	/**
	 * @param totalFeePaymentPendingAmount
	 *            the totalFeePaymentPendingAmount to set
	 */
	public void setTotalFeePaymentPendingAmount(final Double totalFeePaymentPendingAmount) {
		this.totalFeePaymentPendingAmount = totalFeePaymentPendingAmount;
	}

}
