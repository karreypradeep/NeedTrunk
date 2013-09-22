/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.reporting.portal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Named;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.primefaces.model.StreamedContent;
import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.core.model.StudentSection;
import com.apeironsol.need.core.portal.AbstractPortalBean;
import com.apeironsol.need.core.portal.StudentBean;
import com.apeironsol.need.core.portal.util.ViewContentHandlerBean;
import com.apeironsol.need.reporting.dataobject.ReportDO;
import com.apeironsol.need.reporting.ro.BranchRO;
import com.apeironsol.need.reporting.ro.KlassRO;
import com.apeironsol.need.reporting.ro.SectionRO;
import com.apeironsol.need.reporting.ro.StudentRO;
import com.apeironsol.need.reporting.service.StudentReportService;
import com.apeironsol.need.reporting.util.JReportType;
import com.apeironsol.need.reporting.util.JasperUtil;
import com.apeironsol.need.util.comparator.StudentROComparator;
import com.apeironsol.need.util.constants.StudentReportNamesConstant;
import com.apeironsol.need.util.constants.ViewContentConstant;
import com.apeironsol.need.util.searchcriteria.StudentSearchCriteria;

/**
 * @author sunny
 * 
 */
@Named
@Scope("session")
public class StudentReportBean extends AbstractPortalBean implements Serializable {

	private static final long			serialVersionUID	= -3761824292316034621L;

	@Resource
	StudentReportService				studentReportService;

	private StudentSearchCriteria		studentSearchCriteria;

	private StudentReportNamesConstant	selectedReport;

	private Collection<Section>			sectionsForSearhCriteriaByKlass;

	private Collection<StudentRO>		studentROList;

	private BranchRO					branchRO			= null;

	private JReportType					reportType;

	private StudentSection				currentStudentSection;

	@Resource
	private StudentBean					studentBean;

	@Resource
	private ViewContentHandlerBean		viewContentHandlerBean;

	/**
	 * @return the reportType
	 */
	public JReportType getReportType() {
		return this.reportType;
	}

	/**
	 * @param reportType
	 *            the reportType to set
	 */
	public void setReportType(final JReportType reportType) {
		this.reportType = reportType;
	}

	/**
	 * @return the studentSearchCriteria
	 */
	public StudentSearchCriteria getStudentSearchCriteria() {
		return this.studentSearchCriteria;
	}

	/**
	 * @param studentSearchCriteria
	 *            the studentSearchCriteria to set
	 */
	public void setStudentSearchCriteria(final StudentSearchCriteria studentSearchCriteria) {
		this.studentSearchCriteria = studentSearchCriteria;
	}

	/**
	 * @return the sectionsForSearhCriteriaByKlass
	 */
	public Collection<Section> getSectionsForSearhCriteriaByKlass() {
		return this.sectionsForSearhCriteriaByKlass;
	}

	/**
	 * @param sectionsForSearhCriteriaByKlass
	 *            the sectionsForSearhCriteriaByKlass to set
	 */
	public void setSectionsForSearhCriteriaByKlass(final Collection<Section> sectionsForSearhCriteriaByKlass) {
		this.sectionsForSearhCriteriaByKlass = sectionsForSearhCriteriaByKlass;
	}

	@PostConstruct
	public void init() {
		this.initializeSearchCriteria();
	}

	public void initializeSearchCriteria() {
		if (this.studentSearchCriteria == null) {
			this.studentSearchCriteria = new StudentSearchCriteria(this.sessionBean.getCurrentBranch());
		}
		this.studentROList = new ArrayList<StudentRO>();
	}

	public void handleFromKlassChange() {
		this.studentSearchCriteria.setSection(null);
		if (this.studentSearchCriteria.getKlass() != null) {
			if (this.studentSearchCriteria.getAcademicYear() != null) {
				this.setSectionsForSearhCriteriaByKlass(this.sectionService.findActiveSectionsByKlassIdAndAcademicYearId(this.studentSearchCriteria.getKlass()
						.getId(), this.studentSearchCriteria.getAcademicYear().getId()));
			} else {
				this.setSectionsForSearhCriteriaByKlass(this.sectionService.findActiveSectionsByKlassId(this.studentSearchCriteria.getKlass().getId()));
			}
		} else {
			this.setSectionsForSearhCriteriaByKlass(null);
		}
	}

	/**
	 * @return the selectedReport
	 */
	public StudentReportNamesConstant getSelectedReport() {
		return this.selectedReport;
	}

	/**
	 * @param selectedReport
	 *            the selectedReport to set
	 */
	public void setSelectedReport(final StudentReportNamesConstant selectedReport) {
		this.selectedReport = selectedReport;
	}

	public String resetSearchCriteria() {
		this.studentSearchCriteria.resetSeacrhCriteria();
		this.selectedReport = null;
		this.studentROList.clear();
		return null;
	}

	/**
	 * @return the studentROList
	 */
	public Collection<StudentRO> getStudentROList() {
		return this.studentROList;
	}

	/**
	 * @param studentROList
	 *            the studentROList to set
	 */
	public void setStudentROList(final Collection<StudentRO> studentROList) {
		this.studentROList = studentROList;
	}

	public void searchBranchStudentsDetails() {
		// Current branch by default
		this.branchRO = this.studentReportService.getStudentsReportForBranch(this.selectedReport, this.sessionBean.getCurrentBranch(),
				this.studentSearchCriteria);

		// Forming collection for displaying on UI
		this.studentROList.clear();
		if (this.branchRO != null && this.branchRO.getKlassROList() != null) {
			for (final KlassRO klassRO : this.branchRO.getKlassROList()) {
				for (final SectionRO sectionRO : klassRO.getSectionROList()) {
					for (final StudentRO studentRO : sectionRO.getStudentROList()) {
						this.studentROList.add(studentRO);
					}
				}
			}
		}

		Collections.sort((List<StudentRO>) this.studentROList, new StudentROComparator());

	}

	public StreamedContent generateReport() {
		final ReportDO reportDO = new ReportDO();
		reportDO.setInputJasperFileName(JasperUtil.getFullFilePath("/reports/" + this.selectedReport.getJasperReportFileName()));
		reportDO.setOutputReportName(this.selectedReport.getLabel());
		reportDO.setReportType(this.reportType);

		StreamedContent streamedContent = null;
		if (reportDO.getReportType() != null) {
			final HashMap<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("org_name", this.sessionBean.getCurrentOrganization().getName());
			paramsMap.put("report_title", this.selectedReport.getLabel());
			paramsMap.put("subreport_name", this.selectedReport.getJasperSubReportFileName().toString());
			System.out.println(this.selectedReport.getJasperSubReportFileName().toString());
			paramsMap.put("branchRO", this.branchRO);

			streamedContent = JasperUtil.printReport(reportDO, paramsMap);
		}

		return streamedContent;
	}

	public void postProcessXLS(final Object document) {
		HSSFWorkbook wb = (HSSFWorkbook) document;
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow header = sheet.getRow(0);

		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		HSSFFont hSSFFont = wb.createFont();
		hSSFFont.setFontHeightInPoints((short) 10);
		hSSFFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		cellStyle.setFont(hSSFFont);
		cellStyle.setWrapText(false);

		for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
			HSSFCell cell = header.getCell(i);

			cell.setCellStyle(cellStyle);
		}
	}

	public void viewStudent() {

		this.viewContentHandlerBean.setCurrentViewContent(ViewContentConstant.BRANCH_STUDENTS);
		this.studentBean.loadStudentsDetailsBeforeDisplayInformation(this.getCurrentStudentSection());

	}

	public StudentSection getCurrentStudentSection() {
		return this.currentStudentSection;
	}

	public void setCurrentStudentSection(final StudentSection currentStudentSection) {
		this.currentStudentSection = currentStudentSection;
	}

}
