/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.transportation.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.apeironsol.framework.BaseEntity;
import com.apeironsol.framework.exception.InvalidArgumentException;
import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.util.constants.PaymentFrequencyConstant;

/**
 * Entity class for PickUpPointFee
 * 
 * @author sandeep
 * 
 */
@Entity
@Table(name = "PICK_UP_POINT_FEE", uniqueConstraints = @UniqueConstraint(columnNames = { "PICK_UP_POINT_ID", "ACADEMIC_YEAR_ID" }))
public class PickUpPointFee extends BaseEntity implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long					serialVersionUID	= -674549016121138620L;

	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "ACADEMIC_YEAR_ID", nullable = false)
	private AcademicYear						academicYear;

	@NotNull(message = "model.amount_mandatory")
	@Min(value = 0, message = "model.amount_cannot_be_lessthen_zero")
	@Column(name = "AMOUNT", nullable = false)
	private Double								amount;

	@NotNull(message = "model.frequency_mandatory")
	@Basic
	@Column(name = "PAYMENT_FREQUENCY", length = 50, nullable = false)
	@Enumerated(EnumType.STRING)
	private PaymentFrequencyConstant			paymentFrequency;

	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "PICK_UP_POINT_ID", nullable = false)
	private PickUpPoint							pickUpPoint;

	@OneToMany(mappedBy = "pickUpPointFee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Collection<PickUpPointFeeCatalog>	pickUpPointFeeCatalogs;

	@Override
	public void validate() throws InvalidArgumentException {
		super.validate();
		Double totalAmountPayments = 0d;
		if (this.pickUpPointFeeCatalogs != null && !this.pickUpPointFeeCatalogs.isEmpty()) {
			for (PickUpPointFeeCatalog pickUpPointFeeCatalog : this.pickUpPointFeeCatalogs) {
				totalAmountPayments += pickUpPointFeeCatalog.getAmount();
			}
			if (Math.round(totalAmountPayments) != Math.round(this.amount)) {
				throw new InvalidArgumentException("Total sum of catalog amount '" + totalAmountPayments + "' does not match with fee amount '" + this.amount
						+ "'.");
			}
		}

	}

	/**
	 * @return the pickUpPoint
	 */
	public PickUpPoint getPickUpPoint() {
		return this.pickUpPoint;
	}

	/**
	 * @param pickUpPoint
	 *            the pickUpPoint to set
	 */
	public void setPickUpPoint(final PickUpPoint pickUpPoint) {
		this.pickUpPoint = pickUpPoint;
	}

	/**
	 * @return the paymentFrequency
	 */
	public PaymentFrequencyConstant getPaymentFrequency() {
		return this.paymentFrequency;
	}

	/**
	 * @param paymentFrequency
	 *            the paymentFrequency to set
	 */
	public void setPaymentFrequency(final PaymentFrequencyConstant paymentFrequency) {
		this.paymentFrequency = paymentFrequency;
	}

	/**
	 * @return the academicYear
	 */
	public AcademicYear getAcademicYear() {
		return this.academicYear;
	}

	/**
	 * @param academicYear
	 *            the academicYear to set
	 */
	public void setAcademicYear(final AcademicYear academicYear) {
		this.academicYear = academicYear;
	}

	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return this.amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(final Double amount) {
		this.amount = amount;
	}

	/**
	 * @return the pickUpPointFeeCatalogs
	 */
	public Collection<PickUpPointFeeCatalog> getPickUpPointFeeCatalogs() {
		return this.pickUpPointFeeCatalogs;
	}

	/**
	 * @param pickUpPointFeeCatalogs
	 *            the pickUpPointFeeCatalogs to set
	 */
	public void setPickUpPointFeeCatalogs(final Collection<PickUpPointFeeCatalog> pickUpPointFeeCatalogs) {
		this.pickUpPointFeeCatalogs = pickUpPointFeeCatalogs;
	}

}
