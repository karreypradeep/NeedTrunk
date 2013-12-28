/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.service;

/**
 * Service implementation interface for student.
 * 
 * @author Pradeep
 * 
 */
import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.dao.StudentSectionDao;
import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.core.model.StudentSection;
import com.apeironsol.need.core.model.StudentStatusHistory;
import com.apeironsol.need.util.DateUtil;
import com.apeironsol.need.util.constants.StudentSectionStatusConstant;
import com.apeironsol.need.util.dataobject.StudentFinancialAcademicYearDO;

@Service("studentTransferService")
@Transactional(rollbackFor = Exception.class)
public class StudentTransferServiceImpl implements StudentTransferService {

	@Resource
	private StudentSectionDao	studentSectionDao;

	@Resource
	private AdmissionService	admissionService;

	@Override
	public void transferStudent(final Collection<StudentFinancialAcademicYearDO> studentFinancialAcademicYearDOs, final Section toSection) {
		for (final StudentFinancialAcademicYearDO studentFinancialAcademicYearDO : studentFinancialAcademicYearDOs) {
			StudentSection previousStudentSection = studentFinancialAcademicYearDO.getStudentSection();
			previousStudentSection.setStudentSectionStatus(StudentSectionStatusConstant.TRANSFERED);
			previousStudentSection = this.studentSectionDao.persist(previousStudentSection);

			StudentSection studentSection = new StudentSection();
			studentSection.setSection(toSection);
			studentSection.setSequenceNr(previousStudentSection.getSequenceNr() + 1);
			studentSection.setStudentAcademicYear(previousStudentSection.getStudentAcademicYear());
			studentSection.setStudentSectionStatus(StudentSectionStatusConstant.ACTIVE);
			studentSection.setPreviousStudentSection(previousStudentSection);
			studentSection.setActionDate(DateUtil.getSystemDate());
			studentSection = this.studentSectionDao.persist(studentSection);
			final StudentStatusHistory history = new StudentStatusHistory();
			history.setComments("Student Transferred from section " + studentFinancialAcademicYearDO.getStudentSection().getSection().getName()
					+ " to section " + toSection.getName());

			this.admissionService.saveStudentStatusHistory(history, studentSection.getStudentAcademicYear().getStudent(), "Transferred");
		}

	}
}
