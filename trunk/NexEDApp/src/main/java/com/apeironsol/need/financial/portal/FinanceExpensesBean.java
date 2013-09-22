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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.model.BuildingBlock;
import com.apeironsol.need.core.portal.AbstractTabbedBean;
import com.apeironsol.need.core.service.BuildingBlockService;
import com.apeironsol.need.financial.model.BranchBankAccount;
import com.apeironsol.need.financial.model.BranchCreditAccount;
import com.apeironsol.need.financial.model.BranchExpense;
import com.apeironsol.need.financial.service.BranchBankAccountService;
import com.apeironsol.need.financial.service.BranchCreditAccountService;
import com.apeironsol.need.financial.service.BranchExpenseService;
import com.apeironsol.need.util.DateUtil;
import com.apeironsol.need.util.constants.BuildingBlockConstant;
import com.apeironsol.need.util.portal.ViewUtil;
import com.apeironsol.need.util.searchcriteria.BranchExpenseSearchCriteria;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;

/**
 * JSF managed for financial expense.
 * 
 * @author Pradeep
 */
@Named
@Scope(value = "session")
public class FinanceExpensesBean extends AbstractTabbedBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long				serialVersionUID	= 1964206129200014640L;

	/**
	 * Building block service.
	 */
	@Resource
	private BuildingBlockService			buildingBlockService;

	/**
	 * Branch expense.
	 */
	private BranchExpense					branchExpense;

	/**
	 * Branch expense service.
	 */
	@Resource
	private BranchExpenseService			branchExpenseService;

	/**
	 * Branch expense service.
	 */
	@Resource
	private BranchBankAccountService		branchBankAccountService;

	/**
	 * Building blocks for type expenses.
	 */
	private Collection<BuildingBlock>		expensesTypeBuildingBlocks;

	/**
	 * Building blocks for type expenses.
	 */
	private Collection<BranchBankAccount>	branchBakAccounts;

	/**
	 * Branch expense service.
	 */
	@Resource
	private BranchCreditAccountService		branchCreditAccountService;

	/**
	 * Building blocks for type expenses.
	 */
	private Collection<BranchCreditAccount>	branchCreditAccounts;

	/**
	 * Branch expenses.
	 */
	private Collection<BranchExpense>		branchExpensesBySearchCriteria;

	/**
	 * Indicator to load expense types from database.
	 */
	private boolean							loadExpenseTypesFromDB;

	/**
	 * Indicator to load expense types from database.
	 */
	private Date							reportMinimumDate;

	private Date							reportMaximumDate;

	public enum FinancialExpenseWizard {

		EXPENSE_SEARCH("expenseSearch"),
		NEW_EXPENSE("newExpense"),
		EXPENSE_CUMULATIVE_ANALYSYS("expenseCumulativeAnalysys"),
		EXPENSE_INDIVIDUAL_ANALYSYS("expenseIndividualAnalysys");

		private String	key;

		FinancialExpenseWizard(final String key) {
			this.key = key;
		}

		public String getKey() {
			return this.key;
		}

		public void setKey(final String key) {
			this.key = key;
		}
	};

	private String						financialExpenseWizardActiveStep	= FinancialExpenseWizard.EXPENSE_SEARCH.getKey();
	/**
	 * Branch assert used for searching.
	 */
	private BranchExpenseSearchCriteria	branchExpenseSearchCriteria			= null;

	private CartesianChartModel			financialExpenseAnalysisChart		= new CartesianChartModel();

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

	@Override
	public void onTabChange() {
		this.setViewOrNewAction(false);
		this.financialExpenseWizardActiveStep = FinancialExpenseWizard.EXPENSE_SEARCH.getKey();
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

	/**
	 * @return the branchExpense
	 */
	public BranchExpense getBranchExpense() {
		return this.branchExpense;
	}

	/**
	 * @param branchExpense
	 *            the branchExpense to set
	 */
	public void setBranchExpense(final BranchExpense branchExpense) {
		this.branchExpense = branchExpense;
	}

	/**
	 * Create new academic year Expense.
	 */
	public String newBranchExpense() {
		this.branchExpense = new BranchExpense();
		this.branchExpense.setBranch(this.sessionBean.getCurrentBranch());
		this.branchExpense.setExpenseDate(DateUtil.getSystemDate());
		this.setViewOrNewAction(true);
		this.financialExpenseWizardActiveStep = FinancialExpenseWizard.NEW_EXPENSE.getKey();
		return null;
	}

	/**
	 * Saves academic year Expense to database.
	 */
	public String saveBranchExpense() {
		try {
			this.branchExpense.setBranch(this.sessionBean.getCurrentBranch());
			this.branchExpense = this.branchExpenseService.saveBranchExpense(this.branchExpense);
			this.setViewOrNewAction(false);
			this.financialExpenseWizardActiveStep = FinancialExpenseWizard.EXPENSE_SEARCH.getKey();
			ViewUtil.addMessage("Expense created successfully.", FacesMessage.SEVERITY_INFO);
			if (this.branchExpenseSearchCriteria != null && !this.branchExpenseSearchCriteria.isSearchCriteriaIsEmpty()) {
				this.searchBranchExpensesBySearchCriteria();
			}
		} catch (final Exception ex) {
			ViewUtil.addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
		return null;
	}

	/**
	 * Removes academic year Expense from database.
	 */
	public String removeBranchExpense() {
		try {
			this.branchExpense.setBranch(this.sessionBean.getCurrentBranch());
			this.branchExpenseService.removeBranchExpense(this.branchExpense);
			this.setViewOrNewAction(false);
			this.financialExpenseWizardActiveStep = FinancialExpenseWizard.EXPENSE_SEARCH.getKey();
			ViewUtil.addMessage("Expense removed successfully.", FacesMessage.SEVERITY_INFO);
			if (this.branchExpenseSearchCriteria != null && !this.branchExpenseSearchCriteria.isSearchCriteriaIsEmpty()) {
				this.searchBranchExpensesBySearchCriteria();
			}
		} catch (final Exception ex) {
			ViewUtil.addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
		return null;
	}

	/**
	 * Removes academic year Expense from database.
	 */
	public String calcelAction() {
		this.setViewOrNewAction(false);
		this.financialExpenseWizardActiveStep = FinancialExpenseWizard.EXPENSE_SEARCH.getKey();
		return null;
	}

	/**
	 * Removes academic year Expense from database.
	 */
	public String viewBranchExpense() {
		this.setViewOrNewAction(true);
		this.financialExpenseWizardActiveStep = FinancialExpenseWizard.NEW_EXPENSE.getKey();
		return null;
	}

	/**
	 * Load building blocks for expense type for current branch.
	 */
	public void loadExpensesBuildingBlocks() {
		if (this.loadExpenseTypesFromDB) {
			this.expensesTypeBuildingBlocks = this.buildingBlockService.findBuildingBlocksbyBranchIdAndBuildingBlockType(this.sessionBean.getCurrentBranch()
					.getId(), BuildingBlockConstant.EXPENSE_TYPE);
			this.branchBakAccounts = this.branchBankAccountService.findBranchBankAccountByBranchId(this.sessionBean.getCurrentBranch().getId());
			this.branchCreditAccounts = this.branchCreditAccountService.findBranchCreditAccountByBranchId(this.sessionBean.getCurrentBranch().getId());
			this.loadExpenseTypesFromDB = false;
		}
	}

	/**
	 * Retrieve branch expenses for academic year and current branch.
	 * 
	 * @param academicYear
	 *            academic year.
	 * @return
	 */
	public Collection<BranchExpense> getBranchExpensesForAcademicYearAndCurrentBranch(final AcademicYear academicYear) {
		return this.branchExpenseService.findBranchExpensesByBranchIdAndAcademicYear(this.sessionBean.getCurrentBranch().getId(), academicYear);
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
	public void setBranchExpenseSearchCriteria(final BranchExpenseSearchCriteria branchLiabilitySearchCriteria) {
		this.branchExpenseSearchCriteria = branchLiabilitySearchCriteria;
	}

	public String searchBranchExpensesBySearchCriteria() {
		if (this.branchExpenseSearchCriteria.isSearchCriteriaIsEmpty()) {
			ViewUtil.addMessage("Please enter search criteria.", FacesMessage.SEVERITY_ERROR);
		} else {
			this.branchExpenseSearchCriteria.setBranch(this.sessionBean.getCurrentBranch());
			this.setBranchExpensesBySearchCriteria(this.branchExpenseService.findBranchExpensesBySearchCriteria(this.branchExpenseSearchCriteria));
			if (this.getBranchExpensesBySearchCriteria() == null || this.getBranchExpensesBySearchCriteria().isEmpty()) {
				ViewUtil.addMessage("No expenses found for entered search criteria..", FacesMessage.SEVERITY_INFO);
			}
		}
		return null;
	}

	public String resetSearchCriteria() {
		this.branchExpenseSearchCriteria.resetSeacrhCriteria();
		return null;
	}

	public String expenseAnalysis() {
		this.financialExpenseWizardActiveStep = FinancialExpenseWizard.EXPENSE_CUMULATIVE_ANALYSYS.getKey();
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

	/**
	 * @return the financialExpenseWizardActiveStep
	 */
	public String getFinancialExpenseWizardActiveStep() {
		return this.financialExpenseWizardActiveStep;
	}

	/**
	 * @param financialExpenseWizardActiveStep
	 *            the financialExpenseWizardActiveStep to set
	 */
	public void setFinancialExpenseWizardActiveStep(final String financialExpenseWizardActiveStep) {
		this.financialExpenseWizardActiveStep = financialExpenseWizardActiveStep;
	}

	/**
	 * @return the financialExpenseAnalysisChart
	 */
	public CartesianChartModel getFinancialExpenseAnalysisChart() {
		return this.financialExpenseAnalysisChart;
	}

	/**
	 * @param financialExpenseAnalysisChart
	 *            the financialExpenseAnalysisChart to set
	 */
	public void setFinancialExpenseAnalysisChart(final CartesianChartModel financialExpenseAnalysisChart) {
		this.financialExpenseAnalysisChart = financialExpenseAnalysisChart;
	}

	/**
	 * Subject exam results.
	 */
	public void analyseFinancialExpensesLineChartReportIndividual() {
		if (this.financialExpenseWizardActiveStep.equals(FinancialExpenseWizard.EXPENSE_INDIVIDUAL_ANALYSYS.getKey())) {
			this.financialExpenseAnalysisChart = new CartesianChartModel();
			if (this.branchExpensesBySearchCriteria != null) {
				final Map<String, Map<Date, Double>> expensesMapByType = this.getExpensesMapByType(this.branchExpensesBySearchCriteria);
				ChartSeries percentage = null;
				for (final Map.Entry<String, Map<Date, Double>> entry : expensesMapByType.entrySet()) {
					percentage = new ChartSeries();
					percentage.setLabel(entry.getKey());
					final Map<Date, Double> expensesByDate = entry.getValue();
					// Collections.sort(chartDate, comparator);
					final SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
					final Collection<Date> expensesDate = expensesByDate.keySet();
					final List<Date> dates = new ArrayList<Date>();
					for (final Date date : expensesDate) {
						dates.add(date);
					}
					Collections.sort(dates);

					for (final Date entry1 : dates) {
						percentage.set(format.format(entry1), expensesByDate.get(entry1));
					}
					this.financialExpenseAnalysisChart.addSeries(percentage);
				}
			}
		}
	}

	private Map<String, Map<Date, Double>> getExpensesMapByType(final Collection<BranchExpense> branchExpenses) {
		this.reportMinimumDate = null;
		this.reportMaximumDate = null;
		final Map<String, Map<Date, Double>> expensesMapByType = new HashMap<String, Map<Date, Double>>();
		for (final BranchExpense branchExpense : branchExpenses) {
			if (this.reportMinimumDate == null) {
				this.reportMinimumDate = branchExpense.getExpenseDate();
			} else if (this.reportMinimumDate.after(branchExpense.getExpenseDate())) {
				this.reportMinimumDate = branchExpense.getExpenseDate();
			}

			if (this.reportMaximumDate == null) {
				this.reportMaximumDate = branchExpense.getExpenseDate();
			} else if (this.reportMaximumDate.before(branchExpense.getExpenseDate())) {
				this.reportMaximumDate = branchExpense.getExpenseDate();
			}
		}

		final int diffDates = DateUtil.dateDiffInDays(this.reportMinimumDate, this.reportMaximumDate);
		int diffCounter = 1;
		if (diffDates > 10) {
			diffCounter = diffDates / 10;
		}
		final Calendar calDate = Calendar.getInstance();
		for (final BranchExpense branchExpense : branchExpenses) {
			int expenseDiff = DateUtil.dateDiffInDays(this.reportMinimumDate, branchExpense.getExpenseDate());
			expenseDiff = expenseDiff / diffCounter;
			calDate.setTime(this.reportMinimumDate);
			calDate.add(Calendar.DATE, expenseDiff * diffCounter);
			if (expensesMapByType.get(branchExpense.getExpenseBuildingBlock().getName()) == null) {
				final Map<Date, Double> branchExpensesByDate = new HashMap<Date, Double>();
				final List<BranchExpense> branchExpensesList = new ArrayList<BranchExpense>();
				branchExpensesList.add(branchExpense);
				branchExpensesByDate.put(calDate.getTime(), branchExpense.getAmount());
				expensesMapByType.put(branchExpense.getExpenseBuildingBlock().getName(), branchExpensesByDate);
			} else {
				if (expensesMapByType.get(branchExpense.getExpenseBuildingBlock().getName()).get(calDate.getTime()) != null) {
					expensesMapByType.get(branchExpense.getExpenseBuildingBlock().getName()).put(branchExpense.getExpenseDate(),
							branchExpense.getAmount() + expensesMapByType.get(branchExpense.getExpenseBuildingBlock().getName()).get(calDate.getTime()));
				} else {
					expensesMapByType.get(branchExpense.getExpenseBuildingBlock().getName()).put(calDate.getTime(), branchExpense.getAmount());
				}

			}
		}
		return expensesMapByType;
	}

	/**
	 * Subject exam results.
	 */
	public void analyseFinancialExpensesLineChartReportCumulative() {
		if (this.financialExpenseWizardActiveStep.equals(FinancialExpenseWizard.EXPENSE_CUMULATIVE_ANALYSYS.getKey())) {
			this.financialExpenseAnalysisChart = new CartesianChartModel();
			if (this.branchExpensesBySearchCriteria != null) {
				final Map<Date, Double> expensesMapByType = this.getExpensesMapByDate(this.branchExpensesBySearchCriteria);
				final ChartSeries percentage = new ChartSeries();
				percentage.setLabel("Expenses");
				// Collections.sort(chartDate, comparator);
				final SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
				final Collection<Date> expensesDate = expensesMapByType.keySet();
				final List<Date> dates = new ArrayList<Date>();
				for (final Date date : expensesDate) {
					dates.add(date);
				}
				Collections.sort(dates);
				for (final Date entry1 : dates) {
					percentage.set(format.format(entry1), expensesMapByType.get(entry1));
				}
				this.financialExpenseAnalysisChart.addSeries(percentage);
			}
		}
	}

	private Map<Date, Double> getExpensesMapByDate(final Collection<BranchExpense> branchExpenses) {
		this.reportMinimumDate = null;
		this.reportMaximumDate = null;
		final Map<Date, Double> expensesMapByType = new HashMap<Date, Double>();
		for (final BranchExpense branchExpense : branchExpenses) {
			if (this.reportMinimumDate == null) {
				this.reportMinimumDate = branchExpense.getExpenseDate();
			} else if (this.reportMinimumDate.after(branchExpense.getExpenseDate())) {
				this.reportMinimumDate = branchExpense.getExpenseDate();
			}

			if (this.reportMaximumDate == null) {
				this.reportMaximumDate = branchExpense.getExpenseDate();
			} else if (this.reportMaximumDate.before(branchExpense.getExpenseDate())) {
				this.reportMaximumDate = branchExpense.getExpenseDate();
			}
		}

		final int diffDates = DateUtil.dateDiffInDays(this.reportMinimumDate, this.reportMaximumDate);
		int diffCounter = 1;
		if (diffDates > 10) {
			diffCounter = diffDates / 10;
		}
		final Calendar calDate = Calendar.getInstance();
		for (final BranchExpense branchExpense : branchExpenses) {
			int expenseDiff = DateUtil.dateDiffInDays(this.reportMinimumDate, branchExpense.getExpenseDate());
			expenseDiff = expenseDiff / diffCounter;
			calDate.setTime(this.reportMinimumDate);
			calDate.add(Calendar.DATE, expenseDiff * diffCounter);
			if (expensesMapByType.get(calDate.getTime()) == null) {
				expensesMapByType.put(calDate.getTime(), branchExpense.getAmount());
			} else {
				expensesMapByType.put(calDate.getTime(), branchExpense.getAmount() + expensesMapByType.get(calDate.getTime()));
			}
		}
		return expensesMapByType;
	}

	/**
	 * @return the branchBakAccounts
	 */
	public Collection<BranchBankAccount> getBranchBakAccounts() {
		return this.branchBakAccounts;
	}

	/**
	 * @param branchBakAccounts
	 *            the branchBakAccounts to set
	 */
	public void setBranchBakAccounts(final Collection<BranchBankAccount> branchBakAccounts) {
		this.branchBakAccounts = branchBakAccounts;
	}

	public void handlePaymentModeChange() {
		this.branchExpense.setChequeNumber(null);
		this.branchExpense.setBranchBankAccount(null);
		this.branchExpense.setBranchAccountType(null);
	}

	public void handleBranchAccountTypeChange() {
		this.branchExpense.setChequeNumber(null);
		this.branchExpense.setBranchBankAccount(null);
		this.branchExpense.setBranchCreditAccount(null);
	}

	/**
	 * Disable save button if expense created date is not same as current date.
	 * 
	 * @return
	 */
	public boolean isSaveButtonDisabled() {
		boolean result = true;
		if (this.grantedAuthorityBean.isUserAllowedToCreateExpenses()) {
			/*
			 * if (this.branchExpense != null && this.branchExpense.getId() !=
			 * null) {
			 * final Calendar expenseDateCal = Calendar.getInstance();
			 * expenseDateCal.setTime(this.branchExpense.getExpenseDate());
			 * DateUtil.clearTimeInfo(expenseDateCal);
			 * 
			 * final Calendar currDateCal = Calendar.getInstance();
			 * currDateCal.setTime(DateUtil.getSystemDate());
			 * DateUtil.clearTimeInfo(currDateCal);
			 * if (expenseDateCal.equals(currDateCal)) {
			 * result = false;
			 * }
			 * } else if (this.branchExpense != null &&
			 * this.branchExpense.getId() == null) {
			 * result = false;
			 * }
			 */
			result = false;
		}
		return result;
	}

	/**
	 * Delete button is visible only if expense is created on current date.
	 * 
	 * @return
	 */
	public boolean isDeleteButtonVisible(final BranchExpense expense) {
		boolean result = false;
		if (this.grantedAuthorityBean.isUserAllowedToRemoveExpenses() && expense != null && expense.getId() != null) {
			result = true;
			/*
			 * final Calendar expenseDateCal = Calendar.getInstance();
			 * expenseDateCal.setTime(expense.getExpenseDate());
			 * DateUtil.clearTimeInfo(expenseDateCal);
			 * 
			 * final Calendar currDateCal = Calendar.getInstance();
			 * currDateCal.setTime(DateUtil.getSystemDate());
			 * DateUtil.clearTimeInfo(currDateCal);
			 * if (expenseDateCal.equals(currDateCal)) {
			 * 
			 * }
			 */
		}
		return result;
	}

	public Collection<BranchCreditAccount> getBranchCreditAccounts() {
		return this.branchCreditAccounts;
	}

	public void setBranchCreditAccounts(final Collection<BranchCreditAccount> branchCreditAccounts) {
		this.branchCreditAccounts = branchCreditAccounts;
	}
}
