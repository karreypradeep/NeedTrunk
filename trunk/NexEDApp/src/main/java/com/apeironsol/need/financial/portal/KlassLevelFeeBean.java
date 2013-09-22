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
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.primefaces.event.RowEditEvent;
import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.model.BuildingBlock;
import com.apeironsol.need.core.model.Klass;
import com.apeironsol.need.core.portal.AbstractKlassBean;
import com.apeironsol.need.financial.model.KlassLevelFee;
import com.apeironsol.need.financial.model.KlassLevelFeeCatalog;
import com.apeironsol.need.util.comparator.KlassFeePaymentComparator;
import com.apeironsol.need.util.constants.PaymentFrequencyConstant;
import com.apeironsol.need.util.portal.ViewExceptionHandler;
import com.apeironsol.need.util.portal.ViewUtil;
import com.apeironsol.framework.exception.ApplicationException;

@Named
@Scope(value = "session")
public class KlassLevelFeeBean extends AbstractKlassBean {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long					serialVersionUID		= -8396902889304389271L;

	/**
	 * Logger for branch fee bean.
	 */
	private static final Logger					log						= Logger.getLogger(KlassLevelFeeBean.class);

	/**
	 * Klass level fee.
	 */
	private KlassLevelFee						klassLevelFee;

	/**
	 * Academic year for which the class fee is defined.
	 */
	private AcademicYear						academicYear;

	/**
	 * Building block.
	 */
	private BuildingBlock						buildingBlock;

	/**
	 * Boolean which indicates if class fees have to loaded from database.
	 */
	private boolean								loadklassFees			= false;

	/**
	 * Boolean which indicates if class fee is being created.
	 */
	private boolean								displayNewKlassFee		= false;

	/**
	 * Collection of class fee payment definitions defined for the class fee.
	 */
	private Collection<KlassLevelFeeCatalog>	klassLevelFeeCatalogs	= new ArrayList<KlassLevelFeeCatalog>();

	/**
	 * Holds number of payments defined for the payment frequency type
	 * custom for the klass fee.
	 */
	private int									noOfPayments;

	/**
	 * Boolean to display fee payment definition table.
	 */
	private boolean								displayFeeDefinitionTable;

	/**
	 * Boolean, True if payment frequency type custom.
	 */
	private boolean								displayNoOfPayments;

	private boolean								loadAcademicYears;

	@PostConstruct
	public void init() {
		this.klassLevelFeeCatalogs = new ArrayList<KlassLevelFeeCatalog>();
	}

	/**
	 * Returns collection of class fee payments definitions defined for the
	 * class fee.
	 * 
	 * @return collection of class fee payments definitions defined for the
	 *         class fee.
	 */
	public Collection<KlassLevelFeeCatalog> getKlassLevelFeeCatalogs() {
		return this.klassLevelFeeCatalogs;
	}

	/**
	 * Sets collection of klass fee payments definitions defined for the
	 * class fee.
	 * 
	 * @param klassFeePaymentDefList
	 *            collection of klass fee payments definitions defined for the
	 *            class fee.
	 */
	public void setKlassLevelFeeCatalogs(final Collection<KlassLevelFeeCatalog> klassLevelFeeCatalogs) {
		this.klassLevelFeeCatalogs = klassLevelFeeCatalogs;
	}

	/**
	 * Return true if klass fee payment definition table has to be displayed.
	 * 
	 * @return the displayFeeDefinitionTable true if klass fee payment
	 *         definition table has to be displayed.
	 */
	public boolean isDisplayFeeDefinitionTable() {
		return this.displayFeeDefinitionTable;
	}

	/**
	 * True if klass fee payment definition table has to be displayed.
	 * 
	 * @param displayFeeDefinitionTable
	 *            true if klass fee payment
	 *            definition table has to be displayed.
	 */
	public void setDisplayFeeDefinitionTable(final boolean displayFeeDefinitionTable) {
		this.displayFeeDefinitionTable = displayFeeDefinitionTable;
	}

	/**
	 * Returns number of payments defined for payment frequency type Custom.
	 * 
	 * @return the noOfPayments number of payments defined for payment
	 *         frequency type Custom.
	 */
	public int getNoOfPayments() {
		return this.noOfPayments;
	}

	/**
	 * Sets number of payments defined for payment frequency type Custom.
	 * 
	 * @param noOfPayments
	 *            number of payments defined for payment frequency type
	 *            Custom.
	 */
	public void setNoOfPayments(final int noOfPayments) {
		this.noOfPayments = noOfPayments;
	}

	/**
	 * Create a new class fee object.
	 */
	public void newKlassFee() {
		this.klassLevelFee = new KlassLevelFee();
		this.klassLevelFeeCatalogs.clear();
		this.academicYear = null;
	}

	/**
	 * Saves klass fee to database.
	 */
	public void saveKlassFee() {
		try {
			Klass klass = this.sessionBean.getCurrentKlass();
			if (klass == null) {
				ViewUtil.addMessage("Unexpected error : expected klass in seesion", FacesMessage.SEVERITY_ERROR);
			}
			if (this.buildingBlock == null) {
				ViewUtil.addMessage("Unexpected error : expected building block in bean", FacesMessage.SEVERITY_ERROR);
			}
			this.klassLevelFee.setKlass(klass);

			this.klassLevelFee.setBuildingBlock(this.buildingBlock);

			this.klassLevelFee.setAcademicYear(this.academicYear);
			this.klassLevelFee.setKlassLevelFeeCatalogs(this.klassLevelFeeCatalogs);
			this.klassLevelFee = this.klassLevelFeeService.saveKlassFee(this.klassLevelFee);

			ViewUtil.addMessage("Fee type saved sucessfully.", FacesMessage.SEVERITY_INFO);

			this.displayNewKlassFee = false;

		} catch (ApplicationException exception) {
			log.info(exception.getMessage(), exception);
			ViewExceptionHandler.handle(exception);
		}
		this.loadklassFees = true;
	}

	/**
	 * Saves klass fee to database.
	 */
	public void applyToExistingStudnets() {
		try {
			this.klassLevelFee.setKlass(this.sessionBean.getCurrentKlass());

			this.klassLevelFee.setAcademicYear(this.academicYear);

			this.klassLevelFeeService.applyKlassFeeToExistingActiveStudents(this.klassLevelFee);

			ViewUtil.addMessage("Fee applied to active students sucessfully.", FacesMessage.SEVERITY_INFO);

			this.displayNewKlassFee = false;

		} catch (ApplicationException exception) {
			log.info(exception.getMessage(), exception);
			ViewExceptionHandler.handle(exception);
		}
		this.loadklassFees = true;
	}

	/**
	 * Removes class fee from database.
	 */
	public void removeKlassFee() {
		try {
			Klass klass = this.sessionBean.getCurrentKlass();
			if (klass == null) {
				ViewUtil.addMessage("Unexpected error : expected klass in seesion", FacesMessage.SEVERITY_ERROR);
			}
			if (this.buildingBlock == null) {
				ViewUtil.addMessage("Unexpected error : expected building block in bean", FacesMessage.SEVERITY_ERROR);
			}
			this.klassLevelFee.setKlass(klass);
			this.klassLevelFee.setBuildingBlock(this.buildingBlock);
			this.klassLevelFeeService.removeKlassFee(this.klassLevelFee);
			ViewUtil.addMessage(this.getFeeTypeName() + " period removed successfully.", FacesMessage.SEVERITY_INFO);
		} catch (ApplicationException exception) {
			ViewExceptionHandler.handle(exception);
		}
		this.loadklassFees = true;
	}

	/**
	 * Returns name for the fee type from building block.
	 * 
	 * @return name for the fee type from building block.
	 */
	public String getFeeTypeName() {
		return this.buildingBlock != null ? this.buildingBlock.getName() : "";
	}

	/**
	 * Loads branch fee types from database.
	 */
	public void loadKlassFees() {
		try {
			if (this.loadklassFees) {
				Klass klass = this.sessionBean.getCurrentKlass();
				if (klass == null) {
					ViewUtil.addMessage("Unexpected error : expected klass in seesion", FacesMessage.SEVERITY_ERROR);
				}
				if (this.buildingBlock == null) {
					ViewUtil.addMessage("Unexpected error : expected building block in bean", FacesMessage.SEVERITY_ERROR);
				} else {
					this.klassLevelFees = this.klassLevelFeeService.findKlassFeesByKlassIdAndBuildingBlockId(klass.getId(), this.buildingBlock.getId());
				}

				this.loadklassFees = false;
			}
		} catch (Exception ex) {
			log.info(ex.getMessage(), ex);
			ViewUtil.addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}

	/**
	 * Loads class fee payment definitions from the database for the current
	 * klass fee.
	 */
	public String loadKlassLevelFeeCatalogs() {

		this.klassLevelFeeCatalogs = this.klassLevelFeeService.findAllKlassFeePaymentByKlassFeeId(this.klassLevelFee.getId());

		// Sort payments by payment date.
		Collections.sort((List<KlassLevelFeeCatalog>) this.klassLevelFeeCatalogs, new KlassFeePaymentComparator(KlassFeePaymentComparator.DUE_DATE));

		if (PaymentFrequencyConstant.CUSTOM.equals(this.klassLevelFee.getPaymentFrequency())) {
			this.noOfPayments = this.klassLevelFeeCatalogs.size();
			this.displayNoOfPayments = true;
		} else {
			this.noOfPayments = 0;
			this.displayNoOfPayments = false;
		}

		return null;
	}

	/**
	 * Returns class fee .
	 * 
	 * @return class fee .
	 */
	public Collection<KlassLevelFee> getKlassFees() {
		return this.klassLevelFees;
	}

	/**
	 * Return class fee.
	 * 
	 * @return class fee.
	 */
	public KlassLevelFee getKlassFee() {
		return this.klassLevelFee;
	}

	/**
	 * Sets class fee selected.
	 * 
	 * @param klassLevelFee
	 *            class fee.
	 */
	public void setKlassFee(final KlassLevelFee klassLevelFee) {
		this.klassLevelFee = klassLevelFee;
	}

	/**
	 * Returns building block of the class fee.
	 * 
	 * @return building block of the class fee.
	 */
	public BuildingBlock getBuildingBlock() {
		return this.buildingBlock;
	}

	/**
	 * Sets the building block.
	 * 
	 * @param buildingBlock
	 *            the building block.
	 */
	public void setBuildingBlock(final BuildingBlock buildingBlock) {
		this.buildingBlock = buildingBlock;
	}

	/**
	 * Returns true if class fee have to retrieved from database.
	 * 
	 * @return true if class fee have to retrieved from database.
	 */
	public boolean isLoadklassFee() {
		return this.loadklassFees;
	}

	/**
	 * True if class fee have to retrieved from database.
	 * 
	 * @param loadklassFee
	 *            true if class fee have to retrieved from database.
	 */
	public void setLoadklassFee(final boolean loadklassFee) {
		this.loadklassFees = loadklassFee;
	}

	/**
	 * True if new klass fee is being defined.
	 * 
	 * @return true if new klass fee is being defined.
	 */
	public boolean isDisplayNewKlassFee() {
		return this.displayNewKlassFee;
	}

	/**
	 * Set, true if new klass fee is being defined.
	 * 
	 * @param displayNewKlassFee
	 *            true if new klass fee is being defined.
	 */
	public void setDisplayNewKlassFee(final boolean displayNewKlassFee) {
		this.displayNewKlassFee = displayNewKlassFee;
	}

	/**
	 * Return academic year of the klass fee.
	 * 
	 * @return academic year of the klass fee.
	 */
	@Override
	public AcademicYear getAcademicYear() {
		return this.academicYear;
	}

	/**
	 * Sets academic year of the klass fee.
	 * 
	 * @param academicYear
	 *            academic year of the klass fee.
	 */
	@Override
	public void setAcademicYear(final AcademicYear academicYear) {
		this.academicYear = academicYear;
	}

	/**
	 * Convenient method to create klass fee payment definitions for the klass
	 * fee depending on the payment frequency selected.
	 */
	public void generateKlassFeePayments() {

		if (this.academicYearId == null || this.academicYearId == 0 || this.klassLevelFee == null || this.klassLevelFee.getAmount() == null
				|| this.klassLevelFee.getAmount() <= 0 || this.klassLevelFee.getPaymentFrequency() == null) {
			return;
		}

		this.klassLevelFeeCatalogs.clear();
		this.displayFeeDefinitionTable = true;

		for (AcademicYear academicYear : this.getAllAcademicYearsForCurrentBranch()) {
			if (academicYear.getId().equals(this.academicYearId)) {
				this.academicYear = academicYear;
				break;
			}
		}

		int durationInDays = this.academicYear.getDays();

		if (PaymentFrequencyConstant.ONCE.equals(this.klassLevelFee.getPaymentFrequency())) {
			this.klassLevelFeeCatalogs.add(this.createKlassLevelFeeCatalog(this.academicYear.getStartDate(), this.klassLevelFee.getAmount()));

		} else if (PaymentFrequencyConstant.TWICE.equals(this.klassLevelFee.getPaymentFrequency())) {

			this.processPayments(durationInDays, 2, this.klassLevelFee.getAmount());

		} else if (PaymentFrequencyConstant.THRICE.equals(this.klassLevelFee.getPaymentFrequency())) {

			this.processPayments(durationInDays, 3, this.klassLevelFee.getAmount());

		} else if (PaymentFrequencyConstant.EVERY_MONTH.equals(this.klassLevelFee.getPaymentFrequency())) {

			this.processPayments(durationInDays, this.academicYear.getMonths(), this.klassLevelFee.getAmount());

		} else if (PaymentFrequencyConstant.CUSTOM.equals(this.klassLevelFee.getPaymentFrequency())) {

			this.processPayments(durationInDays, this.noOfPayments, this.klassLevelFee.getAmount());

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

		Date paymentDate = this.academicYear.getStartDate();

		DateTime nextPaymentDate = new DateTime(paymentDate.getTime());

		for (int i = 0; i < numberOfPayments; i++) {

			if (i == numberOfPayments - 1) {
				this.klassLevelFeeCatalogs.add(this.createKlassLevelFeeCatalog(paymentDate, splitAmount + lastPaymentCorrectionValue));
			} else {

				this.klassLevelFeeCatalogs.add(this.createKlassLevelFeeCatalog(paymentDate, splitAmount));

			}

			nextPaymentDate = nextPaymentDate.plusDays(nextPaymentDurationInDays);

			paymentDate = new Date(nextPaymentDate.dayOfMonth().withMinimumValue().getMillis());

		}

	}

	/**
	 * Returns collection of KlassFeePaymentDef objects. The collection size is
	 * same as parameter noOfObjects.
	 * 
	 * @param noOfObjects
	 *            number of KlassFeePaymentDef objects to be created.
	 * @return collection of KlassFeePaymentDef objects.
	 */
	private KlassLevelFeeCatalog createKlassLevelFeeCatalog(final Date date, final double amount) {
		KlassLevelFeeCatalog klassLevelFeeCatalog = new KlassLevelFeeCatalog();
		klassLevelFeeCatalog = new KlassLevelFeeCatalog();

		klassLevelFeeCatalog.setAmount(amount);
		klassLevelFeeCatalog.setDueDate(date);

		klassLevelFeeCatalog.setKlassFee(this.klassLevelFee);

		return klassLevelFeeCatalog;
	}

	/**
	 * Listener fired when payment frequency is changed.
	 */
	public void paymentFeequencyListner() {
		if (PaymentFrequencyConstant.CUSTOM.equals(this.klassLevelFee.getPaymentFrequency())) {
			this.displayNoOfPayments = Boolean.TRUE;
		} else {
			this.generateKlassFeePayments();
			this.displayNoOfPayments = Boolean.FALSE;
		}
	}

	public void onChangePaymentFrequency() {
		if (PaymentFrequencyConstant.CUSTOM.equals(this.klassLevelFee.getPaymentFrequency())) {
			this.displayNoOfPayments = true;
		} else {
			this.displayNoOfPayments = false;
			this.generateKlassFeePayments();
		}
	}

	public void onChangeAcademicYear() {
		this.generateKlassFeePayments();
	}

	public void onChangeAmount() {
		this.generateKlassFeePayments();
	}

	public void onChangeNumberOfPayments() {
		this.generateKlassFeePayments();
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

	public void viewKlassFeeForAcademicYear() {

		this.klassLevelFees = this.currentAcademicYearKlassFeePO.getKlassFees();

		this.buildKlassFeeTreeTable(this.currentAcademicYearKlassFeePO.getAcademicYear(), this.klassLevelFees);
	}
}
