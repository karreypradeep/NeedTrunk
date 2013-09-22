package com.apeironsol.need.core.portal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.core.model.BranchAssembly;
import com.apeironsol.need.core.model.BuildingBlock;
import com.apeironsol.need.core.service.BranchAssemblyService;
import com.apeironsol.need.core.service.BuildingBlockService;
import com.apeironsol.need.util.comparator.BuildingBlockComparator;
import com.apeironsol.need.util.constants.BranchTabConstant;
import com.apeironsol.need.util.constants.BuildingBlockConstant;
import com.apeironsol.need.util.portal.BuildingBlockTreeNode;
import com.apeironsol.need.util.portal.ViewExceptionHandler;
import com.apeironsol.need.util.portal.ViewUtil;
import com.apeironsol.framework.exception.ApplicationException;

@Named
@Scope(value = "session")
public class BranchAssemblyBean extends AbstractBranchBean {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long			serialVersionUID	= -3144616196583014772L;

	/**
	 * Logger for the class.
	 */
	private static final Logger			log					= Logger.getLogger(BranchAssemblyBean.class);

	/**
	 * Collection of all building blocks.
	 */
	private Collection<BuildingBlock>	buildingBlocks;

	/**
	 * Collection of tree nodes containing selected branch assemblies.
	 */
	private TreeNode[]					selectedAssemblies;

	/**
	 * Collection of branch assemblies for a branch.
	 */
	private Collection<BranchAssembly>	branchAssemblies;

	/**
	 * Branch assembly service.
	 */
	@Resource
	private BranchAssemblyService		branchAssemblyService;

	/**
	 * Root node for building blocks tree.
	 */
	private TreeNode					buildingBlocksRoot;

	/**
	 * Branch service resource.
	 */
	@Resource
	BranchBean							branchBean;

	/**
	 * Branch service resource.
	 */
	@Resource
	private SessionBean					sessionBean;

	@Resource
	private BuildingBlockService		buildingBlockService;

	private Collection<BuildingBlock>	branchBuildingBlocks;

	private boolean						loadBuildingBlocksFlag;

	@PostConstruct
	public void init() {
		this.branchBuildingBlocks = new ArrayList<BuildingBlock>();
	}

	/**
	 * Returns the branch object.
	 * 
	 * @return the branch object.
	 */
	public Branch getBranch() {
		return this.sessionBean.getCurrentBranch();
	}

	/**
	 * Returns all branch assemblies present.
	 * 
	 * @return all branch assemblies present.
	 */
	public Collection<BranchAssembly> getAllBranchAssemblies() {
		return this.branchAssemblies;
	}

	/**
	 * Returns all the building blocks available.
	 * 
	 * @return all the building blocks available.
	 */
	public Collection<BuildingBlock> getAllBuildingBlocks() {
		return this.buildingBlocks;
	}

	/**
	 * Returns the building block by id from already existing building block
	 * array.
	 * 
	 * @param id
	 *            the id of the building block.
	 * @return the building block.
	 */
	public Collection<BuildingBlock> getBuildingBlocksByType(final BuildingBlockConstant type) {
		Collection<BuildingBlock> result = new ArrayList<BuildingBlock>();
		for (BuildingBlock buildingBlock : this.buildingBlocks) {
			if (buildingBlock.getType().equals(type)) {
				result.add(buildingBlock);
			}
		}
		return result;
	}

	/**
	 * Returns the root node for building block tree.
	 * 
	 * @return
	 */
	public TreeNode getBuildingBlocksRoot() {
		return this.buildingBlocksRoot;
	}

	/**
	 * Returns the selected building block assemblies as tree nodes.
	 * 
	 * @return the selected building block assemblies as tree nodes.
	 */
	public TreeNode[] getSelectedAssemblies() {
		return this.selectedAssemblies;
	}

	/**
	 * Sets the selected building block assemblies as tree nodes.
	 * 
	 * @param selectedAssemblies
	 *            the selected building block assemblies as tree nodes.
	 */
	public void setSelectedAssemblies(final TreeNode[] selectedAssemblies) {
		this.selectedAssemblies = selectedAssemblies;
	}

	/**
	 * Saves all the branch assemblies selected. If the branch assembly is
	 * already present then it is not added again. If the already present one is
	 * deleted then it is deleted.
	 * 
	 * @return
	 */
	public void saveBranchAssemblies() {
		try {
			Collection<BuildingBlock> selectedBuildingBlocks = new ArrayList<BuildingBlock>();
			for (TreeNode treeNode : this.selectedAssemblies) {
				if (treeNode instanceof BuildingBlockTreeNode) {
					BuildingBlockTreeNode buildingBlockTreeNode = (BuildingBlockTreeNode) treeNode;
					BuildingBlock buildingBlock = buildingBlockTreeNode.getBuildingBlock();
					selectedBuildingBlocks.add(buildingBlock);
				}
			}
			if (!selectedBuildingBlocks.isEmpty()) {
				this.branchAssemblyService.createOrRemoveBranchAssemblies(this.getBranch(), selectedBuildingBlocks);
				this.sessionBean.setLoadBranchTreeFromDatabase(true);
				this.sessionBean.setLoadBranchBuildingBlockTreeFromDatabase(true);
			}

			ViewUtil.addMessage("Builiding blocks assembled/un-assembled to the branch sucessfully.", FacesMessage.SEVERITY_INFO);

		} catch (Exception ex) {
			log.info(ex.getMessage());
			this.sessionBean.setLoadBranchBuildingBlockTreeFromDatabase(true);
			ViewUtil.addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}

	/**
	 * Appends the building block tree to the root node. If a assembly is
	 * present then the tree node will be selected.
	 * 
	 * @param root
	 *            the root node of the tree.
	 */
	public void prepareBuildingBlockTree(final TreeNode root) {

		// For each of building block type which can be coupled to branch.
		for (BuildingBlockConstant buildingBlockType : BuildingBlockConstant.getAllSortedBuildingBlocksForBranchAssemblies()) {

			TreeNode buildingBlockParentNode = new DefaultTreeNode(buildingBlockType.getLabel(), root);

			// For all the building blocks.
			if (this.buildingBlocks != null) {

				List<BuildingBlock> sortedBuildingBlocks = new ArrayList<BuildingBlock>(this.buildingBlocks);
				Collections.sort(sortedBuildingBlocks, new BuildingBlockComparator(BuildingBlockComparator.NAME));

				for (BuildingBlock buildingBlock : sortedBuildingBlocks) {

					// For each building block type build a child tree.
					if (buildingBlockType.equals(buildingBlock.getType())) {

						// Create building block node.
						TreeNode buildingBlockNode = new BuildingBlockTreeNode(buildingBlock.getName(), buildingBlock, buildingBlockParentNode);

						// if building block is already assembled to branch,
						// mark as selected.
						if (this.branchBuildingBlocks.contains(buildingBlock)) {
							buildingBlockNode.setSelected(Boolean.TRUE);
							buildingBlockParentNode.setExpanded(Boolean.FALSE);
						}
					}
				}
			}
		}
	}

	/**
	 * Prepares a map containing building block id and its corresponding branch
	 * assembly.
	 */
	public void prepareSelectedBuildingBlocksMap() {
		try {
			if (this.branchBean.getBranch() != null && this.branchBean.getActiveTabIndex() == BranchTabConstant.ASSEMBLY.getTabIndex()) {
				if (this.sessionBean.isLoadBranchBuildingBlockTreeFromDatabase()) {

					this.branchBuildingBlocks = this.buildingBlockService.findBuildingBlocksbyBranchId(this.branchBean.getBranch().getId());

					// Prepare buildings blocks tree
					this.buildingBlocksRoot = new DefaultTreeNode("Root", null);
					this.prepareBuildingBlockTree(this.buildingBlocksRoot);
					this.sessionBean.setLoadBranchBuildingBlockTreeFromDatabase(false);
				}
			}
		} catch (ApplicationException ex) {
			log.info(ex.getMessage());
			ViewExceptionHandler.handle(ex);
		}
	}

	public void loadBuldingBlocksFromDatabase() {
		try {

			if (this.loadBuildingBlocksFlag) {
				this.buildingBlocks = this.buildingBlockService.findAllBuildingBlocks();
				this.loadBuildingBlocksFlag = false;
			}
		} catch (ApplicationException ex) {
			log.info(ex.getMessage());
			ViewExceptionHandler.handle(ex);
		}
	}

	public boolean isLoadBuildingBlocksFlag() {
		return this.loadBuildingBlocksFlag;
	}

	public void setLoadBuildingBlocksFlag(final boolean loadBuildingBlocksFlag) {
		this.loadBuildingBlocksFlag = loadBuildingBlocksFlag;
	}

}
