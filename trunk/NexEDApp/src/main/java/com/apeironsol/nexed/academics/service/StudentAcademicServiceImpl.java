package com.apeironsol.nexed.academics.service;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.nexed.academics.dao.SectionExamDao;
import com.apeironsol.nexed.academics.dao.SectionExamSubjectDao;
import com.apeironsol.nexed.academics.dao.StudentExamSubjectDao;
import com.apeironsol.nexed.academics.dataobject.StudentAcademicExamDO;
import com.apeironsol.nexed.academics.dataobject.StudentAcademicSubjectDO;
import com.apeironsol.nexed.academics.dataobject.StudentExamSubjectDO;
import com.apeironsol.nexed.academics.model.SectionExam;
import com.apeironsol.nexed.academics.model.SectionExamSubject;
import com.apeironsol.nexed.academics.model.StudentExamSubject;
import com.apeironsol.nexed.core.dao.StudentSectionDao;
import com.apeironsol.nexed.core.dao.SubjectDao;
import com.apeironsol.nexed.core.model.Section;
import com.apeironsol.nexed.core.model.StudentSection;
import com.apeironsol.nexed.core.model.Subject;

@Service
@Transactional
public class StudentAcademicServiceImpl implements StudentAcademicService {

	@Resource
	private SubjectDao				subjectDao;

	@Resource
	private SectionExamDao			sectionExamDao;

	@Resource
	private SectionExamSubjectDao	sectionExamSubjectDao;

	@Resource
	private StudentExamSubjectDao	studentExamSubjectDao;

	@Resource
	private StudentSectionDao		studentSectionDao;

	@Override
	public Collection<StudentAcademicExamDO> getStudentAcademicDetailsByExamWise(final Long studentAcademicYearId) {

		Collection<StudentSection> studentSections = this.studentSectionDao.findStudentSectionByStudendAcademicYearId(studentAcademicYearId);

		Collection<StudentAcademicExamDO> studentAcademicExamDOs = new ArrayList<StudentAcademicExamDO>();

		for (StudentSection studentSection : studentSections) {

			Collection<SectionExam> sectionExams = this.sectionExamDao.findSectionExamsBySectionId(studentSection.getSection().getId());

			for (SectionExam sectionExam : sectionExams) {

				Collection<SectionExamSubject> sectionExamSubjects = this.sectionExamSubjectDao.findSectionExamSubjectsBySectionExamId(sectionExam.getId());

				int totalSubjectsScheduled = sectionExamSubjects.size();

				double totalMaximumMarks = 0d;

				double totalScoredMarks = 0d;

				boolean failed = false;

				Collection<StudentExamSubjectDO> studentExamSubjectDOs = new ArrayList<StudentExamSubjectDO>();

				for (SectionExamSubject sectionExamSubject : sectionExamSubjects) {

					StudentExamSubjectDO studentExamSubjectDO = new StudentExamSubjectDO();

					StudentExamSubject studentExamSubject = this.studentExamSubjectDao.findStudentExamSubjectByStudentAcademicYearIdAndSectionExamSubjectId(
							studentAcademicYearId, sectionExamSubject.getId());

					studentExamSubjectDO.setSubject(sectionExamSubject.getSectionSubject().getSubject());

					studentExamSubjectDO.setSectionExamSubject(sectionExamSubject);

					studentExamSubjectDO.setStudentExamSubject(studentExamSubject);

					studentExamSubjectDOs.add(studentExamSubjectDO);

					if (studentExamSubject.getScoredMarks() != null && studentExamSubject.getScoredMarks() < sectionExamSubject.getPassMarks()) {
						failed = true;
					}

					totalMaximumMarks = totalMaximumMarks + sectionExamSubject.getMaximumMarks();

					totalScoredMarks = totalScoredMarks + (studentExamSubject.getScoredMarks() != null ? studentExamSubject.getScoredMarks() : 0d);
				}

				StudentAcademicExamDO studentAcademicExamDO = new StudentAcademicExamDO();

				studentAcademicExamDO.setExam(sectionExam.getExam());

				studentAcademicExamDO.setSectionExam(sectionExam);

				studentAcademicExamDO.setTotalMaximumMarks(totalMaximumMarks);

				studentAcademicExamDO.setTotalScoredMarks(totalScoredMarks);

				studentAcademicExamDO.setTotalSubjectsScheduled(totalSubjectsScheduled);

				studentAcademicExamDO.setPercentageScored(totalScoredMarks / totalMaximumMarks);

				studentAcademicExamDO.setFailed(failed);

				studentAcademicExamDO.setStudentExamSubjectDOs(studentExamSubjectDOs);

				studentAcademicExamDOs.add(studentAcademicExamDO);

			}

		}

		return studentAcademicExamDOs;
	}

	@Override
	public Collection<StudentAcademicSubjectDO> getStudentAcademicDetailsBySubjectWise(final Long studentAcademicYearId) {

		Collection<StudentSection> studentSections = this.studentSectionDao.findStudentSectionByStudendAcademicYearId(studentAcademicYearId);

		Collection<Section> sections = new ArrayList<Section>();

		for (StudentSection studentSection : studentSections) {
			sections.add(studentSection.getSection());
		}

		Collection<Subject> subjects = this.subjectDao.findDistenctSubjectsAmongSections(sections);

		Collection<StudentAcademicSubjectDO> studentAcademicSubjectDOs = new ArrayList<StudentAcademicSubjectDO>();

		for (Subject subject : subjects) {

			double totalMaximumMarks = 0d;

			double totalPassMarks = 0d;

			double totalScoredMarks = 0d;

			Collection<StudentExamSubject> studentExamSubjects = this.studentExamSubjectDao.findStudentExamSubjectsBySubjectIdAndStudentAcademicYearId(
					subject.getId(), studentAcademicYearId);

			Collection<StudentExamSubjectDO> studentExamSubjectDOs = new ArrayList<StudentExamSubjectDO>();

			for (StudentExamSubject studentExamSubject : studentExamSubjects) {

				StudentExamSubjectDO studentExamSubjectDO = new StudentExamSubjectDO();

				studentExamSubjectDO.setSectionExamSubject(studentExamSubject.getSectionExamSubject());

				studentExamSubjectDO.setStudentExamSubject(studentExamSubject);

				studentExamSubjectDO.setExam(studentExamSubject.getSectionExamSubject().getSectionExam().getExam());

				studentExamSubjectDOs.add(studentExamSubjectDO);

				totalMaximumMarks = totalMaximumMarks + studentExamSubject.getSectionExamSubject().getMaximumMarks();

				totalPassMarks = totalPassMarks + studentExamSubject.getSectionExamSubject().getPassMarks();

				totalScoredMarks = totalScoredMarks + (studentExamSubject.getScoredMarks() != null ? studentExamSubject.getScoredMarks() : 0d);

			}

			StudentAcademicSubjectDO studentAcademicSubjectDO = new StudentAcademicSubjectDO();

			studentAcademicSubjectDO.setSubject(subject);

			studentAcademicSubjectDO.setStudentExamSubjectDOs(studentExamSubjectDOs);

			studentAcademicSubjectDO.setTotalExamTakenCount(studentExamSubjectDOs.size());

			studentAcademicSubjectDO.setTotalPassMarks(totalPassMarks);

			studentAcademicSubjectDO.setTotalScoredMarks(totalScoredMarks);

			studentAcademicSubjectDO.setTotalMaximumMarks(totalMaximumMarks);

			studentAcademicSubjectDOs.add(studentAcademicSubjectDO);

		}

		return studentAcademicSubjectDOs;

	}
}
