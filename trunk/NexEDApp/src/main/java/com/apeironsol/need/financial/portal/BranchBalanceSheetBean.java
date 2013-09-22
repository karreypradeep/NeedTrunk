/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.financial.portal;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.portal.AbstractTabbedBean;
import com.apeironsol.need.financial.dataobject.BalanceSheetElementDO;
import com.apeironsol.need.financial.dataobject.BalanceSheetElementTransaction;
import com.apeironsol.need.financial.model.BranchBalanceSheet;
import com.apeironsol.need.financial.service.BranchBalanceSheetService;
import com.apeironsol.need.util.DateUtil;
import com.apeironsol.need.util.portal.BranchBalanceSheetTreeNode;
import com.apeironsol.need.util.portal.ViewUtil;

/**
 * 
 * @author pradeep
 *         BranchBalanceSheet portal bean.
 * 
 */
@Named
@Scope(value = "session")
public class BranchBalanceSheetBean extends AbstractTabbedBean implements Serializable {

	/**
	 * 
	 */
	private static final long					serialVersionUID			= 7724059172985084306L;

	private Collection<BranchBalanceSheet>		branchBalanceSheets;

	private boolean								loadBrachBalanceSheetInd;

	@Resource
	private BranchBalanceSheetService			branchBalanceSheetService;

	private BranchBalanceSheet					currentBranchBalanceSheet;

	private BranchBalanceSheet					nonProcessedBranchBalanceSheet;

	private Collection<BalanceSheetElementDO>	branchBalanceSheetIncomeElements;

	private Collection<BalanceSheetElementDO>	branchBalanceSheetExpenseElements;

	private boolean								brachBalanceSheetVisibleInd;

	private Double								currentClosingBalance		= 0.0;

	private Double								currentTotalCreditAmount	= 0.0;

	private Double								currentTotalDebitAmount		= 0.0;

	/**
	 * Root node for tree.
	 */
	private final TreeNode						root						= new DefaultTreeNode("Fee collected and due", null);

	/**
	 * @return the branchBalanceSheets
	 */
	public Collection<BranchBalanceSheet> getBranchBalanceSheets() {
		return this.branchBalanceSheets;
	}

	/**
	 * @param branchBalanceSheets
	 *            the branchBalanceSheets to set
	 */
	public void setBranchBalanceSheets(final Collection<BranchBalanceSheet> branchBalanceSheets) {
		this.branchBalanceSheets = branchBalanceSheets;
	}

	@Override
	public void onTabChange() {
	}

	public void loadBranchBalanceSheets() {
		if (this.isLoadBrachBalanceSheetInd()) {
			this.setBranchBalanceSheets(this.branchBalanceSheetService.findBranchBalanceSheetsByBranchId(this.sessionBean.getCurrentBranch().getId()));
			this.nonProcessedBranchBalanceSheet = this.branchBalanceSheetService.findActiveBranchBalanceSheetByBranchId(this.sessionBean.getCurrentBranch()
					.getId());
			this.loadBrachBalanceSheetInd = false;
		}
	}

	/**
	 * @return the loadBrachBankAccountInd
	 */
	public boolean isLoadBrachBalanceSheetInd() {
		return this.loadBrachBalanceSheetInd;
	}

	/**
	 * @param loadBrachBankAccountInd
	 *            the loadBrachBankAccountInd to set
	 */
	public void setLoadBrachBalanceSheetInd(final boolean loadBrachBalanceSheetInd) {
		this.loadBrachBalanceSheetInd = loadBrachBalanceSheetInd;
	}

	public String newBranchBalanceSheet() {
		this.currentBranchBalanceSheet = new BranchBalanceSheet();
		this.currentBranchBalanceSheet.setBranch(this.sessionBean.getCurrentBranch());
		this.currentBranchBalanceSheet.setStartDate(DateUtil.getSystemDate());
		this.currentBranchBalanceSheet.setBalanceSheetClosedIndicator(false);
		final BranchBalanceSheet latestBranchBalanceSheet = this.branchBalanceSheetService.findLatestBranchBalanceSheetByBranchIdAndProcessedState(
				this.sessionBean.getCurrentBranch().getId(), true);
		if (latestBranchBalanceSheet != null) {
			this.currentBranchBalanceSheet.setOpeningBalance(latestBranchBalanceSheet.getClosingBalance());
			this.currentBranchBalanceSheet.setPreviousBranchBalanceSheet(latestBranchBalanceSheet);
		}
		this.brachBalanceSheetVisibleInd = false;
		this.currentClosingBalance = null;
		return null;
	}

	/**
	 * @return the currentBranchBalanceSheet
	 */
	public BranchBalanceSheet getCurrentBranchBalanceSheet() {
		return this.currentBranchBalanceSheet;
	}

	/**
	 * @param currentBranchBalanceSheet
	 *            the currentBranchBalanceSheet to set
	 */
	public void setCurrentBranchBalanceSheet(final BranchBalanceSheet currentBranchBalanceSheet) {
		this.currentBranchBalanceSheet = currentBranchBalanceSheet;
	}

	/**
	 * Saves academic year BankAccount to database.
	 */
	public String saveBranchBalanceSheet() {
		try {
			if (!this.currentBranchBalanceSheet.getBalanceSheetClosedIndicator()) {
				this.currentBranchBalanceSheet.setClosingBalance(null);
			}
			this.currentBranchBalanceSheet = this.branchBalanceSheetService.saveBranchBalanceSheet(this.currentBranchBalanceSheet);
			this.viewBranchBalanceSheet();
			ViewUtil.addMessage("Balance sheet saved successfully.", FacesMessage.SEVERITY_INFO);
		} catch (final Exception ex) {
			ViewUtil.addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
		// this.setViewOrNewAction(false);
		this.setLoadBrachBalanceSheetInd(true);
		return null;
	}

	/**
	 * Saves academic year BankAccount to database.
	 */
	public String processBranchBalanceSheet() {
		try {
			this.currentBranchBalanceSheet.setClosingBalance(this.currentClosingBalance);
			this.currentBranchBalanceSheet = this.branchBalanceSheetService.processBranchBalanceSheet(this.currentBranchBalanceSheet);
			this.viewBranchBalanceSheet();
			ViewUtil.addMessage("Balance sheet processed successfully.", FacesMessage.SEVERITY_INFO);
			this.setViewOrNewAction(false);
			this.setLoadBrachBalanceSheetInd(true);
		} catch (final Exception ex) {
			ViewUtil.addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
		return null;
	}

	/**
	 * Saves academic year BankAccount to database.
	 */
	public String openClosedBranchBalanceSheet() {
		try {
			this.currentBranchBalanceSheet = this.branchBalanceSheetService.openClosedBranchBalanceSheet(this.currentBranchBalanceSheet);
			this.viewBranchBalanceSheet();
			ViewUtil.addMessage("Balance sheet opened successfully.", FacesMessage.SEVERITY_INFO);
		} catch (final Exception ex) {
			ViewUtil.addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
		return null;
	}

	/**
	 * Saves academic year BankAccount to database.
	 */
	public String getBranchBalanceSheetDetails() {
		try {
			if (this.currentBranchBalanceSheet.getId() == null) {
				final BranchBalanceSheet branchBalanceSheet = this.branchBalanceSheetService.findBranchBalanceSheetByBranchIdAndDate(this.sessionBean
						.getCurrentBranch().getId(), this.currentBranchBalanceSheet.getStartDate());
				if (branchBalanceSheet != null) {
					ViewUtil.addMessage("Balance sheet exists for the start date.", FacesMessage.SEVERITY_ERROR);
					return null;
				}
			}
			this.viewBranchBalanceSheet();
			this.brachBalanceSheetVisibleInd = true;
		} catch (final Exception ex) {
			ViewUtil.addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
		return null;
	}

	/**
	 * Removes academic year BankAccount from database.
	 */
	public String removeBranchBalanceSheet() {
		try {
			this.branchBalanceSheetService.removeBranchBalanceSheet(this.currentBranchBalanceSheet);
			ViewUtil.addMessage("Bank account removed successfully.", FacesMessage.SEVERITY_INFO);
		} catch (final Exception ex) {
			ViewUtil.addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
		this.setViewOrNewAction(false);
		this.setLoadBrachBalanceSheetInd(true);
		return null;
	}

	/**
	 * Removes academic year BankAccount from database.
	 */
	public String calcelAction() {
		this.setViewOrNewAction(false);
		return null;
	}

	/**
	 * Removes academic year BankAccount from database.
	 */
	public String viewBranchBalanceSheet() {
		this.setViewOrNewAction(true);
		this.setActiveTabIndex(0);
		this.setCurrentTotalCreditAmount(0.0);
		this.setCurrentTotalDebitAmount(0.0);
		Date endDate = DateUtil.getSystemDate();
		this.removeAllChildsOfRootNode(this.root);
		if (this.currentBranchBalanceSheet.getEndDate() != null) {
			endDate = this.currentBranchBalanceSheet.getEndDate();
		}
		this.setBranchBalanceSheetIncomeElements(this.branchBalanceSheetService.getBranchBalanceSheetIncomeElements(
				this.sessionBean.getCurrentBranch().getId(), this.currentBranchBalanceSheet.getStartDate(), endDate));

		this.setBranchBalanceSheetExpenseElements(this.branchBalanceSheetService.getBranchBalanceSheetExpenseElements(this.sessionBean.getCurrentBranch()
				.getId(), this.currentBranchBalanceSheet.getStartDate(), endDate));
		if (!this.currentBranchBalanceSheet.getBalanceSheetClosedIndicator()) {
			this.currentClosingBalance = this.currentBranchBalanceSheet.getOpeningBalance();
			for (final BalanceSheetElementDO balanceSheetElementDO : this.branchBalanceSheetIncomeElements) {
				this.currentClosingBalance += balanceSheetElementDO.getAmount();
			}

			for (final BalanceSheetElementDO balanceSheetElementDO : this.branchBalanceSheetExpenseElements) {
				this.currentClosingBalance -= balanceSheetElementDO.getAmount();
			}
		} else {
			this.currentClosingBalance = this.currentBranchBalanceSheet.getClosingBalance();
		}
		this.brachBalanceSheetVisibleInd = this.currentBranchBalanceSheet.getId() != null;
		final BranchBalanceSheetTreeNode branchFeeCollectedTreeNodeIncome = new BranchBalanceSheetTreeNode();
		branchFeeCollectedTreeNodeIncome.setDescription("Credit");
		final TreeNode incomeTreeNode = new DefaultTreeNode(branchFeeCollectedTreeNodeIncome, this.root);
		double incomeTotal = 0.0, incomeElementTotal = 0.0;
		for (final BalanceSheetElementDO balanceSheetElementDO : this.branchBalanceSheetIncomeElements) {
			incomeElementTotal = 0.0;
			final BranchBalanceSheetTreeNode balanceSheetElementDONode = new BranchBalanceSheetTreeNode();
			balanceSheetElementDONode.setDescription(balanceSheetElementDO.getDescription());
			final TreeNode incomeElementNode = new DefaultTreeNode(balanceSheetElementDONode, incomeTreeNode);
			incomeElementNode.setExpanded(false);
			if (balanceSheetElementDO.getBalanceSheetElementTransactions() != null) {
				for (final BalanceSheetElementTransaction balanceSheetElementTransaction : balanceSheetElementDO.getBalanceSheetElementTransactions()) {
					incomeElementTotal = incomeElementTotal + balanceSheetElementTransaction.getTransactionAmount();
					final BranchBalanceSheetTreeNode balanceSheetElementTransactionNode = new BranchBalanceSheetTreeNode();
					balanceSheetElementTransactionNode.setDescription(balanceSheetElementTransaction.getDescription());
					balanceSheetElementTransactionNode.setCreditAmount(balanceSheetElementTransaction.getTransactionAmount());
					balanceSheetElementTransactionNode.setDate(balanceSheetElementTransaction.getTransactionDate());
					final TreeNode incomeElementTxnNode = new DefaultTreeNode(balanceSheetElementTransactionNode, incomeElementNode);
					incomeElementTxnNode.setExpanded(false);
				}
				balanceSheetElementDONode.setCreditAmount(incomeElementTotal);
			}
			incomeTotal = incomeTotal + incomeElementTotal;
		}
		branchFeeCollectedTreeNodeIncome.setCreditAmount(incomeTotal);
		this.setCurrentTotalCreditAmount(incomeTotal);
		final BranchBalanceSheetTreeNode branchFeeCollectedTreeNodeExpenses = new BranchBalanceSheetTreeNode();
		branchFeeCollectedTreeNodeExpenses.setDescription("Debit");
		final TreeNode expenseTreeNode = new DefaultTreeNode(branchFeeCollectedTreeNodeExpenses, this.root);
		double expenseTotal = 0.0, expenseElementTotal = 0.0;
		for (final BalanceSheetElementDO balanceSheetElementDO : this.branchBalanceSheetExpenseElements) {
			expenseElementTotal = 0.0;
			final BranchBalanceSheetTreeNode balanceSheetElementDONode = new BranchBalanceSheetTreeNode();
			balanceSheetElementDONode.setDescription(balanceSheetElementDO.getDescription());
			balanceSheetElementDONode.setDebitAmount(balanceSheetElementDO.getAmount());
			final TreeNode expenseElementNode = new DefaultTreeNode(balanceSheetElementDONode, expenseTreeNode);
			expenseElementNode.setExpanded(false);
			if (balanceSheetElementDO.getBalanceSheetElementTransactions() != null) {
				for (final BalanceSheetElementTransaction balanceSheetElementTransaction : balanceSheetElementDO.getBalanceSheetElementTransactions()) {
					expenseElementTotal = expenseElementTotal + balanceSheetElementTransaction.getTransactionAmount();
					final BranchBalanceSheetTreeNode balanceSheetElementTransactionNode = new BranchBalanceSheetTreeNode();
					balanceSheetElementTransactionNode.setDescription(balanceSheetElementTransaction.getDescription());
					balanceSheetElementTransactionNode.setDebitAmount(balanceSheetElementTransaction.getTransactionAmount());
					balanceSheetElementTransactionNode.setDate(balanceSheetElementTransaction.getTransactionDate());
					final TreeNode expenseElementTxnNode = new DefaultTreeNode(balanceSheetElementTransactionNode, expenseElementNode);
					expenseElementTxnNode.setExpanded(false);
				}
				balanceSheetElementDONode.setDebitAmount(expenseElementTotal);
			}
			expenseTotal = expenseTotal + expenseElementTotal;
		}
		branchFeeCollectedTreeNodeExpenses.setDebitAmount(expenseTotal);
		this.setCurrentTotalDebitAmount(expenseTotal);
		return null;
	}

	/**
	 * If balance sheet which is not processed already exists then do not enable
	 * new balance sheet button.
	 * 
	 * @return
	 */
	public boolean isNewBalanceSheetButtonDisabled() {
		return this.nonProcessedBranchBalanceSheet != null;
	}

	/**
	 * If balance sheet which is not processed already exists then do not enable
	 * new balance sheet button.
	 * 
	 * @return
	 */
	public boolean isOpeningBalanceDisabled() {
		return this.branchBalanceSheets != null && !this.branchBalanceSheets.isEmpty();
	}

	/**
	 * @return the branchBalanceSheetIncomeElements
	 */
	public Collection<BalanceSheetElementDO> getBranchBalanceSheetIncomeElements() {
		return this.branchBalanceSheetIncomeElements;
	}

	/**
	 * @param branchBalanceSheetIncomeElements
	 *            the branchBalanceSheetIncomeElements to set
	 */
	public void setBranchBalanceSheetIncomeElements(final Collection<BalanceSheetElementDO> branchBalanceSheetIncomeElements) {
		this.branchBalanceSheetIncomeElements = branchBalanceSheetIncomeElements;
	}

	/**
	 * @return the branchBalanceSheetExpenseElements
	 */
	public Collection<BalanceSheetElementDO> getBranchBalanceSheetExpenseElements() {
		return this.branchBalanceSheetExpenseElements;
	}

	/**
	 * @param branchBalanceSheetExpenseElements
	 *            the branchBalanceSheetExpenseElements to set
	 */
	public void setBranchBalanceSheetExpenseElements(final Collection<BalanceSheetElementDO> branchBalanceSheetExpenseElements) {
		this.branchBalanceSheetExpenseElements = branchBalanceSheetExpenseElements;
	}

	public boolean isBrachBalanceSheetVisibleInd() {
		return this.brachBalanceSheetVisibleInd;
	}

	public void setBrachBalanceSheetVisibleInd(final boolean brachBalanceSheetVisibleInd) {
		this.brachBalanceSheetVisibleInd = brachBalanceSheetVisibleInd;
	}

	public Double getCurrentClosingBalance() {
		return this.currentClosingBalance;
	}

	public void setCurrentClosingBalance(final Double currentClosingBalance) {
		this.currentClosingBalance = currentClosingBalance;
	}

	public TreeNode getRoot() {
		return this.root;
	}

	/**
	 * Removes all child nodes of the supplied root node.
	 */
	private void removeAllChildsOfRootNode(final TreeNode rootNode) {
		if (rootNode != null && rootNode.getChildCount() > 0) {
			final TreeNode[] array = rootNode.getChildren().toArray(new TreeNode[rootNode.getChildCount()]);
			for (TreeNode child : array) {
				child.setParent(null);
				child = null;
			}
		}
	}

	/**
	 * @return the currentTotalCreditAmount
	 */
	public Double getCurrentTotalCreditAmount() {
		return this.currentTotalCreditAmount;
	}

	/**
	 * @param currentTotalCreditAmount
	 *            the currentTotalCreditAmount to set
	 */
	public void setCurrentTotalCreditAmount(final Double currentTotalCreditAmount) {
		this.currentTotalCreditAmount = currentTotalCreditAmount;
	}

	/**
	 * @return the currentTotalDebitAmount
	 */
	public Double getCurrentTotalDebitAmount() {
		return this.currentTotalDebitAmount;
	}

	/**
	 * @param currentTotalDebitAmount
	 *            the currentTotalDebitAmount to set
	 */
	public void setCurrentTotalDebitAmount(final Double currentTotalDebitAmount) {
		this.currentTotalDebitAmount = currentTotalDebitAmount;
	}

}
