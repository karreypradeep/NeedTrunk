package com.apeironsol.need.util.portal;

import java.io.Serializable;

import org.primefaces.model.TreeNode;

import com.apeironsol.need.financial.model.BranchLevelFee;

public class BranchLevelFeeTreeNode extends FeeTreeNode implements Serializable {

	/**
	 * Unique id for this class.
	 */
	private static final long	serialVersionUID	= 1986841304565960296L;

	public BranchLevelFeeTreeNode() {
		super();
	}

	public BranchLevelFeeTreeNode(final Object data, final TreeNode parent) {
		super(data, parent);

	}

	public BranchLevelFeeTreeNode(final String type, final Object data, final TreeNode parent) {
		super(type, data, parent);
	}

	private BranchLevelFee	branchLevelFee;

	public BranchLevelFee getBranchLevelFee() {
		return this.branchLevelFee;
	}

	public void setBranchLevelFee(final BranchLevelFee branchLevelFee) {
		this.branchLevelFee = branchLevelFee;
	}

}
