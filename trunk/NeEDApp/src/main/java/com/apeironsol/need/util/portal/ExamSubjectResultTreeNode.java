package com.apeironsol.need.util.portal;

import java.io.Serializable;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.apeironsol.need.util.constants.StudentSubjectExamResultConstant;

public class ExamSubjectResultTreeNode extends DefaultTreeNode implements Serializable {

	/**
	 * 
	 */
	private static final long					serialVersionUID	= 2443706691126629898L;

	private StudentSubjectExamResultConstant	studentSubjectExamResult;

	private double								totalPercentage;

	private double								percentageScored;

	private String								name;

	private String								status;

	public ExamSubjectResultTreeNode() {
		super();
	}

	public ExamSubjectResultTreeNode(final Object data, final TreeNode parent) {
		super(data, parent);

	}

	public ExamSubjectResultTreeNode(final String type, final Object data, final TreeNode parent) {
		super(type, data, parent);
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the studentSubjectExamResult
	 */
	public StudentSubjectExamResultConstant getStudentSubjectExamResult() {
		return this.studentSubjectExamResult;
	}

	/**
	 * @param studentSubjectExamResult
	 *            the studentSubjectExamResult to set
	 */
	public void setStudentSubjectExamResult(final StudentSubjectExamResultConstant studentSubjectExamResult) {
		this.studentSubjectExamResult = studentSubjectExamResult;
	}

	/**
	 * @return the totalPercentage
	 */
	public double getTotalPercentage() {
		return this.totalPercentage;
	}

	/**
	 * @param totalPercentage
	 *            the totalPercentage to set
	 */
	public void setTotalPercentage(final double totalPercentage) {
		this.totalPercentage = totalPercentage;
	}

	/**
	 * @return the percentageScored
	 */
	public double getPercentageScored() {
		return this.percentageScored;
	}

	/**
	 * @param percentageScored
	 *            the percentageScored to set
	 */
	public void setPercentageScored(final double percentageScored) {
		this.percentageScored = percentageScored;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(final String status) {
		this.status = status;
	}

}
