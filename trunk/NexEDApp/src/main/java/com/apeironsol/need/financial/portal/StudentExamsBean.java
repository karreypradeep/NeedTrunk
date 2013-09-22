/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 * 
 */
package com.apeironsol.need.financial.portal;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.Resource;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.BuildingBlock;
import com.apeironsol.need.core.model.SectionSubject;
import com.apeironsol.need.core.portal.AbstractStudentBean;
import com.apeironsol.need.core.portal.AbstractTabbedBean;
import com.apeironsol.need.core.service.BuildingBlockService;
import com.apeironsol.need.core.service.SectionSubjectService;
import com.apeironsol.need.util.constants.BuildingBlockConstant;

/**
 * JSF managed for financial income.
 * 
 * @author Pradeep
 */
@Named
@Scope(value = "session")
public class StudentExamsBean extends AbstractTabbedBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long			serialVersionUID			= 1557245393725414244L;

	private int							viewExamsResultsSelection	= 1;

	public static final int				EXAM_TYPE_WISE_RESULTS		= 1;

	public static final int				SUBJECT_WISE_RESULTS		= 2;

	private BuildingBlock				selectedExamType;

	private SectionSubject				selectedSubject;

	/**
	 * Collection to hold building block type designations.
	 */
	private Collection<BuildingBlock>	examTypes;

	/**
	 * Collection to hold building block type designations.
	 */
	private Collection<SectionSubject>	sectionSubjects;

	private boolean						loadExamTypeFromDBInd;
	private boolean						loadSectionSubjectsFromDBInd;

	@Resource
	private BuildingBlockService		buildingBlockService;

	@Resource
	private AbstractStudentBean					studentBean;

	@Resource
	private SectionSubjectService		sectionSubjectService;

	@Override
	public void onTabChange() {
		this.loadExamTypeFromDBInd = true;
		this.loadSectionSubjectsFromDBInd = true;
	}

	/**
	 * @return the viewExamsResultsSelection
	 */
	public int getViewExamsResultsSelection() {
		return this.viewExamsResultsSelection;
	}

	/**
	 * @param viewExamsResultsSelection
	 *            the viewExamsResultsSelection to set
	 */
	public void setViewExamsResultsSelection(final int viewExamsResultsSelection) {
		this.viewExamsResultsSelection = viewExamsResultsSelection;
	}

	public boolean isViewExamTypeWiseResults() {
		return EXAM_TYPE_WISE_RESULTS == this.viewExamsResultsSelection;
	}

	public boolean isViewSubjectWiseResults() {
		return SUBJECT_WISE_RESULTS == this.viewExamsResultsSelection;
	}

	/**
	 * @return the selectedExamType
	 */
	public BuildingBlock getSelectedExamType() {
		return this.selectedExamType;
	}

	/**
	 * @param selectedExamType
	 *            the selectedExamType to set
	 */
	public void setSelectedExamType(final BuildingBlock selectedExamType) {
		this.selectedExamType = selectedExamType;
	}

	/**
	 * @return the examTypes
	 */
	public Collection<BuildingBlock> getExamTypes() {
		return this.examTypes;
	}

	/**
	 * @param examTypes
	 *            the examTypes to set
	 */
	public void setExamTypes(final Collection<BuildingBlock> examTypes) {
		this.examTypes = examTypes;
	}

	/**
	 * @return the loadExamTypeFromDBInd
	 */
	public boolean isLoadExamTypeFromDBInd() {
		return this.loadExamTypeFromDBInd;
	}

	/**
	 * @param loadExamTypeFromDBInd
	 *            the loadExamTypeFromDBInd to set
	 */
	public void setLoadExamTypeFromDBInd(final boolean loadExamTypeFromDBInd) {
		this.loadExamTypeFromDBInd = loadExamTypeFromDBInd;
	}

	public void loadExamTypes() {
		if (this.loadExamTypeFromDBInd) {
			this.setExamTypes(this.buildingBlockService.findBuildingBlocksbyBranchIdAndBuildingBlockType(this.sessionBean.getCurrentBranch().getId(),
					BuildingBlockConstant.EXAM_TYPE));
			this.loadExamTypeFromDBInd = false;
		}
	}

	public void loadSectionSubjects() {
		if (this.loadSectionSubjectsFromDBInd) {
			this.setSectionSubjects(this.sectionSubjectService.findSectionSubjectsByScetionId(this.studentBean.getStudentSection().getSection().getId()));
			this.loadSectionSubjectsFromDBInd = false;
		}
	}

	/**
	 * @return the sectionSubjects
	 */
	public Collection<SectionSubject> getSectionSubjects() {
		return this.sectionSubjects;
	}

	/**
	 * @param sectionSubjects
	 *            the sectionSubjects to set
	 */
	public void setSectionSubjects(final Collection<SectionSubject> sectionSubjects) {
		this.sectionSubjects = sectionSubjects;
	}

	/**
	 * @return the loadSectionSubjectsFromDBInd
	 */
	public boolean isLoadSectionSubjectsFromDBInd() {
		return this.loadSectionSubjectsFromDBInd;
	}

	/**
	 * @param loadSectionSubjectsFromDBInd
	 *            the loadSectionSubjectsFromDBInd to set
	 */
	public void setLoadSectionSubjectsFromDBInd(final boolean loadSectionSubjectsFromDBInd) {
		this.loadSectionSubjectsFromDBInd = loadSectionSubjectsFromDBInd;
	}

	/**
	 * @return the selectedSubject
	 */
	public SectionSubject getSelectedSubject() {
		return this.selectedSubject;
	}

	/**
	 * @param selectedSubject
	 *            the selectedSubject to set
	 */
	public void setSelectedSubject(final SectionSubject selectedSubject) {
		this.selectedSubject = selectedSubject;
	}

}
