package com.apeironsol.need.util.portal;

import java.io.Serializable;

import org.primefaces.model.TreeNode;

import com.apeironsol.need.financial.model.StudentLevelFee;

public class StudentLevelFeeTreeNode extends FeeTreeNode implements Serializable {

	/**
	 * Unique id for this class.
	 */
	private static final long	serialVersionUID	= 1986841304565960296L;

	public StudentLevelFeeTreeNode() {
		super();
	}

	public StudentLevelFeeTreeNode(final Object data, final TreeNode parent) {
		super(data, parent);

	}

	public StudentLevelFeeTreeNode(final String type, final Object data, final TreeNode parent) {
		super(type, data, parent);
	}

	private StudentLevelFee	studentLevelFee;

	public StudentLevelFee getStudentLevelFee() {
		return this.studentLevelFee;
	}

	public void setStudentLevelFee(final StudentLevelFee studentLevelFee) {
		this.studentLevelFee = studentLevelFee;
	}

}
