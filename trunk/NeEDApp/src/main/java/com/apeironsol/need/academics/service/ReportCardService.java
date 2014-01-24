/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.academics.service;

import java.util.Collection;

import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.need.academics.model.ReportCard;

/**
 * Service interface for ReportCard.
 * 
 * @author Sunny
 * 
 */
public interface ReportCardService {

	/**
	 * Save ReportCard.
	 * 
	 * @param ReportCard
	 *            reportCard to be saved.
	 * @return persisted ReportCard.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	ReportCard saveReportCard(ReportCard reportCard) throws BusinessException;

	/**
	 * Delete ReportCard.
	 * 
	 * @param ReportCard
	 *            reportCard to be deleted.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	void removeReportCard(ReportCard reportCard) throws BusinessException;

	/**
	 * Find ReportCard by Id.
	 * 
	 * @param id
	 *            ReportCard Id.
	 * @return ReportCard with supplied Id.
	 * @throws BusinessException
	 *             In case of exception.
	 */
	ReportCard findReportCardById(Long id) throws BusinessException;

	/**
	 * Find all ReportCards.
	 * 
	 * @return collections of all ReportCards
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<ReportCard> findAllReportCards(Long branchId) throws BusinessException;

	/**
	 * Find all ReportCards.
	 * 
	 * @return collections of all ReportCards
	 * @throws BusinessException
	 *             In case of exception.
	 */
	Collection<ReportCard> findReportCardsByExams(Collection<Long> examIDs) throws BusinessException;

}
