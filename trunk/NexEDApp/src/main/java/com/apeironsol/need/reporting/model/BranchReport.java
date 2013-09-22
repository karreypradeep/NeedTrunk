/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.reporting.model;

import java.util.ArrayList;
import java.util.List;

import com.apeironsol.need.core.model.Branch;

/**
 * @author sunny This class is model for Branch Report.
 */
public class BranchReport {

	private Branch branch;

	private List<KlassReport> klassReportBeanList = new ArrayList<KlassReport>();

	/**
	 * @return the branch
	 */
	public Branch getBranch() {
		return branch;
	}

	/**
	 * @param branch
	 *            the branch to set
	 */
	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	/**
	 * @return the klassReportBeanList
	 */
	public List<KlassReport> getKlassReportBeanList() {
		return klassReportBeanList;
	}

	/**
	 * @param klassReportBeanList
	 *            the klassReportBeanList to set
	 */
	public void setKlassReportBeanList(List<KlassReport> klassReportBeanList) {
		this.klassReportBeanList = klassReportBeanList;
	}

}
