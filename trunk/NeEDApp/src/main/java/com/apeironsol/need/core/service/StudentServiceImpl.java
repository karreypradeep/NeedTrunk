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
 * @author pradeep
 * 
 */
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.SystemException;
import com.apeironsol.need.core.dao.AddressDao;
import com.apeironsol.need.core.dao.EducationHistoryDao;
import com.apeironsol.need.core.dao.RelationDao;
import com.apeironsol.need.core.dao.StudentAcademicYearDao;
import com.apeironsol.need.core.dao.StudentDao;
import com.apeironsol.need.core.dao.StudentSectionDao;
import com.apeironsol.need.core.dao.StudentStatusHistoryDao;
import com.apeironsol.need.core.model.Address;
import com.apeironsol.need.core.model.EducationHistory;
import com.apeironsol.need.core.model.Relation;
import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.core.model.Student;
import com.apeironsol.need.core.model.StudentAcademicYear;
import com.apeironsol.need.core.model.StudentSection;
import com.apeironsol.need.core.model.StudentStatusHistory;
import com.apeironsol.need.financial.service.StudentFinancialService;
import com.apeironsol.need.util.DateUtil;
import com.apeironsol.need.util.constants.StudentSectionStatusConstant;
import com.apeironsol.need.util.constants.StudentStatusConstant;
import com.apeironsol.need.util.portal.ViewUtil;
import com.apeironsol.need.util.searchcriteria.AdmissionSearchCriteria;
import com.apeironsol.need.util.searchcriteria.StudentSearchCriteria;

@Service("studentService")
@Transactional(rollbackFor = Exception.class)
public class StudentServiceImpl implements StudentService {

	private static final Logger		log	= Logger.getLogger(StudentServiceImpl.class);

	@Resource
	private StudentDao				studentDao;

	@Resource
	private AddressDao				addressDao;

	@Resource
	private EducationHistoryDao		educationHistoryDao;

	@Resource
	private RelationDao				relatonDao;

	@Resource
	private StudentSectionDao		studentSectionDao;

	@Resource
	private SectionService			sectionService;

	@Resource
	private StudentStatusHistoryDao	studentStatusHistoryDao;

	@Resource
	private StudentFinancialService	studentFinancialService;

	@Resource
	private StudentAcademicYearDao	studentAcademicYearDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Student saveStudent(final Student student) {

		if (student.getId() != null) {
			final Collection<Relation> relations = this.relatonDao.findRelationsByStudentId(student.getId());
			student.setRelations(relations);
		}

		return this.studentDao.persist(student);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Student findStudentById(final Long id) {
		return this.studentDao.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Address findStudentAddressByStudentId(final Long studentId) {
		return this.addressDao.findStudentAddressByStudentId(studentId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Student> findStudentsByBranchId(final Long branchId) {
		return this.studentDao.findStudentsByBranchId(branchId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<EducationHistory> findPreviousEducationHistoryByStudentId(final Long studentId) {
		return this.educationHistoryDao.findEducationHistoriesByStudentId(studentId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EducationHistory saveEducationHistory(final EducationHistory educationHistory) {

		return this.educationHistoryDao.persist(educationHistory);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Student> findActiveStudentsByBranchId(final Long branchId) {
		return this.studentDao.findActiveStudentsByBranchId(branchId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentSection> findActiveStudentSectionsBySectionId(final Long sectionId) throws BusinessException, SystemException {
		return this.studentSectionDao.findStudentSectionsBySectionIdAndStateActive(sectionId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Student> findActiveStudentsBySectionId(final Long sectionId) throws BusinessException, SystemException {
		try {

			final Collection<StudentSection> studentSections = this.studentSectionDao.findStudentSectionsBySectionIdAndStateActive(sectionId);
			final Collection<Student> result = new ArrayList<Student>();
			if (studentSections != null) {
				for (final StudentSection studentSection : studentSections) {
					result.add(studentSection.getStudentAcademicYear().getStudent());
				}
			}
			return result;
		} catch (final Throwable e) {
			log.info(e.getMessage());
			throw new SystemException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentAcademicYear> findStudentAcademicYearsWithActiveStatusBySectionId(final Long sectionId) throws BusinessException, SystemException {
		try {

			final Collection<StudentSection> studentSections = this.studentSectionDao.findStudentSectionsBySectionIdAndStateActive(sectionId);
			final Collection<StudentAcademicYear> result = new ArrayList<StudentAcademicYear>();
			if (studentSections != null) {
				for (final StudentSection studentSection : studentSections) {
					result.add(studentSection.getStudentAcademicYear());
				}
			}
			return result;
		} catch (final Throwable e) {
			log.info(e.getMessage());
			throw new SystemException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentSection> findActiveStudentSectionsForAcademicYearId(final Long academicYearId) throws BusinessException {
		Collection<StudentSection> totalActiveStudentSections = null;
		final Collection<Section> totalActiveSections = this.sectionService.findAllSectionsByAcademicYearIdAndStatus(academicYearId, true);
		if (totalActiveSections != null && !totalActiveSections.isEmpty()) {
			final List<Long> sectionIds = new ArrayList<Long>();
			for (final Section activeSection : totalActiveSections) {
				sectionIds.add(activeSection.getId());
			}
			totalActiveStudentSections = this.studentSectionDao.findStudentSectionsBySectionIdsAndStatus(sectionIds, StudentSectionStatusConstant.ACTIVE);
		}
		return totalActiveStudentSections;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int findNumberOfActiveStudentsBySectionId(final Long sectionId) throws BusinessException {
		return this.studentSectionDao.findNumberOfActiveStudentsBySectionId(sectionId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Long, Integer> findNumberOfActiveStudentsBySectionIds(final Collection<Long> sectionIds) {
		return this.studentSectionDao.findNumberOfActiveStudentsBySectionIds(sectionIds);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Student> findAllStudentsBySectionId(final Long sectionId) throws BusinessException, SystemException {
		try {

			final Collection<StudentSection> studentSections = this.studentSectionDao.findStudentSectionsBySectionId(sectionId);
			final Collection<Student> result = new ArrayList<Student>();
			if (studentSections != null) {
				for (final StudentSection studentSection : studentSections) {
					result.add(studentSection.getStudentAcademicYear().getStudent());
				}
			}
			return result;
		} catch (final Throwable e) {
			log.info(e.getMessage());
			throw new SystemException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentSection> findAllStudentSectionsBySectionId(final Long sectionId) throws BusinessException, SystemException {
		return this.studentSectionDao.findStudentSectionsBySectionId(sectionId);
	}

	@Override
	public Collection<StudentSection> findStudentSectionsBySearchCriteria(final StudentSearchCriteria studentSearchCriteria) throws BusinessException {
		return this.studentSectionDao.findStudentSectionsBySearchCriteria(studentSearchCriteria);
	}

	@Override
	public StudentSection findStudentSectionByStudentAcademicYearIdAndActiveStatus(final Long studentAcademicYearId) {
		return this.studentSectionDao.findStudentSectionByStudentAcademicYearIdAndActiveStatus(studentAcademicYearId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Student findStudentByUsername(final String username) {
		return this.studentDao.findStudentByUsername(username);
	}

	@Override
	public boolean isActiveStudentsForBranchId(final Long branchId) {
		return this.studentDao.findActiveStudentsCountForBranchId(branchId) != null && this.studentDao.findActiveStudentsCountForBranchId(branchId) > 0 ? true
				: false;
	}

	@Override
	public Collection<StudentAcademicYear> findStudentAcademicYearsWithActiveStatusBySectionIds(final Collection<Long> sectionIds) throws BusinessException,
			SystemException {
		final Collection<StudentSection> studentSections = this.studentSectionDao.findStudentSectionsBySectionIdsAndStatus(sectionIds,
				StudentSectionStatusConstant.ACTIVE);
		final Collection<StudentAcademicYear> result = new ArrayList<StudentAcademicYear>();
		if (studentSections != null) {
			for (final StudentSection studentSection : studentSections) {
				result.add(studentSection.getStudentAcademicYear());
			}
		}
		return result;
	}

	@Override
	public Collection<StudentSection> findAllStudentSectionsBySectionIds(final Collection<Long> sectionIds) throws BusinessException, SystemException {
		return this.studentSectionDao.findAllStudentSectionsBySectionIds(sectionIds);
	}

	@Override
	public Student processDropoutStudent(final Long studentId, final String actionComment) throws BusinessException, SystemException {

		// final StudentAcademicYear studentAcademicYear =
		// this.studentAcademicYearDao.findStudentCurrentOrMostRecentAcademicYearByStudentId(studentId);
		//
		// final boolean hasOutstandingFeeDue =
		// this.studentFinancialService.hasOutstandingFeeDue(studentId,
		// studentAcademicYear.getAcademicYear().getId());
		//
		// if (hasOutstandingFeeDue) {
		// throw new
		// BusinessException("Student has outstanding fee due, cannot set the student state to 'In dropout'.");
		// }

		Student studentLocal = this.studentDao.findById(studentId);
		final StudentAcademicYear studentAcademicYear = this.studentAcademicYearDao.findStudentCurrentOrMostRecentAcademicYearByStudentId(studentId);
		studentLocal.setStudentStatus(StudentStatusConstant.ACCEPT_FOR_DROPOUT);

		studentLocal = this.studentDao.persist(studentLocal);

		final StudentSection studentSection = this.studentSectionDao.findStudentSectionByStudentAcademicYearIdAndActiveStatus(studentAcademicYear.getId());

		studentSection.setStudentSectionStatus(StudentSectionStatusConstant.ACCEPT_FOR_DROPOUT);

		this.studentSectionDao.persist(studentSection);

		this.createStudentStatusHistory("Accept for dropout", actionComment, studentLocal);

		return studentLocal;
	}

	private void createStudentStatusHistory(final String action, final String actionComment, final Student studentLocal) {
		final StudentStatusHistory studentStatusHistory = new StudentStatusHistory();
		studentStatusHistory.setActionTakenTime(new Timestamp(DateUtil.getSystemDate().getTime()));
		studentStatusHistory.setActionTakenBy(ViewUtil.getPrincipal());
		studentStatusHistory.setAction(action);
		studentStatusHistory.setStudent(studentLocal);
		studentStatusHistory.setComments(actionComment);
		this.studentStatusHistoryDao.persist(studentStatusHistory);
	}

	@Override
	public Student dropoutStudent(final Long studentId, final String actionComment) throws BusinessException, SystemException {

		final StudentAcademicYear studentAcademicYear = this.studentAcademicYearDao.findStudentCurrentOrMostRecentAcademicYearByStudentId(studentId);

		final boolean hasOutstandingFeeDue = this.studentFinancialService.hasOutstandingFeeDue(studentId, studentAcademicYear.getAcademicYear().getId());

		if (hasOutstandingFeeDue) {
			throw new BusinessException("Student has outstanding fee due, cannot set the student state to 'Dropout'.");
		}

		Student studentLocal = this.studentDao.findById(studentId);

		studentLocal.setStudentStatus(StudentStatusConstant.DROPOUT);

		studentLocal = this.studentDao.persist(studentLocal);

		final StudentSection studentSection = this.studentSectionDao.findStudentSectionByStudentAcademicYearIdAndStatus(studentAcademicYear.getId(),
				StudentSectionStatusConstant.ACCEPT_FOR_DROPOUT);

		studentSection.setStudentSectionStatus(StudentSectionStatusConstant.DROPOUT);

		this.studentSectionDao.persist(studentSection);

		this.createStudentStatusHistory("Drop out", actionComment, studentLocal);

		return studentLocal;

	}

	@Override
	public Student rollBackDropoutStudent(final Long studentId, final String actionComment) throws BusinessException, SystemException {

		final StudentAcademicYear studentAcademicYear = this.studentAcademicYearDao.findStudentCurrentOrMostRecentAcademicYearByStudentId(studentId);

		if (studentAcademicYear.getAcademicYear().getEndDate().before(DateUtil.getSystemDate())) {
			throw new BusinessException("Last academic year for Student is before current date.");
		}

		StudentSection studentSection = this.studentSectionDao.findStudentSectionByStudentAcademicYearIdAndStatus(studentAcademicYear.getId(),
				StudentSectionStatusConstant.ACCEPT_FOR_DROPOUT);
		if (studentSection == null) {
			studentSection = this.studentSectionDao.findStudentSectionByStudentAcademicYearIdAndStatus(studentAcademicYear.getId(),
					StudentSectionStatusConstant.DROPOUT);
		}

		if (studentSection == null) {
			throw new BusinessException("Cannot rollback studnet not in Dropout or Accept for Dropout state.");
		}

		Student studentLocal = this.studentDao.findById(studentId);

		studentLocal.setStudentStatus(StudentStatusConstant.ACTIVE);

		studentLocal = this.studentDao.persist(studentLocal);

		studentSection.setStudentSectionStatus(StudentSectionStatusConstant.ACTIVE);

		this.studentSectionDao.persist(studentSection);

		this.createStudentStatusHistory("Roll back Drop Out", actionComment, studentLocal);

		return studentLocal;

	}

	@Override
	public Student findStudentByAdmissionId(final Long admissionId) {
		return this.studentDao.findStudentByAdmissionId(admissionId);
	}

	@Override
	public Collection<Student> findAdmissionsBySearchCriteria(final AdmissionSearchCriteria admissionSearchCriteria) {
		return this.studentDao.findAdmissionsBySearchCriteria(admissionSearchCriteria);
	}

	@Override
	public Student findActiveStudentByAdmissionNumber(final String admissionNumber) {
		return this.studentDao.findActiveStudentByAdmissionNumber(admissionNumber);
	}

	@Override
	public Long findActiveStudentsCountForBranchId(final Long branchId) {
		return this.studentDao.findActiveStudentsCountForBranchId(branchId);
	}

	@Override
	public Student findActiveStudentByExternalAdmissionNumberAndBranchId(final String externalAdmissionNumber, final Long branchId) {
		return this.studentDao.findActiveStudentByExternalAdmissionNumberAndBranchId(externalAdmissionNumber, branchId);
	}

	@Override
	public void deleteStudent(final Student student) throws BusinessException {
		this.studentDao.remove(student);
	}
}
