/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 *
 */
package com.apeironsol.need.core.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.apeironsol.framework.BaseEntity;

/**
 * @author sunny
 *
 *         Entity class for admission reservation fee
 *
 */
@Entity
@Table(name = "ADMISSION_RESERVATION_FEE")
public class AdmissionReservationFee extends BaseEntity implements Serializable {

	/**
	 * Universal serial version id for this class
	 */
	private static final long	serialVersionUID	= 5920635222029145406L;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STUDENT_ID", nullable = false)
	private Student				student;

	@Column(name = "RESERVATION_FEE")
	private Double				reservationFee;

	@Column(name = "RESER_FEE_PAID_DATE")
	@Temporal(TemporalType.DATE)
	private Date				reservationFeePaidDate;

	@Column(name = "RESER_FEE_EXT_TRAN_NR")
	private String				reservationFeeExternalTransactionNr;

	@Column(name = "RESER_FEE_EXT_TRAN_DATE")
	@Temporal(TemporalType.DATE)
	private Date				reservationFeeExternalTransactionDate;

	@Column(name = "RESER_FEE_APPLIED")
	private Boolean				reservationFeeAppliedToStudentFees;

	@Column(name = "APPLICATION_FORM_FEE")
	private Double				applicationFormFee;

	@Column(name = "APPL_FEE_PAID_DATE")
	@Temporal(TemporalType.DATE)
	private Date				applicationFeePaidDate;

	@Column(name = "APPL_FEE_EXT_TRAN_NR")
	private String				applicationFeeExternalTransactionNr;

	@Column(name = "APPL_FEE_EXT_TRAN_DATE")
	@Temporal(TemporalType.DATE)
	private Date				applicationFeeExternalTransactionDate;

	@Column(name = "APPL_FEE_APPLIED")
	private Boolean				applicationFeeAppliedToStudentFees;

	public Double getReservationFee() {
		return this.reservationFee;
	}

	public void setReservationFee(final Double reservationFee) {
		this.reservationFee = reservationFee;
	}

	public Student getStudent() {
		return this.student;
	}

	public void setStudent(final Student student) {
		this.student = student;
	}

	public String getReservationFeeExternalTransactionNr() {
		return this.reservationFeeExternalTransactionNr;
	}

	public void setReservationFeeExternalTransactionNr(final String reservationFeeExternalTransactionNr) {
		this.reservationFeeExternalTransactionNr = reservationFeeExternalTransactionNr;
	}

	public Date getReservationFeeExternalTransactionDate() {
		return this.reservationFeeExternalTransactionDate;
	}

	public void setReservationFeeExternalTransactionDate(final Date reservationFeeExternalTransactionDate) {
		this.reservationFeeExternalTransactionDate = reservationFeeExternalTransactionDate;
	}

	public String getApplicationFeeExternalTransactionNr() {
		return this.applicationFeeExternalTransactionNr;
	}

	public void setApplicationFeeExternalTransactionNr(final String applicationFeeExternalTransactionNr) {
		this.applicationFeeExternalTransactionNr = applicationFeeExternalTransactionNr;
	}

	public Date getApplicationFeeExternalTransactionDate() {
		return this.applicationFeeExternalTransactionDate;
	}

	public void setApplicationFeeExternalTransactionDate(final Date applicationFeeExternalTransactionDate) {
		this.applicationFeeExternalTransactionDate = applicationFeeExternalTransactionDate;
	}

	public Date getReservationFeePaidDate() {
		return this.reservationFeePaidDate;
	}

	public void setReservationFeePaidDate(final Date reservationFeePaidDate) {
		this.reservationFeePaidDate = reservationFeePaidDate;
	}

	public Date getApplicationFeePaidDate() {
		return this.applicationFeePaidDate;
	}

	public void setApplicationFeePaidDate(final Date applicationFeePaidDate) {
		this.applicationFeePaidDate = applicationFeePaidDate;
	}

	public Boolean getReservationFeeAppliedToStudentFees() {
		return this.reservationFeeAppliedToStudentFees;
	}

	public void setReservationFeeAppliedToStudentFees(final Boolean reservationFeeAppliedToStudentFees) {
		this.reservationFeeAppliedToStudentFees = reservationFeeAppliedToStudentFees;
	}

	public Boolean getApplicationFeeAppliedToStudentFees() {
		return this.applicationFeeAppliedToStudentFees;
	}

	public void setApplicationFeeAppliedToStudentFees(final Boolean applicationFeeAppliedToStudentFees) {
		this.applicationFeeAppliedToStudentFees = applicationFeeAppliedToStudentFees;
	}

	public Double getApplicationFormFee() {
		return this.applicationFormFee;
	}

	public void setApplicationFormFee(final Double applicationFormFee) {
		this.applicationFormFee = applicationFormFee;
	}

}
