/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.core.portal;

/**
 * View courses class.
 * 
 * @author Pradeep
 */
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.primefaces.event.DragDropEvent;
import org.springframework.context.annotation.Scope;

import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.core.model.Klass;
import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.core.model.SectionSubject;
import com.apeironsol.need.core.model.Student;
import com.apeironsol.need.core.model.StudentSection;
import com.apeironsol.need.core.model.Subject;
import com.apeironsol.need.core.portal.util.ViewContentHandlerBean;
import com.apeironsol.need.core.service.SectionService;
import com.apeironsol.need.core.service.SectionSubjectService;
import com.apeironsol.need.core.service.StudentSectionService;
import com.apeironsol.need.core.service.StudentService;
import com.apeironsol.need.core.service.SubjectService;
import com.apeironsol.need.util.constants.GenderConstant;
import com.apeironsol.need.util.constants.StudentSectionStatusConstant;
import com.apeironsol.need.util.constants.ViewContentConstant;
import com.apeironsol.need.util.portal.SectionTabModel;
import com.apeironsol.need.util.portal.ViewExceptionHandler;
import com.apeironsol.need.util.portal.ViewUtil;
import com.apeironsol.need.util.searchcriteria.SectionSearchCriteria;

@Named
@Scope("session")
public class SectionBean extends AbstractKlassBean {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long				serialVersionUID							= -6282397725805720672L;

	@Resource
	private SectionService					sectionService;

	@Resource
	private StudentService					studentService;

	@Resource
	private KlassBean						klassBean;

	@Resource
	private SubjectService					subjectService;

	@Resource
	private ViewContentHandlerBean			viewContentHandlerBean;

	@Resource
	private StudentBean						studentBean;

	@Resource
	private StudentSectionService			studentSectionService;

	private Section							section;

	private Collection<Section>				sections;

	private Long							klassId;

	private boolean							loadSectionsFlag;

	private boolean							loadSectionsForKlassFlag;

	private Collection<StudentSection>		studentsForSection;

	private boolean							loadStudentsForSectionFlag;

	private boolean							loadUnassignedKlassSubjectsForSectionFlag;

	private Collection<Subject>				unassignedKlassSubjectsForSection;

	private boolean							loadSectionSubjectsFlag;

	private Collection<SectionSubject>		sectionSubjects								= new ArrayList<SectionSubject>();

	private SectionSubject					sectionSubject;

	@Resource
	SectionSubjectService					sectionSubjectService;

	private Student							slectedStudentForDisplay;

	private SectionTabModel					sectionTabModel								= new SectionTabModel();

	private int								numberOfBoysInSection;

	private int								numberOfGirlsInSection;

	private SectionSearchCriteria			sectionSearchCriteria						= null;

	private StudentSectionStatusConstant	studentSectionStatusConstantForSecStudents	= null;

	/**
	 * @return the sectionSearchCriteria
	 */
	public SectionSearchCriteria getSectionSearchCriteria() {
		return this.sectionSearchCriteria;
	}

	/**
	 * @param sectionSearchCriteria
	 *            the sectionSearchCriteria to set
	 */
	public void setSectionSearchCriteria(final SectionSearchCriteria sectionSearchCriteria) {
		this.sectionSearchCriteria = sectionSearchCriteria;
	}

	public String resetSearchCriteria() {
		this.sectionSearchCriteria.resetSeacrhCriteria();
		return null;
	}

	public void initializeSearchCriteria() {
		if (this.sectionSearchCriteria == null) {
			this.sectionSearchCriteria = new SectionSearchCriteria(this.sessionBean.getCurrentBranch());
		}
	}

	@PostConstruct
	public void init() {
		this.section = new Section();
		this.initializeSearchCriteria();
	}

	public Section getSection() {
		return this.section;
	}

	public void setSection(final Section section) {
		this.section = section;
	}

	public Collection<Section> getSections() {
		return this.sections;
	}

	public void newSection() {
		this.section = new Section();
		this.section.setOpenForAdmission(true);
		this.academicYearId = null;
		this.klassId = null;
	}

	public void saveSection() {
		try {
			Klass klass = this.klassService.findKlassById(this.klassId);
			this.section.setKlass(klass);
			AcademicYear academicYear = this.academicYearService.findAcademicYearById(this.academicYearId);
			this.section.setAcademicYear(academicYear);
			this.section = this.sectionService.saveSection(this.section);
			ViewUtil.addMessage("Section saved sucessfully.", FacesMessage.SEVERITY_INFO);
			this.loadSectionsFlag = true;
		} catch (ApplicationException e) {
			ViewExceptionHandler.handle(e);
		}
	}

	public void removeSection() {
		try {
			this.sectionService.removeSection(this.section);
			this.section = new Section();
			this.loadSectionsFlag = true;
			ViewUtil.addMessage("Section removed sucessfully.", FacesMessage.SEVERITY_INFO);
		} catch (BusinessException e) {
			ViewExceptionHandler.handle(e);
		} catch (Throwable e) {
			ViewExceptionHandler.handle(e);
		}
	}

	public void viewSection() {
		Klass klass = this.section.getKlass();
		this.klassId = klass.getId();
		AcademicYear academicYear = this.section.getAcademicYear();
		this.academicYearId = academicYear.getId();
		this.viewContentHandlerBean.setCurrentViewContent(ViewContentConstant.BRANCH_SECTIONS);
	}

	public void resetSection() {
		this.section = new Section();
	}

	public void loadSections() {
		if (this.isLoadSectionsFlag()) {
			Branch branch = this.sessionBean.getCurrentBranch();
			this.sections = this.sectionService.findAllSectionsByBranchId(branch.getId());
			this.setLoadSectionsFlag(false);
		}
	}

	public void loadSectionsForKlass() {
		if (this.isLoadSectionsForKlassFlag()) {
			Klass klass = this.klassBean.getKlass();
			this.sections = this.sectionService.findAllSectionsByKlassId(klass.getId());
			this.setLoadSectionsForKlassFlag(false);
		}
	}

	public void loadStudentsForSection() {
		try {
			if (this.loadStudentsForSectionFlag) {
				this.numberOfBoysInSection = 0;
				this.numberOfGirlsInSection = 0;
				this.studentsForSection = this.studentSectionService.findStudentStudentSectionStatusAndSection(StudentSectionStatusConstant.ACTIVE,
						this.getSection());
				this.studentService.findActiveStudentsBySectionId(this.section.getId());
				for (StudentSection studentSection : this.studentsForSection) {
					if (GenderConstant.MALE.equals(studentSection.getStudentAcademicYear().getStudent().getGender())) {
						this.numberOfBoysInSection++;
					} else {
						this.numberOfGirlsInSection++;
					}
				}
				this.loadStudentsForSectionFlag = false;
			}
		} catch (ApplicationException e) {
			ViewExceptionHandler.handle(e);
		} catch (Throwable e) {
			ViewExceptionHandler.handle(e);
		}

	}

	public boolean isLoadSectionsFlag() {
		return this.loadSectionsFlag;
	}

	public void setLoadSectionsFlag(final boolean loadSectionsFlag) {
		this.loadSectionsFlag = loadSectionsFlag;
	}

	public Long getKlassId() {
		return this.klassId;
	}

	public void setKlassId(final Long klassId) {
		this.klassId = klassId;
	}

	@Override
	public void onTabChange() {

	}

	public boolean isDisableSubTab() {
		if (this.section != null && this.section.getId() != null) {
			return false;
		}
		return true;
	}

	public boolean isLoadSectionsForKlassFlag() {
		return this.loadSectionsForKlassFlag;
	}

	public void setLoadSectionsForKlassFlag(final boolean loadSectionsForKlassFlag) {
		this.loadSectionsForKlassFlag = loadSectionsForKlassFlag;
	}

	public boolean isLoadStudentsForSectionFlag() {
		return this.loadStudentsForSectionFlag;
	}

	public void setLoadStudentsForSectionFlag(final boolean loadStudentsForSectionFlag) {
		this.loadStudentsForSectionFlag = loadStudentsForSectionFlag;
	}

	public Collection<StudentSection> getStudentsForSection() {
		return this.studentsForSection;
	}

	public void setStudentsForSection(final Collection<StudentSection> studentsForSection) {
		this.studentsForSection = studentsForSection;
	}

	public void loadKlassSubjectsForSection() {

	}

	public void loadUnassignedKlassSubjectsForSection() {

		if (this.isLoadUnassignedKlassSubjectsForSectionFlag()) {

			Collection<Subject> subjects = this.subjectService.findAllSubjectsByKlassId(this.section.getKlass().getId());

			this.sectionSubjects = this.sectionSubjectService.findSectionSubjectsByScetionId(this.section.getId());

			for (SectionSubject sectionSubject : this.sectionSubjects) {
				if (subjects.contains(sectionSubject.getSubject())) {
					subjects.remove(sectionSubject.getSubject());
				}
			}
			this.setUnassignedKlassSubjectsForSection(subjects);
			this.setLoadUnassignedKlassSubjectsForSectionFlag(false);
		}

	}

	public void loadSectionSubjects() {
		if (this.loadSectionSubjectsFlag) {
			this.sectionSubjects = this.sectionSubjectService.findSectionSubjectsByScetionId(this.section.getId());
		}
	}

	public boolean isLoadKlassElectiveSubjectsForSectionFlag() {
		return this.isLoadUnassignedKlassSubjectsForSectionFlag();
	}

	public void setLoadKlassElectiveSubjectsForSectionFlag(final boolean loadKlassElectiveSubjectsForSectionFlag) {
		this.setLoadUnassignedKlassSubjectsForSectionFlag(loadKlassElectiveSubjectsForSectionFlag);
	}

	public void onSubjectDrop(final DragDropEvent ddEvent) {

		Subject subject = (Subject) ddEvent.getData();
		SectionSubject sectionSubject = new SectionSubject();
		sectionSubject.setSection(this.section);
		sectionSubject.setSubject(subject);
		sectionSubject = this.sectionSubjectService.saveSectionSubject(sectionSubject);
		this.section = sectionSubject.getSection();
		this.loadUnassignedKlassSubjectsForSectionFlag = true;
		this.loadUnassignedKlassSubjectsForSection();

	}

	public void removeSectionSubject() {

		this.sectionSubjectService.removeSectionSubject(this.sectionSubject);
		this.loadUnassignedKlassSubjectsForSectionFlag = true;
		this.loadUnassignedKlassSubjectsForSection();

	}

	public boolean isLoadSectionSubjectsFlag() {
		return this.loadSectionSubjectsFlag;
	}

	public void setLoadSectionSubjectsFlag(final boolean loadSectionSubjectsFlag) {
		this.loadSectionSubjectsFlag = loadSectionSubjectsFlag;
	}

	public Collection<SectionSubject> getSectionSubjects() {
		return this.sectionSubjects;
	}

	public void setSectionSubjects(final Collection<SectionSubject> sectionSubjects) {
		this.sectionSubjects = sectionSubjects;
	}

	public boolean isLoadUnassignedKlassSubjectsForSectionFlag() {
		return this.loadUnassignedKlassSubjectsForSectionFlag;
	}

	public void setLoadUnassignedKlassSubjectsForSectionFlag(final boolean loadUnassignedKlassSubjectsForSectionFlag) {
		this.loadUnassignedKlassSubjectsForSectionFlag = loadUnassignedKlassSubjectsForSectionFlag;
	}

	public Collection<Subject> getUnassignedKlassSubjectsForSection() {
		return this.unassignedKlassSubjectsForSection;
	}

	public void setUnassignedKlassSubjectsForSection(final Collection<Subject> unassignedKlassSubjectsForSection) {
		this.unassignedKlassSubjectsForSection = unassignedKlassSubjectsForSection;
	}

	public SectionSubject getSectionSubject() {
		return this.sectionSubject;
	}

	public void setSectionSubject(final SectionSubject sectionSubject) {
		this.sectionSubject = sectionSubject;
	}

	public void activeSection() {

		try {
			this.section = this.sectionService.activateSection(this.section);
			this.loadSectionsFlag = true;
			ViewUtil.addMessage("Section is activated successfully", FacesMessage.SEVERITY_INFO);
		} catch (ApplicationException e) {
			ViewExceptionHandler.handle(e);
			this.section = this.sectionService.findSectionById(this.section.getId());

		} catch (Throwable e) {
			ViewExceptionHandler.handle(e);
			this.section = this.sectionService.findSectionById(this.section.getId());

		}

	}

	public void deactiveSection() {

		try {
			this.section = this.sectionService.deactivateSection(this.section);
			this.loadSectionsFlag = true;
			ViewUtil.addMessage("Section is set to development successfully", FacesMessage.SEVERITY_INFO);
		} catch (ApplicationException e) {
			ViewExceptionHandler.handle(e);
			this.section = this.sectionService.findSectionById(this.section.getId());

		} catch (Throwable e) {
			ViewExceptionHandler.handle(e);
			this.section = this.sectionService.findSectionById(this.section.getId());

		}

	}

	public void viewKlass() {
		this.sessionBean.setCurrentKlass(this.section.getKlass());
		this.viewContentHandlerBean.setCurrentViewContent(ViewContentConstant.BRANCH_KLASSES);
	}

	public void viewStudent() {
		this.viewContentHandlerBean.setCurrentViewContent(ViewContentConstant.BRANCH_STUDENTS);
		this.studentBean.loadStudentsDetailsBeforeDisplayInformation(this.studentSectionService.findStudentSectionByStudentIdAndSectionId(
				this.slectedStudentForDisplay.getId(), this.section.getId()));
	}

	/**
	 * @return the slectedStudentForDisplay
	 */
	public Student getSlectedStudentForDisplay() {
		return this.slectedStudentForDisplay;
	}

	/**
	 * @param slectedStudentForDisplay
	 *            the slectedStudentForDisplay to set
	 */
	public void setSlectedStudentForDisplay(final Student slectedStudentForDisplay) {
		this.slectedStudentForDisplay = slectedStudentForDisplay;
	}

	public void handleSectionTabModel() {

		this.getSectionTabModel().getReportsTab().setRendered(false);
	}

	public SectionTabModel getSectionTabModel() {
		return this.sectionTabModel;
	}

	public void setSectionTabModel(final SectionTabModel sectionTabModel) {
		this.sectionTabModel = sectionTabModel;
	}

	/**
	 * @return the numberOfBoysInSection
	 */
	public int getNumberOfBoysInSection() {
		return this.numberOfBoysInSection;
	}

	/**
	 * @param numberOfBoysInSection
	 *            the numberOfBoysInSection to set
	 */
	public void setNumberOfBoysInSection(final int numberOfBoysInSection) {
		this.numberOfBoysInSection = numberOfBoysInSection;
	}

	/**
	 * @return the numberOfGirlsInSection
	 */
	public int getNumberOfGirlsInSection() {
		return this.numberOfGirlsInSection;
	}

	/**
	 * @param numberOfGirlsInSection
	 *            the numberOfGirlsInSection to set
	 */
	public void setNumberOfGirlsInSection(final int numberOfGirlsInSection) {
		this.numberOfGirlsInSection = numberOfGirlsInSection;
	}

	/**
	 * @param sections
	 *            the sections to set
	 */
	public void setSections(final Collection<Section> sections) {
		this.sections = sections;
	}

	public String searchSectionsBySearchCriteria() {

		if (this.sectionSearchCriteria.getAcademicYear() == null) {
			ViewUtil.addMessage("Please select Academic year.", FacesMessage.SEVERITY_ERROR);
		} else {
			this.sectionSearchCriteria.setBranch(this.sessionBean.getCurrentBranch());
			this.setSections(this.sectionService.findSectionsBySearchCriteria(this.sectionSearchCriteria));
			if (this.getSections() == null || this.getSections().isEmpty()) {
				ViewUtil.addMessage("No Sections found for entered search criteria..", FacesMessage.SEVERITY_INFO);
			}
		}
		return null;
	}

	/**
	 * @return the studentSectionStatusConstantForSecStudents
	 */
	public StudentSectionStatusConstant getStudentSectionStatusConstantForSecStudents() {
		return this.studentSectionStatusConstantForSecStudents;
	}

	/**
	 * @param studentSectionStatusConstantForSecStudents
	 *            the studentSectionStatusConstantForSecStudents to set
	 */
	public void setStudentSectionStatusConstantForSecStudents(final StudentSectionStatusConstant studentSectionStatusConstantForSecStudents) {
		this.studentSectionStatusConstantForSecStudents = studentSectionStatusConstantForSecStudents;
	}

	public String searchSectionStudentsByStatus() {

		if (this.studentSectionStatusConstantForSecStudents == null) {
			ViewUtil.addMessage("Please select student status.", FacesMessage.SEVERITY_ERROR);
		} else {
			this.studentsForSection = this.studentSectionService.findStudentStudentSectionStatusAndSection(this.studentSectionStatusConstantForSecStudents,
					this.getSection());
		}
		return null;
	}

}