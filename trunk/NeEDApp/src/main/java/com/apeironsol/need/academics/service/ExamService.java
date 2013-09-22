package com.apeironsol.need.academics.service;

import java.util.Collection;

import com.apeironsol.need.academics.dataobject.SectionExamDO;
import com.apeironsol.need.academics.model.Exam;
import com.apeironsol.need.academics.model.SectionExam;
import com.apeironsol.need.academics.model.SectionExamSubject;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.SystemException;

public interface ExamService {

	Exam saveExam(Exam exam);

	void removeExam(Exam exam);

	Collection<Exam> findExamsByBranchId(Long id);

	Exam findExamById(Long id) throws BusinessException;

	void scheduleExam(SectionExam sectionExam, Collection<SectionExamSubject> sectionExamSubjects) throws BusinessException, SystemException;

	Collection<SectionExamDO> findSectionExamsByBranchId(Long branchId) throws BusinessException, SystemException;

	Collection<SectionExamDO> findSectionExamsByBranchIdAndExamId(Long branchId, Long examId) throws BusinessException, SystemException;

	void unScheduleExam(SectionExam sectionExam) throws BusinessException, SystemException;

	/**
	 * Find exams by branch id and exam type building block type id.
	 * 
	 * @param branchId
	 * @param examTypeBuildingBlockId
	 * @return
	 */
	Collection<Exam> findExamsByBranchIdAndExamTypeBuildingBlockId(Long branchId, final Long examTypeBuildingBlockId);

}
