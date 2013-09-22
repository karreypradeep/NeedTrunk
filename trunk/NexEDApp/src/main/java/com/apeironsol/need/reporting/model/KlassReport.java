/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.reporting.model;

import java.util.ArrayList;
import java.util.List;

import com.apeironsol.need.core.model.Klass;
import com.apeironsol.need.reporting.model.SectionReport;

/**
 * @author sunny This class is model for Klass Report.
 */
public class KlassReport {

	private Klass klass;

	private List<SectionReport> sectionReportBeanList = new ArrayList<SectionReport>();

	/**
	 * @return the klass
	 */
	public Klass getKlass() {
		return klass;
	}

	/**
	 * @param klass
	 *            the klass to set
	 */
	public void setKlass(Klass klass) {
		this.klass = klass;
	}

	/**
	 * @return the sectionReportBeanList
	 */
	public List<SectionReport> getSectionReportBeanList() {
		return sectionReportBeanList;
	}

	/**
	 * @param sectionReportBeanList the sectionReportBeanList to set
	 */
	public void setSectionReportBeanList(List<SectionReport> sectionReportBeanList) {
		this.sectionReportBeanList = sectionReportBeanList;
	}

}
