package com.apeironsol.need.util.po;

import java.io.Serializable;
import java.util.Collection;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.financial.model.KlassLevelFee;

public class AcademicYearKlassFeePO implements Serializable {

	/**
	 * Serial version id for this class.
	 */
	private static final long			serialVersionUID	= 1757602376107268388L;

	private AcademicYear				academicYear;

	private Double						totalKlassFeeForDayScholar;

	private Double						totalKlassFeeForResidentialStudent;

	private Collection<KlassLevelFee>	klassLevelFees;

	public AcademicYear getAcademicYear() {
		return this.academicYear;
	}

	public void setAcademicYear(final AcademicYear academicYear) {
		this.academicYear = academicYear;
	}

	public Collection<KlassLevelFee> getKlassFees() {
		return this.klassLevelFees;
	}

	public void setKlassFees(final Collection<KlassLevelFee> klassLevelFees) {
		this.klassLevelFees = klassLevelFees;
	}

	/**
	 * @return the totalKlassFeeForDayScholar
	 */
	public Double getTotalKlassFeeForDayScholar() {
		return this.totalKlassFeeForDayScholar;
	}

	/**
	 * @param totalKlassFeeForDayScholar
	 *            the totalKlassFeeForDayScholar to set
	 */
	public void setTotalKlassFeeForDayScholar(final Double totalKlassFeeForDayScholar) {
		this.totalKlassFeeForDayScholar = totalKlassFeeForDayScholar;
	}

	/**
	 * @return the totalKlassFeeForResidentialStudent
	 */
	public Double getTotalKlassFeeForResidentialStudent() {
		return this.totalKlassFeeForResidentialStudent;
	}

	/**
	 * @param totalKlassFeeForResidentialStudent
	 *            the totalKlassFeeForResidentialStudent to set
	 */
	public void setTotalKlassFeeForResidentialStudent(final Double totalKlassFeeForResidentialStudent) {
		this.totalKlassFeeForResidentialStudent = totalKlassFeeForResidentialStudent;
	}

}
