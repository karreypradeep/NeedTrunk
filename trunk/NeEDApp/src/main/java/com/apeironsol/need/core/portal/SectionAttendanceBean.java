/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.core.portal;

/**
 * Managed bean for section attendance.
 * 
 * @author Pradeep
 */
import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.need.core.dataobject.StudentAttendanceDO;
import com.apeironsol.need.core.model.AcademicYearHoliday;
import com.apeironsol.need.core.model.Attendance;
import com.apeironsol.need.core.model.BranchRule;
import com.apeironsol.need.core.model.SectionSubject;
import com.apeironsol.need.core.model.StudentAbsent;
import com.apeironsol.need.core.model.StudentAcademicYear;
import com.apeironsol.need.core.model.StudentSection;
import com.apeironsol.need.core.service.AttendanceService;
import com.apeironsol.need.core.service.BranchRuleService;
import com.apeironsol.need.core.service.SectionSubjectService;
import com.apeironsol.need.core.service.StudentAttendanceService;
import com.apeironsol.need.core.service.StudentService;
import com.apeironsol.need.util.DateUtil;
import com.apeironsol.need.util.constants.AttendanceTypeConstant;
import com.apeironsol.need.util.portal.ViewExceptionHandler;
import com.apeironsol.need.util.portal.ViewUtil;

@Named
@Scope("session")
public class SectionAttendanceBean extends AbstractTabbedBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long				serialVersionUID	= -7354019652222454580L;

	private Date							attendanceDate;

	private Date							previousAttendanceLoadDate;

	@Resource
	private SectionBean						sectionBean;

	private Collection<StudentSection>		studentSections;

	private Collection<SectionSubject>		sectionSubjects;

	private boolean							loadStudentsForSectionFlag;

	private boolean							loadSectionSubjectsFlag;

	@Resource
	private StudentService					studentService;

	@Resource
	private SectionSubjectService			sectionSubjectService;

	private SectionSubject					attendanceSubject;

	@Resource
	private StudentAttendanceService		studentAttendanceService;

	@Resource
	private AttendanceService				attendanceService;

	private Map<Long, StudentAttendanceDO>	studentAttendanceDOs;

	private boolean							hideAttendanceTable;

	@Resource
	private BranchRuleService				branchRuleService;

	private BranchRule						branchRule;

	/**
	 * Current student for whom absent is being marked.
	 */
	private StudentAcademicYear				currentAttendanceStudentAcademicYear;

	/**
	 * Current reason for current student for whom absent is being marked.
	 */
	private String							currentReasonForCurrentStudent;
	private Attendance						currentattendance;

	public enum AttendanceWizard {

		TAKE_ATTENDANCE("takeAttendance"), ATTENDANCE_SUMMARY("attendanceSummary"), ATTENDANCE_REPORT("attendanceReport");

		private String	key;

		AttendanceWizard(final String key) {
			this.key = key;
		}

		public String getKey() {
			return this.key;
		}

		public void setKey(final String key) {
			this.key = key;
		}
	};

	private String	attendanceWizardActiveStep	= AttendanceWizard.ATTENDANCE_SUMMARY.getKey();

	@Override
	public void onTabChange() {
	}

	/**
	 * @return the attendanceDate
	 */
	public Date getAttendanceDate() {
		return this.attendanceDate;
	}

	/**
	 * @param attendanceDate
	 *            the attendanceDate to set
	 */
	public void setAttendanceDate(final Date attendanceDate) {
		this.attendanceDate = attendanceDate;
	}

	public void loadStudentsForSection() {
		try {
			if (this.isLoadStudentsForSectionFlag()) {
				this.setStudentSections(this.studentService.findActiveStudentSectionsBySectionId(this.sectionBean.getSection().getId()));

				this.setLoadStudentsForSectionFlag(false);
			}
		} catch (ApplicationException e) {
			ViewExceptionHandler.handle(e);
		} catch (Throwable e) {
			ViewExceptionHandler.handle(e);
		}

	}

	public void loadSectionSubjects() {
		try {
			if (this.loadSectionSubjectsFlag) {
				this.setSectionSubjects(this.sectionSubjectService.findSectionSubjectsByScetionId(this.sectionBean.getSection().getId()));
				this.loadSectionSubjectsFlag = false;
			}
		} catch (ApplicationException e) {
			ViewExceptionHandler.handle(e);
		} catch (Throwable e) {
			ViewExceptionHandler.handle(e);
		}

	}

	/**
	 * @return the loadSectionSubjectsFlag
	 */
	public boolean isLoadSectionSubjectsFlag() {
		return this.loadSectionSubjectsFlag;
	}

	/**
	 * @param loadSectionSubjectsFlag
	 *            the loadSectionSubjectsFlag to set
	 */
	public void setLoadSectionSubjectsFlag(final boolean loadSectionSubjectsFlag) {
		this.loadSectionSubjectsFlag = loadSectionSubjectsFlag;
	}

	/**
	 * @return the sectionSubjects
	 */
	public Collection<SectionSubject> getSectionSubjects() {
		return this.sectionSubjects;
	}

	/**
	 * @param sectionSubjects
	 *            the sectionSubjects to set
	 */
	public void setSectionSubjects(final Collection<SectionSubject> sectionSubjects) {
		this.sectionSubjects = sectionSubjects;
	}

	/**
	 * @return the attendanceSubject
	 */
	public SectionSubject getAttendanceSubject() {
		return this.attendanceSubject;
	}

	/**
	 * @param attendanceSubject
	 *            the attendanceSubject to set
	 */
	public void setAttendanceSubject(final SectionSubject attendanceSubject) {
		this.attendanceSubject = attendanceSubject;
	}

	/**
	 * @return the studentAttendanceDOs
	 */
	public Map<Long, StudentAttendanceDO> getStudentAttendanceDOs() {
		return this.studentAttendanceDOs;
	}

	/**
	 * @param studentAttendanceDOs
	 *            the studentAttendanceDOs to set
	 */
	public void setStudentAttendanceDOs(final Map<Long, StudentAttendanceDO> studentAttendanceDOs) {
		this.studentAttendanceDOs = studentAttendanceDOs;
	}

	public void loadSectionStudentAbsentDetailsByDate() {
		this.setHideAttendanceTable(false);
		this.setStudentAttendanceDOs(this.studentAttendanceService.getSectionAttendanceDetailsByScetionIdAndDate(this.sectionBean.getSection().getId(),
				this.attendanceDate));
		this.previousAttendanceLoadDate = this.attendanceDate;
	}

	public void clearSectionStudentAbsentDetailsByDate() {
		this.setHideAttendanceTable(true);
	}

	public boolean isStudentPresentForSubjectOrDate(final Long studentAcademicYearId) {
		boolean studentPresent = true;
		if (this.isAttendanceSubjectWise()) {
			if (this.studentAttendanceDOs.get(studentAcademicYearId) != null && this.attendanceSubject != null) {
				studentPresent = this.studentAttendanceDOs.get(studentAcademicYearId).getStudentAbsentBySectionSubjectId(this.attendanceSubject.getId()) == null;
			}
		} else {
			if (this.studentAttendanceDOs.get(studentAcademicYearId) != null && this.attendanceDate != null) {
				studentPresent = this.studentAttendanceDOs.get(studentAcademicYearId).getStudentAbsentForDailyAttendance() == null;
			}
		}
		return studentPresent;
	}

	/**
	 * Returns style class for attendance present and absent command link.
	 * 
	 * @param student
	 *            student
	 * @param date
	 *            attendance date.
	 * @return style class for attendance present and absent command link.
	 */
	public String getStyleClassForStudent(final Long studentAcademicYearId) {
		Calendar calendarAttendaneDate = Calendar.getInstance();
		calendarAttendaneDate.setTime(this.attendanceDate);
		DateUtil.clearTimeInfo(calendarAttendaneDate);
		return this.isWeekEnd(calendarAttendaneDate) || this.isHoliday(calendarAttendaneDate) ? "ui-icon ui-icon-white  ui-icon-note" : this
				.isStudentPresentForSubjectOrDate(studentAcademicYearId) ? "ui-icon ui-icon-white ui-icon-check" : "ui-icon ui-icon-white ui-icon-close";
	}

	/**
	 * @return the studentSections
	 */
	public Collection<StudentSection> getStudentSections() {
		return this.studentSections;
	}

	/**
	 * @param studentSections
	 *            the studentSections to set
	 */
	public void setStudentSections(final Collection<StudentSection> studentSections) {
		this.studentSections = studentSections;
	}

	/**
	 * Return true if date supplied is week end.
	 * 
	 * @param suppliedDate
	 *            date for which week end has to be checked.
	 * @return
	 */
	private boolean isWeekEnd(final Calendar suppliedDate) {
		boolean result = false;
		if (suppliedDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			result = true;
		}
		return result;
	}

	/**
	 * Return true if date supplied is holiday.
	 * 
	 * @param suppliedDate
	 *            date for which holiday has to be checked.
	 * @return true if date supplied is holiday.
	 */
	private boolean isHoliday(final Calendar suppliedDate) {
		boolean result = false;
		if (this.sessionBean.getAcademicYearHolidays() == null) {
			this.sessionBean.loadAcademicYearHolidays(this.sectionBean.getSection().getAcademicYear());
		}
		if (this.sessionBean.getAcademicYearHolidays() != null) {
			for (AcademicYearHoliday academicYearHoliday : this.sessionBean.getAcademicYearHolidays()) {
				Calendar academicYearHolidayStartDate = Calendar.getInstance();
				academicYearHolidayStartDate.setTime(academicYearHoliday.getStartDate());
				DateUtil.clearTimeInfo(academicYearHolidayStartDate);
				Calendar academicYearHolidayEndDate = Calendar.getInstance();
				academicYearHolidayEndDate.setTime(academicYearHoliday.getEndDate());
				DateUtil.clearTimeInfo(academicYearHolidayEndDate);
				if (suppliedDate.equals(academicYearHolidayStartDate) || suppliedDate.equals(academicYearHolidayEndDate)
						|| suppliedDate.after(academicYearHolidayStartDate) && suppliedDate.before(academicYearHolidayEndDate)) {
					// this.academicYearHoliday = academicYearHoliday;
					result = true;
				}
			}
		}
		return result;
	}

	/**
	 * @return the currentAttendanceStudentAcademicYear
	 */
	public StudentAcademicYear getCurrentAttendanceStudentAcademicYear() {
		return this.currentAttendanceStudentAcademicYear;
	}

	/**
	 * @param currentAttendanceStudentAcademicYear
	 *            the currentAttendanceStudentAcademicYear to set
	 */
	public void setCurrentAttendanceStudentAcademicYear(final StudentAcademicYear currentAttendanceStudentAcademicYear) {
		this.currentAttendanceStudentAcademicYear = currentAttendanceStudentAcademicYear;
	}

	/**
	 * @return the currentReasonForCurrentStudent
	 */
	public String getCurrentReasonForCurrentStudent() {
		if (this.studentAttendanceDOs.get(this.currentAttendanceStudentAcademicYear.getId()) != null) {
			if (this.isAttendanceSubjectWise()) {
				this.currentReasonForCurrentStudent = this.studentAttendanceDOs.get(this.currentAttendanceStudentAcademicYear.getId())
						.getStudentAbsentBySectionSubjectId(this.attendanceSubject.getId()) == null ? "" : this.studentAttendanceDOs
						.get(this.currentAttendanceStudentAcademicYear.getId()).getStudentAbsentBySectionSubjectId(this.attendanceSubject.getId())
						.getAbsentReason();
			} else {
				this.currentReasonForCurrentStudent = this.studentAttendanceDOs.get(this.currentAttendanceStudentAcademicYear.getId())
						.getStudentAbsentForDailyAttendance() == null ? "" : this.studentAttendanceDOs.get(this.currentAttendanceStudentAcademicYear.getId())
						.getStudentAbsentForDailyAttendance().getAbsentReason();
			}
		} else {
			this.currentReasonForCurrentStudent = null;
		}
		return this.currentReasonForCurrentStudent;
	}

	/**
	 * @param currentReasonForCurrentStudent
	 *            the currentReasonForCurrentStudent to set
	 */
	public void setCurrentReasonForCurrentStudent(final String currentReasonForCurrentStudent) {
		this.currentReasonForCurrentStudent = currentReasonForCurrentStudent;
	}

	public void takeAttendance() {
		if (!DateUtil.getSystemDate().after(this.sectionBean.getSection().getAcademicYear().getEndDate())) {
			this.attendanceDate = DateUtil.getSystemDate();
		}
		this.branchRule = this.branchRuleService.findBranchRuleByBranchId(this.sessionBean.getCurrentBranch().getId());
		this.loadSectionSubjectsFlag = true;
		this.attendanceDate = null;
		this.attendanceSubject = null;
		this.hideAttendanceTable = true;
	}

	public void getAttendanceDetails() {
		Calendar calendarAttendaneDate = Calendar.getInstance();
		calendarAttendaneDate.setTime(this.attendanceDate);
		DateUtil.clearTimeInfo(calendarAttendaneDate);

		if (this.isWeekEnd(calendarAttendaneDate)) {
			ViewUtil.addMessage("Date selected is week end.", FacesMessage.SEVERITY_WARN);
		} else if (this.isHoliday(calendarAttendaneDate)) {
			ViewUtil.addMessage("Date selected is holiday.", FacesMessage.SEVERITY_WARN);
		} else {
			if (!this.isAttendanceSubjectWise()) {
				this.currentattendance = this.attendanceService.findAttendanceBySectionIdAndAttendanceDateForDailyAttendance(this.sectionBean.getSection()
						.getId(), this.attendanceDate);
			} else {
				this.currentattendance = this.attendanceService.findAttendanceBySectionSubjectIdAndAttendanceDate(this.attendanceSubject.getId(),
						this.attendanceDate);
			}
			if (this.currentattendance == null) {
				this.currentattendance = new Attendance();
				this.currentattendance.setAttendanceType(!this.isAttendanceSubjectWise() ? AttendanceTypeConstant.DAILY : AttendanceTypeConstant.SUBJECT_WISE);
				this.currentattendance.setSection(this.sectionBean.getSection());
				this.currentattendance.setSectionSubject(this.attendanceSubject);
			}
			this.currentattendance.setAttendanceDate(this.attendanceDate);
			this.currentattendance.setSubmittedDate(DateUtil.getSystemDate());
			this.currentattendance.setSubmittedBy(ViewUtil.getPrincipal() == null ? "anonymous" : ViewUtil.getPrincipal());
			this.loadSectionStudentAbsentDetailsByDate();
		}
	}

	public void updateStudentAttendanceForSubject() {
		StudentAbsent studentAbsent = new StudentAbsent();
		studentAbsent.setAbsentReason(this.currentReasonForCurrentStudent);
		studentAbsent.setAttendance(this.currentattendance);
		studentAbsent.setStudentAcademicYear(this.currentAttendanceStudentAcademicYear);
		if (this.studentAttendanceDOs.get(this.currentAttendanceStudentAcademicYear.getId()) == null) {
			StudentAttendanceDO studentAttendanceDO = new StudentAttendanceDO();
			studentAttendanceDO.setAttendanceDate(this.attendanceDate);
			studentAttendanceDO.setStudentAcademicYear(this.currentAttendanceStudentAcademicYear);
			studentAttendanceDO.addStudentAbsent(studentAbsent);
			this.studentAttendanceDOs.put(this.currentAttendanceStudentAcademicYear.getId(), studentAttendanceDO);
		} else {
			this.studentAttendanceDOs.get(this.currentAttendanceStudentAcademicYear.getId()).addStudentAbsent(studentAbsent);
		}

	}

	/**
	 * @return the previousAttendanceLoadDate
	 */
	public Date getPreviousAttendanceLoadDate() {
		return this.previousAttendanceLoadDate;
	}

	/**
	 * @param previousAttendanceLoadDate
	 *            the previousAttendanceLoadDate to set
	 */
	public void setPreviousAttendanceLoadDate(final Date previousAttendanceLoadDate) {
		this.previousAttendanceLoadDate = previousAttendanceLoadDate;
	}

	/**
	 * @return the hideAttendanceTable
	 */
	public boolean isHideAttendanceTable() {
		return this.hideAttendanceTable;
	}

	/**
	 * @param hideAttendanceTable
	 *            the hideAttendanceTable to set
	 */
	public void setHideAttendanceTable(final boolean hideAttendanceTable) {
		this.hideAttendanceTable = hideAttendanceTable;
	}

	/**
	 * @return the currentattendance
	 */
	public Attendance getCurrentattendance() {
		return this.currentattendance;
	}

	/**
	 * @param currentattendance
	 *            the currentattendance to set
	 */
	public void setCurrentattendance(final Attendance currentattendance) {
		this.currentattendance = currentattendance;
	}

	public void submitAttendance() {
		this.currentattendance = this.studentAttendanceService.saveStudentAbsents(this.currentattendance, this.studentAttendanceDOs.values());
		ViewUtil.addMessage("Attendance submitted successfully.", FacesMessage.SEVERITY_INFO);
	}

	public boolean isRendereDelete() {
		return this.currentAttendanceStudentAcademicYear != null ? !this.isStudentPresentForSubjectOrDate(this.currentAttendanceStudentAcademicYear.getId())
				: false;
	}

	public void deleteStudentAttendanceForSubjectOrDaily() {
		StudentAttendanceDO studentAttendanceDO = this.studentAttendanceDOs.get(this.currentAttendanceStudentAcademicYear.getId());
		if (studentAttendanceDO != null) {
			StudentAbsent studentAbsent = null;
			if (this.isAttendanceSubjectWise()) {
				studentAbsent = studentAttendanceDO.getStudentAbsentBySectionSubjectId(this.attendanceSubject.getId());
				if (studentAbsent != null) {
					studentAttendanceDO.removeStudentAbsent(studentAbsent);
				}
			} else {
				studentAbsent = studentAttendanceDO.getStudentAbsentForDailyAttendance();
				if (studentAbsent != null) {
					studentAttendanceDO.removeStudentAbsent(studentAbsent);
				}
			}
		}
	}

	/**
	 * @return the attendanceWizardActiveStep
	 */
	public String getAttendanceWizardActiveStep() {
		return this.attendanceWizardActiveStep;
	}

	/**
	 * @param attendanceWizardActiveStep
	 *            the attendanceWizardActiveStep to set
	 */
	public void setAttendanceWizardActiveStep(final String attendanceWizardActiveStep) {
		this.attendanceWizardActiveStep = attendanceWizardActiveStep;
	}

	public Date getMaxDateForTakeAttendance() {
		return this.sectionBean.getSection().getAcademicYear().getEndDate().after(DateUtil.getSystemDate()) ? DateUtil.getSystemDate() : this.sectionBean
				.getSection().getAcademicYear().getEndDate();
	}

	/**
	 * @return the loadStudentsForSectionFlag
	 */
	public boolean isLoadStudentsForSectionFlag() {
		return this.loadStudentsForSectionFlag;
	}

	/**
	 * @param loadStudentsForSectionFlag
	 *            the loadStudentsForSectionFlag to set
	 */
	public void setLoadStudentsForSectionFlag(final boolean loadStudentsForSectionFlag) {
		this.loadStudentsForSectionFlag = loadStudentsForSectionFlag;
	}

	/**
	 * @return the branchRule
	 */
	public BranchRule getBranchRule() {
		return this.branchRule;
	}

	/**
	 * @param branchRule
	 *            the branchRule to set
	 */
	public void setBranchRule(final BranchRule branchRule) {
		this.branchRule = branchRule;
	}

	public boolean isAttendanceSubjectWise() {
		return this.branchRule != null ? AttendanceTypeConstant.SUBJECT_WISE.equals(this.branchRule.getAttendanceType()) : true;
	}
}