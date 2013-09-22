package com.apeironsol.need.util.dataobject;

import java.io.Serializable;
import java.util.Collection;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.financial.model.KlassLevelFee;

public class AcademicYearClassLevelFeeDO implements Serializable {

	/**
	 * Serial version id for this class.
	 */
	private static final long			serialVersionUID	= -1571395862404684503L;

	private AcademicYear				academicYear;

	private Double						totalBranchLevelFee;

	private Collection<KlassLevelFee>	klassLevelFees;

	public AcademicYear getAcademicYear() {
		return this.academicYear;
	}

	public void setAcademicYear(final AcademicYear academicYear) {
		this.academicYear = academicYear;
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

		if (obj instanceof AcademicYearClassLevelFeeDO) {
			return this.academicYear.equals(((AcademicYearClassLevelFeeDO) obj).getAcademicYear());
		}

		return false;
	}

	/**
	 * @return the klassLevelFees
	 */
	public Collection<KlassLevelFee> getKlassLevelFees() {
		return klassLevelFees;
	}

	/**
	 * @param klassLevelFees the klassLevelFees to set
	 */
	public void setKlassLevelFees(Collection<KlassLevelFee> klassLevelFees) {
		this.klassLevelFees = klassLevelFees;
	}
}
