/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.reporting.ro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.apeironsol.need.core.model.Klass;
import com.apeironsol.need.util.dataobject.ClassFinancialDO;

/**
 * 
 * @author sunny
 *         Data object for class report.
 * 
 */
public class KlassRO implements Serializable {

	/**
	 *  Universal serial version id for this class.
	 */
	private static final long	serialVersionUID	= 4831730784041564041L;

	private Klass klass;

	private List<SectionRO> sectionROList;

	private ClassFinancialDO classFinancialDO;

	/**
	 * @return the klass
	 */
	public Klass getKlass() {
		return this.klass;
	}

	/**
	 * @param klass the klass to set
	 */
	public void setKlass(final Klass klass) {
		this.klass = klass;
	}

	/**
	 * @return the sectionROList
	 */
	public List<SectionRO> getSectionROList() {
		return this.sectionROList;
	}

	/**
	 * @param sectionROList the sectionROList to set
	 */
	public void setSectionROList(final List<SectionRO> sectionROList) {
		this.sectionROList = sectionROList;
	}

	/**
	 * @param sectionROList the sectionROList to set
	 */
	public void addSectionRO(final  SectionRO sectionRO) {
		if(this.sectionROList==null){
			this.sectionROList = new ArrayList<SectionRO>();
		}
		this.sectionROList.add(sectionRO);
	}

	/**
	 * @return the classFinancialDO
	 */
	public ClassFinancialDO getClassFinancialDO() {
		return classFinancialDO;
	}

	/**
	 * @param classFinancialDO the classFinancialDO to set
	 */
	public void setClassFinancialDO(ClassFinancialDO classFinancialDO) {
		this.classFinancialDO = classFinancialDO;
	}

}
