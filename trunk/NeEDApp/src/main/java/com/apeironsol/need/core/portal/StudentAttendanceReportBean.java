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
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Named;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.dataobject.StudentAttendanceMonthlyDO;
import com.apeironsol.need.core.service.AttendanceService;
import com.apeironsol.need.core.service.StudentSectionService;
import com.apeironsol.need.util.comparator.StudentAttendanceMonthlyDOComparator;

@Named
@Scope("session")
public class StudentAttendanceReportBean extends AbstractTabbedBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long		serialVersionUID	= -965622835959353197L;

	private CartesianChartModel		studentYearlyChart	= new CartesianChartModel();

	@Resource
	private StudentAttendanceBean	studentAttendanceBean;

	@Resource
	private StudentSectionService	studentSectionService;

	@Resource
	private AttendanceService		attendanceService;

	@Override
	public void onTabChange() {
		this.viewSectionAttendaceLineChartReport();
	}

	/**
	 * @return the sectionStudentsYearlyChart
	 */
	public CartesianChartModel getStudentYearlyChart() {
		return this.studentYearlyChart;
	}

	/**
	 * @param sectionStudentsYearlyChart
	 *            the sectionStudentsYearlyChart to set
	 */
	public void setStudentYearlyChart(final CartesianChartModel sectionStudentsYearlyChart) {
		this.studentYearlyChart = sectionStudentsYearlyChart;
	}

	/**
	 * Subject exam results.
	 */
	public void viewSectionAttendaceLineChartReport() {
		StudentAttendanceMonthlyDOComparator comparator = new StudentAttendanceMonthlyDOComparator(
				StudentAttendanceMonthlyDOComparator.Order.ATTENDANCE_MONTH_DATE);
		comparator.setDescending();

		this.studentYearlyChart = new CartesianChartModel();

		ChartSeries percentage = new ChartSeries();
		percentage.setLabel("Attendance percentage");
		if (this.studentAttendanceBean.getStudentAttendanceMonthlyDOs() != null) {
			Collections.sort((List<StudentAttendanceMonthlyDO>) this.studentAttendanceBean.getStudentAttendanceMonthlyDOs(), comparator);
			List<StudentAttendanceMonthlyDO> chartDate = new ArrayList<StudentAttendanceMonthlyDO>(this.studentAttendanceBean.getStudentAttendanceMonthlyDOs());
			comparator.setAscending();

			Collections.sort(chartDate, comparator);
			for (StudentAttendanceMonthlyDO studentAttendanceMonthlyDO : chartDate) {

				int numberOfStudentsAbsentes = studentAttendanceMonthlyDO.getNumberOfAbsentsForMonth();

				double percent = studentAttendanceMonthlyDO.getNumberOfPeriods() > 0 ? (studentAttendanceMonthlyDO.getNumberOfPeriods() - numberOfStudentsAbsentes)
						* 100 / studentAttendanceMonthlyDO.getNumberOfPeriods()
						: 0;

				percentage.set(studentAttendanceMonthlyDO.getMonth().getLabel(), percent);
			}
		}
		this.studentYearlyChart.addSeries(percentage);
	}
}
