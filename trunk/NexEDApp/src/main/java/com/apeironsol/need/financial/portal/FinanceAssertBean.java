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
import com.apeironsol.need.inventory.model.BranchAssert;
import com.apeironsol.need.inventory.service.BranchAssertService;
import com.apeironsol.need.util.constants.BuildingBlockConstant;
import com.apeironsol.need.util.portal.ViewUtil;
import com.apeironsol.need.util.searchcriteria.BranchAssertSearchCriteria;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;

/**
 * JSF managed for financial assert.
 * 
 * @author Pradeep
 */
@Named
@Scope(value = "session")
public class FinanceAssertBean extends AbstractTabbedBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long			serialVersionUID			= 1964206129200014640L;

	/**
	 * Building block service.
	 */
	@Resource
	private BuildingBlockService		buildingBlockService;

	/**
	 * Branch assert.
	 */
	private BranchAssert				branchAssert;

	/**
	 * Branch assert service.
	 */
	@Resource
	private BranchAssertService			branchAssertService;

	/**
	 * Building blocks for type asserts.
	 */
	private Collection<BuildingBlock>	assertsTypeBuildingBlocks;

	/**
	 * Branch asserts.
	 */
	private Collection<BranchAssert>	branchAssertsBySearchCriteria;

	/**
	 * Indicator to load assert types from database.
	 */
	private boolean						loadAssertTypesFromDB;

	/**
	 * Branch assert used for searching.
	 */
	private BranchAssertSearchCriteria	branchAssertSearchCriteria	= null;

	/**
	 * @return the loadAssertTypesFromDB
	 */
	public boolean isLoadAssertTypesFromDB() {
		return this.loadAssertTypesFromDB;
	}

	/**
	 * @param loadAssertTypesFromDB
	 *            the loadAssertTypesFromDB to set
	 */
	public void setLoadAssertTypesFromDB(final boolean loadAssertTypesFromDB) {
		this.loadAssertTypesFromDB = loadAssertTypesFromDB;
	}

	/**
	 * @return the assertsTypeBuildingBlocks
	 */
	public Collection<BuildingBlock> getAssertsTypeBuildingBlocks() {
		return this.assertsTypeBuildingBlocks;
	}

	/**
	 * @param assertsTypeBuildingBlocks
	 *            the assertsTypeBuildingBlocks to set
	 */
	public void setAssertsTypeBuildingBlocks(final Collection<BuildingBlock> assertsTypeBuildingBlocks) {
		this.assertsTypeBuildingBlocks = assertsTypeBuildingBlocks;
	}

	@Override
	public void onTabChange() {
		this.setViewOrNewAction(false);
	}

	@PostConstruct
	public void init() {
		this.initializeSearchCriteria();
	}

	public void initializeSearchCriteria() {
		if (this.branchAssertSearchCriteria == null) {
			this.branchAssertSearchCriteria = new BranchAssertSearchCriteria(this.sessionBean.getCurrentBranch());
		}
	}

	/**
	 * @return the branchAssert
	 */
	public BranchAssert getBranchAssert() {
		return this.branchAssert;
	}

	/**
	 * @param branchAssert
	 *            the branchAssert to set
	 */
	public void setBranchAssert(final BranchAssert branchAssert) {
		this.branchAssert = branchAssert;
	}

	/**
	 * Create new academic year Assert.
	 */
	public String newBranchAssert() {
		this.branchAssert = new BranchAssert();
		this.branchAssert.setBranch(this.sessionBean.getCurrentBranch());
		this.setViewOrNewAction(true);
		return null;
	}

	/**
	 * Saves academic year Assert to database.
	 */
	public String saveBranchAssert() {
		this.branchAssert = this.branchAssertService.saveBranchAssert(this.branchAssert);
		this.setViewOrNewAction(false);
		return null;
	}

	/**
	 * Removes academic year Assert from database.
	 */
	public String removeBranchAssert() {
		this.branchAssertService.removeBranchAssert(this.branchAssert);
		this.setViewOrNewAction(false);
		return null;
	}

	/**
	 * Removes academic year Assert from database.
	 */
	public String calcelAction() {
		this.setViewOrNewAction(false);
		return null;
	}

	/**
	 * Removes academic year Assert from database.
	 */
	public String viewBranchAssert() {
		this.setViewOrNewAction(true);
		return null;
	}

	/**
	 * Load building blocks for assert type for current branch.
	 */
	public void loadAssertsBuildingBlocks() {
		if (this.loadAssertTypesFromDB) {
			this.assertsTypeBuildingBlocks = this.buildingBlockService.findBuildingBlocksbyBranchIdAndBuildingBlockType(this.sessionBean.getCurrentBranch()
					.getId(), BuildingBlockConstant.ASSERT_TYPE);
			this.loadAssertTypesFromDB = false;
		}
	}

	/**
	 * Retrieve branch asserts for academic year and current branch.
	 * 
	 * @param academicYear
	 *            academic year.
	 * @return
	 */
	public Collection<BranchAssert> getBranchAssertsForAcademicYearAndCurrentBranch(final AcademicYear academicYear) {
		return this.branchAssertService.findBranchAssertsByBranchIdAndAcademicYear(this.sessionBean.getCurrentBranch().getId(), academicYear);
	}

	/**
	 * /**
	 * 
	 * @return the branchAssertSearchCriteria
	 */
	public BranchAssertSearchCriteria getBranchAssertSearchCriteria() {
		return this.branchAssertSearchCriteria;
	}

	/**
	 * @param branchAssertSearchCriteria
	 *            the branchAssertSearchCriteria to set
	 */
	public void setBranchAssertSearchCriteria(final BranchAssertSearchCriteria branchAssertSearchCriteria) {
		this.branchAssertSearchCriteria = branchAssertSearchCriteria;
	}

	public String searchBranchAssertsBySearchCriteria() {
		if (this.branchAssertSearchCriteria.isSearchCriteriaIsEmpty()) {
			ViewUtil.addMessage("Please enter search criteria.", FacesMessage.SEVERITY_ERROR);
		} else {
			this.branchAssertSearchCriteria.setBranch(this.sessionBean.getCurrentBranch());
			this.setBranchAssertsBySearchCriteria(this.branchAssertService.findBranchAssertsBySearchCriteria(this.branchAssertSearchCriteria));
			if (this.getBranchAssertsBySearchCriteria() == null || this.getBranchAssertsBySearchCriteria().isEmpty()) {
				ViewUtil.addMessage("No asserts found for entered search criteria..", FacesMessage.SEVERITY_INFO);
			}
		}
		return null;
	}

	public String resetSearchCriteria() {
		this.branchAssertSearchCriteria.resetSeacrhCriteria();
		return null;
	}

	/**
	 * @return the branchAssertsBySearchCriteria
	 */
	public Collection<BranchAssert> getBranchAssertsBySearchCriteria() {
		return this.branchAssertsBySearchCriteria;
	}

	/**
	 * @param branchAssertsBySearchCriteria
	 *            the branchAssertsBySearchCriteria to set
	 */
	public void setBranchAssertsBySearchCriteria(final Collection<BranchAssert> branchAssertsBySearchCriteria) {
		this.branchAssertsBySearchCriteria = branchAssertsBySearchCriteria;
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
