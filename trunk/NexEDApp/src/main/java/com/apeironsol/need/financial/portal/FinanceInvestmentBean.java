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

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.model.BuildingBlock;
import com.apeironsol.need.core.portal.AbstractTabbedBean;
import com.apeironsol.need.core.service.BuildingBlockService;
import com.apeironsol.need.financial.model.BranchInvestment;
import com.apeironsol.need.financial.service.BranchInvestmentService;
import com.apeironsol.need.util.constants.BuildingBlockConstant;
import com.apeironsol.need.util.portal.ViewUtil;
import com.apeironsol.need.util.searchcriteria.BranchInvestmentSearchCriteria;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;

/**
 * JSF managed for financial investment.
 * 
 * @author Pradeep
 */
@Named
@Scope(value = "session")
public class FinanceInvestmentBean extends AbstractTabbedBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long				serialVersionUID				= -5310075017095445544L;

	/**
	 * Building block service.
	 */
	@Resource
	private BuildingBlockService			buildingBlockService;

	/**
	 * Branch investment.
	 */
	private BranchInvestment				branchInvestment;

	/**
	 * Branch investment service.
	 */
	@Resource
	private BranchInvestmentService			branchInvestmentService;

	/**
	 * Building blocks for type investments.
	 */
	private Collection<BuildingBlock>		investmentsTypeBuildingBlocks;

	/**
	 * Branch investments.
	 */
	private Collection<BranchInvestment>	branchInvestmentsBySearchCriteria;

	/**
	 * Indicator to load investment types from database.
	 */
	private boolean							loadInvestmentTypesFromDB;

	/**
	 * Branch assert used for searching.
	 */
	private BranchInvestmentSearchCriteria	branchInvestmentSearchCriteria	= null;

	@PostConstruct
	public void init() {
		this.initializeSearchCriteria();
	}

	public void initializeSearchCriteria() {
		if (this.branchInvestmentSearchCriteria == null) {
			this.branchInvestmentSearchCriteria = new BranchInvestmentSearchCriteria(this.sessionBean.getCurrentBranch());
		}
	}

	/**
	 * @return the loadInvestmentTypesFromDB
	 */
	public boolean isLoadInvestmentTypesFromDB() {
		return this.loadInvestmentTypesFromDB;
	}

	/**
	 * @param loadInvestmentTypesFromDB
	 *            the loadInvestmentTypesFromDB to set
	 */
	public void setLoadInvestmentTypesFromDB(final boolean loadInvestmentTypesFromDB) {
		this.loadInvestmentTypesFromDB = loadInvestmentTypesFromDB;
	}

	/**
	 * @return the investmentsTypeBuildingBlocks
	 */
	public Collection<BuildingBlock> getInvestmentsTypeBuildingBlocks() {
		return this.investmentsTypeBuildingBlocks;
	}

	/**
	 * @param investmentsTypeBuildingBlocks
	 *            the investmentsTypeBuildingBlocks to set
	 */
	public void setInvestmentsTypeBuildingBlocks(final Collection<BuildingBlock> investmentsTypeBuildingBlocks) {
		this.investmentsTypeBuildingBlocks = investmentsTypeBuildingBlocks;
	}

	@Override
	public void onTabChange() {
		this.setViewOrNewAction(false);
	}

	/**
	 * @return the branchInvestmentsBySearchCriteria
	 */
	public Collection<BranchInvestment> getBranchInvestmentsBySearchCriteria() {
		return this.branchInvestmentsBySearchCriteria;
	}

	/**
	 * @param branchInvestmentsBySearchCriteria
	 *            the branchInvestmentsBySearchCriteria to set
	 */
	public void setBranchInvestmentsBySearchCriteria(final Collection<BranchInvestment> branchInvestmentsBySearchCriteria) {
		this.branchInvestmentsBySearchCriteria = branchInvestmentsBySearchCriteria;
	}

	/**
	 * @return the branchInvestment
	 */
	public BranchInvestment getBranchInvestment() {
		return this.branchInvestment;
	}

	/**
	 * @param branchInvestment
	 *            the branchInvestment to set
	 */
	public void setBranchInvestment(final BranchInvestment branchInvestment) {
		this.branchInvestment = branchInvestment;
	}

	/**
	 * Create new academic year Investment.
	 */
	public String newBranchInvestment() {
		this.branchInvestment = new BranchInvestment();
		this.branchInvestment.setBranch(this.sessionBean.getCurrentBranch());
		this.setViewOrNewAction(true);
		return null;
	}

	/**
	 * Saves academic year Investment to database.
	 */
	public String saveBranchInvestment() {
		this.branchInvestment = this.branchInvestmentService.saveBranchInvestment(this.branchInvestment);
		this.setViewOrNewAction(false);
		return null;
	}

	/**
	 * Removes academic year Investment from database.
	 */
	public String removeBranchInvestment() {
		this.branchInvestmentService.removeBranchInvestment(this.branchInvestment);
		this.setViewOrNewAction(false);
		return null;
	}

	/**
	 * Removes academic year Investment from database.
	 */
	public String calcelAction() {
		this.setViewOrNewAction(false);
		return null;
	}

	/**
	 * Removes academic year Investment from database.
	 */
	public String viewBranchInvestment() {
		this.setViewOrNewAction(true);
		return null;
	}

	/**
	 * Load building blocks for investment type for current branch.
	 */
	public void loadInvestmentBuildingBlocks() {
		if (this.loadInvestmentTypesFromDB) {
			this.investmentsTypeBuildingBlocks = this.buildingBlockService.findBuildingBlocksbyBranchIdAndBuildingBlockType(this.sessionBean.getCurrentBranch()
					.getId(), BuildingBlockConstant.INVESTMENT_TYPE);
			this.loadInvestmentTypesFromDB = false;
		}
	}

	/**
	 * Retrieve branch investments for academic year and current branch.
	 * 
	 * @param academicYear
	 *            academic year.
	 * @return
	 */
	public Collection<BranchInvestment> getBranchInvestmentsForAcademicYearAndCurrentBranch(final AcademicYear academicYear) {
		return this.branchInvestmentService.findBranchInvestmentsByBranchIdAndAcademicYear(this.sessionBean.getCurrentBranch().getId(), academicYear);
	}

	/**
	 * @return the branchInvestmentSearchCriteria
	 */
	public BranchInvestmentSearchCriteria getBranchInvestmentSearchCriteria() {
		return this.branchInvestmentSearchCriteria;
	}

	/**
	 * @param branchInvestmentSearchCriteria
	 *            the branchInvestmentSearchCriteria to set
	 */
	public void setBranchInvestmentSearchCriteria(final BranchInvestmentSearchCriteria branchInvestmentSearchCriteria) {
		this.branchInvestmentSearchCriteria = branchInvestmentSearchCriteria;
	}

	public String searchBranchInvestmentBySearchCriteria() {
		if (this.branchInvestmentSearchCriteria.isSearchCriteriaIsEmpty()) {
			ViewUtil.addMessage("Please enter search criteria.", FacesMessage.SEVERITY_ERROR);
		} else {
			this.branchInvestmentSearchCriteria.setBranch(this.sessionBean.getCurrentBranch());
			this.setBranchInvestmentsBySearchCriteria(this.branchInvestmentService.findBranchInvestmentBySearchCriteria(this.branchInvestmentSearchCriteria));
			if (this.getBranchInvestmentsBySearchCriteria() == null || this.getBranchInvestmentsBySearchCriteria().isEmpty()) {
				ViewUtil.addMessage("No investment found for entered search criteria..", FacesMessage.SEVERITY_INFO);
			}
		}
		return null;
	}

	public String resetSearchCriteria() {
		this.branchInvestmentSearchCriteria.resetSeacrhCriteria();
		return null;
	}

	/**
	 * Pre-process method for processing pdf file to be exported.
	 * 
	 * @param document
	 *            document to be pre processed.
	 * @throws IOException
	 *             IOException.
	 * @throws BadElementException
	 *             Bad element exception.
	 * @throws DocumentException
	 *             document exception.
	 */
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

}
