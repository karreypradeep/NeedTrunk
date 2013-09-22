/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.core.portal;

/**
 * Managed bean for section attendance.
 * 
 * @author Pradeep
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Named;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.dataobject.SectionAttendanceReportMonthlyDO;
import com.apeironsol.need.core.service.SectionAttendanceService;
import com.apeironsol.need.core.service.StudentService;
import com.apeironsol.need.util.DateUtil;
import com.apeironsol.need.util.comparator.SectionAttendanceReportMonthlyDOComparator;

@Named
@Scope("session")
public class SectionAttendanceReportBean extends AbstractTabbedBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long								serialVersionUID			= -2545811400870265634L;

	private CartesianChartModel								sectionStudentsYearlyChart	= new CartesianChartModel();

	@Resource
	private SectionBean										sectionBean;

	@Resource
	private SectionAttendanceService						sectionAttendanceService;

	@Resource
	private StudentService									studentService;

	private Calendar										attendanceMonth;

	private Collection<SectionAttendanceReportMonthlyDO>	sectionAttendanceReportMonthlyDOs;

	@Override
	public void onTabChange() {

	}

	/**
	 * @return the sectionStudentsYearlyChart
	 */
	public CartesianChartModel getSectionStudentsYearlyChart() {
		return this.sectionStudentsYearlyChart;
	}

	/**
	 * @param sectionStudentsYearlyChart
	 *            the sectionStudentsYearlyChart to set
	 */
	public void setSectionStudentsYearlyChart(final CartesianChartModel sectionStudentsYearlyChart) {
		this.sectionStudentsYearlyChart = sectionStudentsYearlyChart;
	}

	/**
	 * Subject exam results.
	 */
	public void viewSectionAttendaceLineChartReport() {
		this.attendanceMonth = Calendar.getInstance();
		if (this.sessionBean.getCurrentAcademicYear() != null
				&& this.sectionBean.getSection().getAcademicYear().getId().equals(this.sessionBean.getCurrentAcademicYear().getId())) {
			this.attendanceMonth.setTime(DateUtil.getSystemDate());
		} else {
			this.attendanceMonth.setTime(this.sectionBean.getSection().getAcademicYear().getClassStartDate());
		}

		this.setSectionAttendanceReportMonthlyDOs(this.sectionAttendanceService.generateSectionAttendanceReportForAcademicYear(this.sectionBean.getSection()
				.getId(), this.sectionBean.getSection().getAcademicYear().getId()));

		SectionAttendanceReportMonthlyDOComparator comparator = new SectionAttendanceReportMonthlyDOComparator(
				SectionAttendanceReportMonthlyDOComparator.Order.START_DATE);
		comparator.setDescending();
		Collections.sort((List<SectionAttendanceReportMonthlyDO>) this.getSectionAttendanceReportMonthlyDOs(), comparator);

		this.sectionStudentsYearlyChart = new CartesianChartModel();

		ChartSeries percentage = new ChartSeries();
		percentage.setLabel("Attendance percentage");

		List<SectionAttendanceReportMonthlyDO> chartDate = new ArrayList<SectionAttendanceReportMonthlyDO>(this.sectionAttendanceReportMonthlyDOs);
		comparator.setAscending();

		Collections.sort(chartDate, comparator);
		int numberOfActiveStudentsInSection = this.studentService.findActiveStudentSectionsBySectionId(this.sectionBean.getSection().getId()).size();
		for (SectionAttendanceReportMonthlyDO sectionAttendanceReportMonthlyDO : chartDate) {
			if (sectionAttendanceReportMonthlyDO.getNumberOfActiveStudents() == 0) {
				sectionAttendanceReportMonthlyDO.setNumberOfActiveStudents(numberOfActiveStudentsInSection);
			}
			double numberOfStudentsAttendance = sectionAttendanceReportMonthlyDO.getNumberOfPeriods()
					* sectionAttendanceReportMonthlyDO.getNumberOfActiveStudents();

			double numberOfStudentsAbsentes = sectionAttendanceReportMonthlyDO.getNumberOfStudentAbsents();

			double percent = numberOfStudentsAttendance > 0 ? (numberOfStudentsAttendance - numberOfStudentsAbsentes) * 100 / numberOfStudentsAttendance : 0;

			percentage.set(sectionAttendanceReportMonthlyDO.getMonthConstant().getLabel(), percent);
		}

		this.sectionStudentsYearlyChart.addSeries(percentage);
	}

	/**
	 * @return the sectionAttendanceReportMonthlyDOs
	 */
	public Collection<SectionAttendanceReportMonthlyDO> getSectionAttendanceReportMonthlyDOs() {
		return this.sectionAttendanceReportMonthlyDOs;
	}

	/**
	 * @param sectionAttendanceReportMonthlyDOs
	 *            the sectionAttendanceReportMonthlyDOs to set
	 */
	public void setSectionAttendanceReportMonthlyDOs(final Collection<SectionAttendanceReportMonthlyDO> sectionAttendanceReportMonthlyDOs) {
		this.sectionAttendanceReportMonthlyDOs = sectionAttendanceReportMonthlyDOs;
	}

}
