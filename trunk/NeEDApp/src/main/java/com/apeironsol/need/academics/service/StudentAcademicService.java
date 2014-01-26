package com.apeironsol.need.academics.service;

import java.util.Collection;

import com.apeironsol.need.academics.dataobject.StudentAcademicExamDO;
import com.apeironsol.need.academics.dataobject.StudentAcademicSubjectDO;

public interface StudentAcademicService {

	Collection<StudentAcademicExamDO> getStudentAcademicDetailsByExamWise(Long studentAcademicYearId);

	Collection<StudentAcademicSubjectDO> getStudentAcademicDetailsBySubjectWise(Long studentAcademicYearId);

	Collection<StudentAcademicExamDO> getStudentAcademicDetailsByExams(final Long studentAcademicYearId, final Collection<Long> examIds);
}
