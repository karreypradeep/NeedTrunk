/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.transportation.portal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.transportation.model.PickUpPoint;
import com.apeironsol.need.transportation.model.PickUpPointFee;
import com.apeironsol.need.transportation.model.PickUpPointFeeCatalog;
import com.apeironsol.need.util.constants.PaymentFrequencyConstant;
import com.apeironsol.need.util.portal.ViewExceptionHandler;
import com.apeironsol.need.util.portal.ViewUtil;
import com.apeironsol.framework.exception.ApplicationException;

/**
 * View PickUpPointFeeBean class.
 * 
 * @author sandeep
 * 
 */
@Named
@Scope(value = "session")
public class PickUpPointFeeBean extends AbstractTransportationBean {

	/**
	 * 
	 */
	private static final long					serialVersionUID		= 2863774836345469913L;

	static final Logger							log						= Logger.getLogger(PickUpPointFeeBean.class);

	private boolean								renderNewPickUpPointFeeButton;

	/**
	 * Boolean, True if payment frequency type custom.
	 */
	private boolean								displayNoOfPayments;

	private PickUpPointFee						pickUpPointFee;

	/**
	 * Collection of class fee payment definitions defined for the class fee.
	 */
	private Collection<PickUpPointFeeCatalog>	pickUpPointFeeCatalogs	= new ArrayList<PickUpPointFeeCatalog>();

	/**
	 * Academic year for which the pickUpPointFee fee is defined.
	 */
	private AcademicYear						academicYear;

	/**
	 * Boolean to display fee payment definition table.
	 */
	private boolean								displayFeeDefinitionTable;

	/**
	 * Holds number of payments defined for the payment frequency type
	 * custom for the pickUpPointFee.
	 */
	private int									noOfPayments;

	@Resource
	AbstractTransportationBean					pickUpPointBean;

	/**
	 * @return the renderNewPickUpPointFeeButton
	 */
	public boolean isRenderNewPickUpPointFeeButton() {
		return this.renderNewPickUpPointFeeButton;
	}

	/**
	 * @param renderNewPickUpPointFeeButton
	 *            the renderNewPickUpPointFeeButton to set
	 */
	public void setRenderNewPickUpPointFeeButton(final boolean renderNewPickUpPointFeeButton) {
		this.renderNewPickUpPointFeeButton = renderNewPickUpPointFeeButton;
	}

	/**
	 * @return the academicYear
	 */
	@Override
	public AcademicYear getAcademicYear() {
		return this.academicYear;
	}

	/**
	 * @param academicYear
	 *            the academicYear to set
	 */
	@Override
	public void setAcademicYear(final AcademicYear academicYear) {
		this.academicYear = academicYear;
	}

	/**
	 * @return the displayNoOfPayments
	 */
	public boolean isDisplayNoOfPayments() {
		return this.displayNoOfPayments;
	}

	/**
	 * @param displayNoOfPayments
	 *            the displayNoOfPayments to set
	 */
	public void setDisplayNoOfPayments(final boolean displayNoOfPayments) {
		this.displayNoOfPayments = displayNoOfPayments;
	}

	/**
	 * @return the displayFeeDefinitionTable
	 */
	public boolean isDisplayFeeDefinitionTable() {
		return this.displayFeeDefinitionTable;
	}

	/**
	 * @param displayFeeDefinitionTable
	 *            the displayFeeDefinitionTable to set
	 */
	public void setDisplayFeeDefinitionTable(final boolean displayFeeDefinitionTable) {
		this.displayFeeDefinitionTable = displayFeeDefinitionTable;
	}

	/**
	 * @return the noOfPayments
	 */
	public int getNoOfPayments() {
		return this.noOfPayments;
	}

	/**
	 * @param noOfPayments
	 *            the noOfPayments to set
	 */
	public void setNoOfPayments(final int noOfPayments) {
		this.noOfPayments = noOfPayments;
	}

	/**
	 * @return the pickUpPointFeeCatalogs
	 */
	public Collection<PickUpPointFeeCatalog> getPickUpPointFeeCatalogs() {
		return this.pickUpPointFeeCatalogs;
	}

	/**
	 * @return the pickUpPointFee
	 */
	public PickUpPointFee getPickUpPointFee() {
		return this.pickUpPointFee;
	}

	/**
	 * @param pickUpPointFee
	 *            the pickUpPointFee to set
	 */
	public void setPickUpPointFee(final PickUpPointFee pickUpPointFee) {
		this.pickUpPointFee = pickUpPointFee;
	}

	@Override
	public void onTabChange() {
		if (this.pickUpPointBean.getActiveTabIndex() == 1) {
			this.renderNewPickUpPointFeeButton = true;
			this.loadPickUpPointFees();
		}
	}

	/**
	 * Create a new class fee object.
	 */
	public void newPickUpPointFee() {
		this.pickUpPointFee = new PickUpPointFee();
		this.academicYearId = 0L;
		this.pickUpPointFeeCatalogs.clear();
		this.academicYear = null;
		this.renderNewPickUpPointFeeButton = false;
		this.displayNoOfPayments = false;
		this.setLoadAllAcademicYearsFlag(true);
		this.loadAllAcademicYearsForCurrentBranch();
	}

	public void onChangeAcademicYear() {
		this.generatePickUpPointFeeCatalogs();
	}

	public void onChangeAmount() {
		this.generatePickUpPointFeeCatalogs();
	}

	public void generatePickUpPointFeeCatalogs() {
		if (this.academicYearId == null || this.academicYearId == 0 || this.pickUpPointFee == null || this.pickUpPointFee.getAmount() == null
				|| this.pickUpPointFee.getAmount() <= 0 || this.pickUpPointFee.getPaymentFrequency() == null) {
			return;
		}

		this.pickUpPointFeeCatalogs.clear();
		this.displayFeeDefinitionTable = true;

		for (AcademicYear academicYear : this.getAllAcademicYearsForCurrentBranch()) {
			if (academicYear.getId().equals(this.academicYearId)) {
				this.pickUpPointFee.setAcademicYear(academicYear);
				break;
			}
		}

		int durationInDays = this.pickUpPointFee.getAcademicYear().getDays();

		if (PaymentFrequencyConstant.ONCE.equals(this.pickUpPointFee.getPaymentFrequency())) {
			this.pickUpPointFeeCatalogs.add(this.createPickUpPointFeeCatalog(this.pickUpPointFee.getAcademicYear().getStartDate(),
					(this.pickUpPointFee.getAmount())));

		} else if (PaymentFrequencyConstant.TWICE.equals(this.pickUpPointFee.getPaymentFrequency())) {

			this.processPayments(durationInDays, 2, this.pickUpPointFee.getAmount());

		} else if (PaymentFrequencyConstant.THRICE.equals(this.pickUpPointFee.getPaymentFrequency())) {

			this.processPayments(durationInDays, 3, this.pickUpPointFee.getAmount());

		} else if (PaymentFrequencyConstant.EVERY_MONTH.equals(this.pickUpPointFee.getPaymentFrequency())) {

			this.processPayments(durationInDays, this.pickUpPointFee.getAcademicYear().getMonths(), this.pickUpPointFee.getAmount());

		} else if (PaymentFrequencyConstant.CUSTOM.equals(this.pickUpPointFee.getPaymentFrequency())) {

			this.processPayments(durationInDays, this.noOfPayments, this.pickUpPointFee.getAmount());

		}
	}

	public void onChangeNumberOfPayments() {
		this.generatePickUpPointFeeCatalogs();
	}

	public void onChangePaymentFrequency() {
		if (PaymentFrequencyConstant.CUSTOM.equals(this.pickUpPointFee.getPaymentFrequency())) {
			this.displayNoOfPayments = true;
		} else {
			this.displayNoOfPayments = false;
			this.generatePickUpPointFeeCatalogs();
		}
	}

	private PickUpPointFeeCatalog createPickUpPointFeeCatalog(final Date date, final double amount) {

		PickUpPointFeeCatalog pickUpPointFeeCatalog = new PickUpPointFeeCatalog();

		pickUpPointFeeCatalog.setAmount(amount);

		pickUpPointFeeCatalog.setDueDate(date);

		pickUpPointFeeCatalog.setPickUpPointFee(this.pickUpPointFee);

		return pickUpPointFeeCatalog;
	}

	public void processPayments(final int durationInDays, final int numberOfPayments, final Double amount) {

		double splitAmount = Math.round(amount / numberOfPayments);

		double lastPaymentCorrectionValue = 0;

		double totalAmount = splitAmount * numberOfPayments;

		if (!amount.equals(totalAmount)) {
			lastPaymentCorrectionValue = amount - totalAmount;
		}

		int nextPaymentDurationInDays = durationInDays / numberOfPayments;

		Date paymentDate = this.pickUpPointFee.getAcademicYear().getStartDate();

		DateTime nextPaymentDate = new DateTime(paymentDate.getTime());

		for (int i = 0; i < numberOfPayments; i++) {

			if (i == (numberOfPayments - 1)) {
				this.pickUpPointFeeCatalogs.add(this.createPickUpPointFeeCatalog(paymentDate, splitAmount + lastPaymentCorrectionValue));
			} else {

				this.pickUpPointFeeCatalogs.add(this.createPickUpPointFeeCatalog(paymentDate, splitAmount));

			}

			nextPaymentDate = nextPaymentDate.plusDays(nextPaymentDurationInDays);

			paymentDate = new Date(nextPaymentDate.dayOfMonth().withMinimumValue().getMillis());

		}

	}

	public void cancelPickUpPointFee() {
		this.renderNewPickUpPointFeeButton = true;
	}

	public void savePickUpPointFee() {
		try {
			this.pickUpPointFee.setPickUpPoint(this.pickUpPointBean.getPickUpPoint());

			this.academicYear = this.getAcademicYearById(this.academicYearId);

			this.pickUpPointFee.setAcademicYear(this.academicYear);

			this.pickUpPointFee.setPickUpPointFeeCatalogs(this.pickUpPointFeeCatalogs);

			this.pickUpPointFee = this.pickUpPointFeeService.savePickUpPointFee(this.pickUpPointFee);

			this.pickUpPointFeeCatalogs = this.pickUpPointFeeCatalogService.findPickUpPointFeeCatalogsByPickUpPointFeeId(this.pickUpPointFee.getId());

			PickUpPoint pickupPoint = this.pickUpPointService.findPickUpPointById(this.pickUpPointBean.getPickUpPoint().getId());

			this.pickUpPointBean.setPickUpPoint(pickupPoint);

			this.pickUpPointBean.loadPickUpPointFees();

			this.setViewOrNewAction(false);

			ViewUtil.addMessage("Pickup point fees saved sucessfully.", FacesMessage.SEVERITY_INFO);
		} catch (Exception ex) {
			ViewExceptionHandler.handle(ex);
		}
	}

	public void removePickUpPointFee(final PickUpPointFee pickUpPointFee) {
		
		try {
		this.pickUpPointFee = pickUpPointFee;
		this.pickUpPointFeeService.removePickUpPointFee(this.pickUpPointFee.getId());
		this.pickUpPointBean.loadPickUpPointFees();
		ViewUtil.addMessage("Pickup point fees deleted sucessfully.", FacesMessage.SEVERITY_INFO);
		} catch(ApplicationException e) {
			ViewExceptionHandler.handle(e);
		}

		
	}

	public String showPickUpPointFeeCatalogs(final PickUpPointFee pickUpPointFee) {
		
		this.pickUpPointFee = pickUpPointFee;
		
		this.academicYear = pickUpPointFee.getAcademicYear();
		
		this.academicYearId = pickUpPointFee.getAcademicYear().getId();
	
		this.renderNewPickUpPointFeeButton = false;
		
		this.setLoadActiveAcademicYearsFlag(true);
		
		this.loadActiveAcademicYearsForCurrentBranch();

		this.pickUpPointFeeCatalogs = this.pickUpPointFeeCatalogService.findPickUpPointFeeCatalogsByPickUpPointFeeId(this.pickUpPointFee.getId());

		if (PaymentFrequencyConstant.CUSTOM.equals(this.pickUpPointFee.getPaymentFrequency())) {
			this.noOfPayments = this.pickUpPointFeeCatalogs.size();
			this.displayNoOfPayments = true;
		} else {
			this.noOfPayments = 0;
			this.displayNoOfPayments = false;
		}

		this.loadAllAcademicYearsForCurrentBranch();

		return null;
	}
}
