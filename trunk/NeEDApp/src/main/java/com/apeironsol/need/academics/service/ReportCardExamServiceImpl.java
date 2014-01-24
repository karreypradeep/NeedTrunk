/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.academics.service;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.academics.dao.ReportCardExamDao;
import com.apeironsol.need.academics.model.ReportCardExam;
import com.apeironsol.need.util.NonNull;

/**
 * Service implementation interface for student.
 * 
 * @author pradeep
 * 
 */
@Service("reportCardExamService")
@Transactional(rollbackFor = Exception.class)
public class ReportCardExamServiceImpl implements ReportCardExamService {

	@Resource
	private ReportCardExamDao	reportCardExamDao;

	@Override
	public Collection<ReportCardExam> findAllReportCardExamsByReportCard(@NonNull final Long reportCard) {
		return this.reportCardExamDao.findAllReportCardExamsByReportCard(reportCard);
	}

}
