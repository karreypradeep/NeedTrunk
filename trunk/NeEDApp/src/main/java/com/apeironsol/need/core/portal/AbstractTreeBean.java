package com.apeironsol.need.core.portal;

import org.primefaces.model.TreeNode;

public abstract class AbstractTreeBean extends AbstractPortalBean {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long	serialVersionUID	= 788557280043600258L;

	protected void updateNodeForSelected(final TreeNode treeNode, final TreeNode selectedTreeNode) {
		if (selectedTreeNode != null && treeNode != null) {
			if (treeNode == selectedTreeNode) {
				selectedTreeNode.setSelected(true);
			} else {
				treeNode.setSelected(false);
			}

			if (treeNode.getChildCount() != 0) {
				for (TreeNode child : treeNode.getChildren()) {
					this.updateNodeForSelected(child, selectedTreeNode);
				}

			}
		}
	}

}
