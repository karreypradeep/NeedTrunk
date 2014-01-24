package com.apeironsol.need.academics.portal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.academics.dataobject.ReportCardDO;
import com.apeironsol.need.academics.dataobject.StudentAcademicExamDO;
import com.apeironsol.need.academics.model.ReportCard;
import com.apeironsol.need.academics.model.ReportCardExam;
import com.apeironsol.need.academics.service.ReportCardService;
import com.apeironsol.need.academics.service.StudentAcademicService;
import com.apeironsol.need.core.portal.StudentBean;

@Named
@Scope("session")
public class StudentReportCardBean implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long			serialVersionUID	= 6903693331662339318L;

	@Resource
	private StudentAcademicService		studentAcademicService;

	@Resource
	private ReportCardService			reportCardService;

	@Resource
	private StudentBean					studentBean;

	private Collection<ReportCardDO>	reportCardDOs		= new ArrayList<ReportCardDO>();

	/**
	 * @return the reportCardDOs
	 */
	public Collection<ReportCardDO> getReportCardDOs() {
		return this.reportCardDOs;
	}

	/**
	 * @param reportCardDOs
	 *            the reportCardDOs to set
	 */
	public void setReportCardDOs(final Collection<ReportCardDO> reportCardDOs) {
		this.reportCardDOs = reportCardDOs;
	}

	public void fetchReportCardsForAcademicYear() {
		this.reportCardDOs.clear();
		Collection<StudentAcademicExamDO> studentAcademicExamDOs = this.studentAcademicService.getStudentAcademicDetailsByExamWise(this.studentBean
				.getStudentSection().getStudentAcademicYear().getId());
		Map<Long, StudentAcademicExamDO> examByStudentExamDOs = new HashMap<Long, StudentAcademicExamDO>();
		Collection<Long> examIDs = new ArrayList<Long>();
		for (StudentAcademicExamDO studentAcademicExamDO : studentAcademicExamDOs) {
			examByStudentExamDOs.put(studentAcademicExamDO.getExam().getId(), studentAcademicExamDO);
			if (!examIDs.contains(studentAcademicExamDO.getExam().getId())) {
				examIDs.add(studentAcademicExamDO.getExam().getId());
			}
		}
		if (examIDs.size() > 0) {
			Collection<ReportCard> reportCards = this.reportCardService.findReportCardsByExams(examIDs);
			ReportCardDO reportCardDO = null;
			for (ReportCard reportCard : reportCards) {
				Map<Long, StudentAcademicExamDO> examByStudentAcademicExamDOMap = new HashMap<Long, StudentAcademicExamDO>();
				Map<Long, Integer> examPercentages = new HashMap<Long, Integer>();
				reportCardDO = new ReportCardDO();
				reportCardDO.setReportCard(reportCard);
				for (ReportCardExam reportCardExam : reportCard.getReportCardExams()) {
					examByStudentAcademicExamDOMap.put(reportCardExam.getExam().getId(), examByStudentExamDOs.get(reportCardExam.getExam().getId()));
					examPercentages.put(reportCardExam.getExam().getId(), reportCardExam.getPercentage());
				}
				reportCardDO.setExamByStudentAcademicExamDOMap(examByStudentAcademicExamDOMap);
				reportCardDO.setExamPercentages(examPercentages);
				reportCardDO.computeReportCard();
				this.reportCardDOs.add(reportCardDO);
			}
		}
	}
}
