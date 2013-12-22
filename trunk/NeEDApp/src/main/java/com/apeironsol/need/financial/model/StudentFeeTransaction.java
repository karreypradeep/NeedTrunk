package com.apeironsol.need.financial.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.apeironsol.framework.BaseEntity;
import com.apeironsol.need.core.model.StudentAcademicYear;
import com.apeironsol.need.util.NumberUtil;
import com.apeironsol.need.util.constants.PaymentMethodConstant;
import com.apeironsol.need.util.constants.StudentFeeTransactionStatusConstant;
import com.apeironsol.need.util.constants.StudentFeeTransactionTypeConstant;

/**
 * Entity class for class fee
 * 
 * @author Pradeep
 */

@Entity
@Table(name = "STUDENT_FEE_TXN")
public class StudentFeeTransaction extends BaseEntity implements Serializable {

	/**
	 * Universal serial version number.
	 */
	private static final long					serialVersionUID	= 6889673453522054463L;

	@NotNull(message = "model.transactionId_mandatory")
	@Column(name = "TRANSACTION_NR", nullable = false)
	private String								transactionNr;

	@Column(name = "EXT_TRANSACTION_NR")
	private String								externalTransactionNr;

	@NotNull(message = "model.amount_mandatory")
	@Column(name = "AMOUNT", nullable = false)
	private Double								amount;

	@NotNull(message = "model.transaction_date_mandatory")
	@Column(name = "TRANSACTION_DATE", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date								transactionDate;

	@Column(name = "EXT_TRANSACTION_DATE")
	@Temporal(TemporalType.DATE)
	private Date								externalTransactionDate;

	@ManyToOne
	@JoinColumn(name = "STUDENT_ACADEMIC_YEAR_ID", nullable = false)
	private StudentAcademicYear					studentAcademicYear;

	@Basic
	@Column(name = "SFT_TYPE", nullable = false)
	@Enumerated(EnumType.STRING)
	private StudentFeeTransactionTypeConstant	studentFeeTransactionType;

	@Basic
	@Column(name = "SFT_STATUS", nullable = false)
	@Enumerated(EnumType.STRING)
	private StudentFeeTransactionStatusConstant	studentFeeTransactionStatus;

	@Basic
	@Column(name = "PAYMENT_METHOD")
	@Enumerated(EnumType.STRING)
	private PaymentMethodConstant				paymentMethod;

	@Column(name = "CHEQUE_NR")
	private String								chequeNumber;

	@Column(name = "BANK_NAME")
	private String								bankName;

	@Column(name = "BANK_BRANCH")
	private String								bankBranch;

	@Column(name = "COMMENTS", length = 300)
	private String								comments;

	/**
	 * @return the externalReceiptNr
	 */
	public String getExternalTransactionNr() {
		return this.externalTransactionNr;
	}

	/**
	 * @param externalReceiptNr
	 *            the externalReceiptNr to set
	 */
	public void setExternalTransactionNr(final String externalTransactionNr) {
		this.externalTransactionNr = externalTransactionNr;
	}

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

	public StudentFeeTransactionTypeConstant getStudentFeeTransactionType() {
		return this.studentFeeTransactionType;
	}

	public void setStudentFeeTransactionType(final StudentFeeTransactionTypeConstant studentFeeTransactionType) {
		this.studentFeeTransactionType = studentFeeTransactionType;
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

	public Date getExternalTransactionDate() {
		return this.externalTransactionDate;
	}

	public void setExternalTransactionDate(final Date externalTransactionDate) {
		this.externalTransactionDate = externalTransactionDate;
	}

	public boolean isPaymentMethodCheck() {
		return PaymentMethodConstant.CHEQUE.equals(this.getPaymentMethod());
	}

	public StudentFeeTransactionStatusConstant getStudentFeeTransactionStatus() {
		return this.studentFeeTransactionStatus;
	}

	public void setStudentFeeTransactionStatus(final StudentFeeTransactionStatusConstant studentFeeTransactionStatus) {
		this.studentFeeTransactionStatus = studentFeeTransactionStatus;
	}

	public StudentAcademicYear getStudentAcademicYear() {
		return this.studentAcademicYear;
	}

	public void setStudentAcademicYear(final StudentAcademicYear studentAcademicYear) {
		this.studentAcademicYear = studentAcademicYear;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

	public boolean isStatusRequestForRefundOrDeduction() {
		return StudentFeeTransactionStatusConstant.REFUND_REQUEST.equals(this.studentFeeTransactionStatus)
				|| StudentFeeTransactionStatusConstant.DEDUCTION_REQUEST.equals(this.studentFeeTransactionStatus);
	}

	public boolean isStatusPendingForRefundOrDeductionOrPayment() {
		return StudentFeeTransactionStatusConstant.REFUND_PENDING.equals(this.studentFeeTransactionStatus)
				|| StudentFeeTransactionStatusConstant.PAYMENT_PENDING.equals(this.studentFeeTransactionStatus);
	}

	public boolean isStatusProcessedForRefundOrDeductionOrPayment() {
		return StudentFeeTransactionStatusConstant.REFUND_PROCESSED.equals(this.studentFeeTransactionStatus)
				|| StudentFeeTransactionStatusConstant.DEDUCTION_PROCESSED.equals(this.studentFeeTransactionStatus)
				|| StudentFeeTransactionStatusConstant.PAYMENT_PROCESSED.equals(this.studentFeeTransactionStatus);
	}

	public boolean isStatusTransactionCancelled() {
		return StudentFeeTransactionStatusConstant.TRANSACTOIN_CANCELLED.equals(this.studentFeeTransactionStatus);
	}

	public String getTransactionAmountInWords() {
		String result = "";
		if (this.amount != 0) {
			result = NumberUtil.getInWords(this.amount.longValue());
		}
		return result;
	}
}
