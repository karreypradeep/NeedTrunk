package com.apeironsol.need.util.portal;

import java.io.Serializable;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.apeironsol.need.util.constants.StudentSubjectExamResultConstant;

public class ExamSubjectResultTreeNode extends DefaultTreeNode implements Serializable {

	/**
	 * 
	 */
	private static final long					serialVersionUID			= 2443706691126629898L;

	private double								marksScored;

	private String								name;

	private double								percentageForReportCard;

	private String								status;

	private StudentSubjectExamResultConstant	studentSubjectExamResult;

	private double								totalMarks;

	private double								scoredPercentage;

	private boolean								reportCardExamDetailsInd	= false;

	private double								scoredPercentageForReportCard;

	public ExamSubjectResultTreeNode() {
		super();
	}

	public ExamSubjectResultTreeNode(final Object data, final TreeNode parent) {
		super(data, parent);

	}

	public ExamSubjectResultTreeNode(final String type, final Object data, final TreeNode parent) {
		super(type, data, parent);
	}

	/**
	 * @return the marksScored
	 */
	public double getMarksScored() {
		return this.marksScored;
	}

	public String getName() {
		return this.name;
	}

	/**
	 * @return the percentageForReportCard
	 */
	public double getPercentageForReportCard() {
		return this.percentageForReportCard;
	}

	/**
	 * @return the scoredPercentage
	 */
	public double getScoredPercentage() {
		return this.scoredPercentage;
	}

	/**
	 * @return the scoredPercentageForReportCard
	 */
	public double getScoredPercentageForReportCard() {
		return this.scoredPercentageForReportCard;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * @return the studentSubjectExamResult
	 */
	public StudentSubjectExamResultConstant getStudentSubjectExamResult() {
		return this.studentSubjectExamResult;
	}

	/**
	 * @return the totalMarks
	 */
	public double getTotalMarks() {
		return this.totalMarks;
	}

	/**
	 * @return the reportCardExamDetailsInd
	 */
	public boolean isReportCardExamDetailsInd() {
		return this.reportCardExamDetailsInd;
	}

	/**
	 * @param marksScored
	 *            the marksScored to set
	 */
	public void setMarksScored(final double marksScored) {
		this.marksScored = marksScored;
	}

	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @param percentageForReportCard
	 *            the percentageForReportCard to set
	 */
	public void setPercentageForReportCard(final double percentageForReportCard) {
		this.percentageForReportCard = percentageForReportCard;
	}

	/**
	 * @param reportCardExamDetailsInd
	 *            the reportCardExamDetailsInd to set
	 */
	public void setReportCardExamDetailsInd(final boolean reportCardExamDetailsInd) {
		this.reportCardExamDetailsInd = reportCardExamDetailsInd;
	}

	/**
	 * @param scoredPercentage
	 *            the scoredPercentage to set
	 */
	public void setScoredPercentage(final double scoredPercentage) {
		this.scoredPercentage = scoredPercentage;
	}

	/**
	 * @param scoredPercentageForReportCard
	 *            the scoredPercentageForReportCard to set
	 */
	public void setScoredPercentageForReportCard(final double scoredPercentageForReportCard) {
		this.scoredPercentageForReportCard = scoredPercentageForReportCard;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(final String status) {
		this.status = status;
	}

	/**
	 * @param studentSubjectExamResult
	 *            the studentSubjectExamResult to set
	 */
	public void setStudentSubjectExamResult(final StudentSubjectExamResultConstant studentSubjectExamResult) {
		this.studentSubjectExamResult = studentSubjectExamResult;
	}

	/**
	 * @param totalMarks
	 *            the totalMarks to set
	 */
	public void setTotalMarks(final double totalMarks) {
		this.totalMarks = totalMarks;
	}

}
