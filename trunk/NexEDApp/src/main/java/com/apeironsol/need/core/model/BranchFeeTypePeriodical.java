/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.apeironsol.need.core.portal.messages.BusinessMessages;
import com.apeironsol.framework.BaseEntity;
import com.apeironsol.framework.exception.InvalidArgumentException;

/**
 * Entity class for calendar year
 * 
 * @author Pradeep
 */
@Entity
@Table(name = "BRANCH_FEE_TYPE_PERIODICAL")
public class BranchFeeTypePeriodical extends BaseEntity implements Serializable {

	/**
	 * Universal serial version number.
	 */
	private static final long	serialVersionUID	= 7095074517619690496L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCH_ASSEMBLE_ID", nullable = false)
	private BranchAssembly		branchAssembly;

	@NotNull(message = "model.minimum_fee_mandatory")
	@Min(value = 0, message = "model.minimim_fee_cannot_be_lessthen_zero")
	@Column(name = "MINIMUM_FEE", nullable = false)
	private Double				minimumFee;

	@Column(name = "MAXIMUM_FEE", nullable = false)
	@NotNull(message = "model.maximum_fee_mandatory")
	private Double				maximumFee;

	@NotNull(message = "model.start_date_mandatory")
	@Column(name = "START_DATE", nullable = false)
	private Date				startDate;

	@Column(name = "END_DATE")
	private Date				endDate;

	public BranchAssembly getBranchAssembly() {
		return this.branchAssembly;
	}

	public void setBranchAssembly(final BranchAssembly branchAssembly) {
		this.branchAssembly = branchAssembly;
	}

	public Double getMinimumFee() {
		return this.minimumFee;
	}

	public void setMinimumFee(final Double minimumFee) {
		this.minimumFee = minimumFee;
	}

	public Double getMaximumFee() {
		return this.maximumFee;
	}

	public void setMaximumFee(final Double maximumFee) {
		this.maximumFee = maximumFee;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public void validate() throws InvalidArgumentException {
		if (this.getMinimumFee() < 0) {
			throw new InvalidArgumentException(BusinessMessages.getResourceBundleName(),
					BusinessMessages.MSG_AMOUNT_CANNOT_NEGATIVE, new Object[] { "Minimum" });
		}

		if (this.getMaximumFee() < 0) {
			throw new InvalidArgumentException(BusinessMessages.getResourceBundleName(),
					BusinessMessages.MSG_AMOUNT_CANNOT_NEGATIVE, new Object[] { "Maximum" });
		}

		if (this.getMaximumFee() < this.getMinimumFee()) {
			throw new InvalidArgumentException(BusinessMessages.getResourceBundleName(),
					BusinessMessages.MSG_MAXIMUM_FEE_CANNOT_BE_LESSTHEN_MINIMUM_FEE, null);
		}
		if (this.startDate == null) {
			throw new InvalidArgumentException(BusinessMessages.getResourceBundleName(),
					BusinessMessages.MSG_START_DATE_MANDATORY, null);
		}
		if (this.endDate != null && this.startDate.after(this.endDate)) {
			throw new InvalidArgumentException(BusinessMessages.getResourceBundleName(),
					BusinessMessages.MSG_START_DATE_SHOULD_BE_BEFORE_END_DATE, null);
		}

	}
}
