/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.transportation.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.apeironsol.framework.BaseEntity;

/**
 * Entity class for PickUpPointPayment.
 * 
 * @author sandeep
 * 
 */
@Entity
@Table(name = "PICK_UP_POINT_FEE_CATALOG")
public class PickUpPointFeeCatalog extends BaseEntity implements Serializable {

	/**
	 * Universal serial version number.
	 */
	private static final long	serialVersionUID	= -3251652758225855654L;

	@NotNull(message = "model.amount_mandatory")
	@Min(value = 0, message = "model.amount_cannot_be_lessthen_zero")
	@Column(name = "AMOUNT", nullable = false)
	private double				amount;

	@NotNull(message = "model.payment_date_mandatory")
	@Column(name = "DUE_DATE", nullable = false)
	private Date				dueDate;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "PICK_UP_POINT_FEE_ID", nullable = false)
	private PickUpPointFee		pickUpPointFee;

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

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return this.amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(final double amount) {
		this.amount = amount;
	}

	/**
	 * @return the paymentDate
	 */
	public Date getDueDate() {
		return this.dueDate;
	}

	/**
	 * @param paymentDate
	 *            the paymentDate to set
	 */
	public void setDueDate(final Date dueDate) {
		this.dueDate = dueDate;
	}

}
