package com.apeironsol.need.academics.service;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;
import com.apeironsol.framework.exception.SystemException;
import com.apeironsol.need.academics.dao.ExamDao;
import com.apeironsol.need.academics.dao.SectionExamDao;
import com.apeironsol.need.academics.dao.SectionExamSubjectDao;
import com.apeironsol.need.academics.dao.StudentExamSubjectDao;
import com.apeironsol.need.academics.dataobject.SectionExamDO;
import com.apeironsol.need.academics.model.Exam;
import com.apeironsol.need.academics.model.SectionExam;
import com.apeironsol.need.academics.model.SectionExamSubject;
import com.apeironsol.need.academics.model.StudentExamSubject;
import com.apeironsol.need.core.model.Student;
import com.apeironsol.need.core.model.StudentAcademicYear;
import com.apeironsol.need.core.service.StudentAcademicYearService;
import com.apeironsol.need.core.service.StudentService;
import com.apeironsol.need.util.constants.SectionExamStatusConstant;
import com.apeironsol.need.util.constants.StudentExamSubjectStatusConstant;

@Service("examService")
@Transactional(rollbackFor = Exception.class)
public class ExamServiceImpl implements ExamService {

	@Resource
	private ExamDao						examDao;

	@Resource
	private SectionExamDao				sectionExamDao;

	@Resource
	private SectionExamSubjectDao		sectionExamSubjectDao;

	@Resource
	private StudentExamSubjectDao		studentExamSubjectDao;

	@Resource
	private StudentService				studentService;

	@Resource
	private StudentAcademicYearService	studentAcademicYearService;

	@Override
	public Exam saveExam(final Exam exam) throws BusinessException, InvalidArgumentException {
		exam.validate();
		return this.examDao.persist(exam);
	}

	@Override
	public Exam findExamById(final Long id) throws BusinessException {
		return this.examDao.findById(id);
	}

	@Override
	public void removeExam(final Exam exam) throws BusinessException {

		this.examDao.remove(exam);
	}

	@Override
	public Collection<Exam> findExamsByBranchId(final Long branchId) throws BusinessException {
		return this.examDao.findExamsByBranchId(branchId);
	}

	@Override
	public void unScheduleExam(final SectionExam sectionExam) throws BusinessException, SystemException {

		Collection<StudentExamSubject> studentExamSubjects = this.studentExamSubjectDao.findStudentExamSubjectBySectionExamIdAndScoredMarksNotNull(sectionExam
				.getId());

		if ((studentExamSubjects != null) && !studentExamSubjects.isEmpty()) {
			throw new BusinessException("Exam cannot be un-scheduled because , this exam is already evalvated and scored marks are already entered.");
		}

		studentExamSubjects = this.studentExamSubjectDao.findStudentExamSubjectsBySectionExamId(sectionExam.getId());

		for (final StudentExamSubject studentExamSubject : studentExamSubjects) {
			this.studentExamSubjectDao.remove(studentExamSubject);
		}

		final Collection<SectionExamSubject> sectionExamSubjects = this.sectionExamSubjectDao.findSectionExamSubjectsBySectionExamId(sectionExam.getId());
		for (final SectionExamSubject sectionExamSubject : sectionExamSubjects) {
			this.sectionExamSubjectDao.remove(sectionExamSubject);
		}
		this.sectionExamDao.remove(sectionExam);

	}

	@Override
	public void scheduleExam(final SectionExam sectionExam, final Collection<SectionExamSubject> sectionExamSubjects) throws BusinessException,
			SystemException, InvalidArgumentException {

		final Collection<SectionExamSubject> toBeSavedSectionExamSubjects = new ArrayList<SectionExamSubject>();
		for (final SectionExamSubject sectionExamSubject : sectionExamSubjects) {
			if ((sectionExamSubject.getScheduledDate() != null) || (sectionExamSubject.getStartTime() != null) || (sectionExamSubject.getEndTime() != null)
					|| (sectionExamSubject.getPassMarks() != null) || (sectionExamSubject.getMaximumMarks() != null)) {
				toBeSavedSectionExamSubjects.add(sectionExamSubject);
			}
		}

		if (toBeSavedSectionExamSubjects.isEmpty()) {
			throw new BusinessException("Scheduling details are requried for atleast one subject.");
		}

		sectionExam.setSectionExamStatus(SectionExamStatusConstant.SCHEDULED);

		for (final SectionExamSubject sectionExamSubject : toBeSavedSectionExamSubjects) {

			validateSectionExamSubject(sectionExamSubject);

			sectionExamSubject.setSectionExam(sectionExam);

			final SectionExamSubject sectionExamSubjectOverlaping = this.sectionExamSubjectDao
					.findSectionExamSubjectBySectionIdAndScheduledDateBetweenStartAndEndTime(sectionExam.getSection().getId(),
							sectionExamSubject.getScheduledDate(), sectionExamSubject.getStartTime(), sectionExamSubject.getEndTime());

			if (sectionExamSubjectOverlaping != null) {
				throw new BusinessException("Section exam date and times for subject ' " + sectionExamSubject.getSectionSubject().getSubject().getName()
						+ " ' are overlaping with subject ' " + sectionExamSubjectOverlaping.getSectionSubject().getSubject().getName() + " ' for exam "
						+ sectionExamSubjectOverlaping.getSectionExam().getExam().getName());
			}

		}

		if (sectionExam.getSectionExamSubjects() != null) {
			sectionExam.getSectionExamSubjects().clear();
		}

		sectionExam.setSectionExamSubjects(toBeSavedSectionExamSubjects);

		final SectionExam sectionExamLocal = this.sectionExamDao.persist(sectionExam);

		final Collection<SectionExamSubject> sectionExamSubjects2 = this.sectionExamSubjectDao.findSectionExamSubjectsBySectionExamId(sectionExamLocal.getId());

		for (final SectionExamSubject sectionExamSubject : sectionExamSubjects2) {

			final Collection<Student> students = this.studentService.findActiveStudentsBySectionId(sectionExam.getSection().getId());

			final Collection<StudentExamSubject> studentExamSubjects = new ArrayList<StudentExamSubject>();

			for (final Student student : students) {

				final StudentAcademicYear studentAcademicYear = this.studentAcademicYearService.findStudentCurrentOrMostRecentAcademicYearByStudentId(student
						.getId());

				final StudentExamSubject studentExamSubject = new StudentExamSubject();
				studentExamSubject.setStudentExamSubjectStatus(StudentExamSubjectStatusConstant.ASSIGNED);
				studentExamSubject.setSectionExamSubject(sectionExamSubject);
				studentExamSubject.setStudentAcademicYear(studentAcademicYear);
				studentExamSubjects.add(studentExamSubject);

				this.studentExamSubjectDao.persist(studentExamSubject);
			}

		}

	}

	private void validateSectionExamSubject(final SectionExamSubject sectionExamSubject) {
		if ((sectionExamSubject.getScheduledDate() != null) || (sectionExamSubject.getStartTime() != null) || (sectionExamSubject.getEndTime() != null)
				|| (sectionExamSubject.getPassMarks() != null) || (sectionExamSubject.getMaximumMarks() != null)) {

		}

		if (sectionExamSubject.getScheduledDate() == null) {
			throw new InvalidArgumentException("Exam scheduled date should not be empty.");
		}

		if (sectionExamSubject.getStartTime() == null) {
			throw new InvalidArgumentException("Exam start time should not be empty.");
		}

		if (sectionExamSubject.getEndTime() == null) {
			throw new InvalidArgumentException("Exam end time should not be empty.");
		}

		if (sectionExamSubject.getEndTime().before(sectionExamSubject.getStartTime())) {
			throw new InvalidArgumentException("Exam start time  should be before end time.");
		}

		if (sectionExamSubject.getPassMarks() == null) {
			throw new InvalidArgumentException("Exam pass marks should not be empty.");
		}

		if (sectionExamSubject.getMaximumMarks() == null) {
			throw new InvalidArgumentException("Exam maximum marks  should not be empty.");
		}

		if (sectionExamSubject.getMaximumMarks() < sectionExamSubject.getPassMarks()) {
			throw new InvalidArgumentException("Exam pass marks should not be grater then maximum marks.");
		}
	}

	@Override
	public Collection<SectionExamDO> findSectionExamsByBranchId(final Long branchId) throws BusinessException, SystemException {

		final Collection<SectionExam> sectionExams = this.sectionExamDao.findSectionExamsByBranchId(branchId);

		final Collection<SectionExamDO> sectionExamDOs = new ArrayList<SectionExamDO>();

		for (final SectionExam sectionExam : sectionExams) {

			final SectionExamDO sectionExamDO = new SectionExamDO();
			sectionExamDO.setAcademicYear(sectionExam.getSection().getAcademicYear());
			sectionExamDO.setKlass(sectionExam.getSection().getKlass());
			sectionExamDO.setSection(sectionExam.getSection());
			sectionExamDO.setExam(sectionExam.getExam());
			sectionExamDO.setSectionExam(sectionExam);
			sectionExamDOs.add(sectionExamDO);

		}

		return sectionExamDOs;

	}

	@Override
	public Collection<SectionExamDO> findSectionExamsByBranchIdAndExamId(final Long branchId, final Long examId) throws BusinessException, SystemException {

		final Collection<SectionExam> sectionExams = this.sectionExamDao.findSectionExamsByBranchIdAndExamId(branchId, examId);

		final Collection<SectionExamDO> sectionExamDOs = new ArrayList<SectionExamDO>();

		for (final SectionExam sectionExam : sectionExams) {

			final SectionExamDO sectionExamDO = new SectionExamDO();
			sectionExamDO.setAcademicYear(sectionExam.getSection().getAcademicYear());
			sectionExamDO.setKlass(sectionExam.getSection().getKlass());
			sectionExamDO.setSection(sectionExam.getSection());
			sectionExamDO.setExam(sectionExam.getExam());
			sectionExamDO.setSectionExam(sectionExam);
			sectionExamDOs.add(sectionExamDO);

		}

		return sectionExamDOs;

	}

	@Override
	public Collection<Exam> findExamsByBranchIdAndExamTypeBuildingBlockId(final Long branchId, final Long examTypeBuildingBlockId) {
		return this.examDao.findExamsByBranchIdAndExamTypeBuildingBlockId(branchId, examTypeBuildingBlockId);
	}
}
