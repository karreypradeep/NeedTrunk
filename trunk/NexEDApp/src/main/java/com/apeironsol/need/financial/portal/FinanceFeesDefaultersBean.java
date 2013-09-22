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
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.core.model.StudentSection;
import com.apeironsol.need.core.portal.AbstractTabbedBean;
import com.apeironsol.need.core.portal.StudentBean;
import com.apeironsol.need.core.portal.util.ViewContentHandlerBean;
import com.apeironsol.need.financial.service.StudentFinancialService;
import com.apeironsol.need.util.constants.ViewContentConstant;
import com.apeironsol.need.util.dataobject.StudentFinancialAcademicYearDO;
import com.apeironsol.need.util.portal.ViewUtil;
import com.apeironsol.need.util.searchcriteria.FeeDueSearchCriteria;
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
public class FinanceFeesDefaultersBean extends AbstractTabbedBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long							serialVersionUID		= -3943056331375905352L;

	private FeeDueSearchCriteria						feeDueSearchCriteria	= null;

	private Collection<StudentFinancialAcademicYearDO>	studentFinancialAcademicYearDOs;

	@Resource
	private StudentFinancialService						studentFinancialService;

	@Resource
	private ViewContentHandlerBean						viewContentHandlerBean;

	@Resource
	private StudentBean									studentBean;

	private StudentSection								currentStudentSection;

	private Collection<Section>							sectionsForSearhCriteriaByKlass;

	@PostConstruct
	public void init() {
		this.initializeSearchCriteria();
	}

	public void initializeSearchCriteria() {
		if (this.getFeeDueSearchCriteria() == null) {
			this.setFeeDueSearchCriteria(new FeeDueSearchCriteria(this.sessionBean.getCurrentBranch()));
		}
	}

	@Override
	public void onTabChange() {
		this.setViewOrNewAction(false);
	}

	/**
	 * @return the feeCollectedSearchCriteria
	 */
	public FeeDueSearchCriteria getFeeDueSearchCriteria() {
		return this.feeDueSearchCriteria;
	}

	public void searchFeesDueBySearchCriteria() {
		this.feeDueSearchCriteria.setBranch(this.sessionBean.getCurrentBranch());
		this.setStudentFinancialAcademicYearDOs(this.studentFinancialService.getFeeDefaultersBySearchCriteria(this.feeDueSearchCriteria));
		if (this.getStudentFinancialAcademicYearDOs() == null || this.getStudentFinancialAcademicYearDOs().isEmpty()) {
			ViewUtil.addMessage("No records found for the search criteria.", FacesMessage.SEVERITY_ERROR);
		}
	}

	/**
	 * @param feeCollectedSearchCriteria
	 *            the feeCollectedSearchCriteria to set
	 */
	public void setFeeDueSearchCriteria(final FeeDueSearchCriteria feeDueSearchCriteria) {
		this.feeDueSearchCriteria = feeDueSearchCriteria;
	}

	public String resetSearchCriteria() {
		this.feeDueSearchCriteria.resetSeacrhCriteria();
		return null;
	}

	public void preProcessPDF(final Object document) throws IOException, BadElementException, DocumentException {
		Document pdf = (Document) document;
		pdf.open();
		pdf.setPageSize(PageSize.A4);
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		try {
			String logo = servletContext.getRealPath("") + File.separator + "images" + File.separator + "school_logo.png";
			pdf.add(Image.getInstance(logo));
		} catch (IOException e) {
			// TODO: handle exception
		}
		pdf.addTitle(this.sessionBean.getCurrentBranch().getName());
		pdf.addHeader("Header", "Fee collected");
	}

	public void onChangeAcademicYear() {
		this.feeDueSearchCriteria.setDueDate(null);
		this.handleFromKlassChange();
	}

	/**
	 * @return the studentFinancialAcademicYearDOs
	 */
	public Collection<StudentFinancialAcademicYearDO> getStudentFinancialAcademicYearDOs() {
		return this.studentFinancialAcademicYearDOs;
	}

	/**
	 * @param studentFinancialAcademicYearDOs
	 *            the studentFinancialAcademicYearDOs to set
	 */
	public void setStudentFinancialAcademicYearDOs(final Collection<StudentFinancialAcademicYearDO> studentFinancialAcademicYearDOs) {
		this.studentFinancialAcademicYearDOs = studentFinancialAcademicYearDOs;
	}

	/**
	 * @return the currentStudentSection
	 */
	public StudentSection getCurrentStudentSection() {
		return this.currentStudentSection;
	}

	/**
	 * @param currentStudentSection
	 *            the currentStudentSection to set
	 */
	public void setCurrentStudentSection(final StudentSection currentStudentSection) {
		this.currentStudentSection = currentStudentSection;
	}

	public void viewStudent() {
		this.viewContentHandlerBean.setCurrentViewContent(ViewContentConstant.BRANCH_STUDENTS);
		this.studentBean.loadStudentsDetailsBeforeDisplayInformation(this.currentStudentSection);
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

	public void handleFromKlassChange() {
		this.feeDueSearchCriteria.setSection(null);
		if (this.feeDueSearchCriteria.getAcademicYear() != null && this.feeDueSearchCriteria.getKlass() != null) {
			this.setSectionsForSearhCriteriaByKlass(this.sectionService.findActiveSectionsByKlassIdAndAcademicYearId(this.feeDueSearchCriteria.getKlass()
					.getId(), this.feeDueSearchCriteria.getAcademicYear().getId()));
		}
	}
}
