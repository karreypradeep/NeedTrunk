package com.apeironsol.need.util.portal;

import java.io.Serializable;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.apeironsol.need.util.constants.StudentSubjectExamResultConstant;

public class ReprotCardTreeNode extends DefaultTreeNode implements Serializable {

	/**
	 * 
	 */
	private static final long					serialVersionUID	= -698611227663524905L;

	private String								name;

	private Long								reportCardId;

	private StudentSubjectExamResultConstant	result;

	private String								grade;

	private double								scoredPercentage;

	public ReprotCardTreeNode() {
		super();
	}

	public ReprotCardTreeNode(final Object data, final TreeNode parent) {
		super(data, parent);

	}

	public ReprotCardTreeNode(final String type, final Object data, final TreeNode parent) {
		super(type, data, parent);
	}

	/**
	 * @return the grade
	 */
	public String getGrade() {
		return this.grade;
	}

	public String getName() {
		return this.name;
	}

	/**
	 * @return the result
	 */
	public StudentSubjectExamResultConstant getResult() {
		return this.result;
	}

	/**
	 * @return the scoredPercentage
	 */
	public double getScoredPercentage() {
		return this.scoredPercentage;
	}

	/**
	 * @param grade
	 *            the grade to set
	 */
	public void setGrade(final String grade) {
		this.grade = grade;
	}

	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(final StudentSubjectExamResultConstant result) {
		this.result = result;
	}

	/**
	 * @param scoredPercentage
	 *            the scoredPercentage to set
	 */
	public void setScoredPercentage(final double scoredPercentage) {
		this.scoredPercentage = scoredPercentage;
	}

	/**
	 * @return the reportCardId
	 */
	public Long getReportCardId() {
		return this.reportCardId;
	}

	/**
	 * @param reportCardId
	 *            the reportCardId to set
	 */
	public void setReportCardId(final Long reportCardId) {
		this.reportCardId = reportCardId;
	}

}
