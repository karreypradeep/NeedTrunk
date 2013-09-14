package com.apeironsol.nexed.core.service;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.nexed.core.dao.StudentStatusHistoryDao;
import com.apeironsol.nexed.core.model.StudentStatusHistory;

@Service("studentStatusHistoryService")
@Transactional
public class StudentStatusHistoryServiceImpl implements StudentStatusHistoryService {

	@Resource
	StudentStatusHistoryDao	studentStatusHistoryDao;

	@Override
	public Collection<StudentStatusHistory> findStudentStatusHistoryByStudentId(final Long studentId) {

		return this.studentStatusHistoryDao.findStudentStatusHistoryByStudentId(studentId);
	}

}
