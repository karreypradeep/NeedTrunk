/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 * 
 */
package com.apeironsol.need.financial.portal;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.core.portal.AbstractTabbedBean;
import com.apeironsol.need.financial.service.BranchFinancialService;
import com.apeironsol.need.util.dataobject.BranchFinancialDO;
import com.apeironsol.need.util.dataobject.ClassFinancialDO;
import com.apeironsol.need.util.dataobject.SectionFinancialDO;
import com.apeironsol.need.util.portal.BranchFeeCollectedTreeNode;
import com.apeironsol.need.util.searchcriteria.FeeDueSearchCriteria;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;

/**
 * JSF managed for financial income.
 * 
 * @author Pradeep
 */
@Named
@Scope(value = "session")
public class FinanceFeesCollectedDueDetailsBean extends AbstractTabbedBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long		serialVersionUID		= -3943056331375905352L;

	@Resource
	private BranchFinancialService	branchFinancialService;

	private FeeDueSearchCriteria	feeDueSearchCriteria	= null;

	private BranchFinancialDO		branchFinancialDO;

	/**
	 * Indicator to load income types from database.
	 */
	private boolean					buildTree;

	/**
	 * Root node for tree.
	 */
	private final TreeNode			root					= new DefaultTreeNode("Fee collected and due", null);

	private Collection<Section>		sectionsForSearhCriteriaByKlass;

	@PostConstruct
	public void init() {
		this.initializeSearchCriteria();
	}

	public void initializeSearchCriteria() {
		if (this.getFeeDueSearchCriteria() == null) {
			this.setFeeDueSearchCriteria(new FeeDueSearchCriteria(this.sessionBean.getCurrentBranch()));
		}
	}

	@Override
	public void onTabChange() {
		this.setViewOrNewAction(false);
	}

	/**
	 * @return the feeCollectedSearchCriteria
	 */
	public FeeDueSearchCriteria getFeeDueSearchCriteria() {
		return this.feeDueSearchCriteria;
	}

	public void searchFeesDueBySearchCriteria() {
		this.feeDueSearchCriteria.setBranch(this.sessionBean.getCurrentBranch());
		this.branchFinancialDO = this.branchFinancialService.getBranchFeeFinancialDetailsBySearchCriteria(this.feeDueSearchCriteria);
		this.setBuildTree(true);
		this.buildTreeForBranchExpesnses();
	}

	/**
	 * @param feeCollectedSearchCriteria
	 *            the feeCollectedSearchCriteria to set
	 */
	public void setFeeDueSearchCriteria(final FeeDueSearchCriteria feeDueSearchCriteria) {
		this.feeDueSearchCriteria = feeDueSearchCriteria;
	}

	public String resetSearchCriteria() {
		this.feeDueSearchCriteria.resetSeacrhCriteria();
		return null;
	}

	public void preProcessPDF(final Object document) throws IOException, BadElementException, DocumentException {
		Document pdf = (Document) document;
		pdf.open();
		pdf.setPageSize(PageSize.A4);
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		try {
			String logo = servletContext.getRealPath("") + File.separator + "images" + File.separator + "school_logo.png";
			pdf.add(Image.getInstance(logo));
		} catch (IOException e) {
			// TODO: handle exception
		}
		pdf.addTitle(this.sessionBean.getCurrentBranch().getName());
		pdf.addHeader("Header", "Fee collected");
	}

	public void onChangeAcademicYear() {
		this.feeDueSearchCriteria.setDueDate(null);
		this.handleFromKlassChange();
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
	public void buildTreeForBranchExpesnses() {
		if (this.isBuildTree() && this.branchFinancialDO != null) {
			this.removeAllChildsOfRootNode(this.root);
			BranchFeeCollectedTreeNode branchFeeCollectedTreeNode = new BranchFeeCollectedTreeNode();
			branchFeeCollectedTreeNode.setName(this.branchFinancialDO.getName());
			TreeNode branchTreeNode = new DefaultTreeNode(branchFeeCollectedTreeNode, this.root);
			branchFeeCollectedTreeNode.setTotalFee(this.branchFinancialDO.getTotalFeeExpected());
			branchFeeCollectedTreeNode.setFeePaid(this.branchFinancialDO.getTotalFeeCollectedAmount());
			branchFeeCollectedTreeNode.setFeeWaived(this.branchFinancialDO.getTotalFeeDeductedAmount());
			branchFeeCollectedTreeNode.setNetfeeExpected(this.branchFinancialDO.getNetFee());
			branchFeeCollectedTreeNode.setFeeDue(this.branchFinancialDO.getNetFeeDue());
			branchTreeNode.setExpanded(true);
			for (ClassFinancialDO classFinancialDO : this.branchFinancialDO.getClassFinancialDOs()) {
				BranchFeeCollectedTreeNode classFeeCollectedTreeNode = new BranchFeeCollectedTreeNode();
				classFeeCollectedTreeNode.setName(classFinancialDO.getName());
				TreeNode classTreeNode = new DefaultTreeNode(classFeeCollectedTreeNode, branchTreeNode);
				classFeeCollectedTreeNode.setTotalFee(classFinancialDO.getTotalFeeExpected());
				classFeeCollectedTreeNode.setFeePaid(classFinancialDO.getTotalFeeCollectedAmount());
				classFeeCollectedTreeNode.setFeeWaived(classFinancialDO.getTotalFeeDeductedAmount());
				classFeeCollectedTreeNode.setNetfeeExpected(classFinancialDO.getNetFee());
				classFeeCollectedTreeNode.setFeeDue(classFinancialDO.getNetFeeDue());
				classTreeNode.setExpanded(false);
				for (SectionFinancialDO sectionFinancialDO : classFinancialDO.getSectionFinancialDOs()) {
					BranchFeeCollectedTreeNode sectionFeeCollectedTreeNode = new BranchFeeCollectedTreeNode();
					sectionFeeCollectedTreeNode.setName(sectionFinancialDO.getName());
					TreeNode sectionTreeNode = new DefaultTreeNode(sectionFeeCollectedTreeNode, classTreeNode);
					sectionFeeCollectedTreeNode.setTotalFee(sectionFinancialDO.getTotalFeeExpected());
					sectionFeeCollectedTreeNode.setFeePaid(sectionFinancialDO.getTotalFeeCollectedAmount());
					sectionFeeCollectedTreeNode.setFeeWaived(sectionFinancialDO.getTotalFeeDeductedAmount());
					sectionFeeCollectedTreeNode.setNetfeeExpected(sectionFinancialDO.getNetFee());
					sectionFeeCollectedTreeNode.setFeeDue(sectionFinancialDO.getNetFeeDue());
					sectionTreeNode.setExpanded(false);
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

	/**
	 * @return the branchFinancialDO
	 */
	public BranchFinancialDO getBranchFinancialDO() {
		return this.branchFinancialDO;
	}

	/**
	 * @param branchFinancialDO
	 *            the branchFinancialDO to set
	 */
	public void setBranchFinancialDO(final BranchFinancialDO branchFinancialDO) {
		this.branchFinancialDO = branchFinancialDO;
	}

	/**
	 * @return the sectionsForSearhCriteriaByKlass
	 */
	public Collection<Section> getSectionsForSearhCriteriaByKlass() {
		return this.sectionsForSearhCriteriaByKlass;
	}

	/**
	 * @param sectionsForSearhCriteriaByKlass
	 *            the sectionsForSearhCriteriaByKlass to set
	 */
	public void setSectionsForSearhCriteriaByKlass(final Collection<Section> sectionsForSearhCriteriaByKlass) {
		this.sectionsForSearhCriteriaByKlass = sectionsForSearhCriteriaByKlass;
	}

	public void handleFromKlassChange() {
		this.feeDueSearchCriteria.setSection(null);
		if (this.feeDueSearchCriteria.getAcademicYear() != null && this.feeDueSearchCriteria.getKlass() != null) {
			this.setSectionsForSearhCriteriaByKlass(this.sectionService.findActiveSectionsByKlassIdAndAcademicYearId(this.feeDueSearchCriteria.getKlass()
					.getId(), this.feeDueSearchCriteria.getAcademicYear().getId()));
		}
	}

}
