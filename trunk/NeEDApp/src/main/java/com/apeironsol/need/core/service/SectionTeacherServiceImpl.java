/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.dao.SectionTeacherDao;
import com.apeironsol.need.core.model.SectionTeacher;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;

/**
 *  Service layer implementation for section teacher DAO implementation.
 * 
 * @author Pradeep
 * 
 */
@Service("sectionTeacherService")
@Transactional(rollbackFor = Exception.class)
public class SectionTeacherServiceImpl implements SectionTeacherService {

	@Resource
	private SectionTeacherDao	sectionTeacherDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<SectionTeacher> findSectionTeachersByScetionId(final Long sectionId) throws BusinessException {
		return this.sectionTeacherDao.findSectionTeachersByScetionId(sectionId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SectionTeacher findSectionTeacherByScetionIdAndEmployeeId(final Long sectionId, final Long employeeId) throws BusinessException {
		return this.sectionTeacherDao.findSectionTeacherByScetionIdAndEmployeeId(sectionId, employeeId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SectionTeacher findSectionTeacherById(final Long id) throws BusinessException {
		return this.sectionTeacherDao.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SectionTeacher saveSectionTeacher(final SectionTeacher sectionTeacher) throws BusinessException,
			InvalidArgumentException {
		return this.sectionTeacherDao.persist(sectionTeacher);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeSectionTeacher(final SectionTeacher sectionTeacher) throws BusinessException {
		this.sectionTeacherDao.remove(sectionTeacher);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<SectionTeacher> findAllSectionTeachers() throws BusinessException {
		return this.sectionTeacherDao.findAll();
	}

}
