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

import com.apeironsol.need.core.model.Relation;
import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.core.model.StudentSection;
import com.apeironsol.need.core.portal.AbstractTabbedBean;
import com.apeironsol.need.core.portal.StudentBean;
import com.apeironsol.need.core.portal.util.ViewContentHandlerBean;
import com.apeironsol.need.core.service.RelationService;
import com.apeironsol.need.core.service.StudentService;
import com.apeironsol.need.core.service.StudentStatusHistoryService;
import com.apeironsol.need.financial.model.StudentFeeTransaction;
import com.apeironsol.need.financial.service.StudentFinancialService;
import com.apeironsol.need.util.constants.RelationTypeConstant;
import com.apeironsol.need.util.constants.ViewContentConstant;
import com.apeironsol.need.util.dataobject.StudentFeeTransactionDO;
import com.apeironsol.need.util.portal.ViewUtil;
import com.apeironsol.need.util.searchcriteria.FeeCollectedSearchCriteria;
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
public class FinanceFeesCollectedBean extends AbstractTabbedBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long					serialVersionUID			= -3943056331375905352L;

	@Resource
	private StudentFinancialService				studentFinancialService;

	private FeeCollectedSearchCriteria			feeCollectedSearchCriteria	= null;

	/**
	 * Purchase order.
	 */
	private Collection<StudentFeeTransaction>	studentFeeTransactionsProcessed;

	/**
	 * Purchase order.
	 */
	private StudentFeeTransaction				studentFeeTransaction;

	private StudentFeeTransactionDO				studentFeeTransactionDO;

	private StudentSection						studentSection;

	@Resource
	private StudentService						studentService;

	@Resource
	private ViewContentHandlerBean				viewContentHandlerBean;

	@Resource
	private StudentBean							studentBean;

	private Collection<Section>					sectionsForSearhCriteriaByKlass;

	@Resource
	protected RelationService					relationService;

	@Resource
	protected StudentStatusHistoryService		studentStatusHistoryService;

	private String								studentParentOrGuardianName;

	/**
	 * @return the studentParentOrGuardianName
	 */
	public String getStudentParentOrGuardianName() {
		return this.studentParentOrGuardianName;
	}

	/**
	 * @param studentParentOrGuardianName
	 *            the studentParentOrGuardianName to set
	 */
	public void setStudentParentOrGuardianName(final String studentParentOrGuardianName) {
		this.studentParentOrGuardianName = studentParentOrGuardianName;
	}

	@PostConstruct
	public void init() {
		this.initializeSearchCriteria();
	}

	public void initializeSearchCriteria() {
		if (this.getFeeCollectedSearchCriteria() == null) {
			this.setFeeCollectedSearchCriteria(new FeeCollectedSearchCriteria(this.sessionBean.getCurrentBranch()));
		}
	}

	@Override
	public void onTabChange() {
		this.setViewOrNewAction(false);
	}

	/**
	 * @return the feeCollectedSearchCriteria
	 */
	public FeeCollectedSearchCriteria getFeeCollectedSearchCriteria() {
		return this.feeCollectedSearchCriteria;
	}

	public void searchFeesCollectedBySearchCriteria() {
		this.feeCollectedSearchCriteria.setBranch(this.sessionBean.getCurrentBranch());
		this.studentFeeTransactionsProcessed = this.studentFinancialService.findFeesCollectedBySearchCriteria(this.feeCollectedSearchCriteria);
		if (this.studentFeeTransactionsProcessed == null || this.studentFeeTransactionsProcessed.isEmpty()) {
			ViewUtil.addMessage("No fee collected records found for entered search criteria..", FacesMessage.SEVERITY_INFO);
		}
	}

	/**
	 * @param feeCollectedSearchCriteria
	 *            the feeCollectedSearchCriteria to set
	 */
	public void setFeeCollectedSearchCriteria(final FeeCollectedSearchCriteria feeCollectedSearchCriteria) {
		this.feeCollectedSearchCriteria = feeCollectedSearchCriteria;
	}

	public String resetSearchCriteria() {
		this.feeCollectedSearchCriteria.resetSeacrhCriteria();
		return null;
	}

	/**
	 * @return the studentFeeTransactionsProcessed
	 */
	public Collection<StudentFeeTransaction> getStudentFeeTransactionsProcessed() {
		return this.studentFeeTransactionsProcessed;
	}

	/**
	 * @param studentFeeTransactionsProcessed
	 *            the studentFeeTransactionsProcessed to set
	 */
	public void setStudentFeeTransactionsProcessed(final Collection<StudentFeeTransaction> studentFeeTransactionsProcessed) {
		this.studentFeeTransactionsProcessed = studentFeeTransactionsProcessed;
	}

	public double getTotalFeeCollected() {
		double totalFeeCollected = 0.0;
		if (this.studentFeeTransactionsProcessed != null && !this.studentFeeTransactionsProcessed.isEmpty()) {
			for (final StudentFeeTransaction studentFeeTransaction : this.studentFeeTransactionsProcessed) {
				totalFeeCollected += studentFeeTransaction.getAmount();
			}
		}
		return totalFeeCollected;
	}

	/**
	 * @return the studentFeeTransaction
	 */
	public StudentFeeTransaction getStudentFeeTransaction() {
		return this.studentFeeTransaction;
	}

	/**
	 * @param purchaseOrderItem
	 *            the purchaseOrderItem to set
	 */
	public void setStudentFeeTransaction(final StudentFeeTransaction studentFeeTransaction) {
		this.studentFeeTransaction = studentFeeTransaction;
	}

	/**
	 * @return the studentFeeTransactionDO
	 */
	public StudentFeeTransactionDO getStudentFeeTransactionDO() {
		return this.studentFeeTransactionDO;
	}

	/**
	 * @param studentFeeTransactionDO
	 *            the studentFeeTransactionDO to set
	 */
	public void setStudentFeeTransactionDO(final StudentFeeTransactionDO studentFeeTransactionDO) {
		this.studentFeeTransactionDO = studentFeeTransactionDO;
	}

	public void loadStudentFinancialDOByTransaction() {
		this.studentFeeTransactionDO = this.studentFinancialService.retriveStudentFeeTransactionDetailsByTransactionNr(this.studentFeeTransaction
				.getTransactionNr());
		this.getStudentCurrentSection();
		this.loadStudentParentOrGuardianName();
	}

	public String loadStudentParentOrGuardianName() {
		Relation studentFather = null;
		Relation studentMother = null;
		Relation studentGuardian = null;
		final Collection<Relation> relations = this.relationService.findRelationsDetailedByStudentId(this.getStudentSection().getStudentAcademicYear()
				.getStudent().getId());
		if (relations != null && !relations.isEmpty()) {
			for (final Relation relation : relations) {
				if (relation.getRelationType().equals(RelationTypeConstant.FATHER)) {
					studentFather = relation;
				} else if (relation.getRelationType().equals(RelationTypeConstant.MOTHER)) {
					studentMother = relation;
				} else if (relation.getRelationType().equals(RelationTypeConstant.GUARDIAN)) {
					studentGuardian = relation;
				}
			}
		}
		if (studentFather != null) {
			this.studentParentOrGuardianName = studentFather.getDisplayName();
		} else if (studentMother != null) {
			this.studentParentOrGuardianName = studentMother.getDisplayName();
		} else if (studentGuardian != null) {
			this.studentParentOrGuardianName = studentGuardian.getDisplayName();
		}
		return this.studentParentOrGuardianName;
	}

	public void getStudentCurrentSection() {
		this.setStudentSection(this.studentService.findStudentSectionByStudentAcademicYearIdAndActiveStatus(this.studentFeeTransactionDO
				.getStudentFeeTransaction().getStudentAcademicYear().getId()));
	}

	/**
	 * @return the studentSection
	 */
	public StudentSection getStudentSection() {
		return this.studentSection;
	}

	/**
	 * @param studentSection
	 *            the studentSection to set
	 */
	public void setStudentSection(final StudentSection studentSection) {
		this.studentSection = studentSection;
	}

	public void preProcessPDF(final Object document) throws BadElementException, DocumentException {
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

	public void onChangeAcademicYear() {
		this.feeCollectedSearchCriteria.setFromDate(null);
		this.feeCollectedSearchCriteria.setToDate(null);
		this.feeCollectedSearchCriteria.setSection(null);
		this.handleFromKlassChange();
	}

	public void viewStudent() {
		this.loadStudentFinancialDOByTransaction();
		this.viewContentHandlerBean.setCurrentViewContent(ViewContentConstant.BRANCH_STUDENTS);
		this.studentBean.loadStudentsDetailsBeforeDisplayInformation(this.studentSection);
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
		this.feeCollectedSearchCriteria.setSection(null);
		if (this.feeCollectedSearchCriteria.getAcademicYear() != null && this.feeCollectedSearchCriteria.getKlass() != null) {
			this.setSectionsForSearhCriteriaByKlass(this.sectionService.findActiveSectionsByKlassIdAndAcademicYearId(this.feeCollectedSearchCriteria.getKlass()
					.getId(), this.feeCollectedSearchCriteria.getAcademicYear().getId()));
		}
	}
}
