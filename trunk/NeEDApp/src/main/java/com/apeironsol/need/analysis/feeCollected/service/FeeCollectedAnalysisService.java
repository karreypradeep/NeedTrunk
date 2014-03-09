/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.analysis.feeCollected.service;

import com.apeironsol.need.analysis.feeCollected.dataobject.FeeCollectedAcademicYearDO;
import com.apeironsol.need.util.searchcriteria.FeeCollectedAnalysisSearchCriteria;

/**
 * Service interface for HostelRoom.
 * 
 * @author Sunny
 * 
 */
public interface FeeCollectedAnalysisService {

	FeeCollectedAcademicYearDO getFeeCollectedGeneratedForAcademicYearBySearchCriteria(
			final FeeCollectedAnalysisSearchCriteria feeCollectedAnalysisSearchCriteria);

}
