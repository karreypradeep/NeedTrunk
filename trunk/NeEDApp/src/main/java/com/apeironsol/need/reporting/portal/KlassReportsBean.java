/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 */
package com.apeironsol.need.reporting.portal;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.Student;
import com.apeironsol.need.core.model.StudentAbsent;
import com.apeironsol.need.core.model.StudentAcademicYear;
import com.apeironsol.need.core.model.StudentSection;
import com.apeironsol.need.core.portal.AbstractTabbedBean;
import com.apeironsol.need.core.portal.SectionBean;
import com.apeironsol.need.core.portal.SessionBean;
import com.apeironsol.need.core.service.StudentAcademicYearService;
import com.apeironsol.need.core.service.StudentAttendanceService;
import com.apeironsol.need.financial.service.StudentFinancialService;
import com.apeironsol.need.reporting.model.SectionReportParameterBean;
import com.apeironsol.need.reporting.model.StudentReportBean;
import com.apeironsol.need.reporting.util.JReportGenerator;
import com.apeironsol.need.reporting.util.JReportType;
import com.apeironsol.need.util.constants.SectionReportsConstant;
import com.apeironsol.need.util.dataobject.StudentFinancialDO;

/**
 * Managed bean for class reports.
 * 
 * @author Pradeep
 */
@Named
@Scope(value = "session")
public class KlassReportsBean extends AbstractTabbedBean {

	/**
	 * 
	 */
	private static final long		serialVersionUID	= 5325262248625265089L;

	@Resource
	StudentFinancialService			studentFinancialService;

	@Resource
	StudentAcademicYearService		studentAcademicYearService;

	@Resource
	StudentAttendanceService		studentAttendanceService;

	/**
	 * Section bean.
	 */
	@Resource
	SectionBean						sectionBean;

	/**
	 * Section bean.
	 */
	@Resource
	SessionBean						sessionBean;

	private SectionReportsConstant	sectionReportType	= SectionReportsConstant.FINANCIAL_REPORT;

	public KlassReportsBean() {
	}

	@Override
	public void onTabChange() {

	}

	/**
	 * @return the sectionReport
	 */
	public SectionReportsConstant getSectionReportType() {
		return this.sectionReportType;
	}

	/**
	 * @param sectionReport
	 *            the sectionReport to set
	 */
	public void setSectionReportType(final SectionReportsConstant sectionReportType) {
		this.sectionReportType = sectionReportType;
	}

	private void addMessage(final FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public String generateReport() {
		if (this.sectionReportType == null) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select report type to generate.",
					"Please select report type to generate.");
			this.addMessage(message);
		} else {
			try {
				this.getGenerateStudentAdmissionReport();
			} catch (JRException e) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());
				this.addMessage(message);
			}

		}
		return null;
	}

	public StreamedContent getGenerateStudentAdmissionReport() throws JRException {

		this.sectionBean.setLoadStudentsForSectionFlag(true);
		this.sectionBean.loadStudentsForSection();
		Collection<Student> sectionStudents = new ArrayList<Student>();
		if (this.sectionBean.getStudentsForSection() != null) {
			for (StudentSection studentSection : this.sectionBean.getStudentsForSection()) {
				sectionStudents.add(studentSection.getStudentAcademicYear().getStudent());
			}
		}
		List<StudentReportBean> studentReportBeans = new ArrayList<StudentReportBean>();
		if (SectionReportsConstant.ATTENDANCE_REPORT.equals(this.getSectionReportType())) {
			studentReportBeans = this.loadSectionStudentsAttendanceReportDetails(sectionStudents);
		} else if (SectionReportsConstant.FINANCIAL_REPORT.equals(this.getSectionReportType())) {
			studentReportBeans = this.loadSectionStudentsFinancialReportDetails(sectionStudents);
		}

		final JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(studentReportBeans);
		StreamedContent file;
		SectionReportParameterBean adRoPaBean = new SectionReportParameterBean();
		adRoPaBean.setSectionReportsConstant(this.getSectionReportType());
		adRoPaBean.setAcademicYear(this.sectionBean.getAcademicYearById(this.sectionBean.getAcademicYearId()).getDisplayLabel());
		adRoPaBean.setClassName(this.sectionBean.getSection().getKlass().getName());
		adRoPaBean.setSectioName(this.sectionBean.getSection().getName());
		adRoPaBean.setSchoolName(this.sessionBean.getCurrentBranch().getName());
		adRoPaBean.setReportOutPutFileName(this.sectionBean.getSection().getDispalyNameWithKlassName() + " FinancialReport");
		byte[] bytes = JReportGenerator.generateReport(adRoPaBean.getReportParameters(), beanCollectionDataSource, adRoPaBean.getJasperFilePath(),
				JReportType.PDF_REPORT);
		InputStream stream = new ByteArrayInputStream(bytes);
		file = new DefaultStreamedContent(stream, "application/pdf", adRoPaBean.getReportOutPutFileName() + ".pdf");
		return file;
	}

	/**
	 * Loads StudentReportBean for section students with financial details.
	 * 
	 * @param sectionStudents
	 *            section students.
	 * @return StudentReportBean for section students with financial details.
	 */
	private List<StudentReportBean> loadSectionStudentsFinancialReportDetails(final Collection<Student> sectionStudents) {
		List<StudentReportBean> studentReportBeans = new ArrayList<StudentReportBean>();
		for (Student student : sectionStudents) {
			Collection<StudentFinancialDO> studentFinancialDOs = this.studentFinancialService.getStudentFinancialDetailsByStudentIdAndAcadmicYearId(
					student.getId(), this.sectionBean.getAcademicYearId());
			studentReportBeans.add(StudentReportBean.convertToStudentReportBeanForFinancialReport(student, studentFinancialDOs));
		}
		return studentReportBeans;
	}

	/**
	 * Loads StudentReportBean for section students with attendance details.
	 * 
	 * @param sectionStudents
	 *            section students.
	 * @return StudentReportBean for section students with attendance details.
	 */
	private List<StudentReportBean> loadSectionStudentsAttendanceReportDetails(final Collection<Student> sectionStudents) {
		List<StudentReportBean> studentReportBeans = new ArrayList<StudentReportBean>();
		int totalNoOfDays = this.getNoOfAttendanceDaysForCurrentBranchAndAcademisYearById(this.sectionBean.getAcademicYearId());
		for (Student student : sectionStudents) {
			StudentAcademicYear studentAcademicYear = this.studentAcademicYearService.findStudentAcademicYearByStudentIdAndAcademicYearId(student.getId(),
					this.sectionBean.getAcademicYearId());
			Collection<StudentAbsent> studentAttendance = this.studentAttendanceService.findStudentAttendanceByScetionIdAndStudentAcademicYearId(
					this.sectionBean.getSection().getId(), studentAcademicYear.getId());
			studentReportBeans.add(StudentReportBean.convertToStudentReportBeanForAttendanceReport(student, totalNoOfDays, studentAttendance.size()));
		}
		return studentReportBeans;
	}
}
