/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.portal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.model.StudentRegistration;
import com.apeironsol.need.core.service.StudentRegistrationService;
import com.apeironsol.need.notifications.model.BatchLogMessage;
import com.apeironsol.need.notifications.service.BatchLogMessageService;
import com.apeironsol.need.notifications.service.BatchLogService;
import com.apeironsol.need.util.comparator.BatchLogMessageComparator;
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

	/**
	 * Batch log messages for the section for selected batch log.
	 */
	private Collection<BatchLogMessage>			studentRegistrationBatchLogMessages		= new ArrayList<BatchLogMessage>();

	/**
	 * Batch log message service.
	 */
	@Resource
	private BatchLogMessageService				batchLogMessageService;

	/**
	 * Batch log service.
	 */
	@Resource
	private BatchLogService						batchLogService;

	/**
	 * Indicator to specify if batch logs has to be fetched form DB.
	 */
	private boolean								loadBatchLogMessagesFromDB				= false;

	/**
	 * Variable to hole batch log message error message.
	 */
	private String								batchLogMessageErrorMessage;

	/**
	 * Variable to hold batch log message sent.
	 */
	private String								batchLogMessageSentMessage;

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
		if (((this.academicYearForSearch != null) && (this.registrationStatusConstantForSearch != null))) {
			this.studentRegistrationsBySearchCriteria = this.studentRegistrationService.findStudentRegistrationesByAcademicYearIdAndStatus(
					this.academicYearForSearch.getId(), this.registrationStatusConstantForSearch);
		} else if (this.academicYearForSearch != null) {
			this.studentRegistrationsBySearchCriteria = this.studentRegistrationService.findStudentRegistrationsByAcademicYearId(this.academicYearForSearch
					.getId());
		} else if (this.registrationStatusConstantForSearch != null) {
			this.studentRegistrationsBySearchCriteria = this.studentRegistrationService
					.findStudentRegistrationesByStudentRegistrationStatus(this.registrationStatusConstantForSearch);
		} else {
			this.studentRegistrationsBySearchCriteria = this.studentRegistrationService.findStudentRegistrationsByBranchId(this.sessionBean.getCurrentBranch()
					.getId());
		}
	}

	public void viewStudentRegistration() {
		this.currentStudentRegistration.setBranch(this.sessionBean.getCurrentBranch());
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

	/**
	 * @return the studentRegistrationService
	 */
	public StudentRegistrationService getStudentRegistrationService() {
		return this.studentRegistrationService;
	}

	/**
	 * @param studentRegistrationService
	 *            the studentRegistrationService to set
	 */
	public void setStudentRegistrationService(final StudentRegistrationService studentRegistrationService) {
		this.studentRegistrationService = studentRegistrationService;
	}

	/**
	 * @return the studentRegistrationBatchLogMessages
	 */
	public Collection<BatchLogMessage> getStudentRegistrationBatchLogMessages() {
		return this.studentRegistrationBatchLogMessages;
	}

	/**
	 * @param studentRegistrationBatchLogMessages
	 *            the studentRegistrationBatchLogMessages to set
	 */
	public void setStudentRegistrationBatchLogMessages(final Collection<BatchLogMessage> studentRegistrationBatchLogMessages) {
		this.studentRegistrationBatchLogMessages = studentRegistrationBatchLogMessages;
	}

	/**
	 * @return the batchLogMessageService
	 */
	public BatchLogMessageService getBatchLogMessageService() {
		return this.batchLogMessageService;
	}

	/**
	 * @param batchLogMessageService
	 *            the batchLogMessageService to set
	 */
	public void setBatchLogMessageService(final BatchLogMessageService batchLogMessageService) {
		this.batchLogMessageService = batchLogMessageService;
	}

	/**
	 * @return the loadBatchLogMessagesFromDB
	 */
	public boolean isLoadBatchLogMessagesFromDB() {
		return this.loadBatchLogMessagesFromDB;
	}

	/**
	 * @param loadBatchLogMessagesFromDB
	 *            the loadBatchLogMessagesFromDB to set
	 */
	public void setLoadBatchLogMessagesFromDB(final boolean loadBatchLogMessagesFromDB) {
		this.loadBatchLogMessagesFromDB = loadBatchLogMessagesFromDB;
	}

	/**
	 * @return the batchLogMessageErrorMessage
	 */
	public String getBatchLogMessageErrorMessage() {
		return this.batchLogMessageErrorMessage;
	}

	/**
	 * @param batchLogMessageErrorMessage
	 *            the batchLogMessageErrorMessage to set
	 */
	public void setBatchLogMessageErrorMessage(final String batchLogMessageErrorMessage) {
		this.batchLogMessageErrorMessage = batchLogMessageErrorMessage;
	}

	/**
	 * @return the batchLogMessageSentMessage
	 */
	public String getBatchLogMessageSentMessage() {
		return this.batchLogMessageSentMessage;
	}

	/**
	 * @param batchLogMessageSentMessage
	 *            the batchLogMessageSentMessage to set
	 */
	public void setBatchLogMessageSentMessage(final String batchLogMessageSentMessage) {
		this.batchLogMessageSentMessage = batchLogMessageSentMessage;
	}

	/**
	 * Fetch batch log messages for batch log from database.
	 */
	public void loadBatchLogMessagesByStudentRegistration() {
		if (this.loadBatchLogMessagesFromDB) {
			this.setStudentRegistrationBatchLogMessages(this.batchLogMessageService.findBatchLogMessagesByStudentRegistrationId(this.currentStudentRegistration
					.getId()));
			Collections.sort((List<BatchLogMessage>) this.getStudentRegistrationBatchLogMessages(), new BatchLogMessageComparator(
					BatchLogMessageComparator.Order.ID));
			this.loadBatchLogMessagesFromDB = false;
		}
	}

}
