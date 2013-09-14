package com.apeironsol.nexed.academics.dao;

import java.util.Collection;
import java.util.Date;

import com.apeironsol.nexed.academics.model.SectionExamSubject;
import com.apeironsol.framework.BaseDao;

public interface SectionExamSubjectDao extends BaseDao<SectionExamSubject> {

	Collection<SectionExamSubject> findSectionExamSubjectsBySectionExamId(final Long sectionExamId);

	SectionExamSubject findSectionExamSubjectBySectionIdAndScheduledDateBetweenStartAndEndTime(Long sectionId, Date scheduledDate, Date startTime, Date endTime);

	void removeSectionExamSubjectsBySectionExamId(Long sectionExamId);

}
