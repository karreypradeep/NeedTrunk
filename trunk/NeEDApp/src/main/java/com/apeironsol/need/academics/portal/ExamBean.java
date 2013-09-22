package com.apeironsol.need.academics.portal;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.academics.dataobject.SectionExamDO;
import com.apeironsol.need.academics.model.Exam;
import com.apeironsol.need.academics.service.ExamService;
import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.util.portal.ViewExceptionHandler;
import com.apeironsol.need.util.portal.ViewUtil;
import com.apeironsol.framework.exception.ApplicationException;

@Named
@Scope(value = "session")
public class ExamBean extends AbstractExamBean {

	/**
	 * 
	 */
	private static final long			serialVersionUID	= 5243262822521308238L;

	@Resource
	private ExamService					examService;

	private Exam						exam;

	private boolean						loadExamsFlag;

	private Collection<Exam>			exams;

	private boolean						loadSectionExamsFlag;

	private Collection<SectionExamDO>	sectionExamDOs;

	@PostConstruct
	public void init() {
		this.setExam(new Exam());

	}

	public Collection<Exam> getExams() {
		return this.exams;
	}

	public Exam getExam() {
		return this.exam;
	}

	public void setExam(final Exam exam) {
		this.exam = exam;
	}

	public void saveExam() {
		Branch currentBranch = this.sessionBean.getCurrentBranch();
		this.exam.setBranch(currentBranch);
		try {
			this.exam = this.examService.saveExam(this.exam);
			this.setLoadExamsFlag(true);
			ViewUtil.addMessage("Exam is saved successfully.", FacesMessage.SEVERITY_INFO);
		} catch (ApplicationException e) {
			ViewExceptionHandler.handle(e);
		} catch (Exception e) {
			ViewExceptionHandler.handle(e);
		}
	}

	public void removeExam() {
		try {
			this.examService.removeExam(this.exam);
			this.setLoadExamsFlag(true);
		} catch (ApplicationException e) {
			ViewExceptionHandler.handle(e);
		} catch (Exception e) {
			ViewExceptionHandler.handle(e);
		}
	}

	public void resetExam() {
		this.exam = new Exam();
		this.sectionExamDOs = null;
	}

	public void newExam() {

	}

	public void loadExams() {
		try {

			if (this.isLoadExamsFlag()) {
				Branch currentBranch = this.sessionBean.getCurrentBranch();
				this.exams = this.examService.findExamsByBranchId(currentBranch.getId());
				this.setLoadExamsFlag(false);
			}
		} catch (ApplicationException ex) {
			ViewExceptionHandler.handle(ex);
		}

	}

	public void loadSectionExams() {
		try {
			if (this.loadSectionExamsFlag) {
				this.sectionExamDOs = this.examService.findSectionExamsByBranchIdAndExamId(this.sessionBean.getCurrentBranch().getId(), this.exam.getId());
				this.loadSectionExamsFlag = false;

			}
		} catch (ApplicationException e) {
			ViewExceptionHandler.handle(e);
		} catch (Exception e) {
			ViewExceptionHandler.handle(e);
		}
	}

	public boolean isLoadExamsFlag() {
		return this.loadExamsFlag;
	}

	public void setLoadExamsFlag(final boolean loadExamsFlag) {
		this.loadExamsFlag = loadExamsFlag;
	}

	public boolean isLoadSectionExamsFlag() {
		return this.loadSectionExamsFlag;
	}

	public void setLoadSectionExamsFlag(final boolean loadSectionExamsFlag) {
		this.loadSectionExamsFlag = loadSectionExamsFlag;
	}

	public Collection<SectionExamDO> getSectionExamDOs() {
		return this.sectionExamDOs;
	}

	public void setSectionExamDOs(final Collection<SectionExamDO> sectionExamDOs) {
		this.sectionExamDOs = sectionExamDOs;
	}

}
