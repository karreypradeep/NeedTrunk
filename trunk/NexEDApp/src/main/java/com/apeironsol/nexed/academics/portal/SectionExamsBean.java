/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 * 
 */
package com.apeironsol.nexed.academics.portal;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.primefaces.model.DefaultTreeNode;
import org.springframework.context.annotation.Scope;

import com.apeironsol.nexed.academics.dataobject.SectionExamDO;
import com.apeironsol.nexed.academics.dataobject.SectionExamSubjectDO;
import com.apeironsol.nexed.academics.model.SectionExam;
import com.apeironsol.nexed.academics.model.SectionExamSubject;
import com.apeironsol.nexed.academics.model.StudentExamSubject;
import com.apeironsol.nexed.academics.service.SectionExamService;
import com.apeironsol.nexed.academics.service.StudentExamSubjectService;
import com.apeironsol.nexed.core.model.Subject;
import com.apeironsol.nexed.core.portal.AbstractTabbedBean;
import com.apeironsol.nexed.core.portal.SectionBean;
import com.apeironsol.nexed.util.constants.StudentExamSubjectStatusConstant;
import com.apeironsol.nexed.util.portal.SectionExamTreeNode;
import com.apeironsol.nexed.util.portal.ViewExceptionHandler;
import com.apeironsol.nexed.util.portal.ViewUtil;
import com.apeironsol.framework.exception.ApplicationException;

/**
 * JSF managed for financial income.
 * 
 * @author Pradeep
 */
@Named
@Scope(value = "session")
public class SectionExamsBean extends AbstractTabbedBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long				serialVersionUID	= 6608427341321954354L;

	@Resource
	private SectionBean						sectionBean;

	@Resource
	private SectionExamService				sectionExamService;

	@Resource
	private StudentExamSubjectService		studentExamSubjectService;

	private Subject							subject;

	private SectionExam						sectionExam;

	private SectionExamSubject				sectionExamSubject;

	private Collection<StudentExamSubject>	studentExamSubjects;

	private boolean							buildSectionExamTreeFlag;

	private boolean							sectionExamSubjectFlag;

	private SectionExamTreeNode				root				= new SectionExamTreeNode("SectionExamTreeRoot", null);

	@Override
	public void onTabChange() {

	}

	public void buildSectionExamTreeTable() {
		if (this.buildSectionExamTreeFlag) {

			Collection<SectionExamDO> sectionExamDOs = this.sectionExamService.getSectionExamsBySectionId(this.sectionBean.getSection().getId());

			this.root = new SectionExamTreeNode("SectionExamTreeRoot", null);

			for (SectionExamDO sectionExamDO : sectionExamDOs) {

				SectionExamTreeNode sectionExamTreeNode = new SectionExamTreeNode();

				sectionExamTreeNode.setName(sectionExamDO.getSectionExam().getExam().getName());

				SectionExamTreeNode sectionExamTreeTableNode = new SectionExamTreeNode(sectionExamTreeNode, this.getRoot());
				sectionExamTreeTableNode.setExpanded(false);

				Collection<SectionExamSubjectDO> sectionExamSubjectDOs = sectionExamDO.getSectionExamSubjectDOs();

				for (SectionExamSubjectDO sectionExamSubjectDO : sectionExamSubjectDOs) {

					SectionExamTreeNode sectionExamTreeTableNode1 = new SectionExamTreeNode();

					sectionExamTreeTableNode1.setSectionExam(sectionExamDO.getSectionExam());

					sectionExamTreeTableNode1.setSubject(sectionExamSubjectDO.getSubject());

					sectionExamTreeTableNode1.setSectionExamSubject(sectionExamSubjectDO.getSectionExamSubject());

					sectionExamTreeTableNode1.setName(sectionExamSubjectDO.getSubject().getName());

					sectionExamTreeTableNode1.setScheduledDate(sectionExamSubjectDO.getSectionExamSubject().getScheduledDate());

					sectionExamTreeTableNode1.setStartTime(sectionExamSubjectDO.getSectionExamSubject().getStartTime());

					sectionExamTreeTableNode1.setEndTime(sectionExamSubjectDO.getSectionExamSubject().getEndTime());

					sectionExamTreeTableNode1.setPassMarks(sectionExamSubjectDO.getSectionExamSubject().getPassMarks());

					sectionExamTreeTableNode1.setMaximumMarks(sectionExamSubjectDO.getSectionExamSubject().getMaximumMarks());

					sectionExamTreeTableNode1.setOptionsNode(true);

					new DefaultTreeNode(sectionExamTreeTableNode1, sectionExamTreeTableNode);
				}

			}

			this.buildSectionExamTreeFlag = false;
		}
	}

	public void viewSectionSubjectExamDetailsForStudents() {

		this.setStudentExamSubjects(this.studentExamSubjectService.findStudentExamSubjectsByStudentIdAndSubjectExamId(this.sectionExamSubject.getId()));
		this.sectionExamSubjectFlag = true;
	}

	public void submitMarksObtained() {
		try {
			this.setStudentExamSubjects(this.studentExamSubjectService.saveStudentExamSubjects(this.getStudentExamSubjects()));
			ViewUtil.addMessage("Markes saved successfully.", FacesMessage.SEVERITY_INFO);
		} catch (ApplicationException applicationException) {
			ViewExceptionHandler.handle(applicationException);
		}
	}

	public SectionExamService getSectionExamService() {
		return this.sectionExamService;
	}

	public void setSectionExamService(final SectionExamService sectionExamService) {
		this.sectionExamService = sectionExamService;
	}

	public boolean isBuildSectionExamTreeFlag() {
		return this.buildSectionExamTreeFlag;
	}

	public void setBuildSectionExamTreeFlag(final boolean buildSectionExamTreeFlag) {
		this.buildSectionExamTreeFlag = buildSectionExamTreeFlag;
	}

	public Collection<StudentExamSubject> getStudentExamSubjects() {
		return this.studentExamSubjects;
	}

	public void setStudentExamSubjects(final Collection<StudentExamSubject> studentExamSubjects) {
		this.studentExamSubjects = studentExamSubjects;
	}

	public SectionExamTreeNode getRoot() {
		return this.root;
	}

	public SectionExamSubject getSectionExamSubject() {
		return this.sectionExamSubject;
	}

	public void setSectionExamSubject(final SectionExamSubject sectionExamSubject) {
		this.sectionExamSubject = sectionExamSubject;
	}

	public boolean isSectionExamSubjectFlag() {
		return this.sectionExamSubjectFlag;
	}

	public void setSectionExamSubjectFlag(final boolean sectionExamSubjectFlag) {
		this.sectionExamSubjectFlag = sectionExamSubjectFlag;
	}

	public SectionExam getSectionExam() {
		return this.sectionExam;
	}

	public void setSectionExam(final SectionExam sectionExam) {
		this.sectionExam = sectionExam;
	}

	public Subject getSubject() {
		return this.subject;
	}

	public void setSubject(final Subject subject) {
		this.subject = subject;
	}

	public boolean isStudentExamSubjectMarksDisabled(final StudentExamSubject studentExamSubject) {
		return !StudentExamSubjectStatusConstant.TAKEN.equals(studentExamSubject.getStudentExamSubjectStatus());
	}
}
