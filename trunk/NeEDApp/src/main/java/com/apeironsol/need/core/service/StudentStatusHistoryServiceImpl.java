package com.apeironsol.need.core.service;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.dao.StudentStatusHistoryDao;
import com.apeironsol.need.core.model.StudentStatusHistory;

@Service("studentStatusHistoryService")
@Transactional(rollbackFor = Exception.class)
public class StudentStatusHistoryServiceImpl implements StudentStatusHistoryService {

	@Resource
	StudentStatusHistoryDao	studentStatusHistoryDao;

	@Override
	public Collection<StudentStatusHistory> findStudentStatusHistoryByStudentId(final Long studentId) {

		return this.studentStatusHistoryDao.findStudentStatusHistoryByStudentId(studentId);
	}

	@Override
	public StudentStatusHistory saveStudentStatusHistory(final StudentStatusHistory studentStatusHistory) {
		return this.studentStatusHistoryDao.persist(studentStatusHistory);
	}

	@Override
	public void removeStudentStatusHistory(final StudentStatusHistory studentStatusHistory) {
		this.studentStatusHistoryDao.remove(studentStatusHistory);
	}

}
