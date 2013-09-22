/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.financial.portal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.financial.model.BranchLevelFee;
import com.apeironsol.need.financial.model.BranchLevelFeeCatalog;
import com.apeironsol.need.financial.service.BranchLevelFeeService;
import com.apeironsol.need.util.comparator.BranchLevelFeeCatalogComparator;
import com.apeironsol.need.util.constants.PaymentFrequencyConstant;
import com.apeironsol.need.util.dataobject.AcademicYearBranchLevelFeeDO;
import com.apeironsol.need.util.portal.BranchLevelFeeTreeNode;
import com.apeironsol.need.util.portal.ViewExceptionHandler;
import com.apeironsol.need.util.portal.ViewUtil;
import com.apeironsol.framework.exception.ApplicationException;

@Named
@Scope(value = "session")
public class BranchLevelFeeBean extends AbstractFinancialBean {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long							serialVersionUID		= -8396902889304389271L;

	/**
	 * Logger for branch fee bean.
	 */
	private static final Logger							log						= Logger.getLogger(BranchLevelFeeBean.class);

	@Resource
	private BranchLevelFeeService						branchLevelFeeService;

	/**
	 * Branch level fee.
	 */
	private BranchLevelFee								branchLevelFee;

	/**
	 * Boolean which indicates if branch level fees have to loaded from
	 * database.
	 */
	private boolean										loadBranchLevelFees		= false;

	/**
	 * Collection of branch level fee payment definitions defined for the branch
	 * level fee.
	 */
	private Collection<BranchLevelFeeCatalog>			branchLevelFeeCatalogs	= new ArrayList<BranchLevelFeeCatalog>();

	/**
	 * Holds number of payments defined for the payment frequency type
	 * custom for the branch level fee.
	 */
	private int											noOfPayments;

	/**
	 * Boolean, True if payment frequency type custom.
	 */
	private boolean										displayNoOfPayments;

	/**
	 * 
	 */
	private boolean										loadAcademicYears;

	/**
	 * 
	 */
	private Collection<BranchLevelFee>					branchLevelFees;

	/**
	 * Academic Year branch level DO.
	 */
	private AcademicYearBranchLevelFeeDO				academicYearBranchLevelFeeDO;

	/**
	 * Collection of academic year branch level DOs.
	 */
	private Collection<AcademicYearBranchLevelFeeDO>	academicYearBranchLevelFeeDOs;

	/**
	 * Load Academic year branch level data objects.
	 */
	private boolean										loadAcademicYearBranchLevelFeeDOsFlag;

	/**
	 * Root node for tree.
	 */
	private BranchLevelFeeTreeNode						root					= new BranchLevelFeeTreeNode("BranchLevelFeeTableRoot", null);

	@PostConstruct
	public void init() {
		this.reset();
	}

	public void reset() {
		this.branchLevelFee = new BranchLevelFee();
		this.branchLevelFeeCatalogs = new ArrayList<BranchLevelFeeCatalog>();
	}

	@Override
	public void onTabChange() {
		// In case of tabs tab implementations goes here.
	}

	public BranchLevelFeeTreeNode getRoot() {
		return this.root;
	}

	public void setRoot(final BranchLevelFeeTreeNode root) {
		this.root = root;
	}

	public Collection<AcademicYearBranchLevelFeeDO> getAcademicYearBranchLevelFeeDOs() {
		return this.academicYearBranchLevelFeeDOs;
	}

	public void setAcademicYearBranchLevelFeeDOs(final Collection<AcademicYearBranchLevelFeeDO> academicYearBranchLevelFeeDOs) {
		this.academicYearBranchLevelFeeDOs = academicYearBranchLevelFeeDOs;
	}

	public boolean isLoadAcademicYearBranchLevelFeeDOsFlag() {
		return this.loadAcademicYearBranchLevelFeeDOsFlag;
	}

	public void setLoadAcademicYearBranchLevelFeeDOsFlag(final boolean loadAcademicYearBranchLevelFeeDOsFlag) {
		this.loadAcademicYearBranchLevelFeeDOsFlag = loadAcademicYearBranchLevelFeeDOsFlag;
	}

	public Collection<BranchLevelFee> getBranchLevelFees() {
		return this.branchLevelFees;
	}

	public BranchLevelFee getBranchLevelFee() {
		return this.branchLevelFee;
	}

	public void setBranchLevelFee(final BranchLevelFee branchLevelFee) {
		this.branchLevelFee = branchLevelFee;
	}

	public boolean isLoadBranchLevelFees() {
		return this.loadBranchLevelFees;
	}

	public void setLoadBranchLevelFees(final boolean loadBranchLevelFees) {
		this.loadBranchLevelFees = loadBranchLevelFees;
	}

	public Collection<BranchLevelFeeCatalog> getBranchLevelFeeCatalogs() {
		return this.branchLevelFeeCatalogs;
	}

	public void setBranchLevelFeeCatalogs(final Collection<BranchLevelFeeCatalog> branchLevelFeeCatalogs) {
		this.branchLevelFeeCatalogs = branchLevelFeeCatalogs;
	}

	public int getNoOfPayments() {
		return this.noOfPayments;
	}

	public void setNoOfPayments(final int noOfPayments) {
		this.noOfPayments = noOfPayments;
	}

	/**
	 * Saves branch level fee to database.
	 */
	public void saveBranchLevelFee() {
		try {

			this.branchLevelFee.setBranch(this.sessionBean.getCurrentBranch());

			this.branchLevelFee.setBranchLevelFeeCatalogs(this.branchLevelFeeCatalogs);

			if (this.isNewAction()) {
				this.branchLevelFee = this.branchLevelFeeService.saveBranchLevelFee(this.branchLevelFee);
			}

			if (this.isViewAction()) {
				this.branchLevelFee = this.branchLevelFeeService.updateBranchLevelFee(this.branchLevelFee);
			}

			ViewUtil.addMessage("Branch Fee is saved sucessfully.", FacesMessage.SEVERITY_INFO);

			this.setNewAction(false);

			this.setViewAction(false);

			this.loadAcademicYearBranchLevelFeeDOsFlag = true;

		} catch (ApplicationException exception) {
			log.info(exception.getMessage(), exception);
			ViewExceptionHandler.handle(exception);
		}
		this.loadBranchLevelFees = true;
	}

	/**
	 * Saves branch level fee to database.
	 */
	public void applyToExistingStudents() {
		try {

			this.branchLevelFee.setBranch(this.sessionBean.getCurrentBranch());

			this.branchLevelFeeService.applyBranchLevelFeeToExistingActiveStudents(this.branchLevelFee);

			ViewUtil.addMessage("Branch Fee applied sucessfully.", FacesMessage.SEVERITY_INFO);

		} catch (ApplicationException exception) {
			log.info(exception.getMessage(), exception);
			ViewExceptionHandler.handle(exception);
		}
	}

	/**
	 * Removes branch level fee from database.
	 */
	public void removeBranchLevelFee() {
		try {

			this.branchLevelFeeService.removeBranchLevelFee(this.branchLevelFee);

			this.loadAcademicYearBranchLevelFeeDOsFlag = true;

			ViewUtil.addMessage(this.getFeeTypeName() + " period removed successfully.", FacesMessage.SEVERITY_INFO);
		} catch (ApplicationException exception) {
			ViewExceptionHandler.handle(exception);
		}
		this.loadBranchLevelFees = true;
	}

	/**
	 * Returns name for the fee type from building block.
	 * 
	 * @return name for the fee type from building block.
	 */
	public String getFeeTypeName() {
		return this.branchLevelFee.getBuildingBlock() != null ? this.branchLevelFee.getBuildingBlock().getName() : "";
	}

	/**
	 * Loads branch fee types from database.
	 */
	public void loadBranchLevelFees() {
		try {
			if (this.loadBranchLevelFees) {

				this.setBranchLevelFees(this.branchLevelFeeService.findBranchLevelFeesByBranchId(this.sessionBean.getCurrentBranch().getId()));

				this.loadBranchLevelFees = false;
			}
		} catch (Exception ex) {
			ViewUtil.addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}

	/**
	 * Loads branch fee types from database.
	 */
	public void loadAcademicYearBranchLevelFeeDOs() {
		try {
			if (this.loadAcademicYearBranchLevelFeeDOsFlag) {

				this.academicYearBranchLevelFeeDOs = this.branchLevelFeeService.findAllAcademicYearBranchLevelFeesByBranchId(this.sessionBean
						.getCurrentBranch().getId());

				this.buildBranchLevelFeeTreeTable();

				this.loadAcademicYearBranchLevelFeeDOsFlag = false;
			}
		} catch (Exception ex) {
			ViewUtil.addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}

	/**
	 * Loads branch level fee payment definitions from the database for the
	 * current
	 * branch level fee.
	 */
	public String loadBranchLevelFeeCatalogs() {

		this.branchLevelFeeCatalogs = this.branchLevelFeeService.findAllBranchLevelFeeCatalogsByBranchLevelFeeId(this.branchLevelFee.getId());

		// Sort payments by payment date.
		Collections.sort((List<BranchLevelFeeCatalog>) this.branchLevelFeeCatalogs, new BranchLevelFeeCatalogComparator(
				BranchLevelFeeCatalogComparator.DUE_DATE));

		if (PaymentFrequencyConstant.CUSTOM.equals(this.branchLevelFee.getPaymentFrequency())) {
			this.noOfPayments = this.branchLevelFeeCatalogs.size();
			this.displayNoOfPayments = true;
		} else {
			this.noOfPayments = 0;
			this.displayNoOfPayments = false;
		}

		return null;
	}

	/**
	 * Convenient method to create branch level fee catalogs based on the
	 * payment frequency selected.
	 */
	public void generateBranchLevelFeeCatalogs() {

		if (this.branchLevelFee == null || this.branchLevelFee.getAcademicYear() == null || this.branchLevelFee.getAmount() == null
				|| this.branchLevelFee.getAmount() <= 0 || this.branchLevelFee.getPaymentFrequency() == null) {
			return;
		}

		this.branchLevelFeeCatalogs.clear();

		AcademicYear academicYear = this.branchLevelFee.getAcademicYear();

		int durationInDays = academicYear.getDays();

		if (PaymentFrequencyConstant.ONCE.equals(this.branchLevelFee.getPaymentFrequency())) {
			this.branchLevelFeeCatalogs.add(this.createBranchLevelFeeCatalog(academicYear.getStartDate(), this.branchLevelFee.getAmount()));

		} else if (PaymentFrequencyConstant.TWICE.equals(this.branchLevelFee.getPaymentFrequency())) {

			this.processPayments(durationInDays, 2, this.branchLevelFee.getAmount());

		} else if (PaymentFrequencyConstant.THRICE.equals(this.branchLevelFee.getPaymentFrequency())) {

			this.processPayments(durationInDays, 3, this.branchLevelFee.getAmount());

		} else if (PaymentFrequencyConstant.EVERY_MONTH.equals(this.branchLevelFee.getPaymentFrequency())) {

			this.processPayments(durationInDays, academicYear.getMonths(), this.branchLevelFee.getAmount());

		} else if (PaymentFrequencyConstant.CUSTOM.equals(this.branchLevelFee.getPaymentFrequency())) {

			this.processPayments(durationInDays, this.noOfPayments, this.branchLevelFee.getAmount());

		}

	}

	public void processPayments(final int durationInDays, final int numberOfPayments, final Double amount) {

		double splitAmount = Math.round(amount / numberOfPayments);

		double lastPaymentCorrectionValue = 0;

		double totalAmount = splitAmount * numberOfPayments;

		if (!amount.equals(totalAmount)) {
			lastPaymentCorrectionValue = amount - totalAmount;
		}

		int nextPaymentDurationInDays = durationInDays / numberOfPayments;

		Date paymentDate = this.branchLevelFee.getAcademicYear().getStartDate();

		DateTime nextPaymentDate = new DateTime(paymentDate.getTime());

		for (int i = 0; i < numberOfPayments; i++) {

			if (i == numberOfPayments - 1) {
				this.branchLevelFeeCatalogs.add(this.createBranchLevelFeeCatalog(paymentDate, splitAmount + lastPaymentCorrectionValue));
			} else {

				this.branchLevelFeeCatalogs.add(this.createBranchLevelFeeCatalog(paymentDate, splitAmount));

			}

			nextPaymentDate = nextPaymentDate.plusDays(nextPaymentDurationInDays);

			paymentDate = new Date(nextPaymentDate.dayOfMonth().withMinimumValue().getMillis());

		}

	}

	/**
	 * Builds tree for class level fees.
	 */
	public void buildBranchLevelFeeTreeTable() {

		this.removeAllChildsOfRootNode(this.getRoot());

		if (this.academicYearBranchLevelFeeDOs != null) {

			for (AcademicYearBranchLevelFeeDO academicYearBranchLevelFeeDO : this.academicYearBranchLevelFeeDOs) {

				BranchLevelFeeTreeNode branchLevelFeeTreeNode = new BranchLevelFeeTreeNode();

				branchLevelFeeTreeNode.setName(academicYearBranchLevelFeeDO.getAcademicYear().getDisplayLabel());
				branchLevelFeeTreeNode.setTotalAmount(academicYearBranchLevelFeeDO.getTotalBranchLevelFee());

				BranchLevelFeeTreeNode academicYearTree = new BranchLevelFeeTreeNode(branchLevelFeeTreeNode, this.getRoot());
				academicYearTree.setExpanded(false);

				Collection<BranchLevelFee> branchLevelFees = academicYearBranchLevelFeeDO.getBranchLevelFees();

				for (BranchLevelFee branchLevelFee : branchLevelFees) {

					BranchLevelFeeTreeNode branchLevelFeeTreeNode1 = new BranchLevelFeeTreeNode();

					branchLevelFeeTreeNode1.setBranchLevelFee(branchLevelFee);

					branchLevelFeeTreeNode1.setName(branchLevelFee.getBuildingBlock().getName());

					branchLevelFeeTreeNode1.setPaymentFrequency(branchLevelFee.getPaymentFrequency());

					branchLevelFeeTreeNode1.setTotalAmount(branchLevelFee.getAmount());

					branchLevelFeeTreeNode1.setOptionsNode(true);

					TreeNode branchLevelFeeTree = new DefaultTreeNode(branchLevelFeeTreeNode1, academicYearTree);
					branchLevelFeeTree.setExpanded(false);

					Collection<BranchLevelFeeCatalog> branchLevelFeeCatalogs = branchLevelFee.getBranchLevelFeeCatalogs();

					for (BranchLevelFeeCatalog branchLevelFeeCatalog : branchLevelFeeCatalogs) {

						BranchLevelFeeTreeNode branchLevelFeeTreeNode2 = new BranchLevelFeeTreeNode();

						branchLevelFeeTreeNode2.setDueDate(branchLevelFeeCatalog.getDueDate());
						branchLevelFeeTreeNode2.setDueAmount(branchLevelFeeCatalog.getAmount());

						new DefaultTreeNode(branchLevelFeeTreeNode2, branchLevelFeeTree);
					}
				}

			}

		}
	}

	/**
	 * Removes all child nodes of the supplied root node.
	 */
	private void removeAllChildsOfRootNode(final TreeNode rootNode) {
		if (rootNode != null && rootNode.getChildCount() > 0) {
			TreeNode[] array = rootNode.getChildren().toArray(new TreeNode[rootNode.getChildCount()]);
			for (TreeNode child : array) {
				child.setParent(null);
				child = null;
			}
		}
	}

	/**
	 * Returns collection of BranchLevelFeePaymentDef objects. The collection
	 * size is
	 * same as parameter noOfObjects.
	 * 
	 * @param noOfObjects
	 *            number of BranchLevelFeePaymentDef objects to be created.
	 * @return collection of BranchLevelFeePaymentDef objects.
	 */
	private BranchLevelFeeCatalog createBranchLevelFeeCatalog(final Date date, final double amount) {
		BranchLevelFeeCatalog branchLevelFeeCatalog = new BranchLevelFeeCatalog();
		branchLevelFeeCatalog = new BranchLevelFeeCatalog();

		branchLevelFeeCatalog.setAmount(amount);
		branchLevelFeeCatalog.setDueDate(date);

		branchLevelFeeCatalog.setBranchLevelFee(this.branchLevelFee);

		return branchLevelFeeCatalog;
	}

	/**
	 * Listener fired when payment frequency is changed.
	 */
	public void paymentFeequencyListner() {
		if (PaymentFrequencyConstant.CUSTOM.equals(this.branchLevelFee.getPaymentFrequency())) {
			this.displayNoOfPayments = Boolean.TRUE;
		} else {
			this.generateBranchLevelFeeCatalogs();
			this.displayNoOfPayments = Boolean.FALSE;
		}
	}

	public void onChangePaymentFrequency() {
		if (PaymentFrequencyConstant.CUSTOM.equals(this.branchLevelFee.getPaymentFrequency())) {
			this.displayNoOfPayments = true;
		} else {
			this.displayNoOfPayments = false;
			this.generateBranchLevelFeeCatalogs();
		}
	}

	public void onChangeAcademicYear() {
		this.generateBranchLevelFeeCatalogs();
	}

	public void onChangeAmount() {
		this.generateBranchLevelFeeCatalogs();
	}

	public void onChangeNumberOfPayments() {
		this.generateBranchLevelFeeCatalogs();
	}

	/**
	 * Returns true if number of payments has to displayed.
	 * 
	 * @return true if number of payments has to displayed.
	 */
	public boolean isDisplayNoOfPayments() {
		return this.displayNoOfPayments;
	}

	/**
	 * Set true if number of payments has to displayed.
	 * 
	 * @param displayNoOfPayments
	 *            Boolean, true if number of payments has to displayed.
	 */
	public void setDisplayNoOfPayments(final boolean displayNoOfPayments) {
		this.displayNoOfPayments = displayNoOfPayments;
	}

	/**
	 * Listener fired when payment date is changed.
	 */
	public void onChangePaymentDate(final RowEditEvent event) {

	}

	public boolean isLoadAcademicYears() {
		return this.loadAcademicYears;
	}

	public void setLoadAcademicYears(final boolean loadAcademicYears) {
		this.loadAcademicYears = loadAcademicYears;
	}

	public void setBranchLevelFees(final Collection<BranchLevelFee> branchLevelFees) {
		this.branchLevelFees = branchLevelFees;
	}

	public AcademicYearBranchLevelFeeDO getAcademicYearBranchLevelFeeDO() {
		return this.academicYearBranchLevelFeeDO;
	}

	public void setAcademicYearBranchLevelFeeDO(final AcademicYearBranchLevelFeeDO academicYearBranchLevelFeeDO) {
		this.academicYearBranchLevelFeeDO = academicYearBranchLevelFeeDO;
	}

}
