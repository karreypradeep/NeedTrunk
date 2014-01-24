/**
 * 
 */
package com.apeironsol.need.academics.dataobject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.apeironsol.need.academics.model.ReportCard;

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
		for (Map.Entry<Long, Integer> entry : this.examPercentages.entrySet()) {
			this.totalPercentageForReportCard += entry.getValue();
			this.scoredPercentageForReportCard += (double) entry.getValue() * this.examByStudentAcademicExamDOMap.get(entry.getKey()).getPercentageScored();
		}
	}
}
