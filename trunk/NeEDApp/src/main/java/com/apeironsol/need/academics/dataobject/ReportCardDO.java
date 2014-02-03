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
import com.apeironsol.need.core.model.Subject;
import com.apeironsol.need.util.constants.StudentExamSubjectStatusConstant;
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

	/**
	 * List of student exam subjects.
	 */
	private Map<Long, StudentAcademicExamDO>	examByStudentAcademicExamDOMap	= new HashMap<Long, StudentAcademicExamDO>();

	private Map<Long, Integer>					examPercentages					= new HashMap<Long, Integer>();

	private Map<Long, Double>					examScoredPercentages			= new HashMap<Long, Double>();

	private String								gradeForReportCard;

	private ReportCard							reportCard;

	private double								scoredMarksForReportCard;

	private double								scoredPercentageForReportCard;

	private Map<Subject, Double>				scoresBySubject					= new HashMap<Subject, Double>();

	private double								totalMarksForReportCard;

	private double								totalPercentageForReportCard;

	private boolean								reportCardCalculated			= false;

	public void computeReportCard() {
		this.reportCardCalculated = true;
		this.totalPercentageForReportCard = 0;
		this.scoredPercentageForReportCard = 0;
		this.totalMarksForReportCard = 0;
		this.scoredMarksForReportCard = 0;
		this.examScoredPercentages.clear();
		this.scoresBySubject.clear();
		for (final Map.Entry<Long, Integer> entry : this.examPercentages.entrySet()) {
			this.examScoredPercentages.put(entry.getKey(), (double) entry.getValue()
					* this.examByStudentAcademicExamDOMap.get(entry.getKey()).getPercentageScored());
			for (final StudentExamSubjectDO studentExamSubjectDO : this.examByStudentAcademicExamDOMap.get(entry.getKey()).getStudentExamSubjectDOs()) {
				if (StudentExamSubjectStatusConstant.ASSIGNED.equals(studentExamSubjectDO.getStudentExamSubject().getStudentExamSubjectStatus())
						|| StudentExamSubjectStatusConstant.NOT_APPLICABLE.equals(studentExamSubjectDO.getStudentExamSubject().getStudentExamSubjectStatus())) {
					this.scoresBySubject.put(studentExamSubjectDO.getSubject(), -1d);
				} else {
					final double marksBySubject = (studentExamSubjectDO.getStudentExamSubject().getScoredMarks() * entry.getValue()) / 100;
					if (this.scoresBySubject.get(studentExamSubjectDO.getSubject()) != null) {
						this.scoresBySubject.put(studentExamSubjectDO.getSubject(),
								marksBySubject + this.scoresBySubject.get(studentExamSubjectDO.getSubject()));
					} else {
						this.scoresBySubject.put(studentExamSubjectDO.getSubject(), marksBySubject);
					}
				}
			}
			this.totalPercentageForReportCard += entry.getValue();
			this.scoredPercentageForReportCard += (double) entry.getValue() * this.examByStudentAcademicExamDOMap.get(entry.getKey()).getPercentageScored();
			this.totalMarksForReportCard += this.examByStudentAcademicExamDOMap.get(entry.getKey()).getTotalMaximumMarks();
			this.scoredMarksForReportCard += this.examByStudentAcademicExamDOMap.get(entry.getKey()).getTotalScoredMarks();
		}
	}

	/**
	 * @return the examByStudentAcademicExamDOMap
	 */
	public Map<Long, StudentAcademicExamDO> getExamByStudentAcademicExamDOMap() {
		return this.examByStudentAcademicExamDOMap;
	}

	/**
	 * @return the examPercentages
	 */
	public Map<Long, Integer> getExamPercentages() {
		return this.examPercentages;
	}

	/**
	 * @return the examScoredPercentages
	 */
	public Map<Long, Double> getExamScoredPercentages() {
		return this.examScoredPercentages;
	}

	/**
	 * @return the gradeForReportCard
	 */
	public String getGradeForReportCard() {
		// asd
		if (StudentSubjectExamResultConstant.NOT_APPLICABLE.equals(getStudentReportCardResult())) {
			this.gradeForReportCard = StudentSubjectExamResultConstant.NOT_APPLICABLE.getLabel();
		} else {
			if (this.scoredPercentageForReportCard <= 0) {
				computeReportCard();
			}
			if ((this.reportCard != null) && (this.reportCard.getGradeSystem() != null)) {
				final GradeSystem gradeSystem = this.reportCard.getGradeSystem();
				final Collection<GradeSystemRange> gradeSystemRanges = gradeSystem.getGradeSystemRange();
				final double roundedscoredPercentageForReportCard = Math.round(this.scoredPercentageForReportCard);
				for (final GradeSystemRange gradeSystemRange : gradeSystemRanges) {
					if ((roundedscoredPercentageForReportCard >= gradeSystemRange.getMinimumRange())
							&& (roundedscoredPercentageForReportCard <= gradeSystemRange.getMaximumRange())) {
						this.gradeForReportCard = gradeSystemRange.getDistinction();
						break;
					}
				}
			}
		}
		return this.gradeForReportCard;
	}

	/**
	 * @return the gradeForReportCard
	 */
	public String getGradeForReportCardSubject(final Subject subject) {
		if (!this.reportCardCalculated && (this.scoredPercentageForReportCard <= 0)) {
			computeReportCard();
		}
		String subjectGrade = "N/A";
		if ((this.scoresBySubject != null) && (this.scoresBySubject.get(subject) != null)) {
			final int subjectPercentage = this.scoresBySubject.get(subject).intValue();
			final GradeSystem gradeSystem = this.reportCard.getGradeSystem();
			final Collection<GradeSystemRange> gradeSystemRanges = gradeSystem.getGradeSystemRange();
			for (final GradeSystemRange gradeSystemRange : gradeSystemRanges) {
				if ((subjectPercentage >= gradeSystemRange.getMinimumRange()) && (subjectPercentage <= gradeSystemRange.getMaximumRange())) {
					subjectGrade = gradeSystemRange.getDistinction();
					break;
				}
			}
		}
		return subjectGrade;
	}

	/**
	 * @return the reportCard
	 */
	public ReportCard getReportCard() {
		return this.reportCard;
	}

	/**
	 * @return the scoredMarksForReportCard
	 */
	public double getScoredMarksForReportCard() {
		return this.scoredMarksForReportCard;
	}

	/**
	 * @return the scoredPercentageForReportCard
	 */
	public double getScoredPercentageForReportCard() {
		return this.scoredPercentageForReportCard;
	}

	public Map<Subject, Double> getScoresBySubject() {
		return this.scoresBySubject;
	}

	/**
	 * @return the selectedReportCardDO
	 */
	public Collection<StudentAcademicExamDO> getStudentAcademicExamDOs() {
		final Collection<StudentAcademicExamDO> studentAcademicExamDOs = new ArrayList<StudentAcademicExamDO>();
		if (getExamByStudentAcademicExamDOMap() != null) {
			studentAcademicExamDOs.addAll(getExamByStudentAcademicExamDOMap().values());
		}

		return studentAcademicExamDOs;
	}

	/**
	 * @return the studentSubjectExamResult
	 */
	public StudentSubjectExamResultConstant getStudentReportCardResult() {
		StudentSubjectExamResultConstant studentSubjectExamResult = StudentSubjectExamResultConstant.PASS;
		if ((this.scoresBySubject != null)) {
			final int passMarks = this.reportCard.getPassMarksForEachSubject();
			for (final Map.Entry<Subject, Double> entry : this.scoresBySubject.entrySet()) {
				if (entry.getValue() == -1) {
					studentSubjectExamResult = StudentSubjectExamResultConstant.NOT_APPLICABLE;
					break;
				} else if (entry.getValue() < passMarks) {
					studentSubjectExamResult = StudentSubjectExamResultConstant.FAIL;
				}
			}

		}
		return studentSubjectExamResult;
	}

	/**
	 * @return the gradeForReportCard
	 */
	public StudentSubjectExamResultConstant getResultForReportCardSubject(final Subject subject) {

		StudentSubjectExamResultConstant studentSubjectExamResult = StudentSubjectExamResultConstant.PASS;
		if ((this.scoresBySubject != null) && (this.scoresBySubject.get(subject) != null)) {
			final int passMarks = this.reportCard.getPassMarksForEachSubject();
			final int subjectPercentage = this.scoresBySubject.get(subject).intValue();
			if (subjectPercentage == -1) {
				studentSubjectExamResult = StudentSubjectExamResultConstant.NOT_APPLICABLE;
			} else if (subjectPercentage < passMarks) {
				studentSubjectExamResult = StudentSubjectExamResultConstant.FAIL;
			}
		}
		return studentSubjectExamResult;
	}

	/**
	 * @return the totalMarksForReportCard
	 */
	public double getTotalMarksForReportCard() {
		return this.totalMarksForReportCard;
	}

	/**
	 * @return the totalPercentageForReportCard
	 */
	public double getTotalPercentageForReportCard() {
		return this.totalPercentageForReportCard;
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

	/**
	 * @param examScoredPercentages
	 *            the examScoredPercentages to set
	 */
	public void setExamScoredPercentages(final Map<Long, Double> examScoredPercentages) {
		this.examScoredPercentages = examScoredPercentages;
	}

	/**
	 * @param reportCard
	 *            the reportCard to set
	 */
	public void setReportCard(final ReportCard reportCard) {
		this.reportCard = reportCard;
	}

	/**
	 * @param scoredMarksForReportCard
	 *            the scoredMarksForReportCard to set
	 */
	public void setScoredMarksForReportCard(final double scoredMarksForReportCard) {
		this.scoredMarksForReportCard = scoredMarksForReportCard;
	}

	/**
	 * @param scoredPercentageForReportCard
	 *            the scoredPercentageForReportCard to set
	 */
	public void setScoredPercentageForReportCard(final double scoredPercentageForReportCard) {
		this.scoredPercentageForReportCard = scoredPercentageForReportCard;
	}

	public void setScoresBySubject(final Map<Subject, Double> scoresBySubject) {
		this.scoresBySubject = scoresBySubject;
	}

	/**
	 * @param totalMarksForReportCard
	 *            the totalMarksForReportCard to set
	 */
	public void setTotalMarksForReportCard(final double totalMarksForReportCard) {
		this.totalMarksForReportCard = totalMarksForReportCard;
	}

	/**
	 * @param totalPercentageForReportCard
	 *            the totalPercentageForReportCard to set
	 */
	public void setTotalPercentageForReportCard(final double totalPercentageForReportCard) {
		this.totalPercentageForReportCard = totalPercentageForReportCard;
	}

}
