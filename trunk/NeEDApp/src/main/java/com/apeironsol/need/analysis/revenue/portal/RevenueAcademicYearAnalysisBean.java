/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 * 
 */
package com.apeironsol.need.analysis.revenue.portal;

import java.io.Serializable;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;
import org.springframework.context.annotation.Scope;

import com.apeironsol.need.analysis.revenue.dataobject.RevenueAcademicYearDO;
import com.apeironsol.need.analysis.revenue.service.RevenueAnalysisService;
import com.apeironsol.need.core.model.Klass;
import com.apeironsol.need.core.portal.AbstractTabbedBean;
import com.apeironsol.need.util.constants.GenderConstant;
import com.apeironsol.need.util.constants.FeeCollectedAnalysisTypeConstant;
import com.apeironsol.need.util.portal.ViewUtil;
import com.apeironsol.need.util.searchcriteria.RevenueAnalysisSearchCriteria;

/**
 * JSF managed for financial expense.
 * 
 * @author Pradeep
 */
@Named
@Scope(value = "session")
public class RevenueAcademicYearAnalysisBean extends AbstractTabbedBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long				serialVersionUID					= -5693793986889845131L;

	/**
	 * Branch assert used for searching.
	 */
	private RevenueAnalysisSearchCriteria	revenueAnalysisSearchCriteria		= null;

	@Resource
	private RevenueAnalysisService			revenueAnalysisService;

	private RevenueAcademicYearDO			revenueAcademicYearDO;

	private CartesianChartModel				revenueAcademicYearAnalysisChart	= new CartesianChartModel();

	private PieChartModel					revenueAcademicYearAnalysisPieChart	= new PieChartModel();

	private boolean							displayCharts;

	private String							xAxisLabel							= null;

	@PostConstruct
	public void init() {
		this.initializeSearchCriteria();
	}

	public void initializeSearchCriteria() {
		if (this.revenueAnalysisSearchCriteria == null) {
			this.revenueAnalysisSearchCriteria = new RevenueAnalysisSearchCriteria(this.sessionBean.getCurrentBranch());
		}
	}

	@Override
	public void onTabChange() {

	}

	/**
	 * @return the revenueAnalysisSearchCriteria
	 */
	public RevenueAnalysisSearchCriteria getRevenueAnalysisSearchCriteria() {
		return this.revenueAnalysisSearchCriteria;
	}

	public String resetSearchCriteria() {
		this.revenueAnalysisSearchCriteria.resetSeacrhCriteria();
		return null;
	}

	public String generateReport() {

		if (this.revenueAnalysisSearchCriteria.isSearchCriteriaIsEmpty()) {
			ViewUtil.addMessage("Please enter search criteria.", FacesMessage.SEVERITY_ERROR);
		} else {
			this.revenueAnalysisSearchCriteria.setBranch(this.sessionBean.getCurrentBranch());
			this.revenueAcademicYearDO = this.revenueAnalysisService.getRevenueGeneratedForAcademicYearBySearchCriteria(this.revenueAnalysisSearchCriteria);
			this.generateRevenueReport();
		}
		return null;
	}

	/**
	 * @return the revenueAcademicYearDO
	 */
	public RevenueAcademicYearDO getRevenueAcademicYearDO() {
		return this.revenueAcademicYearDO;
	}

	/**
	 * @param revenueAcademicYearDO
	 *            the revenueAcademicYearDO to set
	 */
	public void setRevenueAcademicYearDO(final RevenueAcademicYearDO revenueAcademicYearDO) {
		this.revenueAcademicYearDO = revenueAcademicYearDO;
	}

	/**
	 * Subject exam results.
	 */
	public void generateRevenueReport() {
		this.revenueAcademicYearAnalysisChart = new CartesianChartModel();
		this.revenueAcademicYearAnalysisPieChart = new PieChartModel();
		if (this.revenueAcademicYearDO != null) {
			if (FeeCollectedAnalysisTypeConstant.BY_COURSE.equals(this.revenueAnalysisSearchCriteria.getRevenueAnalysisType())) {
				this.generateChartByCourse();
				this.setxAxisLabel("Course");
			} else if (FeeCollectedAnalysisTypeConstant.BY_GENDER.equals(this.revenueAnalysisSearchCriteria.getRevenueAnalysisType())) {
				this.generateChartByGender();
				this.xAxisLabel = "Gender";
			} else if (FeeCollectedAnalysisTypeConstant.BY_LOCATION.equals(this.revenueAnalysisSearchCriteria.getRevenueAnalysisType())) {
				this.generateChartByLocation();
				this.xAxisLabel = "Pincode";
			}
			this.displayCharts = true;
		}
	}

	private void generateChartByCourse() {
		final Map<Klass, Double> expensesMapByType = this.revenueAcademicYearDO.getRevenueByCourse();
		final ChartSeries percentage = new ChartSeries();
		percentage.setLabel("Revenue");
		for (final Map.Entry<Klass, Double> entry : expensesMapByType.entrySet()) {
			percentage.set(entry.getKey().getName(), entry.getValue());
			this.revenueAcademicYearAnalysisPieChart.set(entry.getKey().getName(), entry.getValue());
		}
		this.revenueAcademicYearAnalysisChart.addSeries(percentage);
	}

	private void generateChartByGender() {
		final Map<GenderConstant, Double> expensesMapByType = this.revenueAcademicYearDO.getRevenueByGender();
		final ChartSeries percentage = new ChartSeries();
		percentage.setLabel("Revenue");
		for (final Map.Entry<GenderConstant, Double> entry : expensesMapByType.entrySet()) {
			percentage.set(entry.getKey().getLabel(), entry.getValue());
			this.revenueAcademicYearAnalysisPieChart.set(entry.getKey().getLabel(), entry.getValue());
		}
		this.revenueAcademicYearAnalysisChart.addSeries(percentage);
	}

	private void generateChartByLocation() {
		final Map<String, Double> expensesMapByType = this.revenueAcademicYearDO.getRevenueByLocation();
		final ChartSeries percentage = new ChartSeries();
		percentage.setLabel("Revenue");
		for (final Map.Entry<String, Double> entry : expensesMapByType.entrySet()) {
			percentage.set(entry.getKey(), entry.getValue());
			this.revenueAcademicYearAnalysisPieChart.set(entry.getKey(), entry.getValue());
		}
		this.revenueAcademicYearAnalysisChart.addSeries(percentage);
	}

	/**
	 * @return the revenueAcademicYearAnalysisChart
	 */
	public CartesianChartModel getRevenueAcademicYearAnalysisChart() {
		return this.revenueAcademicYearAnalysisChart;
	}

	/**
	 * @param revenueAcademicYearAnalysisChart
	 *            the revenueAcademicYearAnalysisChart to set
	 */
	public void setRevenueAcademicYearAnalysisChart(final CartesianChartModel revenueAcademicYearAnalysisChart) {
		this.revenueAcademicYearAnalysisChart = revenueAcademicYearAnalysisChart;
	}

	/**
	 * @return the revenueAcademicYearAnalysisPieChart
	 */
	public PieChartModel getRevenueAcademicYearAnalysisPieChart() {
		return this.revenueAcademicYearAnalysisPieChart;
	}

	/**
	 * @param revenueAcademicYearAnalysisPieChart
	 *            the revenueAcademicYearAnalysisPieChart to set
	 */
	public void setRevenueAcademicYearAnalysisPieChart(final PieChartModel revenueAcademicYearAnalysisPieChart) {
		this.revenueAcademicYearAnalysisPieChart = revenueAcademicYearAnalysisPieChart;
	}

	/**
	 * @return the displayCharts
	 */
	public boolean isDisplayCharts() {
		return this.displayCharts;
	}

	/**
	 * @param displayCharts
	 *            the displayCharts to set
	 */
	public void setDisplayCharts(final boolean displayCharts) {
		this.displayCharts = displayCharts;
	}

	/**
	 * @return the xAxisLabel
	 */
	public String getxAxisLabel() {
		return this.xAxisLabel;
	}

	/**
	 * @param xAxisLabel
	 *            the xAxisLabel to set
	 */
	public void setxAxisLabel(final String xAxisLabel) {
		this.xAxisLabel = xAxisLabel;
	}

}
