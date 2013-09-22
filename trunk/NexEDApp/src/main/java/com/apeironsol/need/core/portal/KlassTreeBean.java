/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.core.portal;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Named;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.BuildingBlock;
import com.apeironsol.need.core.portal.util.ViewContentHandlerBean;
import com.apeironsol.need.core.service.BuildingBlockService;
import com.apeironsol.need.financial.portal.KlassLevelFeeBean;
import com.apeironsol.need.util.comparator.BuildingBlockComparator;
import com.apeironsol.need.util.constants.BuildingBlockConstant;
import com.apeironsol.need.util.constants.FeeClassificationLevelConstant;
import com.apeironsol.need.util.constants.ViewContentConstant;
import com.apeironsol.need.util.portal.BuildingBlockTreeNode;
import com.apeironsol.need.util.portal.ViewExceptionHandler;
import com.apeironsol.framework.exception.ApplicationException;

/**
 * View klass tree class.
 * 
 * @author Pradeep
 */
@Named
@Scope("session")
public class KlassTreeBean extends AbstractTreeBean {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long		serialVersionUID			= -3222444061784140269L;

	@Resource
	private BuildingBlockService	buildingBlockService;

	@Resource
	private KlassBean				klassBean;

	@Resource
	KlassLevelFeeBean				klassLevelFeeBean;

	@Resource
	private SessionBean				sessionBean;

	@Resource
	private ViewContentHandlerBean	viewContentHandlerBean;

	private TreeNode				klassNameNode;

	private TreeNode				klassRootNode;

	private boolean					loadKlassTreeFromDatabase	= false;

	private TreeNode				feeTypeTreeNode;

	private TreeNode				selectedKlassNode;

	@PostConstruct
	public void init() {
		this.klassRootNode = new DefaultTreeNode("Root", null);
	}

	public TreeNode getKlassRoot() {
		return this.klassRootNode;
	}

	public TreeNode getSelectedKlassNode() {
		return this.selectedKlassNode;
	}

	public void setSelectedKlassNode(final TreeNode selectedKlassNode) {
		this.selectedKlassNode = selectedKlassNode;
	}

	public boolean isDisplayKlassTree() {
		if ((ViewContentConstant.BRANCH_KLASSES.equals(this.viewContentHandlerBean.getCurrentViewContent()) || ViewContentConstant.BRANCH_KLASS_FEE
				.equals(this.viewContentHandlerBean.getCurrentViewContent()))
				&& this.klassBean.getKlass() != null
				&& this.klassBean.getKlass().getId() != null
				&& this.klassBean.isViewOrNewAction()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isLoadKlassTreeFromDatabase() {
		return this.loadKlassTreeFromDatabase;
	}

	public void setLoadKlassTreeFromDatabase(final boolean loadKlassTreeFromDatabase) {
		this.loadKlassTreeFromDatabase = loadKlassTreeFromDatabase;
	}

	public void loadKlassTree() {
		if (this.loadKlassTreeFromDatabase) {
			if (this.sessionBean.getCurrentKlass() != null && this.sessionBean.getCurrentKlass().getId() != null) {
				Collection<BuildingBlock> branchFeeTypeBuildingBlocks = null;
				try {
					branchFeeTypeBuildingBlocks = this.buildingBlockService.findBuildingBlocksbyBranchIdAndBuildingBlockType(this.sessionBean
							.getCurrentBranch().getId(), BuildingBlockConstant.FEE_TYPE);
					Collections.sort((List<BuildingBlock>) branchFeeTypeBuildingBlocks, new BuildingBlockComparator(BuildingBlockComparator.NAME));
				} catch (ApplicationException ex) {
					ViewExceptionHandler.handle(ex);
				}

				// Some sorting login need to implemented.
				this.klassRootNode = new DefaultTreeNode("Root", null);
				this.klassRootNode.setExpanded(Boolean.TRUE);

				// Tree for class name node.
				this.klassNameNode = new DefaultTreeNode(this.sessionBean.getCurrentKlass().getName(), this.klassRootNode);
				this.klassNameNode.setSelected(true);

				this.klassNameNode.setExpanded(true);

				this.feeTypeTreeNode = new DefaultTreeNode("Fee", this.klassNameNode);
				this.feeTypeTreeNode.setExpanded(true);
				this.feeTypeTreeNode.setSelectable(true);
				if (branchFeeTypeBuildingBlocks != null) {
					for (BuildingBlock branchBuildingBlock : branchFeeTypeBuildingBlocks) {

						FeeClassificationLevelConstant feeClassificationLevelConstant = branchBuildingBlock.getFeeClassificationLevel();
						if (FeeClassificationLevelConstant.KLASS_LEVEL.equals(feeClassificationLevelConstant)) {
							new BuildingBlockTreeNode(branchBuildingBlock.getName(), branchBuildingBlock, this.feeTypeTreeNode);
						}

					}
				}
				this.loadKlassTreeFromDatabase = false;
			}
		}
	}

	public void onNodeSelect(final NodeSelectEvent event) {
		// Recursive search for selected node.
		this.updateNodeForSelected(this.klassRootNode, event.getTreeNode());

		if (event.getTreeNode() != null && event.getTreeNode() instanceof BuildingBlockTreeNode) {
			BuildingBlockTreeNode treeNode = (BuildingBlockTreeNode) event.getTreeNode();
			if (treeNode.getBuildingBlock() != null) {
				BuildingBlock buildingBlock = treeNode.getBuildingBlock();

				this.klassLevelFeeBean.setBuildingBlock(buildingBlock);
				this.klassLevelFeeBean.setLoadklassFee(true);
				this.klassLevelFeeBean.setDisplayNewKlassFee(false);
				this.viewContentHandlerBean.setCurrentViewContent(ViewContentConstant.BRANCH_KLASS_FEE);

			}
		} else if (event.getTreeNode() != null && event.getTreeNode() instanceof DefaultTreeNode && "Fee".equals(event.getTreeNode().getData())) {

			this.klassBean.setViewOrNewAction(true);
			this.klassBean.setActiveTabIndex(1);
			this.klassLevelFeeBean.setLoadKlassFeesForCurrentAcademicYearFlag(true);
			this.klassLevelFeeBean.onKlassFeeTabSelect();
			this.viewContentHandlerBean.setCurrentViewContent(ViewContentConstant.BRANCH_KLASSES);

		} else {
			this.klassBean.setActiveTabIndex(0);
			this.viewContentHandlerBean.setCurrentViewContent(ViewContentConstant.BRANCH_KLASSES);
			this.klassBean.setViewOrNewAction(true);
		}

	}
}
