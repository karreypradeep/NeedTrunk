package com.apeironsol.need.core.dao;

import java.util.Collection;

import com.apeironsol.need.core.model.StudentStatusHistory;
import com.apeironsol.framework.BaseDao;

public interface StudentStatusHistoryDao extends BaseDao<StudentStatusHistory> {

	/**
	 * Find student status history by student id.
	 * 
	 * @param studentId
	 *            student id.
	 * @return collection of student status history by student id.
	 */
	Collection<StudentStatusHistory> findStudentStatusHistoryByStudentId(final Long studentId);

}
