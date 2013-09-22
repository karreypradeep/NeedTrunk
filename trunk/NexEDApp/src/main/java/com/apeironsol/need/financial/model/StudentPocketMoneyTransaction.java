/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 * 
 */
package com.apeironsol.need.financial.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.apeironsol.need.core.model.StudentSection;
import com.apeironsol.need.util.constants.PaymentMethodConstant;
import com.apeironsol.need.util.constants.StudentPocketMoneyTransactionTypeConstant;
import com.apeironsol.framework.BaseEntity;

/**
 * Entity class for student pocket money transaction.
 * 
 * @author Pradeep
 */

@Entity
@Table(name = "STUDE_POCKET_MONEY_TRAN")
public class StudentPocketMoneyTransaction extends BaseEntity implements Serializable {

	/**
	 * Universal serial version number.
	 */
	private static final long							serialVersionUID	= 7344251667991591997L;

	@NotNull(message = "model.student_section_mandatory")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STUDENT_SECTION_ID", nullable = false)
	private StudentSection								studentSection;

	@NotNull(message = "model.transactionId_mandatory")
	@Column(name = "TRANSACTION_NR", nullable = false)
	private String										transactionNr;

	@NotNull(message = "model.amount_mandatory")
	@Column(name = "AMOUNT", nullable = false)
	private Double										amount;

	@NotNull(message = "model.transaction_date_mandatory")
	@Column(name = "TRANSACTION_DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date										transactionDate;

	@NotNull(message = "model.fee_paid_date_mandatory")
	@Column(name = "FEE_PAID_DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date										depositOrWithdrawDate;

	@Basic
	@Column(name = "SFT_TYPE", nullable = false)
	@Enumerated(EnumType.STRING)
	private StudentPocketMoneyTransactionTypeConstant	studentPocketMoneyTransactionTypeConstant;

	@Basic
	@Column(name = "PAYMENT_METHOD", nullable = false)
	@Enumerated(EnumType.STRING)
	private PaymentMethodConstant						paymentMethod;

	@Column(name = "CHEQUE_NR")
	private String										chequeNumber;

	@Column(name = "BANK_NAME")
	private String										bankName;

	@Column(name = "BANK_BRANCH")
	private String										bankBranch;

	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(final Double amount) {
		this.amount = amount;
	}

	public Date getTransactionDate() {
		return this.transactionDate;
	}

	public void setTransactionDate(final Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public StudentPocketMoneyTransactionTypeConstant getStudentPocketMoneyTransactionTypeConstant() {
		return this.studentPocketMoneyTransactionTypeConstant;
	}

	public void setStudentPocketMoneyTransactionTypeConstant(
			final StudentPocketMoneyTransactionTypeConstant studentPocketMoneyTransactionTypeConstant) {
		this.studentPocketMoneyTransactionTypeConstant = studentPocketMoneyTransactionTypeConstant;
	}

	public String getTransactionNr() {
		return this.transactionNr;
	}

	public void setTransactionNr(final String transactionNr) {
		this.transactionNr = transactionNr;
	}

	public PaymentMethodConstant getPaymentMethod() {
		return this.paymentMethod;
	}

	public void setPaymentMethod(final PaymentMethodConstant paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * @return the chequeNumber
	 */
	public String getChequeNumber() {
		return this.chequeNumber;
	}

	/**
	 * @param chequeNumber
	 *            the chequeNumber to set
	 */
	public void setChequeNumber(final String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}

	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return this.bankName;
	}

	/**
	 * @param bankName
	 *            the bankName to set
	 */
	public void setBankName(final String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return the bankBranch
	 */
	public String getBankBranch() {
		return this.bankBranch;
	}

	/**
	 * @param bankBranch
	 *            the bankBranch to set
	 */
	public void setBankBranch(final String bankBranch) {
		this.bankBranch = bankBranch;
	}

	/**
	 * @return the depositOrWithdrawDate
	 */
	public Date getDepositOrWithdrawDate() {
		return this.depositOrWithdrawDate;
	}

	/**
	 * @param depositOrWithdrawDate
	 *            the depositOrWithdrawDate to set
	 */
	public void setDepositOrWithdrawDate(final Date depositOrWithdrawDate) {
		this.depositOrWithdrawDate = depositOrWithdrawDate;
	}

	public boolean isPaymentMethodCheck() {
		return PaymentMethodConstant.CHEQUE.equals(getPaymentMethod());
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

}
