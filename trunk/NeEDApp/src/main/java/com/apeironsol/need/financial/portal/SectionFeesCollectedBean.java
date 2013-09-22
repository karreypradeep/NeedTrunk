/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 * 
 */
package com.apeironsol.need.financial.portal;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.annotation.Resource;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.portal.AbstractTabbedBean;
import com.apeironsol.need.core.portal.SectionBean;
import com.apeironsol.need.core.portal.StudentBean;
import com.apeironsol.need.core.portal.util.ViewContentHandlerBean;
import com.apeironsol.need.financial.service.SectionFinancialService;
import com.apeironsol.need.financial.service.StudentFinancialService;
import com.apeironsol.need.util.constants.ViewContentConstant;
import com.apeironsol.need.util.dataobject.SectionFinancialDO;
import com.apeironsol.need.util.dataobject.StudentFinancialAcademicYearDO;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;

/**
 * JSF managed for financial income.
 * 
 * @author Pradeep
 */
@Named
@Scope(value = "session")
public class SectionFeesCollectedBean extends AbstractTabbedBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long				serialVersionUID	= -3943056331375905352L;

	@Resource
	private SectionFinancialService			sectionFinancialService;

	@Resource
	private SectionBean						sectionBean;

	private SectionFinancialDO				sectionFinancialDO;

	@Resource
	private ViewContentHandlerBean			viewContentHandlerBean;

	@Resource
	private StudentBean						studentBean;

	private StudentFinancialAcademicYearDO	studentFinancialAcademicYearDO;

	@Resource
	private StudentFinancialService			studentFinancialService;

	/**
	 * @return the sectionFinancialDO
	 */
	public SectionFinancialDO getSectionFinancialDO() {
		return this.sectionFinancialDO;
	}

	/**
	 * @param sectionFinancialDO
	 *            the sectionFinancialDO to set
	 */
	public void setSectionFinancialDO(final SectionFinancialDO sectionFinancialDO) {
		this.sectionFinancialDO = sectionFinancialDO;
	}

	@Override
	public void onTabChange() {
		this.setViewOrNewAction(false);
		this.loadStudentFinancialDetails();
	}

	public void loadStudentFinancialDetails() {
		this.sectionFinancialDO = this.sectionFinancialService.getSectionFeeFinancialDetailsBySectionIdAndAcademicYearIdForDueDate(this.sectionBean
				.getSection().getId(), this.sectionBean.getAcademicYearId(), null, null, false);
	}

	public void preProcessPDF(final Object document) throws IOException, BadElementException, DocumentException {
		final Document pdf = (Document) document;
		pdf.open();
		pdf.setPageSize(PageSize.A4);
		final ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		try {
			final String logo = servletContext.getRealPath("") + File.separator + "images" + File.separator + "school_logo.png";
			pdf.add(Image.getInstance(logo));
		} catch (final IOException e) {
			// TODO: handle exception
		}
		pdf.addTitle(this.sessionBean.getCurrentBranch().getName());
		pdf.addHeader("Header", "Fee collected");
	}

	public void viewStudent() {
		this.viewContentHandlerBean.setCurrentViewContent(ViewContentConstant.BRANCH_STUDENTS);
		this.studentBean.loadStudentsDetailsBeforeDisplayInformation(this.studentFinancialAcademicYearDO.getStudentSection());
	}

	/**
	 * @return the studentFinancialAcademicYearDO
	 */
	public StudentFinancialAcademicYearDO getStudentFinancialAcademicYearDO() {
		return this.studentFinancialAcademicYearDO;
	}

	/**
	 * @param studentFinancialAcademicYearDO
	 *            the studentFinancialAcademicYearDO to set
	 */
	public void setStudentFinancialAcademicYearDO(final StudentFinancialAcademicYearDO studentFinancialAcademicYearDO) {
		this.studentFinancialAcademicYearDO = studentFinancialAcademicYearDO;
	}

	public void getStudentFinancialAcademicYearDODetails(final StudentFinancialAcademicYearDO studentFinancialAcademicYearDO) {
		this.studentFinancialAcademicYearDO = studentFinancialAcademicYearDO;
		this.studentFinancialAcademicYearDO.setStudentFinancialDOs(this.studentFinancialService.getStudentFinancialDetailsByStudentIdAndAcadmicYearId(
				this.studentFinancialAcademicYearDO.getStudentSection().getStudentAcademicYear().getStudent().getId(), this.studentFinancialAcademicYearDO
						.getStudentSection().getStudentAcademicYear().getAcademicYear().getId()));
	}
}
