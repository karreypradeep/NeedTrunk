/**
 * 
 */
package com.apeironsol.need.academics.dataobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.apeironsol.need.academics.model.GradeSystem;
import com.apeironsol.need.academics.model.GradeSystemRange;
import com.apeironsol.need.academics.model.ReportCard;
import com.apeironsol.need.util.constants.StudentSubjectExamResultConstant;

/**
 * @author pradeep
 * 
 */
public class ReportCardDO implements Serializable {

	/**  
	 * 
	 */
	private static final long					serialVersionUID				= 567578771156853175L;

	private ReportCard							reportCard;

	/**
	 * List of student exam subjects.
	 */
	private Map<Long, StudentAcademicExamDO>	examByStudentAcademicExamDOMap	= new HashMap<Long, StudentAcademicExamDO>();

	private Map<Long, Integer>					examPercentages					= new HashMap<Long, Integer>();

	private double								totalPercentageForReportCard;

	private double								scoredPercentageForReportCard;

	private double								totalMarksForReportCard;

	private double								scoredMarksForReportCard;

	private String								gradeForReportCard;

	/**
	 * @return the totalPercentageForReportCard
	 */
	public double getTotalPercentageForReportCard() {
		return this.totalPercentageForReportCard;
	}

	/**
	 * @param totalPercentageForReportCard
	 *            the totalPercentageForReportCard to set
	 */
	public void setTotalPercentageForReportCard(final double totalPercentageForReportCard) {
		this.totalPercentageForReportCard = totalPercentageForReportCard;
	}

	/**
	 * @return the scoredPercentageForReportCard
	 */
	public double getScoredPercentageForReportCard() {
		return this.scoredPercentageForReportCard;
	}

	/**
	 * @param scoredPercentageForReportCard
	 *            the scoredPercentageForReportCard to set
	 */
	public void setScoredPercentageForReportCard(final double scoredPercentageForReportCard) {
		this.scoredPercentageForReportCard = scoredPercentageForReportCard;
	}

	/**
	 * @return the totalMarksForReportCard
	 */
	public double getTotalMarksForReportCard() {
		return this.totalMarksForReportCard;
	}

	/**
	 * @param totalMarksForReportCard
	 *            the totalMarksForReportCard to set
	 */
	public void setTotalMarksForReportCard(final double totalMarksForReportCard) {
		this.totalMarksForReportCard = totalMarksForReportCard;
	}

	/**
	 * @return the scoredMarksForReportCard
	 */
	public double getScoredMarksForReportCard() {
		return this.scoredMarksForReportCard;
	}

	/**
	 * @param scoredMarksForReportCard
	 *            the scoredMarksForReportCard to set
	 */
	public void setScoredMarksForReportCard(final double scoredMarksForReportCard) {
		this.scoredMarksForReportCard = scoredMarksForReportCard;
	}

	/**
	 * @return the reportCard
	 */
	public ReportCard getReportCard() {
		return this.reportCard;
	}

	/**
	 * @param reportCard
	 *            the reportCard to set
	 */
	public void setReportCard(final ReportCard reportCard) {
		this.reportCard = reportCard;
	}

	/**
	 * @return the examPercentages
	 */
	public Map<Long, Integer> getExamPercentages() {
		return this.examPercentages;
	}

	/**
	 * @return the examByStudentAcademicExamDOMap
	 */
	public Map<Long, StudentAcademicExamDO> getExamByStudentAcademicExamDOMap() {
		return this.examByStudentAcademicExamDOMap;
	}

	/**
	 * @param examByStudentAcademicExamDOMap
	 *            the examByStudentAcademicExamDOMap to set
	 */
	public void setExamByStudentAcademicExamDOMap(final Map<Long, StudentAcademicExamDO> examByStudentAcademicExamDOMap) {
		this.examByStudentAcademicExamDOMap = examByStudentAcademicExamDOMap;
	}

	/**
	 * @param examPercentages
	 *            the examPercentages to set
	 */
	public void setExamPercentages(final Map<Long, Integer> examPercentages) {
		this.examPercentages = examPercentages;
	}

	public void computeReportCard() {
		this.totalPercentageForReportCard = 0;
		this.scoredPercentageForReportCard = 0;
		this.totalMarksForReportCard = 0;
		this.scoredMarksForReportCard = 0;
		for (Map.Entry<Long, Integer> entry : this.examPercentages.entrySet()) {
			this.examByStudentAcademicExamDOMap.get(entry.getKey()).setPercentageForReportCard(entry.getValue());
			this.examByStudentAcademicExamDOMap.get(entry.getKey()).setScoredPercentageForReportCard(
					(double) entry.getValue() * this.examByStudentAcademicExamDOMap.get(entry.getKey()).getPercentageScored());
			this.totalPercentageForReportCard += entry.getValue();
			this.scoredPercentageForReportCard += (double) entry.getValue() * this.examByStudentAcademicExamDOMap.get(entry.getKey()).getPercentageScored();
			this.totalMarksForReportCard += this.examByStudentAcademicExamDOMap.get(entry.getKey()).getTotalMaximumMarks();
			this.scoredMarksForReportCard += this.examByStudentAcademicExamDOMap.get(entry.getKey()).getTotalScoredMarks();
		}
	}

	/**
	 * @return the gradeForReportCard
	 */
	public String getGradeForReportCard() {
		if (StudentSubjectExamResultConstant.NOT_APPLICABLE.equals(this.getStudentReportCardResult())) {
			this.gradeForReportCard = StudentSubjectExamResultConstant.NOT_APPLICABLE.getLabel();
		} else {
			if (this.scoredPercentageForReportCard <= 0) {
				this.computeReportCard();
			}
			if (this.reportCard != null && this.reportCard.getGradeSystem() != null) {
				GradeSystem gradeSystem = this.reportCard.getGradeSystem();
				Collection<GradeSystemRange> gradeSystemRanges = gradeSystem.getGradeSystemRange();
				for (GradeSystemRange gradeSystemRange : gradeSystemRanges) {
					if (this.scoredPercentageForReportCard >= gradeSystemRange.getMinimumRange()
							&& this.scoredPercentageForReportCard <= gradeSystemRange.getMaximumRange()) {
						this.gradeForReportCard = gradeSystemRange.getDistinction();
					}
				}
			}
		}
		return this.gradeForReportCard;
	}

	/**
	 * @return the studentSubjectExamResult
	 */
	public StudentSubjectExamResultConstant getStudentReportCardResult() {
		StudentSubjectExamResultConstant studentSubjectExamResult = StudentSubjectExamResultConstant.NOT_APPLICABLE;
		if (this.examByStudentAcademicExamDOMap != null) {
			for (StudentAcademicExamDO studentAcademicExamDO : this.examByStudentAcademicExamDOMap.values()) {
				if (StudentSubjectExamResultConstant.NOT_APPLICABLE.equals(studentAcademicExamDO.getStudentExamResult())
						|| StudentSubjectExamResultConstant.FAIL.equals(studentAcademicExamDO.getStudentExamResult())) {
					studentSubjectExamResult = studentAcademicExamDO.getStudentExamResult();
					break;
				}
				studentSubjectExamResult = StudentSubjectExamResultConstant.PASS;
			}
		}
		return studentSubjectExamResult;
	}

	/**
	 * @return the selectedReportCardDO
	 */
	public Collection<StudentAcademicExamDO> getStudentAcademicExamDOs() {
		Collection<StudentAcademicExamDO> studentAcademicExamDOs = new ArrayList<StudentAcademicExamDO>();
		if (this.getExamByStudentAcademicExamDOMap() != null) {
			studentAcademicExamDOs.addAll(this.getExamByStudentAcademicExamDOMap().values());
		}

		return studentAcademicExamDOs;
	}
}
