package com.apeironsol.nexed.core.service;

import java.util.Collection;

import com.apeironsol.nexed.core.model.StudentStatusHistory;

public interface StudentStatusHistoryService {

	Collection<StudentStatusHistory> findStudentStatusHistoryByStudentId(final Long studentId);

}
