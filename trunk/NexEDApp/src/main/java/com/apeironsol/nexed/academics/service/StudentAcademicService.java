package com.apeironsol.nexed.academics.service;

import java.util.Collection;

import com.apeironsol.nexed.academics.dataobject.StudentAcademicExamDO;
import com.apeironsol.nexed.academics.dataobject.StudentAcademicSubjectDO;

public interface StudentAcademicService {

	Collection<StudentAcademicExamDO> getStudentAcademicDetailsByExamWise(Long studentAcademicYearId);

	Collection<StudentAcademicSubjectDO> getStudentAcademicDetailsBySubjectWise(Long studentAcademicYearId);

}
