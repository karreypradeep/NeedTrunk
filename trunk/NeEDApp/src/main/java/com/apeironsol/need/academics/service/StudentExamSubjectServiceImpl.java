package com.apeironsol.need.academics.service;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.academics.dao.StudentExamSubjectDao;
import com.apeironsol.need.academics.model.StudentExamSubject;
import com.apeironsol.need.util.constants.StudentExamSubjectStatusConstant;
import com.apeironsol.framework.exception.ApplicationException;

@Service("studentExamSubjectService")
@Transactional
public class StudentExamSubjectServiceImpl implements StudentExamSubjectService {

	@Resource
	private StudentExamSubjectDao	studentExamSubjectDao;

	@Override
	public Collection<StudentExamSubject> findStudentExamSubjectsByStudentIdAndSubjectExamId(final Long sectionExamSubjectId) {
		return this.studentExamSubjectDao.findStudentExamSubjectsBySectionExamSubjectId(sectionExamSubjectId);
	}

	@Override
	public Collection<StudentExamSubject> saveStudentExamSubjects(final Collection<StudentExamSubject> studentExamSubjects) throws ApplicationException {
		Collection<StudentExamSubject> result = new ArrayList<StudentExamSubject>();
		for (StudentExamSubject studentExamSubject : studentExamSubjects) {
			if (studentExamSubject.getScoredMarks() != null) {
				this.validateStudentExamSubject(studentExamSubject);
			}
			result.add(this.studentExamSubjectDao.persist(studentExamSubject));
		}
		return result;
	}

	private void validateStudentExamSubject(final StudentExamSubject studentExamSubject) throws ApplicationException {
		if (studentExamSubject.getScoredMarks() > 0 && !studentExamSubject.getStudentExamSubjectStatus().equals(StudentExamSubjectStatusConstant.TAKEN)) {
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
}
