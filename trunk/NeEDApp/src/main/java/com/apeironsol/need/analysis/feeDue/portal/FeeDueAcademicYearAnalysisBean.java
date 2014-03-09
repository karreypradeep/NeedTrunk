/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 * 
 */
package com.apeironsol.need.analysis.feeDue.portal;

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

import com.apeironsol.need.analysis.feeDue.dataobject.FeeDueAcademicYearDO;
import com.apeironsol.need.analysis.feeDue.service.FeeDueAnalysisService;
import com.apeironsol.need.core.model.Klass;
import com.apeironsol.need.core.portal.AbstractTabbedBean;
import com.apeironsol.need.util.constants.FeeDueAnalysisTypeConstant;
import com.apeironsol.need.util.constants.GenderConstant;
import com.apeironsol.need.util.portal.ViewUtil;
import com.apeironsol.need.util.searchcriteria.FeeDueAnalysisSearchCriteria;

/**
 * JSF managed for financial expense.
 * 
 * @author Pradeep
 */
@Named
@Scope(value = "session")
public class FeeDueAcademicYearAnalysisBean extends AbstractTabbedBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long				serialVersionUID					= -5693793986889845131L;

	/**
	 * Branch assert used for searching.
	 */
	private FeeDueAnalysisSearchCriteria	feeDueAnalysisSearchCriteria		= null;

	@Resource
	private FeeDueAnalysisService			feeDueAnalysisService;

	private FeeDueAcademicYearDO			feeDueAcademicYearDO;

	private CartesianChartModel				feeDueAcademicYearAnalysisChart		= new CartesianChartModel();

	private PieChartModel					feeDueAcademicYearAnalysisPieChart	= new PieChartModel();

	private boolean							displayCharts;

	private String							xAxisLabel							= null;

	@PostConstruct
	public void init() {
		this.initializeSearchCriteria();
	}

	public void initializeSearchCriteria() {
		if (this.feeDueAnalysisSearchCriteria == null) {
			this.feeDueAnalysisSearchCriteria = new FeeDueAnalysisSearchCriteria(this.sessionBean.getCurrentBranch());
		}
	}

	@Override
	public void onTabChange() {

	}

	/**
	 * @return the feeDueAnalysisSearchCriteria
	 */
	public FeeDueAnalysisSearchCriteria getFeeDueAnalysisSearchCriteria() {
		return this.feeDueAnalysisSearchCriteria;
	}

	public String resetSearchCriteria() {
		this.feeDueAnalysisSearchCriteria.resetSeacrhCriteria();
		return null;
	}

	public String generateReport() {

		if (this.feeDueAnalysisSearchCriteria.isSearchCriteriaIsEmpty()) {
			ViewUtil.addMessage("Please enter search criteria.", FacesMessage.SEVERITY_ERROR);
		} else {
			this.feeDueAnalysisSearchCriteria.setBranch(this.sessionBean.getCurrentBranch());
			this.feeDueAcademicYearDO = this.feeDueAnalysisService.getFeeDueGeneratedForAcademicYearBySearchCriteria(this.feeDueAnalysisSearchCriteria);
			this.generateFeeDueReport();
		}
		return null;
	}

	/**
	 * @return the feeDueAcademicYearDO
	 */
	public FeeDueAcademicYearDO getFeeDueAcademicYearDO() {
		return this.feeDueAcademicYearDO;
	}

	/**
	 * @param feeDueAcademicYearDO
	 *            the feeDueAcademicYearDO to set
	 */
	public void setFeeDueAcademicYearDO(final FeeDueAcademicYearDO feeDueAcademicYearDO) {
		this.feeDueAcademicYearDO = feeDueAcademicYearDO;
	}

	/**
	 * Subject exam results.
	 */
	public void generateFeeDueReport() {
		this.feeDueAcademicYearAnalysisChart = new CartesianChartModel();
		this.feeDueAcademicYearAnalysisPieChart = new PieChartModel();
		if (this.feeDueAcademicYearDO != null) {
			if (FeeDueAnalysisTypeConstant.BY_COURSE.equals(this.feeDueAnalysisSearchCriteria.getFeeDueAnalysisType())) {
				this.generateChartByCourse();
				this.setxAxisLabel("Course");
			} else if (FeeDueAnalysisTypeConstant.BY_GENDER.equals(this.feeDueAnalysisSearchCriteria.getFeeDueAnalysisType())) {
				this.generateChartByGender();
				this.xAxisLabel = "Gender";
			} else if (FeeDueAnalysisTypeConstant.BY_LOCATION.equals(this.feeDueAnalysisSearchCriteria.getFeeDueAnalysisType())) {
				this.generateChartByLocation();
				this.xAxisLabel = "Pincode";
			}
			this.displayCharts = true;
		}
	}

	private void generateChartByCourse() {
		final Map<Klass, Double> expensesMapByType = this.feeDueAcademicYearDO.getFeeDueByCourse();
		final ChartSeries percentage = new ChartSeries();
		percentage.setLabel("FeeDue");
		for (final Map.Entry<Klass, Double> entry : expensesMapByType.entrySet()) {
			percentage.set(entry.getKey().getName(), entry.getValue());
			this.feeDueAcademicYearAnalysisPieChart.set(entry.getKey().getName(), entry.getValue());
		}
		this.feeDueAcademicYearAnalysisChart.addSeries(percentage);
	}

	private void generateChartByGender() {
		final Map<GenderConstant, Double> expensesMapByType = this.feeDueAcademicYearDO.getFeeDueByGender();
		final ChartSeries percentage = new ChartSeries();
		percentage.setLabel("FeeDue");
		for (final Map.Entry<GenderConstant, Double> entry : expensesMapByType.entrySet()) {
			percentage.set(entry.getKey().getLabel(), entry.getValue());
			this.feeDueAcademicYearAnalysisPieChart.set(entry.getKey().getLabel(), entry.getValue());
		}
		this.feeDueAcademicYearAnalysisChart.addSeries(percentage);
	}

	private void generateChartByLocation() {
		final Map<String, Double> expensesMapByType = this.feeDueAcademicYearDO.getFeeDueByLocation();
		final ChartSeries percentage = new ChartSeries();
		percentage.setLabel("FeeDue");
		for (final Map.Entry<String, Double> entry : expensesMapByType.entrySet()) {
			percentage.set(entry.getKey(), entry.getValue());
			this.feeDueAcademicYearAnalysisPieChart.set(entry.getKey(), entry.getValue());
		}
		this.feeDueAcademicYearAnalysisChart.addSeries(percentage);
	}

	/**
	 * @return the feeDueAcademicYearAnalysisChart
	 */
	public CartesianChartModel getFeeDueAcademicYearAnalysisChart() {
		return this.feeDueAcademicYearAnalysisChart;
	}

	/**
	 * @param feeDueAcademicYearAnalysisChart
	 *            the feeDueAcademicYearAnalysisChart to set
	 */
	public void setFeeDueAcademicYearAnalysisChart(final CartesianChartModel feeDueAcademicYearAnalysisChart) {
		this.feeDueAcademicYearAnalysisChart = feeDueAcademicYearAnalysisChart;
	}

	/**
	 * @return the feeDueAcademicYearAnalysisPieChart
	 */
	public PieChartModel getFeeDueAcademicYearAnalysisPieChart() {
		return this.feeDueAcademicYearAnalysisPieChart;
	}

	/**
	 * @param feeDueAcademicYearAnalysisPieChart
	 *            the feeDueAcademicYearAnalysisPieChart to set
	 */
	public void setFeeDueAcademicYearAnalysisPieChart(final PieChartModel feeDueAcademicYearAnalysisPieChart) {
		this.feeDueAcademicYearAnalysisPieChart = feeDueAcademicYearAnalysisPieChart;
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
