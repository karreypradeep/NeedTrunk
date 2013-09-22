/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 * 
 */
package com.apeironsol.need.util.dataobject;

import java.io.Serializable;
import java.util.List;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.model.Klass;

/**
 * @author sunny
 *         Data object containing fee details for academic year admission
 * 
 */
public class AcademicYearAdmissionFeeDO implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long		serialVersionUID	= 3807722908267051720L;

	private AcademicYear			academicYear;

	private Klass					klass;

	private List<AdmissionFeeDO>	admissionFeeDOs;

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
	 * @return the klass
	 */
	public Klass getKlass() {
		return this.klass;
	}

	/**
	 * @param klass
	 *            the klass to set
	 */
	public void setKlass(final Klass klass) {
		this.klass = klass;
	}

	/**
	 * @return the admissionFeeDOs
	 */
	public List<AdmissionFeeDO> getAdmissionFeeDOs() {
		return this.admissionFeeDOs;
	}

	/**
	 * @param admissionFeeDOs
	 *            the admissionFeeDOs to set
	 */
	public void setAdmissionFeeDOs(final List<AdmissionFeeDO> admissionFeeDOs) {
		this.admissionFeeDOs = admissionFeeDOs;
	}

}
