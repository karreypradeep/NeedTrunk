/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 * 
 */
package com.apeironsol.need.financial.portal;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.Resource;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.BuildingBlock;
import com.apeironsol.need.core.portal.AbstractStudentBean;
import com.apeironsol.need.core.portal.AbstractTabbedBean;
import com.apeironsol.need.core.service.StudentPocketMoneyService;
import com.apeironsol.need.financial.model.StudentPocketMoneyTransaction;
import com.apeironsol.need.util.constants.PaymentMethodConstant;
import com.apeironsol.need.util.constants.ResidenceConstant;
import com.apeironsol.need.util.constants.StudentPocketMoneyTransactionTypeConstant;
import com.apeironsol.need.util.portal.ViewExceptionHandler;
import com.apeironsol.framework.exception.ApplicationException;

/**
 * JSF managed for financial income.
 * 
 * @author Pradeep
 */
@Named
@Scope(value = "session")
public class StudentPocketMoneyBean extends AbstractTabbedBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long							serialVersionUID	= -2819735994600601845L;

	/**
	 * student pocket money transaction.
	 */
	private StudentPocketMoneyTransaction				studentPocketMoneyTransaction;

	/**
	 * student pocket money transaction.
	 */
	private Collection<StudentPocketMoneyTransaction>	studentPocketMoneyTransactionsByStudentSection;

	/**
	 * Branch income service.
	 */
	@Resource
	private StudentPocketMoneyService					studentPocketMoneyService;

	@Resource
	private AbstractStudentBean									studentBean;

	/**
	 * Building blocks for type incomes.
	 */
	private Collection<BuildingBlock>					incomesTypeBuildingBlocks;

	private double										balancePocketMoney;

	/**
	 * @return the incomesTypeBuildingBlocks
	 */
	public Collection<BuildingBlock> getIncomesTypeBuildingBlocks() {
		return this.incomesTypeBuildingBlocks;
	}

	/**
	 * @param incomesTypeBuildingBlocks
	 *            the incomesTypeBuildingBlocks to set
	 */
	public void setIncomesTypeBuildingBlocks(final Collection<BuildingBlock> incomesTypeBuildingBlocks) {
		this.incomesTypeBuildingBlocks = incomesTypeBuildingBlocks;
	}

	@Override
	public void onTabChange() {
		this.setViewOrNewAction(false);
		this.getStudentPocketMoneyTransactionsForCurrentStudent();
	}

	/**
	 * Saves academic year Income to database.
	 */
	public String saveStudentPocketMoneyTransaction() {
		try {
			this.studentPocketMoneyTransaction = this.studentPocketMoneyService.saveStudentPocketMoneyTransaction(this.studentPocketMoneyTransaction);
			this.setViewOrNewAction(false);
			this.getStudentPocketMoneyTransactionsForCurrentStudent();
		} catch (ApplicationException ex) {
			ViewExceptionHandler.handle(ex);
		}
		return null;
	}

	/**
	 * Removes academic year Income from database.
	 */
	public String removeStudentPocketMoneyTransaction() {
		this.studentPocketMoneyService.removeStudentPocketMoneyTransaction(this.studentPocketMoneyTransaction);
		this.setViewOrNewAction(false);
		this.getStudentPocketMoneyTransactionsForCurrentStudent();
		return null;
	}

	/**
	 * Removes academic year Income from database.
	 */
	public String calcelAction() {
		this.setViewOrNewAction(false);
		return null;
	}

	/**
	 * Removes academic year Income from database.
	 */
	public String viewStudentPocketMoneyTransaction() {
		this.setViewOrNewAction(true);
		return null;
	}

	/**
	 * Retrieve branch incomes for academic year and current branch.
	 * 
	 * @param academicYear
	 *            academic year.
	 * @return
	 */
	public void getStudentPocketMoneyTransactionsForCurrentStudent() {
		this.studentPocketMoneyTransactionsByStudentSection = this.studentPocketMoneyService
				.findStudentPocketMoneyTransactionByStudentSectionId(this.studentBean.getStudentSection().getId());
		this.calculateBalancePocketMoney();
	}

	/**
	 * @return the studentPocketMoneyTransaction
	 */
	public StudentPocketMoneyTransaction getStudentPocketMoneyTransaction() {
		return this.studentPocketMoneyTransaction;
	}

	/**
	 * @param studentPocketMoneyTransaction
	 *            the studentPocketMoneyTransaction to set
	 */
	public void setStudentPocketMoneyTransaction(final StudentPocketMoneyTransaction studentPocketMoneyTransaction) {
		this.studentPocketMoneyTransaction = studentPocketMoneyTransaction;
	}

	/**
	 * @return the studentPocketMoneyTransactionsByStudentSection
	 */
	public Collection<StudentPocketMoneyTransaction> getStudentPocketMoneyTransactionsByStudentSection() {
		return this.studentPocketMoneyTransactionsByStudentSection;
	}

	/**
	 * @param studentPocketMoneyTransactionsByStudentSection
	 *            the studentPocketMoneyTransactionsByStudentSection to set
	 */
	public void setStudentPocketMoneyTransactionsByStudentSection(final Collection<StudentPocketMoneyTransaction> studentPocketMoneyTransactionsByStudentSection) {
		this.studentPocketMoneyTransactionsByStudentSection = studentPocketMoneyTransactionsByStudentSection;
	}

	public PaymentMethodConstant[] getPaymentMethodsConstants() {
		PaymentMethodConstant[] paymentMethodConstants;
		if (StudentPocketMoneyTransactionTypeConstant.WITHDRAW.equals(this.studentPocketMoneyTransaction.getStudentPocketMoneyTransactionTypeConstant())) {
			paymentMethodConstants = new PaymentMethodConstant[1];
			paymentMethodConstants[0] = PaymentMethodConstant.CASH;
		} else {
			paymentMethodConstants = new PaymentMethodConstant[2];
			paymentMethodConstants[0] = PaymentMethodConstant.CASH;
			paymentMethodConstants[1] = PaymentMethodConstant.CHEQUE;
		}
		return paymentMethodConstants;
	}

	public boolean isDisplayBankDetailsForPaymentTypeCheque() {
		return PaymentMethodConstant.CHEQUE.equals(this.studentPocketMoneyTransaction.getPaymentMethod());
	}

	public boolean isDisplayPaymentMode() {
		return StudentPocketMoneyTransactionTypeConstant.DEPOSIT.equals(this.studentPocketMoneyTransaction.getStudentPocketMoneyTransactionTypeConstant());
	}

	public String processPocketMoneyDeposit() {
		this.newStudentPocketMoneyTransaction();
		this.studentPocketMoneyTransaction.setStudentPocketMoneyTransactionTypeConstant(StudentPocketMoneyTransactionTypeConstant.DEPOSIT);
		return null;
	}

	public String processPocketMoneyWithDraw() {
		this.newStudentPocketMoneyTransaction();
		this.studentPocketMoneyTransaction.setStudentPocketMoneyTransactionTypeConstant(StudentPocketMoneyTransactionTypeConstant.WITHDRAW);
		this.studentPocketMoneyTransaction.setPaymentMethod(PaymentMethodConstant.CASH);
		return null;
	}

	/**
	 * Create new academic year Income.
	 */
	public void newStudentPocketMoneyTransaction() {
		this.studentPocketMoneyTransaction = new StudentPocketMoneyTransaction();
		this.studentPocketMoneyTransaction.setStudentSection(this.studentBean.getStudentSection());
		this.setViewOrNewAction(true);
	}

	public void calculateBalancePocketMoney() {
		double depositedMoney = 0.0, withDrawnMoney = 0.0;
		for (StudentPocketMoneyTransaction pocketMoneyTrans : this.studentPocketMoneyTransactionsByStudentSection) {
			if (StudentPocketMoneyTransactionTypeConstant.DEPOSIT.equals(pocketMoneyTrans.getStudentPocketMoneyTransactionTypeConstant())) {
				depositedMoney += pocketMoneyTrans.getAmount();
			} else {
				withDrawnMoney += pocketMoneyTrans.getAmount();
			}
		}
		this.balancePocketMoney = depositedMoney - withDrawnMoney;
	}

	/**
	 * @return the balancePocketMoney
	 */
	public double getBalancePocketMoney() {
		return this.balancePocketMoney;
	}

	/**
	 * @param balancePocketMoney
	 *            the balancePocketMoney to set
	 */
	public void setBalancePocketMoney(final double balancePocketMoney) {
		this.balancePocketMoney = balancePocketMoney;
	}

	public boolean isRenderWithDrawButton() {
		return this.balancePocketMoney > 0;
	}

	public boolean isRenderPocketMoneyTab() {
		return this.studentBean.getStudent().getResidence().equals(ResidenceConstant.HOSTEL);
	}

}
