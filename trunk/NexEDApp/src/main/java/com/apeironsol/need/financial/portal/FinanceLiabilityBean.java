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
import com.apeironsol.need.inventory.model.BranchLiability;
import com.apeironsol.need.inventory.service.BranchLiabilityService;
import com.apeironsol.need.util.constants.BuildingBlockConstant;
import com.apeironsol.need.util.portal.ViewUtil;
import com.apeironsol.need.util.searchcriteria.BranchLiabilitySearchCriteria;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;

/**
 * JSF managed for financial liability.
 * 
 * @author Pradeep
 */
@Named
@Scope(value = "session")
public class FinanceLiabilityBean extends AbstractTabbedBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long				serialVersionUID				= 1964206129200014640L;

	/**
	 * Building block service.
	 */
	@Resource
	private BuildingBlockService			buildingBlockService;

	/**
	 * Branch liability.
	 */
	private BranchLiability					branchLiability;

	/**
	 * Branch liability service.
	 */
	@Resource
	private BranchLiabilityService			branchLiabilityService;

	/**
	 * Building blocks for type liabilities.
	 */
	private Collection<BuildingBlock>		liabilitiesTypeBuildingBlocks;

	/**
	 * Branch liabilities.
	 */
	private Collection<BranchLiability>		branchLiabilitiesBySearchCriteria;

	/**
	 * Indicator to load liability types from database.
	 */
	private boolean							loadLiabilityTypesFromDB;

	/**
	 * Branch assert used for searching.
	 */
	private BranchLiabilitySearchCriteria	branchLiabilitySearchCriteria	= null;

	@PostConstruct
	public void init() {
		this.initializeSearchCriteria();
	}

	public void initializeSearchCriteria() {
		if (this.branchLiabilitySearchCriteria == null) {
			this.branchLiabilitySearchCriteria = new BranchLiabilitySearchCriteria(this.sessionBean.getCurrentBranch());
		}
	}

	/**
	 * @return the loadLiabilityTypesFromDB
	 */
	public boolean isLoadLiabilityTypesFromDB() {
		return this.loadLiabilityTypesFromDB;
	}

	/**
	 * @param loadLiabilityTypesFromDB
	 *            the loadLiabilityTypesFromDB to set
	 */
	public void setLoadLiabilityTypesFromDB(final boolean loadLiabilityTypesFromDB) {
		this.loadLiabilityTypesFromDB = loadLiabilityTypesFromDB;
	}

	/**
	 * @return the liabilitiesTypeBuildingBlocks
	 */
	public Collection<BuildingBlock> getLiabilitiesTypeBuildingBlocks() {
		return this.liabilitiesTypeBuildingBlocks;
	}

	/**
	 * @param liabilitiesTypeBuildingBlocks
	 *            the liabilitiesTypeBuildingBlocks to set
	 */
	public void setLiabilitiesTypeBuildingBlocks(final Collection<BuildingBlock> liabilitiesTypeBuildingBlocks) {
		this.liabilitiesTypeBuildingBlocks = liabilitiesTypeBuildingBlocks;
	}

	@Override
	public void onTabChange() {
		this.loadLiabilityTypesFromDB = true;
		this.setViewOrNewAction(false);
	}

	/**
	 * @return the branchLiabilities
	 */
	public Collection<BranchLiability> getBranchLiabilitiesBySearchCriteria() {
		return this.branchLiabilitiesBySearchCriteria;
	}

	/**
	 * @param branchLiabilities
	 *            the branchLiabilities to set
	 */
	public void setBranchLiabilitiesBySearchCriteria(final Collection<BranchLiability> branchLiabilitiesBySearchCriteria) {
		this.branchLiabilitiesBySearchCriteria = branchLiabilitiesBySearchCriteria;
	}

	/**
	 * @return the branchLiability
	 */
	public BranchLiability getBranchLiability() {
		return this.branchLiability;
	}

	/**
	 * @param branchLiability
	 *            the branchLiability to set
	 */
	public void setBranchLiability(final BranchLiability branchLiability) {
		this.branchLiability = branchLiability;
	}

	/**
	 * Create new academic year Liability.
	 */
	public String newBranchLiability() {
		this.branchLiability = new BranchLiability();
		this.branchLiability.setBranch(this.sessionBean.getCurrentBranch());
		this.setViewOrNewAction(true);
		return null;
	}

	/**
	 * Saves academic year Liability to database.
	 */
	public String saveBranchLiability() {
		this.branchLiability = this.branchLiabilityService.saveBranchLiability(this.branchLiability);
		this.setViewOrNewAction(false);
		return null;
	}

	/**
	 * Removes academic year Liability from database.
	 */
	public String removeBranchLiability() {
		this.branchLiabilityService.removeBranchLiability(this.branchLiability);
		this.setViewOrNewAction(false);
		return null;
	}

	/**
	 * Removes academic year Liability from database.
	 */
	public String calcelAction() {
		this.setViewOrNewAction(false);
		return null;
	}

	/**
	 * Removes academic year Liability from database.
	 */
	public String viewBranchLiability() {
		this.setViewOrNewAction(true);
		return null;
	}

	/**
	 * Load building blocks for liability type for current branch.
	 */
	public void loadLiabilitiesBuildingBlocks() {
		if (this.loadLiabilityTypesFromDB) {
			this.liabilitiesTypeBuildingBlocks = this.buildingBlockService.findBuildingBlocksbyBranchIdAndBuildingBlockType(this.sessionBean.getCurrentBranch()
					.getId(), BuildingBlockConstant.LIABILITY_TYPE);
			this.loadLiabilityTypesFromDB = false;
		}
	}

	/**
	 * Retrieve branch liabilities for academic year and current branch.
	 * 
	 * @param academicYear
	 *            academic year.
	 * @return
	 */
	public Collection<BranchLiability> getBranchLiabilitiesForAcademicYearAndCurrentBranch(final AcademicYear academicYear) {
		return this.branchLiabilityService.findBranchLiabilitiesByBranchIdAndAcademicYear(this.sessionBean.getCurrentBranch().getId(), academicYear);
	}

	/**
	 * @return the branchLiabilitySearchCriteria
	 */
	public BranchLiabilitySearchCriteria getBranchLiabilitySearchCriteria() {
		return this.branchLiabilitySearchCriteria;
	}

	/**
	 * @param branchLiabilitySearchCriteria
	 *            the branchLiabilitySearchCriteria to set
	 */
	public void setBranchLiabilitySearchCriteria(final BranchLiabilitySearchCriteria branchLiabilitySearchCriteria) {
		this.branchLiabilitySearchCriteria = branchLiabilitySearchCriteria;
	}

	public String searchBranchLiabilitiesBySearchCriteria() {
		if (this.branchLiabilitySearchCriteria.isSearchCriteriaIsEmpty()) {
			ViewUtil.addMessage("Please enter search criteria.", FacesMessage.SEVERITY_ERROR);
		} else {
			this.branchLiabilitySearchCriteria.setBranch(this.sessionBean.getCurrentBranch());
			this.setBranchLiabilitiesBySearchCriteria(this.branchLiabilityService.findBranchLiabilitiesBySearchCriteria(this.branchLiabilitySearchCriteria));
			if (this.getBranchLiabilitiesBySearchCriteria() == null || this.getBranchLiabilitiesBySearchCriteria().isEmpty()) {
				ViewUtil.addMessage("No liabilities found for entered search criteria..", FacesMessage.SEVERITY_INFO);
			}
		}
		return null;
	}

	public String resetSearchCriteria() {
		this.branchLiabilitySearchCriteria.resetSeacrhCriteria();
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
