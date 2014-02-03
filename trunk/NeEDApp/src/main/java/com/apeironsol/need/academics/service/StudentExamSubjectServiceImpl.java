package com.apeironsol.need.academics.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.need.academics.dao.StudentExamSubjectDao;
import com.apeironsol.need.academics.dataobject.StudentExamAllSubjectsDO;
import com.apeironsol.need.academics.model.SectionExamSubject;
import com.apeironsol.need.academics.model.StudentExamSubject;
import com.apeironsol.need.util.constants.StudentExamSubjectStatusConstant;

@Service("studentExamSubjectService")
@Transactional(rollbackFor = Exception.class)
public class StudentExamSubjectServiceImpl implements StudentExamSubjectService {

	@Resource
	private StudentExamSubjectDao		studentExamSubjectDao;

	@Resource
	private SectionExamSubjectService	sectionExamSubjectService;

	@Override
	public Collection<StudentExamSubject> findStudentExamSubjectsByStudentIdAndSubjectExamId(final Long sectionExamSubjectId) {
		return this.studentExamSubjectDao.findStudentExamSubjectsBySectionExamSubjectId(sectionExamSubjectId);
	}

	@Override
	public Collection<StudentExamSubject> saveStudentExamSubjects(final Collection<StudentExamSubject> studentExamSubjects) throws ApplicationException {
		final Collection<StudentExamSubject> result = new ArrayList<StudentExamSubject>();
		for (final StudentExamSubject studentExamSubject : studentExamSubjects) {
			if (studentExamSubject.getScoredMarks() != null) {
				validateStudentExamSubject(studentExamSubject);
			}
			result.add(this.studentExamSubjectDao.persist(studentExamSubject));
		}
		return result;
	}

	private void validateStudentExamSubject(final StudentExamSubject studentExamSubject) throws ApplicationException {
		if ((studentExamSubject.getScoredMarks() > 0) && !studentExamSubject.getStudentExamSubjectStatus().equals(StudentExamSubjectStatusConstant.TAKEN)) {
			throw new ApplicationException("Exam status for student  " + studentExamSubject.getStudentAcademicYear().getStudent().getDisplayName()
					+ " is not taken. Please change the status if student has taken exam.");
		}
		if (studentExamSubject.getScoredMarks() < 0) {
			throw new ApplicationException("Marks obtained cannot be less than 0 for student "
					+ studentExamSubject.getStudentAcademicYear().getStudent().getDisplayName());
		}
		if (studentExamSubject.getScoredMarks() > studentExamSubject.getSectionExamSubject().getMaximumMarks()) {
			throw new ApplicationException("Marks obtained cannot be more than maximum marks " + studentExamSubject.getSectionExamSubject().getMaximumMarks()
					+ "  for student " + studentExamSubject.getStudentAcademicYear().getStudent().getDisplayName());
		}
	}

	@Override
	public Collection<StudentExamAllSubjectsDO> findStudentExamAllSubjectsDOsBySubjectExamId(final Long sectionExamId) {
		final Map<Long, StudentExamAllSubjectsDO> studentExamAllSubjectsDOMap = new HashMap<Long, StudentExamAllSubjectsDO>();
		final Collection<SectionExamSubject> sectionExamSubjects = this.sectionExamSubjectService.findSectionExamSubjectsBySubjectExamId(sectionExamId);
		final Collection<Long> sectionExamSubjectIds = new ArrayList<Long>();
		for (final SectionExamSubject sectionExamSubject : sectionExamSubjects) {
			sectionExamSubjectIds.add(sectionExamSubject.getId());
		}
		final Collection<StudentExamSubject> studentExamSubjects = this.studentExamSubjectDao
				.findStudentExamAllSubjectsDOsBySubjectExamSubjectIds(sectionExamSubjectIds);
		for (final StudentExamSubject studentExamSubject : studentExamSubjects) {
			if (studentExamAllSubjectsDOMap.get(studentExamSubject.getStudentAcademicYear().getId()) != null) {
				studentExamAllSubjectsDOMap.get(studentExamSubject.getStudentAcademicYear().getId()).addStudentExamSubject(studentExamSubject);
			} else {
				final StudentExamAllSubjectsDO studentExamAllSubjectsDO = new StudentExamAllSubjectsDO();
				studentExamAllSubjectsDO.setSectionExam(studentExamSubject.getSectionExamSubject().getSectionExam());
				studentExamAllSubjectsDO.setStudentAcademicYear(studentExamSubject.getStudentAcademicYear());
				studentExamAllSubjectsDO.addStudentExamSubject(studentExamSubject);
				studentExamAllSubjectsDOMap.put(studentExamSubject.getStudentAcademicYear().getId(), studentExamAllSubjectsDO);
			}
		}
		final Collection<StudentExamAllSubjectsDO> result = new ArrayList<StudentExamAllSubjectsDO>();
		for (final StudentExamAllSubjectsDO studentExamAllSubjectsDO : studentExamAllSubjectsDOMap.values()) {
			result.add(studentExamAllSubjectsDO);
		}
		return result;
	}

	@Override
	public Collection<StudentExamAllSubjectsDO> saveStudentExamAllSubjectsDOs(final Collection<StudentExamAllSubjectsDO> studentExamAllSubjectsDOs)
			throws ApplicationException {
		Long sectionExamId = null;
		for (final StudentExamAllSubjectsDO studentExamAllSubjectsDO : studentExamAllSubjectsDOs) {
			if (sectionExamId == null) {
				sectionExamId = studentExamAllSubjectsDO.getSectionExam().getId();
			}
			for (final StudentExamSubject studentExamSubject : studentExamAllSubjectsDO.getStudentExamSubjects()) {
				if (studentExamSubject.getScoredMarks() != null) {
					validateStudentExamSubject(studentExamSubject);
				}
				this.studentExamSubjectDao.persist(studentExamSubject);
			}
		}
		return findStudentExamAllSubjectsDOsBySubjectExamId(sectionExamId);
	}

	@Override
	public Collection<StudentExamSubject> findAbsentStudentSubjectsForSectionExamIds(final Collection<Long> sectionExamIds) throws ApplicationException {
		return this.studentExamSubjectDao.findAbsentStudentSubjectsForSectionExamIds(sectionExamIds);
	}

	@Override
	public Collection<StudentExamSubject> findStudentExamSubjectsByStudentAcademicYearIdAndExamId(final Long studentAcademicYearId, final Long examId) {
		return this.studentExamSubjectDao.findStudentExamSubjectsByStudentAcademicYearIdAndExamId(studentAcademicYearId, examId);
	}

	@Override
	public Collection<StudentExamSubject> findAllStudentExamSubjectsForSectionExamIds(final Collection<Long> sectionExamIds) throws ApplicationException {
		return this.studentExamSubjectDao.findAllStudentExamSubjectsForSectionExamIds(sectionExamIds);
	}
}
