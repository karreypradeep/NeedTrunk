/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.reporting.portal;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Named;

import org.primefaces.model.StreamedContent;
import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.BuildingBlock;
import com.apeironsol.need.core.portal.AbstractPortalBean;
import com.apeironsol.need.core.service.BuildingBlockService;
import com.apeironsol.need.financial.model.BranchExpense;
import com.apeironsol.need.financial.service.BranchExpenseService;
import com.apeironsol.need.reporting.dataobject.ReportDO;
import com.apeironsol.need.reporting.ro.FinancialRO;
import com.apeironsol.need.reporting.service.FinancialReportService;
import com.apeironsol.need.reporting.util.JReportType;
import com.apeironsol.need.reporting.util.JasperUtil;
import com.apeironsol.need.util.constants.BuildingBlockConstant;
import com.apeironsol.need.util.constants.FinancialReportNamesConstant;
import com.apeironsol.need.util.searchcriteria.BranchExpenseSearchCriteria;

/**
 * @author sunny
 * 
 */
@Named
@Scope("session")
public class FinancialReportBean extends AbstractPortalBean implements Serializable {

	private static final long			serialVersionUID	= 4679700550137434935L;

	private BranchExpenseSearchCriteria	branchExpenseSearchCriteria;

	/**
	 * Building blocks for type expenses.
	 */
	private Collection<BuildingBlock>	expensesTypeBuildingBlocks;

	/**
	 * Indicator to load expense types from database.
	 */
	private boolean						loadExpenseTypesFromDB;

	/**
	 * Building block service.
	 */
	@Resource
	private BuildingBlockService		buildingBlockService;

	/**
	 * Branch expenses.
	 */
	private Collection<BranchExpense>	branchExpensesBySearchCriteria;

	/**
	 * Branch expense service.
	 */
	@Resource
	private BranchExpenseService		branchExpenseService;

	/**
	 * Financial report service.
	 */
	@Resource
	private FinancialReportService		financialReportService;

	private FinancialReportNamesConstant selectedReport;

	/**
	 * @return the loadExpenseTypesFromDB
	 */
	public boolean isLoadExpenseTypesFromDB() {
		return this.loadExpenseTypesFromDB;
	}

	/**
	 * @param loadExpenseTypesFromDB
	 *            the loadExpenseTypesFromDB to set
	 */
	public void setLoadExpenseTypesFromDB(final boolean loadExpenseTypesFromDB) {
		this.loadExpenseTypesFromDB = loadExpenseTypesFromDB;
	}

	/**
	 * @return the expensesTypeBuildingBlocks
	 */
	public Collection<BuildingBlock> getExpensesTypeBuildingBlocks() {
		return this.expensesTypeBuildingBlocks;
	}

	/**
	 * @param expensesTypeBuildingBlocks
	 *            the expensesTypeBuildingBlocks to set
	 */
	public void setExpensesTypeBuildingBlocks(final Collection<BuildingBlock> expensesTypeBuildingBlocks) {
		this.expensesTypeBuildingBlocks = expensesTypeBuildingBlocks;
	}

	/**
	 * @return the branchExpenseSearchCriteria
	 */
	public BranchExpenseSearchCriteria getBranchExpenseSearchCriteria() {
		return this.branchExpenseSearchCriteria;
	}

	/**
	 * @param branchExpenseSearchCriteria
	 *            the branchExpenseSearchCriteria to set
	 */
	public void setBranchExpenseSearchCriteria(final BranchExpenseSearchCriteria branchExpenseSearchCriteria) {
		this.branchExpenseSearchCriteria = branchExpenseSearchCriteria;
	}

	/**
	 * @return the branchExpensesBySearchCriteria
	 */
	public Collection<BranchExpense> getBranchExpensesBySearchCriteria() {
		return this.branchExpensesBySearchCriteria;
	}

	/**
	 * @param branchExpensesBySearchCriteria
	 *            the branchExpensesBySearchCriteria to set
	 */
	public void setBranchExpensesBySearchCriteria(final Collection<BranchExpense> branchExpensesBySearchCriteria) {
		this.branchExpensesBySearchCriteria = branchExpensesBySearchCriteria;
	}

	@PostConstruct
	public void init() {
		this.initializeSearchCriteria();
	}

	public void initializeSearchCriteria() {
		if (this.branchExpenseSearchCriteria == null) {
			this.branchExpenseSearchCriteria = new BranchExpenseSearchCriteria(this.sessionBean.getCurrentBranch());
		}
	}

	/**
	 * Load building blocks for expense type for current branch.
	 */
	public void loadExpensesBuildingBlocks() {
		if (this.loadExpenseTypesFromDB) {
			this.expensesTypeBuildingBlocks = this.buildingBlockService.findBuildingBlocksbyBranchIdAndBuildingBlockType(this.sessionBean.getCurrentBranch()
					.getId(), BuildingBlockConstant.EXPENSE_TYPE);
			this.loadExpenseTypesFromDB = false;
		}
	}

	public StreamedContent generateExpensesReport() {
		this.selectedReport = FinancialReportNamesConstant.BRANCH_EXPENSES;
		final FinancialRO financialRO = this.financialReportService.getExpensesReportForBranch(this.sessionBean.getCurrentBranch(),
				this.branchExpenseSearchCriteria);

		final ReportDO reportDO = new ReportDO();
		reportDO.setInputJasperFileName(JasperUtil
				.getFullFilePath("/reports/" + this.selectedReport.getJasperReportFileName().toString()));
		reportDO.setReportType(JReportType.PDF_REPORT);
		reportDO.setOutputReportName(this.selectedReport.getLabel());

		final HashMap<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("org_name", this.sessionBean.getCurrentOrganization().getName());
		paramsMap.put("branch_name", this.sessionBean.getCurrentBranch().getName());
		paramsMap.put("report_title", this.selectedReport.getLabel());
		paramsMap.put("subreport_name", this.selectedReport.getJasperSubReportFileName().toString());
		paramsMap.put("financialRO", financialRO);

		StreamedContent streamedContent = null;
		streamedContent = JasperUtil.printReport(reportDO, paramsMap);

		return streamedContent;
	}

	public String resetSearchCriteria() {
		this.branchExpenseSearchCriteria.resetSeacrhCriteria();
		return null;
	}

}
