/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.financial.portal;

/**
 * View student class.
 * 
 * @author Pradeep
 * @author pradeep
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;

import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.need.core.model.BuildingBlock;
import com.apeironsol.need.core.model.StudentSection;
import com.apeironsol.need.core.portal.AbstractStudentBean;
import com.apeironsol.need.core.portal.StudentBean;
import com.apeironsol.need.core.portal.messages.BusinessMessages;
import com.apeironsol.need.core.service.BuildingBlockService;
import com.apeironsol.need.financial.model.StudentFeeTransaction;
import com.apeironsol.need.financial.model.StudentFeeTransactionDetails;
import com.apeironsol.need.financial.service.StudentFinancialService;
import com.apeironsol.need.util.DateUtil;
import com.apeironsol.need.util.comparator.StudentFeeDetailsDOComparator;
import com.apeironsol.need.util.constants.AuthorityConstant;
import com.apeironsol.need.util.constants.BuildingBlockConstant;
import com.apeironsol.need.util.constants.PaymentMethodConstant;
import com.apeironsol.need.util.constants.StudentFeeTransactionStatusConstant;
import com.apeironsol.need.util.constants.StudentFeeTransactionTypeConstant;
import com.apeironsol.need.util.constants.StudentSectionStatusConstant;
import com.apeironsol.need.util.dataobject.StudentFeeDetailsDO;
import com.apeironsol.need.util.dataobject.StudentFeeTransactionDO;
import com.apeironsol.need.util.dataobject.StudentFeeTransactionDetailsDO;
import com.apeironsol.need.util.dataobject.StudentFinancialAcademicYearDO;
import com.apeironsol.need.util.dataobject.StudentFinancialDO;
import com.apeironsol.need.util.dataobject.StudentFinancialDataModel;
import com.apeironsol.need.util.portal.ViewExceptionHandler;
import com.apeironsol.need.util.portal.ViewUtil;
import com.apeironsol.need.util.searchcriteria.StudentFeeTransactionSearchCriteria;

@Named
@Scope("session")
public class StudentFinancialBean extends AbstractStudentBean {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long							serialVersionUID						= -6360237442217557881L;

	private static final Logger							log										= Logger.getLogger(StudentFinancialBean.class);

	@Resource
	private StudentFinancialService						studentFinancialService;

	@Resource
	private BuildingBlockService						buildingBlockService;

	@Resource
	private StudentBean									studentBean;

	private Collection<StudentFinancialDO>				studentFinancialDOs;

	private Collection<StudentFeeTransaction>			studentFeeTransactions;

	private boolean										studentFeeTransactionsFlag;

	private boolean										loadStudentFinancialDetailsFlag;

	private boolean										makePaymentFlag;

	private Collection<BuildingBlock>					feeTypes;

	private boolean										loadFeeTypesFlag;

	private Long										feeTypeId;

	private StudentFinancialDataModel					studentFinancialDataModel;

	private StudentFinancialDO[]						selectedStudentFinancialDOs;

	private List<StudentFeeDetailsDO>					procesingStudentFeeDetailsDOs;

	private Collection<StudentFeeDetailsDO>				confirmStudentFeeDetailsDOs;

	private Collection<StudentFeeTransactionDO>			studentFeeTransactionDOs;

	private Double										feeDueAsOfToday;

	private String										paymentTransactionNr;

	private double										totalAmount;

	private Collection<StudentFeeTransactionDetailsDO>	studentFeeTransactionDetailsDOs;

	private StudentFeeTransaction						studentFeeTransaction;

	private StudentFeeTransactionDO						studentFeeTransactionDO;

	private boolean										externalTransaction;

	private boolean										viewingGeneratedReceipt;

	private boolean										viewTransactionOnProcessPendingFeeLevel	= false;

	private StudentFeeTransactionSearchCriteria			studentFeeTransactionSearchCriteria		= null;

	private StudentFinancialAcademicYearDO				studentFinancialAcademicYearDO;

	private StudentFeeTransactionStatusConstant			studentFeeTransactionStatusConstantForPrint;

	private Collection<StudentFeeTransaction>			studentFeeTransactionsForPrint;

	public enum PaymentWizard {

		SELECT_FEE_TYPE("selectFeeType"),
		PROCESS_PAYMENT("processPayment"),
		CONFIRM_PAYMENT("confirmPayment"),
		PAYMENT_RECIPT("paymentRecipt"),
		PROCESS_REFUND("processRefund"),
		CONFIRM_REFUND_REQUEST("confirmRefundRequest"),
		REFUND_REQUEST_RECIPT("refundRequestRecipt"),
		REFUND_PAYMENTS("refundPayments"),
		DEDUCT_PAYMENTS("deductPayments"),
		PROCESS_DEDUCTION("processDeduction"),
		CONFIRM_DEDUCTION_REQUEST("confirmDeductionRequest"),
		DEDUCTION_REQUEST_RECIPT("decutionRequestRecipt"),
		VIEW_TRANSACTIONS("viewTransactions"),
		VIEW_FEE_DETAILS("viewFeeDetails"),
		ADDITIONAL_FEE("additionalFee"),
		REVERT_FEE_TRANSACTION("revertFeeTransaction"),
		PROCESS_PENDING_FEE_TRANSACTION("processPendingFeeTransaction"),
		CONFIRM_AND_SUBMIT_PENDING_FEE_TRANSACTION("confirmAndSubmitPendingFeeTransaction");

		private String	key;

		PaymentWizard(final String key) {
			this.key = key;
		}

		public String getKey() {
			return this.key;
		}

		public void setKey(final String key) {
			this.key = key;
		}
	};

	private String	paymentWizardAciveStep;

	@PostConstruct
	public void init() {
		this.setStudentFeeTransactionDOs(new ArrayList<StudentFeeTransactionDO>());
	}

	public Collection<StudentFinancialDO> getStudentFinancialDOs() {
		return this.studentFinancialDOs;
	}

	public void setStudentFinancialDOs(final Collection<StudentFinancialDO> studentFinancialDOs) {
		this.studentFinancialDOs = studentFinancialDOs;
	}

	public boolean isLoadStudentFinancialDetailsFlag() {
		return this.loadStudentFinancialDetailsFlag;
	}

	public void setLoadStudentFinancialDetailsFlag(final boolean loadStudentFinancialDetailsFlag) {
		this.loadStudentFinancialDetailsFlag = loadStudentFinancialDetailsFlag;
	}

	public Collection<StudentFeeTransaction> getStudentFeeTransactions() {
		return this.studentFeeTransactions;
	}

	public void setStudentFeeTransactions(final Collection<StudentFeeTransaction> studentFeeTransactions) {
		this.studentFeeTransactions = studentFeeTransactions;
	}

	public boolean isStudentFeeTransactionsFlag() {
		return this.studentFeeTransactionsFlag;
	}

	public void setStudentFeeTransactionsFlag(final boolean studentFeeTransactionsFlag) {
		this.studentFeeTransactionsFlag = studentFeeTransactionsFlag;
	}

	public boolean isMakePaymentFlag() {
		return this.makePaymentFlag;
	}

	public void setMakePaymentFlag(final boolean makePaymentFlag) {
		this.makePaymentFlag = makePaymentFlag;
	}

	public Collection<BuildingBlock> getFeeTypes() {
		return this.feeTypes;
	}

	public void setFeeTypes(final Collection<BuildingBlock> feeTypes) {
		this.feeTypes = feeTypes;
	}

	public boolean isLoadFeeTypesFlag() {
		return this.loadFeeTypesFlag;
	}

	public void setLoadFeeTypesFlag(final boolean loadFeeTypesFlag) {
		this.loadFeeTypesFlag = loadFeeTypesFlag;
	}

	public void setFeeDueAsOfToday(final Double feeDueAsOfToday) {
		this.feeDueAsOfToday = feeDueAsOfToday;
	}

	public Collection<StudentFeeDetailsDO> getConfirmStudentFeeDetailsDOs() {
		return this.confirmStudentFeeDetailsDOs;
	}

	public void setConfirmStudentFeeDetailsDOs(final Collection<StudentFeeDetailsDO> confirmStudentFeeDetailsDOs) {
		this.confirmStudentFeeDetailsDOs = confirmStudentFeeDetailsDOs;
	}

	public String getPaymentTransactionNr() {
		return this.paymentTransactionNr;
	}

	public void setPaymentTransactionNr(final String paymentTransactionNr) {
		this.paymentTransactionNr = paymentTransactionNr;
	}

	public double getTotalAmount() {
		return this.totalAmount;
	}

	public void setTotalAmount(final double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Collection<StudentFeeTransactionDetailsDO> getStudentFeeTransactionDetailsDOs() {
		return this.studentFeeTransactionDetailsDOs;
	}

	public void setStudentFeeTransactionDetailsDOs(final Collection<StudentFeeTransactionDetailsDO> studentFeeTransactionsDetailsDOs) {
		this.studentFeeTransactionDetailsDOs = studentFeeTransactionsDetailsDOs;
	}

	public StudentFeeTransaction getStudentFeeTransaction() {
		return this.studentFeeTransaction;
	}

	public void setStudentFeeTransaction(final StudentFeeTransaction studentFeeTransaction) {
		this.studentFeeTransaction = studentFeeTransaction;
	}

	public StudentFeeTransactionDO getStudentFeeTransactionDO() {
		return this.studentFeeTransactionDO;
	}

	public void setStudentFeeTransactionDO(final StudentFeeTransactionDO studentFeeTransactionDO) {
		this.studentFeeTransactionDO = studentFeeTransactionDO;
	}

	public boolean isExternalTransaction() {
		return this.externalTransaction;
	}

	public void setExternalTransaction(final boolean externalTransaction) {
		this.externalTransaction = externalTransaction;
	}

	public Long getFeeTypeId() {
		return this.feeTypeId;
	}

	public void setFeeTypeId(final Long feeTypeId) {
		this.feeTypeId = feeTypeId;
	}

	public StudentFinancialDataModel getStudentFinancialDataModel() {
		return this.studentFinancialDataModel;
	}

	public void setStudentFinancialDataModel(final StudentFinancialDataModel studentFinancialDataModel) {
		this.studentFinancialDataModel = studentFinancialDataModel;
	}

	public StudentFinancialDO[] getSelectedStudentFinancialDOs() {
		return this.selectedStudentFinancialDOs;
	}

	public void setSelectedStudentFinancialDOs(final StudentFinancialDO[] selectedStudentFinancialDOs) {
		this.selectedStudentFinancialDOs = selectedStudentFinancialDOs;
	}

	public String getPaymentWizardAciveStep() {
		return this.paymentWizardAciveStep;
	}

	public void setPaymentWizardAciveStep(final String paymentWizardAciveStep) {
		this.paymentWizardAciveStep = paymentWizardAciveStep;
	}

	public List<StudentFeeDetailsDO> getProcesingStudentFeeDetailsDOs() {
		return this.procesingStudentFeeDetailsDOs;
	}

	public void setProcesingStudentFeeDetailsDOs(final List<StudentFeeDetailsDO> procesingStudentFeeDetailsDOs) {
		this.procesingStudentFeeDetailsDOs = procesingStudentFeeDetailsDOs;
	}

	public Collection<StudentFeeTransactionDO> getStudentFeeTransactionDOs() {
		return this.studentFeeTransactionDOs;
	}

	public void setStudentFeeTransactionDOs(final Collection<StudentFeeTransactionDO> studentFeeTransactionDOs) {
		this.studentFeeTransactionDOs = studentFeeTransactionDOs;
	}

	@Override
	public void onTabChange() {
		this.setLoadStudentFinancialDetailsFlag(true);
		this.setSelectedStudentFinancialDOs(null);
		this.loadStudentFinancialDetails();
	}

	public void loadFeeTypes() {
		if (this.loadFeeTypesFlag) {
			this.feeTypes = this.buildingBlockService.findBuildingBlocksbyBranchIdAndBuildingBlockType(this.sessionBean.getCurrentBranch().getId(),
					BuildingBlockConstant.FEE_TYPE);
		}
	}

	public void loadStudentFinancialDetails() {
		if (this.isLoadStudentFinancialDetailsFlag()) {
			this.reloadStudentFeeDetails();
		}
	}

	public void reloadStudentFeeDetails() {
		this.studentFinancialDOs = this.studentFinancialService.getStudentFinancialDetailsByStudentIdAndAcadmicYearId(this.studentBean.getStudentAcademicYear()
				.getStudent().getId(), this.studentBean.getStudentAcademicYear().getAcademicYear().getId());
		this.studentFinancialAcademicYearDO = new StudentFinancialAcademicYearDO();
		this.getStudentFinancialAcademicYearDO().setStudentFinancialDOs(this.studentFinancialDOs);
		this.getStudentFinancialAcademicYearDO().calculateStudentFeeForAcademicYear();
		this.studentFinancialDataModel = new StudentFinancialDataModel(new ArrayList<StudentFinancialDO>(this.studentFinancialDOs));

		this.setFeeDueAsOfToday(this.studentFinancialService.getStudentFeeDue(this.studentBean.getStudent(), this.studentBean.getStudentAcademicYear()
				.getAcademicYear(), DateUtil.getSystemDate()));
	}

	public Double getStudentTotalFeeDue() {
		Double result = 0.0;
		if (this.studentFinancialDataModel != null) {
			@SuppressWarnings("unchecked")
			final List<StudentFinancialDO> studentFinancialDOs = (List<StudentFinancialDO>) this.studentFinancialDataModel.getWrappedData();
			if (studentFinancialDOs != null) {
				for (final StudentFinancialDO studentFinancialDO : studentFinancialDOs) {
					result += studentFinancialDO.getNetFeeDue();
				}
			}
		}
		return result;
	}

	public void selectFeeForProcessPayments() {

		if (this.validatePaymentsAction()) {

			this.paymentWizardAciveStep = PaymentWizard.PROCESS_PAYMENT.getKey();

			this.populateProcessingData();
		}

	}

	public void selectViewFeeDetails() {

		if (this.validatePaymentsAction()) {

			this.paymentWizardAciveStep = PaymentWizard.VIEW_FEE_DETAILS.getKey();

			this.setPaymentTransactionNr(null);

			this.confirmStudentFeeDetailsDOs = null;

			this.populateProcessingData();
		}

	}

	public void selectFeeForProcessRefunds() {

		if (this.validatePaymentsAction()) {

			this.paymentWizardAciveStep = PaymentWizard.PROCESS_REFUND.getKey();

			this.populateProcessingData();
		}

	}

	private boolean validatePaymentsAction() {
		if (this.selectedStudentFinancialDOs == null || this.selectedStudentFinancialDOs.length == 0) {
			ViewUtil.addMessage("Select Fee's to process payments.", FacesMessage.SEVERITY_WARN);
			return false;
		}

		return true;
	}

	public void selectFeeForProcessDeductibles() {

		if (this.validatePaymentsAction()) {

			this.paymentWizardAciveStep = PaymentWizard.PROCESS_DEDUCTION.getKey();

			this.populateProcessingData();
		}

	}

	public void populateStudentFeeTransactions() {

		this.paymentWizardAciveStep = PaymentWizard.VIEW_TRANSACTIONS.getKey();

		this.studentFeeTransactions = this.studentFinancialService.getStudentFeeTransactionsByStudentAcademicYearId(this.studentBean.getStudentAcademicYear()
				.getId());

	}

	private void populateProcessingData() {
		this.setProcesingStudentFeeDetailsDOs(new ArrayList<StudentFeeDetailsDO>());

		for (final StudentFinancialDO studentFinancialDO : this.selectedStudentFinancialDOs) {

			final Collection<StudentFeeDetailsDO> studentFeeDetails = studentFinancialDO.getStudentFeeDetailsDOs();
			if (studentFeeDetails != null && !studentFeeDetails.isEmpty()) {
				final List<StudentFeeDetailsDO> sortedStudentFeeDetailsByDueDate = new ArrayList<StudentFeeDetailsDO>();
				for (final StudentFeeDetailsDO studentFinancialTransactionDO : studentFeeDetails) {
					sortedStudentFeeDetailsByDueDate.add(studentFinancialTransactionDO);
				}
				// Sort payments by payment date.
				Collections.sort(sortedStudentFeeDetailsByDueDate, new StudentFeeDetailsDOComparator(StudentFeeDetailsDOComparator.DUE_DATE));

				for (final StudentFeeDetailsDO studentFinancialTransactionDO : sortedStudentFeeDetailsByDueDate) {

					this.getProcesingStudentFeeDetailsDOs().add(studentFinancialTransactionDO);

				}
			}

		}

		this.selectedStudentFinancialDOs = null;
	}

	public void processPayment() {

		try {

			boolean paymentTransactionsFound = false;
			boolean hasExceptions = false;
			this.resetDataToProcessTransaction();
			for (final StudentFeeDetailsDO studentFeeDetailsDO : this.getProcesingStudentFeeDetailsDOs()) {

				final Double payingAmount = studentFeeDetailsDO.getPayingAmount();

				if (payingAmount != null && payingAmount != 0) {

					final double totalAmount = studentFeeDetailsDO.getTotalNetDue()
							- (studentFeeDetailsDO.getTotalFeePaymentPendingAmount() + studentFeeDetailsDO.getTotalFeeDeductionRequestAmount());

					if (payingAmount > totalAmount) {
						try {
							throw new BusinessException(BusinessMessages.getResourceBundleName(),
									BusinessMessages.MSG_PAYMENT_AMOUNT_SHOULD_NOT_BE_GREATER_THAN_THE_DUE_AMOUNT, new Object[] {
											studentFeeDetailsDO.getFeeName(), totalAmount, studentFeeDetailsDO.getDueDate() });

						} catch (final BusinessException exception) {
							hasExceptions = true;
							ViewExceptionHandler.handle(exception);
							return;
						}
					} else {
						this.totalAmount = this.totalAmount + payingAmount;
						this.confirmStudentFeeDetailsDOs.add(studentFeeDetailsDO);
					}

					paymentTransactionsFound = true;

				}
			}

			if (paymentTransactionsFound && !hasExceptions) {
				this.paymentWizardAciveStep = PaymentWizard.CONFIRM_PAYMENT.getKey();
			} else {
				if (!paymentTransactionsFound) {
					ViewUtil.addMessage("Paying amount need to be specified on atlease on one of the fee payments.", FacesMessage.SEVERITY_ERROR);
				}

			}

		} catch (final ApplicationException e) {
			log.info(e.getMessage());
		} catch (final Throwable e) {
			log.info(e.getMessage());
		}

	}

	public void submitPayments() {
		StudentFeeTransactionStatusConstant transactionStatus = null;
		if (PaymentMethodConstant.CHEQUE.equals(this.studentFeeTransaction.getPaymentMethod())) {
			transactionStatus = StudentFeeTransactionStatusConstant.PAYMENT_PENDING;
		} else {
			transactionStatus = StudentFeeTransactionStatusConstant.PAYMENT_PROCESSED;
		}
		this.setViewingGeneratedReceipt(false);
		this.submitFeeTransactionForProcess(StudentFeeTransactionTypeConstant.PAYMENT, PaymentWizard.PAYMENT_RECIPT, transactionStatus);
	}

	/**
	 * Cancel transaction.
	 */
	public void processTransactionForCancellation() {
		this.studentFeeTransaction = this.studentFinancialService.revertStudentFeeTransaction(this.studentFeeTransaction);
		this.studentFeeTransactionDO = this.studentFinancialService.retriveStudentFeeTransactionDetailsByTransactionNr(this.studentFeeTransaction
				.getTransactionNr());

		this.setProcesingStudentFeeDetailsDOs(null);

		this.setConfirmStudentFeeDetailsDOs(null);

		this.selectedStudentFinancialDOs = null;

		ViewUtil.addMessage("Transaction is reverted sucessfully.", FacesMessage.SEVERITY_INFO);

		this.reloadStudentFeeDetails();
	}

	/**
	 * Call service layer method for processing the student fee transaction
	 * depending on the status type. If the fee transaction type Payment then
	 * the payments will be deposited and the status depends on the payment
	 * method selected. If fee transaction type is deduct and if status is for
	 * request then request deduction is called. If fee transaction type is
	 * refund and if status is for request then request refund is called.
	 * 
	 * @param studentFeeTransactionType
	 *            studentFeeTransactionType.
	 * @param nextPaymentWizard
	 *            nextPaymentWizard.
	 * @param transactionStatus
	 *            transactionStatus.
	 */
	private void submitFeeTransactionForProcess(final StudentFeeTransactionTypeConstant studentFeeTransactionType, final PaymentWizard nextPaymentWizard,
			final StudentFeeTransactionStatusConstant transactionStatus) {
		try {
			final Collection<StudentFeeDetailsDO> studentFeeDetailsDOs = this.getConfirmStudentFeeDetailsDOs();

			this.studentFeeTransaction.setAmount(this.totalAmount);
			StudentFeeTransaction studentFeeTransaction = null;

			if (StudentFeeTransactionTypeConstant.DEDUCT.equals(studentFeeTransactionType)) {

				if (StudentFeeTransactionStatusConstant.DEDUCTION_REQUEST.equals(transactionStatus)) {

					// Request for deduction
					studentFeeTransaction = this.studentFinancialService.requestForDeduction(this.studentBean.getStudentAcademicYear().getId(),
							this.studentFeeTransaction, studentFeeDetailsDOs);

				} else if (StudentFeeTransactionStatusConstant.DEDUCTION_PROCESSED.equals(transactionStatus)) {

					// Approve deduction request
					studentFeeTransaction = this.studentFinancialService.approveDeduction(this.studentBean.getStudentAcademicYear().getId(),
							this.studentFeeTransaction, studentFeeDetailsDOs);

				}
			} else if (StudentFeeTransactionTypeConstant.REFUND.equals(studentFeeTransactionType)) {
				if (StudentFeeTransactionStatusConstant.REFUND_REQUEST.equals(transactionStatus)) {

					// Request for refund;
					studentFeeTransaction = this.studentFinancialService.requestForRefund(this.studentBean.getStudentAcademicYear().getId(),
							this.studentFeeTransaction, studentFeeDetailsDOs);

				} else if (StudentFeeTransactionStatusConstant.REFUND_PENDING.equals(transactionStatus)) {

					// Approve refund request
					studentFeeTransaction = this.studentFinancialService.approveRefund(this.studentBean.getStudentAcademicYear().getId(),
							this.studentFeeTransaction, studentFeeDetailsDOs);

				}
			} else if (StudentFeeTransactionTypeConstant.PAYMENT.equals(studentFeeTransactionType)
					&& (StudentFeeTransactionStatusConstant.PAYMENT_PROCESSED.equals(transactionStatus) || StudentFeeTransactionStatusConstant.PAYMENT_PENDING
							.equals(transactionStatus))) {

				// deposit payments
				studentFeeTransaction = this.studentFinancialService.depositPayments(this.sessionBean.getCurrentBranch().getId(), this.studentBean
						.getStudentAcademicYear().getId(), this.studentFeeTransaction, studentFeeDetailsDOs);

			}
			this.studentFeeTransactionDO = this.studentFinancialService.retriveStudentFeeTransactionDetailsByTransactionNr(studentFeeTransaction
					.getTransactionNr());

			this.setProcesingStudentFeeDetailsDOs(null);

			this.setConfirmStudentFeeDetailsDOs(null);

			this.selectedStudentFinancialDOs = null;

			ViewUtil.addMessage("Transaction is processed sucessfully.", FacesMessage.SEVERITY_INFO);

			this.paymentWizardAciveStep = nextPaymentWizard.getKey();

			this.reloadStudentFeeDetails();

		} catch (final ApplicationException e) {
			log.info(e.getMessage());
			ViewUtil.addMessage("Exception while processing transaction : " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
		} catch (final Throwable e) {
			log.info(e.getMessage());
			ViewUtil.addMessage("Exception while processing transaction : " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
		}

	}

	/**
	 * Process transaction for refund request of selected fees.
	 */
	public void processRefundForRequest() {
		this.processRefundForRequestOrApproval(StudentFeeTransactionStatusConstant.REFUND_REQUEST);
	}

	/**
	 * Process transaction for refund approval of selected fees.
	 */
	public void processRefundForApproval() {
		this.processRefundForRequestOrApproval(StudentFeeTransactionStatusConstant.REFUND_PENDING);
	}

	/**
	 * Process transaction for refund approval of selected fees.
	 */
	private void processRefundForRequestOrApproval(final StudentFeeTransactionStatusConstant processStatus) {

		try {

			boolean refundTransactionsFound = false;

			boolean hasExceptions = false;
			this.resetDataToProcessTransaction();
			for (final StudentFeeDetailsDO studentFeeDetailsDO : this.getProcesingStudentFeeDetailsDOs()) {

				final Double refundAmount = studentFeeDetailsDO.getRefundAmount();

				if (refundAmount != null && refundAmount != 0) {

					final double allowedRefund = studentFeeDetailsDO.getTotalFeePaymentAmount()
							- (studentFeeDetailsDO.getTotalFeeRefundPendingAmount() + studentFeeDetailsDO.getTotalFeeRefundRequestAmount() + studentFeeDetailsDO
									.getTotalFeePaymentPendingAmount());

					if (refundAmount > allowedRefund) {

						try {
							throw new BusinessException(BusinessMessages.getResourceBundleName(),
									BusinessMessages.MSG_REFUND_AMOUNT_SHOULD_NOT_BE_GREATER_THAN_THE_TOTOAL_AMOUNT, new Object[] {
											studentFeeDetailsDO.getFeeName(), allowedRefund, studentFeeDetailsDO.getDueDate() });
						} catch (final BusinessException exception) {
							hasExceptions = true;
							ViewExceptionHandler.handle(exception);
						}
					} else {
						this.totalAmount = this.totalAmount + refundAmount;
						this.confirmStudentFeeDetailsDOs.add(studentFeeDetailsDO);
					}
					refundTransactionsFound = true;

				}
			}

			if (refundTransactionsFound && !hasExceptions) {

				this.submitFeeTransactionForProcess(StudentFeeTransactionTypeConstant.REFUND, PaymentWizard.SELECT_FEE_TYPE, processStatus);

			} else {
				if (!refundTransactionsFound) {
					ViewUtil.addMessage("Refund amount need to be specified on atlease on one of the fees.", FacesMessage.SEVERITY_ERROR);
				}

			}

		} catch (final ApplicationException e) {
			log.info(e.getMessage());
		} catch (final Throwable e) {
			log.info(e.getMessage());
		}

	}

	public void refundPayments() {
		this.submitFeeTransactionForProcess(StudentFeeTransactionTypeConstant.REFUND, PaymentWizard.REFUND_REQUEST_RECIPT,
				StudentFeeTransactionStatusConstant.REFUND_REQUEST);
	}

	public void processDeductionForRequest() {
		this.processDeductionForRequestOrApproval(StudentFeeTransactionStatusConstant.DEDUCTION_REQUEST);
	}

	public void processDeductionForApproval() {
		this.processDeductionForRequestOrApproval(StudentFeeTransactionStatusConstant.DEDUCTION_PROCESSED);
	}

	public void processDeductionForRequestOrApproval(final StudentFeeTransactionStatusConstant processStatus) {

		try {

			boolean deductionTransactionsFound = false;

			boolean hasExceptions = false;
			this.resetDataToProcessTransaction();
			for (final StudentFeeDetailsDO studentFeeDetailsDO : this.getProcesingStudentFeeDetailsDOs()) {

				final Double deductAmount = studentFeeDetailsDO.getDeductingAmount();

				if (deductAmount != null && deductAmount != 0) {

					final double allowedDeduction = studentFeeDetailsDO.getTotalNetAmount()
							- (studentFeeDetailsDO.getTotalFeeDeductionRequestAmount() + studentFeeDetailsDO.getTotalFeePaymentPendingAmount());

					if (deductAmount > allowedDeduction) {

						try {
							throw new BusinessException(BusinessMessages.getResourceBundleName(),
									BusinessMessages.MSG_DEDUCTING_AMOUNT_SHOULD_NOT_BE_GREATER_THAN_THE_TOTOAL_AMOUNT, new Object[] {
											studentFeeDetailsDO.getFeeName(), allowedDeduction, studentFeeDetailsDO.getDueDate() });
						} catch (final BusinessException exception) {
							hasExceptions = true;
							ViewExceptionHandler.handle(exception);
						}
					} else {
						this.totalAmount = this.totalAmount + deductAmount;
						this.confirmStudentFeeDetailsDOs.add(studentFeeDetailsDO);
					}
					deductionTransactionsFound = true;

				}
			}

			if (deductionTransactionsFound && !hasExceptions) {

				this.submitFeeTransactionForProcess(StudentFeeTransactionTypeConstant.DEDUCT, PaymentWizard.SELECT_FEE_TYPE, processStatus);
			} else {
				if (!deductionTransactionsFound) {
					ViewUtil.addMessage("Deducting amount need to be specified on atleast on one of the fees.", FacesMessage.SEVERITY_ERROR);
				}

			}

		} catch (final ApplicationException e) {
			log.info(e.getMessage());
		} catch (final Throwable e) {
			log.info(e.getMessage());
		}

	}

	public void deductPayments() {
		this.submitFeeTransactionForProcess(StudentFeeTransactionTypeConstant.DEDUCT, PaymentWizard.DEDUCTION_REQUEST_RECIPT,
				StudentFeeTransactionStatusConstant.DEDUCTION_REQUEST);
	}

	public void viewStudentFeeTransactionWithDetails() {

		this.viewStudentFeeTransaction();

		this.paymentWizardAciveStep = PaymentWizard.PAYMENT_RECIPT.getKey();
	}

	public void viewStudentFeeTransaction() {

		final String transactionNr = this.studentFeeTransaction.getTransactionNr();

		this.studentFeeTransactionDO = this.studentFinancialService.retriveStudentFeeTransactionDetailsByTransactionNr(transactionNr);
	}

	public void revertStudentFeeTransaction() {

		this.viewStudentFeeTransaction();

		this.paymentWizardAciveStep = PaymentWizard.REVERT_FEE_TRANSACTION.getKey();
	}

	public void updateSelectPayments() {
		this.selectedStudentFinancialDOs = null;

		final List<StudentFinancialDO> studentFinancialDOs = (List<StudentFinancialDO>) this.studentFinancialService
				.getStudentFinancialDetailsByStudentIdAndAcadmicYearId(this.studentBean.getStudent().getId(), this.studentBean.getStudentAcademicYear()
						.getAcademicYear().getId());

		this.studentFinancialDOs = studentFinancialDOs;

		this.studentFinancialDataModel = new StudentFinancialDataModel(studentFinancialDOs);
		this.setFeeDueAsOfToday(this.studentFinancialService.getStudentFeeDue(this.studentBean.getStudent(), this.studentBean.getStudentAcademicYear()
				.getAcademicYear(), DateUtil.getSystemDate()));
	}

	public Double getFeeDueAsOfToday() {
		return this.feeDueAsOfToday;
	}

	public double totolPayingAmount() {
		double total = 0d;

		final Collection<StudentFeeDetailsDO> studentFinancialTransactionDOs = this.getProcesingStudentFeeDetailsDOs();

		for (final StudentFeeDetailsDO studentFinancialTransactionDO : studentFinancialTransactionDOs) {

			final Double payingAmount = studentFinancialTransactionDO.getPayingAmount();

			if (payingAmount != null && payingAmount != 0) {
				total = total + payingAmount;
			}
		}
		return total;

	}

	public boolean isDisplayBankDetailsForPaymentTypeCheque() {
		return PaymentMethodConstant.CHEQUE.equals(this.studentFeeTransaction.getPaymentMethod());
	}

	public String getStudentName() {
		return this.studentBean.getStudent().getDisplayName();
	}

	public String getStudentCurrentKlassName() {
		return this.studentBean.getStudentSection().getSection().getKlass().getName();
	}

	public String getStudentCurrentSectionName() {
		return this.studentBean.getStudentSection().getSection().getName();
	}

	public String getStudentAdmissionNumber() {
		return this.studentBean.getStudent().getExternalAdmissionNr() == null ? this.studentBean.getStudent().getAdmissionNr() : this.studentBean.getStudent()
				.getExternalAdmissionNr();
	}

	/**
	 * Performs pre-requisites for processing of student fee transaction.
	 */
	private void resetDataToProcessTransaction() {
		this.studentFeeTransaction = new StudentFeeTransaction();
		this.externalTransaction = false;
		this.totalAmount = 0;
		this.confirmStudentFeeDetailsDOs = new ArrayList<StudentFeeDetailsDO>();
	}

	public boolean isStudentSectionInActiveStateForAcademicYear() {
		StudentSection studentSection = null;
		if (this.studentBean.getStudentAcademicYear() != null) {
			studentSection = this.studentSectionService.findStudentSectionByStudentAcademicYearIdAndStatus(this.studentBean.getStudentAcademicYear().getId(),
					StudentSectionStatusConstant.ACTIVE);
		}
		return studentSection != null ? true : false;
	}

	public boolean isViewingGeneratedReceipt() {
		return this.viewingGeneratedReceipt;
	}

	public void setViewingGeneratedReceipt(final boolean viewingGeneratedReceipt) {
		this.viewingGeneratedReceipt = viewingGeneratedReceipt;
	}

	public StudentFinancialAcademicYearDO getStudentFinancialAcademicYearDO() {
		return this.studentFinancialAcademicYearDO;
	}

	public void setStudentFinancialAcademicYearDO(final StudentFinancialAcademicYearDO studentFinancialAcademicYearDO) {
		this.studentFinancialAcademicYearDO = studentFinancialAcademicYearDO;
	}

	public boolean isUserHavingRoleToApproveRequestedTransaction() {
		boolean result = false;
		if (this.studentFeeTransaction != null) {
			if (StudentFeeTransactionStatusConstant.REFUND_REQUEST.equals(this.studentFeeTransaction.getStudentFeeTransactionStatus())) {
				result = this.grantedAuthorityBean.isUserAllowedToAcceptFeeRefundRequest();
			} else if (StudentFeeTransactionStatusConstant.DEDUCTION_REQUEST.equals(this.studentFeeTransaction.getStudentFeeTransactionStatus())) {
				result = this.grantedAuthorityBean.isUserAllowedToAcceptFeeDeductionRequest();
			}
		}
		return result;
	}

	public boolean isUserHavingRoleToRejectRequestedTransaction() {
		boolean result = false;
		if (this.studentFeeTransaction != null) {
			if (StudentFeeTransactionStatusConstant.REFUND_REQUEST.equals(this.studentFeeTransaction.getStudentFeeTransactionStatus())) {
				result = this.grantedAuthorityBean.isUserAllowedToRejectFeeRefundRequest();
			} else if (StudentFeeTransactionStatusConstant.DEDUCTION_REQUEST.equals(this.studentFeeTransaction.getStudentFeeTransactionStatus())) {
				result = this.grantedAuthorityBean.isUserAllowedToRejectFeeDeductionRequest();
			}
		}
		return result;
	}

	public boolean isUserHavingRoleToProcessRequestedTransaction() {
		boolean result = false;
		if (this.studentFeeTransaction != null) {
			if (StudentFeeTransactionStatusConstant.DEDUCTION_REQUEST.equals(this.studentFeeTransaction.getStudentFeeTransactionStatus())) {
				result = this.grantedAuthorityBean.isUserHasAuthorities(EnumSet.of(AuthorityConstant.PROCESS_FEE_DEDUCTION));
			} else if (StudentFeeTransactionStatusConstant.REFUND_PENDING.equals(this.studentFeeTransaction.getStudentFeeTransactionStatus())) {
				result = this.grantedAuthorityBean.isUserHasAuthorities(EnumSet.of(AuthorityConstant.PROCESS_FEE_REFUND));
			} else if (StudentFeeTransactionStatusConstant.PAYMENT_PENDING.equals(this.studentFeeTransaction.getStudentFeeTransactionStatus())) {
				result = this.grantedAuthorityBean.isUserHasAuthorities(EnumSet.of(AuthorityConstant.PROCESS_FEE_PAYMENT));
			}
		}
		return result;
	}

	public void searchStudentFeeTransactionsByRequest() {
		if (this.getStudentFeeTransactionSearchCriteria().isSearchCriteriaIsEmpty()) {

			ViewUtil.addMessage("Please enter search criteria.", FacesMessage.SEVERITY_ERROR);
		} else {

			this.getStudentFeeTransactionSearchCriteria().setBranch(this.sessionBean.getCurrentBranch());

			this.studentFeeTransactions = this.studentFinancialService
					.findStudentFeeTransactionsBySearchCriteria(this.getStudentFeeTransactionSearchCriteria());

			if (this.studentFeeTransactions == null || this.studentFeeTransactions.isEmpty()) {
				ViewUtil.addMessage("No transactions found for entered search criteria..", FacesMessage.SEVERITY_INFO);
			}

		}
	}

	public void viewPendingFeeTransaction() {

		this.viewStudentFeeTransaction();

		this.paymentWizardAciveStep = PaymentWizard.PROCESS_PENDING_FEE_TRANSACTION.getKey();

	}

	public void approveTransaction() {

		if (!this.isUserHavingRoleToApproveRequestedTransaction()) {

			ViewUtil.addMessage("User is not authorized to approve any transactons", FacesMessage.SEVERITY_ERROR);
			return;
		}

		try {

			if (this.studentFeeTransaction.getStudentFeeTransactionType().equals(StudentFeeTransactionTypeConstant.DEDUCT)) {

				this.studentFeeTransaction = this.studentFinancialService.approveDeductionRequest(this.studentFeeTransaction);

			} else if (this.studentFeeTransaction.getStudentFeeTransactionType().equals(StudentFeeTransactionTypeConstant.REFUND)) {

				this.studentFeeTransaction = this.studentFinancialService.approveRefundRequest(this.studentFeeTransaction);

			}

			if (this.isViewTransactionOnProcessPendingFeeLevel()) {
				this.setViewAction(false);
				this.searchStudentFeeTransactionsByRequest();
			} else {

				this.paymentWizardAciveStep = PaymentWizard.VIEW_TRANSACTIONS.getKey();

			}

			ViewUtil.addMessage("Transaction is approved sucessfully, proceed with next steps for payments if required.", FacesMessage.SEVERITY_INFO);

			this.viewStudentFeeTransaction();

		} catch (ApplicationException e) {
			ViewExceptionHandler.handle(e);
		}

	}

	public void rejectTransaction() {

		if (!this.isUserHavingRoleToRejectRequestedTransaction()) {

			ViewUtil.addMessage("User is not authorized to reject any transactons", FacesMessage.SEVERITY_ERROR);
			return;
		}

		try {

			if (this.studentFeeTransaction.getStudentFeeTransactionType().equals(StudentFeeTransactionTypeConstant.DEDUCT)) {

				this.studentFeeTransaction = this.studentFinancialService.rejectDeductionRequest(this.studentFeeTransaction);

			} else if (this.studentFeeTransaction.getStudentFeeTransactionType().equals(StudentFeeTransactionTypeConstant.REFUND)) {

				this.studentFeeTransaction = this.studentFinancialService.rejectRefundRequest(this.studentFeeTransaction);

			}

			if (this.isViewTransactionOnProcessPendingFeeLevel()) {
				this.setViewAction(false);
				this.searchStudentFeeTransactionsByRequest();
			} else {

				this.paymentWizardAciveStep = PaymentWizard.VIEW_TRANSACTIONS.getKey();
			}

			ViewUtil.addMessage("Transaction is approved rejected.", FacesMessage.SEVERITY_INFO);

			this.viewStudentFeeTransaction();

		} catch (ApplicationException e) {
			ViewExceptionHandler.handle(e);
		}

		this.viewStudentFeeTransaction();
	}

	public void processPendingTransaction() {

		this.viewStudentFeeTransaction();

		this.paymentWizardAciveStep = PaymentWizard.CONFIRM_AND_SUBMIT_PENDING_FEE_TRANSACTION.getKey();

		Collection<StudentFeeTransactionDetailsDO> studentFeeTransactionDetailsDOs = this.studentFeeTransactionDO.getStudentFeeTransactionDetailsDOs();

		double totalAmount = 0d;

		for (StudentFeeTransactionDetailsDO studentFeeTransactionDetailsDO : studentFeeTransactionDetailsDOs) {

			StudentFeeTransactionDetails studentFeeTransactionDetails = studentFeeTransactionDetailsDO.getStudentFeeTransactionDetails();

			totalAmount = totalAmount + studentFeeTransactionDetails.getAmount();

		}

		this.totalAmount = totalAmount;

	}

	public void confirmAndSubmitPendingTransaction() {
		try {
			if (!this.isUserHavingRoleToProcessRequestedTransaction()) {

				ViewUtil.addMessage("User is not authorized to process any pending transactons", FacesMessage.SEVERITY_ERROR);
				return;
			}

			if (this.studentFeeTransaction.getStudentFeeTransactionType().equals(StudentFeeTransactionTypeConstant.REFUND)) {

				this.studentFeeTransaction = this.studentFinancialService.processRefundRequest(this.studentFeeTransaction);

				this.studentFeeTransactionDO.getStudentFeeTransaction().setStudentFeeTransactionStatus(StudentFeeTransactionStatusConstant.REFUND_PROCESSED);

			} else if (StudentFeeTransactionTypeConstant.PAYMENT.equals(this.studentFeeTransaction.getStudentFeeTransactionType())) {

				this.studentFeeTransaction = this.studentFinancialService.processPaymentRequest(this.studentFeeTransaction);

			}
			if (this.isViewTransactionOnProcessPendingFeeLevel()) {
				this.setViewAction(false);
				this.searchStudentFeeTransactionsByRequest();
			} else {
				this.paymentWizardAciveStep = PaymentWizard.VIEW_TRANSACTIONS.getKey();
			}

			ViewUtil.addMessage("Transactions has bean processed successfully.", FacesMessage.SEVERITY_INFO);
		} catch (ApplicationException e) {
			ViewExceptionHandler.handle(e);
		}

	}

	public StudentFeeTransactionSearchCriteria getStudentFeeTransactionSearchCriteria() {
		return this.studentFeeTransactionSearchCriteria;
	}

	public void setStudentFeeTransactionSearchCriteria(final StudentFeeTransactionSearchCriteria studentFeeTransactionSearchCriteria) {
		this.studentFeeTransactionSearchCriteria = studentFeeTransactionSearchCriteria;
	}

	public void instantiateStudentFeeTransactionSearchCriteria() {
		if (this.studentFeeTransactionSearchCriteria == null) {
			this.studentFeeTransactionSearchCriteria = new StudentFeeTransactionSearchCriteria(this.sessionBean.getCurrentBranch());
		}
	}

	public void resetSearchCriteria() {
		this.studentFeeTransactionSearchCriteria.resetSeacrhCriteria();
	}

	public boolean isViewTransactionOnProcessPendingFeeLevel() {
		return this.viewTransactionOnProcessPendingFeeLevel;
	}

	public void setViewTransactionOnProcessPendingFeeLevel(final boolean viewTransactionOnProcessPendingFeeLevel) {
		this.viewTransactionOnProcessPendingFeeLevel = viewTransactionOnProcessPendingFeeLevel;
	}

	/**
	 * @return the studentFeeTransactionStatusConstantForPrint
	 */
	public StudentFeeTransactionStatusConstant getStudentFeeTransactionStatusConstantForPrint() {
		return this.studentFeeTransactionStatusConstantForPrint;
	}

	/**
	 * @param studentFeeTransactionStatusConstantForPrint
	 *            the studentFeeTransactionStatusConstantForPrint to set
	 */
	public void setStudentFeeTransactionStatusConstantForPrint(final StudentFeeTransactionStatusConstant studentFeeTransactionStatusConstantForPrint) {
		this.studentFeeTransactionStatusConstantForPrint = studentFeeTransactionStatusConstantForPrint;
	}

	/**
	 * @return the studentFeeTransactionsForPrint
	 */
	public Collection<StudentFeeTransaction> getStudentFeeTransactionsForPrint() {
		return this.studentFeeTransactionsForPrint;
	}

	/**
	 * @param studentFeeTransactionsForPrint
	 *            the studentFeeTransactionsForPrint to set
	 */
	public void setStudentFeeTransactionsForPrint(final Collection<StudentFeeTransaction> studentFeeTransactionsForPrint) {
		this.studentFeeTransactionsForPrint = studentFeeTransactionsForPrint;
	}

	public void fetchStudentFeeTransactionsForPrint() {
		if (this.studentFeeTransactionStatusConstantForPrint != null) {
			this.studentFeeTransactionsForPrint = new ArrayList<StudentFeeTransaction>();
			for (StudentFeeTransaction studentFeeTransaction : this.studentFeeTransactions) {
				if (this.studentFeeTransactionStatusConstantForPrint.equals(studentFeeTransaction.getStudentFeeTransactionStatus())) {
					this.studentFeeTransactionsForPrint.add(studentFeeTransaction);
				}
			}
		} else {
			this.studentFeeTransactionsForPrint = this.studentFeeTransactions;
		}
	}
}
