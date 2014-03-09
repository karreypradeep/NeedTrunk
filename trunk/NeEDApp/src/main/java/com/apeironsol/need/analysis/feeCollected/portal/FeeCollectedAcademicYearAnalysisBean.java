/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 * 
 */
package com.apeironsol.need.analysis.feeCollected.portal;

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

import com.apeironsol.need.analysis.feeCollected.dataobject.FeeCollectedAcademicYearDO;
import com.apeironsol.need.analysis.feeCollected.service.FeeCollectedAnalysisService;
import com.apeironsol.need.core.model.Klass;
import com.apeironsol.need.core.portal.AbstractTabbedBean;
import com.apeironsol.need.util.constants.FeeCollectedAnalysisTypeConstant;
import com.apeironsol.need.util.constants.GenderConstant;
import com.apeironsol.need.util.portal.ViewUtil;
import com.apeironsol.need.util.searchcriteria.FeeCollectedAnalysisSearchCriteria;

/**
 * JSF managed for financial expense.
 * 
 * @author Pradeep
 */
@Named
@Scope(value = "session")
public class FeeCollectedAcademicYearAnalysisBean extends AbstractTabbedBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long					serialVersionUID							= -5693793986889845131L;

	/**
	 * Branch assert used for searching.
	 */
	private FeeCollectedAnalysisSearchCriteria	feeCollectedAnalysisSearchCriteria			= null;

	@Resource
	private FeeCollectedAnalysisService			feeCollectedAnalysisService;

	private FeeCollectedAcademicYearDO			feeCollectedAcademicYearDO;

	private CartesianChartModel					feeCollectedAcademicYearAnalysisChart		= new CartesianChartModel();

	private PieChartModel						feeCollectedAcademicYearAnalysisPieChart	= new PieChartModel();

	private boolean								displayCharts;

	private String								xAxisLabel									= null;

	@PostConstruct
	public void init() {
		this.initializeSearchCriteria();
	}

	public void initializeSearchCriteria() {
		if (this.feeCollectedAnalysisSearchCriteria == null) {
			this.feeCollectedAnalysisSearchCriteria = new FeeCollectedAnalysisSearchCriteria(this.sessionBean.getCurrentBranch());
		}
	}

	@Override
	public void onTabChange() {

	}

	/**
	 * @return the feeCollectedAnalysisSearchCriteria
	 */
	public FeeCollectedAnalysisSearchCriteria getFeeCollectedAnalysisSearchCriteria() {
		return this.feeCollectedAnalysisSearchCriteria;
	}

	public String resetSearchCriteria() {
		this.feeCollectedAnalysisSearchCriteria.resetSeacrhCriteria();
		return null;
	}

	public String generateReport() {

		if (this.feeCollectedAnalysisSearchCriteria.isSearchCriteriaIsEmpty()) {
			ViewUtil.addMessage("Please enter search criteria.", FacesMessage.SEVERITY_ERROR);
		} else {
			this.feeCollectedAnalysisSearchCriteria.setBranch(this.sessionBean.getCurrentBranch());
			this.feeCollectedAcademicYearDO = this.feeCollectedAnalysisService
					.getFeeCollectedGeneratedForAcademicYearBySearchCriteria(this.feeCollectedAnalysisSearchCriteria);
			this.generateFeeCollectedReport();
		}
		return null;
	}

	/**
	 * @return the feeCollectedAcademicYearDO
	 */
	public FeeCollectedAcademicYearDO getFeeCollectedAcademicYearDO() {
		return this.feeCollectedAcademicYearDO;
	}

	/**
	 * @param feeCollectedAcademicYearDO
	 *            the feeCollectedAcademicYearDO to set
	 */
	public void setFeeCollectedAcademicYearDO(final FeeCollectedAcademicYearDO feeCollectedAcademicYearDO) {
		this.feeCollectedAcademicYearDO = feeCollectedAcademicYearDO;
	}

	/**
	 * Subject exam results.
	 */
	public void generateFeeCollectedReport() {
		this.feeCollectedAcademicYearAnalysisChart = new CartesianChartModel();
		this.feeCollectedAcademicYearAnalysisPieChart = new PieChartModel();
		if (this.feeCollectedAcademicYearDO != null) {
			if (FeeCollectedAnalysisTypeConstant.BY_COURSE.equals(this.feeCollectedAnalysisSearchCriteria.getFeeCollectedAnalysisType())) {
				this.generateChartByCourse();
				this.setxAxisLabel("Course");
			} else if (FeeCollectedAnalysisTypeConstant.BY_GENDER.equals(this.feeCollectedAnalysisSearchCriteria.getFeeCollectedAnalysisType())) {
				this.generateChartByGender();
				this.xAxisLabel = "Gender";
			} else if (FeeCollectedAnalysisTypeConstant.BY_LOCATION.equals(this.feeCollectedAnalysisSearchCriteria.getFeeCollectedAnalysisType())) {
				this.generateChartByLocation();
				this.xAxisLabel = "Pincode";
			}
			this.displayCharts = true;
		}
	}

	private void generateChartByCourse() {
		final Map<Klass, Double> expensesMapByType = this.feeCollectedAcademicYearDO.getFeeCollectedByCourse();
		final ChartSeries percentage = new ChartSeries();
		percentage.setLabel("FeeCollected");
		for (final Map.Entry<Klass, Double> entry : expensesMapByType.entrySet()) {
			percentage.set(entry.getKey().getName(), entry.getValue());
			this.feeCollectedAcademicYearAnalysisPieChart.set(entry.getKey().getName(), entry.getValue());
		}
		this.feeCollectedAcademicYearAnalysisChart.addSeries(percentage);
	}

	private void generateChartByGender() {
		final Map<GenderConstant, Double> expensesMapByType = this.feeCollectedAcademicYearDO.getFeeCollectedByGender();
		final ChartSeries percentage = new ChartSeries();
		percentage.setLabel("FeeCollected");
		for (final Map.Entry<GenderConstant, Double> entry : expensesMapByType.entrySet()) {
			percentage.set(entry.getKey().getLabel(), entry.getValue());
			this.feeCollectedAcademicYearAnalysisPieChart.set(entry.getKey().getLabel(), entry.getValue());
		}
		this.feeCollectedAcademicYearAnalysisChart.addSeries(percentage);
	}

	private void generateChartByLocation() {
		final Map<String, Double> expensesMapByType = this.feeCollectedAcademicYearDO.getFeeCollectedByLocation();
		final ChartSeries percentage = new ChartSeries();
		percentage.setLabel("FeeCollected");
		for (final Map.Entry<String, Double> entry : expensesMapByType.entrySet()) {
			percentage.set(entry.getKey(), entry.getValue());
			this.feeCollectedAcademicYearAnalysisPieChart.set(entry.getKey(), entry.getValue());
		}
		this.feeCollectedAcademicYearAnalysisChart.addSeries(percentage);
	}

	/**
	 * @return the feeCollectedAcademicYearAnalysisChart
	 */
	public CartesianChartModel getFeeCollectedAcademicYearAnalysisChart() {
		return this.feeCollectedAcademicYearAnalysisChart;
	}

	/**
	 * @param feeCollectedAcademicYearAnalysisChart
	 *            the feeCollectedAcademicYearAnalysisChart to set
	 */
	public void setFeeCollectedAcademicYearAnalysisChart(final CartesianChartModel feeCollectedAcademicYearAnalysisChart) {
		this.feeCollectedAcademicYearAnalysisChart = feeCollectedAcademicYearAnalysisChart;
	}

	/**
	 * @return the feeCollectedAcademicYearAnalysisPieChart
	 */
	public PieChartModel getFeeCollectedAcademicYearAnalysisPieChart() {
		return this.feeCollectedAcademicYearAnalysisPieChart;
	}

	/**
	 * @param feeCollectedAcademicYearAnalysisPieChart
	 *            the feeCollectedAcademicYearAnalysisPieChart to set
	 */
	public void setFeeCollectedAcademicYearAnalysisPieChart(final PieChartModel feeCollectedAcademicYearAnalysisPieChart) {
		this.feeCollectedAcademicYearAnalysisPieChart = feeCollectedAcademicYearAnalysisPieChart;
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
