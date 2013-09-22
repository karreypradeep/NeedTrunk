/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2013 apeironsol
 */
package com.apeironsol.need.core.portal;

/**
 * StudentsPromoteBean.
 * 
 * @author Pradeep
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.model.Klass;
import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.core.model.StudentSection;
import com.apeironsol.need.core.service.StudentPromotionService;
import com.apeironsol.need.core.service.StudentService;
import com.apeironsol.need.financial.service.StudentFinancialService;
import com.apeironsol.need.util.dataobject.StudentFinancialAcademicYearDO;
import com.apeironsol.need.util.dataobject.StudentFinancialAcademicYearDataModel;
import com.apeironsol.need.util.portal.ViewUtil;

@Named
@Scope("session")
public class StudentsPromotionBean extends AbstractTabbedBean {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long						serialVersionUID	= 8875176995638866501L;

	/**
	 * Academic year from which students have to be promoted.
	 */
	private AcademicYear							fromAcademicYear;

	/**
	 * Class from which students have to be promoted.
	 */
	private Klass									fromKlass;

	/**
	 * Section from which students have to be promoted.
	 */
	private Section									fromSection;

	/**
	 * Academic year to which students have to be promoted.
	 */
	private AcademicYear							toAcademicYear;

	/**
	 * Class to which students have to be promoted.
	 */
	private Klass									toKlass;

	/**
	 * Section to which students have to be promoted.
	 */
	private Section									toSection;

	/**
	 * Active classes for the current branch.
	 */
	private Collection<Klass>						activeKlasses;

	/**
	 * Sections for the class from which students have to be transfered.
	 */
	private Collection<Section>						sourceSections;

	/**
	 * Sections for the class from which students have to be transfered.
	 */
	private Collection<Section>						destinationSections;

	/**
	 * Indicator for loading active classes for current branch.
	 */
	private boolean									loadActiveClassesInd;

	/**
	 * Indicator to load sections(source) for from class.
	 */
	private boolean									loadSectionForFromKlassFlag;

	/**
	 * Indicator to load sections(source) for to class.
	 */
	private boolean									loadSectionForToKlassFlag;

	@Resource
	private StudentService							studentService;

	@Resource
	private StudentPromotionService					studentPromotionService;

	@Resource
	private StudentFinancialService					studentFinancialService;

	private List<StudentFinancialAcademicYearDO>	studentSectionFinancialAcademicYearDOsForSourceSection;

	private Collection<StudentSection>				studentSectionForDestinationSection;

	private StudentFinancialAcademicYearDO[]		studentSectionsForPromotion;

	private StudentFinancialAcademicYearDataModel	studentFinancialAcademicYearDataModel;

	@Override
	public void onTabChange() {
		// TODO Auto-generated method stub
		this.reset();
	}

	/**
	 * @return the fromAcademicYear
	 */
	public AcademicYear getFromAcademicYear() {
		return this.fromAcademicYear;
	}

	/**
	 * @param fromAcademicYear
	 *            the fromAcademicYear to set
	 */
	public void setFromAcademicYear(final AcademicYear fromAcademicYear) {
		this.fromAcademicYear = fromAcademicYear;
	}

	/**
	 * @return the fromKlass
	 */
	public Klass getFromKlass() {
		return this.fromKlass;
	}

	/**
	 * @param fromKlass
	 *            the fromKlass to set
	 */
	public void setFromKlass(final Klass fromKlass) {
		this.fromKlass = fromKlass;
	}

	/**
	 * @return the fromSection
	 */
	public Section getFromSection() {
		return this.fromSection;
	}

	/**
	 * @param fromSection
	 *            the fromSection to set
	 */
	public void setFromSection(final Section fromSection) {
		this.fromSection = fromSection;
	}

	/**
	 * @return the toAcademicYear
	 */
	public AcademicYear getToAcademicYear() {
		return this.toAcademicYear;
	}

	/**
	 * @param toAcademicYear
	 *            the toAcademicYear to set
	 */
	public void setToAcademicYear(final AcademicYear toAcademicYear) {
		this.toAcademicYear = toAcademicYear;
	}

	/**
	 * @return the toKlass
	 */
	public Klass getToKlass() {
		return this.toKlass;
	}

	/**
	 * @param toKlass
	 *            the toKlass to set
	 */
	public void setToKlass(final Klass toKlass) {
		this.toKlass = toKlass;
	}

	/**
	 * @return the toSection
	 */
	public Section getToSection() {
		return this.toSection;
	}

	/**
	 * @param toSection
	 *            the toSection to set
	 */
	public void setToSection(final Section toSection) {
		this.toSection = toSection;
	}

	/**
	 * @return the activeKlasses
	 */
	public Collection<Klass> getActiveKlasses() {
		return this.activeKlasses;
	}

	/**
	 * @param activeKlasses
	 *            the activeKlasses to set
	 */
	public void setActiveKlasses(final Collection<Klass> activeKlasses) {
		this.activeKlasses = activeKlasses;
	}

	/**
	 * @return the sourceSections
	 */
	public Collection<Section> getSourceSections() {
		return this.sourceSections;
	}

	/**
	 * @param sourceSections
	 *            the sourceSections to set
	 */
	public void setSourceSections(final Collection<Section> sourceSections) {
		this.sourceSections = sourceSections;
	}

	/**
	 * @return the destinationSections
	 */
	public Collection<Section> getDestinationSections() {
		return this.destinationSections;
	}

	/**
	 * @param destinationSections
	 *            the destinationSections to set
	 */
	public void setDestinationSections(final Collection<Section> destinationSections) {
		this.destinationSections = destinationSections;
	}

	/**
	 * @return the loadActiveClassesInd
	 */
	public boolean isLoadActiveClassesInd() {
		return this.loadActiveClassesInd;
	}

	/**
	 * @param loadActiveClassesInd
	 *            the loadActiveClassesInd to set
	 */
	public void setLoadActiveClassesInd(final boolean loadActiveClassesInd) {
		this.loadActiveClassesInd = loadActiveClassesInd;
	}

	public void loadActiveClasses() {
		if (this.isLoadActiveClassesInd()) {
			this.activeKlasses = this.klassService.findActiveKlassesByBranchId(this.sessionBean.getCurrentBranch().getId());
		}
	}

	public void handleFromKlassChange() {
		this.fromSection = null;
		this.setLoadSectionForFromKlassFlag(true);
		this.studentSectionFinancialAcademicYearDOsForSourceSection = null;
		this.setStudentFinancialAcademicYearDataModel(null);
		this.loadSectionsForClass();

		this.toAcademicYear = null;
		this.toSection = null;
		this.studentSectionForDestinationSection = null;
		this.toKlass = null;
	}

	public void loadSectionsForClass() {
		if (this.loadSectionForFromKlassFlag && this.fromKlass != null) {
			this.sourceSections = this.sectionService.findActiveSectionsByKlassIdAndAcademicYearId(this.fromKlass.getId(), this.fromAcademicYear.getId());
			this.loadSectionForFromKlassFlag = false;
		}
	}

	/**
	 * 
	 * @return
	 */
	public boolean isLoadSectionForFromKlassFlag() {
		return this.loadSectionForFromKlassFlag;
	}

	/**
	 * 
	 * @param loadSectionForFromKlassFlag
	 */
	public void setLoadSectionForFromKlassFlag(final boolean loadSectionForFromKlassFlag) {
		this.loadSectionForFromKlassFlag = loadSectionForFromKlassFlag;
	}

	public void handleToKlassChange() {
		this.toSection = null;
		this.setLoadSectionForToKlassFlag(true);
		this.studentSectionForDestinationSection = null;
		this.loadSectionsForToClass();
	}

	public void loadSectionsForToClass() {
		if (this.loadSectionForToKlassFlag && this.toKlass != null) {
			this.destinationSections = this.sectionService.findActiveSectionsByKlassIdAndAcademicYearId(this.toKlass.getId(), this.toAcademicYear.getId());
			this.loadSectionForToKlassFlag = false;
		}
	}

	/**
	 * @return the loadSectionForToKlassFlag
	 */
	public boolean isLoadSectionForToKlassFlag() {
		return this.loadSectionForToKlassFlag;
	}

	/**
	 * @param loadSectionForToKlassFlag
	 *            the loadSectionForToKlassFlag to set
	 */
	public void setLoadSectionForToKlassFlag(final boolean loadSectionForToKlassFlag) {
		this.loadSectionForToKlassFlag = loadSectionForToKlassFlag;
	}

	public void getActiveStudentsOfSourceSection() {
		if (this.studentSectionFinancialAcademicYearDOsForSourceSection == null) {
			this.studentSectionFinancialAcademicYearDOsForSourceSection = new ArrayList<StudentFinancialAcademicYearDO>();
		} else {
			this.studentSectionFinancialAcademicYearDOsForSourceSection.clear();
		}
		Collection<StudentSection> activeStudentSections = this.studentService.findActiveStudentSectionsBySectionId(this.fromSection.getId());
		if (activeStudentSections != null && !activeStudentSections.isEmpty()) {
			Collection<StudentFinancialAcademicYearDO> studentFinancialAcademicYearDOs = this.studentFinancialService
					.getStudnetFeeFinancialAcademicYearDetailsByAcademicYearIdForDueDate(activeStudentSections, null);
			if (studentFinancialAcademicYearDOs != null && !studentFinancialAcademicYearDOs.isEmpty()) {
				for (StudentFinancialAcademicYearDO studentFinancialAcademicYearDO : studentFinancialAcademicYearDOs) {
					this.studentSectionFinancialAcademicYearDOsForSourceSection.add(studentFinancialAcademicYearDO);
				}
			}
		}
		this.setStudentFinancialAcademicYearDataModel(new StudentFinancialAcademicYearDataModel(this.studentSectionFinancialAcademicYearDOsForSourceSection));
	}

	/**
	 * @return the studentSectionForSourceSection
	 */
	public Collection<StudentFinancialAcademicYearDO> getStudentSectionForSourceSection() {
		return this.studentSectionFinancialAcademicYearDOsForSourceSection;
	}

	/**
	 * @param studentSectionForSourceSection
	 *            the studentSectionForSourceSection to set
	 */
	public void setStudentSectionForSourceSection(final List<StudentFinancialAcademicYearDO> studentSectionForSourceSection) {
		this.studentSectionFinancialAcademicYearDOsForSourceSection = studentSectionForSourceSection;
	}

	/**
	 * @return the studentSectionForDestinationSection
	 */
	public Collection<StudentSection> getStudentSectionForDestinationSection() {
		return this.studentSectionForDestinationSection;
	}

	/**
	 * @param studentSectionForDestinationSection
	 *            the studentSectionForDestinationSection to set
	 */
	public void setStudentSectionForDestinationSection(final Collection<StudentSection> studentSectionForDestinationSection) {
		this.studentSectionForDestinationSection = studentSectionForDestinationSection;
	}

	public void getActiveStudentsOfDestinationSection() {
		this.studentSectionForDestinationSection = this.studentService.findActiveStudentSectionsBySectionId(this.toSection.getId());
	}

	/**
	 * @return the studentSectionsForPromotion
	 */
	public StudentFinancialAcademicYearDO[] getStudentSectionsForPromotion() {
		return this.studentSectionsForPromotion;
	}

	/**
	 * @param studentSectionsForPromotion
	 *            the studentSectionsForPromotion to set
	 */
	public void setStudentSectionsForPromotion(final StudentFinancialAcademicYearDO[] studentSectionsForPromotion) {
		this.studentSectionsForPromotion = studentSectionsForPromotion;
	}

	/**
	 * @return the studentFinancialAcademicYearDataModel
	 */
	public StudentFinancialAcademicYearDataModel getStudentFinancialAcademicYearDataModel() {
		return this.studentFinancialAcademicYearDataModel;
	}

	/**
	 * @param studentFinancialAcademicYearDataModel
	 *            the studentFinancialAcademicYearDataModel to set
	 */
	public void setStudentFinancialAcademicYearDataModel(final StudentFinancialAcademicYearDataModel studentFinancialAcademicYearDataModel) {
		this.studentFinancialAcademicYearDataModel = studentFinancialAcademicYearDataModel;
	}

	public String reset() {

		this.fromAcademicYear = null;
		this.fromSection = null;
		this.fromKlass = null;
		this.toAcademicYear = null;
		this.toSection = null;
		this.toKlass = null;
		this.studentFinancialAcademicYearDataModel = null;
		this.studentSectionsForPromotion = null;
		this.studentSectionForDestinationSection = null;
		this.studentSectionFinancialAcademicYearDOsForSourceSection = null;
		return null;
	}

	public String promoteStudents() {
		if (this.studentSectionsForPromotion != null && this.studentSectionsForPromotion.length > 0) {
			Collection<StudentFinancialAcademicYearDO> studentFinancialAcademicYearDOs = new ArrayList<StudentFinancialAcademicYearDO>();
			for (StudentFinancialAcademicYearDO studentFinancialAcademicYearDO : this.studentSectionsForPromotion) {
				studentFinancialAcademicYearDOs.add(studentFinancialAcademicYearDO);
			}
			this.studentPromotionService.promoteStudents(studentFinancialAcademicYearDOs, this.toSection);
		}
		ViewUtil.addMessage("Promotion of students completed successfully.", FacesMessage.SEVERITY_INFO);
		this.reset();
		return null;
	}
}
