/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.portal;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.model.StudentRegistration;
import com.apeironsol.need.core.service.StudentRegistrationService;
import com.apeironsol.need.util.constants.StudentRegistrationStatusConstant;
import com.apeironsol.need.util.portal.ViewUtil;

@Named
@Scope(value = "session")
public class StudentRegistrationBean extends AbstractTabbedBean {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long					serialVersionUID						= -6597492992264122943L;

	private StudentRegistration					currentStudentRegistration;

	private Collection<StudentRegistration>		studentRegistrationsBySearchCriteria	= null;

	@Resource
	private StudentRegistrationService			studentRegistrationService;

	private AcademicYear						academicYearForSearch;

	private StudentRegistrationStatusConstant	registrationStatusConstantForSearch;

	private List<String>						interestedGroups;

	@Override
	public void onTabChange() {

	}

	/**
	 * @return the currentStudentRegistration
	 */
	public StudentRegistration getCurrentStudentRegistration() {
		return this.currentStudentRegistration;
	}

	/**
	 * @param currentStudentRegistration
	 *            the currentStudentRegistration to set
	 */
	public void setCurrentStudentRegistration(final StudentRegistration currentStudentRegistration) {
		this.currentStudentRegistration = currentStudentRegistration;
	}

	public void searchStudentRegistrartionBySearchCriteria() {
		if ((this.academicYearForSearch != null) || (this.currentStudentRegistration != null)) {
			this.studentRegistrationsBySearchCriteria = this.studentRegistrationService.findStudentRegistrationesByAcademicYearIdAndStatus(
					this.academicYearForSearch != null ? this.academicYearForSearch.getId() : null, this.registrationStatusConstantForSearch);
		} else {
			this.studentRegistrationsBySearchCriteria = this.studentRegistrationService.findStudentRegistrationesByBranchId(this.sessionBean.getCurrentBranch()
					.getId());
		}
	}

	public void viewStudentRegistration() {

	}

	public void resetSearchCriteria() {
		this.academicYearForSearch = null;
		this.registrationStatusConstantForSearch = null;
	}

	/**
	 * @return the academicYearForSearch
	 */
	public AcademicYear getAcademicYearForSearch() {
		return this.academicYearForSearch;
	}

	/**
	 * @param academicYearForSearch
	 *            the academicYearForSearch to set
	 */
	public void setAcademicYearForSearch(final AcademicYear academicYearForSearch) {
		this.academicYearForSearch = academicYearForSearch;
	}

	public void newStudentRegistration() {
		this.currentStudentRegistration = new StudentRegistration();
		this.currentStudentRegistration.setBranch(this.sessionBean.getCurrentBranch());
		this.currentStudentRegistration.setRegistrationStatus(StudentRegistrationStatusConstant.SUBMITTED);
	}

	public String saveStudentRegistration() {
		this.currentStudentRegistration = this.studentRegistrationService.saveStudentRegistration(this.currentStudentRegistration);
		this.setViewOrNewAction(false);
		ViewUtil.addMessage("Student registration saved sucessfully.", FacesMessage.SEVERITY_INFO);
		this.searchStudentRegistrartionBySearchCriteria();
		return null;
	}

	public String removeStudentRegistration() {
		this.studentRegistrationService.removeStudentRegistration(this.currentStudentRegistration);
		this.searchStudentRegistrartionBySearchCriteria();
		ViewUtil.addMessage("Student registration removed sucessfully.", FacesMessage.SEVERITY_INFO);
		return null;
	}

	/**
	 * @return the registrationStatusConstantForSearch
	 */
	public StudentRegistrationStatusConstant getRegistrationStatusConstantForSearch() {
		return this.registrationStatusConstantForSearch;
	}

	/**
	 * @param registrationStatusConstantForSearch
	 *            the registrationStatusConstantForSearch to set
	 */
	public void setRegistrationStatusConstantForSearch(final StudentRegistrationStatusConstant registrationStatusConstantForSearch) {
		this.registrationStatusConstantForSearch = registrationStatusConstantForSearch;
	}

	/**
	 * @return the studentRegistrationsBySearchCriteria
	 */
	public Collection<StudentRegistration> getStudentRegistrationsBySearchCriteria() {
		return this.studentRegistrationsBySearchCriteria;
	}

	/**
	 * @param studentRegistrationsBySearchCriteria
	 *            the studentRegistrationsBySearchCriteria to set
	 */
	public void setStudentRegistrationsBySearchCriteria(final Collection<StudentRegistration> studentRegistrationsBySearchCriteria) {
		this.studentRegistrationsBySearchCriteria = studentRegistrationsBySearchCriteria;
	}

	/**
	 * @return the interestedGroups
	 */
	public List<String> getInterestedGroups() {
		return this.interestedGroups;
	}

	/**
	 * @param interestedGroups
	 *            the interestedGroups to set
	 */
	public void setInterestedGroups(final List<String> interestedGroups) {
		this.interestedGroups = interestedGroups;
	}

}
