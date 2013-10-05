/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 * 
 */
package com.apeironsol.need.financial.portal.organization;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.financial.model.StudentFeeTransaction;
import com.apeironsol.need.financial.service.StudentFinancialService;
import com.apeironsol.need.util.portal.StudentFeeTransactionTreeNode;
import com.apeironsol.need.util.portal.ViewUtil;
import com.apeironsol.need.util.searchcriteria.FeeCollectedSearchCriteria;

/**
 * JSF managed for financial income.
 * 
 * @author Pradeep
 */
@Named
@Scope(value = "session")
public class OrganizationFinanceFeesCollectedBean extends OrganizationFinancialFeeBean {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long								serialVersionUID			= -3943056331375905352L;

	@Resource
	private StudentFinancialService							studentFinancialService;

	private FeeCollectedSearchCriteria						feeCollectedSearchCriteria	= new FeeCollectedSearchCriteria();

	/**
	 * Indicator to load income types from database.
	 */
	private boolean											buildTree;

	/**
	 * Root node for tree.
	 */
	private final TreeNode									root						= new DefaultTreeNode("Fee collected", null);

	/**
	 * Purchase order.
	 */
	private Map<Branch, Collection<StudentFeeTransaction>>	studentFeeTransactionsByBranch;

	/**
	 * @return the feeCollectedSearchCriteria
	 */
	public FeeCollectedSearchCriteria getFeeCollectedSearchCriteria() {
		return this.feeCollectedSearchCriteria;
	}

	public void searchFeesCollectedBySearchCriteria() {
		if (this.studentFeeTransactionsByBranch == null) {
			this.studentFeeTransactionsByBranch = new HashMap<Branch, Collection<StudentFeeTransaction>>();
		} else {
			this.studentFeeTransactionsByBranch.clear();
		}

		for (Branch activeBranch : this.getActiveBranches()) {
			this.feeCollectedSearchCriteria.setBranch(activeBranch);
			this.studentFeeTransactionsByBranch.put(activeBranch,
					this.studentFinancialService.findFeesCollectedBySearchCriteria(this.feeCollectedSearchCriteria));
		}
		this.buildTree = true;
		this.buildTreeForBranchFeesCollected(this.studentFeeTransactionsByBranch);
		if (this.studentFeeTransactionsByBranch == null || this.studentFeeTransactionsByBranch.isEmpty()) {
			ViewUtil.addMessage("No fee collected records found for entered search criteria..", FacesMessage.SEVERITY_INFO);
		}
	}

	/**
	 * @param feeCollectedSearchCriteria
	 *            the feeCollectedSearchCriteria to set
	 */
	public void setFeeCollectedSearchCriteria(final FeeCollectedSearchCriteria feeCollectedSearchCriteria) {
		this.feeCollectedSearchCriteria = feeCollectedSearchCriteria;
	}

	public String resetSearchCriteria() {
		this.feeCollectedSearchCriteria.resetSeacrhCriteria();
		return null;
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

	/**
	 * @return the root
	 */
	public TreeNode getRoot() {
		return this.root;
	}

	/**
	 * Builds tree for branch incomes.
	 */
	public void buildTreeForBranchFeesCollected(final Map<Branch, Collection<StudentFeeTransaction>> studentFeeTransactionsByBranch) {
		if (this.isBuildTree()) {
			this.removeAllChildsOfRootNode(this.root);
			for (Map.Entry<Branch, Collection<StudentFeeTransaction>> entry : studentFeeTransactionsByBranch.entrySet()) {
				StudentFeeTransactionTreeNode branchStudentFeeTransaction = new StudentFeeTransactionTreeNode();
				branchStudentFeeTransaction.setName(entry.getKey().getName());
				TreeNode branchTreeNode = new DefaultTreeNode(branchStudentFeeTransaction, this.root);
				branchTreeNode.setExpanded(false);
				for (StudentFeeTransaction studentFeeTransaction : entry.getValue()) {
					StudentFeeTransactionTreeNode studentFeeTransactionNode = new StudentFeeTransactionTreeNode();
					studentFeeTransactionNode.setName(studentFeeTransaction.getStudentAcademicYear().getStudent().getDisplayName());
					studentFeeTransactionNode.setAmount(studentFeeTransaction.getAmount());
					studentFeeTransactionNode.setAdmissionNumber(studentFeeTransaction.getStudentAcademicYear().getStudent().getAdmissionNr());
					studentFeeTransactionNode.setTransactionNr(studentFeeTransaction.getTransactionNr());
					studentFeeTransactionNode.setTransactionDate(studentFeeTransaction.getTransactionDate());
					studentFeeTransactionNode.setUserName(studentFeeTransaction.getAuditUsername());
					TreeNode studentFeeTransactionTreeNode = new DefaultTreeNode(studentFeeTransactionNode, branchTreeNode);
				}
			}
			this.buildTree = false;
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
