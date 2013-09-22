/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.reporting.model;

import java.util.Date;
import java.util.Map;

import com.apeironsol.need.core.model.Relation;
import com.apeironsol.need.core.model.Student;
import com.apeironsol.need.reporting.util.JReportParameter;
import com.apeironsol.need.reporting.util.JReportUsingParameter;
import com.apeironsol.need.reporting.util.JReportUtils;

/**
 * 
 * @author pradeep
 * 
 */
public class AdmissionReportParameterBean implements JReportUsingParameter {

	String			reportFileName;

	@JReportParameter(name = "branchName")
	private String	branchName;

	@JReportParameter(name = "registrationNumber")
	private String	registrationNumber;

	@JReportParameter(name = "registrationDate")
	private Date	registrationDate;

	@JReportParameter(name = "admissionNumber")
	private String	admissionNumber;

	@JReportParameter(name = "admissionStatus")
	private String	admissionStatus;

	@JReportParameter(name = "contactPerson")
	private String	contactPerson;

	@JReportParameter(name = "studentLastName")
	private String	studentLastName;

	@JReportParameter(name = "studentFirstName")
	private String	studentFirstName;

	@JReportParameter(name = "studentMiddleName")
	private String	studentMiddleName;

	@JReportParameter(name = "studentStreet")
	private String	studentStreet;

	@JReportParameter(name = "studentCity")
	private String	studentCity;

	@JReportParameter(name = "studentState")
	private String	studentState;

	@JReportParameter(name = "studentZipCode")
	private String	studentZipCode;

	@JReportParameter(name = "studentHomePhone")
	private String	studentHomePhone;

	@JReportParameter(name = "studentPreviosSchool")
	private String	studentPreviosSchool;

	@JReportParameter(name = "parentLastName")
	private String	parentLastName;

	@JReportParameter(name = "parentFirstName")
	private String	parentFirstName;

	@JReportParameter(name = "parentMiddleName")
	private String	parentMiddleName;

	@JReportParameter(name = "parentStreet")
	private String	parentStreet;

	@JReportParameter(name = "parentCity")
	private String	parentCity;

	@JReportParameter(name = "parentState")
	private String	parentState;

	@JReportParameter(name = "parentZipCode")
	private String	parentZipCode;

	@JReportParameter(name = "parentHomePhone")
	private String	parentHomePhone;

	public String getBranchName() {
		return this.branchName;
	}

	public void setBranchName(final String branchName) {
		this.branchName = branchName;
	}

	public String getRegistrationNumber() {
		return this.registrationNumber;
	}

	public void setRegistrationNumber(final String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public Date getRegistrationDate() {
		return this.registrationDate;
	}

	public void setRegistrationDate(final Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getAdmissionNumber() {
		return this.admissionNumber;
	}

	public void setAdmissionNumber(final String admissionNumber) {
		this.admissionNumber = admissionNumber;
	}

	public String getAdmissionStatus() {
		return this.admissionStatus;
	}

	public void setAdmissionStatus(final String admissionStatus) {
		this.admissionStatus = admissionStatus;
	}

	public String getContactPerson() {
		return this.contactPerson;
	}

	public void setContactPerson(final String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getStudentLastName() {
		return this.studentLastName;
	}

	public void setStudentLastName(final String studentLastName) {
		this.studentLastName = studentLastName;
	}

	public String getStudentFirstName() {
		return this.studentFirstName;
	}

	public void setStudentFirstName(final String studentFirstName) {
		this.studentFirstName = studentFirstName;
	}

	public String getStudentMiddleName() {
		return this.studentMiddleName;
	}

	public void setStudentMiddleName(final String studentMiddleName) {
		this.studentMiddleName = studentMiddleName;
	}

	public String getStudentStreet() {
		return this.studentStreet;
	}

	public void setStudentStreet(final String studentStreet) {
		this.studentStreet = studentStreet;
	}

	public String getStudentCity() {
		return this.studentCity;
	}

	public void setStudentCity(final String studentCity) {
		this.studentCity = studentCity;
	}

	public String getStudentState() {
		return this.studentState;
	}

	public void setStudentState(final String studentState) {
		this.studentState = studentState;
	}

	public String getStudentZipCode() {
		return this.studentZipCode;
	}

	public void setStudentZipCode(final String studentZipCode) {
		this.studentZipCode = studentZipCode;
	}

	public String getStudentHomePhone() {
		return this.studentHomePhone;
	}

	public void setStudentHomePhone(final String studentHomePhone) {
		this.studentHomePhone = studentHomePhone;
	}

	public String getStudentPreviosSchool() {
		return this.studentPreviosSchool;
	}

	public void setStudentPreviosSchool(final String studentPreviosSchool) {
		this.studentPreviosSchool = studentPreviosSchool;
	}

	public String getParentLastName() {
		return this.parentLastName;
	}

	public void setParentLastName(final String parentLastName) {
		this.parentLastName = parentLastName;
	}

	public String getParentFirstName() {
		return this.parentFirstName;
	}

	public void setParentFirstName(final String parentFirstName) {
		this.parentFirstName = parentFirstName;
	}

	public String getParentMiddleName() {
		return this.parentMiddleName;
	}

	public void setParentMiddleName(final String parentMiddleName) {
		this.parentMiddleName = parentMiddleName;
	}

	public String getParentStreet() {
		return this.parentStreet;
	}

	public void setParentStreet(final String parentStreet) {
		this.parentStreet = parentStreet;
	}

	public String getParentCity() {
		return this.parentCity;
	}

	public void setParentCity(final String parentCity) {
		this.parentCity = parentCity;
	}

	public String getParentState() {
		return this.parentState;
	}

	public void setParentState(final String parentState) {
		this.parentState = parentState;
	}

	public String getParentZipCode() {
		return this.parentZipCode;
	}

	public void setParentZipCode(final String parentZipCode) {
		this.parentZipCode = parentZipCode;
	}

	public String getParentHomePhone() {
		return this.parentHomePhone;
	}

	public void setParentHomePhone(final String parentHomePhone) {
		this.parentHomePhone = parentHomePhone;
	}

	@Override
	public Map<String, Object> getReportParameters() {
		return JReportUtils.getJReportParameters(this.getClass(), this);
	}

	@Override
	public String getJRXMLFilePath() {
		return "student_admission_report_parameter.jrxml";
	}

	@Override
	public String getJasperFilePath() {
		return "student_admission_report_parameter.jasper";
	}

	@Override
	public String getReportOutPutFileName() {
		return this.reportFileName;
	}

	@Override
	public void setReportOutPutFileName(final String reportFileName) {
		this.reportFileName = reportFileName;
	}

	public AdmissionReportParameterBean(final Student student, final Relation parent) {
		this.setReportOutPutFileName(student.getAdmissionNr() != null ? student.getAdmissionNr() : student
				.getRegistrationNr());
		this.setAdmissionNumber(student.getAdmissionNr());
		this.setRegistrationNumber(student.getRegistrationNr());
		this.setBranchName(student.getBranch().getName());
		this.setStudentFirstName(student.getFirstName());
		this.setStudentLastName(student.getLastName());
		this.setStudentMiddleName(student.getMiddleName());
		this.setStudentCity(student.getAddress().getCity());
		this.setStudentState(student.getAddress().getState());

		this.setParentCity(parent.getAddress().getCity());
		this.setParentFirstName(parent.getFirstName());
		this.setParentLastName(parent.getLastName());
		this.setParentMiddleName(parent.getMiddleName());
		this.setParentHomePhone(parent.getAddress().getHomePhoneNr());
		this.setParentState(parent.getAddress().getState());
		this.setParentZipCode(parent.getAddress().getZipCode());
	}
}
