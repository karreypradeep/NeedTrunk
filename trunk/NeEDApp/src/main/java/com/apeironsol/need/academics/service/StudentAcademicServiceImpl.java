package com.apeironsol.need.academics.service;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.academics.dao.SectionExamDao;
import com.apeironsol.need.academics.dao.SectionExamSubjectDao;
import com.apeironsol.need.academics.dao.StudentExamSubjectDao;
import com.apeironsol.need.academics.dataobject.StudentAcademicExamDO;
import com.apeironsol.need.academics.dataobject.StudentAcademicSubjectDO;
import com.apeironsol.need.academics.dataobject.StudentExamSubjectDO;
import com.apeironsol.need.academics.model.SectionExam;
import com.apeironsol.need.academics.model.SectionExamSubject;
import com.apeironsol.need.academics.model.StudentExamSubject;
import com.apeironsol.need.core.dao.StudentSectionDao;
import com.apeironsol.need.core.dao.SubjectDao;
import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.core.model.StudentSection;
import com.apeironsol.need.core.model.Subject;

@Service
@Transactional(rollbackFor = Exception.class)
public class StudentAcademicServiceImpl implements StudentAcademicService {

	@Resource
	private SectionExamDao			sectionExamDao;

	@Resource
	private SectionExamSubjectDao	sectionExamSubjectDao;

	@Resource
	private StudentExamSubjectDao	studentExamSubjectDao;

	@Resource
	private StudentSectionDao		studentSectionDao;

	@Resource
	private SubjectDao				subjectDao;

	@Override
	public Collection<StudentAcademicExamDO> getStudentAcademicDetailsByExams(final Long studentAcademicYearId, final Collection<Long> examIds) {
		final Collection<StudentSection> studentSections = this.studentSectionDao.findStudentSectionByStudendAcademicYearId(studentAcademicYearId);
		final Collection<StudentAcademicExamDO> studentAcademicExamDOs = new ArrayList<StudentAcademicExamDO>();
		for (final StudentSection studentSection : studentSections) {
			final Collection<SectionExam> sectionExams = this.sectionExamDao.findSectionExamsBySectionId(studentSection.getSection().getId());
			for (final SectionExam sectionExam : sectionExams) {
				if (examIds.contains(sectionExam.getExam().getId())) {
					final Collection<SectionExamSubject> sectionExamSubjects = this.sectionExamSubjectDao.findSectionExamSubjectsBySectionExamId(sectionExam
							.getId());
					final int totalSubjectsScheduled = sectionExamSubjects.size();
					double totalMaximumMarks = 0d;
					double totalScoredMarks = 0d;
					boolean failed = false;
					final Collection<StudentExamSubjectDO> studentExamSubjectDOs = new ArrayList<StudentExamSubjectDO>();
					for (final SectionExamSubject sectionExamSubject : sectionExamSubjects) {
						final StudentExamSubject studentExamSubject = this.studentExamSubjectDao
								.findStudentExamSubjectByStudentAcademicYearIdAndSectionExamSubjectId(studentAcademicYearId, sectionExamSubject.getId());
						if (studentExamSubject != null) {
							final StudentExamSubjectDO studentExamSubjectDO = new StudentExamSubjectDO();
							studentExamSubjectDO.setSectionExamSubject(sectionExamSubject);
							studentExamSubjectDO.setStudentExamSubject(studentExamSubject);
							studentExamSubjectDOs.add(studentExamSubjectDO);
							if ((studentExamSubject.getScoredMarks() != null) && (studentExamSubject.getScoredMarks() < sectionExamSubject.getPassMarks())) {
								failed = true;
							}
							totalMaximumMarks = totalMaximumMarks + sectionExamSubject.getMaximumMarks();
							totalScoredMarks = totalScoredMarks + (studentExamSubject.getScoredMarks() != null ? studentExamSubject.getScoredMarks() : 0d);
						}
					}

					final StudentAcademicExamDO studentAcademicExamDO = new StudentAcademicExamDO();
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
		}
		return studentAcademicExamDOs;
	}

	@Override
	public Collection<StudentAcademicExamDO> getStudentAcademicDetailsByExamWise(final Long studentAcademicYearId) {

		final Collection<StudentSection> studentSections = this.studentSectionDao.findStudentSectionByStudendAcademicYearId(studentAcademicYearId);

		final Collection<StudentAcademicExamDO> studentAcademicExamDOs = new ArrayList<StudentAcademicExamDO>();

		for (final StudentSection studentSection : studentSections) {

			final Collection<SectionExam> sectionExams = this.sectionExamDao.findSectionExamsBySectionId(studentSection.getSection().getId());

			for (final SectionExam sectionExam : sectionExams) {

				final Collection<SectionExamSubject> sectionExamSubjects = this.sectionExamSubjectDao.findSectionExamSubjectsBySectionExamId(sectionExam
						.getId());

				final int totalSubjectsScheduled = sectionExamSubjects.size();

				double totalMaximumMarks = 0d;

				double totalScoredMarks = 0d;

				boolean failed = false;

				final Collection<StudentExamSubjectDO> studentExamSubjectDOs = new ArrayList<StudentExamSubjectDO>();

				for (final SectionExamSubject sectionExamSubject : sectionExamSubjects) {

					final StudentExamSubject studentExamSubject = this.studentExamSubjectDao
							.findStudentExamSubjectByStudentAcademicYearIdAndSectionExamSubjectId(studentAcademicYearId, sectionExamSubject.getId());
					if (studentExamSubject != null) {
						final StudentExamSubjectDO studentExamSubjectDO = new StudentExamSubjectDO();

						studentExamSubjectDO.setSectionExamSubject(sectionExamSubject);

						studentExamSubjectDO.setStudentExamSubject(studentExamSubject);

						studentExamSubjectDOs.add(studentExamSubjectDO);

						if ((studentExamSubject.getScoredMarks() != null) && (studentExamSubject.getScoredMarks() < sectionExamSubject.getPassMarks())) {
							failed = true;
						}

						totalMaximumMarks = totalMaximumMarks + sectionExamSubject.getMaximumMarks();

						totalScoredMarks = totalScoredMarks + (studentExamSubject.getScoredMarks() != null ? studentExamSubject.getScoredMarks() : 0d);
					}
				}

				final StudentAcademicExamDO studentAcademicExamDO = new StudentAcademicExamDO();

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

		final Collection<StudentSection> studentSections = this.studentSectionDao.findStudentSectionByStudendAcademicYearId(studentAcademicYearId);

		final Collection<Section> sections = new ArrayList<Section>();

		for (final StudentSection studentSection : studentSections) {
			sections.add(studentSection.getSection());
		}

		final Collection<Subject> subjects = this.subjectDao.findDistenctSubjectsAmongSections(sections);

		final Collection<StudentAcademicSubjectDO> studentAcademicSubjectDOs = new ArrayList<StudentAcademicSubjectDO>();

		for (final Subject subject : subjects) {

			double totalMaximumMarks = 0d;

			double totalPassMarks = 0d;

			double totalScoredMarks = 0d;

			final Collection<StudentExamSubject> studentExamSubjects = this.studentExamSubjectDao.findStudentExamSubjectsBySubjectIdAndStudentAcademicYearId(
					subject.getId(), studentAcademicYearId);

			final Collection<StudentExamSubjectDO> studentExamSubjectDOs = new ArrayList<StudentExamSubjectDO>();

			for (final StudentExamSubject studentExamSubject : studentExamSubjects) {

				final StudentExamSubjectDO studentExamSubjectDO = new StudentExamSubjectDO();

				studentExamSubjectDO.setSectionExamSubject(studentExamSubject.getSectionExamSubject());

				studentExamSubjectDO.setStudentExamSubject(studentExamSubject);

				studentExamSubjectDO.setExam(studentExamSubject.getSectionExamSubject().getSectionExam().getExam());

				studentExamSubjectDOs.add(studentExamSubjectDO);

				totalMaximumMarks = totalMaximumMarks + studentExamSubject.getSectionExamSubject().getMaximumMarks();

				totalPassMarks = totalPassMarks + studentExamSubject.getSectionExamSubject().getPassMarks();

				totalScoredMarks = totalScoredMarks + (studentExamSubject.getScoredMarks() != null ? studentExamSubject.getScoredMarks() : 0d);

			}

			final StudentAcademicSubjectDO studentAcademicSubjectDO = new StudentAcademicSubjectDO();

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
