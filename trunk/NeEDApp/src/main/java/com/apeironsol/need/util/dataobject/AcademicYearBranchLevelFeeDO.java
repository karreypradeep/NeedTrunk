package com.apeironsol.need.util.dataobject;

import java.io.Serializable;
import java.util.Collection;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.financial.model.BranchLevelFee;

public class AcademicYearBranchLevelFeeDO implements Serializable {

	/**
	 * Serial version id for this class.
	 */
	private static final long			serialVersionUID	= -1571395862404684503L;

	private AcademicYear				academicYear;

	private Double						totalBranchLevelFee;

	private Collection<BranchLevelFee>	branchLevelFees;

	public AcademicYear getAcademicYear() {
		return this.academicYear;
	}

	public void setAcademicYear(final AcademicYear academicYear) {
		this.academicYear = academicYear;
	}

	public Collection<BranchLevelFee> getBranchLevelFees() {
		return this.branchLevelFees;
	}

	public void setBranchLevelFees(final Collection<BranchLevelFee> branchLevelFees) {
		this.branchLevelFees = branchLevelFees;
	}

	public Double getTotalBranchLevelFee() {
		return this.totalBranchLevelFee;
	}

	public void setTotalBranchLevelFee(final Double totalBranchLevelFee) {
		this.totalBranchLevelFee = totalBranchLevelFee;
	}

	@Override
	public int hashCode() {

		if (this.academicYear == null) {
			return 1;
		}

		return this.academicYear.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {

		if (obj == null) {
			return false;
		}

		if (obj instanceof AcademicYearBranchLevelFeeDO) {
			return this.academicYear.equals(((AcademicYearBranchLevelFeeDO) obj).getAcademicYear());
		}

		return false;
	}
}
