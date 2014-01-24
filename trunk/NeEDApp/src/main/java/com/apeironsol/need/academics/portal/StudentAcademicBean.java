package com.apeironsol.need.academics.portal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Named;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.springframework.context.annotation.Scope;

import com.apeironsol.need.academics.dataobject.StudentAcademicExamDO;
import com.apeironsol.need.academics.dataobject.StudentAcademicSubjectDO;
import com.apeironsol.need.academics.dataobject.StudentExamSubjectDO;
import com.apeironsol.need.academics.service.StudentAcademicService;
import com.apeironsol.need.core.portal.AbstractPortalBean;
import com.apeironsol.need.core.portal.StudentBean;
import com.apeironsol.need.util.comparator.StudentAcademicExamDOComparator;
import com.apeironsol.need.util.comparator.StudentAcademicSubjectDOComparator;
import com.apeironsol.need.util.comparator.StudentExamSubjectDOComparator;

@Named
@Scope("session")
public class StudentAcademicBean extends AbstractPortalBean {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long		serialVersionUID	= 6903693331662339318L;

	@Resource
	private StudentAcademicService	studentAcademicService;

	@Resource
	private StudentBean				studentBean;

	private boolean					viewStudentExamSubjectResultsFlag;

	public enum AcademicWizard {

		EXAM_WISE_RESULTS("examWiseResults"),
		SUBJECT_WISE_RESULTS("subjectWiseResults"),
		EXAM_SUBJECT_RESULTS("examSubjectResults"),
		SUBJECT_EXAM_RESULTS("subjectExamResults");

		private String	key;

		AcademicWizard(final String key) {
			this.key = key;
		}

		public String getKey() {
			return this.key;
		}

		public void setKey(final String key) {
			this.key = key;
		}
	};

	private String									academicWizardActiveStep	= AcademicWizard.EXAM_WISE_RESULTS.getKey();

	private Collection<StudentAcademicExamDO>		studentAcademicExamDOs		= new ArrayList<StudentAcademicExamDO>();

	private CartesianChartModel						studentAcademicExamsChart;

	private CartesianChartModel						studentExamSubjectChart;

	private CartesianChartModel						studentAcademicSubjectsChart;

	private CartesianChartModel						studentSubjectExamsChart;

	private Collection<StudentExamSubjectDO>		studentExamSubjectDOs		= new ArrayList<StudentExamSubjectDO>();

	private Collection<StudentAcademicSubjectDO>	studentAcademicSubjectDOs	= new ArrayList<StudentAcademicSubjectDO>();

	/**
	 * Exam Wise Results
	 */
	public void viewStudentExamWiseResults() {

		this.academicWizardActiveStep = AcademicWizard.EXAM_WISE_RESULTS.getKey();

		this.studentAcademicExamDOs = this.studentAcademicService.getStudentAcademicDetailsByExamWise(this.studentBean.getStudentAcademicYear().getId());

		StudentAcademicExamDOComparator comparator = new StudentAcademicExamDOComparator(StudentAcademicExamDOComparator.Order.EXAM_END_DATE);
		comparator.setDescending();
		Collections.sort((List<StudentAcademicExamDO>) this.studentAcademicExamDOs, comparator);

		this.studentAcademicProgressChart();
	}

	/**
	 * Exam Wise results chart
	 */
	public void studentAcademicProgressChart() {

		this.setStudentAcademicExamsChart(new CartesianChartModel());

		ChartSeries percentage = new ChartSeries();
		percentage.setLabel("Scored percentage");

		List<StudentAcademicExamDO> chartDate = new ArrayList<StudentAcademicExamDO>(this.studentAcademicExamDOs);

		StudentAcademicExamDOComparator comparator = new StudentAcademicExamDOComparator(StudentAcademicExamDOComparator.Order.EXAM_END_DATE);
		comparator.setAscending();
		Collections.sort(chartDate, comparator);

		for (StudentAcademicExamDO studentAcademicExamDO : chartDate) {

			double percent = studentAcademicExamDO.getTotalScoredMarks() * 100 / studentAcademicExamDO.getTotalMaximumMarks();
			percentage.set(studentAcademicExamDO.getExam().getName(), percent);
		}

		this.getStudentAcademicExamsChart().addSeries(percentage);

	}

	/**
	 * Exam subject results
	 */
	public void viewStudentAcademicExamSubjectResults() {

		this.academicWizardActiveStep = AcademicWizard.EXAM_SUBJECT_RESULTS.getKey();

		StudentExamSubjectDOComparator comparator = new StudentExamSubjectDOComparator(StudentExamSubjectDOComparator.Order.SCHEDULED_DATE);
		comparator.setAscending();
		Collections.sort((List<StudentExamSubjectDO>) this.getStudentExamSubjectDOs(), comparator);

		this.setStudentExamSubjectChart(new CartesianChartModel());

		ChartSeries percentages = new ChartSeries();
		percentages.setLabel("Scored percentages");

		List<StudentExamSubjectDO> results = new ArrayList<StudentExamSubjectDO>(this.getStudentExamSubjectDOs());
		comparator.setDescending();

		Collections.sort(results, comparator);

		for (StudentExamSubjectDO studentExamSubjectDO : results) {

			double percentage = studentExamSubjectDO.getStudentExamSubject().getScoredMarks() != null ? studentExamSubjectDO.getStudentExamSubject()
					.getScoredMarks() * 100 / studentExamSubjectDO.getSectionExamSubject().getMaximumMarks() : 0d;

			percentages.set(studentExamSubjectDO.getSubject().getName(), percentage);
		}

		this.getStudentExamSubjectChart().addSeries(percentages);
	}

	/**
	 * Subject wise results
	 */
	public void viewStudentSubjectWiseResults() {
		this.academicWizardActiveStep = AcademicWizard.SUBJECT_WISE_RESULTS.getKey();

		this.setStudentAcademicSubjectDOs(this.studentAcademicService.getStudentAcademicDetailsBySubjectWise(this.studentBean.getStudentAcademicYear().getId()));

		StudentAcademicSubjectDOComparator comparator = new StudentAcademicSubjectDOComparator(StudentAcademicSubjectDOComparator.Order.SUBJECT_NAME);
		comparator.setAscending();
		Collections.sort((List<StudentAcademicSubjectDO>) this.studentAcademicSubjectDOs, comparator);

		this.setStudentExamSubjectChart(new CartesianChartModel());

		ChartSeries percentages = new ChartSeries();
		percentages.setLabel("Scored percentages");

		List<StudentAcademicSubjectDO> chartDate = new ArrayList<StudentAcademicSubjectDO>(this.studentAcademicSubjectDOs);
		comparator.setDescending();
		Collections.sort(chartDate, comparator);

		for (StudentAcademicSubjectDO studentAcademicSubjectDO : chartDate) {

			double percentage = studentAcademicSubjectDO.getTotalScoredMarks() != null ? studentAcademicSubjectDO.getTotalScoredMarks() * 100
					/ studentAcademicSubjectDO.getTotalMaximumMarks() : 0d;

			percentages.set(studentAcademicSubjectDO.getSubject().getName(), percentage);
		}

		this.getStudentExamSubjectChart().addSeries(percentages);
	}

	/**
	 * Subject exam results.
	 */
	public void viewStudentSubjectExamResults() {

		this.academicWizardActiveStep = AcademicWizard.SUBJECT_EXAM_RESULTS.getKey();

		StudentExamSubjectDOComparator comparator = new StudentExamSubjectDOComparator(StudentExamSubjectDOComparator.Order.SCHEDULED_DATE);
		comparator.setDescending();
		Collections.sort((List<StudentExamSubjectDO>) this.studentExamSubjectDOs, comparator);

		this.studentSubjectExamsChart = new CartesianChartModel();

		this.setStudentAcademicExamsChart(new CartesianChartModel());

		ChartSeries percentage = new ChartSeries();
		percentage.setLabel("Scored percentage");

		List<StudentExamSubjectDO> chartDate = new ArrayList<StudentExamSubjectDO>(this.studentExamSubjectDOs);
		comparator.setAscending();

		Collections.sort(chartDate, comparator);

		int i = 0;
		for (StudentExamSubjectDO studentExamSubjectDO : chartDate) {

			double maxMarks = studentExamSubjectDO.getSectionExamSubject().getMaximumMarks();

			double scoredMarks = studentExamSubjectDO.getStudentExamSubject().getScoredMarks() == null ? 0d : studentExamSubjectDO.getStudentExamSubject()
					.getScoredMarks();

			double percent = scoredMarks * 100 / maxMarks;

			percentage.set(String.valueOf(++i), percent);
		}

		this.studentSubjectExamsChart.addSeries(percentage);
	}

	public String getAcademicWizardActiveStep() {
		return this.academicWizardActiveStep;
	}

	public void setAcademicWizardActiveStep(final String academicWizardActiveStep) {
		this.academicWizardActiveStep = academicWizardActiveStep;
	}

	public Collection<StudentAcademicExamDO> getStudentAcademicExamDOs() {
		return this.studentAcademicExamDOs;
	}

	public void setStudentAcademicExamDOs(final Collection<StudentAcademicExamDO> studentAcademicExamDOs) {
		this.studentAcademicExamDOs = studentAcademicExamDOs;
	}

	public Collection<StudentExamSubjectDO> getStudentExamSubjectDOs() {
		return this.studentExamSubjectDOs;
	}

	public void setStudentExamSubjectDOs(final Collection<StudentExamSubjectDO> studentExamSubjectDOs) {
		this.studentExamSubjectDOs = studentExamSubjectDOs;
	}

	public CartesianChartModel getStudentAcademicExamsChart() {
		return this.studentAcademicExamsChart;
	}

	public void setStudentAcademicExamsChart(final CartesianChartModel studentAcademicExamsChart) {
		this.studentAcademicExamsChart = studentAcademicExamsChart;
	}

	public boolean isViewStudentExamSubjectResultsFlag() {
		return this.viewStudentExamSubjectResultsFlag;
	}

	public void setViewStudentExamSubjectResultsFlag(final boolean viewStudentExamSubjectResultsFlag) {
		this.viewStudentExamSubjectResultsFlag = viewStudentExamSubjectResultsFlag;
	}

	public Collection<StudentAcademicSubjectDO> getStudentAcademicSubjectDOs() {
		return this.studentAcademicSubjectDOs;
	}

	public void setStudentAcademicSubjectDOs(final Collection<StudentAcademicSubjectDO> studentAcademicSubjectDOs) {
		this.studentAcademicSubjectDOs = studentAcademicSubjectDOs;
	}

	public CartesianChartModel getStudentExamSubjectChart() {
		return this.studentExamSubjectChart;
	}

	public void setStudentExamSubjectChart(final CartesianChartModel studentExamSubjectChart) {
		this.studentExamSubjectChart = studentExamSubjectChart;
	}

	public CartesianChartModel getStudentAcademicSubjectsChart() {
		return this.studentAcademicSubjectsChart;
	}

	public void setStudentAcademicSubjectsChart(final CartesianChartModel studentAcademicSubjectsChart) {
		this.studentAcademicSubjectsChart = studentAcademicSubjectsChart;
	}

	public CartesianChartModel getStudentSubjectExamsChart() {
		return this.studentSubjectExamsChart;
	}

	public void setStudentSubjectExamsChart(final CartesianChartModel studentSubjectExamsChart) {
		this.studentSubjectExamsChart = studentSubjectExamsChart;
	}

}
