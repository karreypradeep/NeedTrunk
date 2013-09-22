/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.core.portal;

/**
 * View courses class.
 * 
 * @author Pradeep
 */
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.Klass;
import com.apeironsol.need.core.model.Subject;
import com.apeironsol.need.core.service.KlassService;
import com.apeironsol.need.core.service.SubjectService;
import com.apeironsol.need.util.portal.ViewPathConstants;
import com.apeironsol.need.util.portal.ViewUtil;

@Named
@Scope("session")
public class SubjectBean extends AbstractKlassBean {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long	serialVersionUID	= 7674552637897562805L;

	@Resource
	SubjectService				subjectService;

	@Resource
	KlassService				klassService;

	private Subject				subject;

	private Collection<Subject>	subjects;

	@Resource
	private KlassBean			klassBean;

	@PostConstruct
	public void init() {

		this.subject = new Subject();
	}

	public Subject getSubject() {
		return this.subject;
	}

	public void setSubject(final Subject subject) {
		this.subject = subject;
	}

	public Collection<Subject> getSubjects() {
		return this.subjects;
	}

	public String saveSubject() {
		Klass klass = this.klassBean.getKlass();
		this.subject.setKlass(klass);
		this.subjectService.saveSubject(this.subject);
		this.subject = new Subject();
		ViewUtil.addMessage("Subject saved sucessfully.", FacesMessage.SEVERITY_INFO);
		return ViewPathConstants.SAME_PAGE;
	}

	public String removeSubject() {
		this.subjectService.removeSubject(this.subject);
		this.subject = new Subject();
		ViewUtil.addMessage("Subject removed sucessfully.", FacesMessage.SEVERITY_INFO);
		return ViewPathConstants.SAME_PAGE;
	}

	public String viewSubject() {
		return ViewPathConstants.SAME_PAGE;
	}

	public String resetSubject() {
		this.subject = new Subject();
		return ViewPathConstants.SAME_PAGE;
	}

	public void loadSubjects() {
		Klass klass = this.klassBean.getKlass();
		if (this.getActiveTabIndex() == 2) {
			this.subjects = this.subjectService.findAllSubjectsByKlassId(klass.getId());
		}
	}

}
