/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.analysis.revenue.service;

import com.apeironsol.need.analysis.revenue.dataobject.RevenueAcademicYearDO;
import com.apeironsol.need.util.searchcriteria.RevenueAnalysisSearchCriteria;

/**
 * Service interface for HostelRoom.
 * 
 * @author Sunny
 * 
 */
public interface RevenueAnalysisService {

	RevenueAcademicYearDO getRevenueGeneratedForAcademicYearBySearchCriteria(final RevenueAnalysisSearchCriteria revenueAnalysisSearchCriteria);

}
