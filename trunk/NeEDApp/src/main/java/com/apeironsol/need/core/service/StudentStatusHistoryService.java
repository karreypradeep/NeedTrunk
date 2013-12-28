package com.apeironsol.need.core.service;

import java.util.Collection;

import com.apeironsol.need.core.model.StudentStatusHistory;

public interface StudentStatusHistoryService {

	Collection<StudentStatusHistory> findStudentStatusHistoryByStudentId(final Long studentId);

	StudentStatusHistory saveStudentStatusHistory(final StudentStatusHistory studentStatusHistory);

	void removeStudentStatusHistory(final StudentStatusHistory studentStatusHistory);

}
