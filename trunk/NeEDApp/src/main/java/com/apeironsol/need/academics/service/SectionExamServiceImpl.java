package com.apeironsol.need.academics.service;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.SystemException;
import com.apeironsol.need.academics.dao.SectionExamDao;
import com.apeironsol.need.academics.dao.SectionExamSubjectDao;
import com.apeironsol.need.academics.dataobject.SectionExamDO;
import com.apeironsol.need.academics.dataobject.SectionExamSubjectDO;
import com.apeironsol.need.academics.model.SectionExam;
import com.apeironsol.need.academics.model.SectionExamSubject;

@Service("sectionExamService")
@Transactional(rollbackFor = Exception.class)
public class SectionExamServiceImpl implements SectionExamService {

	@Resource
	private SectionExamDao			sectionExamDao;

	@Resource
	private SectionExamSubjectDao	sectionExamSubjectDao;

	@Override
	public Collection<SectionExam> findSectionExamsBySectionIdAndExamTypeId(final Long sectionId, final Long examTypeId) {
		return this.sectionExamDao.findSectionExamsBySectionIdAndExamTypeId(sectionId, examTypeId);
	}

	@Override
	public SectionExam findSectionExamsBySectionIdAndExamId(final Long sectionId, final Long examId) {
		return this.sectionExamDao.findSectionExamsBySectionIdAndExamId(sectionId, examId);
	}

	@Override
	public SectionExam findSectionExamById(final Long id) throws BusinessException {
		return this.sectionExamDao.findById(id);
	}

	@Override
	public Collection<SectionExamDO> getSectionExamsBySectionId(final Long sectionId) throws BusinessException, SystemException {

		Collection<SectionExamDO> sectionExamDos = new ArrayList<SectionExamDO>();

		Collection<SectionExam> sectionExams = this.sectionExamDao.findSectionExamsBySectionId(sectionId);

		for (SectionExam sectionExam : sectionExams) {

			SectionExamDO sectionExamDO = new SectionExamDO();

			sectionExamDO.setSectionExam(sectionExam);

			Collection<SectionExamSubject> sectionExamSubjects = this.sectionExamSubjectDao.findSectionExamSubjectsBySectionExamId(sectionExam.getId());

			Collection<SectionExamSubjectDO> sectionExamSubjectDOs = new ArrayList<SectionExamSubjectDO>();

			for (SectionExamSubject sectionExamSubject : sectionExamSubjects) {

				SectionExamSubjectDO sectionExamSubjectDO = new SectionExamSubjectDO();
				sectionExamSubjectDO.setSectionExamSubject(sectionExamSubject);
				sectionExamSubjectDO.setSubject(sectionExamSubject.getSectionSubject().getSubject());
				sectionExamSubjectDOs.add(sectionExamSubjectDO);
			}

			sectionExamDO.setSectionExamSubjects(sectionExamSubjects);
			sectionExamDO.setSectionExamSubjectDOs(sectionExamSubjectDOs);

			sectionExamDos.add(sectionExamDO);
		}

		return sectionExamDos;
	}

	@Override
	public Collection<SectionExam> findSectionExamsBySectionId(final Long sectionId) {
		return this.sectionExamDao.findSectionExamsBySectionId(sectionId);
	}

	@Override
	public Collection<SectionExam> findSectionExamsByKlassId(final Long klassId, final Long academicYearId) {
		return this.sectionExamDao.findSectionExamsByKlassId(klassId, academicYearId);
	}

	@Override
	public Collection<SectionExam> findSectionExamsByExamIdsandAcademicYearId(final Collection<Long> examIDs, final Long academicYearId) {
		return this.sectionExamDao.findSectionExamsByExamIdsandAcademicYearId(examIDs, academicYearId);
	}

}
