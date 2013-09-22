/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.util.portal;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.apeironsol.need.core.model.BuildingBlock;

public class BuildingBlockTreeNode extends DefaultTreeNode {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 5902384961486280696L;

	private BuildingBlock		buildingBlock;

	public BuildingBlockTreeNode(final String display, final BuildingBlock buildingBlock, final TreeNode root) {
		super(display, root);
		this.buildingBlock = buildingBlock;
	}

	public BuildingBlock getBuildingBlock() {
		return this.buildingBlock;
	}

	public void setBuildingBlock(final BuildingBlock buildingBlock) {
		this.buildingBlock = buildingBlock;
	}

}
