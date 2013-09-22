/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.core.portal;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Named;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.Student;
import com.apeironsol.need.core.model.StudentAcademicYear;
import com.apeironsol.need.core.portal.util.ViewContentHandlerBean;
import com.apeironsol.need.core.service.StudentAcademicYearService;
import com.apeironsol.need.util.constants.ViewContentConstant;
import com.apeironsol.need.util.portal.StudentAcademicYearTreeNode;

/**
 * View student tree class.
 * 
 * @author Pradeep
 */
@Named
@Scope("session")
public class StudentTreeBean extends AbstractTreeBean {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long			serialVersionUID	= 1765028927955102371L;

	@Resource
	private AbstractStudentBean					studentBean;

	@Resource
	private StudentAcademicYearService	serviceAcademicYearService;

	@Resource
	private ViewContentHandlerBean		viewContentHandlerBean;

	private TreeNode					studentNameNode;

	private TreeNode					studentRootNode;

	private boolean						loadStudentTreeFlag	= false;

	private TreeNode					selectedStudentNode;

	@PostConstruct
	public void init() {
		this.studentRootNode = new DefaultTreeNode("Root", null);
	}

	public TreeNode getStudentRoot() {
		return this.studentRootNode;
	}

	public TreeNode getSelectedStudentNode() {
		return this.selectedStudentNode;
	}

	public void setSelectedStudentNode(final TreeNode selectedStudentNode) {
		this.selectedStudentNode = selectedStudentNode;
	}

	public boolean isDisplayStudentTree() {
		if (ViewContentConstant.BRANCH_STUDENTS.equals(this.viewContentHandlerBean.getCurrentViewContent())
				&& this.studentBean.getStudent() != null && this.studentBean.getStudent().getId() != null
				&& this.studentBean.isViewAction()) {
			return true;
		} else {
			return false;
		}
	}

	public void loadStudentTree() {
		if (this.isLoadStudentTreeFlag()) {

			if (this.studentBean.getStudent() != null && this.studentBean.getStudent().getId() != null) {

				Student student = this.studentBean.getStudent();

				// Some sorting login need to implemented.
				this.studentRootNode = new DefaultTreeNode("Root", null);
				this.studentRootNode.setExpanded(Boolean.TRUE);

				// Tree for class name node.
				this.studentNameNode = new DefaultTreeNode(student.getDisplayName(), this.studentRootNode);
				this.studentNameNode.setSelected(true);
				this.studentNameNode.setExpanded(true);

				TreeNode studentAcademicYearsTreeNode = new DefaultTreeNode("Academic years", this.studentNameNode);
				studentAcademicYearsTreeNode.setExpanded(true);
				studentAcademicYearsTreeNode.setSelectable(false);

				Collection<StudentAcademicYear> studentAcademicYears = this.serviceAcademicYearService
						.findStudentAcademicYearsByStudentId(student.getId());

				for (StudentAcademicYear studentAcademicYear : studentAcademicYears) {

					StudentAcademicYearTreeNode studentAcademicYearTreeNode = new StudentAcademicYearTreeNode(
							studentAcademicYear.getAcademicYear().getDisplayLabel(),
							studentAcademicYear.getAcademicYear(), studentAcademicYearsTreeNode);

					studentAcademicYearTreeNode.setSelectable(true);

				}
				this.setLoadStudentTreeFlag(false);
			}
		}
	}

	public void onNodeSelect(final NodeSelectEvent event) {
		// Recursive search for selected node.
		this.updateNodeForSelected(this.studentRootNode, event.getTreeNode());

		if (event.getTreeNode() != null && event.getTreeNode() instanceof StudentAcademicYearTreeNode) {
			StudentAcademicYearTreeNode treeNode = (StudentAcademicYearTreeNode) event.getTreeNode();
			if (treeNode.getAcademicYear() != null) {

				// AcademicYear academicYear = treeNode.getAcademicYear();
				// This code will be remove in future because this functionality
				// will be moved as dropdown.
				// this.studentBean.setStudentAcademicYear(academicYear);
				this.studentBean.setActiveTabIndex(0);
				this.studentBean.setViewOrNewAction(true);
				this.viewContentHandlerBean.setCurrentViewContent(ViewContentConstant.BRANCH_STUDENTS);
			}
		} else {
			this.viewContentHandlerBean.setCurrentViewContent(ViewContentConstant.BRANCH_STUDENTS);
			this.studentBean.setActiveTabIndex(0);
			this.studentBean.setViewOrNewAction(true);
		}

	}

	public boolean isLoadStudentTreeFlag() {
		return this.loadStudentTreeFlag;
	}

	public void setLoadStudentTreeFlag(final boolean loadStudentTreeFlag) {
		this.loadStudentTreeFlag = loadStudentTreeFlag;
	}

}
