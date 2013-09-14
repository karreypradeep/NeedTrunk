package com.apeironsol.nexed.academics.dao;

import java.util.Collection;

import com.apeironsol.nexed.academics.model.Exam;
import com.apeironsol.framework.BaseDao;
  
public interface ExamDao extends BaseDao<Exam> {

	Collection<Exam> findExamsByBranchId(Long branchId);

	/**
	 * Find exams by branch id and exam type building block type id.
	 * 
	 * @param branchId
	 * @param examTypeBuildingBlockId
	 * @return
	 */
	Collection<Exam> findExamsByBranchIdAndExamTypeBuildingBlockId(Long branchId, final Long examTypeBuildingBlockId);
}
