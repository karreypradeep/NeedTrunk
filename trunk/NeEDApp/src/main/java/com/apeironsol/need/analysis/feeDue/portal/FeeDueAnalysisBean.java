/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 * 
 */
package com.apeironsol.need.analysis.feeDue.portal;

import java.io.Serializable;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.portal.AbstractTabbedBean;

/**
 * JSF managed for financial expense.
 * 
 * @author Pradeep
 */
@Named
@Scope(value = "session")
public class FeeDueAnalysisBean extends AbstractTabbedBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long	serialVersionUID	= -1370379543559654010L;

	@Override
	public void onTabChange() {

	}

}
