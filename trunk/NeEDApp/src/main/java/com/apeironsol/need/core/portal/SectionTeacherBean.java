/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.core.portal;

import java.util.Collection;

import javax.annotation.Resource;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.SectionTeacher;
import com.apeironsol.need.core.service.SectionTeacherService;
import com.apeironsol.need.hrms.model.Employee;
import com.apeironsol.need.hrms.service.EmployeeService;

/**
 * Managed bean for section teacher.
 * 
 * @author Pradeep
 */
@Named
@Scope(value = "session")
public class SectionTeacherBean extends AbstractTabbedBean {

	/** 
	 * 
	 */
	private static final long			serialVersionUID	= 9115511013030372738L;

	private SectionTeacher				sectionTeacher;

	@Resource
	private SectionBean					sectionBean;

	@Resource
	private SectionTeacherService		sectionTeacherService;

	@Resource
	private EmployeeService				employeeService;

	private Collection<SectionTeacher>	sectionTeachers;

	private boolean						loadSectionTeachers;

	private Collection<Employee>		teachers;

	public Collection<Employee> getTeachers() {
		return this.teachers;
	}

	public SectionTeacherBean() {
	}

	@Override
	public void onTabChange() {
		if (this.getActiveTabIndex() == 4) {
			this.loadSectionTeachers = true;
			this.setViewOrNewAction(false);
		}
	}

	/**
	 * Retrieve section teachers from data base.
	 */
	public void loadSectionsTeachers() {
		if (this.loadSectionTeachers) {
			this.sectionTeachers = this.sectionTeacherService.findSectionTeachersByScetionId(this.sectionBean
					.getSection().getId());
			this.loadSectionTeachers = false;
		}
	}

	/**
	 * Assigns a teacher to the section by creating a new SectionTeacher object.
	 */
	public void assignSectionNewTeacher() {
		this.sectionTeacher = new SectionTeacher();
		this.sectionTeacher.setSection(this.sectionBean.getSection());
		this.loadTeachers();
	}

	public void loadTeachers() {
		this.teachers = this.employeeService.findAllEmployeesByBranchId(this.sessionBean.getCurrentBranch().getId());

	}

	public String saveSectionTeacher() {
		this.sectionTeacher = this.sectionTeacherService.saveSectionTeacher(this.sectionTeacher);
		this.setViewOrNewAction(false);
		this.loadSectionTeachers = true;
		return null;
	}

	public String calcelAction() {
		this.setViewOrNewAction(false);
		return null;
	}

	/**
	 * @return the sectionTeacher
	 */
	public SectionTeacher getSectionTeacher() {
		return this.sectionTeacher;
	}

	/**
	 * @param sectionTeacher
	 *            the sectionTeacher to set
	 */
	public void setSectionTeacher(final SectionTeacher sectionTeacher) {
		this.sectionTeacher = sectionTeacher;
	}

	/**
	 * @return the sectionTeachers
	 */
	public Collection<SectionTeacher> getSectionTeachers() {
		return this.sectionTeachers;
	}

	/**
	 * @param sectionTeachers
	 *            the sectionTeachers to set
	 */
	public void setSectionTeachers(final Collection<SectionTeacher> sectionTeachers) {
		this.sectionTeachers = sectionTeachers;
	}

	/**
	 * @return the loadSectionTeachers
	 */
	public boolean isLoadSectionTeachers() {
		return this.loadSectionTeachers;
	}

	/**
	 * @param loadSectionTeachers
	 *            the loadSectionTeachers to set
	 */
	public void setLoadSectionTeachers(final boolean loadSectionTeachers) {
		this.loadSectionTeachers = loadSectionTeachers;
	}

	public void removeSectionTeacher() {
		this.sectionTeacherService.removeSectionTeacher(this.sectionTeacher);
		this.loadSectionTeachers = true;
	}

}
