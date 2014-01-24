/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.academics.portal;

/**
 * View courses class.
 * 
 * @author Pradeep
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.need.academics.model.Exam;
import com.apeironsol.need.academics.model.GradeSystem;
import com.apeironsol.need.academics.model.ReportCard;
import com.apeironsol.need.academics.model.ReportCardExam;
import com.apeironsol.need.academics.service.ExamService;
import com.apeironsol.need.academics.service.GradeSystemService;
import com.apeironsol.need.academics.service.ReportCardExamService;
import com.apeironsol.need.academics.service.ReportCardService;
import com.apeironsol.need.core.portal.AbstractTabbedBean;
import com.apeironsol.need.util.comparator.ReportCardExamComparator;
import com.apeironsol.need.util.portal.ViewExceptionHandler;
import com.apeironsol.need.util.portal.ViewUtil;

@Named
@Scope("session")
public class ReportCardBean extends AbstractTabbedBean {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long			serialVersionUID	= -3568016756234711151L;

	private ReportCard					reportCard;

	private Collection<ReportCard>		reportCards;

	private boolean						loadReportCardsFlag;

	@Resource
	private ReportCardService			reportCardService;

	@Resource
	private ReportCardExamService		reportCardExamService;

	@Resource
	private ExamService					examService;

	@Resource
	private GradeSystemService			gradeSystemService;

	private ArrayList<ReportCardExam>	reportCardExams;

	private ReportCardExam				currentReportCardExam;

	private Collection<Exam>			exams;

	private Collection<GradeSystem>		gradeSystems;

	private boolean						loadExamsFlag;

	private boolean						loadGradeSystemsFlag;

	@Override
	public void onTabChange() {
		// TODO Auto-generated method stub

	}

	public void newReportCard() {
		this.reportCard = new ReportCard();
		this.reportCard.setBranch(this.sessionBean.getCurrentBranch());
		if (this.reportCardExams == null) {
			this.reportCardExams = new ArrayList<ReportCardExam>();
		} else {
			this.reportCardExams.clear();
		}
		ReportCardExam newReportCardExam = new ReportCardExam();
		this.reportCardExams.add(newReportCardExam);
	}

	public void saveReportCard() {
		try {
			this.reportCard.setBranch(this.sessionBean.getCurrentBranch());
			this.reportCard.setReportCardExams(this.reportCardExams);
			this.reportCard = this.reportCardService.saveReportCard(this.reportCard);
			if (this.reportCardExams == null) {
				this.reportCardExams = new ArrayList<ReportCardExam>();
			} else {
				this.reportCardExams.clear();
			}
			this.reportCardExams.addAll(this.reportCardExamService.findAllReportCardExamsByReportCard(this.reportCard.getId()));
			ViewUtil.addMessage("Grading system saved sucessfully.", FacesMessage.SEVERITY_INFO);
			this.setLoadReportCardsFlag(true);
		} catch (ApplicationException e) {
			ViewExceptionHandler.handle(e);
		}
	}

	public void addReportCardExam() {
		try {
			Collections.sort(this.reportCardExams, new ReportCardExamComparator(ReportCardExamComparator.Order.PERCENTAGE));
			ReportCardExam newReportCardExam = new ReportCardExam();
			this.reportCardExams.add(newReportCardExam);
		} catch (ApplicationException e) {
			ViewExceptionHandler.handle(e);
		}
	}

	public void removeReportCardExam() {
		try {
			Iterator<ReportCardExam> iterator = this.reportCardExams.iterator();
			while (iterator.hasNext()) {
				ReportCardExam reportCardExam = iterator.next();
				if (reportCardExam == this.currentReportCardExam) {
					iterator.remove();
					break;
				}
			}
		} catch (ApplicationException e) {
			ViewExceptionHandler.handle(e);
		}
	}

	public void viewReportCard() {
		if (this.reportCardExams == null) {
			this.reportCardExams = new ArrayList<ReportCardExam>();
		} else {
			this.reportCardExams.clear();
		}
		this.reportCardExams.addAll(this.reportCardExamService.findAllReportCardExamsByReportCard(this.reportCard.getId()));
	}

	public void removeReportCard() {
		try {
			this.reportCard.setBranch(this.sessionBean.getCurrentBranch());
			this.reportCardService.removeReportCard(this.reportCard);
			this.reportCard = new ReportCard();
			if (this.reportCardExams == null) {
				this.reportCardExams = new ArrayList<ReportCardExam>();
			} else {
				this.reportCardExams.clear();
			}
			this.setLoadReportCardsFlag(true);
			ViewUtil.addMessage("Hostel room removed sucessfully.", FacesMessage.SEVERITY_INFO);
		} catch (BusinessException e) {
			ViewExceptionHandler.handle(e);
		} catch (Throwable e) {
			ViewExceptionHandler.handle(e);
		}
	}

	public void resetReportCard() {
		this.reportCard = new ReportCard();
	}

	public void loadReportCards() {
		if (this.isLoadReportCardsFlag()) {
			this.reportCards = this.reportCardService.findAllReportCards(this.sessionBean.getCurrentBranch().getId());
			this.setLoadReportCardsFlag(false);
		}
	}

	public void loadExams() {
		if (this.isLoadExamsFlag()) {
			this.exams = this.examService.findExamsByBranchId(this.sessionBean.getCurrentBranch().getId());
			this.setLoadExamsFlag(false);
		}
	}

	public void loadGradeSystems() {
		if (this.isLoadGradeSystemsFlag()) {
			this.gradeSystems = this.gradeSystemService.findAllGradeSystems(this.sessionBean.getCurrentBranch().getId());
			this.setLoadGradeSystemsFlag(false);
		}
	}

	/**
	 * @return the reportCard
	 */
	public ReportCard getReportCard() {
		return this.reportCard;
	}

	/**
	 * @param reportCard
	 *            the reportCard to set
	 */
	public void setReportCard(final ReportCard reportCard) {
		this.reportCard = reportCard;
	}

	/**
	 * @return the reportCards
	 */
	public Collection<ReportCard> getReportCards() {
		return this.reportCards;
	}

	/**
	 * @param reportCards
	 *            the reportCards to set
	 */
	public void setReportCards(final Collection<ReportCard> reportCards) {
		this.reportCards = reportCards;
	}

	/**
	 * @return the loadReportCardsFlag
	 */
	public boolean isLoadReportCardsFlag() {
		return this.loadReportCardsFlag;
	}

	/**
	 * @param loadReportCardsFlag
	 *            the loadReportCardsFlag to set
	 */
	public void setLoadReportCardsFlag(final boolean loadReportCardsFlag) {
		this.loadReportCardsFlag = loadReportCardsFlag;
	}

	/**
	 * @return the reportCardExams
	 */
	public Collection<ReportCardExam> getReportCardExams() {
		return this.reportCardExams;
	}

	/**
	 * @param reportCardExams
	 *            the reportCardExams to set
	 */
	public void setReportCardExams(final ArrayList<ReportCardExam> reportCardExams) {
		this.reportCardExams = reportCardExams;
	}

	/**
	 * @return the currentReportCardExam
	 */
	public ReportCardExam getCurrentReportCardExam() {
		return this.currentReportCardExam;
	}

	/**
	 * @param currentReportCardExam
	 *            the currentReportCardExam to set
	 */
	public void setCurrentReportCardExam(final ReportCardExam currentReportCardExam) {
		this.currentReportCardExam = currentReportCardExam;
	}

	/**
	 * @return the exams
	 */
	public Collection<Exam> getExams() {
		return this.exams;
	}

	/**
	 * @param exams
	 *            the exams to set
	 */
	public void setExams(final Collection<Exam> exams) {
		this.exams = exams;
	}

	/**
	 * @return the loadExamsFlag
	 */
	public boolean isLoadExamsFlag() {
		return this.loadExamsFlag;
	}

	/**
	 * @param loadExamsFlag
	 *            the loadExamsFlag to set
	 */
	public void setLoadExamsFlag(final boolean loadExamsFlag) {
		this.loadExamsFlag = loadExamsFlag;
	}

	/**
	 * @return the loadGradeSystemsFlag
	 */
	public boolean isLoadGradeSystemsFlag() {
		return this.loadGradeSystemsFlag;
	}

	/**
	 * @param loadGradeSystemsFlag
	 *            the loadGradeSystemsFlag to set
	 */
	public void setLoadGradeSystemsFlag(final boolean loadGradeSystemsFlag) {
		this.loadGradeSystemsFlag = loadGradeSystemsFlag;
	}

	/**
	 * @return the gradeSystem
	 */
	public Collection<GradeSystem> getGradeSystems() {
		return this.gradeSystems;
	}

	/**
	 * @param gradeSystem
	 *            the gradeSystem to set
	 */
	public void setGradeSystems(final Collection<GradeSystem> gradeSystems) {
		this.gradeSystems = gradeSystems;
	}

}