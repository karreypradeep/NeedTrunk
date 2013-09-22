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

import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.util.dataobject.SectionFinancialDO;

/**
 * @author sunny
 *  Data object for section report.
 *
 */
public class SectionRO implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long	serialVersionUID	= 5878678846135676581L;

	private Section section;

	private SectionFinancialDO sectionFinancialDO;

	private List<StudentRO> studentROList;

	/**
	 * @return the section
	 */
	public Section getSection() {
		return this.section;
	}

	/**
	 * @param section the section to set
	 */
	public void setSection(final Section section) {
		this.section = section;
	}

	/**
	 * @return the studentROList
	 */
	public List<StudentRO> getStudentROList() {
		return this.studentROList;
	}

	/**
	 * @param studentROList the studentROList to set
	 */
	public void setStudentROList(final List<StudentRO> studentROList) {
		this.studentROList = studentROList;
	}

	/**
	 * @param sectionROList the sectionROList to set
	 */
	public void addStudentRO(final  StudentRO studentRO) {
		if(this.studentROList==null){
			this.studentROList = new ArrayList<StudentRO>();
		}
		this.studentROList.add(studentRO);
	}

	/**
	 * @return the sectionFinancialDO
	 */
	public SectionFinancialDO getSectionFinancialDO() {
		return this.sectionFinancialDO;
	}

	/**
	 * @param sectionFinancialDO the sectionFinancialDO to set
	 */
	public void setSectionFinancialDO(final SectionFinancialDO sectionFinancialDO) {
		this.sectionFinancialDO = sectionFinancialDO;
	}
}
