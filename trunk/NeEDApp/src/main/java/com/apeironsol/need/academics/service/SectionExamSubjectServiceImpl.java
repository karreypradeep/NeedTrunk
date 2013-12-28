package com.apeironsol.need.academics.service;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.academics.dao.SectionExamSubjectDao;
import com.apeironsol.need.academics.model.SectionExamSubject;

@Service("sectionExamSubjectService")
@Transactional(rollbackFor = Exception.class)
public class SectionExamSubjectServiceImpl implements SectionExamSubjectService {

	@Resource
	private SectionExamSubjectDao	sectionExamSubjectDao;

	@Override
	public Collection<SectionExamSubject> findSectionExamSubjectsBySectionIdAndSubjectExamId(final Long sectionExamId) {
		return this.sectionExamSubjectDao.findSectionExamSubjectsBySectionExamId(sectionExamId);
	}

	@Override
	public SectionExamSubject findSectionExamSubjectById(final Long sectionExamSubjectId) {
		return this.sectionExamSubjectDao.findById(sectionExamSubjectId);
	}

}
