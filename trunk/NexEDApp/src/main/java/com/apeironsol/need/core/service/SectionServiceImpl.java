/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */

package com.apeironsol.need.core.service;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.SystemException;
import com.apeironsol.need.academics.dao.ExamDao;
import com.apeironsol.need.academics.dao.SectionExamDao;
import com.apeironsol.need.academics.model.SectionExam;
import com.apeironsol.need.academics.service.SectionExamService;
import com.apeironsol.need.core.dao.SectionDao;
import com.apeironsol.need.core.dao.StudentSectionDao;
import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.model.Klass;
import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.core.model.SectionSubject;
import com.apeironsol.need.core.model.SectionTeacher;
import com.apeironsol.need.core.model.SectionTimetable;
import com.apeironsol.need.core.model.StudentSection;
import com.apeironsol.need.core.model.Subject;
import com.apeironsol.need.core.portal.messages.BusinessMessages;
import com.apeironsol.need.util.searchcriteria.SectionSearchCriteria;

/**
 * Service interface implementation for section.
 * 
 * @author pradeep
 * 
 */
@Service("sectionService")
@Transactional
public class SectionServiceImpl implements SectionService {

	@Resource
	private SectionDao				sectionDao;

	@Resource
	private SectionSubjectService	sectionSubjectService;

	@Resource
	private StudentSectionDao		studentSectionDao;

	@Resource
	private SectionExamDao			sectionExamDao;

	@Resource
	private ExamDao					examDao;

	@Resource
	private SubjectService			subjectService;

	@Resource
	private SectionTeacherService	sectionTeacherService;

	@Resource
	private SectionTimetableService	sectionTimetableService;

	@Resource
	private SectionExamService		sectionExamService;

	@Resource
	private KlassService			klassService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Section saveSection(final Section section) {
		boolean newSection = false;
		if (section.getId() == null && section.getKlass() != null) {
			newSection = true;
		}
		Section result = this.sectionDao.persist(section);
		if (newSection) {
			this.postCreateNewSection(result);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeSection(final Section section) throws BusinessException, SystemException {

		try {

			// Check if students associated with the section
			Collection<StudentSection> studentSections = this.studentSectionDao.findStudentSectionsBySectionId(section.getId());
			if (studentSections != null && !studentSections.isEmpty()) {
				throw new BusinessException(BusinessMessages.getResourceBundleName(), BusinessMessages.MSG_CANNOT_DELETE_SECTION_STUDENTS_ASSOCIATED, null);
			}

			// Check if students associated with the section
			Collection<SectionTeacher> sectionTeachers = this.sectionTeacherService.findSectionTeachersByScetionId(section.getId());
			if (sectionTeachers != null && !sectionTeachers.isEmpty()) {
				throw new BusinessException(BusinessMessages.getResourceBundleName(), BusinessMessages.MSG_CANNOT_DELETE_SECTION_TEACHERS_ASSOCIATED, null);
			}

			// Check if students associated with the section
			Collection<SectionTimetable> sectionTimetables = this.sectionTimetableService.findSectionTimetablesByScetionId(section.getId());
			if (sectionTimetables != null && !sectionTimetables.isEmpty()) {
				throw new BusinessException(BusinessMessages.getResourceBundleName(), BusinessMessages.MSG_CANNOT_DELETE_SECTION_TIMETABLE_ASSOCIATED, null);
			}

			// Check if students associated with the section
			Collection<SectionExam> sectionExams = this.sectionExamService.findSectionExamsBySectionId(section.getId());
			if (sectionExams != null && !sectionExams.isEmpty()) {
				throw new BusinessException(BusinessMessages.getResourceBundleName(), BusinessMessages.MSG_CANNOT_DELETE_SECTION_TIMETABLE_ASSOCIATED, null);
			}

			// Check if students associated with the section
			this.sectionSubjectService.removeSectionSubjectsByScetionId(section.getId());

			this.sectionDao.remove(section);

		} catch (Throwable e) {
			throw new SystemException(e);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Section findSectionById(final Long id) {

		return this.sectionDao.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Section> findAllSections() {
		return this.sectionDao.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Section> findAllSectionsByKlassId(final Long id) {
		return this.sectionDao.findAllSectionsByKlassId(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Section> findActiveSectionsByKlassId(final Long id) {
		return this.sectionDao.findActiveSectionsByKlassId(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Section> findAllSectionsByBranchId(final Long id) {
		return this.sectionDao.findAllSectionsByBranchId(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Section activateSection(final Section section) {

		this.validateSection(section);

		section.setActive(true);

		return this.sectionDao.persist(section);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Section deactivateSection(final Section section) {

		section.setActive(false);

		return this.sectionDao.persist(section);
	}

	/**
	 * Validates section.
	 * 
	 * @param section
	 *            section to be validated.
	 */
	private void validateSection(final Section section) {

		Collection<SectionSubject> sectionSubjects = this.sectionSubjectService.findSectionSubjectsByScetionId(section.getId());

		if (sectionSubjects == null || sectionSubjects.isEmpty()) {
			throw new BusinessException(BusinessMessages.getResourceBundleName(),
					BusinessMessages.MSG_SECTION_CANNOT_BE_ACTIVATED_SECTIONS_SUBJECTS_ARE_NOT_DEFINED, new Object[] { section.getName() });
		}

		/*
		 * Collection<SectionTeacher> sectionTeachers =
		 * sectionTeacherService.findSectionTeachersByScetionId(section
		 * .getId());
		 * if (sectionTeachers == null || sectionTeachers.isEmpty()) {
		 * 
		 * throw new BusinessException(BusinessMessages.getResourceBundleName(),
		 * BusinessMessages.
		 * MSG_SECTION_CANNOT_BE_ACTIVATED_SECTION_TEACHERS_NOT_DEFINED, new
		 * Object[] {
		 * section.getName(), section.getKlass().getName() });
		 * }
		 */
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Section> findActiveSectionsByKlassIdAndAcademicYearId(final Long klassId, final Long academicYearId) {
		return this.sectionDao.findActiveSectionsByKlassIdAndAcademicYearId(klassId, academicYearId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<SectionExam> findSectionExamsBySectionId(final Long sectionId) {
		return this.sectionExamDao.findSectionExamsBySectionId(sectionId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Section> findActiveAndExamUnAssignedSectionsByKlassIdAndExamId(final Long klassId, final Long examId) {

		this.sectionExamDao.findSectionExamsByKlassIdAndExamId(klassId, examId);

		this.examDao.findById(examId);

		// TODO Pradeep
		// Collection<Section> activeSections =
		// this.sectionDao.findActiveSectionsByKlassIdAndAcademicYearId(klassId,
		// exam.getAcademicYear().getId());
		//
		// for (SectionExam sectionExam : sectionExams) {
		//
		// if (activeSections.contains(sectionExam.getSection())) {
		// activeSections.remove(sectionExam.getSection());
		// }
		//
		// }
		//
		// return activeSections;
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Section> findExamAssignedSectionsByKlassIdAndExamId(final Long klassId, final Long examId) {
		return this.sectionExamDao.findSectionsByKlassIdAndExamId(klassId, examId);
	}

	/**
	 * {@inheritDoc}
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validateSectionForAcademicYearValidation(final AcademicYear academicYear, final Klass klass) throws BusinessException {
		// Validate class had sections defined.
		Collection<Section> sections = this.sectionDao.findActiveSectionsByKlassIdAndAcademicYearId(klass.getId(), academicYear.getId());

		if (sections == null || sections.isEmpty()) {
			throw new BusinessException(BusinessMessages.getResourceBundleName(),
					BusinessMessages.MSG_ACADEMIC_YEAR_CANNOT_NOT_BE_ACTIVATED_SECTIONS_ARE_NOT_DEFINED_FOR_KLASS_FOR_ACADEMIC_YEAR, new Object[] {
							academicYear.getDisplayLabel(), klass.getName() });
		} else {
			for (Section section : sections) {
				this.validateSection(section);
			}

		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Section> findAllSectionsByAcademicYearId(final Long academicYearId) {
		return this.sectionDao.findAllSectionsByAcademicYearId(academicYearId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Section> findAllSectionsByAcademicYearIdAndStatus(final Long academicYearId, final boolean status) {
		return this.sectionDao.findAllSectionsByAcademicYearIdAndStatus(academicYearId, status);
	}

	/**
	 * Creates all necessary entities for new section.
	 * 
	 * @param section
	 */
	private void postCreateNewSection(final Section section) {
		Collection<Subject> subjects = this.subjectService.findAllSubjectsByKlassId(section.getKlass().getId());
		SectionSubject sectionSubject = null;
		for (Subject subject : subjects) {
			if (!subject.isElective()) {
				sectionSubject = new SectionSubject();
				sectionSubject.setSection(section);
				sectionSubject.setSubject(subject);
				this.sectionSubjectService.saveSectionSubject(sectionSubject);
			}
		}
	}

	@Override
	public Collection<Section> findActiveSectionsByKlassIdsAndAcademicYearId(final Collection<Long> klassIds, final Long academicYearId) {
		return this.sectionDao.findActiveSectionsByKlassIdsAndAcademicYearId(klassIds, academicYearId);
	}

	@Override
	public Collection<Section> findAllSectionsByKlassIdAndAcademicYearId(final Long klassId, final Long academicYearId) {
		return this.sectionDao.findAllSectionsByKlassIdAndAcademicYearId(klassId, academicYearId);
	}

	@Override
	public Collection<Section> findSectionsBySearchCriteria(final SectionSearchCriteria sectionSearchCriteria) {
		Collection<Section> sections = null;
		if (sectionSearchCriteria.getKlass() != null) {
			sections = this.sectionDao.findAllSectionsByKlassIdAndAcademicYearId(sectionSearchCriteria.getKlass().getId(), sectionSearchCriteria
					.getAcademicYear().getId());
		} else {
			sections = this.sectionDao.findAllSectionsByAcademicYearId(sectionSearchCriteria.getAcademicYear().getId());
		}
		return sections;
	}

}
