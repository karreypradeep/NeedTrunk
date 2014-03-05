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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.AdmissionReservationFee;
import com.apeironsol.need.core.model.Klass;
import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.core.model.Student;
import com.apeironsol.need.core.portal.AbstractTabbedBean;
import com.apeironsol.need.core.service.AdmissionReservationFeeService;
import com.apeironsol.need.core.service.AdmissionService;
import com.apeironsol.need.util.constants.AdmissionStatusConstant;
import com.apeironsol.need.util.portal.NonAdmissionFeeCollectedTreeNode;
import com.apeironsol.need.util.searchcriteria.AdmissionSearchCriteria;
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
public class FinanceNonAdmissionsFeesCollectedBean extends AbstractTabbedBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long								serialVersionUID		= 7241614784725088204L;

	@Resource
	private AdmissionService								admissionService;

	@Resource
	private AdmissionReservationFeeService					admissionReservationFeeService;

	private AdmissionSearchCriteria							admissionSearchCriteria	= null;

	private Collection<Student>								admissions;

	private Map<Klass, Collection<AdmissionReservationFee>>	admissionsByAppliedClass;

	/**
	 * Indicator to load income types from database.
	 */
	private boolean											buildTree;

	/**
	 * Root node for tree.
	 */
	private final TreeNode									root					= new DefaultTreeNode("Fee collected", null);

	private Collection<Section>								sectionsForSearhCriteriaByKlass;

	@PostConstruct
	public void init() {
		this.initializeSearchCriteria();
	}

	public void initializeSearchCriteria() {
		if (this.getAdmissionSearchCriteria() == null) {
			this.setAdmissionSearchCriteria(new AdmissionSearchCriteria(this.sessionBean.getCurrentBranch()));
		}
	}

	@Override
	public void onTabChange() {
		this.setViewOrNewAction(false);
	}

	public void searchFeesDueBySearchCriteria() {
		this.admissionSearchCriteria.setBranch(this.sessionBean.getCurrentBranch());

		this.setAdmissions(this.admissionService.findAdmissionsBySearchCriteria(this.admissionSearchCriteria));
		this.setBuildTree(true);
		this.buildTreeForBranchExpesnses();
	}

	public String resetSearchCriteria() {
		this.admissionSearchCriteria.resetSeacrhCriteria();
		return null;
	}

	public void preProcessPDF(final Object document) throws IOException, BadElementException, DocumentException {
		final Document pdf = (Document) document;
		pdf.open();
		pdf.setPageSize(PageSize.A4);
		final ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		try {
			final String logo = servletContext.getRealPath("") + File.separator + "images" + File.separator + "school_logo.png";
			pdf.add(Image.getInstance(logo));
		} catch (final IOException e) {
			// TODO: handle exception
		}
		pdf.addTitle(this.sessionBean.getCurrentBranch().getName());
		pdf.addHeader("Header", "Fee collected");
	}

	public void onChangeAcademicYear() {

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
		if (this.isBuildTree() && (this.admissions != null)) {
			double branchLevelApplicationFormFee = 0, branchLevelReservationFormFee = 0;
			double classLevelApplicationFormFee = 0, classLevelReservationFormFee = 0;
			double studentLevelApplicationFormFee = 0, studentLevelReservationFormFee = 0;
			this.admissionsByAppliedClass = new HashMap<Klass, Collection<AdmissionReservationFee>>();
			final Collection<Long> studentIDs = new ArrayList<Long>();
			for (final Student student : this.admissions) {
				studentIDs.add(student.getId());
			}

			final Collection<AdmissionReservationFee> admissionReservationFees = this.admissionReservationFeeService
					.findAdmissionReservationFeeByStudentID(studentIDs);

			for (final AdmissionReservationFee admissionReservationFee : admissionReservationFees) {
				if (!AdmissionStatusConstant.ADMITTED.equals(admissionReservationFee.getStudent().getAdmissionStatus())) {
					if (this.admissionsByAppliedClass.get(admissionReservationFee.getStudent().getApplyingForKlass()) == null) {
						final Collection<AdmissionReservationFee> admissionReseFees = new ArrayList<AdmissionReservationFee>();
						admissionReseFees.add(admissionReservationFee);
						this.admissionsByAppliedClass.put(admissionReservationFee.getStudent().getApplyingForKlass(), admissionReseFees);
					} else {
						this.admissionsByAppliedClass.get(admissionReservationFee.getStudent().getApplyingForKlass()).add(admissionReservationFee);
					}
				}
			}
			this.removeAllChildsOfRootNode(this.root);
			final NonAdmissionFeeCollectedTreeNode branchFeeCollectedTreeNode = new NonAdmissionFeeCollectedTreeNode();
			branchFeeCollectedTreeNode.setName(this.sessionBean.getCurrentBranch().getName());
			final TreeNode branchTreeNode = new DefaultTreeNode(branchFeeCollectedTreeNode, this.root);
			branchFeeCollectedTreeNode.setApplicationFormFee(0);
			branchFeeCollectedTreeNode.setReservationFee(0);

			branchTreeNode.setExpanded(true);

			for (final Map.Entry<Klass, Collection<AdmissionReservationFee>> entry : this.admissionsByAppliedClass.entrySet()) {
				classLevelApplicationFormFee = 0;
				classLevelReservationFormFee = 0;

				final NonAdmissionFeeCollectedTreeNode classFeeCollectedTreeNode = new NonAdmissionFeeCollectedTreeNode();
				classFeeCollectedTreeNode.setName(entry.getKey().getName());
				final TreeNode classTreeNode = new DefaultTreeNode(classFeeCollectedTreeNode, branchTreeNode);
				classTreeNode.setExpanded(false);
				for (final AdmissionReservationFee admissionReservationFee : entry.getValue()) {
					final NonAdmissionFeeCollectedTreeNode sectionFeeCollectedTreeNode = new NonAdmissionFeeCollectedTreeNode();
					sectionFeeCollectedTreeNode.setName(admissionReservationFee.getStudent().getDisplayName());
					final TreeNode sectionTreeNode = new DefaultTreeNode(sectionFeeCollectedTreeNode, classTreeNode);
					studentLevelApplicationFormFee = admissionReservationFee.getApplicationFormFee() != null ? admissionReservationFee.getApplicationFormFee()
							: 0;
					studentLevelReservationFormFee = admissionReservationFee.getReservationFee() != null ? admissionReservationFee.getReservationFee() : 0;
					sectionFeeCollectedTreeNode.setApplicationFormFee(studentLevelApplicationFormFee);
					sectionFeeCollectedTreeNode.setReservationFee(studentLevelReservationFormFee);
					classLevelApplicationFormFee += studentLevelApplicationFormFee;
					classLevelReservationFormFee += studentLevelReservationFormFee;
					sectionTreeNode.setExpanded(false);
				}
				classFeeCollectedTreeNode.setApplicationFormFee(classLevelApplicationFormFee);
				classFeeCollectedTreeNode.setReservationFee(classLevelReservationFormFee);
				branchLevelApplicationFormFee += classLevelApplicationFormFee;
				branchLevelReservationFormFee += classLevelReservationFormFee;
			}
			branchFeeCollectedTreeNode.setApplicationFormFee(branchLevelApplicationFormFee);
			branchFeeCollectedTreeNode.setReservationFee(branchLevelReservationFormFee);
			this.buildTree = false;
		}
	}

	/**
	 * Removes all child nodes of the supplied root node.
	 */
	private void removeAllChildsOfRootNode(final TreeNode rootNode) {
		if ((rootNode != null) && (rootNode.getChildCount() > 0)) {
			final TreeNode[] array = rootNode.getChildren().toArray(new TreeNode[rootNode.getChildCount()]);
			for (TreeNode child : array) {
				child.setParent(null);
				child = null;
			}
		}
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

	}

	/**
	 * @return the admissionSearchCriteria
	 */
	public AdmissionSearchCriteria getAdmissionSearchCriteria() {
		return this.admissionSearchCriteria;
	}

	/**
	 * @param admissionSearchCriteria
	 *            the admissionSearchCriteria to set
	 */
	public void setAdmissionSearchCriteria(final AdmissionSearchCriteria admissionSearchCriteria) {
		this.admissionSearchCriteria = admissionSearchCriteria;
	}

	/**
	 * @return the admissions
	 */
	public Collection<Student> getAdmissions() {
		return this.admissions;
	}

	/**
	 * @param admissions
	 *            the admissions to set
	 */
	public void setAdmissions(final Collection<Student> admissions) {
		this.admissions = admissions;
	}

}
