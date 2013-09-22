/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.apeironsol.need.core.dataobject.SectionAttendanceReportMonthlyDO;
import com.apeironsol.need.core.dataobject.StudentAttendanceDO;
import com.apeironsol.need.core.dataobject.StudentAttendanceMonthlyDO;
import com.apeironsol.need.core.model.Attendance;
import com.apeironsol.need.core.model.SectionSubject;
import com.apeironsol.need.core.model.StudentAbsent;
import com.apeironsol.need.core.model.StudentAcademicYear;
import com.apeironsol.need.core.model.StudentSection;
import com.apeironsol.need.core.service.StudentSectionService;
import com.apeironsol.need.util.DateUtil;
import com.apeironsol.need.util.constants.AttendanceTypeConstant;
import com.apeironsol.need.util.constants.MonthConstant;
import com.apeironsol.framework.BaseDaoImpl;

/**
 * Data access interface for Student Attendance entity implementation.
 * 
 * @author Pradeep
 * 
 */
@Repository("studentAbsentDao")
public class StudentAbsentDaoImpl extends BaseDaoImpl<StudentAbsent> implements StudentAbsentDao {

	@Resource
	private StudentAcademicYearDao	studentAcademicYearDao;

	@Resource
	private AttendanceDao			attendanceDao;

	@Resource
	private SectionSubjectDao		sectionSubjectDao;

	@Resource
	private StudentSectionService	studentSectionService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentAbsent> findStudentAttendanceByScetionIdAndStudentAcademicYearId(final Long sectionId, final Long studentAcademicYearId) {
		TypedQuery<StudentAbsent> query = this.getEntityManager().createQuery(
				"select sa from StudentAbsent sa where sa.section.id = :sectionId and" + " sa.studentAcademicYear.id = :studentAcademicYearId",
				StudentAbsent.class);
		query.setParameter("sectionId", sectionId);
		query.setParameter("studentAcademicYearId", studentAcademicYearId);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<StudentAbsent> findStudentAttendanceByScetionIdAndStudentAcademicYearIdAndForMonth(final Long sectionId,
			final Long studentAcademicYearId, final Date month) {
		TypedQuery<StudentAbsent> query = this.getEntityManager().createQuery(
				"select sa from StudentAbsent sa where sa.section.id = :sectionId and " + "sa.studentAcademicYear.id = :studentAcademicYearId and "
						+ "sa.absentDate >= :startMonth and sa.absentDate <= :endMonth", StudentAbsent.class);
		query.setParameter("sectionId", sectionId);
		query.setParameter("studentAcademicYearId", studentAcademicYearId);
		query.setParameter("startMonth", DateUtil.returnFirstDateOfMonth(month));
		query.setParameter("endMonth", DateUtil.returnLastDateOfMonth(month));
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StudentAbsent findStudentAttendanceByScetionIdAndStudentAcademicYearIdAndForDate(final Long sectionId, final Long studentAcademicYearId,
			final Date date) {
		StudentAbsent result = null;
		try {
			TypedQuery<StudentAbsent> query = this.getEntityManager().createQuery(
					"select sa from StudentAbsent sa where sa.section.id = :sectionId and " + "sa.studentAcademicYear.id = :studentAcademicYearId and "
							+ "sa.absentDate = :date", StudentAbsent.class);
			query.setParameter("sectionId", sectionId);
			query.setParameter("studentAcademicYearId", studentAcademicYearId);
			DateUtil.clearTimeInfo(date);
			query.setParameter("date", date);
			result = query.getSingleResult();
		} catch (NoResultException nre) {
			//
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StudentAbsent findStudentAttendanceByStudentAcademicYearIdAndDate(final Long studentAcademicYearId, final Date date) {
		StudentAbsent result = null;
		try {
			// TODO Pradeep
			// java.lang.IllegalArgumentException: org.hibernate.QueryException:
			// could not resolve property: absentDate of:
			// com.apeironsol.need.core.model.StudentAttendance [select sa from
			// com.apeironsol.need.core.model.StudentAttendance sa where
			// sa.studentAcademicYear.id = :studentAcademicYearId and
			// sa.absentDate = :date]

			// TypedQuery<StudentAttendance> query =
			// this.getEntityManager().createQuery(
			// "select sa from StudentAbsent sa where sa.studentAcademicYear.id = :studentAcademicYearId and "
			// + "sa.absentDate = :date",
			// StudentAttendance.class);
			// query.setParameter("studentAcademicYearId",
			// studentAcademicYearId);
			// DateUtil.clearTimeInfo(date);
			// query.setParameter("date", date);
			// result = query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
		return result;
	}

	@Override
	public StudentAttendanceDO getStudentAttendanceDetailsByScetionIdAndStudentAcademicYearIdAndDate(final Long studentAcademicYearId, final Long sectionId,
			final Date date) {
		TypedQuery<StudentAbsent> query = this.getEntityManager().createQuery(
				"select sa from StudentAbsent sa where sa.attendance.section.id = :sectionId and sa.studentAcademicYear.id = :studentAcademicYearId and "
						+ "sa.attendance.attendanceDate = :attendanceDate", StudentAbsent.class);
		query.setParameter("sectionId", sectionId);
		query.setParameter("studentAcademicYearId", studentAcademicYearId);
		query.setParameter("attendanceDate", date);
		Collection<StudentAbsent> studentAbsentBySubjects = query.getResultList();
		StudentAttendanceDO studentAttendanceDO = new StudentAttendanceDO();
		if (studentAbsentBySubjects != null) {
			if (studentAbsentBySubjects.iterator().next().getAttendance().getAttendanceType().equals(AttendanceTypeConstant.DAILY)) {
				studentAttendanceDO.setStudentAbsentForDailyAttendance(studentAbsentBySubjects.iterator().next());
			} else {
				for (StudentAbsent studentAbsent : studentAbsentBySubjects) {
					studentAttendanceDO.getStudentAbsentsBySubjectId().put(studentAbsent.getAttendance().getSectionSubject().getId(), studentAbsent);
				}
			}
		}
		studentAttendanceDO.setStudentAcademicYear(this.studentAcademicYearDao.findById(studentAcademicYearId));
		studentAttendanceDO.setAttendanceDate(date);
		studentAttendanceDO.setSectionSubjects(this.sectionSubjectDao.findSectionSubjectsBySectionId(sectionId));
		return studentAttendanceDO;
	}

	@Override
	public StudentAttendanceMonthlyDO getStudentAttendanceDetailsForEntireMonthByStudentAcademicYearIdAndMonth(final Long studentAcademicYearId, final Date date) {
		Date fromDate = DateUtil.returnFirstDateOfMonth(date);
		Calendar calFromDate = Calendar.getInstance();
		calFromDate.setTime(fromDate);
		Date toDate = DateUtil.returnLastDateOfMonth(date);
		TypedQuery<StudentAbsent> query = this
				.getEntityManager()
				.createQuery(
						"select sa from StudentAbsent sa where sa.studentAcademicYear.id = :studentAcademicYearId  and sa.attendance.attendanceDate >= :fromDate and sa.attendance.attendanceDate <= :toDate",
						StudentAbsent.class);
		query.setParameter("studentAcademicYearId", studentAcademicYearId);
		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", toDate);
		Collection<StudentAbsent> sectionAbsents = query.getResultList();
		StudentAttendanceMonthlyDO studentAttendanceMonthlyDO = new StudentAttendanceMonthlyDO();
		studentAttendanceMonthlyDO.setMonth(MonthConstant.getMonth(fromDate));
		studentAttendanceMonthlyDO.setAttendanceMonth(calFromDate);
		StudentSection latestStudentSection = this.studentSectionService.findLatestStudentSectionByStudentAcademicYearId(studentAcademicYearId);
		Collection<SectionSubject> sectionSubjects = this.sectionSubjectDao.findSectionSubjectsBySectionId(latestStudentSection.getSection().getId());
		Collection<Attendance> sectioAttendanceBetweenDates = this.attendanceDao.findAttendanceForSectionBetweenDates(
				latestStudentSection.getSection().getId(), fromDate, toDate);
		studentAttendanceMonthlyDO.setNumberOfPeriods(sectioAttendanceBetweenDates.size());
		for (Attendance attendance : sectioAttendanceBetweenDates) {
			calFromDate = Calendar.getInstance();
			calFromDate.setTime(attendance.getAttendanceDate());
			studentAttendanceMonthlyDO.setAttendanceTaken(calFromDate.get(Calendar.DAY_OF_MONTH) - 1);
		}
		StudentAttendanceDO studentAttendanceDO = null;
		for (StudentAbsent studentAbsent : sectionAbsents) {
			if (studentAttendanceMonthlyDO.getStudentAttendanceDOsByDate().get(studentAbsent.getAttendance().getAttendanceDate()) == null) {
				studentAttendanceDO = new StudentAttendanceDO();
				studentAttendanceDO.setAttendanceDate(studentAbsent.getAttendance().getAttendanceDate());
				studentAttendanceDO.setStudentAcademicYear(studentAbsent.getStudentAcademicYear());
				studentAttendanceDO.setSectionSubjects(sectionSubjects);
				if (AttendanceTypeConstant.DAILY.equals(studentAbsent.getAttendance().getAttendanceType())) {
					studentAttendanceDO.setStudentAbsentForDailyAttendance(studentAbsent);
				} else {
					studentAttendanceDO.getStudentAbsentsBySubjectId().put(studentAbsent.getAttendance().getSectionSubject().getId(), studentAbsent);
				}
				studentAttendanceMonthlyDO.getStudentAttendanceDOsByDate().put(studentAbsent.getAttendance().getAttendanceDate(), studentAttendanceDO);
			} else {
				studentAttendanceDO = studentAttendanceMonthlyDO.getStudentAttendanceDOsByDate().get(studentAbsent.getAttendance().getAttendanceDate());
				if (studentAttendanceDO == null) {
					studentAttendanceDO = new StudentAttendanceDO();
					studentAttendanceDO.setSectionSubjects(sectionSubjects);
					studentAttendanceDO.setAttendanceDate(studentAbsent.getAttendance().getAttendanceDate());
					studentAttendanceDO.setStudentAcademicYear(studentAbsent.getStudentAcademicYear());
				}
				if (AttendanceTypeConstant.DAILY.equals(studentAbsent.getAttendance().getAttendanceType())) {
					studentAttendanceDO.setStudentAbsentForDailyAttendance(studentAbsent);
				} else {
					studentAttendanceDO.getStudentAbsentsBySubjectId().put(studentAbsent.getAttendance().getSectionSubject().getId(), studentAbsent);
				}
			}
		}

		return studentAttendanceMonthlyDO;
	}

	@Override
	public Map<Long, StudentAttendanceDO> getSectionAttendanceDetailsByScetionIdAndDate(final Long sectionId, final Date date) {
		TypedQuery<StudentAbsent> query = this.getEntityManager().createQuery(
				"select sa from StudentAbsent sa where sa.attendance.section.id = :sectionId and sa.attendance.attendanceDate = :attendanceDate",
				StudentAbsent.class);
		query.setParameter("sectionId", sectionId);
		query.setParameter("attendanceDate", date);
		Collection<StudentAbsent> sectionAbsentBySubjects = query.getResultList();
		Collection<SectionSubject> sectionSubjects = this.sectionSubjectDao.findSectionSubjectsBySectionId(sectionId);
		Map<Long, StudentAttendanceDO> studentAttendanceMap = new HashMap<Long, StudentAttendanceDO>();
		for (StudentAbsent studentAbsent : sectionAbsentBySubjects) {
			if (studentAttendanceMap.get(studentAbsent.getStudentAcademicYear().getId()) != null) {
				studentAttendanceMap.get(studentAbsent.getStudentAcademicYear().getId()).addStudentAbsent(studentAbsent);
			} else {
				StudentAttendanceDO studentAttendanceDO = new StudentAttendanceDO();
				studentAttendanceDO.setAttendanceDate(date);
				studentAttendanceDO.addStudentAbsent(studentAbsent);
				studentAttendanceDO.setStudentAcademicYear(studentAbsent.getStudentAcademicYear());
				studentAttendanceDO.setSectionSubjects(sectionSubjects);
				studentAttendanceMap.put(studentAbsent.getStudentAcademicYear().getId(), studentAttendanceDO);
			}
		}

		return studentAttendanceMap;
	}

	@Override
	public Map<Long, StudentAttendanceMonthlyDO> getSectionAttendanceDetailsByScetionIdAndFromDateAndToDate(final Long sectionId, final Date fromDate,
			final Date toDate) {
		Collection<StudentAbsent> sectionAbsents = this.getStudentAbsentsBySectionIdBetweenDates(sectionId, fromDate, toDate);
		Map<Long, StudentAttendanceMonthlyDO> studentAttendanceMonthlyMap = new HashMap<Long, StudentAttendanceMonthlyDO>();
		Collection<SectionSubject> sectionSubjects = this.sectionSubjectDao.findSectionSubjectsBySectionId(sectionId);
		for (StudentAbsent studentAbsent : sectionAbsents) {
			if (studentAttendanceMonthlyMap.get(studentAbsent.getStudentAcademicYear().getId()) != null) {
				StudentAttendanceDO studentAttendanceDO = studentAttendanceMonthlyMap.get(studentAbsent.getStudentAcademicYear().getId())
						.getStudentAttendanceDOsByDate().get(studentAbsent.getAttendance().getAttendanceDate());
				if (studentAttendanceDO == null) {
					studentAttendanceDO = new StudentAttendanceDO();
					studentAttendanceDO.setSectionSubjects(sectionSubjects);
				}
				studentAttendanceDO.addStudentAbsent(studentAbsent);
				studentAttendanceMonthlyMap.get(studentAbsent.getStudentAcademicYear().getId()).getStudentAttendanceDOsByDate()
						.put(studentAbsent.getAttendance().getAttendanceDate(), studentAttendanceDO);
			} else {
				StudentAttendanceMonthlyDO studentAttendanceMonthlyDO = new StudentAttendanceMonthlyDO();
				StudentAttendanceDO studentAttendanceDO = new StudentAttendanceDO();
				studentAttendanceDO.setSectionSubjects(sectionSubjects);
				studentAttendanceDO.addStudentAbsent(studentAbsent);
				studentAttendanceMonthlyDO.setStudentAcademicYear(studentAbsent.getStudentAcademicYear());
				studentAttendanceMonthlyDO.setSectionSubjects(this.sectionSubjectDao.findSectionSubjectsBySectionId(sectionId));
				studentAttendanceMonthlyDO.getStudentAttendanceDOsByDate().put(studentAbsent.getAttendance().getAttendanceDate(), studentAttendanceDO);
				studentAttendanceMonthlyMap.put(studentAbsent.getStudentAcademicYear().getId(), studentAttendanceMonthlyDO);
			}
		}
		return studentAttendanceMonthlyMap;
	}

	@Override
	public void saveStudentAbsents(final Attendance attendance, final Collection<StudentAttendanceDO> studentAttendanceDOs) {
		boolean isNew = true;
		if (attendance.getId() != null) {
			isNew = false;
		}
		Attendance attendanceLocal = this.attendanceDao.persist(attendance);
		if (isNew) {
			for (StudentAttendanceDO studentAttendanceDO : studentAttendanceDOs) {
				StudentAbsent studentAbsent = null;
				if (attendanceLocal.getAttendanceType().equals(AttendanceTypeConstant.DAILY)) {
					studentAbsent = studentAttendanceDO.getStudentAbsentForDailyAttendance();
				} else {
					studentAbsent = studentAttendanceDO.getStudentAbsentBySectionSubjectId(attendance.getSectionSubject().getId());
				}
				if (studentAbsent != null) {
					studentAbsent.setAttendance(attendanceLocal);
					this.persist(studentAbsent);
				}
			}
		} else {
			for (StudentAttendanceDO studentAttendanceDO : studentAttendanceDOs) {
				StudentAbsent studentAbsent = null;
				if (attendanceLocal.getAttendanceType().equals(AttendanceTypeConstant.DAILY)) {
					studentAbsent = studentAttendanceDO.getStudentAbsentForDailyAttendance();
				} else {
					studentAbsent = studentAttendanceDO.getStudentAbsentBySectionSubjectId(attendance.getSectionSubject().getId());
				}
				StudentAbsent extistingStudentAbsent = this.findStudentAttendanceByAttendanceIdAndStudentAcademicYearId(attendanceLocal.getId(),
						studentAttendanceDO.getStudentAcademicYear().getId());
				if (extistingStudentAbsent != null && studentAbsent == null) {
					this.remove(extistingStudentAbsent);
				} else if (studentAbsent != null && studentAbsent.getId() == null) {
					studentAbsent.setAttendance(attendanceLocal);
					this.persist(studentAbsent);
				}

			}
		}
	}

	@Override
	public SectionAttendanceReportMonthlyDO generateSectionAttendanceReportBetweenDates(final Long sectionId, final Date attendanceStartDate,
			final Date attendanceEndDate) {
		Calendar calMonthStartDate = Calendar.getInstance();
		calMonthStartDate.setTime(attendanceStartDate);

		SectionAttendanceReportMonthlyDO sectionAttendanceReportMonthlyDO = new SectionAttendanceReportMonthlyDO();
		sectionAttendanceReportMonthlyDO.setMonthConstant(MonthConstant.getMonth(calMonthStartDate.get(Calendar.MONTH) + 1));
		sectionAttendanceReportMonthlyDO.setAttendanceStartDate(attendanceStartDate);
		Collection<Attendance> attendances = this.attendanceDao.findAttendanceForSectionBetweenDates(sectionId, attendanceStartDate, attendanceEndDate);
		sectionAttendanceReportMonthlyDO.setNumberOfPeriods(attendances.size());
		if (!attendances.isEmpty()) {
			Collection<StudentAbsent> studentAbsents = this.getStudentAbsentsByAttendances(attendances);
			sectionAttendanceReportMonthlyDO.setNumberOfStudentAbsents(studentAbsents.size());
		} else {
			sectionAttendanceReportMonthlyDO.setNumberOfStudentAbsents(0);
		}
		return sectionAttendanceReportMonthlyDO;
	}

	/**
	 * 
	 * @param attendances
	 * @return
	 */
	private Collection<StudentAbsent> getStudentAbsentsByAttendances(final Collection<Attendance> attendances) {
		TypedQuery<StudentAbsent> query = this.getEntityManager().createQuery("select sa from StudentAbsent sa where sa.attendance in :attendances",
				StudentAbsent.class);
		query.setParameter("attendances", attendances);
		return query.getResultList();
	}

	/**
	 * 
	 * @param sectionId
	 * @param attendanceStartDate
	 * @param attendanceEndDate
	 * @return
	 */
	private Collection<StudentAbsent> getStudentAbsentsBySectionIdBetweenDates(final Long sectionId, final Date attendanceStartDate,
			final Date attendanceEndDate) {
		TypedQuery<StudentAbsent> query = this
				.getEntityManager()
				.createQuery(
						"select sa from StudentAbsent sa where sa.attendance.section.id = :sectionId and sa.attendance.attendanceDate >= :fromDate and sa.attendance.attendanceDate <= :toDate",
						StudentAbsent.class);
		query.setParameter("sectionId", sectionId);
		query.setParameter("fromDate", attendanceStartDate);
		query.setParameter("toDate", attendanceEndDate);
		return query.getResultList();
	}

	@Override
	public Collection<StudentAttendanceMonthlyDO> getStudentAttendanceDetailsForEntireYearByStudentAcademicYearId(final Long studentAcademicYearId) {
		StudentAcademicYear studentAcademicYear = this.studentAcademicYearDao.findById(studentAcademicYearId);
		Calendar calFromDate = Calendar.getInstance();
		calFromDate.setTime(studentAcademicYear.getAcademicYear().getStartDate());

		Calendar calToDate = Calendar.getInstance();
		calToDate.setTime(studentAcademicYear.getAcademicYear().getEndDate());

		calFromDate = DateUtil.returnFirstDateOfMonth(calFromDate);
		calToDate = DateUtil.returnLastDateOfMonth(calToDate);
		Collection<StudentAttendanceMonthlyDO> studentAttendanceMonthlyDOs = new ArrayList<StudentAttendanceMonthlyDO>();
		while (calFromDate.before(calToDate)) {
			studentAttendanceMonthlyDOs.add(this.getStudentAttendanceDetailsForEntireMonthByStudentAcademicYearIdAndMonth(studentAcademicYearId,
					calFromDate.getTime()));
			calFromDate.add(Calendar.MONTH, 1);
		}
		return studentAttendanceMonthlyDOs;
	}

	@Override
	public StudentAbsent findStudentAttendanceByAttendanceIdAndStudentAcademicYearId(final Long attendanceId, final Long studentAcademicYearId) {
		try {
			TypedQuery<StudentAbsent> query = this.getEntityManager().createQuery(
					"select sa from StudentAbsent sa where sa.attendance.id = :attendanceId and sa.studentAcademicYear.id = :studentAcademicYearId",
					StudentAbsent.class);
			query.setParameter("attendanceId", attendanceId);
			query.setParameter("studentAcademicYearId", studentAcademicYearId);
			return query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public Collection<StudentAbsent> findStudentAttendanceByAttendancesAndStudentAcademicYearId(final Collection<Attendance> attendances,
			final Long studentAcademicYearId) {
		TypedQuery<StudentAbsent> query = this.getEntityManager().createQuery(
				"select sa from StudentAbsent sa where sa.attendance  in :attendances and sa.studentAcademicYear.id = :studentAcademicYearId",
				StudentAbsent.class);
		query.setParameter("attendances", attendances);
		query.setParameter("studentAcademicYearId", studentAcademicYearId);
		return query.getResultList();
	}

	@Override
	public Collection<StudentAbsent> findStudentAttendanceByAttendances(final Collection<Attendance> attendances) {
		TypedQuery<StudentAbsent> query = this.getEntityManager().createQuery(
				"select sa from StudentAbsent sa where sa.attendance  in :attendances and sa.studentAcademicYear.id = :studentAcademicYearId",
				StudentAbsent.class);
		query.setParameter("attendances", attendances);
		return query.getResultList();
	}

}
