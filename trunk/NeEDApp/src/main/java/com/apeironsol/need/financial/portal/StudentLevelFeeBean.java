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

import org.joda.time.DateTime;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.portal.StudentBean;
import com.apeironsol.need.financial.model.StudentLevelFee;
import com.apeironsol.need.financial.model.StudentLevelFeeCatalog;
import com.apeironsol.need.financial.service.StudentLevelFeeService;
import com.apeironsol.need.util.comparator.StudentLevelFeeCatalogComparator;
import com.apeironsol.need.util.constants.PaymentFrequencyConstant;
import com.apeironsol.need.util.portal.StudentLevelFeeTreeNode;
import com.apeironsol.need.util.portal.ViewExceptionHandler;
import com.apeironsol.need.util.portal.ViewUtil;
import com.apeironsol.framework.exception.ApplicationException;

@Named
@Scope(value = "session")
public class StudentLevelFeeBean extends AbstractFinancialBean {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long					serialVersionUID			= -8396902889304389271L;

	@Resource
	private StudentLevelFeeService				studentLevelFeeService;

	@Resource
	private StudentBean							studentBean;

	/**
	 * Student level fee.
	 */
	private StudentLevelFee						studentLevelFee;

	/**
	 * Boolean which indicates if student level fees have to loaded from
	 * database.
	 */
	private boolean								loadStudentLevelFeesFlag	= false;

	/**
	 * Collection of student level fee payment definitions defined for the
	 * student
	 * level fee.
	 */
	private Collection<StudentLevelFeeCatalog>	studentLevelFeeCatalogs		= new ArrayList<StudentLevelFeeCatalog>();

	/**
	 * Holds number of payments defined for the payment frequency type
	 * custom for the student level fee.
	 */
	private int									noOfPayments;

	/**
	 * Boolean, True if payment frequency type custom.
	 */
	private boolean								displayNoOfPayments;

	/**
	 * 
	 */
	private Collection<StudentLevelFee>			studentLevelFees;

	/**
	 * Root node for tree.
	 */
	private final StudentLevelFeeTreeNode		root						= new StudentLevelFeeTreeNode("StudentLevelFeeTreeNode", null);

	@PostConstruct
	public void init() {
		this.reset();
	}

	public void reset() {
		this.studentLevelFee = new StudentLevelFee();
		this.studentLevelFeeCatalogs = new ArrayList<StudentLevelFeeCatalog>();
	}

	@Override
	public void onTabChange() {
		this.setViewAction(false);
		this.setNewAction(false);
		this.setLoadStudentLevelFeesFlag(true);
		this.loadStudentLevelFees();
	}

	public Collection<StudentLevelFee> getStudentLevelFees() {
		return this.studentLevelFees;
	}

	public StudentLevelFee getStudentLevelFee() {
		return this.studentLevelFee;
	}

	public void setStudentLevelFee(final StudentLevelFee studentLevelFee) {
		this.studentLevelFee = studentLevelFee;
	}

	public Collection<StudentLevelFeeCatalog> getStudentLevelFeeCatalogs() {
		return this.studentLevelFeeCatalogs;
	}

	public void setStudentLevelFeeCatalogs(final Collection<StudentLevelFeeCatalog> studentLevelFeeCatalogs) {
		this.studentLevelFeeCatalogs = studentLevelFeeCatalogs;
	}

	public int getNoOfPayments() {
		return this.noOfPayments;
	}

	public void setNoOfPayments(final int noOfPayments) {
		this.noOfPayments = noOfPayments;
	}

	/**
	 * Saves student level fee to database.
	 */
	public void saveStudentLevelFee() {
		try {

			this.studentLevelFee.setStudentAcademicYear(this.studentBean.getStudentAcademicYear());

			this.studentLevelFee.setStudentLevelFeeCatalogs(this.studentLevelFeeCatalogs);

			this.studentLevelFee = this.studentLevelFeeService.saveStudentLevelFee(this.studentLevelFee);

			ViewUtil.addMessage("Student Fee is saved sucessfully.", FacesMessage.SEVERITY_INFO);

			this.setNewAction(false);

			this.setViewAction(false);

			this.loadStudentLevelFeesFlag = true;

		} catch (ApplicationException e) {
			ViewExceptionHandler.handle(e);
		} catch (Exception e) {
			ViewExceptionHandler.handle(e);
		}
		this.loadStudentLevelFeesFlag = true;
	}

	/**
	 * Removes student level fee from database.
	 */
	public void removeStudentLevelFee() {
		try {

			this.studentLevelFeeService.removeStudentLevelFee(this.studentLevelFee);

			this.loadStudentLevelFeesFlag = true;

			ViewUtil.addMessage(this.getFeeTypeName() + " removed successfully.", FacesMessage.SEVERITY_INFO);
		} catch (ApplicationException exception) {
			ViewExceptionHandler.handle(exception);
		}
		this.loadStudentLevelFeesFlag = true;
	}

	/**
	 * Returns name for the fee type from building block.
	 * 
	 * @return name for the fee type from building block.
	 */
	public String getFeeTypeName() {
		return this.studentLevelFee.getBuildingBlock() != null ? this.studentLevelFee.getBuildingBlock().getName() : "";
	}

	/**
	 * Loads student fee types from database.
	 */
	public void loadStudentLevelFees() {
		try {
			if (this.loadStudentLevelFeesFlag) {

				this.setStudentLevelFees(this.studentLevelFeeService.findStudentLevelFeesByStudentAcademicYearId(this.studentBean.getStudentAcademicYear()
						.getId()));

				this.buildStudentLevelFeeTreeTable();

				this.loadStudentLevelFeesFlag = false;
			}
		} catch (Exception ex) {
			ViewUtil.addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}

	/**
	 * Loads student level fee payment definitions from the database for the
	 * current
	 * student level fee.
	 */
	public void loadStudentLevelFeeCatalogs() {

		if (this.isViewAction()) {
			this.studentLevelFeeCatalogs = this.studentLevelFeeService.findStudentLevelFeeCatalogsByStudentLevelFeeId(this.studentLevelFee.getId());

			// Sort payments by payment date.
			Collections.sort((List<StudentLevelFeeCatalog>) this.studentLevelFeeCatalogs, new StudentLevelFeeCatalogComparator(
					StudentLevelFeeCatalogComparator.DUE_DATE));

			if (PaymentFrequencyConstant.CUSTOM.equals(this.studentLevelFee.getPaymentFrequency())) {
				this.noOfPayments = this.studentLevelFeeCatalogs.size();
				this.displayNoOfPayments = true;
			} else {
				this.noOfPayments = 0;
				this.displayNoOfPayments = false;
			}

		}

	}

	/**
	 * Convenient method to create student level fee catalogs based on the
	 * payment frequency selected.
	 */
	public void generateStudentLevelFeeCatalogs() {

		if (this.studentLevelFee == null || this.studentLevelFee.getAmount() == null || this.studentLevelFee.getAmount() <= 0
				|| this.studentLevelFee.getPaymentFrequency() == null) {
			return;
		}

		this.studentLevelFeeCatalogs.clear();

		AcademicYear academicYear = this.studentBean.getStudentAcademicYear().getAcademicYear();

		int durationInDays = academicYear.getDays();

		if (PaymentFrequencyConstant.ONCE.equals(this.studentLevelFee.getPaymentFrequency())) {
			this.studentLevelFeeCatalogs.add(this.createStudentLevelFeeCatalog(academicYear.getStartDate(), this.studentLevelFee.getAmount()));

		} else if (PaymentFrequencyConstant.TWICE.equals(this.studentLevelFee.getPaymentFrequency())) {

			this.processPayments(durationInDays, 2, this.studentLevelFee.getAmount());

		} else if (PaymentFrequencyConstant.THRICE.equals(this.studentLevelFee.getPaymentFrequency())) {

			this.processPayments(durationInDays, 3, this.studentLevelFee.getAmount());

		} else if (PaymentFrequencyConstant.EVERY_MONTH.equals(this.studentLevelFee.getPaymentFrequency())) {

			this.processPayments(durationInDays, academicYear.getMonths(), this.studentLevelFee.getAmount());

		} else if (PaymentFrequencyConstant.CUSTOM.equals(this.studentLevelFee.getPaymentFrequency())) {

			this.processPayments(durationInDays, this.noOfPayments, this.studentLevelFee.getAmount());

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

		Date paymentDate = this.studentBean.getStudentAcademicYear().getAcademicYear().getStartDate();

		DateTime nextPaymentDate = new DateTime(paymentDate.getTime());

		for (int i = 0; i < numberOfPayments; i++) {

			if (i == (numberOfPayments - 1)) {
				this.studentLevelFeeCatalogs.add(this.createStudentLevelFeeCatalog(paymentDate, splitAmount + lastPaymentCorrectionValue));
			} else {

				this.studentLevelFeeCatalogs.add(this.createStudentLevelFeeCatalog(paymentDate, splitAmount));

			}

			nextPaymentDate = nextPaymentDate.plusDays(nextPaymentDurationInDays);

			paymentDate = new Date(nextPaymentDate.dayOfMonth().withMinimumValue().getMillis());

		}

	}

	/**
	 * Returns collection of StudentLevelFeePaymentDef objects. The collection
	 * size is
	 * same as parameter noOfObjects.
	 * 
	 * @param noOfObjects
	 *            number of StudentLevelFeePaymentDef objects to be created.
	 * @return collection of StudentLevelFeePaymentDef objects.
	 */
	private StudentLevelFeeCatalog createStudentLevelFeeCatalog(final Date date, final double amount) {
		StudentLevelFeeCatalog studentLevelFeeCatalog = new StudentLevelFeeCatalog();
		studentLevelFeeCatalog = new StudentLevelFeeCatalog();

		studentLevelFeeCatalog.setAmount(amount);
		studentLevelFeeCatalog.setDueDate(date);

		studentLevelFeeCatalog.setStudentLevelFee(this.studentLevelFee);

		return studentLevelFeeCatalog;
	}

	/**
	 * Listener fired when payment frequency is changed.
	 */
	public void paymentFeequencyListner() {
		if (PaymentFrequencyConstant.CUSTOM.equals(this.studentLevelFee.getPaymentFrequency())) {
			this.displayNoOfPayments = Boolean.TRUE;
		} else {
			this.generateStudentLevelFeeCatalogs();
			this.displayNoOfPayments = Boolean.FALSE;
		}
	}

	public void onChangePaymentFrequency() {
		if (PaymentFrequencyConstant.CUSTOM.equals(this.studentLevelFee.getPaymentFrequency())) {
			this.displayNoOfPayments = true;
		} else {
			this.displayNoOfPayments = false;
			this.generateStudentLevelFeeCatalogs();
		}
	}

	public void onChangeAcademicYear() {
		this.generateStudentLevelFeeCatalogs();
	}

	public void onChangeAmount() {
		this.generateStudentLevelFeeCatalogs();
	}

	public void onChangeNumberOfPayments() {
		this.generateStudentLevelFeeCatalogs();
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

	public void setStudentLevelFees(final Collection<StudentLevelFee> studentLevelFees) {
		this.studentLevelFees = studentLevelFees;
	}

	/**
	 * Builds tree for class level fees.
	 */
	public void buildStudentLevelFeeTreeTable() {

		this.removeAllChildsOfRootNode(this.getRoot());

		if (this.studentLevelFees != null) {

			for (StudentLevelFee studentLevelFee : this.studentLevelFees) {

				StudentLevelFeeTreeNode studentLevelFeeTreeNode = new StudentLevelFeeTreeNode();

				studentLevelFeeTreeNode.setName(studentLevelFee.getBuildingBlock().getName());
				studentLevelFeeTreeNode.setPaymentFrequency(studentLevelFee.getPaymentFrequency());
				studentLevelFeeTreeNode.setTotalAmount(studentLevelFee.getAmount());
				studentLevelFeeTreeNode.setStudentLevelFee(studentLevelFee);
				studentLevelFeeTreeNode.setOptionsNode(true);

				StudentLevelFeeTreeNode studentlevelFeeCatalogNode = new StudentLevelFeeTreeNode(studentLevelFeeTreeNode, this.getRoot());
				studentlevelFeeCatalogNode.setExpanded(false);

				Collection<StudentLevelFeeCatalog> studentLevelFeeCatalogs = studentLevelFee.getStudentLevelFeeCatalogs();

				for (StudentLevelFeeCatalog studentLevelFeeCatalog : studentLevelFeeCatalogs) {

					StudentLevelFeeTreeNode studentLevelFeeTreeNode2 = new StudentLevelFeeTreeNode();

					studentLevelFeeTreeNode2.setDueDate(studentLevelFeeCatalog.getDueDate());
					studentLevelFeeTreeNode2.setDueAmount(studentLevelFeeCatalog.getAmount());

					new DefaultTreeNode(studentLevelFeeTreeNode2, studentlevelFeeCatalogNode);
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

	public StudentLevelFeeTreeNode getRoot() {
		return this.root;
	}

	public boolean isLoadStudentLevelFeesFlag() {
		return this.loadStudentLevelFeesFlag;
	}

	public void setLoadStudentLevelFeesFlag(final boolean loadStudentLevelFeesFlag) {
		this.loadStudentLevelFeesFlag = loadStudentLevelFeesFlag;
	}

}
