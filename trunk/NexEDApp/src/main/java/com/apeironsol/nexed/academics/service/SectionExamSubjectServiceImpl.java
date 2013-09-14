package com.apeironsol.nexed.academics.service;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.nexed.academics.dao.SectionExamSubjectDao;
import com.apeironsol.nexed.academics.model.SectionExamSubject;

@Service("sectionExamSubjectService")
@Transactional
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
