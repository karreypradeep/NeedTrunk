/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 * 
 */
package com.apeironsol.need.util.dataobject;

import java.io.Serializable;

import com.apeironsol.need.financial.model.BranchLevelFee;
import com.apeironsol.need.financial.model.KlassLevelFee;
import com.apeironsol.need.util.constants.FeeClassificationLevelConstant;
import com.apeironsol.need.util.constants.PaymentFrequencyConstant;

/**
 * @author sunny
 *         Data object containing fee details for admission
 * 
 */
public class AdmissionFeeDO implements Serializable {

	/**
	 * 
	 */
	private static final long				serialVersionUID	= -2353125894984874844L;

	private FeeClassificationLevelConstant	feeClassificationLevelConstant;

	private BranchLevelFee					branchLevelFee;

	private KlassLevelFee					klassLevelFee;

	private Double							feePaidDuringAdmission;

	/**
	 * @return the feeClassificationLevelConstant
	 */
	public FeeClassificationLevelConstant getFeeClassificationLevelConstant() {
		return this.feeClassificationLevelConstant;
	}

	/**
	 * @param feeClassificationLevelConstant
	 *            the feeClassificationLevelConstant to set
	 */
	public void setFeeClassificationLevelConstant(final FeeClassificationLevelConstant feeClassificationLevelConstant) {
		this.feeClassificationLevelConstant = feeClassificationLevelConstant;
	}

	/**
	 * @return the branchLevelFee
	 */
	public BranchLevelFee getBranchLevelFee() {
		return this.branchLevelFee;
	}

	/**
	 * @param branchLevelFee
	 *            the branchLevelFee to set
	 */
	public void setBranchLevelFee(final BranchLevelFee branchLevelFee) {
		this.branchLevelFee = branchLevelFee;
	}

	/**
	 * @return the klassLevelFee
	 */
	public KlassLevelFee getKlassLevelFee() {
		return this.klassLevelFee;
	}

	/**
	 * @param klassLevelFee
	 *            the klassLevelFee to set
	 */
	public void setKlassLevelFee(final KlassLevelFee klassLevelFee) {
		this.klassLevelFee = klassLevelFee;
	}

	public String getFeeName() {
		return this.branchLevelFee != null ? this.branchLevelFee.getBuildingBlock().getName() : this.klassLevelFee != null ? this.klassLevelFee
				.getBuildingBlock().getName() : null;
	}

	public Double getAmount() {
		return this.branchLevelFee != null ? this.branchLevelFee.getAmount() : this.klassLevelFee != null ? this.klassLevelFee.getAmount() : null;
	}

	public PaymentFrequencyConstant getPaymentFrequency() {
		return this.branchLevelFee != null ? this.branchLevelFee.getPaymentFrequency() : this.klassLevelFee != null ? this.klassLevelFee.getPaymentFrequency()
				: null;
	}

	/**
	 * @return the feePaidDuringAdmission
	 */
	public Double getFeePaidDuringAdmission() {
		return this.feePaidDuringAdmission;
	}

	/**
	 * @param feePaidDuringAdmission
	 *            the feePaidDuringAdmission to set
	 */
	public void setFeePaidDuringAdmission(final Double feePaidDuringAdmission) {
		this.feePaidDuringAdmission = feePaidDuringAdmission;
	}

}
