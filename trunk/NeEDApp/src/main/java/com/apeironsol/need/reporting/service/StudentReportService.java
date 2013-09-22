/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.reporting.service;

import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.reporting.ro.BranchRO;
import com.apeironsol.need.util.constants.StudentReportNamesConstant;
import com.apeironsol.need.util.searchcriteria.StudentSearchCriteria;

/**
 * Service layer interface for supplier DAO implementation.
 * 
 * @author sunny
 * 
 */
public interface StudentReportService {

	/**
	 * Retrieve students fee information for search criteria.
	 * 
	 * @param branch
	 *            branch.
	 * @param studentSearchCriteria
	 *            search criteria.
	 * @return
	 */
	BranchRO getStudentsFeeReportForBranch(final Branch branch, final StudentSearchCriteria studentSearchCriteria);

	/**
	 * Retrieve students personal information for search criteria.
	 * 
	 * @param branch
	 *            branch.
	 * @param studentSearchCriteria
	 *            search criteria.
	 * @return
	 */
	BranchRO getStudentsPersonalDetailsReportForBranch(final Branch branch, final StudentSearchCriteria studentSearchCriteria);

	/**
	 * Retrieve students contact information for search criteria.
	 * 
	 * @param branch
	 *            branch.
	 * @param studentSearchCriteria
	 *            search criteria.
	 * @return
	 */
	BranchRO getStudentsContactDetailsReportForBranch(final Branch branch, final StudentSearchCriteria studentSearchCriteria);

	/**
	 * Retrieve students information for search criteria and report.
	 * 
	 * @param reportName
	 * @param branch
	 *            branch.
	 * @param studentSearchCriteria
	 *            search criteria.
	 * @return
	 */
	BranchRO getStudentsReportForBranch(final StudentReportNamesConstant reportName, final Branch branch, final StudentSearchCriteria studentSearchCriteria);
}
