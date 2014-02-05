/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.academics.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.need.academics.dao.ReportCardDao;
import com.apeironsol.need.academics.dao.ReportCardExamDao;
import com.apeironsol.need.academics.model.ReportCard;
import com.apeironsol.need.academics.model.ReportCardExam;
import com.apeironsol.need.notifications.service.BatchLogService;

/**
 * Service implementation interface for student.
 * 
 * @author pradeep
 * 
 */
@Service("reportCardService")
@Transactional(rollbackFor = Exception.class)
public class ReportCardServiceImpl implements ReportCardService {

	@Resource
	private ReportCardDao		reportCardDao;

	@Resource
	private ReportCardExamDao	reportCardRangeDao;

	@Resource
	private BatchLogService		batchLogService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ReportCard saveReportCard(final ReportCard reportCard) {
		validate(reportCard);
		ReportCard result = this.reportCardDao.persist(reportCard);
		for (final ReportCardExam reportCardRange : reportCard.getReportCardExams()) {
			reportCardRange.setReportCard(result);
			this.reportCardRangeDao.persist(reportCardRange);
		}
		result = findReportCardById(result.getId());
		return result;
	}

	private void validate(final ReportCard reportCard) {
		int percentage = 0;
		final ArrayList<Long> examIds = new ArrayList<Long>();
		// if same exam is assigned for multiple percentages then throw exeption

		for (final ReportCardExam reportCardExam : reportCard.getReportCardExams()) {
			if (reportCardExam.getExam() == null) {
				throw new ApplicationException("Please select Exam.");
			} else if (reportCardExam.getPercentage() == null) {
				throw new ApplicationException("Please enter percentage for exam.");
			} else if (reportCardExam.getPercentage() <= 0) {
				throw new ApplicationException("Percentage should be greater than 0.");
			}
			if (examIds.contains(reportCardExam.getExam().getId())) {
				throw new ApplicationException("Exam " + reportCardExam.getExam().getName() + " is used more than once.");
			} else {
				examIds.add(reportCardExam.getExam().getId());
			}
			percentage += reportCardExam.getPercentage();
		}
		if (percentage != 100) {
			throw new ApplicationException("Total percentage of all exams should be 100.");
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeReportCard(final ReportCard reportCard) {
		if ((this.batchLogService.findBatchLogsForReportCardId(reportCard.getId()) != null)
				&& (this.batchLogService.findBatchLogsForReportCardId(reportCard.getId()).size() > 0)) {
			throw new ApplicationException("Notifications have been sent. Report card cannot be deleted.");
		}
		for (final ReportCardExam reportCardRange : reportCard.getReportCardExams()) {
			this.reportCardRangeDao.remove(reportCardRange);
		}
		this.reportCardDao.remove(reportCard);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ReportCard findReportCardById(final Long id) {
		return this.reportCardDao.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<ReportCard> findAllReportCards(final Long branchId) {
		return this.reportCardDao.findAllReportCards(branchId);
	}

	@Override
	public Collection<ReportCard> findReportCardsByExams(final Collection<Long> examIDs) throws BusinessException {
		final Collection<ReportCardExam> reportCardExams = this.reportCardRangeDao.findAllReportCardExamsByExams(examIDs);
		final Map<Long, ReportCard> reportCards = new HashMap<Long, ReportCard>();
		for (final ReportCardExam reportCardExam : reportCardExams) {
			reportCards.put(reportCardExam.getReportCard().getId(), reportCardExam.getReportCard());
		}
		return reportCards.values();
	}

}
