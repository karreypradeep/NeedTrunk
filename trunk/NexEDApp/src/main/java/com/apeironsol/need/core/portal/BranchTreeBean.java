/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.portal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.core.model.BranchAssembly;
import com.apeironsol.need.core.model.BuildingBlock;
import com.apeironsol.need.core.portal.util.ViewContentHandlerBean;
import com.apeironsol.need.core.service.BranchAssemblyService;
import com.apeironsol.need.core.service.BuildingBlockService;
import com.apeironsol.need.util.comparator.BuildingBlockComparator;
import com.apeironsol.need.util.constants.BuildingBlockConstant;
import com.apeironsol.need.util.constants.ViewContentConstant;
import com.apeironsol.need.util.portal.BuildingBlockTreeNode;
import com.apeironsol.need.util.portal.ViewExceptionHandler;
import com.apeironsol.framework.exception.ApplicationException;

@Named
@Scope(value = "session")
public class BranchTreeBean extends AbstractBranchBean {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long		serialVersionUID	= 8427995336485530003L;

	/**
	 * Logger for the class.
	 */
	private static final Logger							log					= Logger.getLogger(BranchTreeBean.class);

	/**
	 * Branch service resource.
	 */
	@Resource
	BranchBean						branchBean;

	@Resource
	BranchAssemblyBean				branchAssemblyBean;

	@Resource
	BranchFeeTypePeriodicalBean		branchFeeBean;

	@Resource
	BranchExpenseTypePeriodicalBean	branchExpenseBean;

	@Resource
	BranchDepartmentBean			branchDepartmentBean;

	@Resource
	private BuildingBlockService	buildingBlockService;

	@Resource
	private BranchAssemblyService	branchAssemblyService;

	@Resource
	private ViewContentHandlerBean	viewContentHandlerBean;

	/**
	 * Branch service resource.
	 */
	@Resource
	private SessionBean				sessionBean;

	/**
	 * Root node for branch tree.
	 */
	private TreeNode				branchRootNode;

	/**
	 * Node for representing branch name.
	 */
	private TreeNode				branchNameNode;

	/**
	 * Flag to display branch tree
	 */
	private boolean					displayBranchTree;

	/**
	 * Returns the branch object.
	 * 
	 * @return the branch object.
	 */
	public Branch getBranch() {
		return this.sessionBean.getCurrentBranch();
	}

	/**
	 * Returns the root node for branch tree displayed.
	 * 
	 * @return the root node for branch tree displayed.
	 */
	public TreeNode getBranchRoot() {
		return this.branchRootNode;
	}

	/**
	 * This method is called on selection of node in branch tree.
	 * 
	 * @param event
	 *            node select event.
	 */
	public void onNodeSelect(final NodeSelectEvent event) {
		try {
			// Recursive check of selected.
			this.updateNodeForSelected(this.branchNameNode, event.getTreeNode());

			if (event.getTreeNode() != null && event.getTreeNode() instanceof BuildingBlockTreeNode) {
				BuildingBlockTreeNode treeNode = (BuildingBlockTreeNode) event.getTreeNode();

				if (treeNode.getBuildingBlock() != null) {
					BuildingBlock selectedBuildingBlock = treeNode.getBuildingBlock();
					if (BuildingBlockConstant.FEE_TYPE.equals(selectedBuildingBlock.getType())) {

						BranchAssembly branchAssembly = this.branchAssemblyService
								.findBranchAssemblyByBuildingBlockIdAndBranch(selectedBuildingBlock, this.getBranch());
						this.branchFeeBean.setBranchAssembly(branchAssembly);
						this.branchFeeBean.setBuildingBlock(selectedBuildingBlock);
						this.branchFeeBean.setLoadBranchFeeTypesFromDB(true);
						this.branchFeeBean.setDisplayNewBranchFeeType(false);

						// NavigationHandler navigationHandler =
						// ViewUtil.getFacesContext().getApplication()
						// .getNavigationHandler();
						// navigationHandler.handleNavigation(ViewUtil.getFacesContext(),
						// null,
						// ViewPathConstants.ORGANIZATION_BRANCH_FEE_TYPE_PERIODICAS);

					} else if (BuildingBlockConstant.EXPENSE_TYPE.equals(selectedBuildingBlock.getType())) {

						BranchAssembly branchAssembly = this.branchAssemblyService
								.findBranchAssemblyByBuildingBlockIdAndBranch(selectedBuildingBlock, this.getBranch());
						this.branchExpenseBean.setBranchAssembly(branchAssembly);
						this.branchExpenseBean.setBuildingBlock(selectedBuildingBlock);
						this.branchExpenseBean.setLoadBranchExpenseTypesFromDB(true);

						// NavigationHandler navigationHandler =
						// ViewUtil.getFacesContext().getApplication()
						// .getNavigationHandler();
						// navigationHandler.handleNavigation(ViewUtil.getFacesContext(),
						// null,
						// ViewPathConstants.ORGANIZATION_BRANCH_EXPENSE_TYPE_PERIODICALS);
					} else if (BuildingBlockConstant.DEPARTMENT.equals(selectedBuildingBlock.getType())) {
						BranchAssembly branchAssembly = this.branchAssemblyService
								.findBranchAssemblyByBuildingBlockIdAndBranch(selectedBuildingBlock, this.getBranch());
						this.branchDepartmentBean.setBranchAssembly(branchAssembly);
						this.branchDepartmentBean.setBuildingBlock(selectedBuildingBlock);
						this.branchDepartmentBean.setLoadBranchFeeTypesFromDB(true);
						// NavigationHandler navigationHandler =
						// ViewUtil.getFacesContext().getApplication()
						// .getNavigationHandler();
						// navigationHandler.handleNavigation(ViewUtil.getFacesContext(),
						// null,
						// ViewPathConstants.ORGANIZATION_BRANCH_DEPARTMENTS);
					}
				}
			} else {
				this.branchBean.setActiveTabIndex(0);
				this.viewContentHandlerBean.setCurrentViewContent(ViewContentConstant.ORGANIZATION_BRANCHES);
				this.branchBean.setViewOrNewAction(true);

			}

		} catch (ApplicationException exception) {
			log.info(exception.getMessage());
			ViewExceptionHandler.handle(exception);
		}
	}

	private void updateNodeForSelected(final TreeNode treeNode, final TreeNode selectedTreeNode) {
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

	private void prepareBranchTree(final TreeNode branchTreeNode) {

		// For each of building block type which can be coupled to branch.
		for (BuildingBlockConstant buildingBlockType : BuildingBlockConstant
				.getAllSortedBuildingBlocksForBranchAssemblies()) {

			Collection<BuildingBlock> branchBuildBlocksByType = null;
			try {
				branchBuildBlocksByType = this.buildingBlockService.findBuildingBlocksbyBranchIdAndBuildingBlockType(
						this.getBranch().getId(), buildingBlockType);
			} catch (ApplicationException ex) {
				log.info(ex.getMessage());
				ViewExceptionHandler.handle(ex);
			}

			if (branchBuildBlocksByType != null && !branchBuildBlocksByType.isEmpty()) {
				TreeNode buildingBlockTypeNode = new DefaultTreeNode(buildingBlockType.getLabel(), branchTreeNode);
				buildingBlockTypeNode.setExpanded(true);
				buildingBlockTypeNode.setSelectable(false);
				List<BuildingBlock> sortedBuildingBlocks = new ArrayList<BuildingBlock>(branchBuildBlocksByType);
				Collections.sort(sortedBuildingBlocks, new BuildingBlockComparator(BuildingBlockComparator.NAME));

				for (BuildingBlock buildingBlock : sortedBuildingBlocks) {

					BuildingBlockTreeNode buildingBlockTreeNode = new BuildingBlockTreeNode(buildingBlock.getName(),
							buildingBlock, buildingBlockTypeNode);
					buildingBlockTreeNode.setSelectable(false);
				}

			}

		}
	}

	/**
	 * Returns true if branch tree has to be rendered.
	 * 
	 * @return true if branch tree has to be rendered.
	 */
	public boolean isDisplayBranchTree() {
		if (ViewContentConstant.ORGANIZATION_BRANCHES.equals(this.viewContentHandlerBean.getCurrentViewContent())
				&& this.branchBean.getBranch() != null && this.branchBean.getBranch().getId() != null
				&& this.branchBean.isViewOrNewAction()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns true if branch tree has to be rendered.
	 * 
	 * @return true if branch tree has to be rendered.
	 */
	public void setDisplayBranchTree(final boolean showBranchTree) {
		this.displayBranchTree = showBranchTree;
	}

	/**
	 * Display branch tree.
	 * 
	 */
	public void showBranchTree() {
		this.displayBranchTree = true;
	}

	/**
	 * Display branch tree.
	 * 
	 */
	public void hideBranchTree() {
		this.displayBranchTree = false;
	}

	/**
	 * Load branch tree.
	 */
	public void loadBranchTree() {
		if (this.displayBranchTree) {
			if (this.sessionBean.isLoadBranchTreeFromDatabase()) {
				this.branchRootNode = new DefaultTreeNode("Root", null);
				this.branchRootNode.setExpanded(Boolean.TRUE);
				this.removeAllChildsOfRootNode(this.branchRootNode);
				this.branchNameNode = new DefaultTreeNode(this.getBranch().getName(), this.branchRootNode);
				this.branchNameNode.setExpanded(Boolean.TRUE);
				this.branchNameNode.setSelected(true);

				// Prepare branch tree
				this.prepareBranchTree(this.branchNameNode);
				this.sessionBean.setLoadBranchTreeFromDatabase(false);
			}
		}
	}

	/**
	 * Removes all child nodes of the supplied root node.
	 */
	private void removeAllChildsOfRootNode(final TreeNode rootNode) {
		if (rootNode != null && rootNode.getChildCount() > 0) {
			TreeNode[] array = rootNode.getChildren().toArray(new TreeNode[rootNode.getChildCount()]);
			for (TreeNode child : array) {
				child.setParent(null);
				child = null;
			}
		}
	}

}
