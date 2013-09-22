package com.apeironsol.need.core.dataobject;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.apeironsol.need.core.model.SectionSubject;
import com.apeironsol.need.core.model.StudentAbsent;
import com.apeironsol.need.core.model.StudentAcademicYear;
import com.apeironsol.need.util.constants.AttendanceTypeConstant;

/**
 * 
 * @author pradeep
 * 
 */
public class StudentAttendanceDO implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long			serialVersionUID					= -6561268220313115645L;

	private Date						attendanceDate;

	private StudentAcademicYear			studentAcademicYear;

	private Collection<SectionSubject>	sectionSubjects;

	private Map<Long, StudentAbsent>	studentAbsentsBySectionSubjectId	= new HashMap<Long, StudentAbsent>();

	private StudentAbsent				studentAbsentForDailyAttendance;

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

	public void addStudentAbsent(final StudentAbsent studentAbsent) {
		if (studentAbsent.getAttendance().getAttendanceType().equals(AttendanceTypeConstant.DAILY)) {
			this.studentAbsentForDailyAttendance = studentAbsent;
		} else {
			this.studentAbsentsBySectionSubjectId.put(studentAbsent.getAttendance().getSectionSubject().getId(), studentAbsent);
		}
	}

	public void removeStudentAbsent(final StudentAbsent studentAbsent) {
		if (studentAbsent.getAttendance().getAttendanceType().equals(AttendanceTypeConstant.DAILY)) {
			this.studentAbsentForDailyAttendance = null;
		} else {
			this.studentAbsentsBySectionSubjectId.put(studentAbsent.getAttendance().getSectionSubject().getId(), null);
		}
	}

	public StudentAbsent getStudentAbsentBySectionSubjectId(final Long sectionSubjectId) {
		StudentAbsent result = null;
		if (this.studentAbsentForDailyAttendance != null) {
			result = null;
		} else {
			result = this.studentAbsentsBySectionSubjectId.get(sectionSubjectId);
		}
		return result;
	}

	/**
	 * @return the studentAcademicYear
	 */
	public StudentAcademicYear getStudentAcademicYear() {
		return this.studentAcademicYear;
	}

	/**
	 * @param studentAcademicYear
	 *            the studentAcademicYear to set
	 */
	public void setStudentAcademicYear(final StudentAcademicYear studentAcademicYear) {
		this.studentAcademicYear = studentAcademicYear;
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

	public int getNumberOfPeriodsForADay() {
		return this.sectionSubjects != null ? this.sectionSubjects.size() : 0;
	}

	public String getAbsentSubjectsAsString() {
		String result = "NA";

		if (this.studentAbsentsBySectionSubjectId != null && !this.studentAbsentsBySectionSubjectId.values().isEmpty()) {
			int noOfSubjects = this.studentAbsentsBySectionSubjectId.values().size();
			int counter = 0;
			result = "";
			for (StudentAbsent studentAbsentObj : this.studentAbsentsBySectionSubjectId.values()) {
				counter++;
				result += studentAbsentObj.getAttendance().getSectionSubject().getSubject().getName();
				if (counter < noOfSubjects) {
					result += ", ";
				}
			}
		}
		return result.trim().length() == 0 ? "NA" : result;
	}

	public String getAllSubjectsAsString() {
		String result = "NA";

		if (this.sectionSubjects != null && !this.sectionSubjects.isEmpty()) {
			int noOfSubjects = this.sectionSubjects.size();
			int counter = 0;
			result = "";
			for (SectionSubject sectionSubject : this.sectionSubjects) {
				counter++;
				result += sectionSubject.getSubject().getName();
				if (counter < noOfSubjects) {
					result += ", ";
				}
			}
		}
		return result.trim().length() == 0 ? "NA" : result;
	}

	public String getPresentSubjectsAsString() {
		String result = "NA";

		if (this.sectionSubjects != null && !this.sectionSubjects.isEmpty()) {
			int noOfSubjects = this.sectionSubjects.size();
			int counter = 0;
			result = "";
			for (SectionSubject sectionSubject : this.sectionSubjects) {
				counter++;
				if (this.getStudentAbsentBySectionSubjectId(sectionSubject.getId()) == null) {
					result += sectionSubject.getSubject().getName();
					if (counter < noOfSubjects) {
						result += ", ";
					}
				}
			}
		}
		return result.trim().length() == 0 ? "NA" : result;
	}

	/**
	 * @return the studentAbsentsBySubjectId
	 */
	public Map<Long, StudentAbsent> getStudentAbsentsBySubjectId() {
		return this.studentAbsentsBySectionSubjectId;
	}

	/**
	 * @param studentAbsentsBySubjectId
	 *            the studentAbsentsBySubjectId to set
	 */
	public void setStudentAbsentsBySubjectId(final Map<Long, StudentAbsent> studentAbsentsBySubjectId) {
		this.studentAbsentsBySectionSubjectId = studentAbsentsBySubjectId;
	}

	/**
	 * @return the studentAbsentForDailyAttendance
	 */
	public StudentAbsent getStudentAbsentForDailyAttendance() {
		return this.studentAbsentForDailyAttendance;
	}

	/**
	 * @param studentAbsentForDailyAttendance
	 *            the studentAbsentForDailyAttendance to set
	 */
	public void setStudentAbsentForDailyAttendance(final StudentAbsent studentAbsentForDailyAttendance) {
		this.studentAbsentForDailyAttendance = studentAbsentForDailyAttendance;
	}

}
