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

import com.apeironsol.need.core.model.AcademicYear;

public class StudentAcademicYearTreeNode extends DefaultTreeNode {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long	serialVersionUID	= 8434636429758962345L;

	private AcademicYear		academicYear;

	public StudentAcademicYearTreeNode(final String display, final AcademicYear academicYear, final TreeNode root) {
		super(display, root);
		this.academicYear = academicYear;
	}

	public AcademicYear getAcademicYear() {
		return this.academicYear;
	}

	public void setAcademicYear(final AcademicYear academicYear) {
		this.academicYear = academicYear;
	}

}
