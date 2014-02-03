/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 * 
 */
package com.apeironsol.need.academics.portal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.need.academics.dataobject.StudentExamAllSubjectsDO;
import com.apeironsol.need.academics.service.StudentExamSubjectService;
import com.apeironsol.need.core.portal.AbstractTabbedBean;
import com.apeironsol.need.util.portal.ViewExceptionHandler;
import com.apeironsol.need.util.portal.ViewUtil;

/**
 * JSF managed for financial income.
 * 
 * @author Pradeep
 */
@Named
@Scope(value = "session")
public class SectionExamAllSubjectsBean extends AbstractTabbedBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long						serialVersionUID				= -2766565870083602301L;

	@Resource
	private SectionExamsBean						sectionExamsBean;

	@Resource
	private StudentExamSubjectService				studentExamSubjectService;

	private Collection<StudentExamAllSubjectsDO>	studentExamAllSubjectsDOs		= new ArrayList<StudentExamAllSubjectsDO>();

	private boolean									loadStudentExamAllSubjectsDOs	= false;

	@Override
	public void onTabChange() {

	}

	/**
	 * @return the studentExamAllSubjectsDOs
	 */
	public Collection<StudentExamAllSubjectsDO> getStudentExamAllSubjectsDOs() {
		return this.studentExamAllSubjectsDOs;
	}

	/**
	 * @return the loadStudentExamAllSubjectsDOs
	 */
	public boolean isLoadStudentExamAllSubjectsDOs() {
		return this.loadStudentExamAllSubjectsDOs;
	}

	/**
	 * @param loadStudentExamAllSubjectsDOs
	 *            the loadStudentExamAllSubjectsDOs to set
	 */
	public void setLoadStudentExamAllSubjectsDOs(final boolean loadStudentExamAllSubjectsDOs) {
		this.loadStudentExamAllSubjectsDOs = loadStudentExamAllSubjectsDOs;
	}

	public void loadStudentExamAllSubjectsDOsFromDB() {
		if (isLoadStudentExamAllSubjectsDOs()) {
			this.studentExamAllSubjectsDOs = this.studentExamSubjectService.findStudentExamAllSubjectsDOsBySubjectExamId(this.sectionExamsBean.getSectionExam()
					.getId());
			this.loadStudentExamAllSubjectsDOs = false;
		}
	}

	public void submitMarksObtained() {
		try {
			this.studentExamAllSubjectsDOs = this.studentExamSubjectService.saveStudentExamAllSubjectsDOs(this.studentExamAllSubjectsDOs);
			ViewUtil.addMessage("Markes saved successfully.", FacesMessage.SEVERITY_INFO);
		} catch (final ApplicationException applicationException) {
			ViewExceptionHandler.handle(applicationException);
		}
	}
}
