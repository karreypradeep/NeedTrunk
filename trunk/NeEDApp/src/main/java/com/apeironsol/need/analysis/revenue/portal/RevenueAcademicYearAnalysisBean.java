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
import org.springframework.context.annotation.Scope;

import com.apeironsol.need.analysis.revenue.dataobject.RevenueAcademicYearDO;
import com.apeironsol.need.analysis.revenue.service.RevenueAnalysisService;
import com.apeironsol.need.core.model.Klass;
import com.apeironsol.need.core.portal.AbstractTabbedBean;
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
			this.analyseRevenueByAcademicYearLineChartReport();
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
	public void analyseRevenueByAcademicYearLineChartReport() {
		this.revenueAcademicYearAnalysisChart = new CartesianChartModel();
		if (this.revenueAcademicYearDO != null) {
			final Map<Klass, Double> expensesMapByType = this.revenueAcademicYearDO.getRevenueByCourse();
			final ChartSeries percentage = new ChartSeries();
			percentage.setLabel("Revenue");
			for (final Map.Entry<Klass, Double> entry : expensesMapByType.entrySet()) {
				percentage.set(entry.getKey().getName(), entry.getValue());
			}
			this.revenueAcademicYearAnalysisChart.addSeries(percentage);
		}
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
}
