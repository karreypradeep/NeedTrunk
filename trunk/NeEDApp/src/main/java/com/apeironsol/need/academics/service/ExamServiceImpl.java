package com.apeironsol.need.academics.service;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;
import com.apeironsol.framework.exception.SystemException;

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

		if (studentExamSubjects != null && !studentExamSubjects.isEmpty()) {
			throw new BusinessException("Exam cannot be un-scheduled because , this exam is already evalvated and scored marks are already entered.");
		}

		studentExamSubjects = this.studentExamSubjectDao.findStudentExamSubjectsBySectionExamId(sectionExam.getId());

		for (StudentExamSubject studentExamSubject : studentExamSubjects) {
			this.studentExamSubjectDao.remove(studentExamSubject);
		}

		Collection<SectionExamSubject> sectionExamSubjects = this.sectionExamSubjectDao.findSectionExamSubjectsBySectionExamId(sectionExam.getId());

		for (SectionExamSubject studentExamSubject : sectionExamSubjects) {

			this.sectionExamSubjectDao.remove(studentExamSubject);
		}

		this.sectionExamDao.remove(sectionExam);

	}

	@Override
	public void scheduleExam(final SectionExam sectionExam, final Collection<SectionExamSubject> sectionExamSubjects) throws BusinessException,
			SystemException, InvalidArgumentException {

		sectionExam.setSectionExamStatus(SectionExamStatusConstant.SCHEDULED);

		for (SectionExamSubject sectionExamSubject : sectionExamSubjects) {

			this.validateSectionExamSubject(sectionExamSubject);

			sectionExamSubject.setSectionExam(sectionExam);

			SectionExamSubject sectionExamSubjectOverlaping = this.sectionExamSubjectDao
					.findSectionExamSubjectBySectionIdAndScheduledDateBetweenStartAndEndTime(sectionExam.getSection().getId(),
							sectionExamSubject.getScheduledDate(), sectionExamSubject.getStartTime(), sectionExamSubject.getEndTime());

			if (sectionExamSubjectOverlaping != null) {
				throw new BusinessException("Section exam date and times for subject ' " + sectionExamSubject.getSectionSubject().getSubject().getName()
						+ " ' are overlaping with subject ' " + sectionExamSubjectOverlaping.getSectionSubject().getSubject().getName() + " ' for exam "
						+ sectionExamSubjectOverlaping.getSectionExam().getExam().getName());
			}

		}

		sectionExam.setSectionExamSubjects(sectionExamSubjects);

		SectionExam sectionExamLocal = this.sectionExamDao.persist(sectionExam);

		Collection<SectionExamSubject> sectionExamSubjects2 = this.sectionExamSubjectDao.findSectionExamSubjectsBySectionExamId(sectionExamLocal.getId());

		for (SectionExamSubject sectionExamSubject : sectionExamSubjects2) {

			Collection<Student> students = this.studentService.findActiveStudentsBySectionId(sectionExam.getSection().getId());

			Collection<StudentExamSubject> studentExamSubjects = new ArrayList<StudentExamSubject>();

			for (Student student : students) {

				StudentAcademicYear studentAcademicYear = this.studentAcademicYearService
						.findStudentCurrentOrMostRecentAcademicYearByStudentId(student.getId());

				StudentExamSubject studentExamSubject = new StudentExamSubject();
				studentExamSubject.setStudentExamSubjectStatus(StudentExamSubjectStatusConstant.ASSIGNED);
				studentExamSubject.setSectionExamSubject(sectionExamSubject);
				studentExamSubject.setStudentAcademicYear(studentAcademicYear);
				studentExamSubjects.add(studentExamSubject);

				this.studentExamSubjectDao.persist(studentExamSubject);
			}

		}

	}

	private void validateSectionExamSubject(final SectionExamSubject sectionExamSubject) {
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

		Collection<SectionExam> sectionExams = this.sectionExamDao.findSectionExamsByBranchId(branchId);

		Collection<SectionExamDO> sectionExamDOs = new ArrayList<SectionExamDO>();

		for (SectionExam sectionExam : sectionExams) {

			SectionExamDO sectionExamDO = new SectionExamDO();
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

		Collection<SectionExam> sectionExams = this.sectionExamDao.findSectionExamsByBranchIdAndExamId(branchId, examId);

		Collection<SectionExamDO> sectionExamDOs = new ArrayList<SectionExamDO>();

		for (SectionExam sectionExam : sectionExams) {

			SectionExamDO sectionExamDO = new SectionExamDO();
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
