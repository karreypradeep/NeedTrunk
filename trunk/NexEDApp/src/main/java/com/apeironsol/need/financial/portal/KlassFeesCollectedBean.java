/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 * 
 */
package com.apeironsol.need.financial.portal;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Named;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.portal.AbstractTabbedBean;
import com.apeironsol.need.core.portal.KlassBean;
import com.apeironsol.need.financial.service.ClassFinancialService;
import com.apeironsol.need.util.dataobject.ClassFinancialDO;
import com.apeironsol.need.util.dataobject.SectionFinancialDO;
import com.apeironsol.need.util.portal.BranchFeeCollectedTreeNode;
import com.apeironsol.need.util.searchcriteria.FeeDueSearchCriteria;

/**
 * JSF managed for financial income.
 * 
 * @author Pradeep
 */
@Named
@Scope(value = "session")
public class KlassFeesCollectedBean extends AbstractTabbedBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long		serialVersionUID		= -3943056331375905352L;

	/**
	 * Indicator to load income types from database.
	 */
	private boolean					buildTree;

	/**
	 * Root node for tree.
	 */
	private final TreeNode			root					= new DefaultTreeNode("incomes", null);

	@Resource
	private ClassFinancialService	classFinancialService;

	private ClassFinancialDO		classFinancialDO;

	@Resource
	private KlassBean				klassBean;

	private FeeDueSearchCriteria	feeDueSearchCriteria	= null;

	@PostConstruct
	public void init() {
		this.initializeSearchCriteria();
	}

	public void initializeSearchCriteria() {
		if (this.getFeeDueSearchCriteria() == null) {
			this.setFeeDueSearchCriteria(new FeeDueSearchCriteria(this.sessionBean.getCurrentBranch()));
		}
	}

	/**
	 * @return the feeDueSearchCriteria
	 */
	public FeeDueSearchCriteria getFeeDueSearchCriteria() {
		return this.feeDueSearchCriteria;
	}

	/**
	 * @param feeDueSearchCriteria
	 *            the feeDueSearchCriteria to set
	 */
	public void setFeeDueSearchCriteria(final FeeDueSearchCriteria feeDueSearchCriteria) {
		this.feeDueSearchCriteria = feeDueSearchCriteria;
	}

	/**
	 * @return the buildTree
	 */
	public boolean isBuildTree() {
		return this.buildTree;
	}

	/**
	 * @param buildTree
	 *            the buildTree to set
	 */
	public void setBuildTree(final boolean buildTree) {
		this.buildTree = buildTree;
	}

	@Override
	public void onTabChange() {
		this.buildTree = false;
		this.removeAllChildsOfRootNode(this.root);
		this.setViewOrNewAction(false);
	}

	/**
	 * Builds tree for branch incomes.
	 */
	public void buildTreeForFeesCollected() {
		if (this.buildTree) {
			this.removeAllChildsOfRootNode(this.root);
			final BranchFeeCollectedTreeNode classFeeCollectedTreeNode = new BranchFeeCollectedTreeNode();
			classFeeCollectedTreeNode.setName(this.classFinancialDO.getName());
			final TreeNode classTreeNode = new DefaultTreeNode(classFeeCollectedTreeNode, this.root);
			classFeeCollectedTreeNode.setTotalFee(this.classFinancialDO.getTotalFeeExpected());
			classFeeCollectedTreeNode.setFeePaid(this.classFinancialDO.getTotalFeeCollectedAmount());
			classFeeCollectedTreeNode.setFeeWaived(this.classFinancialDO.getTotalFeeDeductedAmount());
			classFeeCollectedTreeNode.setNetfeeExpected(this.classFinancialDO.getNetFee());
			classFeeCollectedTreeNode.setFeeRefunded(this.classFinancialDO.getTotalFeeRefundAmount());
			classFeeCollectedTreeNode.setNetfeeDue(this.classFinancialDO.getNetFeeDue());
			classFeeCollectedTreeNode.setNetfeePaid(this.classFinancialDO.getNetFeePaid());
			classTreeNode.setExpanded(false);
			if (this.classFinancialDO.getSectionFinancialDOs() != null) {
				for (final SectionFinancialDO sectionFinancialDO : this.classFinancialDO.getSectionFinancialDOs()) {
					final BranchFeeCollectedTreeNode sectionFeeCollectedTreeNode = new BranchFeeCollectedTreeNode();
					sectionFeeCollectedTreeNode.setName(sectionFinancialDO.getName());
					final TreeNode sectionTreeNode = new DefaultTreeNode(sectionFeeCollectedTreeNode, classTreeNode);
					sectionFeeCollectedTreeNode.setTotalFee(sectionFinancialDO.getTotalFeeExpected());
					sectionFeeCollectedTreeNode.setFeePaid(sectionFinancialDO.getTotalFeeCollectedAmount());
					sectionFeeCollectedTreeNode.setFeeRefunded(sectionFinancialDO.getTotalFeeRefundAmount());
					sectionFeeCollectedTreeNode.setFeeWaived(sectionFinancialDO.getTotalFeeDeductedAmount());
					sectionFeeCollectedTreeNode.setNetfeeExpected(sectionFinancialDO.getNetFee());
					sectionFeeCollectedTreeNode.setFeeDue(sectionFinancialDO.getNetFeeDue());
					sectionFeeCollectedTreeNode.setNetfeeDue(sectionFinancialDO.getNetFeeDue());
					sectionFeeCollectedTreeNode.setNetfeePaid(sectionFinancialDO.getNetFeePaid());
					sectionTreeNode.setExpanded(false);
				}
			}
			this.buildTree = false;
		}
	}

	/**
	 * Return root.
	 * 
	 * @return
	 */
	public TreeNode getRoot() {
		return this.root;
	}

	/**
	 * Removes all child nodes of the supplied root node.
	 */
	private void removeAllChildsOfRootNode(final TreeNode rootNode) {
		if (rootNode != null && rootNode.getChildCount() > 0) {
			final TreeNode[] array = rootNode.getChildren().toArray(new TreeNode[rootNode.getChildCount()]);
			for (TreeNode child : array) {
				child.setParent(null);
				child = null;
			}
		}
	}

	public void searchFeesDueBySearchCriteria() {
		this.feeDueSearchCriteria.setBranch(this.sessionBean.getCurrentBranch());
		this.classFinancialDO = this.classFinancialService.getClassFeeFinancialDetailsByClassIdAndAcademicYearIdForDueDate(this.klassBean.getKlass().getId(),
				null, this.feeDueSearchCriteria.getAcademicYear().getId(), null, null);
		this.setBuildTree(true);
		this.buildTreeForFeesCollected();
	}

	public void onChangeAcademicYear() {
		this.feeDueSearchCriteria.setDueDate(null);
	}

	public String resetSearchCriteria() {
		this.feeDueSearchCriteria.resetSeacrhCriteria();
		return null;
	}

	/**
	 * @return the classFinancialDO
	 */
	public ClassFinancialDO getClassFinancialDO() {
		return this.classFinancialDO;
	}

	/**
	 * @param classFinancialDO
	 *            the classFinancialDO to set
	 */
	public void setClassFinancialDO(final ClassFinancialDO classFinancialDO) {
		this.classFinancialDO = classFinancialDO;
	}
}
